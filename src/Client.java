package src;
import java.rmi.Naming;
import java.util.Map;

public class Client {
    public static void main(String argsp[]) {
        try {
            //Look up the remote tuple space object from the RMI Registry
            String name = "//localhost/TupleSpace";
            TupleSpaceInterface tupleSpace = (TupleSpaceInterface) Naming.lookup(name);

            //Perform operations
            Map<String, Object> songDetails = tupleSpace.read("Baby");
            System.out.println(songDetails);

            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }    
}
