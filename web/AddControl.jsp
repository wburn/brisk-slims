<%String lapasId = "ViewControl";%>
<%@include file="Header.jsp"%>



<%//test new session
        response.setHeader("Cache-Control", "no-store");
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }

// check permissions
if (!tmpHttpSessObj.isPowerUserWetUp()) {%>
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
            pageContext.forward(response.encodeURL("./ViewControls.jsp"));
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

</script>
<%
        int messageNum = 0;
        Control control = null;
        String cntrlId;
String controlID;
String description;
String controlType;

        if ((tmpAction = request.getParameter("addControl")) != null) {
            cntrlId = request.getParameter("cntrlId");
             description= request.getParameter("description");
             controlType= request.getParameter("controlType");

            if (messageNum == 0) {
                messageNum = tmpHttpSessObj.addControl(cntrlId, description, controlType);

            }
            if (messageNum == 0) {
                pageContext.forward(response.encodeURL("./ViewControls.jsp") + "?add=1");
                return;
            } else if (messageNum == 1) {
                pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                return;
            }
%>
<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddControl.jsp")%>">
    <a class="largest">Add Control</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <%@include file="AddControlE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addControl" value="  Add  "/>
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
<%} else if ((tmpAction = request.getParameter("updateControl")) != null) {
    control = tmpHttpSessObj.getCurrentControl();
    selfId = request.getParameter("selfId");
    cntrlId = request.getParameter("cntrlId");
    description = request.getParameter("description");
    controlType = request.getParameter("controlType");

    if (messageNum == 0) {
                messageNum = tmpHttpSessObj.updateControl(cntrlId, description, controlType);

    }
    if (messageNum == 0) {
        pageContext.forward(response.encodeURL("./ViewControls.jsp"));
        return;
    }
    if (messageNum == 1) {
        pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
        return;
    }

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddControl.jsp")%>">
    <a class="largest">Edit Control</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <%cntrlId = request.getParameter("cntrlId");%>
    <input type="hidden" name="cntrlId" value="<%=cntrlId%>"/>
    <%@include file="AddControlE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateControl" value="Update"/>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else //from View page
if ((tmpAction = request.getParameter("cntrlId")) != null) {
    if (tmpAction.equals("-1")) {//Add control
        //?????????????????????
        cntrlId = null;
    description = null;
    controlType = null;

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddControl.jsp")%>">
    <a class="largest">Add Control</a>
    <br>
    <a class="error">&nbsp;</a>
    <%@include file="AddControlE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addControl" value="  Add  "/>
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
    } else {//edit Control
        tmpHttpSessObj.setCurrentControl(tmpAction);
        control = tmpHttpSessObj.getCurrentControl();
        if (control != null) {
            selfId = tmpAction;
            cntrlId = control.getVisibleName();
            cntrlId = request.getParameter(" cntrlId");
            cntrlId = control.getControlID();
            description = control.getDescription();
            controlType = control.getControlType();

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddControl.jsp")%>">
    <a class="largest">Edit Control</a>
    <br>
    <a class="error">&nbsp;</a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <input type="hidden" name="cntrlId" value="<%=cntrlId%>"/>
    <%@include file="AddControlE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateControl" value="Update"/>
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

