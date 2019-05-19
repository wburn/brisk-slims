package icapture.hibernate;

//~--- non-JDK imports --------------------------------------------------------

import icapture.com.Util;

//~--- JDK imports ------------------------------------------------------------

import java.util.Date;

public final class UserType extends Persistent {
    private String description;
    private String userTypeID;

    public String getUserTypeID() {
        return userTypeID;
    }

    public String getDescription() {
        return description;
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
            valueArray[indexArray[6] - 1] = userTypeID;
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = description;
        }

        return valueArray;
    }

    public void setUserTypeID(String input) {
        userTypeID = input;
    }

    public void setDescription(String input) {
        description = input;
    }
}



