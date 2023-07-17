package src.Client.Subscriber;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.util.Map;

import src.Client.Utils.CommonUtils;
import src.DataObject.SongObject;
import src.Servant.RadioSparkApp;

public class Subscriber {

    private RadioSparkApp servant;
    private BufferedReader input;

    public Subscriber() {
        try {
            String name = "//localhost/TupleSpace";
            servant = (RadioSparkApp) Naming.lookup(name);
            input = new BufferedReader(new InputStreamReader(System.in));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public boolean userLogin(String Role) {
        return CommonUtils.userLogIn(servant, Role);
    }

    public void searchSong() {
        try {
            System.out.print("Enter the song name you want to search: ");
            String songInput = input.readLine();

            // Lookup Operation
            System.out.println(servant.getSongsList());

            // Read operation
            Map<String, Object> songDetails = servant.getSongDetails(songInput);
            System.out.println(songDetails);

            // Take Operation
            SongObject takeSong = servant.takeSong(songInput);
            System.out.println(takeSong.getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
