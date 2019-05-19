/**
 * Keeps track of plating operations: what samples need to be plated, what
 * samples have been plated, order of samples based on phenotypes, what controls
 * need to be plated, what controls have been plated, how much space there is on
 * a plate after adding controls etc.
 * This is used for make 96 well plates from a list of contents as well as making
 * 384 well plates from a list of 96 well plates.

 * @author tvanrossum
 */
package icapture.com;

import icapture.hibernate.*;

import org.hibernate.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Date;
import java.util.Iterator;

/**
 *
 * @author tvanrossum
 */
public class Plater {

    private UserHttpSess session;
    private ArrayList<String> workingSubjectList = null;
    private ArrayList<String> workingContentList = null;
    private ArrayList<String> cases = null;
    private ArrayList<String> controls = null;
    private Double percentCases = null;
    private String orderToPlate = ""; // this should be by row or by column
    private int numCols = 0;
    private int numRows = 0;
    public Boolean consideringCaseControl = null;
    private MaterialType materialType = null;
    public int plated = 0; // how many contents in the list have been plated
    public int numberOfPlatesMade = 0; //how many plates have been made so far
    public ArrayList platesMade; //how many plates have been made so far
    public int controlsJustAdded = 0; //how many controls were added to most recent plate
    public String controlLayoutJustUsed; // name of control layout used for the most recent plate

    public Plater(UserHttpSess tmpHttpSessObj) {
        session = tmpHttpSessObj;
        workingSubjectList = new ArrayList<String>(session.getCurrentShoppingCartList().getSubjectList());
        workingContentList = new ArrayList<String>(session.getCurrentShoppingCartList().getContainerContentsList());
        cases = new ArrayList<String>();
        controls = new ArrayList<String>();
        percentCases = new Double("0.0");
        consideringCaseControl = null; // if set to true or false, doesn't show user option control
        platesMade = new ArrayList<String>();
        numCols = 12;
        numRows = 8;
    }

    /**
     * splits the working content based on their subjects' statuses as cases and controls
     * case or control is based on the phenotypeIDs passed in
     * @param phenotypeIDs if a subject is affected by any of these of phenotypeIDs, they are a 'case'
     */
    public void prepareCasesAndControls(String[] phenotypeIDs) {
        // for each subject
        Iterator iter = workingSubjectList.listIterator();
        System.out.println("workingSubjectList:" + workingSubjectList);
        System.out.println("workingContentList:" + workingContentList);
        session.openHibSession();

        String where = "(";
        for (int i = 0; i < phenotypeIDs.length; i++) {
            where = where + " phenotypeID = " + phenotypeIDs[i] + " ";
            if (i + 1 < phenotypeIDs.length) {
                where = where + " or ";
            }
        }
        where = where + " )";
        Query q;

        while (iter.hasNext()) {
            String subjectID = iter.next().toString();
            // find out its phenotype status for each phenotype
            q = session.hibSess.createQuery("select value from SubjectPhenotype where " + where + " and subjectID = " + subjectID);
            ArrayList statuses = (ArrayList) q.list();
            // if any are = 1, it's a case
            boolean isCase = false;
            if (statuses != null) {
                Iterator iterVal = statuses.iterator();
                while (iterVal.hasNext()) {
                    String status = iterVal.next().toString();
                    if (status.equals("1")) {
                        isCase = true;
                    }
                }
            }

            ArrayList contents = null;
            Iterator iterCC = null;
            if (isCase) {
                // get contents (IDs) that belong to this subject and if they're in the content list, store them as cases
                q = session.hibSess.createQuery("select containerContentsID from ContainerContent where sample.subject.subjectID = " + subjectID);
                contents = (ArrayList) q.list();
                iterCC = contents.iterator();
                while (iterCC.hasNext()) {
                    String ccID = iterCC.next().toString();
                    if (workingContentList.contains(ccID)) {
                        cases.add(ccID);
                        workingContentList.remove(ccID);
                    }
                }
            } // otherwise it's a control
            else {
                // get contents (IDs) that belong to this subject and if they're in the content list, store them as controls
                q = session.hibSess.createQuery("select containerContentsID from ContainerContent where sample.subject.subjectID = " + subjectID);
                contents = (ArrayList) q.list();
                iterCC = contents.iterator();
                while (iterCC.hasNext()) {
                    String ccID = iterCC.next().toString();
                    if (workingContentList.contains(ccID)) {
                        controls.add(ccID);
                        workingContentList.remove(ccID);
                    }
                }
            }
        }

        System.out.println("workingSubjectList:" + workingSubjectList);
        System.out.println("workingContentList:" + workingContentList);
        System.out.println("CASES:" + cases);
        System.out.println("CONTROLS:" + controls);

        shuffleCasesAndControls();
        percentCases = new Double(cases.size()) / (controls.size() + cases.size());
    }

