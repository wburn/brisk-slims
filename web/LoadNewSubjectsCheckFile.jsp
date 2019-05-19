<%-- 
    Document   : LoadNewSubjectsCheckFile
    Created on : Nov 20, 2009, 5:47:25 PM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">


<%String lapasId = "LoadNewSubjectsCheckFile";%>

<%@include file="Header.jsp"%>
<%@page import="icapture.com.FileUploading"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.FileItemIterator"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="org.apache.commons.fileio.*"%>


<script language="JavaScript">
    function goBack(){
        alert("in go back");
        pageContext.forward(response.encodeURL("./LoadNewSubjectsChooseFile.jsp"));
        return;
    }
</script>
<%

        tmpHttpSessObj.clearInvalidField();
//test new session
        if (tmpHttpSessObj.getCurrentUser() == null) {
            // release write lock on list
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }
        String tmpString = null;
        int messageNum = 0;

        
        if((tmpString = request.getParameter("cancel"))!=null){
            tmpHttpSessObj.setCurrentUploader(null);
            pageContext.forward(response.encodeURL("./Index.jsp"));
            return;
        }

        // user has approved results of SLIMS parsing file, so add the subjects
        if((tmpString = request.getParameter("create"))!=null){

            FileUploading up = tmpHttpSessObj.getCurrentUploader();

            // add the subjects just parsed
            if (messageNum == 0) {
                messageNum = up.addSubjects(tmpHttpSessObj);
            }
            if(messageNum==0){
                // reset uploader
                tmpHttpSessObj.setCurrentUploader(null);
                pageContext.forward(response.encodeURL("./ViewSubjects.jsp"));
                return;
            }
            else{
                System.out.println("Error in list creation.");

%>
<form method="POST" name="searchForm"
          action="<%=response.encodeURL("./LoadNewSubjectsCheckFile.jsp")%>"
          onsubmit="return checkSubmitFlag()">
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    
    <br><br>
</form>
<%}} else  {

        // Check that we have a file upload request
        boolean isMultipart = FileUpload.isMultipartContent(request);

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload();

        ArrayList<String> alreadyExists= null;
        ArrayList<String> dupSubjects= null;
        ArrayList<String> missingValues= null;
        ArrayList<String> badCohort= null;
        ArrayList<String> badEthnicity= null;
        Integer numGoodSubj = -1;

        FileItemIterator iter = upload.getItemIterator(request);
        try {
            FileUploading up = new FileUploading();
            tmpHttpSessObj.setCurrentUploader(up);
            //{alreadyExists,dupSubjects,missingValues,badCohort,badEthnicity,goodSubjects.size()};
            Object[] arr = up.checkUploadSubjectFile(iter);
            alreadyExists = (ArrayList) arr[0];
            dupSubjects = (ArrayList) arr[1];
            missingValues = (ArrayList) arr[2];
            badCohort = (ArrayList) arr[3];
            badEthnicity = (ArrayList) arr[4];
            numGoodSubj = (Integer) arr[5];
        } catch (Exception e) {
            System.out.println("Error in file checking: " + e.getMessage());
            e.printStackTrace();
            pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
            return;
        }

        Iterator bads = null;
%>

<html>
<form method="POST" name="searchForm"
          action="<%=response.encodeURL("./LoadNewSubjectsCheckFile.jsp")%>"
          onsubmit="return checkSubmitFlag();">
    <table class="navigate">
        <tr>
            <td class="left" align="left" colspan="2"><a class="largest">Create Subjects From File</a></td>
        </tr>
    </table>
    <table style="padding-left:20pt"><tr><td>
        <%if (missingValues == null || badCohort == null || badEthnicity == null || numGoodSubj == null) {%>
        <table style="background-color:#FBFBFB">
            <tr><td>An error occured while parsing your file, please contact a systems administrator.</td></tr>
        </table>
        <%} else {
    if (missingValues.size() > 0) {%>
        <tr><td class="subHeader" align="left" colspan="3">Missing Values</a></th>
        </tr><tr><td colspan="2">The following lines from your file contained too few values,
        if you proceed they will not be created.</td></tr>
        <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
                <% bads = missingValues.iterator();
            while (bads.hasNext()) {%><tr><td><%=bads.next()%></td></tr>
    <%}%></td></tr></table><%}%>

    <%if (dupSubjects.size() > 0) {%>
        <tr><td class="subHeader" align="left" colspan="3">Cohort-Subject ID Pair Occurs >1 in File</a></th>
        </tr><tr><td colspan="2">The following lines from your file contain a cohort-subjectID pair that occurs more than once in this file,
        if you proceed the subjects in these lines will not be created. Each line in this file must have a unique cohort-subjectID pair.</td></tr>
        <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
                <% bads = dupSubjects.iterator();
            while (bads.hasNext()) {%><tr><td><%=bads.next()%></td></tr>
    <%}%></td></tr></table><%}%>

    <%    if (alreadyExists.size() > 0) {%>
        <tr><td class="subHeader" align="left" colspan="3">Subjects Already Exist</a></th>
        </tr><tr><td colspan="2">The following lines from your file contained subject names that already exist in the database,
        if you proceed they will not be created.</td></tr>
        <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
                <% bads = alreadyExists.iterator();
            while (bads.hasNext()) {%><tr><td><%=bads.next()%></td></tr>
    <%}%></td></tr></table><%}
    if (badCohort.size() > 0) {%>
    <tr><td class="subHeader" align="left" colspan="3">Cohorts Not Found</a></th>
    </tr><tr><td colspan="2">The following lines from your file describe cohorts that are not in the database,
    if you proceed they will not be created. If you wish to create these cohorts, contact an admin user.</td></tr>
    <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
        <% bads = badCohort.iterator();
        while (bads.hasNext()) {
        %><tr><td><%=bads.next()%></td></tr>
    <%}%></table></td></tr><%
    }
    if (badEthnicity.size() > 0) {%>
    <tr><td class="subHeader" align="left" colspan="3">Ethnicities Not Found</a></th>
    </tr><tr><td colspan="2">The following lines from your file describe ethnicities that are not in the database,
    if you proceed they will not be created. If you wish to create these ethnicities, contact an admin user.</td></tr>
    <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
        <% bads = badEthnicity.iterator();
    while (bads.hasNext()) {%>
        <tr><td><%=bads.next()%></td></tr>
    <%}%></table></td></tr><%}%>
    </td></tr>
    <%if (missingValues.size() == 0 && badCohort.size() == 0 && badEthnicity.size() == 0 && alreadyExists.size() == 0 && dupSubjects.size() == 0 ) {%>
    <tr><td colspan="4">No errors were found in your file.</td></tr>
    <%} else {%>
    <tr><td colspan="2"style="font-weight:bold"><br>Would you like to try again?&nbsp;&nbsp;
    <a href="./LoadNewSubjectsChooseFile.jsp">Go Back</a></td></tr>
    <tr><td colspan="2">If not, continue below.</td></tr>
    <%}%>
    </table><br><br>
    <table>
        <tr><td>If you proceed, <%=numGoodSubj%> subjects will be added to SLIMS.
        <tr><td>Would you like to create these subjects?&nbsp;&nbsp;</td>
        <td><input type="submit" name="create" value="Create Subjects"/> </td>
        <td><input type="submit" name="cancel" value="Cancel"/></td></tr>
    </table>
    <%}%>
</form>
<br/>
<br/>
<br/>
<br/>
</html>
<%}%>
<%@include file="Footer.jsp"%>