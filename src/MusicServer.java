package src;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MusicServer {
    public static void main(String args[]) {
        try {
            // Create an instance of Tuple Space Implementation
            TupleSpaceImpl tupleSpace = new TupleSpaceImpl();

            //Start the RMI registry on the default port
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);

            //Bind the Tuple Space object to the RMI registery
            Naming.rebind("//localhost/TupleSpace", tupleSpace);

            System.out.println("Server Started");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
