package icapture.hibernate;

//~--- non-JDK imports --------------------------------------------------------

import icapture.com.Util;

//~--- JDK imports ------------------------------------------------------------

import java.util.Date;
public final class GeneSet extends Persistent {
    private Gene gene;
    private GeneSetLookUp geneSetLookUp;
    private String geneSetPrimary;

    public String getGeneSetPrimary(){
        return geneSetPrimary;
    }

    public Gene getGene() {
        return gene;
    }

    public GeneSetLookUp getGeneSetLookUp() {
        return geneSetLookUp;
    }

/*     visible name overides allow for different fields to be used as visible name
    * alternate way to assign visibleName (in *.hbm.xml) won't allow for a mapping
    * of the visible name column to its own getter/setter methods
*/
    public String getVisibleName() {
        return geneSetPrimary;
    }

    public String getPropertyValue(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return geneSetPrimary;
        } else {
            return super.getPropertyValue(propertyName);
        }
    }

    public Object getPropertyObject(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return geneSetPrimary;
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
            valueArray[indexArray[6] - 1] = geneSetLookUp.getGeneSetID();
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = gene.getGeneID();
        }
        return valueArray;
    }

    public void setGene(Gene geneID) {
        this.gene = geneID;
    }

    public void setGeneSetLookUp(GeneSetLookUp name) {
        this.geneSetLookUp = name;
    }

    public void setGeneSetPrimary(String name) {
        geneSetPrimary = name;
    }
}



