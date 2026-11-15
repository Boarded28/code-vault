import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javazoom.jl.player.Player;

class AudioServer {
    
    static Thread songThread;
    static boolean isPlaying = true;
    static Song first = null;

    public static void main(String[] args) {
        new AudioServer().start();
    }

    public void start() {
        var sockPath = Path.of(System.getProperty("java.io.tmpdir")).resolve("jp3-server.sock");
        try {
            Files.deleteIfExists(sockPath);
            sockPath.toFile().deleteOnExit();

            var sockAddr = UnixDomainSocketAddress.of(sockPath);
            try (var serverChannel = ServerSocketChannel.open(StandardProtocolFamily.UNIX)) {
                serverChannel.bind(sockAddr);
                ByteBuffer buffer = ByteBuffer.allocate(64);
                while (isPlaying) {
                    try (var clientChannel = serverChannel.accept()) {
                        buffer.clear();
                        int bytes = clientChannel.read(buffer);
                        if (bytes > 0) {
                            buffer.flip();
                            String response = handle_command(StandardCharsets.UTF_8.decode(buffer).toString());
                            buffer.clear();
                            if (response != null) {
                                ByteBuffer out = ByteBuffer.wrap(response.getBytes());
                                while (out.hasRemaining())
                                    clientChannel.write(out);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String handle_command(String command) {
        if (command.startsWith("SONGPATH:")) {
            String path = command.replace("SONGPATH:", "");
            if (first == null) {
                first = new Song(path);
            } else {
                Song s = first;
                for (; s.next != null; s = s.next) {}
                s = new Song(path);
            }
            if (first != null)
                start_playing();
        } else {
            switch (command) {
                case "STOP" -> {
                    isPlaying = false;
                }
                case "STATUS" -> {
                    return String.format("Now playing: %s\n", first.name);
                }
            }
        }
        return null;
    }

    private void start_playing() {
        if (!songThread.isAlive()) {
            songThread = new Thread(() -> {
                while (isPlaying) {
                    for (Song s = first; s.next != null; s = s.next) {
                        try (FileInputStream fis = new FileInputStream(first.path)) {
                            BufferedInputStream bis = new BufferedInputStream(fis);
                            Player player = new Player(bis);
                            player.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    isPlaying = false;
                }
            });
        }
    }
    class Song {
        String name, path;
        Song next;

        Song(String path) {
            this.path = path;
            String filename = Path.of(path).getFileName().toString();
            name = filename.substring(0, filename.lastIndexOf("."));
        }
    }
}
