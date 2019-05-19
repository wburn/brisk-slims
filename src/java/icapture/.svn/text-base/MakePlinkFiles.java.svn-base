
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package icapture;

//~--- non-JDK imports --------------------------------------------------------

import icapture.ConnectionMgr;
import icapture.SQLMgr;

import icapture.beans.DB.PhenotypeValue;
import icapture.beans.DB.Phenotypes;
import icapture.beans.DB.SNP;
import icapture.beans.DB.Sample;

//~--- JDK imports ------------------------------------------------------------

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author btripp
 */
public class MakePlinkFiles {
    public static File genFile = new File("./MCDONALD.ped");

    public static void makePlinkFiles() throws Exception {
        BufferedWriter ped    = new BufferedWriter(new FileWriter(new File("./plink.ped")));
        BufferedWriter map    = new BufferedWriter(new FileWriter(new File("./plink.map")));
        BufferedReader reader = null;
        SNP[]          snpArray;
        String         line;
        String[]       elem;
        String         a1;
        String         a2;
        long           count     = 1;
        long           fileCount = 1;

        try {
            reader = new BufferedReader(new FileReader(genFile));
            line   = reader.readLine();

            HashMap<String, SNP> snpMap = new HashMap<String, SNP>();
            List<SNP>            snps   = SQLMgr.getSNPs();

            for (SNP snp : snps) {
                snpMap.put(snp.getRsNumber(), snp);
            }

            // parse header
            elem     = line.split("\\s");
            snpArray = new SNP[elem.length];

            for (int i = 0; i < snpArray.length; i++) {
                snpArray[i] = snpMap.get(elem[i].trim());

                // writemap file
                if (snpArray[i] != null) {
                    map.write(snpArray[i].getChromosome() + "\t" + snpArray[i].getRsNumber() + "\t0\t"
                              + snpArray[i].getPosition());
                    map.newLine();
                } else {
                    System.out.println("SNP not found -" + elem[i].trim());
                }
            }

            map.close();

            long start;
            long end;

            start = System.currentTimeMillis();

            int linecount = 1;

            while ((line = reader.readLine()) != null) {
                elem = line.split("\\s");

                Phenotypes phen   = SQLMgr.getPhenotype("NHL");
                Sample     sample = SQLMgr.getSample(elem[1].trim(), 0);

                elem[0] = sample.getSubject().getFamilyID();
                elem[1] = sample.getSubject().getID();

                PhenotypeValue pv = SQLMgr.getPhenotypeValue(sample.getSubject(), phen);

                if (pv == null) {
                    elem[5] = "0";
                } else {
                    if (pv.getValue().equalsIgnoreCase("0")) {
                        elem[5] = "1";
                    } else {
                        if (pv.getValue().equalsIgnoreCase("1")) {
                            elem[5] = "2";
                        }
                    }
                }

                for (String s : elem) {
                    ped.write(s + " ");
                }

                ped.newLine();

                if (linecount % 10 == 0) {
                    System.err.println("processed " + linecount + " lines.");
                }

                linecount++;
            }

            ped.close();
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

    public static void makePlinkFilesSamples() throws Exception {
        BufferedWriter ped    = new BufferedWriter(new FileWriter(new File("./plink.ped")));
        BufferedWriter map    = new BufferedWriter(new FileWriter(new File("./plink.map")));
        BufferedReader reader = null;
        SNP[]          snpArray;
        String         line;
        String[]       elem;
        String         a1;
        String         a2;
        long           count     = 1;
        long           fileCount = 1;

        try {
            reader = new BufferedReader(new FileReader(genFile));
            line   = reader.readLine();

            HashMap<String, SNP> snpMap = new HashMap<String, SNP>();
            List<SNP>            snps   = SQLMgr.getSNPs();

            for (SNP snp : snps) {
                snpMap.put(snp.getRsNumber(), snp);
            }

            // parse header
            elem     = line.split("\\s");
            snpArray = new SNP[elem.length];

            for (int i = 0; i < snpArray.length; i++) {
                snpArray[i] = snpMap.get(elem[i].trim());

                // writemap file
                if (snpArray[i] != null) {
                    map.write(snpArray[i].getChromosome() + "\t" + snpArray[i].getRsNumber() + "\t0\t"
                              + snpArray[i].getPosition());
                    map.newLine();
                } else {
                    System.out.println("SNP not found -" + elem[i].trim());
                }
            }

            map.close();

            long start;
            long end;

            start = System.currentTimeMillis();

            int linecount = 1;

            while ((line = reader.readLine()) != null) {
                ped.write(line);
                ped.newLine();

                if (linecount % 10 == 0) {
                    System.err.println("processed " + linecount + " lines.");
                }

                linecount++;
            }

            ped.close();
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

        try {
            genFile = new File("./plink/SAGE_Atopy.ped");
            makePlinkFilesSamples();
            (new File("./plink.ped")).renameTo(new File("./plink/SAGE_Atopy-plink.ped"));
            (new File("./plink.map")).renameTo(new File("./plink/SAGE_Atopy-plink.map"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



