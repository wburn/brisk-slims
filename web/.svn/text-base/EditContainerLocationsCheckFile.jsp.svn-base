<%String lapasId = "EditContainerLocationsCheckFile";%>

<%@include file="Header.jsp"%>
<%@page import="icapture.com.FileUploading"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.FileItemIterator"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="org.apache.commons.io.*"%>


<script type="text/javascript" language="JavaScript">
    function goBack(){
        pageContext.forward(response.encodeURL("./EditContainerLocationsChooseFile.jsp"));
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

        // user has approved results of SLIMS parsing file, so add the containers
        if((tmpString = request.getParameter("create"))!=null){

            FileUploading up = tmpHttpSessObj.getCurrentUploader();

            // add the subjects just parsed
            if (messageNum == 0) {
                messageNum = up.editContainers(tmpHttpSessObj);
            }
            if(messageNum==0){
                // reset uploader
                tmpHttpSessObj.setCurrentUploader(null);
                pageContext.forward(response.encodeURL("./ViewContainers.jsp"));
                return;
            }
            else{
                System.out.println("Error in list creation.");

%>
<form method="POST" name="searchForm"
          action="<%=response.encodeURL("./EditContainerLocationsCheckFile.jsp")%>"
          onsubmit="return checkSubmitFlag()">
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>

    <br><br>
</form>
<%}} else  {

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload();

        ArrayList<String> doesNotExists = null;
        ArrayList<String> dupContainers = null;
        ArrayList<String> missingValues = null;
        ArrayList<String> badFreezer = null;
        ArrayList<String> notInteger = null;
        Integer numGoodContainers = -1;

        FileItemIterator iter = upload.getItemIterator(request);
        try {
            FileUploading up = new FileUploading();
            tmpHttpSessObj.setCurrentUploader(up);
            Object[] arr = up.checkUploadContainerLocationFile(iter);
            //{alreadyExists,dupContainers,missingValues,badContainerType,badDate
            //,badFreezer,badShippedTo,badYN,goodContainers.size()};

            doesNotExists = (ArrayList) arr[0];
            dupContainers = (ArrayList) arr[1];
            missingValues = (ArrayList) arr[2];
            badFreezer = (ArrayList) arr[3];
            numGoodContainers = (Integer) arr[4];
            notInteger = (ArrayList) arr[5];
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
          action="<%=response.encodeURL("./EditContainerLocationsCheckFile.jsp")%>"
          onsubmit="return checkSubmitFlag();">
    <table class="navigate">
        <tr>
            <td class="left" align="left" colspan="2"><a class="largest">Edit Container Locations From File</a></td>
        </tr>
    </table>
    <table style="padding-left:20pt"><tr><td>
        <%if (doesNotExists == null || missingValues == null || badFreezer == null || numGoodContainers == null) {%>
        <table style="background-color:#FBFBFB">
            <tr><td>An error occured while parsing your file, please contact a systems administrator.</td></tr>
        </table>
        <%} else {%>

    <%if (notInteger.size() > 0) {%>
        <tr><td class="subHeader" align="left" colspan="3">Not Integer Values</a></th>
        </tr><tr><td colspan="2">The following lines from your file contained values that are suppose to be integers (check shelf),
        if you proceed the containers they define will not be created.</td></tr>
        <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
                <% bads = notInteger.iterator();
            while (bads.hasNext()) {%><tr><td><%=bads.next()%></td></tr>
    <%}%></td></tr></table><%}%>

    <%if (missingValues.size() > 0) {%>
        <tr><td class="subHeader" align="left" colspan="3">Missing Values</a></th>
        </tr><tr><td colspan="2">The following lines from your file contained too few values,
        if you proceed the containers they define will not be created.</td></tr>
        <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
                <% bads = missingValues.iterator();
            while (bads.hasNext()) {%><tr><td><%=bads.next()%></td></tr>
    <%}%></td></tr></table><%}%>

    <%if (dupContainers.size() > 0) {%>
        <tr><td class="subHeader" align="left" colspan="3">Containers Occur >1 in File</a></th>
        </tr><tr><td colspan="2">The following lines from your file contain a container name that occurs more than once in your file,
        if you proceed the containers in the lines below will not be created. Each line in this file must have a unique container name.</td></tr>
        <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
                <% bads = dupContainers.iterator();
            while (bads.hasNext()) {%><tr><td><%=bads.next()%></td></tr>
    <%}%></td></tr></table><%}%>

    <%if (doesNotExists.size() > 0) {%>
        <tr><td class="subHeader" align="left" colspan="3">Containers Does Not Exist</a></th>
        </tr><tr><td colspan="2">The following lines from your file describe containers that don't exist in the database,
        if you proceed they will not be modified.</td></tr>
        <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
                <% bads = doesNotExists.iterator();
            while (bads.hasNext()) {%><tr><td><%=bads.next()%></td></tr>
    <%}%></td></tr></table><%}%>

    <%if (badFreezer.size() > 0) {%>
    <tr><td class="subHeader" align="left" colspan="3">Freezers Not Found</a></th>
    </tr><tr><td colspan="2">The following lines from your file describe freezers that are not in the database,
    if you proceed the containers defined by these lines will not be created.
    If you wish to create these freezers, contact an admin user.</td></tr>
    <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
        <% bads = badFreezer.iterator();
        while (bads.hasNext()) {
        %><tr><td><%=bads.next()%></td></tr>
    <%}%></table></td></tr><%}%>

    </td></tr>
    <%if (missingValues.size() == 0 && badFreezer.size() == 0 && doesNotExists.size() == 0 && notInteger.size() == 0) {%>
    <tr><td>No errors were found in your file.</td></tr>
    <%} else {%>
    <tr><td colspan="2"style="font-weight:bold"><br>Would you like to try again?&nbsp;&nbsp;
    <a href="./EditContainerLocationsChooseFile.jsp">Go Back</a></td></tr>
    <tr><td colspan="2">If not, continue below.</td></tr>
    <%}%>
    </table><br><br>
    <table>
        <tr><td>If you proceed, <%=numGoodContainers%> containers will be edited in SLIMS.
        <tr><td>Would you like to edit these containers?&nbsp;&nbsp;</td>
        <td><input type="submit" name="create" value="Edit Containers"/> </td>
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