/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icapture;

import icapture.Tools;
import icapture.beans.DB.PhenotypeValue;
import icapture.beans.DB.Phenotypes;
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
import icapture.hibernate.Genotype;
import icapture.hibernate.GenotypingRun;
import icapture.hibernate.Sample;
import icapture.hibernate.SampleType;
import icapture.hibernate.ShippedTo;
import icapture.hibernate.Snp;
import icapture.hibernate.Subject;
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
public class CNGDataLoader {

    UserHttpSess userHttpSess = null;
    HashMap<String, Double> sampleCallRateMap = new HashMap<String, Double>();
    HashMap<String, Double> snpCallRateMap = new HashMap<String, Double>();

    public CNGDataLoader(UserHttpSess userHttpSess) {
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
        ArrayList<String> fileContents = Tools.readFile(new File("D:/AllerGenGabrielGWAS/success_samp.txt"));
        if (fileContents.size() > 0) {

            GenotypingRun genotypingRun = userHttpSess.getGenotypingRun("Gabriel Genotyping");
            if (genotypingRun == null) {
                Date date = userHttpSess.getDate("2009", "5", "5", "ayear", "amonth", "aday");
                userHttpSess.addGenotypingRun("-1", "Gabriel Genotyping", date);
                genotypingRun = userHttpSess.getGenotypingRun("Gabriel Genotyping");
            }

            fileContents.remove(0); //remove header
            for (String line : fileContents) {
                String[] elem = line.split("\t");
                Container container = userHttpSess.getContainer(elem[0].trim());
                if (container == null) {
                    container = addCNGContainer(elem[0].trim(), "12-14-2007");
                }
                if (container != null) {
                    String sampleName = "";
                    if (elem[2].trim().equalsIgnoreCase("A")) {
                        sampleName = elem[1].trim();
                    } else {
                        sampleName = elem[1].trim() + "-" + elem[2].trim();
                    }

                    Sample sample = addCNGSample(sampleName);

                    ContainerContent containerContent = userHttpSess.getContainerContent(container, "1", "1");
                    if (containerContent == null) {
                        userHttpSess.addContainerContent("-1", null, "1", container, "1", "1", sample, "-1", "-1", "-1", null, null, null);
                        containerContent = userHttpSess.getContainerContent(container, "1", "1");
                    }


                    //try{
                    userHttpSess.addGenotypingRunContainer(genotypingRun, container);
                    //catch()
                    Double callrate = null;
                    try {
                        callrate = new Double(elem[5]);
                    } catch (Exception e) {
                        System.err.println("Error parsing callrate for sample " + elem[0]);
                    }
                    userHttpSess.addGenotypingRunSampleStatus(genotypingRun, containerContent, "1", callrate);
                } else {
                    System.out.println("Scipted container - " + elem[0]);
                }
            }
        }
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
        //verifyContainers(new File("D:/Gabriel Samples/Sent Samples/GWAS Batch 1.txt"));

        //load sampleList

        //load shippedTo

        //load container contents
        verifyContainerContents(new File("D:/AllerGenGabrielGWAS/success_samp.txt"));
    }

