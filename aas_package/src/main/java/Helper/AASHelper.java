package Helper;

/**
 * class for helpers for creating AASX Files and AAS Objects
 *
 */
public class AASHelper {
    private static final String EXCEPTION_SHORT_ID = "exception_DUMMY_ID_short";
    public static String nameToIdShort(String nameString)
    {
        if (!nameString.isEmpty())
        {
            return nameString.replaceAll("\\s","_").replace('.','_').replaceAll("[^a-zA-Z0-9]+","");

        }
        else return EXCEPTION_SHORT_ID;

    }
}
