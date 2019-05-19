package icapture.hibernate;

//~--- non-JDK imports --------------------------------------------------------

import icapture.com.Util;

//~--- JDK imports ------------------------------------------------------------

import java.util.Date;

public final class Cohort extends Persistent {
    private String cohortID;
    private String description;
    private String sortOrder;

    public String getCohortID() {
        return cohortID;
    }

    public String getDescription() {
        return description;
    }

    public String getSortOrder() {
        return sortOrder;
    }

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
            valueArray[indexArray[6] - 1] = cohortID;
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = description;
        }

        if (indexArray[8] > 0) {
            valueArray[indexArray[8] - 1] = sortOrder;
        }

        return valueArray;
    }

    public void setCohortID(String input) {
        cohortID = input;
    }

    public void setDescription(String input) {
        description = input;
    }

    public void setSortOrder(String input) {
        sortOrder = input;
    }
}



