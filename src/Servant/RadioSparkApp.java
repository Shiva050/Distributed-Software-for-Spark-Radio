package src.Servant;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import src.DataObject.SongObject;

public interface RadioSparkApp extends Remote {
    // Publisher methods
    public boolean writeSong(SongObject song) throws RemoteException;
    public void updateSong(String songName) throws RemoteException;
    public void deleteSong(String songName) throws RemoteException;

    // Client methodss
    public Map<String, Object> getSongDetails(String songName) throws RemoteException;
    public List<String> getSongsList() throws RemoteException;
    SongObject takeSong(String songName) throws RemoteException;

    // Common methods
    public String isSongAvailable(String songName) throws RemoteException;
    public Map<String, Object> userSignIn(String userName, String password) throws RemoteException;
    public void userSignOut() throws RemoteException;

}