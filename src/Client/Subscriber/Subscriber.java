package src.Client.Subscriber;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import src.Client.Utils.CommonUtils;
import src.Servant.RadioSparkApp;

public class Subscriber {
    private RadioSparkApp servant;
    private BufferedReader input;

    public Subscriber() {
        try {
            String name = "//localhost/TupleSpace";
            System.out.println("***** Welcome to Distrubuted Software for SparkRadio *****");
            servant = (RadioSparkApp) Naming.lookup(name);
            input = new BufferedReader(new InputStreamReader(System.in));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public boolean userLogin(String Role) {
        return CommonUtils.userLogIn(servant, Role);
    }

    public void userSignOut() {
        try {
            String result = servant.userSignOut() ? "You logged out successfully...\n" : "Failed to log you out..\n";
            System.out.println(result);
        } catch(RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getSongDetails() {
        try {
            System.out.print("Enter the name of the song : ");
            String songName = input.readLine();
            Map<String, Object> songDetails = servant.getSongDetails(songName);
            CommonUtils.formatObject(songDetails);
        } catch(RemoteException e) {
            System.out.println(e.getMessage());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void retrieveSongs() {
        List<String> songsList = null;
        try {
            songsList = servant.getSongsList();
            if (songsList.isEmpty()) {
                System.out.println("No songs available...\n");
            } else {
                System.out.print("\nSongs in the server:" + songsList + "\n\n");
            }
        } catch(RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    public void lookUpSong() {
        try {
            System.out.print("Enter something to look up : ");
            String songName = input.readLine();

            List<String> matchingSongs = servant.lookUp(songName);
             
            // Print list elements using forEach()
             System.out.println("\nLookup results:");
             matchingSongs.forEach(System.out::println);
             System.out.println("\n");
             
        } catch(RemoteException e) {
            System.out.println(e.getMessage());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void purchaseSong() {
        try {
            System.out.print("Enter the song name: ");
            String songName = input.readLine();
            
            byte[] songFile = servant.purchaseSong(songName);
            if (songFile != null) {
                CommonUtils.byteArraytoMp3(songFile, songName);
            } else {
                System.out.println("Couldn't Retrieve the song from the server...\nPlease try again..");
            }
        } catch(RemoteException e) {
            System.out.println(e.getMessage());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}