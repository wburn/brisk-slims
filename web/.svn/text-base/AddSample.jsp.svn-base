<%String lapasId = "ViewSample";%>
<%@include file="Header.jsp"%>

<%//test new session
        response.setHeader("Cache-Control", "no-store");
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }

// check permissions
if (!tmpHttpSessObj.isPowerUserUp()) {%>
<script  type="text/javascript" language="javascript">
    window.onload = disableAllButBack;
</script>
<br><a class="largest">Sorry, you do not have permission to use this page. </a><input type='button' onclick='history.go(-1);' name="badPermGoBack" value="Back" /><br><br>
Your privilege level is: <%=tmpHttpSessObj.getCurrentUser().getRightsReadable()%>.
To see the privileges associated with each level <input type="button" class="button" name="getPermissionDoc" onclick="openPermissionsDoc()" value="Click here"/> <br><br>
<br><br>
<% }%>


<%
        String tmpAction;
        String selfId;
        tmpHttpSessObj.clearInvalidField();
//test new session
        response.setHeader("Cache-Control", "no-store");
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }
        
// where it was directed from
    String ccID = request.getParameter("ccID");
        if ((tmpAction = request.getParameter("cancelSam")) != null) {
            if(ccID != null){
                    pageContext.forward(response.encodeURL("./AddContainerContent.jsp") + "?contContId="+ccID);
                    return;
            }
            pageContext.forward(response.encodeURL("./ViewSamples.jsp"));
            return;
        }
%>
<script  type="text/javascript" language="javascript">
    function setFocus(){
        field = document.getElementById("kluda");
        if (field) field.focus();
        else
            document.fForm.genId.focus();
    }
    function confirmDelete(){
        return (confirm("Are You sure?"));
    }
    $(window).load(function(){
        $("#addSampleForm").validate({
            success: function(label) {
                // set   as text for IE
                label.html(" ").addClass("checked");
            }
        });
    });