    /**
     * randomize the orders in cases and controls lists
     */
    private void shuffleCasesAndControls() {
        java.util.Collections.shuffle(cases, new java.util.Random());
        java.util.Collections.shuffle(controls, new java.util.Random());
    }

    /**
     * get the right proportion of cases and controls, in random order
     * remove the subjects returned from the working case and control lists
     * If want case/control considerations, prepareCasesAndControls(String[] phenotypeIDs) must have been run
     * as long as this method has not been run, cases/controls will not be considered
     * @param maNumberOfWells max number of contents to return
     * @return list of content IDs to add to plate, in random order
     */
    public ArrayList<String> getPlatingList(int maxNumberOfWells) {
        int casesAdded = 0;
        int controlsAdded = 0;

        //If no case/control considerations required
        if (Double.compare(percentCases, new Double(0.0)) == 0) {
            // if number of contents left is < maxNumberOfWells, then number of contents left is limiter
            int numContentsToAdd = java.lang.Math.min(maxNumberOfWells, workingContentList.size());

            java.util.Collections.shuffle(workingContentList, new java.util.Random());
            ArrayList toAdd = new ArrayList(numContentsToAdd);
            // get subjects and remove them from working list
            if (workingContentList.size() == numContentsToAdd) {
                toAdd.addAll(workingContentList);
                workingContentList.clear();
            } else {
                for (int i = 0; i < numContentsToAdd; i++) {
                    toAdd.add(workingContentList.get(0));
                    workingContentList.remove(0);
                }
            }
            return toAdd;
        }

        // if number of contents left is < maxNumberOfWells, then number of contents left is limiter
        // and we can just take all contents left
        int numContentsToAdd = java.lang.Math.min(maxNumberOfWells, cases.size() + controls.size());
        ArrayList toAdd = new ArrayList(maxNumberOfWells);
        if (numContentsToAdd == cases.size() + controls.size()) {
            // add all cases
            casesAdded = cases.size();
            toAdd.addAll(cases);
            cases.clear();
            // add all controls
            controlsAdded = controls.size();
            toAdd.addAll(controls);
            controls.clear();
        } else {
            // get number of wells that shound be cases
            int numCases = ((Double) Util.round(numContentsToAdd * percentCases, 0)).intValue();
            // get number of wells that shound be controls
            int numControls = ((Double) Util.round(numContentsToAdd * (1 - percentCases), 0)).intValue();

            // make sure case & control lists are randomized
            shuffleCasesAndControls();

            // get cases and remove them from case list
            if (cases.size() > 0) {
                if (cases.size() == numCases) {
                    casesAdded = cases.size();
                    toAdd.addAll(cases);
                    cases.clear();
                } else {
                    for (int i = 0; i < numCases; i++) {
                        if (!cases.isEmpty()) {
                            casesAdded++;
                            toAdd.add(cases.get(0));
                            cases.remove(0);
                        }
                    }
                }
            }
            // get controls and remove them from control list
            if (controls.size() > 0) {
                if (controls.size() == numControls) {
                    controlsAdded = controls.size();
                    toAdd.addAll(controls);
                    controls.clear();
                } else {
                    for (int i = 0; i < numControls; i++) {
                        if (!controls.isEmpty()) {
                            controlsAdded++;
                            toAdd.add(controls.get(0));
                            controls.remove(0);
                        }
                    }
                }
            }
        }
        System.out.println("getPlatingList(), adding " + casesAdded + " cases and " + controlsAdded + " controls");

        java.util.Collections.shuffle(toAdd, new java.util.Random());

        return toAdd;
    }

