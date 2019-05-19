<%-- 
    Document   : CAPPS_Search
    Created on : May 14, 2010, 11:37:46 AM
    Author     : ATan1
--%>
<%String lapasId = "CAPPS_Search";%>
<%@include file="Header.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%
        tmpHttpSessObj.clearInvalidField();
//test new session
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }
        if (request.getParameter("viewResults") != null) {
                tmpHttpSessObj.cancelFilter();
                tmpHttpSessObj.cancelSearch();

        if ((request.getParameter("toView")).equalsIgnoreCase("subject")) {
        tmpHttpSessObj.setFilter(request);
        pageContext.forward(response.encodeURL("./CAPPS_Results.jsp"));
%>
<input type="hidden" name="searchFor" value=""/>
<%
                return;
            }
        }
%>
<html>
    <form method="POST" name="searchForm"
          action="<%=response.encodeURL("./CAPPS_Search.jsp")%>">
        <table class="navigate">
            <tr>
                <td class="left" align="left" colspan="2"><a class="largest">CAPPS 15 Year Follow-Up Sample Collection</a></td>
                </tr>
            <tr>
                <td><br>
                    Family ID Number&nbsp;
<%--                    <select name="searchFor">
                        <option value="subject">Subjects</option>
                    </select>&nbsp;&nbsp; by &nbsp;&nbsp;
                    <select name="searchBy" style="width:120px">
                        <option value="subjectName">ID</option>
                    </select>&nbsp;&nbsp;--%>
<input type="hidden" name="toView" value="subject">
<input type="hidden" name="subjCohort" value="CAPPS">
                    <input type="text" name="subjName">
                    &nbsp;&nbsp;
                    <input type="submit" name="viewResults" value="Go"/>
                </td>
            </tr>
        </table>
    </form>
    <br/>
    <br/>
    <br/>
    <br/>
</html>
<%@include file="Footer.jsp"%>