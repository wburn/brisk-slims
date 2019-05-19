<%-- 
    Document   : Browse
    Created on : Aug 3, 2009, 11:07:39 AM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%String lapasId = "Browse";%>
<%@include file="Header.jsp"%>
<%
        tmpHttpSessObj.clearInvalidField();
//test new session
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }
%>
<script type="text/javascript">
    function setFocus(){}
</script>

<html>
        <table class="navigate">
            <tr>
                <td class="left" align="left" colspan="2"><a class="largest">Browse the Database</a></td>
                </tr>
            <tr><td style="font-weight:bold"><a href="<%= response.encodeURL("./ViewSubjects.jsp")%>">
            Subjects</a> </td><td>View a list of all subjects in the database, including their
                ethnicities, genders, mothers and fathers. Tools are also
            provided to edit a subject's information and view all their samples.
            </td></tr>

            <%if(tmpHttpSessObj.isTechUser()){%>
            <tr><td style="font-weight:bold"><a href="<%= response.encodeURL("./ViewSamples.jsp")%>">
            DBSamples</a>
            <%}else{%>
            <tr><td style="font-weight:bold"><a href="<%= response.encodeURL("./ViewContainerContents.jsp")%>">
            Samples</a>
            <%}%></td><td>View a list of all samples in the database, including
            their subjects, biological material types and dates of collection. Tools are also
            provided to edit a sample's information.
            </td></tr>

            <tr><td style="font-weight:bold"><a href="<%= response.encodeURL("./ViewContainers.jsp")%>">
            Containers</a> </td><td>View a list of all containers in the database, including
            their names, types, locations, shipping status and comments. Tools are also provided to
            edit or clone containers and bulk edit all tubes/wells in a container.
            </td></tr>
<%if(tmpHttpSessObj.isTechUser()){%>
            <tr><td style="font-weight:bold"><a href="<%= response.encodeURL("./ViewContainerContents.jsp")%>">
            Contents</a> </td><td>View a list of all tubes and wells in the database, including
            their samples, locations (container name, row and column), volumes, concentrations,
            dilutions, parent wells and comments. Tools are also provided to
            edit a tube's/well's information. (This screen may take a few moments to load.)
            </td></tr>
            <%}%>

        </table>
    <br/>
    <br/>
    <br/>
    <br/>
</html>


<%@include file="Footer.jsp"%>
