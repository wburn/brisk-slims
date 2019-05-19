/**
 * Manages setting up the application and contains nearly all the database
 * querying methods. This includes methods to add, edit and delete entries
 * in the database, fetch all objects of a certain type (and potentially
 * only those that satisfy search criteria) and get an object by its
 * database ID. It also stores ‘current’ variables shared throughout the
 * application such as power and simple search objects, a user’s ID, type,
 * and permission status, a plating object, the active shopping list, and
 * active objects (like containers, subjects etc—though these can be unreliable).
 */
package icapture.com;

import java.util.*;


import javax.servlet.http.*;
import icapture.hibernate.*;
import icapture.hibernate.ShippedToMetaData;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public final class UserHttpSess {

    private static ArrayList<String> histParents = null;
    private static ArrayList<String> histTree = null;
    private static Configuration cfg;
    private static HttpSession httpSess = null;
    private String httpSessId = null;
    public static Session hibSess = null;
    private File tempFile = null;
    private static File qualityDirectory = null;
    private static SessionFactory hibFactory = null;
    private static int viewLimit = 0;
    private static String secondUser = null;
    private User currentUser = null;
    private ShippedTo currentShippedTo = null;
    private Freezer currentFreezer = null;
    private String shippedToSortCol = "sortOrder";
    private String freezerSortCol = "sortOrder";
    private boolean shippedToSortDir = true; //true - asc; false - desc
    private boolean freezerSortDir = true; //true - asc; false - desc
    private String invalidField;
    private RequestParser reqpar; //for class deployment only
    private DataExport exporter;   //for class deployment only
    private MetadataManager mdm = null;
    private ViewShippedToManager vhm = null;
    private ViewFreezerManager vfm = null;
    private Fieldname fn;        //for class deployment only
    private FilterObject filter = null;  //
    private SearchObject search = null;
    private FilterObject filterList = null;  //
    private SearchObject searchList = null;
    private HashMap filterValues = null;
    private HashMap filterListValues = null;
    private ArrayList idsToLoad = null;
    private SampleSelector currentSampleSelector = null;
    private Plater currentPlater = null;
    private FileUploading currentUploader = null;
    private String histRootParent = null;
    //private LinkedList samplesList = new LinkedList();
    //private LinkedList contentsList = new LinkedList();
    private ShoppingCartList currentShoppingList = null;
    private String[] query = null;
    private static final String[] myMessage = {
        "", "Internal error", //1
        "Invalid or missing identifier", //2
        "Duplicate identifier in database", //3
        "Invalid relative person", //4
        "Invalid or missing date", //5
        "You have read rights only", //6
        "Invalid or missing number", //7
        "Misuse of \"Back\" command", //8
        "Mandatory value(s) missing", //9
        "Shelf must be identified by a number", //10
        "Sort order value must be numeric", //11
        "Row and column values must be numeric", //12
        "Temperature must be numeric", //13
        "Mother and Father IDs must be numeric", //14
        "Parent ID must be numeric", //15
        "Row, column, volume and concentration must be numeric and positive", //16
        "Invalid parent well", // 17
        "Creation of genomic plates must reduce volumes of the original plate's samples by > 0 uL ", //18
        "One or more samples in the original plate have insufficient volume to complete this action", //19
        "Concentrations must be positive.", //20
        "Each filename must be unique", //21
        "Unable to find sample history", //22
        "Sample not in any container", //23
        "Shipment name missing", //24
        "Invalid email address"
    };

    /**
     * Constructor with a parameter for specifying the log path
     * @param logPath The path of the log file
     */
    public UserHttpSess(String logPath) {
        File logDirectory = new File(logPath + "/log");
        logDirectory.mkdir();
        Util.initializeLog(logDirectory);
        cfg = new Configuration().addClass(User.class).addClass(UserMetaData.class).addClass(ShippedTo.class).addClass(ShippedToMetaData.class).addClass(Freezer.class).addClass(FreezerMetaData.class).addClass(ContainerType.class).addClass(ContainerTypeMetaData.class).addClass(Cohort.class).addClass(CohortMetaData.class).addClass(Container.class).addClass(ContainerMetaData.class).addClass(SampleProcess.class).addClass(SampleProcessMetaData.class).addClass(SampleType.class).addClass(SampleTypeMetaData.class).addClass(Subject.class).addClass(SubjectMetaData.class).addClass(Sample.class).addClass(SampleMetaData.class).addClass(ContainerContent.class).addClass(ContainerContentMetaData.class).addClass(GenotypingRunContainer.class).addClass(GenotypingRunContainerMetaData.class).addClass(GenotypingRun.class).addClass(GenotypingRunMetaData.class).addClass(GenotypingRunSampleStatus.class).addClass(GenotypingRunSampleStatusMetaData.class).addClass(GenotypingRunSnpStatus.class).addClass(GenotypingRunSnpStatusMetaData.class).addClass(Genotype.class).addClass(GenotypeMetaData.class).addClass(Snp.class).addClass(SnpMetaData.class).addClass(icapture.hibernate.ShoppingList.class).addClass(ShoppingListContainerContent.class).addClass(ShoppingListSample.class).addClass(ShoppingListContainer.class).addClass(ShoppingListSubject.class).addClass(Ethnicity.class).addClass(EthnicityMetaData.class).addClass(MaterialType.class).addClass(MaterialTypeMetaData.class).addClass(Control.class).addClass(ControlMetaData.class).addClass(ControlWell.class).addClass(ControlLayoutWell.class).addClass(SubjectPhenotype.class).addClass(Phenotype.class).addClass(UserType.class).addClass(SampleDocuments.class).addClass(SampleDocumentsMetaData.class).addClass(Shipment.class).addClass(ShipCont.class).addClass(Gene.class).addClass(GeneSet.class).addClass(GeneSetLookUp.class).addClass(SnpSetLookUp.class).addClass(SnpSet.class);
        hibFactory = cfg.buildSessionFactory();

        vhm = new ViewShippedToManager(this);
        vfm = new ViewFreezerManager(this);
        vContainerTypeM = new ViewContainerTypeManager(this);
        vContainerM = new ViewContainerManager(this);
        vCohortM = new ViewCohortManager(this);
        vSampleProcessM = new ViewSampleProcessManager(this);
        vSampleTypeM = new ViewSampleTypeManager(this);
        vSubjectM = new ViewSubjectManager(this);
        vSampleM = new ViewSampleManager(this);
        vContainerContentM = new ViewContainerContentManager(this);
        vGenotypingRunM = new ViewGenotypingRunManager(this);
        vGenotypingRunSampleStatusM = new ViewGenotypingRunSampleStatusManager(this);
        vGenotypingRunSnpStatusM = new ViewGenotypingRunSnpStatusManager(this);
        vGenotypeM = new ViewGenotypeManager(this);
        vSnpM = new ViewSnpManager(this);
        vEthnicityM = new ViewEthnicityManager(this);
        vMaterialTypeM = new ViewMaterialTypeManager(this);
        vControlM = new ViewControlManager(this);
        vUserM = new ViewUserManager(this);
        vShipmentM = new ViewShipmentManager(this);
    }

    /**
     * Constructor that takes an existing session and associates UserHttpSEss with that
     * @param s the HttpSession session
     */
    public UserHttpSess(HttpSession s) {
        httpSess = s;
        httpSessId = s.getId();
        vhm = new ViewShippedToManager(this);
        vfm = new ViewFreezerManager(this);
        vContainerTypeM = new ViewContainerTypeManager(this);
        vContainerM = new ViewContainerManager(this);
        vCohortM = new ViewCohortManager(this);
        vSampleProcessM = new ViewSampleProcessManager(this);
        vSampleTypeM = new ViewSampleTypeManager(this);
        vSubjectM = new ViewSubjectManager(this);
        vSampleM = new ViewSampleManager(this);
        vContainerContentM = new ViewContainerContentManager(this);
        vGenotypingRunM = new ViewGenotypingRunManager(this);
        vGenotypingRunSampleStatusM = new ViewGenotypingRunSampleStatusManager(this);
        vGenotypingRunSnpStatusM = new ViewGenotypingRunSnpStatusManager(this);
        vGenotypeM = new ViewGenotypeManager(this);
        vSnpM = new ViewSnpManager(this);
        vEthnicityM = new ViewEthnicityManager(this);
        vMaterialTypeM = new ViewMaterialTypeManager(this);
        vControlM = new ViewControlManager(this);
        vUserM = new ViewUserManager(this);
        vSampleDocumentsM = new ViewSampleDocumentsManager(this);
        vShipmentM = new ViewShipmentManager(this);

    }

    /**
     * Resets variables used in the class
     * @return true if success
     */
    public boolean resetCurrents() {
        try {
            releaseLockCurrentShoppingList();
        } catch (Exception e) {
//            e.printStackTrace();
        }
        currentShippedTo = null;
        currentSampleProcess = null;
        currentFreezer = null;
        currentCohort = null;
        currentContainerType = null;
        currentContainer = null;
        currentSubject = null;
        currentSample = null;
        currentUser = null;
        currentContainerContent = null;
        currentGenotype = null;
        currentGenotypingRun = null;
        currentGenotypingRunContainer = null;
        currentGenotypingRunSampleStatus = null;
        currentGenotypingRunSnpStatus = null;
        currentSnp = null;
        currentEthnicity = null;
        currentSampleDocuments = null;
        currentShipment = null;
        mdm = null;
        vhm.clearValues();
        vfm.clearValues();
        vContainerTypeM.clearValues();
        vCohortM.clearValues();
        vContainerM.clearValues();
        vSampleProcessM.clearValues();
        vSampleTypeM.clearValues();
        vSubjectM.clearValues();
        vSampleM.clearValues();
        vContainerContentM.clearValues();
        vGenotypingRunM.clearValues();
        vGenotypingRunSampleStatusM.clearValues();
        vGenotypingRunSnpStatusM.clearValues();
        vGenotypeM.clearValues();
        vSnpM.clearValues();
        vEthnicityM.clearValues();
        vMaterialTypeM.clearValues();
        vControlM.clearValues();
        vUserM.clearValues();
        vSampleDocumentsM.clearValues();
        vShipmentM.clearValues();
        cancelFilter();
        cancelSearch();
        cancelFilterList();
        cancelSearchList();
        currentSampleSelector = null;
        currentShoppingList = null;
        currentPlater = null;
        currentShipCont = null;

        return true;
    }

    /**
     * Opens the session factory
     * @throws HibernateException
     */
    private void openFactory() throws HibernateException {
        ServletContext sc = httpSess.getServletContext();
        File logDirectory = new File(sc.getRealPath("/log"));
        logDirectory.mkdir();
        Util.initializeLog(logDirectory);
        cfg = new Configuration().addClass(User.class).addClass(UserMetaData.class).addClass(ShippedTo.class).addClass(ShippedToMetaData.class).addClass(Freezer.class).addClass(FreezerMetaData.class).addClass(ContainerType.class).addClass(ContainerTypeMetaData.class).addClass(Cohort.class).addClass(CohortMetaData.class).addClass(Container.class).addClass(ContainerMetaData.class).addClass(SampleProcess.class).addClass(SampleProcessMetaData.class).addClass(SampleType.class).addClass(SampleTypeMetaData.class).addClass(Subject.class).addClass(SubjectMetaData.class).addClass(Sample.class).addClass(SampleMetaData.class).addClass(ContainerContent.class).addClass(ContainerContentMetaData.class).addClass(GenotypingRunContainer.class).addClass(GenotypingRunContainerMetaData.class).addClass(GenotypingRun.class).addClass(GenotypingRunMetaData.class).addClass(GenotypingRunSampleStatus.class).addClass(GenotypingRunSampleStatusMetaData.class).addClass(GenotypingRunSnpStatus.class).addClass(GenotypingRunSnpStatusMetaData.class).addClass(Genotype.class).addClass(GenotypeMetaData.class).addClass(Snp.class).addClass(SnpMetaData.class).addClass(icapture.hibernate.ShoppingList.class).addClass(ShoppingListContainerContent.class).addClass(ShoppingListSample.class).addClass(ShoppingListContainer.class).addClass(ShoppingListSubject.class).addClass(Ethnicity.class).addClass(EthnicityMetaData.class).addClass(MaterialType.class).addClass(MaterialTypeMetaData.class).addClass(Control.class).addClass(ControlMetaData.class).addClass(ControlWell.class).addClass(ControlLayoutWell.class).addClass(SubjectPhenotype.class).addClass(Phenotype.class).addClass(UserType.class).addClass(SampleDocuments.class).addClass(SampleDocumentsMetaData.class).addClass(Shipment.class).addClass(ShipmentMetaData.class).addClass(ShipCont.class).addClass(Gene.class).addClass(GeneSet.class).addClass(GeneSetLookUp.class).addClass(SnpSetLookUp.class).addClass(SnpSet.class);

        hibFactory = cfg.buildSessionFactory();
        secondUser = sc.getInitParameter("seconduser");
        // Prepares limit of view tables row number
        String tmp11 = sc.getInitParameter("viewlimit");
        if (tmp11 != null) {
            viewLimit = Util.getInt(tmp11);
        }
        //Println used to be here

    }

    public String getProperty(String name) {
        return cfg.getProperties().getProperty(name);
    }

    public void buildMetaData() throws HibernateException {
        openHibSession();
        mdm = new MetadataManager();
        List list = getAllObjects(ContainerTypeMetaData.class);
        mdm.buildContainerTypeData(list);
        list = getAllObjects(ContainerMetaData.class);
        mdm.buildContainerData(list);
        list = getAllObjects(SampleProcessMetaData.class);
        mdm.buildSampleProcessData(list);
        list = getAllObjects(ShippedToMetaData.class);
        mdm.buildShippedToData(list);
        list = getAllObjects(CohortMetaData.class);
        mdm.buildCohortData(list);
        list = getAllObjects(FreezerMetaData.class);
        mdm.buildFreezerData(list);
        list = getAllObjects(SampleTypeMetaData.class);
        mdm.buildSampleTypeData(list);
        list = getAllObjects(SubjectMetaData.class);
        mdm.buildSubjectData(list);
        list = getAllObjects(SampleMetaData.class);
        mdm.buildSampleData(list);
        list = getAllccMetaDataByUserID(ContainerContentMetaData.class);
        mdm.buildContainerContentData(list);
        list = getAllObjects(GenotypingRunContainerMetaData.class);
        mdm.buildGenotypingRunContainerData(list);
        list = getAllObjects(GenotypingRunMetaData.class);
        mdm.buildGenotypingRunData(list);
        list = getAllObjects(GenotypingRunSampleStatusMetaData.class);
        mdm.buildGenotypingRunSampleStatusData(list);
        list = getAllObjects(GenotypingRunSnpStatusMetaData.class);
        mdm.buildGenotypingRunSnpStatusData(list);
        list = getAllObjects(GenotypeMetaData.class);
        mdm.buildGenotypeData(list);
        list = getAllObjects(SnpMetaData.class);
        mdm.buildSnpData(list);
        list = getAllObjects(EthnicityMetaData.class);
        mdm.buildEthnicityData(list);
        list = getAllObjects(MaterialTypeMetaData.class);
        mdm.buildMaterialTypeData(list);
        list = getAllObjects(ControlMetaData.class);
        mdm.buildControlData(list);
        list = getAllObjects(UserMetaData.class);
        mdm.buildUserData(list);
        list = getAllObjects(SampleDocumentsMetaData.class);
        mdm.buildSampleDocumentsData(list);
        list = getAllObjects(ShipmentMetaData.class);
        mdm.buildShipmentData(list);

        closeHibSession();
    }

    public void setSettings(HttpServletRequest request) throws HibernateException {
        String className[] = {
            Fieldname.CONTAINERTYPE, Fieldname.SHIPPEDTO, Fieldname.FREEZER,
            Fieldname.COHORT, Fieldname.CONTAINER, Fieldname.SAMPLEPROCESS,
            Fieldname.SAMPLETYPE, Fieldname.SUBJECT, Fieldname.SAMPLE,
            Fieldname.CONTAINERCONTENT, Fieldname.GENOTYPINGRUNCONTAINER,
            Fieldname.GENOTYPINGRUN, Fieldname.GENOTYPINGRUNSAMPLESTATUS,
            Fieldname.GENOTYPINGRUNSNPSTATUS, Fieldname.GENOTYPE, Fieldname.SNP,
            Fieldname.ETHNICITY, Fieldname.SAMPLEDOCUMENTS, Fieldname.SHIPMENT, 
            Fieldname.GENE, Fieldname.GENESET, Fieldname.SNP, Fieldname.SNPSET};
        List list[] = new List[20];
        Iterator iter[] = new Iterator[20];
        TempMetaData data[] = new TempMetaData[20];
        TempMetaData tempMetaData;

        // changed order, might make problem?
        list[0] = mdm.createOrderMetaData(mdm.getShippedToMetaData());
        list[1] = mdm.createOrderMetaData(mdm.getFreezerMetaData());
        list[2] = mdm.createOrderMetaData(mdm.getContainerTypeMetaData());
        list[3] = mdm.createOrderMetaData(mdm.getCohortMetaData());
        list[4] = mdm.createOrderMetaData(mdm.getContainerMetaData());
        list[5] = mdm.createOrderMetaData(mdm.getSampleProcessMetaData());
        list[6] = mdm.createOrderMetaData(mdm.getSampleTypeMetaData());
        list[7] = mdm.createOrderMetaData(mdm.getSubjectMetaData());
        list[8] = mdm.createOrderMetaData(mdm.getSampleMetaData());
        list[9] = mdm.createOrderMetaData(mdm.getContainerContentMetaData());
        list[10] = mdm.createOrderMetaData(mdm.getGenotypingRunContainerMetaData());
        list[11] = mdm.createOrderMetaData(mdm.getGenotypingRunMetaData());
        list[12] = mdm.createOrderMetaData(mdm.getGenotypingRunSampleStatusMetaData());
        list[13] = mdm.createOrderMetaData(mdm.getGenotypingRunSnpStatusMetaData());
        list[14] = mdm.createOrderMetaData(mdm.getGenotypeMetaData());
        list[15] = mdm.createOrderMetaData(mdm.getSnpMetaData());
        list[16] = mdm.createOrderMetaData(mdm.getEthnicityMetaData());
        list[17] = mdm.createOrderMetaData(mdm.getMaterialTypeMetaData());
        list[18] = mdm.createOrderMetaData(mdm.getControlMetaData());
        list[19] = mdm.createOrderMetaData(mdm.getSampleDocumentsMetaData());
        list[20] = mdm.createOrderMetaData(mdm.getShipmentMetaData());

        int size = 0;
        int cNumb;
        String reportName;
        String value;

        for (int i = 0; i < 3; i++) {
            iter[i] = list[i].iterator();
            size = Math.max(size, list[i].size());
        }

        for (int j = 0; j < size; j++) {
            for (int i = 0; i < 3; i++) {
                tempMetaData = iter[i].hasNext() ? (TempMetaData) iter[i].next() : null;

                if (tempMetaData != null) {
                    reportName = className[i] + " " + tempMetaData.getShortName()
                            + " colNumb";
                    value = request.getParameter(reportName);

                    try {
                        cNumb = Integer.parseInt(value);
                        tempMetaData.setViewColumnNumber(cNumb);
                    } catch (Exception ex) {
                        tempMetaData.setViewColumnNumber(0);
                    }
                }
            }
        }



        for (int i = 0; i < 3; i++) {
            MetadataManager.orderMetaData(list[i]);
            iter[i] = list[i].iterator();
            cNumb = 1;

            while (iter[i].hasNext()) {
                tempMetaData = (TempMetaData) iter[i].next();
                tempMetaData.setViewColumnNumber(tempMetaData.getViewColumnNumber() > 0 ? cNumb++ : 0);
            }
        }

        this.mdm.normalizeShippedToData();
        this.mdm.normalizeFreezerData();
        this.mdm.normalizeCohortData();
        this.mdm.normalizeContainerTypeData();
        this.mdm.normalizeSampleProcessData();
        this.mdm.normalizeContainerData();
        this.mdm.normalizeSampleTypeData();
        this.mdm.normalizeSubjectData();
        this.mdm.normalizeSampleData();
        this.mdm.normalizeContainerContentData();
        this.mdm.normalizeGenotypingRunSampleStatusData();
        this.mdm.normalizeGenotypingRunSnpStatusData();
        this.mdm.normalizeGenotypeData();
        this.mdm.normalizeSnpData();
        this.mdm.normalizeEthnicityData();
        this.mdm.normalizeMaterialTypeData();
        this.mdm.normalizeControlData();
        this.mdm.normalizeSampleDocumentsData();
        this.mdm.normalizeShipmentData();
    }

    public void setFilter(HttpServletRequest request) {
        filter = new FilterObject();
        filterValues = filter.setFilter(request);
    }

    public void setFilterList(HttpServletRequest request) {
        filterList = new FilterObject();
        filterListValues = filterList.setFilter(request);
    }

    public void cancelFilter() {
        filter = null;
        filterValues = null;
        vhm.clearValues();
        vfm.clearValues();
        vContainerTypeM.clearValues();
        vCohortM.clearValues();
        vContainerM.clearValues();
        vSampleProcessM.clearValues();
        vSampleTypeM.clearValues();
        vSubjectM.clearValues();
        vSampleM.clearValues();
        vContainerContentM.clearValues();
        vGenotypingRunM.clearValues();
        vGenotypingRunSampleStatusM.clearValues();
        vGenotypingRunSnpStatusM.clearValues();
        vGenotypeM.clearValues();
        vSnpM.clearValues();
        vEthnicityM.clearValues();
        vMaterialTypeM.clearValues();
        vSampleDocumentsM.clearValues();
        vShipmentM.clearValues();
    }

    public void cancelFilterList() {
        filterList = null;
        filterListValues = null;
        vhm.clearValues();
        vfm.clearValues();
        vContainerTypeM.clearValues();
        vCohortM.clearValues();
        vContainerM.clearValues();
        vSampleProcessM.clearValues();
        vSampleTypeM.clearValues();
        vSubjectM.clearValues();
        vSampleM.clearValues();
        vContainerContentM.clearValues();
        vGenotypingRunM.clearValues();
        vGenotypingRunSampleStatusM.clearValues();
        vGenotypingRunSnpStatusM.clearValues();
        vGenotypeM.clearValues();
        vSnpM.clearValues();
        vEthnicityM.clearValues();
        vMaterialTypeM.clearValues();
        vSampleDocumentsM.clearValues();
        vShipmentM.clearValues();
    }

    public boolean isFilter() {
        return (filter != null);
    }

    public boolean isFilterList() {
        return (filterList != null);
    }

    public boolean isFilterListQueryingSubjects() {
        return (filterList.queryingSubject());
    }

    public boolean isFilterListQueryingSamples() {
        return (filterList.queryingSample());
    }

    public boolean isFilterListQueryingContents() {
        return (filterList.queryingContent());
    }

    public boolean isFilterListQueryingContainers() {
        return (filterList.queryingContainer());
    }

    /**
     * checks if a field value is in the filter
     * @param nameVal the name of the field concat with the value in question
     * @return true if the value is in the filter
     */
    public boolean isInFilter(String nameVal) {
        if (filterValues != null && filterValues.containsKey(nameVal)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * checks if a field value is in the list filter
     * @param nameVal the name of the field concat with the value in question
     * @return true if the value is in the filter
     */
    public boolean isInFilterList(String nameVal) {
        if (filterListValues != null && filterListValues.containsKey(nameVal)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isSearch() {
        return (search != null);
    }

    public void setSearch(HttpServletRequest request) {
        search = new SearchObject();
        search.setUpSearch(request);
    }

    public void cancelSearch() {
        search = null;
    }

    public boolean isSearchList() {
        return (searchList != null);
    }

    public void setSearchList(HttpServletRequest request) {
        searchList = new SearchObject();
        searchList.setUpSearch(request);
    }

    public void cancelSearchList() {
        searchList = null;
    }

    public void setQuerySearch(HttpServletRequest request) {
        query = request.getParameterValues("query");
    }

    public List getQueryResults(String query) throws HibernateException {
        return hibSess.createSQLQuery(query.trim()).list();
    }

    public String[] getQueryResultsColumnNames(String query) throws HibernateException {
        openHibSession();
        Query q = hibSess.createSQLQuery(query.trim());

        String[] str = q.getReturnAliases();
        return q.getReturnAliases();
    }

    /**
     * Returns the error message corresponding to the message number
     * @param messageNum the error message number
     * @return error message corresponding to the message number
     */
    public static String getMyMessage(int messageNum) {
        return myMessage[messageNum];
    }

    /**
     * Gets the number of results limited
     * @return The number of results
     */
    public static int getViewLimit() {
        return viewLimit;
    }

    public MetadataManager getMetadataManager() throws Exception {
        if (mdm == null) {
            throw new Exception(getMyMessage(8));
        }
        return mdm;
    }

    public static void closeFactory() {
        try {
            if (hibFactory != null) {
                hibFactory.close();
                hibFactory = null;
                Util.writeEndOfApplication();
            }
        } catch (Exception ex) {
            Util.writeLog("Close factory",
                    ex.getMessage(),
                    "");
        }
    }

    public void setShippedToSortCol(String sortCol) {
        if (shippedToSortCol.equals(sortCol)) {
            shippedToSortDir = !shippedToSortDir;
        } else {
            shippedToSortCol = sortCol;
            shippedToSortDir = true;
        }
    }

    public void setFreezerSortCol(String sortCol) {
        if (freezerSortCol.equals(sortCol)) {
            freezerSortDir = !freezerSortDir;
        } else {
            freezerSortCol = sortCol;
            freezerSortDir = true;
        }
    }

    public String getShippedToSortId(String sortCol) {
        return (!shippedToSortCol.equals(sortCol)) ? "normal" : (shippedToSortDir) ? "normalu" : "normalo";
    }

    public String getFreezerSortId(String sortCol) {
        return (!freezerSortCol.equals(sortCol)) ? "normal" : (freezerSortDir) ? "normalu" : "normalo";
    }

    /**
     * Opens a hibernate session to run hibernate commands. Will not open if there is already a session opened.
     * @throws HibernateException
     */
    public void openHibSession() throws HibernateException {
        if (hibFactory == null) {
            openFactory();
        }
        if (hibSess == null || !hibSess.isOpen()) {
            hibSess = hibFactory.openSession();
        }
    }

    /**
     * Closes the hibernate session used to run hibernate commands. Will not close if no session is opened.
     */
    public void closeHibSession() {
        try {
            if (hibSess != null && hibSess.isOpen()) {
                hibSess.close();
            }
        } catch (Exception ex) {
            Util.writeLog("Close session",
                    ex.getMessage(),
                    httpSessId);
        }
    }

    /**
     * Closes the hibernate session
     */
    public void closeAll() {
        if (currentUser != null) {
            Util.writeLog(currentUser.getVisibleName(),
                    "End of session",
                    httpSessId);
        }
        closeHibSession();
    }

    /**
     * Finds the user info and logs in if matches
     * @param userName username
     * @param pswd password
     * @return true if logged in
     * @throws Exception
     */
    public boolean logIn(String userName, String pswd) throws Exception {
        resetCurrents();
        if (userName != null && pswd != null) {


            // change password to match encrypted version stored in DB
            String pswwd = Util.criptString(pswd);

            //not storing passwords as encrypted format
//            String pswwd = pswd;

            Query q;
            q = hibSess.createQuery("from User user" + " where user.loginName = :username" + " and user.password = :pswd");
            q.setString("username", userName);
            q.setString("pswd", pswwd);
            List list = q.list();
            if (list.isEmpty()) {
                q = hibSess.createQuery("select count(*) from User");
                int count = ((Long) q.iterate().next()).intValue();
                if (count == 0) {
                    currentUser = addUserGetUser("-1", "First user", userName, pswd, 10, 0, "FU", "default first user for setup");
                } else if (secondUser != null
                        && secondUser.equals(userName + '/' + pswd)) {
                    currentUser = addUserGetUser("-1", "Second user", userName, pswd, 10, 0, "SU", "Back-up account.");
                    secondUser = null;
                }
            } else {
                currentUser = (User) list.get(0);
            }
        }
        if (currentUser.getRights()==100){
            return false;
        }
        if(currentUser != null) {
            //currentShoppingList = new ShoppingCartList(this,new Long(0));
            currentShoppingList = null;
            currentSampleSelector = null;
            Util.writeLog(currentUser.getVisibleName(),
                    currentUser.getLoginName(),
                    httpSessId);
            buildMetaData();
            return true;
        }
        return false;
    }

    /**
     * Logs out the user and calls resetCurrents()
     * @return true if logged out
     */
    public boolean logOut() {
        if (currentUser != null) {
            Util.writeLog(currentUser.getVisibleName(),
                    "End of session", httpSessId);
        }
        resetCurrents();
        return true;
    }

    /**
     * Gets the current user's name
     * @return The current user's name
     */
    public String getUserString() {
        return (currentUser == null) ? "NONE" : currentUser.getVisibleName();
    }

    /**
     * Gets the current user
     * @return the current logged in User object
     */
    public User getCurrentUser() {
        return currentUser;
    }

    public HttpSession getHttpSess() {
        return httpSess;
    }

    public String getHttpSessId() {
        return (httpSess == null) ? "NONE" : httpSess.getId();
    }
//----------------------------------------------------------//
//----------------------------------------------------------//
//----------------------------------------------------------//
//----------------------------------------------------------//

    /**
     * Grabs all objects from a single class
     * @param className the name of the class you want to get objects from
     * @return a list of items in that class
     * @throws HibernateException
     */
    public List getAllObjects(String className) throws HibernateException {
        return hibSess.createQuery("from " + className).list();
    }

    /**
     * Grabs all obejcts from a single class
     * @param cl the class you want to get objects from
     * @return
     * @throws HibernateException
     */
    public List getAllObjects(Class cl) throws HibernateException {
        openHibSession();
        return hibSess.createQuery("from " + cl.getName()).list();
    }

    /**
     * Gets all the data about the current user
     * @param cl the class you want to get it from
     * @return a list
     * @throws HibernateException
     */
    public List getAllMetaDataByUserID(Class cl) throws HibernateException {
        openHibSession();
        return hibSess.createQuery("from " + cl.getName() + " where userID =" + currentUser.getId()).list();
    }

    public List getAllccMetaDataByUserID(Class cl) throws HibernateException {
        openHibSession();
        return hibSess.createQuery("from " + cl.getName()
                + " where userID =" + currentUser.getId() + " order by longName").list();
    }

    public List getAllIdAndNames(Class cl) throws HibernateException {
        return hibSess.createQuery("select myObj.id, myObj.visibleName from " + cl.getName() + " myObj").list();
    }

    public List getAllField(Class cl, String field) throws HibernateException {
        openHibSession();
        return hibSess.createQuery("select distinct myObj." + field + " from " + cl.getName() + " myObj order by myObj." + field).list();
    }

    public List getAllIdAndUniqueField(Class cl, String field) throws HibernateException {
        openHibSession();
        return hibSess.createQuery("select myObj.id, myObj." + field + " from " + cl.getName() + " myObj order by myObj." + field).list();
    }

    /**
     * Returns a list of all shippedTo locations
     * @param startPosition the position to start form
     * @return a list of all shippedTo locations
     * @throws HibernateException
     */
    public List getAllShippedTo(int startPosition) throws
            HibernateException {
        String orderClause = " order by myShippedTo."
                + ((shippedToSortDir) ? shippedToSortCol : shippedToSortCol + " desc");
        String prefix = "select myShippedTo from ShippedTo as myShippedTo";
        Query q = hibSess.createQuery(prefix + orderClause);
        if (viewLimit > 0 && startPosition >= 0) {
            q.setFirstResult(startPosition);
            q.setMaxResults(viewLimit);
        }
        ////Println used to be here
        ////Println used to be here

        return q.list();
    }

    public ShippedTo getShippedTo(String shippedToDescription) throws
            HibernateException {
        Query q = hibSess.createQuery("from ShippedTo Con " + " where Con.description= :shippedToDescription");
        q.setString("shippedToDescription", shippedToDescription);
        if (q.list().isEmpty()) {
            return null;
        } else {
            return (ShippedTo) q.iterate().next();
        }
    }

    public List getAllFreezers(int startPosition) throws
            HibernateException {
        String orderClause = " order by myFreezer."
                + ((freezerSortDir) ? freezerSortCol : freezerSortCol + " desc");
        String prefix = "select myFreezer from Freezer as myFreezer";
        Query q = hibSess.createQuery(prefix + orderClause);
        if (viewLimit > 0 && startPosition >= 0) {
            q.setFirstResult(startPosition);
            q.setMaxResults(viewLimit);
        }
        ////Println used to be here
        ////Println used to be here

        return q.list();
    }

    public int getAllShippedTosCount() throws HibernateException {
        String prefix = "select count(*) from ShippedTo as myShippedTo";
        Query q;
        q = hibSess.createQuery(prefix);
        int count = ((Long) q.iterate().next()).intValue();
        return count;
    }

    public int getAllFreezersCount() throws HibernateException {
        String prefix = "select count(*) from Freezer as myFreezer";
        Query q;
        q = hibSess.createQuery(prefix);
        ////Println used to be here
        ////Println used to be here

        int count = ((Long) q.iterate().next()).intValue();
        return count;
    }

    public String getObjectPrompter(Class cl, String objIdStr) throws
            HibernateException {
        StringBuilder xyz = new StringBuilder();
        List list = getAllObjects(cl);
        if (list != null) {
            //xyz.append("<option selected value=\"\"> ").append("none");
            try {
                Long objId = Long.parseLong(objIdStr);
                Iterator iter = list.iterator();
                while (iter.hasNext()) {
                    Persistent obj = (Persistent) iter.next();
                    if (obj.getId().equals(objId)) {
                        xyz.append("<option selected value=\"");
                    } else {
                        xyz.append("<option value=\"");
                    }
                    xyz.append(obj.getId()).append("\">").append(obj.getVisibleName());
                }
            } catch (Exception e) {

                Iterator iter = list.iterator();

                while (iter.hasNext()) {
                    Persistent obj = (Persistent) iter.next();
                    xyz.append("<option value=\"");
                    xyz.append(obj.getId()).append("\">").append(obj.getVisibleName());
                }
            }
        }
        return xyz.toString();
    }

    public String getObjectPrompterUniqueField(Class cl, String objIdStr, String field, boolean nullOK) throws
            HibernateException {
        StringBuilder xyz = new StringBuilder();
        List list = getAllIdAndUniqueField(cl, field);
        int i = 0;
        if (list != null) {
            if (nullOK) {
                xyz.append("<option value=\"\" id=\"option").append(i).append("\">").append("none");
                i++;
            }
            try {
                if (objIdStr != null && !objIdStr.equals("")) {
                    Long.parseLong(objIdStr);
                }
                Iterator iter = list.iterator();
                while (iter.hasNext()) {
                    Object[] vals = (Object[]) iter.next();
                    if (vals[0].toString().equals(objIdStr)) {
                        xyz.append("<option selected id=\"option").append(i).append("\" value=\"");
                    } else {
                        xyz.append("<option id=\"option").append(i).append("\" value=\"");
                    }
                    i++;
                    xyz.append(vals[0]).append("\">").append(vals[1]).append("</option>\n");
                }
            } catch (Exception e) {
                //Println used to be here
            }
        }
        return xyz.toString();

    }

    public String getObjectPrompterNullOK(Class cl, String objIdStr) throws
            HibernateException {
        StringBuilder xyz = new StringBuilder();
        List list = getAllObjects(cl);
        if (list != null) {
            xyz.append("<option value=\"\"> ").append("none");
            try {
                Long objId = Long.parseLong(objIdStr);
                Iterator iter = list.iterator();
                while (iter.hasNext()) {
                    Persistent obj = (Persistent) iter.next();
                    if (obj.getId().equals(objId)) {
                        xyz.append("<option selected value=\"");
                    } else {
                        xyz.append("<option value=\"");
                    }
                    xyz.append(obj.getId()).append("\">").append(obj.getVisibleName());
                }
            } catch (Exception e) {

                Iterator iter = list.iterator();

                while (iter.hasNext()) {
                    Persistent obj = (Persistent) iter.next();
                    xyz.append("<option value=\"");
                    xyz.append(obj.getId()).append("\">").append(obj.getVisibleName());
                }
            }
        }
        return xyz.toString();
    }

    public String getObjectPrompterField(Class cl, String objIdStr, String field, boolean nullOK) throws
            HibernateException {
        StringBuilder xyz = new StringBuilder();
        List list = getAllField(cl, field);
        if (list != null) {
            String sel = (objIdStr == null || objIdStr.equals("")) ? "selected" : "";
            if (nullOK) {
                xyz.append("<option ").append(sel).append(" value=\"\"> ").append("none");
            }
            try {
                Iterator iter = list.iterator();
                while (iter.hasNext()) {
                    String vals = (String) iter.next();
                    if (vals.equals(objIdStr)) {
                        xyz.append("<option selected value=\"");
                    } else {
                        xyz.append("<option value=\"");
                    }
                    xyz.append(vals).append("\">").append(vals);
                }
            } catch (Exception e) {
                //Println used to be here
            }
        }
        return xyz.toString();

    }

    public String getObjectPrompter(Class cl, Long objId) throws
            HibernateException {
        StringBuilder xyz = new StringBuilder();
        List list = getAllObjects(cl);
        if (list != null) {
            Iterator iter = list.iterator();
            while (iter.hasNext()) {
                Persistent obj = (Persistent) iter.next();
                if (objId != null && obj.getId().equals(objId)) {
                    xyz.append("<option selected value=\"");
                } else {
                    xyz.append("<option value=\"");
                }
                xyz.append(obj.getId().toString()).append("\">").append(obj.getVisibleName());
            }
        }

        return xyz.toString();
    }

    public String getObjectPrompterSubject(String objId) throws
            HibernateException {
        StringBuilder xyz = new StringBuilder();
        List list = getAllObjects(Subject.class);
        if (list != null) {
            Iterator iter = list.iterator();
            while (iter.hasNext()) {
                Subject obj = (Subject) iter.next();
                if (objId != null && obj.getSubjectID().equals(objId)) {
                    xyz.append("<option selected value=\"");
                } else {
                    xyz.append("<option value=\"");
                }
                xyz.append(obj.getId().toString()).append("\">").append(obj.getCohort().getDescription()).append(" ").append(obj.getSubjectName());
            }
        }

        return xyz.toString();
    }

    public String getObjectPrompterFromList(List list, Long objId) {
        StringBuilder xyz = new StringBuilder();
        if (list != null) {
            Iterator iter = list.iterator();
            while (iter.hasNext()) {
                Persistent obj = (Persistent) iter.next();
                if (obj.getId().equals(objId)) {
                    xyz.append("<option selected value=\"");
                } else {
                    xyz.append("<option value=\"");
                }
                xyz.append(obj.getId()).append("\">").append(obj.getVisibleName());
            }
        }
        return xyz.toString();
    }

    public String getPrompterForCCMetaDataOrder(String metadataName, String fieldName, String visName) {
        openHibSession();
        StringBuilder xyz = new StringBuilder();
        ContainerContentMetaData ccMeta = null;
        int selected = -1;
        String disabled = "disabled";
        String checked = "";

        Query q = hibSess.createQuery("select tbl from ContainerContentMetaData tbl" + " where visibleName = '" + metadataName + "' and userID = " + currentUser.getId());
        ccMeta = (ContainerContentMetaData) q.uniqueResult();
        if (ccMeta != null) {
            disabled = "";
            checked = "checked";
            selected = ccMeta.getViewColumnNumber();
            //Println used to be here
        }
        xyz.append("<tr><td><input type=\"checkbox\" id=\"ccmd_").append(fieldName).append("Check\" name=\"ccmd_").append(fieldName).append("Check\" ").append(checked).append("" + " onclick=\"toggleSelect(this)\" align=\"middle\">" + "<!--this will make the value submitted for ccmd_sampleIDCheck be false if the checkbox not checked-->" + "<input name=\"ccmd_").append(fieldName).append("Check\" value=\"false\" type=\"hidden\"/></td>" + "<td class=\"dialhLeft\">").append(visName).append("</td>" + "<td><select name=\"ccmd_").append(fieldName).append("Num\" id=\"ccmd_").append(fieldName).append("Num\" ").append(disabled).append(">");
        xyz.append(getMetaDataOrderOptions(fieldName, selected));
        xyz.append("</select></td></tr>");

        return xyz.toString();
    }

    private String getMetaDataOrderOptions(String rowName, int selected) throws HibernateException {
        StringBuilder xyz = new StringBuilder();
        int colCount = mdm.ccMetaDataAllColCount;
        for (int i = 1; i <= colCount; i++) {
            if (i == selected - 1) {
                xyz.append("<option selected value=\"");
            } else {
                xyz.append("<option value=\"");
            }
            xyz.append(i + 1).append("\">").append(i).append("</option>\n");
        }
        return xyz.toString();
    }

    /**
     * get a select box sized to the number of options for your field
     * with active search terms selected
     * @param cl class in prompter
     * @param field field of class to display to user
     * @param fieldName "name" param for option tags 
     *          and used in filter name to determine if option should be selected
     * @return
     * @throws org.hibernate.HibernateException
     */
    public String getSearchPrompterForField(Class cl, String field, String fieldName) throws
            HibernateException {
        StringBuilder xyz = new StringBuilder();
        List list = null;
        list = getAllField(cl, field);

        //List list = getAllIdAndNames(cl);
        if (list != null) {
            xyz.append("<td><select name=");
            xyz.append(fieldName);
            xyz.append(" multiple size='");
            xyz.append(list.size());
            xyz.append("'>");
            Iterator iter = list.iterator();
            while (iter.hasNext()) {
                //Object[] obj = (Object[]) iter.next();
                //String value = (String) obj[0];
                String value = (String) iter.next();
                if (value != null) {
                    xyz.append("<option");
                    if (filterValues != null && filterValues.containsKey(fieldName + value)) {
                        xyz.append(" selected");
                    }
                    xyz.append(" value='");
                    xyz.append(value).append("'>");
                    if (field.equals("gender")) {
                        if (value.equals("0")) {
                            value = "Unknown";
                        }
                        if (value.equals("1")) {
                            value = "Male";
                        }
                        if (value.equals("2")) {
                            value = "Female";
                        }
                    }
                    else if (field.equals("shipAction")) {
                        if (value.equals("0"))
                            value = "None";
                        else if(value.equals("1"))
                            value = "Shipped";
                        else if(value.equals("2"))
                            value = "Returned";
                    }else if (value.equals("0")) {
                        value = "No";
                    } else if (value.equals("1")) {
                        value = "Yes";
                    } else if (value.equals("2")) {
                        value = "Unknown";
                    }
                    xyz.append(value);
                }
            }
        }
        xyz.append("</select></td>");
        return xyz.toString();
    }
    // get search prompter for specified field

    public boolean inFilter(String fieldName, String value) {
        if (filterValues == null) {
            return false;
        }
        return filterValues.containsKey(fieldName + value);
    }

    // get input text box for specified field
    public String getSearchInputForField(Class cl, String fieldName) throws
            HibernateException {
        StringBuilder xyz = new StringBuilder();
        xyz.append("<input type=\"text\" name=\"");
        xyz.append(fieldName);
        xyz.append("\"  size='20' ");
        if (filterValues != null && filterValues.containsKey(fieldName)) {
            xyz.append(" value=\"").append(filterValues.get(fieldName)).append("\" ");
        }
        /*if (filterValues != null) {
        //Println used to be here
        //Println used to be here
        } else {
        //Println used to be here
        }*/

        xyz.append("/>");
        return xyz.toString();
    }

    // get input check box for exact searching of specified field
    public String getSearchInputForFieldExactBox(Class cl, String fieldName) throws
            HibernateException {
        StringBuilder xyz = new StringBuilder();
        xyz.append("<input type=\"checkbox\" name=\"").append(fieldName).append("Exact\" value=\"true\"");
        if (filterValues != null && filterValues.containsKey(fieldName + "Exacttrue")) {
            xyz.append(" checked");
        }
        xyz.append("/>Exact");
        return xyz.toString();
    }

    // get input check box for exact searching of specified field
    public String getSearchInputForFieldNotBox(Class cl, String fieldName) throws
            HibernateException {
        StringBuilder xyz = new StringBuilder();
        xyz.append("<input type=\"checkbox\" name=\"").append(fieldName).append("Not\" value=\"true\"");
        if (filterValues != null && filterValues.containsKey(fieldName + "Nottrue")) {
            xyz.append(" checked");
        }
        xyz.append("/>Is not");

        return xyz.toString();
    }

    /**
     * get input text box for specified field without 'exact' checkbox
     * @param cl class of field
     * @param fieldName
     * @return html text for element
     * @throws org.hibernate.HibernateException
     */
    public String getSearchInputForFieldNum(Class cl, String fieldName) throws
            HibernateException {
        StringBuilder xyz = new StringBuilder();
        xyz.append("<input type=\"text\" name=\"");
        xyz.append(fieldName);
        xyz.append("\"  size='8' ");
        if (filterValues != null && filterValues.containsKey(fieldName)) {
            xyz.append(" value=\"").append(filterValues.get(fieldName)).append("\" ");
        }
        /* if (filterValues != null) {
        //Println used to be here
        //Println used to be here
        } else {
        //Println used to be here
        }*/

        xyz.append(">");

        return xyz.toString();
    }

    /**
     * get a select box sized to the number of options for your field
     * @param cl class in prompter
     * @param field field of class to display to user
     * @param fieldName "name" param for select tag = fieldName+"In";<br>
     *             "id" param for select tag = fieldName+"In"
     * @return
     * @throws org.hibernate.HibernateException
     */
    public String getSelectorPrompterForField(Class cl, String field, String fieldName) throws
            HibernateException {
        StringBuilder xyz = new StringBuilder();
        List list = null;
        if (fieldName.equals("subjCohort")) {
            list = new ArrayList();
            list.add(((Cohort) getObjectById(Cohort.class, "3")).getDescription());
            list.add(((Cohort) getObjectById(Cohort.class, "4")).getDescription());
        } else {
            list = getAllField(cl, field);
        }
        //List list = getAllIdAndNames(cl);
        if (list != null) {
            xyz.append("<select name='");
            xyz.append(fieldName);
            xyz.append("' id='");
            xyz.append(fieldName);
            xyz.append("In' multiple size='");
            xyz.append(list.size());
            xyz.append("'>");
            Iterator iter = list.iterator();
            while (iter.hasNext()) {
                //Object[] obj = (Object[]) iter.next();
                //String value = (String) obj[0];
                String value = (String) iter.next();
                if (value != null) {
                    xyz.append("<option");
                    xyz.append(" value='");
                    xyz.append(value).append("'>");
                    if (field.equals("gender")) {
                        if (value.equals("0")) {
                            value = "Unknown";
                        }
                        if (value.equals("1")) {
                            value = "Male";
                        }
                        if (value.equals("2")) {
                            value = "Female";
                        }
                    } else if (value.equals("0")) {
                        value = "No";
                    } else if (value.equals("1")) {
                        value = "Yes";
                    } else if (value.equals("2")) {
                        value = "Unknown";
                    }
                    xyz.append(value);
                }
            }
        }
        xyz.append("</select>");
        return xyz.toString();
    }

    // get search prompter for specified field
    public String getSelectorPrompterForField(String fieldName) throws
            HibernateException {
        StringBuilder xyz = new StringBuilder();
        List list = null;

        if (fieldName.equals("sampleType")) {
            list = getAllField(SampleType.class, "sampleType");
        }
        if (fieldName.equals("materialType")) {
            list = getAllField(MaterialType.class, "materialType");
        }
        if (fieldName.equals("cohort")) {
            list = new ArrayList();
            list.add(((Cohort) getObjectById(Cohort.class, "3")).getDescription());
            list.add(((Cohort) getObjectById(Cohort.class, "4")).getDescription());
        }
        if (fieldName.equals("gender")) {
            list = getAllField(Subject.class, "gender");
        }
        //List list = getAllIdAndNames(cl);
        if (list != null) {
            xyz.append("<select name='");
            xyz.append(fieldName);
            xyz.append("' id='");
            xyz.append(fieldName);
            xyz.append("In' multiple size='");
            xyz.append(list.size());
            xyz.append("'>");
            Iterator iter = list.iterator();
            while (iter.hasNext()) {
                //Object[] obj = (Object[]) iter.next();
                //String value = (String) obj[0];
                String value = (String) iter.next();
                if (value != null) {
                    xyz.append("<option");
                    xyz.append(" value='");
                    xyz.append(value).append("'>");
                    if (fieldName.equals("gender")) {
                        if (value.equals("0")) {
                            value = "Unknown";
                        }
                        if (value.equals("1")) {
                            value = "Male";
                        }
                        if (value.equals("2")) {
                            value = "Female";
                        }
                    } else if (value.equals("0")) {
                        value = "No";
                    } else if (value.equals("1")) {
                        value = "Yes";
                    } else if (value.equals("2")) {
                        value = "Unknown";
                    }
                    xyz.append(value);
                }
            }
        }
        xyz.append("</select>");
        return xyz.toString();
    }

    /**
     * get a select box sized to the number of options for your field
     * with active search terms selected
     * displays 'visible name' field to user
     * @param cl class in prompter
     * @param fieldName "name" param for option tags
     *          and used in filter name to determine if option should be selected
     * @return
     * @throws org.hibernate.HibernateException
     */
    public String getSearchPrompterVisibleName(Class cl, String fieldName) throws
            HibernateException {
        StringBuilder xyz = new StringBuilder();
        List list = getAllIdAndNames(cl);
        if (list != null) {
            xyz.append("<td><select name='");
            xyz.append(fieldName);
            xyz.append("' multiple size='");
            xyz.append(list.size());
            xyz.append("'>");
            Iterator iter = list.iterator();
            while (iter.hasNext()) {
                Object[] obj = (Object[]) iter.next();
                Long id = (Long) obj[0];
                String visibleName = (String) obj[1];
//      Persistent obj = (Persistent) iter.next();
                xyz.append("<option");
                if (filterValues != null && filterValues.containsKey(fieldName + id)) {
                    xyz.append(" selected");
                }
                xyz.append(" value='");
                xyz.append(id).append("'>").append(visibleName);
            }
        }
        xyz.append("</select></td>");
        return xyz.toString();
    }

    /**
     * Sets up a set of input fields for date input
     * @param fieldName the name of the field
     * @return html code for the creation and display of a date fields
     */
    public String getSearchDatePrompter(String fieldName) {
        StringBuilder xyz = new StringBuilder();
        Date aDate = null;
        Date bDate = null;
        if (filterValues != null) {
            aDate = (Date) filterValues.get(fieldName + "a");
            bDate = (Date) filterValues.get(fieldName + "b");
        }
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
//----------------------------------------------------------//
//---------- Administrator tables --------------------------//
//----------------------------------------------------------//

    private boolean addPersistent(Persistent myObj, String name, String comment) throws Exception {
        if (currentUser == null || myObj == null) {
            return false;
        }
        myObj.setCreateInfo(currentUser);
        myObj.setVisibleName(name);
        myObj.setComment(comment);
        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.save(myObj);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            closeHibSession(); //needs before forward
        }
    }

    private boolean updatePersistent(Persistent myObj, String name, String comment) throws Exception {
        if (currentUser == null || myObj == null) {
            return false;
        }
        myObj.setModifInfo(currentUser);
        myObj.setVisibleName(name);
        myObj.setComment(comment);
        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.update(myObj);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            closeHibSession(); //needs before forward
        }
    }
//------------------------------------------------------//
//------------------------------------------------------//

    public Object getObjectById(Class cl, String sysId) throws HibernateException {
        openHibSession();
        Object obj = null;
        if (sysId != null) {
            Long id = new Long(sysId);
            obj = hibSess.load(cl, id);
        }
        return obj;
    }

    /**
     * Grabs the object with the specified ID in the specified class
     * @param cl the class to find the ID in
     * @param sysId the ID of the object to find
     * @return the Object in the class with the ID
     * @throws HibernateException
     */
    public Object getObjectById(Class cl, Long sysId) throws HibernateException {
        openHibSession();
        Object obj = null;
        if (sysId != null) {
            obj = hibSess.load(cl, sysId);
        }
        return obj;
    }

    /**
     * get the object defined by the field and values in the hashmap
     * @param c1 the class to look in
     * @param keys string fields need "'"s around the value
     * @return the matching object or null if there's none or >1
     */
    public Object getObjectByUniqueKey(Class c1, HashMap keys) {
        openHibSession();
        StringBuffer where = new StringBuffer();
        if (!keys.isEmpty()) {
            where.append(" where ");
        }
        Iterator iter = keys.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry pair = (Map.Entry) iter.next();
            ////Println used to be here
            where.append(pair.getKey().toString()).append(" = ").append(pair.getValue());
            if (iter.hasNext()) {
                where.append(" and ");
            }
        }
        try {
            Query q = hibSess.createQuery("select tbl from " + c1.getName() + " tbl" + where);
            return q.uniqueResult();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * get objects defined by the field and values in the hashmap
     * @param c1
     * @param keys string fields need "'"s around the value
     * @return a list of the matching objects
     */
    public List getObjectsOfClass(Class c1, HashMap keys) {
        openHibSession();
        StringBuffer where = new StringBuffer();
        if (!keys.isEmpty()) {
            where.append(" where ");
        }
        Iterator iter = keys.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry pair = (Map.Entry) iter.next();
            ////Println used to be here
            where.append(pair.getKey().toString()).append(" = ").append(pair.getValue());
            if (iter.hasNext()) {
                where.append(" and ");
            }
        }
        try {
            Query q = hibSess.createQuery("select tbl from " + c1.getName() + " tbl" + where);
            return q.list();
        } catch (Exception ex) {
            //Println used to be here
//            ex.printStackTrace();
            return null;
        }
    }

    public boolean addPersistent(Class cl,
            HttpServletRequest request) throws Exception {
        String visname = request.getParameter("visname");
        String comment = request.getParameter("comment");
        if (visname != null && !visname.trim().equals("")) {
            Persistent tmpObj = (Persistent) cl.newInstance();
            return addPersistent(tmpObj, visname, comment);
        }
        return false;
    }

    public boolean updatePersistent(Class cl,
            HttpServletRequest request) throws Exception {
        String sysId = request.getParameter("sysId");
        String visname = request.getParameter("visname");
        String comment = request.getParameter("comment");
        if (visname != null && !visname.trim().equals("")) {
            Persistent tmpObj = (Persistent) getObjectById(cl, sysId);
            return updatePersistent(tmpObj, visname, comment);
        }
        return false;
    }

    public boolean deletePersistent(Class cl,
            HttpServletRequest request) throws Exception {
        String sysId = request.getParameter("sysId");
        Object myObj = getObjectById(cl, sysId);
        if (currentUser == null || myObj == null) {
            return false;
        }
        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.delete(myObj);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            closeHibSession(); //needs before forward
        }
    }
//---User-------------------------------------------------
/*
    public User addUser(String usDescr, String logName,
    int usRights, String usPassw, String partner, String comment) throws Exception {
    openHibSession();
    User tmpUser = new User();
    tmpUser.setCreateDate(new Date());
    tmpUser.setModifDate(new Date());
    tmpUser.setVisibleName(usDescr);
    tmpUser.setLoginName(logName);
    tmpUser.setRights(usRights);
    //        tmpUser.setPassword(usPassw);
    String psswd = usPassw.trim();
    if (psswd.equals("")) {
    throw new Exception(this.getMyMessage(9));
    }
    tmpUser.setPassword(Util.criptString(psswd));

    tmpUser.setPartner(
    (Partner) getObjectById(Partner.class, partner));
    tmpUser.setComment(comment);
    Transaction tx = null;
    try {
    tx = hibSess.beginTransaction();
    hibSess.save(tmpUser);
    tx.commit();

    // set up container contents metadata for user to default for their user group
    vContainerContentM.resetMetaDataRows();
    vContainerContentM.refreshContainerContentMetaData();

    return tmpUser;
    } catch (Exception e) {
    if (tx != null) {
    tx.rollback();
    }
    throw e;
    } finally {
    closeHibSession(); //needs before forward
    }
    }
    
    public boolean updateUser(String usId, String usDescr, String logName,
    int usRights, String usPassw, String partner, String comment) throws Exception {
    openHibSession();
    if (currentUser == null) {
    return false;
    }
    User tmpUser = (User) getObjectById(User.class, usId);
    if (tmpUser != null) {
    tmpUser.setModifDate(new Date());
    tmpUser.setVisibleName(usDescr);
    tmpUser.setLoginName(logName);
    tmpUser.setRights(usRights);
    //            tmpUser.setPassword(usPassw);
    String psswd = usPassw;
    if (psswd != null) {
    psswd = psswd.trim();
    if (psswd.equals("")) {
    throw new Exception(this.getMyMessage(9));
    }
    tmpUser.setPassword(Util.criptString(psswd));
    }

    tmpUser.setPartner(
    (Partner) getObjectById(Partner.class, partner));
    tmpUser.setComment(comment);
    Transaction tx = null;
    try {
    tx = hibSess.beginTransaction();
    hibSess.update(tmpUser);
    tx.commit();
    return true;
    } catch (Exception e) {
    if (tx != null) {
    tx.rollback();
    }
    throw e;
    } finally {
    closeHibSession(); //needs before forward
    }
    }
    return false;
    }

    public boolean deleteUser(HttpServletRequest request) throws Exception {
    String sysId = request.getParameter("sysId");
    User myObj = (User) getObjectById(User.class, sysId);
    if (currentUser == null || myObj == null) {
    return false;
    }
    if (myObj.getId().equals(currentUser.getId())) {
    currentUser = null;
    }
    Transaction tx = null;
    try {
    tx = hibSess.beginTransaction();
    hibSess.delete(myObj);
    tx.commit();
    return true;
    } catch (Exception e) {
    if (tx != null) {
    tx.rollback();
    }
    throw e;
    } finally {
    closeHibSession(); //needs before forward
    }
    }
     */

// my version, with userTypeID and initials
    public int addUser(String usId, String usDescr, String logName,
            String usPassw, int usRights, int userTypeID, String initials, String comment, String lastName, String email) throws Exception {

        HashMap uniques = new HashMap();
        uniques.put("loginName", "'" + logName + "'");
        int rezult = countKey(User.class, uniques);

        //Println used to be here
        if (rezult == 0) {

            User tmpUser = new User();
            tmpUser.setCreateDate(new Date());
            tmpUser.setModifDate(new Date());
            tmpUser.setVisibleName(usDescr);
            tmpUser.setLoginName(logName);
            tmpUser.setRights(usRights);
            tmpUser.setUserTypeID(userTypeID);
            tmpUser.setInitials(initials);
            tmpUser.setLastName(lastName);
            tmpUser.setEmail(email);
            
//        tmpUser.setPassword(usPassw);
            String psswd = usPassw.trim();
            if (psswd.equals("")) {
                throw new Exception(getMyMessage(9));
            }
            tmpUser.setPassword(Util.criptString(psswd));

            tmpUser.setComment(comment);
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpUser);
                tx.commit();

                User tmp2 = currentUser;
                currentUser = tmpUser;
                // set up container contents metadata for user to default for their user group
                vContainerContentM.resetMetaDataRows();
                vContainerContentM.refreshContainerContentMetaData();
                currentUser = tmp2;

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }
// my version, with userTypeID and initials

    public User addUserGetUser(String usId, String usDescr, String logName,
            String usPassw, int usRights, int userTypeID, String initials, String comment) throws Exception {

        User tmpUser = new User();
        tmpUser.setCreateDate(new Date());
        tmpUser.setModifDate(new Date());
        tmpUser.setVisibleName(usDescr);
        tmpUser.setLoginName(logName);
        tmpUser.setRights(usRights);
        tmpUser.setUserTypeID(userTypeID);
        tmpUser.setInitials(initials);
//        tmpUser.setPassword(usPassw);
        String psswd = usPassw.trim();
        if (psswd.equals("")) {
            throw new Exception(getMyMessage(9));
        }
        tmpUser.setPassword(Util.criptString(psswd));

        tmpUser.setComment(comment);
        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.save(tmpUser);
            tx.commit();

            User tmp2 = currentUser;
            currentUser = tmpUser;
            // set up container contents metadata for user to default for their user group
            vContainerContentM.resetMetaDataRows();
            vContainerContentM.refreshContainerContentMetaData();
            currentUser = tmp2;

            return tmpUser;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            closeHibSession(); //needs before forward
        }
    }

// my version, with userTypeID and initials
    public int updateUser(String usId, String usDescr, String logName,
            String usPassw, int usRights, int userTypeID, String initials, String comment, String lastName, String email) throws Exception {
        if (currentUser == null) {
            return 1;
        }

        HashMap uniques = new HashMap();
        uniques.put("loginName", "'" + logName + "'");
        int rezult = (countKey(User.class, uniques) <= 1) ? 0 : 3;
        //Println used to be here

        if (rezult == 0) {

            User tmpUser = (User) getObjectById(User.class, usId);
            if (tmpUser != null) {
                tmpUser.setModifDate(new Date());
                tmpUser.setVisibleName(usDescr);
                tmpUser.setLoginName(logName);
                tmpUser.setRights(usRights);
                tmpUser.setUserTypeID(userTypeID);
                tmpUser.setInitials(initials);
                tmpUser.setLastName(lastName);
                tmpUser.setEmail(email);
//            tmpUser.setPassword(usPassw);
                String psswd = usPassw;
                if (psswd != null) {
                    psswd = psswd.trim();
                    if (psswd.equals("")) {
                        throw new Exception(getMyMessage(9));
                    }
                    tmpUser.setPassword(Util.criptString(psswd));
                }

                tmpUser.setComment(comment);
                Transaction tx = null;
                try {
                    tx = hibSess.beginTransaction();
                    hibSess.update(tmpUser);
                    tx.commit();

                    User tmp2 = currentUser;
                    currentUser = tmpUser;
                    // set up container contents metadata for user to default for their user group
                    vContainerContentM.resetMetaDataRows();
                    vContainerContentM.refreshContainerContentMetaData();
                    currentUser = tmp2;

                } catch (Exception e) {
                    if (tx != null) {
                        tx.rollback();
                    }
                    throw e;
                } finally {
                    closeHibSession(); //needs before forward
                }
            }
        }
        return rezult;
    }

//---MetaData------------------------------------------
    public boolean addMetaData(Class cl,
            HttpServletRequest request) throws Exception {
        String visname = request.getParameter("visname");
        String shortname = request.getParameter("shortname");
        String longname = request.getParameter("longname");
        String colnum = request.getParameter("colnum");
        String sortable = request.getParameter("sortable");
        String editable = request.getParameter("editable");
        String showInr = request.getParameter("showInr");
        String comment = request.getParameter("comment");
        MetaData tmpObj = (MetaData) cl.newInstance();
        tmpObj.setShortName(shortname);
        tmpObj.setLongName(longname);
        tmpObj.setViewColumnNumber(Util.getInteger(colnum));
        tmpObj.setSortable(Boolean.valueOf(sortable));
        tmpObj.setEditable(Boolean.valueOf(editable));
        tmpObj.setShowInReports(Boolean.valueOf(showInr));
        return addPersistent(tmpObj, visname, comment);
    }

    public boolean updateMetaData(Class cl,
            HttpServletRequest request) throws Exception {
        String sysId = request.getParameter("sysId");
        String visname = request.getParameter("visname");
        String shortname = request.getParameter("shortname");
        String longname = request.getParameter("longname");
        String colnum = request.getParameter("colnum");
        String sortable = request.getParameter("sortable");
        String editable = request.getParameter("editable");
        String showInr = request.getParameter("showInr");
        String comment = request.getParameter("comment");

        MetaData tmpObj = (MetaData) getObjectById(cl, sysId);
        tmpObj.setShortName(shortname);
        tmpObj.setLongName(longname);
        tmpObj.setViewColumnNumber(Util.getInteger(colnum));
        tmpObj.setSortable(Boolean.valueOf(sortable));
        tmpObj.setEditable(Boolean.valueOf(editable));
        tmpObj.setShowInReports(Boolean.valueOf(showInr));
        return updatePersistent(tmpObj, visname, comment);
    }

//---Persistent--------------------------------------
    public boolean notUsedBy(Class cls, String fld, Persistent obj) {
        Long cnt = null;
        try {
            cnt = (Long) hibSess.createQuery(
                    "select count(*) from " + cls.getName()
                    + " obj where obj." + fld
                    + "='" + obj.getId().toString() + "'").iterate().next();
        } catch (Exception ex) {
        }
        return (cnt == null || cnt.intValue() == 0);
    }

    public boolean notProcessedByMe(Class cls, User obj) {
        Long cnt = null;
        try {
            cnt = (Long) hibSess.createQuery(
                    "select count(*) from " + cls.getName() + " obj where obj.creator = '" + obj.getId().toString() + "' or obj.modifier = '" + obj.getId().toString() + "'").iterate().next();
        } catch (Exception ex) {
        }
        
        return (cnt == null || cnt.intValue() == 0);
    }

//---ShippedTo-------------------------------------------
//---ShippedTo-------------------------------------------
    public int checkShippedToId(String shipId) {
        setInvalidField("shipId");
        //Println used to be here
        if (shipId == null || shipId.trim().equals("")) {
            return 2;
        }
        try {
            Query q = hibSess.createQuery("select count(*) from ShippedTo ship" + " where ship.id = :shipId");
            q.setString("shipId", shipId);
            //Println used to be here
            Long cnt = (Long) q.iterate().next();
            //Println used to be here
            if (cnt.intValue() > 0) {
                return 3;
            }
            clearInvalidField();
        } catch (Exception ex) {
            //Println used to be here
            return 1;
        }
        return 0;
    }

    public int addShippedTo(
            String shipId, String description, String sortOrder) throws Exception {
        int rezult = 0;
        //Println used to be here
        if (currentUser == null) {
            return 1;
        }

        HashMap uniques = new HashMap();
        uniques.put("description", "'" + description + "'");
        rezult = countKey(ShippedTo.class, uniques);

        //Println used to be here
        if (rezult == 0) {
            ShippedTo tmpShippedTo = new ShippedTo();
            tmpShippedTo.setCreateInfo(currentUser);
            tmpShippedTo.setDescription(description);
            tmpShippedTo.setShippedToID(shipId);
            tmpShippedTo.setSortOrder(sortOrder);
            //Println used to be here
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpShippedTo);
                tx.commit();
                currentShippedTo = tmpShippedTo;

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public int updateShippedTo(
            String shipId, String description, String sortOrder) throws Exception {
        int rezult = 0;
        //Println used to be here
        //Println used to be here
        if (currentUser == null) {
            return 1;
        }
        ShippedTo tmpShippedTo = getCurrentShippedTo();
        
        if (tmpShippedTo == null) {
            return 1;
        }
        //Println used to be here
        //Println used to be here
        //Println used to be here
        if (!shipId.equals(tmpShippedTo.getId().toString())) {
            return 1;
        }

        HashMap uniques = new HashMap();
        uniques.put("description", "'" + description + "'");
        //only thing that can be updates is description, which needs to be unique
        rezult = ((countKey(Cohort.class, uniques) == 0) ? 0 : 3);

        //Println used to be here
        if (rezult == 0) {
            tmpShippedTo.setModifInfo(currentUser);
            tmpShippedTo.setShippedToID(shipId);
            tmpShippedTo.setDescription(description);
            tmpShippedTo.setSortOrder(sortOrder);

            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.update(tmpShippedTo);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public int deleteShippedTo(String selfId) throws Exception {
        if (currentUser == null) {
            return 1;
        }
        ShippedTo tmpShippedTo = getCurrentShippedTo();
        if (tmpShippedTo == null) {
            return 1;
        }
        if (!selfId.equals(tmpShippedTo.getId().toString())) {
            return 1;
        }

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.delete(tmpShippedTo);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            currentShippedTo = null;
            closeHibSession(); //needs before forward
        }
        return 0;
    }

//---Freezer-------------------------------------------
//---Freezer-------------------------------------------
    public int checkFreezerDescription(String freezerDesc) {
        setInvalidField("freezDesc");

        if (freezerDesc == null || freezerDesc.trim().equals("")) {
            return 2;
        }
        try {
            Query q = hibSess.createQuery("select count(*) from Freezer freez" + " where freez.description = :freezerDesc");
            q.setString("freezerDesc", freezerDesc);

            Long cnt = (Long) q.iterate().next();

            if (cnt.intValue() > 0) {
                return 3;
            }
            clearInvalidField();
        } catch (Exception ex) {
            return 1;
        }
        return 0;
    }

    public int checkFreezerId(String freezID) {
        setInvalidField("freezID");
        //Println used to be here
        if (freezID == null || freezID.trim().equals("")) {
            return 2;
        }
        try {
            Query q = hibSess.createQuery("select count(*) from Freezer freez" + " where freez.id = :freezID");
            q.setString("freezID", freezID);
            //Println used to be here
            Long cnt = (Long) q.iterate().next();
            //Println used to be here
            if (cnt.intValue() > 0) {
                return 3;
            }
            clearInvalidField();
        } catch (Exception ex) {
            //Println used to be here
            return 1;
        }
        return 0;
    }

    public int addFreezer(
            String freezerID, String description, String location,
            String temperature, String sortOrder) throws Exception {
        int rezult = 0;
        System.out.println("In addFreezer, freezerID= " + freezerID + ", description=" + description + ", "
                + "location=" + location + ", sortOrder=" + sortOrder + ".");
        if (currentUser == null) {
            return 1;
        }

        HashMap uniques = new HashMap();
        uniques.put("description", "'" + description + "'");
        rezult = countKey(Freezer.class, uniques);

        //Println used to be here
        if (rezult == 0) {
            Freezer tmpFreezer = new Freezer();
            tmpFreezer.setCreateInfo(currentUser);
            //tmpFreezer.setFreezerID(freezerID); cant set this its set by the database
            tmpFreezer.setDescription(description);
            tmpFreezer.setLocation(location);
            tmpFreezer.setTemperature(temperature);
            if (sortOrder != null && sortOrder.length() > 0) {
                try {
                    Integer tmp = Integer.parseInt(sortOrder);
                    tmpFreezer.setSortOrder(tmp);
                } catch (NumberFormatException e) {
                }
            }
            //Println used to be here
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpFreezer);
                tx.commit();
                currentFreezer = tmpFreezer;

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public int updateFreezer(
            String freezerID, String description, String location,
            String temperature, String sortOrder) throws Exception {
        int rezult = 0;
        //Println used to be here
        //Println used to be here
        if (currentUser == null) {
            return 1;
        }
        Freezer tmpFreezer = getCurrentFreezer();
        
        if (tmpFreezer == null) {
            return 1;
        }
        //Println used to be here
        //Println used to be here
        //Println used to be here
        if (!freezerID.equals(tmpFreezer.getId().toString())) {
            return 1;
        }

        HashMap uniques = new HashMap();
        uniques.put("description", "'" + description + "'");
        //only thing that needs to be unique is description
        rezult = ((countKey(Cohort.class, uniques) == 0) ? 0 : 3);

        //Println used to be here
        if (rezult == 0) {
            tmpFreezer.setModifInfo(currentUser);
            tmpFreezer.setFreezerID(Long.parseLong(freezerID));
            tmpFreezer.setDescription(description);
            tmpFreezer.setLocation(location);
            tmpFreezer.setTemperature(temperature);
            if (sortOrder != null && sortOrder.length() > 0) {
                try {
                    Integer tmp = Integer.parseInt(sortOrder);
                    tmpFreezer.setSortOrder(tmp);
                } catch (NumberFormatException e) {
                }
            }

            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.update(tmpFreezer);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public int deleteFreezer(String selfId) throws Exception {
        if (currentUser == null) {
            return 1;
        }
        Freezer tmpFreezer = getCurrentFreezer();
        if (tmpFreezer == null) {
            return 1;
        }
        if (!selfId.equals(tmpFreezer.getId().toString())) {
            return 1;
        }

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.delete(tmpFreezer);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            currentShippedTo = null;
            currentFreezer = null;

            closeHibSession(); //needs before forward
        }
        return 0;
    }

    public void setCurrentShippedTo(String shipId) throws HibernateException {
        currentShippedTo = (ShippedTo) getObjectById(ShippedTo.class, shipId);
    }

    public void setCurrentFreezer(String freezId) throws HibernateException {
        currentFreezer = (Freezer) getObjectById(Freezer.class, freezId);
    }

    public ShippedTo getCurrentShippedTo() {
        return currentShippedTo;
    }

    public Freezer getCurrentFreezer() {
        return currentFreezer;
    }

    public Long getCurrentShippedToId() {
        return (currentShippedTo == null) ? null : currentShippedTo.getId();
    }

    public Long getCurrentFreezerId() {
        return (currentFreezer == null) ? null : currentFreezer.getId();
    }

//----------------------------------------------------------//
//----------------------------------------------------------//
    private int checkValue(String value, String valueName) {
        int result = 0;
        try {
            if (!value.trim().equals("")) {
                result = 7;
                setInvalidField(valueName);
                double xx = Double.parseDouble(value);
                result = 0;
                clearInvalidField();
            }
        } catch (Exception ex) {
        }
        return result;
    }

    private int checkValueAndYear(String value1, String value2, String year,
            String valueName1, String valueName2, String yearName) {
        int result = 0;
        try {
            if (!year.trim().equals("")
                    || !value1.trim().equals("") || !value2.trim().equals("")) {
                result = 7;
                setInvalidField(valueName1);
                if (!value1.trim().equals("") || !value2.trim().equals("")) {
                    String v = (value1.trim().equals("")) ? "0" : value1;
                    int vv = Integer.parseInt(v);
                    setInvalidField(valueName2);
                    v = (value2.trim().equals("")) ? "0" : value2;
                    vv = Integer.parseInt(v);
                    result = 5;
                    setInvalidField(yearName);
                    vv = Integer.parseInt(year);
                    if (vv > 1900 && vv < 2100) {
                        result = 0;
                        clearInvalidField();
                    }
                }
            }
        } catch (Exception ex) {
        }
        return result;
    }

    public Date getDate(String year) {
        Date myDate = null;
        if (!year.trim().equals("")) {
            try {
                int yy = Integer.parseInt(year);
                int mm = 1;
                int dd = 1;
                if (yy > 1900 && yy < 2100) {
                    Calendar myCal = Calendar.getInstance();
                    myCal.set(yy, mm - 1, dd);
                    myDate = myCal.getTime();
                }
            } catch (Exception ex) {
            }
        }
        return myDate;
    }

    public Date getDate(String year, String month, String day,
            String yearName, String monthName, String dayName) {
        Date myDate = null;
        try {
            setInvalidField(yearName);
            int yy = Integer.parseInt(year);
            if (yy > 1900 && yy < 2100) {
                setInvalidField(monthName);
                int mm = Integer.parseInt(month);
                if (mm > 0 && mm < 13) {
                    setInvalidField(dayName);
                    int dd = Integer.parseInt(day);
                    if (dd > 0 & dd < 32) {
                        Calendar myCal = Calendar.getInstance();
                        myCal.set(yy, mm - 1, dd);
                        myDate = myCal.getTime();
                        clearInvalidField();
                    }
                }
            }
        } catch (Exception ex) {
        }
        return myDate;
    }

    public Date getDate(String year, String month, String day,
            String hour, String minute,
            String yearName, String monthName, String dayName,
            String hourName, String minuteName) {
        Date myDate = null;
        try {
            setInvalidField(yearName);
            int yy = Integer.parseInt(year);
            if (yy > 1900 && yy < 2100) {
                setInvalidField(monthName);
                int mm = Integer.parseInt(month);
                if (mm > 0 && mm < 13) {
                    setInvalidField(dayName);
                    int dd = Integer.parseInt(day);
                    if (dd > 0 & dd < 32) {
                        setInvalidField(hourName);
                        int hh = Integer.parseInt(hour);
                        if (hh >= 0 & hh < 24) {
                            setInvalidField(minuteName);
                            int mi = Integer.parseInt(minute);
                            if (mi >= 0 & mi < 60) {
                                Calendar myCal = Calendar.getInstance();
                                myCal.set(yy, mm - 1, dd);
                                myCal.set(Calendar.HOUR_OF_DAY, hh);
                                myCal.set(Calendar.MINUTE, mi);
                                myDate = myCal.getTime();
                                clearInvalidField();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        }
        return myDate;
    }

    public void clearInvalidField() {
        invalidField = null;
    }

    public void setInvalidField(String fieldName) {
        invalidField = fieldName;
    }

    public String getFieldId(String fieldName) {
        return (fieldName.equals(invalidField)) ? "id=\"kluda\"" : "";
    }
//----------------------------------------------------------//
//----------------------------------------------------------//

    public File getTempFile() { //used by RequestParser
        tempFile = new File(qualityDirectory, httpSessId);
        return tempFile;
    }

    public void deleteTempFile() {
        if (tempFile != null) {
            tempFile.delete();
            tempFile = null;
        }
    }

    public void renameTempFile(String fileName) {
        if (tempFile != null) {
            File permFile = new File(qualityDirectory, fileName);
            permFile.delete();
            tempFile.renameTo(permFile);
            tempFile = null;
        }
    }

    public void deletePermFile(String fileName) {
        File permFile = new File(qualityDirectory, fileName);
        permFile.delete();
    }
//----------------------------------------------------------//

    public int currentUserTypeID() {
        return ((currentUser != null) ? currentUser.getUserTypeID() : null);
    }

    public boolean isAdminSession() {
        return (currentUser != null && currentUser.getUserTypeID() == 0);
        //return (currentUser != null && currentUser.getRights().charAt(1) > '0');
    }

    public boolean isWetLabSession() {
        return (currentUser != null && currentUser.getUserTypeID() == 2);
    }

    public boolean isDryLabSession() {
        return (currentUser != null && currentUser.getUserTypeID() == 1);
    }

    public boolean isWriteAdminSession() {
        //return (currentUser != null && currentUser.getRights().charAt(1) > '1');
        return (currentUser != null && currentUser.getRights() <= 10);
    }

    public boolean isPowerAdminSession() {
        //return (currentUser != null && currentUser.getRights().charAt(1) > '2');
        return (currentUser != null && currentUser.getRights() <= 30);
    }

    /**
     * check if the user is an admin user
     * or any user type with at least the rights of an
     * admin user
     * @return
     */
    public boolean isTechUser() {
        if (currentUser == null) {
            return false;
        }
        if (currentUser.getUserTypeID() == 3 || currentUser.getUserTypeID() == 4) {
            return true;
        }
        return false;
    }

    /**
     * check if the user is an admin user
     * or any user type with at least the rights of an
     * admin user
     * @return
     */
    public boolean isAdminUp() {
        if (currentUser == null) {
            return false;
        }
        if (currentUser.getRights() <= 10) {
            return true;
        }
        return false;
    }

    /**
     * check if the user is a wet lab power user
     * or any user type with at least the rights of an
     * wet lab power user,
     * a dry lab power user will not be accepted
     * @return
     */
    public boolean isPowerUserWetUp() {
        if (currentUser == null) {
            return false;
        }
        if (currentUser.getRights() <= 20) {
            return true;
        }
        return false;
    }

    /**
     * check if the user is a dry lab power user
     * or any user type with at least the rights of an
     * dry lab power user
     * a wet lab power user will not be accepted
     * @return
     */
    public boolean isPowerUserDryUp() {
        if (currentUser == null) {
            return false;
        }
        if (currentUser.getRights() <= 30 && currentUser.getRights() != 20) {
            return true;
        }
        return false;
    }

    /**
     * check if the user is a power user
     * or any user type with at least the rights of an
     * power user
     * both wet and dry power users are accepted
     * @return
     */
    public boolean isPowerUserUp() {
        if (currentUser == null) {
            return false;
        }
        if (currentUser.getRights() <= 30) {
            return true;
        }
        return false;
    }

    /**
     * check if the user is a wet lab advanced user
     * or any user type with at least the rights of an
     * wet lab advanced user
     * a dry lab power user will not be accepted
     * @return
     */
    public boolean isAdvancedUserWetUp() {
        if (currentUser == null) {
            return false;
        }
        if (currentUser.getRights() <= 40) {
            return true;
        }
        return false;
    }

    /**
     * check if the user is a dry lab advanced user
     * or any user type with at least the rights of an
     * dry lab advanced user
     * a wet lab power user will not be accepted
     * @return
     */
    public boolean isAdvancedUserDryUp() {
        if (currentUser == null) {
            return false;
        }
        if (currentUser.getRights() <= 50 && currentUser.getRights() != 40) {
            return true;
        }
        return false;
    }

    /**
     * check if the user is an advanced user
     * or any user type with at least the rights of an
     * advanced user
     * both wet and dry advanced users are accepted
     * @return
     */
    public boolean isAdvancedUserUp() {
        if (currentUser == null) {
            return false;
        }
        if (currentUser.getRights() < 60) {
            return true;
        }
        return false;
    }

//----------------------------------------------------------//
    public ViewShippedToManager getViewShippedToManager() {
        return vhm;
    }

    public ViewFreezerManager getViewFreezerManager() {
        return vfm;
    }
//---ContainerType-------------------------------------------
//---ContainerType-------------------------------------------
    private ContainerType currentContainerType = null;
    private String containertypeSortCol = "sortOrder";
    private boolean containertypeSortDir = true; //true - asc; false - desc
    private ViewContainerTypeManager vContainerTypeM = null;

    public int checkContainerTypeId(String contTypeId) {
        setInvalidField("contTypeId");
        //Println used to be here
        if (contTypeId == null || contTypeId.trim().equals("")) {
            return 2;
        }
        try {
            Query q = hibSess.createQuery("select count(*) from ContainerType Con " + " where Con.id= :contTypeId");
            q.setString("contTypeId", contTypeId);
            //Println used to be here
            Long cnt = (Long) q.iterate().next();
            //Println used to be here
            if (cnt.intValue() > 0) {
                return 3;
            }
            clearInvalidField();
        } catch (Exception ex) {
            //Println used to be here
            return 1;
        }
        return 0;
    }

    public int addContainerType(String containerTypeID, String description, String rows, String columns, String sortOrder) throws Exception {
        int rezult = 0;
        //Println used to be here

        if (currentUser == null) {
            return 1;
        }

        HashMap uniques = new HashMap();
        uniques.put("description", "'" + description + "'");
        rezult = countKey(ContainerType.class, uniques);

        //Println used to be here
        if (rezult == 0) {
            ContainerType tmpContainerType = new ContainerType();
            tmpContainerType.setCreateInfo(currentUser);
            tmpContainerType.setContainerTypeID(containerTypeID);
            tmpContainerType.setDescription(description);
            tmpContainerType.setRows(rows);
            tmpContainerType.setColumns(columns);
            tmpContainerType.setSortOrder(sortOrder);

            //Println used to be here
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpContainerType);
                tx.commit();
                currentContainerType = tmpContainerType;

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public int updateContainerType(String containerTypeID, String description, String rows, String columns, String sortOrder) throws Exception {
        int rezult = 0;
        //Println used to be here

        //Println used to be here
        if (currentUser == null) {
            return 1;
        }
        ContainerType tmpContainerType = getCurrentContainerType();
        
        if (tmpContainerType == null) {
            return 1;
        }
        //Println used to be here
        //Println used to be here
        //Println used to be here
        if (!containerTypeID.equals(tmpContainerType.getId().toString())) {
            return 1;
        }

        HashMap uniques = new HashMap();
        uniques.put("description", "'" + description + "'");
        //only thing that needs to be unique is description
        rezult = ((countKey(Cohort.class, uniques) == 0) ? 0 : 3);

        //Println used to be here
        if (rezult == 0) {
            tmpContainerType.setModifInfo(currentUser);
            tmpContainerType.setContainerTypeID(containerTypeID);
            tmpContainerType.setDescription(description);
            tmpContainerType.setRows(rows);
            tmpContainerType.setColumns(columns);
            tmpContainerType.setSortOrder(sortOrder);

            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.update(tmpContainerType);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public int deleteContainerType(String selfId) throws Exception {
        if (currentUser == null) {
            return 1;
        }
        ContainerType tmpContainerType = getCurrentContainerType();
        if (tmpContainerType == null) {
            return 1;
        }
        if (!selfId.equals(tmpContainerType.getId().toString())) {
            return 1;
        }

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.delete(tmpContainerType);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            currentContainerType = null;
            /*          currentShippedTo = null;
            currentAliquot = null;
            currentSample = null;
            currentPerson = null;
             */
            closeHibSession(); //needs before forward
        }
        return 0;
    }

    public ViewContainerTypeManager getViewContainerTypeManager() {
        return vContainerTypeM;
    }

    public ContainerType getCurrentContainerType() {
        return currentContainerType;
    }

    public Long getCurrentContainerTypeId() {
        return (currentContainerType == null) ? null : currentContainerType.getId();
    }

    public List getAllContainerTypes(int startPosition) throws
            HibernateException {
        String orderClause = " order by myContainerType."
                + ((containertypeSortDir) ? containertypeSortCol : containertypeSortCol + " desc");
        String prefix = "select myContainerType from ContainerType as myContainerType";
        Query q = hibSess.createQuery(prefix + orderClause);
        if (viewLimit > 0 && startPosition >= 0) {
            q.setFirstResult(startPosition);
            q.setMaxResults(viewLimit);
        }
        ////Println used to be here
        ////Println used to be here

        return q.list();
    }

    public ContainerType getContainerType(String containerDescription) throws
            HibernateException {
        Query q = hibSess.createQuery("from ContainerType Con " + " where Con.description= :containerDescription");
        q.setString("containerDescription", containerDescription);
        if (q.list().isEmpty()) {
            return null;
        } else {
            return (ContainerType) q.iterate().next();
        }


    }

    public int getAllContainerTypesCount() throws HibernateException {
        String prefix = "select count(*) from ContainerType";
        Query q;
        q = hibSess.createQuery(prefix);
        //Println used to be here
        int count = ((Long) q.iterate().next()).intValue();
        //Println used to be here
        //Println used to be here

        return count;
    }

    public void setContainerTypeSortCol(String sortCol) {
        if (containertypeSortCol.equals(sortCol)) {
            containertypeSortDir = !containertypeSortDir;
        } else {
            containertypeSortCol = sortCol;
            containertypeSortDir = true;
        }
    }

    public String getContainerTypeSortId(String sortCol) {
        return (!containertypeSortCol.equals(sortCol)) ? "normal" : (containertypeSortDir) ? "normalu" : "normalo";
    }

    public void setCurrentContainerType(String contTypeId) throws HibernateException {
        currentContainerType = (ContainerType) getObjectById(ContainerType.class, contTypeId);
    }
//---Cohort-------------------------------------------
//---Cohort-------------------------------------------
    private Cohort currentCohort = null;
    private String cohortSortCol = "sortOrder";
    private boolean cohortSortDir = true; //true - asc; false - desc
    private ViewCohortManager vCohortM = null;

    public int checkCohortId(String cohoId) {
        setInvalidField("cohoId");
        //Println used to be here
        if (cohoId == null || cohoId.trim().equals("")) {
            return 2;
        }
        try {
            Query q = hibSess.createQuery("select count(*) from Cohort Coh " + " where Coh.id= :cohoId");
            q.setString("cohoId", cohoId);
            //Println used to be here
            Long cnt = (Long) q.iterate().next();
            //Println used to be here
            if (cnt.intValue() > 0) {
                return 3;
            }
            clearInvalidField();
        } catch (Exception ex) {
            //Println used to be here
            return 1;
        }
        return 0;
    }

    public int addCohort(String cohortID, String description, String sortOrder) throws Exception {
        int rezult = 0;
        //Println used to be here

        if (currentUser == null) {
            return 1;
        }

        HashMap uniques = new HashMap();
        uniques.put("description", "'" + description + "'");
        rezult = countKey(Cohort.class, uniques);

        //Println used to be here
        if (rezult == 0) {
            Cohort tmpCohort = new Cohort();
            tmpCohort.setCreateInfo(currentUser);
            tmpCohort.setCohortID(cohortID);
            tmpCohort.setDescription(description);
            tmpCohort.setSortOrder(sortOrder);

            //Println used to be here
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpCohort);
                tx.commit();
                currentCohort = tmpCohort;

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public int updateCohort(String cohortID, String description, String sortOrder) throws Exception {
        int rezult = 0;
        //Println used to be here

        //Println used to be here
        if (currentUser == null) {
            return 1;
        }
        Cohort tmpCohort = getCurrentCohort();
        
        if (tmpCohort == null) {
            return 1;
        }
        //Println used to be here
        //Println used to be here
        //Println used to be here
        if (!cohortID.equals(tmpCohort.getId().toString())) {
            return 1;
        }

        HashMap uniques = new HashMap();
        uniques.put("description", "'" + description + "'");
        //only thing that can be updates is description, which needs to be unique
        rezult = ((countKey(Cohort.class, uniques) == 0) ? 0 : 3);

        //Println used to be here
        if (rezult == 0) {
            tmpCohort.setModifInfo(currentUser);
            tmpCohort.setCohortID(cohortID);
            tmpCohort.setDescription(description);
            tmpCohort.setSortOrder(sortOrder);

            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.update(tmpCohort);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public int deleteCohort(String selfId) throws Exception {
        if (currentUser == null) {
            return 1;
        }
        Cohort tmpCohort = getCurrentCohort();
        if (tmpCohort == null) {
            return 1;
        }
        if (!selfId.equals(tmpCohort.getId().toString())) {
            return 1;
        }

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.delete(tmpCohort);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            currentCohort = null;
            /*          currentShippedTo = null;
            currentAliquot = null;
            currentSample = null;
            currentPerson = null;
             */
            closeHibSession(); //needs before forward
        }
        return 0;
    }

    public ViewCohortManager getViewCohortManager() {
        return vCohortM;
    }

    public Cohort getCurrentCohort() {
        return currentCohort;
    }

    public Long getCurrentCohortId() {
        return (currentCohort == null) ? null : currentCohort.getId();
    }

    public Cohort getCohort(String cohortID) throws HibernateException {
        openHibSession();
        Query q = hibSess.createQuery("from Cohort c where c.cohortID=:cohortID");
        q.setString("cohortID", cohortID);
        if (q.list().isEmpty()) {
            return null;
        } else {
            return (Cohort) q.iterate().next();
        }

        ////Println used to be here
        ////Println used to be here
    }

    public List getAllCohorts(int startPosition) throws
            HibernateException {
        String orderClause = " order by myCohort."
                + ((cohortSortDir) ? cohortSortCol : cohortSortCol + " desc");
        String prefix = "select myCohort from Cohort as myCohort";
        Query q = hibSess.createQuery(prefix + orderClause);
        if (viewLimit > 0 && startPosition >= 0) {
            q.setFirstResult(startPosition);
            q.setMaxResults(viewLimit);
        }
        ////Println used to be here
        ////Println used to be here

        return q.list();
    }

    public int getAllCohortsCount() throws HibernateException {
        String prefix = "select count(*) from Cohort as myCohort";
        Query q;
        q = hibSess.createQuery(prefix);
        int count = ((Long) q.iterate().next()).intValue();
        return count;
    }

    public void setCohortSortCol(String sortCol) {
        if (cohortSortCol.equals(sortCol)) {
            cohortSortDir = !cohortSortDir;
        } else {
            cohortSortCol = sortCol;
            cohortSortDir = true;
        }
    }

    public String getCohortSortId(String sortCol) {
        return (!cohortSortCol.equals(sortCol)) ? "normal" : (cohortSortDir) ? "normalu" : "normalo";
    }

    public void setCurrentCohort(String cohoId) throws HibernateException {
        currentCohort = (Cohort) getObjectById(Cohort.class, cohoId);
    }
//---Container-------------------------------------------
//---Container-------------------------------------------
    private Container currentContainer = null;
    private String containerSortCol = "containerName";
    private boolean containerSortDir = true; //true - asc; false - desc
    private ViewContainerManager vContainerM = null;

    public int checkContainerId(String contId) {
        setInvalidField("contId");
        //Println used to be here
        if (contId == null || contId.trim().equals("")) {
            return 2;
        }
        try {
            if (!hibSess.isOpen()) {
                openHibSession();
            }
            Query q = hibSess.createQuery("select count(*) from Container Con " + " where Con.id= :contId");
            q.setString("contId", contId);
            //Println used to be here
            Long cnt = (Long) q.iterate().next();
            //Println used to be here
            if (cnt.intValue() > 0) {
                return 3;
            }
            clearInvalidField();
        } catch (Exception ex) {
            //Println used to be here
            return 1;
        }
        return 0;
    }

    public int checkContainerName(String contName) {
        openHibSession();
        setInvalidField("contName");
        if (contName == null || contName.trim().equals("")) {
            return 2;
        }
        try {
            Query q = hibSess.createQuery("select count(*) from Container Con " + " where Con.containerName = :contName");
            q.setString("contName", contName);
            Long cnt = (Long) q.iterate().next();
            if (cnt.intValue() > 0) {
                return 3;
            }
            clearInvalidField();
        } catch (Exception ex) {
            return 1;
        }
        return 0;
    }

    /**
     * makes a new container
     * @param containerID
     * @param containerName
     * @param containerType
     * @param freezer
     * @param shelf
     * @param discarded
     * @param comments
     * @param isStock
     * @param valid
     * @param containerAlias
     * @param lot
     * @param initials
     * @param dateOnContainer
     * @param location
     * @param barcode
     * @return 0 if successful, 3 if container exists (ConstraintViolationException error)
     * @throws Exception
     */
    public int addContainer(String containerID, String containerName, ContainerType containerType, Freezer freezer, String shelf, String discarded, String comments, String isStock, String valid, String containerAlias, String lot, String initials, java.util.Date dateOnContainer, String location, String barcode) throws Exception {
        int rezult = 0;
        ////Println used to be here

        if (currentUser == null) {
            return 1;
        }
        
        HashMap uniques = new HashMap();
        //uniques.put("containerID", containerID); //container id is generated on the insert by the DB
        uniques.put("containerName", "'" + containerName + "'");
        rezult = countKey(Container.class, uniques);

        //Println used to be here
        if (rezult == 0) {
        
        Container tmpContainer = new Container();
        tmpContainer.setCreateInfo(currentUser);
        //tmpContainer.setContainerID(containerID);
        tmpContainer.setContainerID("-1");
        tmpContainer.setcontainerName(containerName);
        tmpContainer.setContainerType(containerType);
        tmpContainer.setFreezer(freezer);
        tmpContainer.setShelf(shelf);
        tmpContainer.setDiscarded(discarded);
        tmpContainer.setComments(comments);
        tmpContainer.setIsStock(isStock);
        tmpContainer.setValid(valid);
        tmpContainer.setContainerAlias(containerAlias);
        tmpContainer.setLot(lot);
        tmpContainer.setLot("1");
        tmpContainer.setInitials(initials);
        tmpContainer.setDateOnContainer(dateOnContainer);
        tmpContainer.setLocation(location);
        tmpContainer.setBarcode(barcode);

        //Println used to be here
        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.save(tmpContainer);
            tx.commit();
            currentContainer = tmpContainer;

            //Println used to be here
            //Println used to be here
            //Println used to be here
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            if (e.getClass().equals(org.hibernate.exception.ConstraintViolationException.class)) {
                //Println used to be here
//                e.printStackTrace();
                return 3;
            }
            throw e;
        } finally {
            closeHibSession(); //needs before forward
            openHibSession();
        }
        }
//            rezult = addShippingTrack("-1", currentContainer.getContainerID(), shippedOut, shippedDate, shippedTo);
        return rezult;
    }

    public List getContainerByBarcode(String barcode) throws HibernateException {
        openHibSession();
        Query q = null;
        if (barcode != null || !barcode.equals("")) {
            q = hibSess.createQuery("select myContainer from Container as myContainer"
                    + "where myContainer.barcode = " + barcode);
        }
        if (q != null) {
            return q.list();
        }
        return null;
    }

    public ArrayList addShippingInfoList(HttpServletRequest request) throws Exception {
        ArrayList<String> errorArray = new ArrayList();
        String ayear = request.getParameter("ayear");
        String amonth = request.getParameter("amonth");
        String aday = request.getParameter("aday");
        String shipmentName = request.getParameter("shipmentName");
        String shipAction = request.getParameter("shippedOut");
        String shippedToID = request.getParameter("shippedToID");
        String comment = request.getParameter("comment");
        if (shipmentName.equals("") || shipmentName == null) {
            errorArray.add("Shipment name required!");
            return errorArray;
        }
        Date shippedDate = getDate(ayear, amonth, aday,
                "ayear", "amonth", "aday");
        if (shippedDate == null) {
            errorArray.add("The date specified is not a valid date.");
            return errorArray;
        }
        if (shipAction == null || shipAction.equals("")
                || shippedToID == null || shippedToID.equals("")) {
            errorArray.add("The impossible has happened. Call me.");
            return errorArray;
        }
        ShippedTo shippedTo = (ShippedTo) getObjectById(ShippedTo.class, shippedToID);
        writeLockCurrentShoppingList();
        boolean searched = (isFilterList()
                || isSearchList()) ? true : false;
        ArrayList items = (searched) ? new ArrayList(getContainersListSearched())
                : new ArrayList(getAllContainerObjectsInList());
        if (items.isEmpty() || items.isEmpty()) {
            releaseLockCurrentShoppingList();
            return items;
        }
        if (shipAction.equals("1")) {
            this.addShipment("-1", shipmentName, shippedDate, shippedTo, shipAction, comment);
        } else if (shipAction.equals("2")) {
            this.addShipment("-1", shipmentName, shippedDate, shippedTo, shipAction, comment);
        }
        //iterator to iterate through items
        Iterator iter = items.iterator();
        Container container;
        while (iter.hasNext()) {
            container = (Container) iter.next();
            if (shipAction.equals("1") && checkIfShipped(container)) {
                errorArray.add(container.getcontainerName() + " was already shipped.");
                continue;
            }
            if (shipAction.equals("2") && !checkIfShipped(container)) {
                errorArray.add(container.getcontainerName() + " wasn't shipped.");
                continue;
            }
            this.addShipCont("-1", currentShipment, container);
            currentContainer = container;
        }
        closeHibSession(); //needs before forward
        releaseLockCurrentShoppingList();
        return errorArray;
    }

    public ArrayList addShippingInfoChecked(HttpServletRequest request) throws HibernateException, Exception {
        ArrayList<String> errorArray = new ArrayList();
        String ayear = request.getParameter("ayear");
        String amonth = request.getParameter("amonth");
        String aday = request.getParameter("aday");
        String shipmentName = request.getParameter("shipmentName");
        String shipAction = request.getParameter("shippedOut");
        String shippedToID = request.getParameter("shippedToID");
        String comment = request.getParameter("comment");

        if (shipmentName.equals("") || shipmentName == null) {
            errorArray.add("Shipment name required!");
            return errorArray;
        }

        Date shippedDate = getDate(ayear, amonth, aday,
                "ayear", "amonth", "aday");

        if (shippedDate == null) {
            errorArray.add("The date specified is not a valid date.");
            return errorArray;
        }

        if (shipAction == null || shipAction.equals("")
                || shippedToID == null || shippedToID.equals("")) {
            errorArray.add("The impossible has happened. Call me.");
            return errorArray;
        }

        ShippedTo shippedTo = (ShippedTo) getObjectById(ShippedTo.class, shippedToID);
        String containerId;
        Container container;
        Map valueMap = request.getParameterMap();
        String[] checkValues = (String[]) valueMap.get("containerListChecked");

        if (checkValues == null) {
            //Println used to be here
            releaseLockCurrentShoppingList();
            return new ArrayList();
        }

        if (shipAction.equals("1")) {
            this.addShipment("-1", shipmentName, shippedDate, shippedTo, shipAction, comment);
        } else if (shipAction.equals("2")) {
            this.addShipment("-1", shipmentName, shippedDate, shippedTo, shipAction, comment);
        }

        ArrayList<String> containerListChecked = new ArrayList(Arrays.asList(checkValues));
        Iterator iter = containerListChecked.iterator();

        while (iter.hasNext()) {
            containerId = iter.next().toString();
            container = (Container) getObjectById(Container.class, containerId);
            if (shipAction.equals("1") && checkIfShipped(container)) {
                errorArray.add(container.getcontainerName() + " was already shipped.");
                continue;
            }
            if (shipAction.equals("2") && !checkIfShipped(container)) {
                errorArray.add(container.getcontainerName() + " wasn't shipped.");
                continue;
            }
            this.addShipCont("-1", currentShipment, container);
            currentContainer = container;
        }
        
        closeHibSession(); //needs before forward
        return errorArray;
    }

    public int updateContainer(String containerID, String containerName, ContainerType containerType, Freezer freezer, String shelf, String discarded, String comments, String isStock, String valid, String containerAlias, String lot, String initials, java.util.Date dateOnContainer, String location, String barcode) throws Exception {
        int rezult = 0;

        //Println used to be here
        if (currentUser == null) {
            return 1;
        }
        Container tmpContainer = (Container) getObjectById(Container.class, containerID);
        /*Container tmpContainer = getCurrentContainer();
        
        if (tmpContainer == null) {
        return 1;
        }
        //Println used to be here
        //Println used to be here
        //Println used to be here
        if (!containerID.equals(tmpContainer.getId().toString())) {
        return 1;
        }
         * */

        HashMap uniques = new HashMap();
        uniques.put("containerName", "'" + containerName + "'");
        rezult = ((countKey(Container.class, uniques) <= 1) ? 0 : 3);

        //Println used to be here
        if (rezult == 0) {
            tmpContainer.setModifInfo(currentUser);
            tmpContainer.setContainerID(containerID);
            tmpContainer.setcontainerName(containerName);
            tmpContainer.setContainerType(containerType);
            tmpContainer.setFreezer(freezer);
            tmpContainer.setShelf(shelf);
            tmpContainer.setDiscarded(discarded);
            tmpContainer.setComments(comments);
            tmpContainer.setIsStock(isStock);
            tmpContainer.setValid(valid);
            tmpContainer.setContainerAlias(containerAlias);
            tmpContainer.setLot(lot);
            tmpContainer.setInitials(initials);
            tmpContainer.setDateOnContainer(dateOnContainer);
            tmpContainer.setLocation(location);
            tmpContainer.setBarcode(barcode);

            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.update(tmpContainer);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public int updateContainerLocation(String contName, Freezer freezer, String shelf, String location) throws Exception {
        int rezult = 0;
        if (currentUser == null) {
            return 1;
        }
        Container tempCont = getContainer(contName);
        if (freezer == null) {
            freezer = tempCont.getFreezer();
        }
        if (shelf.equals("-1")) {
            shelf = tempCont.getShelf();
        }
        if (location == null) {
            location = tempCont.getLocation();
        }
        HashMap uniques = new HashMap();
        uniques.put("containerName", "'" + contName + "'");
        rezult = ((countKey(Container.class, uniques) == 1) ? 0 : 3);
        if (rezult == 0) {
            tempCont.setLocation(location);
            tempCont.setShelf(shelf);
            tempCont.setFreezer(freezer);
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.update(tempCont);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public int deleteContainer(String selfId) throws Exception {
        if (currentUser == null) {
            return 1;
        }
        Container tmpContainer = getCurrentContainer();
        if (tmpContainer == null) {
            return 1;
        }
        if (!selfId.equals(tmpContainer.getId().toString())) {
            return 1;
        }

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.delete(tmpContainer);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            currentContainer = null;
            /*          currentShippedTo = null;
            currentAliquot = null;
            currentSample = null;
            currentPerson = null;
             */
            closeHibSession(); //needs before forward
        }
        return 0;
    }

    public ViewContainerManager getViewContainerManager() {
        return vContainerM;
    }

    public Container getCurrentContainer() {
        return currentContainer;
    }

    public Long getCurrentContainerId() {
        return (currentContainer == null) ? null : currentContainer.getId();
    }

    public Container getContainer(String containerName) throws HibernateException {
        openHibSession();
        Query q = hibSess.createQuery("from Container Con " + " where Con.containerName= :containerName");
        q.setString("containerName", containerName);
        if (q.list().isEmpty()) {
            return null;
        } else {
            return (Container) q.iterate().next();
        }
    }

    /**
     * checks if a container contains contaminated contents
     * @param containerID
     * @return true if it has contaminated contents or false if not
     */
    public boolean containsContaminated(String containerID) {
        openHibSession();
        String prefix = "select myContainerContent.contaminated from ContainerContent as myContainerContent "
                + "where myContainerContent.container.containerID = " + containerID
                + " order by contaminated desc";
        Query q = hibSess.createQuery(prefix);
        List conts = q.list();

        Iterator iter = conts.iterator();
        String cc = null;
        while (iter.hasNext()) {
            cc = (String) iter.next();
            if (cc.equals("1")) {
                return true;
            }
        }
        return false;
    }

    public ContainerContent getContainerContentsFromSampId(String sampId) throws HibernateException {
        openHibSession();
        Query q = hibSess.createQuery("select myContainerContent from ContainerContent as myContainerContent"
                + " where myContainerContent.sample.sampleID = " + sampId);
        if (q.list().isEmpty()) {
            return null;
        } else {
            return (ContainerContent) q.iterate().next();
        }
    }

    public String getHistRootParent() {
        return histRootParent;
    }

    public List<Sample> getAllAmplifiedSamples(String sampId) throws HibernateException {
        openHibSession();
        Query q = hibSess.createQuery("select mySample from Sample as mySample"
                + " where mySample.parent.sampleID = " + sampId);
        if (q.list().isEmpty()) {
            return null;
        } else {
            return q.list();
        }
    }

    public Sample getSample(Subject subject, String sampleName) throws HibernateException {
        openHibSession();
        Query q = hibSess.createQuery("from Sample sam where sam.subject.subjectID=:subjectID and  sam.sampleName= :sampleName");
        q.setString("subjectID", subject.getSubjectID());
        q.setString("sampleName", sampleName);
        if (q.list().isEmpty()) {
            return null;
        } else {
            return (Sample) q.iterate().next();
        }
    }

    public Sample getSample(String sampleName) throws HibernateException {
        openHibSession();
        Query q = hibSess.createQuery("from Sample sam where sam.sampleName= :sampleName");
        q.setString("sampleName", sampleName);
        if (q.list().isEmpty()) {
            return null;
        } else {
            return (Sample) q.iterate().next();
        }
    }

    public Snp getSnp(String rsNumber) throws HibernateException {
        openHibSession();
        Query q = hibSess.createQuery("from Snp snp where snp.rsNumber=:rsNumber");
        q.setString("rsNumber", rsNumber);
        if (q.list().isEmpty()) {
            return null;
        } else {
            return (Snp) q.iterate().next();
        }
    }

    public Subject getSubject(Integer subjectID) throws HibernateException {
        openHibSession();
        Query q = hibSess.createQuery("from Subject sub where sub.subjectID= :subjectID");
        q.setString("subjectID", subjectID.toString());
        if (q.list().isEmpty()) {
            return null;
        } else {
            return (Subject) q.iterate().next();
        }
    }

    public SampleType getSampleType(String description) throws HibernateException {
        openHibSession();
        Query q = hibSess.createQuery("from SampleType st where st.description= :description");
        q.setString("description", description);
        if (q.list().isEmpty()) {
            return null;
        } else {
            return (SampleType) q.iterate().next();
        }
    }

    public Subject getSubject(Cohort cohort, String ID) throws HibernateException {
        openHibSession();
        Query q = hibSess.createQuery("from Subject sub where sub.subjectName=:subjectName and sub.cohort.cohortID=:cohortid");
        q.setString("subjectName", ID);
        q.setString("cohortid", cohort.getCohortID());
        if (q.list().isEmpty()) {
            return null;
        } else {
            return (Subject) q.iterate().next();
        }
    }

    public List getAllContainers(int startPosition) throws
            HibernateException {
        openHibSession();
        String sort = (containerSortCol.equals("containerType")) ? "contType.description" + ((containerSortDir) ? "" : " desc") + ", myContainer.containerName"
                : (containerSortCol.equals("freezer")) ? "free.description" + ((containerSortDir) ? "" : " desc") + ", myContainer.containerName"
                : (containerSortCol.equals("shippedTo")) ? "ship.description" + ((containerSortDir) ? "" : " desc") + ", myContainer.containerName"
                : "myContainer." + containerSortCol;
        String orderClause = " order by "
                + ((containerSortDir) ? sort : sort + " desc");

        String prefix = "select distinct myContainer, free, contType  from Container as myContainer"
                + " left outer join fetch myContainer.freezer as free"
                + " left outer join fetch myContainer.containerType as contType";
        String limit = (filter != null) ? filter.getContainerString() : "";
        String searchStr = (search == null) ? "" : search.getContainerString();
        //Println used to be here

        Query q = hibSess.createQuery(prefix + limit + searchStr + orderClause);
        if (viewLimit > 0 && startPosition >= 0) {
            q.setFirstResult(startPosition);
            q.setMaxResults(viewLimit);
        }
        //Println used to be here
        ////Println used to be here

        return q.list();
    }

    public List getAllContainers(int startPosition, String shipId) throws
            HibernateException {
        openHibSession();
        String sort = (containerSortCol.equals("containerType")) ? "contType.description" + ((containerSortDir) ? "" : " desc") + ", myContainer.containerName"
                : (containerSortCol.equals("freezer")) ? "free.description" + ((containerSortDir) ? "" : " desc") + ", myContainer.containerName"
                : (containerSortCol.equals("shippedTo")) ? "ship.description" + ((containerSortDir) ? "" : " desc") + ", myContainer.containerName"
                : "myContainer." + containerSortCol;
        String orderClause = " order by "
                + ((containerSortDir) ? sort : sort + " desc");

        String prefix = "select distinct myContainer, free, contType  from Container as myContainer, ShipCont as myShipCont"
                + " left outer join fetch myContainer.freezer as free"
                + " left outer join fetch myContainer.containerType as contType";
        cancelFilter();
        cancelSearch();
        String limit = " where myContainer.containerID = myShipCont.container.containerID AND myShipCont.shipment.shipmentID = " + shipId;

        Query q = hibSess.createQuery(prefix + limit + orderClause);
        if (viewLimit > 0 && startPosition >= 0) {
            q.setFirstResult(startPosition);
            q.setMaxResults(viewLimit);
        }
        //Println used to be here
        ////Println used to be here

        return q.list();
    }

    public int getAllContainersCount() throws HibernateException {
        openHibSession();
        String prefix = "select count(distinct myContainer)  from Container as myContainer";

        String limit = (filter != null) ? filter.getContainerString() : "";
        String searchStr = (search == null) ? "" : search.getContainerString();

        //Println used to be here
        Query q = hibSess.createQuery(prefix + limit + searchStr);
        int count = ((Long) q.iterate().next()).intValue();
        //Println used to be here
        return count;
    }

    public int getAllContainersCount(String shipId) throws HibernateException {
        openHibSession();
        String prefix = "select count(distinct myContainer)  from Container as myContainer, ShipCont as myShipCont";
        cancelFilter();
        cancelSearch();
        String limit = " where myContainer.containerID = myShipCont.container.containerID AND myShipCont.shipment.shipmentID = " + shipId;

        //Println used to be here
        Query q = hibSess.createQuery(prefix + limit);
        int count = ((Long) q.iterate().next()).intValue();
        //Println used to be here
        return count;
    }

    public void setContainerSortCol(String sortCol) {
        if (containerSortCol.equals(sortCol)) {
            containerSortDir = !containerSortDir;
        } else {
            containerSortCol = sortCol;
            containerSortDir = true;
        }
    }

    public String getContainerSortId(String sortCol) {
        return (!containerSortCol.equals(sortCol)) ? "normal" : (containerSortDir) ? "normalu" : "normalo";
    }

    public void setCurrentContainer(String contId) throws HibernateException {
        currentContainer = (Container) getObjectById(Container.class, contId);
    }
//---SampleProcess-------------------------------------------
//---SampleProcess-------------------------------------------
    private SampleProcess currentSampleProcess = null;
    private String sampleprocessSortCol = "sortOrder";
    private boolean sampleprocessSortDir = true; //true - asc; false - desc
    private ViewSampleProcessManager vSampleProcessM = null;

    public int checkSampleProcessId(String sampProcId) {
        openHibSession();
        setInvalidField("sampProcId");
        //Println used to be here
        if (sampProcId == null || sampProcId.trim().equals("")) {
            return 2;
        }
        try {
            Query q = hibSess.createQuery("select count(*) from SampleProcess Sam " + " where Sam.id= :sampProcId");
            q.setString("sampProcId", sampProcId);
            //Println used to be here
            Long cnt = (Long) q.iterate().next();
            //Println used to be here
            if (cnt.intValue() > 0) {
                return 3;
            }
            clearInvalidField();
        } catch (Exception ex) {
            //Println used to be here
            return 1;
        }
        return 0;
    }

    public int addSampleProcess(String sampleProcessID, String description, String sortOrder) throws Exception {
        int rezult = 0;
        //Println used to be here

        if (currentUser == null) {
            return 1;
        }

        HashMap uniques = new HashMap();
        uniques.put("description", "'" + description + "'");
        rezult = countKey(SampleProcess.class, uniques);

        //Println used to be here
        if (rezult == 0) {
            SampleProcess tmpSampleProcess = new SampleProcess();
            tmpSampleProcess.setCreateInfo(currentUser);
            tmpSampleProcess.setSampleProcessID(sampleProcessID);
            tmpSampleProcess.setDescription(description);
            tmpSampleProcess.setSortOrder(sortOrder);

            //Println used to be here
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpSampleProcess);
                tx.commit();
                currentSampleProcess = tmpSampleProcess;

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public int updateSampleProcess(String sampleProcessID, String description, String sortOrder) throws Exception {
        int rezult = 0;
        //Println used to be here

        //Println used to be here
        if (currentUser == null) {
            return 1;
        }
        SampleProcess tmpSampleProcess = getCurrentSampleProcess();
        
        if (tmpSampleProcess == null) {
            return 1;
        }
        //Println used to be here
        //Println used to be here
        //Println used to be here
        if (!sampleProcessID.equals(tmpSampleProcess.getId().toString())) {
            return 1;
        }


        HashMap uniques = new HashMap();
        uniques.put("description", "'" + description + "'");
        //only thing that can be updates is description, which needs to be unique
        rezult = ((countKey(Cohort.class, uniques) == 0) ? 0 : 3);

        //Println used to be here
        if (rezult == 0) {
            tmpSampleProcess.setModifInfo(currentUser);
            tmpSampleProcess.setSampleProcessID(sampleProcessID);
            tmpSampleProcess.setDescription(description);
            tmpSampleProcess.setSortOrder(sortOrder);

            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.update(tmpSampleProcess);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public int deleteSampleProcess(String selfId) throws Exception {
        if (currentUser == null) {
            return 1;
        }
        SampleProcess tmpSampleProcess = getCurrentSampleProcess();
        if (tmpSampleProcess == null) {
            return 1;
        }
        if (!selfId.equals(tmpSampleProcess.getId().toString())) {
            return 1;
        }

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.delete(tmpSampleProcess);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            currentSampleProcess = null;
            /*          currentShippedTo = null;
            currentAliquot = null;
            currentSample = null;
            currentPerson = null;
             */
            closeHibSession(); //needs before forward
        }
        return 0;
    }

    public ViewSampleProcessManager getViewSampleProcessManager() {
        return vSampleProcessM;
    }

    public SampleProcess getCurrentSampleProcess() {
        return currentSampleProcess;
    }

    public Long getCurrentSampleProcessId() {
        return (currentSampleProcess == null) ? null : currentSampleProcess.getId();
    }

    public List getAllSampleProcesss(int startPosition) throws
            HibernateException {
        openHibSession();
        String orderClause = " order by mySampleProcess."
                + ((sampleprocessSortDir) ? sampleprocessSortCol : sampleprocessSortCol + " desc");
        String prefix = "select mySampleProcess from SampleProcess as mySampleProcess";
        Query q = hibSess.createQuery(prefix + orderClause);
        if (viewLimit > 0 && startPosition >= 0) {
            q.setFirstResult(startPosition);
            q.setMaxResults(viewLimit);
        }
        ////Println used to be here
        ////Println used to be here

        return q.list();
    }

    public int getAllSampleProcesssCount() throws HibernateException {
        openHibSession();
        String prefix = "select count(*) from SampleProcess as mySampleProcess";
        Query q;
        q = hibSess.createQuery(prefix);
        int count = ((Long) q.iterate().next()).intValue();
        return count;
    }

    public void setSampleProcessSortCol(String sortCol) {
        if (sampleprocessSortCol.equals(sortCol)) {
            sampleprocessSortDir = !sampleprocessSortDir;
        } else {
            sampleprocessSortCol = sortCol;
            sampleprocessSortDir = true;
        }
    }

    public String getSampleProcessSortId(String sortCol) {
        return (!sampleprocessSortCol.equals(sortCol)) ? "normal" : (sampleprocessSortDir) ? "normalu" : "normalo";
    }

    public void setCurrentSampleProcess(String sampProcId) throws HibernateException {
        currentSampleProcess = (SampleProcess) getObjectById(SampleProcess.class, sampProcId);
    }
//---SampleType-------------------------------------------
//---SampleType-------------------------------------------
    private SampleType currentSampleType = null;
    private String sampletypeSortCol = "sortOrder";
    private boolean sampletypeSortDir = true; //true - asc; false - desc
    private ViewSampleTypeManager vSampleTypeM = null;

    public int checkSampleTypeId(String sampTypId) {
        openHibSession();
        setInvalidField("sampTypId");
        //Println used to be here
        if (sampTypId == null || sampTypId.trim().equals("")) {
            return 2;
        }
        try {
            Query q = hibSess.createQuery("select count(*) from SampleType Sam " + " where Sam.id= :sampTypId");
            q.setString("sampTypId", sampTypId);
            //Println used to be here
            Long cnt = (Long) q.iterate().next();
            //Println used to be here
            if (cnt.intValue() > 0) {
                return 3;
            }
            clearInvalidField();
        } catch (Exception ex) {
            //Println used to be here
            return 1;
        }
        return 0;
    }

    public int addSampleType(String sampleTypeID, String description, String sortOrder) throws Exception {
        int rezult = 0;
        //Println used to be here

        if (currentUser == null) {
            return 1;
        }
        HashMap uniques = new HashMap();
        uniques.put("description", "'" + description + "'");
        rezult = countKey(SampleType.class, uniques);

        //Println used to be here
        if (rezult == 0) {
            SampleType tmpSampleType = new SampleType();
            tmpSampleType.setCreateInfo(currentUser);
            tmpSampleType.setSampleTypeID(sampleTypeID);
            tmpSampleType.setDescription(description);
            tmpSampleType.setSortOrder(sortOrder);

            //Println used to be here
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpSampleType);
                tx.commit();
                currentSampleType = tmpSampleType;

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public int updateSampleType(String sampleTypeID, String description, String sortOrder) throws Exception {
        int rezult = 0;
        //Println used to be here

        //Println used to be here
        if (currentUser == null) {
            return 1;
        }
        SampleType tmpSampleType = getCurrentSampleType();
        
        if (tmpSampleType == null) {
            return 1;
        }
        //Println used to be here
        //Println used to be here
        //Println used to be here
        if (!sampleTypeID.equals(tmpSampleType.getId().toString())) {
            return 1;
        }

        HashMap uniques = new HashMap();
        uniques.put("description", "'" + description + "'");
        //only thing that can be updates is description, which needs to be unique
        rezult = ((countKey(Cohort.class, uniques) == 0) ? 0 : 3);

        //Println used to be here
        if (rezult == 0) {
            tmpSampleType.setModifInfo(currentUser);
            tmpSampleType.setSampleTypeID(sampleTypeID);
            tmpSampleType.setDescription(description);
            tmpSampleType.setSortOrder(sortOrder);

            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.update(tmpSampleType);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public int deleteSampleType(String selfId) throws Exception {
        if (currentUser == null) {
            return 1;
        }
        SampleType tmpSampleType = getCurrentSampleType();
        if (tmpSampleType == null) {
            return 1;
        }
        if (!selfId.equals(tmpSampleType.getId().toString())) {
            return 1;
        }

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.delete(tmpSampleType);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            currentSampleType = null;
            /*          currentShippedTo = null;
            currentAliquot = null;
            currentSample = null;
            currentPerson = null;
             */
            closeHibSession(); //needs before forward
        }
        return 0;
    }

    public ViewSampleTypeManager getViewSampleTypeManager() {
        return vSampleTypeM;
    }

    public SampleType getCurrentSampleType() {
        return currentSampleType;
    }

    public Long getCurrentSampleTypeId() {
        return (currentSampleType == null) ? null : currentSampleType.getId();
    }

    public List getAllSampleTypes(int startPosition) throws
            HibernateException {
        openHibSession();
        String orderClause = " order by mySampleType."
                + ((sampletypeSortDir) ? sampletypeSortCol : sampletypeSortCol + " desc");
        String prefix = "select mySampleType from SampleType as mySampleType";
        Query q = hibSess.createQuery(prefix + orderClause);
        if (viewLimit > 0 && startPosition >= 0) {
            q.setFirstResult(startPosition);
            q.setMaxResults(viewLimit);
        }
        ////Println used to be here
        ////Println used to be here

        return q.list();
    }

    public int getAllSampleTypesCount() throws HibernateException {
        openHibSession();
        String prefix = "select count(*) from SampleType as mySampleType";
        Query q;
        q = hibSess.createQuery(prefix);
        int count = ((Long) q.iterate().next()).intValue();
        return count;
    }

    public void setSampleTypeSortCol(String sortCol) {
        if (sampletypeSortCol.equals(sortCol)) {
            sampletypeSortDir = !sampletypeSortDir;
        } else {
            sampletypeSortCol = sortCol;
            sampletypeSortDir = true;
        }
    }

    public String getSampleTypeSortId(String sortCol) {
        return (!sampletypeSortCol.equals(sortCol)) ? "normal" : (sampletypeSortDir) ? "normalu" : "normalo";
    }

    public void setCurrentSampleType(String sampTypId) throws HibernateException {
        currentSampleType = (SampleType) getObjectById(SampleType.class, sampTypId);
    }
//---Subject-------------------------------------------
//---Subject-------------------------------------------
    private Subject currentSubject = null;
    private String subjectSortCol = "subjectName";
    private boolean subjectSortDir = true; //true - asc; false - desc
    private ViewSubjectManager vSubjectM = null;

    public int checkSubjectId(String subjId) {
        openHibSession();
        setInvalidField("subjId");
        //Println used to be here
        if (subjId == null || subjId.trim().equals("")) {
            return 2;
        }
        try {
            Query q = hibSess.createQuery("select count(*) from Subject Sub " + " where Sub.id= :subjId");
            q.setString("subjId", subjId);
            //Println used to be here
            Long cnt = (Long) q.iterate().next();
            //Println used to be here
            if (cnt.intValue() > 0) {
                return 3;
            }
            clearInvalidField();
        } catch (Exception ex) {
            //Println used to be here
            return 1;
        }
        return 0;
    }

    public int addSubject(String subjectID, String subjectName, String fatherName, String motherName, Cohort cohort, String gender, String familyID, String hasConsent, Ethnicity ethnicity, String comment) throws Exception {
        int rezult = 0;
        //Println used to be here

        if (currentUser == null) {
            return 1;
        }

        HashMap uniques = new HashMap();
        //uniques.put("subjectID", subjectID);
        uniques.put("subjectName", "\'" + subjectName + "\'");
        uniques.put("cohort.cohortID", cohort.getCohortID());
        rezult = countKey(Subject.class, uniques);

        //Println used to be here
        if (rezult == 0) {
            Subject tmpSubject = new Subject();
            tmpSubject.setCreateInfo(currentUser);
            tmpSubject.setSubjectID(subjectID);
            tmpSubject.setSubjectName(subjectName);
            tmpSubject.setFatherName(fatherName);
            tmpSubject.setMotherName(motherName);
            tmpSubject.setCohort(cohort);
            tmpSubject.setGender(gender);
            tmpSubject.setFamilyID(familyID);
            tmpSubject.setHasConsent(hasConsent);
            tmpSubject.setEthnicity(ethnicity);
            tmpSubject.setComments(comment);

            //Println used to be here
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpSubject);
                tx.commit();
                currentSubject = tmpSubject;

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public int updateSubject(String subjectID, String subjectName, String fatherName, String motherName, Cohort cohort, String gender, String familyID, String hasConsent, Ethnicity ethnicity, String comment) throws Exception {
        int rezult = 0;
        ////Println used to be here

        //Println used to be here
        if (currentUser == null) {
            return 1;
        }
        Subject tmpSubject = (Subject) getObjectById(Subject.class, subjectID);
        
        if (tmpSubject == null) {
            return 1;
        }
        //Println used to be here
        //Println used to be here
        //Println used to be here
        if (!subjectID.equals(tmpSubject.getId().toString())) {
            return 1;
        }

        HashMap uniques = new HashMap();
        uniques.put("subjectName", "\'" + subjectName + "\'");
        uniques.put("cohort.cohortID", cohort.getCohortID());
        rezult = (countKey(Subject.class, uniques) <= 1) ? 0 : 3;

        //Println used to be here
        if (rezult == 0) {
            tmpSubject.setModifInfo(currentUser);
            tmpSubject.setSubjectID(subjectID);
            tmpSubject.setSubjectName(subjectName);
            tmpSubject.setFatherName(fatherName);
            tmpSubject.setMotherName(motherName);
            tmpSubject.setCohort(cohort);
            tmpSubject.setGender(gender);
            tmpSubject.setFamilyID(familyID);
            tmpSubject.setHasConsent(hasConsent);
            tmpSubject.setEthnicity(ethnicity);
            tmpSubject.setComments(comment);

            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.update(tmpSubject);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public int deleteSubject(String selfId) throws Exception {
        if (currentUser == null) {
            return 1;
        }
        Subject tmpSubject = getCurrentSubject();
        if (tmpSubject == null) {
            return 1;
        }
        if (!selfId.equals(tmpSubject.getId().toString())) {
            return 1;
        }

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.delete(tmpSubject);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            currentSubject = null;
            /*          currentShippedTo = null;
            currentAliquot = null;
            currentSample = null;
            currentPerson = null;
             */
            closeHibSession(); //needs before forward
        }
        return 0;
    }

    public ViewSubjectManager getViewSubjectManager() {
        return vSubjectM;
    }

    public Subject getCurrentSubject() {
        return currentSubject;
    }

    public String getCurrentSubjectId() {
        return (currentSubject == null) ? null : currentSubject.getSubjectID();
    }

    public List getAllSubjects(int startPosition) throws
            HibernateException {
        openHibSession();
        String sort =
                (subjectSortCol.equals("cohort")) ? "coho.description" + ((subjectSortDir) ? "" : " desc") + ", mySubject.subjectName"
                : (subjectSortCol.equals("father")) ? "mySubject.fatherName" + ((subjectSortDir) ? "" : " desc") + ", mySubject.subjectName"
                : (subjectSortCol.equals("mother")) ? "mySubject.motherName" + ((subjectSortDir) ? "" : " desc") + ", mySubject.subjectName"
                : "mySubject." + subjectSortCol;
        String orderClause = " order by "
                + ((subjectSortDir) ? sort : sort + " desc");

        // only one should be active at a time
        String limit = (filter != null) ? filter.getSubjectString() : "";
        String searchStr = (search == null) ? "" : search.getSubjectString();

        String prefix = "select distinct mySubject, coho from Subject as mySubject"
                + " join fetch mySubject.cohort as coho";
        Query q = hibSess.createQuery(prefix + limit + searchStr + orderClause);
        if (viewLimit > 0 && startPosition >= 0) {
            q.setFirstResult(startPosition);
            q.setMaxResults(viewLimit);
        }
        //Println used to be here
        ////Println used to be here

        return q.list();
    }

    public int getAllSubjectsCount() throws HibernateException {
        openHibSession();

        // only one should be active at a time
        String limit = (filter != null) ? filter.getSubjectString() : "";
        String searchStr = (search == null) ? "" : search.getSubjectString();

        String prefix = "select count(*) from Subject as mySubject";
        Query q = hibSess.createQuery(prefix + limit + searchStr);
        int count = ((Long) q.iterate().next()).intValue();
        return count;
    }

    public void setSubjectSortCol(String sortCol) {
        if (subjectSortCol.equals(sortCol)) {
            subjectSortDir = !subjectSortDir;
        } else {
            subjectSortCol = sortCol;
            subjectSortDir = true;
        }
    }

    public String getSubjectSortId(String sortCol) {
        return (!subjectSortCol.equals(sortCol)) ? "normal" : (subjectSortDir) ? "normalu" : "normalo";
    }

    public void setCurrentSubject(String subjId) throws HibernateException {
        currentSubject = (Subject) getObjectById(Subject.class, subjId);
    }
//---Sample-------------------------------------------
//---Sample-------------------------------------------
    private Sample currentSample = null;
    private String sampleSortCol = "sampleName";
    private boolean sampleSortDir = true; //true - asc; false - desc
    private ViewSampleManager vSampleM = null;

    public int checkSampleId(String sampId) {
        openHibSession();
        setInvalidField("sampId");
        //Println used to be here
        if (sampId == null || sampId.trim().equals("")) {
            return 2;
        }
        try {
            Query q = hibSess.createQuery("select count(*) from Sample Sam " + " where Sam.id= :sampId");
            q.setString("sampId", sampId);
            //Println used to be here
            Long cnt = (Long) q.iterate().next();
            //Println used to be here
            if (cnt.intValue() > 0) {
                return 3;
            }
            clearInvalidField();
        } catch (Exception ex) {
            //Println used to be here
            return 1;
        }
        return 0;
    }

    public int addSample(String sampleID, String sampleName, String valid, Sample parent, SampleType sampleType, Subject subject, Date collectionDate, Date extractionDate, String comments) throws Exception {
        int rezult = 0;
        //Println used to be here

        if (currentUser == null) {
            return 1;
        }

        HashMap uniques = new HashMap();
        //subjectid, sampleName is unique
        //uniques.put("sampleID", sampleID);
        uniques.put("sampleName", "'" + sampleName + "'");
        rezult = countKey(Sample.class, uniques);

        //Println used to be here
        if (rezult == 0) {
            Sample tmpSample = new Sample();
            tmpSample.setCreateInfo(currentUser);
            tmpSample.setSampleID(sampleID);
            tmpSample.setSampleName(sampleName);
            tmpSample.setValid(valid);
            tmpSample.setParent(parent);
            tmpSample.setSampleType(sampleType);
            tmpSample.setSubject(subject);
            // collection date not nullable, though it should be
            if (collectionDate == null) {
                Calendar myCal = Calendar.getInstance();
                myCal.set(0001, 00, 01);
                collectionDate = myCal.getTime();
            }
            tmpSample.setDateCollected(collectionDate);
            tmpSample.setDateExtracted(extractionDate);
            tmpSample.setComments(comments);

            //Println used to be here
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpSample);
                tx.commit();
                currentSample = tmpSample;

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public int updateSample(String sampleID, String sampleName, String valid, Sample parent, SampleType sampleType, Subject subject, Date collectionDate, Date extractionDate, String comments) throws Exception {
        int rezult = 0;
        ////Println used to be here

        //Println used to be here
        if (currentUser == null) {
            return 1;
        }
        Sample tmpSample = (Sample) getObjectById(Sample.class, sampleID);
        
        if (tmpSample == null) {
            return 1;
        }
        //Println used to be here
        //Println used to be here
        //Println used to be here

        HashMap uniques = new HashMap();
        uniques.put("sampleName", "'" + sampleName + "'");
        rezult = ((countKey(Sample.class, uniques) <= 1) ? 0 : 3);

        //Println used to be here
        if (rezult == 0) {
            tmpSample.setModifInfo(currentUser);
            tmpSample.setSampleID(sampleID);
            tmpSample.setSampleName(sampleName);
            tmpSample.setValid(valid);
            tmpSample.setParent(parent);
            tmpSample.setSampleType(sampleType);
            tmpSample.setSubject(subject);
            // collection date not nullable, though it should be
            if (collectionDate == null) {
                Calendar myCal = Calendar.getInstance();
                myCal.set(0001, 00, 01);
                collectionDate = myCal.getTime();
            }
            tmpSample.setDateCollected(collectionDate);
            tmpSample.setDateExtracted(extractionDate);
            tmpSample.setComments(comments);

            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.update(tmpSample);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public int deleteSample(String selfId) throws Exception {
        if (currentUser == null) {
            return 1;
        }
        Sample tmpSample = getCurrentSample();
        if (tmpSample == null) {
            return 1;
        }
        if (!selfId.equals(tmpSample.getId().toString())) {
            return 1;
        }

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.delete(tmpSample);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            currentSample = null;
            /*          currentShippedTo = null;
            currentAliquot = null;
            currentSample = null;
            currentPerson = null;
             */
            closeHibSession(); //needs before forward
        }
        return 0;
    }

    public ViewSampleManager getViewSampleManager() {
        return vSampleM;
    }

    public Sample getCurrentSample() {
        return currentSample;
    }

    public Long getCurrentSampleId() {
        return (currentSample == null) ? null : currentSample.getId();
    }

    public List getAllSamples(String subjectId, int startPosition) throws
            HibernateException {
        openHibSession();
        String sort =
                (sampleSortCol.equals("subject")) ? "subj.subjectName" + ((sampleSortDir) ? "" : " desc") + ", mySample.sampleName"
                : (sampleSortCol.equals("parent")) ? "par.sampleName " + ((sampleSortDir) ? "" : " desc") + ", mySample.sampleName"
                : (sampleSortCol.equals("sampleType")) ? "samType.description " + ((sampleSortDir) ? "" : " desc") + ", mySample.sampleName"
                : (sampleSortCol.equals("sampleProcess")) ? "samProc.description " + ((sampleSortDir) ? "" : " desc") + ", mySample.sampleName"
                : "mySample." + sampleSortCol;
        String orderClause = " order by "
                + ((sampleSortDir) ? sort : sort + " desc");

        String prefix = "select distinct mySample, subj, par, samType, samProc from Sample as mySample"
                + " left outer join fetch mySample.subject as subj"
                + " left outer join fetch mySample.parent as par"
                + " left outer join fetch mySample.sampleType as samType"
                + " left outer join fetch mySample.sampleProcess as samProc";

        String limit = "";
        // only one search should be active at a time
        if (filter != null || search != null) {
            limit = (filter != null) ? filter.getSampleString() : search.getSampleString();
            if (subjectId != null) {
                limit = limit + " and mySample.subject.subjectID = " + subjectId;
            }
        } else if (subjectId != null) {
            limit = limit + " where mySample.subject.subjectID = " + subjectId;
        }

        Query q = hibSess.createQuery(prefix + limit + orderClause);
        if (viewLimit > 0 && startPosition >= 0) {
            q.setFirstResult(startPosition);
            q.setMaxResults(viewLimit);
        }
        //Println used to be here
        ////Println used to be here

        return q.list();
    }

    public List getShoppingListSamples(Long listID) throws HibernateException {
        openHibSession();
        Query q = hibSess.createQuery("from ShoppingListSample Con " + " where Con.listID= :listID");
        q.setLong("listID", listID);
        return q.list();
    }

    public List getSamplesListSearched() throws
            HibernateException {
        openHibSession();
        Long listID = currentShoppingList.getShoppingList().getId();
        if (currentShoppingList == null || currentShoppingList.getShoppingList() == null) {
            return null;
        }
        String sort =
                (sampleSortCol.equals("subject")) ? "subj.subjectName" + ((sampleSortDir) ? "" : " desc") + ", mySample.sampleName"
                : (sampleSortCol.equals("parent")) ? "par.sampleName " + ((sampleSortDir) ? "" : " desc") + ", mySample.sampleName"
                : (sampleSortCol.equals("sampleType")) ? "samType.description " + ((sampleSortDir) ? "" : " desc") + ", mySample.sampleName"
                : (sampleSortCol.equals("sampleProcess")) ? "samProc.description " + ((sampleSortDir) ? "" : " desc") + ", mySample.sampleName"
                : "mySample." + sampleSortCol;
        String orderClause = " order by "
                + ((sampleSortDir) ? sort : sort + " desc");

        String prefix = "select distinct mySample, subj, par, samType, samProc from Sample as mySample"
                + " left outer join fetch mySample.subject as subj"
                + " left outer join fetch mySample.parent as par"
                + " left outer join fetch mySample.sampleType as samType"
                + " left outer join fetch mySample.sampleProcess as samProc"
                + " , ShoppingListSample as myShoppingList ";

        String suffix = "  mySample.sampleID = myShoppingList.sampleID "
                + " AND mySample.sampleID IN "
                + " (SELECT myShoppingList.sampleID FROM ShoppingListSample as myShoppingList"
                + "  WHERE myShoppingList.listID =:listID) ";

        String limit = "";
        // only one search should be active at a time
        if (filterList != null || searchList != null) {
            // if search term is one of object's fields, only getting a " and x=y" clause
            limit = (filterList != null) ? filterList.getSampleString() : searchList.getSampleString();
            limit = limit + " AND ";
            if (limit.indexOf("where") < 0 && limit.indexOf("WHERE") < 0) {
                suffix = " WHERE " + suffix + limit;
                limit = "";
            }
        } else {
            limit = " WHERE ";
        }

        Query q = hibSess.createQuery(prefix + limit + suffix + orderClause);
        q.setLong("listID", listID);
        List results = q.list();

        //remove duplicates but keep order
        LinkedHashSet set = new LinkedHashSet(results);
        results = new ArrayList(set);
        return results;

    }

    public List getShoppingListSubjects(Long listID) throws HibernateException {
        openHibSession();
        Query q = hibSess.createQuery("from ShoppingListSubject Con " + " where Con.listID= :listID");
        q.setLong("listID", listID);
        return q.list();
    }

    public List getShoppingListContainerContents(Long listID) throws HibernateException {
        openHibSession();
        Query q = hibSess.createQuery("from ShoppingListContainerContent Con " + " where Con.listID= :listID");
        q.setLong("listID", listID);
        return q.list();
    }

    public List getShoppingListContainers(Long listID) throws HibernateException {
        openHibSession();
        Query q = hibSess.createQuery("from ShoppingListContainer Con " + " where Con.listID= :listID");
        q.setLong("listID", listID);
        return q.list();
    }

    public void addShoppingListContainerContent(ShoppingListContainerContent slcc) throws Exception {

        openHibSession();

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.save(slcc);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            closeHibSession(); //needs before forward
        }
    }

    public void addShoppingListSubject(ShoppingListSubject slsu) throws Exception {

        openHibSession();

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.save(slsu);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            closeHibSession(); //needs before forward
        }
    }

    public void addShoppingListContainer(ShoppingListContainer slc) throws Exception {

        openHibSession();

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.save(slc);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            closeHibSession(); //needs before forward
        }
    }

    public void addShoppingListSample(ShoppingListSample sls) throws Exception {

        openHibSession();

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.save(sls);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            closeHibSession(); //needs before forward
        }
    }

    public void deleteShoppingListContainerContent(ShoppingListContainerContent slcc) throws Exception {

        openHibSession();

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.delete(slcc);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            closeHibSession(); //needs before forward
        }
    }

    public void deleteShoppingListContainer(ShoppingListContainer slc) throws Exception {

        openHibSession();

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.delete(slc);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            closeHibSession(); //needs before forward
        }
    }

    public void deleteShoppingListSubject(ShoppingListSubject slsu) throws Exception {

        openHibSession();

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.delete(slsu);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            closeHibSession(); //needs before forward
        }
    }

    public void deleteShoppingListSample(ShoppingListSample sls) throws Exception {

        openHibSession();

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.delete(sls);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            closeHibSession(); //needs before forward
        }
    }

    public int getAllSamplesCount() throws HibernateException {
        openHibSession();
        String prefix = "select count(*) from Sample as mySample";
        Query q = hibSess.createQuery(prefix);
        int count = ((Long) q.iterate().next()).intValue();
        return count;
    }

    public void setSampleSortCol(String sortCol) {
        if (sampleSortCol.equals(sortCol)) {
            sampleSortDir = !sampleSortDir;
        } else {
            sampleSortCol = sortCol;
            sampleSortDir = true;
        }
    }

    public String getSampleSortId(String sortCol) {
        return (!sampleSortCol.equals(sortCol)) ? "normal" : (sampleSortDir) ? "normalu" : "normalo";
    }

    public void setCurrentSample(String sampId) throws HibernateException {
        currentSample = (Sample) getObjectById(Sample.class, sampId);
    }
//---ContainerContent-------------------------------------------
//---ContainerContent-------------------------------------------
    private ContainerContent currentContainerContent = null;
    private String containercontentSortCol = "sample";
    private boolean containercontentSortDir = true; //true - asc; false - desc
    private ViewContainerContentManager vContainerContentM = null;

    public int checkContainerContentId(String contContId) {
        setInvalidField("contContId");
        //Println used to be here
        if (contContId == null || contContId.trim().equals("")) {
            return 2;
        }
        try {
            if (!hibSess.isOpen()) {
                openHibSession();
            }
            Query q = hibSess.createQuery("select count(*) from ContainerContent Con " + " where Con.id= :contContId");
            q.setString("contContId", contContId);
            //Println used to be here
            Integer cnt = ((Long) q.iterate().next()).intValue();
            //Println used to be here
            if (cnt.intValue() > 0) {
                return 3;
            }
            clearInvalidField();
        } catch (Exception ex) {
            //Println used to be here
            return 1;
        }
        return 0;
    }

    public int addControlLayout(String layoutName, String[] controlIDs, String[] rows, String[] columns) throws Exception {
        openHibSession();
        int result = 0;

        if (currentUser == null) {
            return 1;
        }

        if (controlIDs == null) {
            return result;
        }


        for (int i = 0; i < controlIDs.length; i++) {
            if (result == 0) {
                Control control = (Control) getObjectById(Control.class, controlIDs[i]);

                System.out.println("In addControl, control=" + control.getDescription()
                        + " row=" + rows[i] + Util.letterToNum((rows[i]))
                        + " col=" + columns[i]);
                ControlLayoutWell tmpControlLayoutWell = new ControlLayoutWell();
                //tmpControlWell.setCreateInfo(currentUser);
                tmpControlLayoutWell.setLayoutName(layoutName);
                tmpControlLayoutWell.setControl(control);
                tmpControlLayoutWell.setRow(Util.letterToNum((rows[i])));
                tmpControlLayoutWell.setColumn(columns[i]);


                //Println used to be here
                Transaction tx = null;
                try {
                    tx = hibSess.beginTransaction();
                    hibSess.save(tmpControlLayoutWell);
                    tx.commit();

                } catch (Exception e) {
                    if (tx != null) {
                        tx.rollback();
                    }
                    if (e.getClass().equals(org.hibernate.exception.ConstraintViolationException.class)) {
                        //Println used to be here
                        return 3;
                    }
                    throw e;
                } finally {
                    closeHibSession(); //needs before forward
                    openHibSession();
                }
            }
        }
        return result;
    }

    public int addControls(String containerID, String[] controlIDs, String[] rows, String[] columns, String[] volumes) throws Exception {
        openHibSession();
        int result = 0;

        if (currentUser == null) {
            return 1;
        }

        if (controlIDs == null) {
            return result;
        }

        Container container = (Container) getObjectById(Container.class, containerID);

        for (int i = 0; i < controlIDs.length; i++) {
            if (result == 0) {
                Control control = (Control) getObjectById(Control.class, controlIDs[i]);

                System.out.println("In addControl, control=" + control.getDescription()
                        + " container=" + container.getcontainerName()
                        + " row=" + rows[i] + Util.letterToNum((rows[i]))
                        + " col=" + columns[i]);
                ControlWell tmpControlWell = new ControlWell();
                //tmpControlWell.setCreateInfo(currentUser);
                tmpControlWell.setControl(control);
                tmpControlWell.setContainer(container);
                tmpControlWell.setRow(Util.letterToNum((rows[i])));
                tmpControlWell.setColumn(columns[i]);
                if (volumes != null && i < volumes.length && volumes[i] != null && !volumes[i].trim().equals("")) {
                    tmpControlWell.setVolume(volumes[i]);
                }


                //Println used to be here
                Transaction tx = null;
                try {
                    tx = hibSess.beginTransaction();
                    hibSess.save(tmpControlWell);
                    tx.commit();

                } catch (Exception e) {
                    if (tx != null) {
                        tx.rollback();
                    }
                    if (e.getClass().equals(org.hibernate.exception.ConstraintViolationException.class)) {
                        //Println used to be here
                        return 3;
                    }
                    throw e;
                } finally {
                    closeHibSession(); //needs before forward
                    openHibSession();
                }
            }
        }
        return result;
    }

    public int addControlWell(Container container, Control control, String row, String column, String volume) throws Exception {
        openHibSession();
        int rezult = 0;

        if (currentUser == null) {
            return 1;
        }

        //Println used to be here
        if (rezult == 0) {

            ControlWell tmpControlWell = new ControlWell();
            tmpControlWell.setCreateInfo(currentUser);
            tmpControlWell.setControl(control);
            tmpControlWell.setContainer(container);
            tmpControlWell.setRow(Util.letterToNum((row)));
            tmpControlWell.setColumn(column);
            tmpControlWell.setVolume(volume);

            //Println used to be here
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpControlWell);
                tx.commit();

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                if (e.getClass().equals(org.hibernate.exception.ConstraintViolationException.class)) {
                    //Println used to be here
                    return 3;
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
                openHibSession();
            }
        }

        return rezult;
    }

    /**
     * get all wells in a plate that aren't taken up by controls
     * returns wells in order specified, either by "row" or "column"
     * @param container
     * @param order the order to plate in, either by row (order = "row") or by column (order = "column")
     * @return array of coordinates, with ar[x][0]= row number and ar[x][1]=col number
     * or null if container param is null
     */
    public int[][] getAvailableWells(Container container, String order) {

        if (container == null) {
            return null;
        }
        // get wells with controls in them
        // get all control wells in container
        ArrayList controlWells = new ArrayList();
        ArrayList occupiedWells = new ArrayList();
        HashMap matches = new HashMap();
        matches.put("container.containerID", container.getContainerID());
        controlWells = (ArrayList) getObjectsOfClass(ControlWell.class, matches);

        // get their rows and cols
        Iterator iter = controlWells.iterator();
        ControlWell cw = null;
        String row = null;
        String col = null;

        while (iter.hasNext()) {
            cw = (ControlWell) iter.next();
            row = cw.getRow();
            col = cw.getColumn();
            occupiedWells.add("row" + row + "col" + col);
        }

        int rows = new Integer(container.getContainerType().getRows());
        int columns = new Integer(container.getContainerType().getColumns());
        //Println used to be here
        int[][] coor = new int[rows * columns][2];

        //Println used to be here
        int i = 0;
        int r = 1;
        int c = 1;
        if (order.equals("row")) {
            for (r = 1; r <= rows; r++) {
                for (c = 1; c <= columns; c++) {
                    if (!occupiedWells.contains("row" + r + "col" + c)) {
                        coor[i][0] = r;
                        coor[i][1] = c;
                        i++;
                    }
                }
            }
        } else {
            for (c = 1; c <= columns; c++) {
                for (r = 1; r <= rows; r++) {
                    if (!occupiedWells.contains("row" + r + "col" + c)) {
                        coor[i][0] = r;
                        coor[i][1] = c;
                        i++;
                    }
                }
            }
        }

        return coor;

    }

    public int addContainerContent(String containerContentsID, ContainerContent parent, String contaminated, Container container, String row, String column, Sample sample, String volume, String concentration, String dilution, String comments, Date amplificationDate, MaterialType materialType) throws Exception {
        openHibSession();
        int rezult = 0;

        if (currentUser == null) {
            return 1;
        }
        rezult = checkContainerContentId(containerContentsID);

        //Println used to be here
        if (rezult == 0) {
            ContainerContent tmpContainerContent = new ContainerContent();
            tmpContainerContent.setCreateInfo(currentUser);
            tmpContainerContent.setContainerContentsID(containerContentsID);
            tmpContainerContent.setParent(parent);
            tmpContainerContent.setContaminated(contaminated);
            tmpContainerContent.setContainer(container);
            tmpContainerContent.setRow(row);
            tmpContainerContent.setColumn(column);
            tmpContainerContent.setSample(sample);
            tmpContainerContent.setVolume(volume);
            tmpContainerContent.setConcentration(concentration);
            //DILUTION CANNOT BE NULL
            if (dilution == null) {
                dilution = "-1";
            }
            tmpContainerContent.setDilution(dilution);
            tmpContainerContent.setComments(comments);
            tmpContainerContent.setAmplificationDate(amplificationDate);
            tmpContainerContent.setMaterialType(materialType);
            tmpContainerContent.setBarcode(null);

            //Println used to be here
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpContainerContent);
                tx.commit();
                currentContainerContent = tmpContainerContent;

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                if (e.getClass().equals(org.hibernate.exception.ConstraintViolationException.class)) {
                    //Println used to be here
                    return 3;
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
                openHibSession();
            }
        }
        return rezult;
    }

    public int updateContainerContentVolume(String containerContentID, String volume) throws Exception {
        openHibSession();
        int rezult = 0;
        if (currentUser == null) {
            return 1;
        }
        ContainerContent tmpContainerContent = (ContainerContent) getObjectById(ContainerContent.class, containerContentID);
        if (tmpContainerContent == null) {
            return 1;
        }

        if (rezult == 0) {
            tmpContainerContent.setModifInfo(currentUser);
            tmpContainerContent.setVolume(volume);

            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.update(tmpContainerContent);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
                openHibSession();
            }
        }
        return rezult;
    }

    public int updateContainerContentConcentration(String containerContentID, String concen) throws Exception {
        openHibSession();
        int rezult = 0;
        if (currentUser == null) {
            return 1;
        }
        ContainerContent tmpContainerContent = (ContainerContent) getObjectById(ContainerContent.class, containerContentID);
        if (tmpContainerContent == null) {
            return 1;
        }

        if (rezult == 0) {
            tmpContainerContent.setModifInfo(currentUser);
            tmpContainerContent.setConcentration(concen);

            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.update(tmpContainerContent);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
                openHibSession();
            }
        }
        return rezult;
    }

    public int updateContainerContentComments(String containerContentID, String comments) throws Exception {
        openHibSession();
        int rezult = 0;
        if (currentUser == null) {
            return 1;
        }
        ContainerContent tmpContainerContent = (ContainerContent) getObjectById(ContainerContent.class, containerContentID);
        if (tmpContainerContent == null) {
            return 1;
        }

        if (rezult == 0) {
            tmpContainerContent.setModifInfo(currentUser);
            tmpContainerContent.setComments(comments);

            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.update(tmpContainerContent);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
                openHibSession();
            }
        }
        return rezult;
    }

    public int updateContainerContent(String containerContentsID, ContainerContent parent, String contaminated, Container container, String row, String column, Sample sample, String volume, String concentration, String dilution, String comments, Date amplificationDate, MaterialType materialType) throws Exception {
        openHibSession();
        int rezult = 0;
        if (currentUser == null) {
            return 1;
        }
        ContainerContent tmpContainerContent = (ContainerContent) getObjectById(ContainerContent.class, containerContentsID);
        if (tmpContainerContent == null) {
            return 1;
        }
        if (!containerContentsID.equals(tmpContainerContent.getId().toString())) {
            return 1;
        }


        if (rezult == 0) {
            tmpContainerContent.setModifInfo(currentUser);

            tmpContainerContent.setContainerContentsID(containerContentsID);
            tmpContainerContent.setParent(parent);
            tmpContainerContent.setContaminated(contaminated);
            tmpContainerContent.setContainer(container);
            tmpContainerContent.setRow(row);
            tmpContainerContent.setColumn(column);
            tmpContainerContent.setSample(sample);
            //DILUTION CANNOT BE NULL
            if (dilution == null) {
                dilution = "-1";
            }
            tmpContainerContent.setDilution(dilution);
            tmpContainerContent.setVolume(volume);
            tmpContainerContent.setComments(comments);
            tmpContainerContent.setConcentration(concentration);
            tmpContainerContent.setAmplificationDate(amplificationDate);
            tmpContainerContent.setMaterialType(materialType);

            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.update(tmpContainerContent);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                //if(e.getClass().equals(org.hibernate.exception.ConstraintViolationException.class)){
                //   return 3;
                //}
                throw e;
            } finally {
                closeHibSession(); //needs before forward
                openHibSession();
            }
        }
        return rezult;
    }

    public int deleteContainerContent(String selfId) throws Exception {
        if (currentUser == null) {
            return 1;
        }
        ContainerContent tmpContainerContent = getCurrentContainerContent();
        if (tmpContainerContent == null) {
            return 1;
        }
        if (!selfId.equals(tmpContainerContent.getId().toString())) {
            return 1;
        }

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.delete(tmpContainerContent);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            currentContainerContent = null;
            closeHibSession(); //needs before forward
        }
        return 0;
    }

    public ViewContainerContentManager getViewContainerContentManager() {
        return vContainerContentM;
    }

    public ContainerContent getCurrentContainerContent() {
        return currentContainerContent;
    }

    public Long getCurrentContainerContentId() {
        return (currentContainerContent == null) ? null : currentContainerContent.getId();
    }

    public void resetSampleHistoryValues() {
        if (histParents == null) {
            histParents = new ArrayList<String>();
        } else {
            histParents.clear();
        }
        if (histTree == null) {
            histTree = new ArrayList<String>();
        } else {
            histTree.clear();
        }
        histRootParent = null;
    }

    public String getContainerContParent(String conContId) throws HibernateException {
        openHibSession();
        ContainerContent parent = null;
        ContainerContent nextParent;
        String parentString = "-1";
        int count = 0;
        while (!(conContId == null || conContId.equals("-3") || conContId.equals(""))) {
            count ++;
            String getParentQuery = "select myContainerContent from ContainerContent as myContainerContent"
                    + " where myContainerContent.containerContentsID = " + conContId;
            Query q = hibSess.createQuery(getParentQuery);
            parent = (ContainerContent) q.iterate().next();
            if (parent != null) {
                nextParent = parent.getParent();
                if (nextParent != null) {
                    conContId = nextParent.getId().toString();
                } else {
                    conContId = null;
                }
            }
        }
        if (parent != null) {
            parentString = parent.getId().toString();
            histParents.add(parentString);
            histTree.add("-1");
        }
        return parentString;
    }

    public String getSampleParent(String sampId) throws HibernateException {
        openHibSession();
        Sample parent = null;
        Sample nextParent;
        String parentString = "-1";
        while (!(sampId == null || sampId.equals("null") || sampId.equals(""))) {
            String getParentQuery = "select mySample from Sample as mySample"
                    + " where mySample.sampleID = " + sampId;
            Query q = hibSess.createQuery(getParentQuery);
            parent = (Sample) q.iterate().next();
            if (parent != null) {
                nextParent = parent.getParent();
                if (nextParent != null) {
                    sampId = nextParent.getSampleID().toString();
                } else {
                    sampId = null;
                }
            }
        }
        if (parent != null) {
            parentString = parent.getSampleID().toString();
            histParents.add(parentString);
            histTree.add("-1");
        }
        return parentString;
    }

    public void getContainerContChildren(String conContId) throws HibernateException {
        openHibSession();
        ContainerContent child = null;
        Iterator<ContainerContent> iter;
        String getChildQuery = "select myContainerContent from ContainerContent as myContainerContent"
                + " where myContainerContent.parent.containerContentsID = " + conContId;
        Query q = hibSess.createQuery(getChildQuery);
        iter = q.iterate();
        int count = 0;
        while (iter.hasNext()) {
            count ++;
            child = iter.next();
            String childId = child.getId().toString();
            histParents.add(childId);
            ContainerContent parentOf = (ContainerContent) getObjectById(ContainerContent.class, childId);
            histTree.add(Integer.toString(histParents.indexOf((parentOf.getParent().getId().toString()))));
            getContainerContChildren(childId);
        }

    }

    public void getSampleChildren(String sampId) throws HibernateException {
        openHibSession();
        Sample child = null;
        Iterator<Sample> iter;
        String getChildQuery = "select mySample from Sample as mySample"
                + " where mySample.parent.sampleID = " + sampId;
        Query q = hibSess.createQuery(getChildQuery);
        iter = q.iterate();
        while (iter.hasNext()) {
            child = iter.next();
            String childId = child.getId().toString();
            histParents.add(childId);
            Sample parentOf = (Sample) getObjectById(Sample.class, childId);
            histTree.add(Integer.toString(histParents.indexOf((parentOf.getParent().getId().toString()))));
            getSampleChildren(childId);
        }

    }

    public ArrayList<String> getHistTree() {
        return histTree;
    }

    public ArrayList<String> getHistParents() {
        return histParents;
    }

    /**
     * get all containercontents, while considering limits from searches or specific
     * container or subject requests
     * @param startPosition
     * @param containerID
     * @param subjectID
     * @return
     * @throws org.hibernate.HibernateException
     */
    public List getAllContainerContents(int startPosition, String containerID, String subjectID) throws
            HibernateException {
        openHibSession();
        String sort = (containercontentSortCol.equals("container")) ? "myContainerContent.container.containerName" + ((containercontentSortDir) ? "" : " desc") + ", myContainerContent.row"
                : (containercontentSortCol.equals("sample")) ? "myContainerContent.sample.sampleName" + ((containercontentSortDir) ? "" : " desc") + ", myContainerContent.container.containerName"
                : (containercontentSortCol.equals("parent")) ? "parentCont.containerName" + ((containercontentSortDir) ? "" : " desc") + ", myContainerContent.container.containerName"
                : "myContainerContent." + containercontentSortCol;
        String orderClause = " order by "
                + ((containercontentSortDir) ? sort : sort + " desc");

        String prefix = "select myContainerContent,parent,parentCont from ContainerContent as myContainerContent"
                + " left outer join fetch myContainerContent.parent as parent"
                + " left outer join fetch myContainerContent.parent.container as parentCont";

        String limit = "";
        // only one search should be active at a time
        if (filter != null || search != null) {
            limit = (filter != null) ? filter.getContainerContentString() : search.getContainerContentString();
            if (subjectID != null && subjectID.indexOf("null") < 0) {
                limit = limit + " and myContainerContent.sample.subject.subjectID = " + subjectID;
            }
            if (containerID != null && containerID.indexOf("null") < 0) {
                limit = limit + " and myContainerContent.container.containerID = " + containerID;
            }
        } else if (subjectID != null && subjectID.indexOf("null") < 0) {
            limit = limit + " where myContainerContent.sample.subject.subjectID = " + subjectID;
        } else if (containerID != null && containerID.indexOf("null") < 0) {
            limit = limit + " where myContainerContent.container.containerID = " + containerID;
        }

        Query q = hibSess.createQuery(prefix + limit + orderClause);
        if (viewLimit > 0 && startPosition >= 0) {
            q.setFirstResult(startPosition);
            q.setMaxResults(viewLimit);
        }

        return q.list();
    }

    public int getAllContainerContentsCount(String containerID, String subjectID) throws HibernateException {
        openHibSession();

        String prefix = "select count(*) from ContainerContent as myContainerContent";

        String limit = "";
        // only one search should be active at a time
        if (filter != null || search != null) {
            limit = (filter != null) ? filter.getContainerContentString() : search.getContainerContentString();
            if (subjectID != null && !subjectID.equals("null")) {
                limit = limit + " and myContainerContent.sample.subject.subjectID = " + subjectID;
            }
            if (containerID != null && !containerID.equals("null")) {
                limit = limit + " and myContainerContent.container.containerID = " + containerID;
            }
        } else if (subjectID != null && !subjectID.equals("null")) {
            limit = limit + " where myContainerContent.sample.subject.subjectID = " + subjectID;
        } else if (containerID != null && !containerID.equals("null")) {
            limit = limit + " where myContainerContent.container.containerID = " + containerID;
        }

        Query q = hibSess.createQuery(prefix + limit);
        int count = ((Long) q.iterate().next()).intValue();
        return count;
    }

    /**
     * get all containercontentsID, while considering limits from searches or specific
     * container or subject requests and NOT considering view limit
     * @param containerID
     * @param subjectID
     * @return
     * @throws org.hibernate.HibernateException
     */
    public List getAllContainerContentIDsUnlimited(String containerID, String subjectID) throws
            HibernateException {
        openHibSession();


        String prefix = "select myContainerContent.id from ContainerContent as myContainerContent";

        String limit = "";
        // only one search should be active at a time
        if (filter != null || search != null) {
            limit = (filter != null) ? filter.getContainerContentString() : search.getContainerContentString();
            if (subjectID != null && !subjectID.equals("null")) {
                limit = limit + " and myContainerContent.sample.subject.subjectID = " + subjectID;
            }
            if (containerID != null && !containerID.equals("null")) {
                limit = limit + " and myContainerContent.container.containerID = " + containerID;
            }
        } else if (subjectID != null && !subjectID.equals("null")) {
            limit = limit + " where myContainerContent.sample.subject.subjectID = " + subjectID;
        } else if (containerID != null && !containerID.equals("null")) {
            limit = limit + " where myContainerContent.container.containerID = " + containerID;
        }

        Query q = hibSess.createQuery(prefix + limit);
        return q.list();
    }

    public void setContainerContentSortCol(String sortCol) {
        if (containercontentSortCol.equals(sortCol)) {
            containercontentSortDir = !containercontentSortDir;
        } else {
            containercontentSortCol = sortCol;
            containercontentSortDir = true;
        }
    }

    public String getContainerContentSortId(String sortCol) {
        return (!containercontentSortCol.equals(sortCol)) ? "normal" : (containercontentSortDir) ? "normalu" : "normalo";
    }

    public void setCurrentContainerContent(String contContId) throws HibernateException {
        currentContainerContent = (ContainerContent) getObjectById(ContainerContent.class, contContId);
    }

    /**
     * get containerContents of fromID and recreate them for the new container
     * used in cloning container (new container found by name)
     * @param fromID
     * @param toName
     * @param materialType material type of new contents, null if want to use original types
     * @return id of new container or -1 if unsuccessful
     */
    public int copyContents(String fromID, String toName, MaterialType materialType) {
        String contId = "-1";
        try {
            openHibSession();

            ListIterator iter = getContentsOfContainer(fromID).listIterator();

            String prefix = "select distinct myContainer.containerID from Container as myContainer "
                    + "where myContainer.containerName = '" + toName + "'";
            Query q = hibSess.createQuery(prefix);
            //Println used to be here
            contId = q.uniqueResult().toString();

            HashMap h = null;
            ContainerContent toAdd = null;
            int result = 0;
            while (iter.hasNext()) {
                toAdd = (ContainerContent) iter.next();
                h = toAdd.getContainerContentHash();
                // set to new container
                h.remove("container");
                Container cont = (Container) getObjectById(Container.class, contId);
                h.put("container", cont);

                // set to make new cc object
                h.remove("containerContentsID");
                h.put("containerContentsID", "-1");

                // set to make subject of cloning the parent
                h.remove("parent");
                ContainerContent parent = (ContainerContent) getObjectById(ContainerContent.class, toAdd.getContainerContentsID());
                h.put("parent", parent);

                Iterator it = h.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pairs = (Map.Entry) it.next();
                    //Println used to be here
                }
                result = addContainerContent(h.get("containerContentsID").toString(),
                        (ContainerContent) h.get("parent"), h.get("contaminated").toString(),
                        (Container) h.get("container"), h.get("row").toString(),
                        h.get("column").toString(), (Sample) h.get("sample"),
                        h.get("volume").toString(), h.get("concentration").toString(),
                        h.get("dilution").toString(),
                        (h.get("comments") == null
                        || h.get("comments").toString().equalsIgnoreCase("null")
                        || h.get("comments").toString().equalsIgnoreCase(""))
                        ? null : h.get("comments").toString(),
                        ((h.get("amplificationDate") == null) ? null : (Date) h.get("amplificationDate")),
                        ((materialType == null) ? (MaterialType) h.get("materialType") : materialType));
                if (result != 0) {
                    return -1;
                }
            }
        } catch (HibernateException he) {
            System.err.println("Hibernate error in cloning. " + he.getLocalizedMessage());
            return -1;
        } catch (NullPointerException npe) {
            System.err.println("Null pointer error in cloning. " + npe.getLocalizedMessage());
            return -1;
        } catch (Exception e) {
            System.err.println("Error in cloning. " + e.getLocalizedMessage());
            return -1;
        }
        return new Integer(contId);
    }

    /**
     * get containerContents of fromID and recreate them for the new container
     * used in cloning container (new container found by name)
     * @param fromID
     * @param toName
     * @return id of new container or -1 if unsuccessful
     */
    public int copyControls(String fromID, String toName) {
        String contId = "-1";
        try {
            openHibSession();

            ListIterator iter = getControlWellsOfContainer(fromID).listIterator();

            //get containerid
            String prefix = "select distinct myContainer.containerID from Container as myContainer "
                    + "where myContainer.containerName = '" + toName + "'";
            Query q = hibSess.createQuery(prefix);
            //Println used to be here
            contId = q.uniqueResult().toString();

            HashMap h = null;
            ControlWell toAdd = null;
            int result = 0;
            while (iter.hasNext()) {
                toAdd = (ControlWell) iter.next();
                h = toAdd.getControlWellHash();
                // set to new container
                h.remove("container");
                Container cont = (Container) getObjectById(Container.class, contId);
                h.put("container", cont);

                // set to make new cw object
                h.remove("controlWellID");
                h.put("controlWellID", "-1");

                Iterator it = h.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pairs = (Map.Entry) it.next();
                    //Println used to be here
                }
                result = addControlWell((Container) h.get("container"),
                        (Control) h.get("control"),
                        h.get("row").toString(),
                        h.get("column").toString(),
                        (h.get("volume") == null
                        || h.get("volume").toString().equalsIgnoreCase("null")
                        || h.get("volume").toString().equalsIgnoreCase(""))
                        ? null : h.get("volume").toString());
                if (result != 0) {
                    return -1;
                }
            }
        } catch (HibernateException he) {
            System.err.println("Hibernate error in cloning. " + he.getLocalizedMessage());
            return -1;
        } catch (NullPointerException npe) {
            System.err.println("Null pointer error in cloning. " + npe.getLocalizedMessage());
            return -1;
        } catch (Exception e) {
            System.err.println("Error in cloning. " + e.getLocalizedMessage());
            return -1;
        }
        return new Integer(contId);
    }

    private List getContentsOfContainer(String fromID) {
        openHibSession();
        String prefix = "select myContainerContent from ContainerContent as myContainerContent "
                + "where myContainerContent.container.containerID = " + fromID;
        Query q = hibSess.createQuery(prefix);
        List conts = q.list();
        return conts;
    }

    private List getControlWellsOfContainer(String fromID) {
        openHibSession();
        String prefix = "select myControlWell from ControlWell as myControlWell "
                + "where myControlWell.container.containerID = " + fromID;
        Query q = hibSess.createQuery(prefix);
        List conts = q.list();
        return conts;
    }

    /**
     * adjust volumes and/or concentrations of all containerContents of the
     * container (containerID)
     * @param containerID
     * @param doVolume
     * @param volume
     * @param volumeAction
     * @param doConcen
     * @param concentration
     * @param concentrationAction
     * @param doComments
     * @param comments
     * @return 0 if successful
     */
    public int editBulkContainerContents(String containerID, String doVolume,
            String volume, String volumeAction, String doConcen, String concentration,
            String concentrationAction, String doComments, String comments) {
        try {
            openHibSession();

            String orderClause = " order by myContainerContent."
                    + ((containercontentSortDir) ? containercontentSortCol : containercontentSortCol + " desc");
            String prefix = "select myContainerContent from ContainerContent as myContainerContent "
                    + "where myContainerContent.container.containerID = " + containerID;
            Query q = hibSess.createQuery(prefix + orderClause);
            //Println used to be here
            List conts = q.list();
            //Println used to be here
            ListIterator iter = conts.listIterator();

            int rezult = 0;
            if (currentUser == null) {
                return 1;
            }
            ContainerContent toAdjust = null;
            while (iter.hasNext()) {
                toAdjust = (ContainerContent) iter.next();

                if (toAdjust == null) {
                    return 1;
                }

                if (rezult == 0) {
                    toAdjust.setModifInfo(currentUser);
                    if (doVolume != null && volumeAction != null && volume != null
                            && doVolume.equals("true")) {
                        if (volumeAction.equals("set")) {
                            toAdjust.setVolume(volume);
                        }
                        if (volumeAction.equals("add")) {
                            double orig = Double.parseDouble(toAdjust.getVolume());
                            double add = Double.parseDouble(volume);
                            String newVol = String.valueOf((add + orig));
                            toAdjust.setVolume(newVol);
                        }
                        if (volumeAction.equals("subtract")) {
                            double orig = Double.parseDouble(toAdjust.getVolume());
                            double sub = Double.parseDouble(volume);
                            String newVol = String.valueOf((orig - sub));
                            toAdjust.setVolume(newVol);
                        }
                    }
                    if (doConcen != null && concentrationAction != null && concentration != null
                            && doConcen.equals("true")) {
                        if (concentrationAction.equals("set")) {
                            toAdjust.setConcentration(concentration);
                        }
                        if (concentrationAction.equals("add")) {
                            double orig = Double.parseDouble(toAdjust.getConcentration());
                            double add = Double.parseDouble(concentration);
                            String newVol = String.valueOf((add + orig));
                            toAdjust.setConcentration(newVol);
                        }
                        if (concentrationAction.equals("subtract")) {
                            double orig = Double.parseDouble(toAdjust.getConcentration());
                            double sub = Double.parseDouble(concentration);
                            String newVol = String.valueOf((orig - sub));
                            toAdjust.setConcentration(newVol);
                        }
                    }

                    // update comments
                    if (doComments != null && comments != null
                            && doComments.equals("true") && !comments.trim().equals("")
                            && !comments.trim().equals("null")) {

                        // don't want any apostrophes
                        comments = comments.replace("'", "");
                        comments = comments.trim();

                        // want a period at the end of ever chunk of comment
                        String currentComment = toAdjust.getComments();
                        if (currentComment != null && !currentComment.trim().equals("")) {
                            currentComment = currentComment.trim();
                            if (!(currentComment.charAt(currentComment.length() - 1) == '.')) {
                                currentComment = currentComment + ". ";
                            } else {
                                currentComment = currentComment + " ";
                            }
                        }
                        if (currentComment == null) {
                            currentComment = "";
                        }

                        if (!(comments.charAt(comments.length() - 1) == '.')) {
                            comments = comments + ". ";
                        } else {
                            comments = comments + " ";
                        }
                        toAdjust.setComments(currentComment + comments);

                    }

                    Transaction tx = null;
                    try {
                        if (!hibSess.isOpen()) {
                            openHibSession();
                        }
                        tx = hibSess.beginTransaction();
                        hibSess.update(toAdjust);
                        tx.commit();
                    } catch (Exception e) {
                        if (tx != null) {
                            tx.rollback();
                        }
                        throw e;
                    } finally {
                        closeHibSession(); //needs before forward
                    }
                }
            }
        } catch (HibernateException he) {
            System.err.println("Hibernate error in cloning. " + he.getLocalizedMessage());
            return 1;
        } catch (Exception e) {
            System.err.println("Error in cloning. " + e.getLocalizedMessage());
            return 1;
        }
        return 0;
    }

    /**
     * checks if all the contents of a container have enough volume to apply the reduction
     * leaving all cc with >= 0 volume
     * @param containerID
     * @param reductionVol
     * @return 0 if successful, -1 if error, -2 if insufficient volume
     */
    public int checkContainerContentsVolumes(String containerID, double reductionVol) {
        try {
            openHibSession();

            String orderClause = " order by myContainerContent.volume ";
            // get cc for which we have volume information
            String prefix = "select myContainerContent from ContainerContent as myContainerContent "
                    + "where myContainerContent.container.containerID = " + containerID + " and volume >= 0 ";
            Query q = hibSess.createQuery(prefix + orderClause);
            //Println used to be here
            List conts = q.list();
            //Println used to be here
            ListIterator iter = conts.listIterator();

            if (currentUser == null || conts == null || conts.isEmpty()) {
                return -1;
            }

            ContainerContent toCheck = null;
            while (iter.hasNext()) {
                toCheck = (ContainerContent) iter.next();
                double vol = Double.parseDouble(toCheck.getVolume());
                if ((vol - reductionVol) < 0) {
                    return -2;
                }
            }
        } catch (Exception e) {
            System.err.println("Error in cloning. " + e.getLocalizedMessage());
            return 1;
        }
        return 0;
    }

    /**
     * returns min volume in a container
     * @param containerID
     * @return min volume, -1 if error
     */
    public double getMinContentVolume(String containerID) {
        double minD = -1;
        try {
            openHibSession();

            // get cc for which we have volume information
            String prefix = "select min(myContainerContent.volume) from ContainerContent as myContainerContent "
                    + "where myContainerContent.container.containerID = " + containerID + " and myContainerContent.volume >= 0 ";
            Query q = hibSess.createQuery(prefix);
            //Println used to be here
            List min = q.list();

            if (currentUser == null || min == null || min.isEmpty()) {
                return -1;
            }
            minD = new Double(min.get(0).toString());

        } catch (Exception e) {
            System.err.println("Error in cloning. " + e.getLocalizedMessage());
            return 1;
        }
        return minD;
    }

    /**
     * adjust volumes of all containerContents of the container (containerID)
     * @param containerID
     * @param volume
     * @param volumeAction
     * @param dilution
     * @param setToZero
     * @param concentration
     * @return 0 if successful
     */
    public int editBulkContainerContents(String containerID,
            String volume, String volumeAction, String dilution, String concentration,
            boolean setToZero) {
        try {
            openHibSession();


            String orderClause = " order by myContainerContent."
                    + ((containercontentSortDir) ? containercontentSortCol : containercontentSortCol + " desc");
            String prefix = "select myContainerContent from ContainerContent as myContainerContent "
                    + "where myContainerContent.container.containerID = " + containerID;
            Query q = hibSess.createQuery(prefix + orderClause);
            //Println used to be here
            List conts = q.list();
            //Println used to be here
            ListIterator iter = conts.listIterator();

            int rezult = 0;
            if (currentUser == null) {
                return 1;
            }
            ContainerContent toAdjust = null;
            while (iter.hasNext()) {
                toAdjust = (ContainerContent) iter.next();

                if (toAdjust == null) {
                    return 1;
                }

                if (rezult == 0) {
                    toAdjust.setModifInfo(currentUser);
                    if (volumeAction != null && volume != null) {
                        if (volumeAction.equals("set")) {
                            toAdjust.setVolume(volume);
                        }
                        if (volumeAction.equals("add")) {
                            double orig = Double.parseDouble(toAdjust.getVolume());
                            double add = Double.parseDouble(volume);
                            String newVol = String.valueOf((add + orig));
                            toAdjust.setVolume(newVol);
                        }
                        if (volumeAction.equals("subtract")) {
                            double orig = Double.parseDouble(toAdjust.getVolume());
                            double sub = Double.parseDouble(volume);
                            String newVol = String.valueOf((orig - sub));
                            if (setToZero && (orig - sub) < 0) {
                                newVol = "0";
                            }
                            toAdjust.setVolume(newVol);
                        }
                    }
                    if (dilution != null) {
                        toAdjust.setDilution(dilution);
                    }
                    if (concentration != null) {
                        toAdjust.setConcentration(concentration);
                    }

                    Transaction tx = null;
                    try {
                        if (!hibSess.isOpen()) {
                            openHibSession();
                        }
                        tx = hibSess.beginTransaction();
                        hibSess.update(toAdjust);
                        tx.commit();
                    } catch (Exception e) {
                        if (tx != null) {
                            tx.rollback();
                        }
                        throw e;
                    } finally {
                        closeHibSession(); //needs before forward
                    }
                }
            }
        } catch (HibernateException he) {
            System.err.println("Hibernate error in cloning. " + he.getLocalizedMessage());
            return 1;
        } catch (Exception e) {
            System.err.println("Error in cloning. " + e.getLocalizedMessage());
            return 1;
        }
        return 0;
    }
//---GenotypingRunContainer-------------------------------------------
//---GenotypingRunContainer-------------------------------------------
    private GenotypingRunContainer currentGenotypingRunContainer = null;
    private String genotypingruncontainerSortCol = "genotypingRunID";
    private boolean genotypingruncontainerSortDir = true; //true - asc; false - desc

    public int checkGenotypingRunContainerId(String genotypingRunID, String containerID) {
        openHibSession();
        setInvalidField("genoRunContId");
        //Println used to be here
        if (genotypingRunID == null || genotypingRunID.trim().equals("")) {
            return 2;
        }
        try {
            Query q = hibSess.createQuery("select count(*) from GenotypingRunContainer Gen where Gen.genotypingRunID= :genotypingRunID and Gen.containerID=:containerID");
            q.setString("genotypingRunID", genotypingRunID);
            q.setString("containerID", containerID);
            //Println used to be here
            Integer cnt = ((Long) q.iterate().next()).intValue();
            //Println used to be here
            if (cnt.intValue() > 0) {
                return 3;
            }
            clearInvalidField();
        } catch (Exception ex) {
            //Println used to be here
            return 1;
        }
        return 0;
    }

    public int addGenotypingRunContainer(GenotypingRun genotypingRun, Container container) throws Exception {
        openHibSession();
        int rezult = 0;
        //Println used to be here

        if (currentUser == null) {
            return 1;
        }
        rezult = checkGenotypingRunContainerId(genotypingRun.getId().toString(), container.getId().toString());
        openHibSession();
        //Println used to be here
        if (rezult == 0) {
            GenotypingRunContainer tmpGenotypingRunContainer = new GenotypingRunContainer();
            tmpGenotypingRunContainer.setCreateInfo(currentUser);
            tmpGenotypingRunContainer.setGenotypingRun(genotypingRun);
            tmpGenotypingRunContainer.setContainer(container);

            //Println used to be here
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpGenotypingRunContainer);
                tx.commit();
                currentGenotypingRunContainer = tmpGenotypingRunContainer;

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                if (e.getClass().equals(org.hibernate.exception.ConstraintViolationException.class)) {
                    return 3;
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
                openHibSession();
            }
        }
        return rezult;
    }

    public int updateGenotypingRunContainer(GenotypingRun genotypingRun, Container container) throws Exception {
        int rezult = 0;
        //Println used to be here

        if (currentUser == null) {
            return 1;
        }
        GenotypingRunContainer tmpGenotypingRunContainer = getCurrentGenotypingRunContainer();
        
        if (tmpGenotypingRunContainer == null) {
            return 1;
        }
        //Println used to be here
        //Println used to be here
        //Println used to be here
        if (!genotypingRun.getId().toString().equals(tmpGenotypingRunContainer.getId().toString())) {
            return 1;
        }

        //Println used to be here
        if (rezult == 0) {
            tmpGenotypingRunContainer.setModifInfo(currentUser);
            tmpGenotypingRunContainer.setGenotypingRun(genotypingRun);
            tmpGenotypingRunContainer.setContainer(container);

            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.update(tmpGenotypingRunContainer);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                if (e.getClass().equals(org.hibernate.exception.ConstraintViolationException.class)) {
                    return 3;
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
                openHibSession();
            }
        }
        return rezult;
    }

    public int deleteGenotypingRunContainer(String selfId) throws Exception {
        if (currentUser == null) {
            return 1;
        }
        GenotypingRunContainer tmpGenotypingRunContainer = getCurrentGenotypingRunContainer();
        if (tmpGenotypingRunContainer == null) {
            return 1;
        }
        if (!selfId.equals(tmpGenotypingRunContainer.getId().toString())) {
            return 1;
        }

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.delete(tmpGenotypingRunContainer);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            currentGenotypingRunContainer = null;
            /*          currentShippedTo = null;
            currentAliquot = null;
            currentSample = null;
            currentPerson = null;
             */
            closeHibSession(); //needs before forward
        }
        return 0;
    }

    public GenotypingRunContainer getCurrentGenotypingRunContainer() {
        return currentGenotypingRunContainer;
    }

    public Long getCurrentGenotypingRunContainerId() {
        return (currentGenotypingRunContainer == null) ? null : currentGenotypingRunContainer.getId();
    }

    public List getAllGenotypingRunContainers(int startPosition) throws
            HibernateException {
        openHibSession();
        String orderClause = " order by myGenotypingRunContainer."
                + ((genotypingruncontainerSortDir) ? genotypingruncontainerSortCol : genotypingruncontainerSortCol + " desc");
        String prefix = "select myGenotypingRunContainer from GenotypingRunContainer as myGenotypingRunContainer";
        Query q = hibSess.createQuery(prefix + orderClause);
        if (viewLimit > 0 && startPosition >= 0) {
            q.setFirstResult(startPosition);
            q.setMaxResults(viewLimit);
        }
        //Println used to be here
        //Println used to be here

        return q.list();
    }

    public int getAllGenotypingRunContainersCount() throws HibernateException {
        openHibSession();
        String prefix = "select count(*) from GenotypingRunContainer as myGenotypingRunContainer";
        Query q;
        q = hibSess.createQuery(prefix);
        int count = ((Integer) q.iterate().next()).intValue();
        return count;
    }

    public void setGenotypingRunContainerSortCol(String sortCol) {
        if (genotypingruncontainerSortCol.equals(sortCol)) {
            genotypingruncontainerSortDir = !genotypingruncontainerSortDir;
        } else {
            genotypingruncontainerSortCol = sortCol;
            genotypingruncontainerSortDir = true;
        }
    }

    public String getGenotypingRunContainerSortId(String sortCol) {
        return (!genotypingruncontainerSortCol.equals(sortCol)) ? "normal" : (genotypingruncontainerSortDir) ? "normalu" : "normalo";
    }

    public void setCurrentGenotypingRunContainer(String genoRunContId) throws HibernateException {
        currentGenotypingRunContainer = (GenotypingRunContainer) getObjectById(GenotypingRunContainer.class, genoRunContId);
    }
//---GenotypingRun-------------------------------------------
//---GenotypingRun-------------------------------------------
    private GenotypingRun currentGenotypingRun = null;
    private String genotypingrunSortCol = "genotypingRunID";
    private boolean genotypingrunSortDir = true; //true - asc; false - desc
    private ViewGenotypingRunManager vGenotypingRunM = null;

    public int checkGenotypingRunId(String genoRunId) {
        openHibSession();
        setInvalidField("genoRunId");
        //Println used to be here
        if (genoRunId == null || genoRunId.trim().equals("")) {
            return 2;
        }
        try {
            Query q = hibSess.createQuery("select count(*) from GenotypingRun Gen " + " where Gen.id= :genoRunId");
            q.setString("genoRunId", genoRunId);
            //Println used to be here
            Integer cnt = ((Long) q.iterate().next()).intValue();
            //Println used to be here
            if (cnt.intValue() > 0) {
                return 3;
            }
            clearInvalidField();
        } catch (Exception ex) {
            //Println used to be here
            return 1;
        }
        return 0;
    }

    public int addGenotypingRun(String genotypingRunID, String description, Date date) throws Exception {
        openHibSession();
        int rezult = 0;
        //Println used to be here

        if (currentUser == null) {
            return 1;
        }

        HashMap uniques = new HashMap();
        uniques.put("description", "'" + description + "'");

        rezult = countKey(GenotypingRun.class, uniques);

        //Println used to be here
        if (rezult == 0) {
            GenotypingRun tmpGenotypingRun = new GenotypingRun();
            tmpGenotypingRun.setCreateInfo(currentUser);
            tmpGenotypingRun.setGenotypingRunID(genotypingRunID);
            tmpGenotypingRun.setDescription(description);
            tmpGenotypingRun.setDate(date);

            //Println used to be here
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpGenotypingRun);
                tx.commit();
                currentGenotypingRun = tmpGenotypingRun;

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public int updateGenotypingRun(String genotypingRunID, String description, Date date) throws Exception {
        int rezult = 0;
        //Println used to be here

        //Println used to be here
        if (currentUser == null) {
            return 1;
        }
        GenotypingRun tmpGenotypingRun = getCurrentGenotypingRun();
        
        if (tmpGenotypingRun == null) {
            return 1;
        }
        //Println used to be here
        //Println used to be here
        //Println used to be here
        if (!genotypingRunID.equals(tmpGenotypingRun.getId().toString())) {
            return 1;
        }


        HashMap uniques = new HashMap();
        uniques.put("description", "'" + description + "'");
        //only thing that can be updates is description, which needs to be unique
        rezult = ((countKey(Cohort.class, uniques) == 0) ? 0 : 3);

        //Println used to be here
        if (rezult == 0) {
            tmpGenotypingRun.setModifInfo(currentUser);
            tmpGenotypingRun.setGenotypingRunID(genotypingRunID);
            tmpGenotypingRun.setDescription(description);
            tmpGenotypingRun.setDate(date);

            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.update(tmpGenotypingRun);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public int deleteGenotypingRun(String selfId) throws Exception {
        if (currentUser == null) {
            return 1;
        }
        GenotypingRun tmpGenotypingRun = getCurrentGenotypingRun();
        if (tmpGenotypingRun == null) {
            return 1;
        }
        if (!selfId.equals(tmpGenotypingRun.getId().toString())) {
            return 1;
        }

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.delete(tmpGenotypingRun);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            currentGenotypingRun = null;
            /*          currentShippedTo = null;
            currentAliquot = null;
            currentSample = null;
            currentPerson = null;
             */
            closeHibSession(); //needs before forward
        }
        return 0;
    }

    public ViewGenotypingRunManager getViewGenotypingRunManager() {
        return vGenotypingRunM;
    }

    public GenotypingRun getCurrentGenotypingRun() {
        return currentGenotypingRun;
    }

    public Long getCurrentGenotypingRunId() {
        return (currentGenotypingRun == null) ? null : currentGenotypingRun.getId();
    }

    public List getAllGenotypingRuns(int startPosition) throws
            HibernateException {
        openHibSession();
        String orderClause = " order by myGenotypingRun."
                + ((genotypingrunSortDir) ? genotypingrunSortCol : genotypingrunSortCol + " desc");
        String prefix = "select myGenotypingRun from GenotypingRun as myGenotypingRun";
        Query q = hibSess.createQuery(prefix + orderClause);
        if (viewLimit > 0 && startPosition >= 0) {
            q.setFirstResult(startPosition);
            q.setMaxResults(viewLimit);
        }
        //Println used to be here
        //Println used to be here

        return q.list();
    }

    public GenotypingRun getGenotypingRun(String description) throws HibernateException {
        openHibSession();
        Query q = hibSess.createQuery("from GenotypingRun gr where gr.description= :description");
        q.setString("description", description);
        if (q.list().isEmpty()) {
            return null;
        } else {
            return (GenotypingRun) q.iterate().next();
        }

    }

    public List getContainerContent(Sample sample) throws
            HibernateException {
        openHibSession();
        Query q = hibSess.createQuery("from ContainerContent cc where cc.sample.sampleID= :sampleID");
        q.setString("sampleID", sample.getSampleID());

        return q.list();

    }

    public ContainerContent getContainerContent(Container container, String row, String column) throws HibernateException {
        openHibSession();
        Query q = hibSess.createQuery("from ContainerContent cc where cc.container.containerID= :containerID and cc.row=:row and cc.column=:column");
        q.setString("containerID", container.getContainerID());
        q.setString("row", row);
        q.setString("column", column);
        if (q.list().isEmpty()) {
            return null;
        } else {
            return (ContainerContent) q.iterate().next();
        }

    }

    public ContainerContent getContainerContent(String barcode) throws HibernateException {
        openHibSession();
        Query q = hibSess.createQuery("from ContainerContent cc where cc.barcode=:barcode ");
        q.setString("barcode", barcode);
        if (q.list().isEmpty()) {
            return null;
        } else {
            return (ContainerContent) q.iterate().next();
        }

    }

    public int getGenotypingRunsCount() throws HibernateException {
        openHibSession();
        String prefix = "select count(*) from GenotypingRun as myGenotypingRun";
        Query q;
        q = hibSess.createQuery(prefix);
        int count = ((Long) q.iterate().next()).intValue();
        return count;
    }

    public void setGenotypingRunSortCol(String sortCol) {
        if (genotypingrunSortCol.equals(sortCol)) {
            genotypingrunSortDir = !genotypingrunSortDir;
        } else {
            genotypingrunSortCol = sortCol;
            genotypingrunSortDir = true;
        }
    }

    public String getGenotypingRunSortId(String sortCol) {
        return (!genotypingrunSortCol.equals(sortCol)) ? "normal" : (genotypingrunSortDir) ? "normalu" : "normalo";
    }

    public void setCurrentGenotypingRun(String genoRunId) throws HibernateException {
        currentGenotypingRun = (GenotypingRun) getObjectById(GenotypingRun.class, genoRunId);
    }
//---GenotypingRunSampleStatus-------------------------------------------
//---GenotypingRunSampleStatus-------------------------------------------
    private GenotypingRunSampleStatus currentGenotypingRunSampleStatus = null;
    private String genotypingrunsamplestatusSortCol = "valid";
    private boolean genotypingrunsamplestatusSortDir = true; //true - asc; false - desc
    private ViewGenotypingRunSampleStatusManager vGenotypingRunSampleStatusM = null;

    public int checkGenotypingRunSampleStatusId(String genotypingRunID, String containerContentsID) {
        setInvalidField("genoRunSampStatId");
        //Println used to be here
        if (genotypingRunID == null || genotypingRunID.trim().equals("")) {
            return 2;
        }
        try {
            openHibSession();
            Query q = hibSess.createQuery("select count(*) from GenotypingRunSampleStatus Gen where Gen.genotypingRun= :genotypingRun and Gen.containerContents=:containerContents");
            q.setString("genotypingRun", genotypingRunID);
            q.setString("containerContents", containerContentsID);
            //Println used to be here
            Integer cnt = ((Long) q.iterate().next()).intValue();
            //Println used to be here
            if (cnt.intValue() > 0) {
                return 3;
            }
            clearInvalidField();
        } catch (Exception ex) {
            //Println used to be here
            return 1;
        }
        return 0;
    }

    public int addGenotypingRunSampleStatus(GenotypingRun genotypingRun, ContainerContent containerContents, String valid, Double callRate) throws Exception {
        int rezult = 0;
        //Println used to be here

        if (currentUser == null) {
            return 1;
        }
        rezult = checkGenotypingRunSampleStatusId(genotypingRun.getId().toString(), containerContents.getId().toString());
        //Println used to be here
        if (rezult == 0) {
            GenotypingRunSampleStatus tmpGenotypingRunSampleStatus = new GenotypingRunSampleStatus();
            tmpGenotypingRunSampleStatus.setCreateInfo(currentUser);
            tmpGenotypingRunSampleStatus.setGenotypingRun(genotypingRun);
            tmpGenotypingRunSampleStatus.setContainerContents(containerContents);
            tmpGenotypingRunSampleStatus.setValid(valid);
            tmpGenotypingRunSampleStatus.setCallRate(callRate);

            //Println used to be here
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpGenotypingRunSampleStatus);
                tx.commit();
                currentGenotypingRunSampleStatus = tmpGenotypingRunSampleStatus;

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                if (e.getClass().equals(org.hibernate.exception.ConstraintViolationException.class)) {
                    return 3;
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
                openHibSession();
            }
        }
        return rezult;
    }

    public int updateGenotypingRunSampleStatus(GenotypingRun genotypingRun, ContainerContent containerContents, String valid, Double callRate) throws Exception {
        int rezult = 0;
        //Println used to be here

        //Println used to be here
        if (currentUser == null) {
            return 1;
        }
        GenotypingRunSampleStatus tmpGenotypingRunSampleStatus = getCurrentGenotypingRunSampleStatus();
        
        if (tmpGenotypingRunSampleStatus == null) {
            return 1;
        }
        //Println used to be here
        //Println used to be here
        //Println used to be here
        if (!genotypingRun.getId().toString().equals(tmpGenotypingRunSampleStatus.getId().toString())) {
            return 1;
        }

        //Println used to be here
        if (rezult == 0) {
            tmpGenotypingRunSampleStatus.setModifInfo(currentUser);
            tmpGenotypingRunSampleStatus.setGenotypingRun(genotypingRun);
            tmpGenotypingRunSampleStatus.setContainerContents(containerContents);
            tmpGenotypingRunSampleStatus.setValid(valid);
            tmpGenotypingRunSampleStatus.setCallRate(callRate);

            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.update(tmpGenotypingRunSampleStatus);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                if (e.getClass().equals(org.hibernate.exception.ConstraintViolationException.class)) {
                    return 3;
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
                openHibSession();
            }
        }
        return rezult;
    }

    public int deleteGenotypingRunSampleStatus(String selfId) throws Exception {
        if (currentUser == null) {
            return 1;
        }
        GenotypingRunSampleStatus tmpGenotypingRunSampleStatus = getCurrentGenotypingRunSampleStatus();
        if (tmpGenotypingRunSampleStatus == null) {
            return 1;
        }
        if (!selfId.equals(tmpGenotypingRunSampleStatus.getId().toString())) {
            return 1;
        }

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.delete(tmpGenotypingRunSampleStatus);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            currentGenotypingRunSampleStatus = null;
            /*          currentShippedTo = null;
            currentAliquot = null;
            currentSample = null;
            currentPerson = null;
             */
            closeHibSession(); //needs before forward
        }
        return 0;
    }

    public ViewGenotypingRunSampleStatusManager getViewGenotypingRunSampleStatusManager() {
        return vGenotypingRunSampleStatusM;
    }

    public GenotypingRunSampleStatus getCurrentGenotypingRunSampleStatus() {
        return currentGenotypingRunSampleStatus;
    }

    public Long getCurrentGenotypingRunSampleStatusId() {
        return (currentGenotypingRunSampleStatus == null) ? null : currentGenotypingRunSampleStatus.getId();
    }

    public List getAllGenotypingRunSampleStatuss(int startPosition) throws
            HibernateException {
        String orderClause = " order by myGenotypingRunSampleStatus."
                + ((genotypingrunsamplestatusSortDir) ? genotypingrunsamplestatusSortCol : genotypingrunsamplestatusSortCol + " desc");
        openHibSession();
        String prefix = "select myGenotypingRunSampleStatus from GenotypingRunSampleStatus as myGenotypingRunSampleStatus";
        Query q = hibSess.createQuery(prefix + orderClause);
        if (viewLimit > 0 && startPosition >= 0) {
            q.setFirstResult(startPosition);
            q.setMaxResults(viewLimit);
        }
        //Println used to be here
        //Println used to be here

        return q.list();
    }

    public int getAllGenotypingRunSampleStatussCount() throws HibernateException {
        openHibSession();
        String prefix = "select count(*) from GenotypingRunSampleStatus as myGenotypingRunSampleStatus";
        Query q;
        q = hibSess.createQuery(prefix);
        int count = ((Long) q.iterate().next()).intValue();
        return count;
    }

    public void setGenotypingRunSampleStatusSortCol(String sortCol) {
        if (genotypingrunsamplestatusSortCol.equals(sortCol)) {
            genotypingrunsamplestatusSortDir = !genotypingrunsamplestatusSortDir;
        } else {
            genotypingrunsamplestatusSortCol = sortCol;
            genotypingrunsamplestatusSortDir = true;
        }
    }

    public String getGenotypingRunSampleStatusSortId(String sortCol) {
        return (!genotypingrunsamplestatusSortCol.equals(sortCol)) ? "normal" : (genotypingrunsamplestatusSortDir) ? "normalu" : "normalo";
    }

    public void setCurrentGenotypingRunSampleStatus(String genoRunSampStatId) throws HibernateException {
        currentGenotypingRunSampleStatus = (GenotypingRunSampleStatus) getObjectById(GenotypingRunSampleStatus.class, genoRunSampStatId);
    }
//---GenotypingRunSnpStatus-------------------------------------------
//---GenotypingRunSnpStatus-------------------------------------------
    private GenotypingRunSnpStatus currentGenotypingRunSnpStatus = null;
    private String genotypingrunsnpstatusSortCol = "valid";
    private boolean genotypingrunsnpstatusSortDir = true; //true - asc; false - desc
    private ViewGenotypingRunSnpStatusManager vGenotypingRunSnpStatusM = null;

    public int checkGenotypingRunSnpStatusId(String genotypingRunID, String snpID) {
        setInvalidField("genoRunSnpStatId");
        //Println used to be here
        if (genotypingRunID == null || genotypingRunID.trim().equals("")) {
            return 2;
        }
        try {
            Query q = hibSess.createQuery("select count(*) from GenotypingRunSnpStatus Gen where Gen.genotypingRunID= :genotypingRunID and gen.snpID=:snpID");
            q.setString("genotypingRunID", genotypingRunID);
            q.setString("snpID", snpID);
            //Println used to be here
            Integer cnt = ((Long) q.iterate().next()).intValue();
            //Println used to be here
            if (cnt.intValue() > 0) {
                return 3;
            }
            clearInvalidField();
        } catch (Exception ex) {
            //Println used to be here
            return 1;
        }
        return 0;
    }

    public int addGenotypingRunSnpStatus(String genotypingRunID, String snpID, String valid, String strand) throws Exception {
        int rezult = 0;
        //Println used to be here

        if (currentUser == null) {
            return 1;
        }
        rezult = checkGenotypingRunSnpStatusId(genotypingRunID, snpID);
        //Println used to be here
        if (rezult == 0) {
            GenotypingRunSnpStatus tmpGenotypingRunSnpStatus = new GenotypingRunSnpStatus();
            tmpGenotypingRunSnpStatus.setCreateInfo(currentUser);
            tmpGenotypingRunSnpStatus.setGenotypingRunID(genotypingRunID);
            tmpGenotypingRunSnpStatus.setSnpID(snpID);
            tmpGenotypingRunSnpStatus.setValid(valid);
            tmpGenotypingRunSnpStatus.setStrand(strand);

            //Println used to be here
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpGenotypingRunSnpStatus);
                tx.commit();
                currentGenotypingRunSnpStatus = tmpGenotypingRunSnpStatus;

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                if (e.getClass().equals(org.hibernate.exception.ConstraintViolationException.class)) {
                    return 3;
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
                openHibSession();
            }
        }
        return rezult;
    }

    public int updateGenotypingRunSnpStatus(String genotypingRunID, String snpID, String valid, String strand) throws Exception {
        int rezult = 0;
        //Println used to be here

        //Println used to be here
        if (currentUser == null) {
            return 1;
        }
        GenotypingRunSnpStatus tmpGenotypingRunSnpStatus = getCurrentGenotypingRunSnpStatus();
        
        if (tmpGenotypingRunSnpStatus == null) {
            return 1;
        }
        //Println used to be here
        //Println used to be here
        //Println used to be here
        if (!genotypingRunID.equals(tmpGenotypingRunSnpStatus.getId().toString())) {
            return 1;
        }

        //Println used to be here
        if (rezult == 0) {
            tmpGenotypingRunSnpStatus.setModifInfo(currentUser);
            tmpGenotypingRunSnpStatus.setGenotypingRunID(genotypingRunID);
            tmpGenotypingRunSnpStatus.setSnpID(snpID);
            tmpGenotypingRunSnpStatus.setValid(valid);
            tmpGenotypingRunSnpStatus.setStrand(strand);

            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.update(tmpGenotypingRunSnpStatus);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                if (e.getClass().equals(org.hibernate.exception.ConstraintViolationException.class)) {
                    return 3;
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
                openHibSession();
            }
        }
        return rezult;
    }

    public int deleteGenotypingRunSnpStatus(String selfId) throws Exception {
        if (currentUser == null) {
            return 1;
        }
        GenotypingRunSnpStatus tmpGenotypingRunSnpStatus = getCurrentGenotypingRunSnpStatus();
        if (tmpGenotypingRunSnpStatus == null) {
            return 1;
        }
        if (!selfId.equals(tmpGenotypingRunSnpStatus.getId().toString())) {
            return 1;
        }

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.delete(tmpGenotypingRunSnpStatus);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            currentGenotypingRunSnpStatus = null;
            /*          currentShippedTo = null;
            currentAliquot = null;
            currentSample = null;
            currentPerson = null;
             */
            closeHibSession(); //needs before forward
        }
        return 0;
    }

    public ViewGenotypingRunSnpStatusManager getViewGenotypingRunSnpStatusManager() {
        return vGenotypingRunSnpStatusM;
    }

    public GenotypingRunSnpStatus getCurrentGenotypingRunSnpStatus() {
        return currentGenotypingRunSnpStatus;
    }

    public Long getCurrentGenotypingRunSnpStatusId() {
        return (currentGenotypingRunSnpStatus == null) ? null : currentGenotypingRunSnpStatus.getId();
    }

    public List getAllGenotypingRunSnpStatuss(int startPosition) throws
            HibernateException {
        String orderClause = " order by myGenotypingRunSnpStatus."
                + ((genotypingrunsnpstatusSortDir) ? genotypingrunsnpstatusSortCol : genotypingrunsnpstatusSortCol + " desc");
        String prefix = "select myGenotypingRunSnpStatus from GenotypingRunSnpStatus as myGenotypingRunSnpStatus";
        Query q = hibSess.createQuery(prefix + orderClause);
        if (viewLimit > 0 && startPosition >= 0) {
            q.setFirstResult(startPosition);
            q.setMaxResults(viewLimit);
        }
        //Println used to be here
        //Println used to be here

        return q.list();
    }

    public int getAllGenotypingRunSnpStatussCount() throws HibernateException {
        String prefix = "select count(*) from GenotypingRunSnpStatus as myGenotypingRunSnpStatus";
        Query q;
        q = hibSess.createQuery(prefix);
        int count = ((Long) q.iterate().next()).intValue();
        return count;
    }

    public void setGenotypingRunSnpStatusSortCol(String sortCol) {
        if (genotypingrunsnpstatusSortCol.equals(sortCol)) {
            genotypingrunsnpstatusSortDir = !genotypingrunsnpstatusSortDir;
        } else {
            genotypingrunsnpstatusSortCol = sortCol;
            genotypingrunsnpstatusSortDir = true;
        }
    }

    public String getGenotypingRunSnpStatusSortId(String sortCol) {
        return (!genotypingrunsnpstatusSortCol.equals(sortCol)) ? "normal" : (genotypingrunsnpstatusSortDir) ? "normalu" : "normalo";
    }

    public void setCurrentGenotypingRunSnpStatus(String genoRunSnpStatId) throws HibernateException {
        currentGenotypingRunSnpStatus = (GenotypingRunSnpStatus) getObjectById(GenotypingRunSnpStatus.class, genoRunSnpStatId);
    }
//---Genotype-------------------------------------------
//---Genotype-------------------------------------------
    private Genotype currentGenotype = null;
    private int genotypesCount = 0;
    private String genotypeSortCol = "genotypeID";
    private boolean genotypeSortDir = true; //true - asc; false - desc
    private ViewGenotypeManager vGenotypeM = null;

    public int checkGenotypeId(String genotypId) {
        setInvalidField("genotypId");
        //Println used to be here
        if (genotypId == null || genotypId.trim().equals("")) {
            return 2;
        }
        try {
            Query q = hibSess.createQuery("select count(*) from Genotype Gen " + " where Gen.id= :genotypId");
            q.setString("genotypId", genotypId);
            //Println used to be here
            Integer cnt = ((Long) q.iterate().next()).intValue();
            //Println used to be here
            if (cnt.intValue() > 0) {
                return 3;
            }
            clearInvalidField();
        } catch (Exception ex) {
            //Println used to be here
            return 1;
        }
        return 0;
    }

    public int addGenotype(String allele1, String allele2, String containerContentsID, String genotypingRunID, String snpID) throws Exception {
        int rezult = 0;
        ////Println used to be here

        if (currentUser == null) {
            return 1;
        }
        //rezult = checkGenotypeId(genotypeID);
        ////Println used to be here
        ContainerContent tmpCC = null;
        GenotypingRun tmpGR = null;
        Snp tmpS = null;
        try {
            if (containerContentsID != null && !containerContentsID.equals("") && Integer.parseInt(containerContentsID) > 0) {
                tmpCC = (ContainerContent) this.getObjectById(ContainerContent.class, containerContentsID);
            }
            if (genotypingRunID != null && !genotypingRunID.equals("") && Integer.parseInt(genotypingRunID) > 0) {
                tmpGR = (GenotypingRun) this.getObjectById(GenotypingRun.class, genotypingRunID);
            }
            if (snpID != null && !snpID.equals("") && Integer.parseInt(snpID) > 0) {
                tmpS = (Snp) this.getObjectById(Snp.class, snpID);
            }
        }
        catch(NumberFormatException e){
            return 1;
        }
        HashMap tmpKey = new HashMap();
        if (rezult == 0) {
            Genotype tmpGenotype = new Genotype();
            tmpGenotype.setCreateInfo(currentUser);
            tmpGenotype.setGenotypeID("-1");
            tmpGenotype.setAllele1(allele1);
            tmpGenotype.setAllele2(allele2);
            tmpGenotype.setContainerContents(tmpCC);
            tmpGenotype.setGenotypingRun(tmpGR);
            tmpGenotype.setSnp(tmpS);

            ////Println used to be here
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpGenotype);
                tx.commit();
                currentGenotype = tmpGenotype;

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                if (e.getClass().equals(org.hibernate.exception.ConstraintViolationException.class)) {
                    return 3;
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
                openHibSession();
            }
        }
        return rezult;
    }

    public int addGenotypes(List<Genotype> gtList) throws Exception {
        int rezult = 0;
        ////Println used to be here

        if (currentUser == null) {
            return 1;
        }
        //rezult = checkGenotypeId(genotypeID);
        ////Println used to be here

//            Genotype tmpGenotype = new Genotype();
//            tmpGenotype.setCreateInfo(currentUser);
//            tmpGenotype.setAllele1(allele1);
//            tmpGenotype.setAllele2(allele2);
//            tmpGenotype.setContainerContentsID(containerContentsID);
//            tmpGenotype.setGenotypingRunID(genotypingRunID);
//            tmpGenotype.setSnpID(snpID);

        ////Println used to be here
        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            for (Genotype tmpGenotype : gtList) {
                hibSess.save(tmpGenotype);
            }
            tx.commit();

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            if (e.getClass().equals(org.hibernate.exception.ConstraintViolationException.class)) {
                return 3;
            }
            throw e;
        } finally {
            closeHibSession(); //needs before forward
            openHibSession();
        }

        return rezult;
    }

    public int updateGenotype(String genotypeID, String allele1, String allele2, String containerContentsID, String genotypingRunID, String snpID) throws Exception {
        int rezult = 0;
        //Println used to be here

        //Println used to be here
        if (currentUser == null) {
            return 1;
        }
        Genotype tmpGenotype = getCurrentGenotype();
        
        if (tmpGenotype == null) {
            return 1;
        }
        //Println used to be here
        //Println used to be here
        //Println used to be here
        if (!genotypeID.equals(tmpGenotype.getId().toString())) {
            return 1;
        }
        ContainerContent tmpCC = null;
        GenotypingRun tmpGR = null;
        Snp tmpS = null;
        try {
            if (containerContentsID != null && !containerContentsID.equals("") && Integer.parseInt(containerContentsID) >= 0) {
                tmpCC = (ContainerContent) this.getObjectById(ContainerContent.class, containerContentsID);
            }
            if (genotypingRunID != null && !genotypingRunID.equals("") && Integer.parseInt(genotypingRunID) >= 0) {
                tmpGR = (GenotypingRun) this.getObjectById(GenotypingRun.class, genotypingRunID);
            }
            if (snpID != null && !snpID.equals("") && Integer.parseInt(snpID) >= 0) {
                tmpS = (Snp) this.getObjectById(Snp.class, snpID);
            }
        }
        catch(NumberFormatException e){
            return 1;
        }
        //Println used to be here
        if (rezult == 0) {
            tmpGenotype.setModifInfo(currentUser);
            tmpGenotype.setGenotypeID(genotypeID);
            tmpGenotype.setAllele1(allele1);
            tmpGenotype.setAllele2(allele2);
            tmpGenotype.setContainerContents(tmpCC);
            tmpGenotype.setGenotypingRun(tmpGR);
            tmpGenotype.setSnp(tmpS);

            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.update(tmpGenotype);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                if (e.getClass().equals(org.hibernate.exception.ConstraintViolationException.class)) {
                    return 3;
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
                openHibSession();
            }
        }
        return rezult;
    }

    public int deleteGenotype(String selfId) throws Exception {
        if (currentUser == null) {
            return 1;
        }
        Genotype tmpGenotype = getCurrentGenotype();
        if (tmpGenotype == null) {
            return 1;
        }
        if (!selfId.equals(tmpGenotype.getId().toString())) {
            return 1;
        }

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.delete(tmpGenotype);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            currentGenotype = null;
            /*          currentShippedTo = null;
            currentAliquot = null;
            currentSample = null;
            currentPerson = null;
             */
            closeHibSession(); //needs before forward
        }
        return 0;
    }

    public ViewGenotypeManager getViewGenotypeManager() {
        return vGenotypeM;
    }

    public Genotype getCurrentGenotype() {
        return currentGenotype;
    }

    public Long getCurrentGenotypeId() {
        return (currentGenotype == null) ? null : currentGenotype.getId();
    }

    public List getAllGenotypes(int startPosition) throws
            HibernateException {
        String orderClause = " order by myGenotype."
                + ((genotypeSortDir) ? genotypeSortCol : genotypeSortCol + " desc");
        String prefix = "select myGenotype from Genotype as myGenotype";
        Query q = hibSess.createQuery(prefix + orderClause);
        if (viewLimit > 0 && startPosition >= 0) {
            q.setFirstResult(startPosition);
            q.setMaxResults(viewLimit);
        }
        //Println used to be here
        //Println used to be here        
        return q.list();
    }

    public List getAllGenotypes(int startPosition, String ccId) throws
            HibernateException {
        String orderClause = " order by myGenotype."
                + ((genotypeSortDir) ? genotypeSortCol : genotypeSortCol + " desc");
        String prefix = "select myGenotype from Genotype as myGenotype";
        String limit = " WHERE myGenotype.containerContents.containerContentsID = "+ ccId;
        Query q = hibSess.createQuery(prefix + limit + orderClause);
        if (viewLimit > 0 && startPosition >= 0) {
            q.setFirstResult(startPosition);
            q.setMaxResults(viewLimit);
        }
        //Println used to be here
        //Println used to be here
        return q.list();
    }

    public int getAllGenotypesCount() throws HibernateException {
        String prefix = "select count(*) from Genotype as myGenotype";
        Query q;
        q = hibSess.createQuery(prefix);
        int count = ((Long) q.iterate().next()).intValue();
        return count;
    }

    public int getAllGenotypesCount(String ccId) throws HibernateException {
        openHibSession();
        int count = 0;
        String limit="";
        String prefix = "select count(*) from Genotype as myGenotype";
            if (ccId != null && !ccId.equals("null")) {
                limit = " WHERE myGenotype.containerContents.containerContentsID = " + ccId;
            }

        Query q;
        q = hibSess.createQuery(prefix+limit);
        count = ((Long) q.iterate().next()).intValue();
        return count;
    }

    public void setGenotypeSortCol(String sortCol) {
        if (genotypeSortCol.equals(sortCol)) {
            genotypeSortDir = !genotypeSortDir;
        } else {
            genotypeSortCol = sortCol;
            genotypeSortDir = true;
        }
    }

    public String getGenotypeSortId(String sortCol) {
        return (!genotypeSortCol.equals(sortCol)) ? "normal" : (genotypeSortDir) ? "normalu" : "normalo";
    }

    public void setCurrentGenotype(String genotypId) throws HibernateException {
        currentGenotype = (Genotype) getObjectById(Genotype.class, genotypId);
    }
//---Snp-------------------------------------------
//---Snp-------------------------------------------
    private Snp currentSnp = null;
    private String snpSortCol = "snpID";
    private boolean snpSortDir = true; //true - asc; false - desc
    private ViewSnpManager vSnpM = null;

    public int checkSnpId(String snId) {
        setInvalidField("snId");
        //Println used to be here
        if (snId == null || snId.trim().equals("")) {
            return 2;
        }
        try {
            Query q = hibSess.createQuery("select count(*) from Snp Snp " + " where Snp.id= :snId");
            q.setString("snId", snId);
            //Println used to be here
            Integer cnt = ((Long) q.iterate().next()).intValue();
            //Println used to be here
            if (cnt.intValue() > 0) {
                return 3;
            }
            clearInvalidField();
        } catch (Exception ex) {
            //Println used to be here
            return 1;
        }
        return 0;
    }

    public int addSnp(String snpID, String chromosome, String pos, String geneID, String fxnClass, String rsNumber, String majorAllele, String minorAllele, String strand) throws Exception {
        openHibSession();
        int rezult = 0;

        if (currentUser == null) {
            return 1;
        }
        rezult = checkSnpId(snpID); //doesn't check for new SNPS eg. ID = -1

        //check for duplications
        if (rezult == 0) {
            HashMap checkExists = new HashMap();
            checkExists.put("Chromosome", "'" + chromosome + "'");
            checkExists.put("pos", "'" + pos + "'");
            checkExists.put("rsNumber", "'" + rsNumber + "'");
            Snp tmpSnp = (Snp)this.getObjectByUniqueKey(Snp.class, checkExists);
            //no issues
            if (tmpSnp == null){
                rezult = 0;
            }
            //handle duplications, update on duplicate
            else {
                this.updateSnp(tmpSnp.getSnpID(), chromosome, pos, geneID, fxnClass, rsNumber, majorAllele, minorAllele, strand);
                return rezult;
            }
        }
        if (rezult == 0) {
            Snp tmpSnp = new Snp();
            tmpSnp.setCreateInfo(currentUser);
            tmpSnp.setSnpID(snpID);
            tmpSnp.setChromosome(chromosome);
            tmpSnp.setPos(pos);
            tmpSnp.setGeneID(geneID);
            tmpSnp.setFxnClass(fxnClass);
            tmpSnp.setRsNumber(rsNumber);
            tmpSnp.setMajorAllele(majorAllele);
            tmpSnp.setMinorAllele(minorAllele);
            tmpSnp.setStrand(strand);

            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpSnp);
                tx.commit();
                currentSnp = tmpSnp;

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
                openHibSession();
            }
        }
        return rezult;
    }

    public int updateSnp(String snpID, String Chromosome, String pos, String geneID, String fxnClass, String rsNumber, String majorAllele, String minorAllele, String strand) throws Exception {
        openHibSession();
        int rezult = 0;

        if (currentUser == null) {
            return 1;
        }
        Snp tmpSnp = getCurrentSnp();
        
        if (tmpSnp == null) {
            return 1;
        }
        if (!snpID.equals(tmpSnp.getId().toString())) {
            return 1;
        }

        if (rezult == 0) {
            tmpSnp.setModifInfo(currentUser);
            tmpSnp.setSnpID(snpID);
            tmpSnp.setChromosome(Chromosome);
            tmpSnp.setPos(pos);
            tmpSnp.setGeneID(geneID);
            tmpSnp.setFxnClass(fxnClass);
            tmpSnp.setRsNumber(rsNumber);
            tmpSnp.setMajorAllele(majorAllele);
            tmpSnp.setMinorAllele(minorAllele);
            tmpSnp.setStrand(strand);

            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.update(tmpSnp);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public int deleteSnp(String selfId) throws Exception {
        if (currentUser == null) {
            return 1;
        }
        Snp tmpSnp = getCurrentSnp();
        if (tmpSnp == null) {
            return 1;
        }
        if (!selfId.equals(tmpSnp.getId().toString())) {
            return 1;
        }

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.delete(tmpSnp);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            currentSnp = null;
            /*          currentShippedTo = null;
            currentAliquot = null;
            currentSample = null;
            currentPerson = null;
             */
            closeHibSession(); //needs before forward
        }
        return 0;
    }

    public ViewSnpManager getViewSnpManager() {
        return vSnpM;
    }

    public Snp getCurrentSnp() {
        return currentSnp;
    }

    public Long getCurrentSnpId() {
        return (currentSnp == null) ? null : currentSnp.getId();
    }

    public List getAllSnps(int startPosition) throws
            HibernateException {
        String orderClause = " order by mySnp."
                + ((snpSortDir) ? snpSortCol : snpSortCol + " desc");
        String prefix = "select mySnp from Snp as mySnp";
        Query q = hibSess.createQuery(prefix + orderClause);
        if (viewLimit > 0 && startPosition >= 0) {
            q.setFirstResult(startPosition);
            q.setMaxResults(viewLimit);
        }
        ////Println used to be here
        ////Println used to be here

        return q.list();
    }

    public int getAllSnpsCount() throws HibernateException {
        String prefix = "select count(*) from Snp as mySnp";
        Query q;
        q = hibSess.createQuery(prefix);
        int count = ((Long) q.iterate().next()).intValue();
        return count;
    }

    public void setSnpSortCol(String sortCol) {
        if (snpSortCol.equals(sortCol)) {
            snpSortDir = !snpSortDir;
        } else {
            snpSortCol = sortCol;
            snpSortDir = true;
        }
    }

    public String getSnpSortId(String sortCol) {
        return (!snpSortCol.equals(sortCol)) ? "normal" : (snpSortDir) ? "normalu" : "normalo";
    }

    public void setCurrentSnp(String snId) throws HibernateException {
        currentSnp = (Snp) getObjectById(Snp.class, snId);
    }

    /**
     * count how many objects are described by the hash of values
     * @param c1 class of object
     * @param keys string fields need "'"s around the value
     * @return the number of objects or -1 if error
     */
    public int countKey(Class c1, HashMap keys) {
        openHibSession();
        StringBuffer where = new StringBuffer();
        if (!keys.isEmpty()) {
            where.append(" where ");
        }
        Iterator iter = keys.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry pair = (Map.Entry) iter.next();
            where.append(pair.getKey().toString()).append(" = ").append(pair.getValue());
            if (iter.hasNext()) {
                where.append(" and ");
            }
        }
        try {
            Query q = hibSess.createQuery("select tbl from " + c1.getName() + " tbl" + where);
            return q.list().size();
        } catch (Exception ex) {
            return -1;
        }
    }

    /**
     * check whether a value is already in a field of a table
     * @param value
     * @param table
     * @param field
     * @return 2 if field's value is blank or null, 3 if value is already in table,
     * 1 if misc error, 0 if value is unique
     */
    private int checkFieldForUnique(String table, String field, String value, int numResults) {
        setInvalidField(field);

        if (value == null || value.trim().equals("")) {
            return 2;
        }
        try {
            Query q = hibSess.createQuery("select count(*) from " + table + " tab where tab." + field + " = :value");
            q.setString("value", value);

            Long cnt = (Long) q.iterate().next();

            if (cnt.intValue() > numResults) {
                return 3;
            }
            clearInvalidField();
        } catch (Exception ex) {
            return 1;
        }
        return 0;
    }

    /**
     * @param currentUser the currentUser to set
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getUser(String userName, String pswd) {
        openHibSession();
        String pswwd = Util.criptString(pswd);
        Query q;
        q = hibSess.createQuery("from User user" + " where user.loginName = :username" + " and user.password = :pswd");
        q.setString("username", userName);
        q.setString("pswd", pswwd);
        List list = q.list();
        if (list.size() > 0) {
            return (User) list.get(0);
        } else {
            return null;
        }
    }
//---Ethnicity-------------------------------------------
//---Ethnicity-------------------------------------------
    private Ethnicity currentEthnicity = null;
    private String ethnicitySortCol = "ethnicity";
    private boolean ethnicitySortDir = true; //true - asc; false - desc
    private ViewEthnicityManager vEthnicityM = null;

    public int checkEthnicityId(String ethnId) {
        setInvalidField("ethnId");
        //Println used to be here
        if (ethnId == null || ethnId.trim().equals("")) {
            return 2;
        }
        try {
            Query q = hibSess.createQuery("select count(*) from Ethnicity Eth "
                    + " where Eth.id= :ethnId");
            q.setString("ethnId", ethnId);
            //Println used to be here
            Integer cnt = ((Long) q.iterate().next()).intValue();
            //Println used to be here
            if (cnt.intValue() > 0) {
                return 3;
            }
            clearInvalidField();
        } catch (Exception ex) {
            //Println used to be here
            return 1;
        }
        return 0;
    }

    public int addEthnicity(String ethnicityID, String ethnicity) throws Exception {
        int rezult = 0;
        //Println used to be here

        if (currentUser == null) {
            return 1;
        }
        rezult = checkEthnicityId(ethnicityID);
        //Println used to be here
        if (rezult == 0) {
            Ethnicity tmpEthnicity = new Ethnicity();
            tmpEthnicity.setCreateInfo(currentUser);
            tmpEthnicity.setEthnicityID(ethnicityID);
            tmpEthnicity.setEthnicity(ethnicity);

            //Println used to be here
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpEthnicity);
                tx.commit();
                currentEthnicity = tmpEthnicity;

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public int updateEthnicity(String ethnicityID, String ethnicity) throws Exception {
        int rezult = 0;
        //Println used to be here

        //Println used to be here
        if (currentUser == null) {
            return 1;
        }
        Ethnicity tmpEthnicity = (Ethnicity) getObjectById(Ethnicity.class, ethnicityID);
        
        if (tmpEthnicity == null) {
            return 1;
        }
        //Println used to be here
        //Println used to be here
        //Println used to be here
        if (!ethnicityID.equals(tmpEthnicity.getId().toString())) {
            return 1;
        }

        //Println used to be here
        if (rezult == 0) {
            tmpEthnicity.setModifInfo(currentUser);
            tmpEthnicity.setEthnicityID(ethnicityID);
            tmpEthnicity.setEthnicity(ethnicity);

            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.update(tmpEthnicity);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public int deleteEthnicity(String selfId) throws Exception {
        if (currentUser == null) {
            return 1;
        }
        Ethnicity tmpEthnicity = getCurrentEthnicity();
        if (tmpEthnicity == null) {
            return 1;
        }
        if (!selfId.equals(tmpEthnicity.getId().toString())) {
            return 1;
        }

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.delete(tmpEthnicity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            currentEthnicity = null;
            /*          currentShippedTo = null;
            currentAliquot = null;
            currentSample = null;
            currentPerson = null;
             */
            closeHibSession(); //needs before forward
        }
        return 0;
    }

    public ViewEthnicityManager getViewEthnicityManager() {
        return vEthnicityM;
    }

    public Ethnicity getCurrentEthnicity() {
        return currentEthnicity;
    }

    public Long getCurrentEthnicityId() {
        return (currentEthnicity == null) ? null : currentEthnicity.getId();
    }

    public List getAllEthnicities(int startPosition) throws
            HibernateException {
        String orderClause = " order by myEthnicity."
                + ((ethnicitySortDir) ? ethnicitySortCol
                : ethnicitySortCol + " desc");
        String prefix = "select myEthnicity from Ethnicity as myEthnicity";
        Query q = hibSess.createQuery(prefix + orderClause);
        if (viewLimit > 0 && startPosition >= 0) {
            q.setFirstResult(startPosition);
            q.setMaxResults(viewLimit);
        }
        //Println used to be here
        //Println used to be here

        return q.list();
    }

    public int getAllEthnicitiesCount() throws HibernateException {
        String prefix = "select count(*) from Ethnicity as myEthnicity";
        Query q;
        q = hibSess.createQuery(prefix);
        int count = ((Long) q.iterate().next()).intValue();
        return count;
    }

    public void setEthnicitySortCol(String sortCol) {
        if (ethnicitySortCol.equals(sortCol)) {
            ethnicitySortDir = !ethnicitySortDir;
        } else {
            ethnicitySortCol = sortCol;
            ethnicitySortDir = true;
        }
    }

    public String getEthnicitySortId(String sortCol) {
        return (!ethnicitySortCol.equals(sortCol)) ? "normal"
                : (ethnicitySortDir) ? "normalu" : "normalo";
    }

    public void setCurrentEthnicity(String ethnId) throws HibernateException {
        currentEthnicity = (Ethnicity) getObjectById(Ethnicity.class, ethnId);
    }
    //---ShoppingList-------------------------------------------
//---ShoppingList-------------------------------------------
    private String shoppinglistSortCol = "listName";
    private boolean shoppinglistSortDir = true; //true - asc; false - desc
    //private ViewShoppingListManager vShoppingListM = null;

    public int checkShoppingListId(String shopList) {
        setInvalidField("shopList");
        openHibSession();
        //Println used to be here
        if (shopList == null || shopList.trim().equals("")) {
            return 2;
        }
        try {
            Query q = hibSess.createQuery("select count(*) from ShoppingList Sho "
                    + " where Sho.id= :shopList");
            q.setString("shopList", shopList);
            //Println used to be here
            Integer cnt = ((Long) q.iterate().next()).intValue();
            //Println used to be here
            if (cnt.intValue() > 0) {
                return 3;
            }
            clearInvalidField();
        } catch (Exception ex) {
            //Println used to be here
            return 1;
        }
        return 0;
    }

    public int checkShoppingListName(String shopList) {
        openHibSession();
        //Println used to be here
        if (shopList == null || shopList.trim().equals("")) {
            return 2;
        }
        try {
            Query q = hibSess.createQuery("select count(*) from ShoppingList Sho "
                    + " where Sho.listName= :shopList");
            q.setString("shopList", shopList);
            //Println used to be here
            Integer cnt = ((Long) q.iterate().next()).intValue();
            //Println used to be here
            if (cnt.intValue() > 0) {
                return 3;
            }
            clearInvalidField();
        } catch (Exception ex) {
            //Println used to be here
            return 1;
        }
        return 0;
    }

    /**
     * adds a shopping list to DB, sets the currentShoppingList field to this new shoppingList
     * @param listName
     * @param inUseBy
     * @return
     * @throws java.lang.Exception
     */
    public int addShoppingList(String listName, User inUseBy) throws Exception {
        int rezult = 0;
        //Println used to be here
        openHibSession();
        if (currentUser == null) {
            return 1;
        }
        rezult = checkShoppingListName(listName);
        //Println used to be here
        if (rezult == 0) {
            ShoppingList tmpShoppingList = new ShoppingList();
            tmpShoppingList.setCreateInfo(currentUser);
            //tmpShoppingList.setListID(listID);
            tmpShoppingList.setListName(listName);
            tmpShoppingList.setInUseBy(inUseBy);

            //Println used to be here
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpShoppingList);
                tx.commit();
                currentShoppingList = new ShoppingCartList(this, tmpShoppingList.getListID());
            } catch (Exception e) {
                //Println used to be here
//                e.printStackTrace();
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession();//needs before forward
            }
        }
        return rezult;
    }

    public int updateShoppingList(String listName, ShoppingCartList shoppingCart, User inUseBy) throws Exception {
        closeHibSession();
        openHibSession();
        int rezult = 0;
        if (currentUser == null) {
            return 1;
        }
        ShoppingList tmpShoppingList = shoppingCart.getShoppingList();
        if (tmpShoppingList == null) {
            return 1;
        }
        Long listID = tmpShoppingList.getId();
        if (listName == null || listName.equals("")) {
            listName = tmpShoppingList.getListName();
        }
        else {
            rezult = checkShoppingListName(listName);
        }
        if (rezult == 0) {
            tmpShoppingList.setModifInfo(currentUser);
            tmpShoppingList.setListID(listID);
            tmpShoppingList.setListName(listName);
            tmpShoppingList.setInUseBy(inUseBy);

            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.update(tmpShoppingList);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    /**
     * set the inUseBy field of the current list to the current user
     * to prevent other user from using this list while the current user is
     * editing/using it
     * @return 0 if succeeded, !=0 if failed, 1 if error in updating shopping list
     * @throws java.lang.Exception
     */
    public int writeLockCurrentShoppingList() throws Exception {

        return writeLockShoppingList(getCurrentShoppingCartList());
    }

    /**
     * set the inUseBy field of the specified cart's list to the current user
     * to prevent other user from using this list while the current user is
     * editing/using it
     * @param shoppingCartList
     * @return 0 if succeeded, !=0 if failed, 1 if error in updating shopping list
     * @throws java.lang.Exception
     */
    public int writeLockShoppingList(ShoppingCartList shoppingCartList) throws Exception {
        if (currentUser == null) {
            return 2;
        }
        if (shoppingCartList == null) {
            return 3;
        }
        if (shoppingCartList.getShoppingList() == null) {
            return 4;
        }
        if (shoppingCartList.getShoppingList().getInUseBy() != null) {
            return 5;
        }

        return updateShoppingList(null, shoppingCartList, currentUser);
    }

    /**
     * release the write lock on the current shopping list that would otherwise
     * prevent other user from using this list while the current user is
     * editing/using it
     * @return 0 if succeeded, !=0 if failed
     * @throws java.lang.Exception
     */
    public int releaseLockCurrentShoppingList() throws Exception {
        return releaseLockShoppingList(getCurrentShoppingCartList());

    }

    /**
     * release the write lock on the specified cart's shopping list that would otherwise
     * prevent other user from using this list while the current user is
     * editing/using it
     * @param shoppingCartList
     * @return 0 if succeeded, !=0 if failed
     * @throws java.lang.Exception
     */
    public int releaseLockShoppingList(ShoppingCartList shoppingCartList) throws Exception {
        if (currentUser == null) {
            return 1;
        }
        if (shoppingCartList == null) {
            return 1;
        }
        if (shoppingCartList.getShoppingList() == null) {
            return 1;
        }

        return updateShoppingList(null, shoppingCartList, null);
    }

    public int deleteShoppingList(String selfId) throws Exception {
        openHibSession();
        if (currentUser == null) {
            return 1;
        }
        ShoppingList tmpShoppingList = getCurrentShoppingCartList().getShoppingList();
        if (tmpShoppingList == null) {
            return 1;
        }
        if (!selfId.equals(tmpShoppingList.getId().toString())) {
            return 1;
        }

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.delete(tmpShoppingList);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            currentShoppingList = null;
            closeHibSession(); //needs before forward
        }
        return 0;
    }

    /*    public ViewShoppingListManager getViewShoppingListManager(){
    return vShoppingListM;
    }
     */
    public ShoppingCartList getCurrentShoppingCartList() {
        return currentShoppingList;
    }

    public ShoppingList getCurrentShoppingList() {
        if (currentShoppingList == null) {
            return null;
        }
        return (currentShoppingList.getShoppingList() == null) ? null
                : currentShoppingList.getShoppingList();
    }

    public void setCurrentShoppingCartList(ShoppingCartList cartList) {
        currentShoppingList = cartList;
    }

    public Long getCurrentContentsShoppingListId() {
        return (currentShoppingList == null) ? null : currentShoppingList.getShoppingList().getListID();
    }

    public List getAllShoppingLists(int startPosition) throws
            HibernateException {
        openHibSession();
        String orderClause = " order by myShoppingList."
                + ((shoppinglistSortDir) ? shoppinglistSortCol
                : shoppinglistSortCol + " desc");
        String prefix = "select myShoppingList from ShoppingList as myShoppingList";
        Query q = hibSess.createQuery(prefix + orderClause);
        if (viewLimit > 0 && startPosition >= 0) {
            q.setFirstResult(startPosition);
            q.setMaxResults(viewLimit);
        }
        //Println used to be here
        //Println used to be here

        return q.list();
    }

    public int getAllShoppingListsCount() throws HibernateException {
        openHibSession();
        String prefix = "select count(*) from ShoppingList as myShoppingList";
        Query q;
        q = hibSess.createQuery(prefix);
        int count = ((Long) q.iterate().next()).intValue();
        return count;
    }

    public void setShoppingListSortCol(String sortCol) {
        if (shoppinglistSortCol.equals(sortCol)) {
            shoppinglistSortDir = !shoppinglistSortDir;
        } else {
            shoppinglistSortCol = sortCol;
            shoppinglistSortDir = true;
        }
    }

    public String getShoppingListSortId(String sortCol) {
        return (!shoppinglistSortCol.equals(sortCol)) ? "normal"
                : (shoppinglistSortDir) ? "normalu" : "normalo";
    }

    public void setCurrentShoppingList(Long shopListID) throws Exception {
        if (shopListID == null) {
            currentShoppingList = null;
        } else {
            //get shopping cart with appropriate
            currentShoppingList = new ShoppingCartList(this, shopListID);
        }
    }

    /**
     * updates current list based on an item view page with tick boxes for to indicate an
     * item should be kept in/added to a list.
     * deletes items that were ticked on page load but aren't on form submission
     * adds items that weren't ticked on page load but were on form submission
     * @param request
     * @throws java.lang.Exception
     */
    public void updateList(HttpServletRequest request) throws Exception {

        // lock shopping list
        writeLockCurrentShoppingList();

        Map valueMap = request.getParameterMap();
        Iterator iter = null;
        String id = null;

        if (valueMap.get("containerListWereChecked") != null && valueMap.get("containerListChecked") != null) {
            // get the IDs that were originally selected
            ArrayList<String> containerListWereChecked = new ArrayList(Arrays.asList((String[]) valueMap.get("containerListWereChecked")));
            // get the ID that are now selected
            ArrayList<String> containerListChecked = new ArrayList(Arrays.asList((String[]) valueMap.get("containerListChecked")));

            // remove the IDs that were originally selected but aren't now
            iter = containerListWereChecked.iterator();
            while (iter.hasNext()) {
                id = iter.next().toString();
                if (!containerListChecked.contains(id)) {
                    currentShoppingList.removeObjectsByContainer(id);
                }
            }
            // add the IDs that were not originally selected but are now
            iter = containerListChecked.iterator();
            while (iter.hasNext()) {
                id = iter.next().toString();
                if (!containerListWereChecked.contains(id)) {
                    currentShoppingList.addObjectsByContainer(id);
                }


            }
        }// if none selected to start
        else if (valueMap.get("containerListChecked") != null) {
            // get the ID that are now selected
            ArrayList<String> containerListChecked = new ArrayList(Arrays.asList((String[]) valueMap.get("containerListChecked")));

            iter = containerListChecked.iterator();
            while (iter.hasNext()) {
                id = iter.next().toString();
                currentShoppingList.addObjectsByContainer(id);
            }
        } // everything is deselected
        else if (valueMap.get("containerListWereChecked") != null) {
            // get the IDs that were originally selected
            ArrayList<String> containerListWereChecked = new ArrayList(Arrays.asList((String[]) valueMap.get("containerListWereChecked")));

            // remove all the IDs that were originally selected
            iter = containerListWereChecked.iterator();
            while (iter.hasNext()) {
                id = iter.next().toString();
                currentShoppingList.removeObjectsByContainer(id);
            }
        }


        if (valueMap.get("contentListWereChecked") != null && valueMap.get("contentListChecked") != null) {
            // get the IDs that were originally selected
            ArrayList<String> contentListWereChecked = new ArrayList(Arrays.asList((String[]) valueMap.get("contentListWereChecked")));
            // get the ID that are now selected
            ArrayList<String> contentListChecked = new ArrayList(Arrays.asList((String[]) valueMap.get("contentListChecked")));

            // remove the IDs that were originally selected but aren't now
            iter = contentListWereChecked.iterator();
            while (iter.hasNext()) {
                id = iter.next().toString();
                if (!contentListChecked.contains(id)) {
                    currentShoppingList.removeObjectsByContent(id);
                }
            }
            // add the IDs that were not originally selected but are now
            iter = contentListChecked.iterator();
            while (iter.hasNext()) {
                id = iter.next().toString();
                if (!contentListWereChecked.contains(id)) {
                    currentShoppingList.addObjectsByContent(id);
                }
            }
        }// if none selected to start
        else if (valueMap.get("contentListChecked") != null) {
            // get the ID that are now selected
            ArrayList<String> contentListChecked = new ArrayList(Arrays.asList((String[]) valueMap.get("contentListChecked")));

            iter = contentListChecked.iterator();
            while (iter.hasNext()) {
                id = iter.next().toString();
                currentShoppingList.addObjectsByContent(id);
            }
        } // everything is deselected
        else if (valueMap.get("contentListWereChecked") != null) {
            // get the IDs that were originally selected
            ArrayList<String> contentListWereChecked = new ArrayList(Arrays.asList((String[]) valueMap.get("contentListWereChecked")));

            // remove all the IDs that were originally selected
            iter = contentListWereChecked.iterator();
            while (iter.hasNext()) {
                id = iter.next().toString();
                currentShoppingList.removeObjectsByContent(id);
            }
        }

        if (valueMap.get("sampleListWereChecked") != null && valueMap.get("sampleListChecked") != null) {
            // get the IDs that were originally selected
            ArrayList<String> sampleListWereChecked = new ArrayList(Arrays.asList((String[]) valueMap.get("sampleListWereChecked")));
            // get the ID that are now selected
            ArrayList<String> sampleListChecked = new ArrayList(Arrays.asList((String[]) valueMap.get("sampleListChecked")));

            // remove the IDs that were originally selected but aren't now
            iter = sampleListWereChecked.iterator();
            while (iter.hasNext()) {
                id = iter.next().toString();
                if (!sampleListChecked.contains(id)) {
                    currentShoppingList.removeObjectsBySample(id);
                }
            }
            // add the IDs that were not originally selected but are now
            iter = sampleListChecked.iterator();
            while (iter.hasNext()) {
                id = iter.next().toString();
                if (!sampleListWereChecked.contains(id)) {
                    currentShoppingList.addObjectsBySample(id);
                }
            }
        }// if none selected to start
        else if (valueMap.get("sampleListChecked") != null) {
            // get the ID that are now selected
            ArrayList<String> sampleListChecked = new ArrayList(Arrays.asList((String[]) valueMap.get("sampleListChecked")));

            iter = sampleListChecked.iterator();
            while (iter.hasNext()) {
                id = iter.next().toString();
                currentShoppingList.addObjectsBySample(id);
            }
        } // everything is deselected
        else if (valueMap.get("sampleListWereChecked") != null) {
            // get the IDs that were originally selected
            ArrayList<String> sampleListWereChecked = new ArrayList(Arrays.asList((String[]) valueMap.get("sampleListWereChecked")));

            // remove all the IDs that were originally selected
            iter = sampleListWereChecked.iterator();
            while (iter.hasNext()) {
                id = iter.next().toString();
                currentShoppingList.removeObjectsBySample(id);
            }
        }

        if (valueMap.get("subjectListWereChecked") != null && valueMap.get("subjectListChecked") != null) {
            // get the IDs that were originally selected
            ArrayList<String> subjectListWereChecked = new ArrayList(Arrays.asList((String[]) valueMap.get("subjectListWereChecked")));
            // get the ID that are now selected
            ArrayList<String> subjectListChecked = new ArrayList(Arrays.asList((String[]) valueMap.get("subjectListChecked")));

            // remove the IDs that were originally selected but aren't now
            iter = subjectListWereChecked.iterator();
            while (iter.hasNext()) {
                id = iter.next().toString();
                if (!subjectListChecked.contains(id)) {
                    currentShoppingList.removeObjectsBySubject(id);
                }
            }
            // add the IDs that were not originally selected but are now
            iter = subjectListChecked.iterator();
            while (iter.hasNext()) {
                id = iter.next().toString();
                if (!subjectListWereChecked.contains(id)) {
                    currentShoppingList.addObjectsBySubject(id);
                }
            }
        }// if none selected to start
        else if (valueMap.get("subjectListChecked") != null) {
            // get the ID that are now selected
            ArrayList<String> subjectListChecked = new ArrayList(Arrays.asList((String[]) valueMap.get("subjectListChecked")));

            iter = subjectListChecked.iterator();
            while (iter.hasNext()) {
                id = iter.next().toString();
                currentShoppingList.addObjectsBySubject(id);
            }
        } // everything is deselected
        else if (valueMap.get("subjectListWereChecked") != null) {
            // get the IDs that were originally selected
            ArrayList<String> subjectListWereChecked = new ArrayList(Arrays.asList((String[]) valueMap.get("subjectListWereChecked")));

            // remove all the IDs that were originally selected
            iter = subjectListWereChecked.iterator();
            while (iter.hasNext()) {
                id = iter.next().toString();
                currentShoppingList.removeObjectsBySubject(id);
            }
        }

        // unlock shopping list
        releaseLockCurrentShoppingList();
    }

    /**
     * adjust volumes of all containerContents of the indicated list
     * @param volume how much to do add/subtract/set
     * @param volumeAction what to do to the volume
     * @param listID the list contents to change the volumes for
     * @return 0 if successful, 1 otherwise
     */
    public int editBulkListContainerContents(String volume, String volumeAction, String listID) {
        try {
            openHibSession();
            // get all list's contents
            String prefix = "select myCC from ContainerContent as myCC, ShoppingListContainerContent as mySL "
                    + "where mySL.listID = " + listID + " and mySL.containerContentsID=myCC.containerContentsID";
            Query q = hibSess.createQuery(prefix);
            //Println used to be here
            List conts = q.list();
            //Println used to be here
            ListIterator iter = conts.listIterator();

            int rezult = 0;
            if (currentUser == null) {
                return 1;
            }
            // adjust every content volume
            ContainerContent toAdjust = null;
            while (iter.hasNext()) {
                toAdjust = (ContainerContent) iter.next();
                // don't make any changes except 'setting' if the volume is unknown (-1)
                boolean volKnown = false;
                if (new Double(toAdjust.getVolume()).compareTo(new Double("-1.0")) > 0) {
                    volKnown = true;
                }

                if (toAdjust == null) {
                    return 1;
                }

                if (rezult == 0) {
                    toAdjust.setModifInfo(currentUser);
                    if (volumeAction != null && volume != null) {
                        if (volumeAction.equals("set")) {
                            toAdjust.setVolume(volume);
                        }
                        if (volumeAction.equals("add") && volKnown) {
                            double orig = Double.parseDouble(toAdjust.getVolume());
                            double add = Double.parseDouble(volume);
                            String newVol = String.valueOf((add + orig));
                            toAdjust.setVolume(newVol);
                        }
                        if (volumeAction.equals("subtract") && volKnown) {
                            double orig = Double.parseDouble(toAdjust.getVolume());
                            double sub = Double.parseDouble(volume);
                            String newVol = String.valueOf((orig - sub));
                            toAdjust.setVolume(newVol);
                        }
                    }

                    Transaction tx = null;
                    try {
                        if (!hibSess.isOpen()) {
                            openHibSession();
                        }
                        tx = hibSess.beginTransaction();
                        hibSess.update(toAdjust);
                        tx.commit();
                    } catch (Exception e) {
                        if (tx != null) {
                            tx.rollback();
                        }
                        throw e;
                    } finally {
                        closeHibSession(); //needs before forward
                    }
                }

            }
        } catch (HibernateException he) {
//            he.printStackTrace();
            System.err.println("Hibernate error in cloning. " + he.getLocalizedMessage());
            return 1;
        } catch (Exception e) {
//            e.printStackTrace();
            System.err.println("Error in cloning. " + e.getLocalizedMessage());
            return 1;
        }
        return 0;
    }

    /**
     * adjust volumes of all containerContents of the indicated list
     * @param volume how much to do add/subtract/set
     * @param volumeAction what to do to the volume
     * @param commentToAdd comment to add to the original comment, null or blank won't be added
     * @param listID the list contents to change the volumes for
     * @return 0 if successful, 1 otherwise
     */
    public int editBulkListContainerContents(String volume, String volumeAction, String commentToAdd, String listID) {
        try {
            openHibSession();
            // get all list's contents
            String prefix = "select myCC from ContainerContent as myCC, ShoppingListContainerContent as mySL "
                    + "where mySL.listID = " + listID + " and mySL.containerContentsID=myCC.containerContentsID";
            Query q = hibSess.createQuery(prefix);
            //Println used to be here
            List conts = q.list();
            //Println used to be here
            ListIterator iter = conts.listIterator();

            int rezult = 0;
            if (currentUser == null) {
                return 1;
            }
            // adjust every content volume
            ContainerContent toAdjust = null;
            while (iter.hasNext()) {
                toAdjust = (ContainerContent) iter.next();
                // don't make any changes except 'setting' if the volume is unknown (-1)
                boolean volKnown = false;
                if (new Double(toAdjust.getVolume()).compareTo(new Double("-1.0")) > 0) {
                    volKnown = true;
                }

                if (toAdjust == null) {
                    return 1;
                }

                if (rezult == 0) {
                    toAdjust.setModifInfo(currentUser);
                    // update volume
                    if (volumeAction != null && volume != null) {
                        if (volumeAction.equals("set")) {
                            toAdjust.setVolume(volume);
                        }
                        if (volumeAction.equals("add") && volKnown) {
                            double orig = Double.parseDouble(toAdjust.getVolume());
                            double add = Double.parseDouble(volume);
                            String newVol = String.valueOf((add + orig));
                            toAdjust.setVolume(newVol);
                        }
                        if (volumeAction.equals("subtract") && volKnown) {
                            double orig = Double.parseDouble(toAdjust.getVolume());
                            double sub = Double.parseDouble(volume);
                            String newVol = String.valueOf((orig - sub));
                            toAdjust.setVolume(newVol);
                        }
                    }

                    // update comments
                    if (commentToAdd != null && !commentToAdd.trim().equals("")
                            && !commentToAdd.trim().equals("null")) {
                        // don't want any apostrophes
                        commentToAdd = commentToAdd.replace("'", "");
                        commentToAdd = commentToAdd.trim();

                        // want a period at the end of ever chunk of comment
                        String currentComment = toAdjust.getComments();
                        if (currentComment != null && !currentComment.trim().equals("")) {
                            currentComment = currentComment.trim();
                            if (!(currentComment.charAt(currentComment.length() - 1) == '.')) {
                                currentComment = currentComment + ". ";
                            } else {
                                currentComment = currentComment + " ";
                            }
                        }
                        if (currentComment == null) {
                            currentComment = "";
                        }

                        if (!(commentToAdd.charAt(commentToAdd.length() - 1) == '.')) {
                            commentToAdd = commentToAdd + ". ";
                        } else {
                            commentToAdd = commentToAdd + " ";
                        }

                        toAdjust.setComments(currentComment + commentToAdd);
                    }

                    Transaction tx = null;
                    try {
                        if (!hibSess.isOpen()) {
                            openHibSession();
                        }
                        tx = hibSess.beginTransaction();
                        hibSess.update(toAdjust);
                        tx.commit();
                    } catch (Exception e) {
                        if (tx != null) {
                            tx.rollback();
                        }
                        throw e;
                    } finally {
                        closeHibSession(); //needs before forward
                    }
                }

            }
        } catch (HibernateException he) {
//            he.printStackTrace();
            System.err.println("Hibernate error in cloning. " + he.getLocalizedMessage());
            return 1;
        } catch (Exception e) {
//            e.printStackTrace();
            System.err.println("Error in cloning. " + e.getLocalizedMessage());
            return 1;
        }
        return 0;
    }

    /**
     *  get the lowest volume of all the contents in the designated list
     * @param listID ID of the designated list
     * @return lowest volume above 0 or -1 of all are below 0 (-1.0 = unknown), null if error
     */
    public Double getListLowestVolume(String listID) {
        try {
            openHibSession();
            // get lowest volume of list's contents
            String prefix = "select distinct myCC.volume from ContainerContent as myCC, ShoppingListContainerContent as mySL "
                    + "where mySL.listID = " + listID + " and mySL.containerContentsID=myCC.containerContentsID";
            Query q = UserHttpSess.hibSess.createQuery(prefix);
            q.setMaxResults(2);
            List lowestVols = q.list();
            // there should only be 2 kinds of volumes, -1 and real positive numbers
            if ((new Double(lowestVols.get(0).toString())).compareTo(new Double("-1.0")) > 0) {
                return (new Double(lowestVols.get(0).toString()));
            } else if ((new Double(lowestVols.get(1).toString())).compareTo(new Double("-1.0")) > 0) {
                return (new Double(lowestVols.get(1).toString()));
            } else {
                return null;
            }

        } catch (HibernateException he) {
//            he.printStackTrace();
            System.err.println("Hibernate error in checking volume. " + he.getLocalizedMessage());
            return null;
        } catch (Exception e) {
//            e.printStackTrace();
            System.err.println("Error in checking volume. " + e.getLocalizedMessage());
            return null;
        }
    }

    /**
     *  checks if the designated list has any unknown volumes (value = -1 or blank) for its contents
     * @param listID ID of the designated list
     * @return true if has unknown volumes (value = -1 or blank) or false if all values are >=0
     * @throws org.hibernate.HibernateException
     */
    public boolean getListUnknownVolume(String listID) throws HibernateException {
        openHibSession();
        // get lowest volume of list's contents
        String prefix = "select min(myCC.volume) from ContainerContent as myCC, ShoppingListContainerContent as mySL "
                + "where mySL.listID = " + listID + " and mySL.containerContentsID=myCC.containerContentsID";
        Query q = UserHttpSess.hibSess.createQuery(prefix);
        Double lowestVol = (new Double(q.uniqueResult().toString()));
        if (lowestVol.compareTo(new Double("-1.0")) <= 0) {
            return true;
        }
        return false;
    }

    /**
     * "select "+field+" from ContainerContent Con where Con."+relToCC
     * @param field
     * @param relToCC
     * @return
     */
    public List getFieldFromContainerContent(String field, String relToCC) {
        openHibSession();//containerContentsID Con.sample.sampleID="+sampleID
        Query q = hibSess.createQuery("select " + field + " from ContainerContent Con where Con." + relToCC);
        return q.list();
    }

    public void setIdsToLoad(ArrayList ids) {
        idsToLoad = ids;
    }

    public ArrayList getIdsToLoad() {
        return idsToLoad;
    }

    public int loadIDsToCurrentList(ArrayList<String> ids, String idType) {
        Iterator iter = ids.iterator();
        // for every id, add the object itself, and the corresponding other objects
        try {
            if (idType.equals("samples")) {
                while (iter.hasNext()) {
                    currentShoppingList.addObjectsBySample((String) iter.next());
                }
            }
            if (idType.equals("subjects")) {
                while (iter.hasNext()) {
                    currentShoppingList.addObjectsBySubject((String) iter.next());
                }
            }
            if (idType.equals("containers")) {
                while (iter.hasNext()) {
                    currentShoppingList.addObjectsByContainer((String) iter.next());
                }
            }
            if (idType.equals("contents")) {
                while (iter.hasNext()) {
                    currentShoppingList.addObjectsByContent((String) iter.next());
                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
            //Println used to be here
            return -1;
        }

        return 0;
    }

    public boolean inSamplesList(String id) {
        if (currentShoppingList == null) {
            return false;
        }
        if (currentShoppingList.getSampleList() == null) {
            return false;
        }
        return currentShoppingList.getSampleList().contains(id);
    }

    public boolean inSubjectsList(String id) {
        if (currentShoppingList == null) {
            return false;
        }
        if (currentShoppingList.getSubjectList() == null) {
            return false;
        }
        return currentShoppingList.getSubjectList().contains(id);
    }

    public boolean inContainerContentsList(String id) {
        if (currentShoppingList == null) {
            return false;
        }
        if (currentShoppingList.getContainerContentsList() == null) {
            return false;
        }
        return currentShoppingList.getContainerContentsList().contains(id);
    }

    public boolean inContainersList(String id) {
        if (currentShoppingList == null) {
            return false;
        }
        if (currentShoppingList.getContainerList() == null) {
            return false;
        }
        return currentShoppingList.getContainerList().contains(id);
    }

    public boolean inSampleDocumentsList(String id) {
        if (currentShoppingList == null) {
            return false;
        }
        if (currentShoppingList.getContainerList() == null) {
            return false;
        }
        return currentShoppingList.getContainerList().contains(id);
    }

    public void addToSamplesList(String id) throws Exception {
        //Println used to be here
        currentShoppingList.addSample(id);
    }

    public void clearSamplesList(String id) throws Exception {
        currentShoppingList.clearSamples();
    }

    public boolean isSamplesList(String id) {
        return ((currentShoppingList.getSampleList() == null || currentShoppingList.getSampleList().isEmpty()) ? false : true);
    }

    public int getAllSamplesListCount() {
        return currentShoppingList.getSampleList().size();
    }

    // returns sample ids in string format
    public ArrayList getAllSampleObjectsInList() {
        Iterator iter = currentShoppingList.SampleIterator();
        ArrayList sampleObjectsList = new ArrayList();
        Sample sample = null;
        while (iter.hasNext()) {
            sample = (Sample) getObjectById(Sample.class, iter.next().toString());
            sampleObjectsList.add(sample);
        }
        return sampleObjectsList;
    }

    public int getAllContentsListCount() {
        return currentShoppingList.getContainerContentsList().size();
    }

    // returns list of container contents
    public ArrayList getAllContentObjectsInList() {
        Iterator iter = currentShoppingList.containerContentsIterator();
        ArrayList contentObjectsList = new ArrayList();
        ContainerContent content = null;
        while (iter.hasNext()) {
            content = (ContainerContent) getObjectById(ContainerContent.class, iter.next().toString());
            contentObjectsList.add(content);
        }
        return contentObjectsList;
    }

    /**
     * sort the contents list according to the current containercontentSortCol
     * will get ccIDs from database sorted and reassign the current ccShoppingList to results
     * @return 0 if ok, -1 if no current shoppingList
     * @throws HibernateException
     */
    public int sortContentsList() throws HibernateException {
        Long listID = currentShoppingList.getShoppingList().getId();
        if (currentShoppingList == null || currentShoppingList.getShoppingList() == null) {
            return -1;
        }
        openHibSession();
        String sort = (containercontentSortCol.equals("container")) ? "myContainerContent.container.containerName" + ((containercontentSortDir) ? "" : " desc") + ", myContainerContent.row"
                : (containercontentSortCol.equals("sample")) ? "myContainerContent.sample.sampleName" + ((containercontentSortDir) ? "" : " desc") + ", myContainerContent.container.containerName"
                : (containercontentSortCol.equals("parent")) ? "parentCont.containerName" + ((containercontentSortDir) ? "" : " desc") + ", myContainerContent.container.containerName"
                : "myContainerContent." + containercontentSortCol;
        String orderClause = " order by "
                + ((containercontentSortDir) ? sort : sort + " desc");

        String prefix = "select myContainerContent,parent,parentCont"
                + " FROM ContainerContent as myContainerContent"
                + " left outer join fetch myContainerContent.parent as parent"
                + " left outer join fetch myContainerContent.parent.container as parentCont "
                + " , ShoppingListContainerContent as myShoppingList"
                + " WHERE myContainerContent.containerContentsID = myShoppingList.containerContentsID "
                + " AND myContainerContent.containerContentsID IN "
                + " (SELECT myShoppingList.containerContentsID FROM ShoppingListContainerContent as myShoppingList"
                + "  WHERE myShoppingList.listID =:listID)";
        //" AND myShoppingList.listID =:listID";
        Query q = hibSess.createQuery(prefix + orderClause);
        q.setLong("listID", listID);
        // get IDs and remove duplicates
        Iterator iter = q.list().iterator();
        ArrayList ids = new ArrayList();
        String id = "-1";
        while (iter.hasNext()) {
            id = ((ContainerContent) iter.next()).getContainerContentsID();
            if (!ids.contains(id)) {
                ids.add(id);
            }
        }
        currentShoppingList.setContainerContentsList(ids);
        return 0;
    }

    /**
     * get the elements of the active list that match the search criteria
     *
     * @return list of containerContents objects, null if no current shoppingList
     * @throws HibernateException
     */
    public List getContentsListSearched() throws HibernateException {
        Long listID = currentShoppingList.getShoppingList().getId();
        if (currentShoppingList == null || currentShoppingList.getShoppingList() == null) {
            return null;
        }
        openHibSession();
        String sort = (containercontentSortCol.equals("container")) ? "myContainerContent.container.containerName" + ((containercontentSortDir) ? "" : " desc") + ", myContainerContent.row"
                : (containercontentSortCol.equals("sample")) ? "myContainerContent.sample.sampleName" + ((containercontentSortDir) ? "" : " desc") + ", myContainerContent.container.containerName"
                : (containercontentSortCol.equals("parent")) ? "parentCont.containerName" + ((containercontentSortDir) ? "" : " desc") + ", myContainerContent.container.containerName"
                : "myContainerContent." + containercontentSortCol;
        String orderClause = " order by "
                + ((containercontentSortDir) ? sort : sort + " desc");

        String prefix = "select myContainerContent,parent,parentCont"
                + " FROM ContainerContent as myContainerContent"
                + " left outer join fetch myContainerContent.parent as parent"
                + " left outer join fetch myContainerContent.parent.container as parentCont "
                + " , ShoppingListContainerContent as myShoppingList ";
        String suffix = "  myContainerContent.containerContentsID = myShoppingList.containerContentsID "
                + " AND myContainerContent.containerContentsID IN "
                + " (SELECT myShoppingList.containerContentsID FROM ShoppingListContainerContent as myShoppingList"
                + "  WHERE myShoppingList.listID =:listID) ";
        String limit = "";
        // only one search should be active at a time
        if (filterList != null || searchList != null) {
            // if search term is one of object's fields, only getting a " and x=y" clause
            limit = (filterList != null) ? filterList.getContainerContentString() : searchList.getContainerContentString();
            limit = limit + " AND ";
            if (limit.indexOf("where") < 0 && limit.indexOf("WHERE") < 0) {
                suffix = " WHERE " + suffix + limit;
                limit = "";
            }
        } else {
            limit = " WHERE ";
        }

        Query q = hibSess.createQuery(prefix + limit + suffix + orderClause);
        q.setLong("listID", listID);
        List results = q.list();

        //remove duplicates but keep order
        LinkedHashSet set = new LinkedHashSet(results);
        results = new ArrayList(set);
        return results;
    }

    /**
     * get the elements of the active list that match the search criteria
     *
     * @return list of container objects, null if no current shoppingList
     * @throws HibernateException
     */
    public List getContainersListSearched() throws HibernateException {
        Long listID = currentShoppingList.getShoppingList().getId();
        if (currentShoppingList == null || currentShoppingList.getShoppingList() == null) {
            return null;
        }
        openHibSession();


        String sort = (containerSortCol.equals("containerType")) ? "contType.description" + ((containerSortDir) ? "" : " desc") + ", myContainer.containerName"
                : (containerSortCol.equals("freezer")) ? "free.description" + ((containerSortDir) ? "" : " desc") + ", myContainer.containerName"
                : (containerSortCol.equals("shippedTo")) ? "ship.description" + ((containerSortDir) ? "" : " desc") + ", myContainer.containerName"
                : "myContainer." + containerSortCol;
        String orderClause = " order by "
                + ((containerSortDir) ? sort : sort + " desc");

        String prefix = "select distinct myContainer, free, contType  from Container as myContainer"
                + " left outer join fetch myContainer.freezer as free"
                + " left outer join fetch myContainer.containerType as contType"
                + " , ShoppingListContainer as myShoppingList ";
        String suffix = "  myContainer.containerID = myShoppingList.containerID "
                + " AND myContainer.containerID IN "
                + " (SELECT myShoppingList.containerID FROM ShoppingListContainer as myShoppingList"
                + "  WHERE myShoppingList.listID =:listID) ";

        String limit = "";
        // only one search should be active at a time
        if (filterList != null || searchList != null) {
            // if search term is one of object's fields, only getting a " and x=y" clause
            limit = (filterList != null) ? filterList.getContainerString() : searchList.getContainerString();
            limit = limit + " AND ";
            if (limit.indexOf("where") < 0 && limit.indexOf("WHERE") < 0) {
                suffix = " WHERE " + suffix + limit;
                limit = "";
            }
        } else {
            limit = " WHERE ";
        }

        Query q = hibSess.createQuery(prefix + limit + suffix + orderClause);
        q.setLong("listID", listID);
        List results = q.list();
        /*  not sure I need to do this:
        //remove duplicates
        HashSet set = new HashSet(results);
        results = new ArrayList(set);*/
        return results;
    }

    /**
     * get the elements of the active list that match the search criteria
     *
     * @return list of subject objects, null if no current shoppingList
     * @throws HibernateException
     */
    public List getSubjectsListSearched() throws HibernateException {
        Long listID = currentShoppingList.getShoppingList().getId();
        if (currentShoppingList == null || currentShoppingList.getShoppingList() == null) {
            return null;
        }
        openHibSession();

        String sort =
                (subjectSortCol.equals("cohort")) ? "coho.description" + ((subjectSortDir) ? "" : " desc") + ", mySubject.subjectName"
                : (subjectSortCol.equals("father")) ? "mySubject.fatherName" + ((subjectSortDir) ? "" : " desc") + ", mySubject.subjectName"
                : (subjectSortCol.equals("mother")) ? "mySubject.motherName" + ((subjectSortDir) ? "" : " desc") + ", mySubject.subjectName"
                : "mySubject." + subjectSortCol;
        String orderClause = " order by "
                + ((subjectSortDir) ? sort : sort + " desc");

        String prefix = "select distinct mySubject, coho from Subject as mySubject"
                + " join fetch mySubject.cohort as coho"
                + " , ShoppingListSubject as myShoppingList ";

        String suffix = "  mySubject.subjectID = myShoppingList.subjectID "
                + " AND mySubject.subjectID IN "
                + " (SELECT myShoppingList.subjectID FROM ShoppingListSubject as myShoppingList"
                + "  WHERE myShoppingList.listID =:listID) ";

        String limit = "";
        // only one search should be active at a time
        if (filterList != null || searchList != null) {
            // if search term is one of object's fields, only getting a " and x=y" clause
            limit = (filterList != null) ? filterList.getSubjectString() : searchList.getSubjectString();
            limit = limit + " AND ";
            if (limit.indexOf("where") < 0 && limit.indexOf("WHERE") < 0) {
                suffix = " WHERE " + suffix + limit;
                limit = "";
            }
        } else {
            limit = " WHERE ";
        }

        Query q = hibSess.createQuery(prefix + limit + suffix + orderClause);
        q.setLong("listID", listID);
        List results = q.list();
        /*  not sure I need to do this:
        //remove duplicates
        HashSet set = new HashSet(results);
        results = new ArrayList(set);*/
        return results;
    }

    public int getAllContainersListCount() {
        return currentShoppingList.getContainerList().size();
    }

    // returns list of container contents
    public ArrayList getAllContainerObjectsInList() {
        Iterator iter = currentShoppingList.containerIterator();
        ArrayList containerObjectsList = new ArrayList();
        Container container = null;
        while (iter.hasNext()) {
            container = (Container) getObjectById(Container.class, iter.next().toString());
            containerObjectsList.add(container);
        }
        return containerObjectsList;
    }

    /**
     * sort the containers list according to the current containerSortCol
     * will get containerIDs from database sorted and reassign the current containerShoppingList to results
     * @return 0 if ok, -1 if no current shoppingList
     * @throws HibernateException
     */
    public int sortContainersList() throws HibernateException {
        Long listID = currentShoppingList.getShoppingList().getId();
        if (currentShoppingList == null || currentShoppingList.getShoppingList() == null) {
            return -1;
        }
        openHibSession();

        String sort = (containerSortCol.equals("containerType")) ? "contType.description" + ((containerSortDir) ? "" : " desc") + ", myContainer.containerName"
                : (containerSortCol.equals("freezer")) ? "free.description" + ((containerSortDir) ? "" : " desc") + ", myContainer.containerName"
                : (containerSortCol.equals("shippedTo")) ? "ship.description" + ((containerSortDir) ? "" : " desc") + ", myContainer.containerName"
                : "myContainer." + containerSortCol;
        String orderClause = " order by "
                + ((containerSortDir) ? sort : sort + " desc");

        String prefix = "select myContainer, free, contType "
                + " FROM Container as myContainer"
                + " left outer join fetch myContainer.freezer as free"
                + " left outer join fetch myContainer.containerType as contType"
                + " , ShoppingListContainer as myShoppingList"
                + " WHERE myContainer.containerID = myShoppingList.containerID "
                + " AND myContainer.containerID IN "
                + " (SELECT myShoppingList.containerID FROM ShoppingListContainer as myShoppingList"
                + "  WHERE myShoppingList.listID =:listID)";
        //" AND myShoppingList.listID =:listID";
        Query q = hibSess.createQuery(prefix + orderClause);
        q.setLong("listID", listID);
        // get IDs and remove duplicates
        Iterator iter = q.list().iterator();
        ArrayList ids = new ArrayList();
        String id = "-1";
        while (iter.hasNext()) {
            id = ((Container) iter.next()).getContainerID();
            if (!ids.contains(id)) {
                ids.add(id);
            }
        }
        currentShoppingList.setContainerList(ids);
        return 0;
    }

    public int getAllSubjectsListCount() {
        return currentShoppingList.getSubjectList().size();
    }

    // returns arraylist of subject contents
    public ArrayList getAllSubjectObjectsInList() {
        Iterator iter = currentShoppingList.subjectIterator();
        ArrayList subjectObjectsList = new ArrayList();
        Subject subject = null;
        while (iter.hasNext()) {
            subject = (Subject) getObjectById(Subject.class, iter.next().toString());
            subjectObjectsList.add(subject);
        }
        return subjectObjectsList;
    }

    /**
     * sort the containers list according to the current containerSortCol
     * will get containerIDs from database sorted and reassign the current containerShoppingList to results
     * @return 0 if ok, -1 if no current shoppingList
     * @throws HibernateException
     */
    public int sortSubjectsList() throws HibernateException {
        Long listID = currentShoppingList.getShoppingList().getId();
        if (currentShoppingList == null || currentShoppingList.getShoppingList() == null) {
            return -1;
        }
        openHibSession();

        String sort =
                (subjectSortCol.equals("cohort")) ? "coho.description" + ((subjectSortDir) ? "" : " desc") + ", mySubject.subjectName"
                : (subjectSortCol.equals("father")) ? "mySubject.fatherName" + ((subjectSortDir) ? "" : " desc") + ", mySubject.subjectName"
                : (subjectSortCol.equals("mother")) ? "mySubject.motherName" + ((subjectSortDir) ? "" : " desc") + ", mySubject.subjectName"
                : "mySubject." + subjectSortCol;
        String orderClause = " order by "
                + ((subjectSortDir) ? sort : sort + " desc");

        String prefix = "select distinct mySubject, coho"
                + " FROM Subject as mySubject"
                + "  join fetch mySubject.cohort as coho"
                + " , ShoppingListSubject as myShoppingList"
                + " WHERE mySubject.subjectID = myShoppingList.subjectID "
                + " AND mySubject.subjectID IN "
                + " (SELECT myShoppingList.subjectID FROM ShoppingListSubject as myShoppingList"
                + "  WHERE myShoppingList.listID =:listID)";
        //" AND myShoppingList.listID =:listID";
        Query q = hibSess.createQuery(prefix + orderClause);
        q.setLong("listID", listID);
        // get IDs and remove duplicates
        Iterator iter = q.list().iterator();
        ArrayList ids = new ArrayList();
        String id = "-1";
        while (iter.hasNext()) {
            id = ((Subject) iter.next()).getSubjectID();
            if (!ids.contains(id)) {
                ids.add(id);
            }
        }
        currentShoppingList.setSubjectList(ids);
        return 0;
    }

    /**
     * returns list of container content ids in string format
     * for given sample list (as indicated in request)
     * ONLY WORKS FOR SOURCE TUBES
     * @param request
     * @return list of container content ids
     * @throws Exception
     */
    public List getContentListFromSampleList(HttpServletRequest request) throws Exception {

        // list to return if not setting working/current list
        ArrayList contentsIDs = new ArrayList();

        String listID = request.getParameter("listID");

        //String[] materialTypes = request.getParameterValues("materialType");
        String[] materialSources = request.getParameterValues("materialSource");
        String volume = request.getParameter("volume");
        String concen = request.getParameter("concen");

        String[] materialSourcesID = null;
        String materialSourcesLimit = "";
        String volLimit = "";
        String concenLimit = "";
        int i = 0;
        boolean where = false;
        HashMap limits = new HashMap();

        // ONLY MADE THIS WORK FOR SOURCE TUBES

        if (materialSources != null && materialSources.length > 0) {
            materialSourcesID = new String[materialSources.length];
            for (i = 0; i < materialSources.length; i++) {
                materialSourcesID[i] = (materialSources[i].equals("stockTubes")) ? "7" : "";
                //(materialSources[i].equals("wgaSourcePlates"))? "";
            }
            if (materialSourcesID.length == 2) {
                materialSourcesLimit = " (container.containerType = " + materialSourcesID[0]
                        + " OR container.containerType = " + materialSourcesID[1] + ") ";
                where = true;
            } else if (materialSourcesID.length == 1) {
                materialSourcesLimit = " container.containerType = " + materialSourcesID[0];
                where = true;
            }
        }

        if (volume != null && !volume.equals("")) {
            volLimit = " volume >= " + volume;
            where = true;
        }
        if (concen != null && !concen.equals("")) {
            concenLimit = " concentration >= " + concen;
            where = true;
        }

        openHibSession();
        // if samplelistID is -1, use current sample list to generate contents list
        /*if(listID.equals("-1")){
        Iterator iter = samplesList.iterator();
        String sampleID = null;
        while(iter.hasNext()){
        sampleID = iter.next().toString();
        // get contents for sample according to limits
        try {
        Query q = hibSess.createQuery("select containerContentsID " +
        "from ContainerContent myContainerContent "+
        "where sampleID = "+sampleID+
        ((!where)? "":(" AND "+materialSourcesLimit))+
        ((volLimit.equals("") || materialSourcesLimit.equals(""))? volLimit:(" AND "+volLimit))+
        ((concenLimit.equals("") || (volLimit.equals("") && materialSourcesLimit.equals("")))? concenLimit:(" AND "+concenLimit)));
        //+" order by myContainerContent.sample.sampleName ");
        //Println used to be here
        contentsIDs.addAll(q.list());
        } catch (Exception ex) {
        System.err.println(ex.getLocalizedMessage());
        }

        }
        // set current content list to newly created one
        currentShoppingList.setContainerContentsList(contentsIDs);

        // return null if current content list was set
        return null;
        } // if stored sample list is chosen, use it as source and don't overide current contents list
        else {
         */
        ShoppingCartList shoppingList = new ShoppingCartList(this, new Long(listID));

        //}

        return contentsIDs;
    }

    /*
     * populate sample list with hard coded IDs
     * for user testing.
     * Only does something if samplesList is empty
     * @return fake list of samples
    public void populateFakeSamplesList() {

    if (samplesList.isEmpty()) {
    String[] sampleIDs = {"12626","12453","12553","13025","13057","12205","16111","13305","12707","12540","12760","12771","12741","12576","13832","12697","12815","12384","12782","12777","12681","12544","12115","12078","12197","12263","11948","12833"};

    for (int i = 0; i < sampleIDs.length; i++) {
    samplesList.add(sampleIDs[i]);
    }
    }

    }
     */

    public String getShoppingListPrompter(String objIdStr) throws
            HibernateException {
        openHibSession();
        StringBuilder xyz = new StringBuilder();
        String prefix = "from ShoppingList as myShop order by myShop.createDate desc";
        Query q = hibSess.createQuery(prefix);
        List list = q.list();
        if (list != null) {
            //xyz.append("<option selected value=\"\"> ").append("none");
            Long objId = null;
            try {
                objId = Long.parseLong(objIdStr);
            } catch (Exception e) {
            } finally {
                Iterator iter = list.iterator();
                while (iter.hasNext()) {
                    ShoppingList obj = (ShoppingList) iter.next();
                    if (obj.getId().equals(objId)) {
                        xyz.append("<option selected value=\"");
                    } else {
                        xyz.append("<option value=\"");
                    }
                    String date = (new java.sql.Date(obj.getCreateDate().getTime())).toString();
//                    String date = (obj.getModifDate() == null) ? (new java.sql.Date(obj.getCreateDate().getTime())).toString() : (new java.sql.Date(obj.getModifDate().getTime())).toString();
                    xyz.append(obj.getId()).append("\">").append(date).append("  ").append(obj.getVisibleName());
                }

                return xyz.toString();
            }
        }
        xyz.append("<option/>");
        return xyz.toString();
    }

    public List<ShoppingList> getShoppingListResults(String field, String term, boolean exact) throws
            HibernateException {
        openHibSession();
        String prefix = "select myShop from ShoppingList as myShop";
        String suffix = " order by myShop." + field + " desc";
        String clause = " where upper(myShop." + field + ") like '" + ((exact) ? "" : "%") + term.toUpperCase() + ((exact) ? "" : "%") + "'";
        //Println used to be here
        Query q = hibSess.createQuery(prefix + clause + suffix);
        return q.list();
    }

    public Object getSQLsampleQCstatus(String sampleID, String genotypingrunID) {
        openHibSession();
        try {
            Query s = hibSess.createSQLQuery("select STATUS from TBLSAMPLEQCSTATUS "
                    + "WHERE SAMPLEID = " + sampleID + " AND GENOTYPINGRUNID = " + genotypingrunID + "");
            return s.uniqueResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public Object getSQLsampleyYear1Data(String sampleID) {
        openHibSession();
        try {
            Query s = hibSess.createSQLQuery("select sampletypeyear1id,dateCollectedyear1,extractiondateyear1 from TBLSAMPLES "
                    + "WHERE SAMPLEID = " + sampleID);
            return s.uniqueResult();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * makes a 384-well plate from 4 96-well plates and reduces volumes of source plates
     * @param request httprequest with the parameters
     * @return an array of the new 384 container IDs if successful,
     *          {-20} if failed to create 384-well plate because of duplicate ident
     *          {-2} if failed to create 384-well plate,
     *          {-3} if failed to make a content for 384 plate,
     *          {-4} if failed to reduce volume of src plate
     */
    public int[] four96to384(HttpServletRequest request) {
        int ok = 0;
        //get values from user

        String[] srcPlateIDs = request.getParameterValues("doPlate");
        String[] srcPlateOrder = request.getParameterValues("orderNewPlate");
        //String newPlateName = request.getParameter("plateName");
        String newPlateNameRoot = request.getParameter("plateNameRoot") + request.getParameter("batchName");
        String batch = request.getParameter("batchName");
        String[] initials = request.getParameterValues("initials");
        String[] barcode = request.getParameterValues("barcode");
        String[] deckLocation = request.getParameterValues("deckLocations");

        //names of plates to make
        String[] plateNames = new String[deckLocation.length];
        for (int i = 0; i < deckLocation.length; i++) {
            plateNames[i] = newPlateNameRoot + "_" + barcode[i];
        }

        String doWith = request.getParameter("doWith");
        // if storing, get freezer, shelf and location details
        Freezer freezer = null;
        String shelf = null;
        String location = null;
        String discarded = null;
        if (doWith != null && doWith.equals("doStore")) {
            freezer = (Freezer) getObjectById(Freezer.class, request.getParameter("freezerID"));
            shelf = request.getParameter("shelf");
            if (shelf.equals("")) {
                shelf = "-1";
            }
            location = request.getParameter("location");
            discarded = "0";
        } else {
            discarded = "1";
        }


        // get 96 well plates to make into 384, preserve order even if < 4 plates
        String[] srcPlateArr = {null, null, null, null};
        Container newContainer = null;

        for (int i = 0; i < srcPlateIDs.length; i++) {
            if (srcPlateOrder[i].indexOf("1") >= 0) {
                srcPlateArr[0] = srcPlateIDs[i];
            } else if (srcPlateOrder[i].indexOf("2") >= 0) {
                srcPlateArr[1] = srcPlateIDs[i];
            } else if (srcPlateOrder[i].indexOf("3") >= 0) {
                srcPlateArr[2] = srcPlateIDs[i];
            } else if (srcPlateOrder[i].indexOf("4") >= 0) {
                srcPlateArr[3] = srcPlateIDs[i];
            }
        }

        // make new 384 well plates
        String newPlateName = null;
        String comments = null;
        String initial = null;
        int[] containerIDs = new int[plateNames.length];
        for (int j = 0; j < plateNames.length; j++) {
            newPlateName = plateNames[j];
            comments = "Deck location: " + deckLocation[j] + ".";
            initial = initials[j];
            try {

                ok = addContainer("-1", newPlateName, (ContainerType) getObjectById(ContainerType.class, "1"), freezer, shelf, discarded, comments, "0", "1", null, "1", initial, null, location, null);
                if (ok == 3) {
                    // ConstraintViolationException copyContentserror
                    containerIDs[0] = -2;
                    return containerIDs;
                }
                // get container that you just made
                HashMap uniques = new HashMap();
                uniques.put("containerName", "'" + newPlateName + "'");
                newContainer = (Container) getObjectByUniqueKey(Container.class, uniques);
                containerIDs[j] = new Integer(newContainer.getContainerID());
            } catch (ConstraintViolationException ex) {
                System.err.println(ex.getLocalizedMessage());
                containerIDs[0] = -20;
                return containerIDs;
            } catch (Exception e) {
                System.err.println(e.getLocalizedMessage());
//                e.printStackTrace();
                containerIDs[0] = -2;
                return containerIDs;
            }
            // make new 384 container contents from src plates
            for (int i = 0; i < srcPlateArr.length; i++) {
                if (srcPlateArr[i] != null) {
                    //go through all contents
                    List<ContainerContent> contents = getContentsOfContainer(srcPlateArr[i]);
                    Iterator iter = contents.listIterator();
                    ContainerContent content = null;
                    int[] err = new int[1];
                    //for each content, make a new one for the 384
                    while (iter.hasNext()) {
                        content = (ContainerContent) iter.next();
                        // (I) get new coordinates by:(row*2-1,col*2-1)
                        int newRow = 0;
                        int newCol = 0;
                        if (i == 0) {
                            newRow = Integer.parseInt(content.getRow()) * 2 - 1;
                            newCol = Integer.parseInt(content.getColumn()) * 2 - 1;
                        } else if (i == 1) {
                            newRow = Integer.parseInt(content.getRow()) * 2 - 1;
                            newCol = Integer.parseInt(content.getColumn()) * 2;
                        } else if (i == 2) {
                            newRow = Integer.parseInt(content.getRow()) * 2;
                            newCol = Integer.parseInt(content.getColumn()) * 2 - 1;
                        } else if (i == 3) {
                            newRow = Integer.parseInt(content.getRow()) * 2;
                            newCol = Integer.parseInt(content.getColumn()) * 2;
                        }

                        try {
                            ok = addContainerContent("-1", content, "0", newContainer, String.valueOf(newRow), String.valueOf(newCol), content.getSample(), "5", content.getConcentration(), content.getDilution(), content.getComments(), content.getAmplificationDate(), content.getMaterialType());
                            if (ok != 0) {
                                System.err.println("Error in making new contents for 384 from plate id= " + srcPlateArr[i] + " " + newRow + "," + newCol + " " + content.getSample().getSampleName());
                                err[0] = -3;
                                return err;

                            }
                        } catch (Exception e) {
                            System.err.println(e.getLocalizedMessage());
                            err[0] = -3;
                            return err;
                        }
                    }

                    // make new 384 control wells from src plates

                    //go through all contents
                    List<ControlWell> controlWells = getControlWellsOfContainer(srcPlateArr[i]);
                    Iterator iter2 = controlWells.listIterator();
                    ControlWell controlWell = null;
                    //for each content, make a new one for the 384
                    while (iter2.hasNext()) {
                        controlWell = (ControlWell) iter2.next();
                        // (I) get new coordinates by:(row*2-1,col*2-1)
                        int newRow = 0;
                        int newCol = 0;
                        if (i == 0) {
                            newRow = Integer.parseInt(controlWell.getRow()) * 2 - 1;
                            newCol = Integer.parseInt(controlWell.getColumn()) * 2 - 1;
                        } else if (i == 1) {
                            newRow = Integer.parseInt(controlWell.getRow()) * 2 - 1;
                            newCol = Integer.parseInt(controlWell.getColumn()) * 2;
                        } else if (i == 2) {
                            newRow = Integer.parseInt(controlWell.getRow()) * 2;
                            newCol = Integer.parseInt(controlWell.getColumn()) * 2 - 1;
                        } else if (i == 3) {
                            newRow = Integer.parseInt(controlWell.getRow()) * 2;
                            newCol = Integer.parseInt(controlWell.getColumn()) * 2;
                        }

                        try {
                            ok = addControlWell(newContainer, controlWell.getControl(), String.valueOf(newRow), String.valueOf(newCol), controlWell.getVolume());
                            if (ok != 0) {
                                System.err.println("Error in making new control for 384 from plate id= " + srcPlateArr[i] + " " + newRow + "," + newCol + " " + controlWell.getControl().getDescription());
                                err[0] = -3;
                                return err;
                            }
                        } catch (Exception e) {
                            System.err.println(e.getLocalizedMessage());
                            err[0] = -3;
                            return err;
                        }
                    }

                    // reduce volume of original by 5 ul
                    ok = editBulkContainerContents(srcPlateArr[i], "5", "subtract", null, null, false);
                    if (ok != 0) {
                        System.err.println("Error in reducing original plate (id= " + srcPlateArr[i] + ") volume");
                        err[0] = -4;
                        return err;
                    }

                }
            }
        }
        return containerIDs;
    }

    public String[] getSDSImportData(String containerID) {
        String[] lines = new String[384];
        int i = 0; // starting array at index = 1
        ArrayList contents = (ArrayList) getContentsOfContainer(containerID);
        ArrayList controls = (ArrayList) getControlWellsOfContainer(containerID);

        // go through every entry and map it's well to the appropriate array index
        // using (row-1)*<number of columns>+column-1 (so A1 maps to 0)

        // map contents
        ListIterator iter = contents.listIterator();
        ContainerContent content = null;
        while (iter.hasNext()) {
            content = (ContainerContent) iter.next();
            i = (Integer.valueOf(content.getRow()) - 1) * 24
                    + Integer.valueOf(content.getColumn()) - 1;
            lines[i] = content.getSample().getSampleName();
        }

        // map controls
        iter = controls.listIterator();
        ControlWell controlWell = null;
        while (iter.hasNext()) {
            controlWell = (ControlWell) iter.next();
            i = (Integer.valueOf(controlWell.getRow()) - 1) * 24
                    + Integer.valueOf(controlWell.getColumn()) - 1;
            lines[i] = controlWell.getControl().getDescription();
        }

        return lines;
    }

    public void setCurrentSampleSelector(SampleSelector input) {
        currentSampleSelector = input;
    }

    public SampleSelector getCurrentSampleSelector() {
        return currentSampleSelector;
    }

    public void setCurrentPlater(Plater input) {
        currentPlater = input;
    }

    public Plater getCurrentPlater() {
        return currentPlater;
    }

    public void setCurrentUploader(FileUploading input) {
        currentUploader = input;
    }

    public FileUploading getCurrentUploader() {
        return currentUploader;
    }
//---MaterialType-------------------------------------------
//---MaterialType-------------------------------------------
    private MaterialType currentMaterialType = null;
    private String materialtypeSortCol = "description";
    private boolean materialtypeSortDir = true; //true - asc; false - desc
    private ViewMaterialTypeManager vMaterialTypeM = null;

    public int checkMaterialTypeId(String matTypId) {
        setInvalidField("matTypId");
        //Println used to be here
        if (matTypId == null || matTypId.trim().equals("")) {
            return 2;
        }
        try {
            Query q = hibSess.createQuery("select count(*) from MaterialType Mat "
                    + " where Mat.id= :matTypId");
            q.setString("matTypId", matTypId);
            //Println used to be here
            Integer cnt = ((Long) q.iterate().next()).intValue();
            //Println used to be here
            if (cnt.intValue() > 0) {
                return 3;
            }
            clearInvalidField();
        } catch (Exception ex) {
            //Println used to be here
            return 1;
        }
        return 0;
    }

    public int addMaterialType(String materialTypeID, String description, String sortOrder) throws Exception {
        int rezult = 0;
        //Println used to be here

        if (currentUser == null) {
            return 1;
        }
        rezult = checkMaterialTypeId(materialTypeID);
        //Println used to be here
        if (rezult == 0) {
            MaterialType tmpMaterialType = new MaterialType();
            tmpMaterialType.setCreateInfo(currentUser);
            tmpMaterialType.setMaterialTypeID(materialTypeID);
            tmpMaterialType.setDescription(description);

            //Println used to be here
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpMaterialType);
                tx.commit();
                currentMaterialType = tmpMaterialType;

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public int updateMaterialType(String materialTypeID, String description, String sortOrder) throws Exception {
        int rezult = 0;
        //Println used to be here

        //Println used to be here
        if (currentUser == null) {
            return 1;
        }
        MaterialType tmpMaterialType = getCurrentMaterialType();
        
        if (tmpMaterialType == null) {
            return 1;
        }
        //Println used to be here
        //Println used to be here
        //Println used to be here
        if (!materialTypeID.equals(tmpMaterialType.getId().toString())) {
            return 1;
        }

        //Println used to be here
        if (rezult == 0) {
            tmpMaterialType.setModifInfo(currentUser);
            tmpMaterialType.setMaterialTypeID(materialTypeID);
            tmpMaterialType.setDescription(description);

            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.update(tmpMaterialType);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public int deleteMaterialType(String selfId) throws Exception {
        if (currentUser == null) {
            return 1;
        }
        MaterialType tmpMaterialType = getCurrentMaterialType();
        if (tmpMaterialType == null) {
            return 1;
        }
        if (!selfId.equals(tmpMaterialType.getId().toString())) {
            return 1;
        }

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.delete(tmpMaterialType);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            currentMaterialType = null;
            /*          currentShippedTo = null;
            currentAliquot = null;
            currentSample = null;
            currentPerson = null;
             */
            closeHibSession(); //needs before forward
        }
        return 0;
    }

    public ViewMaterialTypeManager getViewMaterialTypeManager() {
        return vMaterialTypeM;
    }

    public MaterialType getCurrentMaterialType() {
        return currentMaterialType;
    }

    public Long getCurrentMaterialTypeId() {
        return (currentMaterialType == null) ? null : currentMaterialType.getId();
    }

    public List getAllMaterialTypes(int startPosition) throws
            HibernateException {
        String orderClause = " order by myMaterialType."
                + ((materialtypeSortDir) ? materialtypeSortCol
                : materialtypeSortCol + " desc");
        String prefix = "select myMaterialType from MaterialType as myMaterialType";
        Query q = hibSess.createQuery(prefix + orderClause);
        if (viewLimit > 0 && startPosition >= 0) {
            q.setFirstResult(startPosition);
            q.setMaxResults(viewLimit);
        }
        //Println used to be here
        //Println used to be here

        return q.list();
    }

    public int getAllMaterialTypesCount() throws HibernateException {
        String prefix = "select count(*) from MaterialType as myMaterialType";
        Query q;
        q = hibSess.createQuery(prefix);
        int count = ((Long) q.iterate().next()).intValue();
        return count;
    }

    public void setMaterialTypeSortCol(String sortCol) {
        if (materialtypeSortCol.equals(sortCol)) {
            materialtypeSortDir = !materialtypeSortDir;
        } else {
            materialtypeSortCol = sortCol;
            materialtypeSortDir = true;
        }
    }

    public String getMaterialTypeSortId(String sortCol) {
        return (!materialtypeSortCol.equals(sortCol)) ? "normal"
                : (materialtypeSortDir) ? "normalu" : "normalo";
    }

    public void setCurrentMaterialType(String matTypId) throws HibernateException {
        currentMaterialType = (MaterialType) getObjectById(MaterialType.class, matTypId);
    }
//---Control-------------------------------------------
//---Control-------------------------------------------
    private Control currentControl = null;
    private String controlSortCol = "description";
    private boolean controlSortDir = true; //true - asc; false - desc
    private ViewControlManager vControlM = null;

    public int checkControlId(String cntrlId) {
        setInvalidField("cntrlId");
        //Println used to be here
        if (cntrlId == null || cntrlId.trim().equals("")) {
            return 2;
        }
        try {
            Query q = hibSess.createQuery("select count(*) from Control Con "
                    + " where Con.id= :cntrlId");
            q.setString("cntrlId", cntrlId);
            //Println used to be here
            Integer cnt = ((Long) q.iterate().next()).intValue();
            //Println used to be here
            if (cnt.intValue() > 0) {
                return 3;
            }
            clearInvalidField();
        } catch (Exception ex) {
            //Println used to be here
            return 1;
        }
        return 0;
    }

    public int addControl(String controlID, String description, String controlType) throws Exception {
        int rezult = 0;
        //Println used to be here

        if (currentUser == null) {
            return 1;
        }
        rezult = checkControlId(controlID);
        //Println used to be here
        if (rezult == 0) {
            Control tmpControl = new Control();
            tmpControl.setCreateInfo(currentUser);
            tmpControl.setControlID(controlID);
            tmpControl.setDescription(description);
            tmpControl.setControlType(controlType);

            //Println used to be here
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpControl);
                tx.commit();
                currentControl = tmpControl;

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public int updateControl(String controlID, String description, String controlType) throws Exception {
        int rezult = 0;
        //Println used to be here

        //Println used to be here
        if (currentUser == null) {
            return 1;
        }
        Control tmpControl = getCurrentControl();
        
        if (tmpControl == null) {
            return 1;
        }
        //Println used to be here
        //Println used to be here
        //Println used to be here
        if (!controlID.equals(tmpControl.getId().toString())) {
            return 1;
        }

        //Println used to be here
        if (rezult == 0) {
            tmpControl.setModifInfo(currentUser);
            tmpControl.setControlID(controlID);
            tmpControl.setDescription(description);
            tmpControl.setControlType(controlType);

            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.update(tmpControl);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public int deleteControl(String selfId) throws Exception {
        if (currentUser == null) {
            return 1;
        }
        Control tmpControl = getCurrentControl();
        if (tmpControl == null) {
            return 1;
        }
        if (!selfId.equals(tmpControl.getId().toString())) {
            return 1;
        }

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.delete(tmpControl);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            currentControl = null;
            /*          currentShippedTo = null;
            currentAliquot = null;
            currentSample = null;
            currentPerson = null;
             */
            closeHibSession(); //needs before forward
        }
        return 0;
    }

    public ViewControlManager getViewControlManager() {
        return vControlM;
    }

    public Control getCurrentControl() {
        return currentControl;
    }

    public Long getCurrentControlId() {
        return (currentControl == null) ? null : currentControl.getId();
    }

    public List getAllControls(int startPosition) throws
            HibernateException {
        String orderClause = " order by myControl."
                + ((controlSortDir) ? controlSortCol
                : controlSortCol + " desc");
        String prefix = "select myControl from Control as myControl";
        Query q = hibSess.createQuery(prefix + orderClause);
        if (viewLimit > 0 && startPosition >= 0) {
            q.setFirstResult(startPosition);
            q.setMaxResults(viewLimit);
        }
        //Println used to be here
        //Println used to be here

        return q.list();
    }

    public int getAllControlsCount() throws HibernateException {
        String prefix = "select count(*) from Control as myControl";
        Query q;
        q = hibSess.createQuery(prefix);
        int count = ((Long) q.iterate().next()).intValue();
        return count;
    }

    public void setControlSortCol(String sortCol) {
        if (controlSortCol.equals(sortCol)) {
            controlSortDir = !controlSortDir;
        } else {
            controlSortCol = sortCol;
            controlSortDir = true;
        }
    }

    public String getControlSortId(String sortCol) {
        return (!controlSortCol.equals(sortCol)) ? "normal"
                : (controlSortDir) ? "normalu" : "normalo";
    }

    public void setCurrentControl(String cntrlId) throws HibernateException {
        currentControl = (Control) getObjectById(Control.class, cntrlId);
    }
//---User-------------------------------------------
//---User-------------------------------------------
    private String userSortCol = "visibleName";
    private boolean userSortDir = true; //true - asc; false - desc
    private ViewUserManager vUserM = null;

    public ViewUserManager getViewUserManager() {
        return vUserM;
    }

    /**
     * Returns the ID of the current User
     * @return the ID of the current user
     */
    public Long getCurrentUserId() {
        return (currentUser == null) ? null : currentUser.getId();
    }

    /**
     * Returns a list of users that are in the database
     * @param startPosition where to start looking for users
     * @return a list of users started from startPosition that are in the database
     * @throws HibernateException
     */
    public List getAllUsers(int startPosition) throws
            HibernateException {
        String orderClause = " order by myUser."
                + ((userSortDir) ? userSortCol
                : userSortCol + " desc");
        String prefix = "select myUser from User as myUser";
        Query q = hibSess.createQuery(prefix + orderClause);
        if (viewLimit > 0 && startPosition >= 0) {
            q.setFirstResult(startPosition);
            q.setMaxResults(viewLimit);
        }
        //Println used to be here
        //Println used to be here

        return q.list();
    }

    /**
     * Gets a count of all the users in the database
     * @return the number of users in the database
     * @throws HibernateException
     */
    public int getAllUsersCount() throws HibernateException {
        String prefix = "select count(*) from User as myUser";
        Query q;
        q = hibSess.createQuery(prefix);
        int count = ((Long) q.iterate().next()).intValue();
        return count;
    }

    public void setUserSortCol(String sortCol) {
        if (userSortCol.equals(sortCol)) {
            userSortDir = !userSortDir;
        } else {
            userSortCol = sortCol;
            userSortDir = true;
        }
    }

    public String getUserSortId(String sortCol) {
        return (!userSortCol.equals(sortCol)) ? "normal"
                : (userSortDir) ? "normalu" : "normalo";
    }

    public void setCurrentUser(String userID) throws HibernateException {
        currentUser = (User) getObjectById(User.class, userID);
    }

    /*---------------------------------------------------------------------------------
    --------------------------|||Sample Documents|||-----------------------------------
    ---------------------------------------------------------------------------------*/
    private SampleDocuments currentSampleDocuments = null;
    private String sampleDocumentsSortCol = "sampleDocumentID";
    private boolean sampleDocumentsSortDir = true; //true - asc; false - desc
    private ViewSampleDocumentsManager vSampleDocumentsM = null;

    /**
     * Checks if a SampleDocumentID already exists in the database
     * @param samDocId The SampleDocumentID to check
     * @return "0" if does not exist, "3" if already exists
     */
    public int checkSampleDocumentsId(String samDocId) {
        openHibSession();
        setInvalidField("samDocId");
        //Println used to be here
        if (samDocId == null || samDocId.trim().equals("")) {
            return 2;
        }
        try {
            Query q = hibSess.createQuery("select count(*) from SampleDocuments samDoc " + " where samDoc.id= :samDocId");
            q.setString("samDocId", samDocId);
            //Println used to be here
            Long cnt = (Long) q.iterate().next();
            //Println used to be here
            if (cnt.intValue() > 0) {
                return 3;
            }
            clearInvalidField();
        } catch (Exception ex) {
            //Println used to be here
            return 1;
        }
        return 0;
    }

    /**
     * Adds a new SampleDocument into the database
     * @param sampleDocumentsID "-1"
     * @param filename The filename of the SampleDocument
     * @param sample The sample to associate the SampleDocument with
     * @param document The file data
     * @param comment Additional Comments about the file
     * @return "0" if successful
     * @throws Exception
     */
    public int addSampleDocuments(String sampleDocumentsID, String filename, Sample sample, byte[] document, String comment) throws Exception {
        int rezult = 0;

        if (currentUser == null) {
            return 1;
        }

        HashMap uniques = new HashMap();
        //uniques.put("sampleDocumentsID", sampleDocumentsID);
        uniques.put("sampleID", sample.getSampleID());
        uniques.put("filename", "\'" + filename + "\'");
        rezult = countKey(SampleDocuments.class, uniques);

        //Println used to be here
        if (rezult == 0) {
            SampleDocuments tmpSampleDocuments = new SampleDocuments();
            tmpSampleDocuments.setCreateInfo(currentUser);
            tmpSampleDocuments.setSampleDocumentID(sampleDocumentsID);
            tmpSampleDocuments.setFilename(filename);
            tmpSampleDocuments.setSample(sample);
            tmpSampleDocuments.setDocument(document);
            tmpSampleDocuments.setComments(comment);

            //Println used to be here
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpSampleDocuments);
                tx.commit();
                currentSampleDocuments = tmpSampleDocuments;

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    /**
     * Updates the data of an existing SammpleDocument
     * @param sampleDocumentsID The ID to update
     * @param filename The filename of the file
     * @param sample The sample that the file is associated with
     * @param document The file data
     * @param comment Any additional comments about the document
     * @return "0" if successful
     * @throws Exception
     */
    public int updateSampleDocuments(String sampleDocumentsID, String filename, Sample sample, byte[] document, String comment) throws Exception {
        int rezult = 0;
        ////Println used to be here

        //Println used to be here
        if (currentUser == null) {
            return 1;
        }
        SampleDocuments tmpSampleDocuments = (SampleDocuments) getObjectById(SampleDocuments.class, sampleDocumentsID);        
        if (tmpSampleDocuments == null) {
            return 1;
        }
        //Println used to be here
        //Println used to be here
        //Println used to be here
        if (!sampleDocumentsID.equals(tmpSampleDocuments.getId().toString())) {
            return 1;
        }

        HashMap uniques = new HashMap();
        //uniques.put("sampleDocumentsID", sampleDocumentsID);
        uniques.put("sampleID", sample.getSampleID());
        uniques.put("filename", "\'" + filename + "\'");
        rezult = (countKey(SampleDocuments.class, uniques) < 1) ? 0 : 3;
        if (rezult == 3 && tmpSampleDocuments.getFilename().equals(filename)) {
            rezult = 0;
        }
        //Println used to be here
        if (rezult == 0) {
            tmpSampleDocuments.setModifInfo(currentUser);
            tmpSampleDocuments.setSampleDocumentID(sampleDocumentsID);
            tmpSampleDocuments.setFilename(filename);
            tmpSampleDocuments.setSample(sample);
            if (document != null && document.length > 0) {
                tmpSampleDocuments.setDocument(document);
            }
            tmpSampleDocuments.setComments(comment);

            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.update(tmpSampleDocuments);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public ViewSampleDocumentsManager getViewSampleDocumentsManager() {
        return vSampleDocumentsM;
    }

    /**
     * Returns the current SampleDocument
     * @return the current SampleDocument
     */
    public SampleDocuments getCurrentSampleDocuments() {
        return currentSampleDocuments;
    }

    /**
     * Gets the ID of the current SampleDocument
     * @return The ID of the current SampleDocument in String format
     */
    public String getCurrentSampleDocumentsId() {
        return (currentSampleDocuments == null) ? null : currentSampleDocuments.getSampleDocumentID();
    }

    /**
     * Returns a list of all SampleDocuments in the database starting from a specified location
     * @param startPosition The position to start looking for SampleDocuments
     * @return A list of all the SampleDocuments starting from a specified position
     * @throws HibernateException
     */
    public List getAllSampleDocuments(int startPosition) throws
            HibernateException {
        openHibSession();
        String orderClause = " order by mySampleDocuments."
                + ((sampleDocumentsSortDir) ? sampleDocumentsSortCol
                : sampleDocumentsSortCol + " desc");
        String prefix = "select mySampleDocuments from SampleDocuments as mySampleDocuments";

        String limit = "";
        // only one search should be active at a time
        if (filter != null || search != null) {
            limit = (filter != null) ? filter.getSampleDocumentsString() : search.getSampleDocumentsString();
        }

        Query q = hibSess.createQuery(prefix + limit + orderClause);
        if (viewLimit > 0 && startPosition >= 0) {
            q.setFirstResult(startPosition);
            q.setMaxResults(viewLimit);
        }

        return q.list();
    }

    /**
     * Gets all the Sample Documents that's associated with a specified SampleID starting at a specified spot
     * @param startPosition The position to start getting Sample Documents from
     * @param sampId The SampleID to look for documents with
     * @return List of all the Sample Documents
     * @throws HibernateException
     */
    public List getAllSampleDocuments(int startPosition, String sampId) throws HibernateException {
        openHibSession();
        String orderClause = " order by mySampleDocuments."
                + ((sampleDocumentsSortDir) ? sampleDocumentsSortCol
                : sampleDocumentsSortCol + " desc");
        String prefix = "select mySampleDocuments from SampleDocuments as mySampleDocuments";
        String limit = "";
        // only one search should be active at a time
        if (filter != null || search != null) {
            limit = (filter != null) ? filter.getSampleDocumentsString() : search.getSampleDocumentsString();
            if (sampId != null && sampId.indexOf("null") < 0) {
//                limit = limit + " and mySampleDocuments.sample.sampleID = " + sampId;
                limit = " where mySampleDocuments.sample.sampleID = " + sampId;
            }
        } else if (sampId != null && sampId.indexOf("null") < 0) {
            limit = limit + " where mySampleDocuments.sample.sampleID = " + sampId;
        }
        Query q = hibSess.createQuery(prefix + limit + orderClause);
        if (viewLimit > 0 && startPosition >= 0) {
            q.setFirstResult(startPosition);
            q.setMaxResults(viewLimit);
        }
//        Iterator<String> iter = q.list().iterator();
//        ArrayList<SampleDocuments> sampleDocuments = new ArrayList();
//        while (iter.hasNext()){
//            String sampDocId = iter.next();
//            this.getObjectById(SampleDocuments.class, sampDocId);
//        }
        return q.list();
    }

    /**
     * Gets a count of all the Samples Documents in the database
     * @return A count of all the Sample Documents in the database
     * @throws HibernateException
     */
    public int getAllSampleDocumentsCount() throws HibernateException {
        openHibSession();

        // only one should be active at a time
        String limit = (filter != null) ? filter.getSampleDocumentsString() : "";
        String searchStr = (search == null) ? "" : search.getSampleDocumentsString();

        String prefix = "select count(*) from SampleDocuments as mySampleDocuments";
        Query q = hibSess.createQuery(prefix + limit + searchStr);
        int count = ((Long) q.iterate().next()).intValue();
        return count;
    }

    /**
     * Shows the number of Sample Documents associated with a specified Sample ID
     * @param sampId The sample ID to check for the number of files that it belongs to
     * @return Returns the number of files that's associated with the sample
     * @throws HibernateException
     */
    public int getAllSampleDocumentsCount(String sampId) throws HibernateException {
        openHibSession();

        // only one should be active at a time
        String limit = (filter != null) ? filter.getSampleDocumentsString() : "";
        String searchStr = (search == null) ? "" : search.getSampleDocumentsString();

        String prefix = "select count(distinct mySampleDocuments) from SampleDocuments as mySampleDocuments";

        if (!limit.equals("") || !searchStr.equals("")) {
            if (sampId != null && !sampId.equals("null")) {
                limit = limit + " and mySampleDocuments.sample.sampleID = " + sampId;
            }
        } else if (sampId != null && !sampId.equals("null")) {
            limit = limit + " where mySampleDocuments.sample.sampleID = " + sampId;
        }

        Query q = hibSess.createQuery(prefix + searchStr + limit);
        int count = ((Long) q.iterate().next()).intValue();
        return count;
    }

    /**
     * Sets the sort column for the SampleDocuments table
     * @param sortCol the column to sort by
     */
    public void setSampleDocumentsSortCol(String sortCol) {
        if (sortCol.equals("sampleDocumentID")) {
            sortCol = "sampleDocuments.sampleDocumentID";
        }
        if (sampleDocumentsSortCol.equals(sortCol)) {
            sampleDocumentsSortDir = !sampleDocumentsSortDir;
        } else {
            sampleDocumentsSortCol = sortCol;
            sampleDocumentsSortDir = true;
        }
    }

    /**
     *
     * @param sortCol
     * @return
     */
    public String getSampleDocumentsSortId(String sortCol) {
        return (!sampleDocumentsSortCol.equals(sortCol)) ? "normal" : (sampleDocumentsSortDir) ? "normalu" : "normalo";
    }

    /**
     * Sets the current sampledocument to the one specified by the ID
     * @param sampDocId the SampleDocument ID to set as the current one
     * @throws HibernateException
     */
    public void setCurrentSampleDocuments(String sampDocId) throws HibernateException {
        currentSampleDocuments = (SampleDocuments) getObjectById(SampleDocuments.class, sampDocId);
    }

    /**
     * Checks if the filename is already used for a specified Sample Document
     * @param sampId The Sample the document is associated with
     * @param filename The filename in question
     * @param sampDocId The SampleDocument ID of the Sample Document in question
     * @return true if unique, false if already exists
     */
    public boolean checkFilenameUniqueness(String sampId, String filename, String sampDocId) {
        String currentFileName = "";
        if (!sampDocId.equals("-1")) {
            SampleDocuments sampDoc = (SampleDocuments) getObjectById(SampleDocuments.class, sampDocId);
            currentFileName = sampDoc.getFilename();
        }
        if (currentFileName.equals(filename)) {
            return true;
        }
        HashMap uniques = new HashMap();
        uniques.put("sampleID", sampId);
        uniques.put("filename", "\'" + filename + "\'");
        boolean result = (countKey(SampleDocuments.class, uniques) < 1) ? true : false;
        return result;
    }

    /**
     * Gets the ASCII value for the row# (Eg. 1 = A)
     * @param row The row number to change into a Letter
     * @return
     */
    public String getASCIIrow(String row) {
        int rowNum = Integer.parseInt(row) + 64;
        char letter = (char) rowNum;
        return String.valueOf(letter);
    }

    /*---------------------------------------------------------------------------------
    --------------------------|||SHIPMENT|||-----------------------------------
    ---------------------------------------------------------------------------------*/
    private Shipment currentShipment = null;
    private String shipmentSortCol = "shipmentID";
    private boolean shipmentSortDir = true; //true - asc; false - desc
    private ViewShipmentManager vShipmentM = null;

    /**
     * Checks if a shipment is already in the database by shipment ID
     * @param shipmentId The shipment ID to check if it exists
     * @return "0" if doesn't exist, any other number is an error
     */
    public int checkShipmentId(String shipmentId) {
        openHibSession();
        setInvalidField("shipmentId");
        //Println used to be here
        if (shipmentId == null || shipmentId.trim().equals("")) {
            return 2;
        }
        try {
            Query q = hibSess.createQuery("select count(*) from Shipment shipment " + " where shipment.id= :shipmentID");
            q.setString("shipmentID", shipmentId);
            //Println used to be here
            Long cnt = (Long) q.iterate().next();
            //Println used to be here
            if (cnt.intValue() > 0) {
                return 3;
            }
            clearInvalidField();
        } catch (Exception ex) {
            //Println used to be here
            return 1;
        }
        return 0;
    }

    /**
     * Adds a shipment to the database with the information provided in the parameters
     * @param shipmentID "-1" for new Shipment
     * @param shipmentName The shipment name
     * @param shipDate The date shipped out/returned
     * @param shipTo The location shipped to/returned from
     * @param shipAction The action that was done to the container (Shipped,Returned,None)
     * @param comment Comments on the shipment
     * @return "0" if successful, "1" if errored
     * @throws Exception
     */
    public int addShipment(String shipmentID, String shipmentName, Date shipDate, ShippedTo shipTo, String shipAction, String comment) throws Exception {
        openHibSession();
        int rezult = 0;

        if (currentUser == null) {
            return 1;
        }

//        HashMap uniques = new HashMap();
        //uniques.put("ShipmentID", shipmentID);
//        uniques.put("sampleID", sample.getSampleID());
//        uniques.put("filename", "\'"+filename+"\'");
//        rezult = countKey(Shipment.class, uniques);

        //Println used to be here
        if (rezult == 0) {
            Shipment tmpShipment = new Shipment();
            tmpShipment.setCreateInfo(currentUser);
            tmpShipment.setShipmentID(shipmentID);
            tmpShipment.setShipmentName(shipmentName);
            tmpShipment.setShipDate(shipDate);
            tmpShipment.setShippedTo(shipTo);
            tmpShipment.setShipAction(shipAction);
            tmpShipment.setComments(comment);

            //Println used to be here
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpShipment);
                tx.commit();
                currentShipment = tmpShipment;

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
//                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    /**
     * Updates the shipment with the information in the parameters provided
     * @param shipmentID The shipmentID of the Shipment that needs to be updated
     * @param shipmentName The Shipment Name to update to
     * @param shipDate The Shipment Date to update to
     * @param shipTo The updated location
     * @param shipAction The updated action
     * @param comment The updated comment
     * @return "0" if successful, "1" if errored
     * @throws Exception
     */
    public int updateShipment(String shipmentID, String shipmentName, Date shipDate, ShippedTo shipTo, String shipAction, String comment) throws Exception {
        openHibSession();
        int rezult = 0;
        ////Println used to be here

        //Println used to be here
        if (currentUser == null) {
            return 1;
        }
        Shipment tmpShipment = (Shipment) getObjectById(Shipment.class, shipmentID);
        
        if (tmpShipment == null) {
            return 1;
        }
        //Println used to be here
        //Println used to be here
        //Println used to be here
        if (!shipmentID.equals(tmpShipment.getId().toString())) {
            return 1;
        }

        HashMap uniques = new HashMap();
        //uniques.put("shipmentID", shipmentID);
        uniques.put("shipmentName", "\'" + shipmentName + "\'");
        uniques.put("shipDate", "\'" + shipDate + "\'");
        rezult = (countKey(Shipment.class, uniques) <= 1) ? 0 : 3;
        if (rezult == 3) {
            return rezult;
        }
        //Println used to be here
        if (rezult == 0) {
            tmpShipment.setModifInfo(currentUser);
            tmpShipment.setShipmentID(shipmentID);
            tmpShipment.setShipmentName(shipmentName);
            tmpShipment.setShipDate(shipDate);
            tmpShipment.setShippedTo(shipTo);
            tmpShipment.setShipAction(shipAction);
            tmpShipment.setComments(comment);

            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.update(tmpShipment);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
//                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public ViewShipmentManager getViewShipmentManager() {
        return vShipmentM;
    }

    /**
     *
     * @return The current Shipment object
     */
    public Shipment getCurrentShipment() {
        return currentShipment;
    }

    /**
     *
     * @return Returns the current shipment ID
     */
    public Long getCurrentShipmentId() {
        return (currentShipment == null) ? null : currentShipment.getId();
    }

    /**
     * Returns a list of all shipments in the database
     * @param startPosition The index to start grabbing shipments from
     * @return A list of all shipments beginning at startPosition
     * @throws HibernateException
     */
    public List getAllShipment(int startPosition) throws
            HibernateException {
        openHibSession();
        String orderClause = " order by myShipment."
                + ((shipmentSortDir) ? shipmentSortCol
                : shipmentSortCol + " desc");
        String prefix = "select myShipment from Shipment as myShipment";

        String limit = "";
        // only one search should be active at a time
        if (filter != null || search != null) {
            limit = (filter != null) ? filter.getShipmentString() : search.getShipmentString();
        }

        Query q = hibSess.createQuery(prefix + limit + orderClause);
        if (viewLimit > 0 && startPosition >= 0) {
            q.setFirstResult(startPosition);
            q.setMaxResults(viewLimit);
        }

        return q.list();
    }

    /**
     * Returns a list of all the shipments in the database associated with the containerID
     * @param startPosition The position to start grabbing shipments from
     * @param contId The containerID to get all the shipments for
     * @return A list of all the shipments associated with that containerID
     * @throws HibernateException
     */
    public List getAllShipment(int startPosition, String contId) throws HibernateException {
        openHibSession();
        List returnList = new ArrayList();
        String orderClause = " order by myShipment."
                + ((shipmentSortDir) ? shipmentSortCol
                : shipmentSortCol + " desc");
        String prefix = "select myShipment from Shipment as myShipment, ShipCont as myShipCont";
        String limit = "";
        // only one search should be active at a time

        if (getAllShipmentCount() > 0) {
            if (contId != null && !contId.equals("null")) {
                limit = " WHERE myShipment.shipmentID = myShipCont.shipment.shipmentID AND myShipCont.container.containerID = " + contId;
            }
            Query q = hibSess.createQuery(prefix + limit + orderClause);
            if (viewLimit > 0 && startPosition >= 0) {
                q.setFirstResult(startPosition);
                q.setMaxResults(viewLimit);
            }
            returnList = q.list();
        }
        return returnList;
    }

    /**
     * Counts the number of shipments sent out
     * @return The number of Shipments in the database
     * @throws HibernateException
     */
    public int getAllShipmentCount() throws HibernateException {
        openHibSession();

        // only one should be active at a time
        String limit = (filter != null) ? filter.getShipmentString() : "";
        String searchStr = (search == null) ? "" : search.getShipmentString();

        String prefix = "select count(*) from Shipment as myShipment";
        Query q = hibSess.createQuery(prefix + limit + searchStr);
        int count = ((Long) q.iterate().next()).intValue();
        return count;
    }

    /**
     * Counts all the Shipments that contains the ContainerID
     * @param contId the ContainerID to look for shipments associated with
     * @return The number of Shipments associated with that container
     * @throws HibernateException
     */
    public int getAllShipmentCount(String contId) throws HibernateException {
        openHibSession();
        int count = 0;
        // only one should be active at a time
        String limit = (filter != null) ? filter.getShipmentString() : "";
        String searchStr = (search == null) ? "" : search.getShipmentString();

        String prefix = "select count(*) from Shipment as myShipment, ShipCont as myShipCont";
        if (getAllShipmentCount() > 0) {
            if (contId != null && !contId.equals("null")) {
                limit = " WHERE myShipment.shipmentID = myShipCont.shipment.shipmentID AND myShipCont.container.containerID = " + contId;
            }

            Query q = hibSess.createQuery(prefix + limit);
            count = ((Long) q.iterate().next()).intValue();
        }
        return count;
    }

    /**
     * Sets the column to sort the Shipments by
     * @param sortCol The column that should be sorted by
     */
    public void setShipmentSortCol(String sortCol) {
        if (sortCol.equals("shipmentID")) {
            sortCol = "shipment.shipmentID";
        }
        if (shipmentSortCol.equals(sortCol)) {
            shipmentSortDir = !shipmentSortDir;
        } else {
            shipmentSortCol = sortCol;
            shipmentSortDir = true;
        }
    }

    /**
     * Gets the method of sorting in that column
     * @param sortCol
     * @return
     */
    public String getShipmentSortId(String sortCol) {
        return (!shipmentSortCol.equals(sortCol)) ? "normal" : (shipmentSortDir) ? "normalu" : "normalo";
    }

    /**
     * Sets the current Shipment to the one defined by the ID
     * @param shipmentId The Shipment ID that's associated with the Shipment you want to set a current
     * @throws HibernateException
     */
    public void setCurrentShipment(String shipmentId) throws HibernateException {
        currentShipment = (Shipment) getObjectById(Shipment.class, shipmentId);
    }

    /**
     * Gets a list of Shipment Names for displaying in a select combobox
     * @param objIdStr the current object (default selected)
     * @return HTML string to display the select box
     * @throws HibernateException
     */
    public String getShipmentListPrompter(String objIdStr) throws
            HibernateException {
        openHibSession();
        StringBuilder xyz = new StringBuilder();
        String prefix = "from Shipment as myShipment order by myShipment.createDate desc";
        String indicator = "";
        Query q = hibSess.createQuery(prefix);
        List list = q.list();
        if (list != null) {
            //xyz.append("<option selected value=\"\"> ").append("none");
            Long objId = null;
            try {
                objId = Long.parseLong(objIdStr);
            } catch (Exception e) {
            } finally {
                Iterator iter = list.iterator();
                while (iter.hasNext()) {
                    Shipment obj = (Shipment) iter.next();
                    if (obj.getShipAction().equals("1")) {
                        indicator = "S";
                    }
                    if (obj.getShipAction().equals("2")) {
                        indicator = "R";
                    }
                    if (obj.getId().equals(objId)) {
                        xyz.append("<option selected value=\"");
                    } else {
                        xyz.append("<option value=\"");
                    }
                    String date = (obj.getModifDate() == null) ? (new java.sql.Date(obj.getCreateDate().getTime())).toString() : (new java.sql.Date(obj.getModifDate().getTime())).toString();
                    xyz.append(obj.getId()).append("\">").append(indicator).append(" ").append(date).append("  ").append(obj.getVisibleName());
                }

                return xyz.toString();
            }
        }
        xyz.append("<option/>");
        return xyz.toString();
    }
    //********** END OF SHIPMENT TABLE ************//

    /**
     * Check out container in the ShoppingList
     * @param request The HTTP Request
     * @return an ArrayList with errors (if any)
     * @throws Exception
     */
    public ArrayList checkOutContainersList(HttpServletRequest request) throws Exception {
        writeLockCurrentShoppingList();
        //get the login name
        String user = getUserString();
        //get the person's actual name
        String commentAuthor = request.getParameter("checkName");
        String comments = request.getParameter("checkComment");
        //arraylist to keep track of already checkout stuff and return in the end
        ArrayList<String> errorArray = new ArrayList();
        //checking commets formatting
        String checkOutFormatting = commentAuthor + "(" + user + ")" + "\n" + (new Date()).toString() + "\nComments: " + comments;
        boolean searched = (isFilterList()
                || isSearchList()) ? true : false;
        ArrayList items = (searched) ? new ArrayList(getContainersListSearched())
                : new ArrayList(getAllContainerObjectsInList());
        if (items.isEmpty()) {
            releaseLockCurrentShoppingList();
            return items;
        }
        Iterator iter = items.iterator();
        Container container;
        Transaction tx = null;
        try {
            while (iter.hasNext()) {
                container = (Container) iter.next();
                if (container.getCheckedOut() == null || container.getCheckedOut().equals("")) {
                    container.setCheckedOut(checkOutFormatting);
                } else {
                    errorArray.add(container.getcontainerName());
                    continue;
                }
                tx = null;
                tx = hibSess.beginTransaction();
                hibSess.save(container);
                tx.commit();
                currentContainer = container;
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            closeHibSession(); //needs before forward
            releaseLockCurrentShoppingList();
        }

        return errorArray;
    }

    /**
     * Checks out a container with the checked lists
     * @param request The HTTP request
     * @return an ArrayList of errors
     * @throws HibernateException Error during transaction
     * @throws Exception
     */
    public ArrayList checkOutContainersChecked(HttpServletRequest request) throws HibernateException, Exception {
        String containerId;
        ArrayList<String> errorArray = new ArrayList();
        Container container;
        String user = getUserString();
        String commentAuthor = request.getParameter("checkName");
        String comments = request.getParameter("checkComment");
        String checkOutFormatting = commentAuthor + "(" + user + ")" + "\n" + (new Date()).toString() + "\nComments: " + comments;
        Map valueMap = request.getParameterMap();
        String[] checkValues = (String[]) valueMap.get("containerListChecked");
        if (checkValues == null) {
            //Println used to be here
            releaseLockCurrentShoppingList();
            return new ArrayList();
        }
        ArrayList<String> containerListChecked = new ArrayList(Arrays.asList(checkValues));
        Iterator iter = containerListChecked.iterator();
        Transaction tx = null;
        try {
            while (iter.hasNext()) {
                containerId = iter.next().toString();
                container = (Container) getObjectById(Container.class, containerId);
                if (container.getCheckedOut() == null || container.getCheckedOut().equals("")) {
                    container.setCheckedOut(checkOutFormatting);
                } else {
                    errorArray.add(container.getcontainerName());
                    continue;
                }
                tx = null;
                tx = hibSess.beginTransaction();
                hibSess.save(container);
                tx.commit();
                currentContainer = container;
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            closeHibSession(); //needs before forward
        }
        return errorArray;
    }

    /**
     * Checks in all the containers in the current workList
     * @return Returns an ArrayList with errors, empty if none
     * @throws HibernateException
     * @throws Exception
     */
    public ArrayList checkInContainersList() throws HibernateException, Exception {
        writeLockCurrentShoppingList();
        boolean searched = (isFilterList()
                || isSearchList()) ? true : false;
        ArrayList items = (searched) ? new ArrayList(getContainersListSearched())
                : new ArrayList(getAllContainerObjectsInList());
        if (items.isEmpty()) {
            releaseLockCurrentShoppingList();
            return items;
        }
        //iterator to iterate through items
        Iterator iter = items.iterator();
        ArrayList<String> errorArray = new ArrayList();
        Container container;
        String user = getUserString();
        Transaction tx = null;
        try {
            while (iter.hasNext()) {
                container = (Container) iter.next();
                if (container.getCheckedOut().equals("") || container.getCheckedOut() == null) {
                    continue;
                } else {
                    if (container.getCheckedOut().contains(user) || isWriteAdminSession()) {
                        container.setCheckedOut(null);
                    } else {
                        errorArray.add(container.getcontainerName());
                        continue;
                    }
                }
                tx = null;
                tx = hibSess.beginTransaction();
                hibSess.save(container);
                tx.commit();
                currentContainer = container;
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            closeHibSession(); //needs before forward
            this.releaseLockCurrentShoppingList();
        }
        return errorArray;
    }

    /**
     * Checks in containers that have been marked by a checkmark
     * @param request The HTTP Request data
     * @return Returns an ArrayList with errors, empty if no errors
     * @throws HibernateException
     * @throws Exception
     */
    public ArrayList checkInContainersChecked(HttpServletRequest request) throws HibernateException, Exception {
        String containerId;
        Container container;
        Map valueMap = request.getParameterMap();
        String[] checkValues = (String[]) valueMap.get("containerListChecked");
        if (checkValues == null) {
            //Println used to be here
            releaseLockCurrentShoppingList();
            return new ArrayList();
        }
        ArrayList<String> containerListChecked = new ArrayList(Arrays.asList(checkValues));
        Iterator iter = containerListChecked.iterator();
        ArrayList<String> errorArray = new ArrayList();
        String user = getUserString();
        Transaction tx = null;
        try {
            while (iter.hasNext()) {
                containerId = iter.next().toString();
                container = (Container) getObjectById(Container.class, containerId);
                if (container.getCheckedOut() == null || container.getCheckedOut().equals("")) {
                    continue;
                } else {
                    if (container.getCheckedOut().contains(user) || isWriteAdminSession()) {
                        container.setCheckedOut(null);
                    } else {
                        errorArray.add(container.getcontainerName());
                        continue;
                    }
                }
                tx = null;
                tx = hibSess.beginTransaction();
                hibSess.save(container);
                tx.commit();
                currentContainer = container;
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            closeHibSession(); //needs before forward
        }
        return errorArray;
    }
    ////////////////////////////////////////SHIPCONT///////////////////////////////////////////
    ////////////////////////////////////////SHIPCONT///////////////////////////////////////////
    ////////////////////////////////////////SHIPCONT///////////////////////////////////////////
    private ShipCont currentShipCont = null;
    private String shipContSortCol = "shipContID";
    private boolean shipContSortDir = true; //true - asc; false - desc
//    private ViewShipContManager vShipContM = null;

    /**
     * Checks if there is already a ShipCont with the same ID
     * @param shipContID The ShipCont ID to check in the database
     * @return "0" if doesn't exist, "1" if error, "2" if invalid parameter, "3" if already exists
     */
    public int checkShipContId(String shipContID) {
        openHibSession();
        setInvalidField("shipContID");
        //Println used to be here
        if (shipContID == null || shipContID.trim().equals("")) {
            return 2;
        }
        try {
            Query q = hibSess.createQuery("select count(*) from ShipCont shipCont " + " where shipCont.id= :shipContID");
            q.setString("shipContID", shipContID);
            //Println used to be here
            Long cnt = (Long) q.iterate().next();
            //Println used to be here
            if (cnt.intValue() > 0) {
                return 3;
            }
            clearInvalidField();
        } catch (Exception ex) {
            //Println used to be here
            return 1;
        }
        return 0;
    }

    /**
     * Adds a new ShipCont into the database
     * @param shipContID "-1" for new ShipCont object
     * @param shipment The shipment you want to associate the ShipCont with
     * @param container The container you want to associate the ShipCont with
     * @return "0" if success, "1" if errored
     * @throws Exception HibernateExeception if the transaction failed
     */
    public int addShipCont(String shipContID, Shipment shipment, Container container) throws Exception {
        int rezult = 0;

        if (currentUser == null) {
            return 1;
        }

        if (shipment == null || container == null) {
            return 1;
        }

        HashMap uniques = new HashMap();
        //uniques.put("shipContID", shipContID);
        uniques.put("shipment", shipment.getShipmentID());
        uniques.put("container", container.getContainerID());
        rezult = countKey(ShipCont.class, uniques);

        //Println used to be here
        if (rezult == 0) {
            ShipCont tmpShipCont = new ShipCont();
            tmpShipCont.setCreateInfo(currentUser);
            tmpShipCont.setShipContID(shipContID);
            tmpShipCont.setShipment(shipment);
            tmpShipCont.setContainer(container);

            //Println used to be here
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpShipCont);
                tx.commit();
                currentShipCont = tmpShipCont;

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    /**
     * Updates a Shipment-Container object with the parameters
     * @param shipContID The ID of the ShipCont object you want to update
     * @param shipment The new shipment you want to link the ShipCont with
     * @param container The new container you want to like the ShipCont with
     * @return ReTurns 0 for success, and 1 if an error occurred
     * @throws Exception HibernateException if an error occurred
     */
    public int updateShipCont(String shipContID, Shipment shipment, Container container) throws Exception {
        int rezult = 0;
        ////Println used to be here

        //Println used to be here
        if (currentUser == null) {
            return 1;
        }
        ShipCont tmpShipCont = (ShipCont) getObjectById(ShipCont.class, shipContID);
        if (tmpShipCont == null) {
            //Println used to be here
            return 1;
        }

        if (shipment == null || container == null) {
            return 1;
        }

        //Println used to be here
        //Println used to be here
        //Println used to be here

        HashMap uniques = new HashMap();
        //uniques.put("shipContID", shipContID);
        uniques.put("shipmentID", shipment.getShipmentID());
        uniques.put("containerID", container.getContainerID());
        rezult = (countKey(ShipCont.class, uniques) <= 1) ? 0 : 3;
        //Println used to be here
        if (rezult == 0) {
            tmpShipCont.setModifInfo(currentUser);
            tmpShipCont.setShipContID(shipContID);
            tmpShipCont.setContainer(container);
            tmpShipCont.setShipment(shipment);

            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.update(tmpShipCont);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

//    public ViewShipContManager getViewShipContManager() {
//        return vShipContM;
//    }

    /**
     *
     * @return Returns the current Shipment-Container object
     */
    public ShipCont getCurrentShipCont() {
        return currentShipCont;
    }

    /**
     * Returns the current ShipCont's ID
     * @return Returns the current Shipment-Container ID
     */
    public String getCurrentShipContId() {
        return (currentShipCont == null) ? null : currentShipCont.getShipContID();
    }

//    public List getAllShipCont(int startPosition) throws
//            HibernateException {
//        openHibSession();
//        String orderClause = " order by myShipCont."
//                + ((shipContSortDir) ? shipContSortCol
//                : shipContSortCol + " desc");
//        String prefix = "select myShipCont from ShipCont as myShipCont";
//
//                String limit = "";
//        // only one search should be active at a time
//        if (filter != null || search != null) {
//            limit = (filter!=null)? filter.getShipContString(): search.getShipContString();
//        }
//
//        Query q = hibSess.createQuery(prefix + limit + orderClause);
//        if (viewLimit > 0 && startPosition >= 0) {
//            q.setFirstResult(startPosition);
//            q.setMaxResults(viewLimit);
//        }
//
//        return q.list();
//    }
//    public List getAllShipCont(int startPosition, String sampId) throws HibernateException {
//        openHibSession();
//        String orderClause = " order by myShipCont."
//                + ((shipContSortDir) ? shipContSortCol
//                : shipContSortCol + " desc");
//        String prefix = "select myShipCont from ShipCont as myShipCont";
//        String limit = "";
//        // only one search should be active at a time
//        if (filter != null || search != null) {
//            limit = (filter!=null)? filter.getShipContString(): search.getShipContString();
//            if (sampId != null && sampId.indexOf("null") < 0) {
//                limit = limit + " and myShipCont.sample.sampleID = " + sampId;
//            }
//        } else if (sampId != null && sampId.indexOf("null") < 0) {
//            limit = limit + " where myShipCont.sample.sampleID = " + sampId;
//        }
//        Query q = hibSess.createQuery(prefix + limit + orderClause);
//        if (viewLimit > 0 && startPosition >= 0) {
//            q.setFirstResult(startPosition);
//            q.setMaxResults(viewLimit);
//        }
//        return q.list();
//    }
//    public int getAllShipContCount() throws HibernateException {
//        openHibSession();
//
//        // only one should be active at a time
//        String limit = (filter != null) ? filter.getShipContString() : "";
//        String searchStr = (search == null) ? "" : search.getShipContString();
//
//        String prefix = "select count(*) from ShipCont as myShipCont";
//        Query q = hibSess.createQuery(prefix + limit +searchStr);
//        int count = ((Long) q.iterate().next()).intValue();
//        return count;
//    }
//
//    public int getAllShipContCount(String sampId) throws HibernateException {
//        openHibSession();
//
//        // only one should be active at a time
//        String limit = (filter != null) ? filter.getShipContString() : "";
//        String searchStr = (search == null) ? "" : search.getShipContString();
//
//        String prefix = "select count(*) from ShipCont as myShipCont";
//
//        if (filter != null || search != null) {
//            if (sampId != null && !sampId.equals("null")) {
//                limit = limit + " and myShipCont.sample.sampleID = " + sampId;
//            }
//        } else if (sampId != null && !sampId.equals("null")) {
//            limit = limit + " where myShipCont.sample.sampleID = " + sampId;
//        }
//
//        Query q = hibSess.createQuery(prefix + limit + searchStr);
//        int count = ((Long) q.iterate().next()).intValue();
//        return count;
//    }
    /**
     * Sets the column to be sorted
     * @param sortCol The column to be sorted
     */
    public void setShipContSortCol(String sortCol) {
        if (sortCol.equals("sampleID")) {
            sortCol = "sample.sampleID";
        }
        if (shipContSortCol.equals(sortCol)) {
            shipContSortDir = !shipContSortDir;
        } else {
            shipContSortCol = sortCol;
            shipContSortDir = true;
        }
    }

    /**
     *
     * @param sortCol
     * @return
     */
    public String getShipContSortId(String sortCol) {
        return (!shipContSortCol.equals(sortCol)) ? "normal" : (shipContSortDir) ? "normalu" : "normalo";
    }

    /**
     * Sets the current ShipCont to the one indicated by the shipContId
     * @param shipContId the Id of the ShipCont object
     * @throws HibernateException
     */
    public void setCurrentShipCont(String shipContId) throws HibernateException {
        currentShipCont = (ShipCont) getObjectById(ShipCont.class, shipContId);
    }

    /**
     * Check if a specified container is marked as shipped
     * @param container The container to check
     * @return True if shipped, else False
     */
    public boolean checkIfShipped(Container container) {
        List<ShipCont> shipList = (container != null) ? (List<ShipCont>) container.getShipCont() : null;
        ShipCont shipCont;
        Shipment shipment;
        shipCont = (shipList != null && shipList.size() > 0) ? shipList.get(shipList.size() - 1) : null;
        shipment = (shipCont != null) ? shipCont.getShipment() : null;
        if (shipment != null && shipment.getShipAction().equals("1")) {
            return true;
        }
        return false;
    }


    private Gene currentGene = null;
    public int addGene(String geneID, String name, String publicFlag, String entrezGeneID, String arm) throws Exception {
        openHibSession();
        int rezult = 0;
        //Println used to be here

        if (currentUser == null) {
            return 1;
        }

        HashMap uniques = new HashMap();
        //subjectid, sampleName is unique
        //uniques.put("sampleID", sampleID);
        uniques.put("name", "'" + name + "'");
        Gene findGene = (Gene) getObjectByUniqueKey(Gene.class, uniques);
        if (findGene != null){
            rezult = 1;
            currentGene = findGene;
        }

        //Println used to be here
        if (rezult == 0) {
            Gene tmpGene = new Gene();
            tmpGene.setCreateInfo(currentUser);
            tmpGene.setGeneID(geneID);
            tmpGene.setArm(arm);
            tmpGene.setEntrezGeneID(entrezGeneID);
            tmpGene.setName(name);
            tmpGene.setPublicFlag(publicFlag);

            //Println used to be here
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpGene);
                tx.commit();
                currentGene = tmpGene;

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public Gene getCurrentGene(){
        return currentGene;
    }

    GeneSet currentGeneSet = null;
    public int addGeneSet(GeneSetLookUp geneSetLookUp, Gene gene) throws Exception{
        openHibSession();
        int rezult = 0;
        //Println used to be here

        if (currentUser == null) {
            return 1;
        }

        HashMap uniques = new HashMap();
        //subjectid, sampleName is unique
        //uniques.put("sampleID", sampleID);
        uniques.put("geneID", gene.getId().toString());
        uniques.put("geneSetID", geneSetLookUp.getId().toString());
        GeneSet findGeneSet = (GeneSet) getObjectByUniqueKey(GeneSet.class, uniques);
        if (findGeneSet != null){
            rezult = 1;
            currentGeneSet = findGeneSet;
        }

        //Println used to be here
        if (rezult == 0) {
            GeneSet tmpGeneSet = new GeneSet();
            tmpGeneSet.setCreateInfo(currentUser);
            tmpGeneSet.setGeneSetLookUp(geneSetLookUp);
            tmpGeneSet.setGene(gene);

            //Println used to be here
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpGeneSet);
                tx.commit();
                currentGeneSet = tmpGeneSet;

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public GeneSet getCurrentGeneSet(){
        return currentGeneSet;
    }

    GeneSetLookUp currentGeneSetLookUp = null;
    public int addGeneSetLookUp(String geneSetID, String name) throws Exception{
        openHibSession();
        int rezult = 0;
        //Println used to be here

        if (currentUser == null) {
            return 1;
        }

        HashMap uniques = new HashMap();
        //subjectid, sampleName is unique
        //uniques.put("sampleID", sampleID);
        uniques.put("name", "'" + name + "'");
        GeneSetLookUp findGeneSetLookUp = (GeneSetLookUp) getObjectByUniqueKey(GeneSetLookUp.class, uniques);
        if (findGeneSetLookUp != null){
            rezult = 1;
            currentGeneSetLookUp = findGeneSetLookUp;
        }

        //Println used to be here
        if (rezult == 0) {
            GeneSetLookUp tmpGeneSetLookUp = new GeneSetLookUp();
            tmpGeneSetLookUp.setCreateInfo(currentUser);
            tmpGeneSetLookUp.setGeneSetID(geneSetID);
            tmpGeneSetLookUp.setName(name);

            //Println used to be here
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpGeneSetLookUp);
                tx.commit();
                currentGeneSetLookUp = tmpGeneSetLookUp;

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public GeneSetLookUp getCurrentGeneSetLookUp(){
        return currentGeneSetLookUp;
    }

    SnpSet currentSnpSet = null;
    public int addSnpSet(SnpSetLookUp snpSetLookUp, Snp snp) throws Exception{
        openHibSession();
        int rezult = 0;
        //Println used to be here

        if (currentUser == null) {
            return 1;
        }

        HashMap uniques = new HashMap();
        //subjectid, sampleName is unique
        //uniques.put("sampleID", sampleID);
        uniques.put("snpID", snp.getId().toString());
        uniques.put("snpSetID", snpSetLookUp.getId().toString());
        SnpSet findSnpSet = (SnpSet) getObjectByUniqueKey(SnpSet.class, uniques);
        if (findSnpSet != null){
            rezult = 1;
            currentSnpSet = findSnpSet;
        }

        //Println used to be here
        if (rezult == 0) {
            SnpSet tmpSnpSet = new SnpSet();
            tmpSnpSet.setCreateInfo(currentUser);
            tmpSnpSet.setSnpSetLookUp(snpSetLookUp);
            tmpSnpSet.setSnp(snp);

            //Println used to be here
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpSnpSet);
                tx.commit();
                currentSnpSet = tmpSnpSet;

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public SnpSet getCurrentSnpSet(){
        return currentSnpSet;
    }

    SnpSetLookUp currentSnpSetLookUp = null;
    public int addSnpSetLookUp(String snpSetID, String name) throws Exception{
        openHibSession();
        int rezult = 0;
        //Println used to be here

        if (currentUser == null) {
            return 1;
        }

        HashMap uniques = new HashMap();
        //subjectid, sampleName is unique
        //uniques.put("sampleID", sampleID);
        uniques.put("name", "'" + name + "'");
        SnpSetLookUp findSnpSetLookUp = (SnpSetLookUp) getObjectByUniqueKey(SnpSetLookUp.class, uniques);
        if (findSnpSetLookUp != null){
            rezult = 1;
            currentSnpSetLookUp = findSnpSetLookUp;
        }

        //Println used to be here
        if (rezult == 0) {
            SnpSetLookUp tmpSnpSetLookUp = new SnpSetLookUp();
            tmpSnpSetLookUp.setCreateInfo(currentUser);
            tmpSnpSetLookUp.setSnpSetID(snpSetID);
            tmpSnpSetLookUp.setName(name);

            //Println used to be here
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(tmpSnpSetLookUp);
                tx.commit();
                currentSnpSetLookUp = tmpSnpSetLookUp;

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeHibSession(); //needs before forward
            }
        }
        return rezult;
    }

    public SnpSetLookUp getCurrentSnpSetLookUp(){
        return currentSnpSetLookUp;
    }
//    public int updateSample(String sampleID, String sampleName, String valid, Sample parent, SampleType sampleType, Subject subject, Date collectionDate, Date extractionDate, String comments) throws Exception {
//        int rezult = 0;
//        ////Println used to be here
//
//        //Println used to be here
//        if (currentUser == null) {
//            return 1;
//        }
//        Sample tmpSample = (Sample) getObjectById(Sample.class, sampleID);
//
//        if (tmpSample == null) {
//            return 1;
//        }
//        //Println used to be here
//        //Println used to be here
//        //Println used to be here
//
//        HashMap uniques = new HashMap();
//        uniques.put("sampleName", "'" + sampleName + "'");
//        rezult = ((countKey(Sample.class, uniques) <= 1) ? 0 : 3);
//
//        //Println used to be here
//        if (rezult == 0) {
//            tmpSample.setModifInfo(currentUser);
//            tmpSample.setSampleID(sampleID);
//            tmpSample.setSampleName(sampleName);
//            tmpSample.setValid(valid);
//            tmpSample.setParent(parent);
//            tmpSample.setSampleType(sampleType);
//            tmpSample.setSubject(subject);
//            // collection date not nullable, though it should be
//            if (collectionDate == null) {
//                Calendar myCal = Calendar.getInstance();
//                myCal.set(0001, 00, 01);
//                collectionDate = myCal.getTime();
//            }
//            tmpSample.setDateCollected(collectionDate);
//            tmpSample.setDateExtracted(extractionDate);
//            tmpSample.setComments(comments);
//
//            Transaction tx = null;
//            try {
//                tx = hibSess.beginTransaction();
//                hibSess.update(tmpSample);
//                tx.commit();
//            } catch (Exception e) {
//                if (tx != null) {
//                    tx.rollback();
//                }
//                throw e;
//            } finally {
//                closeHibSession(); //needs before forward
//            }
//        }
//        return rezult;
//    }

}
