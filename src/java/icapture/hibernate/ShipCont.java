package icapture.hibernate;

public final class ShipCont extends Persistent {
    private Container container;
    private String    shipContID;
    private Shipment  shipment;

    public String getShipContID() {
        return shipContID;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public Container getContainer() {
        return container;
    }

    @Override
    public String getPropertyValue(String propertyName) {
        return super.getPropertyValue(propertyName);
    }

    @Override
    public Object getPropertyObject(String propertyName) {
        return super.getPropertyObject(propertyName);
    }

    @Override
    public String[] getValueArray(byte[] indexArray, int viewSize) {
        return getValueArrayReadable(indexArray, viewSize);
    }

    @Override
    public String[] getValueArrayReadable(byte[] indexArray, int viewSize) {
        String[] valueArray = super.getValueArray(indexArray, viewSize);

        if (indexArray[6] > 0) {
            valueArray[indexArray[6] - 1] = shipContID;
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[6] - 1] = shipment.getShipmentID();
        }

        if (indexArray[8] > 0) {
            valueArray[indexArray[8] - 1] = container.getContainerID();
        }

        return valueArray;
    }

    public void setShipContID(String shipContID) {
        this.shipContID = shipContID;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public void setContainer(Container container) {
        this.container = container;
    }
}



