<%@ page isErrorPage="true"%>
<%@page import="icapture.com.*"%>
<%
UserHttpSess mySess = SessionListener.getUserHttpSessObject(session);
if (mySess != null) {
mySess.closeHibSession();
mySess.logOut();
}
String httpSessId = (mySess==null)?"null":mySess.getHttpSessId();
String msg = (exception==null)?"Unspecified error":exception.getMessage();
Util.writeLog("---An error detected. Error message is:",
                     msg, httpSessId);
if (exception!=null){
 java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
 java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
 exception.printStackTrace(pw);
 Util.writeLog(cw.toString(),"","");
}
%>
<!DOCTYPE html>
<html>
<head>
<title>Sample Laboratory Information Management System</title>
<link rel="shortcut icon" href="./images/favicon.ico">
<link type="text/css" rel="stylesheet" href="formstyle.css">
<script  src="./utils.js" TYPE="text/javascript"></script>
<script  type="text/javascript" language="javascript">
  function confirmLogout(){
    return (confirm("Are you sure?"));
  }
</script>
</head>
<body>
            <div id="header" style="background: url(./images/basicBRISKextend.png) repeat-x right transparent;
    height: 120px;">
            <div id="header2" style="background: url(./images/basicBRISKSLIMSbanner.png) no-repeat left transparent;
    height: 120px;"></div>
            </div>
<!---->
      
  <table cellPadding="4" cellSpacing="0">
    <tr class="nav">
      <td><a style="color:black" href="<%= response.encodeURL("./LogIn.jsp")+"?logOut=yes"%>">Login</a></td>
      <td width="100%" align="right">&nbsp;</td>
    </tr>
  </table>
<br>
<div style=" margin: 4px 4px 4px 4px">
  <h3>An error detected by system. Error message is:<%=msg%></h3>
  You have been automatically logged out. Please log in again.
</div>
<br>
<br>
  <table cellPadding="4" cellSpacing="0">
    <tr class="nav">
      <td><a style="color:black" href="<%= response.encodeURL("./LogIn.jsp")+"?logOut=yes"%>">Login</a></td>
      <td width="100%" align="right">&nbsp;</td>
    </tr>
  </table>
<br>

<br></body>
</html>
