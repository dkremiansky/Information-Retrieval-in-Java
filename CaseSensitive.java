import java.io.File;
import java.util.HashMap;
import java.util.Stack;
import java.util.TreeSet;

/**
 * CaseInsensitive
 * class for sensitive queries
 */
public class CaseSensitive extends AbstractInvertedIndex {
    private static CaseSensitive caseSensitiveIndex;

    //c-tor
    private CaseSensitive(){}

    /**
     * creates a singleton of this current class
     * @return - case sensitive object or a warning message
     */
    public static CaseSensitive getInstanceCaseSensitive(){
        if(caseSensitiveIndex == null){
            caseSensitiveIndex = new CaseSensitive();
            System.out.println("New CaseSensitive index is created");
        }
        else
        {
            System.out.println("You already have a CaseSensitive index");
        }
        return caseSensitiveIndex;
    }

    /**
     * creates an inverted index hash map,
     * s.t the keys are the words and the values are the file names
     * @param fileArray - array with the file names
     */
    @Override
    public void buildInvertedIndex(File[] fileArray){
        invertMap = new HashMap<String, TreeSet<String>>();
        TreeSet<String> values;
        for(File tmpFile:fileArray){
            for(String tmpLine:Utils.readLines(tmpFile)){
                if(tmpLine == null)
                    continue;
                for(String word:Utils.splitBySpace(tmpLine)){
                    if(invertMap.containsKey(word)){
                        invertMap.get(word).add(tmpFile.getName());
                    }
                    else{
                        values = new TreeSet<String>();
                        values.add(tmpFile.getName());
                        invertMap.put(word,values);
                    }
                }
            }
        }
    }

    /**
     * gets a boolean query and returns all the file names that answers this query
     * @param query - a boolean query in form of reverse polish nation
     * @return all the file names that answers the query
     */
    @Override
    public TreeSet<String> runQuery(String query){
    String tmpWord = "";
    TreeSet<String> op1 = new TreeSet<String>();
    TreeSet<String> op2 = new TreeSet<String>();
    Stack<TreeSet<String>> stack = new Stack<TreeSet<String>>();
    String[] queryArray = Utils.splitBySpace(query);
    for(int i = 0; i<queryArray.length ; i++) {
        tmpWord = queryArray[i];
        if ((!tmpWord.equals("AND")) && (!tmpWord.equals("OR")) && (!tmpWord.equals("NOT"))) {
            op1 = new TreeSet<String>(invertMap.get(tmpWord));
            stack.push(op1);
        }
        if (tmpWord.equals("AND")) {
            op1 = stack.pop();
            op2 = stack.pop();
            op2.retainAll(op1);
            stack.push(op2);
        }
        if (tmpWord.equals("OR")) {
            op1 = stack.pop();
            op2 = stack.pop();
            op2.addAll(op1);
            stack.push(op2);
        }
        if (tmpWord.equals("NOT")) {
            op1 = stack.pop();
            op2 = stack.pop();
            op2.removeAll(op1);
            stack.push(op2);
        }
    }
    return stack.pop();
    }
}
