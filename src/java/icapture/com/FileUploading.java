/**
 * Enables files to be uploaded to create new subjects, samples, containers
 * and shopping lists. Has methods to check a file for upload and return any
 * lines that have problems. Also has methods to create elements.
 * @author tvanrossum
 */
package icapture.com;

import org.biomoby.services.ncbi.NCBIJobManager;
import org.biomoby.services.ncbi.jobs.GeneInformationByEntrezGeneIdFromXML;
import org.biomoby.services.ncbi.jobs.SnpInformationBySnpFromXML;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import icapture.hibernate.*;
import org.hibernate.Query;
import java.util.Calendar;
import java.util.Date;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashSet;
import org.apache.commons.fileupload.*;
import org.biomoby.services.ncbi.jobs.EntrezGeneIdBySnpFromXML;
import org.biomoby.services.ncbi.jobs.SNPPosBySnpFromXML;

public class FileUploading {

    ArrayList subjectsToCreate = null;
    ArrayList samplesToCreate = null;
    ArrayList contentsToCreate = null;
    ArrayList containersToCreate = null;
    ArrayList containersToEdit = null;
    ArrayList snpsToCreate = null;
    ArrayList genotypesToCreate = null;
    HashMap subjectIDStorage = new HashMap();
    String genotypingRunID = "";
    int NUMBEROFSUBJECTFIELDS = 9;
    int NUMBEROFSAMPLEFIELDS = 8;
    int NUMBEROFCONTENTFIELDS = 17;
    int NUMBEROFCONTAINERFIELDS = 16;
    int NUMBEROFCONTAINERLOCATIONFIELDS = 4;
    int SNPINFOSTARTBEADFILE = 4;
    int SNPINFOENDBEADFILE = 7;
    int NUMBEROFSNPFIELDS = 9;
    int MINBEADFIELDSNEEDED = 18;
    int MINBEADGENOTYPEFIELDSNEEDED = 5;

    public FileUploading() {
        subjectsToCreate = new ArrayList();
        samplesToCreate = new ArrayList();
        contentsToCreate = new ArrayList();
        containersToCreate = new ArrayList();
        containersToEdit = new ArrayList();
        snpsToCreate = new ArrayList();
        genotypesToCreate = new ArrayList();
    }

