
<%String lapasId = "ViewContainerType";%>
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
        pageContext.forward(response.encodeURL("./ViewContainerTypes.jsp"));
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
    function IsNumeric(strString,add)
    //  check for valid numeric strings
    {
        var strValidChars = "0123456789"+add;
        var strChar;
        var blnResult = true;

        if (strString.length == 0) return false;

        //  test strString consists of valid characters listed above
        for (i = 0; i < strString.length && blnResult == true; i++)
        {
            strChar = strString.charAt(i);
            if (strValidChars.indexOf(strChar) == -1)
            {
                blnResult = false;
            }
        }
        return blnResult;
    }
    function validate(){
        var name = document.getElementById('name');
        var row = document.getElementById('row');
        var col = document.getElementById('col');
        if(name == null || name.value == null || name.value.valueOf()==""){
            alert("New container type name needed.");
            return false;
        }

        if(row != null && row.value != null && row.value.valueOf()!="" &&
            col != null && col.value != null && col.value.valueOf()!=""
            && IsNumeric(row.value.valueOf()) && IsNumeric(col.value.valueOf())){
            return true;
        }
        alert("Row and column must be numeric");
        return false;

    }

</script>
<%
        int messageNum = 0;
        ContainerType containertype = null;
        String contTypeId;
        String containerTypeID;
        String description;
        String rows;
        String columns;
        String sortOrder;

        if ((tmpAction = request.getParameter("addContainerType")) != null) {
            contTypeId = request.getParameter("contTypeId");
            description = request.getParameter("description");
            rows = request.getParameter("rows");
            columns = request.getParameter("columns");
            //sortOrder = request.getParameter("sortOrder");
            sortOrder = "0";

            rows = (rows.equals("")) ? null : rows;
            columns = (columns.equals("")) ? null : columns;
            //sortOrder = (sortOrder.equals("")) ? null : sortOrder;

            try {
                Integer.parseInt(rows);
                Integer.parseInt(columns);
            } catch (NumberFormatException e) {
                messageNum = 12;
            }

            /*try {
                Integer.parseInt(sortOrder);
            } catch (NumberFormatException e) {
                messageNum = 11;
            }*/


            if (messageNum == 0) {
                messageNum = tmpHttpSessObj.addContainerType(contTypeId,
                        description, rows, columns, sortOrder);

            }
            if (messageNum == 0) {
                pageContext.forward(response.encodeURL("./ViewContainerTypes.jsp") + "?add=1");
                return;
            } else if (messageNum == 1) {
                pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                return;
            }
%>
<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddContainerType.jsp")%>">
    <a class="largest">Add ContainerType</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <%@include file="AddContainerTypeE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addContainerType" value="  Add  " onclick="return validate()"/>
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
<%} else if ((tmpAction = request.getParameter("updateContainerType")) != null) {
    containertype = tmpHttpSessObj.getCurrentContainerType();
    selfId = request.getParameter("selfId");
    contTypeId = request.getParameter("contTypeId");
    description = request.getParameter("description");
    rows = request.getParameter("rows");
    columns = request.getParameter("columns");
    sortOrder = request.getParameter("sortOrder");

    rows = (rows.equals("")) ? null : rows;
    columns = (columns.equals("")) ? null : columns;
    sortOrder = (sortOrder.equals("")) ? null : sortOrder;

    try {
        Integer.parseInt(rows);
        Integer.parseInt(columns);
    } catch (NumberFormatException e) {
        messageNum = 12;
    }

    try {
        Integer.parseInt(sortOrder);
    } catch (NumberFormatException e) {
        messageNum = 11;
    }

    if (messageNum == 0) {
        messageNum = tmpHttpSessObj.updateContainerType(contTypeId, description,
                rows, columns, sortOrder);

    }
    if (messageNum == 0) {
        pageContext.forward(response.encodeURL("./ViewContainerTypes.jsp"));
        return;
    }
    if (messageNum == 1) {
        pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
        return;
    }

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddContainerType.jsp")%>">
    <a class="largest">Edit ContainerType</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <%contTypeId = request.getParameter("contTypeId");%>
    <input type="hidden" name="contTypeId" value="<%=contTypeId%>"/>
    <%@include file="AddContainerTypeE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                <input type="submit" name="updateContainerType" value="Update" onclick="return validate()"/>
                   </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else if ((tmpAction = request.getParameter("contTypeId")) != null) {//from View page
    if (tmpAction.equals("-1")) {//Add containertype
        contTypeId = null;
        description = null;
        rows = null;
        columns = null;
        sortOrder = null;

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddContainerType.jsp")%>">
    <a class="largest">Add ContainerType</a>
    <br>
    <a class="error">&nbsp;</a>
    <%@include file="AddContainerTypeE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addContainerType" value="  Add  " onclick="return validate()"/>
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
} else {//edit ContainerType
    tmpHttpSessObj.setCurrentContainerType(tmpAction);
    containertype = tmpHttpSessObj.getCurrentContainerType();
    if (containertype != null) {
        selfId = tmpAction;
        contTypeId = containertype.getVisibleName();
        contTypeId = request.getParameter(" contTypeId");
        contTypeId = containertype.getContainerTypeID();
        description = containertype.getDescription();
        rows = containertype.getRows();
        columns = containertype.getColumns();
        sortOrder = containertype.getSortOrder();

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddContainerType.jsp")%>">
    <a class="largest">Edit ContainerType</a>
    <br>
    <a class="error">&nbsp;</a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <input type="hidden" name="contTypeId" value="<%=contTypeId%>"/>
    <%@include file="AddContainerTypeE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateContainerType" value="Update"/>
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

