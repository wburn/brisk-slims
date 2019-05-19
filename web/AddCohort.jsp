<%String lapasId = "ViewCohort";%>
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
            pageContext.forward("./ViewCohorts.jsp");
            //pageContext.forward(response.encodeURL("./ViewCohorts.jsp"));
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
        Cohort cohort = null;
        String cohoId;
        String cohortID;
        String description;
        String sortOrder;

        if ((tmpAction = request.getParameter("addCohort")) != null) {
            cohoId = request.getParameter("cohoId");
            description = request.getParameter("description");
            //sortOrder = request.getParameter("sortOrder");
            sortOrder = "0";
            if (sortOrder.equals("")) {
                sortOrder = null;
            }
            else {
            try {
                Integer.parseInt(sortOrder);
            } catch (NumberFormatException e) {
                messageNum = 11;
            }
}
            if ( cohoId == null || description== null || sortOrder== null) {
                messageNum = 9;
            }

            if (messageNum == 0) {
                messageNum = tmpHttpSessObj.addCohort(cohoId, description, sortOrder);

            }
            if (messageNum == 0) {
                pageContext.forward(response.encodeURL("./ViewCohorts.jsp") + "?add=1");
                return;
            } else if (messageNum == 1) {
                pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                return;
            }
%>
<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddCohort.jsp")%>">
    <a class="largest">Add Cohort</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <%@include file="AddCohortE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addCohort" value="  Add  "/>
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
<%} else if ((tmpAction = request.getParameter("updateCohort")) != null) {
    cohort = tmpHttpSessObj.getCurrentCohort();
    selfId = request.getParameter("selfId");
    cohoId = request.getParameter("cohoId");
    description = request.getParameter("description");
    sortOrder = request.getParameter("sortOrder");

    if (sortOrder == null || sortOrder.equals("")) {
        sortOrder = "0";
    }

    else {
            try {
        Integer.parseInt(sortOrder);
    } catch (NumberFormatException e) {
        messageNum = 11;
    }
            }
    if ( cohoId == null || description== null || sortOrder== null) {
                messageNum = 9;
            }

    if (messageNum == 0) {
        messageNum = tmpHttpSessObj.updateCohort(cohoId, description, sortOrder);

    }
    if (messageNum == 0) {
        pageContext.forward(response.encodeURL("./ViewCohorts.jsp"));
        return;
    }
    if (messageNum == 1) {
        pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
        return;
    }

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddCohort.jsp")%>">
    <a class="largest">Edit Cohort</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <%cohoId = request.getParameter("cohoId");%>
    <input type="hidden" name="cohoId" value="<%=cohoId%>"/>
    <%@include file="AddCohortE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateCohort" value="Update"/>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else //from View page
if ((tmpAction = request.getParameter("cohoId")) != null) {
    if (tmpAction.equals("-1")) {//Add cohort
        //?????????????????????
        cohoId = null;
        description = null;
        sortOrder = null;

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddCohort.jsp")%>">
    <a class="largest">Add Cohort</a>
    <br>
    <a class="error">&nbsp;</a>
    <%@include file="AddCohortE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addCohort" value="  Add  "/>
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
    } else {//edit Cohort
        tmpHttpSessObj.setCurrentCohort(tmpAction);
        cohort = tmpHttpSessObj.getCurrentCohort();
        if (cohort != null) {
            selfId = tmpAction;
            cohoId = cohort.getVisibleName();
            cohoId = request.getParameter(" cohoId");
            cohoId = cohort.getCohortID();
            description = cohort.getDescription();
            sortOrder = cohort.getSortOrder();

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddCohort.jsp")%>">
    <a class="largest">Edit Cohort</a>
    <br>
    <a class="error">&nbsp;</a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <input type="hidden" name="cohoId" value="<%=cohoId%>"/>
    <%@include file="AddCohortE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateCohort" value="Update"/>
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

