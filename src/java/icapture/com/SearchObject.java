/**
 * Sets up simple searches. Checks the request field for input and what to
 * search and uses those fields to set up the the query
 * (method: setUpSearch (HttpServletRequest request)).
 * Prepares strings to add to queries that return search results (get<Object>String()).
 * This file is a simplified version of FilerObject.java
 * June 3, 2009
 * @author tvanrossum
 */
package icapture.com;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Date;

public class SearchObject {

    private String sampleString = null;
    private String containerString = null;
    private String subjectString = null;
    private String listString = null;
    private String sampleDocumentsString = null;
    private String shipmentString = null;

    /**
     * prepares where clauses for searching
     * queries to append reads "select myTable from Table as myTable"
     * @param request
     */
    public void setUpSearch(HttpServletRequest request) {
        String searchFor = request.getParameter("searchFor");
        if (searchFor.equalsIgnoreCase("sample")) {
            String exact = request.getParameter("exact");
            if (new Boolean(exact)) {
                exact = "";
            } else {
                exact = "%";
            }
            String searchBy = request.getParameter("searchBy");
            // search by sample name
            if (searchBy.equalsIgnoreCase("sampleName")) {
                String keyword = request.getParameter("searchTerm");
                sampleString = "  upper(mySample.sampleName) like '" + exact + keyword.toUpperCase() + exact + "' ";

            }
        }
        if (searchFor.equalsIgnoreCase("container")) {
            String searchBy = request.getParameter("searchBy");
            String exact = request.getParameter("exact");
            if (new Boolean(exact)) {
                exact = "";
            } else {
                exact = "%";
            }
            // search by container name
            if (searchBy.equalsIgnoreCase("containerName")) {
                String keyword = request.getParameter("searchTerm");
                containerString = "  upper(myContainer.containerName) like '" + exact + keyword.toUpperCase() + exact + "' ";
                System.out.println("Container string: " + containerString);
            }
            // search by container alias
            if (searchBy.equalsIgnoreCase("containerAlias")) {
                String keyword = request.getParameter("searchTerm");
                containerString = "  upper(myContainer.containerAlias) like '" + exact + keyword.toUpperCase() + exact + "' ";
            }
            // search by container name and alias
            if (searchBy.equalsIgnoreCase("containerNameAlias")) {
                String keyword = request.getParameter("searchTerm");
                containerString = "  ( upper(myContainer.containerName) like '" + exact + keyword.toUpperCase() + exact + "'"
                        + " OR upper(myContainer.containerAlias) like '" + exact + keyword.toUpperCase() + exact + "') ";
            }
        }
        if (searchFor.equalsIgnoreCase("subject")) {
            String searchBy = request.getParameter("searchBy");
            String exact = request.getParameter("exact");
            if (new Boolean(exact)) {
                exact = "";
            } else {
                exact = "%";
            }
            if (searchBy.equalsIgnoreCase("subjectName")) {
                String keyword = request.getParameter("searchTerm");
                subjectString = "  upper(mySubject.subjectName) like '" + exact + keyword.toUpperCase() + exact + "' ";
            }
            if (searchBy.equalsIgnoreCase("subjectCohort")) {
                String keyword = request.getParameter("searchTerm");
                subjectString = "  upper(mySubject.cohort.description) like '" + exact + keyword.toUpperCase() + exact + "' ";
            }
        }
        if (searchFor.equalsIgnoreCase("shipment")) {
            String searchBy = request.getParameter("searchBy");
            String exact = request.getParameter("exact");
            if (new Boolean(exact)) {
                exact = "";
            } else {
                exact = "%";
            }
            if (searchBy.equalsIgnoreCase("shipmentName")) {
                shipmentString = "(";
                String[] keyword = request.getParameterValues("shipmentTerm");
                for (int i = 0; i < keyword.length; ++i) {
                    shipmentString += " myShipment.shipmentID = " + keyword[i].toUpperCase();
                    if (keyword.length - 1 == i) {
                        shipmentString += ")";
                        continue;
                    }
                    shipmentString += " OR";
                }
            }
        }
        if (searchFor.equalsIgnoreCase("list")) {
            String keyword = request.getParameter("searchTerm");
            listString = " where upper(myList.listName) like '%" + keyword.toUpperCase() + "%'";
        }
    }

