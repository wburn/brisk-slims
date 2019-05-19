<%--
    Document   : Browse
    Created on : Aug 3, 2009, 11:07:39 AM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%String lapasId = "Index";%>
<%@include file="Header.jsp"%>
<%
        tmpHttpSessObj.clearInvalidField();
//test new session
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }
%>
<script type="text/javascript" language="JavaScript">
  function openManual(){
       window.open('./SLIMS User Manual.doc','',
        'scrollbars=yes,resizable=yes,menubar=no,toolbar=no,location=no,status=no');
}
    function setFocus(){}
</script>

<html>
        <table class="navigate">


            
            
            <tr>
                <td class="left" align="left" colspan="2"><a class="largest">Welcome to SLIMS</a> &nbsp; &nbsp;
                <input type="button" class="button" name="getManual" onclick="openManual()" value="Download Manual"/>
                </td>
                </tr>
                
                <tr><td style="font-weight:bold">
                <a href="<%= response.encodeURL("./Search.jsp")%>" >
                <!--a style="color:grey"-->
            Search</a> </td>
            <td>Limit the viewable data according to selected criteria. Advanced,
            simple and query searches are available.
            </td></tr>

            <tr><td style=" font-weight:bold"><a href="<%= response.encodeURL("./Browse.jsp")%>">
            Browse/Results</a> </td><td>View information stored in the database by browsing
            or viewing search results. Visible fields
            can be customised and data can be limited using searches.
            </td></tr>

            <tr><td style="font-weight:bold"><a href="<%= response.encodeURL("./ViewLists.jsp")%>">
            Lists</a> </td><td>View, load and edit 'shopping cart' lists that have been made by yourself and others.
            Each list contains corresponding subjects, samples and containers. 
            </td></tr>

            <tr><td style="font-weight:bold"><a href="<%= response.encodeURL("./SelectReport.jsp")%>">
            Reports</a> </td><td>Retrieve commonly required pre-formated reports,
            prepared dynamically for you.
            </td></tr>
            <tr><td>&nbsp;</td></tr>
            <tr><td style="font-weight:bold;text-decoration:underline">
            Essential Vocabulary:</td><td>
            <a style="font-weight:bold">Subjects</a> are study participants.<br><br>
            <a style="font-weight:bold">Samples</a><em></em> are wells (in plates) and tubes.<br><br>
            <a style="font-weight:bold">Containers</a><em></em> are plates and boxes of tubes.<br><br>
                For more, see the user manual's vocabulary section.

            </td></tr>

        </table>
    <br/>
    <br/>
    <br/>
    <br/>
</html>


<%@include file="Footer.jsp"%>
