package src.Client.Subscriber;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.util.Map;

import src.DataObject.SongObject;
import src.Servant.RadioSparkApp;

public class Subscriber {

    private RadioSparkApp tupleSpace;
    private BufferedReader input;

    public Subscriber() {
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

    public void searchSong() {
        try {
            System.out.print("Enter the song name you want to search: ");
            String songInput = input.readLine();

            // Lookup Operation
            System.out.println(tupleSpace.getSongsList());

            // Read operation
            Map<String, Object> songDetails = tupleSpace.getSongDetails(songInput);
            System.out.println(songDetails);

            // Take Operation
            SongObject takeSong = tupleSpace.takeSong(songInput);
            System.out.println(takeSong.getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
