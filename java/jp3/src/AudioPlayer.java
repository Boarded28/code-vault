import javazoom.jl.player.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.nio.file.Path;

import javazoom.jl.decoder.*;

class AudioPlayer {  

  public static void play(String args[]) {    
    Thread musicThread = new Thread(() -> {
      for(int i=1; i<args.length; i++) {
        String absPath = args[i];
        Path path = Paths.get(absPath);
        String name = path.getFileName().toString();

        try {
          if (absPath.endsWith(".wav")) {
            
          } else if (absPath.endsWith(".mp3")) {

          } else {
            System.out.println("\'"+absPath+"\' is not a valid file/path");
          }
        } catch (Exception e) {
          System.out.println("Error playing file: " + e.toString());
        }
      }
    });

    musicThread.start();
    if (!musicThread.isAlive())
      System.out.println("JP3 music player stopped.");
  }

  private static void playMP3(String path) throws JavaLayerException, FileNotFoundException {
    FileInputStream fis = new FileInputStream(path);
    Player player = new Player(fis);
    player.play();
  }

  private static void playWAV(String path) {
    // TODO: Implement wav player using javax.sound.sampled
  }
}
