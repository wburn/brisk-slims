package icapture.hibernate;

//~--- non-JDK imports --------------------------------------------------------

import icapture.com.Util;

//~--- JDK imports ------------------------------------------------------------

import java.util.Date;

public final class Shipment extends Persistent {
    private String[]  shipStatus = { "None", "Shipped", "Returned" };
    private String    comments;
    private String    shipAction;
    private Date      shipDate;
    private String    shipmentID;
    private String    shipmentName;
    private ShippedTo shippedTo;

    public String getShipmentID() {
        return shipmentID;
    }

    public String getShipmentName() {
        return shipmentName;
    }

    public Date getShipDate() {
        return shipDate;
    }

    public ShippedTo getShippedTo() {
        return shippedTo;
    }

    public String getShipAction() {
        return shipAction;
    }

    public String getComments() {
        return comments;
    }

    /*
     *  visible name overides allow for different fields to be used as visible name
     * alternate way to assign visibleName (in *.hbm.xml) won't allow for a mapping
     * of the visible name column to its own getter/setter methods
     */
    public String getVisibleName() {
        return shipmentName;
    }

    public String getPropertyValue(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return shipmentName;
        } else {
            return super.getPropertyValue(propertyName);
        }
    }

    public Object getPropertyObject(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return shipmentName;
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
            valueArray[indexArray[6] - 1] = shipmentID;
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = shipmentName;
        }

        if (indexArray[8] > 0) {
            valueArray[indexArray[8] - 1] = Util.truncateDate(shipDate);
        }

        if (indexArray[9] > 0) {
            valueArray[indexArray[9] - 1] = (shippedTo != null)
                                            ? shippedTo.getDescription()
                                            : "";
        }

        if (indexArray[10] > 0) {
            valueArray[indexArray[10] - 1] = shipStatus[Integer.parseInt(shipAction)];
        }

        if (indexArray[11] > 0) {
            valueArray[indexArray[11] - 1] = ((comments == null) || comments.equals("null"))
                                             ? ""
                                             : comments;
        }

        return valueArray;
    }

    public void setShipmentID(String input) {
        shipmentID = input;
    }

    public void setShipmentName(String input) {
        shipmentName = input;
    }

    public void setShipDate(Date input) {
        shipDate = input;
    }

    public void setShippedTo(ShippedTo input) {
        shippedTo = input;
    }

    public void setShipAction(String input) {
        shipAction = input;
    }

    public void setComments(String input) {
        comments = input;
    }
}



