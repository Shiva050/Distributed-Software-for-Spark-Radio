package src.TupleSpace;
import java.util.HashMap;
import java.util.Map;

import src.DataObject.SongObject;

public class TupleSpace {
    private Map<String, SongObject> tupleSpace;

    public TupleSpace() { // Constructor
        tupleSpace = new HashMap<>();
    }

    public void addItem(String key, SongObject value) { // add item into the tuple space
        tupleSpace.put(key, value);
    }

    public SongObject getItem(String key) { // get item from the tuple space
        return tupleSpace.get(key);
    }

    public void remove(String key) { // remove item from the tuple sapce
        tupleSpace.remove(key);
    }

    public boolean isPresent(String key) { // check if the item is present in tuple space or not
        return tupleSpace.containsKey(key);
    }

}