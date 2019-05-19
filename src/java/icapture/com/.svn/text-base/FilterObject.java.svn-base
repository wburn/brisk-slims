/**
 * Sets up power searches. Checks every request field for input and if it
 * is found, adds that field to the query (method: setFilter(HttpServletRequest request)).
 * Prepares strings to add to queries that return search results (get<Object>String()).
 * @author tvanrossum
 */
package icapture.com;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Date;
import java.sql.*;
import java.util.Calendar;

public final class FilterObject {

    private String subjectString = null;
    private String sampleString = null;
    private String containerString = null;
    private String shipment = null;
    private String containerContentString = null;
    private String sampleDocumentsString = null;
    private String shipmentString = null;
    private boolean subtypeNeeded;
    HashMap hm = new HashMap();
    private boolean queryingSubject = false;
    private boolean queryingSample = false;
    private boolean queryingContent = false;
    private boolean queryingContainer = false;
    private boolean queryingSampleDocuments = false;
    private boolean queryingShipment = false;

    /**
     * Checks every request field for input and if it is found, adds that field to the query
     * @param request
     * @return
     */
    public HashMap setFilter(HttpServletRequest request) {
        HashMap uvz = new HashMap();
        StringBuffer xyz = new StringBuffer();

        //  SAMPLE DOCUMENTS
        String[] docFilename = request.getParameterValues("docFilename");
        addValues("docFilename", docFilename, uvz);
        String docFilenameExact = request.getParameter("docFilenameExact");
        addValue("docFilenameExact", docFilenameExact, uvz);
        String docFilenameNot = request.getParameter("docFilenameNot");
        addValue("docFilenameNot", docFilenameNot, uvz);
        String[] docComments = request.getParameterValues("docComments");
        addValues("docComments", docComments, uvz);
        String docCommentsExact = request.getParameter("docCommentsExact");
        addValue("docCommentsExact", docCommentsExact, uvz);
        String docCommentsNot = request.getParameter("docCommentsNot");
        addValue("docCommentsNot", docCommentsNot, uvz);

        if (addConjunct("mySampleDocuments.filename", docFilename, xyz, "\'", docFilenameExact, docFilenameNot)) {
            xyz.append(" and ");
        }

        if (addConjunct("mySampleDocuments.comments", docComments, xyz, "\'", docCommentsExact, docCommentsNot)) {
            xyz.append(" and ");
        }

        sampleDocumentsString = (xyz.length() > 0) ? xyz.toString() : null;
        queryingSampleDocuments = (xyz.length() > 0) ? true : false;

        // SUBJECT
        String[] subjName = request.getParameterValues("subjName");
        addValues("subjName", subjName, uvz);
        String[] subjMother = request.getParameterValues("subjMother");
        addValues("subjMother", subjMother, uvz);
        String[] subjFather = request.getParameterValues("subjFather");
        addValues("subjFather", subjFather, uvz);
        String[] subjGender = request.getParameterValues("subjGender");
        addValues("subjGender", subjGender, uvz);
        String[] subjCohort = request.getParameterValues("subjCohort");
        addValues("subjCohort", subjCohort, uvz);
        String[] subjFamily = request.getParameterValues("subjFamily");
        addValues("subjFamily", subjFamily, uvz);
        String[] subjHasConsent = request.getParameterValues("subjHasConsent");
        addValues("subjHasConsent", subjHasConsent, uvz);
        String[] subjEthnicity = request.getParameterValues("subjEthnicity");
        addValues("subjEthnicity", subjEthnicity, uvz);
        String exactN = request.getParameter("subjNameExact");
        addValue("subjNameExact", exactN, uvz);
        String exactM = request.getParameter("subjMotherExact");
        addValue("subjMotherExact", exactM, uvz);
        String exactF = request.getParameter("subjFatherExact");
        addValue("subjFatherExact", exactF, uvz);
        String exactFam = request.getParameter("subjFamilyExact");
        addValue("subjFamilyExact", exactFam, uvz);
        String[] subjComments = request.getParameterValues("subjComments");
        addValues("subjComments", subjComments, uvz);
        String exactsubjCom = request.getParameter("subjCommentExact");
        addValue("subjCommentExact", exactsubjCom, uvz);
        String notsubjCom = request.getParameter("subjCommentNot");
        addValue("subjCommentNot", notsubjCom, uvz);

        String notN = request.getParameter("subjNameNot");
        addValue("subjNameNot", notN, uvz);
        String notM = request.getParameter("subjMotherNot");
        addValue("subjMotherNot", notM, uvz);
        String notF = request.getParameter("subjFatherNot");
        addValue("subjFatherNot", notF, uvz);
        String notFam = request.getParameter("subjFamilyNot");
        addValue("subjFamilyNot", notFam, uvz);

        xyz = new StringBuffer();
        if (addConjunct("mySubject.subjectName", subjName, xyz, "\'", exactN, notN)) {
            xyz.append(" and ");
        }
        if (addConjunct("mySubject.motherName", subjMother, xyz, "\'", exactM, notM)) {
            xyz.append(" and ");
        }
        if (addConjunct("mySubject.fatherName", subjFather, xyz, "\'", exactF, notF)) {
            xyz.append(" and ");
        }
        if (addConjunct("mySubject.familyID", subjFamily, xyz, "\'", exactFam, notFam)) {
            xyz.append(" and ");
        }
        if (addConjunct("mySubject.gender", subjGender, xyz, "")) {
            xyz.append(" and ");
        }
        if (addConjunct("mySubject.hasConsent", subjHasConsent, xyz, "")) {
            xyz.append(" and ");
        }
        if (addConjunct("myCohort.description", subjCohort, xyz, "\'")) {
            xyz.append(" and ");
            hm.put("cohortAdded", true);
        }
        if (addConjunct("myEthnicity.ethnicity", subjEthnicity, xyz, "\'")) {
            xyz.append(" and ");
            hm.put("ethnicityAdded", true);
        }
        if (addConjunct("mySubject.comments", subjComments, xyz, "\'", exactsubjCom, notsubjCom)) {
            xyz.append(" and ");
        }
        int j = xyz.length();

        subjectString = (xyz.length() > 0) ? xyz.toString() : null;
        queryingSubject = (xyz.length() > 0) ? true : false;

        // SAMPLE---------------------------------------------------------------
        String[] samName = request.getParameterValues("samName");
        addValues("samName", samName, uvz);
        String[] samTypeDesc = request.getParameterValues("samTypeDesc");
        addValues("samTypeDesc", samTypeDesc, uvz);
        String[] samProcDesc = request.getParameterValues("samProcDesc");
        addValues("samProcDesc", samProcDesc, uvz);
        String[] samValid = request.getParameterValues("samValid");
        addValues("samValid", samValid, uvz);
        String exactSN = request.getParameter("samNameExact");
        addValue("samNameExact", exactSN, uvz);
        String notSN = request.getParameter("samNameNot");
        addValue("samNameNot", notSN, uvz);

        xyz = new StringBuffer();
        if (addConjunct("mySample.sampleName", samName, xyz, "\'", exactSN, notSN)) {
            xyz.append(" and ");
        }
        if (addConjunct("mySampleType.description", samTypeDesc, xyz, "\'")) {
            xyz.append(" and ");
            hm.put("sampleTypeAdded", true);
        }
        if (addConjunct("mySampleProcess.description", samProcDesc, xyz, "\'")) {
            xyz.append(" and ");
            hm.put("sampleProcessAdded", true);
        }
        if (addConjunct("mySample.valid", samValid, xyz, "")) {
            xyz.append(" and ");
        }

        //------------ start of sample collection date ------------------
        String ayear = request.getParameter("sampleCollDateayear");
        addValue("sampleCollDateayear", ayear, uvz);
        String amonth = request.getParameter("sampleCollDateamonth");
        addValue("sampleCollDateamonth", amonth, uvz);
        String aday = request.getParameter("sampleCollDateaday");
        addValue("sampleCollDateaday", aday, uvz);
        Date myDate = getLowDate(ayear, amonth, aday);

        if (myDate != null) {
            uvz.put("sampleCollDatea", myDate);
            java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
            xyz.append(" mySample.dateCollected >= '" + sqlDate + "'");
            xyz.append(" and ");
        }
        String byear = request.getParameter("sampleCollDatebyear");
        addValue("sampleCollDatebyear", byear, uvz);
        String bmonth = request.getParameter("sampleCollDatebmonth");
        addValue("sampleCollDatebmonth", bmonth, uvz);
        String bday = request.getParameter("sampleCollDatebday");
        addValue("sampleCollDatebday", bday, uvz);
        myDate = getHighDate(byear, bmonth, bday);

        if (myDate != null) {
            uvz.put("sampleCollDateb", myDate);
            java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
            xyz.append(" mySample.dateCollected <= '" + sqlDate + "'");
        }
        // ------------ end of sample collection date-------

        //------------ start of sample extraction date ------------------
        ayear = request.getParameter("sampleExtracDateayear");
        addValue("sampleExtracDateayear", ayear, uvz);
        amonth = request.getParameter("sampleExtracDateamonth");
        addValue("sampleExtracDateamonth", amonth, uvz);
        aday = request.getParameter("sampleExtracDateaday");
        addValue("sampleExtracDateaday", aday, uvz);
        myDate = getLowDate(ayear, amonth, aday);

        if (myDate != null) {
            uvz.put("sampleExtracDatea", myDate);
            java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
            xyz.append(" mySample.dateExtracted >= '" + sqlDate + "'");
            xyz.append(" and ");
        }
        byear = request.getParameter("sampleExtracDatebyear");
        addValue("sampleExtracDatebyear", byear, uvz);
        bmonth = request.getParameter("sampleExtracDatebmonth");
        addValue("sampleExtracDatebmonth", bmonth, uvz);
        bday = request.getParameter("sampleExtracDatebday");
        addValue("sampleExtracDatebday", bday, uvz);
        myDate = getHighDate(byear, bmonth, bday);

        if (myDate != null) {
            uvz.put("sampleExtracDateb", myDate);
            java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
            xyz.append(" mySample.dateExtracted <= '" + sqlDate + "'");
        }
        // ------------ end of sample extraction date-------

        j = xyz.length();
        sampleString = (xyz.length() > 0) ? xyz.toString() : null;
        queryingSample = (xyz.length() > 0) ? true : false;

        // CONTAINER
        String[] contName = request.getParameterValues("contName");
        addValues("contName", contName, uvz);
        String exactCN = request.getParameter("contNameExact");
        addValue("contNameExact", exactCN, uvz);
        String notCN = request.getParameter("contNameNot");
        addValue("contNameNot", notCN, uvz);
        String exactCA = request.getParameter("contAliasExact");
        addValue("contAliasExact", exactCA, uvz);
        String notCA = request.getParameter("contAliasNot");
        addValue("contAliasNot", notCA, uvz);
        String[] contAlias = request.getParameterValues("contAlias");
        addValues("contAlias", contAlias, uvz);
        String[] contType = request.getParameterValues("contType");
        addValues("contType", contType, uvz);
        String[] contIsStock = request.getParameterValues("contIsStock");
        addValues("contIsStock", contIsStock, uvz);
        String[] contFreez = request.getParameterValues("contFreez");
        addValues("contFreez", contFreez, uvz);
        String[] contDisc = request.getParameterValues("contDisc");
        addValues("contDisc", contDisc, uvz);
        String[] contComments = request.getParameterValues("contComments");
        addValues("contComments", contComments, uvz);
        String exactCom = request.getParameter("contCommentsExact");
        addValue("contCommentsExact", exactCom, uvz);
        String notCom = request.getParameter("contCommentsNot");
        addValue("contCommentsNot", notCom, uvz);
        String[] contMaker = request.getParameterValues("contMaker");
        addValues("contMaker", contMaker, uvz);
        String exactMaker = request.getParameter("contMakerExact");
        addValue("contMakerExact", exactMaker, uvz);
        String notMaker = request.getParameter("contMakerNot");
        addValue("contMakerNot", notMaker, uvz);

        String[] contBarcode = request.getParameterValues("contBarcode");
        addValues("contBarcode", contBarcode, uvz);
        String exactBar = request.getParameter("contBarcodeExact");
        addValue("contBarcodeExact", exactBar, uvz);
        String notBar = request.getParameter("contBarcodeNot");
        addValue("contBarcodeNot", notBar, uvz);


        xyz = new StringBuffer();
        if (addConjunct("myContainer.containerName", contName, xyz, "\'", exactCN, notCN)) {
            xyz.append(" and ");
        }
        if (addConjunct("myContainer.containerAlias", contAlias, xyz, "\'", exactCA, notCA)) {
            xyz.append(" and ");
        }
        if (addConjunct("myContainer.initials", contMaker, xyz, "\'", exactMaker, notMaker)) {
            xyz.append(" and ");
        }
        if (addConjunct("myContainerType.description", contType, xyz, "\'")) {
            xyz.append(" and ");
            hm.put("containerTypeAdded", true);
        }
        if (addConjunct("myContainer.isStock", contIsStock, xyz, "")) {
            xyz.append(" and ");
        }
        if (addConjunct("myFreezer.description", contFreez, xyz, "\'")) {
            xyz.append(" and ");
            hm.put("freezerAdded", true);
        }
        if (addConjunct("myContainer.discarded", contDisc, xyz, "")) {
            xyz.append(" and ");
        }
        if (addConjunct("myContainer.comments", contComments, xyz, "\'", exactCom, notCom)) {
            xyz.append(" and ");
        }
        if (addConjunct("myContainer.barcode", contBarcode, xyz, "\'", exactBar, notBar)) {
            xyz.append(" and ");
        }
        containerString = (xyz.length() > 0) ? xyz.toString() : null;
        queryingContainer = (xyz.length() > 0) ? true : false;
        //------------ container shipped date start ------------------
        //-------------------SHIPMENT-----------------------//
        //-------------------SHIPMENT-----------------------//
        ayear = request.getParameter("contShipDateayear");
        addValue("contShipDateayear", ayear, uvz);
        amonth = request.getParameter("contShipDateamonth");
        addValue("contShipDateamonth", amonth, uvz);
        aday = request.getParameter("contShipDateaday");
        addValue("contShipDateaday", aday, uvz);
        myDate = getLowDate(ayear, amonth, aday);
        String[] contShipOut = request.getParameterValues("contShipOut");
        addValues("contShipOut", contShipOut, uvz);
        String[] contShipTo = request.getParameterValues("contShipTo");
        addValues("contShipTo", contShipTo, uvz);
        xyz = new StringBuffer();

        if (myDate != null) {
            uvz.put("contShipDatea", myDate);
            java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
            xyz.append(" myShipment.shipDate >= '" + sqlDate + "'");
            xyz.append(" and ");
        }
        if (addConjunct("myShipment.shipAction", contShipOut, xyz, "")) {
            xyz.append(" and ");
        }
        if (addConjunct("myShippedTo.description", contShipTo, xyz, "\'")) {
            xyz.append(" and ");
            hm.put("shippedToAdded", true);
        }
        byear = request.getParameter("contShipDatebyear");
        addValue("contShipDatebyear", byear, uvz);
        bmonth = request.getParameter("contShipDatebmonth");
        addValue("contShipDatebmonth", bmonth, uvz);
        bday = request.getParameter("contShipDatebday");
        addValue("contShipDatebday", bday, uvz);
        myDate = getHighDate(byear, bmonth, bday);

        if (myDate != null) {
            uvz.put("contShipDateb", myDate);
            java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
            xyz.append(" myShipment.shipDate <= '" + sqlDate + "'");
        }
        shipmentString = (xyz.length() > 0) ? xyz.toString() : null;
        queryingShipment = (xyz.length() > 0) ? true : false;
        // ------------ end of container shipped date-------

        // CONTAINER CONTENT
        String[] ccBarcode = request.getParameterValues("ccBarcode");
        addValues("ccBarcode", ccBarcode, uvz);
        String exactBarcode = request.getParameter("ccBarcodeExact");
        addValue("ccBarcodeExact", exactBarcode, uvz);
        String notBarcode = request.getParameter("ccBarcodeNot");
        addValue("ccBarcodeNot", notBarcode, uvz);
        String concen = request.getParameter("concen");
        addValue("concen", concen, uvz);
        String concenRel = request.getParameter("concenRel");
        addValue("concenRel", concenRel, uvz);
        String[] ccMaterialType = request.getParameterValues("ccMaterialType");
        addValues("ccMaterialType", ccMaterialType, uvz);
        String vol = request.getParameter("vol");
        addValue("vol", vol, uvz);
        String volRel = request.getParameter("volRel");
        addValue("volRel", volRel, uvz);
        String dna = request.getParameter("dna");
        addValue("dna", dna, uvz);
        String dnaRel = request.getParameter("dnaRel");
        addValue("dnaRel", dnaRel, uvz);
        String[] ccContaminated = request.getParameterValues("ccContaminated");
        addValues("ccContaminated", ccContaminated, uvz);
        String[] contentComments = request.getParameterValues("contentComments");
        addValues("contComments", contComments, uvz);
        String exactContentCom = request.getParameter("contentCommentsExact");
        addValue("contentCommentsExact", exactContentCom, uvz);
        String notContentCom = request.getParameter("contentCommentsNot");
        addValue("contentCommentsNot", notContentCom, uvz);

        xyz = new StringBuffer();
        if (addConjunct("myContainerContent.barcode", ccBarcode, xyz, "\'", exactBarcode, notBarcode)) {
            xyz.append(" and ");
        }
        if (addConjunctNum("myContainerContent.concentration", concen, xyz, concenRel)) {
            xyz.append(" and ");
        }
        if (addConjunctNum("myContainerContent.volume", vol, xyz, volRel)) {
            xyz.append(" and ");
        }
        if (addConjunctNum("myContainerContent.volume*myContainerContent.concentration", dna, xyz, dnaRel)) {
            xyz.append(" and ");
        }
        if (addConjunct("myContainerContent.contaminated", ccContaminated, xyz, "")) {
            xyz.append(" and ");
        }
        if (addConjunct("myContainerContent.materialType.description", ccMaterialType, xyz, "\'")) {
            xyz.append(" and ");
        }
        if (addConjunct("myContainerContent.comments", "mySample.comments", contentComments, xyz, "\'", exactContentCom, notContentCom)) {
            xyz.append(" and ");
            // need tblsamples join
            if (sampleString == null) {
                sampleString = "";
            }
        }
        j = xyz.length();

        containerContentString = (xyz.length() > 0) ? xyz.toString() : null;
        queryingContent = (xyz.length() > 0) ? true : false;


        return uvz;
    }

