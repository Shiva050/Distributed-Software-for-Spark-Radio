package src.Interfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import src.Data.SongObject;

public interface TupleSpaceInterface extends Remote {
    boolean write(SongObject song) throws RemoteException;
    Map<String, Object> read(String songName) throws RemoteException;
    SongObject take(String songName) throws RemoteException;
    String lookup(String songName) throws RemoteException;
    Map<String, Object> authentication(String userName, String password) throws RemoteException;
}