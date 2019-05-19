package icapture.hibernate;

//~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

public final class GenotypingRunSampleStatus extends Persistent implements Serializable {
    private Double           callRate;
    private ContainerContent containerContents;
    private GenotypingRun    genotypingRun;
    private String           valid;

    public GenotypingRun getGenotypingRun() {
        return genotypingRun;
    }

    public ContainerContent getContainerContents() {
        return containerContents;
    }

    /*
     * public String getGenotypingRunID() {
     *   return genotypingRunID;
     * }
     *
     * public String getContainerContentsID() {
     *   return containerContentsID;
     * }
     */
    public String getValid() {
        return valid;
    }

    public Double getCallRate() {
        return callRate;
    }

    /*
     *  visible name overides allow for different fields to be used as visible name
     * alternate way to assign visibleName (in *.hbm.xml) won't allow for a mapping
     * of the visible name column to its own getter/setter methods
     */

/*    public String getVisibleName() {
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
*/
    public String[] getValueArray(byte[] indexArray, int viewSize) {
        String[] valueArray = super.getValueArray(indexArray, viewSize);

        if (indexArray[6] > 0) {
            valueArray[indexArray[6] - 1] = genotypingRun.getId().toString();
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = containerContents.getId().toString();
        }

        if (indexArray[8] > 0) {
            valueArray[indexArray[8] - 1] = valid;
        }

        if (indexArray[9] > 0) {
            valueArray[indexArray[9] - 1] = callRate.toString();
        }

        return valueArray;
    }

    public void setGenotypingRun(GenotypingRun input) {
        genotypingRun = input;
    }

    public void setContainerContents(ContainerContent input) {
        containerContents = input;
    }

    public void setValid(String input) {
        valid = input;
    }

    public void setCallRate(Double input) {
        callRate = input;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof GenotypingRunSampleStatus)) {
            return false;
        }

        final GenotypingRunSampleStatus genotypingRunSampleStatus = (GenotypingRunSampleStatus) o;

        if (this.getGenotypingRun().getId().toString()
                .equals(genotypingRunSampleStatus.getGenotypingRun().getId().toString()) && this.getContainerContents()
                .getId().toString().equals(genotypingRunSampleStatus.getContainerContents().getId().toString())) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return (genotypingRun.getId().toString() + "-" + containerContents.getId().toString()).hashCode();
    }
}



