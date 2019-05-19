package icapture.hibernate;

public final class SnpSetLookUp extends Persistent {
    private String snpSetID;
    private String name;

    public String getName() {
        return name;
    }

    public String getSnpSetID() {
        return snpSetID;
    }

/*     visible name overides allow for different fields to be used as visible name
    * alternate way to assign visibleName (in *.hbm.xml) won't allow for a mapping
    * of the visible name column to its own getter/setter methods
*/
    public String getVisibleName() {
        return snpSetID;
    }

    public String getPropertyValue(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return snpSetID;
        } else {
            return super.getPropertyValue(propertyName);
        }
    }

    public Object getPropertyObject(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return snpSetID;
        } else {
            return super.getPropertyObject(propertyName);
        }
    }

    public String[] getValueArray(byte[] indexArray, int viewSize) {
        String[] valueArray = super.getValueArray(indexArray, viewSize);
/*
    private int snpID;
    private String name;
    private int publicFlag;
    private int entrezSnpID;
    private String arm;
 */
        if (indexArray[6] > 0) {
            valueArray[indexArray[6] - 1] = snpSetID;
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = name;
        }
        return valueArray;
    }

    public void setSnpSetID(String name) {
        this.snpSetID = name;
    }

    public void setName(String name) {
        this.name = name;
    }
}



