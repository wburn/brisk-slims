<%String lapasId = "ViewSampleProcess";%>
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
        if ((tmpAction = request.getParameter("cancel")) != null) {
            pageContext.forward(response.encodeURL("./ViewSampleProcesss.jsp"));
            return;
        }
        if ((tmpAction = request.getParameter("deleteSampleProcess")) != null) {
            selfId = request.getParameter("selfId");
            tmpHttpSessObj.deleteSampleProcess(selfId);
            pageContext.forward(response.encodeURL("./ViewSampleProcesss.jsp") + "?del=1");
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

</script>
<%
        int messageNum = 0;
        SampleProcess sampleprocess = null;
        String sampProcId;
String sampleProcessID;
String description;
String sortOrder;

        if ((tmpAction = request.getParameter("addSampleProcess")) != null) {
            sampProcId = request.getParameter("sampProcId");
             description= request.getParameter("description");
             sortOrder= request.getParameter("sortOrder");

            if (messageNum == 0) {
                messageNum = tmpHttpSessObj.addSampleProcess(sampProcId, description, sortOrder);

            }
            if (messageNum == 0) {
                pageContext.forward(response.encodeURL("./ViewSampleProcesss.jsp") + "?add=1");
                return;
            } else if (messageNum == 1) {
                pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                return;
            }
%>
<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddSampleProcess.jsp")%>">
    <a class="largest">Add SampleProcess</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <%@include file="AddSampleProcessE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addSampleProcess" value="  Add  "/>
                    <%} else {%>
                    <%=tmpHttpSessObj.getMyMessage(6)%>      <%}%>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else if ((tmpAction = request.getParameter("updateSampleProcess")) != null) {
    sampleprocess = tmpHttpSessObj.getCurrentSampleProcess();
    selfId = request.getParameter("selfId");
    sampProcId = request.getParameter("sampProcId");
    description = request.getParameter("description");
    sortOrder = request.getParameter("sortOrder");

    if (messageNum == 0) {
                messageNum = tmpHttpSessObj.updateSampleProcess(sampProcId, description, sortOrder);

    }
    if (messageNum == 0) {
        pageContext.forward(response.encodeURL("./ViewSampleProcesss.jsp"));
        return;
    }
    if (messageNum == 1) {
        pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
        return;
    }

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddSampleProcess.jsp")%>">
    <a class="largest">Edit SampleProcess</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <%sampProcId = request.getParameter("sampProcId");%>
    <input type="hidden" name="sampProcId" value="<%=sampProcId%>"/>
    <%@include file="AddSampleProcessE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateSampleProcess" value="Update"/>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else //from View page
if ((tmpAction = request.getParameter("sampProcId")) != null) {
    if (tmpAction.equals("-1")) {//Add sampleprocess
        //?????????????????????
        sampProcId = null;
    description = null;
    sortOrder = null;

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddSampleProcess.jsp")%>">
    <a class="largest">Add SampleProcess</a>
    <br>
    <a class="error">&nbsp;</a>
    <%@include file="AddSampleProcessE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addSampleProcess" value="  Add  "/>
                    <%} else {%>
                    <%=tmpHttpSessObj.getMyMessage(6)%>      <%}%>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form><%
    } else {//edit SampleProcess
        tmpHttpSessObj.setCurrentSampleProcess(tmpAction);
        sampleprocess = tmpHttpSessObj.getCurrentSampleProcess();
        if (sampleprocess != null) {
            selfId = tmpAction;
            sampProcId = sampleprocess.getVisibleName();
            sampProcId = request.getParameter(" sampProcId");
            sampProcId = sampleprocess.getSampleProcessID();
            description = sampleprocess.getDescription();
            sortOrder = sampleprocess.getSortOrder();

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddSampleProcess.jsp")%>">
    <a class="largest">Edit SampleProcess</a>
    <br>
    <a class="error">&nbsp;</a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <input type="hidden" name="sampProcId" value="<%=sampProcId%>"/>
    <%@include file="AddSampleProcessE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateSampleProcess" value="Update"/>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%}
    }
}%>
<%@include file="Footer.jsp"%>

