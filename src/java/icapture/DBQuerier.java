/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icapture;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;
import icapture.com.UserHttpSess;
import icapture.hibernate.Cohort;
import icapture.hibernate.Subject;
import icapture.hibernate.Sample;
import icapture.hibernate.SampleType;
import icapture.hibernate.ContainerContent;
import icapture.hibernate.Container;

/**
 *
 * @author tvanrossum
 */
public class DBQuerier {

    UserHttpSess userHttpSess = null;

    public static void main(String[] args) {

        UserHttpSess httpSess = new UserHttpSess("c:/temp/log");
        DBQuerier dbQuerier = new DBQuerier(httpSess);
        //dbQuerier.getCNGSampleDataFromDB("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Samples For GWAS\\CNG Samples For Genotyping\\current");
        dbQuerier.getMontrealSampleDataFromDBParentsAsthmaEver("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Samples For GWAS\\Summer2009\\Montreal Samples For GWAS");
        //dbQuerier.getGenomicTubeContentDataFromDB("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Samples For GWAS\\PostSendIssues\\Plated as opposite type");
        //dbQuerier.getSubjectsWithoutWGA("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Samples For GWAS\\WGA GWAS oct2009");
        //dbQuerier.getSamplesWithoutWGA("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Samples For GWAS\\WGA GWAS oct2009");
        //dbQuerier.getContentIDs("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\labFiles\\dealWith\\CAPPS stock tubes move & vol update");
    }

    public DBQuerier(UserHttpSess userHttpSess) {
        this.userHttpSess = userHttpSess;
    }

