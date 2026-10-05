package controller;

import model.SongEntry;

public class MusicTracker {
    private SongEntry current;

    public SongEntry getCurrent() { return current; }
    
    public void insertSong(SongEntry song) {
        if (current == null)
            current = song;
    }
}
