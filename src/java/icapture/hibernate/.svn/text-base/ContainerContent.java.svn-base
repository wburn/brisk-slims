package icapture.hibernate;

//~--- non-JDK imports --------------------------------------------------------

import icapture.com.MetadataManager;
import icapture.com.UserHttpSess;
import icapture.com.Util;

//~--- JDK imports ------------------------------------------------------------

import java.util.Date;
import java.util.HashMap;

public final class ContainerContent extends Persistent {
    private Date             amplificationDate;
    private String           barcode;
    private String           column;
    private String           comments;
    private String           concentration;
    private Container        container;
    private String           containerContentsID;
    private String           contaminated;
    private String           dilution;
    private MaterialType     materialType;
    private ContainerContent parent;
    private String           row;
    private Sample           sample;
    private String           volume;

    public HashMap getContainerContentHash() {
        HashMap fields = new HashMap(10);

        fields.put("containerContentsID", containerContentsID);
        fields.put("parent", parent);
        fields.put("contaminated", contaminated);
        fields.put("container", container);
        fields.put("row", row);
        fields.put("column", column);
        fields.put("sample", sample);
        fields.put("volume", volume);
        fields.put("concentration", concentration);
        fields.put("dilution", dilution);
        fields.put("comments", comments);
        fields.put("amplificationDate", amplificationDate);
        fields.put("materialType", materialType);

        return fields;
    }

    public String getContainerContentsID() {
        return containerContentsID;
    }

    public ContainerContent getParent() {
        return parent;
    }

    public String getContaminated() {
        return contaminated;
    }

    public Container getContainer() {
        return container;
    }

    public String getRow() {
        return row;
    }

    public String getColumn() {
        return column;
    }

    public Sample getSample() {
        return sample;
    }

    public String getVolume() {
        return volume;
    }

    public String getConcentration() {
        return concentration;
    }

    public String getDilution() {
        return dilution;
    }

    public String getComments() {
        return comments;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public Date getAmplificationDate() {
        return amplificationDate;
    }

    public String getBarcode() {
        return barcode;
    }

    /*
     *  visible name overides allow for different fields to be used as visible name
     * alternate way to assign visibleName (in *.hbm.xml) won't allow for a mapping
     * of the visible name column to its own getter/setter methods
     */
    public String getVisibleName() {
        return containerContentsID;
    }

    public String getPropertyValue(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return containerContentsID;
        } else {
            return super.getPropertyValue(propertyName);
        }
    }

