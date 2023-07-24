package src.Servant;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import src.DataObject.SongObject;
import src.TupleSpace.TupleSpace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RadioSparkAppImpl extends UnicastRemoteObject implements RadioSparkApp {
    
    private Map<String, String[]> userPasswords;
    private TupleSpace tupleSpace;
    private String subscriberusername;
    private String publisherusername;
    private int userCredits;
    private Boolean subscriberLoggedIn;
    private Boolean publisherLoggedIn;

    
    public  RadioSparkAppImpl() throws RemoteException {
        this.userPasswords = new HashMap<>();
        this.tupleSpace = new TupleSpace();
        this.subscriberusername = "";
        this.publisherusername = "";
        this.subscriberLoggedIn = false;
        this.publisherLoggedIn = false;
        this.userCredits = 0;
    }

    // Implementation of Subscriber methods

    public Map<String, Object> getSongDetails(String songName) throws RemoteException {
        //Implemengtation here
        if (tupleSpace.isPresent(songName)) {
            Map<String, Object> resultMap = new HashMap<>();
            SongObject result = (SongObject) tupleSpace.getItem(songName);
            resultMap.put("Name", result.getName());
            resultMap.put("Artist", result.getArtist());
            resultMap.put("Album", result.getAulbum());
            resultMap.put("Duration", result.getDuration());
            resultMap.put("Credits", result.getCredits());
            return resultMap;
        } else {
            System.out.println("Sorry!, Song requested was not available");
            return null;
        }
    }

    public List<String> getSongsList() throws RemoteException{
        List<String> songsList = new ArrayList<>(tupleSpace.getallSongs());
        return songsList;
    }

    public Map<String, Object> purchaseSong(String songName) throws RemoteException {

        try {
            if (!this.subscriberLoggedIn) {
                System.out.println("User is not logged in.");
                return null;
            }
            
            if (!tupleSpace.isPresent(songName)) {
                System.out.println("Sorry, the requested song is not available.");
                return null;
            }
            
            SongObject song = tupleSpace.getItem(songName);
            
            if (this.userCredits < song.getCredits()) {
                System.out.println("Sorry, the user attempted to purchase the song but has insufficient credits.");
                return null;
            }
            
            int creditsUsed = song.getCredits();
            byte[] songData = song.getData();
    
            Map<String, Object> result = new HashMap<>();
            result.put("user", this.subscriberusername);
            result.put("songData", songData);
            result.put("creditsUsed", creditsUsed);
            result.put("subscriberCredits", this.userCredits);
            return result;

        } catch (NoSuchElementException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
        return null;
    }

    // Implementation of Publisher Methods

    public boolean writeSong(SongObject song) throws RemoteException {
        try {
            tupleSpace.addItem(song.getName(), song);
            System.out.println("Song Added Sucessfully");
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSong(String songName) {
        boolean result = false;
        try {
            result = tupleSpace.remove(songName);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    // Implementation of Common Methods

    public List<String> lookUp(String songName) throws RemoteException {
        List<String> matchingSongs = null;
        try {
            List<String> songs = getSongsList();
            matchingSongs = songs.stream()
                    .filter(str -> str.contains(songName))
                    .collect(Collectors.toList());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return matchingSongs;
    }

    public Map<String, Object> userSignIn(String userName, String password) throws RemoteException {
        String role = null;
        boolean isAuthenticated = false;
        Map<String, Object> result = new HashMap<>();

        if(userPasswords.isEmpty()) {
            try {
                readUsersFile();
            } catch (Exception e) {
                System.out.println("An error occurred while reading user data.");
                e.printStackTrace();
            }
        }

        if(userPasswords.containsKey(userName)) {
            System.out.println("Valid User");
            if (password.equalsIgnoreCase(userPasswords.get(userName)[0])) {
                System.out.println(userPasswords.get(userName)[1] + " Authenticated");
                role = userPasswords.get(userName)[1];
                isAuthenticated = true;
                if (role.equals("Subscriber")) {
                    this.subscriberusername = userName;
                    this.userCredits = Integer.parseInt(userPasswords.get(userName)[2]);
                    this.subscriberLoggedIn = true;
                } 
                if (role.equals("Publisher")) {
                    this.publisherusername = userName;
                    this.publisherLoggedIn = true;
                }
                
            } else {
                System.out.println("Incorrect password.\nPlease try again with a valid password.");
            }
        } else {
            System.out.println("Invalid username. \nPlease try again with a different username.");
        }

        result.put("user", userName);
        result.put("Role", (role == null) ? "Not Authenticated" : role);
        result.put("AuthStatus", isAuthenticated);
        return result;
    }

    public boolean userSignOut(String role) throws RemoteException {
        if(role.equals("Subscriber")) {
            System.out.println(this.subscriberusername + " logged out");
            this.subscriberLoggedIn = false;
            this.subscriberusername = "";
            this.userCredits = 0;
        }
        if(role.equals("Publisher")) {
            System.out.println(this.publisherusername + " logged out");
            this.publisherLoggedIn = false;
            this.publisherusername = "";
        }   
        return true;
    }

    public void readUsersFile() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("../Database/userList.txt"));
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(" ");
                if (columns.length == 4) {
                    String userName = columns[0].trim();
                    String password = columns[1].trim();
                    String role = columns[2].trim();
                    String credits = columns[3];
                    userPasswords.put(userName, new String[]{password, role, credits});
                } else {
                    System.out.println("Invaild line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.print("File Reader: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch(IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}