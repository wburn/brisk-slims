/**
 * When a user wants to export a table (from search or browse or list of
 * containers etc), this file makes appropriate headers for the columns of
 * data in the file and prepared the data to be exported (method: getHeader(…)).
 * After the data is prepared, it can be accesses by an iterator and an overridden
 * next() method that returns data line by line in tab delimited format.
 * @author tvanrossum
 */
package icapture.com;

import java.util.Iterator;
import java.util.List;
import icapture.hibernate.Persistent;

public final class DataExport implements Iterator {

    private UserHttpSess session;
    private Iterator objIterator;
    private byte[] valueIndexArray;
    private int colCount;
    private boolean[] canShowInReport;
    private List objList;

    public DataExport(UserHttpSess tmpHttpSessObj) {
        session = tmpHttpSessObj;
    }

    /**
     * gets headers of columns and prepares data
     * @param selectName what table to get the header for
     * @param subjectID needed in case a view of container contents is limited by subjects, can be null
     * @param containerID needed in case a view of container contents is limited by container, can be null
     * @return headers of data columns
     */
    public String getHeader(String selectName, String subjectID, String containerID, String sampId) {
        System.out.println("selectName is: " + selectName);
        if (selectName.equals("containertype")) {
            return getContainerTypeHeader();
        } else if (selectName.equals("shippedTo")) {
            return getShippedToHeader();
        } else if (selectName.equals("sampleProcess")) {
            return getSampleProcessHeader();
        } else if (selectName.equals("cohort")) {
            return getCohortHeader();
        } else if (selectName.equals("container")) {
            return getContainerHeader();
        } else if (selectName.equals("freezer")) {
            return getFreezerHeader();
        } else if (selectName.equals("sampleType")) {
            return getSampleTypeHeader();
        } else if (selectName.equals("subject")) {
            return getSubjectHeader();
        } else if (selectName.equals("sample")) {
            return getSampleHeader(subjectID);
        } else if (selectName.equals("containerContent")) {
            return getContainerContentHeader(containerID, subjectID);
        } else if (selectName.equals("genotypingRun")) {
            return getGenotypingRunHeader();
        } else if (selectName.equals("genotype")) {
            return getGenotypeHeader();
        } else if (selectName.equals("snp")) {
            return getSnpHeader();
        } else if (selectName.equals("sampleDocuments")) {
            return getSampleDocumentsHeader(sampId);
        } else {
            return null;
        }
    }

    public String getQueryResultsHeader(String query) {
        try {
            // set up export
            objList = session.getQueryResults(query);
            valueIndexArray = null;

            // get header
            StringBuffer xyz = new StringBuffer();
            String[] keyArray = session.getQueryResultsColumnNames(query);
            colCount = keyArray.length;

            for (int i = 0; i < colCount; i++) {
                xyz.append(keyArray[i]).append("\t");
            }
            return xyz.toString();
        } catch (Exception ex) {
            System.out.println("DataExport.java: " + ex.getLocalizedMessage());
            return null;
        }
    }

    /**
     * gets iterator for data entries to export
     * @return
     */
    public Iterator iterator() {
        objIterator = objList.iterator();
        return this;
    }

    public boolean hasNext() {
        return objIterator.hasNext();
    }

    /**
     * override of Iterator's next() method, gets tab delimited line of data
     * @return
     */
    public Object next() {
        StringBuffer xyz = new StringBuffer();
        if (valueIndexArray != null) {
            Persistent obj = (Persistent) objIterator.next();
            String[] var = obj.getValueArrayReadable(valueIndexArray, colCount);
            for (int i = 0; i < colCount; i++) {
                if (canShowInReport[i]) {
                    xyz.append((var[i] == null || var[i].equals("&nbsp;")) ? "" : var[i]).append("\t");
                }
            }
        } else {
            String str = "";
            Integer num = -1;
            // 1 return field
            if (objIterator.hasNext() && objIterator.next().getClass().equals(str.getClass())) {
                str = (String) objIterator.next();
                xyz.append((str == null || str.equals("&nbsp;")) ? "" : str).append("\t");
            } else if (objIterator.hasNext() && objIterator.next().getClass().equals(num.getClass())) {
                num = (Integer) objIterator.next();
                xyz.append((num == null) ? "" : num).append("\t");
            } // multiple return fields
            else if (objIterator.hasNext()) {
                Object[] objArr = (Object[]) objIterator.next();
                for (int i = 0; i < objArr.length; i++) {
                    xyz.append((objArr[i] == null || objArr[i].equals("&nbsp;")) ? "" : objArr[i]).append("\t");
                }
            }
        }

        return xyz.toString();
    }