    public Object getPropertyObject(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return containerContentsID;
        } else {
            return super.getPropertyObject(propertyName);
        }
    }

    public String[] getValueArray(byte[] indexArray, int viewSize) {
        String[] valueArray = super.getValueArray(indexArray, viewSize);

        if (indexArray[6] > 0) {
            valueArray[indexArray[6] - 1] = containerContentsID;
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = parent.getContainerContentsID();
        }

        if (indexArray[8] > 0) {
            valueArray[indexArray[8] - 1] = contaminated;
        }

        if (indexArray[9] > 0) {
            valueArray[indexArray[9] - 1] = container.getContainerID();
        }

        if (indexArray[10] > 0) {
            valueArray[indexArray[10] - 1] = row;
        }

        if (indexArray[11] > 0) {
            valueArray[indexArray[11] - 1] = column;
        }

        if (indexArray[12] > 0) {
            valueArray[indexArray[12] - 1] = sample.getSampleID();
        }

        if (indexArray[13] > 0) {
            valueArray[indexArray[13] - 1] = volume;
        }

        if (indexArray[14] > 0) {
            valueArray[indexArray[14] - 1] = concentration;
        }

        if (indexArray[15] > 0) {
            valueArray[indexArray[15] - 1] = dilution;
        }

        if (indexArray[16] > 0) {
            valueArray[indexArray[16] - 1] = (comments != null)
                                             ? comments
                                             : "";
        }

        if (indexArray[17] > 0) {
            valueArray[indexArray[17] - 1] = (sample.getSampleType() != null)
                                             ? sample.getSampleType().getDescription()
                                             : "";
        }

        if (indexArray[18] > 0) {
            valueArray[indexArray[18] - 1] = (materialType != null)
                                             ? materialType.getDescription()
                                             : "";
        }

        if (indexArray[19] > 0) {
            valueArray[indexArray[19] - 1] = Util.truncateDate(amplificationDate);
        }

        if (indexArray[20] > 0) {
            valueArray[indexArray[20] - 1] = barcode;
        }

        return valueArray;
    }

    public String[] getValueArrayReadable(byte[] indexArray, int viewSize) {
        String parentStr = "";

        if (parent != null) {
            parentStr = ((parent != null) && parent.getContainerContentsID().equals("-2"))
                        ? "N/A"
                        : (parent.getContainerContentsID().equals("-3"))
                          ? "Unknown"
                          : parent.getSample().getSampleName() + ", " + parent.getContainer().getcontainerName() + " ("
                            + Util.numToLetter(parent.getRow()) + ", " + parent.getColumn() + ")";
        }

        // format containercontents comments and sample comments
        String printComments = "";
        String ccCom         = comments;
        String sampCom       = sample.getComments();

        // subject comments
        String subjCom = sample.getSubject().getComments();

        if ((sampCom != null) && sampCom.equals(subjCom)) {
            sampCom = "";
        }

        if ((ccCom != null) && ccCom.equals(subjCom)) {
            ccCom = "";
        }

        if ((subjCom != null) &&!subjCom.equals("null") && (subjCom.length() > 0)) {
            subjCom       = (subjCom.trim().endsWith("."))
                            ? subjCom.trim()
                            : subjCom.trim() + ".";
            printComments = "[Subject comment:" + subjCom + "] ";
        }

        // sample and containercontents comments
        if ((ccCom == null) || ccCom.equals("") || ccCom.equalsIgnoreCase("null")) {
            if ((sampCom == null) || sampCom.equals("") || sampCom.equalsIgnoreCase("null")) {
                printComments = printComments + "";
            } else {
                printComments = printComments + sampCom;
            }
        } else {
            if ((sampCom == null) || sampCom.equals("") || sampCom.equalsIgnoreCase("null")) {
                printComments = printComments + ccCom;
            } else {
                if (ccCom.equals(sampCom)) {
                    printComments = printComments + ccCom;
                } else {
                    ccCom         = (ccCom.trim().endsWith("."))
                                    ? ccCom.trim()
                                    : ccCom.trim() + ".";
                    sampCom       = (sampCom.trim().endsWith("."))
                                    ? sampCom.trim()
                                    : sampCom.trim() + ".";
                    printComments = printComments + sampCom + " " + ccCom;
                }
            }
        }

        String[] valueArray = super.getValueArray(indexArray, viewSize);

        if (indexArray[5] > 0) {
            valueArray[indexArray[5] - 1] = (getModifDate() != null)
                                            ? Util.truncateDate(getModifDate())
                                            : Util.truncateDate(getCreateDate());
        }

        if (indexArray[6] > 0) {
            valueArray[indexArray[6] - 1] = containerContentsID;
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = parentStr;
        }

        if (indexArray[8] > 0) {
            valueArray[indexArray[8] - 1] = (contaminated.equals("0"))
                                            ? "no"
                                            : "yes";
        }

        if (indexArray[9] > 0) {
            valueArray[indexArray[9] - 1] = container.getcontainerName();
        }

        if (indexArray[10] > 0) {
            valueArray[indexArray[10] - 1] = icapture.com.Util.numToLetter(row);
        }

        if (indexArray[11] > 0) {
            valueArray[indexArray[11] - 1] = column;
        }

        if (indexArray[12] > 0) {
            valueArray[indexArray[12] - 1] = sample.getSampleName();
        }

        if (indexArray[13] > 0) {
            valueArray[indexArray[13] - 1] = (volume.equals("-1.0"))
                                             ? ""
                                             : String.valueOf(Util.round(new Double(volume), 2));
        }

        if (indexArray[14] > 0) {
            valueArray[indexArray[14] - 1] = (concentration.equals("-1.0"))
                                             ? ""
                                             : String.valueOf(Util.round(new Double(concentration), 2));
        }

        if (indexArray[15] > 0) {
            valueArray[indexArray[15] - 1] = (dilution.equals("-1"))
                                             ? ""
                                             : dilution;
        }

        if (indexArray[16] > 0) {
            valueArray[indexArray[16] - 1] = printComments;
        }

        if (indexArray[17] > 0) {
            valueArray[indexArray[17] - 1] = (sample.getSampleType() != null)
                                             ? sample.getSampleType().getDescription()
                                             : "";
        }

        if (indexArray[18] > 0) {
            valueArray[indexArray[18] - 1] = (sample.getDateCollected() != null)
                                             ? ((sample.getDateCollected().toString().startsWith("0001"))
                    ? ""
                    : (new java.sql.Date(sample.getDateCollected().getTime())).toString())
                                             : "";
        }

        if (indexArray[19] > 0) {
            valueArray[indexArray[19] - 1] = (sample.getDateExtracted() != null)
                                             ? (new java.sql.Date(sample.getDateExtracted().getTime())).toString()
                                             : "";
        }

        if (indexArray[20] > 0) {
            valueArray[indexArray[20] - 1] = (materialType != null)
                                             ? materialType.getDescription()
                                             : "";
        }

        if (indexArray[21] > 0) {
            valueArray[indexArray[21] - 1] = (container.getIsStock() != null)
                                             ? (container.getIsStock().equals("0"))
                                               ? "no"
                                               : "yes"
                                             : "";
        }

        if (indexArray[22] > 0) {
            valueArray[indexArray[22] - 1] = (container.getContainerType() != null)
                                             ? container.getContainerType().getDescription()
                                             : "";
        }

        if (indexArray[23] > 0) {
            valueArray[indexArray[23] - 1] = (container.getFreezer() != null)
                                             ? container.getFreezer().getDescription()
                                             : "";
        }

        if (indexArray[24] > 0) {
            valueArray[indexArray[24] - 1] = container.getShelf();
        }

        if (indexArray[25] > 0) {
            valueArray[indexArray[25] - 1] = (getModifDate() != null)
                                             ? (new java.sql.Date(getModifDate().getTime())).toString()
                                             : (new java.sql.Date(getCreateDate().getTime())).toString();
        }

        if (indexArray[26] > 0) {
            valueArray[indexArray[26] - 1] = Util.truncateDate(amplificationDate);
        }

        if (indexArray[27] > 0) {
            valueArray[indexArray[27] - 1] = barcode;
        }

        if (indexArray[28] > 0) {
            valueArray[indexArray[28] - 1] = container.getLocation();
        }

        return valueArray;
    }

    public void setContainerContentsID(String input) {
        containerContentsID = input;
    }

    public void setParent(ContainerContent input) {
        parent = input;
    }

    public void setContaminated(String input) {
        contaminated = input;
    }

    public void setContainer(Container input) {
        container = input;
    }

    public void setRow(String input) {
        row = input;
    }

    public void setColumn(String input) {
        column = input;
    }

    public void setSample(Sample input) {
        sample = input;
    }

    public void setVolume(String input) {
        volume = input;
    }

    public void setConcentration(String input) {
        concentration = input;
    }

    public void setDilution(String input) {
        dilution = input;
    }

    public void setComments(String input) {
        comments = input;
    }

    public void setMaterialType(MaterialType input) {
        materialType = input;
    }

    public void setAmplificationDate(Date input) {
        amplificationDate = input;
    }

    public void setBarcode(String input) {
        barcode = input;
    }
}



