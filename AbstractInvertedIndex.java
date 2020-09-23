import java.io.File;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * AbstractInvertedIndex
 * abstract class for both cases of sensitive and insensitive cases of queries
 */
public abstract class AbstractInvertedIndex {

    protected HashMap<String, TreeSet<String>> invertMap;

    /**
     * abstract method
     * creates a singleton of this current class
     */
    public abstract void buildInvertedIndex(File[] fileArray);

    /**
     * abstract method
     * gets a boolean query and returns all the file names that answers this query
     * @param query - a boolean query in form of reverse polish nation
     */
    public abstract TreeSet<String> runQuery(String query);
}
