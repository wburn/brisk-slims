/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icapture;

import icapture.com.UserHttpSess;
import icapture.genapha.GenaphaTools;
import icapture.hibernate.Cohort;
import icapture.hibernate.Subject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author btripp
 */
public class AllergenGWASDataLoader {

    private static String prevRS;
    private static UserHttpSess userHttpSess;
    private static Cohort CAPPS;
    private static Cohort SAGE;

    private static void makePlinkFiles(String chromosome) throws Exception {
        File genFile = new File("./genotypes-chr" + chromosome + ".txt");

        BufferedWriter fam = new BufferedWriter(new FileWriter(new File("./plink-" + chromosome + ".fam")));
        BufferedWriter map = new BufferedWriter(new FileWriter(new File("./plink-" + chromosome + ".map")));
        BufferedWriter lgen = new BufferedWriter(new FileWriter(new File("./plink-" + chromosome + ".lgen")));

        userHttpSess = new UserHttpSess(".");

        CAPPS = userHttpSess.getCohort("3");
        SAGE = userHttpSess.getCohort("4");
        HashSet<String> snpSet = new HashSet<String>(10000);
        HashMap<String, Integer> subjectMap = new HashMap<String, Integer>();
        prevRS = "";

        BufferedReader reader = null;

        String line;
        String[] elem;
        String a1;
        String a2;
        String ID = null;
        Subject sub = null;
        long count = 1;
        long fileCount = 1;
        String prevBarcode = "";
        try {

            reader = new BufferedReader(new FileReader(genFile));
            reader.readLine(); //throw away header

            int linecount = 1;
            while ((line = reader.readLine()) != null) {
                elem = line.split("\t");

                addToMap(elem, map, snpSet);
                final String barcode = elem[3] + "#@#" + elem[5];
                if (!prevBarcode.equalsIgnoreCase(barcode)) {
                    //if the same subject has been done more than once
                    //number there other samples
                    prevBarcode = barcode;
                    sub = parseSubject(elem);
                    if (sub != null) {
                        ID = sub.getSubjectName();
                        Integer i = subjectMap.get(elem[8]);
                        if (i == null) {
                            i = 1;
                        }
                        if (i > 1) {
                            ID = ID + "_" + i.toString();
                        }
                        i++;
                        subjectMap.put(elem[8], i);

                        fam.write(sub.getCohort().getDescription() + "_" + sub.getFamilyID() + "\t" + ID + "\t" + sub.getFatherName() + "\t" + sub.getMotherName() + "\t" + sub.getGender() + "\t0");
                        fam.newLine();
                    } else {
                        ID = null;
                        System.out.println(elem[4] + "\t" + elem[8] + " not found.");
                    }
                }

                if (ID != null && sub != null) {
                    lgen.write(sub.getCohort().getDescription() + "_" + sub.getFamilyID() + "\t" + ID + "\t" + elem[0] + "\t" + elem[15] + "\t" + elem[16]);
                    lgen.newLine();
                }

                if (linecount % 1000000 == 0) {
                    System.err.println("processed " + linecount + " lines.");
                }
                linecount++;
            }

            fam.close();
            map.close();
            lgen.close();
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

    private static void outputSamples(String chromosome) throws Exception {
        File genFile = new File("./genotypes-chr" + chromosome + ".txt");


        BufferedReader reader = null;
        String line;
        String[] elem;

        try {
            HashSet<String> samples = new HashSet<String>();
            reader = new BufferedReader(new FileReader(genFile));
            reader.readLine(); //throw away header

            while ((line = reader.readLine()) != null) {

                elem = line.split("\t");
                samples.add(elem[4] + "\t" + elem[8] + "\t" + elem[3] + "\t" + elem[5]);

            }

            for (String s : samples) {
                System.out.println(s);
            }

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

    private static void outputMarkerStatus(String fileName) throws Exception {
        File genFile = new File(fileName);

        FileWriter passed = null;
        FileWriter failed = null;
        BufferedReader reader = null;
        String line;
        String[] elem;

        try {
            passed = new FileWriter("./passed.txt");
            failed = new FileWriter("./failed.txt");
            reader = new BufferedReader(new FileReader(genFile));
            reader.readLine(); //throw away header

            while ((line = reader.readLine()) != null) {

                elem = line.split(",");
                if (elem[0].equalsIgnoreCase("good")) {
                    passed.write(elem[2] + "\t" + elem[14] + "\n");
                } else {
                    failed.write(elem[2] + "\t" + elem[14] + "\n");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                }
            }
            if (passed != null) {
                try {
                    passed.close();
                } catch (Exception e) {
                }
            }
            if (failed != null) {
                try {
                    failed.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private static void outputPassedSNPs(String fileName) throws Exception {
        File genFile = new File(fileName);

        FileWriter passed = null;
        FileWriter failed = null;
        BufferedReader reader = null;
        String line;
        String[] elem;

        try {
            passed = new FileWriter("./passed.txt");
            failed = new FileWriter("./failed.txt");
            reader = new BufferedReader(new FileReader(genFile));

            while ((line = reader.readLine()) != null) {

                elem = line.split("\\s");
                if (elem[0].startsWith("rs")) {
                    passed.write(elem[0] + "\n");
                } else {
                    failed.write(elem[0] + "\n");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                }
            }
            if (passed != null) {
                try {
                    passed.close();
                } catch (Exception e) {
                }
            }
            if (failed != null) {
                try {
                    failed.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private static void outputPassedSamples(String fileName) throws Exception {
        File genFile = new File(fileName);

        FileWriter passed = null;
        FileWriter failed = null;
        BufferedReader reader = null;
        String line;
        String[] elem;

        try {
            passed = new FileWriter("./passed.txt");

            reader = new BufferedReader(new FileReader(genFile));


            while ((line = reader.readLine()) != null) {

                elem = line.split("\\s");
                if (elem[0].startsWith("SAGE")) {
                    String id = elem[0].substring(4, elem[0].length());
                    passed.write("SAGE_" + id.split("-")[0] + "\t" + id + "\n");
                } else {
                    String id = elem[0].substring(2, elem[0].length());
                    passed.write("CAPPS_" + id.split("-")[0] + "\t" + id + "\n");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                }
            }
            if (passed != null) {
                try {
                    passed.close();
                } catch (Exception e) {
                }
            }
            if (failed != null) {
                try {
                    failed.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private static void checkBimFile(String bimFile, String snpFile) throws Exception {
        File genFile = new File(bimFile);
        ArrayList<String> snpList = GenaphaTools.readFile(new File(snpFile));
        HashMap<String, Integer> snpMap = new HashMap<String, Integer>();
        FileWriter passed = null;
        FileWriter failed = null;
        BufferedReader reader = null;
        String line;
        String[] elem;

        try {


            reader = new BufferedReader(new FileReader(genFile));

            while ((line = reader.readLine()) != null) {

                snpMap.put(line.split("\\s")[1], 0);

            }

            System.out.println("SNP's in bim file = " + snpMap.keySet().size());
            System.out.println("SNP's in snp file = " + snpList.size());

            for (String rs : snpList) {
                Integer i = snpMap.get(rs);
                if (i == null) {
                    System.out.println("SNP missing from bim file = " + rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                }
            }
            if (passed != null) {
                try {
                    passed.close();
                } catch (Exception e) {
                }
            }
            if (failed != null) {
                try {
                    failed.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private static void countSNPS(String fileName) throws Exception {
        File genFile = new File(fileName);

        BufferedReader reader = null;
        String line;
        String[] elem;
        HashMap<String, Integer> sampleTypeMap = new HashMap<String, Integer>();
        try {

            reader = new BufferedReader(new FileReader(genFile));

            int count = 0;
            while ((line = reader.readLine()) != null) {

                elem = line.split("\t");
                String st = elem[1].substring(0, 2);
                Integer i = sampleTypeMap.get(st);
                if (i == null) {
                    i = 0;
                }
                i++;
                sampleTypeMap.put(st, i);

            }
            for (String s : sampleTypeMap.keySet()) {
                System.out.println(s + " - " + sampleTypeMap.get(s));
            }

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

    private static void countSNPSTypes(String locusReport) throws Exception {
        File genFile = new File(locusReport);

        BufferedReader reader = null;
        String line;
        String[] elem;
        HashMap<String, Integer> sampleTypeMap = new HashMap<String, Integer>();
        try {

            reader = new BufferedReader(new FileReader(genFile));

            int count = 0;
            while ((line = reader.readLine()) != null) {

                elem = line.split(",");
                String st = elem[2].substring(0, 2);

                if (st.equalsIgnoreCase("50")) {
                    System.out.println(elem[2]);
                }

                if (st.equalsIgnoreCase("40")) {
                    System.out.println(elem[2]);
                }

                if (st.equalsIgnoreCase("20")) {
                    System.out.println(elem[2]);
                }

                if (st.equalsIgnoreCase("hc")) {
                    System.out.println(elem[2]);
                }

                if (st.equalsIgnoreCase("pg")) {
                    System.out.println(elem[2]);
                }

                Integer i = sampleTypeMap.get(st);
                if (i == null) {
                    i = 0;
                }
                i++;
                sampleTypeMap.put(st, i);

            }
            for (String s : sampleTypeMap.keySet()) {
                System.out.println(s + " - " + sampleTypeMap.get(s));
            }

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
    private static HashSet<String> markerTypeSet = new HashSet<String>();

    private static void countSNPSFromGenotypeFiles() throws Exception {

        for (int i = 1; i < 23; i++) {
            countSNPSFromGenotypeFiles("./genotypes-chr" + i + ".txt");
        }
        countSNPSFromGenotypeFiles("./genotypes-chrX.txt");
        countSNPSFromGenotypeFiles("./genotypes-chrY.txt");
        countSNPSFromGenotypeFiles("./genotypes-chrXY.txt");

        for (String s : markerTypeSet) {
            System.out.println(s);
        }
    }

    private static void countSNPSFromGenotypeFiles(String fileName) throws Exception {
        File genFile = new File(fileName);

        BufferedReader reader = null;
        String line;

        try {

            reader = new BufferedReader(new FileReader(genFile));

            int count = 0;
            while ((line = reader.readLine()) != null) {
                markerTypeSet.add(line.substring(0, 2));
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                }
            }
            System.out.println("completed - " + fileName);
        }
    }

    private static void addToMap(String[] elem, BufferedWriter map, HashSet<String> snpSet) throws Exception {

        if (!elem[0].equalsIgnoreCase(prevRS)) {
            prevRS = elem[0];
            //writemap file
            if (!snpSet.contains(elem[0])) {
                snpSet.add(elem[0]);
                map.write(elem[10] + "\t" + elem[0] + "\t0\t" + elem[11]);
                map.newLine();
            }
        }

    }

    private static Subject parseSubject(String[] elem) throws Exception {
        String ID;
        Cohort cohort = null;

        //parse column 8 to get cohort,id
        ID = elem[8].trim();
        if (ID.startsWith("Az")) {
            cohort = CAPPS;
            ID = ID.substring(2);
            //alias = getSageAlias(id);
        } else if (ID.startsWith("SAGE")) {
            cohort = SAGE;
            ID = ID.substring(4);
            //alias = getCappsAlias(id);
        }

        Subject sub = userHttpSess.getSubject(cohort, ID);
        if (sub == null) {
            icapture.beans.DB.Subject s2 = SQLMgr.getSubject(ID, Integer.parseInt(cohort.getCohortID()));
            if (s2 != null) {
                sub = userHttpSess.getSubject(cohort, s2.getID());
            }
        }

        return sub;

    }

    public static void main(String[] args) {
        //ConnectionMgr.setConnectionType(ConnectionMgr.CONNECTION_TYPE_DIRECT);
        try {
//            int start = 1;
//            if (args.length == 1){
//                start = Integer.parseInt(args[0].trim());
//            }
//            
//            for(int i = start;i<23;i++){
//                makePlinkFiles(i +"");
//            }
//            makePlinkFiles("X");
//            makePlinkFiles("Y");
//            makePlinkFiles("XY");

            //makePlinkFiles("bad");
            //countSNPS("./GQ_Genomic.bim");
            //countSNPSFromGenotypeFiles();
            //outputMarkerStatus("./Locus_Report_AllergenGWAS_DD_P02.csv");
            //outputMarkerStatus("./Locus_report_AllergenGWAS_WGA_DD_P02.csv");
            //outputPassedSNPs("./wga-passed-locus.txt");
            //outputPassedSNPs("./genomic-passed-locus.txt");
            //countSNPSTypes("./Locus_Report_AllergenGWAS_DD_P02.csv");
            //outputSamples("1");


            //outputMarkerStatus("./Locus_Report_AllergenGWAS_DD_P02.csv");
            //outputMarkerStatus("./Locus_report_AllergenGWAS_WGA_DD_P02.csv");
            outputPassedSNPs("./wga-passed-locus.txt");
            //outputPassedSNPs("./genomic-passed-locus.txt");

            //outputMarkerStatus("./Sample_report_AllergenGWAS_DD_P02.csv");
            //outputPassedSamples("./genomic-samples-passed.txt");

            //outputMarkerStatus("./Sample_report_AllergenGWAS_WGA_DD_P02.csv");
            //outputPassedSamples("./wga-samples-passed.txt");

            //checkBimFile("./gc_genomic_492samples.bim","./genomic-markers-passed-snps.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
