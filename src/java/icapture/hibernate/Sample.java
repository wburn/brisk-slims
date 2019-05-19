package icapture.hibernate;

//~--- non-JDK imports --------------------------------------------------------

import icapture.com.UserHttpSess;
import icapture.com.Util;

import icapture.hibernate.SampleProcess;
import icapture.hibernate.SampleType;
import icapture.hibernate.Subject;

//~--- JDK imports ------------------------------------------------------------

import java.util.Date;

public final class Sample extends Persistent {
    private String        comments;
    private Date          dateCollected;
    private Date          dateExtracted;
    private Sample        parent;
    private String        sampleID;
    private String        sampleName;
    private SampleProcess sampleProcess;
    private SampleType    sampleType;
    private Subject       subject;
    private String        valid;

    public String getSampleID() {
        return sampleID;
    }

    public String getSampleName() {
        return sampleName;
    }

    public String getValid() {
        return valid;
    }

    public Sample getParent() {
        return parent;
    }

    public SampleType getSampleType() {
        return sampleType;
    }

    public SampleProcess getSampleProcess() {
        return sampleProcess;
    }

    public Subject getSubject() {
        return subject;
    }

    public Date getDateCollected() {
        return dateCollected;
    }

    public Date getDateExtracted() {
        return dateExtracted;
    }

    public String getComments() {
        return comments;
    }

    /*
     *  visible name overides allow for different fields to be used as visible name
     * alternate way to assign visibleName (in *.hbm.xml) won't allow for a mapping
     * of the visible name column to its own getter/setter methods
     */
    @Override
    public String getVisibleName() {
        return sampleName;
    }

    @Override
    public String getPropertyValue(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return sampleName;
        } else {
            return super.getPropertyValue(propertyName);
        }
    }

    @Override
    public Object getPropertyObject(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return sampleName;
        } else {
            return super.getPropertyObject(propertyName);
        }
    }

    @Override
    public String[] getValueArray(byte[] indexArray, int viewSize) {
        return getValueArrayReadable(indexArray, viewSize);
    }

    public String[] getValueArrayReadable(byte[] indexArray, int viewSize) {
        String[] valueArray = super.getValueArray(indexArray, viewSize);

        if (indexArray[6] > 0) {
            valueArray[indexArray[6] - 1] = sampleID;
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = sampleName;
        }

        if (indexArray[8] > 0) {
            valueArray[indexArray[8] - 1] = (valid.equals("0"))
                                            ? "no"
                                            : "yes";
        }

        if (indexArray[9] > 0) {
            valueArray[indexArray[9] - 1] = (parent != null)
                                            ? parent.getSampleName()
                                            : "";
        }

        if (indexArray[10] > 0) {
            valueArray[indexArray[10] - 1] = (sampleType != null)
                                             ? sampleType.getDescription()
                                             : "";
        }

        if (indexArray[11] > 0) {
            valueArray[indexArray[11] - 1] = (sampleProcess != null)
                                             ? sampleProcess.getDescription()
                                             : "";
        }

        if (indexArray[12] > 0) {
            valueArray[indexArray[12] - 1] = (subject != null)
                                             ? subject.getSubjectName()
                                             : "";
        }

        if (indexArray[13] > 0) {
            valueArray[indexArray[13] - 1] = (dateCollected.toString().startsWith("0001"))
                                             ? ""
                                             : (dateCollected != null)
                                               ? (new java.sql.Date(dateCollected.getTime())).toString()
                                               : "";
        }

        if (indexArray[14] > 0) {
            valueArray[indexArray[14] - 1] = (dateExtracted != null)
                                             ? (new java.sql.Date(dateExtracted.getTime())).toString()
                                             : "";
        }

        if (indexArray[15] > 0) {
            valueArray[indexArray[15] - 1] = (comments != null)
                                             ? comments
                                             : "";
        }

        return valueArray;
    }

    public void setSampleID(String input) {
        sampleID = input;
    }

    public void setSampleName(String input) {
        sampleName = input;
    }

    public void setValid(String input) {
        valid = input;
    }

    public void setParent(Sample input) {
        parent = input;
    }

    public void setSampleType(SampleType input) {
        sampleType = input;
    }

    public void setSampleProcess(SampleProcess input) {
        sampleProcess = input;
    }

    public void setSubject(Subject input) {
        subject = input;
    }

    public void setDateCollected(Date input) {
        dateCollected = input;
    }

    public void setDateExtracted(Date input) {
        dateExtracted = input;
    }

    public void setComments(String input) {
        comments = input;
    }
}