    private void verifyContainerContents(File sampleReport) {
        ArrayList<String> fileContents = Tools.readFile(sampleReport);
        if (fileContents.size() > 0) {
            fileContents.remove(0); //remove header
            for (String line : fileContents) {
                String[] elem = line.split("\\s");
                ContainerContent cc = userHttpSess.getContainerContent(elem[0]);
                if (cc != null) {
                    Subject sub = cc.getSample().getSubject();
                    if (!elem[1].contains(sub.getFamilyID())) {
                        System.out.println("barcode sample missmatch - " + elem[0] + "\t" + elem[1] + "-" + elem[2] + "\t" + sub.getCohort().getDescription() + "_" + sub.getSubjectName());
                    }
                } else {
                    System.out.println("barcode not found - " + elem[0]);
                }
            }
        }

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
                userHttpSess.addShipment("-1", "CNGLoadedShipment", shippedDate, shippedTo, shippedout, null);
                userHttpSess.addContainer("-1", containerName, containerType, null, shelf, discarded, null, null, null, "1", null, null, null, null, null);
                userHttpSess.addShipCont("-1", userHttpSess.getCurrentShipment(), userHttpSess.getCurrentContainer());
            }
        }

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
        userHttpSess.addShipment("-1", "CNGLoadedShipment", shippedDate, shippedTo, shippedout, null);
        userHttpSess.addContainer("-1", containerName, containerType, null, shelf, discarded, null, null, null, "1", null, null, null, null, null);
        userHttpSess.addShipCont("-1", userHttpSess.getCurrentShipment(), userHttpSess.getCurrentContainer());
        return userHttpSess.getContainer(containerName);


    }

    private Sample addCNGSample(String sampleName) throws Exception {

        userHttpSess.openHibSession();
        Cohort cohort;

        if (sampleName.startsWith("8")) {
            cohort = (Cohort) userHttpSess.getObjectById(Cohort.class, "4");
        } else {
            cohort = (Cohort) userHttpSess.getObjectById(Cohort.class, "3");
        }
        String id = sampleName.substring(1, sampleName.length());
        Subject subject = userHttpSess.getSubject(cohort, id);
        SampleType sampleType = userHttpSess.getSampleType("");
        if (subject != null) {
            List<Sample> sampleList = userHttpSess.getAllSamples(subject.getSubjectID(), -1);

            if (sampleList.isEmpty()) {
                userHttpSess.addSample("", id, "1", null, sampleType, subject, null, null, null);
                return userHttpSess.getSample(subject, id);
            } else {
                return sampleList.get(0);
            }



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

    private static void addPhenotype(File file, Phenotypes phen) {
        ArrayList<String> fileContents = GenaphaTools.readFile(file);
        for (String line : fileContents) {
            String[] elem = line.trim().split("\\s");
            icapture.beans.DB.Subject subject;
            if (elem[0].startsWith("CAPPS_")) {
                subject = SQLMgr.getSubject(elem[1], 3);
            } else {
                subject = SQLMgr.getSubject(elem[1], 4);
            }

            PhenotypeValue pv = SQLMgr.getPhenotypeValue(subject, phen);
            String val = "-9";
            if (pv != null && pv.getValue() != null) {
                if (pv.getPhenotype().getType() == Phenotypes.BINARY) {
                    if (pv.getValue().equalsIgnoreCase("0")) {
                        val = "1";
                    } else if (pv.getValue().equalsIgnoreCase("1")) {
                        val = "2";
                    }
                } else {
                    val = pv.getValue();
                }
            }
            elem[5] = val;
            for (String s : elem) {
                System.out.print(s + " ");
            }
            System.out.println("");
        }

    }

    public static void main(String[] args) {
        ConnectionMgr.setConnectionType(ConnectionMgr.CONNECTION_TYPE_DIRECT);
        //UserHttpSess httpSess = new UserHttpSess(".");
        //CNGDataLoader cNGDataLoader = new CNGDataLoader(httpSess);
        //cNGDataLoader.verifyLoad();

        GenotypeToolsManager gtm;
        try {
            //gtm = new GenotypeToolsManager();
            //gtm.calculateIBS(new File("./fbat.ped"), new File("./plinkIBS.txt"));
            //makePlinkFiles();
            //createOverlapSubjects();
            //createOverlapSubjectList();
            //createNewSubjectIDs(new File("./plink/CNG_ALL_SUBJECTS_DUP_MERGE2.fam"));

            //processOverlapDiscepacies(new File("./plink/Overlap Discepacies.txt"));
            //lineCount(new File("./mcdonald.ped"));
            //makeMapFile();

            //compareSNPs();
            //makePlinkFiles();
            //createOverlapSubjects("./cng-sage-95%snps-97%samples.fam");
            //processOverlapDiscepacies(new File("./Overlap/Duplicate Merge Diff report.txt"));
            addPhenotype(new File("C:/Documents and Settings/btripp/Desktop/cng-dupMerge-95%snps-97%samples-dupsRemoved-caucasian.fam"), SQLMgr.getPhenotype(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
