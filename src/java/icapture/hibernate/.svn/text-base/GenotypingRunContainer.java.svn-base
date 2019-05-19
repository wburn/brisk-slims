package icapture.hibernate;

//~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

public final class GenotypingRunContainer extends Persistent implements Serializable {
    private Container     container;
    private GenotypingRun genotypingRun;

/*  use these is treating key vals as properties
    public String getGenotypingRunID() {
        return genotypingRunID;
    }
    public String getContainerID() {
        return containerID;
    }*/
    public GenotypingRun getGenotypingRun() {
        return genotypingRun;
    }

    public Container getContainer() {
        return container;
    }

    /*
     *  visible name overides allow for different fields to be used as visible name
     * alternate way to assign visibleName (in *.hbm.xml) won't allow for a mapping
     * of the visible name column to its own getter/setter methods
     */

    /*
     *  public String getVisibleName() {
     *    return genotypingRunID;
     * }
     *
     * public String getPropertyValue(String propertyName) {
     *    if (propertyName.equals("visibleName")) {
     *        return genotypingRunID;
     *    } else {
     *        return super.getPropertyValue(propertyName);
     *    }
     * }
     * public Object getPropertyObject(String propertyName) {
     *    if (propertyName.equals("visibleName")) {
     *        return genotypingRunID;
     *    } else {
     *        return super.getPropertyObject(propertyName);
     *    }
     * }
     *
     */
    public String[] getValueArray(byte[] indexArray, int viewSize) {
        String[] valueArray = super.getValueArray(indexArray, viewSize);

        if (indexArray[6] > 0) {
            valueArray[indexArray[6] - 1] = genotypingRun.getId().toString();
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = container.getId().toString();
        }

        return valueArray;
    }

    public void setGenotypingRun(GenotypingRun input) {
        genotypingRun = input;
    }

    public void setContainer(Container input) {
        container = input;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof GenotypingRunContainer)) {
            return false;
        }

        final GenotypingRunContainer genotypingRunContainer = (GenotypingRunContainer) o;

        if (this.getGenotypingRun().equals(genotypingRunContainer.getGenotypingRun())
                && this.getContainer().equals(genotypingRunContainer.getContainer())) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return (genotypingRun.getId() + "-" + container.getId()).hashCode();
    }
}



