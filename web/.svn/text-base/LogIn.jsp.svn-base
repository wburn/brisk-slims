<%String lapasId = "LogIn";%>
<%@include file="Header.jsp"%>
<script  type="text/javascript" language="javascript">
 function setFocus(){
     document.fForm.log_name.focus();
}
</script>
<script language="JavaScript">
  function openManual(){
       window.open('./SLIMS User Manual.doc','',
        'scrollbars=yes,resizable=yes,menubar=no,toolbar=no,location=no,status=no');
}
</script>
<%
  boolean tmpBool = true;
  String tmpLogName = null;
  String tmpPswd = null;
  String tmpAction = request.getParameter("logInAction");
  if (tmpAction != null && tmpAction.equals("Login")) {
    tmpLogName = request.getParameter("log_name");
    tmpPswd = request.getParameter("password");
    tmpBool = tmpHttpSessObj.logIn(tmpLogName, tmpPswd);
    if (tmpBool) {
      pageContext.forward(response.encodeURL("./Index.jsp"));
      return;
    }
  }
  else
  if ((tmpAction = request.getParameter("logOut")) != null) {
    tmpHttpSessObj.logOut();
  }
%>
<form method="post" name="fForm" onsubmit="return checkSubmitFlag()"
  action="<%=response.encodeURL("./LogIn.jsp")%>">

<table style="padding:30px">
    <tr>
                <td class="left" align="left" colspan="2"><a class="largest">Welcome to SLIMS</a> &nbsp; &nbsp;
                <input type="button" class="button" name="getManual" onclick="openManual()" value="Download Manual"/>
                </td>
                </tr>
  <tr>
    <td><br>
    <%if (tmpHttpSessObj.getCurrentUser() == null) {    %>
      You are not logged in or your session is out of time.
      <br> Log in using your username and password.
    <%} else {%>
    You are logged in as <%=tmpHttpSessObj.getUserString()%>
    <br> in another window.
    <br> Session ID:<%=session.getId()%>
    <input type="submit" name="logOut" value="Log Out"/>
    <%}%>
    <a class="error">
<%if (!tmpBool) {%>
          <br><br>Incorrect user information
          <%} else { %>
            &nbsp;
          <%}        %>
</a>
  </td></tr><tr>

      <td>
      <table>
        <tr>
          <td class="dialh">User Name:</td>
          <td>
            <input style="width: 10em" type="text" name="log_name" value="<%=(!tmpBool)?tmpLogName:""%>">
          </td>
        </tr>
        <tr>
          <td class="dialh">Password:</td>
          <td>
            <input style="width: 10em" type="password" name="password" value="<%=(!tmpBool)?tmpPswd:""%>">
          </td>
          <td>
          <%if (tmpHttpSessObj.getCurrentUser() == null) {          %>
            <input name="logInAction" value="Login" type="submit">
          <%} else { %>
            <input name="logInAction" value="Login" type="submit" disabled>
          <%} %>
          </td>
        </tr>
      </table>
    </td>
    
  </tr>

        <tr>
          <td>&nbsp;</td></tr>
  <tr><td colspan="4">Note: SLIMS is Mozilla Firefox 3 and Microsoft Internet Explorer 7 compatible. It looks best on <a href="http://www.mozilla.com/en-US/firefox/upgrade.html">Firefox.</a></td></tr>
  
</table>
<br><br>
</form>
<%@include file="Footer.jsp"%>
