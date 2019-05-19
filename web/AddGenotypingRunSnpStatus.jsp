<%String lapasId = "ViewGenotypingRunSnpStatus";%>
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
            pageContext.forward(response.encodeURL("./ViewGenotypingRunSnpStatuss.jsp"));
            return;
        }
        if ((tmpAction = request.getParameter("deleteGenotypingRunSnpStatus")) != null) {
            selfId = request.getParameter("selfId");
            tmpHttpSessObj.deleteGenotypingRunSnpStatus(selfId);
            pageContext.forward(response.encodeURL("./ViewGenotypingRunSnpStatuss.jsp") + "?del=1");
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
        GenotypingRunSnpStatus genotypingrunsnpstatus = null;
        String genoRunSnpStatId;
String genotypingRunID;
String snpID;
String valid;
String strand;

        if ((tmpAction = request.getParameter("addGenotypingRunSnpStatus")) != null) {
            genoRunSnpStatId = request.getParameter("genoRunSnpStatId");
             snpID= request.getParameter("snpID");
             valid= request.getParameter("valid");
             strand= request.getParameter("strand");

            if (messageNum == 0) {
                messageNum = tmpHttpSessObj.addGenotypingRunSnpStatus(genoRunSnpStatId, snpID, valid, strand);

            }
            if (messageNum == 0) {
                pageContext.forward(response.encodeURL("./ViewGenotypingRunSnpStatuss.jsp") + "?add=1");
                return;
            } else if (messageNum == 1) {
                pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                return;
            }
%>
<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddGenotypingRunSnpStatus.jsp")%>">
    <a class="largest">Add GenotypingRunSnpStatus</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <%@include file="AddGenotypingRunSnpStatusE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addGenotypingRunSnpStatus" value="  Add  "/>
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
<%} else if ((tmpAction = request.getParameter("updateGenotypingRunSnpStatus")) != null) {
    genotypingrunsnpstatus = tmpHttpSessObj.getCurrentGenotypingRunSnpStatus();
    selfId = request.getParameter("selfId");
    genoRunSnpStatId = request.getParameter("genoRunSnpStatId");
    snpID = request.getParameter("snpID");
    valid = request.getParameter("valid");
    strand = request.getParameter("strand");

    if (messageNum == 0) {
                messageNum = tmpHttpSessObj.updateGenotypingRunSnpStatus(genoRunSnpStatId, snpID, valid, strand);

    }
    if (messageNum == 0) {
        pageContext.forward(response.encodeURL("./ViewGenotypingRunSnpStatuss.jsp"));
        return;
    }
    if (messageNum == 1) {
        pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
        return;
    }

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddGenotypingRunSnpStatus.jsp")%>">
    <a class="largest">Edit GenotypingRunSnpStatus</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <%genoRunSnpStatId = request.getParameter("genoRunSnpStatId");%>
    <input type="hidden" name="genoRunSnpStatId" value="<%=genoRunSnpStatId%>"/>
    <%@include file="AddGenotypingRunSnpStatusE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateGenotypingRunSnpStatus" value="Update"/>
                    </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else //from View page
if ((tmpAction = request.getParameter("genoRunSnpStatId")) != null) {
    if (tmpAction.equals("-1")) {//Add genotypingrunsnpstatus
        //?????????????????????
        genoRunSnpStatId = null;
    snpID = null;
    valid = null;
    strand = null;

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddGenotypingRunSnpStatus.jsp")%>">
    <a class="largest">Add GenotypingRunSnpStatus</a>
    <br>
    <a class="error">&nbsp;</a>
    <%@include file="AddGenotypingRunSnpStatusE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addGenotypingRunSnpStatus" value="  Add  "/>
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
    } else {//edit GenotypingRunSnpStatus
        tmpHttpSessObj.setCurrentGenotypingRunSnpStatus(tmpAction);
        genotypingrunsnpstatus = tmpHttpSessObj.getCurrentGenotypingRunSnpStatus();
        if (genotypingrunsnpstatus != null) {
            selfId = tmpAction;
            genoRunSnpStatId = genotypingrunsnpstatus.getVisibleName();
            genoRunSnpStatId = request.getParameter(" genoRunSnpStatId");
            genoRunSnpStatId = genotypingrunsnpstatus.getGenotypingRunSnpStatusID();
            snpID = genotypingrunsnpstatus.getSnpID();
            valid = genotypingrunsnpstatus.getValid();
            strand = genotypingrunsnpstatus.getStrand();

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddGenotypingRunSnpStatus.jsp")%>">
    <a class="largest">Edit GenotypingRunSnpStatus</a>
    <br>
    <a class="error">&nbsp;</a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <input type="hidden" name="genoRunSnpStatId" value="<%=genoRunSnpStatId%>"/>
    <%@include file="AddGenotypingRunSnpStatusE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateGenotypingRunSnpStatus" value="Update"/>
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

