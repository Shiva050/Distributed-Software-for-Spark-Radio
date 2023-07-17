package src.Servant;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

import src.DataObject.SongObject;
import src.TupleSpace.TupleSpace;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class RadioSparkAppImpl extends UnicastRemoteObject implements RadioSparkApp{
    
    private Map<String, String[]> userPasswords;
    private TupleSpace tupleSpace;

    
    public  RadioSparkAppImpl() throws RemoteException {
        
        userPasswords = new HashMap<>();
        tupleSpace = new TupleSpace();
    }

    // Implementation of Subscriber methods
    public Map<String, Object> getSongDetails(String songName) throws RemoteException {
        //Implemengtation here
        if (tupleSpace.isPresent(songName)) {
            Map<String, Object> resultMap = new HashMap<>();
            SongObject result = (SongObject) tupleSpace.getItem(songName);
            resultMap.put("Name", result.getName());
            resultMap.put("Artist", result.getArtist());
            resultMap.put("Aulbum", result.getAulbum());
            resultMap.put("Duration", result.getDuration());
            return resultMap;
        } else {
            System.out.println("Sorry!, Song requested was not available");
            return null;
        }

    }

    public List<String> getSongsList() throws RemoteException{
        List<String> emptyList = Collections.emptyList();
        return emptyList;
    }

    public SongObject takeSong(String songName) throws RemoteException {
        if (tupleSpace.isPresent(songName)) {
            return (SongObject) tupleSpace.getItem(songName);
        } else {
            System.out.println("Sorry!, Song requested was not available");
            return null;
        }
    }

    // Implementation of Publisher methods

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

    public void updateSong(String songName) {
        // 
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

    // Common Methods
    public String isSongAvailable(String songName) throws RemoteException {
        //Implementation here
        System.out.println(songName);
        if (tupleSpace.isPresent(songName)) {
            return "Song found";
        } else {
            return "Song not found";
        }
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
        } else {
            System.out.println("Invalid username. \nPlease try again with a different username.");
            // continue;
        }

        if (password.equalsIgnoreCase(userPasswords.get(userName)[0])) {
            System.out.println(userPasswords.get(userName)[1] + " Authenticated");
            role = userPasswords.get(userName)[1];
            isAuthenticated = true;
            // break;
        } else {
            System.out.println("Incorrect password.\nPlease try again with a valid password.");
        }

        result.put("user", userName);
        result.put("Role", (role == null) ? "Not Authenticated" : role);
        result.put("AuthStatus", isAuthenticated);
        return result;
    }

    public void userSignOut() {
        //..
    }

    public void readUsersFile() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("../Database/userList.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(" ");
                if (columns.length == 3) {
                    String userName = columns[0].trim();
                    String password = columns[1].trim();
                    String role = columns[2].trim();
                    userPasswords.put(userName, new String[]{password, role});
                } else {
                    System.out.println("Invaild line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.print("Relay File Reader: " + e.getMessage());
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