    public String getSubjectString() {
        StringBuffer xyz = new StringBuffer();
        StringBuffer froms = new StringBuffer();
        StringBuffer joins = new StringBuffer();
        StringBuffer filters = new StringBuffer();
//    xyz.append("select mySubject from Subject as mySubject");

        joins.append(" where ");

        if (subjectString != null) {
            filters.append(subjectString);
        }
        if (shipmentString != null) {
            froms.append(", Sample as mySample, Container as myContainer, ContainerContent as myContainerContent, Shipment as myShipment, ShipCont as myShipCont");
            joins.append(" myShipment.shipmentID = myShipCont.shipment.shipmentID and "
                    + "myShipCont.container.containerID = myContainer.containerID and "
                    + "mySubject.subjectID=mySample.subject.subjectID and "
                    + "mySample.sampleID=myContainerContent.sample.sampleID and "
                    + "myContainerContent.container.containerID=myContainer.containerID and ");
            if (containerContentString != null) {
                filters.append(containerContentString);
            }
            if (sampleString != null) {
                filters.append(sampleString);
            }
            if (containerString != null) {
                filters.append(containerString);
            }
            filters.append(shipmentString);
        } else if (containerString != null) {
            froms.append(", Shipment as myShipment, Sample as mySample, Container as myContainer, ContainerContent as myContainerContent");
            joins.append(" mySubject.subjectID=mySample.subject.subjectID and "
                    + "mySample.sampleID=myContainerContent.sample.sampleID and "
                    + "myContainerContent.container.containerID=myContainer.containerID and ");
            if (containerContentString != null) {
                filters.append(containerContentString);
            }
            if (sampleString != null) {
                filters.append(sampleString);
            }
            filters.append(containerString);
        } else if (containerContentString != null) {
            froms.append(", Shipment as myShipment, Sample as mySample, ContainerContent as myContainerContent");
            joins.append(" mySubject.subjectID=mySample.subject.subjectID and "
                    + "mySample.sampleID=myContainerContent.sample.sampleID and ");
            if (sampleString != null) {
                filters.append(sampleString);
            }
            filters.append(containerContentString);

        } else if (sampleDocumentsString != null) {
            froms.append(", Shipment as myShipment, Sample as mySample, SampleDocuments as mySampleDocuments");
            joins.append(" mySubject.subjectID=mySample.subject.subjectID and "
                    + "mySample.sampleID=mySampleDocuments.sample.sampleID and ");
            if (sampleString != null) {
                filters.append(sampleString);
            }
            filters.append(sampleDocumentsString);
        } else if (sampleString != null) {
            froms.append(", Shipment as myShipment, Sample as mySample");
            joins.append(" mySubject.subjectID=mySample.subject.subjectID and ");
            filters.append(sampleString);
        }
        if (hm.get("cohortAdded") != null) {
            froms.append(", Cohort as myCohort ");
            joins.append(" mySubject.cohort.cohortID=myCohort.cohortID and ");
        }
        if (hm.get("ethnicityAdded") != null) {
            froms.append(", Ethnicity as myEthnicity ");
            joins.append(" mySubject.ethnicity.ethnicityID=myEthnicity.ethnicityID and ");
        }
        if (hm.get("sampleTypeAdded") != null) {
            froms.append(", SampleType as mySampleType ");
            joins.append(" mySample.sampleType.sampleTypeID=mySampleType.sampleTypeID and ");
        }
        if (hm.get("sampleProcessAdded") != null) {
            froms.append(", SampleProcess as mySampleProcess ");
            joins.append(" mySample.sampleProcess.sampleProcessID=mySampleProcess.sampleProcessID and ");
        }
        if (hm.get("freezerAdded") != null) {
            froms.append(", Freezer as myFreezer ");
            joins.append(" myContainer.freezer.freezerID=myFreezer.freezerID and ");
        }
        if (hm.get("shippedToAdded") != null) {
            froms.append(", ShippedTo as myShippedTo ");
            joins.append(" myShipment.shippedTo.shippedToID=myShippedTo.shippedToID and ");
        }
        if (hm.get("containerTypeAdded") != null) {
            froms.append(", ContainerType as myContainerType ");
            joins.append(" myContainer.containerType.containerTypeID=myContainerType.containerTypeID and ");
        }

        trimAnd(froms);
        xyz.append(froms);
        if (filters.length() < 1) {
            trimAnd(joins);
        }
        if (joins.toString().equals(" where") && filters.length() < 1) {
            joins = new StringBuffer();
            filters = new StringBuffer();
        }
        xyz.append(joins);
        trimAnd(filters);
        xyz.append(filters);

        System.out.println("query: " + xyz.toString());
        return xyz.toString();
    }

