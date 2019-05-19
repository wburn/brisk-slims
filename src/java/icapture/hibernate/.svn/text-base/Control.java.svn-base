
/**
 *
 * @author tvanrossum
 */
package icapture.hibernate;

//~--- non-JDK imports --------------------------------------------------------

import icapture.com.Util;

//~--- JDK imports ------------------------------------------------------------

import java.util.Date;

public final class Control extends Persistent {
    private String controlID;
    private String controlType;
    private String description;

    public String getControlID() {
        return controlID;
    }

    public String getDescription() {
        return description;
    }

    public String getControlType() {
        return controlType;
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
            valueArray[indexArray[6] - 1] = controlID;
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = description;
        }

        if (indexArray[8] > 0) {
            valueArray[indexArray[8] - 1] = (controlType.equals("0"))
                                            ? "negative"
                                            : (controlType.equals("1"))
                                              ? "positive"
                                              : controlType;
        }

        return valueArray;
    }

    public void setControlID(String input) {
        controlID = input;
    }

    public void setDescription(String input) {
        description = input;
    }

    public void setControlType(String input) {
        controlType = input;
    }
}



