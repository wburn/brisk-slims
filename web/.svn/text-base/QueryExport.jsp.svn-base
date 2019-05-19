<%-- 
    Document   : QueryExport
    Created on : Jun 9, 2009, 4:28:21 PM
    Author     : tvanrossum
--%>

<%@page   import="java.util.*"
          import="java.io.*"
          import="icapture.com.*"
          import="icapture.hibernate.*"
          errorPage="Test_JSP_error.jsp"
          %>
<%@ page import="datasource.*" %>
<%@ page import="java.sql.*" %>

<%
        UserHttpSess tmpHttpSessObj = SessionListener.getUserHttpSessObject(session);

        if (tmpHttpSessObj == null) {
            pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
            return;
        }
        String query = request.getParameter("query");
        if (query.length()>0){
        tmpHttpSessObj.openHibSession();
        response.setContentType("application/csv");
        response.setHeader("Content-Disposition", "inline; filename=results.csv");
        try{
        Connection conn = DriverManager.getConnection(
                tmpHttpSessObj.getProperty("hibernate.connection.url"),
                tmpHttpSessObj.getProperty("hibernate.connection.username"),
                tmpHttpSessObj.getProperty("hibernate.connection.password"));
        Statement stmt = null;
        stmt = conn.createStatement();
        ResultSet rst = stmt.executeQuery(query);
        out.clearBuffer();
        ResultSetMetaData meta = rst.getMetaData();
        int col = meta.getColumnCount();
        int i = 1;
        for (i = 1; i <= col; i++) {
            String table = meta.getTableName(i);
            String column = meta.getColumnName(i);
            out.print(((i == 1) ? "" : ",") + table + "." + column);
        }
        out.print("\n");
        while (rst.next()) {
            for (i = 1; i <= col; i++) {
                out.print(((i == 1) ? "" : ",") + rst.getObject(i).toString());
            }
            out.print("\n");
            out.flush();
        }

        rst.close();
        
        tmpHttpSessObj.closeHibSession();
        } catch(SQLException sqlEx){
            System.err.println(sqlEx.getLocalizedMessage());
            System.err.println("SQL State: "+sqlEx.getSQLState());
        }
        }
        else{
            %>
            Error: query blank.
            <%
        }
%>