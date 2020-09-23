/**
 * CaseSensitiveFactory
 * class for case sensitive objects
 */
public class CaseSensitiveFactory extends AbstractInvertedIndexFactory {

    /**
     * creates an abject of the current case
     * @return a case sensitive object
     */
    @Override
    public AbstractInvertedIndex createInvertedIndex(){
        return CaseSensitive.getInstanceCaseSensitive();
    }
}