    public String getSampleString() {
        StringBuffer xyz = new StringBuffer();
        StringBuffer froms = new StringBuffer();
        StringBuffer joins = new StringBuffer();
        StringBuffer filters = new StringBuffer();
//    xyz.append("select mySample from Sample as mySample");

        joins.append(" where ");

        if (sampleString != null) {
            filters.append(sampleString);
        }

        if (containerString != null) {
            froms.append(", Shipment as myShipment, Container as myContainer, ContainerContent as myContainerContent");
            joins.append(" mySample.sampleID=myContainerContent.sample.sampleID and "
                    + "myContainerContent.container.containerID=myContainer.containerID and ");
            if (containerContentString != null) {
                filters.append(containerContentString);
            }
            filters.append(containerString);
        } else if (containerContentString != null) {
            froms.append(", Shipment as myShipment, ContainerContent as myContainerContent");
            joins.append(" mySample.sampleID=myContainerContent.sample.sampleID and ");
            filters.append(containerContentString);
        }
        if (sampleDocumentsString != null) {
            froms.append(", Shipment as myShipment, SampleDocuments as mySampleDocuments");
            joins.append(" mySample.sampleID=mySampleDocuments.sample.sampleID and ");
            filters.append(sampleDocumentsString);
        }
        if (subjectString != null) {
            froms.append(", Shipment as myShipment, Subject as mySubject");
            joins.append(" mySample.subject.subjectID=mySubject.subjectID and ");
            filters.append(subjectString);
        }
        if (shipmentString != null) {
            froms.append(", Shipment as myShipment, ShipCont as myShipCont, Container as myContainer, ContainerContent as myContainerContent");
            joins.append(" myShipment.shipmentID = myShipCont.shipment.shipmentID and "
                    + "myShipCont.container.containerID = myContainer.containerID and "
                    + "mySample.sampleID=myContainerContent.sample.sampleID and "
                    + "myContainerContent.container.containerID=myContainer.containerID and ");
            filters.append(shipmentString);
        }
        if (hm.get("cohortAdded") != null) {
            froms.append(", Cohort as myCohort ");
            joins.append(" mySubject.cohort.cohortID=myCohort.cohortID and ");
        }
        if (hm.get("ethnicityAdded") != null) {
            froms.append(", Ethnicity as myEthnicity ");
            joins.append(" mySubject.ethnicity.ethnicityID=myEthnicity.ethnicityID and ");
        }
        if (hm.get("sampleTypeAdded") != null) {
            froms.append(", SampleType as mySampleType ");
            joins.append(" mySample.sampleType.sampleTypeID=mySampleType.sampleTypeID and ");
        }
        if (hm.get("sampleProcessAdded") != null) {
            froms.append(", SampleProcess as mySampleProcess ");
            joins.append(" mySample.sampleProcess.sampleProcessID=mySampleProcess.sampleProcessID and ");
        }
        if (hm.get("freezerAdded") != null) {
            froms.append(", Freezer as myFreezer ");
            joins.append(" myContainer.freezer.freezerID=myFreezer.freezerID and ");
        }
        if (hm.get("shippedToAdded") != null) {
            froms.append(", ShippedTo as myShippedTo ");
            joins.append(" myShipment.shippedTo.shippedToID=myShippedTo.shippedToID and ");
        }
        if (hm.get("containerTypeAdded") != null) {
            froms.append(", ContainerType as myContainerType ");
            joins.append(" myContainer.containerType.containerTypeID=myContainerType.containerTypeID and ");
        }
        System.out.println("xyz: '" + xyz.toString());
        trimAnd(froms);
        xyz.append(froms);
        System.out.println("froms: '" + froms.toString());
        if (filters.length() < 1) {
            trimAnd(joins);
        }
        xyz.append(joins);
        System.out.println("joins: '" + joins.toString());
        trimAnd(filters);
        xyz.append(filters);
        System.out.println("filters: '" + filters.toString());

        System.out.println("query: " + xyz.toString());
        return xyz.toString();
    }

