package src.Servant;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.List;

import src.DataObject.SongObject;

public interface RadioSparkApp extends Remote {
    // Publisher methods
    public boolean writeSong(SongObject song) throws RemoteException;
    public boolean deleteSong(String songName) throws RemoteException;

    // Subscriber methodss
    public Map<String, Object> getSongDetails(String songName) throws RemoteException;
    public Map<String, Object> purchaseSong(String songName) throws RemoteException;

    // Common methods
    public List<String> lookUp(String songName) throws RemoteException;
    public Map<String, Object> userSignIn(String userName, String password) throws RemoteException;
    public List<String> getSongsList() throws RemoteException;
    public boolean userSignOut(String role) throws RemoteException;

}