package icapture.hibernate;

//~--- non-JDK imports --------------------------------------------------------

import icapture.com.Util;

//~--- JDK imports ------------------------------------------------------------

import java.util.Date;
import java.util.Iterator;
import java.util.List;

public final class ShippedTo extends Persistent {
    private String description;
    private String shippedToID;
    private String sortOrder;

    public String getShippedToID() {
        return shippedToID;
    }

    public String getDescription() {
        return description;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    @Override
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
            valueArray[indexArray[6] - 1] = shippedToID;
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = description;
        }

        if (indexArray[8] > 0) {
            valueArray[indexArray[8] - 1] = sortOrder.toString();
        }

        return valueArray;
    }

    public void setShippedToID(String shipID) {
        shippedToID = shipID;
    }

    public void setDescription(String desc) {
        description = desc;
    }

    public void setSortOrder(String order) {
        sortOrder = order;
    }
}



