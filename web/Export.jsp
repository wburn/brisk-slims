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

        if (tmpHttpSessObj == null) {
            pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
            return;
        }
        String table = request.getParameter("tableToExport");
        String subjectID = request.getParameter("subjectID");
        subjectID =(subjectID==null||subjectID.equals("null"))? null: subjectID;
        String containerID = request.getParameter("containerID");
        containerID =(containerID==null || containerID.equals("null"))? null: containerID;
        String query = request.getParameter("query");
        String sampId = request.getParameter("sampId");
        sampId =(sampId==null || sampId.equals("null"))? null: sampId;

        tmpHttpSessObj.openHibSession();
        DataExport writer = new DataExport(tmpHttpSessObj);
        response.setContentType("application/txt");
//response.setHeader("Head", "aaaa");
//response.addHeader("Content-Disposition", "attachment; filename=" + table + ".txt");
        response.setHeader("Content-Disposition", "attachment; filename=" +
                ((table!=null)?table:"results") + ".txt");
        String line = null;
        if (query != null && query.length() > 0) {
            line = writer.getQueryResultsHeader(query);
        } else {
            line = writer.getHeader(table, subjectID,containerID,sampId);
        }
        if (line != null) {
            out.clearBuffer();
            out.println(line);
}
            for (Iterator iter = writer.iterator(); iter.hasNext();) {
                out.println((String) iter.next());
            }
            out.flush();
        //}

    tmpHttpSessObj.closeHibSession ();
%>