    public String getControlLayoutJustUsed() {
        return controlLayoutJustUsed;
    }

    public void setControlLayoutJustUsed(String input) {
        controlLayoutJustUsed = input;
    }

    public String getOrderToPlate() {
        return orderToPlate;
    }

    public void setOrderToPlate(String input) {
        orderToPlate = input;
    }

    /**
     * returns a hash describing a control layout
     * each key is "rowXcolY" where X = the row number and Y = column number
     * each value is the control object at the key's described location or "none" if the control=null
     * @param layoutName the name of the control layout to get the hash for
     * @return hash representing
     */
    public HashMap getControlWellHash(String layoutName) {

        ArrayList<ControlLayoutWell> wells = new ArrayList();

        HashMap keys = new HashMap(1);
        keys.put("layoutName", "'" + layoutName + "'");
        wells = (ArrayList) session.getObjectsOfClass(ControlLayoutWell.class, keys);

        // get control locations and default controls
        Iterator iter = wells.iterator();
        ControlLayoutWell ctrlLay = null;
        HashMap wellHash = new HashMap();
        while (iter.hasNext()) {
            ctrlLay = (ControlLayoutWell) iter.next();
            //ctrlLay.getControl() may be null
            wellHash.put("row" + ctrlLay.getRow() + "col" + ctrlLay.getColumn(), (ctrlLay.getControl() == null) ? "none" : ctrlLay.getControl());
            System.out.println(wellHash.toString());
        }

        return wellHash;

    }

    /**
     * returns a list of locations describing a plate layout of samples
     * layout considers controls and is filled 'by column'
     * each entry is "rowXcolY" where X = the row number and Y = column number
     * @param layoutName the name of the control layout to get the hash for
     * @return arraylist representing filled sample wells
     */
    public ArrayList getPlateFillByColumn(String layoutName, int numSamples) {

        // ---- get control locations by col ------
        HashMap ctrlHash = getControlWellHash(layoutName);

        // generate hash for samples
        ArrayList samp = new ArrayList();
        // for every column
        for (int i = 1; i <= numCols && numSamples > 0; i++) {
            // for every row
            for (int j = 1; j <= numRows && numSamples > 0; j++) {
                // if not a control, out a sample
                if (!ctrlHash.containsKey("row" + j + "col" + i)) {
                    samp.add("row" + j + "col" + i);
                    numSamples--;
                }
            }
        }
        return samp;
    }

    /**
     * get a select box sized to the number of options for phenotypes of type 1
     * @return html for the selector
     * @throws org.hibernate.HibernateException
     */
    public String getSelectorPrompterForPhenotype() throws
            Exception {
        StringBuffer xyz = new StringBuffer();

        Query q;
        session.openHibSession();
        q = session.hibSess.createQuery("from Phenotype where type=1");
        ArrayList phenotypes = (ArrayList) q.list();

        if (phenotypes != null) {
            xyz.append("<select disabled name='");
            xyz.append("phenotype");
            xyz.append("' id='");
            xyz.append("phenotype");
            xyz.append("In' multiple size='");
            xyz.append(phenotypes.size());
            xyz.append("'>");
            Iterator iter = phenotypes.iterator();
            while (iter.hasNext()) {
                Phenotype phenotype = (Phenotype) iter.next();
                xyz.append("<option");
                xyz.append(" value='");
                xyz.append(phenotype.getPhenotypeID()).append("'>");
                xyz.append(phenotype.getName());
            }
        }
        xyz.append("</select>");
        return xyz.toString();
    }

