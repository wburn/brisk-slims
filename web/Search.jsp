<%--
    Document   : Browse
    Created on : Aug 3, 2009, 11:07:39 AM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%String lapasId = "Search";%>
<%@include file="Header.jsp"%>
<%
        tmpHttpSessObj.clearInvalidField();
//test new session
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }
%>
<script language="JavaScript">

</script>

<html>
        <table class="navigate">
            <tr>
                <td class="left" align="left" colspan="2"><a class="largest">Search the Database</a></td>
                </tr>
                
            <tr><td style="font-weight:bold">
                <a href="<%= response.encodeURL("./SimpleSearch.jsp")%>" >
                <!--a style="color:grey"-->
            Simple Search</a> </td>
            <td>Quick search limited to the names or IDs of subjects
            (study participants), samples (wells in plates and tubes)
            and containers (plates and boxes of tubes).
            </td></tr>

            <tr><td style="width:130px; font-weight:bold"><a href="<%= response.encodeURL("./DefineFilter.jsp")%>">
            Power Search</a> </td><td> Advanced search using all fields from subjects (study participants),
            samples (wells in plates and tubes) and containers (plates and boxes of tubes).
            </td></tr>
            
            <tr><td style="font-weight:bold"><a href="<%= response.encodeURL("./QuerySearch.jsp")%>">
            SQL Query Search</a> </td><td>For advanced users with knowledge of SQL <em>only</em>.
            Allows an SQL query search to be performed on the database.
            Results are returned as a text file in tab-separated format.
            </td></tr>

        </table>
    <br/>
    <br/>
    <br/>
    <br/>
</html>


<%@include file="Footer.jsp"%>
