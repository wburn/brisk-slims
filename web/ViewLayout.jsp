<%-- 
    Document   : ViewLayout
    Created on : Nov 18, 2009, 11:08:26 AM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@page   import="java.util.*"
          import="icapture.com.*"
          import="icapture.hibernate.*"
          errorPage="Test_JSP_error.jsp"
%>
<link type="text/css" rel="stylesheet" href="formstyle.css">
<%String lapasId = "ViewLayout";%>
<%

  UserHttpSess tmpHttpSessObj = SessionListener.getUserHttpSessObject(session);
//test new session
        if (tmpHttpSessObj.getCurrentUser() == null) {
                // release write lock on list
                tmpHttpSessObj.releaseLockCurrentShoppingList();
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }

        String containerID = request.getParameter("containerID");

        Container cont = (Container) tmpHttpSessObj.getObjectById(Container.class, containerID);

        %>
<script language="JavaScript">

</script>
<body>
<html>
<FORM>
<table class="navigate">
            <tr><td><a class="larger"><%=cont.getcontainerName()%></a>
            &nbsp;&nbsp;
<INPUT type="button" class="button" value="Print" onClick="window.print()">

            </td></tr>
            <tr><td><table class="plate" style=""><%=Plater.getPlateHtml(cont,tmpHttpSessObj)%></table></td></tr>
</table>
<INPUT type="button" class="button" value="Close Window" onClick="window.close()">
</FORM> 
</html>
</body>