    public String getContainerString() {
        StringBuffer xyz = new StringBuffer();
        StringBuffer froms = new StringBuffer();
        StringBuffer joins = new StringBuffer();
        StringBuffer filters = new StringBuffer();
//    xyz.append("select mySample from Sample as mySample");

        joins.append(" where ");

        if (containerString != null) {
            filters.append(containerString);
        }

        if (subjectString != null) {
            froms.append(", Shipment as myShipment, ContainerContent as myContainerContent, Sample as mySample, Subject as mySubject");
            joins.append(" mySubject.subjectID=mySample.subject.subjectID and "
                    + "mySample.sampleID=myContainerContent.sample.sampleID and "
                    + "myContainerContent.container.containerID=myContainer.containerID and ");
            if (containerContentString != null) {
                filters.append(containerContentString);
            }
            if (sampleString != null) {
                filters.append(sampleString);
            }
            filters.append(subjectString);
        } else if (sampleDocumentsString != null) {
            froms.append(", Shipment as myShipment, ContainerContent as myContainerContent, Sample as mySample, SampleDocuments as mySampleDocuments");
            joins.append(" mySample.sampleID=myContainerContent.sample.sampleID and "
                    + "mySample.sampleID=mySampleDocuments.sample.sampleID and "
                    + "myContainerContent.container.containerID=myContainer.containerID and ");
            if (sampleString != null) {
                filters.append(sampleString);
            }
            if (containerContentString != null) {
                filters.append(containerContentString);
            }
            filters.append(sampleDocumentsString);
        } else if (sampleString != null) {
            froms.append(", Shipment as myShipment, ContainerContent as myContainerContent, Sample as mySample");
            joins.append(" mySample.sampleID=myContainerContent.sample.sampleID and "
                    + "myContainerContent.container.containerID=myContainer.containerID and ");
            if (containerContentString != null) {
                filters.append(containerContentString);
            }
            filters.append(sampleString);
        } else if (containerContentString != null) {
            froms.append(", Shipment as myShipment, ContainerContent as myContainerContent");
            joins.append(" myContainerContent.container.containerID=myContainer.containerID and ");
            filters.append(containerContentString);
        } else if (shipmentString != null) {
            froms.append(", Shipment as myShipment, ShipCont as myShipCont");
            joins.append(" myShipment.shipmentID = myShipCont.shipment.shipmentID and "
                    + "myShipCont.container.containerID = myContainer.containerID and ");
            filters.append(shipmentString);
        }
        if (hm.get("cohortAdded") != null) {
            froms.append(", Cohort as myCohort ");
            joins.append(" mySubject.cohort.cohortID=myCohort.cohortID and ");
        }
        if (hm.get("ethnicityAdded") != null) {
            froms.append(", Ethnicity as myEthnicity ");
            joins.append(" mySubject.ethnicity.ethnicityID=myEthnicity.ethnicityID and ");
        }
        if (hm.get("sampleTypeAdded") != null) {
            froms.append(", SampleType as mySampleType ");
            joins.append(" mySample.sampleType.sampleTypeID=mySampleType.sampleTypeID and ");
        }
        if (hm.get("sampleProcessAdded") != null) {
            froms.append(", SampleProcess as mySampleProcess ");
            joins.append(" mySample.sampleProcess.sampleProcessID=mySampleProcess.sampleProcessID and ");
        }
        if (hm.get("freezerAdded") != null) {
            froms.append(", Freezer as myFreezer ");
            joins.append(" myContainer.freezer.freezerID=myFreezer.freezerID and ");
        }
        if (hm.get("shippedToAdded") != null) {
            froms.append(", ShippedTo as myShippedTo ");
            joins.append(" myShipment.shippedTo.shippedToID=myShippedTo.shippedToID and ");
        }
        if (hm.get("containerTypeAdded") != null) {
            froms.append(", ContainerType as myContainerType ");
            joins.append(" myContainer.containerType.containerTypeID=myContainerType.containerTypeID and ");
        }

        trimAnd(froms);
        xyz.append(froms);
        if (filters.length() < 1) {
            trimAnd(joins);
        }
        if (joins.toString().equals(" where") && filters.length() < 1) {
            joins = new StringBuffer();
            filters = new StringBuffer();
        }
        xyz.append(joins);
        trimAnd(filters);
        xyz.append(filters);

        return xyz.toString();
    }

