import javazoom.jl.*;

class Player {  

  public static void play(String args[]) {    
    Thread musicThread = new Thread(() -> {
      for(int i=1; i<args.length; i++) {
        String path = args[i];
        if (!(path.contains(".wav") || path.contains(".mp3"))) {
          System.out.println("skipping path: "+path);
          continue;
        }
      }      
    });

    musicThread.start();
    if (!musicThread.isAlive())
      System.out.println("JP3 music player stopped.");
  }
}