    public String getSubjectString() {
        StringBuffer xyz = new StringBuffer();
        StringBuffer froms = new StringBuffer();
        StringBuffer joins = new StringBuffer();
        StringBuffer filters = new StringBuffer();
//    xyz.append("select mySubject from Subject as mySubject");

        joins.append(" where ");

        // will only have one search string at a time
        if (subjectString != null) {
            filters.append(subjectString);
        } else if (containerString != null) {
            froms.append(", Sample as mySample, Container as myContainer, ContainerContent as myContainerContent");
            joins.append(" mySubject.subjectID=mySample.subject.subjectID and "
                    + "mySample.sampleID=myContainerContent.sample.sampleID and "
                    + "myContainerContent.container.containerID=myContainer.containerID and ");
            filters.append(containerString);
        } else if (sampleString != null) {
            froms.append(", Sample as mySample");
            joins.append(" mySubject.subjectID=mySample.subject.subjectID and ");
            filters.append(sampleString);
        } else if (shipmentString != null) {
            froms.append(", Shipment as myShipment, ShipCont as myShipCont, Sample as mySample, Container as myContainer, ContainerContent as myContainerContent");
            joins.append(" myShipment.shipmentID=myShipCont.shipment.shipmentID and "
                    + "myShipCont.container.containerID = myContainer.containerID and "
                    + "mySubject.subjectID=mySample.subject.subjectID and "
                    + "mySample.sampleID=myContainerContent.sample.sampleID and "
                    + "myContainerContent.container.containerID=myContainer.containerID and ");
            filters.append(shipmentString);
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
        } else if (containerString != null) {
            froms.append(", Container as myContainer, ContainerContent as myContainerContent");
            joins.append(" mySample.sampleID=myContainerContent.sample.sampleID and "
                    + "myContainerContent.container.containerID=myContainer.containerID and ");
            filters.append(containerString);
        } else if (subjectString != null) {
            froms.append(", Subject as mySubject");
            joins.append(" mySample.subject.subjectID=mySubject.subjectID and ");
            filters.append(subjectString);
        } else if (shipmentString != null) {
            froms.append(", Shipment as myShipment, ShipCont as myShipCont, Container as myContainer, ContainerContent as myContainerContent");
            joins.append(" myShipment.shipmentID=myShipCont.shipment.shipmentID and "
                    + "myShipCont.container.containerID = myContainer.containerID and "
                    + "mySample.sampleID=myContainerContent.sample.sampleID and "
                    + "myContainerContent.container.containerID=myContainer.containerID and ");
            filters.append(shipmentString);
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
        // will only have one search string at a time
        if (containerString != null) {
            filters.append(containerString);
        } else if (subjectString != null) {
            froms.append(", ContainerContent as myContainerContent, Sample as mySample, Subject as mySubject");
            joins.append(" mySubject.subjectID=mySample.subject.subjectID and "
                    + "mySample.sampleID=myContainerContent.sample.sampleID and "
                    + "myContainerContent.container.containerID=myContainer.containerID and ");
            filters.append(subjectString);
        } else if (sampleString != null) {
            froms.append(", ContainerContent as myContainerContent, Sample as mySample");
            joins.append(" mySample.sampleID=myContainerContent.sample.sampleID and "
                    + "myContainerContent.container.containerID=myContainer.containerID and ");
            filters.append(sampleString);
        } else if (shipmentString != null) {
            froms.append(", Shipment as myShipment, ShipCont as myShipCont");
            joins.append(" myShipment.shipmentID=myShipCont.shipment.shipmentID and "
                    + "myShipCont.container.containerID = myContainer.containerID and ");
            filters.append(shipmentString);
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

        // will only have one search string at a time
        if (containerString != null) {
            froms.append(", Container as myContainer ");
            joins.append(" myContainerContent.container.containerID=myContainer.containerID and ");
            filters.append(containerString);
        } else if (subjectString != null) {
            froms.append(", Sample as mySample, Subject as mySubject");
            joins.append(" mySubject.subjectID=mySample.subject.subjectID and "
                    + "mySample.sampleID=myContainerContent.sample.sampleID and ");
            filters.append(subjectString);
        } else if (sampleString != null) {
            froms.append(", Sample as mySample");
            joins.append(" mySample.sampleID=myContainerContent.sample.sampleID and ");
            filters.append(sampleString);
        } else if (shipmentString != null) {
            froms.append(", Shipment as myShipment, ShipCont as myShipCont, Container as myContainer ");
            joins.append(" myShipment.shipmentID=myShipCont.shipment.shipmentID and "
                    + "myShipCont.container.containerID = myContainer.containerID and "
                    + "myContainerContent.container.containerID=myContainer.containerID and ");
            filters.append(shipmentString);
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

    private void trimAnd(StringBuffer buff) {
        if (buff.length() > 4) {
            String end = buff.substring(buff.length() - 4);
            if (end.equals("and ")) {
                buff.replace(buff.length() - 4, buff.length() + 1, "");
            }
        }
    }

    /**
     * returns a where clause for searching samples
     * query to append reads "select mySample from Sample as mySample"
     * @param
     * @return null if no search or string for search of samples starting with 'WHERE ...'
     */
    public String getListSearchString() {
        return listString;
    }

    public String getSampleDocumentsString() {
        StringBuffer xyz = new StringBuffer();
        StringBuffer froms = new StringBuffer();
        StringBuffer joins = new StringBuffer();
        StringBuffer filters = new StringBuffer();
//    xyz.append("select mySample from Sample as mySample");

        joins.append(" where ");
        // will only have one search string at a time

        if (sampleDocumentsString != null) {
            filters.append(sampleDocumentsString);
        } else if (containerString != null) {
            froms.append(", Container as myContainer, ContainerContent as myContainerContent");
            joins.append(" mySampleDocuments.sample.sampleID=myContainerContent.sample.sampleID and "
                    + "myContainerContent.container.containerID=myContainer.containerID and ");
            filters.append(containerString);
        }  else if (sampleString != null) {
            froms.append(", Sample as mySample");
            joins.append(" mySampleDocuments.sample.sampleID = mySample.sampleID and ");
            filters.append(sampleString);
        }  else if (subjectString != null) {
            froms.append(", Subject as mySubject");
            joins.append(" mySampleDocuments.sample.subject.subjectID=mySubject.subjectID and ");
            filters.append(subjectString);
        } else if (shipmentString != null) {
            froms.append(", Shipment as myShipment, ShipCont as myShipCont, Container as myContainer, ContainerContent as myContainerContent");
            joins.append(" myShipment.shipmentID=myShipCont.shipment.shipmentID and "
                    + "myShipCont.container.containerID = myContainer.containerID and "
                    + "mySampleDocuments.sample.sampleID=myContainerContent.sample.sampleID and "
                    + "myContainerContent.container.containerID=myContainer.containerID and ");
            filters.append(shipmentString);
        }

        trimAnd(froms);
        xyz.append(froms);
        if (filters.length() < 1) {
            trimAnd(joins);
        }
        xyz.append(joins);
        trimAnd(filters);
        xyz.append(filters);

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
        // will only have one search string at a time
        if (shipmentString != null) {
            filters.append(shipmentString);
        } else if (subjectString != null) {
            froms.append(", ShipCont as myShipCont, ContainerContent as myContainerContent, Sample as mySample, Subject as mySubject");
            joins.append(" myShipCont.shipment.shipmentID = myShipment.shipmentID and "
                    + "mySubject.subjectID=mySample.subject.subjectID and "
                    + "mySample.sampleID=myContainerContent.sample.sampleID and "
                    + "myContainerContent.container.containerID=myShipCont.container.containerID and ");
            filters.append(subjectString);
        } else if (sampleString != null) {
            froms.append(", ShipCont as myShipCont, ContainerContent as myContainerContent, Sample as mySample");
            joins.append(" myShipCont.shipment.shipmentID = myShipment.shipmentID and "
                    + "mySample.sampleID=myContainerContent.sample.sampleID and "
                    + "myContainerContent.container.containerID=myShipCont.container.containerID and ");
            filters.append(sampleString);
        } else if (containerString != null) {
            froms.append(", ShipCont as myShipCont, ContainerContent as myContainerContent");
            joins.append(" myShipCont.shipment.shipmentID = myShipment.shipmentID and "
                    + "myContainerContent.container.containerID=myShipCont.container.containerID and ");
            filters.append(containerString);
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
