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
        Utils.clearscreen();
        while (isRunning) {
            showmenu();
            userChoice(Utils.readInt(Utils.readLine(in), 0, 4, ()->{Utils.clearscreen();showPlaylist();}));
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
        System.out.printf("""
        ______________________________________________
        ============== Media Player CLI ==============
        | [Now Playing]$                             |
        | %s%s|
        | Artist: %s%s|
        | ------------------------------------------ |
        | [P]rev  [N]ext  [Enter]Pause/Play          |
        ==============================================
        [player]$\s""", title, " ".repeat(42-title.length()), " ".repeat(34-artist.length()));
    }

    private static void userChoice(int c) {        
        switch(c) {
            case 1 -> addSong();
            case 2 -> removeSong();
            case 3 -> showPlaylist();
            case 4 -> showplayermenu();
            case 0 -> exit();
        }
    }
}