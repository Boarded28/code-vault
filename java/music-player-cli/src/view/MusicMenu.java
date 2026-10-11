package view;

import java.util.Scanner;
import controller.MediaPlayer;
import util.Utils;

public class MusicMenu {

    private static boolean isRunning;
    private static MediaPlayer mp;
    private static Scanner in;
    private static String c;

    public static void open() {
        // run pre-requisites
        isRunning = true;
        in = new Scanner(System.in);
        mp = MediaPlayer.getInstance();

        // main loop        
        while (isRunning) {
            Utils.clearscreen();
            showmenu();
            userChoice(Utils.readInt(Utils.readLine(in), in, 0, 4, () -> {
                Utils.clearscreen();
                showmenu();
            }));
        }
    }

    private static void showmenu() {
        System.out.print("""

        __________________________
        === Welcome to MsP CLI ===
        |[1] Add song            |
        |[2] Remove song         |
        |[3] Show playlist       |
        |[4] Media player        |
        |[0] Exit                |
        ==========================
        [menu]$\s""");
    }
    
    private static void addSong() {
        // TODO
    }

    private static void removeSong() {
        // TODO
    }

    private static void showPlaylist() {
        // TODO
    }

    private static void exit() {
        System.out.println("Goodbye!");
        System.exit(0);
    }

    private static void showplayermenu() {
        System.out.print("""

        ____________________
        === media player ===
        |[1] Play          |
        |[2] Shuffle       |
        |[0] Back          |
        ====================
        [player]$\s""");
    }

    private static void mediaplayerI() {
        String title = mp.getSongTitle();
        String artist = mp.getArtistName();
        int titleLen = (title == null) ? 3 : title.length();
        int artistLen = (artist == null) ? 3 : artist.length();
        if (title == null || artist == null) System.out.print("\n<Playlist is empty. Try adding your music!>\n");

        System.out.printf("""
        ______________________________________________
        ============== Media Player CLI ==============
        | [Now Playing]$                             |
        | %s%s|
        | Artist: %s%s|
        | ------------------------------------------ |
        | [P]rev  [N]ext  [Enter]Pause/Play  [B]ack  |
        ==============================================
        [player]$\s""", title, " ".repeat(42-titleLen), artist, " ".repeat(34-artistLen));
    }

    private static void userChoice(int c) {        
        switch(c) {
            case 1 -> addSong();
            case 2 -> removeSong();
            case 3 -> showPlaylist();
            case 4 -> {
                while (true) {
                    Utils.clearscreen();
                    showplayermenu();
                    if (playerChoice(Utils.readInt(Utils.readLine(in), in, 0, 2, () -> {
                        Utils.clearscreen();
                        showplayermenu();
                    }))) break;
                }
            }
            case 0 -> exit();
        }
    }

    private static boolean playerChoice(int c) {
        switch (c) {
            case 1 -> {
                while (true) {
                    Utils.clearscreen();
                    mediaplayerI();
                    if (mediaPlayerChoice(Utils.readLine(in))) break;
                }
                return true;                
            }
            case 2 -> {
                mediaplayerI();
                return true;
            }
            case 0 -> {return true;}
            default -> {return false;}
        }
    }

    private static boolean mediaPlayerChoice(String c) {
        switch (c.toUpperCase()) {
            case "P" -> {return false;}
            case "N" -> {return false;}
            case "B" -> {return true;}
            case "\n" -> {
                System.out.println("<Pressed Enter>");
                return false;
            }
            default -> {return false;}
        }        
    }
}