    /**
     * check the upload file for subjects to make sure every subject can be made
     * prepares list of subjects to be added from successful lines
     * @param iter
     * @return array of lists detailing errors in file and good subjects: {alreadyExists,missingValues,badCohort,badEthnicity,number of good subjects}
     * @throws java.lang.Exception
     */
    public Object[] checkUploadSubjectFile(FileItemIterator iter) throws Exception {
        // reset list of good subjects
        subjectsToCreate.clear();

        // get input type (need this before parsing the file)
        BufferedReader readerParam = null;
        InputStream stream = null;

        ArrayList<String> results = new ArrayList();
        ArrayList<String> subjects = new ArrayList();
        ArrayList<String> dupSubjects = new ArrayList();
        ArrayList<String> missingValues = null;
        ArrayList<String> alreadyExists = null;
        ArrayList<String> badCohort = null;
        ArrayList<String> badEthnicity = null;
        ArrayList<HashMap> goodSubjects = null;
        HashMap key = new HashMap();

        // need the typeToLoad value before processing file
        while (iter.hasNext()) {
            FileItemStream item = iter.next();
            String name = item.getFieldName();
//            System.out.println("checkUploadFile name:" + name + " item" + item.getName());
            stream = item.openStream();
//            System.out.println("checkUploadFile " + name);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));


            reader = new BufferedReader(new InputStreamReader(stream));
            String line = null;
            try {
                missingValues = new ArrayList();
                alreadyExists = new ArrayList();
                results = new ArrayList();
                badCohort = new ArrayList();
                badEthnicity = new ArrayList();
                goodSubjects = new ArrayList();
                String[] lineAr = null;
                String cohortID = null;
                String subjectName = null;
                String ethnicityID = null;
                String familyName = null;
                String gender = null;
                String consent = null;
                String motherName = null;
                String fatherName = null;
                String comments = null;
                // read each line
                while ((line = reader.readLine()) != null) {
//                    System.out.println("LINE:" + line);

                    // get rid of any apostrophes, they mess with DB
                    line.replace("'", "");
                    // check if right format
                    line.trim();
                    if (line.length() == 0) {
                        continue;
                    }
                    lineAr = line.split("\t");
                    if (lineAr.length != NUMBEROFSUBJECTFIELDS) {
                        missingValues.add(line);
                        continue;
                    }
                    // get rid of trailing spaces
                    for (int i = 0; i < lineAr.length; i++) {
                        lineAr[i] = lineAr[i].trim();
                    }
                    // shouldn't be any blanks
                    boolean blanks = false;
                    for (int i = 0; i < lineAr.length; i++) {
                        if (lineAr[i].equals("")) {
                            missingValues.add(line);
                            blanks = true;
                        }
                    }
                    if (blanks) {
                        continue;
                    }
                    // check each field

                    //Cohort
                    key.clear();
                    key.put("description", "'" + lineAr[0] + "'");
                    results = (ArrayList) getIDsOfClass(Cohort.class, key, "cohortID");
                    // if not, add to DB warning list
                    if (results == null || results.size() != 1) {
                        badCohort.add(line);
                        continue;
                    } else {
                        cohortID = results.get(0);
                    }

                    //Subject ID
                    key.clear();
                    key.put("subjectName", "'" + lineAr[1] + "'");
                    key.put("cohort", cohortID);
                    results = (ArrayList) getIDsOfClass(Subject.class, key, "subjectID");
                    // if not, add to DB warning list
                    if (results != null && results.size() != 0) {
                        alreadyExists.add(line);
                        continue;
                    }
                    subjectName = lineAr[1].trim();
                    if (subjects.contains(cohortID + "_" + subjectName)) {
                        dupSubjects.add(line);
                        continue;
                    }
                    subjects.add(cohortID + "_" + subjectName);

                    //Family ID
                    if (lineAr[2].length() == 0) {
                        missingValues.add(line);
                        continue;
                    }
                    familyName = lineAr[2];

                    //Gender
                    if (lineAr[3].length() == 0) {
                        missingValues.add(line);
                        continue;
                    }
                    if (lineAr[3].equalsIgnoreCase("0")) {
                        gender = "0";
                    }
                    if (lineAr[3].equalsIgnoreCase("m")) {
                        gender = "1";
                    }
                    if (lineAr[3].equalsIgnoreCase("f")) {
                        gender = "2";
                    }

                    //Consent
                    if (lineAr[4].length() == 0) {
                        missingValues.add(line);
                        continue;
                    }
                    if (lineAr[4].equalsIgnoreCase("0")) {
                        consent = "2";
                    }
                    if (lineAr[4].equalsIgnoreCase("y")) {
                        consent = "1";
                    }
                    if (lineAr[4].equalsIgnoreCase("n")) {
                        consent = "0";
                    }

                    //Mother ID
                    if (lineAr[5].length() == 0) {
                        missingValues.add(line);
                        continue;
                    }
                    motherName = lineAr[5];

                    //Father ID
                    if (lineAr[6].length() == 0) {
                        missingValues.add(line);
                        continue;
                    }
                    fatherName = lineAr[6];

                    //Ethnicity
                    key.clear();
                    key.put("ethnicity", "'" + lineAr[7] + "'");
                    results = (ArrayList) getIDsOfClass(Ethnicity.class, key, "ethnicityID");
                    // if not, add to DB warning list
                    if (results == null || results.size() != 1) {
                        badEthnicity.add(line);
                        continue;
                    } else {
                        ethnicityID = results.get(0);
                    }
                    //Comments
                    if (lineAr[8].length() == 0) {
                        missingValues.add(line);
                        continue;
                    }
                    if (lineAr[8].equalsIgnoreCase("0")) {
                        comments = "";
                    } else {
                        comments = lineAr[8];
                    }

                    // if all is ok, save subject details to be added
                    HashMap subjHash = new HashMap(NUMBEROFSUBJECTFIELDS);
                    subjHash.put("cohort", cohortID);
                    subjHash.put("name", subjectName);
                    subjHash.put("ethnicity", ethnicityID);
                    subjHash.put("family", familyName);
                    subjHash.put("gender", gender);
                    subjHash.put("consent", consent);
                    subjHash.put("mother", motherName);
                    subjHash.put("father", fatherName);
                    subjHash.put("comments", comments);
                    goodSubjects.add(subjHash);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
        subjectsToCreate = goodSubjects;
        Object[] arr = {alreadyExists, dupSubjects, missingValues, badCohort, badEthnicity, goodSubjects.size()};
        return arr;
    }

    public int addSubjects(UserHttpSess session) throws Exception {
        Iterator iter = subjectsToCreate.iterator();
        HashMap subj = new HashMap(NUMBEROFSUBJECTFIELDS);
        Cohort cohort = null;
        Ethnicity ethnicity = null;
        int messageNum = 0;
        // create each subject
        while (iter.hasNext() && messageNum == 0) {
            subj = (HashMap) iter.next();
            cohort = (Cohort) session.getObjectById(Cohort.class, subj.get("cohort").toString());
            ethnicity = (Ethnicity) session.getObjectById(Ethnicity.class, subj.get("ethnicity").toString());
            messageNum = session.addSubject("-1", subj.get("name").toString(), subj.get("father").toString(), subj.get("mother").toString(), cohort, subj.get("gender").toString(), subj.get("family").toString(), subj.get("consent").toString(), ethnicity, subj.get("comments").toString());
        }
        return messageNum;
    }

    /**
     * check the upload file for db samples to make sure every sample can be made
     * @param iter
     * @return array of lists detailing errors in file and good samples: {alreadyExists,missingValues,badCohort,badSubject,badSampleType,badDate,number of good samples}
     * @throws java.lang.Exception
     */
    public Object[] checkUploadDBSampleFile(FileItemIterator iter) throws Exception {
        // reset list of good subjects
        samplesToCreate.clear();

        // get input type (need this before parsing the file)
        BufferedReader readerParam = null;
        InputStream stream = null;

        ArrayList<String> results = new ArrayList();
        ArrayList<String> sampleNames = new ArrayList();
        ArrayList<String> dupSampleNames = new ArrayList();
        ArrayList<String> missingValues = null;
        ArrayList<String> alreadyExists = null;
        ArrayList<String> badCohort = null;
        ArrayList<String> badSubject = null;
        ArrayList<String> badSampleType = null;
        ArrayList<String> badDate = null;
        ArrayList<HashMap> goodSamples = null;
        HashMap key = new HashMap();

        // need the typeToLoad value before processing file
        while (iter.hasNext()) {
            FileItemStream item = iter.next();
            String name = item.getFieldName();
//            System.out.println("checkUploadFile name:" + name + " item" + item.getName());
            stream = item.openStream();
//            System.out.println("checkUploadFile " + name);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));


            reader = new BufferedReader(new InputStreamReader(stream));
            String line = null;
            try {
                missingValues = new ArrayList();
                alreadyExists = new ArrayList();
                results = new ArrayList();
                badCohort = new ArrayList();
                badSubject = new ArrayList();
                badSampleType = new ArrayList();
                badDate = new ArrayList();
                goodSamples = new ArrayList();
                String[] lineAr = null;
                String sampleName = null;
                String cohortID = null;
                String subjectID = null;
                String valid = null;
                String sampleTypeID = null;
                Date collectionDate = null;
                Date extractionDate = null;
                String comments = null;
                // read each line
                while ((line = reader.readLine()) != null) {
//                    System.out.println("LINE:" + line);

                    // get rid of any apostrophes, they mess with DB
                    line.replace("'", "");
                    // check if right format
                    line.trim();
                    if (line.length() == 0) {
                        continue;
                    }
                    lineAr = line.split("\t");
                    if (lineAr.length != NUMBEROFSAMPLEFIELDS) {
                        missingValues.add(line);
                        continue;
                    }
                    // get rid of trailing spaces
                    for (int i = 0; i < lineAr.length; i++) {
                        lineAr[i] = lineAr[i].trim();
                    }
                    // shouldn't be any blanks
                    boolean blanks = false;
                    for (int i = 0; i < lineAr.length; i++) {
                        if (lineAr[i].equals("")) {
                            missingValues.add(line);
                            blanks = true;
                        }
                    }
                    if (blanks) {
                        continue;
                    }
                    // check each field

                    //Sample ID
                    key.clear();
                    key.put("sampleName", "'" + lineAr[0] + "'");
                    results = (ArrayList) getIDsOfClass(Sample.class, key, "sampleID");
                    // if exists, add to DB warning list
                    if (results != null && results.size() != 0) {
                        alreadyExists.add(line);
                        continue;
                    }
                    if (sampleNames.contains(lineAr[0])) {
                        dupSampleNames.add(line);
                        continue;
                    }
                    sampleName = lineAr[0];
                    sampleNames.add(sampleName);

                    //Cohort
                    key.clear();
                    key.put("description", "'" + lineAr[1] + "'");
                    results = (ArrayList) getIDsOfClass(Cohort.class, key, "cohortID");
                    // if not, add to DB warning list
                    if (results == null || results.size() != 1) {
                        badCohort.add(line);
                        continue;
                    } else {
                        cohortID = results.get(0);
                    }

                    //Subject ID
                    key.clear();
                    // make sure subject exists
                    key.put("subjectName", "'" + lineAr[2] + "'");
                    key.put("cohort.cohortID", cohortID);
                    results = (ArrayList) getIDsOfClass(Subject.class, key, "subjectID");
                    // if not, add to DB warning list
                    if (results == null || results.size() == 0) {
                        badSubject.add(line);
                        continue;
                    }
                    subjectID = results.get(0);

                    //Valid
                    if (lineAr[3].length() == 0) {
                        missingValues.add(line);
                        continue;
                    }
                    if (lineAr[3].equalsIgnoreCase("0")) {
                        valid = "2";
                    }
                    if (lineAr[3].equalsIgnoreCase("y")) {
                        valid = "1";
                    }
                    if (lineAr[3].equalsIgnoreCase("n")) {
                        valid = "0";
                    }

                    //Sample Type
                    key.clear();
                    key.put("description", "'" + lineAr[4] + "'");
                    results = (ArrayList) getIDsOfClass(SampleType.class, key, "sampleTypeID");
                    // if not, add to DB warning list
                    if (results == null || results.size() != 1) {
                        badSampleType.add(line);
                        continue;
                    } else {
                        sampleTypeID = results.get(0);
                    }

                    //Collection date
                    if (lineAr[5].length() == 0) {
                        badDate.add(line);
                        continue;
                    }
                    if (lineAr[5].equalsIgnoreCase("0")) {
                        collectionDate = null;
                    } else {
                        collectionDate = getDate(lineAr[5]);
                    }

                    //extraction date
                    if (lineAr[6].length() == 0) {
                        badDate.add(line);
                        continue;
                    }
                    if (lineAr[6].equalsIgnoreCase("0")) {
                        extractionDate = null;
                    } else {
                        extractionDate = getDate(lineAr[6]);
                    }

                    //Comments
                    if (lineAr[7].length() == 0) {
                        missingValues.add(line);
                        continue;
                    }
                    if (lineAr[7].equalsIgnoreCase("0")) {
                        comments = "";
                    } else {
                        comments = lineAr[7];
                    }

                    // if all is ok, save subject details to be added
                    HashMap samHash = new HashMap(NUMBEROFSAMPLEFIELDS);
                    samHash.put("sampleName", sampleName);
                    samHash.put("subjectID", subjectID);
                    samHash.put("valid", valid);
                    samHash.put("sampleTypeID", sampleTypeID);
                    samHash.put("collectionDate", collectionDate);
                    samHash.put("extractionDate", extractionDate);
                    samHash.put("comments", comments);
                    goodSamples.add(samHash);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
        samplesToCreate = goodSamples;
        Object[] arr = {alreadyExists, dupSampleNames, missingValues, badCohort, badSubject, badSampleType, badDate, goodSamples.size()};
        return arr;
    }

    public int addSamples(UserHttpSess session) throws Exception {
        Iterator iter = samplesToCreate.iterator();
        HashMap samp = new HashMap(NUMBEROFSAMPLEFIELDS);
        SampleType sampleType = null;
        Subject subject = null;
        int messageNum = 0;
        // create each subject
        while (iter.hasNext() && messageNum == 0) {
            samp = (HashMap) iter.next();
            sampleType = (SampleType) session.getObjectById(SampleType.class, samp.get("sampleTypeID").toString());
            subject = (Subject) session.getObjectById(Subject.class, samp.get("subjectID").toString());
            messageNum = session.addSample("-1", samp.get("sampleName").toString(), samp.get("valid").toString(),
                    null, sampleType, subject, (Date) samp.get("collectionDate"), (Date) samp.get("extractionDate"),
                    samp.get("comments").toString());
        }
        return messageNum;
    }

    /**
     * check the upload file for contents to make sure every content can be made
     * @param iter
     * @return array of lists detailing errors in file and good contents: {alreadyExists,missingValues,badCohort,badSubject,badSampleType,badDate,number of good samples}
     * @throws java.lang.Exception
     */
    public Object[] checkUploadContentsFile(FileItemIterator iter) throws Exception {
        // reset list of good contents
        contentsToCreate.clear();

        // get input type (need this before parsing the file)
        BufferedReader readerParam = null;
        InputStream stream = null;

        ArrayList<String> results = new ArrayList();
        ArrayList<String> contents = new ArrayList();
        ArrayList<String> dupContents = new ArrayList();
        ArrayList<String> missingValues = null;
        ArrayList<String> alreadyExists = null;
        ArrayList<String> badContainer = null;
        ArrayList<String> badRow = null;
        ArrayList<String> badCol = null;
        ArrayList<String> badVol = null;
        ArrayList<String> badConcen = null;
        ArrayList<String> badDilution = null;
        ArrayList<String> badParent = null;
        ArrayList<String> badSample = null;
        ArrayList<String> badDate = null;
        ArrayList<String> badMaterialType = null;
        ArrayList<HashMap> goodContents = null;
        HashMap key = new HashMap();

        // need the typeToLoad value before processing file
        while (iter.hasNext()) {
            FileItemStream item = iter.next();
            String name = item.getFieldName();
//            System.out.println("checkUploadFile name:" + name + " item" + item.getName());
            stream = item.openStream();
//            System.out.println("checkUploadFile " + name);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));


            reader = new BufferedReader(new InputStreamReader(stream));
            String line = null;
            try {
                missingValues = new ArrayList();
                alreadyExists = new ArrayList();
                dupContents = new ArrayList();
                results = new ArrayList();
                badContainer = new ArrayList();
                badRow = new ArrayList();
                badCol = new ArrayList();
                badVol = new ArrayList();
                badConcen = new ArrayList();
                badDilution = new ArrayList();
                badParent = new ArrayList();
                badSample = new ArrayList();
                badDate = new ArrayList();
                badMaterialType = new ArrayList();
                goodContents = new ArrayList();
                String[] lineAr = null;
                String containerID = null;
                int row = -1;
                int column = -1;
                String sampleName = null;
                String sampleID = null;
                String barcode = null;
                String materialTypeID = null;
                Double volume = null;
                Double concentration = null;
                String dilution = null;

                String parentContainerID = null;
                int parentRow = -1;
                int parentColumn = -1;
                String parentSampleID = null;
                String parentID = null;

                String contaminated = null;
                Date quantificationDate = null;
                Date amplificationDate = null;
                String comments = null;
                // read each line
                while ((line = reader.readLine()) != null) {
//                    System.out.println("LINE:" + line);

                    // get rid of any apostrophes, they mess with DB
                    line.replace("'", "");
                    // check if right format
                    line.trim();
                    if (line.length() == 0) {
                        continue;
                    }
                    lineAr = line.split("\t");
                    if (lineAr.length != NUMBEROFCONTENTFIELDS) {
                        missingValues.add(line);
                        continue;
                    }
                    // get rid of trailing spaces
                    for (int i = 0; i < lineAr.length; i++) {
                        lineAr[i] = lineAr[i].trim();
                    }
                    // shouldn't be any blanks
                    boolean blanks = false;
                    for (int i = 0; i < lineAr.length; i++) {
                        if (lineAr[i].equals("")) {
                            missingValues.add(line);
                            blanks = true;
                        }
                    }
                    if (blanks) {
                        continue;
                    }
                    // check each field

                    //Container name
                    key.clear();
                    key.put("containerName", "'" + lineAr[0] + "'");
                    results = (ArrayList) getIDsOfClass(Container.class, key, "containerID");
                    // if exists, add to DB warning list
                    if (results == null || results.size() == 0) {
                        badContainer.add(line);
                        continue;
                    }
                    containerID = results.get(0);

                    //Row
                    String rowStr = lineAr[1];
                    try {
                        row = Integer.parseInt(rowStr);
                    } catch (NumberFormatException e) {
                        rowStr = Util.letterToNum(rowStr);
                        // if failed to change it to a number, it's bad
                        if (rowStr.equals(lineAr[1])) {
                            badRow.add(line);
                            continue;
                        }
                        try {
                            row = Integer.parseInt(rowStr);
                        } catch (NumberFormatException ex) {
                            badRow.add(line);
                            continue;
                        }
                    }

                    //Column
                    String colStr = lineAr[2];
                    try {
                        column = Integer.parseInt(colStr);
                    } catch (NumberFormatException e) {
                        colStr = Util.letterToNum(colStr);
                        // if failed to change it to a number, it's bad
                        if (rowStr.equals(lineAr[2])) {
                            badCol.add(line);
                            continue;
                        }
                        try {
                            column = Integer.parseInt(colStr);
                        } catch (NumberFormatException ex) {
                            badCol.add(line);
                            continue;
                        }
                    }

                    // check if container,row,col is repeated within file
                    if (contents.contains(containerID + "-" + row + "-" + column)) {
                        dupContents.add(line);
                        continue;
                    } else {
                        contents.add(containerID + "-" + row + "-" + column);
                    }

                    // check if content already exists
                    key.clear();
                    key.put("containerID", containerID);
                    key.put("row", row);
                    key.put("column", column);
                    results = (ArrayList) getIDsOfClass(ContainerContent.class, key, "containerContentsID");
                    // if error, abort
                    if (results == null) {
                        return null;
                    }// if exists, add to DB warning list
                    if (results != null && results.size() != 0) {
                        alreadyExists.add(line);
                        continue;
                    }

                    //Sample ID
                    key.clear();
                    // make sure subject exists
                    key.put("sampleName", "'" + lineAr[3] + "'");
                    results = (ArrayList) getIDsOfClass(Sample.class, key, "sampleID");
                    // if not, add to DB warning list
                    if (results == null || results.size() == 0) {
                        badSample.add(line);
                        continue;
                    }
                    sampleID = results.get(0);

                    //Barcode
                    if (lineAr[4].length() == 0) {
                        missingValues.add(line);
                        continue;
                    }
                    if (lineAr[4].equalsIgnoreCase("0")) {
                        barcode = null;
                    } else {
                        barcode = lineAr[4];
                    }

                    //Material Type
                    key.clear();
                    key.put("description", "'" + lineAr[5] + "'");
                    results = (ArrayList) getIDsOfClass(MaterialType.class, key, "materialTypeID");
                    // if not, add to DB warning list
                    if (results == null || results.size() != 1) {
                        badMaterialType.add(line);
                        continue;
                    } else {
                        materialTypeID = results.get(0);
                    }

                    //Volume
                    try {
                        volume = Double.parseDouble(lineAr[6]);
                    } catch (NumberFormatException e) {
                        // if failed to change it to a number, it's bad
                        badVol.add(line);
                        continue;
                    }
                    //Concentration
                    try {
                        concentration = Double.parseDouble(lineAr[7]);
                    } catch (NumberFormatException e) {
                        // if failed to change it to a number, it's bad
                        badConcen.add(line);
                        continue;
                    }
                    //Dilution
                    if (lineAr[8].length() == 0) {
                        missingValues.add(line);
                        continue;
                    }
                    if (lineAr[8].equalsIgnoreCase("0")) {
                        dilution = null;
                    } else if (lineAr[8].indexOf(":") <= 0) {
                        badDilution.add(line);
                        continue;
                    } else {
                        dilution = lineAr[8];
                    }

                    // If parent fields are all -2 or -3, don't both with them
                    if (lineAr[9].equals("-3") && lineAr[10].equals("-3")
                            && lineAr[11].equals("-3")
                            && lineAr[12].equals("-3")) {
                        parentID = "-3";

                        key.clear();
                        // make sure content exists
                        key.put("containerContentsID", parentID);
                        results = (ArrayList) getIDsOfClass(ContainerContent.class, key, "containerContentsID");
                        // if doesn't exist, add to DB warning list unless -2 or -3
                        if (results == null || results.size() == 0) {
                            parentID = null;
                        }
                    } else if (lineAr[9].equals("-2") && lineAr[10].equals("-2")
                            && lineAr[11].equals("-2")
                            && lineAr[12].equals("-2")) {
                        parentID = "-2";

                        key.clear();
                        // make sure content exists
                        key.put("containerContentsID", parentID);
                        results = (ArrayList) getIDsOfClass(ContainerContent.class, key, "containerContentsID");
                        // if doesn't exist, add to DB warning list unless -2 or -3
                        if (results == null || results.size() == 0) {
                            parentID = null;
                        }
                    } else {

                        //Parent Sample ID
                        key.clear();
                        // make sure subject exists
                        key.put("sampleName", "'" + lineAr[9] + "'");
                        results = (ArrayList) getIDsOfClass(Sample.class, key, "sampleID");
                        // if not, add to DB warning list
                        if (results == null || results.size() == 0) {
                            badSample.add(line);
                            continue;
                        }
                        parentSampleID = results.get(0);

                        //Parent Container name
                        key.clear();
                        key.put("containerName", "'" + lineAr[10] + "'");
                        results = (ArrayList) getIDsOfClass(Container.class, key, "containerID");
                        // if exists, add to DB warning list
                        if (results == null || results.size() == 0) {
                            badContainer.add(line);
                            continue;
                        }
                        parentContainerID = results.get(0);
                        //Parent Row
                        String parRowStr = null;
                        try {
                            parentRow = Integer.parseInt(lineAr[11]);
                        } catch (NumberFormatException e) {
                            parRowStr = Util.letterToNum(lineAr[11]);
                            // if failed to change it to a number, it's bad
                            if (rowStr.equals(lineAr[11])) {
                                badRow.add(line);
                                continue;
                            }
                        }
                        try {
                            parentRow = Integer.parseInt(parRowStr);
                        } catch (NumberFormatException e) {
                            badRow.add(line);
                            continue;
                        }
                        //Parent Column
                        String parColStr = null;
                        try {
                            parentColumn = Integer.parseInt(lineAr[12]);
                        } catch (NumberFormatException e) {
                            parColStr = Util.letterToNum(lineAr[12]);
                            // if failed to change it to a number, it's bad
                            if (parColStr.equals(lineAr[12])) {
                                badCol.add(line);
                                continue;
                            }
                        }
                        try {
                            parentColumn = Integer.parseInt(parColStr);
                        } catch (NumberFormatException e) {
                            badCol.add(line);
                            continue;
                        }

                        // check if parent exists
                        key.clear();
                        key.put("containerID", parentContainerID);
                        key.put("sampleID", parentSampleID);
                        key.put("row", parentRow);
                        key.put("column", parentColumn);
                        results = (ArrayList) getIDsOfClass(ContainerContent.class, key, "containerContentsID");
                        // if doesn't exist, add to DB warning list unless -2 or -3
                        if (results == null || results.size() == 0) {
                            badParent.add(line);
                            continue;
                        }
                        parentID = results.get(0);
                    }

                    //Contaminated
                    if (lineAr[13].length() == 0) {
                        missingValues.add(line);
                        continue;
                    }
                    if (lineAr[13].equalsIgnoreCase("0")) {
                        contaminated = "2";
                    }
                    if (lineAr[13].equalsIgnoreCase("y")) {
                        contaminated = "1";
                    }
                    if (lineAr[13].equalsIgnoreCase("n")) {
                        contaminated = "0";
                    }
                    //Quantification Date
                    if (lineAr[14].length() == 0) {
                        badDate.add(line);
                        continue;
                    }
                    if (lineAr[14].equalsIgnoreCase("0")) {
                        quantificationDate = null;
                    } else {
                        quantificationDate = getDate(lineAr[14]);
                    }
                    //Amplification Date
                    if (lineAr[14].length() == 0) {
                        badDate.add(line);
                        continue;
                    }
                    if (lineAr[15].equalsIgnoreCase("0")) {
                        amplificationDate = null;
                    } else {
                        amplificationDate = getDate(lineAr[15]);
                    }
                    //Comments
                    if (lineAr[16].length() == 0) {
                        missingValues.add(line);
                        continue;
                    }
                    if (lineAr[16].equalsIgnoreCase("0")) {
                        comments = "";
                    } else {
                        comments = lineAr[16];
                    }

                    // if all is ok, save subject details to be added
                    HashMap ccHash = new HashMap(NUMBEROFCONTENTFIELDS);
                    ccHash.put("containerID", containerID);
                    ccHash.put("row", row);
                    ccHash.put("column", column);
                    ccHash.put("sampleID", sampleID);
                    ccHash.put("barcode", barcode);
                    ccHash.put("materialTypeID", materialTypeID);
                    ccHash.put("volume", volume);
                    ccHash.put("concentration", concentration);
                    ccHash.put("dilution", dilution);
                    ccHash.put("parentID", parentID);
                    ccHash.put("contaminated", contaminated);
                    ccHash.put("quantificationDate", quantificationDate);
                    ccHash.put("amplificationDate", amplificationDate);
                    ccHash.put("comments", comments);
                    goodContents.add(ccHash);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
        contentsToCreate = goodContents;
        Object[] arr = {missingValues, alreadyExists, dupContents, badContainer, badRow, badCol, badVol, badConcen, badDilution, badParent, badSample, badDate, badMaterialType, goodContents.size()};
        return arr;
    }

    public int addContents(UserHttpSess session) throws Exception {
        Iterator iter = contentsToCreate.iterator();
        HashMap samp = new HashMap(NUMBEROFCONTENTFIELDS);
        MaterialType materialType = null;
        ContainerContent parent = null;
        Container container = null;
        Sample sample = null;
        int messageNum = 0;
        // create each subject
        while (iter.hasNext() && messageNum == 0) {
            samp = (HashMap) iter.next();
            materialType = (MaterialType) session.getObjectById(MaterialType.class, samp.get("materialTypeID").toString());
            parent = ((samp.get("parentID") != null) ? (ContainerContent) session.getObjectById(ContainerContent.class, samp.get("parentID").toString()) : null);
            container = (Container) session.getObjectById(Container.class, samp.get("containerID").toString());
            sample = (Sample) session.getObjectById(Sample.class, samp.get("sampleID").toString());
            messageNum = session.addContainerContent("-1", parent,
                    (String) samp.get("contaminated"), container, samp.get("row").toString(),
                    samp.get("column").toString(), sample, samp.get("volume").toString(),
                    samp.get("concentration").toString(), (String) samp.get("dilution"),
                    (String) samp.get("comments"), (Date) samp.get("amplificationDate"),
                    materialType);
        }
        return messageNum;
    }

    /**
     * checks the upload file for containers to make sure every container can be made
     * prepares list of containers to be added from successful lines
     * @param iter
     * @return array of lists detailing errors in file and good containers: {alreadyExists,dupContainers,missingValues,badContainerType,badDate,badFreezer,badMaterialType,badShippedTo,badYN,goodContainers.size()}
     * @throws java.lang.Exception
     */
    public Object[] checkUploadContainerLocationFile(FileItemIterator iter) throws Exception {
        // reset list of good subjects
        containersToEdit.clear();

        // get input type (need this before parsing the file)
        BufferedReader readerParam = null;
        InputStream stream = null;

        ArrayList<String> results = new ArrayList();
        ArrayList<String> containers = new ArrayList();
        ArrayList<String> dupContainers = new ArrayList();
        ArrayList<String> missingValues = null;
        ArrayList<String> doesNotExists = null;
        ArrayList<String> notInteger = null;
        ArrayList<String> badFreezer = null;
        ArrayList<HashMap> goodContainers = null;
        HashMap key = new HashMap();

        while (iter.hasNext()) {
            FileItemStream item = iter.next();
            String name = item.getFieldName();
//            System.out.println("checkUploadFile name:" + name + " item" + item.getName());
            stream = item.openStream();
//            System.out.println("checkUploadFile " + name);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));


            reader = new BufferedReader(new InputStreamReader(stream));
            String line = null;
            try {
                missingValues = new ArrayList();
                doesNotExists = new ArrayList();
                notInteger = new ArrayList();
                results = new ArrayList();
                badFreezer = new ArrayList();

                goodContainers = new ArrayList();

                String[] lineAr = null;

                String containerName = null;
                String freezerID = null;
                int shelf = -1;
                String location = null;
                // read each line
                while ((line = reader.readLine()) != null) {
//                    System.out.println("LINE:" + line);

                    // get rid of any apostrophes, they mess with DB
                    line.replace("'", "");
                    // check if right format
                    line.trim();
                    if (line.length() == 0) {
                        continue;
                    }
                    lineAr = line.split("\t");
                    if (lineAr.length != NUMBEROFCONTAINERLOCATIONFIELDS) {
                        missingValues.add(line);
                        continue;
                    }
                    // get rid of trailing spaces
                    for (int i = 0; i < lineAr.length; i++) {
                        lineAr[i] = lineAr[i].trim();
                    }
                    // shouldn't be any blanks
                    boolean blanks = false;
                    for (int i = 0; i < lineAr.length; i++) {
                        if (lineAr[i].equals("")) {
                            missingValues.add(line);
                            blanks = true;
                        }
                    }
                    if (blanks) {
                        continue;
                    }
                    // check each field

                    //Container Name
                    //Subject ID
                    key.clear();
                    key.put("containerName", "'" + lineAr[0] + "'");
                    results = (ArrayList) getIDsOfClass(Container.class, key, "containerID");
                    // if not, add to DB warning list
                    if (results == null || results.size() != 1) {
                        doesNotExists.add(line);
                        continue;
                    }
                    containerName = lineAr[0];
                    if (containers.contains(containerName)) {
                        dupContainers.add(line);
                        continue;
                    }
                    containers.add(containerName);

                    //Freezer Name
                    key.clear();
                    if (lineAr[1].equalsIgnoreCase("-1")) {
                        freezerID = null;
                    } else {
                        key.put("description", "'" + lineAr[1] + "'");
                        results = (ArrayList) getIDsOfClass(Freezer.class, key, "freezerID");
                        // if not, add to DB warning list
                        if (results == null || results.size() != 1) {
                            badFreezer.add(line);
                            continue;
                        } else {
                            freezerID = String.valueOf(results.get(0));
                        }
                    }

                    //Shelf
                    if (lineAr[2].length() == 0) {
                        missingValues.add(line);
                        continue;
                    }
                    if (lineAr[2].equalsIgnoreCase("-1")) {
                        shelf = -1;
                    } else {
                        try {
                            shelf = new Integer(lineAr[2]);
                        } catch (NumberFormatException e) {
                            notInteger.add(line);
                            continue;
                        }
                    }

                    //Location Details
                    if (lineAr[3].length() == 0) {
                        missingValues.add(line);
                        continue;
                    }
                    if (lineAr[3].equalsIgnoreCase("-1")) {
                        location = null;
                    } else {
                        location = lineAr[3];
                    }

                    // if all is ok, save subject details to be added
                    HashMap contHash = new HashMap(NUMBEROFCONTAINERLOCATIONFIELDS);
                    contHash.put("containerName", containerName);
                    contHash.put("freezer", freezerID);
                    contHash.put("shelf", shelf);
                    contHash.put("location", location);
                    goodContainers.add(contHash);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
        containersToEdit = goodContainers;
        Object[] arr = {doesNotExists, dupContainers, missingValues, badFreezer, goodContainers.size(), notInteger};
        return arr;
    }

    /**
     * checks the upload file for containers to make sure every container can be made
     * prepares list of containers to be added from successful lines
     * @param iter
     * @return array of lists detailing errors in file and good containers: {alreadyExists,dupContainers,missingValues,badContainerType,badDate,badFreezer,badMaterialType,badShippedTo,badYN,goodContainers.size()}
     * @throws java.lang.Exception
     */
    public Object[] checkUploadContainerFile(FileItemIterator iter) throws Exception {
        // reset list of good subjects
        containersToCreate.clear();

        // get input type (need this before parsing the file)
        BufferedReader readerParam = null;
        InputStream stream = null;

        ArrayList<String> results = new ArrayList();
        ArrayList<String> containers = new ArrayList();
        ArrayList<String> dupContainers = new ArrayList();
        ArrayList<String> missingValues = null;
        ArrayList<String> alreadyExists = null;
        ArrayList<String> badContainerType = null;
        ArrayList<String> badFreezer = null;
        ArrayList<String> badShippedTo = null;
        ArrayList<String> badDate = null;
        ArrayList<String> badYN = null;
        ArrayList<HashMap> goodContainers = null;
        HashMap key = new HashMap();

        while (iter.hasNext()) {
            FileItemStream item = iter.next();
            String name = item.getFieldName();
//            System.out.println("checkUploadFile name:" + name + " item" + item.getName());
            stream = item.openStream();
//            System.out.println("checkUploadFile " + name);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));


            reader = new BufferedReader(new InputStreamReader(stream));
            String line = null;
            try {
                missingValues = new ArrayList();
                alreadyExists = new ArrayList();
                results = new ArrayList();
                badContainerType = new ArrayList();
                badFreezer = new ArrayList();
                badShippedTo = new ArrayList();
                badDate = new ArrayList();
                badYN = new ArrayList();
                goodContainers = new ArrayList();

                String[] lineAr = null;

                String containerName = null;
                String containerTypeID = null;
                String freezerID = null;
                int shelf = -1;
                String discarded = null;
                String shippedOut = null;
                Date shippedDate = null;
                String shippedToID = null;
                String stock = null;
                String valid = null;
                String containerAlias = null;
                Date date = null;
                String shipmentName = null;
                String initials = null;
                String location = null;
                String comments = null;
                // read each line
                while ((line = reader.readLine()) != null) {
//                    System.out.println("LINE:" + line);

                    // get rid of any apostrophes, they mess with DB
                    line.replace("'", "");
                    // check if right format
                    line.trim();
                    if (line.length() == 0) {
                        continue;
                    }
                    lineAr = line.split("\t");
                    if (lineAr.length != NUMBEROFCONTAINERFIELDS) {
                        missingValues.add(line);
                        continue;
                    }
                    // get rid of trailing spaces
                    for (int i = 0; i < lineAr.length; i++) {
                        lineAr[i] = lineAr[i].trim();
                    }
                    // shouldn't be any blanks
                    boolean blanks = false;
                    for (int i = 0; i < lineAr.length; i++) {
                        if (lineAr[i].equals("")) {
                            missingValues.add(line);
                            blanks = true;
                        }
                    }
                    if (blanks) {
                        continue;
                    }
                    // check each field

                    //Container Name
                    //Subject ID
                    key.clear();
                    key.put("containerName", "'" + lineAr[0] + "'");
                    results = (ArrayList) getIDsOfClass(Container.class, key, "containerID");
                    // if not, add to DB warning list
                    if (results != null && results.size() != 0) {
                        alreadyExists.add(line);
                        continue;
                    }
                    containerName = lineAr[0];
                    if (containers.contains(containerName)) {
                        dupContainers.add(line);
                        continue;
                    }
                    containers.add(containerName);

                    //Container Alias
                    if (lineAr[1].length() == 0) {
                        missingValues.add(line);
                        continue;
                    }
                    if (lineAr[1].equalsIgnoreCase("0")) {
                        containerAlias = null;
                    } else {
                        containerAlias = lineAr[1];
                    }

                    //Initials of Plate Maker
                    if (lineAr[2].length() == 0) {
                        missingValues.add(line);
                        continue;
                    }
                    if (lineAr[2].equalsIgnoreCase("0")) {
                        initials = null;
                    } else {
                        initials = lineAr[2];
                    }
                    //Date on Plate
                    if (lineAr[3].length() == 0) {
                        badDate.add(line);
                        continue;
                    }
                    if (lineAr[3].equalsIgnoreCase("0")) {
                        date = null;
                    } else {
                        date = getDate(lineAr[3]);
                    }

                    //Container Type
                    key.clear();
                    key.put("description", "'" + lineAr[4] + "'");
                    results = (ArrayList) getIDsOfClass(ContainerType.class, key, "containerTypeID");
                    // if not, add to DB warning list
                    if (results == null || results.size() != 1) {
                        badContainerType.add(line);
                        continue;
                    } else {
                        containerTypeID = String.valueOf(results.get(0));
                    }

                    //Stock
                    if (lineAr[5].length() == 0) {
                        missingValues.add(line);
                        continue;
                    } else if (lineAr[5].equalsIgnoreCase("y")) {
                        stock = "1";
                    } else if (lineAr[5].equalsIgnoreCase("n")) {
                        stock = "0";
                    } else {
                        badYN.add(line);
                        continue;
                    }

                    //Valid
                    if (lineAr[6].length() == 0) {
                        missingValues.add(line);
                        continue;
                    } else if (lineAr[6].equalsIgnoreCase("y")) {
                        valid = "1";
                    } else if (lineAr[6].equalsIgnoreCase("n")) {
                        valid = "0";
                    } else {
                        badYN.add(line);
                        continue;
                    }
                    //Freezer Name
                    key.clear();
                    if (lineAr[7].equalsIgnoreCase("-1")) {
                        freezerID = null;
                    } else {
                        key.put("description", "'" + lineAr[7] + "'");
                        results = (ArrayList) getIDsOfClass(Freezer.class, key, "freezerID");
                        // if not, add to DB warning list
                        if (results == null || results.size() != 1) {
                            badFreezer.add(line);
                            continue;
                        } else {
                            freezerID = String.valueOf(results.get(0));
                        }
                    }

                    //Shelf
                    if (lineAr[8].length() == 0) {
                        missingValues.add(line);
                        continue;
                    }
                    if (lineAr[8].equalsIgnoreCase("-1")) {
                        shelf = -1;
                    } else {
                        shelf = new Integer(lineAr[8]);
                    }

                    //Location Details
                    if (lineAr[9].length() == 0) {
                        missingValues.add(line);
                        continue;
                    }
                    if (lineAr[9].equalsIgnoreCase("-1")) {
                        location = null;
                    } else {
                        location = lineAr[9];
                    }

                    //Discarded
                    if (lineAr[10].length() == 0) {
                        missingValues.add(line);
                        continue;
                    } else if (lineAr[10].equalsIgnoreCase("y")) {
                        discarded = "1";
                    } else if (lineAr[10].equalsIgnoreCase("n")) {
                        discarded = "0";
                    } else {
                        badYN.add(line);
                        continue;
                    }

                    //Shipped Out
                    if (lineAr[11].length() == 0) {
                        missingValues.add(line);
                        continue;
                    } else if (lineAr[11].equalsIgnoreCase("y")) {
                        shippedOut = "1";
                    } else if (lineAr[11].equalsIgnoreCase("n")) {
                        shippedOut = "0";
                    } else if (lineAr[11].equals("r")) {
                        shippedOut = "2";
                    } else {
                        badYN.add(line);
                        continue;
                    }

                    //Shipment Name
                    if (lineAr[12].length() == 0) {
                        missingValues.add(line);
                        continue;
                    }
                    if (lineAr[12].equalsIgnoreCase("0")) {
                        shipmentName = null;
                    } else {
                        shipmentName = lineAr[12];
                    }

                    //Ship Date
                    if (lineAr[13].length() == 0) {
                        badDate.add(line);
                        continue;
                    }
                    if (lineAr[13].equalsIgnoreCase("0")) {
                        shippedDate = null;
                    } else {
                        shippedDate = getDate(lineAr[13]);
                    }

                    //Shipped To
                    if (lineAr[14].length() == 0) {
                        badShippedTo.add(line);
                        continue;
                    }
                    if (lineAr[14].equalsIgnoreCase("0")) {
                        shippedToID = null;
                    } else {
                        key.clear();
                        key.put("description", "'" + lineAr[14] + "'");
                        results = (ArrayList) getIDsOfClass(ShippedTo.class, key, "shippedToID");
                        // if not, add to DB warning list
                        if (results == null || results.size() != 1) {
                            badShippedTo.add(line);
                            continue;
                        } else {
                            shippedToID = String.valueOf(results.get(0));
                        }
                    }

                    //Comments
                    if (lineAr[15].length() == 0) {
                        missingValues.add(line);
                        continue;
                    }
                    if (lineAr[15].equalsIgnoreCase("0")) {
                        comments = "";
                    } else {
                        comments = lineAr[15];
                    }

                    // if all is ok, save subject details to be added
                    HashMap contHash = new HashMap(NUMBEROFCONTAINERFIELDS);
                    contHash.put("containerName", containerName);
                    contHash.put("containerType", containerTypeID);
                    contHash.put("freezer", freezerID);
                    contHash.put("shelf", shelf);
                    contHash.put("discarded", discarded);
                    contHash.put("shippedOut", shippedOut);
                    contHash.put("shippedDate", shippedDate);
                    contHash.put("shippedTo", shippedToID);
                    contHash.put("shipmentName", shipmentName);
                    contHash.put("stock", stock);
                    contHash.put("valid", valid);
                    contHash.put("containerAlias", containerAlias);
                    contHash.put("date", date);
                    contHash.put("initials", initials);
                    contHash.put("location", location);
                    contHash.put("comments", comments);
                    goodContainers.add(contHash);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
        containersToCreate = goodContainers;
        Object[] arr = {alreadyExists, dupContainers, missingValues, badContainerType, badDate, badFreezer, badShippedTo, badYN, goodContainers.size()};
        return arr;
    }

    public int addContainers(UserHttpSess session) throws Exception {
        Iterator iter = containersToCreate.iterator();
        HashMap cont = new HashMap(NUMBEROFCONTAINERFIELDS);
        ContainerType contType = null;
        Freezer freezer = null;
        ShippedTo shippedTo = null;
        int messageNum = 0;
        // create each container
        while (iter.hasNext() && messageNum == 0) {
            cont = (HashMap) iter.next();
            contType = (cont.get("containerType") != null) ? (ContainerType) session.getObjectById(ContainerType.class, cont.get("containerType").toString()) : null;
            freezer = (cont.get("freezer") != null) ? (Freezer) session.getObjectById(Freezer.class, (String) cont.get("freezer")) : null;
            messageNum = session.addContainer("-1", cont.get("containerName").toString(),
                    contType, freezer, cont.get("shelf").toString(),
                    (String) cont.get("discarded"), (String) cont.get("comments"),
                    (String) cont.get("stock"), (String) cont.get("valid"),
                    (String) cont.get("containerAlias"), null, (String) cont.get("initials"),
                    (Date) cont.get("date"), (String) cont.get("location"), (String) cont.get("barcode"));
            shippedTo = (cont.get("shippedTo") != null) ? (ShippedTo) session.getObjectById(ShippedTo.class, (String) cont.get("shippedTo")) : null;
            if (((String) cont.get("shippedOut")).equals("1")) {
                session.addShipment("-1", (String) cont.get("shipmentName"), (Date) cont.get("shippedDate"), shippedTo, (String) cont.get("shippedOut"), null);
                session.addShipCont("-1", session.getCurrentShipment(), session.getCurrentContainer());
            }
            if (((String) cont.get("shippedOut")).equals("2")) {
                session.addShipment("-1", "(Dummy Shipment) " + (String) cont.get("shipmentName"), (Date) cont.get("shippedDate"), shippedTo, "1", "Dummy shipment for returning new containers");
                session.addShipCont("-1", session.getCurrentShipment(), session.getCurrentContainer());
                session.addShipment("-1", (String) cont.get("shipmentName"), (Date) cont.get("shippedDate"), shippedTo, (String) cont.get("shippedOut"), null);
                session.addShipCont("-1", session.getCurrentShipment(), session.getCurrentContainer());
            }
        }
        return messageNum;
    }

    public int editContainers(UserHttpSess session) throws Exception {
        Iterator iter = containersToEdit.iterator();
        HashMap cont = new HashMap(NUMBEROFCONTAINERLOCATIONFIELDS);
        Freezer freezer = null;
        int messageNum = 0;
        // create each container
        while (iter.hasNext() && messageNum == 0) {
            cont = (HashMap) iter.next();
            freezer = (cont.get("freezer") != null) ? (Freezer) session.getObjectById(Freezer.class, (String) cont.get("freezer")) : null;
            messageNum = session.updateContainerLocation(cont.get("containerName").toString(), freezer, cont.get("shelf").toString(), cont.get("location").toString());
        }
        return messageNum;
    }

    /**
     * check file to upload
     * @param iter lines of file to upload
     * @return array of arrays to warn about bad lines and list good lines
     *          {badFormat,notInDB,moreThanOneMatch,addTolist,inputType};
     * @throws java.lang.Exception
     */
    public Object[] checkUploadListFile(FileItemIterator iter) throws Exception {


        // get input type (need this before parsing the file)
        BufferedReader readerParam = null;
        InputStream stream = null;
        String inputType = null;

        ArrayList<String> results = new ArrayList();
        ArrayList<String> badFormat = null;
        ArrayList<String> notInDB = null;
        ArrayList<String> moreThanOneMatch = null;
        ArrayList<String> addTolist = null;
        HashMap key = new HashMap();

        // need the typeToLoad value before processing file
        while (iter.hasNext()) {
            FileItemStream item = iter.next();
            String name = item.getFieldName();
//            System.out.println("checkUploadFile name:" + name + " item" + item.getName());
            stream = item.openStream();
            if (name != null && name.length() > 0 && name.equals("typeToLoad")) {
                readerParam = new BufferedReader(new InputStreamReader(stream));
                try {
                    inputType = readerParam.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (name != null && name.length() > 0 && name.equals("fileToLoad") && item.getName().length() > 0) {
//                System.out.println("checkUploadFile " + name);
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));


                reader = new BufferedReader(new InputStreamReader(stream));
                String line = null;
                try {
                    badFormat = new ArrayList();
                    results = new ArrayList();
                    notInDB = new ArrayList();
                    moreThanOneMatch = new ArrayList();
                    addTolist = new ArrayList();
                    String[] lineAr = null;
                    String cohort = null;
                    // read each line
                    while ((line = reader.readLine()) != null) {
//                        System.out.println("LINE:" + line);

                        if (inputType.equals("subjects")) {

                            // check if right format
                            line.trim();
                            if (line.length() == 0) {
                                continue;
                            }
                            //check that the first 2 letters are Az or first 4 are SAGE is in the right place

                            lineAr = line.split("\t");
                            if (lineAr.length != 2) {
                                badFormat.add(line);
                                continue;
                            }
                            if (lineAr[0].length() < 2) {
                                badFormat.add(line);
                                continue;
                            }
                            cohort = null;
                            if (lineAr[0].equalsIgnoreCase("Az")) {
                                cohort = "3";
                            } else if (lineAr[0].equalsIgnoreCase("CAPPS")) {
                                cohort = "3";
                            } else if (lineAr[0].equalsIgnoreCase("SAGE")) {
                                cohort = "4";
                            } else if (lineAr[0].equalsIgnoreCase("FA")) {
                                cohort = "11";
                            } else if (lineAr[0].equalsIgnoreCase("NFA")) {
                                cohort = "12";
                            } else {
                                // if not, add to format warning list
                                badFormat.add(line);
                                continue;
                            }
                            // check if exists in DB
                            key.clear();
                            key.put("cohort.cohortID", cohort);
                            key.put("subjectName", "'" + lineAr[1] + "'");
                            String tmp = lineAr[1];
                            results = (ArrayList) getIDsOfClass(Subject.class, key, "subjectID");
                            // if not, add to DB warning list
                            if (results == null || results.size() == 0) {
                                notInDB.add(line);
                                continue;
                            } else if (results.size() > 1) {
                                moreThanOneMatch.add(line);
                                continue;
                            } else {
                                // if it does, add db ID to list to build new shopping list
                                addTolist.add(results.get(0));
                            }
                        } else if (inputType.equals("samples")) {
                            // check if right format
                            line.trim();
                            if (line.length() == 0) {
                                continue;
                            }
                            if (line.split("\t").length > 1) {
                                badFormat.add(line);
                                continue;
                            }
                            if (line.length() < 6) {
                                badFormat.add(line);
                                continue;
                            }
                            //check that the first 2 letters are Az or first 4 are SAGE is in the right place
                            if (!(line.indexOf("Az") == 0 || line.indexOf("SAGE") == 0)) {
                                // if not, add to format warning list
                                badFormat.add(line);
                                continue;
                            }
                            // check if exists in DB
                            key.clear();
                            key.put("sampleName", "'" + line + "'");
                            results = (ArrayList) getIDsOfClass(Sample.class, key, "sampleID");
                            // if not, add to DB warning list
                            if (results == null || results.size() == 0) {
                                notInDB.add(line);
                                continue;
                            } else if (results.size() > 1) {
                                moreThanOneMatch.add(line);
                                continue;
                            } else {
                                // if it does, add db ID to list to build new shopping list
                                addTolist.add(results.get(0));
                            }
                        } else if (inputType.equals("containers")) {
                            // check if right format
                            line.trim();
                            if (line.length() == 0) {
                                continue;
                            }
                            if (line.split("\t").length > 1) {
                                badFormat.add(line);
                                continue;
                            }
                            // check if exists in DB
                            key.clear();
                            key.put("containerName", "'" + line + "'");
                            results = (ArrayList) getIDsOfClass(Container.class, key, "containerID");
                            // if not, add to DB warning list
                            if (results == null || results.size() == 0) {
                                notInDB.add(line);
                                continue;
                            } else if (results.size() > 1) {
                                moreThanOneMatch.add(line);
                                continue;
                            } else {
                                // if it does, add db ID to list to build new shopping list
                                addTolist.add(results.get(0));
                            }
                        } else if (inputType.equals("contents")) {
                            // check if right format
                            line.trim();
                            if (line.length() == 0) {
                                continue;
                            }
                            lineAr = line.split("\t");
                            if (lineAr.length != 4) {
                                badFormat.add(line);
                                continue;
                            }
                            // check if exists in DB
                            key.clear();
                            key.put("sample.sampleName", "'" + lineAr[0] + "'");
                            key.put("container.containerName", "'" + lineAr[1] + "'");
                            key.put("row", Util.letterToNum(lineAr[2]));
                            key.put("column", lineAr[3]);
                            results = (ArrayList) getIDsOfClass(ContainerContent.class, key, "containerContentsID");
                            // if not, add to DB warning list
                            if (results == null || results.size() == 0) {
                                notInDB.add(line);
                                continue;
                            } else if (results.size() > 1) {
                                moreThanOneMatch.add(line);
                                continue;
                            } else {
                                // if it does, add db ID to list to build new shopping list
                                addTolist.add(results.get(0));
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }


        Object[] arr = {badFormat, notInDB, moreThanOneMatch, addTolist, inputType};
        return arr;
    }

    public ArrayList getIDsOfClass(Class c1, HashMap keys, String fieldToGet) {
        StringBuffer where = new StringBuffer();
        if (!keys.isEmpty()) {
            where.append(" where ");
        }
        Iterator iter = keys.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry pair = (Map.Entry) iter.next();
            //System.out.println(pair.getKey() + " = " + pair.getValue());
            where.append(pair.getKey().toString() + " = " + pair.getValue());
            if (iter.hasNext()) {
                where.append(" and ");
            }
        }
        try {
            Query q = UserHttpSess.hibSess.createQuery("select " + fieldToGet + " from " + c1.getName() + " tbl" + where);
            return (ArrayList) q.list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

        public ArrayList getIDsOfClassCross(Class c2, Class c1, HashMap keys, String fieldToGet) {
        StringBuffer where = new StringBuffer();
        if (!keys.isEmpty()) {
            where.append(" where ");
        }
        Iterator iter = keys.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry pair = (Map.Entry) iter.next();
            //System.out.println(pair.getKey() + " = " + pair.getValue());
            where.append(pair.getKey().toString() + " = " + pair.getValue());
            if (iter.hasNext()) {
                where.append(" and ");
            }
        }
        try {
            Query q = UserHttpSess.hibSess.createQuery("select " + fieldToGet + " from " + c1.getName() + " tbl ," + c2.getName() + " tbl2 " + where);
            return (ArrayList) q.list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    /**
     * makes date from yyyy-mm-dd string, returns null if bad date
     * @param dateStr
     * @return
     */
    public Date getDate(String dateStr) {
        Date myDate = null;
        String[] fields = dateStr.split("-");
        if (fields.length != 3) {
            return null;
        }
        try {
            int yy = Integer.parseInt(fields[0]);
            if (yy > 1900 && yy < 2100) {
                int mm = Integer.parseInt(fields[1]);
                if (mm > 0 && mm < 13) {
                    int dd = Integer.parseInt(fields[2]);
                    if (dd > 0 & dd < 32) {
                        Calendar myCal = Calendar.getInstance();
                        myCal.set(yy, mm - 1, dd);
                        myDate = myCal.getTime();
                    }
                }
            }
        } catch (Exception ex) {
            return null;
        }
        return myDate;
    }

    public Object[] checkBeadManager(FileItemIterator iter) throws Exception {
        FileItemStream item = null;
        Object[] errors = new Object[4];
        while (iter.hasNext()) {
            item = iter.next();
            if (item.getFieldName().equals("snpToLoad")) {
                errors[0] = checkBeadSNPFile(item);
                //Object[] arr = {missingValues, goodSNP.size()};
            } else if (item.getFieldName().equals("subjectToLoad")) {
                errors[1] = checkBeadSubjectFile(item);
                //Object[] arr = {alreadyExists, dupSubjects, missingValues, badCohort, badEthnicity, goodSubjects.size()};
            } else if (item.getFieldName().equals("sampleToLoad")) {
                errors[2] = checkBeadSampleFile(item);
                //Object[] arr = {missingValues, badSampleType, badSubject, badContainerType, badConcVol, badMaterialType, goodSamples.size(), goodContainers.size, goodContents.size};
            } else if (item.getFieldName().equals("genotypeToLoad")) {
                errors[3] = checkBeadGenotype(item);
                //Object[] arr = {missingValues, badSNPs, badContent, goodGenotypes.size()};
            } else {
                getGenotypingRun(item);
            }
        }
        return errors;
    }

    public int addBeadManager(UserHttpSess session) throws Exception {
        addSNP(session);
        addContainers(session);
        addSubjects(session);
        addBeadSamples(session);
        addBeadContents(session);
        addBeadGenotypes(session);
        return 0;
    }

    public Object[] checkBeadSNPFile(FileItemStream item) throws Exception {
        // reset list of good subjects
        this.snpsToCreate.clear();

        // get input type (need this before parsing the file)
        InputStream stream = null;

        ArrayList<String> missingValues = null;
        ArrayList<String> alreadyExists = null;
        ArrayList<HashMap> goodSNP = null;

//        System.out.println("checkUploadFile name:" + name + " item" + item.getName());
        stream = item.openStream();
//        System.out.println("checkUploadFile " + name);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));


        reader = new BufferedReader(new InputStreamReader(stream));
        String line = null;
        try {
            missingValues = new ArrayList();
            alreadyExists = new ArrayList();
            goodSNP = new ArrayList();

            String[] lineAr = null;

            //Read in from file
            String snpRS = null;
            String snpPos = null;
            String snpChromo = null;

            //Not read in from file
            String snpID = "-1";
            String snpGeneID = "1"; //Unknown gene
            String snpFxn_Class = null;
            String snpMajorAllele = null;
            String snpMinorAllele = null;
            String snpStrand = " ";

            //skip first line for table headers
            reader.readLine();
            // read each line
            while ((line = reader.readLine()) != null) {
//                System.out.println("LINE:" + line);

                // get rid of any apostrophes, they mess with DB
                line.replace("'", "");
                // check if right format
                line.trim();
                if (line.length() == 0) {
                    continue;
                }
                lineAr = line.split(",");

                // shouldn't be any blanks
                boolean blanks = false;
                for (int i = SNPINFOSTARTBEADFILE; i < SNPINFOENDBEADFILE; i++) {
                    //get rid of trailing spaces
                    lineAr[i] = lineAr[i].trim();
                    if (lineAr[i].equals("")) {
                        missingValues.add(line);
                        blanks = true;
                    }
                }
                if (blanks) {
                    continue;
                } else {
                    snpRS = lineAr[4];
                    snpChromo = lineAr[5];
                    snpPos = lineAr[6];
                }
                if (snpRS.indexOf("rs")!=0){
                    snpRS = "rs" + snpRS;
                }
                HashMap tmpHash = new HashMap();
                tmpHash.put("rsNumber", "'" + snpRS + "'");
                ArrayList tmpArr = this.getIDsOfClass(Snp.class, tmpHash, "snpID");
                if (tmpArr!=null && !tmpArr.isEmpty()){
                    alreadyExists.add(line);
                    continue;
                }


                // if all is ok, save subject details to be added
                HashMap snpHash = new HashMap(NUMBEROFSNPFIELDS);
                snpHash.put("snpID", snpID);
                snpHash.put("chromosome", snpChromo);
                snpHash.put("pos", snpPos);
                snpHash.put("geneID", snpGeneID);
                snpHash.put("fxn_Class", snpFxn_Class);
                snpHash.put("rsNumber", snpRS);
                snpHash.put("majorAllele", snpMajorAllele);
                snpHash.put("minorAllele", snpMinorAllele);
                snpHash.put("strand", snpStrand);

                goodSNP.add(snpHash);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        snpsToCreate = goodSNP;
        Object[] arr = {missingValues, alreadyExists, goodSNP.size()};
        return arr;
    }

    public int addSNP(UserHttpSess session) throws Exception {
        SnpSetLookUp snpSetLookUp = null;
        Iterator iter = snpsToCreate.iterator();
        HashMap cont = new HashMap(NUMBEROFSNPFIELDS);
        int messageNum = 0;
        // create each container
        while (iter.hasNext() && messageNum == 0) {
            cont = (HashMap) iter.next();
            String snpRS = (String) cont.get("rsNumber");
            HashSet tmpHSet = new HashSet();
            tmpHSet.add(snpRS);
            String geneID = getNCBIGeneList(tmpHSet, session);
            messageNum = session.addSnp((String) cont.get("snpID"), (String) cont.get("chromosome"), (String) cont.get("pos"), geneID, (String) cont.get("fxn_Class"), snpRS, (String) cont.get("majorAllele"), (String) cont.get("minorAllele"), (String) cont.get("strand"));
            if (messageNum == 0){
                HashMap tmpHash = new HashMap();
                tmpHash.put("name","'Private'");
                snpSetLookUp = (SnpSetLookUp) session.getObjectByUniqueKey(SnpSetLookUp.class, tmpHash);
                if (snpSetLookUp == null) {
                    messageNum = session.addSnpSetLookUp("-1", "Private");
                    snpSetLookUp = session.getCurrentSnpSetLookUp();
                }
                if (messageNum == 0){
                    session.addSnpSet(snpSetLookUp, session.getCurrentSnp());
                }
            }
        }
        return messageNum;
    }

    /**
     * check the upload file for subjects to make sure every subject can be made
     * prepares list of subjects to be added from successful lines
     * @param item
     * @return array of lists detailing errors in file and good subjects: {alreadyExists,missingValues,badCohort,badEthnicity,number of good subjects}
     * @throws java.lang.Exception
     */
    public Object[] checkBeadSubjectFile(FileItemStream item) throws Exception {
        // reset list of good subjects
        subjectsToCreate.clear();

        InputStream stream = null;

        ArrayList<String> results = new ArrayList();
        ArrayList<String> subjects = new ArrayList();
        ArrayList<String> dupSubjects = new ArrayList();
        ArrayList<String> missingValues = null;
        ArrayList<String> alreadyExists = null;
        ArrayList<String> badCohort = null;
        ArrayList<String> badEthnicity = null;
        ArrayList<HashMap> goodSubjects = null;
        HashMap key = new HashMap();
        HashMap localEth = new HashMap();
        HashMap localCoh = new HashMap();

//        System.out.println("checkUploadFile name:" + name + " item" + item.getName());
        stream = item.openStream();
//        System.out.println("checkUploadFile " + name);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));


        reader = new BufferedReader(new InputStreamReader(stream));
        String line = null;
        boolean breakFlag = false;
        try {
            missingValues = new ArrayList();
            alreadyExists = new ArrayList();
            results = new ArrayList();
            badCohort = new ArrayList();
            badEthnicity = new ArrayList();
            goodSubjects = new ArrayList();
            String[] lineAr = null;
            String cohortID = null;
            String subjectName = null;
            String ethnicityID = null;
            String familyName = null;
            String gender = null;
            String consent = null;
            String motherName = null;
            String fatherName = null;
            String comments = null;
            //skip first line (header)
            reader.readLine();
            // read each line
            while ((line = reader.readLine()) != null) {
//                System.out.println("LINE:" + line);

                // get rid of any apostrophes, they mess with DB
                line.replace("'", "");
                // check if right format
                line.trim();
                if (line.length() == 0) {
                    continue;
                }
                lineAr = line.split(",");
                //one extra column, DUP ID optional.
                if (lineAr.length < NUMBEROFSUBJECTFIELDS + 1) {
                    missingValues.add(line);
                    continue;
                }

                boolean blanks = false;
                for (int i = 0; i < lineAr.length; i++) {
                    // get rid of trailing spaces
                    lineAr[i] = lineAr[i].trim();
                    // shouldn't be any blanks
                    if (i == 8 || i==10 | i==3) {
                        continue;
                    }
                    if (lineAr[i].equals("")) {
                        missingValues.add(line);
                        blanks = true;
                    }
                }
                if (blanks) {
                    continue;
                }
                // check each field

                //Cohort
                key.clear();
                key.put("description", "'" + lineAr[0] + "'");
                if ((cohortID = (String) localCoh.get(lineAr[0])) == null) {
                    results = (ArrayList) getIDsOfClass(Cohort.class, key, "cohortID");
                    if (results == null || results.size() != 1) {
                        badCohort.add(line);
                        continue;
                    } else {
                        cohortID = results.get(0);
                        localCoh.put(lineAr[0], cohortID);
                    }
                }

                //Subject ID
                key.clear();
                key.put("subjectName", "'" + lineAr[1] + "'");
                key.put("cohort", cohortID);
                results = (ArrayList) getIDsOfClass(Subject.class, key, "subjectID");
                // if not, add to DB warning list
                if (results != null && !results.isEmpty()) {
                    alreadyExists.add(line);
                    breakFlag = true;
                }
                subjectName = lineAr[1].trim();
                if (subjects.contains(cohortID + "_" + subjectName)) {
                    dupSubjects.add(line);
                    breakFlag = true;
                }
                subjects.add(cohortID + "_" + subjectName);

                //Family ID
                if (lineAr[2].length() == 0) {
                    missingValues.add(line);
                    continue;
                }
                familyName = lineAr[2];

                //Gender
//                if (lineAr[3].length() == 0) {
//                    missingValues.add(line);
//                    continue;
//                }
                if (lineAr[3].toLowerCase().startsWith("m")) {
                    gender = "1";
                } else if (lineAr[3].toLowerCase().startsWith("f")) {
                    gender = "2";
                } else {
                    gender = "0";
                }

                //Consent
                if (lineAr[4].length() == 0) {
                    missingValues.add(line);
                    continue;
                }
                if (lineAr[4].equalsIgnoreCase("y") || lineAr[4].equalsIgnoreCase("1")) {
                    consent = "1";
                } else if (lineAr[4].equalsIgnoreCase("n") || lineAr[4].equalsIgnoreCase("0")) {
                    consent = "0";
                } else {
                    consent = "2";
                }

                //Mother ID
                if (lineAr[5].length() == 0) {
                    missingValues.add(line);
                    continue;
                }
                motherName = lineAr[5];

                //Father ID
                if (lineAr[6].length() == 0) {
                    missingValues.add(line);
                    continue;
                }
                fatherName = lineAr[6];

                //Ethnicity
                key.clear();
                key.put("ethnicity", "'" + lineAr[7] + "'");
                if ((ethnicityID = (String) localEth.get(lineAr[7])) == null) {
                    results = (ArrayList) getIDsOfClass(Ethnicity.class, key, "ethnicityID");
                    if (results == null || results.size() != 1) {
                        badEthnicity.add(line);
                        continue;
                    } else {
                        ethnicityID = results.get(0);
                        localEth.put(lineAr[7], ethnicityID);
                    }
                }
                //Comments
                if (lineAr[8].equalsIgnoreCase("0")) {
                    comments = "";
                } else {
                    comments = lineAr[8];
                }
                if (lineAr[9].equals("")) {
                    missingValues.add(line);
                    continue;
                }
                this.subjectIDStorage.put(lineAr[9], subjectName);
                if (lineAr.length > 10 && !lineAr[10].equals("")) {
                    this.subjectIDStorage.put(lineAr[10], subjectName);
                }
                if (breakFlag) {
                    continue;
                }
                // if all is ok, save subject details to be added
                HashMap subjHash = new HashMap(NUMBEROFSUBJECTFIELDS);
                subjHash.put("cohort", cohortID);
                subjHash.put("name", subjectName);
                subjHash.put("ethnicity", ethnicityID);
                subjHash.put("family", familyName);
                subjHash.put("gender", gender);
                subjHash.put("consent", consent);
                subjHash.put("mother", motherName);
                subjHash.put("father", fatherName);
                subjHash.put("comments", comments);
                goodSubjects.add(subjHash);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        subjectsToCreate = goodSubjects;
        Object[] arr = {alreadyExists, dupSubjects, missingValues, badCohort, badEthnicity, goodSubjects.size()};
        return arr;
    }

    public Object[] checkBeadSampleFile(FileItemStream item) throws Exception {
        // reset list of good subjects
        samplesToCreate.clear();
        containersToCreate.clear();
        contentsToCreate.clear();

        // get input type (need this before parsing the file)
        InputStream stream = null;

        ArrayList<String> results = new ArrayList();
        ArrayList<String> missingValues = null;
        ArrayList<String> badSampleType = null;
        ArrayList<String> badSubject = null;
        ArrayList<String> badConcVol = new ArrayList();
        ArrayList<String> badContainerType = new ArrayList();
        HashMap localSamp = new HashMap();
        HashMap localCont = new HashMap();
        // need the typeToLoad value before processing file
        String name = item.getFieldName();
//        System.out.println("checkUploadFile name:" + name + " item" + item.getName());
        stream = item.openStream();
//        System.out.println("checkUploadFile " + name);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        //CONTAINER VARIABLES
        String freezerID = null;
        int shelf = -1;
        String discarded = "0";
        String shippedOut = "0";
        String shippedDate = null;
        String shippedToID = null;
        String shipmentName = null;
        String stock = "1";
        String containerAlias = null;
        String date = null;
        String initials = null;
        String location = null;
        String containerTypeID = "-1";
        String containerName;

        reader = new BufferedReader(new InputStreamReader(stream));
        String line = null;
        //Sample/Container/Subject Varibles
        String sampleTypeID;
        String sampleName;
        String subjectName;
        String valid = "1";
        String collectionDate = null;
        String extractionDate = null;

        //ContainerContent Variables
        String row;
        int column;
        ArrayList<String> badContVol = new ArrayList();
        ArrayList<String> badMaterialType = new ArrayList();
        String concentration = "-1";
        String volume = "-1";
        String materialTypeID = "-1";
        String barcode = null;
        String dilution = "-1";
        String parentID = null;
        String contaminated = "0";
        String quantificationDate = null;
        String amplificationDate = null;
        String commentsContent = null;


        ArrayList<HashMap> goodContents = new ArrayList();
        ArrayList<HashMap> goodSamples = new ArrayList();
        ArrayList<HashMap> goodContainers = new ArrayList();
        String commentsSample = "";
        String commentsCont = "";
        try {
            missingValues = new ArrayList();
            results = new ArrayList();
            badSampleType = new ArrayList();
            badSubject = new ArrayList();
            String[] lineAr = null;
            String cidrName = null;
            // read each line
            reader.readLine();
            while ((line = reader.readLine()) != null) {
//                System.out.println("LINE:" + line);

                // get rid of any apostrophes, they mess with DB
                line.replace("'", "");
                // check if right format
                line.trim();
                if (line.length() == 0) {
                    continue;
                }
                lineAr = line.split(",");
                if (lineAr.length < MINBEADFIELDSNEEDED) {
                    missingValues.add(line);
                    continue;
                }
                for (int i = 0; i < lineAr.length; i++) {
                    //remove trailing spaces
                    lineAr[i] = lineAr[i].trim();
                }

                if (!lineAr[1].equals("")) {
                    commentsSample = lineAr[1];
                }
                //gets the sampleName
                if (lineAr[4].equals("")) {
                    missingValues.add(line);
                    continue;
                } else {
                    sampleName = lineAr[4];
                }

                //gets the CIDR name
                if (lineAr[3].equals("")) {
                    missingValues.add(line);
                    continue;
                } else {
                    cidrName = lineAr[3];
                    subjectName = (String) subjectIDStorage.get(cidrName);
                    if (subjectName == null) {
                        badSubject.add(line);
                        continue;
                    }
                }

                //gets Sample Type
                String samp = null;
                if (lineAr[10].equals("")) {
                    missingValues.add(line);
                    continue;
                } else if ((sampleTypeID = (String) localSamp.get(lineAr[10])) == null) {
                    samp = lineAr[10];
                    HashMap tmpKey = new HashMap();
                    tmpKey.put("description", "'" + samp + "'");
                    results = this.getIDsOfClass(SampleType.class, tmpKey, "sampleTypeID");
                    if (results == null || results.size() != 1) {
                        badSampleType.add(line);
                        continue;
                    } else {
                        sampleTypeID = results.get(0);
                        localSamp.put(samp, sampleTypeID);
                    }
                }

                //gets sample plate name
                if (lineAr[16].equals("")) {
                    missingValues.add(line);
                    continue;
                } else {
                    containerName = lineAr[16];
                }

                //gets sample well
                if (lineAr[17].equals("")) {
                    missingValues.add(line);
                    continue;
                } else {
                    row = Util.letterToNum(lineAr[17].substring(0, 1));
                    column = Integer.parseInt(lineAr[17].substring(2));
                }
                if (lineAr.length >= 32 && !lineAr[31].equals("")) {
                    try {
                        Integer.parseInt(lineAr[31]);
                    } catch (NumberFormatException e) {
                        badContainerType.add(line);
                        continue;
                    }
                    containerTypeID = lineAr[31];
                }
                if (lineAr.length >= 33 && !lineAr[32].equals("")) {
                    try {
                        Double.parseDouble(lineAr[32]);
                    } catch (NumberFormatException e) {
                        badContVol.add(line);
                        continue;
                    }
                    concentration = lineAr[32];
                    if (concentration.equals("")) {
                        concentration = "-1";
                    }
                }
                if (lineAr.length >= 34 && !lineAr[33].equals("")) {
                    try {
                        Double.parseDouble(lineAr[33]);
                    } catch (NumberFormatException e) {
                        badContVol.add(line);
                        continue;
                    }
                    volume = lineAr[33];
                    if (volume.equals("")) {
                        volume = "0";
                    }
                }
                if (lineAr.length >= 35 && !lineAr[34].equals("")) {
                    try {
                        Integer.parseInt(lineAr[34]);
                    } catch (NumberFormatException e) {
                        badMaterialType.add(line);
                        continue;
                    }
                    materialTypeID = lineAr[34];
                }

                //New Sample
                // if all is ok, save subject details to be added
                HashMap samHash = new HashMap(NUMBEROFSAMPLEFIELDS);
                samHash.put("sampleName", sampleName);
                samHash.put("subjectName", subjectName);
                samHash.put("valid", valid);
                samHash.put("sampleTypeID", sampleTypeID);
                samHash.put("collectionDate", collectionDate);
                samHash.put("extractionDate", extractionDate);
                samHash.put("comments", commentsSample);
                goodSamples.add(samHash);

                //New Container
                HashMap contHash = new HashMap(NUMBEROFCONTAINERFIELDS);
                contHash.put("containerName", containerName);
                contHash.put("containerType", containerTypeID);
                contHash.put("freezer", freezerID);
                contHash.put("shelf", shelf);
                contHash.put("discarded", discarded);
                contHash.put("shippedOut", shippedOut);
                contHash.put("shippedDate", shippedDate);
                contHash.put("shippedTo", shippedToID);
                contHash.put("shipmentName", shipmentName);
                contHash.put("stock", stock);
                contHash.put("valid", valid);
                contHash.put("containerAlias", containerAlias);
                contHash.put("date", date);
                contHash.put("initials", initials);
                contHash.put("location", location);
                contHash.put("comments", commentsCont);
                contHash.put("barcode", barcode);
                goodContainers.add(contHash);

                //New Content
                HashMap ccHash = new HashMap(NUMBEROFCONTENTFIELDS);
                ccHash.put("containerName", containerName);
                ccHash.put("row", row);
                ccHash.put("column", column);
                ccHash.put("sampleName", sampleName);
                ccHash.put("barcode", barcode);
                ccHash.put("materialTypeID", materialTypeID);
                ccHash.put("volume", volume);
                ccHash.put("concentration", concentration);
                ccHash.put("dilution", dilution);
                ccHash.put("parentID", parentID);
                ccHash.put("contaminated", contaminated);
                ccHash.put("quantificationDate", quantificationDate);
                ccHash.put("amplificationDate", amplificationDate);
                ccHash.put("comments", commentsContent);
                goodContents.add(ccHash);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.containersToCreate = goodContainers;
        this.samplesToCreate = goodSamples;
        this.contentsToCreate = goodContents;

        Object[] arr = {missingValues, badSampleType, badSubject, badContainerType, badConcVol, badMaterialType, goodSamples.size(), goodContainers.size(), goodContents.size()};
        return arr;
    }

    public int addBeadSamples(UserHttpSess session) throws Exception {
        Iterator iter = samplesToCreate.iterator();
        HashMap samp = new HashMap(NUMBEROFSAMPLEFIELDS);
        SampleType sampleType = null;
        Subject subject = null;
        int messageNum = 0;
        // create each subject
        while (iter.hasNext() && messageNum == 0) {
            samp = (HashMap) iter.next();
            sampleType = (SampleType) session.getObjectById(SampleType.class, samp.get("sampleTypeID").toString());
            HashMap tmpKey = new HashMap();
            tmpKey.put("subjectName", "'" + samp.get("subjectName") + "'");
            subject = (Subject) session.getObjectByUniqueKey(Subject.class, tmpKey);
            messageNum = session.addSample("-1", samp.get("sampleName").toString(), samp.get("valid").toString(),
                    null, sampleType, subject, (Date) samp.get("collectionDate"), (Date) samp.get("extractionDate"),
                    samp.get("comments").toString());
        }
        return messageNum;
    }

    public int addBeadContents(UserHttpSess session) throws Exception {
        Iterator iter = contentsToCreate.iterator();
        HashMap samp = new HashMap(NUMBEROFCONTENTFIELDS);
        MaterialType materialType = null;
        ContainerContent parent = null;
        Container container = null;
        Sample sample = null;
        int messageNum = 0;
        // create each subject
        while (iter.hasNext() && messageNum == 0) {
            samp = (HashMap) iter.next();
            materialType = (MaterialType) session.getObjectById(MaterialType.class, samp.get("materialTypeID").toString());
            parent = ((samp.get("parentID") != null) ? (ContainerContent) session.getObjectById(ContainerContent.class, samp.get("parentID").toString()) : null);
            HashMap tmpKey = new HashMap();
            tmpKey.put("containerName", "'" + samp.get("containerName") + "'");
            container = (Container) session.getObjectByUniqueKey(Container.class, tmpKey);
            tmpKey.clear();
            tmpKey.put("sampleName", "'" + samp.get("sampleName") + "'");
            sample = (Sample) session.getObjectByUniqueKey(Sample.class, tmpKey);
            messageNum = session.addContainerContent("-1", parent,
                    (String) samp.get("contaminated"), container, samp.get("row").toString(),
                    samp.get("column").toString(), sample, samp.get("volume").toString(),
                    samp.get("concentration").toString(), (String) samp.get("dilution"),
                    (String) samp.get("comments"), (Date) samp.get("amplificationDate"),
                    materialType);
        }
        return messageNum;
    }

    public Object[] checkBeadGenotype(FileItemStream item) {
        //clear residual genotypes
        genotypesToCreate.clear();

        ArrayList<String> badSNPs = new ArrayList();
        ArrayList<String> badContent = new ArrayList();
        ArrayList<String> missingValues = new ArrayList();
        ArrayList<String> acceptedAllele = new ArrayList();
        acceptedAllele.add("A");
        acceptedAllele.add("C");
        acceptedAllele.add("T");
        acceptedAllele.add("G");
        HashMap localCont = new HashMap();
        //initialize the stream for reading
        String line = null;
        InputStream stream = null;
        String[] lineAr = null;
        HashMap tmpHash = new HashMap();
        String name = item.getFieldName();

        //picks the two allele-forwards
        String allele1 = "";
        String allele2 = "";
        String containerContentsID = "";
        String genotypingRunID = "";
        String snpID = "";
        ArrayList<HashMap> goodGenotypes = new ArrayList();
        boolean missing = false;
        try {
//            System.out.println("checkUploadFile name:" + name + " item" + item.getName());
            stream = item.openStream();
//            System.out.println("checkUploadFile " + name);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            //skip the header
            reader.readLine();
            while ((line = reader.readLine()) != null) {
//                System.out.println("Line:" + line);

                //get rid of an apostrophes
                line.replace("'", "");
                //remove trailing spaces
                line.trim();
                if (line.length() == 0) {
                    continue;
                }
                lineAr = line.split(",");
                if (lineAr.length < MINBEADGENOTYPEFIELDSNEEDED) {
                    missingValues.add(line);
                    continue;
                }
                for (int i = 0; i <= MINBEADGENOTYPEFIELDSNEEDED; i++) {
                    //remove trailing spaces
                    lineAr[i] = lineAr[i].trim();
                    //skip checks
                    if (i == 2) {
                        continue;
                    }
                    if (lineAr[i].equals("")) {
                        missingValues.add(line);
                        missing = true;
                        break;
                    }
                }
                if (missing) {
                    missing = false;
                    continue;
                }
                tmpHash.clear();
                String rsNumber = lineAr[0];
                if (rsNumber.indexOf("rs")!=0){
                    rsNumber = "rs"+rsNumber;
                }
                tmpHash.put("rsNumber", "'" + rsNumber + "'");
                ArrayList tmpArr = getIDsOfClass(Snp.class, tmpHash, "snpID");
                snpID = (String) ((tmpArr != null && !tmpArr.isEmpty()) ? tmpArr.get(0) : "");
                if (snpID.equals("")) {
                    badSNPs.add(line);
                    continue;
                }
                if ((containerContentsID = (String) localCont.get(lineAr[1])) == null) {
                    tmpHash.clear();
                    tmpHash.put("sample.sampleName", "'" + lineAr[1] + "'");
                    tmpArr = getIDsOfClass(ContainerContent.class, tmpHash, "containerContentsID");
                    if (tmpArr != null && !tmpArr.isEmpty()) {
                        containerContentsID = (String) tmpArr.get(0);
                        localCont.put(lineAr[1], containerContentsID);
                    }
                }
                if (containerContentsID == null || containerContentsID.equals("")) {
                    badContent.add(line);
                    continue;
                }
                if (acceptedAllele.contains(lineAr[3].toUpperCase()) && acceptedAllele.contains(lineAr[4].toUpperCase())) {
                    allele1 = lineAr[3];
                    allele2 = lineAr[4];
                }
                HashMap genoHash = new HashMap(MINBEADGENOTYPEFIELDSNEEDED);
                genoHash.put("snpID", snpID);
                genoHash.put("containerContentsID", containerContentsID);
                genoHash.put("genotypingRunID", genotypingRunID);
                genoHash.put("allele1", allele1);
                genoHash.put("allele2", allele2);

                goodGenotypes.add(genoHash);
            }
        } catch (IOException e) {
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
            }
        }

        genotypesToCreate = goodGenotypes;
        Object[] arr = {missingValues, badSNPs, badContent, goodGenotypes.size()};
        return arr;

    }

    public int addBeadGenotypes(UserHttpSess session) throws Exception {
        Iterator iter = genotypesToCreate.iterator();
        HashMap geno = new HashMap(MINBEADGENOTYPEFIELDSNEEDED);
        int messageNum = 0;
        //create each genotype
        while (iter.hasNext() && messageNum == 0) {
            geno = (HashMap) iter.next();
            messageNum = session.addGenotype(geno.get("allele1").toString(), geno.get("allele2").toString(), geno.get("containerContentsID").toString(), genotypingRunID, geno.get("snpID").toString());
        }
        return messageNum;
    }

    public void getGenotypingRun(FileItemStream item) throws IOException {
        InputStream stream = item.openStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        genotypingRunID = reader.readLine();


    }

    public String getNCBIGeneList(HashSet rsList, UserHttpSess session) throws Exception {
        HashMap tmpKey = new HashMap();
        tmpKey.put("name", "'Unknown'");
        Gene tmpGene = (Gene) session.getObjectByUniqueKey(Gene.class, tmpKey);
        String unknownGene = null;
        if (tmpGene == null){
            session.addGene("-1", "Unknown", "0", "0", "");
            unknownGene = session.getCurrentGene().getId().toString();
        }
        else {
            unknownGene = tmpGene.getGeneID();
        }

            SNPPosBySnpFromXML sNPPosBySnpFromXML = new SNPPosBySnpFromXML();
            sNPPosBySnpFromXML.setSnpList(new ArrayList(rsList));
            NCBIJobManager.runJob(sNPPosBySnpFromXML); //allows jobs to be run every 3 seconds
        HashSet<String> geneIDList = new HashSet<String>();
        for (Object[] snpInfo : sNPPosBySnpFromXML.getSnpMap().values()) {
            HashSet<String> geneID = (HashSet<String>) snpInfo[0];
            if (geneID != null) {
                geneIDList.addAll(geneID);
            }
        }

        //makes map of geneid to geneinfo String Array
        GeneInformationByEntrezGeneIdFromXML geneInformationByEntrezGeneIdFromXML = new GeneInformationByEntrezGeneIdFromXML();
        geneInformationByEntrezGeneIdFromXML.setGeneIdList(new ArrayList(geneIDList));
        NCBIJobManager.runJob(geneInformationByEntrezGeneIdFromXML); //allows jobs to be run every 3 seconds

        //loop through genes and add them to the db if they are not already in the db
        for (String entrezGeneID : geneInformationByEntrezGeneIdFromXML.getGeneMap().keySet()) {

            try {
                    String[] geneInfo = geneInformationByEntrezGeneIdFromXML.getGeneMap().get(entrezGeneID);
                    if (geneInfo[0].length() > 0) {
                        //it return the gene info
                        session.addGene("-1", geneInfo[0], "0", entrezGeneID, "");
                        if (session.getCurrentGene()!=null){
                            GeneSetLookUp geneSetLookUp = null;
                            int messageNum = 0;
                            HashMap tmpHash = new HashMap();
                            tmpHash.put("name","'Private'");
                            geneSetLookUp = (GeneSetLookUp) session.getObjectByUniqueKey(GeneSetLookUp.class, tmpHash);
                            if (geneSetLookUp == null ) {
                                messageNum = session.addGeneSetLookUp("-1", "Private");
                                geneSetLookUp = session.getCurrentGeneSetLookUp();
                            }
                            if (messageNum == 0){
                                session.addGeneSet(geneSetLookUp, session.getCurrentGene());
                            }
                            return session.getCurrentGene().getId().toString();
                        }
                        return unknownGene;
                    }
            } catch (Exception e) {
            }
        }
        return unknownGene;
    }
}
