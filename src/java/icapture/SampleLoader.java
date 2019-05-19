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
import java.util.*;
import icapture.com.UserHttpSess;
import icapture.hibernate.Cohort;
import icapture.hibernate.Sample;
import icapture.hibernate.SampleType;
import icapture.hibernate.Subject;
import icapture.hibernate.Container;
import icapture.hibernate.ContainerContent;
import icapture.hibernate.Control;

/**
 *
 * @author tvanrossum
 */
public class SampleLoader {

    UserHttpSess userHttpSess = null;

    public static void main(String[] args) {

        UserHttpSess httpSess = new UserHttpSess("c:/temp/log");
        SampleLoader sampleLoader = new SampleLoader(httpSess);
        //sampleLoader.checkIfSubjectsFromExcelAreInDB("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\textFiles");
        //sampleLoader.writeSampleInserts("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\textFiles");
        //sampleLoader.writeSampleInserts("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Updating");
        //sampleLoader.writeNewContainerInserts("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Updating");
        //sampleLoader.writeNormalContainerInserts("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\haveContainerData\\normal");
        //sampleLoader.writeNormalContainerInserts("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\haveContainerData\\normal");
        //sampleLoader.writeSemiNormalContainerInserts("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\haveContainerData\\semiNormal");
        //sampleLoader.updateSampleType("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Wells\\haveContainers\\current");
        //sampleLoader.writeTubeContainerInserts("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\haveContainerData\\current");
        //sampleLoader.writeNormalWellInserts("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Wells\\haveContainers\\current");
        //sampleLoader.checkIfSubjectsFromExcelAreInDB("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Wells\\haveContainers\\current");
        //sampleLoader.writeNewContainerInserts("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Wells\\2nd try\\current");
        //sampleLoader.writeContainerNameUpdates("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Wells\\2nd try\\current");
        //sampleLoader.writeNewWells("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Wells\\2nd try\\current");
        //sampleLoader.printAllContainers();
        //sampleLoader.writeNewWellInserts("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Wells\\2nd try\\current");
        //sampleLoader.writeNewWellInserts("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Wells\\2nd try\\noContainers");
        //sampleLoader.updateSampleTypes("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Updating");
        //sampleLoader.updateSampleTypeMultipleTypes("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\SampleType-dateCollected-Jul21\\current");
        //sampleLoader.updateSampleQC("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\QC_field\\current");
        //sampleLoader.updateDatesOfCollection("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\DateOfCollection-Extraction\\current");
        //sampleLoader.updateDatesOfExtractionCAPPSABs("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\DateOfCollection-Extraction\\current");
        //sampleLoader.checkFileContents("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\LookUps\\CNG Samples For Genotyping\\current");
        //sampleLoader.updateWellsUniqueSampleIDs("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Updating");
        //sampleLoader.insertControlWells("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Updating");
        //sampleLoader.updateWellsUsingIDs("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Updating");
        //sampleLoader.writeContainerInserts("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Updating");
        //sampleLoader.writeSampleInserts("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Updating");
        //sampleLoader.writeContentsUniqueSampleIDs("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Updating");
        //sampleLoader.updateWellsUsingIDs("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Updating");
        sampleLoader.writeGenotypingRunSampleStatus("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Updating");
    }

    public SampleLoader(UserHttpSess userHttpSess) {
        this.userHttpSess = userHttpSess;
    }