    /**
     * check if database has phenotype information
     * @return true if db has phenotype info, false otherwise
     * @throws org.hibernate.HibernateException
     */
    public boolean hasPhenotypeData() throws
            Exception {
        StringBuffer xyz = new StringBuffer();

        Query q;
        session.openHibSession();
        q = session.hibSess.createQuery("from Phenotype where type=1");
        ArrayList phenotypes = (ArrayList) q.list();
        return (q.list() != null && q.list().size() > 0) ? true : false;

    }

    public String getPlateHtml(Container container) {

        if (container == null) {
            return null;
        }
        //---------CONTROLS START--------------//
        // get wells with controls in them
        // get all control wells in container
        ArrayList controlWells = new ArrayList();
        HashMap wellToCtrl = new HashMap();
        HashMap wellToSample = new HashMap();
        HashMap matches = new HashMap();
        matches.put("container.containerID", container.getContainerID());
        controlWells = (ArrayList) session.getObjectsOfClass(ControlWell.class, matches);

        // get their rows and cols
        Iterator iter = controlWells.iterator();
        ControlWell cw = null;
        String row = null;
        String col = null;

        // store controls by their location
        while (iter.hasNext()) {
            cw = (ControlWell) iter.next();
            row = cw.getRow();
            col = cw.getColumn();
            wellToCtrl.put("row" + row + "col" + col, cw.getControl().getDescription());
        }
        //---------CONTROLS END--------------//

        //---------SAMPLES START--------------//
        // get wells with samples in them
        // get all content wells in container
        ArrayList sampleWells = new ArrayList();
        matches = new HashMap();
        matches.put("container.containerID", container.getContainerID());
        sampleWells = (ArrayList) session.getObjectsOfClass(ContainerContent.class, matches);

        // get their rows and cols
        iter = sampleWells.iterator();
        ContainerContent cc = null;
        row = null;
        col = null;

        // store sample names by their location
        while (iter.hasNext()) {
            cc = (ContainerContent) iter.next();
            row = cc.getRow();
            col = cc.getColumn();
            wellToSample.put("row" + row + "col" + col,
                    "<a href=\"ViewContainerContents.jsp?ccId=" + cc.getContainerContentsID()
                    + "\">" + cc.getSample().getSampleName() + "</a><br><a style=\"font-size:9px\">"
                    + cc.getVolume() + " uL</a>");
        }
        //---------SAMPLES END--------------//

        int rows = new Integer(container.getContainerType().getRows());
        int columns = new Integer(container.getContainerType().getColumns());
        System.out.println("before coor, rows " + rows + ", cols " + columns);

        StringBuffer str = new StringBuffer();
        int r = 1;
        int c = 1;

        // column headers
        str.append("<tr>");
        str.append("<td style=\"width=\"40px\">&nbsp;</td>");
        for (c = 1; c <= columns; c++) {
            str.append("<td >" + c + "</td>");
        }
        str.append("</tr>");

        for (r = 1; r <= rows; r++) {
            str.append("<tr>");
            for (c = 1; c <= columns; c++) {
                // row headers
                if (c == 1) {
                    str.append("<td style=\"width=\"40px\">" + Util.numToLetter(String.valueOf(r)) + "</td>");
                }
                if (wellToSample.containsKey("row" + r + "col" + c)) {
                    str.append("<td style=\"background-color:#B4CFEA\">" + wellToSample.get("row" + r + "col" + c) + "</td>");
                } else if (wellToCtrl.containsKey("row" + r + "col" + c)) {
                    str.append("<td style=\"background-color:#B9E0BA\">" + wellToCtrl.get("row" + r + "col" + c) + "</td>");
                } else {
                    str.append("<td style=\"background-color:white\">&nbsp;</td>");
                }
            }
            str.append("</tr>");
        }

        return str.toString();
    }

