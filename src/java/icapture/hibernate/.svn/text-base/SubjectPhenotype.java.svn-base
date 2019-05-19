package icapture.hibernate;

//~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

public final class SubjectPhenotype extends Persistent implements Serializable {
    private String phenotypeID;
    private String subjectID;
    private String value;

    public String getphenotypeID() {
        return phenotypeID;
    }

    public String getsubjectID() {
        return subjectID;
    }

    public String getValue() {
        return value;
    }

    /*
     *  visible name overides allow for different fields to be used as visible name
     * alternate way to assign visibleName (in *.hbm.xml) won't allow for a mapping
     * of the visible name column to its own getter/setter methods
     */
    public String getVisibleName() {
        return phenotypeID;
    }

    public String getPropertyValue(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return phenotypeID;
        } else {
            return super.getPropertyValue(propertyName);
        }
    }

    public Object getPropertyObject(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return phenotypeID;
        } else {
            return super.getPropertyObject(propertyName);
        }
    }

    public String[] getValueArray(byte[] indexArray, int viewSize) {
        String[] valueArray = super.getValueArray(indexArray, viewSize);

        if (indexArray[6] > 0) {
            valueArray[indexArray[6] - 1] = phenotypeID;
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = subjectID;
        }

        if (indexArray[8] > 0) {
            valueArray[indexArray[8] - 1] = value;
        }

        return valueArray;
    }

    public void setphenotypeID(String input) {
        phenotypeID = input;
    }

    public void setsubjectID(String input) {
        subjectID = input;
    }

    public void setValue(String input) {
        value = input;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SubjectPhenotype)) {
            return false;
        }

        final SubjectPhenotype phenotype = (SubjectPhenotype) o;

        if (this.getphenotypeID().equals(phenotype.getphenotypeID())
                && this.getsubjectID().equals(phenotype.getsubjectID())) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return (phenotypeID + "-" + subjectID).hashCode();
    }
}



