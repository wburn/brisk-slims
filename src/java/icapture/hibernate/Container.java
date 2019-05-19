package icapture.hibernate;

//~--- non-JDK imports --------------------------------------------------------

import icapture.com.Util;


//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class Container extends Persistent {
    private String[]       shipStatus  = { "None", "Shipped", "Returned" };
    private Date           shippedDate = null;
    private String         shippedOut  = null;
    private ShippedTo      shippedTo   = null;
    private List<ShipCont> shipCont    = new ArrayList<ShipCont>(0);
    private String         barcode;
    private String         checkedOut;
    private String         comments;
    private String         containerAlias;
    private String         containerID;
    private String         containerName;
    private ContainerType  containerType;
    private Date           dateOnContainer;
    private String         discarded;
    private Freezer        freezer;
    private String         initials;
    private String         isStock;
    private String         location;
    private String         lot;
    private String         shelf;
    private String         valid;

    public List<ShipCont> getShipCont() {
        if (shipCont != null) {
            shipCont.size();
        }

        return shipCont;
    }

    public String getCheckedOut() {
        return checkedOut;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getContainerID() {
        return containerID;
    }

    public String getcontainerName() {
        return containerName;
    }

    public ContainerType getContainerType() {
        return containerType;
    }

    public Freezer getFreezer() {
        return freezer;
    }

    public String getShelf() {
        return shelf;
    }

    public String getLocation() {
        return location;
    }

    public String getDiscarded() {
        return discarded;
    }

    public String getComments() {
        return comments;
    }

    public String getIsStock() {
        return isStock;
    }

    public String getContainerAlias() {
        return containerAlias;
    }

    public String getLot() {
        return lot;
    }

    public String getInitials() {
        return initials;
    }

    public String getValid() {
        return valid;
    }

    public Date getDateOnContainer() {
        return dateOnContainer;
    }

    @Override
    public String getVisibleName() {
        return containerName;
    }

//  want visible name to be the ID

    @Override
    public String getPropertyValue(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return containerName;
        } else {
            return super.getPropertyValue(propertyName);
        }
    }

    @Override
    public Object getPropertyObject(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return containerName;
        } else {
            return super.getPropertyObject(propertyName);
        }
    }

    @Override
    public String[] getValueArray(byte[] indexArray, int viewSize) {
        return getValueArrayReadable(indexArray, viewSize);
    }

    /**
     *
     * @param indexArray
     * @param viewSize
     * @return
     */
    @Override
    public String[] getValueArrayReadable(byte[] indexArray, int viewSize) {
        ShipCont lastItem = null;
        if (shipCont != null && shipCont.size()>0){
            lastItem = shipCont.get(shipCont.size()-1);
        }

        String[] valueArray = super.getValueArray(indexArray, viewSize);

        if (indexArray[6] > 0) {
            valueArray[indexArray[6] - 1] = containerID;
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = containerName;
        }

        if (indexArray[8] > 0) {
            valueArray[indexArray[8] - 1] = (containerType != null)
                                            ? containerType.getDescription()
                                            : "";
        }

        if (indexArray[9] > 0) {
            valueArray[indexArray[9] - 1] = (freezer != null)
                                            ? freezer.getDescription()
                                            : "";
        }

        if (indexArray[10] > 0) {
            valueArray[indexArray[10] - 1] = shelf;
        }

        if (indexArray[11] > 0) {
            valueArray[indexArray[11] - 1] = (discarded.equals("0"))
                                             ? "No"
                                             : "Yes";
        }

        if (indexArray[12] > 0) {
            shippedOut = "0";

            if ((lastItem != null) && (lastItem.getShipment() != null)) {
                shippedOut = lastItem.getShipment().getShipAction();
            }

            valueArray[indexArray[12] - 1] = shipStatus[Integer.parseInt(shippedOut)];
        }

        if (indexArray[13] > 0) {
            shippedDate = null;

            if ((lastItem != null) && (lastItem.getShipment() != null)) {
                shippedDate = lastItem.getShipment().getShipDate();
            }

            valueArray[indexArray[13] - 1] = Util.truncateDate(shippedDate);
        }

        if (indexArray[14] > 0) {
            shippedTo = null;

            if ((lastItem != null) && (lastItem.getShipment() != null)) {
                shippedTo = lastItem.getShipment().getShippedTo();
            }

            valueArray[indexArray[14] - 1] = (shippedTo != null)
                                             ? shippedTo.getDescription()
                                             : "";
        }

        if (indexArray[15] > 0) {
            valueArray[indexArray[15] - 1] = comments;
        }

        if (indexArray[16] > 0) {
            valueArray[indexArray[16] - 1] = (isStock.equals("0"))
                                             ? "No"
                                             : "Yes";
        }

        if (indexArray[17] > 0) {
            valueArray[indexArray[17] - 1] = ((valid == null) || valid.equals("0"))
                                             ? "no"
                                             : "yes";
        }

        if (indexArray[18] > 0) {
            valueArray[indexArray[18] - 1] = containerAlias;
        }

        if (indexArray[19] > 0) {
            valueArray[indexArray[19] - 1] = lot;
        }

        if (indexArray[20] > 0) {
            valueArray[indexArray[20] - 1] = initials;
        }

        if (indexArray[21] > 0) {
            valueArray[indexArray[21] - 1] = Util.truncateDate(dateOnContainer);
        }

        if (indexArray[22] > 0) {
            valueArray[indexArray[22] - 1] = location;
        }

        if (indexArray[23] > 0) {
            valueArray[indexArray[23] - 1] = barcode;
        }

        if (indexArray[24] > 0) {
            valueArray[indexArray[24] - 1] = (checkedOut == null)
                                             ? "Available"
                                             : (!discarded.equals("0"))
                                               ? "Discarded"
                                               : "Checked Out by:" + checkedOut;
        }

        return valueArray;
    }

    public void setContainerID(String input) {
        containerID = input;
    }

    public void setcontainerName(String input) {
        containerName = input;
    }

    public void setContainerType(ContainerType input) {
        containerType = input;
    }

    public void setFreezer(Freezer input) {
        freezer = input;
    }

    public void setShelf(String input) {
        shelf = input;
    }

    public void setLocation(String input) {
        location = input;
    }

    public void setDiscarded(String input) {
        discarded = input;
    }

    public void setComments(String input) {
        comments = input;
    }

    public void setIsStock(String input) {
        isStock = input;
    }

    public void setContainerAlias(String input) {
        containerAlias = input;
    }

    public void setLot(String input) {
        lot = input;
    }

    public void setInitials(String input) {
        initials = input;
    }

    public void setValid(String input) {
        valid = input;
    }

    public void setDateOnContainer(Date input) {
        dateOnContainer = input;
    }

    public void setBarcode(String input) {
        barcode = input;
    }

    public void setCheckedOut(String input) {
        checkedOut = input;
    }

    public void setShipCont(List<ShipCont> shipCont) {
        if (this.shipCont != null) {
            this.shipCont.size();
        }

        this.shipCont = shipCont;
    }
}



