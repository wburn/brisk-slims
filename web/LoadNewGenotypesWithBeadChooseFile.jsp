<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%String lapasId = "LoadNewSubjectsWithBeadChooseFile";%>
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
</script>

<%
            tmpHttpSessObj.clearInvalidField();
%>

<html>
    <form method="POST" name="searchForm" ENCTYPE="multipart/form-data"
          action="<%=response.encodeURL("./LoadNewGenotypesWithBeadCheckFile.jsp")%>"
          onsubmit="return checkFormat();">
        <table class="navigate">
            <tr>
                <td class="left" align="left" colspan="2"><a class="largest">Populate Genotypes with BeadStudio Generated CSV Files</a></td>
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
                <tr><th colspan="2">1. Genotype Information File (BeadStudio generated)</th></tr>
                <tr><th colspan="2">This filename of this file may contain the words 'FinalReport' if it does not then look for the file that contains
                    the follow information in the corresponding columns.</th></tr>
                <tr><td>RS Number</td><td>The RS number of the SNP that's associated with this genotype data. This should be in the <b>1st (A) column.</b></td></tr>
                <tr><td>Sample Name</td><td>The name of the sample that was genotyped. This should be in the <b>2nd (B) column.</b></td></tr>
                <tr><td>Allele Forward #1</td><td>The forward allele. This should be in the <b>4th (D) column.</b></td></tr>
                <tr><td>Allele Forward #2</td><td>The other forward allele. This should be in the <b>5th (E) column.</b></td></tr>
            </table>
            <table class="spaced" style=""><tr>
                    <td style="font-weight:bold">Select a file:
                        <input class="button" type="file" name="genotypeToLoad" id="genotypeToLoad">
                    </td>
                </tr>
                <tr><td>Genotyping Run Name<select name="genotypingRun" id="genotypingRun">
                        <%=tmpHttpSessObj.getObjectPrompterUniqueField(GenotypingRun.class, null, "description", false)%>
                        </select>
                    </td>
                </tr>
                </table>
            <table class="spaced"><tr><td><input class="button" type="submit" value="Next"></td>
                    <td>
                        <input class="button" type="button" value="Cancel" onclick="return gotoIndex();"></td>
                </tr></table>


        </fieldset>

    </form>

</html>

<%@include file="Footer.jsp"%>
