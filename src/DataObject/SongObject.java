package src.DataObject;
import java.io.Serializable;
import java.time.Duration;

public class SongObject implements Serializable {
    private String name;
    private String artist;
    private String aulbum;
    private Duration duration;
    private int credits;
    private byte[] data;

    public SongObject(String name, String artist, String aulbum, Duration duration, int credits ,byte[] data) {
        this.name = name;
        this.artist = artist;
        this.data = data;
        this.aulbum = aulbum;
        this.duration = duration;
        this.credits = credits;
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

    public int getCredits() {
        return this.credits;
    }
    
}
