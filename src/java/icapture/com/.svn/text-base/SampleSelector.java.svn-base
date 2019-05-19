/**
 * Manages and tracks the decision tree structure being made by the user.
 * Prepares and runs queries to fetch the 'best-match' or 'next-best-match' contents.
 * Manages accepting and rejecting of contents and storage of those accepted.
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

public class SampleSelector {

    private UserHttpSess session;
    /*** --------------- fields for defining profiles ------------------- ***/
    private LinkedHashMap allCriteria = new LinkedHashMap();
    private LinkedHashMap[] levelAvailCriteria = null; // the criteria avaialable for each level
    private int currentLevel = 0;
    // positionTracker = list of arrays holding the index of the current option
    // and the total num of options. Keeps
    // track of where you are in defining decision tree
    // position in array corresponds with level
    public ArrayList<Integer[]> positionTracker = new ArrayList();
    int highestNumLevels = 0; // the highest number of levels that any branch has
    public ArrayList<Integer> printTracker = new ArrayList();
    int maxNumCriteria = 18;
    int maxNumOptions = 18;
    //private String[][] selectionProfile = new String[0][0];
    int profileNumber = 1; // what profile the user is defining (row the user is defining)
    private ArrayList<String[]> specificationProfile = new ArrayList(); // the sub-choices that specify each criterion
    // the criteria chosen, in order. corresponds to specificationProfile
    // the index in the arraylist is the level each criterion corresponds to
    private ArrayList<String> criteriaProfile = new ArrayList();
    private ArrayList optionStoreTopLevel = new ArrayList();
    public boolean noMoreOptionSets = false;
    /*** --------------- fields for fetching contents ------------------- ***/
    ArrayList<String> masterSubjectList = null;
    ArrayList<String> workingSubjectList = null;// working subject list to take away from when samples are accepted
    ArrayList<ContainerContent> displayContents = null;// list of contents to show user
    ArrayList<String> noContents = null; // list of subjects that don't have any contents
    ArrayList<String> noMoreMatches = null; // list of subjects that don't have any contents that match profiles
    ArrayList<String> acceptedContents = null; // list of content IDs that user has accepted
    ArrayList<String> rejectedContents = null; // list of contents that user has rejected
    String resultsListName = null; // name for list of samples to be made at end

    /**
     * set up top level criteria for selecting, (re)set decision structure
     * @param tmpHttpSessObj
     */
    public SampleSelector(UserHttpSess tmpHttpSessObj) {
        session = tmpHttpSessObj;
        // prepare criteria
        //allCriteria.put("header0", "    Select a Criterion     ");
        allCriteria.put("header1", "---------- Types ----------");
        allCriteria.put("sampleType", "Sample Type");
        allCriteria.put("materialType", "Material Type");
        allCriteria.put("stockOnly", "Stock Samples Only");
        allCriteria.put("header2", "--- Physical Properties ---");
        allCriteria.put("concentrationMinimum", "Concentration");
        allCriteria.put("volumeMinimum", "Volume");
        allCriteria.put("amtDNAMinimum", "Amount of DNA");
        allCriteria.put("dilution", "Dilution");
        allCriteria.put("concentrationMaximize", "Maximize Concentration");
        allCriteria.put("volumeMaximize", "Maximize Volume");
        allCriteria.put("amtDNAMaximize", "Maximize Amount of DNA");
        allCriteria.put("header3", "-------- Genotyping -------");
        allCriteria.put("prevGenotyped", "Previously Genotyped");
        allCriteria.put("callRate", "Genotyping Call Rate");
        allCriteria.put("neverGenotyped", "Never Genotyped");
        allCriteria.put("header4", "---------- Dates ----------");
        allCriteria.put("collectionDate", "Collection Date Range");
        allCriteria.put("extractionDate", "Extraction Date Range");
        allCriteria.put("amplificationDate", "Amplification Date Range");
        allCriteria.put("collectionDateMax", "Most Recent Collection Date");
        allCriteria.put("extractionDateMax", "Most Recent Extraction Date");
        allCriteria.put("amplificationDateMax", "Most Recent Amplification Date");


        // (re)set decision structure
        currentLevel = 0;
        levelAvailCriteria = new LinkedHashMap[maxNumCriteria];
        levelAvailCriteria[0] = allCriteria;
    }

    public LinkedHashMap getAllCriteria() {
        return allCriteria;
    }

    public LinkedHashMap getAvailCriteriaForLevel(int level) {
        return levelAvailCriteria[level];
    }

    public void setAvailCriteriaForLevel(int level, LinkedHashMap lvlCriteria) {
        levelAvailCriteria[level] = lvlCriteria;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int input) {
        currentLevel = input;
    }

    public ArrayList<String> getCriteriaProfile() {
        return criteriaProfile;
    }

    public int getHighestNumLevels() {
        return highestNumLevels;
    }

    public void setHighestNumLevels(int input) {
        highestNumLevels = input;
    }

    public void updateHighestNumLevels(int input) {
        if (highestNumLevels < input) {
            highestNumLevels = input;
        }
    }

    public void increaseProfileNumber(int inc) {
        if (inc == 0) {
            inc = 1;
        }
        profileNumber += inc;
    }

    public int getProfileNumber() {
        return profileNumber;
    }

    public void updateSpecificationProfile(int level, String[] specification) {
        specificationProfile.set(level, specification);
        //selectionProfile[level] = specification;
        System.out.println("selectionProfile.toString(): " + specificationProfile.toString());
        System.out.println("selectionProfile.get(level).toString(): " + specificationProfile.get(level).toString());
        System.out.println("(String[]) selectionProfile.get(" + level + "))[0]: " + ((String[]) specificationProfile.get(level))[0]);
        System.out.println("selectionProfile.size(): " + specificationProfile.size());
    }

    public void updateCriteriaProfile(int level, String criterion) {
        criteriaProfile.set(level, criterion);
        //selectionProfile[level] = specification;
        System.out.println("selectionProfile.toString(): " + criteriaProfile.toString());
        System.out.println("selectionProfile.get(" + level + "): " + criteriaProfile.get(level));
        System.out.println("selectionProfile.size(): " + specificationProfile.size());
    }

    public void removeSpecificationProfile(int level) {
        specificationProfile.remove(level);
        System.out.println("1 specificationProfile.toString(): " + specificationProfile.toString());
        System.out.println("removed level " + level + " specificationProfile.size(): " + specificationProfile.size());
        System.out.println("2 specificationProfile.toString(): " + specificationProfile.toString());
    }

    public void removeCriteriaProfile(int level) {
        criteriaProfile.remove(level);
        System.out.println("removed level " + level + ", criteriaProfile.size(): " + specificationProfile.size());
    }

    public void addToSpecificationProfile(int level, String[] specification) {
        specificationProfile.add(level, specification);
        //selectionProfile[level] = specification;
        System.out.println("selectionProfile.toString(): " + specificationProfile.toString());
        System.out.println("selectionProfile.get(level).toString(): " + specificationProfile.get(level).toString());
        System.out.println("(String[]) selectionProfile.get(" + level + "))[0]: " + ((String[]) specificationProfile.get(level))[0]);
        System.out.println("selectionProfile.size(): " + specificationProfile.size());
    }

    public void addToCriteriaProfile(int level, String criterion) {
        criteriaProfile.add(level, criterion);
        //selectionProfile[level] = specification;
        System.out.println("selectionProfile.toString(): " + criteriaProfile.toString());
        System.out.println("selectionProfile.get(" + level + "): " + criteriaProfile.get(level));
        System.out.println("selectionProfile.size(): " + specificationProfile.size());
    }

    public String getReadableOption(String crtierionName, Object option) {
        if (crtierionName.equals("sampleType")) {
            return ": " + getReadableSampleType(Integer.valueOf(option.toString()));
        }
        if (crtierionName.equals("materialType")) {
            return ": " + getReadableMaterialType(Integer.valueOf(option.toString()));
        }
        if (crtierionName.equals("stockOnly")) {
            return "";
        }
        if (crtierionName.equals("concentrationMinimum")) {
            return ": " + option.toString() + " ng/ul";
        }
        if (crtierionName.equals("volumeMinimum")) {
            return ": " + option.toString() + " ul";
        }
        if (crtierionName.equals("amtDNAMinimum")) {
            return ": " + option.toString() + " ng";
        }
        if (crtierionName.equals("dilution")) {
            ArrayList arr = (ArrayList) option;
            Iterator iter = arr.iterator();
            String str = ": ";
            while (iter.hasNext()) {
                str = str + iter.next();
                if (iter.hasNext()) {
                    str = str + " or ";
                }
            }
            return str;
        }
        if (crtierionName.equals("concentrationMaximize")) {
            return "";
        }
        if (crtierionName.equals("volumeMaximize")) {
            return "";
        }
        if (crtierionName.equals("amtDNAMaximize")) {
            return "";
        }
        if (crtierionName.equals("prevGenotyped")) {
            return "";
        }
        if (crtierionName.equals("callRate")) {
            return ((ArrayList) option).get(0) + ((((ArrayList) option).get(1).equals("true")) ? ", WGA ok" : ", no WGA");
        }
        if (crtierionName.equals("neverGenotyped")) {
            return "";
        }
        if (crtierionName.equals("collectionDate")) {
            //within dates
            if ((((ArrayList) option).get(2).equals("in"))) {
                return ": after " + ((ArrayList) option).get(0).toString()
                        + " and before " + ((ArrayList) option).get(1).toString();
            }
            // outside of date range
            if ((((ArrayList) option).get(2).equals("out"))) {
                return ": before " + ((ArrayList) option).get(0).toString()
                        + " or after " + ((ArrayList) option).get(1).toString();
            }
        }
        if (crtierionName.equals("extractionDate")) {
            //within dates
            if ((((ArrayList) option).get(2).equals("in"))) {
                return ": after " + ((ArrayList) option).get(0).toString()
                        + " and before " + ((ArrayList) option).get(1).toString();
            }
            // outside of date range
            if ((((ArrayList) option).get(2).equals("out"))) {
                return ": before " + ((ArrayList) option).get(0).toString()
                        + " or after " + ((ArrayList) option).get(1).toString();
            }
        }
        if (crtierionName.equals("amplificationDate")) {
            //within dates
            if ((((ArrayList) option).get(2).equals("in"))) {
                return ": after " + ((ArrayList) option).get(0).toString()
                        + " and before " + ((ArrayList) option).get(1).toString();
            }
            // outside of date range
            if ((((ArrayList) option).get(2).equals("out"))) {
                return ": before " + ((ArrayList) option).get(0).toString()
                        + " or after " + ((ArrayList) option).get(1).toString();
            }
        }
        if (crtierionName.equals("collectionDateMax")) {
            return "";
        }
        if (crtierionName.equals("extractionDateMax")) {
            return "";
        }
        if (crtierionName.equals("amplificationDateMax")) {
            return "";
        }

        return null;
    }

    private String getReadableSampleType(Integer optionID) {
        SampleType obj = (SampleType) session.getObjectById(SampleType.class, String.valueOf(optionID));
        return obj.getDescription();
    }

    private String getReadableMaterialType(Integer optionID) {
        MaterialType obj = (MaterialType) session.getObjectById(MaterialType.class, String.valueOf(optionID));
        return obj.getDescription();
    }

    public void printSpecificationProfile(String prefix) {
        if (specificationProfile != null && criteriaProfile != null) {
            Object[] specs = (Object[]) specificationProfile.toArray();
            Object[] crits = (Object[]) criteriaProfile.toArray();
            System.out.println(prefix);
            int minLength = Math.min(crits.length, specs.length);
            for (int i = 0; i < minLength; i++) {
                //(int j = 0; j<specs.length;j++){
                for (int k = 0; k < ((Object[]) specs[i]).length; k++) {
                    System.out.println(i + "." + k + ") " + crits[i] + ": " + ((Object[]) specs[i])[k]);
                }
                // }
            }
        }
    }

    public void printOptionsStore() {
        System.out.print(printArrayList(optionStoreTopLevel));
        /*Iterator iter = optionStoreTopLevel.iterator();
        ArrayList tmp1 = new ArrayList();
        Object[] entry;
        while(iter.hasNext()){
        entry = (Object[])iter.next();
        tmp1 = (ArrayList) entry[1];
        }*/
    }

    public String printArrayList(ArrayList arrList) {
        if (arrList == null) {
            return "[END1\n";
        }
        Iterator iter = arrList.iterator();
        Object[] entry;
        String str = "";
        while (iter.hasNext()) {
            entry = (Object[]) iter.next();
            str = str + entry[0] + printArrayList((ArrayList) entry[1]);
        }
        return str;
    }

    /**
     * returns the current option index of each level as a formatted string,
     * ex: 0.2-1.1-2.1 would mean that the option being defined now is a
     * daughter of the 1st level's 3rd option, the 2nd level's 1st option and the
     * 3rd leve's 2nd option
     * @return a string summary of where you are in defining the decision tree
     */
    public String getPositionTrackerStateString() {
        String str = "";
        Integer[] arr;
        for (int i = 0; i < positionTracker.size(); i++) {
            arr = positionTracker.get(i);
            str = str + i + "." + arr[0] + "-";
        }
        return str;
    }

    /**
     * returns the current option index of each level as an ArrayList of indexes,
     * ex: {2,1,1} would mean that the option being defined now is a
     * daughter of the 1st level's 3rd option, the 2nd level's 1st option and the
     * 3rd leve's 2nd option
     * @return an ArrayList summary of where you are in defining the decision tree
     */
    public ArrayList getPositionTrackerStateArrayList() {
        Integer[] arr;
        ArrayList state = new ArrayList();
        for (int i = 0; i < positionTracker.size(); i++) {
            arr = positionTracker.get(i);
            state.add(arr[0]);
        }
        return state;
    }

    /**
     * store the options the user has decided on using the current position
     * @param options ArrayList of strings or string arrays (depending on the
     * criteria field) that define the preferential/allowable values for a field
     * @param position current position in decision tree as formatted by getPositionTrackerStateArrayList()
     * @return 0 if successful, 1 if not
     */
    public int storeOptions(ArrayList options, String criteria, ArrayList position) {
        try {
//            System.out.println("storeOptions, position:" + position.toString());
//            System.out.println("storeOptions, options:" + options.toString());

            // get the lowest level array (the one to insert into)

            // if setting options of top array, just use optionStoreTopLevel pointer
            if (position.size() == 0) {
                // insert each option
                // make arrays to hold each option and it's next-level-down arraylist (null now)
                Iterator iter = options.iterator();
                while (iter.hasNext()) {
                    Object[] newEntry = {criteria, iter.next(), null};
                    optionStoreTopLevel.add(newEntry);
                }
            } else {
                Object[] entryArray;
                ArrayList currArray = new ArrayList(optionStoreTopLevel);
                // i = 1 because top level (optionsStoreTopLevel) is already set
                for (int i = 0; i < position.size(); i++) {

                    System.out.println("At i=" + i + " pos(i)=" + position.get(i)
                            + " matArr(pos(i))=" + currArray.get((Integer) position.get(i)));
                    entryArray = (Object[]) currArray.get((Integer) position.get(i));
                    ArrayList optionLevelsList = null;
                    // if adding options to this level, need new arraylist
                    if (entryArray[2] == null) {
                        optionLevelsList = new ArrayList();
                        // insert each option
                        // make arrays to hold each option and it's next-level-down arraylist
                        Iterator iter = options.iterator();
                        while (iter.hasNext()) {
                            Object[] newEntry = {criteria, iter.next(), null};
                            optionLevelsList.add(newEntry);
                        }
                        entryArray[2] = optionLevelsList;
                        currArray.set((Integer) position.get(i), entryArray);
                        break;
                    } // if this level is above the one options are beinbg added to, traverse it
                    else {
                        currArray = (ArrayList) entryArray[2];
                    }
                }
            }

            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
            return 1;
        }
    }

    /**
     * returns the next set of options in order of criteria, or null if exauhsted all options
     * also increments the current position in the options store (printTracker).
     * print tracker need to be initialised before the first time running this
     * to be a list of x zeros, where x = greatest number of levels in any profile
     * each optionSet element is an array of [criteria,value]
     * @param position array list describing what option for each crtieria level
     * is currently being used to select samples
     * @return the next set of options in order of criteria, or null if exauhsted all options
     */
    public ArrayList getNextOptionSet() {
        if (noMoreOptionSets == true) {
            return null;
        }

        // option set in criteria order
        ArrayList optionSet = new ArrayList();

        Object[] entryArray;
        ArrayList currArray = new ArrayList(optionStoreTopLevel);
        // i = 1 because top level (optionsStoreTopLevel) is already set
        for (int i = 0; i < printTracker.size(); i++) {
            entryArray = (Object[]) currArray.get((Integer) printTracker.get(i));
            // if next level down's arraylist is null, reached the end of values
            if (entryArray[2] == null) {
                // add last value (2nd) and the criteria it represents (1st) to option set
                Object[] ar = {entryArray[0], entryArray[1]};
                optionSet.add(ar);
                // increment position
                incrementPosition();
                break;
            } // if this level is not the lowest
            // add its options and follow that option to its next level down
            else {
                Object[] ar = {entryArray[0], entryArray[1]};
                optionSet.add(ar);
                currArray = (ArrayList) entryArray[2];
            }
        }

        return optionSet;
    }

    /**
     * try to increment position, if can't will set global variable noMoreOptionSets = true
     * @param position
     * @return false if couldn't increment any more
     */
    private void incrementPosition() {
        boolean incremented = false;

        // if getting options from top array, just use optionStoreTopLevel pointer
        if (printTracker.size() == 1) {
            int currPos = (Integer) printTracker.get(0);
            // if run out of options don't increment and return false
            if (optionStoreTopLevel.size() <= currPos + 1) {
                noMoreOptionSets = true;
                return;
            }

            int newPos = currPos + 1;
            printTracker.set(0, newPos);
            incremented = true;
        } else {
            // arraylist of all option arraylists in path
            ArrayList<ArrayList> lists = new ArrayList<ArrayList>();
            Object[] entryArray;
            ArrayList currArray = new ArrayList(optionStoreTopLevel);
            lists.add(currArray);
            // i = 1 because top level (optionsStoreTopLevel) is already set
            for (int i = 0; i < printTracker.size(); i++) {
                entryArray = (Object[]) currArray.get((Integer) printTracker.get(i));
                // if next level down's arraylist is null, reached the end of values
                if (entryArray[2] == null) {
                    break;
                } // if this level is not the lowest
                // add its next level down arraylist and go to it
                else {
                    lists.add((ArrayList) entryArray[2]);
                    currArray = ((ArrayList) entryArray[2]);
                }
            }

            // now have all array lists in path

            // try incrementing lowest arraylist index,
            // if can't, move up one and reset lower arraylist indexes to 0

            for (int i = lists.size() - 1; i >= 0 && !incremented; i--) {
                currArray = lists.get(i);
                int currPos = (Integer) printTracker.get(i);
                // if can increment
                if (currArray.size() > currPos + 1) {

                    // increment position
                    int newPos = currPos + 1;
                    printTracker.set(i, newPos);
                    incremented = true;

                    // reset levels below
                    for (int j = lists.size() - 1; j > i; j--) {
                        //printTracker.remove(j);
                        printTracker.set(j, 0);
                    }
                }
            }
        }
        noMoreOptionSets = (!incremented) ? true : false;
    }

    /**
     * get HTML for 2 date inputs
     * @param fieldName name for root of date field names
     * @return html for 2 date inputs (after & before)
     */
    public String getSelectorDatePrompter(String fieldName) {
        StringBuffer xyz = new StringBuffer();
        Date aDate = null;
        Date bDate = null;
        xyz.append("<td><table><tr><td class='dialh'>After</td>");
        xyz.append("<td><input type='text' name='");
        xyz.append(fieldName);
        xyz.append("ayear' maxlength='4' size='4' value='");
        if (aDate != null) {
            xyz.append(Util.getYear(aDate));
        }
        xyz.append("'><input type='text' name='");
        xyz.append(fieldName);
        xyz.append("amonth' maxlength='2' size='2' value='");
        if (aDate != null) {
            xyz.append(Util.getMonth(aDate));
        }
        xyz.append("'><input type='text' name='");
        xyz.append(fieldName);
        xyz.append("aday' maxlength='2' size='2' value='");
        if (aDate != null) {
            xyz.append(Util.getDay(aDate));
        }
        xyz.append("'></td></tr><tr><td>&nbsp;</td><td>(yyyy/mm/dd)</td></tr>");
        xyz.append("<tr><td class='dialh'>Before</td>");
        xyz.append("<td><input type='text' name='");
        xyz.append(fieldName);
        xyz.append("byear' maxlength='4' size='4' value='");
        if (bDate != null) {
            xyz.append(Util.getYear(bDate));
        }
        xyz.append("'><input type='text' name='");
        xyz.append(fieldName);
        xyz.append("bmonth' maxlength='2' size='2' value='");
        if (bDate != null) {
            xyz.append(Util.getMonth(bDate));
        }
        xyz.append("'><input type='text' name='");
        xyz.append(fieldName);
        xyz.append("bday' maxlength='2' size='2' value='");
        if (bDate != null) {
            xyz.append(Util.getDay(bDate));
        }
        xyz.append("'></td></tr></table></td>");
        return xyz.toString();
    }

    /**
     * prepare to fetch 'best-match' and 'next-best-match' contents
     * save name of list that will store accepted contents
     * @param listName
     */
    public void initialiseFetcher(String listName) {
        // get subject list
        masterSubjectList = session.getCurrentShoppingCartList().getSubjectList();
        // make a copy = working subject list to take away from when samples are accepted
        workingSubjectList = new ArrayList(masterSubjectList);
        noContents = new ArrayList();
        noMoreMatches = new ArrayList();
        displayContents = new ArrayList();
        acceptedContents = new ArrayList(); // list of content IDs that user has accepted
        rejectedContents = new ArrayList(); // list of content IDs that user has rejected

        resultsListName = listName;

        // do initial check to remove subjects without any containerContents in the DB
        String subjID = null;
        // for each subject ID
        for (int i = 0; i < workingSubjectList.size(); i++) {
            subjID = workingSubjectList.get(i);
            // check each subject to make sure it has at least one content in the DB
            HashMap contentCheck = new HashMap();
            contentCheck.put("sample.subject.subjectID", subjID);
            ArrayList samples = (ArrayList) session.getObjectsOfClass(ContainerContent.class, contentCheck);
            // if no samples, add the subject to the noSamplesList
            if (samples == null || samples.isEmpty()) {
                noContents.add(subjID);
                // remove the subject from the working list
                workingSubjectList.remove(subjID);
            }
        }

    }

    /**
     * fetch 'best-match' and 'next-best-match' contents
     * @param listName
     */
    public void fetchSamples() {

        displayContents.clear();
        System.out.println("workingSubjectList.size, pre noSample: " + workingSubjectList.size());

        String subjID = null;
        // for each subject ID
        for (int i = 0; i < workingSubjectList.size(); i++) {
            subjID = workingSubjectList.get(i);
            // check each subject to make sure it has at least one content in the DB
            HashMap contentCheck = new HashMap();
            contentCheck.put("sample.subject.subjectID", subjID);
            ArrayList samples = (ArrayList) session.getObjectsOfClass(ContainerContent.class, contentCheck);
            // if no samples, add the subject to the noSamplesList
            if (samples == null || samples.isEmpty()) {
                noContents.add(subjID);
                // remove the subject from the working list
                workingSubjectList.remove(subjID);
            }
        }

        // for all subjects with at least one db-sample
        for (int i = 0; i < workingSubjectList.size(); i++) {
            System.out.println("working on subject: " + workingSubjectList.get(i));

            // initialise printTrakcer
            printTracker.clear();
            for (int j = 0; j <= highestNumLevels; j++) {
                printTracker.add(0);
            }
            // while a content has not been found
            boolean subjectResolved = false;
            noMoreOptionSets = false;
            while (!subjectResolved) {

                subjID = workingSubjectList.get(i);

                ArrayList optionSet = getNextOptionSet();

                // if no profile associated with this profile number
                // then you've run out of options for this sample
                if (optionSet == null) {
                    noMoreMatches.add(subjID);
                    // remove the subject from the working list
                    workingSubjectList.remove(subjID);
                    // move the tracker back so don't skip next subject
                    i--;
                    subjectResolved = true;
                } else {
                    // turn the option set into a query and run it
                    ArrayList results = runQuery(optionSet, subjID);

                    // if you get 1+ result(s), take the first one
                    // add it to the display-sample list
                    if (results != null && results.size() > 0) {
                        Iterator res = results.iterator();
                        boolean found = false;
                        // keep going until you find a result that hasn't been rejected
                        while (res.hasNext() & !found) {
                            ContainerContent cc = (ContainerContent) res.next();
                            if (!rejectedContents.contains(cc.getContainerContentsID())) {
                                found = true;
                                displayContents.add(cc);
                                // mark the sample as found
                                subjectResolved = true;
                            }
                        }
                    }
                }
                // if you don't get any results, try again
            }
        }
    }

    /**
     * return container contents based on the option set provided
     * @param optionSet
     * @return
     */
    private ArrayList runQuery(ArrayList optionSet, String subjID) {

        // option sets are array lists of arrays (length=2) with index 0 = the criteria and index 1 = the option
        // option formats are different for each

        // make up a statement for each option
        ArrayList statements = new ArrayList();
        ArrayList orders = new ArrayList();
        ArrayList optionArr = new ArrayList();
        Iterator iter = optionSet.iterator();
        while (iter.hasNext()) {
            Object[] thisOption = (Object[]) iter.next();
            String crtierionName = thisOption[0].toString();
            Object option = thisOption[1];
            System.out.println("sample selector query maker, crtierionName:" + crtierionName);
            System.out.println("sample selector query maker, option:" + option.toString());
            if (crtierionName.equals("sampleType")) {
                statements.add(" (sample.sampleType.sampleTypeID = " + option.toString() + ") ");
            }
            if (crtierionName.equals("materialType")) {
                statements.add(" (materialType.materialTypeID = " + option.toString() + ") ");
            }
            if (crtierionName.equals("stockOnly")) {
                statements.add(" (container.isStock = 1) ");
            }
            if (crtierionName.equals("concentrationMinimum")) {
                statements.add(" (concentration " + option.toString() + ") ");
            }
            if (crtierionName.equals("volumeMinimum")) {
                statements.add(" (volume " + option.toString() + ") ");
            }
            if (crtierionName.equals("amtDNAMinimum")) {
                statements.add(" (volume*concentration " + option.toString() + ") ");
            }
            if (crtierionName.equals("dilution")) {
                ArrayList arr = (ArrayList) option;
                Iterator dilu = arr.iterator();
                String str = " (";
                while (dilu.hasNext()) {
                    str = str + " dilution = '" + dilu.next() + "' ";
                    if (dilu.hasNext()) {
                        str = str + " or ";
                    }
                }
                statements.add(str + ") ");
            }
            if (crtierionName.equals("concentrationMaximize")) {
                orders.add(" concentration Desc ");
            }
            if (crtierionName.equals("volumeMaximize")) {
                orders.add(" volume Desc ");
            }
            if (crtierionName.equals("amtDNAMaximize")) {
                orders.add(" (volume*concentration) Desc ");
            }
            if (crtierionName.equals("prevGenotyped")) {
                statements.add(" (containerContentsID is in "
                        + "(select containerContentsID from genotypingRunSampleStatus where valid = 1)) ");
            }
            if (crtierionName.equals("callRate")) {
                optionArr = (ArrayList) option;
                statements.add(" (containerContentsID is in "
                        + "(select containerContentsID from genotypingRunSampleStatus where valid = 1 and "
                        + "callRate " + optionArr.get(0).toString() + " )) ");
            }
            if (crtierionName.equals("neverGenotyped")) {
                statements.add(" (containerContentsID not in "
                        + "(select containerContentsID from genotypingRunSampleStatus where valid = 1)) ");
            }
            if (crtierionName.equals("collectionDate")) {
                optionArr = (ArrayList) option;
                //within dates
                if (optionArr.get(2).equals("in")) {
                    statements.add(" (sample.dateCollected >= '" + optionArr.get(0).toString()
                            + "' and sample.dateCollected < '" + optionArr.get(1).toString() + "')");
                }
                // outside of date range
                if (optionArr.get(2).equals("out")) {
                    statements.add(" (sample.dateCollected is null or sample.dateCollected < '" + optionArr.get(0).toString()
                            + "' or sample.dateCollected >= '" + optionArr.get(1).toString() + "')");
                }
            }
            if (crtierionName.equals("extractionDate")) {
                optionArr = (ArrayList) option;
                //within dates
                if (optionArr.get(2).equals("in")) {
                    statements.add(" (sample.dateExtracted >= '" + optionArr.get(0).toString()
                            + "' and sample.dateExtracted < '" + optionArr.get(1).toString() + "')");
                }
                // outside of date range
                if (optionArr.get(2).equals("out")) {
                    statements.add(" (sample.dateExtracted is null or sample.dateExtracted < '" + optionArr.get(0).toString()
                            + "' or sample.dateExtracted >= '" + optionArr.get(1).toString() + "')");
                }
            }
            if (crtierionName.equals("amplificationDate")) {
                optionArr = (ArrayList) option;
                //within dates
                if (optionArr.get(2).equals("in")) {
                    statements.add(" (sample.amplificationDate >= '" + optionArr.get(0).toString()
                            + "' and sample.amplificationDate < '" + optionArr.get(1).toString() + "')");
                }
                // outside of date range
                if (optionArr.get(2).equals("out")) {
                    statements.add(" (amplificationDate is null or amplificationDate < '" + optionArr.get(0).toString()
                            + "' or amplificationDate >= '" + optionArr.get(1).toString() + "')");
                }
            }
            if (crtierionName.equals("collectionDateMax")) {
                orders.add(" sample.dateCollected desc ");
            }
            if (crtierionName.equals("extractionDateMax")) {
                orders.add(" sample.dateExtracted desc ");
            }
            if (crtierionName.equals("amplificationDateMax")) {
                orders.add(" amplificationDate desc ");
            }
        }

        String prefix = "from ContainerContent ";
        String where = " where sample.subject.subjectID = " + subjID;
        for (int i = 0; i < statements.size(); i++) {
            where = where.concat(" and ");
            where = where.concat(statements.get(i).toString());

        }
        String order = (orders.size() > 0) ? " order by " : "";
        for (int i = 0; i < orders.size(); i++) {
            order = order.concat(orders.get(i).toString());
            if (i < orders.size() - 1) {
                order = order.concat(",");
            }
        }
        // join into a query
        System.out.println("sample selector query :" + prefix + where + order);
        Query q;
        q = session.hibSess.createQuery(prefix + where + order);
        ArrayList list = (ArrayList) q.list();
        return list;
    }

    public List getDisplayList() {
        return displayContents;
    }

    public ArrayList getNoContentsList() {
        return noContents;
    }

    public ArrayList getNoContentsSubjNames() {
        ArrayList subjNames = new ArrayList(noContents.size());
        Subject sub = null;
        for (int i = 0; i < noContents.size(); i++) {
            sub = (Subject) session.getObjectById(Subject.class, noContents.get(i).toString());
            subjNames.add(sub.getCohort().getDescription() + " " + sub.getSubjectName());
        }
        return subjNames;
    }

    public ArrayList getNoMoreMatchesList() {
        return noMoreMatches;
    }

    public ArrayList getNoMoreMatchesSubjNames() {
        ArrayList subjNames = new ArrayList(noMoreMatches.size());
        Subject sub = null;
        for (int i = 0; i < noMoreMatches.size(); i++) {
            sub = (Subject) session.getObjectById(Subject.class, noMoreMatches.get(i).toString());
            subjNames.add(sub.getCohort().getDescription() + " " + sub.getSubjectName());
        }
        return subjNames;
    }

    /**
     * adds container content's ID to accept list and removes its subject from the working list
     * @param cc
     */
    public void acceptContent(ContainerContent cc) {
        acceptedContents.add(cc.getContainerContentsID());
        workingSubjectList.remove(cc.getSample().getSubject().getSubjectID());
    }

    public ArrayList<String> getAcceptedContents() {
        return acceptedContents;
    }

    /**
     * adds container content's ID to rejected list so it won't be chosen again
     * @param cc
     */
    public void rejectContent(ContainerContent cc) {
        rejectedContents.add(cc.getContainerContentsID());
    }

    public ArrayList<String> getRejectedContents() {
        return rejectedContents;
    }

    public boolean finishedWorkingSubjectList() {
        return workingSubjectList.isEmpty();
    }

    public void createList() throws Exception {
        session.addShoppingList(resultsListName, session.getCurrentUser());
        // load new list as current
        session.setCurrentShoppingCartList(new ShoppingCartList(session, resultsListName));
    }

    public void fillList() throws Exception {
        Iterator iter = acceptedContents.iterator();
        while (iter.hasNext()) {
            session.getCurrentShoppingCartList().addObjectsByContent(iter.next().toString());
        }
    }
}