    public String getContainerContentString() {
        StringBuffer xyz = new StringBuffer();
        StringBuffer froms = new StringBuffer();
        StringBuffer joins = new StringBuffer();
        StringBuffer filters = new StringBuffer();
//    xyz.append("select mySample from Sample as mySample");

        joins.append(" where ");

        if (containerContentString != null) {
            filters.append(containerContentString);
        }

        if (containerString != null) {
            froms.append(", Shipment as myShipment, Container as myContainer ");
            joins.append(" myContainerContent.container.containerID=myContainer.containerID and ");
            filters.append(containerString);
        }
        if (subjectString != null) {
            froms.append(", Shipment as myShipment, Sample as mySample, Subject as mySubject");
            joins.append(" mySubject.subjectID=mySample.subject.subjectID and "
                    + "mySample.sampleID=myContainerContent.sample.sampleID and ");
            if (sampleString != null) {
                filters.append(sampleString);
            }
            filters.append(subjectString);
        } else if (shipmentString != null) {
            froms.append(", Shipment as myShipment, ShipCont as myShipCont, Container as myContainer");
            joins.append(" myShipment.shipmentID = myShipCont.shipment.shipmentID and "
                    + "myShipCont.container.containerID = myContainer.containerID and "
                    + "myContainerContent.container.containerID = myContainer.containerID and ");
            filters.append(shipmentString);
        } else if (sampleDocumentsString != null) {
            froms.append(", Shipment as myShipment, Sample as mySample, SampleDocuments as mySampleDocuments");
            joins.append(" mySample.sampleID=myContainerContent.sample.sampleID and "
                    + "mySample.sampleID=mySampleDocuments.sample.sampleID and ");
            if (sampleString != null) {
                filters.append(sampleString);
            }
            filters.append(sampleDocumentsString);
        } else if (sampleString != null) {
            froms.append(", Shipment as myShipment, Sample as mySample");
            joins.append(" mySample.sampleID=myContainerContent.sample.sampleID and ");
            filters.append(sampleString);
        }

        if (hm.get("cohortAdded") != null) {
            froms.append(", Cohort as myCohort ");
            joins.append(" mySubject.cohort.cohortID=myCohort.cohortID and ");
        }
        if (hm.get("ethnicityAdded") != null) {
            froms.append(", Ethnicity as myEthnicity ");
            joins.append(" mySubject.ethnicity.ethnicityID=myEthnicity.ethnicityID and ");
        }
        if (hm.get("sampleTypeAdded") != null) {
            froms.append(", SampleType as mySampleType ");
            joins.append(" mySample.sampleType.sampleTypeID=mySampleType.sampleTypeID and ");
        }
        if (hm.get("sampleProcessAdded") != null) {
            froms.append(", SampleProcess as mySampleProcess ");
            joins.append(" mySample.sampleProcess.sampleProcessID=mySampleProcess.sampleProcessID and ");
        }
        if (hm.get("freezerAdded") != null) {
            froms.append(", Freezer as myFreezer ");
            joins.append(" myContainer.freezer.freezerID=myFreezer.freezerID and ");
        }
        if (hm.get("shippedToAdded") != null) {
            froms.append(", ShippedTo as myShippedTo ");
            joins.append(" myShipment.shippedTo.shippedToID=myShippedTo.shippedToID and ");
        }
        if (hm.get("containerTypeAdded") != null) {
            froms.append(", ContainerType as myContainerType ");
            joins.append(" myContainer.containerType.containerTypeID=myContainerType.containerTypeID and ");
        }

        trimAnd(froms);
        xyz.append(froms);
        if (filters.length() < 1) {
            trimAnd(joins);
        }
        if (joins.toString().equals(" where") && filters.length() < 1) {
            joins = new StringBuffer();
            filters = new StringBuffer();
        }
        xyz.append(joins);
        trimAnd(filters);
        xyz.append(filters);

        return xyz.toString();
    }

