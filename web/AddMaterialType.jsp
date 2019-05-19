<%String lapasId = "ViewMaterialType";%>
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
            pageContext.forward(response.encodeURL("./ViewMaterialTypes.jsp"));
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
        MaterialType materialtype = null;
        String matTypId;
String materialTypeID;
String description;
String sortOrder;

        if ((tmpAction = request.getParameter("addMaterialType")) != null) {
            matTypId = request.getParameter("matTypId");
             description= request.getParameter("description");
             //sortOrder= request.getParameter("sortOrder");
             sortOrder= "0";

            if (messageNum == 0) {
                messageNum = tmpHttpSessObj.addMaterialType(matTypId, description, sortOrder);

            }
            if (messageNum == 0) {
                pageContext.forward(response.encodeURL("./ViewMaterialTypes.jsp") + "?add=1");
                return;
            } else if (messageNum == 1) {
                pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                return;
            }
%>
<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddMaterialType.jsp")%>">
    <a class="largest">Add MaterialType</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <%@include file="AddMaterialTypeE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addMaterialType" value="  Add  "/>
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
<%} else if ((tmpAction = request.getParameter("updateMaterialType")) != null) {
    materialtype = tmpHttpSessObj.getCurrentMaterialType();
    selfId = request.getParameter("selfId");
    matTypId = request.getParameter("matTypId");
    description = request.getParameter("description");
    //sortOrder = request.getParameter("sortOrder");
    sortOrder = "0";

    if (messageNum == 0) {
                messageNum = tmpHttpSessObj.updateMaterialType(matTypId, description, sortOrder);

    }
    if (messageNum == 0) {
        pageContext.forward(response.encodeURL("./ViewMaterialTypes.jsp"));
        return;
    }
    if (messageNum == 1) {
        pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
        return;
    }

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddMaterialType.jsp")%>">
    <a class="largest">Edit MaterialType</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <%matTypId = request.getParameter("matTypId");%>
    <input type="hidden" name="matTypId" value="<%=matTypId%>"/>
    <%@include file="AddMaterialTypeE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                     <input type="submit" name="updateMaterialType" value="Update"/>
                    </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10"/>
                </td>

            </tr>
        </thead>
    </table>
</form>
<%} else //from View page
if ((tmpAction = request.getParameter("matTypId")) != null) {
    if (tmpAction.equals("-1")) {//Add materialtype
        //?????????????????????
        matTypId = null;
    description = null;
    sortOrder = null;

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddMaterialType.jsp")%>">
    <a class="largest">Add MaterialType</a>
    <br>
    <a class="error">&nbsp;</a>
    <%@include file="AddMaterialTypeE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addMaterialType" value="  Add  "/>
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
    } else {//edit MaterialType
        tmpHttpSessObj.setCurrentMaterialType(tmpAction);
        materialtype = tmpHttpSessObj.getCurrentMaterialType();
        if (materialtype != null) {
            selfId = tmpAction;
            matTypId = materialtype.getVisibleName();
            matTypId = request.getParameter(" matTypId");
            matTypId = materialtype.getMaterialTypeID();
            description = materialtype.getDescription();

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddMaterialType.jsp")%>">
    <a class="largest">Edit MaterialType</a>
    <br>
    <a class="error">&nbsp;</a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <input type="hidden" name="matTypId" value="<%=matTypId%>"/>
    <%@include file="AddMaterialTypeE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateMaterialType" value="Update"/>
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

