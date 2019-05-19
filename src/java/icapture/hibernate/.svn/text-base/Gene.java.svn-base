package icapture.hibernate;

//~--- non-JDK imports --------------------------------------------------------

import icapture.com.Util;

//~--- JDK imports ------------------------------------------------------------

import java.util.Date;
public final class Gene extends Persistent {
    private String geneID;
    private String name;
    private String publicFlag;
    private String entrezGeneID;
    private String arm;

    public String getGeneID() {
        return geneID;
    }

    public String getName() {
        return name;
    }

    public String getPublicFlag() {
        return publicFlag;
    }

    public String getEntrezGeneID() {
        return entrezGeneID;
    }

    public String getArm() {
        return arm;
    }

/*     visible name overides allow for different fields to be used as visible name
    * alternate way to assign visibleName (in *.hbm.xml) won't allow for a mapping
    * of the visible name column to its own getter/setter methods
*/
    public String getVisibleName() {
        return geneID;
    }

    public String getPropertyValue(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return geneID;
        } else {
            return super.getPropertyValue(propertyName);
        }
    }

    public Object getPropertyObject(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return geneID;
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
            valueArray[indexArray[6] - 1] = geneID;
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = name;
        }

        if (indexArray[8] > 0) {
            valueArray[indexArray[8] - 1] = publicFlag;
        }

        if (indexArray[9] > 0) {
            valueArray[indexArray[9] - 1] = entrezGeneID;
        }

        if (indexArray[10] > 0) {
            valueArray[indexArray[10] - 1] = arm;
        }

        return valueArray;
    }

    public void setGeneID(String geneID) {
        this.geneID = geneID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublicFlag(String publicFlag) {
        this.publicFlag = publicFlag;
    }

    public void setEntrezGeneID(String geneID) {
        entrezGeneID = geneID;
    }

    public void setArm(String arm) {
        this.arm = arm;
    }
}



