
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package icapture.hibernate;

/**
 *
 * @author tvanrossum
 */
public final class MaterialType extends Persistent {
    private String description;
    private String materialTypeID;

    public String getMaterialTypeID() {
        return materialTypeID;
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
            valueArray[indexArray[6] - 1] = materialTypeID;
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = description;
        }

        return valueArray;
    }

    public void setMaterialTypeID(String input) {
        materialTypeID = input;
    }

    public void setDescription(String input) {
        description = input;
    }
}



