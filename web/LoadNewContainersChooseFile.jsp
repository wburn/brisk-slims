<%-- 
    Document   : LoadNewContainers
    Created on : Nov 20, 2009, 5:09:26 PM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">


<%String lapasId = "LoadNewContainersChooseFile";%>
<%@include file="Header.jsp"%>

<%//test new session
        response.setHeader("Cache-Control", "no-store");
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }

// check permissions
if (!tmpHttpSessObj.isAdvancedUserWetUp()) {%>
<script  type="text/javascript" language="javascript">
    window.onload = disableAllButBack;
</script>
<br><a class="largest">Sorry, you do not have permission to use this page. </a><input type='button' onclick='history.go(-1);' name="badPermGoBack" value="Back" /><br><br>
Your privilege level is: <%=tmpHttpSessObj.getCurrentUser().getRightsReadable()%>.
To see the privileges associated with each level <input type="button" class="button" name="getPermissionDoc" onclick="openPermissionsDoc()" value="Click here"/> <br><br>
<br><br>
<% }%>

<%
        tmpHttpSessObj.clearInvalidField();
//test new session
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }

%>
<script language="JavaScript">

function checkFormat(){
    var file = document.getElementById("fileToLoad");
    if(file.value.substring(file.value.length-4) == '.txt') {
        return true;
    }
    else { //Wrong filetype!
        alert("Error: Import file must be a .txt file.");
        var file = file.value="";
        return false;
    }
}

function gotoIndex(){
        if (checkSubmitFlag()) {
            window.location.assign(
            "<%=response.encodeURL("./Index.jsp")%>");
        }
}
</script>

<%
        tmpHttpSessObj.clearInvalidField();
%>

<html>
    <form method="POST" name="searchForm" ENCTYPE="multipart/form-data"
          action="<%=response.encodeURL("./LoadNewContainersCheckFile.jsp")%>"
          onsubmit="return checkFormat();">
        <table class="navigate">
            <tr>
                <td class="left" align="left" colspan="2"><a class="largest">Create New Containers From File</a></td>
                </tr>
                
          </table>
        <div id="displayNote">
            <table class="instructions">
                    <tr><td>
                     This tool will create new containers in SLIMS from a file you prepare and select.</td></tr>
                    <tr><td>Your file must be a tab delimited txt
                    with one line for every container. <u>Every line must have a value for each of the
                    following fields</u>, see below for what to enter if the field is unknown or blank.</td></tr>
                   </table>
            <table class="navigate">
                <tr><td>Container Name</td><td>The systematic name of this container</td></tr>
                <tr><td>Container Alias</td><td>Non systematic name for a container. What is actually written on the container if it differs from the systematic name, or what the container was once known as. "0" if none.</td></tr>
                <tr><td>Initials of Plate Maker</td><td>The initials of the plate maker(s), or "0" if unknown.</td></tr>
                <tr><td>Date on Plate</td><td>The date written on the container (format: yyyy-mm-dd), or "0" if none.</td></tr>
                <tr><td>Container Type</td><td>For existing container types, see the supporting data page. If you want to use a new container type, it will need to be created by an admin user.</td></tr>
                <tr><td>Stock</td><td>Whether the wet lab considers this container only to have stock contents (like a box of extracted DNA tubes or a stock (1:1 dilution) WGA plate) ("y" or "n").</td></tr>
                <tr><td>Valid</td><td>Whether this container is valid or not ("y" or "n").</td></tr>
                <tr><td>Freezer Name</td><td>For existing freezers, see the supporting data page. If you want to use a new freezer, it will need to be created by an admin user. "-1" if unknown.</td></tr>
                <tr><td>Shelf</td><td>The shelf the container is on in the freezer, "-1" if unknown.</td></tr>
                <tr><td>Location Details</td><td>The specifics of where the container is on the shelf in the freezer, "-1" if unknown.</td></tr>
                <tr><td>Discarded</td><td>Whether this container has been discarded or not ("y" or "n").</td></tr>
                <tr><td>Shipped Out</td><td>Whether this container has been shipped-out, or returned. "y" for shipped out, "r" for returned, "n" for N/A.</td></tr>
                <tr><td>Shipment Name</td><td>The name of the shipment. "0" if unknown or N/A.</td></tr>
                <tr><td>Ship Date</td><td>When this container was shipped-out/returned (format: yyyy-mm-dd). "0" if unknown or N/A.</td></tr>
                <tr><td>Shipped To</td><td>Where this container was shipped to, or "0" if N/A. For existing shipped to locations, see the supporting data page. If you want to use a new shipping location, it will need to be created by an admin user.</td></tr>
                <tr><td>Comments</td><td>"0" if none. (must not contain any apostrophes.)</td></tr>

        </table>
        </div>


<table class="spaced"><tr>
                    <td style="font-weight:bold">Select a file:
                    <input class="button" type="file" name="fileToLoad" id="fileToLoad">
                        </td>
</tr></table>

<table class="spaced"><tr><td><input class="button" type="submit" value="Next"></td>
                    <td>
                        <input class="button" type="button" value="Cancel" onclick="return gotoIndex();"></td>
</tr></table>


    </form>

</html>


<%@include file="Footer.jsp"%>