    public void remove() {
    }

    private String getShippedToHeader() {
        try {
            StringBuffer xyz = new StringBuffer();
            MetadataManager mdm = this.session.getMetadataManager();
            objList = session.getAllShippedTo(-1);

            String[] keyArray = mdm.getShippedToKeyArray();
            colCount = keyArray.length;
            canShowInReport = new boolean[colCount];
            for (int i = 0; i < colCount; i++) {
                canShowInReport[i] = mdm.getShippedToCanShowInReport(keyArray[i]);
            }
            valueIndexArray = mdm.getShippedToIndexArray();

            for (int i = 0; i < colCount; i++) {
                if (canShowInReport[i]) {
                    xyz.append(mdm.getShippedToShortName(keyArray[i])).append("\t");
                }
            }
            return xyz.toString();
        } catch (Exception ex) {
            return null;
        }
    }

    private String getFreezerHeader() {
        try {
            StringBuffer xyz = new StringBuffer();
            MetadataManager mdm = this.session.getMetadataManager();
            objList = session.getAllFreezers(-1);

            String[] keyArray = mdm.getFreezerKeyArray();
            colCount = keyArray.length;
            canShowInReport = new boolean[colCount];
            for (int i = 0; i < colCount; i++) {
                canShowInReport[i] = mdm.getFreezerCanShowInReport(keyArray[i]);
            }
            valueIndexArray = mdm.getFreezerIndexArray();

            for (int i = 0; i < colCount; i++) {
                if (canShowInReport[i]) {
                    xyz.append(mdm.getFreezerShortName(keyArray[i])).append("\t");
                }
            }
            return xyz.toString();
        } catch (Exception ex) {
            return null;
        }
    }

    private String getContainerTypeHeader() {
        try {
            StringBuffer xyz = new StringBuffer();
            MetadataManager mdm = this.session.getMetadataManager();
            objList = session.getAllContainerTypes(-1);

            String[] keyArray = mdm.getContainerTypeKeyArray();
            colCount = keyArray.length;
            canShowInReport = new boolean[colCount];
            for (int i = 0; i < colCount; i++) {
                canShowInReport[i] = mdm.getContainerTypeCanShowInReport(keyArray[i]);
            }
            valueIndexArray = mdm.getContainerTypeIndexArray();

            for (int i = 0; i < colCount; i++) {
                if (canShowInReport[i]) {
                    xyz.append(mdm.getContainerTypeShortName(keyArray[i])).append("\t");
                }
            }
            return xyz.toString();
        } catch (Exception ex) {
            return null;
        }
    }

    private String getCohortHeader() {
        try {
            StringBuffer xyz = new StringBuffer();
            MetadataManager mdm = this.session.getMetadataManager();
            objList = session.getAllCohorts(-1);

            String[] keyArray = mdm.getCohortKeyArray();
            colCount = keyArray.length;
            canShowInReport = new boolean[colCount];
            for (int i = 0; i < colCount; i++) {
                canShowInReport[i] = mdm.getCohortCanShowInReport(keyArray[i]);
            }
            valueIndexArray = mdm.getCohortIndexArray();

            for (int i = 0; i < colCount; i++) {
                if (canShowInReport[i]) {
                    xyz.append(mdm.getCohortShortName(keyArray[i])).append("\t");
                }
            }
            return xyz.toString();
        } catch (Exception ex) {
            return null;
        }
    }

