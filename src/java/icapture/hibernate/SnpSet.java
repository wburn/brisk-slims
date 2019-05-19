package icapture.hibernate;

//~--- non-JDK imports --------------------------------------------------------


//~--- JDK imports ------------------------------------------------------------

public final class SnpSet extends Persistent {
    private Snp snp;
    private SnpSetLookUp snpSetLookUp;
    private String snpSetPrimary;

    public String getSnpSetPrimary(){
        return snpSetPrimary;
    }

    public Snp getSnp() {
        return snp;
    }

    public SnpSetLookUp getSnpSetLookUp() {
        return snpSetLookUp;
    }

/*     visible name overides allow for different fields to be used as visible name
    * alternate way to assign visibleName (in *.hbm.xml) won't allow for a mapping
    * of the visible name column to its own getter/setter methods
*/
    public String getVisibleName() {
        return snpSetPrimary;
    }

    public String getPropertyValue(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return snpSetPrimary;
        } else {
            return super.getPropertyValue(propertyName);
        }
    }

    public Object getPropertyObject(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return snpSetPrimary;
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
            valueArray[indexArray[6] - 1] = snpSetLookUp.getSnpSetID();
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = snp.getSnpID();
        }
        return valueArray;
    }

    public void setSnp(Snp snpID) {
        this.snp = snpID;
    }

    public void setSnpSetLookUp(SnpSetLookUp name) {
        this.snpSetLookUp = name;
    }

    public void setSnpSetPrimary(String name) {
        snpSetPrimary = name;
    }
}



