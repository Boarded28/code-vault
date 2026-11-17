import java.io.IOException;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;

public class JP3 {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Invalid option: try 'jp3 help'");
            return;
        }
        String payload = switch (args[0]) {
            case "-p", "play", "--play" -> { 
                if (args.length < 2) {
                    System.out.println("Invalid option: try 'jp3 help'");
                    yield null;
                }
                yield String.format("SONGPATH:%s",args[1]); 
            }
            case "-s", "stop", "--stop" -> { yield "STOP"; }
            case "-u", "status", "--status" -> { yield "STATUS"; }
            case "-h", "help", "--help" -> {
                System.out.println("""

                        command: jp3 <option> <file_path>

                        options:                    Description:
                        -h, --help, help        -: shows the list of commands
                        -p, --play, play        -: plays a song from <file_path>, only supports .wav and .mp3
                        -s, --stop, stop        -: stops the song and kills the AudioServer
                        -u, --status, status    -: shows the current song playing in the AudioServer

                        """);
                yield null;
            }
            default -> {
                System.out.println("Invalid option: try 'jp3 help'");
                yield null;
            }
        };

        if (payload != null) {
            String addr = Path.of(System.getProperty("java.io.tmpdir"))
                .resolve("jp3-server.sock")
                .toString();
            var sockAddr = UnixDomainSocketAddress.of(addr);
            try (var clientChannel = SocketChannel.open(sockAddr)) {
                ByteBuffer outBuffer = ByteBuffer.wrap(payload.getBytes());
                clientChannel.write(outBuffer);
                outBuffer.clear();
            } catch (IOException e) {e.printStackTrace();}
        }
    }
}
