<%String lapasId = "ViewSampleType";%>
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
            pageContext.forward(response.encodeURL("./ViewSampleTypes.jsp"));
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
        SampleType sampletype = null;
        String sampTypId;
String sampleTypeID;
String description;
String sortOrder;

        if ((tmpAction = request.getParameter("addSampleType")) != null) {
            sampTypId = request.getParameter("sampTypId");
             description= request.getParameter("description");
             sortOrder= request.getParameter("sortOrder");
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
                messageNum = tmpHttpSessObj.addSampleType(sampTypId, description, sortOrder);

            }
            if (messageNum == 0) {
                pageContext.forward(response.encodeURL("./ViewSampleTypes.jsp") + "?add=1");
                return;
            } else if (messageNum == 1) {
                pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                return;
            }
%>
<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddSampleType.jsp")%>">
    <a class="largest">Add SampleType</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <%@include file="AddSampleTypeE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addSampleType" value="  Add  "/>
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
<%} else if ((tmpAction = request.getParameter("updateSampleType")) != null) {
    sampletype = tmpHttpSessObj.getCurrentSampleType();
    selfId = request.getParameter("selfId");
    sampTypId = request.getParameter("sampTypId");
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
                messageNum = tmpHttpSessObj.updateSampleType(sampTypId, description, sortOrder);

    }
    if (messageNum == 0) {
        pageContext.forward(response.encodeURL("./ViewSampleTypes.jsp"));
        return;
    }
    if (messageNum == 1) {
        pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
        return;
    }

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddSampleType.jsp")%>">
    <a class="largest">Edit SampleType</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <%sampTypId = request.getParameter("sampTypId");%>
    <input type="hidden" name="sampTypId" value="<%=sampTypId%>"/>
    <%@include file="AddSampleTypeE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateSampleType" value="Update"/>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else //from View page
if ((tmpAction = request.getParameter("sampTypId")) != null) {
    if (tmpAction.equals("-1")) {//Add sampletype
        //?????????????????????
        sampTypId = null;
    description = null;
    sortOrder = null;

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddSampleType.jsp")%>">
    <a class="largest">Add SampleType</a>
    <br>
    <a class="error">&nbsp;</a>
    <%@include file="AddSampleTypeE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addSampleType" value="  Add  "/>
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
    } else {//edit SampleType
        tmpHttpSessObj.setCurrentSampleType(tmpAction);
        sampletype = tmpHttpSessObj.getCurrentSampleType();
        if (sampletype != null) {
            selfId = tmpAction;
            sampTypId = sampletype.getVisibleName();
            sampTypId = request.getParameter(" sampTypId");
            sampTypId = sampletype.getSampleTypeID();
            description = sampletype.getDescription();
            sortOrder = sampletype.getSortOrder();

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddSampleType.jsp")%>">
    <a class="largest">Edit SampleType</a>
    <br>
    <a class="error">&nbsp;</a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <input type="hidden" name="sampTypId" value="<%=sampTypId%>"/>
    <%@include file="AddSampleTypeE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateSampleType" value="Update"/>
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

