<%-- 
    Document   : EditContainerLocationsChooseFile
    Created on : Jun 28, 2010, 10:12:39 AM
    Author     : ATan1
--%>
<%String lapasId = "EditContainerLocationsChooseFile";%>
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
          action="<%=response.encodeURL("./EditContainerLocationsCheckFile.jsp")%>"
          onsubmit="return checkFormat();">
        <table class="navigate">
            <tr>
                <td class="left" align="left" colspan="2"><a class="largest">Edit Container Locations From a File</a></td>
                </tr>

          </table>
        <div id="displayNote">
            <table class="instructions">
                    <tr><td>
                     This tool will change the location, shelf, and freezer fields from a file of your choosing.</td></tr>
                    <tr><td>Your file must be a tab delimited txt
                    with one line for every container. <u>Every line must have a value for each of the
                    following fields</u>, see below for what to enter if the field is unknown or blank.</td></tr>
                   </table>
            <table class="navigate">
                <tr><td>Container Name</td><td>The systematic name of this container</td></tr>
                <tr><td>Freezer Name</td><td>For existing freezers, see the supporting data page. If you want to use a new freezer, it will need to be created by an admin user. "-1" if unchanged.</td></tr>
                <tr><td>Shelf</td><td>The shelf the container is on in the freezer, "-1" if unchanged.</td></tr>
                <tr><td>Location Details</td><td>The specifics of where the container is on the shelf in the freezer, "-1" if unchanged.</td></tr>

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
