package src;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.rmi.Naming;
import java.util.Map;
import java.time.Duration;

public class Client {
    public static void main(String argsp[]) {
        try {
            //Look up the remote tuple space object from the RMI Registry
            String name = "//localhost/TupleSpace";
            TupleSpaceInterface tupleSpace = (TupleSpaceInterface) Naming.lookup(name);

            // Read data from the user
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
 
            // Reading data using readLine
            System.out.println("Enter the song name you want to search: ");
            String song_input = input.readLine();

            //Perform operations

            // Read the song file and convert it to a binary array
            String songName = "Salaar Teaser";
            String artist = "Ravi Basrur";
            String aulbum = "Salaar";
            Duration duration = Duration.ofHours(0).plusMinutes(01).plusSeconds(49);
            String filePath = "../Songs/Salaar.mp3";
           
            byte[] songData = Files.readAllBytes(new File(filePath).toPath());

            // Create a Song object
            SongObject song = new SongObject(songName, artist, aulbum, duration, songData);

            // Publish the song to the tuple space
            tupleSpace.write(song);

            // Lookup Operation
            System.out.println(tupleSpace.lookup(song_input));

            // Read operation
            Map<String, Object> songDetails = tupleSpace.read(song_input);
            System.out.println(songDetails);

            // Take Operation
            SongObject take_song = tupleSpace.take(song_input);
            System.out.println(take_song.getData());

            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }    
}
