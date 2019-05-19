package icapture.com.xmlWriter;

//~--- non-JDK imports --------------------------------------------------------

import icapture.hibernate.Fieldname;

public class XMLUtil {
    protected static String       DATE_TYPE     = "date";
    protected static int          INT_UNDEF     = Integer.MIN_VALUE;
    protected static int          INT_MIN_VALUE = Integer.MIN_VALUE + 1;
    protected static int          INT_MAX_VALUE = Integer.MAX_VALUE;
    protected static String       LIST_TYPE     = "objectList";
    protected static String       NUMERIC_TYPE  = "numeric";
    protected static String       OBJECT_UNDEF  = "Undef";
    protected static final String PERSON        = Fieldname.PERSON;
    protected static final String SAMPLE        = Fieldname.SAMPLE;
    protected static final String ALIQUOT       = Fieldname.ALIQUOT;
    protected static String       STRING_TYPE   = "string";
    protected static String       TITLE         = "title";
    protected static String       USER_TYPE     = "user";
    protected static int          objectCount   = 3;

    public static void vassert(Object value) {
        XMLUtil.vassert(value != null);
    }

    public static void vassert(boolean value) {
        if (!value) {
            System.out.println("Assertion failed!");

            Throwable e = new Throwable("Assertion failed!");

            e.printStackTrace();
            System.exit(1);
        }
    }
}



