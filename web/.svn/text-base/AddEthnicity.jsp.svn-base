<%String lapasId = "ViewEthnicity";%>
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
            pageContext.forward(response.encodeURL("./ViewEthnicities.jsp"));
            return;
        }
        if ((tmpAction = request.getParameter("deleteEthnicity")) != null) {
            selfId = request.getParameter("selfId");
            tmpHttpSessObj.deleteEthnicity(selfId);
            pageContext.forward(response.encodeURL("./ViewEthnicities.jsp") + "?del=1");
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
        Ethnicity ethnicityObj = null;
        String ethnId;
        String ethnicityID;
        String ethnicity;

        if ((tmpAction = request.getParameter("addEthnicity")) != null) {
            ethnId = request.getParameter("ethnId");
             ethnicity= request.getParameter("ethnicity");
            if(ethnicity != null && ethnicity.equals("")){
                ethnicity = null;
            }
             if(ethnicity == null){
                messageNum = 9;
             }
            if (messageNum == 0) {
                messageNum = tmpHttpSessObj.addEthnicity(ethnId, ethnicity);

            }
            if (messageNum == 0) {
                pageContext.forward(response.encodeURL("./ViewEthnicities.jsp") + "?add=1");
                return;
            } else if (messageNum == 1) {
                pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                return;
            }
%>
<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddEthnicity.jsp")%>">
    <a class="largest">Add Ethnicity</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <%@include file="AddEthnicityE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addEthnicity" value="  Add  "/>
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
<%} else if ((tmpAction = request.getParameter("updateEthnicity")) != null) {
    ethnicityObj = tmpHttpSessObj.getCurrentEthnicity();
    selfId = request.getParameter("selfId");
    ethnId = request.getParameter("ethnId");
    ethnicity = request.getParameter("ethnicity");
    if(ethnicity != null && ethnicity.equals("")){
         ethnicity = null;
    }
    if(ethnicity == null ){
                messageNum = 9;
    }
    if (messageNum == 0) {
                messageNum = tmpHttpSessObj.updateEthnicity(ethnId, ethnicity);

    }
    if (messageNum == 0) {
        pageContext.forward(response.encodeURL("./ViewEthnicities.jsp"));
        return;
    }
    if (messageNum == 1) {
        pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
        return;
    }

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddEthnicity.jsp")%>">
    <a class="largest">Edit Ethnicity</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <%ethnId = request.getParameter("ethnId");%>
    <input type="hidden" name="ethnId" value="<%=ethnId%>"/>
    <%@include file="AddEthnicityE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateEthnicity" value="Update"/>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else //from View page
if ((tmpAction = request.getParameter("ethnId")) != null) {
    if (tmpAction.equals("-1")) {//Add ethnicity
        //?????????????????????
        ethnId = null;
    ethnicity = null;

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddEthnicity.jsp")%>">
    <a class="largest">Add Ethnicity</a>
    <br>
    <a class="error">&nbsp;</a>
    <%@include file="AddEthnicityE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addEthnicity" value="  Add  "/>
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
    } else {//edit Ethnicity
        tmpHttpSessObj.setCurrentEthnicity(tmpAction);
        ethnicityObj = tmpHttpSessObj.getCurrentEthnicity();
        if (ethnicityObj != null) {
            selfId = tmpAction;
            ethnId = ethnicityObj.getVisibleName();
            ethnId = request.getParameter(" ethnId");
            ethnId = ethnicityObj.getEthnicityID();
            ethnicity = ethnicityObj.getEthnicity();

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddEthnicity.jsp")%>">
    <a class="largest">Edit Ethnicity</a>
    <br>
    <a class="error">&nbsp;</a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <input type="hidden" name="ethnId" value="<%=ethnId%>"/>
    <%@include file="AddEthnicityE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateEthnicity" value="Update"/>
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