    private boolean addValues(String fieldName, String[] values, HashMap toAppend) {
        if (values == null || values.length == 0 || values[0].equals("")) {
            return false;
        }
        for (int i = 0; i < values.length; i++) {
            String key = fieldName + values[i];
            toAppend.put(key, values[i]);
            toAppend.put(fieldName, values[i]);
        }
        return true;
    }

    private boolean addValue(String fieldName, String value, HashMap toAppend) {
        if (value == null || value.length() == 0 || value.equals("")) {
            return false;
        }
        String key = fieldName + value;
        toAppend.put(key, value);
        toAppend.put(fieldName, value);
        return true;
    }

    private boolean addConjunct(String prefix, String[] values, StringBuffer toAppend, String quote) {
        if (values == null || values.length == 0 || values[0].equals("")) {
            return false;
        }
        toAppend.append("(");
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                toAppend.append(quote + " or ");
            }
            toAppend.append(prefix);
            toAppend.append("=" + quote);
            toAppend.append(values[i]);
        }
        toAppend.append(quote + ")");
        return true;
    }

    private boolean addConjunct(String prefix, String table2, String[] values, StringBuffer toAppend, String quote) {
        if (values == null || values.length == 0 || values[0].equals("")) {
            return false;
        }
        toAppend.append("(");
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                toAppend.append(" or ");
            }
            toAppend.append(prefix);
            toAppend.append("=" + quote);
            toAppend.append(values[i] + quote);
        }
        for (int i = 0; i < values.length; i++) {
            toAppend.append(" or ");
            toAppend.append(table2);
            toAppend.append("=" + quote);
            toAppend.append(values[i] + quote);
        }
        toAppend.append(")");
        return true;
    }

    private boolean addConjunctNum(String prefix, String value, StringBuffer toAppend, String relation) {
        if (value == null || value.equals("")) {
            return false;
        }
        toAppend.append("(");
        toAppend.append(prefix);
        toAppend.append(relation);
        toAppend.append(value);
        toAppend.append(")");
        return true;
    }

    private boolean addConjunct(String prefix, String[] values, StringBuffer toAppend, String quote, String exact, String not) {
        if (values == null || values.length == 0 || values[0].equals("")) {
            return false;
        }
        if (exact != null && exact.equalsIgnoreCase("true")) {
            return addConjunct(prefix, values, toAppend, quote);
        }
        if (not != null && not.equalsIgnoreCase("true")) {
            toAppend.append(" NOT ");
        }
        toAppend.append("(");
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                toAppend.append(quote + " or ");
            }
            toAppend.append(" UPPER(" + prefix);
            toAppend.append(") like " + quote + "%");
            toAppend.append(values[i].toUpperCase());
        }
        toAppend.append("%" + quote + ")");
        return true;
    }

    private boolean addConjunct(String prefix, String table2, String[] values, StringBuffer toAppend, String quote, String exact, String not) {
        if (values == null || values.length == 0 || values[0].equals("")) {
            return false;
        }
        if (exact != null && exact.equalsIgnoreCase("true")) {
            return addConjunct(prefix, table2, values, toAppend, quote);
        }
        if (not != null && not.equalsIgnoreCase("true")) {
            toAppend.append(" NOT ");
        }
        toAppend.append("(");
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                toAppend.append(quote + " or ");
            }
            toAppend.append(" UPPER(" + prefix);
            toAppend.append(") like " + quote + "%");
            toAppend.append(values[i].toUpperCase() + "%" + quote);
        }
        for (int i = 0; i < values.length; i++) {
            toAppend.append(" or ");
            toAppend.append(" UPPER(" + table2);
            toAppend.append(") like " + quote + "%");
            toAppend.append(values[i].toUpperCase() + "%" + quote);
        }
        toAppend.append(")");
        return true;
    }

    public static Date getLowDate(String year, String month, String day) {
        Date myDate = null;
        try {
            int yy = Integer.parseInt(year);
            if (yy > 1900 && yy < 2100) {
                int mm = (month.equals("")) ? 1 : Integer.parseInt(month);
                if (mm > 0 && mm < 13) {
                    int dd = (day.equals("")) ? 1 : Integer.parseInt(day);
                    if (dd > 0 & dd < 32) {
                        Calendar myCal = Calendar.getInstance();
                        myCal.set(yy, mm - 1, dd, 0, 0);
                        myDate = myCal.getTime();
                    }
                }
            }
        } catch (Exception ex) {
        }
        return myDate;
    }

    public static Date getHighDate(String year, String month, String day) {
        Date myDate = null;
        try {
            int yy = Integer.parseInt(year);
            if (yy > 1900 && yy < 2100) {
                int mm = (month.equals("")) ? 12 : Integer.parseInt(month);
                if (mm > 0 && mm < 13) {
                    int dd = (day.equals("")) ? 31 : Integer.parseInt(day);
                    if (dd > 0 & dd < 32) {
                        Calendar myCal = Calendar.getInstance();
                        myCal.set(yy, mm - 1, dd, 23, 59);
                        myDate = myCal.getTime();
                    }
                }
            }
        } catch (Exception ex) {
        }
        return myDate;
    }

    private Date getLowDate(String year) {
        Date myDate = null;
        try {
            int yy = Integer.parseInt(year);
            if (yy > 1900 && yy < 2100) {
                Calendar myCal = Calendar.getInstance();
                myCal.set(yy, 0, 1, 0, 0);
                myDate = myCal.getTime();
            }
        } catch (Exception ex) {
        }
        return myDate;
    }

    private Date getHighDate(String year) {
        Date myDate = null;
        try {
            int yy = Integer.parseInt(year);
            if (yy > 1900 && yy < 2100) {
                Calendar myCal = Calendar.getInstance();
                myCal.set(yy, 11, 31, 23, 59);
                myDate = myCal.getTime();
            }
        } catch (Exception ex) {
        }
        return myDate;
    }

    private void trimAnd(StringBuffer buff) {
        if (buff.length() > 4) {
            String end = buff.substring(buff.length() - 4);
            if (end.equals("and ")) {
                buff.replace(buff.length() - 4, buff.length() + 1, "");
            }
        }
    }

    /**
     * @param subjectString the subjectString to set
     */
    public void setSubjectString(String subjectString) {
        this.subjectString = subjectString;
    }

    /**
     * @param sampleString the sampleString to set
     */
    public void setSampleString(String sampleString) {
        this.sampleString = sampleString;
    }

    public void setSampleDocumentsString(String sampleDocumentsString) {
        this.sampleDocumentsString = sampleDocumentsString;
    }

    /**
     * @param containerString the containerString to set
     */
    public void setContainerString(String containerString) {
        this.containerString = containerString;
    }

    public void setShipmentString(String shipmentString){
        this.shipmentString=shipmentString;
    }

    public boolean queryingSubject() {
        return queryingSubject;
    }

    public boolean queryingSample() {
        return queryingSample;
    }

    public boolean queryingContent() {
        return queryingContent;
    }

    public boolean queryingContainer() {
        return queryingContainer;
    }

    public boolean queryingShipment() {
        return queryingShipment;
    }

    public String getSampleDocumentsString() {
        StringBuffer xyz = new StringBuffer();
        StringBuffer froms = new StringBuffer();
        StringBuffer joins = new StringBuffer();
        StringBuffer filters = new StringBuffer();
//    xyz.append("select mySample from Sample as mySample");

        joins.append(" where ");

        if (sampleDocumentsString != null) {
            filters.append(sampleDocumentsString);
        }

        if (containerString != null) {
            froms.append(", Shipment as myShipment, Container as myContainer, ContainerContent as myContainerContent");
            joins.append(" mySampleDocuments.sample.sampleID=myContainerContent.sample.sampleID and "
                    + "myContainerContent.container.containerID=myContainer.containerID and ");
            if (containerContentString != null) {
                filters.append(containerContentString);
            }
            filters.append(containerString);
        } else if (containerContentString != null) {
            froms.append(", Shipment as myShipment, ContainerContent as myContainerContent");
            joins.append(" mySampleDocuments.sample.sampleID=myContainerContent.sample.sampleID and ");
            filters.append(containerContentString);
        }
        if (subjectString != null) {
            froms.append(", Shipment as myShipment, Subject as mySubject");
            joins.append(" mySampleDocuments.sample.subject.subjectID=mySubject.subjectID and ");
            filters.append(subjectString);
        }
        if (sampleString != null) {
            froms.append(", Shipment as myShipment, Samples as mySample");
            joins.append(" mySampleDocuments.sample.sampleID = mySample.sampleID and ");
            filters.append(sampleString);
        }
        if (shipmentString != null) {
            froms.append(", Shipment as myShipment, ShipCont as myShipCont, ContainerContent as myContainerContent");
            joins.append(" mySampleDocuments.sample.sampleID = myContainerContent.sample.sampleID and "
                    + "myContainerContent.container.containerID = myShipCont.container.containerID and "
                    + "myShipCont.shipment.shipmentID = myShipment.shipmentID and ");
            filters.append(shipmentString);
        }
        if (hm.get("cohortAdded") != null) {
            froms.append(", Cohort as myCohort ");
            joins.append(" mySubject.cohort.cohortID=myCohort.cohortID and ");
        }
        if (hm.get("ethnicityAdded") != null) {
            froms.append(", Ethnicity as myEthnicity ");
            joins.append(" mySubject.ethnicity.ethnicityID=myEthnicity.ethnicityID and ");
        }
        if (hm.get("sampleTypeAdded") != null) {
            froms.append(", SampleType as mySampleType ");
            joins.append(" mySampleDocuments.sample.sampleType.sampleTypeID=mySampleType.sampleTypeID and ");
        }
        if (hm.get("sampleProcessAdded") != null) {
            froms.append(", SampleProcess as mySampleProcess ");
            joins.append(" mySampleDocuments.sample.sampleProcess.sampleProcessID=mySampleProcess.sampleProcessID and ");
        }
        if (hm.get("freezerAdded") != null) {
            froms.append(", Freezer as myFreezer ");
            joins.append(" myContainer.freezer.freezerID=myFreezer.freezerID and ");
        }
        if (hm.get("shippedToAdded") != null) {
            froms.append(", ShippedTo as myShippedTo ");
            joins.append(" myShipment.shippedTo.shippedToID=myShippedTo.shippedToID and ");
        }
        if (hm.get("containerTypeAdded") != null) {
            froms.append(", ContainerType as myContainerType ");
            joins.append(" myContainer.containerType.containerTypeID=myContainerType.containerTypeID and ");
        }
        System.out.println("xyz: '" + xyz.toString());
        trimAnd(froms);
        xyz.append(froms);
        System.out.println("froms: '" + froms.toString());
        if (filters.length() < 1) {
            trimAnd(joins);
        }
        xyz.append(joins);
        System.out.println("joins: '" + joins.toString());
        trimAnd(filters);
        xyz.append(filters);
        System.out.println("filters: '" + filters.toString());

        System.out.println("query: " + xyz.toString());
        return xyz.toString();
    }

    public String getShipmentString() {
        StringBuffer xyz = new StringBuffer();
        StringBuffer froms = new StringBuffer();
        StringBuffer joins = new StringBuffer();
        StringBuffer filters = new StringBuffer();
//    xyz.append("select mySample from Sample as mySample");

        joins.append(" where ");

        if (shipmentString != null) {
            filters.append(shipmentString);
        }

        if (subjectString != null) {
            froms.append(", ShipCont as myShipCont, ContainerContent as myContainerContent, Sample as mySample, Subject as mySubject");
            joins.append(" myShipment.shipmentID = myShipCont.shipment.shipmentID and "
                    + "myShipCont.container.containerID = myContainer.containerID and "
                    + "mySubject.subjectID=mySample.subject.subjectID and "
                    + "mySample.sampleID=myContainerContent.sample.sampleID and "
                    + "myContainerContent.container.containerID=myContainer.containerID and ");
            if (containerContentString != null) {
                filters.append(containerContentString);
            }
            if (sampleString != null) {
                filters.append(sampleString);
            }
            if (sampleDocumentsString != null){
                filters.append(sampleDocumentsString);
            }
            if (containerString != null){
                filters.append(containerString);
            }
            filters.append(subjectString);
        } else if (sampleDocumentsString != null) {
            froms.append(", ShipCont as myShipCont, ContainerContent as myContainerContent, Sample as mySample, SampleDocuments as mySampleDocuments");
            joins.append(" myShipment.shipmentID = myShipCont.shipment.shipmentID and "
                    + "myShipCont.container.containerID = myContainer.containerID and "
                    + "mySample.sampleID=myContainerContent.sample.sampleID and "
                    + "mySample.sampleID=mySampleDocuments.sample.sampleID and "
                    + "myContainerContent.container.containerID=myContainer.containerID and ");
            if (sampleString != null) {
                filters.append(sampleString);
            }
            if (containerContentString != null) {
                filters.append(containerContentString);
            }
            if (containerString != null){
                filters.append(containerString);
            }
            filters.append(sampleDocumentsString);
        } else if (sampleString != null) {
            froms.append(", ShipCont as myShipCont, ContainerContent as myContainerContent, Sample as mySample");
            joins.append(" myShipment.shipmentID = myShipCont.shipment.shipmentID and "
                    + "myShipCont.container.containerID = myContainer.containerID and "
                    + "mySample.sampleID=myContainerContent.sample.sampleID and "
                    + "myContainerContent.container.containerID=myContainer.containerID and ");
            if (containerContentString != null) {
                filters.append(containerContentString);
            }
            if (containerString != null){
                filters.append(containerString);
            }
            filters.append(sampleString);
        } else if (containerContentString != null) {
            froms.append(", ShipCont as myShipCont, ContainerContent as myContainerContent");
            joins.append(" myShipment.shipmentID = myShipCont.shipment.shipmentID and "
                    + "myShipCont.container.containerID = myContainer.containerID and "
                    + "myContainerContent.container.containerID=myContainer.containerID and ");
            if (containerString != null){
                filters.append(containerString);
            }
            filters.append(containerContentString);
        } else if (containerString != null) {
            froms.append(", ShipCont as myShipCont, Sample as mySample, Container as myContainer, ContainerContent as myContainerContent");
            joins.append(" myShipment.shipmentID = myShipCont.shipment.shipmentID and "
                    + "myShipCont.container.containerID = myContainer.containerID and "
                    + "mySubject.subjectID=mySample.subject.subjectID and "
                    + "mySample.sampleID=myContainerContent.sample.sampleID and "
                    + "myContainerContent.container.containerID=myContainer.containerID and ");
            if (containerContentString != null) {
                filters.append(containerContentString);
            }
            if (sampleString != null) {
                filters.append(sampleString);
            }
            filters.append(containerString);
        }
        if (hm.get("cohortAdded") != null) {
            froms.append(", Cohort as myCohort ");
            joins.append(" mySubject.cohort.cohortID=myCohort.cohortID and ");
        }
        if (hm.get("ethnicityAdded") != null) {
            froms.append(", Ethnicity as myEthnicity ");
            joins.append(" mySubject.ethnicity.ethnicityID=myEthnicity.ethnicityID and ");
        }
        if (hm.get("sampleTypeAdded") != null) {
            froms.append(", SampleType as mySampleType ");
            joins.append(" mySample.sampleType.sampleTypeID=mySampleType.sampleTypeID and ");
        }
        if (hm.get("sampleProcessAdded") != null) {
            froms.append(", SampleProcess as mySampleProcess ");
            joins.append(" mySample.sampleProcess.sampleProcessID=mySampleProcess.sampleProcessID and ");
        }
        if (hm.get("freezerAdded") != null) {
            froms.append(", Freezer as myFreezer ");
            joins.append(" myContainer.freezer.freezerID=myFreezer.freezerID and ");
        }
        if (hm.get("shippedToAdded") != null) {
            froms.append(", ShippedTo as myShippedTo ");
            joins.append(" myShipment.shippedTo.shippedToID=myShippedTo.shippedToID and ");
        }
        if (hm.get("containerTypeAdded") != null) {
            froms.append(", ContainerType as myContainerType ");
            joins.append(" myContainer.containerType.containerTypeID=myContainerType.containerTypeID and ");
        }

        trimAnd(froms);
        xyz.append(froms);
        if (filters.length() < 1) {
            trimAnd(joins);
        }
        if (joins.toString().equals(" where") && filters.length() < 1) {
            joins = new StringBuffer();
            filters = new StringBuffer();
        }
        xyz.append(joins);
        trimAnd(filters);
        xyz.append(filters);

        return xyz.toString();
    }
}
