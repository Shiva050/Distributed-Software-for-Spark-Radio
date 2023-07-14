package src.Client.Subscriber;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.util.Map;
import src.Data.SongObject;
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

    public void userLogIn() {
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

    public void searchSong() {
        try {
            System.out.println("Enter the song name you want to search: ");
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
