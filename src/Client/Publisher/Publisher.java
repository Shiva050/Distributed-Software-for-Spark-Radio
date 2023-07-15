package src.Client.Publisher;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.rmi.Naming;
import java.time.Duration;
import java.util.Map;

import src.DataObject.SongObject;
import src.Servant.RadioSparkApp;

public class Publisher {
    private RadioSparkApp tupleSpace;
    private BufferedReader input;

    public Publisher() {
        try {
            String name = "//localhost/TupleSpace";
            tupleSpace = (RadioSparkApp) Naming.lookup(name);
            input = new BufferedReader(new InputStreamReader(System.in));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> userLogin() {
        Map<String, Object> authResult = null;
        try {
            System.out.print("Enter your username: ");
            String userName = input.readLine();

            System.out.print("Enter your password: ");
            String password = input.readLine();

            authResult = tupleSpace.userSignIn(userName, password);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return authResult;
    }

    public void writeSong() {
        try {
            // Read the song file and convert it to a binary array

            System.out.print("Enter the name of the song you want to publish: ");
            String songName = input.readLine();

            System.out.print("Enter the album name of your song: ");
            String aulbum = input.readLine();

            System.out.print("Enter the artist name of your song: ");
            String artist = input.readLine();

            System.out.print("Enter the duration of your song (hours:minutes:seconds): ");
            String[] timeString = input.readLine().split(":");
            int hours = Integer.parseInt(timeString[0]);
            int minutes = Integer.parseInt(timeString[1]);
            int seconds = Integer.parseInt(timeString[2]); 
            Duration duration = Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds);

            System.out.print("Enter the name of your song (without extension): ");
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