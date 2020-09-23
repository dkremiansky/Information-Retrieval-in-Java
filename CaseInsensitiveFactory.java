/**
 * CaseInsensitiveFactory
 * class for case insensitive objects
 */
public class CaseInsensitiveFactory extends AbstractInvertedIndexFactory {

    /**
     * creates an abject of the current case
     * @return a case insensitive object
     */
    @Override
    public AbstractInvertedIndex createInvertedIndex(){
        return CaseInsensitive.getInstanceCaseInsensitive();
    }
}
