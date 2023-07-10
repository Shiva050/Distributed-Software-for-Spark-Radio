import java.io.Serializable;

public class SongObject implements Serializable {
    private String name;
    private String artist;
    private String aulbum;
    private String duration;
    private byte[] data;

    public SongObject(String name, String artist, String aulbum, String duration, byte[] data) {
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

    public String getDuration() {
        return this.duration;
    }
    
}
