import java.io.File;
import java.util.HashMap;
import java.util.Stack;
import java.util.TreeSet;

/**
 * CaseInsensitive
 * class for insensitive queries
 */
public class CaseInsensitive extends AbstractInvertedIndex {
    private static CaseInsensitive caseInsensitiveIndex;

    //c-tor
    private CaseInsensitive(){}

    /**
     * creates a singleton of this current class
     * @return - case insensitive object or a warning message
     */
    public static CaseInsensitive getInstanceCaseInsensitive(){
        if(caseInsensitiveIndex == null){
            caseInsensitiveIndex = new CaseInsensitive();
            System.out.println("New CaseInsensitive index is created");
        }
        else
        {
            System.out.println("You already have a CaseInsensitive index");
        }
        return caseInsensitiveIndex;
    }

    /**
     * creates an inverted index hash map,
     * s.t the keys are the lowerCase words and the values are the file names
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
                    word = word.toLowerCase();
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
     * @return all the file names that answers the lowerCase version of the query
     */
    @Override
    public TreeSet<String> runQuery(String query){
        String tmpWord = "";
        TreeSet<String> op1 = new TreeSet<String>();
        TreeSet<String> op2 = new TreeSet<String>();
        Stack<TreeSet<String>> stack = new Stack<TreeSet<String>>();
        String[] queryArray = Utils.splitBySpace(query);
        for(int i = 0; i<queryArray.length ; i++) {
            tmpWord = queryArray[i].toLowerCase();
            if ((!tmpWord.equals("and")) && (!tmpWord.equals("or")) && (!tmpWord.equals("not"))) {
                op1 = new TreeSet<String>(invertMap.get(tmpWord));
                stack.push(op1);
            }
            if (tmpWord.equals("and")) {
                op1 = stack.pop();
                op2 = stack.pop();
                op2.retainAll(op1);
                stack.push(op2);
            }
            if (tmpWord.equals("or")) {
                op1 = stack.pop();
                op2 = stack.pop();
                op2.addAll(op1);
                stack.push(op2);
            }
            if (tmpWord.equals("not")) {
                op1 = stack.pop();
                op2 = stack.pop();
                op2.removeAll(op1);
                stack.push(op2);
            }
        }
        return stack.pop();
    }
}
