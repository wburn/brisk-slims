<%-- 
    Document   : LoadNewSubjects
    Created on : Nov 20, 2009, 5:09:26 PM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">


<%String lapasId = "LoadNewSubjectsChooseFile";%>
<%@include file="Header.jsp"%>


<%//test new session
        response.setHeader("Cache-Control", "no-store");
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }

// check permissions
if (!tmpHttpSessObj.isPowerUserDryUp()) {%>
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
          action="<%=response.encodeURL("./LoadNewSubjectsCheckFile.jsp")%>"
          onsubmit="return checkFormat();">
        <table class="navigate">
            <tr>
                <td class="left" align="left" colspan="2"><a class="largest">Create New Subjects From File</a></td>
                </tr>
                
          </table>
        <div id="displayNote">
            <table class="instructions">
                    <tr><td>
                     This tool will create new subjects in SLIMS from a file you prepare and select.</td></tr>
                    <tr><td>Your file must be a tab delimited txt
                    with one line for every subject. <u>Every line must have a value for each of the
                    following fields</u>, see below for what to enter if the field is unknown or blank.</td></tr>
                   </table>
            <table class="navigate">
                    <tr><td>Cohort</td><td>For existing cohorts, see the supporting data page. If you want to use a new cohort, it will need to be created by an admin user.</td></tr>
                    <tr><td>Subject ID</td><td>Subject's ID within its cohort, ie "1001-1" (not Az1001-1)</td></tr>
                    <tr><td>Family ID</td><td>The subject's family ID within its cohort, ie "1001" or "0" if N/A</td></tr>
                    <tr><td>Gender</td><td>"M" or "F", or "0" if unknown</td></tr>
                    <tr><td>Consent</td><td>Whether we have consent from this subject or not ("y" or "n"). "0" if unknown.</td></tr>
                    <tr><td>Mother ID</td><td>Mother's ID within its cohort, ie "1001-4" or "0" if N/A</td></tr>
                    <tr><td>Father ID</td><td>Mother's ID within its cohort, ie "1001-5" or "0" if N/A</td></tr>
                    <tr><td>Ethnicity</td><td>For existing ethnicities, see the supporting data page. If you want to use a new ethnicity, it will need to be created by an admin user. </td></tr>
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