    private void checkSampleExistsIfFiles(String fileDir) {
        try {
            //1.) build list of all samples in files
            HashSet<String> sampleList = new HashSet();
            String[] elem;
            String prefix;
            /*
            File files = new File(fileDir);
            for(File file:files.listFiles()){
            if(file.getName().endsWith(".txt")){
            //parse it
            ArrayList<String> fileContents = Tools.readFile(file);
            for(String line:fileContents){
            elem = line.split("\t");  //split using tab char
            if(elem.length>0){
            sampleList.add(elem[0]);}
            }
            }
            }

            //get all our existing db samples
            List<Sample> dbSampleList =  userHttpSess.getAllSamples(null, -1);
            for(Sample sample:dbSampleList){
            Cohort cohort = sample.getSubject().getCohort();
            if(cohort.getDescription().equalsIgnoreCase("CAPPS")){
            prefix="Az";
            }else{
            prefix="SAGE";
            }
            if(!sampleList.contains(prefix + sample.getSampleName())){
            System.out.println("DB sample - "+prefix + sample.getSampleName()+" - "+ sample.getSampleID() + " does not exist" );
            }
            }
             */
            /*
            //check that all subjects in the spreadsheets are existing db subjects
            List<Subject> dbSubjectList = userHttpSess.getAllSubjects(-1);
            HashMap h = new HashMap();
            for(Subject subject:dbSubjectList){
            Cohort cohort = subject.getCohort();
            prefix = cohort.getDescription();
            if(cohort.getDescription().equalsIgnoreCase("CAPPS")){
            prefix="Az";
            }else if (cohort.getDescription().equalsIgnoreCase("SAGE")){
            prefix="SAGE";
            }
            h.put(prefix + subject.getSubjectName(), subject.getSubjectID());

            //System.out.println("DB subject - "+prefix + subject.getSubjectName()+" - "+ subject.getSubjectID());

            }
            File files = new File(fileDir);
            for(File file:files.listFiles()){
            if(file.getName().endsWith(".txt")){
            //parse it
            ArrayList<String> fileContents = Tools.readFile(file);
            for(String line:fileContents){
            elem = line.split("\t");  //split using tab char
            if(elem.length>0){
            String id = elem[0];
            if(id.length()>5
            && !id.equalsIgnoreCase("blank")
            && !id.equalsIgnoreCase("sampleID")
            && !id.equalsIgnoreCase("sample")){
            sampleList.add(id);
            if(id.endsWith("a") || id.endsWith("b")
            || id.endsWith("c") || id.endsWith("d")){
            id=id.substring(0, id.length()-1);
            }
            if(!h.containsKey(id)){
            System.out.println("DB sample - "+ id + " does not exist" );
            }
            }
            }
            }
            }
            }
             */
            //check that all samples in the spreadsheets are existing db samples
            List<Sample> dbSampleList = userHttpSess.getAllSamples(null, -1);
            HashMap h = new HashMap();
            for (Sample sample : dbSampleList) {
                Cohort cohort = sample.getSubject().getCohort();
                prefix = "";
                if (cohort.getDescription().equalsIgnoreCase("CAPPS")) {
                    prefix = "Az";
                } else {
                    prefix = "SAGE";
                }
                if (!prefix.equals("")) {
                    h.put(prefix + sample.getSampleName(), sample.getSampleID());
                }

                //System.out.println("DB subject - "+prefix + subject.getSubjectName()+" - "+ subject.getSubjectID());

            }
            File files = new File(fileDir);
            for (File file : files.listFiles()) {
                if (file.getName().endsWith(".txt")) {
                    //parse it
                    ArrayList<String> fileContents = Tools.readFile(file);
                    for (String line : fileContents) {
                        elem = line.split("\t");  //split using tab char
                        if (elem.length > 0) {
                            String id = elem[0];
                            if (id.length() > 5 && !id.equalsIgnoreCase("blank") && !id.equalsIgnoreCase("sampleID") && !id.equalsIgnoreCase("sample ID") && !id.equalsIgnoreCase("sample") && !id.equalsIgnoreCase("+ve ctrl")) {
                                //sampleList.add(id);
                                //if(id.endsWith("a") || id.endsWith("b")
                                //        || id.endsWith("c") || id.endsWith("d")){
                                //        id=id.substring(0, id.length()-1);
                                //}
                                if (!h.containsKey(id)) {
                                    System.out.println("DB sample - " + id + " does not exist");
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void checkIfSubjectsFromExcelAreInDB(String fileDir) {
        try {
            //1.) build list of all samples in files
            HashSet<String> sampleList = new HashSet();
            String[] elem;
            String prefix;

            //check that all subjects in the spreadsheets are existing db subjects
            List<Subject> dbSubjectList = userHttpSess.getAllSubjects(-1);
            HashMap h = new HashMap();
            for (Subject subject : dbSubjectList) {
                Cohort cohort = subject.getCohort();
                prefix = cohort.getDescription();
                if (cohort.getDescription().equalsIgnoreCase("CAPPS")) {
                    prefix = "Az";
                } else if (cohort.getDescription().equalsIgnoreCase("SAGE")) {
                    prefix = "SAGE";
                }
                h.put(prefix + subject.getSubjectName(), subject.getSubjectID());

                //System.out.println("DB subject - "+prefix + subject.getSubjectName()+" - "+ subject.getSubjectID());

            }
            File files = new File(fileDir);
            for (File file : files.listFiles()) {
                if (file.getName().endsWith(".txt")) {
                    //parse it
                    ArrayList<String> fileContents = Tools.readFile(file);
                    for (String line : fileContents) {
                        elem = line.split("\t");  //split using tab char
                        if (elem.length > 0) {
                            String id = elem[0];
                            if (id.length() > 5 && !id.equalsIgnoreCase("blank") && !id.equalsIgnoreCase("sampleID") && !id.equalsIgnoreCase("sample")) {
                                sampleList.add(id);
                                if (id.endsWith("a") || id.endsWith("b") || id.endsWith("c") || id.endsWith("d")) {
                                    id = id.substring(0, id.length() - 1);
                                }
                                if (!h.containsKey(id)) {
                                    System.out.println("excel subject - " + id + " does not exist");
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void checkIfSampleFromExcelIsInDB(String fileDir) {
        try {
            //1.) build list of all samples in files
            HashSet<String> sampleList = new HashSet();
            String[] elem;
            String prefix;

            //check that all samples in the spreadsheets are existing db samples
            List<Sample> dbSampleList = userHttpSess.getAllSamples(null, -1);
            HashMap h = new HashMap();
            for (Sample sample : dbSampleList) {
                Cohort cohort = sample.getSubject().getCohort();
                prefix = "";
                if (cohort.getDescription().equalsIgnoreCase("CAPPS")) {
                    prefix = "Az";
                } else {
                    prefix = "SAGE";
                }
                if (!prefix.equals("")) {
                    h.put(prefix + sample.getSampleName(), sample.getSampleID());
                }

                //System.out.println("DB subject - "+prefix + subject.getSubjectName()+" - "+ subject.getSubjectID());

            }
            File files = new File(fileDir);
            for (File file : files.listFiles()) {
                if (file.getName().endsWith(".txt")) {
                    //parse it
                    ArrayList<String> fileContents = Tools.readFile(file);
                    for (String line : fileContents) {
                        elem = line.split("\t");  //split using tab char
                        if (elem.length > 0) {
                            String id = elem[0];
                            if (id.length() > 5 && !id.equalsIgnoreCase("blank") && !id.equalsIgnoreCase("sampleID") && !id.equalsIgnoreCase("sample ID") && !id.equalsIgnoreCase("sample") && !id.equalsIgnoreCase("+ve ctrl")) {
                                //sampleList.add(id);
                                //if(id.endsWith("a") || id.endsWith("b")
                                //        || id.endsWith("c") || id.endsWith("d")){
                                //        id=id.substring(0, id.length()-1);
                                //}
                                if (!h.containsKey(id)) {
                                    System.out.println("DB sample - " + id + " does not exist");
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * writes insert statements for samples
     * @param fileDir where the input file is
     */
    private void writeSampleInserts(String fileDir) {
        try {
            UserHttpSess httpSess = new UserHttpSess("c:/temp/log");

            FileOutputStream out; // declare a file output object
            FileOutputStream err; // declare a file output object
            PrintStream p; // declare a print stream object
            PrintStream e; // declare a print stream object

            String dateTime = icapture.com.Util.getCurrentDateTime();
            out = new FileOutputStream(fileDir + "\\out" + dateTime + ".txt");
            err = new FileOutputStream(fileDir + "\\err" + dateTime + ".txt");

            // Connect print stream to the output stream
            p = new PrintStream(out);
            e = new PrintStream(err);

            //1.) build list of all samples in files
            HashSet<String> sampleList = new HashSet();
            String[] elem;
            String prefix;

            File files = new File(fileDir + "\\current");
            for (File file : files.listFiles()) {
                if (file.getName().endsWith(".txt")) {
                    //parse it
                    ArrayList<String> fileContents = Tools.readFile(file);
                    for (String line : fileContents) {
                        elem = line.split("\t");  //split using tab char
                        if (elem.length > 0) {
                            String subjectID = null;
                            String sampleName = null;
                            String valid = "1";
                            //String numcalls = null;
                            //String location = null;
                            //String numnocalls = null;
                            //String freezerLocation = null;
                            //String concentration = null;
                            String parent = "null";
                            String sampleTypeID = "23";
                            //String sampleprocessid = null;
                            String parentID = null;
                            String collectionDate = "\'0001-01-01\'"; // field is mistakenly 'not null'
                            String comments = null;
                            //String sampletypeyear1ID = null;
                            //String extractiondateyear1 = null;
                            //String collectiondateyear1 = null;
                            String extractionDate = "null";
                            String createdby = "1";
                            String created = "default";
                            //String modified_by = null;
                            //String modified = null;

                            String cohort = (elem.length > 0) ? elem[0] : "";
                            String subjectName = (elem.length > 1) ? elem[1] : "";
                            //subjectID = (elem.length > 2) ? elem[2] : "";
                            sampleName = (elem.length > 2) ? elem[2] : "";
                            //extractionDate = (elem.length > 5) ? "'"+elem[5]+"'" : "";
                            //extractionDate = (extractionDate.equals("''"))? "null":extractionDate;
                            //String extractedBy = (elem.length > 6) ? "Extracted by " + elem[6] + "." : "";
                            //sampleTypeID = (elem.length > 7) ? elem[7] : "";
                            //sampleTypeID = (sampleTypeID.equals(""))? "null":sampleTypeID;
                            //valid = (elem.length > 8) ? elem[8] : "";
                            comments = (elem.length > 7) ? "'" + elem[7] + "'" : "";
                            //comments = (extractedBy.equals(""))? "'"+comments+"'":"'"+comments+" "+extractedBy+"'";




                            // ------------- get subject ID-------------
                            if (subjectName.length() <= 0) {
                                e.println("Issues for subject = " + subjectName);
                                continue;
                            }
                            // this assumes subject names are unique
                            HashMap subjName = new HashMap(1);
                            subjName.put("subjectName", "'" + subjectName + "'");
                            subjName.put("cohort.description", "'" + cohort + "'");
                            Subject subject = (Subject) httpSess.getObjectByUniqueKey(Subject.class, subjName);
                            if (subject == null) {
                                e.println("Could not find subject for = " + subjectName);
                                continue;
                            }
                            subjectID = subject.getSubjectID();
                            // ---------------------------------------------


                            // -------- check if sample already exists -----
                            HashMap sampleHM = new HashMap(2);
                            sampleHM.put("sampleName", "'" + sampleName + "'");
                            sampleHM.put("subject.subjectID", subjectID);
                            Sample sample = (Sample) httpSess.getObjectByUniqueKey(Sample.class, sampleHM);
                            if (sample != null) {
                                e.println("Found that sample already exists for sample: " + sampleName + " subject: " + subjectName);
                                continue;
                            }
                            // -----------------------------------------------

                            if (!sampleName.equalsIgnoreCase("blank")
                                    && !sampleName.equalsIgnoreCase("sampleID")
                                    && !sampleName.equalsIgnoreCase("sample ID")
                                    && !sampleName.equalsIgnoreCase("sample")
                                    && !sampleName.equalsIgnoreCase("+ve ctrl")
                                    && subjectID != null && !subjectID.equals("")) {

                                p.println("INSERT INTO allergen.tblsamples "
                                        + "(subjectid,name,valid,parentID,sampletypeID,"
                                        + "collectiondate,comments,extractiondate,"
                                        + "created_by,created) "
                                        + "VALUES (" + subjectID + ",'" + sampleName + "',"
                                        + valid + "," + parent + "," + sampleTypeID + ","
                                        + collectionDate + "," + comments + "," + extractionDate
                                        + "," + createdby + "," + created + ");");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void writeNormalWellInserts(String fileDir) {
        try {
            UserHttpSess httpSess = new UserHttpSess("c:/temp/log");

            //1.) build list of all samples in files
            HashSet<String> sampleList = new HashSet();
            String[] elem;
            String prefix;
            FileOutputStream out; // declare a file output object
            FileOutputStream err; // declare a file output object
            PrintStream p; // declare a print stream object
            PrintStream e; // declare a print stream object

            out = new FileOutputStream(fileDir + "\\out.txt");
            err = new FileOutputStream(fileDir + "\\err.txt");

            // Connect print stream to the output stream
            p = new PrintStream(out);
            e = new PrintStream(err);



            File files = new File(fileDir);
            for (File file : files.listFiles()) {
                if (file.getName().endsWith(".txt")) {
                    //parse it
                    ArrayList<String> fileContents = Tools.readFile(file);
                    String sampleName = "";
                    for (String line : fileContents) {
                        elem = line.split("\t");  //split using tab char
                        if (elem.length > 0) {

                            sampleName = (elem.length > 0) ? elem[0] : "";
                            String containerName = (elem.length > 1) ? elem[1] : "";
                            String well = (elem.length > 2) ? elem[2] : "";
                            String volume = (elem.length > 3) ? elem[3] : "";
                            String comments = "";
                            String dilution = "-1";
                            String concentration = "-1";

                            // get containerID
                            if (containerName.length() > 0) {
                                if (containerName.equals("(Denise) MTHFR SAGE/Asthma WGA 6Nov07WGA plate:")) {
                                    containerName = "(Denise) MTHFR SAGE/Asthma WGA 6Nov07WGA plate";
                                }
                                HashMap contName = new HashMap(3);
                                contName.put("containerName", "'" + containerName + "'");
                                Container container = (Container) httpSess.getObjectByUniqueKey(Container.class, contName);
                                if (container == null) {
                                    e.println("Could not find container for name = " + containerName);
                                } else {
                                    String containerID = container.getContainerID();


                                    //get row & column
                                    String row = String.valueOf(well.charAt(0));
                                    if (!Character.isDigit(well.charAt(0))) {
                                        row = (row.equalsIgnoreCase("a") ? "1" : row.equalsIgnoreCase("b") ? "2" : row.equalsIgnoreCase("c") ? "3" : row.equalsIgnoreCase("d") ? "4" : row.equalsIgnoreCase("e") ? "5" : row.equalsIgnoreCase("f") ? "6" : row.equalsIgnoreCase("g") ? "7" : row.equalsIgnoreCase("h") ? "8" : row.equalsIgnoreCase("i") ? "9" : row.equalsIgnoreCase("j") ? "10" : row.equalsIgnoreCase("k") ? "11" : row.equalsIgnoreCase("l") ? "12" : row.equalsIgnoreCase("m") ? "13" : row.equalsIgnoreCase("n") ? "14" : row.equalsIgnoreCase("o") ? "15" : row.equalsIgnoreCase("p") ? "16" : row.equalsIgnoreCase("q") ? "17" : row.equalsIgnoreCase("r") ? "18" : row.equalsIgnoreCase("s") ? "19" : row.equalsIgnoreCase("t") ? "20" : row.equalsIgnoreCase("u") ? "21" : row.equalsIgnoreCase("v") ? "22" : row.equalsIgnoreCase("w") ? "23" : row.equalsIgnoreCase("x") ? "24" : row.equalsIgnoreCase("y") ? "25" : row.equalsIgnoreCase("z") ? "26" : row);
                                    }
                                    String col = well.substring(1);

                                    // get sampleID
                                    if (sampleName.length() > 0 && !sampleName.equals("0")) {
                                        String cohortName = "";
                                        if (sampleName.startsWith("Az")) {
                                            sampleName = sampleName.substring(2, sampleName.length());
                                            cohortName = "CAPPS";
                                        }
                                        if (sampleName.startsWith("SAGE")) {
                                            sampleName = sampleName.substring(4, sampleName.length());
                                            cohortName = "SAGE";
                                        }
                                        HashMap sampName = new HashMap(1);
                                        sampName.put("sampleName", "'" + sampleName + "'");
                                        Sample sample = (Sample) httpSess.getObjectByUniqueKey(Sample.class, sampName);
                                        if (sample == null) {
                                            // could be 2+ samples in DB with that ID but different cohort subjects
                                            if (!cohortName.equals("")) {
                                                String subjectName = sampleName;
                                                if (sampleName.endsWith("a") || sampleName.endsWith("b") || sampleName.endsWith("c") || sampleName.endsWith("d")) {
                                                    subjectName = sampleName.substring(0, sampleName.length() - 1);
                                                }
                                                HashMap cohortHM = new HashMap(2);
                                                cohortHM.put("description", "'" + cohortName + "'");
                                                Cohort cohort = (Cohort) httpSess.getObjectByUniqueKey(Cohort.class, cohortHM);
                                                if (cohort == null) {
                                                    e.println("Could not find cohort for ID = " + cohortName);
                                                } else {
                                                    HashMap nameCohort = new HashMap(2);
                                                    nameCohort.put("subjectName", "'" + subjectName + "'");
                                                    nameCohort.put("cohort.cohortID", cohort.getCohortID());
                                                    Subject subject = (Subject) httpSess.getObjectByUniqueKey(Subject.class, nameCohort);
                                                    if (subject == null) {
                                                        e.println("Could not find subject for ID = " + subjectName + " cohort:" + cohort.getDescription());
                                                    } else {
                                                        String subjectID = subject.getSubjectID();
                                                        HashMap sampleHM = new HashMap(2);
                                                        sampleHM.put("sampleName", "'" + sampleName + "'");
                                                        sampleHM.put("subject.subjectID", subject);
                                                    }
                                                }
                                            } else {
                                                e.println("Could not find sample for ID = " + sampleName);
                                            }
                                        } else {
                                            String sampleID = sample.getSampleID();


                                            if (sampleName.length() > 0) {
                                                //System.out.println("sampleName:" + sampleName + " subjectName: "+subjectName);
                                                // if(h.containsKey(subjectName)){
                                                //System.out.println("Container*"+containerName+"*comments*"+comments);
                                                p.println("INSERT INTO allergen.tblcontainercontents "
                                                        + "(containerID,row,column,sampleID,volume,concentration,dilution,comments) "
                                                        + "VALUES (" + containerID + "," + row + "," + col + "," + sampleID + "," + volume + "," + concentration + ",'" + dilution + "','" + comments + "');");
                                                /*
                                                p.println("UPDATE allergen.tblcontainercontents SET dilution='1:100' " +
                                                "where containerID =" + containerID + " AND sampleID=" + sampleID +
                                                " AND row = "+row+" AND column = "+col+" AND dilution ='1:10';"
                                                );
                                                 */
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void writeContentsUniqueSampleIDs(String fileDir) {
        try {
            UserHttpSess httpSess = new UserHttpSess("c:/temp/log");

            String[] elem;
            FileOutputStream outI; // declare a file output object
            FileOutputStream outU; // declare a file output object
            FileOutputStream err; // declare a file output object
            PrintStream pI; // declare a print stream object
            PrintStream pU; // declare a print stream object
            PrintStream e; // declare a print stream object

            String dateTime = icapture.com.Util.getCurrentDateTime();
            outI = new FileOutputStream(fileDir + "\\outContentInsert" + dateTime + ".txt");
            outU = new FileOutputStream(fileDir + "\\outContentUpdate" + dateTime + ".txt");
            err = new FileOutputStream(fileDir + "\\errContent" + dateTime + ".txt");

            // Connect print stream to the output stream
            pI = new PrintStream(outI);
            pU = new PrintStream(outU);
            e = new PrintStream(err);

            int one = 0, two = 0, three = 0;
            boolean exists = false;

            File files = new File(fileDir + "\\current");
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
                        String volume = null;
                        String concentration = null;
                        String parentID = "-3"; // -3 unknown, -2 is N/A (like for stock tubes)
                        String contaminated = "0";
                        String dilution = "\'\'";
                        String comments = null;
                        String created = "default";
                        String createdBy = "1";
                        String quantified = null;
                        String materialTypeID = "1"; // 0 = unknown
                        String amplificationDate = "null";
                        String barcode = null;

                        // assign those you have values for
                        //quantified = (elem.length > 9) ? "'"+elem[9].trim()+"'" : "";
                        //quantified =(quantified.equals("''"))? "null":quantified;
                        concentration = (elem.length > 4) ? elem[4] : "";
                        volume = (elem.length > 5) ? elem[5] : "";
                        comments = (elem.length > 8) ? "'" + elem[8] + "'" : "";
                        comments = (comments.equals("''")) ? "null" : comments;

                        // grab value to determine needed values
                        String sampleName = (elem.length > 1) ? elem[1].trim() : "";
                        String containerName = (elem.length > 2) ? elem[2].trim() : "";
                        String well = (elem.length > 3) ? elem[3].trim() : "";

                        if (concentration == null || concentration.equals("")) {
                            concentration = "-1";
                        }
                        if (volume == null || volume.equals("")) {
                            volume = "-1";
                        }

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

                        // check if it already exsists -- for updates
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
                            exists = true;
                            contentsID = ccO.getContainerContentsID();
                            if (!(volume.equals(ccO.getVolume()) || ccO.getVolume().equals(volume + ".0"))) {
                                e.println(sampleName + "\t" + containerName + "\t" + well + "\toldVol=" + ccO.getVolume() + "\tnewVol=" + volume);
                            }
                            if (!((concentration + ".0").equals(ccO.getConcentration()) || concentration.equals(ccO.getConcentration()))) {
                                //e.println(sampleName+"\t"+containerName+"\t"+well+"\toldConcen="+ccO.getConcentration()+"\tnewConcen="+concentration);
                            }
                            //continue;
                        } else {
                            //  e.println("No existing sample at \t"+containerName+"\t"+well+"\t("+sampleName+")");
                            two++;
                        }
                        /*
                        //updates
                        //if(exists){
                        pI.println("UPDATE allergen.tblcontainercontents " +
                        "set row= "+row+",column="+column+", containerID="+containerID+
                        ", sampleID = " +sampleID +//", comment = "+comments+
                        ", concentration =" +concentration +", volume="+volume+
                        " where containercontentsid = "+ccID+"; ");

                        /*   pU.println("DELETE FROM allergen.tblcontainercontents "
                        + " where containerID = " + containerID + " AND row = " + row + " AND column = " + col + ";");
                         */
                        //}
                        // inserts

                        if (!exists) {
                            pI.println("Insert into allergen.tblcontainercontents "
                                    + "(containerID,row,column,sampleID,volume,concentration,parentID,contaminated,dilution,comments,created,created_By,quantified,materialTypeID,amplificationDate,barcode) "
                                    + " VALUES (" + containerID + ", " + row + ", " + column + ", " + sampleID + ", " + volume + ", " + concentration + ", " + parentID + ", " + contaminated + ", " + dilution + ", " + comments + ", " + created + ", " + createdBy + ", " + quantified + ", " + materialTypeID + ", " + amplificationDate + ", " + barcode + ");");
                        }
                    }
                }
                e.println(one + " existed. " + two + " didn't.");
            }
            pI.close();
            pU.close();
            e.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void writeGenotypingRunSampleStatus(String fileDir) {
        try {
            UserHttpSess httpSess = new UserHttpSess("c:/temp/log");

            String[] elem;
            FileOutputStream out; // declare a file output object
            FileOutputStream err; // declare a file output object
            PrintStream p; // declare a print stream object
            PrintStream e; // declare a print stream object

            String dateTime = icapture.com.Util.getCurrentDateTime();
            out = new FileOutputStream(fileDir + "\\out" + dateTime + ".txt");
            err = new FileOutputStream(fileDir + "\\err" + dateTime + ".txt");

            // Connect print stream to the output stream
            p = new PrintStream(out);
            e = new PrintStream(err);

            int one = 0, two = 0, three = 0;
            boolean exists = false;

            ArrayList containersDone = new ArrayList();

            File files = new File(fileDir + "\\current");
            for (File file : files.listFiles()) {
                if (file.getName().endsWith(".txt")) {
                    //parse it
                    ArrayList<String> fileContents = Tools.readFile(file);
                    for (String line : fileContents) {
                        elem = line.split("\t");  //split using tab char
                        if (elem.length <= 0) {
                            continue;
                        }
                        // all GenotypingRunSampleStatus fields (to be used in writing insert/update)
                        String genotypingRunID = null;
                        String containerContentsID = null;
                        String valid = null;
                        String callrate = null;

                        // get values from input
                        callrate = (elem.length > 12) ? elem[12].trim() : "";
                        valid = "1";
                        genotypingRunID = "7";

                        // grab value to determine needed values
                        String sampleName = (elem.length > 1) ? elem[1].trim() : "";
                        String containerName = (elem.length > 8) ? elem[8].trim() : "";
                        String well = (elem.length > 9) ? elem[9].trim() : "";

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
                        String containerID = container.getContainerID();
                        // ----------------------------

                        //------get row & column-------
                        if (well.length() < 2) {
                            e.println("Well < 2 char for sample = " + sampleName + ", well=" + well);
                            continue;
                        }
                        String row = String.valueOf(well.charAt(0));
                        if (!Character.isDigit(well.charAt(0))) {
                            row = (row.equalsIgnoreCase("a") ? "1" : row.equalsIgnoreCase("b") ? "2" : row.equalsIgnoreCase("c") ? "3" : row.equalsIgnoreCase("d") ? "4" : row.equalsIgnoreCase("e") ? "5" : row.equalsIgnoreCase("f") ? "6" : row.equalsIgnoreCase("g") ? "7" : row.equalsIgnoreCase("h") ? "8" : row.equalsIgnoreCase("i") ? "9" : row.equalsIgnoreCase("j") ? "10" : row.equalsIgnoreCase("k") ? "11" : row.equalsIgnoreCase("l") ? "12" : row.equalsIgnoreCase("m") ? "13" : row.equalsIgnoreCase("n") ? "14" : row.equalsIgnoreCase("o") ? "15" : row.equalsIgnoreCase("p") ? "16" : row.equalsIgnoreCase("q") ? "17" : row.equalsIgnoreCase("r") ? "18" : row.equalsIgnoreCase("s") ? "19" : row.equalsIgnoreCase("t") ? "20" : row.equalsIgnoreCase("u") ? "21" : row.equalsIgnoreCase("v") ? "22" : row.equalsIgnoreCase("w") ? "23" : row.equalsIgnoreCase("x") ? "24" : row.equalsIgnoreCase("y") ? "25" : row.equalsIgnoreCase("z") ? "26" : row);
                        }
                        String column = well.substring(1);
                        //get rid of leading 0
                        if (column.indexOf("0") == 0) {
                            column = column.substring(1);
                        }
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
                        String sampleID = sample.getSampleID();

                        if (sampleName.length() <= 0) {
                            e.println("sample name legnth <=0 for ID = " + sampleName);
                            continue;
                        }

                        // get container content
                        HashMap cc = new HashMap(4);
                        cc.put("container.containerID", containerID);
                        cc.put("row", row);
                        cc.put("column", column);
                        cc.put("sample.sampleID", sampleID);
                        ContainerContent ccO = (ContainerContent) httpSess.getObjectByUniqueKey(ContainerContent.class, cc);
                        if (ccO != null) {
                            one++;
                            exists = true;
                            containerContentsID = ccO.getContainerContentsID();
                            //e.println(sampleName+"\t"+containerName+"\t"+row+"\t"+column+" exists");
                        } else {
                            e.println("No existing sample at \t" + containerName + "\t" + row + "\t" + column + "\t(" + sampleName + ", " + sampleID + ")");
                            e.println("No existing sample with contID:\t" + containerID + "\t row:" + row + "\t col:" + column + "\t sampID:" + sampleID);
                            two++;
                            continue;
                        }

                        // update
                        p.println("update allergen.tblGenotypingRunSampleStatus "
                                + "set callrate = " + callrate + " where "
                                + " containerContentsID =" + containerContentsID + " and "
                                + " GenotypingRunID = " + genotypingRunID + ";");


                        /*
                        // insert for tblGenotypingRunSampleStatus
                        p.println("Insert into allergen.tblGenotypingRunSampleStatus " +
                        "(GenotypingRunID,containerContentsID,valid,callRate) " +
                        " VALUES ("+genotypingRunID+", "+containerContentsID+", "+valid+", "+callrate+");");
                         */

                        // insert for tblGenotypingRuncontainers
                        /*
                        if(!containersDone.contains(containerID)){
                        /*p.println("Insert into allergen.tblGenotypingRuncontainers " +
                        "(GenotypingRunID,containerID) " +
                        " VALUES ("+genotypingRunID+", "+containerID+");");

                        p.println("update allergen.tblGenotypingRunContainers " +
                        "set GenotypingRunID = 7 where "+
                        " containerID ="+containerID+" and " +
                        " GenotypingRunID = 2;");
                        containersDone.add(containerID);
                        }
                         */
                    }
                }
                e.println(one + " existed. " + two + " didn't.");
            }
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void insertControlWells(String fileDir) {
        try {
            UserHttpSess httpSess = new UserHttpSess("c:/temp/log");

            String[] elem;
            FileOutputStream outI; // declare a file output object
            FileOutputStream outU; // declare a file output object
            FileOutputStream err; // declare a file output object
            PrintStream pI; // declare a print stream object
            PrintStream pU; // declare a print stream object
            PrintStream e; // declare a print stream object

            String dateTime = icapture.com.Util.getCurrentDateTime();
            outI = new FileOutputStream(fileDir + "\\outInsert" + dateTime + ".txt");
            err = new FileOutputStream(fileDir + "\\err" + dateTime + ".txt");

            // Connect print stream to the output stream
            pI = new PrintStream(outI);
            e = new PrintStream(err);

            int one = 0, two = 0, three = 0;

            File files = new File(fileDir + "\\current");
            for (File file : files.listFiles()) {
                if (file.getName().endsWith(".txt")) {
                    //parse it
                    ArrayList<String> fileContents = Tools.readFile(file);
                    String sampleName = "";
                    for (String line : fileContents) {
                        elem = line.split("\t");  //split using tab char
                        if (elem.length <= 0) {
                            continue;
                        }
                        String containerName = (elem.length > 0) ? elem[0] : "";
                        String well = (elem.length > 1) ? elem[1] : "";
                        String controlDesc = (elem.length > 2) ? elem[2] : "";

                        // get containerID
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
                        String containerID = container.getContainerID();

//get row & column
                        if (well.length() < 2) {
                            e.println("Well < 2 char for sample = " + sampleName + ", well=" + well);
                            continue;
                        }
                        String row = String.valueOf(well.charAt(0));
                        if (!Character.isDigit(well.charAt(0))) {
                            row = (row.equalsIgnoreCase("a") ? "1" : row.equalsIgnoreCase("b") ? "2" : row.equalsIgnoreCase("c") ? "3" : row.equalsIgnoreCase("d") ? "4" : row.equalsIgnoreCase("e") ? "5" : row.equalsIgnoreCase("f") ? "6" : row.equalsIgnoreCase("g") ? "7" : row.equalsIgnoreCase("h") ? "8" : row.equalsIgnoreCase("i") ? "9" : row.equalsIgnoreCase("j") ? "10" : row.equalsIgnoreCase("k") ? "11" : row.equalsIgnoreCase("l") ? "12" : row.equalsIgnoreCase("m") ? "13" : row.equalsIgnoreCase("n") ? "14" : row.equalsIgnoreCase("o") ? "15" : row.equalsIgnoreCase("p") ? "16" : row.equalsIgnoreCase("q") ? "17" : row.equalsIgnoreCase("r") ? "18" : row.equalsIgnoreCase("s") ? "19" : row.equalsIgnoreCase("t") ? "20" : row.equalsIgnoreCase("u") ? "21" : row.equalsIgnoreCase("v") ? "22" : row.equalsIgnoreCase("w") ? "23" : row.equalsIgnoreCase("x") ? "24" : row.equalsIgnoreCase("y") ? "25" : row.equalsIgnoreCase("z") ? "26" : row);
                        }
                        String col = well.substring(1);

                        // get controlID
                        if (controlDesc.length() <= 0 || controlDesc.equals("0") || row == null || col == null) {

                            e.println("Issues for control = " + controlDesc + containerName + well);
                            continue;
                        }
                        HashMap sampName = new HashMap(1);
                        sampName.put("description", "'" + controlDesc + "'");
                        Control control = (Control) httpSess.getObjectByUniqueKey(Control.class, sampName);
                        if (control == null) {
                            e.println("Could not find control for: " + controlDesc);
                            continue;
                        }
                        String controlID = control.getControlID();

                        pI.println("insert into ALLERGEN.TBLCONTROLWELLS "
                                + "(containerid,row,column,controlid,created,created_by) "
                                + "values (" + containerID + "," + row + "," + col + "," + controlID + ",default,0);");
                    }
                }
                e.println(one + " were already created. " + two + " were just now created.");
            }
            pI.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateWellsUsingIDs(String fileDir) {
        try {
            UserHttpSess httpSess = new UserHttpSess("c:/temp/log");

            //1.) build list of all samples in files
            HashSet<String> sampleList = new HashSet();
            String[] elem;
            String prefix;
            FileOutputStream outI; // declare a file output object
            FileOutputStream outU; // declare a file output object
            FileOutputStream err; // declare a file output object
            PrintStream pI; // declare a print stream object
            PrintStream pU; // declare a print stream object
            PrintStream e; // declare a print stream object

            String dateTime = icapture.com.Util.getCurrentDateTime();
            outI = new FileOutputStream(fileDir + "\\outInsert" + dateTime + ".txt");
            outU = new FileOutputStream(fileDir + "\\outUpdate" + dateTime + ".txt");
            err = new FileOutputStream(fileDir + "\\err" + dateTime + ".txt");

            // Connect print stream to the output stream
            pI = new PrintStream(outI);
            pU = new PrintStream(outU);
            e = new PrintStream(err);

            int one = 0, two = 0, three = 0;

            File files = new File(fileDir + "\\current");
            for (File file : files.listFiles()) {
                if (file.getName().endsWith(".txt")) {
                    //parse it
                    ArrayList<String> fileContents = Tools.readFile(file);
                    String sampleName = "";
                    for (String line : fileContents) {
                        elem = line.split("\t");  //split using tab char
                        if (elem.length <= 0) {
                            continue;
                        }
                        //String cohortTag = (elem.length > 0) ? elem[0] : "";
                        //String familyID = (elem.length > 0) ? elem[0] : "";
                        String contentsID = (elem.length > 0) ? elem[0] : "";
                        //String containerID = (elem.length > 1) ? elem[1] : "";
                        String containerName = (elem.length > 1) ? elem[1] : "";
                        String containerID = null;
                        String well = (elem.length > 2) ? elem[2] : "";
                        //String row = (elem.length > 2) ? elem[2] : "";
                        String row = null;
                        //String col = (elem.length > 3) ? elem[3] : "";
                        String col = null;
                        String sampleID = (elem.length > 4) ? elem[4] : "";
                        String volume = (elem.length > 4) ? elem[4] : "";
                        String concen = (elem.length > 3) ? elem[3] : "";
                        //String parentID = (elem.length > 7) ? elem[7] : "";
                        // String contaminted = (elem.length > 8) ? elem[8] : "";
                        //String dilution = (elem.length > 9) ? elem[9] : "";
                        String comments = (elem.length > 10) ? elem[10] : "";
                        String created = (elem.length > 11) ? elem[11] : "";

                        // get container ID
                        containerID = getContainerID(httpSess, containerName);
                        // get row/col from well
                        row = getRowColFromWell(httpSess, well)[0];
                        col = getRowColFromWell(httpSess, well)[1];


                        pU.println("UPDATE allergen.tblcontainercontents SET "
                                + "volume = " + volume //+ ", sampleID = "+sampleID
                                + ", concentration = " + concen //+", comments='"+comments
                                // +"', quantified= null,parentid=-2,contaminated=0,dilution='', "
                                + ", containerID = " + containerID + ", row = " + row
                                + ", column = " + col //+ ", created = '" + created+"' "
                                + " where containercontentsID= " + contentsID + ";");





                    }
                }
                e.println(one + " were already created. " + two + " were just now created.");
            }
            pI.close();
            pU.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * writes insert statements for containers
     * @param fileDir where the input file is
     */
    private void writeContainerInserts(String fileDir) {
        try {
            UserHttpSess httpSess = new UserHttpSess("c:/temp/log");

            String[] elem;
            FileOutputStream outCont; // declare a file output object
            FileOutputStream err; // declare a file output object
            PrintStream c; // declare a print stream object
            PrintStream e; // declare a print stream object

            String dateTime = icapture.com.Util.getCurrentDateTime();
            outCont = new FileOutputStream(fileDir + "\\outContainer" + dateTime + ".txt");
            err = new FileOutputStream(fileDir + "\\errContainer" + dateTime + ".txt");

            // Connect print stream to the output stream
            c = new PrintStream(outCont);
            e = new PrintStream(err);

            File files = new File(fileDir + "\\current");
            for (File file : files.listFiles()) {
                if (file.getName().endsWith(".txt")) {
                    e.println("Parsing file: " + file.getName());
                    //parse it
                    ArrayList<String> fileContents = Tools.readFile(file);
                    String containerName = "";
                    List contNames = new ArrayList();
                    for (String line : fileContents) {
                        elem = line.split("\t");  //split using tab char
                        if (elem.length > 8) {
                            containerName = (elem.length > 12) ? "'" + elem[12].trim() + "'" : "";
                            String containerTypeID = "0";
                            String freezerID = "null";
                            String shelf = "null";
                            String discarded = "0";
                            String shippedOut = "0";
                            String shippedDate = "null";
                            String shippedTo = "0";
                            String comments = "null";
                            String stock = "1";
                            String materialTypeID = "1";
                            String created = "default";
                            String createdBy = "1";
                            String valid = "1";
                            String containerAlias = "null";
                            String dateOnPlate = "null";
                            String initials = "null";
                            String lot = "1";
                            //check if this container's insert has aready been written
                            if (contNames.contains(containerName)) {
                                continue;
                            }
                            contNames.add(containerName);

                            // write insert
                            if (containerName.length() > 0 && !containerName.equalsIgnoreCase("plate id") && !containerName.equalsIgnoreCase("plateid")) {
                                c.println("INSERT INTO allergen.tblcontainers "
                                        + "(containername,containertypeid,freezerid,shelf,discarded,shippedout,shippedDate,shippedToID,comments, "
                                        + "stock, materialtypeid,created,created_by,valid,containerAlias,date,initials,lot) "
                                        + "VALUES (" + containerName + "," + containerTypeID + "," + freezerID + "," + shelf + ","
                                        + discarded + "," + shippedOut + "," + shippedDate + "," + shippedTo + "," + comments + ","
                                        + stock + "," + materialTypeID + "," + created + "," + createdBy + "," + valid + "," + containerAlias + ","
                                        + dateOnPlate + "," + initials + "," + lot + ");");
                            }
                            /*
                            // write update
                            if (containerName.length() > 0) {
                            c.println("UPDATE allergen.tblcontainers set " +
                            "containername ='" + containerName + "', comments='" + containerComments + "' " +
                            "where containerid=" + containerID + ";");
                            }*/
                        }
                    }
                }
            }
            c.close();
            e.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * writes insert statements for subjects
     * @param fileDir where the input file is
     */
    private void writeSubjectInserts(String fileDir) {
        try {
            UserHttpSess httpSess = new UserHttpSess("c:/temp/log");

            String[] elem;
            FileOutputStream outCont; // declare a file output object
            FileOutputStream err; // declare a file output object
            PrintStream c; // declare a print stream object
            PrintStream e; // declare a print stream object

            String dateTime = icapture.com.Util.getCurrentDateTime();
            outCont = new FileOutputStream(fileDir + "\\outSubject" + dateTime + ".txt");
            err = new FileOutputStream(fileDir + "\\errSubject" + dateTime + ".txt");

            // Connect print stream to the output stream
            c = new PrintStream(outCont);
            e = new PrintStream(err);

            File files = new File(fileDir + "\\current");
            for (File file : files.listFiles()) {
                if (file.getName().endsWith(".txt")) {
                    e.println("Parsing file: " + file.getName());
                    //parse it
                    ArrayList<String> fileContents = Tools.readFile(file);
                    List contNames = new ArrayList();
                    for (String line : fileContents) {
                        elem = line.split("\t");  //split using tab char
                        if (elem.length > 8) {
                            String subjectName = null;
                            String cohortID = null;
                            String familyID = null;
                            String gender = null;
                            String code = "-1";
                            String hasconsent = "1";
                            String motherid = "\'0\'";
                            String fatherid = "\'0\'";
                            String ethnicity = null;
                            String passedQC = "-1";
                            String numsamplescollected = "1";
                            String created = "default";
                            String createdBy = "1";
                            String comment = null;


                            subjectName = (elem.length > 0) ? "'" + elem[0].trim() + "'" : "";
                            cohortID = (elem.length > 2) ? elem[2].trim() : "";
                            familyID = (elem.length > 1) ? "'" + elem[1].trim() + "'" : "";
                            gender = (elem.length > 3) ? elem[3].trim() : "";
                            ethnicity = (elem.length > 4) ? elem[4].trim() : "";
                            ethnicity = (ethnicity.equals("")) ? "0" : ethnicity;
                            comment = (elem.length > 14) ? "'" + elem[14].trim() + "'" : "";

                            // write insert
                            if (subjectName.length() > 0) {
                                c.println("INSERT INTO allergen.tblsubject "
                                        + "(ID,cohortID,familyID,gender,code,hasconsent,"
                                        + "motherid,fatherid,ethnicityid,passedQC,"
                                        + "numsamplescollected,created,created_By,comment) "
                                        + "VALUES (" + subjectName + ", " + cohortID + ", " + familyID
                                        + ", " + gender + ", " + code + ", " + hasconsent + ", " + motherid
                                        + ", " + fatherid + ", " + ethnicity + ", " + passedQC + ", "
                                        + numsamplescollected + ", " + created + ", " + createdBy
                                        + ", " + comment + ");");
                            }
                            /*
                            // write update
                            if (containerName.length() > 0) {
                            c.println("UPDATE allergen.tblcontainers set " +
                            "containername ='" + containerName + "', comments='" + containerComments + "' " +
                            "where containerid=" + containerID + ";");
                            }*/
                        }
                    }
                }
            }
            c.close();
            e.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void writeContainerNameUpdates(String fileDir) {
        try {
            UserHttpSess httpSess = new UserHttpSess("c:/temp/log");

            //1.) build list of all samples in files
            HashSet<String> sampleList = new HashSet();
            String[] elem;
            String prefix;
            FileOutputStream outWell; // declare a file output object
            FileOutputStream outCont; // declare a file output object
            FileOutputStream err; // declare a file output object
            //PrintStream w; // declare a print stream object
            PrintStream c; // declare a print stream object
            PrintStream e; // declare a print stream object

            //outWell = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Samples\\haveContainers\\current\\outWell.txt");
            outCont = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Wells\\2nd try\\outCont.txt");
            err = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Wells\\2nd try\\err.txt");

            // Connect print stream to the output stream
            //w = new PrintStream(outWell);
            c = new PrintStream(outCont);
            e = new PrintStream(err);

            File files = new File(fileDir);
            for (File file : files.listFiles()) {
                if (file.getName().endsWith(".txt")) {
                    e.println("Parsing file: " + file.getName());
                    //parse it
                    ArrayList<String> fileContents = Tools.readFile(file);
                    for (String line : fileContents) {
                        elem = line.split("\t");  //split using tab char
                        if (elem.length > 1 && !elem[0].toString().equals("")) {
                            String containerName = (elem.length > 0) ? elem[0] : "";
                            containerName.trim();
                            String containerComments = (elem.length > 1) ? elem[1] : "";
                            containerComments.trim();
                            String containerID = (elem.length > 2) ? elem[2] : "";
                            containerID.trim();

                            // get containerID
                            if (containerName.length() > 0) {
                                c.println("UPDATE allergen.tblcontainers set "
                                        + "containername ='" + containerName + "', comments='" + containerComments + "' "
                                        + "where containerid=" + containerID + ";");


                            }
                        }
                    }
                }
            }
            c.close();
            e.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void writeNewWellInserts(String fileDir) {
        try {
            UserHttpSess httpSess = new UserHttpSess("c:/temp/log");

            //1.) build list of all samples in files
            HashSet<String> sampleList = new HashSet();
            String[] elem;
            String prefix;
            FileOutputStream outWell; // declare a file output object
            FileOutputStream outCont; // declare a file output object
            FileOutputStream err; // declare a file output object
            PrintStream w; // declare a print stream object
            PrintStream c; // declare a print stream object
            PrintStream e; // declare a print stream object

            outWell = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Wells\\2nd try\\outWells.txt");
            outCont = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Wells\\2nd try\\outCont.txt");
            err = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Wells\\2nd try\\err.txt");

            // Connect print stream to the output stream
            w = new PrintStream(outWell);
            c = new PrintStream(outCont);
            e = new PrintStream(err);

            File files = new File(fileDir);
            for (File file : files.listFiles()) {
                if (file.getName().endsWith(".txt")) {
                    //e.println("Parsing file: "+file.getName());
                    //parse it
                    ArrayList<String> fileContents = Tools.readFile(file);
                    for (String line : fileContents) {
                        elem = line.split("\t");  //split using tab char
                        if (elem.length > 0) {
                            String sampleName = (elem.length > 0) ? elem[0] : "";
                            String containerName = (elem.length > 1) ? elem[1] : "";
                            String well = (elem.length > 2) ? elem[2] : "";
                            String volume = (elem.length > 3) ? elem[3] : "";
                            String dilution = (elem.length > 6) ? elem[6] : "";
                            String concentration = (elem.length > 7) ? elem[7] : "";
                            String wellComments = (elem.length > 8) ? elem[8] : "";
                            String containerComments = (elem.length > 9) ? elem[9] : "";

                            // get containerID
                            if (containerName.length() > 0) {

                                HashMap contName = new HashMap(3);
                                contName.put("containerName", "'" + containerName + "'");
                                Container container = (Container) httpSess.getObjectByUniqueKey(Container.class, contName);
                                if (container == null) {
                                    e.println("Could not find container for name = " + containerName);
                                } else {
                                    String containerID = container.getContainerID();

                                    //get row & column
                                    String row = String.valueOf(well.charAt(0));
                                    if (!Character.isDigit(well.charAt(0))) {
                                        row = (row.equalsIgnoreCase("a") ? "1" : row.equalsIgnoreCase("b") ? "2" : row.equalsIgnoreCase("c") ? "3" : row.equalsIgnoreCase("d") ? "4" : row.equalsIgnoreCase("e") ? "5" : row.equalsIgnoreCase("f") ? "6" : row.equalsIgnoreCase("g") ? "7" : row.equalsIgnoreCase("h") ? "8" : row.equalsIgnoreCase("i") ? "9" : row.equalsIgnoreCase("j") ? "10" : row.equalsIgnoreCase("k") ? "11" : row.equalsIgnoreCase("l") ? "12" : row.equalsIgnoreCase("m") ? "13" : row.equalsIgnoreCase("n") ? "14" : row.equalsIgnoreCase("o") ? "15" : row.equalsIgnoreCase("p") ? "16" : row.equalsIgnoreCase("q") ? "17" : row.equalsIgnoreCase("r") ? "18" : row.equalsIgnoreCase("s") ? "19" : row.equalsIgnoreCase("t") ? "20" : row.equalsIgnoreCase("u") ? "21" : row.equalsIgnoreCase("v") ? "22" : row.equalsIgnoreCase("w") ? "23" : row.equalsIgnoreCase("x") ? "24" : row.equalsIgnoreCase("y") ? "25" : row.equalsIgnoreCase("z") ? "26" : "-1");
                                    }
                                    String col = well.substring(1);

                                    // get sampleID
                                    if (sampleName.length() > 0 && !sampleName.equals("0")) {
                                        String cohortName = "";
                                        if (sampleName.startsWith("Az")) {
                                            sampleName = sampleName.substring(2, sampleName.length());
                                            cohortName = "CAPPS";
                                        }
                                        if (sampleName.startsWith("SAGE")) {
                                            sampleName = sampleName.substring(4, sampleName.length());
                                            cohortName = "SAGE";
                                        }
                                        HashMap sampName = new HashMap(1);
                                        sampName.put("sampleName", "'" + sampleName + "'");
                                        Sample sample = (Sample) httpSess.getObjectByUniqueKey(Sample.class, sampName);
                                        if (sample == null) {
                                            // could be 2+ samples in DB with that ID but different cohort subjects
                                            if (!cohortName.equals("")) {
                                                String subjectName = sampleName;
                                                if (sampleName.endsWith("a") || sampleName.endsWith("b") || sampleName.endsWith("c") || sampleName.endsWith("d")) {
                                                    subjectName = sampleName.substring(0, sampleName.length() - 1);
                                                }
                                                HashMap cohortHM = new HashMap(2);
                                                cohortHM.put("description", "'" + cohortName + "'");
                                                Cohort cohort = (Cohort) httpSess.getObjectByUniqueKey(Cohort.class, cohortHM);
                                                if (cohort == null) {
                                                    e.println("Could not find cohort for ID = " + cohortName);
                                                } else {
                                                    HashMap nameCohort = new HashMap(2);
                                                    nameCohort.put("subjectName", "'" + subjectName + "'");
                                                    nameCohort.put("cohort.cohortID", cohort.getCohortID());
                                                    Subject subject = (Subject) httpSess.getObjectByUniqueKey(Subject.class, nameCohort);
                                                    if (subject == null) {
                                                        e.println("Could not find subject for ID = " + subjectName + " cohort:" + cohort.getDescription());
                                                    } else {
                                                        String subjectID = subject.getSubjectID();
                                                        HashMap sampleHM = new HashMap(2);
                                                        sampleHM.put("sampleName", "'" + sampleName + "'");
                                                        sampleHM.put("subject", subject);
                                                    }
                                                }
                                            } else {
                                                e.println("Could not find sample for ID = " + sampleName);
                                            }
                                        } else {
                                            String sampleID = sample.getSampleID();


                                            if (sampleName.length() > 0) {
                                                //System.out.println("sampleName:" + sampleName + " subjectName: "+subjectName);
                                                // if(h.containsKey(subjectName)){
                                                //System.out.println("Container*"+containerName+"*comments*"+comments);
                                                /*p.println("UPDATE allergen.tblcontainercontents SET dilution='1:100' " +
                                                "where containerID =" + containerID + " AND sampleID=" + sampleID +
                                                " AND row = "+row+" AND column = "+col+" AND dilution ='1:10';"
                                                );*/
                                                if (containerID.equals("1409")) {
                                                    e.println("INSERT INTO allergen.tblcontainercontents "
                                                            + "(containerID,row,column,sampleID,volume,concentration,dilution,comments) "
                                                            + "VALUES (" + containerID + "," + row + "," + col + "," + sampleID + "," + volume + "," + concentration + ",'" + dilution + "','" + wellComments + "');");
                                                    e.println("container name=" + containerName + " concentration=" + concentration + " comments=" + wellComments);
                                                }
                                                w.println("INSERT INTO allergen.tblcontainercontents "
                                                        + "(containerID,row,column,sampleID,volume,concentration,dilution,comments) "
                                                        + "VALUES (" + containerID + "," + row + "," + col + "," + sampleID + "," + volume + "," + concentration + ",'" + dilution + "','" + wellComments + "');");

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void writeNewWells(String fileDir) {
        try {
            UserHttpSess httpSess = new UserHttpSess("c:/temp/log");

            //1.) build list of all samples in files
            HashSet<String> sampleList = new HashSet();
            String[] elem;
            String prefix;
            FileOutputStream outWell; // declare a file output object
            FileOutputStream outCont; // declare a file output object
            FileOutputStream err; // declare a file output object
            PrintStream w; // declare a print stream object
            PrintStream c; // declare a print stream object
            PrintStream e; // declare a print stream object

            outWell = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Wells\\2nd try\\outWells.txt");
            outCont = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Wells\\2nd try\\outCont.txt");
            err = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Wells\\2nd try\\err.txt");

            // Connect print stream to the output stream
            w = new PrintStream(outWell);
            c = new PrintStream(outCont);
            e = new PrintStream(err);

            File files = new File(fileDir);
            for (File file : files.listFiles()) {
                if (file.getName().endsWith(".txt")) {
                    //e.println("Parsing file: "+file.getName());
                    //parse it
                    ArrayList<String> fileContents = Tools.readFile(file);
                    for (String line : fileContents) {
                        elem = line.split("\t");  //split using tab char
                        if (elem.length > 0) {
                            String sampleName = (elem.length > 0) ? elem[0] : "";
                            String containerName = (elem.length > 1) ? elem[1] : "";
                            String well = (elem.length > 2) ? elem[2] : "";
                            String volume = (elem.length > 3) ? elem[3] : "";
                            String dilution = (elem.length > 6) ? elem[6] : "";
                            String concentration = (elem.length > 7) ? elem[7] : "";
                            String wellComments = (elem.length > 8) ? elem[8] : null;
                            String containerComments = (elem.length > 9) ? elem[9] : null;
                            String sampleType = (elem.length > 10) ? elem[10] : null;
                            String dateCollected = (elem.length > 11) ? elem[11] : null;

                            // get containerID
                            if (containerName.length() > 0 && sampleName.length() > 0) {

                                HashMap contName = new HashMap(3);
                                contName.put("containerName", "'" + containerName + "'");
                                Container container = (Container) httpSess.getObjectByUniqueKey(Container.class, contName);
                                if (container == null) {
                                    e.println("Could not find container for name = " + containerName);
                                } else {
                                    String containerID = container.getContainerID();

                                    //get row & column
                                    String row = String.valueOf(well.charAt(0));
                                    if (!Character.isDigit(well.charAt(0))) {
                                        row = (row.equalsIgnoreCase("a") ? "1" : row.equalsIgnoreCase("b") ? "2" : row.equalsIgnoreCase("c") ? "3" : row.equalsIgnoreCase("d") ? "4" : row.equalsIgnoreCase("e") ? "5" : row.equalsIgnoreCase("f") ? "6" : row.equalsIgnoreCase("g") ? "7" : row.equalsIgnoreCase("h") ? "8" : row.equalsIgnoreCase("i") ? "9" : row.equalsIgnoreCase("j") ? "10" : row.equalsIgnoreCase("k") ? "11" : row.equalsIgnoreCase("l") ? "12" : row.equalsIgnoreCase("m") ? "13" : row.equalsIgnoreCase("n") ? "14" : row.equalsIgnoreCase("o") ? "15" : row.equalsIgnoreCase("p") ? "16" : row.equalsIgnoreCase("q") ? "17" : row.equalsIgnoreCase("r") ? "18" : row.equalsIgnoreCase("s") ? "19" : row.equalsIgnoreCase("t") ? "20" : row.equalsIgnoreCase("u") ? "21" : row.equalsIgnoreCase("v") ? "22" : row.equalsIgnoreCase("w") ? "23" : row.equalsIgnoreCase("x") ? "24" : row.equalsIgnoreCase("y") ? "25" : row.equalsIgnoreCase("z") ? "26" : "-1");
                                    }
                                    String col = well.substring(1);

                                    // get sampleID
                                    if (sampleName.length() > 0 && !sampleName.equals("0")) {
                                        String cohortName = "";
                                        if (sampleName.startsWith("Az")) {
                                            sampleName = sampleName.substring(2, sampleName.length());
                                            cohortName = "CAPPS";
                                        }
                                        if (sampleName.startsWith("SAGE")) {
                                            sampleName = sampleName.substring(4, sampleName.length());
                                            cohortName = "SAGE";
                                        }
                                        HashMap sampName = new HashMap(1);
                                        sampName.put("sampleName", "'" + sampleName + "'");
                                        Sample sample = (Sample) httpSess.getObjectByUniqueKey(Sample.class, sampName);
                                        if (sample == null) {
                                            // could be 2+ samples in DB with that ID but different cohort subjects
                                            if (!cohortName.equals("")) {
                                                String subjectName = sampleName;
                                                if (sampleName.endsWith("a") || sampleName.endsWith("b") || sampleName.endsWith("c") || sampleName.endsWith("d")) {
                                                    subjectName = sampleName.substring(0, sampleName.length() - 1);
                                                }
                                                HashMap cohortHM = new HashMap(2);
                                                cohortHM.put("description", "'" + cohortName + "'");
                                                Cohort cohort = (Cohort) httpSess.getObjectByUniqueKey(Cohort.class, cohortHM);
                                                if (cohort == null) {
                                                    e.println("Could not find cohort for ID = " + cohortName);
                                                } else {
                                                    HashMap nameCohort = new HashMap(2);
                                                    nameCohort.put("subjectName", "'" + subjectName + "'");
                                                    nameCohort.put("cohort.cohortID", cohort.getCohortID());
                                                    Subject subject = (Subject) httpSess.getObjectByUniqueKey(Subject.class, nameCohort);
                                                    if (subject == null) {
                                                        e.println("Could not find subject for ID = " + subjectName + " cohort:" + cohort.getDescription());
                                                    } else {
                                                        String subjectID = subject.getSubjectID();
                                                        HashMap sampleHM = new HashMap(2);
                                                        sampleHM.put("sampleName", "'" + sampleName + "'");
                                                        sampleHM.put("subject.subjectID", subjectID);
                                                        sample = (Sample) httpSess.getObjectByUniqueKey(Sample.class, sampleHM);
                                                    }
                                                    if (sample == null) {
                                                        e.println("Could not find sample for sample: " + sampleName + " subject: " + subjectName + " cohort:" + cohort.getDescription());
                                                    }
                                                }
                                            } else {
                                                e.println("Could not find sample for ID = " + sampleName);
                                            }
                                        }
                                        if (sample != null) {
                                            String sampleID = sample.getSampleID();

                                            // get sampleTypeID
                                            String sampleTypeID =
                                                    (sampleType == null) ? null
                                                    : (sampleType.equalsIgnoreCase("Blood")) ? "0"
                                                    : (sampleType.equalsIgnoreCase("Urine")) ? "1"
                                                    : (sampleType.equalsIgnoreCase("DNA")) ? "2"
                                                    : (sampleType.equalsIgnoreCase("Mouth Swab")) ? "3"
                                                    : (sampleType.equalsIgnoreCase("Buccal")) ? "3"
                                                    : (sampleType.equalsIgnoreCase("ST cells")) ? "5"
                                                    : (sampleType.equalsIgnoreCase("Year 1")) ? "6" : null;

                                            // format dateCollected
                                            if (dateCollected != null) {
                                                dateCollected = dateCollected.replace('/', '-');
                                            }

                                            if (sampleName.length() > 0) {
                                                //System.out.println("sampleName:" + sampleName + " subjectName: "+subjectName);
                                                // if(h.containsKey(subjectName)){
                                                //System.out.println("Container*"+containerName+"*comments*"+comments);

                                                HashMap ccHM = new HashMap(2);
                                                ccHM.put("containerID", containerID);
                                                ccHM.put("row", row);
                                                ccHM.put("column", col);
                                                ContainerContent itemExists = (ContainerContent) httpSess.getObjectByUniqueKey(ContainerContent.class, ccHM);
                                                if (itemExists == null) {
                                                    /*w.println("DELETE FROM allergen.tblcontainercontents WHERE "
                                                    +" containerID="+containerID+" AND sampleID = "+sampleID
                                                    +" AND row = "+row+" AND column="+col+";");

                                                    w.println("INSERT INTO allergen.tblcontainercontents " +
                                                    "(containerID,row,column,sampleID,volume,concentration,dilution,comments) " +
                                                    "VALUES (" + containerID + "," + row + "," + col + "," + sampleID + "," + volume + "," + concentration + ",'" + dilution + "','" + wellComments +
                                                    "');");
                                                     */
                                                    //((dateCollected==null)? "',null":"','"+dateCollected+"'")+" );");
                                                }
                                                /*w.println("UPDATE allergen.tblcontainercontents SET " +
                                                " row = " + row + ", column=" + col + ",volume=" + volume + ",concentration=" + concentration + ",dilution='" + dilution + "',comments='" + wellComments + "'"+
                                                " WHERE containerID = " + containerID + " AND sampleID=" + sampleID+";");
                                                 */
                                                if (dateCollected != null && !dateCollected.equals("")) {
                                                    w.println("UPDATE allergen.tblsamples SET "
                                                            + //" sampletypeID="+ sampleTypeID+
                                                            " dateCollected=" + dateCollected
                                                            + " WHERE sampleID=" + sampleID + ";");
                                                } else if (sampleType != null) {
                                                    //e.println("Sample Name was: " +sampleType+ " but no sampleTypeID was found.");
                                                }



                                            }
                                        } else {
                                            e.println("Could not find sample for: " + cohortName + sampleName);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateSampleTypes(String fileDir) {
        try {
            UserHttpSess httpSess = new UserHttpSess("c:/temp/log");

            //1.) build list of all samples in files
            HashSet<String> sampleList = new HashSet();
            String[] elem;
            String prefix;
            FileOutputStream outSamp; // declare a file output object
            FileOutputStream outCont; // declare a file output object
            FileOutputStream err; // declare a file output object
            PrintStream w; // declare a print stream object
            PrintStream c; // declare a print stream object
            PrintStream e; // declare a print stream object

            outSamp = new FileOutputStream(fileDir + "\\outSampleTypes.txt");
            //outCont = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\SampleType-dateCollected-Jul21\\outCont.txt");
            err = new FileOutputStream(fileDir + "\\err.txt");

            // Connect print stream to the output stream
            w = new PrintStream(outSamp);
            //c = new PrintStream(outCont);
            e = new PrintStream(err);

            File files = new File(fileDir + "\\current");
            for (File file : files.listFiles()) {
                if (file.getName().endsWith(".txt")) {
                    //e.println("Parsing file: "+file.getName());
                    //parse it
                    ArrayList<String> fileContents = Tools.readFile(file);
                    for (String line : fileContents) {
                        elem = line.split("\t");  //split using tab char
                        if (elem.length > 0) {
                            String sampleName = (elem.length > 1) ? "Az" + elem[1] + "_yr1" : "";
                            String sampleType = (elem.length > 9) ? elem[9] : null;
                            String dateCollected = (elem.length > 7) ? elem[7] : null;
                            String extractionDate = (elem.length > 8) ? elem[8] : null;


                            if (dateCollected != null && sampleType == null) {
                                sampleType = "Blood";
                            }

                            // get sampleID
                            if (sampleName.length() > 0 && !sampleName.equals("0")) {
                                String cohortName = "";
                                if (sampleName.startsWith("Az")) {
                                    cohortName = "CAPPS";
                                }
                                if (sampleName.startsWith("SAGE")) {
                                    cohortName = "SAGE";
                                }
                                HashMap sampName = new HashMap(1);
                                sampName.put("sampleName", "'" + sampleName + "'");
                                Sample sample = (Sample) httpSess.getObjectByUniqueKey(Sample.class, sampName);
                                if (sample == null) {
                                    // could be 2+ samples in DB with that ID but different cohort subjects
                                    if (!cohortName.equals("")) {
                                        String subjectName = sampleName;
                                        if (sampleName.endsWith("a") || sampleName.endsWith("b") || sampleName.endsWith("c") || sampleName.endsWith("d")) {
                                            subjectName = sampleName.substring(0, sampleName.length() - 1);
                                        }
                                        HashMap cohortHM = new HashMap(2);
                                        cohortHM.put("description", "'" + cohortName + "'");
                                        Cohort cohort = (Cohort) httpSess.getObjectByUniqueKey(Cohort.class, cohortHM);
                                        if (cohort == null) {
                                            e.println("Could not find cohort for ID = " + cohortName);
                                        } else {
                                            HashMap nameCohort = new HashMap(2);
                                            nameCohort.put("subjectName", "'" + subjectName + "'");
                                            nameCohort.put("cohort.cohortID", cohort.getCohortID());
                                            Subject subject = (Subject) httpSess.getObjectByUniqueKey(Subject.class, nameCohort);
                                            if (subject == null) {
                                                e.println("Could not find subject for ID = " + subjectName + " cohort:" + cohort.getDescription());
                                            } else {
                                                String subjectID = subject.getSubjectID();
                                                HashMap sampleHM = new HashMap(2);
                                                sampleHM.put("sampleName", "'" + sampleName + "'");
                                                sampleHM.put("subject.subjectID", subjectID);
                                                sample = (Sample) httpSess.getObjectByUniqueKey(Sample.class, sampleHM);
                                            }
                                            if (sample == null) {
                                                e.println("Could not find sample for sample: " + sampleName + " subject: " + subjectName + " cohort:" + cohort.getDescription());
                                            }
                                        }
                                    } else {
                                        e.println("Could not find sample for ID = " + sampleName);
                                    }
                                }
                                if (sample != null) {
                                    String sampleID = sample.getSampleID();
                                    /* String sampleTypeExisting = (sample.getSampleType() != null) ? sample.getSampleType().getDescription() : null;
                                    if (sampleTypeExisting != null) {
                                    e.println("sample " + sampName + " already has type: " + sampleTypeExisting);
                                    continue;
                                    }

                                    String sampleDateExisting = (sample.getDateCollected() != null) ? sample.getDateCollected().toString() : null;
                                    if (sampleDateExisting != null) {
                                    e.println("sample " + sampName + " already has coll date: " + sampleDateExisting);
                                    continue;
                                    }
                                    sampleDateExisting = (sample.getDateExtracted() != null) ? sample.getDateExtracted().toString() : null;
                                    if (sampleDateExisting != null) {
                                    e.println("sample " + sampName + " already has extract date: " + sampleDateExisting);
                                    continue;
                                    }
                                     */

                                    // get sampleTypeID
                                    if (sampleType != null && sampleType.equals("ST. CELLS")) {
                                        sampleType = "ST Cells";
                                    }
                                    if (sampleType != null && sampleType.equals("Buccal")) {
                                        sampleType = "Buccal Swab";
                                    }
                                    if (sampleType != null && sampleType.equals("Year 1")) {
                                        sampleType = "Year 1 (Unknown)";
                                    }

                                    HashMap sampTyp = new HashMap(1);
                                    sampTyp.put("description", "'" + sampleType + "'");
                                    SampleType sampleTypeObj = (SampleType) httpSess.getObjectByUniqueKey(SampleType.class, sampTyp);
                                    if (sampleTypeObj != null) {
                                        String sampleTypeID = sampleTypeObj.getSampleTypeID();

                                        // format dateCollected
                                        if (dateCollected != null) {
                                            dateCollected = dateCollected.replace('/', '-');
                                        }

                                        if (sampleName.length() > 0) {
                                            //System.out.println("sampleName:" + sampleName + " subjectName: "+subjectName);
                                            // if(h.containsKey(subjectName)){
                                            //System.out.println("Container*"+containerName+"*comments*"+comments);

                                            if (dateCollected != null && !dateCollected.equals("")) {
                                                w.println("UPDATE allergen.tblsamples SET "
                                                        + " sampletypeID=" + sampleTypeID
                                                        + ", dateCollected='" + dateCollected + "'"
                                                        + ", extractionDate='" + extractionDate + "'"
                                                        + " WHERE sampleID=" + sampleID + ";");
                                            } else {
                                                w.println("UPDATE allergen.tblsamples SET "
                                                        + " sampletypeID=" + sampleTypeID
                                                        + " WHERE sampleID=" + sampleID + ";");
                                            }
                                            //} else if (sampleType != null) {
                                            //e.println("Sample Name was: " +sampleType+ " but no sampleTypeID was found.");
                                            //}



                                        }
                                    } else {
                                        e.println("Could not find sample type for: " + cohortName + sampleName + sampleType);
                                    }
                                } else {
                                    e.println("Could not find sample for: " + cohortName + sampleName);
                                }
                            }

                        }
                    }
                }

            }
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateSampleTypeMultipleTypes(String fileDir) {
        try {
            UserHttpSess httpSess = new UserHttpSess("c:/temp/log");

            //1.) build list of all samples in files
            HashSet<String> sampleList = new HashSet();
            String[] elem;
            String prefix;
            FileOutputStream outSamp; // declare a file output object
            FileOutputStream outCont; // declare a file output object
            FileOutputStream err; // declare a file output object
            PrintStream w; // declare a print stream object
            PrintStream c; // declare a print stream object
            PrintStream e; // declare a print stream object

            outSamp = new FileOutputStream(fileDir + "\\outSampleTypes.txt");
            //outCont = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\SampleType-dateCollected-Jul21\\outCont.txt");
            err = new FileOutputStream(fileDir + "\\err.txt");

            // Connect print stream to the output stream
            w = new PrintStream(outSamp);
            //c = new PrintStream(outCont);
            e = new PrintStream(err);

            File files = new File(fileDir);
            for (File file : files.listFiles()) {
                if (file.getName().endsWith(".txt")) {
                    //e.println("Parsing file: "+file.getName());
                    //parse it
                    ArrayList<String> fileContents = Tools.readFile(file);
                    for (String line : fileContents) {
                        elem = line.split("\t");  //split using tab char
                        if (elem.length > 0) {
                            String sampleName = (elem.length > 0) ? elem[0] : "";
                            String sampleType1 = (elem.length > 1) ? elem[1] : null;
                            String sampleType2 = (elem.length > 2) ? elem[2] : null;


                            // get sampleID
                            if (sampleName.length() > 0 && !sampleName.equals("0")) {
                                String cohortName = "";
                                if (sampleName.startsWith("Az")) {
                                    cohortName = "CAPPS";
                                }
                                if (sampleName.startsWith("SAGE")) {
                                    cohortName = "SAGE";
                                }
                                HashMap sampName = new HashMap(1);
                                sampName.put("sampleName", "'" + sampleName + "'");
                                Sample sample = (Sample) httpSess.getObjectByUniqueKey(Sample.class, sampName);
                                if (sample == null) {
                                    // could be 2+ samples in DB with that ID but different cohort subjects
                                    if (!cohortName.equals("")) {
                                        String subjectName = sampleName;
                                        if (sampleName.endsWith("a") || sampleName.endsWith("b") || sampleName.endsWith("c") || sampleName.endsWith("d")) {
                                            subjectName = sampleName.substring(0, sampleName.length() - 1);
                                        }
                                        HashMap cohortHM = new HashMap(2);
                                        cohortHM.put("description", "'" + cohortName + "'");
                                        Cohort cohort = (Cohort) httpSess.getObjectByUniqueKey(Cohort.class, cohortHM);
                                        if (cohort == null) {
                                            e.println("Could not find cohort for ID = " + cohortName);
                                        } else {
                                            HashMap nameCohort = new HashMap(2);
                                            nameCohort.put("subjectName", "'" + subjectName + "'");
                                            nameCohort.put("cohort.cohortID", cohort.getCohortID());
                                            Subject subject = (Subject) httpSess.getObjectByUniqueKey(Subject.class, nameCohort);
                                            if (subject == null) {
                                                e.println("Could not find subject for ID = " + subjectName + " cohort:" + cohort.getDescription());
                                            } else {
                                                String subjectID = subject.getSubjectID();
                                                HashMap sampleHM = new HashMap(2);
                                                sampleHM.put("sampleName", "'" + sampleName + "'");
                                                sampleHM.put("subject.subjectID", subjectID);
                                                sample = (Sample) httpSess.getObjectByUniqueKey(Sample.class, sampleHM);
                                            }
                                            if (sample == null) {
                                                e.println("Could not find sample for sample: " + sampleName + " subject: " + subjectName + " cohort:" + cohort.getDescription());
                                            }
                                        }
                                    } else {
                                        e.println("Could not find sample for ID = " + sampleName);
                                    }
                                }
                                if (sample != null) {
                                    String sampleID = sample.getSampleID();

                                    // get sampleTypeID
                                    if (sampleType1 != null && sampleType1.equals("ST. CELLS")) {
                                        sampleType1 = "ST Cells";
                                    }
                                    if (sampleType2 != null && sampleType2.equals("ST. CELLS")) {
                                        sampleType2 = "ST Cells";
                                    }

                                    HashMap sampTyp = new HashMap(1);
                                    sampTyp.put("description", "'Multiple'");
                                    SampleType sampleTypeObj = (SampleType) httpSess.getObjectByUniqueKey(SampleType.class, sampTyp);
                                    if (sampleTypeObj != null) {
                                        String sampleTypeID = sampleTypeObj.getSampleTypeID();


                                        if (sampleName.length() > 0) {
                                            w.println("UPDATE allergen.tblsamples SET "
                                                    + " sampletypeID=" + sampleTypeID
                                                    + ", comments = 'Multiple sample types: " + sampleType1 + ", " + sampleType2 + "' "
                                                    + " WHERE sampleID=" + sampleID + ";");



                                        }
                                    }
                                } else {
                                    e.println("Could not find sample for: " + cohortName + sampleName);
                                }
                            }

                        }
                    }
                }

            }
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateDatesOfCollection(String fileDir) {
        try {
            UserHttpSess httpSess = new UserHttpSess("c:/temp/log");

            //1.) build list of all samples in files
            HashSet<String> sampleList = new HashSet();
            String[] elem;
            String lineStr;
            String prefix;
            FileOutputStream out; // declare a file output object
            FileOutputStream outCont; // declare a file output object
            FileOutputStream err; // declare a file output object
            PrintStream w; // declare a print stream object
            PrintStream c; // declare a print stream object
            PrintStream e; // declare a print stream object

            out = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\DateOfCollection-Extraction\\outDates.txt");
            //outCont = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\SampleType-dateCollected-Jul21\\outCont.txt");
            err = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\DateOfCollection-Extraction\\err.txt");

            // Connect print stream to the output stream
            w = new PrintStream(out);
            //c = new PrintStream(outCont);
            e = new PrintStream(err);

            File files = new File(fileDir);
            for (File file : files.listFiles()) {
                if (file.getName().endsWith(".txt")) {
                    //e.println("Parsing file: "+file.getName());
                    //parse it
                    ArrayList<String> fileContents = Tools.readFile(file);
                    for (String line : fileContents) {
                        lineStr = line;
                        elem = line.split("\t");  //split using tab char
                        if (elem.length > 0) {
                            String familyName = (elem.length > 0) ? elem[0].trim() : "";
                            String person = (elem.length > 1) ? elem[1].trim() : "";
                            String gender = (elem.length > 2) ? elem[2].trim() : "";
                            String bloodCollected = (elem.length > 3) ? elem[3].trim() : "";
                            String bloodExtracted = (elem.length > 4) ? elem[4].trim() : "";
                            String sampleType = (elem.length > 5) ? elem[5].trim() : "";
                            String swabCollected = (elem.length > 6) ? elem[6].trim() : "";
                            String swabExtracted = (elem.length > 7) ? elem[7].trim() : "";

                            String genderID = (gender.equals("Female")) ? "2" : "1";

                            HashMap sampTyp = new HashMap(1);
                            if ((bloodCollected.equals("") && bloodExtracted.equals(""))
                                    || (swabCollected.equals("") && swabExtracted.equals(""))) {
                                sampTyp.put("description", "'" + sampleType + "'");
                                SampleType sampleTypeObj = (SampleType) httpSess.getObjectByUniqueKey(SampleType.class, sampTyp);
                                if (sampleType != null && !sampleType.equals("") && sampleTypeObj != null) {
                                    String sampleTypeID = sampleTypeObj.getSampleTypeID();
                                    w.println("UPDATE allergen.tblsamples SET "
                                            + " sampletypeyear1id=" + sampleTypeID
                                            + " WHERE name like 'Az" + familyName + "-" + person + "%';");
                                } else if (sampleType != null && !sampleType.equals("")) {
                                    e.println("no sampleType for " + sampleType);
                                }
                            } else {
                                w.println("UPDATE allergen.tblsamples SET "
                                        + " sampletypeyear1id=" + "null"
                                        + " WHERE name like 'Az" + familyName + "-" + person + "%';");

                            }

                            // format dateCollected
                            if (bloodCollected != null) {
                                bloodCollected = bloodCollected.replace('/', '-');
                            }
                            // format dateCollected
                            if (swabCollected != null) {
                                swabCollected = swabCollected.replace('/', '-');
                            }

                            if (bloodCollected != null && bloodExtracted.equals("")
                                    && (!swabCollected.equals("") || !swabExtracted.equals(""))) {
                                //e.println(familyName + "\t" + person + "\t" +gender + "\t" + bloodCollected + "\t" + bloodExtracted + "\t" +sampleType + "\t" + swabCollected + "\t" + swabExtracted);
                                e.println(lineStr);
                            } else if (!bloodCollected.equals("") && !bloodExtracted.equals("")
                                    && swabCollected.equals("") && swabExtracted.equals("")) {
                                w.println("UPDATE allergen.tblsamples SET "
                                        + " \"DATECOLLECTEDYEAR1-GOOD\"='" + bloodCollected + "'"
                                        + ", dateExtractedyear1='" + bloodExtracted + "'"
                                        + " WHERE name like 'Az" + familyName + "-" + person + "%';");
                            } else if (bloodCollected.equals("") && bloodExtracted.equals("")
                                    && !swabCollected.equals("") && !swabExtracted.equals("")) {
                                w.println("UPDATE allergen.tblsamples SET "
                                        + " \"DATECOLLECTEDYEAR1-GOOD\"='" + swabCollected + "'"
                                        + ", dateExtractedyear1='" + swabExtracted + "'"
                                        + " WHERE name like 'Az" + familyName + "-" + person + "%';");
                            } else if (!bloodCollected.equals("") && bloodExtracted.equals("")
                                    && swabCollected.equals("") && swabExtracted.equals("")) {
                                w.println("UPDATE allergen.tblsamples SET "
                                        + " \"DATECOLLECTEDYEAR1-GOOD\"='" + bloodCollected + "'"
                                        + " WHERE name like 'Az" + familyName + "-" + person + "%';");
                            } else if (bloodCollected.equals("") && !bloodExtracted.equals("")
                                    && swabCollected.equals("") && swabExtracted.equals("")) {
                                w.println("UPDATE allergen.tblsamples SET "
                                        + " dateExtractedyear1='" + bloodExtracted + "'"
                                        + " WHERE name like 'Az" + familyName + "-" + person + "%';");
                            } else if (bloodCollected.equals("") && bloodExtracted.equals("")
                                    && !swabCollected.equals("") && swabExtracted.equals("")) {
                                w.println("UPDATE allergen.tblsamples SET "
                                        + " \"DATECOLLECTEDYEAR1-GOOD\"='" + swabCollected + "'"
                                        + " WHERE name like 'Az" + familyName + "-" + person + "%';");
                            } else if (bloodCollected.equals("") && bloodExtracted.equals("")
                                    && swabCollected.equals("") && !swabExtracted.equals("")) {
                                w.println("UPDATE allergen.tblsamples SET "
                                        + " dateExtractedyear1='" + swabExtracted + "'"
                                        + " WHERE name like 'Az" + familyName + "-" + person + "%';");
                            } else {
                                e.println(lineStr);
                            }

                        }
                    }
                }
            }
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateDatesOfExtraction(String fileDir) {
        try {
            UserHttpSess httpSess = new UserHttpSess("c:/temp/log");

            //1.) build list of all samples in files
            HashSet<String> sampleList = new HashSet();
            String[] elem;
            String lineStr;
            String prefix;
            FileOutputStream out; // declare a file output object
            FileOutputStream outCont; // declare a file output object
            FileOutputStream err; // declare a file output object
            PrintStream w; // declare a print stream object
            PrintStream c; // declare a print stream object
            PrintStream e; // declare a print stream object

            out = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\DateOfCollection-Extraction\\outDates.txt");
            //outCont = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\SampleType-dateCollected-Jul21\\outCont.txt");
            err = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\DateOfCollection-Extraction\\err.txt");

            // Connect print stream to the output stream
            w = new PrintStream(out);
            //c = new PrintStream(outCont);
            e = new PrintStream(err);

            File files = new File(fileDir);
            for (File file : files.listFiles()) {
                if (file.getName().endsWith(".txt")) {
                    //e.println("Parsing file: "+file.getName());
                    //parse it
                    ArrayList<String> fileContents = Tools.readFile(file);
                    for (String line : fileContents) {
                        lineStr = line;
                        elem = line.split("\t");  //split using tab char
                        if (elem.length > 0) {
                            String sampleName = (elem.length > 0) ? elem[0].trim() : "";
                            String sampleType = (elem.length > 1) ? elem[1].trim() : "";
                            String extractionDate = (elem.length > 2) ? elem[2].trim() : "";
                            String sampleComments = (elem.length > 3) ? elem[3].trim() : "";

                            // get exsisting sample type and comments
                            HashMap samp = new HashMap();
                            samp.put("sampleName", "'" + sampleName + "'");
                            Sample sampleObj = (Sample) httpSess.getObjectByUniqueKey(Sample.class, samp);
                            String existingSampleTypeID = "";
                            String existingSampleComments = "";
                            if (sampleObj != null && sampleObj.getSampleType() != null) {
                                existingSampleTypeID = sampleObj.getSampleType().getSampleTypeID();
                            }
                            if (sampleObj != null && sampleObj.getComments() != null) {
                                existingSampleComments = sampleObj.getComments() + ". ";
                            }

                            // append new comments to old
                            String newComments = existingSampleComments + "Lab book note: " + sampleComments + ".";

                            HashMap sampTyp = new HashMap();
                            sampTyp.put("description", "'" + sampleType + "'");
                            SampleType sampleTypeObj = (SampleType) httpSess.getObjectByUniqueKey(SampleType.class, sampTyp);
                            String newSampleTypeID = "";
                            if (sampleType != null && !sampleType.equals("") && sampleTypeObj != null) {
                                newSampleTypeID = sampleTypeObj.getSampleTypeID();
                            }

                            // sample type update
                            if (!existingSampleTypeID.equals("") && !newSampleTypeID.equals("") && !existingSampleTypeID.equals(newSampleTypeID)) {
                                e.println("Sample type mis match for " + sampleName + " current ID:"
                                        + existingSampleTypeID + " new ID:" + newSampleTypeID);

                            } else if (!newSampleTypeID.equals("") && existingSampleTypeID.equals("")) {
                                w.println("UPDATE allergen.tblsamples SET "
                                        + " sampletypeid=" + newSampleTypeID
                                        + " WHERE name like '" + sampleName + "';");

                            }

                            // format extractionDate
                            if (extractionDate != null) {
                                extractionDate = extractionDate.replace('/', '-');
                            }

                            if (extractionDate != null && !extractionDate.equals("")) {
                                w.println("UPDATE allergen.tblsamples SET "
                                        + " extractionDate= '" + extractionDate
                                        + "' WHERE name like '" + sampleName + "';");

                            }
                            if (sampleComments != null && !sampleComments.equals("")) {
                                w.println("UPDATE allergen.tblsamples SET "
                                        + " comments= '" + newComments
                                        + "' WHERE name like '" + sampleName + "';");

                            } else {
                            }

                        }
                    }
                }
            }
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateDatesOfExtractionCAPPSABs(String fileDir) {
        try {
            UserHttpSess httpSess = new UserHttpSess("c:/temp/log");

            //1.) build list of all samples in files
            HashSet<String> sampleList = new HashSet();
            String[] elem;
            String lineStr;
            String prefix;
            FileOutputStream out; // declare a file output object
            FileOutputStream outCont; // declare a file output object
            FileOutputStream err; // declare a file output object
            PrintStream w; // declare a print stream object
            PrintStream c; // declare a print stream object
            PrintStream e; // declare a print stream object

            out = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\DateOfCollection-Extraction\\outDates.txt");
            //outCont = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\SampleType-dateCollected-Jul21\\outCont.txt");
            err = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\DateOfCollection-Extraction\\err.txt");

            // Connect print stream to the output stream
            w = new PrintStream(out);
            //c = new PrintStream(outCont);
            e = new PrintStream(err);

            File files = new File(fileDir);
            for (File file : files.listFiles()) {
                if (file.getName().endsWith(".txt")) {
                    //e.println("Parsing file: "+file.getName());
                    //parse it
                    ArrayList<String> fileContents = Tools.readFile(file);
                    for (String line : fileContents) {
                        lineStr = line;
                        elem = line.split("\t");  //split using tab char
                        if (elem.length > 0) {
                            String sampleName = (elem.length > 0) ? elem[0].trim() : "";
                            String sampleType = (elem.length > 1) ? elem[1].trim() : "";
                            String extractionDate = (elem.length > 2) ? elem[2].trim() : "";
                            String sampleComments = (elem.length > 3) ? elem[3].trim() : "";

                            // get exsisting sample type
                            HashMap samp = new HashMap();
                            samp.put("sampleName", "'" + sampleName + "'");
                            Sample sampleObj = (Sample) httpSess.getObjectByUniqueKey(Sample.class, samp);
                            String existingSampleTypeID = "";
                            if (sampleObj != null && sampleObj.getSampleType() != null) {
                                existingSampleTypeID = sampleObj.getSampleType().getSampleTypeID();
                            }

                            HashMap sampTyp = new HashMap();
                            sampTyp.put("description", "'" + sampleType + "'");
                            SampleType sampleTypeObj = (SampleType) httpSess.getObjectByUniqueKey(SampleType.class, sampTyp);
                            String newSampleTypeID = "";
                            if (sampleType != null && !sampleType.equals("") && sampleTypeObj != null) {
                                newSampleTypeID = sampleTypeObj.getSampleTypeID();
                            }

                            // sample type update
                            if (!existingSampleTypeID.equals("") && !newSampleTypeID.equals("") && !existingSampleTypeID.equals(newSampleTypeID)) {
                                e.println("Sample type mis match for " + sampleName + " current ID:"
                                        + existingSampleTypeID + " new ID:" + newSampleTypeID);

                            } else if (!newSampleTypeID.equals("") && existingSampleTypeID.equals("")) {
                                w.println("UPDATE allergen.tblsamples SET "
                                        + " sampletypeid=" + newSampleTypeID
                                        + " WHERE name like '" + sampleName + "';");

                            }

                            // update things that will apply to 'a' and 'b' samples

                            String sampleNameNoLetter = sampleName;
                            if (sampleName.endsWith("a") || sampleName.endsWith("b") || sampleName.endsWith("c") || sampleName.endsWith("d")) {
                                sampleNameNoLetter = sampleName.substring(0, sampleName.length() - 1);
                            }

                            String existingSampleComments = "";
                            if (sampleObj != null && sampleObj.getComments() != null) {
                                existingSampleComments = sampleObj.getComments() + ". ";
                            }

                            // append new comments to old
                            String newComments = "JG lab book note: " + sampleComments + ".";

                            if (sampleComments != null && !sampleComments.equals("")) {
                                w.println("UPDATE allergen.tblsamples SET "
                                        + " comments= CONCAT(comments, '" + newComments
                                        + "') WHERE name like '" + sampleNameNoLetter + "%';");

                            }

                            // format extractionDate
                            if (extractionDate != null) {
                                extractionDate = extractionDate.replace('/', '-');
                            }

                            if (extractionDate != null && !extractionDate.equals("")) {
                                w.println("UPDATE allergen.tblsamples SET "
                                        + " extractionDate= '" + extractionDate
                                        + "' WHERE name like '" + sampleNameNoLetter + "%';");

                            } else {
                            }

                        }
                    }
                }
            }
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void printAllContainers() {
        try {
            UserHttpSess httpSess = new UserHttpSess("c:/temp/log");

            FileOutputStream out; // declare a file output object
            PrintStream c; // declare a print stream object

            out = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\Wells\\2nd try\\containers.txt");

            // Connect print stream to the output stream
            c = new PrintStream(out);
            List list = httpSess.getAllObjects(Container.class);
            Iterator iter = list.iterator();
            Container cont = null;
            while (iter.hasNext()) {
                cont = (Container) iter.next();
                c.println(cont.getcontainerName() + "\t ID= " + cont.getContainerID());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * set the QC values for sample based on two files
     * one has QC data for subjects, the other has what
     * sample was sent for each subject
     * QC is stored associated with sample here
     * genoming center is hard coded within
     */
    private void updateSampleQC(String fileDir) {
        try {
            UserHttpSess httpSess = new UserHttpSess("c:/temp/log");

            //1.) build list of all samples in files
            HashSet<String> sampleList = new HashSet();
            HashMap subjectToSample = new HashMap();
            String[] elem;
            String prefix;
            FileOutputStream outWell; // declare a file output object
            FileOutputStream outCont; // declare a file output object
            FileOutputStream err; // declare a file output object
            PrintStream w; // declare a print stream object
            PrintStream c; // declare a print stream object
            PrintStream e; // declare a print stream object

            outWell = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\QC_field\\out1.txt");
            outCont = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\QC_field\\out2.txt");
            err = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\QC_field\\err.txt");

            // Connect print stream to the output stream
            w = new PrintStream(outWell);
            c = new PrintStream(outCont);
            e = new PrintStream(err);

            File files = new File(fileDir);
            for (File file : files.listFiles()) {
                // THIS FILE MUST BE ACCESSED FIRST!! make sure it come before other one by name
                if (file.getName().endsWith("sampleNameLookup.txt")) {
                    //parse it
                    ArrayList<String> fileContents = Tools.readFile(file);
                    for (String line : fileContents) {
                        elem = line.split("\t");  //split using tab char
                        if (elem.length > 1) {
                            String sampleName = (elem.length > 4) ? elem[4] : "";
                            String subjectName = null;
                            List sampleNameList = new LinkedList();
                            // get subject name from sample name
                            if (sampleName.endsWith("a") || sampleName.endsWith("b") || sampleName.endsWith("c") || sampleName.endsWith("d") || sampleName.endsWith("e") || sampleName.endsWith("f")) {
                                subjectName = sampleName.substring(0, sampleName.length() - 1);
                            } else {
                                subjectName = sampleName;
                            }
                            // add new samplename to subject's existing sample names
                            // if more than one exists
                            if (subjectToSample.containsKey(subjectName)) {
                                //e.println("more than one sample name for "+subjectName);
                                sampleNameList = (LinkedList) subjectToSample.get(subjectName);
                                subjectToSample.remove(subjectName);
                            }
                            sampleNameList.add(sampleName);
                            subjectToSample.put(subjectName, sampleNameList);
                        }
                    }
                } else if (file.getName().endsWith(".txt")) {
                    //e.println("Parsing file: "+file.getName());
                    //parse it
                    ArrayList<String> fileContents = Tools.readFile(file);
                    for (String line : fileContents) {
                        elem = line.split("\t");  //split using tab char
                        if (elem.length > 1) {
                            String cohortName = (elem.length > 1) ? elem[1] : "";
                            String subjectName = (elem.length > 5) ? elem[5] : "";
                            String validity = (elem.length > 17) ? elem[17] : "";

                            String status = (validity.equals("1") || (validity.equals("2")) ? "1" : (validity.equals("0")) ? "0" : null);
                            if (status != null) {

                                String cohortSubjectName = null;
                                if (cohortName.startsWith("Az")) {
                                    cohortSubjectName = "Az" + subjectName;
                                } else if (cohortName.startsWith("SAGE")) {
                                    cohortSubjectName = "SAGE" + subjectName;
                                } else {
                                    e.println("Unrecognized cohort: " + cohortName);
                                }
                                String sampleName = null;
                                Sample sample = null;
                                List sampleNameList = (LinkedList) subjectToSample.get(cohortSubjectName);
                                if (sampleNameList == null) {
                                    e.println("no sample name list entry for " + cohortSubjectName + ", insert written anyway");
                                    sampleName = cohortSubjectName;
                                } else if (sampleNameList.size() > 1) {
                                    Iterator iter = sampleNameList.listIterator();
                                    Sample sampleTmp = null;
                                    List existingSamples = new LinkedList();
                                    while (iter.hasNext()) {
                                        sampleName = (String) iter.next();
                                        HashMap sampName = new HashMap(1);
                                        sampName.put("sampleName", "'" + sampleName + "'");
                                        sampleTmp = (Sample) httpSess.getObjectByUniqueKey(Sample.class, sampName);
                                        if (sampleTmp != null) {
                                            sample = sampleTmp;
                                            existingSamples.add(sample);
                                        }
                                    }
                                    if (existingSamples.size() > 1) {
                                        sampleName = null;
                                        e.println("more than one sample in DB for " + subjectName + "; " + existingSamples.toString());
                                        continue;
                                    } else {
                                        sampleName = sample.getSampleName();
                                    }
                                } else {
                                    sampleName = (String) sampleNameList.get(0);
                                }
                                if (sampleName != null) {
                                    // get sampleID
                                    if (cohortName.length() > 0 && !cohortName.equals("0")) {
                                        if (cohortName.startsWith("Az")) {
                                            cohortName = "CAPPS";
                                        } else if (cohortName.startsWith("SAGE")) {
                                            cohortName = "SAGE";
                                        } else {
                                            e.println("Unrecognized cohort: " + cohortName);
                                        }
                                        HashMap sampName = new HashMap(1);
                                        sampName.put("sampleName", "'" + sampleName + "'");
                                        sample = (Sample) httpSess.getObjectByUniqueKey(Sample.class, sampName);
                                        if (sample == null) {
                                            // could be 2+ samples in DB with that ID but different cohort subjects

                                            if (!cohortName.equals("")) {

                                                // get cohort object
                                                HashMap cohortHM = new HashMap(2);
                                                cohortHM.put("description", "'" + cohortName + "'");
                                                Cohort cohort = (Cohort) httpSess.getObjectByUniqueKey(Cohort.class, cohortHM);
                                                if (cohort == null) {
                                                    e.println("Could not find cohort for ID = " + cohortName);
                                                } else {
                                                    // get subject object
                                                    HashMap nameCohort = new HashMap(2);
                                                    nameCohort.put("subjectName", "'" + subjectName + "'");
                                                    nameCohort.put("cohort.cohortID", cohort.getCohortID());
                                                    Subject subject = (Subject) httpSess.getObjectByUniqueKey(Subject.class, nameCohort);

                                                    if (subject == null) {
                                                        e.println("Could not find subject for ID = " + subjectName + " cohort:" + cohort.getDescription());
                                                    } else {
                                                        // get sample from subject and sample name
                                                        String subjectID = subject.getSubjectID();
                                                        HashMap sampleHM = new HashMap(2);
                                                        sampleHM.put("sampleName", "'" + sampleName + "'");
                                                        sampleHM.put("subject.subjectID", subjectID);
                                                        sample = (Sample) httpSess.getObjectByUniqueKey(Sample.class, sampleHM);
                                                    }
                                                    if (sample == null) {
                                                        e.println("Could not find sample for sample: " + sampleName + " subject: " + subjectName + " cohort:" + cohort.getDescription());
                                                    }
                                                }
                                            } else {
                                                e.println("Could not find sample for ID = " + sampleName);
                                            }
                                        }
                                        if (sample != null) {
                                            // get sample ID
                                            String sampleID = sample.getSampleID();

                                            w.println("INSERT INTO allergen.tblsampleqcstatus "
                                                    + "(sampleID,GENOTYPINGRUNID,STATUS) "
                                                    + "VALUES (" + sampleID + "," + "1" + "," + status
                                                    + ");");


                                        } else {
                                            e.println("Could not find sample for : " + sampleName);
                                        }

                                    }
                                } else {
                                    e.println("Could not find sample name for subject: " + cohortName + subjectName);
                                }
                            }
                        }

                    }
                }
            }

            w.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * get info from file 1 (Big) about subjects in file 1 but not in file 2 (Small)
     * make sure big file comes before small file in file list
     * @param fileDir
     */
    private void checkFileContents(String fileDir) {
        try {
            UserHttpSess httpSess = new UserHttpSess("c:/temp/log");

            //1.) build list of all samples in files
            HashSet<String> sampleList = new HashSet();
            String[] elem;
            String lineStr = "";
            String prefix;
            List found = new LinkedList();
            List notFound = new LinkedList();
            int fromBig = 0;
            int fromSmall = 0;
            int matches = 0;
            int unmatches = 0;
            HashMap idToLineBigfile = new HashMap();
            HashMap idToLineBigfileNonCaucasian = new HashMap();
            HashMap idToLineBigfileCaucasian = new HashMap();
            FileOutputStream outSamp; // declare a file output object
            FileOutputStream outCont; // declare a file output object
            FileOutputStream err; // declare a file output object
            PrintStream w; // declare a print stream object
            PrintStream c; // declare a print stream object
            PrintStream e; // declare a print stream object

            outSamp = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\LookUps\\CNG Samples For Genotyping\\out.txt");
            //outCont = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\Wet-Dry lab Integration\\SampleType-dateCollected-Jul21\\outCont.txt");
            err = new FileOutputStream("C:\\Documents and Settings\\tvanrossum\\My Documents\\DB\\LookUps\\CNG Samples For Genotyping\\err.txt");

            // Connect print stream to the output stream
            w = new PrintStream(outSamp);
            //c = new PrintStream(outCont);
            e = new PrintStream(err);

            File files = new File(fileDir);
            for (File file : files.listFiles()) {
                if (file.getName().endsWith("-Big.txt") && idToLineBigfileNonCaucasian.isEmpty()) {
                    //e.println("Parsing file: "+file.getName());
                    //parse it
                    ArrayList<String> fileContents = Tools.readFile(file);
                    for (String line : fileContents) {
                        lineStr = line;
                        elem = line.split("\t");  //split using tab char
                        if (elem.length > 0) {
                            String cohortDesc = (elem.length > 1) ? elem[1] : "";
                            String subjectID = (elem.length > 5) ? elem[5] : null;
                            //e.println(cohortDesc + subjectID);
                            fromBig++;
                            idToLineBigfile.put(cohortDesc + subjectID, lineStr);
                        }
                    }

                } else if (file.getName().endsWith("-Small.txt")) {
                    //e.println("Parsing file: "+file.getName());
                    //parse it
                    ArrayList<String> fileContents = Tools.readFile(file);
                    for (String line : fileContents) {
                        lineStr = line;
                        elem = line.split("\t");  //split using tab char
                        if (elem.length > 0) {
                            fromSmall++;
                            e.println("SIZE OF idToLineBigfile =" + idToLineBigfileNonCaucasian.size());
                            String cohortDesc = (elem.length > 4) ? elem[4] : "";
                            String subjectID = (elem.length > 5) ? elem[5] : null;
                            if (idToLineBigfile.containsKey(cohortDesc + subjectID)) {
                                idToLineBigfile.remove(cohortDesc + subjectID);
                                matches++;
                                found.add(cohortDesc + subjectID);
                            } else {
                                notFound.add(cohortDesc + subjectID);
                                e.println(cohortDesc + subjectID);
                                unmatches++;
                            }
                        }
                    }
                    idToLineBigfileCaucasian = new HashMap(idToLineBigfile);
                    idToLineBigfileNonCaucasian = new HashMap(idToLineBigfile);

                } else if (file.getName().endsWith("-Ethnicities.txt") && !idToLineBigfileNonCaucasian.isEmpty()) {
                    //e.println("Parsing file: "+file.getName());
                    //parse it
                    int n = idToLineBigfileNonCaucasian.size();
                    int ca = idToLineBigfileCaucasian.size();
                    ArrayList<String> fileContents = Tools.readFile(file);
                    for (String line : fileContents) {
                        lineStr = line;
                        elem = line.split("\t");  //split using tab char
                        if (elem.length > 0) {
                            String cohortDesc = (elem.length > 0) ? elem[0] : "";
                            String subjectID = (elem.length > 1) ? elem[1] : null;
                            String ethnicity = (elem.length > 2) ? elem[2] : null;
                            //e.println(cohortDesc + subjectID);
                            if (ethnicity.equals("Caucasian")) {
                                idToLineBigfileNonCaucasian.remove(cohortDesc + subjectID);
                            } else {
                                idToLineBigfileCaucasian.remove(cohortDesc + subjectID);
                            }
                        }
                    }
                    ca = idToLineBigfileCaucasian.size();
                    n = idToLineBigfileNonCaucasian.size();
                    int i = n;
                }


            }// iterate through hash and print what's left of big file
            w.println("Caucasians not found in smaller file: " + idToLineBigfileCaucasian.size());
            w.println("Non-Caucasians not found in smaller file: " + idToLineBigfileNonCaucasian.size());
            Iterator iter = idToLineBigfileCaucasian.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry pair = (Map.Entry) iter.next();
                //System.out.println(pair.getKey() + " = " + pair.getValue());
                w.println(pair.getValue());
            }
            e.println("Read from big file: " + fromBig + ", read from small file: " + fromSmall);
            e.println("Found: " + matches);
            iter = found.iterator();
            while (iter.hasNext()) {
                e.println(iter.next().toString());
            }
            e.println("\n\n\n\n\n\n\n\nNot Found: " + unmatches);
            iter = notFound.iterator();
            while (iter.hasNext()) {
                e.println(iter.next().toString());
            }
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void findBarcodeInComments(String fileDir) {
        try {
            UserHttpSess httpSess = new UserHttpSess("c:/temp/log");

            //1.) build list of all samples in files
            HashSet<String> sampleList = new HashSet();
            String[] elem;
            String prefix;
            FileOutputStream outI; // declare a file output object
            FileOutputStream outU; // declare a file output object
            FileOutputStream err; // declare a file output object
            PrintStream pI; // declare a print stream object
            PrintStream pU; // declare a print stream object
            PrintStream e; // declare a print stream object

            String dateTime = icapture.com.Util.getCurrentDateTime();
            outU = new FileOutputStream(fileDir + "\\outUpdate" + dateTime + ".txt");
            err = new FileOutputStream(fileDir + "\\err" + dateTime + ".txt");

            // Connect print stream to the output stream
            pU = new PrintStream(outU);
            e = new PrintStream(err);
            Iterator iter = null;
            /*
            // for each content
            List contents = httpSess.getAllContainerContents(0, null, null);
            iter = contents.iterator();
            ContainerContent content = null;
            String comment = null;
            while(iter.hasNext()){
            // if it has a barcode in the comments, get barcodes from comments
            content = (ContainerContent) iter.next();
            comment = content.getComments();
            if(comment!=null && comment.indexOf("Barcode: ")>0){
            // get barcode
            int start = comment.indexOf("Barcode: ")+9;
            String barcode = comment.substring(start, start+7);
            e.println(barcode+"\t"+comment);
            // put barcode in barcode field
            pU.println("update allergen.tblcontainercontents " +
            " set barcode ='"+barcode +
            "' where containercontentsid="+content.getContainerContentsID()+";");
            }
            }
             */
            e.println("\nCONTAINERS");
            // for each containerList contents = httpSess.getAllContainerContents(0, null, null);

            List containers = httpSess.getAllObjects(Container.class);
            iter = containers.iterator();
            Container container = null;
            String name = null;
            String ccID = null;
            while (iter.hasNext()) {
                // if the containers has a barcode as a name
                container = (Container) iter.next();
                name = container.getcontainerName();
                if (name != null && name.indexOf("B") == 0) {
                    e.println(name);
                    // get their containerContent's ID (should only be one content)
                    List contentsFromContainer = httpSess.getAllContainerContents(0, container.getContainerID(), null);
                    if (contentsFromContainer.size() != 1) {
                        e.println("\nNumber of content for container " + name + " is " + contentsFromContainer.size());
                    } else {
                        // get genotypingsamplestatus for this containercontent
                        String barcodeContainerContentID = ((ContainerContent) contentsFromContainer.get(0)).getContainerContentsID();

                        // get the content with this barcode
                        HashMap code = new HashMap(1);
                        code.put("barcode", "'" + name + "'");
                        ContainerContent contentWithBarcode = (ContainerContent) httpSess.getObjectByUniqueKey(ContainerContent.class, code);
                        if (contentWithBarcode != null && barcodeContainerContentID != null) {
                            // replace the ccID of the genotypingsamplestatus object with
                            // the ccID of the containercontent with barcode field = the current container's barcode
                            pU.println("update ALLERGEN.TBLGENOTYPINGRUNSAMPLESTATUS "
                                    + " set containercontentsid =" + contentWithBarcode.getContainerContentsID()
                                    + " where containercontentsid=" + barcodeContainerContentID + ";");
                        }
                    }
                }
            }


            pU.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    private void useBarcodeToAddGenotypedStatus(String fileDir) {
    try {
    UserHttpSess httpSess = new UserHttpSess("c:/temp/log");

    String[] elem;
    FileOutputStream outI; // declare a file output object
    FileOutputStream err; // declare a file output object
    PrintStream pI; // declare a print stream object
    PrintStream e; // declare a print stream object

    String dateTime = icapture.com.Util.getCurrentDateTime();
    outI = new FileOutputStream(fileDir + "\\outGenotypedInsert" + dateTime + ".txt");
    err = new FileOutputStream(fileDir + "\\errGenotyped" + dateTime + ".txt");

    // Connect print stream to the output stream
    pI = new PrintStream(outI);
    e = new PrintStream(err);

    int one = 0, two = 0, three = 0;
    boolean exists = false;

    File files = new File(fileDir + "\\current");
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
    String containerContentsID = null;
    String callRate = null;
    String barcode = null;
    String genotypingRunID = null;

    // assign those you have values for
    //quantified = (elem.length > 9) ? "'"+elem[9].trim()+"'" : "";
    //quantified =(quantified.equals("''"))? "null":quantified;
    concentration = (elem.length > 4) ? elem[4] : "";
    volume = (elem.length > 5) ? elem[5] : "";
    comments = (elem.length > 8) ? "'"+elem[8]+"'" : "";
    comments =(comments.equals("''"))? "null":comments;

    // grab value to determine needed values
    String sampleName = (elem.length > 1) ? elem[1].trim() : "";
    String containerName = (elem.length > 2) ? elem[2].trim() : "";
    String well = (elem.length > 3) ? elem[3].trim() : "";

    if(concentration==null || concentration.equals("")){concentration="-1";}
    if(volume==null || volume.equals("")){volume="-1";}

    // ----- get containerID ---------
    if (containerName.length() <= 0) {
    e.println("No container name for sample"+sampleName);
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
    e.println("Well < 2 char for sample = " + sampleName+", well="+well);
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

    // check if it already exsists -- for updates
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
    exists = true;
    contentsID=ccO.getContainerContentsID();
    if(!(volume.equals(ccO.getVolume()) || ccO.getVolume().equals(volume+".0"))){
    e.println(sampleName+"\t"+containerName+"\t"+well+"\toldVol="+ccO.getVolume()+"\tnewVol="+volume);
    }
    if(!((concentration+".0").equals(ccO.getConcentration()) || concentration.equals(ccO.getConcentration()) )){
    //e.println(sampleName+"\t"+containerName+"\t"+well+"\toldConcen="+ccO.getConcentration()+"\tnewConcen="+concentration);
    }
    //continue;
    } else {
    //  e.println("No existing sample at \t"+containerName+"\t"+well+"\t("+sampleName+")");
    two++;
    }
    /*
    //updates
    //if(exists){
    pI.println("UPDATE allergen.tblcontainercontents " +
    "set row= "+row+",column="+column+", containerID="+containerID+
    ", sampleID = " +sampleID +//", comment = "+comments+
    ", concentration =" +concentration +", volume="+volume+
    " where containercontentsid = "+ccID+"; ");

    /*   pU.println("DELETE FROM allergen.tblcontainercontents "
    + " where containerID = " + containerID + " AND row = " + row + " AND column = " + col + ";");
     */
    //}
    // inserts
/*
    if(!exists){
    pI.println("Insert into allergen.tblcontainercontents " +
    "(containerID,row,column,sampleID,volume,concentration,parentID,contaminated,dilution,comments,created,created_By,quantified,materialTypeID,amplificationDate,barcode) " +
    " VALUES ("+containerID+", "+row+", "+column+", "+sampleID+", "+volume+", "+concentration+", "+parentID+", "+contaminated+", "+dilution+", "+comments+", "+created+", "+createdBy+", "+quantified+", "+materialTypeID+", "+amplificationDate+", "+barcode+");");
    }
    }
    }
    e.println(one + " existed. " + two + " didn't.");
    }
    pI.close();
    pU.close();
    e.close();
    } catch (Exception e) {
    e.printStackTrace();
    }

    }
    }
     */
    /**
     * get container ID for container name or null if no match found
     * @param httpSess
     * @param containerName
     * @return
     */
    public String getContainerID(UserHttpSess httpSess, String containerName) {
        // get containerID
        if (containerName.length() > 0) {
            HashMap contName = new HashMap(3);
            contName.put("containerName", "'" + containerName + "'");
            Container container = (Container) httpSess.getObjectByUniqueKey(Container.class, contName);
            if (container == null) {
                return null;
            }
            return container.getContainerID();
        }
        return null;
    }

    public String[] getRowColFromWell(UserHttpSess httpSess, String well) {
        if (well.length() < 2) {
            return null;
        }
        String row = String.valueOf(well.charAt(0));
        if (!Character.isDigit(well.charAt(0))) {
            row = (row.equalsIgnoreCase("a") ? "1" : row.equalsIgnoreCase("b") ? "2" : row.equalsIgnoreCase("c") ? "3" : row.equalsIgnoreCase("d") ? "4" : row.equalsIgnoreCase("e") ? "5" : row.equalsIgnoreCase("f") ? "6" : row.equalsIgnoreCase("g") ? "7" : row.equalsIgnoreCase("h") ? "8" : row.equalsIgnoreCase("i") ? "9" : row.equalsIgnoreCase("j") ? "10" : row.equalsIgnoreCase("k") ? "11" : row.equalsIgnoreCase("l") ? "12" : row.equalsIgnoreCase("m") ? "13" : row.equalsIgnoreCase("n") ? "14" : row.equalsIgnoreCase("o") ? "15" : row.equalsIgnoreCase("p") ? "16" : row.equalsIgnoreCase("q") ? "17" : row.equalsIgnoreCase("r") ? "18" : row.equalsIgnoreCase("s") ? "19" : row.equalsIgnoreCase("t") ? "20" : row.equalsIgnoreCase("u") ? "21" : row.equalsIgnoreCase("v") ? "22" : row.equalsIgnoreCase("w") ? "23" : row.equalsIgnoreCase("x") ? "24" : row.equalsIgnoreCase("y") ? "25" : row.equalsIgnoreCase("z") ? "26" : row);
        }
        String column = well.substring(1);
        String[] ar = {row, column};
        return ar;
    }
}
