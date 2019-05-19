<%String lapasId = "ViewShippedTo";%>
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
            pageContext.forward(response.encodeURL("./ViewShippedTos.jsp"));
            return;
        }
        if ((tmpAction = request.getParameter("deleteShippedTo")) != null) {
            selfId = request.getParameter("selfId");
            tmpHttpSessObj.deleteShippedTo(selfId);
            pageContext.forward(response.encodeURL("./ViewShippedTos.jsp") + "?del=1");
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
        ShippedTo shippedTo = null;
        String shipId;
        String description;
        String sortOrder;

        if ((tmpAction = request.getParameter("addShippedTo")) != null) {
            shipId = request.getParameter("shipId");
            description = request.getParameter("description");
            sortOrder = request.getParameter("sortOrder");
            if ( sortOrder == null || sortOrder.equals("")) {
                sortOrder = "0";
            }
            if (description == null || sortOrder == null) {
                messageNum = 9;
            }
            try {
                Integer.parseInt(sortOrder);
            } catch (NumberFormatException e) {
                messageNum = 11;
            }

            if (messageNum == 0) {
                messageNum = tmpHttpSessObj.addShippedTo(shipId,
                        description, sortOrder);
            }
            if (messageNum == 0) {
                pageContext.forward(response.encodeURL("./ViewShippedTos.jsp") + "?add=1");
                return;
            } else if (messageNum == 1) {
                pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                return;
            }
%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddShippedTo.jsp")%>">
    <a class="largest">Add ShippedTo</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <%@include file="AddShippedToE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addShippedTo" value="  Add  "/>
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
<%} else if ((tmpAction = request.getParameter("updateShippedTo")) != null) {
    shippedTo = tmpHttpSessObj.getCurrentShippedTo();
    selfId = request.getParameter("selfId");
    shipId = request.getParameter("shipId");
    description = request.getParameter("description");
    sortOrder = request.getParameter("sortOrder");
    if ( sortOrder == null || sortOrder.equals("")) {
                sortOrder = "0";
            }
    if (description == null || sortOrder == null) {
        messageNum = 9;
    }
    try {
        Integer.parseInt(sortOrder);
    } catch (NumberFormatException e) {
        messageNum = 11;
    }

    if (messageNum == 0) {
        messageNum = tmpHttpSessObj.updateShippedTo(selfId,
                description, sortOrder);
    }
    if (messageNum == 0) {
        pageContext.forward(response.encodeURL("./ViewShippedTos.jsp"));
        return;
    }
    if (messageNum == 1) {
        pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
        return;
    }

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddShippedTo.jsp")%>">
    <a class="largest">Edit ShippedTo</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <%shipId = request.getParameter("shipId");%>
    <input type="hidden" name="shipId" value="<%=shipId%>"/>
    <%@include file="AddShippedToE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateShippedTo" value="Update"/>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else //from View page
if ((tmpAction = request.getParameter("shipId")) != null) {
    if (tmpAction.equals("-1")) {//Add shippedTo
        //?????????????????????
        shipId = null;
        description = null;
        sortOrder = null;

%>
<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddShippedTo.jsp")%>">
    <a class="largest">Add ShippedTo</a>
    <br>
    <a class="error">&nbsp;</a>
    <%@include file="AddShippedToE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addShippedTo" value="  Add  "/>
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
    } else {//edit ShippedTo
        tmpHttpSessObj.setCurrentShippedTo(tmpAction);
        shippedTo = tmpHttpSessObj.getCurrentShippedTo();
        if (shippedTo != null) {
            selfId = tmpAction;
            shipId = shippedTo.getVisibleName();
            shipId = request.getParameter("shipId");
            shipId = shippedTo.getShippedToID();
            description = shippedTo.getDescription();
            sortOrder = shippedTo.getSortOrder();
%>
<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddShippedTo.jsp")%>">
    <a class="largest">Edit ShippedTo</a>
    <br>
    <a class="error">&nbsp;</a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <input type="hidden" name="shipId" value="<%=shipId%>"/>
    <%@include file="AddShippedToE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateShippedTo" value="Update"/>
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