    private String getContainerHeader() {
        try {
            StringBuffer xyz = new StringBuffer();
            MetadataManager mdm = this.session.getMetadataManager();
            objList = session.getAllContainers(-1);

            String[] keyArray = mdm.getContainerKeyArray();
            colCount = keyArray.length;
            canShowInReport = new boolean[colCount];
            for (int i = 0; i < colCount; i++) {
                canShowInReport[i] = mdm.getContainerCanShowInReport(keyArray[i]);
            }
            valueIndexArray = mdm.getContainerIndexArray();

            for (int i = 0; i < colCount; i++) {
                if (canShowInReport[i]) {
                    xyz.append(mdm.getContainerShortName(keyArray[i])).append("\t");
                }
            }
            return xyz.toString();
        } catch (Exception ex) {
            return null;
        }
    }

    private String getSampleProcessHeader() {
        try {
            StringBuffer xyz = new StringBuffer();
            MetadataManager mdm = this.session.getMetadataManager();
            objList = session.getAllSampleProcesss(-1);

            String[] keyArray = mdm.getSampleProcessKeyArray();
            colCount = keyArray.length;
            canShowInReport = new boolean[colCount];
            for (int i = 0; i < colCount; i++) {
                canShowInReport[i] = mdm.getSampleProcessCanShowInReport(keyArray[i]);
            }
            valueIndexArray = mdm.getSampleProcessIndexArray();

            for (int i = 0; i < colCount; i++) {
                if (canShowInReport[i]) {
                    xyz.append(mdm.getSampleProcessShortName(keyArray[i])).append("\t");
                }
            }
            return xyz.toString();
        } catch (Exception ex) {
            return null;
        }
    }

    private String getSampleTypeHeader() {
        try {
            StringBuffer xyz = new StringBuffer();
            MetadataManager mdm = this.session.getMetadataManager();
            objList = session.getAllSampleTypes(-1);

            String[] keyArray = mdm.getSampleTypeKeyArray();
            colCount = keyArray.length;
            canShowInReport = new boolean[colCount];
            for (int i = 0; i < colCount; i++) {
                canShowInReport[i] = mdm.getSampleTypeCanShowInReport(keyArray[i]);
            }
            valueIndexArray = mdm.getSampleTypeIndexArray();

            for (int i = 0; i < colCount; i++) {
                if (canShowInReport[i]) {
                    xyz.append(mdm.getSampleTypeShortName(keyArray[i])).append("\t");
                }
            }
            return xyz.toString();
        } catch (Exception ex) {
            return null;
        }
    }

    private String getSubjectHeader() {
        try {
            StringBuffer xyz = new StringBuffer();
            MetadataManager mdm = this.session.getMetadataManager();
            objList = session.getAllSubjects(-1);

            String[] keyArray = mdm.getSubjectKeyArray();
            colCount = keyArray.length;
            canShowInReport = new boolean[colCount];
            for (int i = 0; i < colCount; i++) {
                canShowInReport[i] = mdm.getSubjectCanShowInReport(keyArray[i]);
            }
            valueIndexArray = mdm.getSubjectIndexArray();

            for (int i = 0; i < colCount; i++) {
                if (canShowInReport[i]) {
                    xyz.append(mdm.getSubjectShortName(keyArray[i])).append("\t");
                }
            }
            return xyz.toString();
        } catch (Exception ex) {
            return null;
        }
    }

    private String getSampleHeader(String subjectID) {
        try {
            StringBuffer xyz = new StringBuffer();
            MetadataManager mdm = this.session.getMetadataManager();
            subjectID = (subjectID != null && subjectID.equalsIgnoreCase("null")) ? null : subjectID;
            objList = session.getAllSamples(subjectID, -1);
            System.out.println("subjectID: " + subjectID);
            System.out.println("objList.size(): " + objList.size());

            String[] keyArray = mdm.getSampleKeyArray();
            colCount = keyArray.length;
            canShowInReport = new boolean[colCount];
            for (int i = 0; i < colCount; i++) {
                canShowInReport[i] = mdm.getSampleCanShowInReport(keyArray[i]);
            }
            valueIndexArray = mdm.getSampleIndexArray();

            for (int i = 0; i < colCount; i++) {
                if (canShowInReport[i]) {
                    xyz.append(mdm.getSampleShortName(keyArray[i])).append("\t");
                }
            }
            return xyz.toString();
        } catch (Exception ex) {
            return null;
        }
    }

