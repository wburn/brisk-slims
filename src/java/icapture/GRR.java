
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package icapture;

//~--- JDK imports ------------------------------------------------------------

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 *
 * @author btripp
 */
public class GRR {
    public static void main(String[] args) {
        BufferedWriter out    = null;
        BufferedReader reader = null;
        String         line;
        String[]       elem;
        String         iid1;
        String         iid2;
        String         ret;
        long           ibs0;
        long           ibs1;
        long           ibs2;
        double         mean;
        double         stddev;
        int            linecount = 1;

        try {
            out    = new BufferedWriter(new FileWriter(new File(args[1])));
            reader = new BufferedReader(new FileReader(new File(args[0])));
            reader.readLine();    // throw away header
            out.write("iid1\tiid2\tret\tibs0\tibs1\tibs2\tmean\tstddev");
            out.newLine();

            while ((line = reader.readLine()) != null) {
                while (line.contains("  ")) {
                    line = line.replaceAll("  ", " ");
                }

                elem   = line.trim().split("\\s");
                iid1   = elem[0].split("_")[0] + "_" + elem[1];
                iid2   = elem[2].split("_")[0] + "_" + elem[3];
                ret    = elem[4];
                ibs0   = Long.parseLong(elem[14]);
                ibs1   = Long.parseLong(elem[15]);
                ibs2   = Long.parseLong(elem[16]);
                mean   = (double) (ibs1 + 2 * ibs2) / (double) (ibs0 + ibs1 + ibs2);
                stddev = Math.sqrt((double) ((ibs0 * (mean * mean)) + (ibs1 * ((1 - mean) * (1 - mean)))
                                             + (ibs2 * ((2 - mean) * (2 - mean)))) / (double) (ibs0 + ibs1 + ibs2));
                out.write(iid1 + "\t" + iid2 + "\t" + ret + "\t" + ibs0 + "\t" + ibs1 + "\t" + ibs2 + "\t" + mean
                          + "\t" + stddev);
                out.newLine();

                if (linecount % 1000000 == 0) {
                    System.err.println("processed " + linecount + " lines.");
                }

                linecount++;
            }

            out.close();
            reader.close();
        } catch (Exception e) {
            System.err.println("Linecount = " + linecount);
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {}
            }
        }
    }
}



