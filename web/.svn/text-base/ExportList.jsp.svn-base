<%--
    Document   : Export.jsp
    Created on : Jun 8, 2009, 4:04:02 PM
    Author     : tvanrossum
--%>

<%@page   import="java.util.*"
          import="java.io.*"
          import="icapture.com.*"
          import="icapture.hibernate.*"
          errorPage="Test_JSP_error.jsp"
          %>

<%
        UserHttpSess tmpHttpSessObj = SessionListener.getUserHttpSessObject(session);
        MetadataManager mdm = tmpHttpSessObj.getMetadataManager();

        if (tmpHttpSessObj == null) {
            pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
            return;
        }

        String table = request.getParameter("tableToExport");

        // lock shopping list
        tmpHttpSessObj.writeLockCurrentShoppingList();

        ArrayList objects = null;
        byte[] valueIndexArray = null;
        String[] keyArray = null;
        int colCount = -1;

        // is a search applied?
        boolean searched = (tmpHttpSessObj.isFilterList() ||
                                tmpHttpSessObj.isSearchList())? true:false;


        if (table.equals("containerContent")) {

            objects = (searched)? new ArrayList(tmpHttpSessObj.getContentsListSearched())
                    : new ArrayList(tmpHttpSessObj.getAllContentObjectsInList());
            valueIndexArray = mdm.getContainerContentIndexArray();
            keyArray = mdm.getContainerContentKeyArray();
            colCount = keyArray.length;
        } else if (table.equals("sample")) {
            objects = new ArrayList(tmpHttpSessObj.getAllSampleObjectsInList());
            valueIndexArray = mdm.getSampleIndexArray();
            keyArray = mdm.getSampleKeyArray();
            colCount = keyArray.length;
        }else if (table.equals("subject")) {
            objects = (searched)? new ArrayList(tmpHttpSessObj.getSubjectsListSearched())
                    : new ArrayList(tmpHttpSessObj.getAllSubjectObjectsInList());
            valueIndexArray = mdm.getSubjectIndexArray();
            keyArray = mdm.getSubjectKeyArray();
            colCount = keyArray.length;
        }else if (table.equals("container")) {
            objects = (searched)? new ArrayList(tmpHttpSessObj.getContainersListSearched())
                    : new ArrayList(tmpHttpSessObj.getAllContainerObjectsInList());
            valueIndexArray = mdm.getContainerIndexArray();
            keyArray = mdm.getContainerKeyArray();
            colCount = keyArray.length;
        }

        tmpHttpSessObj.openHibSession();
        response.setContentType("application/txt");
        response.setHeader("Content-Disposition", "attachment; filename=" +
                ((table != null) ? table : "results") + ".txt");

        out.clearBuffer();
        // write header
        for (int i = 1; i < colCount; i++) {
            out.print(mdm.getContainerContentLongName(keyArray[i])+"\t");
        }
        out.newLine();

         // decides whether to show the DB ID
        int viewStart = 1;
        if (tmpHttpSessObj.isTechUser()) {
            viewStart = 0;
        }
        // write data rows
        Iterator iter = objects.iterator();
        while (iter.hasNext()) {
            Persistent currObject = (Persistent) iter.next();
            String[] var = currObject.getValueArrayReadable(valueIndexArray, colCount);
            for (int i = viewStart; i < colCount; i++) {
                out.print((var[i] != null) ? var[i] + "\t" : "\t");
            }
            out.newLine();
        }

        out.flush();

        tmpHttpSessObj.releaseLockCurrentShoppingList();

        tmpHttpSessObj.closeHibSession();
%>

