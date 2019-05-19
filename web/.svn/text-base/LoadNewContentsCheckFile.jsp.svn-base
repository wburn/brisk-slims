<%-- 
    Document   : LoadNewContentsCheckFile
    Created on : Nov 23, 2009, 1:57:34 PM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%String lapasId = "LoadNewContentsCheckFile";%>

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
        pageContext.forward(response.encodeURL("./LoadNewContentsChooseFile.jsp"));
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
                messageNum = up.addContents(tmpHttpSessObj);
            }
            if(messageNum==0){
                // reset uploader
                tmpHttpSessObj.setCurrentUploader(null);
                pageContext.forward(response.encodeURL("./ViewContainerContents.jsp"));
                return;
            }
            else{
                System.out.println("Error in list creation.");

%>
<form method="POST" name="searchForm"
          action="<%=response.encodeURL("./LoadNewContentsCheckFile.jsp")%>"
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
        ArrayList<String> missingValues= null;
        Integer numGoodCC = -1;

        ArrayList<String> dupContents = null;
        ArrayList<String> badContainer = null;
        ArrayList<String> badRow = null;
        ArrayList<String> badCol = null;
        ArrayList<String> badVol = null;
        ArrayList<String> badConcen =null;
        ArrayList<String> badDilution = null;
        ArrayList<String> badDate= null;
        ArrayList<String> badParent = null;
        ArrayList<String> badSample = null;
        ArrayList<String> badMaterialType =null;

        FileItemIterator iter = upload.getItemIterator(request);
        try {
            FileUploading up = new FileUploading();
            tmpHttpSessObj.setCurrentUploader(up);
            Object[] arr = up.checkUploadContentsFile(iter);
            //{missingValues,alreadyExists,dupContents,badContainer,badRow,badCol,badVol,badConcen,badDilution,badParent,badSample,badDate,badMaterialType,goodContents.size()};
            missingValues = (ArrayList) arr[0];
            alreadyExists = (ArrayList) arr[1];
            dupContents = (ArrayList) arr[2];
            badContainer = (ArrayList) arr[3];
            badRow = (ArrayList) arr[4];
            badCol = (ArrayList) arr[5];
            badVol = (ArrayList) arr[6];
            badConcen = (ArrayList) arr[7];
            badDilution = (ArrayList) arr[8];
            badParent = (ArrayList) arr[9];
            badSample = (ArrayList) arr[10];
            badDate = (ArrayList) arr[11];
            badMaterialType = (ArrayList) arr[12];
            numGoodCC = (Integer) arr[13];
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
          action="<%=response.encodeURL("./LoadNewContentsCheckFile.jsp")%>"
          onsubmit="return checkSubmitFlag();">
    <table class="navigate">
        <tr>
            <td class="left" align="left" colspan="2"><a class="largest">Create Samples From File</a></td>
        </tr>
    </table>
    <table style="padding-left:20pt"><tr><td>
        <%
        if (missingValues == null || alreadyExists == null || dupContents == null ||
    badContainer == null || badRow == null || badCol == null || badVol == null ||
    badConcen == null || badDilution == null || badParent == null ||
    badSample == null || badDate == null || badMaterialType == null || numGoodCC ==null) {%>
   <table style="background-color:#FBFBFB">
            <tr><td>An error occured while parsing your file, please contact a systems administrator.</td></tr>
        </table>
        <%} else {%>

    <%if (missingValues.size() > 0) {%>
        <tr><td class="subHeader" align="left" colspan="3">Missing Values</a></th>
        </tr><tr><td colspan="2">The following lines from your file contained too few values,
        if you proceed they will not be created.</td></tr>
        <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
                <% bads = missingValues.iterator();
            while (bads.hasNext()) {%><tr><td><%=bads.next()%></td></tr>
    <%}%></td></tr></table><%}%>

    <%if (alreadyExists.size() > 0) {%>
        <tr><td class="subHeader" align="left" colspan="3">Samples Already Exist</a></th>
        </tr><tr><td colspan="2">The following lines from your file contain [container name, row, column] sets that already exist in the database,
        if you proceed they will not be created.</td></tr>
        <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
                <% bads = alreadyExists.iterator();
            while (bads.hasNext()) {%><tr><td><%=bads.next()%></td></tr>
    <%}%></td></tr></table><%}%>

    <%if (dupContents.size() > 0) {%>
        <tr><td class="subHeader" align="left" colspan="3">Container Slot Occurs >1 in File</a></th>
        </tr><tr><td colspan="2">The following lines from your file contain a container slot (container name, row, column)
        that occurs more than once in your file,
        if you proceed the samples in these lines will not be created. Each line in this file must have a unique container slot.</td></tr>
        <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
                <% bads = dupContents.iterator();
            while (bads.hasNext()) {%><tr><td><%=bads.next()%></td></tr>
    <%}%></td></tr></table><%}%>

    <%if (badSample.size() > 0) {%>
    <tr><td class="subHeader" align="left" colspan="3">Sample ID Not Found</a></th>
    </tr><tr><td colspan="2">The following lines from your file contain sample IDs that are not in the database,
    if you proceed the samples described by these lines will not be created. If you wish to use these sample IDs, you must frist create them.</td></tr>
    <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
        <% bads = badSample.iterator();
        while (bads.hasNext()) {
        %><tr><td><%=bads.next()%></td></tr>
    <%}%></table></td></tr><%}%>

    <%if (badContainer.size() > 0) {%>
    <tr><td class="subHeader" align="left" colspan="3">Containers Not Found</a></th>
    </tr><tr><td colspan="2">The following lines from your file describe containers that are not in the database,
    if you proceed they will not be created. If you wish to use these containers, you must frist create them.</td></tr>
    <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
        <% bads = badContainer.iterator();
        while (bads.hasNext()) {
        %><tr><td><%=bads.next()%></td></tr>
    <%}%></table></td></tr><%}%>

    <%if (badRow.size() > 0) {%>
    <tr><td class="subHeader" align="left" colspan="3">Invalid Row</a></th>
    </tr><tr><td colspan="2">The following lines from your file describe an invalid row value,
    if you proceed they will not be created.</td></tr>
    <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
        <% bads = badRow.iterator();
    while (bads.hasNext()) {%>
        <tr><td><%=bads.next()%></td></tr>
    <%}%></table></td></tr><%}%>

    <%if (badCol.size() > 0) {%>
    <tr><td class="subHeader" align="left" colspan="3">Invalid Column</a></th>
    </tr><tr><td colspan="2">The following lines from your file describe an invalid column value,
    if you proceed they will not be created.</td></tr>
    <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
        <% bads = badCol.iterator();
    while (bads.hasNext()) {%>
        <tr><td><%=bads.next()%></td></tr>
    <%}%></table></td></tr><%}%>

    <%if (badMaterialType.size() > 0) {%>
    <tr><td class="subHeader" align="left" colspan="3">Material Type Not Found</a></th>
    </tr><tr><td colspan="2">The following lines from your file describe material types that are not in the database,
    if you proceed they will not be created. If you wish to create these subjects, contact an admin user.</td></tr>
    <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
        <% bads = badMaterialType.iterator();
        while (bads.hasNext()) {
        %><tr><td><%=bads.next()%></td></tr>
    <%}%></table></td></tr><%}%>

    <%if (badVol.size() > 0) {%>
    <tr><td class="subHeader" align="left" colspan="3">Invalid Volume</a></th>
    </tr><tr><td colspan="2">The following lines from your file describe an invalid volume value,
    if you proceed they will not be created.</td></tr>
    <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
        <% bads = badVol.iterator();
    while (bads.hasNext()) {%>
        <tr><td><%=bads.next()%></td></tr>
    <%}%></table></td></tr><%}%>

    <%if (badConcen.size() > 0) {%>
    <tr><td class="subHeader" align="left" colspan="3">Invalid Concentration</a></th>
    </tr><tr><td colspan="2">The following lines from your file describe an invalid concentration value,
    if you proceed they will not be created.</td></tr>
    <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
        <% bads = badConcen.iterator();
    while (bads.hasNext()) {%>
        <tr><td><%=bads.next()%></td></tr>
    <%}%></table></td></tr><%}%>

    <%if (badDilution.size() > 0) {%>
    <tr><td class="subHeader" align="left" colspan="3">Invalid Dilution</a></th>
    </tr><tr><td colspan="2">The following lines from your file describe an invalid dilution value,
    if you proceed they will not be created.</td></tr>
    <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
        <% bads = badDilution.iterator();
    while (bads.hasNext()) {%>
        <tr><td><%=bads.next()%></td></tr>
    <%}%></table></td></tr><%}%>

    <%if (badParent.size() > 0) {%>
    <tr><td class="subHeader" align="left" colspan="3">Sample Parent Not Found</a></th>
    </tr><tr><td colspan="2">The following lines from your file describe sample parents that were not found in the database,
    if you proceed the samples described by these lines will not be created.</td></tr>
    <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
        <% bads = badParent.iterator();
    while (bads.hasNext()) {%>
        <tr><td><%=bads.next()%></td></tr>
    <%}%></table></td></tr><%}%>

    <%if (badDate.size() > 0) {%>
    <tr><td class="subHeader" align="left" colspan="3">Badly Formatted Dates</a></th>
    </tr><tr><td colspan="2">The following lines from your file contain dates that are not in the required format,
    if you proceed theses samples will not be created. Dates must either be in a YYYY-MM-DD format (ex "2009-11-20") or "0"</td></tr>
    <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
        <% bads = badDate.iterator();
        while (bads.hasNext()) {
        %><tr><td><%=bads.next()%></td></tr>
    <%}%></table></td></tr><%}%>

    </td></tr>
    <%
    if (missingValues.size() == 0 && alreadyExists.size() == 0 && dupContents.size() == 0 && 
    badContainer.size() == 0 && badRow.size() == 0 && badCol.size() == 0 && badVol.size() == 0 && 
    badConcen.size() == 0 && badDilution.size() == 0 && badParent.size() == 0 && 
    badSample.size() == 0 && badDate.size() == 0 && badMaterialType.size() == 0) {%>
            
<tr><td>No errors were found in your file.</td></tr>
    <%} else {%>
    <tr><td colspan="2"style="font-weight:bold"><br>Would you like to try again?&nbsp;&nbsp;
    <a href="./LoadNewContentsChooseFile.jsp">Go Back</a></td></tr>
    <tr><td colspan="2">If not, continue below.</td></tr>
    <%}%>
    </table><br><br>
    <table>
        <tr><td>If you proceed, <%=numGoodCC%> samples will be added to SLIMS.
        <tr><td>Would you like to create these samples?&nbsp;&nbsp;</td>
        <td><input type="submit" name="create" value="Create Samples"/> </td>
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