
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package icapture.hibernate;

//~--- JDK imports ------------------------------------------------------------

/**
 *
 * @author ATan1
 */
public final class SampleDocuments extends Persistent {
    private String comments;
    private byte[] document;
    private String filename;
    private Sample sample;
    private String sampleDocumentID;
    private String sampleId;

    public String getSampleDocumentID() {
        return sampleDocumentID;
    }

    public String getSampleID() {
        return sampleId;
    }

    public String getFilename() {
        return filename;
    }

    public String getComments() {
        return comments;
    }

    public Sample getSample() {
        return sample;
    }

    public byte[] getDocument() {
        return document;
    }

    public String getVisibleName() {
        return sampleDocumentID;
    }

    public String getPropertyValue(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return sampleDocumentID;
        } else {
            return super.getPropertyValue(propertyName);
        }
    }

    public Object getPropertyObject(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return sampleDocumentID;
        } else {
            return super.getPropertyObject(propertyName);
        }
    }

    public String[] getValueArray(byte[] indexArray, int viewSize) {
        return getValueArrayReadable(indexArray, viewSize);
    }

    public String[] getValueArrayReadable(byte[] indexArray, int viewSize) {
        String[] valueArray = super.getValueArray(indexArray, viewSize);

        if (indexArray[6] > 0) {
            valueArray[indexArray[6] - 1] = sampleDocumentID;
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = sample.getSampleID();
        }

        if (indexArray[8] > 0) {
            valueArray[indexArray[8] - 1] = filename;
        }

        if (indexArray[9] > 0) {
            valueArray[indexArray[9] - 1] = (comments != null)
                                            ? comments
                                            : "";
        }

        return valueArray;
    }

    public void setComments(String input) {
        comments = input;
    }

    public void setDocument(byte[] input) {
        document = input;
    }

    public void setFilename(String input) {
        filename = input;
    }

    public void setSample(Sample input) {
        sample = input;
    }

    public void setSampleDocumentID(String input) {
        sampleDocumentID = input;
    }

    public void setSampleID(String input) {
        sampleId = input;
    }
}



