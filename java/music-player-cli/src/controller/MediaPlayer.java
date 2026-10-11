package controller;

public class MediaPlayer implements Runnable {
    
    private static MediaPlayer instance;
    private Thread mediaThread;

    private MusicTracker mt;

    MediaPlayer() {
        mt = new MusicTracker();
        mediaThread = new Thread(this);
        mediaThread.start();
    }

    public static MediaPlayer getInstance() { return (instance == null) ? instance = new MediaPlayer() : instance; }

    public String getSongTitle() { return (mt.getCurrent() == null) ? null : mt.getCurrent().getTitle(); }

    public String getArtistName() { return (mt.getCurrent() == null) ? null : mt.getCurrent().getArtist(); }

    @Override 
    public void run() {
        
    }
}
