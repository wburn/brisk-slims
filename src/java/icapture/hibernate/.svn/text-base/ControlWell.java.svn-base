package icapture.hibernate;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;

public final class ControlWell extends Persistent {
    private String    column;
    private Container container;
    private Control   control;
    private String    controlWellID;
    private String    row;
    private String    volume;

    public HashMap getControlWellHash() {
        HashMap fields = new HashMap(10);

        fields.put("controlWellID", controlWellID);
        fields.put("container", container);
        fields.put("row", row);
        fields.put("column", column);
        fields.put("control", control);
        fields.put("volume", volume);

        return fields;
    }

    public String getControlWellID() {
        return controlWellID;
    }

    public Container getContainer() {
        return container;
    }

    public String getRow() {
        return row;
    }

    public String getColumn() {
        return column;
    }

    public Control getControl() {
        return control;
    }

    public String getVolume() {
        return volume;
    }

    /*
     *  visible name overides allow for different fields to be used as visible name
     * alternate way to assign visibleName (in *.hbm.xml) won't allow for a mapping
     * of the visible name column to its own getter/setter methods
     */
    public String getVisibleName() {
        return controlWellID;
    }

    public String getPropertyValue(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return controlWellID;
        } else {
            return super.getPropertyValue(propertyName);
        }
    }

    public Object getPropertyObject(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return controlWellID;
        } else {
            return super.getPropertyObject(propertyName);
        }
    }

    public String[] getValueArray(byte[] indexArray, int viewSize) {
        String[] valueArray = super.getValueArray(indexArray, viewSize);

        if (indexArray[6] > 0) {
            valueArray[indexArray[6] - 1] = controlWellID;
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = container.getContainerID();
        }

        if (indexArray[8] > 0) {
            valueArray[indexArray[8] - 1] = row;
        }

        if (indexArray[9] > 0) {
            valueArray[indexArray[9] - 1] = column;
        }

        if (indexArray[10] > 0) {
            valueArray[indexArray[10] - 1] = control.getControlID();
        }

        if (indexArray[11] > 0) {
            valueArray[indexArray[11] - 1] = volume;
        }

        if (indexArray[13] > 0) {
            valueArray[indexArray[12] - 1] = control.getControlType();
        }

        return valueArray;
    }

    public String[] getValueArrayReadable(byte[] indexArray, int viewSize) {
        String[] valueArray = super.getValueArray(indexArray, viewSize);

        if (indexArray[6] > 0) {
            valueArray[indexArray[6] - 1] = controlWellID;
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = container.getcontainerName();
        }

        if (indexArray[8] > 0) {
            valueArray[indexArray[8] - 1] = row;
        }

        if (indexArray[9] > 0) {
            valueArray[indexArray[9] - 1] = column;
        }

        if (indexArray[10] > 0) {
            valueArray[indexArray[10] - 1] = control.getDescription();
        }

        if (indexArray[11] > 0) {
            valueArray[indexArray[11] - 1] = volume;
        }

        if (indexArray[13] > 0) {
            valueArray[indexArray[12] - 1] = (control.getControlType() != null)
                                             ? ((control.getControlType().equals("0"))
                    ? "-"
                    : "+")
                                             : "";
        }

        return valueArray;
    }

    public void setControlWellID(String input) {
        controlWellID = input;
    }

    public void setContainer(Container input) {
        container = input;
    }

    public void setRow(String input) {
        row = input;
    }

    public void setColumn(String input) {
        column = input;
    }

    public void setControl(Control input) {
        control = input;
    }

    public void setVolume(String input) {
        volume = input;
    }
}



