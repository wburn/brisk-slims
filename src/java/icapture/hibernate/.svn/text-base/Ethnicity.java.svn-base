package icapture.hibernate;

//~--- non-JDK imports --------------------------------------------------------

import icapture.com.Util;

//~--- JDK imports ------------------------------------------------------------

import java.util.Date;

public final class Ethnicity extends Persistent {
    private String ethnicity;
    private String ethnicityID;

    public String getEthnicityID() {
        return ethnicityID;
    }

    public String getEthnicity() {
        return ethnicity;
    }

/*     visible name overides allow for different fields to be used as visible name
    * alternate way to assign visibleName (in *.hbm.xml) won't allow for a mapping
    * of the visible name column to its own getter/setter methods
*/
    public String getVisibleName() {
        return ethnicity;
    }

    public String getPropertyValue(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return ethnicity;
        } else {
            return super.getPropertyValue(propertyName);
        }
    }

    public Object getPropertyObject(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return ethnicity;
        } else {
            return super.getPropertyObject(propertyName);
        }
    }

    public String[] getValueArray(byte[] indexArray, int viewSize) {
        String[] valueArray = super.getValueArray(indexArray, viewSize);

        if (indexArray[6] > 0) {
            valueArray[indexArray[6] - 1] = ethnicityID;
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = ethnicity;
        }

        return valueArray;
    }

    public void setEthnicityID(String input) {
        ethnicityID = input;
    }

    public void setEthnicity(String input) {
        ethnicity = input;
    }
}



