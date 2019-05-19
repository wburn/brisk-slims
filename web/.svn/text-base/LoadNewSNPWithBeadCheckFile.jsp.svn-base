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
        pageContext.forward(response.encodeURL("./LoadNewSNPWithBeadChooseFile.jsp"));
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
                    pageContext.forward(response.encodeURL("./LoadNewGenotypesWithBeadChooseFile.jsp"));
                    return;
                } else {
                    System.out.println("Error in list creation.");

%>
<form method="POST" name="searchForm"
      action="<%=response.encodeURL("./LoadNewSNPWithBeadCheckFile.jsp")%>"
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
                ArrayList<String> alreadyExistsSNP = null;
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
                    Object[] snpErrors = (Object[]) arr[0];
                    Object[] subjectErrors = (Object[]) arr[1];
                    Object[] sampContErrors = (Object[]) arr[2];

//SNP
                    missingValuesSNP = (ArrayList) snpErrors[0];
                    alreadyExistsSNP = (ArrayList) snpErrors[1];
                    numGoodSNPs = (Integer) snpErrors[2];
//SUBJECT
                    alreadyExistsSubject = (ArrayList) subjectErrors[0];
                    dupSubjects = (ArrayList) subjectErrors[1];
                    missingValuesSubject = (ArrayList) subjectErrors[2];
                    badCohort = (ArrayList) subjectErrors[3];
                    badEthnicity = (ArrayList) subjectErrors[4];
                    numGoodSubjects = (Integer) subjectErrors[5];
//SAMPLE/CONTAINER
                    missingValuesSampCont = (ArrayList) sampContErrors[0];
                    badSampleType = (ArrayList) sampContErrors[1];
                    badSubject = (ArrayList) sampContErrors[2];
                    badContainerTypes = (ArrayList) sampContErrors[3];
                    badConcVol = (ArrayList) sampContErrors[4];
                    badMaterialType = (ArrayList) sampContErrors[5];
                    numGoodSamples = (Integer) sampContErrors[6];
                    numGoodContainers = (Integer) sampContErrors[7];
                    numGoodContents = (Integer) sampContErrors[8];
//GENOTYPE

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
          action="<%=response.encodeURL("./LoadNewSNPWithBeadCheckFile.jsp")%>"
          onsubmit="return checkSubmitFlag();">
        <table class="navigate">
            <tr>
                <td class="left" align="left" colspan="2"><a class="largest">Populate Database with Bead Studio Formatted Files</a></td>
            </tr>
        </table>
        <table style="padding-left:20pt"><tr><td>
                    <%if (numGoodContainers == -1 || numGoodSubjects == -1 || numGoodSamples == -1 || numGoodContents == -1 || numGoodSNPs == -1) {%>
                    <table style="background-color:#FBFBFB">
                        <tr><td>An error occurred while parsing your file, please contact a systems administrator.</td></tr>
                    </table>
                    <%} else {%>

                    <%if (missingValuesSNP.size() > 0 || alreadyExistsSNP.size() > 0) {%>
            <tr><td class="subHeader" align="left" colspan="3">SNP File - Errors:</td>
            </tr>
            <%if (missingValuesSNP.size() > 0) { %>
            <tr><td class="subSubHeader" align="left" colspan="3">Missing Value:</td>
            </tr><tr><td colspan="2">The following lines from your file contained too few values,
                    if you proceed the SNPs they define will not be created.</td></tr>
            <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
                        <% bads = missingValuesSNP.iterator();
                            while (bads.hasNext()) {%><tr><td><%=bads.next()%></td></tr>
                        <%}%></table></td></tr><%}%>
            <%if (alreadyExistsSNP.size() > 0) { %>
            <tr><td class="subSubHeader" align="left" colspan="3">Already Exists:</td>
            </tr><tr><td colspan="2">The following lines from your file describe SNPs that already exist in the database,
                    if you proceed they will not be created.</td></tr>
            <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
                        <% bads = alreadyExistsSNP.iterator();
                            while (bads.hasNext()) {%><tr><td><%=bads.next()%></td></tr>
                        <%}%></table></td></tr><%}%>
            <%}%>



            <%if (alreadyExistsSubject.size() > 0 || dupSubjects.size() > 0 || missingValuesSubject.size() > 0 || badCohort.size() > 0 || badEthnicity.size() > 0) {%>
            <tr><td class="subHeader" align="left" colspan="3">Subject File - Errors:</td>
            </tr><%}%>
            <%if (alreadyExistsSubject.size() > 0) {%>
            <tr><td class="subSubHeader" align="left" colspan="3">Already Exists:</td>
            </tr><tr><td colspan="2">The following lines from your file describe subjects that already exist in the database,
                    if you proceed they will not be created.</td></tr>
            <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
                        <% bads = alreadyExistsSubject.iterator();
                            while (bads.hasNext()) {%><tr><td><%=bads.next()%></td></tr>
                        <%}%></table></td></tr><%}%>

            <%if (dupSubjects.size() > 0) {%>
            <tr><td class="subSubHeader" align="left" colspan="3">Duplicate Subjects in File:</td>
            </tr><tr><td colspan="2">The following lines from your file contain a subject name that occurs more than once in your file,
                    if you proceed the subjects in the lines below will not be created. Each line in this file must have a unique subject name.</td></tr>
            <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
                        <% bads = dupSubjects.iterator();
                            while (bads.hasNext()) {%><tr><td><%=bads.next()%></td></tr>
                        <%}%></table></td></tr><%}%>

            <%if (missingValuesSubject.size() > 0) {%>
            <tr><td class="subSubHeader" align="left" colspan="3">Missing Values:</td>
            </tr><tr><td colspan="2">The following lines from your file contained too few values,
                    if you proceed the Subjects they define will not be created.</td></tr>
            <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
                        <% bads = missingValuesSubject.iterator();
                            while (bads.hasNext()) {
                        %><tr><td><%=bads.next()%></td></tr>
                        <%}%></table></td></tr><%}%>

            <%if (badCohort.size() > 0) {%>
            <tr><td class="subSubHeader" align="left" colspan="3">Cohorts Not Found</td>
            </tr><tr><td colspan="2">The following lines from your file describe cohorts that are not in the database,
                    if you proceed they will not be created. If you wish to create these cohorts, contact an admin user.</td></tr>
            <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
                        <% bads = badCohort.iterator();
                            while (bads.hasNext()) {
                        %><tr><td><%=bads.next()%></td></tr>
                        <%}%></table></td></tr><%
                            }
                            if (badEthnicity.size() > 0) {%>
            <tr><td class="subSubHeader" align="left" colspan="3">Ethnicities Not Found</td>
            </tr><tr><td colspan="2">The following lines from your file describe ethnicities that are not in the database,
                    if you proceed they will not be created. If you wish to create these ethnicities, contact an admin user.</td></tr>
            <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
                        <% bads = badEthnicity.iterator();
                                                        while (bads.hasNext()) {%>
                        <tr><td><%=bads.next()%></td></tr>
                        <%}%></table></td></tr><%}%>



            <%if (missingValuesSampCont.size() > 0 || badSampleType.size() > 0 || badSubject.size() > 0 || badContainerTypes.size() > 0 || badConcVol.size() > 0 || badMaterialType.size() > 0) {%>
            <tr><td class="subHeader" align="left" colspan="3">Sample/Container File - Errors</td>
            </tr><%}%>
            <%if (missingValuesSampCont.size() > 0) {%>
            <tr><td class="subSubHeader" align="left" colspan="3">Missing Values</td>
            </tr><tr><td colspan="2">The following lines from your file contained too few values,
                    if you proceed they will not be created.</td></tr>
            <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
                        <% bads = missingValuesSampCont.iterator();
                            while (bads.hasNext()) {%><tr><td><%=bads.next()%></td></tr>
                        <%}%></table></td></tr><%}%>
                        <%if (badSubject.size() > 0) {%>
            <tr><td class="subSubHeader" align="left" colspan="3">Subjects Not Found</td>
            </tr><tr><td colspan="2">The following lines from your file describe subjects that are not in the subject file you submitted,
                    if you proceed they will not be created. If you wish to create these subjects, try the subject loader or the create subjects tool in the 'browse subjects' page.</td></tr>
            <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
                        <% bads = badSubject.iterator();
                            while (bads.hasNext()) {
                        %><tr><td><%=bads.next()%></td></tr>
                        <%}%></table></td></tr><%}%>

            <%if (badContainerTypes.size() > 0) {%>
            <tr><td class="subSubHeader" align="left" colspan="3">Container Types Not Found</td>
            </tr><tr><td colspan="2">The following lines from your file describe container types that are not in the database,
                    if you proceed the containers defined by these lines will not be created.
                    If you wish to create these container types, contact an admin user.</td></tr>
            <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
                        <% bads = badContainerTypes.iterator();
                            while (bads.hasNext()) {
                        %><tr><td><%=bads.next()%></td></tr>
                        <%}%></table></td></tr><%}%>

            <%if (badMaterialType.size() > 0) {%>
            <tr><td class="subSubHeader" align="left" colspan="3">Material Type Not Found</td>
            </tr><tr><td colspan="2">The following lines from your file describe material types that are not in the database,
                    if you proceed they will not be created. If you wish to create these subjects, contact an admin user.</td></tr>
            <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
                        <% bads = badMaterialType.iterator();
                            while (bads.hasNext()) {
                        %><tr><td><%=bads.next()%></td></tr>
                        <%}%></table></td></tr><%}%>

            <%if (badConcVol.size() > 0) {%>
            <tr><td class="subSubHeader" align="left" colspan="3">Invalid Concentration/Volume Value</td>
            </tr><tr><td colspan="2">The following lines from your file describe an invalid concentration value or invalid volume value,
                    if you proceed they will not be created.</td></tr>
            <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
                        <% bads = badConcVol.iterator();
                            while (bads.hasNext()) {%>
                        <tr><td><%=bads.next()%></td></tr>
                        <%}%></table></td></tr><%}%>

            <%if (badSampleType.size() > 0) {%>
            <tr><td class="subSubHeader" align="left" colspan="3">Sample Types Not Found</td>
            </tr><tr><td colspan="2">The following lines from your file describe sample types that are not in the database,
                    if you proceed they will not be created. If you wish to create these sample types, contact an admin user.</td></tr>
            <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
                        <% bads = badSampleType.iterator();
                            while (bads.hasNext()) {%>
                        <tr><td><%=bads.next()%></td></tr>
                        <%}%></table></td></tr><%}%>



            <tr><td colspan="2"style="font-weight:bold"><br>Would you like to try again?&nbsp;&nbsp;
                    <a href="./LoadNewSNPWithBeadChooseFile.jsp">Go Back</a></td></tr>
            <tr><td colspan="2">If not, continue below.</td></tr>
        </table><br><br>
        <table>
            <tr><td>If you proceed: <%=numGoodContainers%> Containers, <%=numGoodSubjects%> Subjects, <%=numGoodSamples%> Samples, <%=numGoodContents%> Container Contents (Samples), <%=numGoodSNPs%> SNPs will be added to SLIMS.
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