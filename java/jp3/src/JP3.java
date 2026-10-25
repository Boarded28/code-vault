public class JP3 {

    static String version = "jp3 0.0.1 beta";

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Unknown command: usage \'jp3 <option>\'");
            return;
        }

        switch (args[0]) {
            case "help" -> {
                System.out.println("""
                usage: jp3 <option> ...
                
                Options:
                    version                          : shows the program's current version
                    help                             : shows the list of options and usage
                    play <filepath1> <filepath2> ... : plays the audio of the specified filepath
                                                       Filetypes: .mp3, .wav
                    stop                             : stops playing and kills the process
                    status                           : shows the status of the player
                                                       Status: playing <song>, sleeping            
                """);
            }

            case "version" -> {
                System.out.println(version);
            }

            case "play" -> {
                if (args.length < 2) {
                    System.out.println("Invalid command usage: use \'help\' to list all commands");
                    return;
                }                         
                Player.play(args);                
            }

            default -> {
                System.out.println("Unknown command: use \'jp3 help\' to list all commands");
            }
        }

    }
    
}
