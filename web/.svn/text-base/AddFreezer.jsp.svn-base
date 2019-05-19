<%String lapasId = "ViewFreezer";%>
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
            pageContext.forward(response.encodeURL("./ViewFreezers.jsp"));
            return;
        }
        if ((tmpAction = request.getParameter("deleteFreezer")) != null) {
            selfId = request.getParameter("selfId");
            tmpHttpSessObj.deleteFreezer(selfId);
            pageContext.forward(response.encodeURL("./ViewFreezers.jsp") + "?del=1");
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
        return (confirm("Are you sure?"));
    }

</script>
<%
        int messageNum = 0;
        Freezer freezer = null;
        String freezId;
        String freezerID;
        String description;
        String location;
        String temperature;
        String sortOrder;

        if ((tmpAction = request.getParameter("addFreezer")) != null) {
            freezId = request.getParameter("freezId");
            description = request.getParameter("description");
            location = request.getParameter("location");
            temperature = request.getParameter("temperature");
            sortOrder = request.getParameter("sortOrder");
            if ( sortOrder == null || sortOrder.equals("")) {
                sortOrder = "0";
            }
            if ( sortOrder == null || description == null) {
                messageNum = 9;
            }
            if (sortOrder != null) {
                try {
                    Integer.parseInt(sortOrder);
                } catch (NumberFormatException e) {
                    messageNum = 11;
                }
            }
            if (messageNum == 0) {
                messageNum = tmpHttpSessObj.addFreezer(freezId, description, location, temperature, sortOrder);

            }
            if (messageNum == 0) {
                pageContext.forward(response.encodeURL("./ViewFreezers.jsp") + "?add=1");
                return;
            } else if (messageNum == 1) {
                pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                return;
            }
%>
<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddFreezer.jsp")%>">
    <a class="largest">Add Freezer</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <%@include file="AddFreezerE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addFreezer" value="  Add  "/>
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
<%} else if ((tmpAction = request.getParameter("updateFreezer")) != null) {
    freezer = tmpHttpSessObj.getCurrentFreezer();
    selfId = request.getParameter("selfId");
    freezId = request.getParameter("freezId");
    description = request.getParameter("description");
    location = request.getParameter("location");
    temperature = request.getParameter("temperature");
    sortOrder = request.getParameter("sortOrder");
    if (sortOrder == null || sortOrder.equals("") ) {
        sortOrder = "0";
    }
    if (description == null || sortOrder == null) {
        messageNum = 9;
    }
    if (sortOrder != null) {
        try {
            Integer.parseInt(sortOrder);
        } catch (NumberFormatException e) {
            messageNum = 11;
        }
    }

    if (messageNum == 0) {
        messageNum = tmpHttpSessObj.updateFreezer(freezId, description, location, temperature, sortOrder);

    }
    if (messageNum == 0) {
        pageContext.forward(response.encodeURL("./ViewFreezers.jsp"));
        return;
    }
    if (messageNum == 1) {
        pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
        return;
    }

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddFreezer.jsp")%>">
    <a class="largest">Edit Freezer</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <%freezId = request.getParameter("freezId");%>
    <input type="hidden" name="freezId" value="<%=freezId%>"/>
    <%@include file="AddFreezerE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateFreezer" value="Update"/>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else //from View page
if ((tmpAction = request.getParameter("freezId")) != null) {
    if (tmpAction.equals("-1")) {//Add freezer
        //?????????????????????
        freezId = null;
        description = null;
        location = null;
        temperature = null;
        sortOrder = null;

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddFreezer.jsp")%>">
    <a class="largest">Add Freezer</a>
    <br>
    <a class="error">&nbsp;</a>
    <%@include file="AddFreezerE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addFreezer" value="  Add  "/>
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
    } else {//edit Freezer
        tmpHttpSessObj.setCurrentFreezer(tmpAction);
        freezer = tmpHttpSessObj.getCurrentFreezer();
        if (freezer != null) {
            selfId = tmpAction;

            //freezId = freezer.getVisibleName();
            //freezId = request.getParameter("freezId");
            freezId = freezer.getFreezerID().toString();

            description = freezer.getDescription();
            location = freezer.getLocation();
            temperature = freezer.getTemperature();
            sortOrder = freezer.getSortOrder().toString();

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddFreezer.jsp")%>">
    <a class="largest">Edit Freezer</a>
    <br>
    <a class="error">&nbsp;</a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <input type="hidden" name="freezId" value="<%=freezId%>"/>
    <%@include file="AddFreezerE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateFreezer" value="Update"/>
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

