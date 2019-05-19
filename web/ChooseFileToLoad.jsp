<%-- 
    Document   : ChooseFileToLoad
    Created on : Oct 1, 2009, 5:27:17 PM
    Author     : tvanrossum
--%>

<%String lapasId = "ChooseFileToLoad";%>
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
function loadNote(elem){
    if(elem.value=="subjects"){
        var note = document.getElementById('subjectsNote').innerHTML;
        document.getElementById('displayNote').innerHTML = note;
    }else if(elem.value=="samples"){
        var note = document.getElementById('samplesNote').innerHTML;
        document.getElementById('displayNote').innerHTML = note;
    }else if(elem.value=="contents"){
        var note = document.getElementById('contentsNote').innerHTML;
        document.getElementById('displayNote').innerHTML = note;
    }else if(elem.value=="containers"){
        var note = document.getElementById('containersNote').innerHTML;
        document.getElementById('displayNote').innerHTML = note;
    }
}
function changeText3(){
	var note = document.getElementById('subjectsNote').innerHTML;
	//var displayNote = document.getElementById('displayNote').innerHTML;
	//var newHTML = "<span style='color:#ffffff'>" + oldHTML + "</span>";
	document.getElementById('displayNote').innerHTML = note;
}

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

    function gotoViewLists(){
        if (checkSubmitFlag()) {
            window.location.assign(
            "<%=response.encodeURL("./ViewLists.jsp")%>");
        }
    }
</script>

<%
        tmpHttpSessObj.clearInvalidField();
%>

<html>
    <form method="POST" name="searchForm" ENCTYPE="multipart/form-data"
          action="<%=response.encodeURL("./CheckFile.jsp")%>"
          onsubmit="return checkFormat();">
        <table class="navigate">
            <tr>
                <td class="left" align="left" colspan="2"><a class="largest">Create List From File</a></td>
                </tr>
                <tr>
                <th class="left" align="left" colspan="2">
                    What kind of data would you like to load?</a></th>
                </tr>
                <tr><td><select name="typeToLoad" id="typeToLoad" onchange="loadNote(this)" >
                    <option value="subjects"> Subject IDs
                    <option value="samples"> Sample IDs
                    <option value="contents"> Sample IDs with containers and coordinates
                    <option value="containers"> Container names
                </select></td>
                </tr>
          </table>
        <div id="displayNote">
            <table class="instructions">
                    <tr><td>
                     This tool will populate a list of subjects, all their samples,
                     and any containers those samples are in based on your list of
                     cohorts and subject IDs. A cohort tag can be either Az, CAPPS, SAGE, FA or NFA.
                     Subject IDs are composed of digits and dashes.</td></tr>
                    <tr><td>Your file must be a tab delimited txt
                    with one line for every subject, in the following format:</td></tr>
                    <tr><td style="padding-left:20px">[cohort tag][tab][subject ID]</td></tr>
                    <tr><td style="padding-left:20px">For example: "Az &nbsp;&nbsp;&nbsp;&nbsp; 1001-1"</td></tr>
                    </table>
        </div>
                

<table class="spaced"><tr>
                    <td style="font-weight:bold">Select a file:
                    <input class="button" type="file" name="fileToLoad" id="fileToLoad">
                        </td>
</tr></table>

<table class="spaced"><tr><td><input class="button" type="submit" value="Next"></td>
                    <td>
                        <input class="button" type="button" value="Cancel" onclick="return gotoViewLists();"></td>
</tr></table>

            
    </form>

</html>


<%@include file="Footer.jsp"%>
<!--text for changing instructions below-->
                <div id="subjectsNote" style="visibility:collapse">
                    <table class="instructions">
                    <tr><td>
                     This tool will populate a list of subjects, all their samples,
                     and any containers those samples are in based on your list of
                     cohorts and subject IDs. A cohort tag can be either Az, CAPPS, SAGE, FA or NFA.
                     Subject IDs are composed of digits and dashes.</td></tr>
                     <tr><td>Your file must be a tab delimited txt
                    with one line for every subject, in the following format:</td></tr>
                    <tr><td style="padding-left:20px">[cohort tag][tab][subjectID]</td></tr>
                    <tr><td style="padding-left:20px">For example: "Az &nbsp;&nbsp;&nbsp;&nbsp; 1001-1"</td></tr>
                    </table> </div>
                <div id="samplesNote" style="visibility:collapse">
                    <table class="instructions">
                    <tr><td>
                    This tool will create a list of all samples that match your list of sample IDs
                    , the subjects these samples came from,
                     and all containers these samples are found in. </td></tr>
                    <tr><td>Your file must be a
                            txt with one sample ID (ex: Az1001-1b) on each line</td></tr>
                    </table>
                    </div>
                <div id="contentsNote" style="visibility:collapse">
                    <table class="instructions">
                    <tr><td>This tool will create a list of specific samples (and containers)
                    based on sample IDs, the containers they're in and where they are in their containers.
                    The corresponding
                    subject list will include all the subjects these samples came from.
                   </td></tr>
                    <tr><td>Your file must be a tab delimited txt
                    with one line for every sample, in the following format:</td></tr>
                    <tr><td style="padding-left:20px">[sample ID][tab][container name][tab][row letter][tab][column number] </td></tr>
                    <tr><td style="padding-left:20px">For example: "Az1001-1&nbsp;&nbsp;&nbsp;&nbsp;Az_VANP4WGA 1:100&nbsp;&nbsp;&nbsp;&nbsp;A&nbsp;&nbsp;&nbsp;&nbsp;3"</td></tr>
                </table></div>
                <div id="containersNote" style="visibility:collapse">
                    <table class="instructions">
                        <tr><td>This tool will populate a list of containers, their samples,
                     and any subjects those samples came from based on your list of container names. </td></tr>
                    <tr><td>Your file must be a txt with one
                    container name (ex: Az_VANP4WGA 1:100) on each line</td></tr>
                    </table></div>