package src;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.HashMap;

public class TupleSpaceImpl extends UnicastRemoteObject implements TupleSpaceInterface{
    private Map<String, SongObject> songs;
    
    public  TupleSpaceImpl() throws RemoteException {
        songs = new HashMap<>();
    }

    public String lookup(String songName) throws RemoteException {
        //Implementation here
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

    public void write(SongObject song) throws RemoteException {
        // Implementation
        songs.put(song.getName(), song);
        System.out.println("Song Added Sucessfully");

    }
}
