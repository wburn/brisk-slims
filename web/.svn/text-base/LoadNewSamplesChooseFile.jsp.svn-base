<%-- 
    Document   : LoadNewSubjects
    Created on : Nov 20, 2009, 5:09:26 PM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">


<%String lapasId = "LoadNewSamplesChooseFile";%>
<%@include file="Header.jsp"%>


<%//test new session
        response.setHeader("Cache-Control", "no-store");
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }

// check permissions
if (!tmpHttpSessObj.isPowerUserWetUp()) {%>
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

function gotoContents(){
    window.location.assign(
    "<%=response.encodeURL("./LoadNewContentsChooseFile.jsp")%>");
}
</script>

<%
        tmpHttpSessObj.clearInvalidField();
%>

<html>
    <form method="POST" name="searchForm" ENCTYPE="multipart/form-data"
          action="<%=response.encodeURL("./LoadNewSamplesCheckFile.jsp")%>"
          onsubmit="return checkFormat();">
        <table class="navigate">
            <tr>
                <td class="left" align="left" colspan="2"><a class="largest">Create New Samples From Files</a></td>
                </tr>
                
          </table>
            <table class="instructions">
                    <tr><td>
                     This tool will create new samples in SLIMS from two files you prepare and select.
                    <br>
                    The first of the two files is called the General Samples file, this will have one line 
                    for <em>every sample ID</em>. The second file is the Specific Samples file and will have
                    one line for <em>every physical sample</em>. You will upload the files one at a time.</td></tr>
                    <tr><td>Your files must be tab delimited 'txt's and <u>every line must have a value for each of the
                    following fields</u>. (See below for what to enter if the field is unknown or blank.)
            </td></tr></table><br><br>
            <fieldset>
            <table class="navigate">
                 <tr><th colspan="2">1. General Samples File</th></tr>
                    <tr><td>Sample ID</td><td>(ex: Az1001-1a or SAGE1234)</td></tr>
                    <tr><td>Cohort</td><td>(ex: CAPPS or SAGE). For existing cohorts, see the supporting data page. If you want to use a new cohort, it will need to be created by an admin user.</td></tr>
                    <tr><td>Subject ID</td><td>Subject's ID within its cohort, ie "1001-1" (not Az1001-1)</td></tr>
                    <tr><td>Valid</td><td>Whether the sample is valid ("y" or "n"). "0" if unknown.</td></tr>
                    <tr><td>Sample Type</td><td>The type of biological material this sample is or was extracted from (ie blood, epithelial etc). For existing sample types, see the supporting data page. If you want to use a new sample type, it will need to be created by an admin user.</td></tr>
                    <tr><td>Collection Date</td><td>The date this sample (the unextracted version) was collected (format: yyyy-mm-dd). "0" if unknown.</td></tr>
                    <tr><td>Extraction Date</td><td>The date DNA was extracted from the unextracted version of this sample (format: yyyy-mm-dd). "0" if unknown.</td></tr>
                    <tr><td>Comments</td><td>"0" if none. (Must not contain any apostrophes.)</td></tr>
        </table>
<table class="spaced"><tr>
                    <td style="font-weight:bold">Select a file:
                    <input class="button" type="file" name="fileToLoad" id="fileToLoad">
                        </td>
</tr></table>
<table class="spaced"><tr><td><input class="button" type="submit" value="Next"></td>
                    <td>
                        <input class="button" type="button" value="Cancel" onclick="return gotoIndex();"></td>
</tr></table>

</fieldset>
            <br>
<fieldset>
        <table class="navigate" style="color:grey">
                 <tr><th colspan="2"><input type="button" class="button" onclick="gotoContents()" value="2. Specific Samples File"></th></tr>
                    <tr><td>Container name</td><td>The container this sample is in. New containers can be made individually through SLIMS’s interface in the browse containers screen.</td></tr>
                    <tr><td>Row</td><td>The row in the container the sample is in</td></tr>
                    <tr><td>Column</td><td>The column in the container the sample is in</td></tr>
                    <tr><td>Sample ID</td><td>(ex: Az1001-1a or SAGE1234)</td></tr>
                    <tr><td>Barcode</td><td>The barcode on a tube or plate if the sample has one, "0" if none.</td></tr>
                    <tr><td>Material Type</td><td>What type of material this content is (WGA, genomic, unextracted, etc). For existing material types, see the supporting data page. If you want to use a new material type, it will need to be created by an admin user.</td></tr>
                    <tr><td>Volume</td><td>The volume of the content in ul, -1 for unknown, 0 for empty.</td></tr>
                    <tr><td>Concentration</td><td>The concentration of the content in ng/ul, -1 for unknown.</td></tr>
                    <tr><td>Dilution</td><td>The dilution of the content (ex 1:1 or 1:100), "0" for unknown or N/A.</td></tr>
                    <tr><td>Parent Sample ID</td><td>The sample ID of the sample this sample came from (ex: if this sample was made from material taken from another tube ‘x’, then tube tube ‘x’ would be the parent sample) "-2" if N/A or "-3" if unknown.</td></tr>
                    <tr><td>Parent Container name</td><td>The container name of the sample this sample came from (ex: if this sample was made from material taken from another tube ‘x’, then tube tube ‘x’ would be the parent sample) "-2" if N/A or "-3" if unknown.</td></tr>
                    <tr><td>Parent Row</td><td>The row of the sample this sample came from (ex: if this sample was made from material taken from another tube ‘x’, then tube tube ‘x’ would be the parent sample) "-2" if N/A or "-3" if unknown.</td></tr>
                    <tr><td>Parent Column</td><td>The column of the sample this sample came from (ex: if this sample was made from material taken from another tube ‘x’, then tube tube ‘x’ would be the parent sample) "-2" if N/A or "-3" if unknown.</td></tr>
                    <tr><td>Contaminated</td><td>If the tube/well is contaminated ("y" or "n"). "0" if unknown.</td></tr>
                    <tr><td>Quantification Date</td><td>The date this content was inventoried (format: yyyy-mm-dd). "0" if unknown or N/A.</td></tr>
                    <tr><td>Amplification Date</td><td>The date this sample was amplified (format: yyyy-mm-dd). "0" if unknown or N/A.</td></tr>
                    <tr><td>Comments</td><td>"0" if none. (Must not contain any apostrophes.)</td></tr>
        </table>
<table class="spaced"><tr>
                    <td style="font-weight:bold;color:grey">Select a file:
                    <input class="button" type="file" name="contentFileToLoad" disabled>
                        </td>
</tr></table>
<table class="spaced"><tr><td><input class="button" type="submit" value="Next" disabled></td>
                    <td>
                        <input class="button" type="button" value="Cancel" onclick="return gotoIndex();" disabled></td>
</tr></table>


</fieldset>

    </form>

</html>

<%@include file="Footer.jsp"%>