    private HashMap getCAPPSyr1DNAsamplesStatuses(String fileDir) {
        try {
            //1.) build list of all samples in files
            HashSet<String> sampleList = new HashSet();
            String[] elem = {};
            String lineStr = "";
            String prefix;
            HashMap dnaSubjectStatuses = new HashMap();

            File files = new File(fileDir);
            for (File file : files.listFiles()) {
                if (file.getName().endsWith("CAPPSyr1-DNAsample-reformat.txt")) {
                    //parse it
                    ArrayList<String> fileContents = Tools.readFile(file);
                    for (String line : fileContents) {
                        lineStr = line;
                        elem = line.split("\t");  //split using tab char
                        if (elem.length > 0) {
                            String family = elem[0].trim();
                            String individual = elem[1].trim();
                            String status = elem[2].trim();
                            dnaSubjectStatuses.put("Az" + family + "-" + individual, status);
                        }
                    }
                }
            }

            return dnaSubjectStatuses;
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return null;
    }

    private ArrayList getSampleNamesFromFile(String fileDir, String readFileName, int sampIdCol) {
        try {
            //1.) build list of all samples in files
            ArrayList<String> sampleList = new ArrayList();
            String[] elem = {};
            String lineStr = "";
            String prefix;

            File files = new File(fileDir);
            for (File file : files.listFiles()) {
                if (file.getName().equals(readFileName)) {
                    //parse it
                    ArrayList<String> fileContents = Tools.readFile(file);
                    for (String line : fileContents) {
                        lineStr = line;
                        elem = line.split("\t");  //split using tab char
                        if (elem.length > 0) {
                            String sampleID = elem[sampIdCol].trim();
                            sampleList.add(sampleID);
                        }
                    }
                }
            }

            return sampleList;
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return null;
    }

    private void getCNGSampleDataFromDB(String fileDir) {
        try {
            //1.) build list of all samples in files
            HashSet<String> sampleList = new HashSet();
            String[] elem = {};
            String lineStr = "";
            String prefix;
            List sampCohort = new LinkedList();
            List genotypedAtCNG = new LinkedList();

            FileOutputStream out; // declare a file output object
            PrintStream p; // declare a print stream object
            out = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Samples For GWOS\\CNG Samples For Genotyping\\\\out.txt");
            // Connect print stream to the output stream
            p = new PrintStream(out);

            FileOutputStream err; // declare a file output object
            PrintStream e; // declare a print stream object
            err = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Samples For GWOS\\CNG Samples For Genotyping\\err.txt");
            // Connect print stream to the output stream
            e = new PrintStream(err);

            HashMap cappsDNASubjectStatuses = getCAPPSyr1DNAsamplesStatuses(fileDir);

            File files = new File(fileDir);
            for (File file : files.listFiles()) {
                if (file.getName().endsWith("input.txt")) {
                    //parse it
                    ArrayList<String> fileContents = Tools.readFile(file);
                    sampCohort = new LinkedList();
                    for (String line : fileContents) {
                        lineStr = line;
                        elem = line.split("\t");  //split using tab char
                        if (elem.length > 0) {
                            String cohortName = elem[4].trim();
                            String subjectName = elem[5].trim();
                            String[] ar = {subjectName, cohortName, lineStr};
                            sampCohort.add(ar);
                            //p.println(cohortName + "\t" + sampleName);
                        }
                    }
                } //
                else if (file.getName().endsWith("genotypedAtCNG-reformat.txt")) {
                    //parse it
                    ArrayList<String> fileContents = Tools.readFile(file);
                    for (String line : fileContents) {
                        elem = line.split("\t");  //split using tab char
                        if (elem.length > 0) {
                            String numFamilyID = elem[1].trim();
                            String individualID = elem[2].trim();
                            individualID = (individualID.equalsIgnoreCase("a")) ? "" : "-" + individualID;
                            String cohortSubjectName = null;
                            if (numFamilyID.startsWith("9")) {
                                cohortSubjectName = "Az" + numFamilyID.substring(1) + individualID;
                            } else if (numFamilyID.startsWith("8")) {
                                cohortSubjectName = "SAGE" + numFamilyID.substring(1) + individualID;
                            } else {
                                e.println("Unrecognized cohort: " + numFamilyID);
                            }
                            genotypedAtCNG.add(cohortSubjectName);
                            //p.println(cohortName + "\t" + sampleName);
                        }
                    }
                }
            }



            // get every sample
            Iterator itr = sampCohort.iterator();
            HashMap uniqueSam = new HashMap(2);
            HashMap uniqueSub = new HashMap(2);
            String subjectID = null;
            String subjectName = "";
            while (itr.hasNext()) {
                String[] ar = (String[]) itr.next();
                //if (!subjectName.equals(ar[0])) {
                subjectName = ar[0];
                uniqueSub.put("subjectName", "'" + ar[0] + "'");

                String cohortID = null;
                if (ar[1].equalsIgnoreCase("Az")) {
                    cohortID = "3";
                } else if (ar[1].equalsIgnoreCase("SAGE")) {
                    cohortID = "4";
                }
                if (cohortID != null) {

                    uniqueSub.put("cohort.cohortID", cohortID);
                    Subject subject = (Subject) userHttpSess.getObjectByUniqueKey(Subject.class, uniqueSub);
                    String ethnicityID = subject.getEthnicity().getEthnicityID();
                    if (subject == null) {
                        e.println("No subject for " + ar[0] + ", cohortName = " + ar[1]);
                        continue;
                    } else {
                        subjectID = subject.getSubjectID();
                    }

                    //uniqueSam.put("sampleName", "'" + ar[0] + "'");
                    uniqueSam.put("subject.subjectID", subjectID);
                    List<Sample> samples = userHttpSess.getObjectsOfClass(Sample.class, uniqueSam);
                    Iterator itrSam = samples.iterator();
                    if (samples == null || samples.size() == 0 || samples.isEmpty()) {
                        e.println("No sample for subject " + ar[1] + ar[0]);
                        continue;
                    }
                    while (itrSam.hasNext()) {
                        Sample sample = (Sample) itrSam.next();
                        if (sample != null) {
                            String type = "NULL";
                            String sampleID = sample.getSampleID();
                            String sampleName = sample.getSampleName();
                            if (sample.getSampleType() != null) {
                                type = sample.getSampleType().getDescription();
                            }
                            // GET CONTAINER CONTENTS DATA FOR SAMPLE
                            List ccList = new LinkedList();
                            HashMap cont = new HashMap();
                            cont.put("sample.sampleID", sampleID);
                            ccList = userHttpSess.getObjectsOfClass(ContainerContent.class, cont);
                            if (ccList == null || ccList.size() == 0 || ccList.isEmpty()) {
                                e.println("No container contents for sample " + ar[1] + ar[0] + ", sampleID used=" + sampleID);
                            }
                            // get container name
                            Iterator iter = ccList.iterator();
                            while (iter.hasNext()) {
                                ContainerContent cc = (ContainerContent) iter.next();
                                String containerID = (cc.getContainer() == null) ? "" : cc.getContainer().getContainerID();
                                int contcontainerIDNum = (containerID == null || containerID.equals("")) ? -1 : Integer.valueOf(containerID);

                                String containerType = (cc.getContainer() == null) ? "" : cc.getContainer().getContainerType().getDescription();
                                String containerTypeID = (cc.getContainer() == null) ? "" : cc.getContainer().getContainerType().getContainerTypeID();
                                String containerName = (cc.getContainer() == null) ? "" : cc.getContainer().getcontainerName();
                                String vol = cc.getVolume();
                                String concen = cc.getConcentration();
                                String ccComment = cc.getComment();
                                String row = cc.getRow();
                                String col = cc.getColumn();
                                String dateCollected = (sample.getDateCollected() == null) ? "" : sample.getDateCollected().toString();
                                String dateExtracted = (sample.getDateExtracted() == null) ? "" : sample.getDateExtracted().toString();
                                String sampleComments = sample.getComments();

                                String sampleTypeYear1ID = "";
                                String dateCollectedYear1 = "";
                                String dateExtractedYear1 = "";
                                Object[] year1Data = (Object[]) userHttpSess.getSQLsampleyYear1Data(sampleID);
                                if (year1Data != null) {
                                    sampleTypeYear1ID = (year1Data[0] == null) ? "" : year1Data[0].toString();
                                    if (!sampleTypeYear1ID.equals("")) {
                                        SampleType sampleType = (SampleType) userHttpSess.getObjectById(SampleType.class, sampleTypeYear1ID);
                                        sampleTypeYear1ID = (sampleType != null) ? sampleType.getDescription() : sampleTypeYear1ID;
                                    }
                                    dateCollectedYear1 = (year1Data[1] == null) ? "" : year1Data[1].toString();
                                    dateExtractedYear1 = (year1Data[2] == null) ? "" : year1Data[2].toString();
                                }


                                // show unknown fields as blank
                                vol = (vol.equals("-1.0")) ? "" : vol;
                                concen = (concen == null || concen.equals("-1.0")) ? "" : concen;
                                ccComment = (ccComment == null || ccComment.equalsIgnoreCase("null")) ? "" : ccComment;
                                sampleComments = (sampleComments == null || sampleComments.equalsIgnoreCase("null")) ? "" : sampleComments;


                                // row should be alpha
                                row = row.equalsIgnoreCase("1") ? "A"
                                        : row.equalsIgnoreCase("2") ? "B"
                                        : row.equalsIgnoreCase("3") ? "C"
                                        : row.equalsIgnoreCase("4") ? "D"
                                        : row.equalsIgnoreCase("5") ? "E"
                                        : row.equalsIgnoreCase("6") ? "F"
                                        : row.equalsIgnoreCase("7") ? "G"
                                        : row.equalsIgnoreCase("8") ? "H"
                                        : row.equalsIgnoreCase("9") ? "I"
                                        : row.equalsIgnoreCase("10") ? "J"
                                        : row.equalsIgnoreCase("11") ? "K"
                                        : row.equalsIgnoreCase("12") ? "L" : row;

                                // only need the fist part of container type for WGA and GEN
                                String processType = (containerType.startsWith("WGA")) ? "WGA" : (containerType.startsWith("GEN")) ? "Genomic" : (containerType.startsWith("Box of Tubes")) ? "Tube" : "";

                                // get rid of dummy dates
                                if (dateCollected != null && dateCollected.equals("0001-01-01 00:00:00.0")) {
                                    dateCollected = "";
                                }
                                // trim time from dates
                                if (dateCollected != null && dateCollected.endsWith("00:00:00.0")) {
                                    dateCollected = dateCollected.substring(0, 10);
                                }
                                if (dateExtracted != null && dateExtracted.endsWith("00:00:00.0")) {
                                    dateExtracted = dateExtracted.substring(0, 10);
                                }

                                String qcStatus = "";
                                int status = -1;

                                Object statusObj = userHttpSess.getSQLsampleQCstatus(sampleID, "1");
                                if (statusObj != null) {
                                    status = (Integer) statusObj;
                                }
                                if (status == 1 || status == 0) {

                                    qcStatus = (status == 0) ? "fail" : "pass";
                                }

                                // determine if sample was genotyped at CNG
                                String sampleNameNoLetter = sampleName;
                                if (sampleName.endsWith("a") || sampleName.endsWith("b") || sampleName.endsWith("c") || sampleName.endsWith("d")) {
                                    sampleNameNoLetter = sampleName.substring(0, sampleName.length() - 1);
                                }
                                boolean wasGenotypedAtCNG = genotypedAtCNG.contains(sampleNameNoLetter);

                                // determine if DNA sample was collected in Yr 1 for CAPPS
                                String cappsYr1DNAWasCollected = (String) cappsDNASubjectStatuses.get(sampleNameNoLetter);

                                // only want stock tubes and stock plates for CNG
                                if (containerTypeID.equals("7")) {
                                    // only want Caucasians
                                    if (ethnicityID.equals("19")) {
                                        p.println(ar[2] + "\t" + sampleName + "\t" + processType + "\t" + type + "\t" + sampleComments + "\t" + dateCollected + "\t" + dateExtracted + "\t" + cappsYr1DNAWasCollected + "\t" + sampleTypeYear1ID + "\t" + dateCollectedYear1 + "\t" + dateExtractedYear1 + "\t" + qcStatus + "\t" + wasGenotypedAtCNG + "\t" + vol + "\t" + concen + "\t" + ccComment + "\t" + containerType + "\t" + containerName + "\t" + row + "\t" + col);
                                    }
                                }
                            }

                        } else {
                            p.println(ar[1] + "\t" + ar[0]);
                            e.println("No sample for " + ar[0] + ", cohortName = " + ar[1]);
                        }
                    }
                } else {
                    p.println(ar[1] + "\t" + ar[0]);
                    e.println("No sample for " + ar[0] + ", cohortName = " + ar[1]);
                }
                //}
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    private void getMontrealSampleDataFromDB(String fileDir) {
        try {
            //1.) build list of all samples in files
            HashSet<String> sampleList = new HashSet();
            String[] elem = {};
            String lineStr = "";
            String prefix;
            List samplesToFetch = new LinkedList();

            FileOutputStream out; // declare a file output object
            PrintStream p; // declare a print stream object
            out = new FileOutputStream(fileDir + "\\out.txt");
            // Connect print stream to the output stream
            p = new PrintStream(out);

            FileOutputStream err; // declare a file output object
            PrintStream e; // declare a print stream object
            err = new FileOutputStream(fileDir + "\\err.txt");
            // Connect print stream to the output stream
            e = new PrintStream(err);


            HashMap cappsDNASubjectStatuses = getCAPPSyr1DNAsamplesStatuses(fileDir);
            //ArrayList noTubeSampleNames = getSampleNamesFromFile(fileDir);

            File files = new File(fileDir + "\\current");
            for (File file : files.listFiles()) {
                if (file.getName().endsWith("-input.txt")) {
                    //parse file of samples to get for Mtl
                    ArrayList<String> fileContents = Tools.readFile(file);
                    samplesToFetch = new LinkedList();
                    for (String line : fileContents) {
                        lineStr = line;
                        elem = line.split("\t");  //split using tab char
                        if (elem.length > 0) {
                            String cohortName = (elem.length > 0) ? elem[0].trim() : "";
                            String subjectName = (elem.length > 1) ? elem[1].trim() : "";
                            String source = (elem.length > 2) ? elem[2].trim() : "";
                            String comment = (elem.length > 3) ? elem[3].trim() : "";

                            String[] ar = {subjectName, cohortName, source, comment};
                            samplesToFetch.add(ar);
                            //p.println(cohortName + "\t" + sampleName);
                        }
                    }
                }
            }

            p.println("Cohort\tID\tSource\tComments\tUpdated Source\tSample ID\tPhysical Type\tIs Stock\tSample Type\tSample Comments\tDate of Collection\tDate of Extraction\tYr 1 DNA collected (Y/N)\tYr 1 Sample Type\tYr 1 Collection Date\tYr 1 Extraction Date\tPass/Fail CNG\tVolume (ul)\tConcentration (ng/ul)\tTube Comments\tContainer Type\tOld Container Type\tContainer Name\tRow\tColumn");

            // for every sample, get subject, samples of subject and tubes of samples
            Iterator itr = samplesToFetch.iterator();
            HashMap uniqueSam = new HashMap(2);
            HashMap uniqueSub = new HashMap(2);
            String subjectID = null;
            String subjectName = "";
            while (itr.hasNext()) {
                String[] ar = (String[]) itr.next();
                String cohortTag = (ar.length > 1) ? ar[1] : "";

                // get subject
                if (!subjectName.equals(ar[0])) {
                    subjectName = ar[0];
                    uniqueSub.put("subjectName", "'" + ar[0] + "'");

                    String cohortID = null;
                    if (ar[1].equalsIgnoreCase("Az")) {
                        cohortID = "3";
                    } else if (ar[1].equalsIgnoreCase("SAGE")) {
                        cohortID = "4";
                    }
                    if (cohortID != null) {
                        uniqueSub.put("cohort.cohortID", cohortID);
                        Subject subject = (Subject) userHttpSess.getObjectByUniqueKey(Subject.class, uniqueSub);
                        if (subject == null) {
                            e.println("No subject for " + ar[0] + ", cohortName = " + ar[1]);
                            continue;
                        } else {
                            subjectID = subject.getSubjectID();
                        }

                        //uniqueSam.put("sampleName", "'" + ar[0] + "'");
                        // get all samples for subject
                        uniqueSam.put("subject.subjectID", subjectID);
                        List<Sample> samples = userHttpSess.getObjectsOfClass(Sample.class, uniqueSam);
                        Iterator itrSam = samples.iterator();
                        if (samples == null || samples.size() == 0 || samples.isEmpty()) {
                            e.println("No sample for subject " + ar[1] + ar[0]);
                            continue;
                        }
                        // for each sample
                        while (itrSam.hasNext()) {
                            Sample sample = (Sample) itrSam.next();
                            if (sample != null) {
                                String sampleID = sample.getSampleID();
                                String sampleName = sample.getSampleName();
                                String sampleTypeDesc = "NULL";
                                if (sample.getSampleType() != null) {
                                    sampleTypeDesc = sample.getSampleType().getDescription();
                                }
                                // GET CONTAINER CONTENTS DATA FOR SAMPLE
                                List ccList = new LinkedList();
                                HashMap cont = new HashMap();
                                cont.put("sample.sampleID", sampleID);
                                ccList = userHttpSess.getObjectsOfClass(ContainerContent.class, cont);
                                if (ccList == null || ccList.size() == 0 || ccList.isEmpty()) {
                                    e.println("No container contents for sample " + sampleName + " of subject " + ar[1] + ar[0] + " using sampleID=" + sampleID);
                                }
                                // get container name
                                Iterator iter = ccList.iterator();
                                while (iter.hasNext()) {
                                    ContainerContent cc = (ContainerContent) iter.next();
                                    String containerTypeID = (cc.getContainer() == null) ? "" : cc.getContainer().getContainerType().getContainerTypeID();
                                    String containerType = (cc.getContainer() == null) ? "" : cc.getContainer().getContainerType().getDescription();
                                    String containerName = (cc.getContainer() == null) ? "" : cc.getContainer().getcontainerName();
                                    String vol = cc.getVolume();
                                    String concen = cc.getConcentration();
                                    String ccComment = cc.getComments();
                                    String row = cc.getRow();
                                    String col = cc.getColumn();
                                    String dateCollected = (sample.getDateCollected() == null) ? "" : sample.getDateCollected().toString();
                                    String dateExtracted = (sample.getDateExtracted() == null) ? "" : sample.getDateExtracted().toString();
                                    String sampleComments = sample.getComments();
                                    String contIsStock = (cc.getContainer() == null) ? "" : cc.getContainer().getIsStock();
                                    contIsStock = (contIsStock.equals("0")) ? "No" : (contIsStock.equals("1")) ? "Yes" : "";

                                    String sampleTypeYear1ID = "";
                                    String dateCollectedYear1 = "";
                                    String dateExtractedYear1 = "";
                                    Object[] year1Data = (Object[]) userHttpSess.getSQLsampleyYear1Data(sampleID);
                                    if (year1Data != null) {
                                        sampleTypeYear1ID = (year1Data[0] == null) ? "" : year1Data[0].toString();
                                        if (!sampleTypeYear1ID.equals("")) {
                                            SampleType sampleType = (SampleType) userHttpSess.getObjectById(SampleType.class, sampleTypeYear1ID);
                                            sampleTypeYear1ID = (sampleType != null) ? sampleType.getDescription() : sampleTypeYear1ID;
                                        }
                                        dateCollectedYear1 = (year1Data[1] == null) ? "" : year1Data[1].toString();
                                        dateExtractedYear1 = (year1Data[2] == null) ? "" : year1Data[2].toString();
                                    }



                                    // show unknown fields as blank
                                    vol = (vol.equals("-1.0")) ? "" : vol;
                                    concen = (concen.equals("-1.0")) ? "" : concen;
                                    ccComment = (ccComment == null || ccComment.equalsIgnoreCase("null")) ? "" : ccComment;
                                    sampleComments = (sampleComments == null || sampleComments.equalsIgnoreCase("null")) ? "" : sampleComments;


                                    // row should be alpha
                                    row = row.equalsIgnoreCase("1") ? "A"
                                            : row.equalsIgnoreCase("2") ? "B"
                                            : row.equalsIgnoreCase("3") ? "C"
                                            : row.equalsIgnoreCase("4") ? "D"
                                            : row.equalsIgnoreCase("5") ? "E"
                                            : row.equalsIgnoreCase("6") ? "F"
                                            : row.equalsIgnoreCase("7") ? "G"
                                            : row.equalsIgnoreCase("8") ? "H"
                                            : row.equalsIgnoreCase("9") ? "I"
                                            : row.equalsIgnoreCase("10") ? "J"
                                            : row.equalsIgnoreCase("11") ? "K"
                                            : row.equalsIgnoreCase("12") ? "L" : row;
                                    /*
                                    // only need the fist part of container type for WGA and GEN
                                    String processType = (containerType.startsWith("WGA"))? "WGA":
                                    (containerType.startsWith("GEN"))? "Genomic":
                                    (containerType.startsWith("Box of Tubes"))? "Tube": "";

                                     */
                                    // String processType = (containerTypeID.equals("6")) ? "WGA" : (containerTypeID.equals("5")) ? "Genomic" : (containerTypeID.equals("7")) ? "Tube" : containerTypeID;

                                    // get rid of dummy dates
                                    if (dateCollected != null && dateCollected.equals("0001-01-01 00:00:00.0")) {
                                        dateCollected = "";
                                    }

                                    // trim time from dates
                                    if (dateCollected != null && dateCollected.endsWith("00:00:00.0")) {
                                        dateCollected = dateCollected.substring(0, 10);
                                    }
                                    if (dateExtracted != null && dateExtracted.endsWith("00:00:00.0")) {
                                        dateExtracted = dateExtracted.substring(0, 10);
                                    }

                                    String qcStatus = "";
                                    int status = -1;

                                    Object statusObj = userHttpSess.getSQLsampleQCstatus(sampleID, "1");
                                    if (statusObj != null) {
                                        status = (Integer) statusObj;
                                    }
                                    if (status == 1 || status == 0) {

                                        qcStatus = (status == 0) ? "fail" : "pass";
                                    }

                                    // determine if DNA sample was collected in Yr 1 for CAPPS
                                    String cappsYr1DNAWasCollected = (String) cappsDNASubjectStatuses.get(cohortTag + subjectName);


                                    // match type to what's in list
//                                    if ((ar[2].equalsIgnoreCase("genomic") && (processType.equals("Genomic")
//                                            || processType.equals("Tube"))) || (ar[2].equalsIgnoreCase("wga")
//                                            && !(processType.equals("Genomic") || processType.equals("Tube")))) {

                                    //lab: only want tubes if genomic or tubes and stock plates if WGA

                                    //if(noTubeSampleNames.contains(sampleName)){
                                    if ((ar[2].equalsIgnoreCase("genomic") && containerTypeID.equals("7"))
                                            || (ar[2].equalsIgnoreCase("wga") && (containerName.indexOf("Stock") > 0) || containerTypeID.equals("7"))) {
                                        //  sample selecting: only care if lab has tubes
                                        //    if(containerTypeID.equals("7")){

                                        p.println(ar[1] + "\t" + ar[0] + "\t" + ar[2] + "\t" + ar[3] + "\t" + "\t" + sampleName + "\t" + contIsStock + "\t" + sampleTypeDesc + "\t" + sampleComments + "\t" + dateCollected + "\t" + dateExtracted + "\t" + cappsYr1DNAWasCollected + "\t" + sampleTypeYear1ID + "\t" + dateCollectedYear1 + "\t" + dateExtractedYear1 + "\t" + qcStatus + "\t" + vol + "\t" + concen + "\t" + ccComment + "\t" + containerType + "\t" + containerName + "\t" + row + "\t" + col);
                                    }
                                    //}
                                }
                                // }

                            } else {
                                p.println(ar[1] + "\t" + ar[0]);
                                e.println("No sample for " + ar[0] + ", cohortName = " + ar[1]);
                            }
                        }
                    } else {
                        p.println(ar[1] + "\t" + ar[0]);
                        e.println("No sample for " + ar[0] + ", cohortName = " + ar[1]);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    private void getMontrealSampleDataFromDBParentsAsthmaEver(String fileDir) {
        try {
            //1.) build list of all samples in files
            HashSet<String> sampleList = new HashSet();
            String[] elem = {};
            String lineStr = "";
            String prefix;
            List samplesToFetch = new LinkedList();

            FileOutputStream out; // declare a file output object
            PrintStream p; // declare a print stream object
            out = new FileOutputStream(fileDir + "\\out.txt");
            // Connect print stream to the output stream
            p = new PrintStream(out);

            FileOutputStream err; // declare a file output object
            PrintStream e; // declare a print stream object
            err = new FileOutputStream(fileDir + "\\err.txt");
            // Connect print stream to the output stream
            e = new PrintStream(err);


            HashMap cappsDNASubjectStatuses = getCAPPSyr1DNAsamplesStatuses(fileDir);
            //ArrayList noTubeSampleNames = getSampleNamesFromFile(fileDir);

            File files = new File(fileDir + "\\current");
            for (File file : files.listFiles()) {
                if (file.getName().endsWith("-input.txt")) {
                    //parse file of samples to get for Mtl
                    ArrayList<String> fileContents = Tools.readFile(file);
                    samplesToFetch = new LinkedList();
                    for (String line : fileContents) {
                        lineStr = line;
                        elem = line.split("\t");  //split using tab char
                        if (elem.length > 0) {
                            String cohortName = (elem.length > 0) ? elem[0].trim() : "";
                            String subjectName = (elem.length > 1) ? elem[1].trim() : "";
                            String asthmaEver = (elem.length > 2) ? elem[2].trim() : "";
                            String ethnicity = (elem.length > 3) ? elem[3].trim() : "";
                            String source = (elem.length > 4) ? elem[4].trim() : "";
                            String comment = (elem.length > 5) ? elem[5].trim() : "";
                            String add = (elem.length > 6) ? elem[6].trim() : "";

                            String[] origAr = {cohortName, subjectName, asthmaEver, ethnicity, source, comment, add};
                            String[] ar = {subjectName, cohortName, source, comment};
                            samplesToFetch.add(ar);
                            samplesToFetch.add(origAr);
                            //p.println(cohortName + "\t" + sampleName);
                        }
                    }
                }
            }

            //p.println("Cohort\tID\tSource\tComments\tUpdated Source\tSample ID\tPhysical Type\tIs Stock\tSample Type\tSample Comments\tDate of Collection\tDate of Extraction\tYr 1 DNA collected (Y/N)\tYr 1 Sample Type\tYr 1 Collection Date\tYr 1 Extraction Date\tPass/Fail CNG\tVolume (ul)\tConcentration (ng/ul)\tTube Comments\tContainer Type\tOld Container Type\tContainer Name\tRow\tColumn");

            // for every sample, get subject, samples of subject and tubes of samples
            Iterator itr = samplesToFetch.iterator();
            HashMap uniqueSam = new HashMap(2);
            HashMap uniqueSub = new HashMap(2);
            String subjectID = null;
            String subjectName = "";
            while (itr.hasNext()) {
                String[] ar = (String[]) itr.next();
                String[] arOrig = (String[]) itr.next();
                String cohortTag = (ar.length > 1) ? ar[1] : "";

                // get subject
                if (!subjectName.equals(ar[0])) {
                    subjectName = ar[0];
                    uniqueSub.put("subjectName", "'" + ar[0] + "'");

                    String cohortID = null;
                    if (ar[1].equalsIgnoreCase("Az")) {
                        cohortID = "3";
                    } else if (ar[1].equalsIgnoreCase("SAGE")) {
                        cohortID = "4";
                    }
                    if (cohortID != null) {
                        uniqueSub.put("cohort.cohortID", cohortID);
                        Subject subject = (Subject) userHttpSess.getObjectByUniqueKey(Subject.class, uniqueSub);
                        if (subject == null) {
                            e.println("No subject for " + ar[0] + ", cohortName = " + ar[1]);
                            continue;
                        } else {
                            subjectID = subject.getSubjectID();
                        }

                        //uniqueSam.put("sampleName", "'" + ar[0] + "'");
                        // get all samples for subject
                        uniqueSam.put("subject.subjectID", subjectID);
                        List<Sample> samples = userHttpSess.getObjectsOfClass(Sample.class, uniqueSam);
                        Iterator itrSam = samples.iterator();
                        if (samples == null || samples.size() == 0 || samples.isEmpty()) {
                            e.println("No sample for subject " + ar[1] + ar[0]);
                            continue;
                        }
                        // for each sample
                        while (itrSam.hasNext()) {
                            Sample sample = (Sample) itrSam.next();
                            if (sample != null) {
                                String sampleID = sample.getSampleID();
                                String sampleName = sample.getSampleName();
                                String sampleTypeDesc = "NULL";
                                if (sample.getSampleType() != null) {
                                    sampleTypeDesc = sample.getSampleType().getDescription();
                                }
                                // GET CONTAINER CONTENTS DATA FOR SAMPLE
                                List ccList = new LinkedList();
                                HashMap cont = new HashMap();
                                cont.put("sample.sampleID", sampleID);
                                ccList = userHttpSess.getObjectsOfClass(ContainerContent.class, cont);
                                if (ccList == null || ccList.size() == 0 || ccList.isEmpty()) {
                                    e.println("No container contents for sample " + sampleName + " of subject " + ar[1] + ar[0] + " using sampleID=" + sampleID);
                                }
                                // get container name
                                Iterator iter = ccList.iterator();
                                while (iter.hasNext()) {
                                    ContainerContent cc = (ContainerContent) iter.next();
                                    String containerTypeID = (cc.getContainer() == null) ? "" : cc.getContainer().getContainerType().getContainerTypeID();
                                    String containerType = (cc.getContainer() == null) ? "" : cc.getContainer().getContainerType().getDescription();
                                    String containerName = (cc.getContainer() == null) ? "" : cc.getContainer().getcontainerName();
                                    String vol = cc.getVolume();
                                    String concen = cc.getConcentration();
                                    String ccComment = cc.getComments();
                                    String row = cc.getRow();
                                    String col = cc.getColumn();
                                    String dateCollected = (sample.getDateCollected() == null) ? "" : sample.getDateCollected().toString();
                                    String dateExtracted = (sample.getDateExtracted() == null) ? "" : sample.getDateExtracted().toString();
                                    String sampleComments = sample.getComments();
                                    String contMaterialType = (cc.getContainer() == null) ? "" : cc.getMaterialType().getDescription();
                                    String contIsStock = (cc.getContainer() == null) ? "" : cc.getContainer().getIsStock();
                                    contIsStock = (contIsStock.equals("0")) ? "No" : (contIsStock.equals("1")) ? "Yes" : "";

                                    String sampleTypeYear1ID = "";
                                    String dateCollectedYear1 = "";
                                    String dateExtractedYear1 = "";
                                    Object[] year1Data = (Object[]) userHttpSess.getSQLsampleyYear1Data(sampleID);
                                    if (year1Data != null) {
                                        sampleTypeYear1ID = (year1Data[0] == null) ? "" : year1Data[0].toString();
                                        if (!sampleTypeYear1ID.equals("")) {
                                            SampleType sampleType = (SampleType) userHttpSess.getObjectById(SampleType.class, sampleTypeYear1ID);
                                            sampleTypeYear1ID = (sampleType != null) ? sampleType.getDescription() : sampleTypeYear1ID;
                                        }
                                        dateCollectedYear1 = (year1Data[1] == null) ? "" : year1Data[1].toString();
                                        dateExtractedYear1 = (year1Data[2] == null) ? "" : year1Data[2].toString();
                                    }



                                    // show unknown fields as blank
                                    vol = (vol.equals("-1.0")) ? "" : vol;
                                    concen = (concen.equals("-1.0")) ? "" : concen;
                                    ccComment = (ccComment == null || ccComment.equalsIgnoreCase("null")) ? "" : ccComment;
                                    sampleComments = (sampleComments == null || sampleComments.equalsIgnoreCase("null")) ? "" : sampleComments;


                                    // row should be alpha
                                    row = row.equalsIgnoreCase("1") ? "A"
                                            : row.equalsIgnoreCase("2") ? "B"
                                            : row.equalsIgnoreCase("3") ? "C"
                                            : row.equalsIgnoreCase("4") ? "D"
                                            : row.equalsIgnoreCase("5") ? "E"
                                            : row.equalsIgnoreCase("6") ? "F"
                                            : row.equalsIgnoreCase("7") ? "G"
                                            : row.equalsIgnoreCase("8") ? "H"
                                            : row.equalsIgnoreCase("9") ? "I"
                                            : row.equalsIgnoreCase("10") ? "J"
                                            : row.equalsIgnoreCase("11") ? "K"
                                            : row.equalsIgnoreCase("12") ? "L" : row;
                                    /*
                                    // only need the fist part of container type for WGA and GEN
                                    String processType = (containerType.startsWith("WGA"))? "WGA":
                                    (containerType.startsWith("GEN"))? "Genomic":
                                    (containerType.startsWith("Box of Tubes"))? "Tube": "";

                                     */
                                    // String processType = (containerTypeID.equals("6")) ? "WGA" : (containerTypeID.equals("5")) ? "Genomic" : (containerTypeID.equals("7")) ? "Tube" : containerTypeID;

                                    // get rid of dummy dates
                                    if (dateCollected != null && dateCollected.equals("0001-01-01 00:00:00.0")) {
                                        dateCollected = "";
                                    }

                                    // trim time from dates
                                    if (dateCollected != null && dateCollected.endsWith("00:00:00.0")) {
                                        dateCollected = dateCollected.substring(0, 10);
                                    }
                                    if (dateExtracted != null && dateExtracted.endsWith("00:00:00.0")) {
                                        dateExtracted = dateExtracted.substring(0, 10);
                                    }

                                    String qcStatus = "";
                                    int status = -1;

                                    Object statusObj = userHttpSess.getSQLsampleQCstatus(sampleID, "1");
                                    if (statusObj != null) {
                                        status = (Integer) statusObj;
                                    }
                                    if (status == 1 || status == 0) {

                                        qcStatus = (status == 0) ? "fail" : "pass";
                                    }

                                    // determine if DNA sample was collected in Yr 1 for CAPPS
                                    String cappsYr1DNAWasCollected = (String) cappsDNASubjectStatuses.get(cohortTag + subjectName);


                                    // match type to what's in list
//                                    if ((ar[2].equalsIgnoreCase("genomic") && (processType.equals("Genomic")
//                                            || processType.equals("Tube"))) || (ar[2].equalsIgnoreCase("wga")
//                                            && !(processType.equals("Genomic") || processType.equals("Tube")))) {

                                    //lab: only want tubes if genomic or tubes and stock plates if WGA

                                    //if(noTubeSampleNames.contains(sampleName)){
                                    if ((ar[2].indexOf("GEN") >= 0 && containerTypeID.equals("7"))
                                            || (ar[2].indexOf("WGA") >= 0 && (containerName.indexOf("Stock") >= 0) || containerTypeID.equals("7"))) {
                                        //  sample selecting: only care if lab has tubes
                                        //    if(containerTypeID.equals("7")){

                                        p.println(arOrig[0] + "\t" + arOrig[1] + "\t" + arOrig[2] + "\t" + arOrig[3] + "\t" + arOrig[4] + "\t"
                                                + arOrig[5] + "\t" + arOrig[6] + "\t" + "\t" + sampleName + "\t" + contMaterialType + "\t" + contIsStock + "\t" + sampleTypeDesc + "\t" + sampleComments + "\t" + dateCollected + "\t" + dateExtracted + "\t" + cappsYr1DNAWasCollected + "\t" + sampleTypeYear1ID + "\t" + dateCollectedYear1 + "\t" + dateExtractedYear1 + "\t" + qcStatus + "\t" + vol + "\t" + concen + "\t" + ccComment + "\t" + containerType + "\t" + contMaterialType + " " + containerType + "\t" + containerName + "\t" + row + "\t" + col);
                                        //p.println(ar[1] + "\t" + ar[0] + "\t" + ar[2] + "\t" + ar[3] + "\t" + "\t" + sampleName + "\t" + contMaterialType +"\t" + contIsStock + "\t" + sampleTypeDesc + "\t" + sampleComments + "\t" + dateCollected + "\t" + dateExtracted + "\t" +cappsYr1DNAWasCollected + "\t" + sampleTypeYear1ID + "\t" + dateCollectedYear1 + "\t" + dateExtractedYear1 + "\t" + qcStatus + "\t" + vol + "\t" + concen + "\t" + ccComment + "\t" + containerType +"\t" + contMaterialType+" "+containerType + "\t" + containerName + "\t" + row + "\t" + col);
                                    }
                                    //}
                                }
                                // }

                            } else {
                                p.println(ar[1] + "\t" + ar[0]);
                                e.println("No sample for " + ar[0] + ", cohortName = " + ar[1]);
                            }
                        }
                    } else {
                        p.println(ar[1] + "\t" + ar[0]);
                        e.println("No sample for " + ar[0] + ", cohortName = " + ar[1]);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    private void getGenomicTubeContentDataFromDB(String fileDir) {
        try {
            //1.) build list of all samples in files
            HashSet<String> sampleList = new HashSet();
            String[] elem = {};
            String lineStr = "";
            String prefix;

            FileOutputStream out; // declare a file output object
            PrintStream p; // declare a print stream object
            out = new FileOutputStream(fileDir + "\\out.txt");
            // Connect print stream to the output stream
            p = new PrintStream(out);

            FileOutputStream err; // declare a file output object
            PrintStream e; // declare a print stream object
            err = new FileOutputStream(fileDir + "\\err.txt");
            // Connect print stream to the output stream
            e = new PrintStream(err);


            //HashMap cappsDNASubjectStatuses = getCAPPSyr1DNAsamplesStatuses(fileDir);
            //ArrayList noTubeSampleNames = getSampleNamesFromFile(fileDir);

            List sampleNames = getSampleNamesFromFile(fileDir, "WGAtoGENsampIDs-1.txt", 0);


            // get sampleType for every sample
            Iterator itr = sampleNames.iterator();
            HashMap sam = new HashMap(1);
            while (itr.hasNext()) {
                String sampleName = (String) itr.next();
                // get sample object
                sam.put("sampleName", "'" + sampleName + "'");
                List<Sample> samples = userHttpSess.getObjectsOfClass(Sample.class, sam);

                Iterator itrSam = samples.iterator();
                if (samples == null || samples.size() == 0 || samples.isEmpty()) {
                    e.println("No sample for sample " + sampleName);
                    continue;
                }
                while (itrSam.hasNext()) {
                    Sample sample = (Sample) itrSam.next();
                    // for each sample, get all it's contents
                    if (sample != null) {
                        String type = "NULL";
                        String sampleID = sample.getSampleID();
                        sampleName = sample.getSampleName();
                        if (sample.getSampleType() != null) {
                            type = sample.getSampleType().getDescription();
                        }
                        // GET CONTAINER CONTENTS DATA FOR SAMPLE
                        List ccList = new LinkedList();
                        HashMap cont = new HashMap();
                        cont.put("sample.sampleID", sampleID);
                        ccList = userHttpSess.getObjectsOfClass(ContainerContent.class, cont);
                        if (ccList == null || ccList.size() == 0 || ccList.isEmpty()) {
                            e.println("No container contents for sample " + sampleName);
                        }
                        // get container name
                        Iterator iter = ccList.iterator();
                        while (iter.hasNext()) {
                            ContainerContent cc = (ContainerContent) iter.next();
                            String containerTypeID = (cc.getContainer() == null) ? "" : cc.getContainer().getContainerType().getContainerTypeID();
                            String containerType = (cc.getContainer() == null) ? "" : cc.getContainer().getContainerType().getDescription();
                            String containerName = (cc.getContainer() == null) ? "" : cc.getContainer().getcontainerName();
                            String materialType = (cc.getContainer() == null) ? "" : cc.getMaterialType().getDescription();
                            String isStock = (cc.getContainer() == null) ? "" : cc.getContainer().getIsStock();
                            String vol = cc.getVolume();
                            String concen = cc.getConcentration();
                            String ccComment = cc.getComment();
                            String row = cc.getRow();
                            String col = cc.getColumn();
                            String dateCollected = (sample.getDateCollected() == null) ? "" : sample.getDateCollected().toString();
                            String dateExtracted = (sample.getDateExtracted() == null) ? "" : sample.getDateExtracted().toString();
                            String sampleComments = sample.getComments();


                            String sampleTypeYear1ID = "";
                            String dateCollectedYear1 = "";
                            String dateExtractedYear1 = "";
                            Object[] year1Data = (Object[]) userHttpSess.getSQLsampleyYear1Data(sampleID);
                            if (year1Data != null) {
                                sampleTypeYear1ID = (year1Data[0] == null) ? "" : year1Data[0].toString();
                                if (!sampleTypeYear1ID.equals("")) {
                                    SampleType sampleType = (SampleType) userHttpSess.getObjectById(SampleType.class, sampleTypeYear1ID);
                                    sampleTypeYear1ID = (sampleType != null) ? sampleType.getDescription() : sampleTypeYear1ID;
                                }
                                dateCollectedYear1 = (year1Data[1] == null) ? "" : year1Data[1].toString();
                                dateExtractedYear1 = (year1Data[2] == null) ? "" : year1Data[2].toString();
                            }



                            // show unknown fields as blank
                            vol = (vol.equals("-1.0")) ? "" : vol;
                            concen = (concen.equals("-1.0")) ? "" : concen;
                            ccComment = (ccComment == null || ccComment.equalsIgnoreCase("null")) ? "" : ccComment;
                            sampleComments = (sampleComments == null || sampleComments.equalsIgnoreCase("null")) ? "" : sampleComments;


                            // row should be alpha
                            row = row.equalsIgnoreCase("1") ? "A"
                                    : row.equalsIgnoreCase("2") ? "B"
                                    : row.equalsIgnoreCase("3") ? "C"
                                    : row.equalsIgnoreCase("4") ? "D"
                                    : row.equalsIgnoreCase("5") ? "E"
                                    : row.equalsIgnoreCase("6") ? "F"
                                    : row.equalsIgnoreCase("7") ? "G"
                                    : row.equalsIgnoreCase("8") ? "H"
                                    : row.equalsIgnoreCase("9") ? "I"
                                    : row.equalsIgnoreCase("10") ? "J"
                                    : row.equalsIgnoreCase("11") ? "K"
                                    : row.equalsIgnoreCase("12") ? "L" : row;
                            /*
                            // only need the fist part of container type for WGA and GEN
                            String processType = (containerType.startsWith("WGA"))? "WGA":
                            (containerType.startsWith("GEN"))? "Genomic":
                            (containerType.startsWith("Box of Tubes"))? "Tube": "";

                             */

                            // get rid of dummy dates
                            if (dateCollected != null && dateCollected.equals("0001-01-01 00:00:00.0")) {
                                dateCollected = "";
                            }

                            // trim time from dates
                            if (dateCollected != null && dateCollected.endsWith("00:00:00.0")) {
                                dateCollected = dateCollected.substring(0, 10);
                            }
                            if (dateExtracted != null && dateExtracted.endsWith("00:00:00.0")) {
                                dateExtracted = dateExtracted.substring(0, 10);
                            }

                            //only want genomic tubes
                            //if(noTubeSampleNames.contains(sampleName)){
                            if (materialType.equals("Genomic") && containerTypeID.equals("7")) {
                                //  sample selecting: only care if lab has tubes
                                //    if(containerTypeID.equals("7")){
                                p.println(sampleName + "\t" + materialType + "\t" + isStock + "\t" + type + "\t" + sampleComments + "\t" + dateCollected + "\t" + dateExtracted + "\t" + sampleTypeYear1ID + "\t" + dateCollectedYear1 + "\t" + dateExtractedYear1 + "\t" + vol + "\t" + concen + "\t" + ccComment + "\t" + containerType + "\t" + containerName + "\t" + row + "\t" + col);
                            }
                        }

                    } else {
                        p.println(sampleName);
                        e.println("No sample for " + sampleName);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    private void getSubjectsWithoutWGA(String fileDir) {
        try {
            //1.) build list of all samples in files
            HashSet<String> sampleList = new HashSet();
            String[] elem = {};
            String lineStr = "";
            String prefix;
            List subjectsToFetch = new LinkedList();


            String dateTime = icapture.com.Util.getCurrentDateTime();

            FileOutputStream out; // declare a file output object
            PrintStream p; // declare a print stream object
            out = new FileOutputStream(fileDir + "\\out" + dateTime + ".txt");
            // Connect print stream to the output stream
            p = new PrintStream(out);

            FileOutputStream err; // declare a file output object
            PrintStream e; // declare a print stream object
            err = new FileOutputStream(fileDir + "\\err" + dateTime + ".txt");
            // Connect print stream to the output stream
            e = new PrintStream(err);


            // get subjects from every input file
            File files = new File(fileDir + "\\current");
            for (File file : files.listFiles()) {
                if (file.getName().endsWith("-input.txt")) {
                    //parse file of subjects to check
                    ArrayList<String> fileContents = Tools.readFile(file);
                    subjectsToFetch = new LinkedList();
                    for (String line : fileContents) {
                        lineStr = line;
                        elem = line.split("\t");  //split using tab char
                        if (elem.length > 0) {
                            String cohortName = (elem.length > 0) ? elem[0].trim() : "";
                            String subjectName = (elem.length > 1) ? elem[1].trim() : "";

                            String[] ar = {subjectName, cohortName};
                            subjectsToFetch.add(ar);
                            //p.println(cohortName + "\t" + sampleName);
                        }
                    }
                }
            }

            int subjectCountFromFile = subjectsToFetch.size();
            p.println("Cohort\tSubject ID\tSample Name\tPhysical Type\tIs Stock\tSample Type\tSample Comments\tVolume (ul)\tConcentration (ng/ul)\tTube Comments\tContainer Type\tContainer Name\tRow\tColumn");

            // for every subject, check if there is a WGA sample available
            Iterator itr = subjectsToFetch.iterator();
            HashMap contBySubj = new HashMap(2);
            HashMap uniqueSub = new HashMap(2);
            String subjectID = null;
            String subjectName = "";
            int countWGAFound = 0;
            int countNoWGAOrTubeFound = 0;
            int countNoWGAButTubeFound = 0;
            while (itr.hasNext()) {
                String[] ar = (String[]) itr.next();
                String cohortTag = (ar.length > 1) ? ar[1] : "";

                // get subject (skip duplicates)
                if (!subjectName.equals(ar[0])) {
                    subjectName = ar[0];
                    uniqueSub.put("subjectName", "'" + ar[0] + "'");

                    String cohortID = null;
                    if (ar[1].equalsIgnoreCase("Az")) {
                        cohortID = "3";
                    } else if (ar[1].equalsIgnoreCase("CAPPS")) {
                        cohortID = "3";
                    } else if (ar[1].equalsIgnoreCase("SAGE")) {
                        cohortID = "4";
                    }
                    if (cohortID != null) {
                        uniqueSub.put("cohort.cohortID", cohortID);
                        Subject subject = (Subject) userHttpSess.getObjectByUniqueKey(Subject.class, uniqueSub);
                        if (subject == null) {
                            e.println("No subject for " + ar[0] + ", cohortName = " + ar[1]);
                            continue;
                        } else {
                            subjectID = subject.getSubjectID();
                        }

                        // get all WGA contents for subject
                        contBySubj.put("sample.subject.subjectID", subjectID);
                        contBySubj.put("materialType", "2");
                        contBySubj.put("container.isStock", "1");
                        List<ContainerContent> contents = userHttpSess.getObjectsOfClass(ContainerContent.class, contBySubj);
                        // if has WGA samples, check the volume otherwise get the stock tubes
                        if (contents != null && contents.size() != 0 && !contents.isEmpty()) {
                            boolean sufficientVol = false;
                            Iterator itWGA = contents.iterator();
                            while (itWGA.hasNext() && !sufficientVol) {
                                ContainerContent content = (ContainerContent) itWGA.next();
                                if (Double.parseDouble(content.getVolume()) > 20) {
                                    sufficientVol = true;
                                    e.println("Subject:\t" + content.getSample().getSubject().getCohort().getDescription() + content.getSample().getSubject().getSubjectName() + "\t Sample:\t" + content.getSample().getSampleName() + " cc-Comments:\t" + content.getComments() + "\t sample-Comments:\t" + content.getSample().getComments());
                                }
                            }
                            // if have WGA w/ sufficient vol, you're done
                            if (sufficientVol) {
                                countWGAFound++;
                                continue;
                            }
                        }
                        // if no WGA sample or none with enough volume, get tubes to use in amplification
                        HashMap subjTubes = new HashMap();
                        subjTubes.put("sample.subject.subjectID", subjectID);
                        subjTubes.put("materialType.materialTypeID", "1");
                        subjTubes.put("container.containerType.containerTypeID", "7");
                        List<ContainerContent> tubes = userHttpSess.getObjectsOfClass(ContainerContent.class, subjTubes);
                        if (tubes == null || tubes.size() == 0 || tubes.isEmpty()) {
                            //e.println("hash:  " + subjTubes);
                            e.println("No WGA or tube samples for subject " + ar[1] + ar[0] + " (dbID:" + subjectID + ")");
                            countNoWGAOrTubeFound++;
                            continue;
                        }

                        countNoWGAButTubeFound++;
                        Iterator iter = tubes.iterator();

                        while (iter.hasNext()) {
                            ContainerContent cc = (ContainerContent) iter.next();
                            String sampleName = cc.getSample().getSampleName();
                            String sampleType = (cc.getSample().getSampleType() == null) ? "" : cc.getSample().getSampleType().getDescription();
                            String containerTypeID = (cc.getContainer() == null) ? "" : cc.getContainer().getContainerType().getContainerTypeID();
                            String containerType = (cc.getContainer() == null) ? "" : cc.getContainer().getContainerType().getDescription();
                            String containerName = (cc.getContainer() == null) ? "" : cc.getContainer().getcontainerName();
                            String vol = cc.getVolume();
                            String concen = cc.getConcentration();
                            String ccComment = cc.getComments();
                            String row = cc.getRow();
                            String col = cc.getColumn();
                            String sampleComments = cc.getSample().getComments();
                            String materialType = cc.getMaterialType().getDescription();
                            String contIsStock = (cc.getContainer() == null) ? "" : cc.getContainer().getIsStock();
                            contIsStock = (contIsStock.equals("0")) ? "No" : (contIsStock.equals("1")) ? "Yes" : "";


                            // show unknown fields as blank
                            vol = (vol.equals("-1.0")) ? "" : vol;
                            concen = (concen.equals("-1.0")) ? "" : concen;
                            ccComment = (ccComment == null || ccComment.equalsIgnoreCase("null")) ? "" : ccComment;
                            sampleComments = (sampleComments == null || sampleComments.equalsIgnoreCase("null")) ? "" : sampleComments;


                            // row should be alpha
                            row = row.equalsIgnoreCase("1") ? "A"
                                    : row.equalsIgnoreCase("2") ? "B"
                                    : row.equalsIgnoreCase("3") ? "C"
                                    : row.equalsIgnoreCase("4") ? "D"
                                    : row.equalsIgnoreCase("5") ? "E"
                                    : row.equalsIgnoreCase("6") ? "F"
                                    : row.equalsIgnoreCase("7") ? "G"
                                    : row.equalsIgnoreCase("8") ? "H"
                                    : row.equalsIgnoreCase("9") ? "I"
                                    : row.equalsIgnoreCase("10") ? "J"
                                    : row.equalsIgnoreCase("11") ? "K"
                                    : row.equalsIgnoreCase("12") ? "L" : row;

                            p.println(ar[1] + "\t" + ar[0] + "\t" + sampleName + "\t" + materialType + "\t" + contIsStock + "\t" + sampleType + "\t" + sampleComments + "\t" + vol + "\t" + concen + "\t" + ccComment + "\t" + containerType + "\t" + containerName + "\t" + row + "\t" + col);

                        }
                    } else {
                        p.println(ar[1] + "\t" + ar[0]);
                        e.println("No sample for " + ar[0] + ", cohortName = " + ar[1]);
                    }
                }
            }

            e.println("Number of Lines Parsed From File (incl header): " + subjectCountFromFile);
            e.println("Number of Subjects with WGA Found: " + countWGAFound);
            e.println("Number of Subjects without WGA but with tube found: " + countNoWGAButTubeFound);
            e.println("Number of Subjects without WGA or tube found: " + countNoWGAOrTubeFound);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    private void getSamplesWithoutWGA(String fileDir) {
        try {
            //1.) build list of all samples in files
            HashSet<String> sampleList = new HashSet();
            String[] elem = {};
            String lineStr = "";
            String prefix;
            List subjectsToFetch = new LinkedList();


            String dateTime = icapture.com.Util.getCurrentDateTime();

            FileOutputStream out; // declare a file output object
            PrintStream p; // declare a print stream object
            out = new FileOutputStream(fileDir + "\\out" + dateTime + ".txt");
            // Connect print stream to the output stream
            p = new PrintStream(out);

            FileOutputStream err; // declare a file output object
            PrintStream e; // declare a print stream object
            err = new FileOutputStream(fileDir + "\\err" + dateTime + ".txt");
            // Connect print stream to the output stream
            e = new PrintStream(err);


            // get subjects from every input file
            File files = new File(fileDir + "\\current");
            for (File file : files.listFiles()) {
                if (file.getName().endsWith("-input.txt")) {
                    //parse file of subjects to check
                    ArrayList<String> fileContents = Tools.readFile(file);
                    subjectsToFetch = new LinkedList();
                    for (String line : fileContents) {
                        lineStr = line;
                        elem = line.split("\t");  //split using tab char
                        if (elem.length > 0) {
                            String cohortName = (elem.length > 0) ? elem[0].trim() : "";
                            String subjectName = (elem.length > 1) ? elem[1].trim() : "";

                            String[] ar = {subjectName, cohortName};
                            subjectsToFetch.add(ar);
                        }
                    }
                }
            }

            int subjectCountFromFile = subjectsToFetch.size();
            p.println("Cohort\tSubject ID\tSample Name\tPhysical Type\tIs Stock\tSample Type\tSample Comments\tVolume (ul)\tConcentration (ng/ul)\tTube Comments\tContainer Type\tContainer Name\tRow\tColumn");

            // for every sample of every subject check if there are WGA samples available
            Iterator itr = subjectsToFetch.iterator();
            HashMap sampBySubj = new HashMap(2);
            HashMap contBySubj = new HashMap(2);
            HashMap uniqueSub = new HashMap(2);
            String subjectID = null;
            String subjectName = "";
            int subjectCount = 0;
            int sampleCount = 0;
            int countWGAFound = 0;
            int countNoWGAOrTubeFound = 0;
            int countNoWGAButTubeFound = 0;
            while (itr.hasNext()) {
                String[] ar = (String[]) itr.next();
                String cohortTag = (ar.length > 1) ? ar[1] : "";

                // get subject (skip duplicates)
                if (!subjectName.equals(ar[0])) {
                    subjectName = ar[0];
                    uniqueSub.put("subjectName", "'" + ar[0] + "'");

                    String cohortID = null;
                    if (ar[1].equalsIgnoreCase("Az")) {
                        cohortID = "3";
                    } else if (ar[1].equalsIgnoreCase("CAPPS")) {
                        cohortID = "3";
                    } else if (ar[1].equalsIgnoreCase("SAGE")) {
                        cohortID = "4";
                    }
                    if (cohortID != null) {
                        uniqueSub.put("cohort.cohortID", cohortID);
                        Subject subject = (Subject) userHttpSess.getObjectByUniqueKey(Subject.class, uniqueSub);
                        if (subject == null) {
                            e.println("No subject for " + ar[0] + ", cohortName = " + ar[1]);
                            continue;
                        }
                        subjectID = subject.getSubjectID();
                        subjectCount++;


                        // get all samples for subject
                        sampBySubj.put("subject.subjectID", subjectID);
                        List<Sample> samples = userHttpSess.getObjectsOfClass(Sample.class, sampBySubj);
                        Iterator sampIter = samples.iterator();
                        while (sampIter.hasNext()) {
                            sampleCount++;
                            Sample sample = (Sample) sampIter.next();
                            contBySubj.put("sample.sampleID", sample.getSampleID());
                            contBySubj.put("materialType", "2");
                            contBySubj.put("container.isStock", "1");
                            List<ContainerContent> contents = userHttpSess.getObjectsOfClass(ContainerContent.class, contBySubj);
                            // if has WGA samples, check the volume otherwise get the stock tubes
                            if (contents != null && contents.size() != 0 && !contents.isEmpty()) {
                                boolean sufficientVol = false;
                                Iterator itWGA = contents.iterator();
                                while (itWGA.hasNext() && !sufficientVol) {
                                    ContainerContent content = (ContainerContent) itWGA.next();
                                    if (Double.parseDouble(content.getVolume()) > 20) {
                                        sufficientVol = true;
                                        e.println("Subject:\t" + content.getSample().getSubject().getCohort().getDescription() + content.getSample().getSubject().getSubjectName() + "\t Sample:\t" + content.getSample().getSampleName() + " cc-Comments:\t" + content.getComments() + "\t sample-Comments:\t" + content.getSample().getComments());
                                    }
                                }
                                // if have WGA w/ sufficient vol, you're done
                                if (sufficientVol) {
                                    countWGAFound++;
                                    continue;
                                }
                            }
                            // if no WGA sample or none with enough volume, get tubes to use in amplification
                            HashMap subjTubes = new HashMap();
                            subjTubes.put("sample", sample.getSampleID());
                            subjTubes.put("materialType", "1");
                            subjTubes.put("container.containerType", "7");
                            List<ContainerContent> tubes = userHttpSess.getObjectsOfClass(ContainerContent.class, subjTubes);
                            if (tubes == null || tubes.size() == 0 || tubes.isEmpty()) {
                                //e.println("hash:  " + subjTubes);
                                e.println("No WGA or tube samples for subject " + ar[1] + ar[0] + " (dbID:" + subjectID + ")");
                                countNoWGAOrTubeFound++;
                                continue;
                            }

                            Iterator iter = tubes.iterator();
                            countNoWGAButTubeFound++;

                            while (iter.hasNext()) {
                                ContainerContent cc = (ContainerContent) iter.next();
                                String sampleName = cc.getSample().getSampleName();
                                String sampleType = (cc.getSample().getSampleType() != null) ? cc.getSample().getSampleType().getDescription() : "";
                                String containerTypeID = (cc.getContainer() == null) ? "" : cc.getContainer().getContainerType().getContainerTypeID();
                                String containerType = (cc.getContainer() == null) ? "" : cc.getContainer().getContainerType().getDescription();
                                String containerName = (cc.getContainer() == null) ? "" : cc.getContainer().getcontainerName();
                                String vol = cc.getVolume();
                                String concen = cc.getConcentration();
                                String ccComment = cc.getComments();
                                String row = cc.getRow();
                                String col = cc.getColumn();
                                String sampleComments = cc.getSample().getComments();
                                String materialType = cc.getMaterialType().getDescription();
                                String contIsStock = (cc.getContainer() == null) ? "" : cc.getContainer().getIsStock();
                                contIsStock = (contIsStock.equals("0")) ? "No" : (contIsStock.equals("1")) ? "Yes" : "";


                                // show unknown fields as blank
                                vol = (vol.equals("-1.0")) ? "" : vol;
                                concen = (concen.equals("-1.0")) ? "" : concen;
                                ccComment = (ccComment == null || ccComment.equalsIgnoreCase("null")) ? "" : ccComment;
                                sampleComments = (sampleComments == null || sampleComments.equalsIgnoreCase("null")) ? "" : sampleComments;


                                // row should be alpha
                                row = row.equalsIgnoreCase("1") ? "A"
                                        : row.equalsIgnoreCase("2") ? "B"
                                        : row.equalsIgnoreCase("3") ? "C"
                                        : row.equalsIgnoreCase("4") ? "D"
                                        : row.equalsIgnoreCase("5") ? "E"
                                        : row.equalsIgnoreCase("6") ? "F"
                                        : row.equalsIgnoreCase("7") ? "G"
                                        : row.equalsIgnoreCase("8") ? "H"
                                        : row.equalsIgnoreCase("9") ? "I"
                                        : row.equalsIgnoreCase("10") ? "J"
                                        : row.equalsIgnoreCase("11") ? "K"
                                        : row.equalsIgnoreCase("12") ? "L" : row;

                                p.println(ar[1] + "\t" + ar[0] + "\t" + sampleName + "\t" + materialType + "\t" + contIsStock + "\t" + sampleType + "\t" + sampleComments + "\t" + vol + "\t" + concen + "\t" + ccComment + "\t" + containerType + "\t" + containerName + "\t" + row + "\t" + col);

                            }
                        }
                    } else {
                        p.println(ar[1] + "\t" + ar[0]);
                        e.println("No sample for " + ar[0] + ", cohortName = " + ar[1]);
                    }
                }
            }
            e.println("Number of Lines Parsed From File (incl header): " + subjectCountFromFile);
            e.println("Number of Subjects Found From File: " + subjectCount);
            e.println("Number of Samples found for Subjects: " + sampleCount);
            e.println("Number of Samples with WGA Found: " + countWGAFound);
            e.println("Number of Samples without WGA but with tube found: " + countNoWGAButTubeFound);
            e.println("Number of Samples without WGA or tube found: " + countNoWGAOrTubeFound);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
        }
    }

    private void getContentIDs(String fileDir) {
        try {
            UserHttpSess httpSess = new UserHttpSess("c:/temp/log");

            String[] elem;
            FileOutputStream out; // declare a file output object
            FileOutputStream err; // declare a file output object
            PrintStream p; // declare a print stream object
            PrintStream e; // declare a print stream object

            String dateTime = icapture.com.Util.getCurrentDateTime();
            out = new FileOutputStream(fileDir + "\\outContent" + dateTime + ".txt");
            err = new FileOutputStream(fileDir + "\\errContent" + dateTime + ".txt");

            // Connect print stream to the output stream
            p = new PrintStream(out);
            e = new PrintStream(err);

            int one = 0, two = 0;

            File files = new File(fileDir + "\\input");
            for (File file : files.listFiles()) {
                if (file.getName().endsWith(".txt")) {
                    //parse it
                    ArrayList<String> fileContents = Tools.readFile(file);
                    for (String line : fileContents) {
                        elem = line.split("\t");  //split using tab char
                        if (elem.length <= 0) {
                            continue;
                        }
                        // all containerContent fields (to be used in writing insert/update)
                        String containerID = null;
                        String row = null;
                        String column = null;
                        String sampleID = null;

                        // grab value to determine needed values
                        String fileID = (elem.length > 1) ? elem[1].trim() : "";
                        String sampleName = (elem.length > 2) ? elem[2].trim() : "";
                        String containerName = (elem.length > 3) ? elem[3].trim() : "";
                        String well = (elem.length > 4) ? elem[4].trim() : "";


                        // ----- get containerID ---------
                        if (containerName.length() <= 0) {
                            e.println("No container name for sample" + sampleName);
                            continue;
                        }
                        HashMap contName = new HashMap(3);
                        contName.put("containerName", "'" + containerName + "'");
                        Container container = (Container) httpSess.getObjectByUniqueKey(Container.class, contName);
                        if (container == null) {
                            e.println("Could not find container for name = " + containerName);
                            continue;
                        }
                        containerID = container.getContainerID();
                        // ----------------------------

                        //------get row & column-------
                        if (well.length() < 2) {
                            e.println("Well < 2 char for sample = " + sampleName + ", well=" + well);
                            continue;
                        }
                        row = String.valueOf(well.charAt(0));
                        if (!Character.isDigit(well.charAt(0))) {
                            row = (row.equalsIgnoreCase("a") ? "1" : row.equalsIgnoreCase("b") ? "2" : row.equalsIgnoreCase("c") ? "3" : row.equalsIgnoreCase("d") ? "4" : row.equalsIgnoreCase("e") ? "5" : row.equalsIgnoreCase("f") ? "6" : row.equalsIgnoreCase("g") ? "7" : row.equalsIgnoreCase("h") ? "8" : row.equalsIgnoreCase("i") ? "9" : row.equalsIgnoreCase("j") ? "10" : row.equalsIgnoreCase("k") ? "11" : row.equalsIgnoreCase("l") ? "12" : row.equalsIgnoreCase("m") ? "13" : row.equalsIgnoreCase("n") ? "14" : row.equalsIgnoreCase("o") ? "15" : row.equalsIgnoreCase("p") ? "16" : row.equalsIgnoreCase("q") ? "17" : row.equalsIgnoreCase("r") ? "18" : row.equalsIgnoreCase("s") ? "19" : row.equalsIgnoreCase("t") ? "20" : row.equalsIgnoreCase("u") ? "21" : row.equalsIgnoreCase("v") ? "22" : row.equalsIgnoreCase("w") ? "23" : row.equalsIgnoreCase("x") ? "24" : row.equalsIgnoreCase("y") ? "25" : row.equalsIgnoreCase("z") ? "26" : row);
                        }
                        column = well.substring(1);
                        // ----------------------------

                        // --------get sampleID--------
                        if (sampleName.length() <= 0 || sampleName.equals("0") || row == null || column == null) {
                            e.println("Issues for sample = " + sampleName);
                            continue;
                        }
                        // this assumes sample names are unique
                        HashMap sampName = new HashMap(1);
                        sampName.put("sampleName", "'" + sampleName + "'");
                        Sample sample = (Sample) httpSess.getObjectByUniqueKey(Sample.class, sampName);
                        if (sample == null) {
                            e.println("Could not find sample for ID = " + sampleName);
                            continue;
                        }
                        sampleID = sample.getSampleID();

                        if (sampleName.length() <= 0) {
                            e.println("sample name legnth <=0 for ID = " + sampleName);
                            continue;
                        }

                        // fetch the container content
                        HashMap cc = new HashMap(1);
                        cc.put("container.containerID", containerID);
                        cc.put("row", row);
                        cc.put("column", column);
                        cc.put("sample.sampleID", sampleID);
                        String contentsID = "";
                        ContainerContent ccO = (ContainerContent) httpSess.getObjectByUniqueKey(ContainerContent.class, cc);
                        if (ccO != null) {
                            //e.println(sampleName+"\t"+containerName+"\t"+well+" exists");
                            one++;
                            contentsID = ccO.getContainerContentsID();
                            p.println(contentsID + "\t" + fileID + "\t" + sampleName + "\t" + containerName + "\t" + well);
                        } else {
                            p.println(contentsID + "\t" + fileID + "\t" + sampleName + "\t" + containerName + "\t" + well);
                            e.println("No existing sample at \t" + containerName + "\t" + well + "\t(" + sampleName + ")");
                            two++;
                        }

                    }
                }
                e.println(one + " existed. " + two + " didn't.");
            }
            p.close();
            e.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
