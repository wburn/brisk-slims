<%--
    Document   : Upload
    Created on : Dec 3, 2009, 18:07:39
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
<script language="JavaScript">

</script>

<html>
    <table class="navigate">


        <tr>
            <td class="left" align="left" colspan="2"><a class="largest">Uploading</a></td>
        </tr>

        <tr><td style="font-weight:bold;"><a class="larger">Add</a></td></tr>
        <tr><td style="font-weight:bold">
                <a href="<%= response.encodeURL("./LoadNewSubjectsChooseFile.jsp")%>" >
                    Load Subjects From a File</a> </td>
            <td>Create new subjects in the database from a formatted text file.
            </td></tr>

        <tr><td style="font-weight:bold">
                <a href="<%= response.encodeURL("./LoadNewContainersChooseFile.jsp")%>" >
                    Load Containers From a File</a> </td>
            <td>Create new containers in the database from a formatted text file.
            </td></tr>

        <tr><td style="font-weight:bold">
                <a href="<%= response.encodeURL("./LoadNewSamplesChooseFile.jsp")%>" >
                    Load Samples From a File</a> </td>
            <td>Create new samples in the database from two formatted text files.
            </td></tr>

        <tr><td style="font-weight:bold">
                <a href="<%= response.encodeURL("./LoadNewSNPWithBeadChooseFile.jsp")%>" >
                    Populate Database with BeadStudio Generated CSV Files</a> </td>
            <td>Populates the tables for SNPs, Genes, Samples, Containers, Container Contents, Subjects, and a few essential tables
            </td></tr>

        <tr><td style="font-weight:bold">
                <a href="<%= response.encodeURL("./LoadNewSNPWithBeadChooseFile.jsp")%>" >
                    Populate Genotypes with BeadStudio Generated CSV Files</a> </td>
            <td>Populates the table responsible for Genotype data.
            </td></tr>

        <tr><td style="font-weight:bold;"><a class="larger">Modify</a></td></tr>
        <tr><td style="font-weight:bold">
                <a href="<%= response.encodeURL("./EditContainerLocationsChooseFile.jsp")%>" >
                    Edit Container Locations From a File</a> </td>
            <td>Modify the locations of containers already in the database from a formatted text file.
            </td></tr>
    </table>
    <br/>
    <br/>
    <br/>
    <br/>
</html>


<%@include file="Footer.jsp"%>
