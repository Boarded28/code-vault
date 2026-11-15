import java.io.IOException;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;

public class JP3 {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Invalid command: try 'jp3 help'");
            return;
        }
        var sockPath = Path.of(System.getProperty("java.io.tmpdir")).resolve("jp3-server.sock");
        var sockAddr = check_server(sockPath);
        System.out.println(sockAddr.toString());
        if (sockAddr != null) {
            try (var clientChannel = SocketChannel.open(sockAddr)) {
                ByteBuffer buffer = null;
                switch (args[0]) {
                    case "play" -> buffer = ByteBuffer.wrap(String.format("SONGPATH:%s",args[1]).getBytes());
                    case "stop" -> buffer = ByteBuffer.wrap("STOP".getBytes());
                    case "status" -> buffer = ByteBuffer.wrap("STATUS".getBytes());
                    case "help" -> show_help();
                    default -> System.out.println("Invalid command: try 'jp3 help'");
                }
                if (buffer != null) {
                    while (buffer.hasRemaining())
                        clientChannel.write(buffer);
                    buffer.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("jp3: error running AudioServer");
        }
    }

    private static UnixDomainSocketAddress check_server(Path sockPath) {
        if (!Files.exists(sockPath)) {
            Thread serverThread = new Thread(() -> AudioServer.main(new String[]{}));
            serverThread.setDaemon(true);
            serverThread.start();
            int attempts = 0;
            while (!Files.exists(sockPath) && attempts < 50) {
                try {
                    Thread.sleep(50);
                    attempts++;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            return UnixDomainSocketAddress.of(sockPath);
        }
        return null;
    }

    private static void show_help() {
        System.out.println("""

            usage: jp3 <option> <file_path>

            commands:
                play <file_path> : Decodes and plays audio from a music file
                                   Only supports: '.mp3', '.wav'
                stop             : Stops the entire process
                status           : Show currently playing on track
                help             : Shows all available commands

        """);
    }
}
