<%String lapasId = "ViewSample";%>
<%@include file="Header.jsp"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.io.*"%>
<%@page import="icapture.genapha.GenaphaTools"%>

<%
    ServletContext sc = session.getServletContext();
    out.println(sc.getRealPath("testFiles.txt"));
    ArrayList<String> fileContent = GenaphaTools.readFile(new File(sc.getRealPath("testFiles.txt")));
    for (String s:fileContent){
        out.println(s);
    }
%>

<%@include file="Footer.jsp"%>