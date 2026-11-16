import java.nio.file.Path;

public class JP3 {
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Invalid option: try 'jp3 help'");
            return;
        }
        String payload = switch (args[0]) {
            case "-p", "play", "--play" -> { yield String.format("SONGPATH:%s",args[1]); }
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
            String addr = Path.of(System.getProperty("java.io.tmpdir")).resolve("jp3-server.sock").toString();
            System.out.println(addr);
        }
    }
}
