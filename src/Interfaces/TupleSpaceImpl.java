package src.Interfaces;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import src.Data.SongObject;
import java.util.HashMap;

public class TupleSpaceImpl extends UnicastRemoteObject implements TupleSpaceInterface{
    private Map<String, SongObject> songs;
    Map<String, String[]> userPasswords;
    
    public  TupleSpaceImpl() throws RemoteException {
        songs = new HashMap<>();
        userPasswords = new HashMap<>();
    }

    public String lookup(String songName) throws RemoteException {
        //Implementation here
        System.out.println(songName);
        if (songs.containsKey(songName)) {
            return "Song found";
        } else {
            return "Song not found";
        }
    }

    public Map<String, Object> read(String songName) throws RemoteException {
        //Implemengtation here
        if (songs.containsKey(songName)) {
            Map<String, Object> resultMap = new HashMap<>();
            SongObject result = (SongObject) songs.get(songName);
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

    public SongObject take(String songName) throws RemoteException {
        if (songs.containsKey(songName)) {
            return songs.get(songName);
        } else {
            System.out.println("Sorry!, Song requested was not available");
            return null;
        }
    }

    public boolean write(SongObject song) throws RemoteException {
        // Implementation

        try {
            songs.put(song.getName(), song);
            System.out.println("Song Added Sucessfully");
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void readUsersFile() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("../security/userList.txt"));
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

    public Map<String, Object> authentication(String userName, String password) throws RemoteException {
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

        while(true) {
            if(userPasswords.containsKey(userName)) {
                System.out.println("Valid User");
            } else {
                System.out.println("Invalid username. \nPlease try again with a different username.");
                continue;
            }

            if (password.equalsIgnoreCase(userPasswords.get(userName)[0])) {
                System.out.println("Authenticated");
                role = userPasswords.get(userName)[1];
                isAuthenticated = true;
                break;
            } else {
                System.out.println("Incorrect password.\nPlease try again with a valid password.");
            }
        }

        result.put("Role", role);
        result.put("AuthStatus", isAuthenticated);
        return result;
    }
}
