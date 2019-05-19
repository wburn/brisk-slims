/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icapture;

import icapture.Tools;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import icapture.com.UserHttpSess;
import icapture.genapha.GenaphaTools;
import icapture.genapha.GenotypeToolsManager;
import icapture.hibernate.Cohort;
import icapture.hibernate.Container;
import icapture.hibernate.ContainerContent;
import icapture.hibernate.ContainerType;
import icapture.hibernate.Ethnicity;
import icapture.hibernate.Genotype;
import icapture.hibernate.GenotypingRun;
import icapture.hibernate.MaterialType;
import icapture.hibernate.Sample;
import icapture.hibernate.SampleType;
import icapture.hibernate.ShippedTo;
import icapture.hibernate.Snp;
import icapture.hibernate.Subject;
import icapture.hibernate.User;
import java.awt.Point;
import java.io.BufferedWriter;
import java.io.FileWriter;
import org.hibernate.Transaction;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btripp
 */
public class AVSDataLoader {

    UserHttpSess userHttpSess = null;
    HashMap<String, Double> sampleCallRateMap = new HashMap<String, Double>();
    HashMap<String, Double> snpCallRateMap = new HashMap<String, Double>();

    public AVSDataLoader(UserHttpSess userHttpSess) {
        this.userHttpSess = userHttpSess;
    }

