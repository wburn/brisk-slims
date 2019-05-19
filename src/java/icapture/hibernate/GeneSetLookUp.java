package icapture.hibernate;

public final class GeneSetLookUp extends Persistent {
    private String geneSetID;
    private String name;

    public String getName() {
        return name;
    }

    public String getGeneSetID() {
        return geneSetID;
    }

/*     visible name overides allow for different fields to be used as visible name
    * alternate way to assign visibleName (in *.hbm.xml) won't allow for a mapping
    * of the visible name column to its own getter/setter methods
*/
    public String getVisibleName() {
        return geneSetID;
    }

    public String getPropertyValue(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return geneSetID;
        } else {
            return super.getPropertyValue(propertyName);
        }
    }

    public Object getPropertyObject(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return geneSetID;
        } else {
            return super.getPropertyObject(propertyName);
        }
    }

    public String[] getValueArray(byte[] indexArray, int viewSize) {
        String[] valueArray = super.getValueArray(indexArray, viewSize);
/*
    private int geneID;
    private String name;
    private int publicFlag;
    private int entrezGeneID;
    private String arm;
 */
        if (indexArray[6] > 0) {
            valueArray[indexArray[6] - 1] = geneSetID;
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = name;
        }
        return valueArray;
    }

    public void setGeneSetID(String name) {
        this.geneSetID = name;
    }

    public void setName(String name) {
        this.name = name;
    }
}