</script>
<%


        int messageNum = 0;
        Sample sample = null;
        String sampId;
        String sampleID;
        String sampleName;
        String valid;
        String parentID;
        String sampleTypeID;
        String sampleProcessID;
        String subjectID;
        Date collectionDate= null;
        Date extractionDate= null;
        String comments= null;

        Sample parent = null;
        Subject subject = null;
        SampleType sampleType = null;
        SampleProcess sampleProcess = null;


        if ((tmpAction = request.getParameter("addSample")) != null) {
            sampId = request.getParameter("sampId");
            sampleName = request.getParameter("sampleName");
            valid = request.getParameter("valid");
            parentID = request.getParameter("parentID");
            sampleTypeID = request.getParameter("sampleTypeID");
            sampleProcessID = request.getParameter("sampleProcessID");
            subjectID = request.getParameter("subjectID");

    if (parentID==null || parentID.equals("")) {
        parentID = null;
    }
    if (sampleTypeID== null || sampleTypeID.equals("")) {
        sampleTypeID = null;
    }
    if (sampleProcessID==null || sampleProcessID.equals("")) {
        sampleProcessID = null;
    }

            if (sampId == null || sampId.equals("") ||
                    sampleName == null || sampleName.equals("") ||
                    valid == null || valid.equals("") ||
                    subjectID == null || subjectID.equals("")) {
                messageNum = 9;
            }
            if (parentID != null) {
                try {
                    Integer.parseInt(parentID);
                } catch (NumberFormatException e) {
                    messageNum = 15;
                }
            }
            if (messageNum == 0) {
                if(parentID!=null&& parentID.length()>0 && parentID.indexOf("null")<0)parent = (Sample) tmpHttpSessObj.getObjectById(Sample.class, parentID);
                subject = (Subject) tmpHttpSessObj.getObjectById(Subject.class, subjectID);
                if(sampleTypeID!=null&& sampleTypeID.length()>0 && sampleTypeID.indexOf("null")<0)sampleType = (SampleType) tmpHttpSessObj.getObjectById(SampleType.class, sampleTypeID);
                if(sampleProcessID!=null&& sampleProcessID.length()>0 && sampleProcessID.indexOf("null")<0)sampleProcess = (SampleProcess) tmpHttpSessObj.getObjectById(SampleProcess.class, sampleProcessID);

            }
            if (messageNum == 0) {
                messageNum = tmpHttpSessObj.addSample(sampId, sampleName, valid, parent, sampleType, subject, collectionDate, extractionDate, comments);

            }
            if (messageNum == 0) {
                if(ccID != null){
                    pageContext.forward(response.encodeURL("./AddContainerContent.jsp") + "?contContId="+ccID);
                    return;
                }
                pageContext.forward(response.encodeURL("./ViewSamples.jsp") + "?add=1");
                return;
            } else if (messageNum == 1) {
                pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                return;
            }
%>
<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()" id="addSampleform"
      action="<%=response.encodeURL("./AddSample.jsp")%>">
    <input type="hidden" name="ccID" value="<%=ccID%>"/>
    <a class="largest">Add Sample</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <%@include file="AddSampleE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addSample" value="  Add  "/>
                    <%} else {%>
                    <%=tmpHttpSessObj.getMyMessage(6)%>      <%}%>
                </td>
                <td>
                    <input type="submit" name="cancelSam" value="Cancel" size="10" class="cancel"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else if ((tmpAction = request.getParameter("updateSample")) != null) {
    sample = tmpHttpSessObj.getCurrentSample();
    selfId = request.getParameter("selfId");
    sampId = request.getParameter("sampId");
    sampleName = request.getParameter("sampleName");
    valid = request.getParameter("valid");
    parentID = request.getParameter("parentID");
    sampleTypeID = request.getParameter("sampleTypeID");
    sampleProcessID = request.getParameter("sampleProcessID");
    subjectID = request.getParameter("subjectID");

    if (parentID== null || parentID.equals("")) {
        parentID = null;
    }

    if (sampId == null || sampId.equals("") ||
            sampleName == null || sampleName.equals("") ||
            valid == null || valid.equals("") ||
            subjectID == null || subjectID.equals("")) {
        messageNum = 9;
    }
    if (parentID != null) {
        try {
            Integer.parseInt(parentID);
        } catch (NumberFormatException e) {
            messageNum = 15;
        }
    }
    if (messageNum == 0) {
               if(parentID!=null&& parentID.length()>0 && parentID.indexOf("null")<0)parent = (Sample) tmpHttpSessObj.getObjectById(Sample.class, parentID);
                subject = (Subject) tmpHttpSessObj.getObjectById(Subject.class, subjectID);
                if(sampleTypeID!=null&& sampleTypeID.length()>0 && sampleTypeID.indexOf("null")<0)sampleType = (SampleType) tmpHttpSessObj.getObjectById(SampleType.class, sampleTypeID);
                if(sampleProcessID!=null&& sampleProcessID.length()>0 && sampleProcessID.indexOf("null")<0)sampleProcess = (SampleProcess) tmpHttpSessObj.getObjectById(SampleProcess.class, sampleProcessID);

            }
    if (messageNum == 0) {
        messageNum = tmpHttpSessObj.updateSample(sampId, sampleName, valid, parent, sampleType, subject, collectionDate,extractionDate,comments);

    }
    if (messageNum == 0) {

        if(ccID != null){
            pageContext.forward(response.encodeURL("./AddContainerContent.jsp") + "?contContId="+ccID);
            return;
        }
        pageContext.forward(response.encodeURL("./ViewSamples.jsp"));
        return;
    }
    if (messageNum == 1) {
        pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
        return;
    }

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()" id="addSampleForm"
      action="<%=response.encodeURL("./AddSample.jsp")%>">
    <input type="hidden" name="ccID" value="<%=ccID%>"/>
    <%sampId = request.getParameter("sampId");%>
    <input type="hidden" name="sampId" value="<%=sampId%>"/>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <a class="largest">Edit Sample</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <%@include file="AddSampleE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateSample" value="Update"/>
                    </td>
                <td>
                    <input type="submit" name="cancelSam" value="Cancel" size="10" class="cancel"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else //from View page
if ((tmpAction = request.getParameter("sampId")) != null) {
    if (tmpAction.equals("-1")) {//Add sample
        //?????????????????????
        sampId = null;
        sampleName = null;
        valid = null;
        parentID = null;
        sampleTypeID = null;
        sampleProcessID = null;
        subjectID = null;
         collectionDate=null;
         extractionDate=null;
         comments=null;

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()" id="addSampleForm"
      action="<%=response.encodeURL("./AddSample.jsp")%>">
    <input type="hidden" name="ccID" value="<%=ccID%>"/>
    <a class="largest">Add Sample</a>
    <br>
    <a class="error">&nbsp;</a>
    <%@include file="AddSampleE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addSample" value="  Add  "/>
                    <%} else {%>
                    <%=tmpHttpSessObj.getMyMessage(6)%>      <%}%>
                </td>
                <td>
                    <input type="submit" name="cancelSam" value="Cancel" size="10" class="cancel"/>
                </td>
            </tr>
        </thead>
    </table>
</form><%
    } else {//edit Sample
        tmpHttpSessObj.setCurrentSample(tmpAction);
        sample = tmpHttpSessObj.getCurrentSample();
        if (sample != null) {
            selfId = tmpAction;
            sampId = sample.getVisibleName();
            sampId = request.getParameter(" sampId");
            sampId = sample.getSampleID();
            sampleName = sample.getSampleName();
            valid = sample.getValid();
            parentID = (sample.getParent()!= null)? sample.getParent().getSampleID() : null;
            sampleTypeID = (sample.getSampleType()!=null)?sample.getSampleType().getSampleTypeID():null;
            sampleProcessID = (sample.getSampleProcess()!=null)?sample.getSampleProcess().getSampleProcessID():null;
            subjectID = sample.getSubject().getSubjectID();

         collectionDate = sample.getDateCollected();
         extractionDate = sample.getDateExtracted();
         comments = sample.getComments();

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()" id="addSampleForm"
      action="<%=response.encodeURL("./AddSample.jsp")%>">
    <input type="hidden" name="ccID" value="<%=ccID%>"/>
    <a class="largest">Edit Sample</a>
    <br>
    <a class="error">&nbsp;</a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <input type="hidden" name="sampId" value="<%=sampId%>"/>
    <%@include file="AddSampleE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateSample" value="Update"/>
                </td>
                <td>
                    <input type="submit" name="cancelSam" value="Cancel" size="10" class="cancel"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%}
    }
}%>
<%@include file="Footer.jsp"%>

