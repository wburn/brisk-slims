package icapture.hibernate;

//~--- non-JDK imports --------------------------------------------------------

import icapture.com.Util;

//~--- JDK imports ------------------------------------------------------------

import java.util.Date;

public final class Freezer extends Persistent {
    private String  description;
    private Long    freezerID;
    private String  location;
    private Integer sortOrder;
    private String  temperature;

    public Long getFreezerID() {
        return freezerID;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getTemperature() {
        return temperature;
    }

    public Integer getSortOrder() {
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
            valueArray[indexArray[6] - 1] = freezerID.toString();
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = description;
        }

        if (indexArray[8] > 0) {
            valueArray[indexArray[8] - 1] = location;
        }

        if (indexArray[9] > 0) {
            valueArray[indexArray[9] - 1] = temperature;
        }

        if (indexArray[10] > 0) {
            valueArray[indexArray[10] - 1] = sortOrder.toString();
        }

        return valueArray;
    }

    public void setFreezerID(Long input) {
        freezerID = input;
    }

    public void setDescription(String input) {
        description = input;
    }

    public void setLocation(String input) {
        location = input;
    }

    public void setTemperature(String input) {
        temperature = input;
    }

    public void setSortOrder(Integer input) {
        sortOrder = input;
    }
}



