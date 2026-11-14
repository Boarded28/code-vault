import javazoom.jl.player.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;

import javazoom.jl.decoder.*;

class AudioPlayer {
    static Thread thread;
    static Player player;

    public static void play(String path) {
        path = Path.of(path).toAbsolutePath().toString();
        String ext = path.substring(path.lastIndexOf("."));
        try {
            switch (ext) {
                case ".wav" -> { playWav(path); }
                case ".mp3" -> { playMp3(path); }
                default -> {
                    System.out.println("jp3: invalid file extension");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("jp3: could not find file");
        }
        catch (Exception e) {
            System.out.println("jp3: error decoding file");
        }
    }

    public static boolean stop() {
        if (player != null) {
            player.close();
            return true;
        }
        return false;
    }

    private static void playMp3(String path) throws JavaLayerException, FileNotFoundException {
        thread = new Thread( () -> {
            try { 
                FileInputStream fis = new FileInputStream(path);
                AudioDevice dev = new JavaSoundAudioDevice();
                player = new Player(fis, dev);
                System.out.println("jp3: playing audio, type 'jp3 status' to see what's playing");
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
            } 
        });
        thread.setDaemon(true);
        thread.start();
    }

    private static void playWav(String path) {
        // TODO: Implement wav player using javax.sound.sampled
    }
}
