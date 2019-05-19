package icapture.hibernate;

//~--- non-JDK imports --------------------------------------------------------

import icapture.com.Util;

//~--- JDK imports ------------------------------------------------------------

import java.util.Date;

public final class Genotype extends Persistent {
    private String allele1;
    private String allele2;
//    private String containerContentsID;
    private String genotypeID;
//    private String genotypingRunID;
//    private String snpID;
    private GenotypingRun genotypingRun;
    private ContainerContent containerContents;
    private Snp snp;

    public String getGenotypeID() {
        return genotypeID;
    }

    public String getAllele1() {
        return allele1;
    }

    public String getAllele2() {
        return allele2;
    }

    public ContainerContent getContainerContents() {
        return containerContents;
    }

    public GenotypingRun getGenotypingRun() {
        return genotypingRun;
    }

    public Snp getSnp() {
        return snp;
    }

/*     visible name overides allow for different fields to be used as visible name
    * alternate way to assign visibleName (in *.hbm.xml) won't allow for a mapping
    * of the visible name column to its own getter/setter methods
*/
    public String getVisibleName() {
        return genotypeID;
    }

    public String getPropertyValue(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return genotypeID;
        } else {
            return super.getPropertyValue(propertyName);
        }
    }

    public Object getPropertyObject(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return genotypeID;
        } else {
            return super.getPropertyObject(propertyName);
        }
    }

    public String[] getValueArray(byte[] indexArray, int viewSize) {
        String[] valueArray = super.getValueArray(indexArray, viewSize);

        if (indexArray[6] > 0) {
            valueArray[indexArray[6] - 1] = genotypeID;
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = (containerContents != null)?containerContents.getContainerContentsID():null;
        }

        if (indexArray[8] > 0) {
            valueArray[indexArray[8] - 1] = (genotypingRun != null)?genotypingRun.getDescription():null;
        }

        if (indexArray[9] > 0) {
            valueArray[indexArray[9] - 1] = allele2;
        }

        if (indexArray[10] > 0) {
            valueArray[indexArray[10] - 1] = allele1;
        }

        if (indexArray[11] > 0) {
            String NCBIlink = "<a href='http://www.ncbi.nlm.nih.gov/projects/SNP/snp_ref.cgi?rs=";
            String rsNum = (snp != null)?snp.getRsNumber():null;
            if (rsNum!=null || !rsNum.equals("")){
                String noRsNum = rsNum.replace("rs", "");
                NCBIlink += noRsNum;
            }
            NCBIlink += "'>" + rsNum + "</a>";
            valueArray[indexArray[11] - 1] = NCBIlink;
        }

        return valueArray;
    }

    public void setGenotypeID(String input) {
        genotypeID = input;
    }

    public void setAllele1(String input) {
        allele1 = input;
    }

    public void setAllele2(String input) {
        allele2 = input;
    }

    public void setContainerContents(ContainerContent input) {
        containerContents = input;
    }

    public void setGenotypingRun(GenotypingRun input) {
        genotypingRun = input;
    }

    public void setSnp(Snp input) {
        snp = input;
    }
}



