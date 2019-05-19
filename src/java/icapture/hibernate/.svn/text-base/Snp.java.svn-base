package icapture.hibernate;

//~--- non-JDK imports --------------------------------------------------------

import icapture.com.Util;

//~--- JDK imports ------------------------------------------------------------

import java.util.Date;

public final class Snp extends Persistent {
    private String Chromosome;
    private String fxnClass;
    private String geneID;
    private String pos;
    private String rsNumber;
    private String snpID;
    private String majorAllele;
    private String minorAllele;
    private String strand;

    public String getStrand() {
        return strand;
    }

    public String getMajorAllele() {
        return majorAllele;
    }

    public String getMinorAllele() {
        return minorAllele;
    }

    public String getSnpID() {
        return snpID;
    }

    public String getChromosome() {
        return Chromosome;
    }

    public String getPos() {
        return pos;
    }

    public String getGeneID() {
        return geneID;
    }

    public String getFxnClass() {
        return fxnClass;
    }

    public String getRsNumber() {
        return rsNumber;
    }

/*     visible name overides allow for different fields to be used as visible name
    * alternate way to assign visibleName (in *.hbm.xml) won't allow for a mapping
    * of the visible name column to its own getter/setter methods
*/
    public String getVisibleName() {
        return snpID;
    }

    public String getPropertyValue(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return snpID;
        } else {
            return super.getPropertyValue(propertyName);
        }
    }

    public Object getPropertyObject(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return snpID;
        } else {
            return super.getPropertyObject(propertyName);
        }
    }

    public String[] getValueArray(byte[] indexArray, int viewSize) {
        String[] valueArray = super.getValueArray(indexArray, viewSize);

        if (indexArray[6] > 0) {
            valueArray[indexArray[6] - 1] = snpID;
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = Chromosome;
        }

        if (indexArray[8] > 0) {
            valueArray[indexArray[8] - 1] = pos;
        }

        if (indexArray[9] > 0) {
            valueArray[indexArray[9] - 1] = geneID;
        }

        if (indexArray[10] > 0) {
            valueArray[indexArray[10] - 1] = fxnClass;
        }

        if (indexArray[11] > 0) {
            valueArray[indexArray[11] - 1] = rsNumber;
        }

        return valueArray;
    }

    public void setSnpID(String input) {
        snpID = input;
    }

    public void setChromosome(String input) {
        Chromosome = input;
    }

    public void setPos(String input) {
        pos = input;
    }

    public void setGeneID(String input) {
        geneID = input;
    }

    public void setFxnClass(String input) {
        fxnClass = input;
    }

    public void setRsNumber(String input) {
        rsNumber = input;
    }

    public void setStrand(String inputStrand) {
        strand = inputStrand;
    }

    public void setMajorAllele (String majorAllele) {
        this.majorAllele = majorAllele;
    }

    public void setMinorAllele (String minorAllele) {
        this.minorAllele = minorAllele;
    }
}



