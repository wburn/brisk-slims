<%@page   import="java.util.*"
          import="icapture.com.*"
          import="icapture.hibernate.*"
          errorPage="Test_JSP_error.jsp"
%>
<%
  UserHttpSess tmpHttpSessObj = SessionListener.getUserHttpSessObject(session);
String filename = request.getParameter("filename");
String sampId = request.getParameter("sampId");
String sampDocId = request.getParameter("sampDocId");
if (sampId.equals("-1"))
    out.println(true);
out.println(tmpHttpSessObj.checkFilenameUniqueness(sampId, filename, sampDocId));
%>