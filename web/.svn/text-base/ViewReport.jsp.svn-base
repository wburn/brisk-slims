<%--
    Document   : viewReport
    Created on : May 27, 2009, 4:35:56 PM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<!--from Header.jsp-->
<%@page   import="java.util.*"
          import="icapture.com.*"
          import="icapture.hibernate.*"
          errorPage="Test_JSP_error.jsp"
%><%
        UserHttpSess tmpHttpSessObj = SessionListener.getUserHttpSessObject(session);
        if (tmpHttpSessObj == null) {
            pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
            return;
        }
%>

<%--<%@ page import="datasource.*" %>--%>
<%@ page import="net.sf.jasperreports.engine.*" %>
<%@ page import="net.sf.jasperreports.engine.util.*" %>
<%@ page import="net.sf.jasperreports.engine.export.*" %>
<%@ page import="net.sf.jasperreports.j2ee.servlets.*" %>
<%@ page import="net.sf.jasperreports.j2ee.servlets.*" %>

<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.sql.*" %>

<%@ page language="java" 
         import="net.sf.jasperreports.engine.*,
         net.sf.jasperreports.engine.export.*" %>
<%@ page import="java.sql.*,java.io.*" %>
<%
// get user data
System.out.println("-------In view report------");

HashMap properties = new HashMap();
String filename = request.getParameter("reportName");
String reportType = request.getParameter("reportFormat");

// --- get the subreports directory to pass in ---
String appPath = application.getRealPath("/");
System.out.println("application.getRealPath(/): " + appPath);
File reportsDir = new File(appPath + "WEB-INF/classes/reports");

if (!reportsDir.exists()) {
    System.out.println("NF reportsDir:"+reportsDir.toString());
    throw new FileNotFoundException(String.valueOf(reportsDir));
}
properties.put("SUBREPORT_DIR", reportsDir.toString()+"/");
// -------------------------------------------------

String shippingRecordLimit = request.getParameter("ShippingRecordLimit");

if(filename!= null && (filename.equals("recipeName") || filename.equals("sampleLocationSheetName"))){

    String containerName = request.getParameter("containerName");
    HashMap cont = new HashMap(1);
    cont.put("containerName", "'"+containerName+"'");
    Object contObj = tmpHttpSessObj.getObjectByUniqueKey(Container.class, cont);
    if(contObj == null){
        %>
        <html>
    <head>
        <title>Report Creation Error:</title>
        <link rel="stylesheet" type="text/css" href="../stylesheet.css" title="Style">
    </head>

    <body bgcolor="white">
        <span class="bold">Error: no unique container found for container name '<%=containerName%>'.
        Try checking spelling and spacing, the name must be exact.</span>
    </body>
</html>
        <%
        return;
    }
    // remove "name" from the report name
    filename = filename.substring(0, filename.length()-4);
    // set up sending the containerID
    properties.put("containerID", ((Container)contObj).getId());
}

String containerIDM = request.getParameter("customPlatingSheet");
if(containerIDM!=null && !containerIDM.equals("")){
    filename="recipe";
    // set up sending the containerID
    properties.put("containerID", new Long(containerIDM));
}

String containerIDS = request.getParameter("sampleLocationSheet");
if(containerIDS!=null && !containerIDS.equals("")){
    filename="sampleLocationSheet";
    // set up sending the containerID
    properties.put("containerID", new Long(containerIDS));
}

if (filename.equals("ShippingRecord") && shippingRecordLimit != null && shippingRecordLimit.equals("date")){

    filename = "ShippingRecord_InputDate";

        String startYear = request.getParameter("startYear");
        String startMonth = request.getParameter("startMonth");
        String startDay = request.getParameter("startDay");

        String endYear = request.getParameter("endYear");
        String endMonth = request.getParameter("endMonth");
        String endDay = request.getParameter("endDay");
        
        if(startMonth == null || startMonth.equals("")){startMonth = "1";}
        if(startDay == null || startDay.equals("")){ startDay = "1";}
        if(endMonth == null || endMonth.equals("")){ endMonth = "12";}
        if(endDay == null || endDay.equals("")){ endDay = "31";}

        java.util.Date startDate = tmpHttpSessObj.getDate(startYear, startMonth, startDay,
                "startYear", "startMonth", "startDay");
        java.util.Date endDate = tmpHttpSessObj.getDate(endYear, endMonth, endDay,
                "endYear", "endMonth", "endDay");
        // set default initial time to 1970-01-01
        if(startDate == null){
            startDate = new java.util.Date(0);
        }
        if(endDate == null){
        endDate = new java.util.Date();
        }

        // gives sql date the value of startDate
        java.sql.Date start = new java.sql.Date(startDate.getTime());
        java.sql.Date end = new java.sql.Date(endDate.getTime());

        properties.put("startDate", start);
        properties.put("endDate", end);
}

        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        Connection conn = DriverManager.getConnection(
                tmpHttpSessObj.getProperty("hibernate.connection.url"),
                tmpHttpSessObj.getProperty("hibernate.connection.username"),
                tmpHttpSessObj.getProperty("hibernate.connection.password"));
        String path = application.getRealPath("/");
        System.out.println("application.getRealPath(/): " + path);
        File reportFile = new File(path + "WEB-INF/classes/reports/" + filename + ".jasper");
        if (!reportFile.exists()) {
            throw new JRRuntimeException("File " + filename + ".jasper not found. The report design must be compiled first.");
        }
        JasperReport jasperReport =null;
        JasperPrint jasperPrint = null;

        try{
         jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
         jasperPrint = JasperFillManager.fillReport(jasperReport, properties, conn);
        } catch (Exception e){
            System.out.println("REPORT ERROR: "+e.getLocalizedMessage());
        }
        System.out.println("Report Created");

        OutputStream outputStream = response.getOutputStream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        JRExporter exporter = null;

        if (reportType.equalsIgnoreCase("pdf")) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=\"" + filename + ".pdf\"");
            exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
        } else if (reportType.equalsIgnoreCase("rtf")) {
            response.setContentType("application/rtf");
            response.setHeader("Content-Disposition", "inline; filename=\"" + filename + ".rtf\"");

            exporter = new JRRtfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
        } else if (reportType.equalsIgnoreCase("html")) {
            response.setHeader("Content-Disposition", "inline; filename=\"" + filename + ".html\"");

            exporter = new JRHtmlExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
            exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "image?image=");
        } else if (reportType.equalsIgnoreCase("xls")) {
            JExcelApiExporter exporterXLS = new JExcelApiExporter();

            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputStream);

            response.setHeader("Content-Disposition", "inline;filename=" + filename + ".xls");
            response.setContentType("application/vnd.ms-excel");

            exporterXLS.exportReport();

        } else if (reportType.equalsIgnoreCase("csv")) {
            response.setContentType("application/csv");
            response.setHeader("Content-Disposition", "inline; filename=\"" + filename + ".csv\"");

            exporter = new JRCsvExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
        }

        try {
            exporter.exportReport();
            System.out.println("Report exported.");
        } catch (JRException e) {
            System.out.println("error in 1st catch: " + e.getLocalizedMessage());
            throw new ServletException(e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ex) {
                    System.out.println("error in 2nd catch: " + ex.getLocalizedMessage());

                }
            }
        }
%>



<html>
    <head>
        <title>JasperReports - Web Application Sample</title>
        <link rel="stylesheet" type="text/css" href="../stylesheet.css" title="Style">
    </head>

    <body bgcolor="white">

        <span class="bold">The compiled report design was successfully filled with data.</span>

    </body>
</html>

