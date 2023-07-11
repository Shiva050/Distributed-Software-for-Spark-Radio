package src;
import java.io.Serializable;
import java.time.Duration;

public class SongObject implements Serializable {
    private String name;
    private String artist;
    private String aulbum;
    private Duration duration;
    private byte[] data;

    public SongObject(String name, String artist, String aulbum, Duration duration, byte[] data) {
        this.name = name;
        this.artist = artist;
        this.data = data;
        this.aulbum = aulbum;
        this.duration = duration;
    }

    public String getName() {
        return this.name;
    }

    public String getArtist() {
        return this.artist;
    }

    public byte[] getData() {
        return this.data;
    }

    public String getAulbum() {
        return this.aulbum;
    }

    public Duration getDuration() {
        return this.duration;
    }
    
}