    public static String getPlateHtml(Container container, UserHttpSess tmpSession) {

        if (container == null) {
            return null;
        }
        //---------CONTROLS START--------------//
        // get wells with controls in them
        // get all control wells in container
        ArrayList controlWells = new ArrayList();
        HashMap wellToCtrl = new HashMap();
        HashMap wellToSample = new HashMap();
        HashMap matches = new HashMap();
        matches.put("container.containerID", container.getContainerID());
        controlWells = (ArrayList) tmpSession.getObjectsOfClass(ControlWell.class, matches);

        // get their rows and cols
        Iterator iter = controlWells.iterator();
        ControlWell cw = null;
        String row = null;
        String col = null;

        // store controls by their location
        while (iter.hasNext()) {
            cw = (ControlWell) iter.next();
            row = cw.getRow();
            col = cw.getColumn();
            wellToCtrl.put("row" + row + "col" + col, cw.getControl().getDescription());
        }
        //---------CONTROLS END--------------//

        //---------SAMPLES START--------------//
        // get wells with samples in them
        // get all content wells in container
        ArrayList sampleWells = new ArrayList();
        matches = new HashMap();
        matches.put("container.containerID", container.getContainerID());
        sampleWells = (ArrayList) tmpSession.getObjectsOfClass(ContainerContent.class, matches);

        // get their rows and cols
        iter = sampleWells.iterator();
        ContainerContent cc = null;
        row = null;
        col = null;

        // store sample names by their location
        while (iter.hasNext()) {
            cc = (ContainerContent) iter.next();
            row = cc.getRow();
            col = cc.getColumn();
            wellToSample.put("row" + row + "col" + col,
                    "<a href=\"javascript:;\" onclick=\"opener.location='ViewContainerContents.jsp?ccId=" + cc.getContainerContentsID() + "';opener.focus()\""
                    + ">" + cc.getSample().getSampleName() + "</a><br><a style=\"font-size:9px\">"
                    + cc.getVolume() + " uL</a>");
            /*//To close the pop-up when redireting the main window
            wellToSample.put("row"+row+"col"+col,
            "<a href=\"javascript:;\" onclick=\"opener.location='ViewContainerContents.jsp?ccId="+cc.getContainerContentsID()+"';self.close()\""
            +">"+cc.getSample().getSampleName()+"</a><br><a style=\"font-size:9px\">"
            +cc.getVolume()+" uL</a>");
             */
        }
        //---------SAMPLES END--------------//

        int rows = new Integer(container.getContainerType().getRows());
        int columns = new Integer(container.getContainerType().getColumns());
        System.out.println("before coor, rows " + rows + ", cols " + columns);

        StringBuffer str = new StringBuffer();
        int r = 1;
        int c = 1;

        // column headers
        str.append("<tr>");
        str.append("<td style=\"width=\"40px\">&nbsp;</td>");
        for (c = 1; c <= columns; c++) {
            str.append("<td >" + c + "</td>");
        }
        str.append("</tr>");

        for (r = 1; r <= rows; r++) {
            str.append("<tr>");
            for (c = 1; c <= columns; c++) {
                // row headers
                if (c == 1) {
                    str.append("<td style=\"width=\"40px\">" + Util.numToLetter(String.valueOf(r)) + "</td>");
                }
                if (wellToSample.containsKey("row" + r + "col" + c)) {
                    str.append("<td style=\"background-color:#B4CFEA\">" + wellToSample.get("row" + r + "col" + c) + "</td>");
                } else if (wellToCtrl.containsKey("row" + r + "col" + c)) {
                    str.append("<td style=\"background-color:#B9E0BA\">" + wellToCtrl.get("row" + r + "col" + c) + "</td>");
                } else {
                    str.append("<td style=\"background-color:white\">&nbsp;</td>");
                }
            }
            str.append("</tr>");
        }

        return str.toString();
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType input) {
        materialType = input;
    }
}