    private void loadSnpCallRates(File plinkSnpCallRates) {
        BufferedReader reader = null;
        String line;
        String[] elem;

        try {
            reader = new BufferedReader(new FileReader(plinkSnpCallRates));
            while ((line = reader.readLine()) != null) {
                elem = line.split("\\s");
                snpCallRateMap.put(elem[0], new Double(elem[3]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSampleCallRates(File plinkSampleCallRates) {
        BufferedReader reader = null;
        String line;
        String[] elem;

        try {
            reader = new BufferedReader(new FileReader(plinkSampleCallRates));
            while ((line = reader.readLine()) != null) {
                elem = line.split("\\s");
                sampleCallRateMap.put(elem[0], new Double(elem[3]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load() {
        //new Thread() {

        //public void run() {
        long start;
        long end;

        start = System.currentTimeMillis();

        try {
            //verifyLoad();
            //doLoad();

            userHttpSess.setCurrentUser(userHttpSess.getUser("admin", "admin"));
            //loadSamplesAndContainers();
            loadGenotypes(new File("./MCDONALD.ped"), new File("./success_samp.txt"));
            //loadGenotypes(new File("D:/AllerGenGabrielGWAS/MCDONALD.ped"), new File("D:/AllerGenGabrielGWAS/success_samp.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        end = System.currentTimeMillis();
        System.out.println("Processing time: " + ((end - start) / 1000) / 60 + " minutes. ");
        //}
        //}.start();


    }

    private void loadSamplesAndContainers() throws Exception {
        ArrayList<String> fileContents = Tools.readFile(new File("./DB/SampleReport.txt"));
        if (fileContents.size() > 0) {

            GenotypingRun genotypingRun = userHttpSess.getGenotypingRun("AVS Genotyping 1");
            if (genotypingRun == null) {
                Date date = userHttpSess.getDate("2009", "3", "3", "ayear", "amonth", "aday");
                userHttpSess.addGenotypingRun("-1", "AVS Genotyping 1", date);
                genotypingRun = userHttpSess.getGenotypingRun("AVS Genotyping 1");
            }

            fileContents.remove(0); //remove header
            for (String line : fileContents) {
                String[] elem = line.split("\t");
                String location = elem[8];
                Container container = userHttpSess.getContainer(location.split("-")[0].trim());
                if (container == null) {
                    container = add96WellPlate(location.split("-")[0].trim(), "03-03-2009");
                    userHttpSess.addGenotypingRunContainer(genotypingRun, container);
                }
                if (container != null) {
                    String sampleName = elem[1];
                    String iid = elem[2];
                    String gender;

                    if (elem[4].equalsIgnoreCase("male")) {
                        gender = "1";
                    } else if (elem[4].equalsIgnoreCase("female")) {
                        gender = "2";
                    } else {
                        gender = "0";
                    }

                    Sample sample = addSample(sampleName, iid, gender);

                    Point p = parseWellPosition(location.split("-")[1].trim());
                    ContainerContent containerContent = userHttpSess.getContainerContent(container, p.x + "", p.y + "");
                    if (containerContent == null) {
                        userHttpSess.addContainerContent("-1", null, "1", container, p.x + "", p.y + "", sample, "-1", "-1", "-1", null, null, (MaterialType) userHttpSess.getObjectById(MaterialType.class, "0"));
                        containerContent = userHttpSess.getContainerContent(container, p.x + "", p.y + "");
                    }

                    //try{

                    //catch()
                    Double callrate = null;
                    try {
                        callrate = new Double(elem[14]);
                    } catch (Exception e) {
                        System.err.println("Error parsing callrate for sample " + elem[0]);
                    }
                    String valid = "1";
                    if (elem[0].trim().equalsIgnoreCase("failed")) {
                        valid = "0";
                    }
                    userHttpSess.addGenotypingRunSampleStatus(genotypingRun, containerContent, valid, callrate);
                } else {
                    System.out.println("Scipted container failed - " + elem[0]);
                }
            }
        }
    }

    private Point parseWellPosition(String position) {
        String rowPos = position.substring(0, 1);
        String colPos = position.substring(1, 3);
        Point p = new Point();
        if (rowPos.equals("A")) {
            p.x = 1;
        } else if (rowPos.equals("B")) {
            p.x = 2;
        } else if (rowPos.equals("C")) {
            p.x = 3;
        } else if (rowPos.equals("D")) {
            p.x = 4;
        } else if (rowPos.equals("E")) {
            p.x = 5;
        } else if (rowPos.equals("F")) {
            p.x = 6;
        } else if (rowPos.equals("G")) {
            p.x = 7;
        } else if (rowPos.equals("H")) {
            p.x = 8;
        }

        if (colPos.equals("01")) {
            p.y = 1;
        } else if (colPos.equals("02")) {
            p.y = 2;
        } else if (colPos.equals("03")) {
            p.y = 3;
        } else if (colPos.equals("04")) {
            p.y = 4;
        } else if (colPos.equals("05")) {
            p.y = 5;
        } else if (colPos.equals("06")) {
            p.y = 6;
        } else if (colPos.equals("07")) {
            p.y = 7;
        } else if (colPos.equals("08")) {
            p.y = 8;
        } else if (colPos.equals("09")) {
            p.y = 9;
        } else if (colPos.equals("10")) {
            p.y = 10;
        } else if (colPos.equals("11")) {
            p.y = 11;
        } else if (colPos.equals("12")) {
            p.y = 12;
        }

        return p;

    }

    private void doLoad() throws Exception {
        //load freezers

        //load containers
        addContainers(new File("D:/Gabriel Samples/Sent Samples/GWAS Batch 1.txt"));

        //load sampleList

        //load shippedTo

        //load container contents
    }

    private void verifyLoad() {
        //load freezers

        //load containers
        verifyContainers(new File("D:/Gabriel Samples/Sent Samples/GWAS Batch 1.txt"));

        //load sampleList

        //load shippedTo

        //load container contents
    }

    private void verifyContainers(File containerFile) {
        ArrayList<String> fileContents = Tools.readFile(containerFile);
        if (fileContents.size() > 0) {
            fileContents.remove(0); //remove header
            for (String line : fileContents) {
                String[] elem = line.split("\t");
                Container container = userHttpSess.getContainer(elem[0]);
                if (container != null) {
                    System.out.println("Container already exists - " + elem[0]);
                }
            }
        }

    }

    private void addContainers(File containerFile) throws Exception {
        ArrayList<String> fileContents = Tools.readFile(containerFile);
        if (fileContents.size() > 0) {
            fileContents.remove(0); //remove header
            for (String line : fileContents) {
                userHttpSess.openHibSession();
                String[] elem = line.split("\t");
                String containerName = elem[0];
                Date shippedDate = new Date();
                ContainerType containerType = userHttpSess.getContainerType("Tube");
                //Freezer freezer;
                String shelf = null;
                String discarded = "0";
                ShippedTo shippedTo = userHttpSess.getShippedTo("GABRIEL");
                String shippedout = "1";
                String date = elem[5];
                shippedDate = userHttpSess.getDate(date.split("-")[2], date.split("-")[0], date.split("-")[1], "ayear", "amonth", "aday");
                userHttpSess.addShipment("-1", "AVSLoadedShipment", shippedDate, shippedTo, shippedout, null);
                userHttpSess.addContainer("-1", containerName, containerType, null, shelf, discarded, null, null, null, "1", null, null, null, null, null);
                userHttpSess.addShipCont("-1", userHttpSess.getCurrentShipment(), userHttpSess.getCurrentContainer());

            }
        }

    }

    private Container add96WellPlate(String containerName, String date) throws Exception {

        userHttpSess.openHibSession();

        ContainerType containerType = userHttpSess.getContainerType("96 Well Plate");
        //Freezer freezer;
        String shelf = null;
        String discarded = "0";
        String shippedout = "0";

        userHttpSess.addContainer("-1", containerName, containerType, null, shelf, discarded, null, "0", "1", "1", null, null, null, null, null);
        return userHttpSess.getContainer(containerName);


    }

    private Container addCNGContainer(String containerName, String date) throws Exception {

        userHttpSess.openHibSession();

        Date shippedDate = new Date();
        ContainerType containerType = userHttpSess.getContainerType("Tube");
        //Freezer freezer;
        String shelf = null;
        String discarded = "0";
        ShippedTo shippedTo = userHttpSess.getShippedTo("GABRIEL");
        String shippedout = "1";
        shippedDate = userHttpSess.getDate(date.split("-")[2], date.split("-")[0], date.split("-")[1], "ayear", "amonth", "aday");
        userHttpSess.addShipment("-1", "AVSLoadedShipment", shippedDate, shippedTo, shippedout, null);
        userHttpSess.addContainer("-1", containerName, containerType, null, shelf, discarded, null, null, null, "1", null, null, null, null, null);
        userHttpSess.addShipCont("-1", userHttpSess.getCurrentShipment(), userHttpSess.getCurrentContainer());
        return userHttpSess.getContainer(containerName);


    }

    private Sample addSample(String sampleName, String subjectName, String subjectGender) throws Exception {

        userHttpSess.openHibSession();


        Ethnicity ethnicity = (Ethnicity) userHttpSess.getObjectById(Ethnicity.class, "1"); //unknown
        Cohort cohort = (Cohort) userHttpSess.getObjectById(Cohort.class, "1"); //AVS

        Subject subject = userHttpSess.getSubject(cohort, subjectName);

        if (subject == null) {
            //create subject

            //userHttpSess.addSubject("", subjectName, "0", "0", cohort, subjectGender, sampleName, "1", ethnicity, "");
            icapture.beans.DB.Subject sub = new icapture.beans.DB.Subject();
            sub.setMotherID("0");
            sub.setFatherID("0");
            sub.setCohort(SQLMgr.getCohort(1));
            sub.setEthnicity(SQLMgr.getEthnicity(1));
            sub.setFamilyID(subjectName);
            sub.setGender(Integer.parseInt(subjectGender));
            sub.setPassedQC(true);
            sub.setHasConsent(true);
            sub.setID(subjectName);

            SQLMgr.insertSubject(sub);
            SQLMgr.updateSubject(sub);

            subject = userHttpSess.getSubject(cohort, subjectName);
        }

        SampleType sampleType = userHttpSess.getSampleType("");

        if (subject != null) {

            userHttpSess.addSample("", sampleName, "1", null, sampleType, subject, null, null, null);
            return userHttpSess.getSample(subject, sampleName);

        } else {
            System.err.println("Subject not found " + sampleName);
            return null;
        }
    }

    @SuppressWarnings("static-access")
    private void loadGenotypes(File genFile, File sampleFile) {
        BufferedReader reader = null;
        Snp[] snpArray;
        String line;
        String[] elem;
        String a1;
        String a2;
        long count = 0;

        long start;
        long end;

        start = System.currentTimeMillis();

        try {
            GenotypingRun genotypingRun = userHttpSess.getGenotypingRun("CNG (GABRIEL)");
            HashMap<String, String> barcodeMap = new HashMap<String, String>();
            ArrayList<String> fileContents = Tools.readFile(sampleFile);
            fileContents.remove(0); //remove header
            for (String s : fileContents) {
                elem = s.split("\\s");
                barcodeMap.put(elem[1].trim() + "-" + elem[2].trim(), elem[0].trim());
            }

            reader = new BufferedReader(new FileReader(genFile));

            line = reader.readLine();

            HashMap<String, Snp> snpMap = new HashMap<String, Snp>();
            List<Snp> snps = userHttpSess.getAllSnps(-1);
            for (Snp snp : snps) {
                snpMap.put(snp.getRsNumber(), snp);
            }

            //parse header
            elem = line.split("\\s");
            snpArray = new Snp[elem.length - 6];
            for (int i = 0; i < snpArray.length; i++) {
                snpArray[i] = snpMap.get(elem[i + 6].trim());
            }

            Transaction tx = userHttpSess.hibSess.beginTransaction();
            int linecount = 1;
            while ((line = reader.readLine()) != null) {
                String famID;
                String ID;

                elem = line.split("\\s");

                famID = elem[0].trim().split(":")[1];
                ID = elem[1].trim();


                ContainerContent containerContent = userHttpSess.getContainerContent(barcodeMap.get(famID + "-" + ID));

                for (int i = 0; i < snpArray.length; i++) {
                    if (snpArray[i] != null) {
                        a1 = elem[(2 * i) + 6].trim();
                        a2 = elem[(2 * i) + 7].trim();


                        Genotype tmpGenotype = new Genotype();
                        //tmpGenotype.setCreateInfo(currentUser);
                        //tmpGenotype.setGenotypeID(genotypeID);
                        tmpGenotype.setAllele1(a1);
                        tmpGenotype.setAllele2(a2);
                        ContainerContent tmpCC = null;
                        GenotypingRun tmpGR = null;
                        Snp tmpS = null;
                        String containerContentsID = containerContent.getContainerContentsID();
                        String genotypingRunID = genotypingRun.getGenotypingRunID();
                        String snpID = snpArray[i].getSnpID();
                        if (containerContentsID != null && !containerContentsID.equals("") && Integer.parseInt(containerContentsID) > 0) {
                            tmpCC = (ContainerContent) userHttpSess.getObjectById(ContainerContent.class, containerContentsID);
                        }
                        if (genotypingRunID != null && !genotypingRunID.equals("") && Integer.parseInt(genotypingRunID) > 0) {
                            tmpGR = (GenotypingRun) userHttpSess.getObjectById(GenotypingRun.class, genotypingRunID);
                        }
                        if (snpID != null && !snpID.equals("") && Integer.parseInt(snpID) > 0) {
                            tmpS = (Snp) userHttpSess.getObjectById(Snp.class, snpID);
                        }
                        tmpGenotype.setContainerContents(tmpCC);
                        tmpGenotype.setGenotypingRun(tmpGR);
                        tmpGenotype.setSnp(tmpS);

                        userHttpSess.hibSess.save(tmpGenotype);

                        count++;

                        if (count % 100 == 0) {
                            userHttpSess.hibSess.flush();
                            userHttpSess.hibSess.clear();
                        }

                        if (count % 1000 == 0) {
                            tx.commit();
                            tx.begin();
                            end = System.currentTimeMillis();
                            System.out.println("Processing time: " + ((end - start) / 1000) + " sec. ");
                            System.err.println("inserted " + count + " genotypes.");
                        }

                    }
                }

                if (linecount % 10 == 0) {
                    System.err.println("processed " + linecount + " lines.");
                }
                linecount++;
            }
            tx.commit();
            userHttpSess.closeHibSession();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private static void makeGenotypeImportFiles() throws Exception {
        File genFile = new File("./MCDONALD.ped");
        File sampleFile = new File("./success_samp.txt");
        BufferedWriter br = new BufferedWriter(new FileWriter(new File("./tblgenotypes.txt")));

        UserHttpSess userHttpSess = new UserHttpSess(".");

        BufferedReader reader = null;
        int[] snpArray;
        String line;
        String[] elem;
        String a1;
        String a2;
        long count = 1;
        long fileCount = 1;
        try {
            GenotypingRun genotypingRun = userHttpSess.getGenotypingRun("CNG (GABRIEL)");
            HashMap<String, String> barcodeMap = new HashMap<String, String>();
            ArrayList<String> fileContents = Tools.readFile(sampleFile);
            fileContents.remove(0); //remove header
            for (String s : fileContents) {
                elem = s.split("\\s");
                barcodeMap.put(elem[1].trim() + "-" + elem[2].trim(), elem[0].trim());
            }

            reader = new BufferedReader(new FileReader(genFile));

            line = reader.readLine();

            HashMap<String, Snp> snpMap = new HashMap<String, Snp>();
            List<Snp> snps = userHttpSess.getAllSnps(-1);
            for (Snp snp : snps) {
                snpMap.put(snp.getRsNumber(), snp);
            }

            //parse header
            elem = line.split("\\s");
            snpArray = new int[elem.length - 6];
            for (int i = 0; i < snpArray.length; i++) {
                Snp snp = snpMap.get(elem[i + 6].trim());
                if (snp == null) {
                    System.out.println("missing snp - " + elem[i + 6].trim());
                    snpArray[i] = -1;
                } else {
                    snpArray[i] = Integer.parseInt(snp.getSnpID());
                }


            }

            long start;
            long end;


            start = System.currentTimeMillis();

            int linecount = 1;
            while ((line = reader.readLine()) != null) {
                String famID;
                String ID;

                elem = line.split("\\s");

                famID = elem[0].trim().split(":")[1];
                ID = elem[1].trim();


                ContainerContent containerContent = userHttpSess.getContainerContent(barcodeMap.get(famID + "-" + ID));

                for (int i = 0; i < snpArray.length; i++) {

                    if (snpArray[i] >= 0) {

                        a1 = elem[(2 * i) + 6].trim();
                        a2 = elem[(2 * i) + 7].trim();

                        try {
                            String linew = containerContent.getContainerContentsID() + "," + genotypingRun.getGenotypingRunID()
                                    + "," + snpArray[i] + ",\"" + a1 + "\",\"" + a2 + "\"";
                            br.write(linew);
                            br.newLine();



                            if (count % 1000000 == 0) {
                                end = System.currentTimeMillis();
                                System.out.println("Processing time: " + ((end - start) / 1000) + " sec. ");
                                System.out.println("inserted " + count);
                                //System.err.println("inserted " + count);
                            }

                            if (count % 100000000 == 0) {
                                br.close();
                                br = new BufferedWriter(new FileWriter(new File("./tblgenotypes-" + fileCount + ".txt")));
                                fileCount++;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        count++;
                    }


                }

                if (linecount % 10 == 0) {
                    System.err.println("processed " + linecount + " lines.");
                }
                linecount++;
            }


            br.close();
            userHttpSess.closeHibSession();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private static void compareSNPs() throws Exception {
        File genFile = new File("./plink.map");
        File markerFile = new File("./success_mark.txt");

        BufferedReader reader = null;
        HashMap<String, String> snpArray = new HashMap<String, String>();
        String line;
        String[] elem;
        String a1;
        String a2;
        long count = 1;
        long fileCount = 1;
        try {

            reader = new BufferedReader(new FileReader(genFile));
            while ((line = reader.readLine()) != null) {
                elem = line.split("\\s");
                snpArray.put(elem[1], elem[1]);
            }
            reader.close();

            //parse header
//            elem = line.split("\\s");
//            String rs;
//            for(int i=6;i<elem.length;i++){
//                rs = elem[i].trim();
//                snpArray.put(rs, rs);
//            }
//            reader.close();

            System.out.println(snpArray.size() + " SNPs in ped file");

            //parse marker file
            reader = new BufferedReader(new FileReader(markerFile));
            while ((line = reader.readLine()) != null) {
                elem = line.split("\\s");
                if (!(snpArray.get(elem[1].trim()) != null || snpArray.get(elem[2].trim()) != null)) {
                    System.out.println(line);
                }
            }
            reader.close();

//            System.out.println(" Parse marker file");
//             //parse marker file
//            reader = new BufferedReader(new FileReader(markerFile));
//            while((line = reader.readLine()) != null) {
//                elem = line.split("\\s");
//                if(elem[9].startsWith("0") ){
//                    System.out.println(line);
//                }
//            }
//            reader.close();



        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private static void makeMapFile() throws Exception {
        File genFile = new File("./MCDONALD.ped");

        BufferedWriter map = new BufferedWriter(new FileWriter(new File("./plink2.map")));


        UserHttpSess userHttpSess = new UserHttpSess(".");

        BufferedReader reader = null;
        Snp[] snpArray;
        String line;
        String[] elem;
        String a1;
        String a2;
        long count = 1;
        long fileCount = 1;
        try {

            reader = new BufferedReader(new FileReader(genFile));

            line = reader.readLine();

            HashMap<String, Snp> snpMap = new HashMap<String, Snp>();
            List<Snp> snps = userHttpSess.getAllSnps(-1);
            for (Snp snp : snps) {
                snpMap.put(snp.getRsNumber(), snp);
            }

            //parse header
            elem = line.split("\\s");
            snpArray = new Snp[elem.length - 6];
            for (int i = 0; i < snpArray.length; i++) {
                snpArray[i] = snpMap.get(elem[i + 6].trim());

                //writemap file
                if (snpArray[i] != null) {
                    map.write(snpArray[i].getChromosome() + "\t" + snpArray[i].getRsNumber() + "\t0\t" + snpArray[i].getPos());
                    map.newLine();
                } else {
                    System.out.println("SNP not found " + snpArray[i]);

                }
            }
            map.close();

            userHttpSess.closeHibSession();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private static void makePlinkFiles() throws Exception {
        File genFile = new File("./MCDONALD.ped");
        File sampleFile = new File("./success_samp.txt");
        BufferedWriter ped = new BufferedWriter(new FileWriter(new File("./plink2.ped")));
        BufferedWriter map = new BufferedWriter(new FileWriter(new File("./plink2.map")));

        BufferedWriter fbat = new BufferedWriter(new FileWriter(new File("./fbat2.ped")));

        UserHttpSess userHttpSess = new UserHttpSess(".");

        BufferedReader reader = null;
        Snp[] snpArray;
        String line;
        String[] elem;
        String a1;
        String a2;
        long count = 1;
        long fileCount = 1;
        try {
            GenotypingRun genotypingRun = userHttpSess.getGenotypingRun("CNG (GABRIEL)");
            HashMap<String, String> barcodeMap = new HashMap<String, String>();
            ArrayList<String> fileContents = Tools.readFile(sampleFile);
            fileContents.remove(0); //remove header
            for (String s : fileContents) {
                elem = s.split("\\s");
                barcodeMap.put(elem[1].trim() + "-" + elem[2].trim(), elem[0].trim());
            }

            reader = new BufferedReader(new FileReader(genFile));

            line = reader.readLine();

            HashMap<String, Snp> snpMap = new HashMap<String, Snp>();
            List<Snp> snps = userHttpSess.getAllSnps(-1);
            for (Snp snp : snps) {
                snpMap.put(snp.getRsNumber(), snp);
            }

            //parse header
            elem = line.split("\\s");
            snpArray = new Snp[elem.length - 6];
            for (int i = 0; i < snpArray.length; i++) {
                snpArray[i] = snpMap.get(elem[i + 6].trim());

                //writemap file
                if (snpArray[i] != null) {
                    map.write(snpArray[i].getChromosome() + "\t" + snpArray[i].getRsNumber() + "\t0\t" + snpArray[i].getPos());
                    map.newLine();
                    fbat.write(snpArray[i].getRsNumber() + "\t");
                } else {
                    System.out.println("SNP not found " + elem[i + 6].trim());

                }
            }
            map.close();
            fbat.newLine();



            long start;
            long end;


            start = System.currentTimeMillis();

            int linecount = 1;
            while ((line = reader.readLine()) != null) {
                String famID;
                String ID;

                elem = line.split("\\s");

                famID = elem[0].trim().split(":")[1];
                ID = elem[1].trim();


                ContainerContent containerContent = userHttpSess.getContainerContent(barcodeMap.get(famID + "-" + ID));
                Subject sub = containerContent.getSample().getSubject();

                ped.write(sub.getCohort().getDescription() + "_" + sub.getFamilyID() + "\t" + sub.getSubjectName() + "\t" + sub.getFatherName() + "\t" + sub.getMotherName() + "\t" + sub.getGender() + "\t0");
                fbat.write(sub.getCohort().getDescription() + "_" + sub.getFamilyID() + "\t" + sub.getSubjectName() + "\t" + sub.getFatherName() + "\t" + sub.getMotherName() + "\t" + sub.getGender() + "\t0");

                for (int i = 0; i < snpArray.length; i++) {

                    if (snpArray[i] != null) {

                        a1 = elem[(2 * i) + 6].trim();
                        a2 = elem[(2 * i) + 7].trim();

                        ped.write("\t" + a1 + " " + a2);
                        fbat.write("\t" + a1 + " " + a2);
                    }


                }
                ped.newLine();
                fbat.newLine();
                if (linecount % 10 == 0) {
                    System.err.println("processed " + linecount + " lines.");
                }
                linecount++;
            }


            fbat.close();
            ped.close();
            userHttpSess.closeHibSession();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private static void lineCount(File file) throws Exception {



        BufferedReader reader = null;



        try {


            reader = new BufferedReader(new FileReader(file));


            String line;
            int linecount = 1;
            while ((line = reader.readLine()) != null) {

                linecount++;
            }

            System.out.println(linecount + "");


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private static void createOverlapSubjects() {
        ArrayList<String> fileContents = GenaphaTools.readFile(new File("./overlapFams.txt"));
        ArrayList<String> pedFileContents = GenaphaTools.readFile(new File("./Plink/overlap.fam"));
        for (String line : pedFileContents) {

            for (String s : fileContents) {
                String[] elem = s.split("\t");
                if (line.startsWith("CAPPS_" + elem[0])) {
                    line = line.replaceAll("CAPPS_" + elem[0], "SAGE_" + elem[1]);
                    line = line.replaceAll(elem[0] + "-1", elem[1]);
                    line = line.replaceAll(elem[0] + "-3", elem[1] + "-1");
                    line = line.replaceAll(elem[0] + "-4", elem[1] + "-1");
                    line = line.replaceAll(elem[0] + "-5", elem[1] + "-2");
                    if (line.startsWith("SAGE")) {
                        //we did replacement so exit loop
                        break;
                    }
                } else if (line.startsWith("SAGE_" + elem[1])) {
                    line = line.replaceAll("SAGE_" + elem[1], "CAPPS_" + elem[0]);
                    line = line.replaceAll(elem[1] + " ", elem[0] + "-1 ");
                    line = line.replaceAll(elem[1] + "-2", elem[0] + "-5");

                    //get mom from DB
                    if (SQLMgr.getSubject(elem[0] + "-3", 3) != null) {
                        line = line.replaceAll(elem[1] + "-1", elem[0] + "-3");
                    } else {
                        line = line.replaceAll(elem[1] + "-1", elem[0] + "-4");
                    }


                    if (line.startsWith("CAPPS")) {
                        //we did replacement so exit loop
                        break;
                    }
                }
            }
            System.out.println(line);
        }
    }

    private static void createOverlapSubjects(String famFile) {

        ArrayList<String> pedFileContents = GenaphaTools.readFile(new File(famFile));
        for (String line : pedFileContents) {

            String fid = line.split("\\s")[0];
            String[] elems = fid.split("_");
            if (fid.startsWith("CAPPS_")) {
                String capps_fid = elems[1];
                String sage_fid = SQLMgr.getSageOverlapFamilyID(capps_fid);

                line = line.replaceAll("CAPPS_" + capps_fid, "SAGE_" + sage_fid);
                line = line.replaceAll(capps_fid + "-1", sage_fid);
                line = line.replaceAll(capps_fid + "-3", sage_fid + "-1");
                line = line.replaceAll(capps_fid + "-4", sage_fid + "-1");
                line = line.replaceAll(capps_fid + "-5", sage_fid + "-2");

            } else if (fid.startsWith("SAGE_")) {
                String sage_fid = elems[1];
                String capps_fid = SQLMgr.getCappsOverlapFamilyID(sage_fid);

                line = line.replaceAll("SAGE_" + sage_fid, "CAPPS_" + capps_fid);
                line = line.replaceAll(sage_fid + " ", capps_fid + "-1 ");
                line = line.replaceAll(sage_fid + "-2", capps_fid + "-5");

                //get mom from DB
                if (SQLMgr.getSubject(capps_fid + "-3", 3) != null) {
                    line = line.replaceAll(sage_fid + "-1", capps_fid + "-3");
                } else {
                    line = line.replaceAll(sage_fid + "-1", capps_fid + "-4");
                }
            }
            System.out.println(line);
        }
    }

    private static String createOverlapSubject(String fid, String iid) {
        String line = fid + "\t" + iid;
        String[] elems = fid.split("_");
        if (fid.startsWith("CAPPS_")) {
            String capps_fid = elems[1];
            String sage_fid = SQLMgr.getSageOverlapFamilyID(capps_fid);

            line = line.replaceAll("CAPPS_" + capps_fid, "SAGE_" + sage_fid);
            line = line.replaceAll(capps_fid + "-1", sage_fid);
            line = line.replaceAll(capps_fid + "-3", sage_fid + "-1");
            line = line.replaceAll(capps_fid + "-4", sage_fid + "-1");
            line = line.replaceAll(capps_fid + "-5", sage_fid + "-2");

        } else if (fid.startsWith("SAGE_")) {
            String sage_fid = elems[1];
            String capps_fid = SQLMgr.getCappsOverlapFamilyID(sage_fid);

            line = line.replaceAll("SAGE_" + sage_fid, "CAPPS_" + capps_fid);
            line = line.replaceAll(sage_fid + " ", capps_fid + "-1 ");
            line = line.replaceAll(sage_fid + "-2", capps_fid + "-5");

            //get mom from DB
            if (SQLMgr.getSubject(capps_fid + "-3", 3) != null) {
                line = line.replaceAll(sage_fid + "-1", capps_fid + "-3");
            } else {
                line = line.replaceAll(sage_fid + "-1", capps_fid + "-4");
            }
        }
        return line;

    }

    private static void createOverlapSubjectList() {
        ArrayList<String> fileContents = GenaphaTools.readFile(new File("./overlapFams.txt"));
        for (String s : fileContents) {
            String[] elem = s.split("\t");
            System.out.println("CAPPS_" + elem[0] + " " + elem[0] + "-1");
            System.out.println("CAPPS_" + elem[0] + " " + elem[0] + "-2");
            System.out.println("CAPPS_" + elem[0] + " " + elem[0] + "-3");
            System.out.println("CAPPS_" + elem[0] + " " + elem[0] + "-4");
            System.out.println("CAPPS_" + elem[0] + " " + elem[0] + "-5");

            System.out.println("SAGE_" + elem[1] + " " + elem[1]);
            System.out.println("SAGE_" + elem[1] + " " + elem[1] + "-1");
            System.out.println("SAGE_" + elem[1] + " " + elem[1] + "-2");

        }
    }

    private static void createNewSubjectIDs(File plinkFamFile) {
        ArrayList<String> fileContents = GenaphaTools.readFile(plinkFamFile);
        for (String line : fileContents) {
            String[] elem = line.split("\\s");
            String cohort = elem[0].split("_")[0];
            elem[1] = cohort + "_" + elem[1];
            if (!elem[2].equalsIgnoreCase("0")) {
                elem[2] = cohort + "_" + elem[2];
            }
            if (!elem[3].equalsIgnoreCase("0")) {
                elem[3] = cohort + "_" + elem[3];
            }

            for (String s : elem) {
                System.out.print(s + " ");
            }
            System.out.println();

        }
    }

    private static void processOverlapDiscepacies(File file) {
        //todo add errors by snp, subject. add snp call rate and subject callrate

        ArrayList<String> fileContents = GenaphaTools.readFile(file);
        for (String line : fileContents) {
            while (line.contains("  ")) {
                line = line.replaceAll("  ", " ");
            }
            String[] elem = line.trim().split("\\s");
            String sageFIDIID = createOverlapSubject(elem[1], elem[2]);
            System.out.println(elem[0] + "\t" + elem[1] + "\t" + elem[2] + "\t" + elem[4] + "\t" + sageFIDIID + "\t" + elem[3]);
        }
    }

    public static void main(String[] args) {
        ConnectionMgr.setConnectionType(ConnectionMgr.CONNECTION_TYPE_DIRECT);
        UserHttpSess httpSess = new UserHttpSess(".");

        //loadSamplesAndContainers();


        AVSDataLoader dataLoader = new AVSDataLoader(httpSess);
        //cNGDataLoader.load();
        GenotypeToolsManager gtm;
        try {
            httpSess.openHibSession();
            httpSess.logIn("admin", "admin");
            String row = "";
            String col = "";
            dataLoader.loadSamplesAndContainers();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
