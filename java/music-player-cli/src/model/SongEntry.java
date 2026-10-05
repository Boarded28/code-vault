package model;

public class SongEntry {
    String title, artist;
    int id;

    SongEntry prev, next;
    
    public SongEntry(String title, String artist, int id) {
        this.title = title;
        this.artist = artist;
        this.id = id;
    }

    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public int getId() { return id; }
}
