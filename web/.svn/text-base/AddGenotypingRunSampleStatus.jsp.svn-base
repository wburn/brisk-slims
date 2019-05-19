<%String lapasId = "ViewGenotypingRunSampleStatus";%>
<%@include file="Header.jsp"%>


<%//test new session
        response.setHeader("Cache-Control", "no-store");
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }

// check permissions
if (!tmpHttpSessObj.isPowerUserDryUp()) {%>
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
            pageContext.forward(response.encodeURL("./ViewGenotypingRunSampleStatuss.jsp"));
            return;
        }
        if ((tmpAction = request.getParameter("deleteGenotypingRunSampleStatus")) != null) {
            selfId = request.getParameter("selfId");
            tmpHttpSessObj.deleteGenotypingRunSampleStatus(selfId);
            pageContext.forward(response.encodeURL("./ViewGenotypingRunSampleStatuss.jsp") + "?del=1");
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
        GenotypingRunSampleStatus genotypingrunsamplestatus = null;
        String genotypingRunID;
        String containerContentsID;
        String valid;

        if ((tmpAction = request.getParameter("addGenotypingRunSampleStatus")) != null) {
            genotypingRunID = request.getParameter("genotypingRunID");
            containerContentsID = request.getParameter("containerContentsID");
            valid = request.getParameter("valid");

            if (messageNum == 0) {
                messageNum = tmpHttpSessObj.addGenotypingRunSampleStatus(genotypingRunID, containerContentsID, valid);

            }
            if (messageNum == 0) {
                pageContext.forward(response.encodeURL("./ViewGenotypingRunSampleStatuss.jsp") + "?add=1");
                return;
            } else if (messageNum == 1) {
                pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                return;
            }
%>
<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddGenotypingRunSampleStatus.jsp")%>">
    <a class="largest">Add Genotyping Run Sample Status</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <%@include file="AddGenotypingRunSampleStatusE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addGenotypingRunSampleStatus" value="  Add  "/>
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
<%} else if ((tmpAction = request.getParameter("updateGenotypingRunSampleStatus")) != null) {
    genotypingrunsamplestatus = tmpHttpSessObj.getCurrentGenotypingRunSampleStatus();
    selfId = request.getParameter("selfId");
    genotypingRunID = request.getParameter("genotypingRunID");
    containerContentsID = request.getParameter("containerContentsID");
    valid = request.getParameter("valid");

    if (messageNum == 0) {
        messageNum = tmpHttpSessObj.updateGenotypingRunSampleStatus(genotypingRunID, containerContentsID, valid);

    }
    if (messageNum == 0) {
        pageContext.forward(response.encodeURL("./ViewGenotypingRunSampleStatuss.jsp"));
        return;
    }
    if (messageNum == 1) {
        pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
        return;
    }

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddGenotypingRunSampleStatus.jsp")%>">
    <a class="largest">Edit GenotypingRunSampleStatus</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <%genotypingRunID = request.getParameter("genotypingRunID");%>
    <input type="hidden" name="genotypingRunID" value="<%=genotypingRunID%>"/>
    <%@include file="AddGenotypingRunSampleStatusE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateGenotypingRunSampleStatus" value="Update"/>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else //from View page
if ((tmpAction = request.getParameter("containerContentsID")) != null) {
    if (tmpAction.equals("-1")) {//Add genotypingrunsamplestatus
        //?????????????????????
        genotypingRunID = null;
        containerContentsID = null;
        valid = null;

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddGenotypingRunSampleStatus.jsp")%>">
    <a class="largest">Add GenotypingRunSampleStatus</a>
    <br>
    <a class="error">&nbsp;</a>
    <%@include file="AddGenotypingRunSampleStatusE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addGenotypingRunSampleStatus" value="  Add  "/>
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
    } else {//edit GenotypingRunSampleStatus
        tmpHttpSessObj.setCurrentGenotypingRunSampleStatus(tmpAction);
        genotypingrunsamplestatus = tmpHttpSessObj.getCurrentGenotypingRunSampleStatus();
        if (genotypingrunsamplestatus != null) {
            selfId = tmpAction;
            genotypingRunID = genotypingrunsamplestatus.getGenotypingRunID();
            containerContentsID = genotypingrunsamplestatus.getContainerContentsID();
            valid = genotypingrunsamplestatus.getValid();

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddGenotypingRunSampleStatus.jsp")%>">
    <a class="largest">Edit GenotypingRunSampleStatus</a>
    <br>
    <a class="error">&nbsp;</a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <input type="hidden" name="genotypingRunID" value="<%=genotypingRunID%>"/>
    <%@include file="AddGenotypingRunSampleStatusE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateGenotypingRunSampleStatus" value="Update"/>
                    
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

