package icapture.hibernate;

//~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

public final class GenotypingRunSnpStatus extends Persistent implements Serializable {
    private String genotypingRunID;
    private String snpID;
    private String strand;
    private String valid;

    public String getGenotypingRunID() {
        return genotypingRunID;
    }

    public String getSnpID() {
        return snpID;
    }

    public String getValid() {
        return valid;
    }

    public String getStrand() {
        return strand;
    }

    /*
     *  visible name overides allow for different fields to be used as visible name
     * alternate way to assign visibleName (in *.hbm.xml) won't allow for a mapping
     * of the visible name column to its own getter/setter methods
     */
    public String getVisibleName() {
        return genotypingRunID;
    }

    public String getPropertyValue(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return genotypingRunID;
        } else {
            return super.getPropertyValue(propertyName);
        }
    }

    public Object getPropertyObject(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return genotypingRunID;
        } else {
            return super.getPropertyObject(propertyName);
        }
    }

    public String[] getValueArray(byte[] indexArray, int viewSize) {
        String[] valueArray = super.getValueArray(indexArray, viewSize);

        if (indexArray[6] > 0) {
            valueArray[indexArray[6] - 1] = genotypingRunID;
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = snpID;
        }

        if (indexArray[8] > 0) {
            valueArray[indexArray[8] - 1] = valid;
        }

        if (indexArray[9] > 0) {
            valueArray[indexArray[9] - 1] = strand;
        }

        return valueArray;
    }

    public void setGenotypingRunID(String input) {
        genotypingRunID = input;
    }

    public void setSnpID(String input) {
        snpID = input;
    }

    public void setValid(String input) {
        valid = input;
    }

    public void setStrand(String input) {
        strand = input;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof GenotypingRunSnpStatus)) {
            return false;
        }

        final GenotypingRunSnpStatus genotypingRunSnpStatus = (GenotypingRunSnpStatus) o;

        if (this.getGenotypingRunID().equals(genotypingRunSnpStatus.getGenotypingRunID())
                && this.getSnpID().equals(genotypingRunSnpStatus.getSnpID())) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return (genotypingRunID + "-" + snpID).hashCode();
    }
}



