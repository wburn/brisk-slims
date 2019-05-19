package icapture.hibernate;

//~--- non-JDK imports --------------------------------------------------------

import icapture.com.Util;

//~--- JDK imports ------------------------------------------------------------

import java.util.Date;

public final class SampleProcess extends Persistent {
    private String description;
    private String sampleProcessID;
    private String sortOrder;

    public String getSampleProcessID() {
        return sampleProcessID;
    }

    public String getDescription() {
        return description;
    }

    public String getSortOrder() {
        return sortOrder;
    }

/*     visible name overides allow for different fields to be used as visible name
    * alternate way to assign visibleName (in *.hbm.xml) won't allow for a mapping
    * of the visible name column to its own getter/setter methods
*/
    public String getVisibleName() {
        return description;
    }

    public String getPropertyValue(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return description;
        } else {
            return super.getPropertyValue(propertyName);
        }
    }

    public Object getPropertyObject(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return description;
        } else {
            return super.getPropertyObject(propertyName);
        }
    }

    public String[] getValueArray(byte[] indexArray, int viewSize) {
        return getValueArrayReadable(indexArray, viewSize);
    }

    public String[] getValueArrayReadable(byte[] indexArray, int viewSize) {
        String[] valueArray = super.getValueArray(indexArray, viewSize);

        if (indexArray[6] > 0) {
            valueArray[indexArray[6] - 1] = sampleProcessID;
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = description;
        }

        if (indexArray[8] > 0) {
            valueArray[indexArray[8] - 1] = sortOrder;
        }

        return valueArray;
    }

    public void setSampleProcessID(String input) {
        sampleProcessID = input;
    }

    public void setDescription(String input) {
        description = input;
    }

    public void setSortOrder(String input) {
        sortOrder = input;
    }
}



