import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface TupleSpaceInterface extends Remote {
    void write(SongObject song) throws RemoteException;
    Map<String, Object> read(String songName) throws RemoteException;
    SongObject take(String songName) throws RemoteException;
    String lookup(String songName) throws RemoteException;
}