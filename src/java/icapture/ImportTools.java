
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package icapture;

//~--- non-JDK imports --------------------------------------------------------

import icapture.ConnectionMgr;
import icapture.Tools;

import icapture.com.UserHttpSess;

import icapture.hibernate.ContainerContent;
import icapture.hibernate.GenotypingRun;
import icapture.hibernate.Snp;

//~--- JDK imports ------------------------------------------------------------

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author btripp
 *
 */
public class ImportTools {
    private static void loadGenotypes() throws Exception {
        File              genFile    = new File("D:/AllerGenGabrielGWAS//MCDONALD.ped");
        File              sampleFile = new File("D:/AllerGenGabrielGWAS/success_samp.txt");
        PreparedStatement pst        = null;
        Connection        con        = null;

        con = ConnectionMgr.getConnection();
        con.setAutoCommit(false);

        // insert Variable Value
        pst = con.prepareStatement(
            "insert into tblgenotypes (containercontentsid, snpid, genotypingrunid, allele1, allele2) values (?, ?, ?,?,?)");

        UserHttpSess   userHttpSess = new UserHttpSess(".");
        BufferedReader reader       = null;
        int[]          snpArray;
        String         line;
        String[]       elem;
        String         a1;
        String         a2;
        long           count = 1;

        try {
            GenotypingRun           genotypingRun = userHttpSess.getGenotypingRun("CNG (GABRIEL)");
            HashMap<String, String> barcodeMap    = new HashMap<String, String>();
            ArrayList<String>       fileContents  = Tools.readFile(sampleFile);

            fileContents.remove(0);    // remove header

            for (String s : fileContents) {
                elem = s.split("\\s");
                barcodeMap.put(elem[1].trim() + "-" + elem[2].trim(), elem[0].trim());
            }

            reader = new BufferedReader(new FileReader(genFile));
            line   = reader.readLine();

            HashMap<String, Snp> snpMap = new HashMap<String, Snp>();
            List<Snp>            snps   = userHttpSess.getAllSnps(-1);

            for (Snp snp : snps) {
                snpMap.put(snp.getRsNumber(), snp);
            }

            // parse header
            elem     = line.split("\\s");
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

            pst.setInt(3, Integer.parseInt(genotypingRun.getGenotypingRunID()));
            start = System.currentTimeMillis();

            int linecount = 1;

            while ((line = reader.readLine()) != null) {
                String famID;
                String ID;

                elem  = line.split("\\s");
                famID = elem[0].trim().split(":")[1];
                ID    = elem[1].trim();

                ContainerContent containerContent = userHttpSess.getContainerContent(barcodeMap.get(famID + "-" + ID));

                pst.setInt(1, Integer.parseInt(containerContent.getContainerContentsID()));

                for (int i = 0; i < snpArray.length; i++) {
                    if (snpArray[i] >= 0) {
                        a1 = elem[(2 * i) + 6].trim();
                        a2 = elem[(2 * i) + 7].trim();

                        try {
                            pst.setInt(2, snpArray[i]);
                            pst.setString(4, a1);
                            pst.setString(5, a2);
                            pst.addBatch();

                            if (count % 500 == 0) {
                                int[] updateCounts = pst.executeBatch();

                                con.commit();
                            }

                            if (count % 1000 == 0) {
                                end = System.currentTimeMillis();
                                System.out.println("Processing time: " + ((end - start) / 1000) + " sec. ");
                                System.out.println("inserted " + count);

                                // System.err.println("inserted " + count);
                            }
                        } catch (BatchUpdateException buex) {
                            System.err.println(" Message: " + buex.getMessage());
                            System.err.println(" SQLSTATE: " + buex.getSQLState());
                            System.err.println(" Error code: " + buex.getErrorCode());

                            SQLException ex = buex.getNextException();

                            while (ex != null) {
                                System.err.println("SQL exception:");
                                System.err.println(" Message: " + ex.getMessage());
                                System.err.println(" SQLSTATE: " + ex.getSQLState());
                                System.err.println(" Error code: " + ex.getErrorCode());
                                ex = ex.getNextException();
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

            pst.executeBatch();
            con.commit();
            con.setAutoCommit(true);
            userHttpSess.closeHibSession();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {}
            }
        }
    }

    private static void makeGenotypeImportFiles() throws Exception {
        File           genFile      = new File("./MCDONALD.ped");
        File           sampleFile   = new File("./success_samp.txt");
        BufferedWriter br           = new BufferedWriter(new FileWriter(new File("./tblgenotypes.txt")));
        UserHttpSess   userHttpSess = new UserHttpSess(".");
        BufferedReader reader       = null;
        int[]          snpArray;
        String         line;
        String[]       elem;
        String         a1;
        String         a2;
        long           count     = 1;
        long           fileCount = 1;

        try {
            GenotypingRun           genotypingRun = userHttpSess.getGenotypingRun("CNG (GABRIEL)");
            HashMap<String, String> barcodeMap    = new HashMap<String, String>();
            ArrayList<String>       fileContents  = Tools.readFile(sampleFile);

            fileContents.remove(0);    // remove header

            for (String s : fileContents) {
                elem = s.split("\\s");
                barcodeMap.put(elem[1].trim() + "-" + elem[2].trim(), elem[0].trim());
            }

            reader = new BufferedReader(new FileReader(genFile));
            line   = reader.readLine();

            HashMap<String, Snp> snpMap = new HashMap<String, Snp>();
            List<Snp>            snps   = userHttpSess.getAllSnps(-1);

            for (Snp snp : snps) {
                snpMap.put(snp.getRsNumber(), snp);
            }

            // parse header
            elem     = line.split("\\s");
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

                elem  = line.split("\\s");
                famID = elem[0].trim().split(":")[1];
                ID    = elem[1].trim();

                ContainerContent containerContent = userHttpSess.getContainerContent(barcodeMap.get(famID + "-" + ID));

                for (int i = 0; i < snpArray.length; i++) {
                    if (snpArray[i] >= 0) {
                        a1 = elem[(2 * i) + 6].trim();
                        a2 = elem[(2 * i) + 7].trim();

                        try {
                            String linew = containerContent.getContainerContentsID() + ","
                                           + genotypingRun.getGenotypingRunID() + "," + snpArray[i] + ",\"" + a1
                                           + "\",\"" + a2 + "\"";

                            br.write(linew);
                            br.newLine();

                            if (count % 100000 == 0) {
                                end = System.currentTimeMillis();
                                System.out.println("Processing time: " + ((end - start) / 1000) + " sec. ");
                                System.out.println("inserted " + count);

                                // System.err.println("inserted " + count);
                            }

                            if (count % 100000000 == 0) {
                                br.close();
                                br = new BufferedWriter(new FileWriter(new File("./tblgenotypes-" + fileCount
                                        + ".txt")));
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
                } catch (Exception e) {}
            }
        }
    }

    public static void main(String[] args) {
        ConnectionMgr.setConnectionType(ConnectionMgr.CONNECTION_TYPE_DIRECT);

        // makeGeneAndAllSNPsPublic("PLAU");
        // makeGeneAndAllSNPsPublic("ALOX15");
        // makeGeneAndAllSNPsPublic("CD27");
        // makeGeneAndAllSNPsPublic("IL2RB");
        // redoAssociationSet();
//      ArrayList<String> geneList = GenaphaTools.readFile(new File(args[0]));
//      for(String geneName : geneList){
//          makeGeneAndAllSNPsPublic(geneName);
//      }
        // makeGeneAndAllSNPsPublic("CD27");
        // makeGeneAndAllSNPsPublic("IL2RB");
//      Gene gene = SQLMgr.getGene("HAVCR2");
//      if(gene==null){
//          System.out.println("Gene not found");
//      }else{
//          System.out.println("Gene found: " + gene.getGeneID());
//      }
        // updateFreqPvalues("HAVCR2");
        // associationResults("HAVCR2");
        // snpStatus("HAVCR2");
        // updateSNPs("HAVCR2");
        // makeGeneAndAllSNPsPublic("HAVCR2");
        // makeGeneAndAllSNPsPublic("TSLP");
        // microarray paper genes
        // makeGeneAndAllSNPsPublic("ALOX15");
        // makeGeneAndAllSNPsPublic("CD27");
        // makeGeneAndAllSNPsPublic("IL2RB");
        // importStructureData(new File("D:/snps129_DNA_structure_change.txt/snps129_DNA_structure_change.txt"));
        // moveEthnicityFrequency(new File("./xchromSNPs.txt"));
        // makeGeneAndAllSNPsPublic("CX3CR1");
        try {
            makeGenotypeImportFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



