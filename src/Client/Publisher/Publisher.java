package src.Client.Publisher;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.rmi.Naming;
import java.time.Duration;
import java.util.Map;
import src.Data.SongObject;
import src.Servant.RadioSparkAppImpl;

public class Publisher {
    private RadioSparkAppImpl tupleSpace;
    private BufferedReader input;

    public Publisher() {
        try {
            String name = "//localhost/TupleSpace";
            tupleSpace = (RadioSparkAppImpl) Naming.lookup(name);
            input = new BufferedReader(new InputStreamReader(System.in));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void userLogin() {
        try {
            System.out.println("Enter your username: ");
            String userName = input.readLine();

            System.out.println("Enter your password: ");
            String password = input.readLine();

            Map<String, Object> authresult = tupleSpace.userSignIn(userName, password);
            System.out.println(authresult);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void writeSong() {
        try {
            // Read the song file and convert it to a binary array

            System.out.println("Enter the name of the song you want to publish: ");
            String songName = input.readLine();

            System.out.println("Enter the album name of your song: ");
            String aulbum = input.readLine();

            System.out.println("Enter the artist name of your song: ");
            String artist = input.readLine();

            System.out.println("Enter the duration of your song (hours:minutes:seconds): ");
            String[] timeString = input.readLine().split(":");
            int hours = Integer.parseInt(timeString[0]);
            int minutes = Integer.parseInt(timeString[1]);
            int seconds = Integer.parseInt(timeString[2]); 
            Duration duration = Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds);

            System.out.println("Enter the name of your song (without extension): ");
            String songValue = input.readLine();
            
            String songPath = "../src/Client/Publisher/Songs/"+songValue+".mp3";

            byte[] songData = Files.readAllBytes(new File(songPath).toPath());

            // Create a Song object
            SongObject song = new SongObject(songName, artist, aulbum, duration, seconds, songData);

            // Publish the song to the tuple space
            tupleSpace.writeSong(song);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}