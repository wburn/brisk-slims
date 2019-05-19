<%String lapasId = "LoadNewSNPWithBeadCheckFile";%>

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
        alert("in go back");
        pageContext.forward(response.encodeURL("./LoadNewGenotypeWithBeadChooseFile.jsp"));
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


            if ((tmpString = request.getParameter("cancel")) != null) {
                tmpHttpSessObj.setCurrentUploader(null);
                pageContext.forward(response.encodeURL("./Index.jsp"));
                return;
            }

            // user has approved results of SLIMS parsing file, so add the containers
            if ((tmpString = request.getParameter("create")) != null) {

                FileUploading up = tmpHttpSessObj.getCurrentUploader();

                // add the subjects just parsed
                if (messageNum == 0) {
                    messageNum = up.addBeadManager(tmpHttpSessObj);
                }
                if (messageNum == 0) {
                    // reset uploader
                    tmpHttpSessObj.setCurrentUploader(null);
                    pageContext.forward(response.encodeURL("./Browse.jsp"));
                    return;
                } else {
                    System.out.println("Error in list creation.");

%>
<form method="POST" name="searchForm"
      action="<%=response.encodeURL("./LoadNewGenotypeWithBeadCheckFile.jsp")%>"
      onsubmit="return checkSubmitFlag()">
    <br>
    <a class="error">  Error:
        <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>

    <br><br>
</form>
<%}
            } else {

                // Check that we have a file upload request
                boolean isMultipart = FileUpload.isMultipartContent(request);

                // Create a new file upload handler
                ServletFileUpload upload = new ServletFileUpload();

                ArrayList<String> alreadyExists = null;
                ArrayList<String> dupContainers = null;
                ArrayList<String> missingValues = null;
                ArrayList<String> badContainerTypes = null;
                ArrayList<String> badFreezer = null;
                ArrayList<String> badShippedTo = null;
                ArrayList<String> badYN = null;
                ArrayList<String> badDate = null;
                ArrayList<String> missingValuesSNP = null;
                ArrayList<String> alreadyExistsSubject = null;
                ArrayList<String> missingValuesSubject = null;
                ArrayList<String> dupSubjects = null;
                ArrayList<String> badCohort = null;
                ArrayList<String> badEthnicity = null;
                ArrayList<String> missingValuesSampCont = null;
                ArrayList<String> badSampleType = null;
                ArrayList<String> badSubject = null;
                ArrayList<String> badConcVol = null;
                ArrayList<String> badMaterialType = null;
                ArrayList<String> missingValuesGeno = null;
                ArrayList<String> badSNPs = null;
                ArrayList<String> badContent = null;

                int numGoodSNPs = -1;
                int numGoodSubjects = -1;
                int numGoodContainers = -1;
                int numGoodSamples = -1;
                int numGoodContents = -1;
                int numGoodGeno = -1;

                FileItemIterator iter = upload.getItemIterator(request);
                try {
                    FileUploading up = new FileUploading();
                    tmpHttpSessObj.setCurrentUploader(up);
                    Object[] arr = up.checkBeadManager(iter);
                    Object[] genoErrors = (Object[]) arr[3];
//GENOTYPE
                    missingValuesGeno = (ArrayList) genoErrors[0];
                    badSNPs = (ArrayList) genoErrors[1];
                    badContent = (ArrayList) genoErrors[2];
                    numGoodGeno = (Integer) genoErrors[3];

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
          action="<%=response.encodeURL("./LoadNewGenotypesWithBeadCheckFile.jsp")%>"
          onsubmit="return checkSubmitFlag();">
        <table class="navigate">
            <tr>
                <td class="left" align="left" colspan="2"><a class="largest">Populate Database with Bead Studio Formatted Files</a></td>
            </tr>
        </table>
        <table style="padding-left:20pt"><tr><td>
                    <%if (numGoodGeno == -1) {%>
                    <table style="background-color:#FBFBFB">
                        <tr><td>An error occurred while parsing your file, please contact a systems administrator.</td></tr>
                    </table>
                    <%} else {%>

            <%if (missingValuesGeno.size() > 0 || badSNPs.size() > 0 || badContent.size() > 0) {%>
            <tr><td class="subHeader" align="left" colspan="3">Sample/Container File - Errors</td>
            </tr><%}%>
            <%if (missingValuesGeno.size() > 0) {%>
            <tr><td class="subSubHeader" align="left" colspan="3">Missing Values</td>
            </tr><tr><td colspan="2">The following lines from your file contained too few values,
                    if you proceed they will not be created.</td></tr>
            <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
                        <% bads = missingValuesGeno.iterator();
                            while (bads.hasNext()) {%><tr><td><%=bads.next()%></td></tr>
                        <%}%></table></td></tr><%}%>
                        <%if (badSNPs.size() > 0) {%>
            <tr><td class="subSubHeader" align="left" colspan="3">SNP Not Found</td>
            </tr><tr><td colspan="2">The following lines from your file describe SNPs that are not in the database,
                    if you proceed they will not be created. If you wish to create these SNPs, contact an admin.</td></tr>
            <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
                        <% bads = badSNPs.iterator();
                            while (bads.hasNext()) {
                        %><tr><td><%=bads.next()%></td></tr>
                        <%}%></table></td></tr><%}%>

            <%if (badContent.size() > 0) {%>
            <tr><td class="subSubHeader" align="left" colspan="3">Container Content (Samples) Not Found</td>
            </tr><tr><td colspan="2">The following lines from your file describe container contents (samples) that are not in the database,
                    if you proceed the container contents (samples) defined by these lines will not be created.
                    If you wish to create these container contents (samples), contact an admin user.</td></tr>
            <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
                        <% bads = badContent.iterator();
                            while (bads.hasNext()) {
                        %><tr><td><%=bads.next()%></td></tr>
                        <%}%></table></td></tr><%}%>







            <tr><td colspan="2"style="font-weight:bold"><br>Would you like to try again?&nbsp;&nbsp;
                    <a href="./LoadNewGenotypesWithBeadChooseFile.jsp">Go Back</a></td></tr>
            <tr><td colspan="2">If not, continue below.</td></tr>
        </table><br><br>
        <table>
            <tr><td>If you proceed: <%=numGoodGeno%> Genotypes will be added to SLIMS.
            <tr><td>Would you like to create these containers?&nbsp;&nbsp;</td>
                <td><input type="submit" name="create" value="Create"/> </td>
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