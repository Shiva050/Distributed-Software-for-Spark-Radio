package src.Client.Publisher;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.rmi.Naming;
import java.time.Duration;
import java.util.Map;

import src.Data.SongObject;
import src.Interfaces.TupleSpaceInterface;

public class Publisher {
    private TupleSpaceInterface tupleSpace;
    private BufferedReader input;

    public Publisher() {
        try {
            String name = "//localhost/TupleSpace";
            tupleSpace = (TupleSpaceInterface) Naming.lookup(name);
            input = new BufferedReader(new InputStreamReader(System.in));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void authentication() {
        try {
            System.out.println("Enter your username: ");
            String userName = input.readLine();

            System.out.println("Enter your password: ");
            String password = input.readLine();

            Map<String, Object> authresult = tupleSpace.authentication(userName, password);
            System.out.println(authresult);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void writeSong() {
        try {
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

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}