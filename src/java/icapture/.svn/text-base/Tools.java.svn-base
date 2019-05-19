
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package icapture;

//~--- JDK imports ------------------------------------------------------------

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.ArrayList;

/**
 *
 * @author btripp
 */
public class Tools {
    public static ArrayList<String> readFile(File file) {
        ArrayList<String> list   = new ArrayList<String>();
        BufferedReader    reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));

            String line;

            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {}
            }
        }

        return list;
    }
}