    private String getContainerContentHeader(String contID, String subjID) {
        try {
            StringBuffer xyz = new StringBuffer();
            MetadataManager mdm = this.session.getMetadataManager();
            objList = session.getAllContainerContents(-1, contID, subjID);

            String[] keyArray = mdm.getContainerContentKeyArray();
            colCount = keyArray.length;
            canShowInReport = new boolean[colCount];
            for (int i = 0; i < colCount; i++) {
                canShowInReport[i] = mdm.getContainerContentCanShowInReport(keyArray[i]);
            }
            valueIndexArray = mdm.getContainerContentIndexArray();

            for (int i = 0; i < colCount; i++) {
                if (canShowInReport[i]) {
                    xyz.append(mdm.getContainerContentShortName(keyArray[i])).append("	");
                }
            }
            return xyz.toString();
        } catch (Exception ex) {
            return null;
        }
    }

    private String getGenotypingRunHeader() {
        try {
            StringBuffer xyz = new StringBuffer();
            MetadataManager mdm = this.session.getMetadataManager();
            objList = session.getAllGenotypingRuns(-1);

            String[] keyArray = mdm.getGenotypingRunKeyArray();
            colCount = keyArray.length;
            canShowInReport = new boolean[colCount];
            for (int i = 0; i < colCount; i++) {
                canShowInReport[i] = mdm.getGenotypingRunCanShowInReport(keyArray[i]);
            }
            valueIndexArray = mdm.getGenotypingRunIndexArray();

            for (int i = 0; i < colCount; i++) {
                if (canShowInReport[i]) {
                    xyz.append(mdm.getGenotypingRunShortName(keyArray[i])).append("	");
                }
            }
            return xyz.toString();
        } catch (Exception ex) {
            return null;
        }
    }

    private String getGenotypeHeader() {
        try {
            StringBuffer xyz = new StringBuffer();
            MetadataManager mdm = this.session.getMetadataManager();
            objList = session.getAllGenotypes(-1);

            String[] keyArray = mdm.getGenotypeKeyArray();
            colCount = keyArray.length;
            canShowInReport = new boolean[colCount];
            for (int i = 0; i < colCount; i++) {
                canShowInReport[i] = mdm.getGenotypeCanShowInReport(keyArray[i]);
            }
            valueIndexArray = mdm.getGenotypeIndexArray();

            for (int i = 0; i < colCount; i++) {
                if (canShowInReport[i]) {
                    xyz.append(mdm.getGenotypeShortName(keyArray[i])).append("	");
                }
            }
            return xyz.toString();
        } catch (Exception ex) {
            return null;
        }
    }

    private String getSnpHeader() {
        try {
            StringBuffer xyz = new StringBuffer();
            MetadataManager mdm = this.session.getMetadataManager();
            objList = session.getAllSnps(-1);

            String[] keyArray = mdm.getSnpKeyArray();
            colCount = keyArray.length;
            canShowInReport = new boolean[colCount];
            for (int i = 0; i < colCount; i++) {
                canShowInReport[i] = mdm.getSnpCanShowInReport(keyArray[i]);
            }
            valueIndexArray = mdm.getSnpIndexArray();

            for (int i = 0; i < colCount; i++) {
                if (canShowInReport[i]) {
                    xyz.append(mdm.getSnpShortName(keyArray[i])).append("	");
                }
            }
            return xyz.toString();
        } catch (Exception ex) {
            return null;
        }
    }

    private String getSampleDocumentsHeader(String sampId) {
        try {
            StringBuffer xyz = new StringBuffer();
            MetadataManager mdm = this.session.getMetadataManager();
            objList = session.getAllSampleDocuments(-1, sampId);

            String[] keyArray = mdm.getSampleDocumentsKeyArray();
            colCount = keyArray.length;
            canShowInReport = new boolean[colCount];
            for (int i = 0; i < colCount; i++) {
                canShowInReport[i] = mdm.getSampleDocumentsCanShowInReport(keyArray[i]);
            }
            valueIndexArray = mdm.getSampleDocumentsIndexArray();

            for (int i = 0; i < colCount; i++) {
                if (canShowInReport[i]) {
                    xyz.append(mdm.getSampleDocumentsShortName(keyArray[i])).append("\t");
                }
            }
            return xyz.toString();
        } catch (Exception ex) {
            return null;
        }
    }
}
