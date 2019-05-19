<%String lapasId = "ViewContainerContent";%>
<%@include file="Header.jsp"%>


<%//test new session
        response.setHeader("Cache-Control", "no-store");
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }

// check permissions
if (!tmpHttpSessObj.isAdvancedUserWetUp()) {%>
<script  type="text/javascript" language="javascript">
    window.onload = disableAllButBack;
</script>
<br><a class="largest">Sorry, you do not have permission to use this page. </a><input type='button' onclick='history.go(-1);' name="badPermGoBack" value="Back" /><br><br>
Your privilege level is: <%=tmpHttpSessObj.getCurrentUser().getRightsReadable()%>.
To see the privileges associated with each level <input type="button" class="button" name="getPermissionDoc" onclick="openPermissionsDoc()" value="Click here"/> <br><br>
<br><br>
<% }else if (!tmpHttpSessObj.isPowerUserUp() && tmpHttpSessObj.getCurrentUser().getUserTypeID()==1) {%>
<script  type="text/javascript" language="javascript">
    window.onload = disableDryUserFields;
    function disableDryUserFields(){
        // select boxes can't be read only
        document.getElementById('parentName').style.visibility = "hidden";
        document.getElementById('sampleID').style.visibility = "hidden";
        document.getElementById('materialTypeID').style.visibility = "hidden";
        document.getElementById('contaminated').style.visibility = "hidden";
        document.getElementById('containerID').style.visibility = "hidden";
        document.getElementById('row').style.visibility = "hidden";
        
        document.getElementById('parentRow').readOnly = true;
        document.getElementById('parentColumn').readOnly = true;
        document.getElementById('ayear').readOnly = true;
        document.getElementById('amonth').readOnly = true;
        document.getElementById('aday').readOnly = true;
        document.getElementById('volume').readOnly = true;
        document.getElementById('dilution').readOnly = true;
        document.getElementById('concentration').readOnly = true;
        document.getElementById('column').readOnly = true;
        document.getElementById('editSample').readOnly = true;
        document.getElementById('addSample').readOnly = true;
    }
</script>
<br><a class="larger">Please note, you do not have full editing permission for this page. </a><br>
Your privilege level is: <%=tmpHttpSessObj.getCurrentUser().getRightsReadable()%>.
To see the privileges associated with each level <input type="button" class="button" name="getPermissionDoc" onclick="openPermissionsDoc()" value="Click here"/> <br><br>
<br>
<% }
else if (!tmpHttpSessObj.isAdvancedUserWetUp() && tmpHttpSessObj.getCurrentUser().getUserTypeID()==2) {%>
<script  type="text/javascript" language="javascript">
window.onload = disablePowerUserOnlyFields;
    function disablePowerUserOnlyFields(){
        document.getElementById('parentName').style.visibility = "hidden";
        document.getElementById('sampleID').style.visibility = "hidden";
        document.getElementById('materialTypeID').style.visibility = "hidden";

        document.getElementById('parentRow').readOnly = true;
        document.getElementById('parentColumn').readOnly = true;
        document.getElementById('ayear').readOnly = true;
        document.getElementById('amonth').readOnly = true;
        document.getElementById('aday').readOnly = true;
        document.getElementById('editSample').readOnly = true;
        document.getElementById('addSample').readOnly = true;
    }
</script>
<br><a class="larger">Please note, you do not have full editing permission for this page. </a><br>
Your privilege level is: <%=tmpHttpSessObj.getCurrentUser().getRightsReadable()%>.
To see the privileges associated with each level <input type="button" class="button" name="getPermissionDoc" onclick="openPermissionsDoc()" value="Click here"/> <br><br>
<br>
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

        String viewList;
        String viewContId;
        if ((tmpAction = request.getParameter("cancel")) != null) {
            // get container if cc display is only of contents of a container
            viewContId = request.getParameter("viewContId");
            viewList = request.getParameter("viewList");
            // viewList = "true" when blank value put in input tag
                if (viewList!=null && !viewList.equals("") && viewList.indexOf("null")<0){
                    response.sendRedirect(("./ViewContainerContentsList.jsp"));
                    return;
                }
                if (viewContId!=null && !viewContId.equals("") && viewContId.indexOf("null")<0){
                     pageContext.forward(response.encodeURL("./ViewContainerContents.jsp") + "?contId="+viewContId);
                    return;
                }
                pageContext.forward(response.encodeURL("./ViewContainerContents.jsp"));
                return;
        }
        if ((tmpAction = request.getParameter("deleteContainerContent")) != null) {
            selfId = request.getParameter("selfId");
            tmpHttpSessObj.deleteContainerContent(selfId);
            pageContext.forward(response.encodeURL("./ViewContainerContents.jsp") + "?del=1");
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
    function gotoEditSample( ccID){
        var sampleID = document.getElementById('sampleID').value.valueOf();
        window.location.assign(
        "<%=response.encodeURL("./AddSample.jsp")%>"+"?sampId="+sampleID+"&ccID="+ccID);
    }
    function gotoAddSample(ccID){
        window.location.assign(
        "<%=response.encodeURL("./AddSample.jsp")%>"+"?sampId=-1&ccID="+ccID);
    }
    $(window).load(function(){
        $("#addContainerContentForm").validate({
            rules: {
                aday:{
                    required: true,
                    range: [1,31]
                },
                amonth:{
                    required: true,
                    range: [1,12]
                },
                ayear:{
                    required: true,
                    number: true
                }
            },
            groups: {
                wgaDate: "aday amonth ayear"
            },
            errorPlacement: function(error, element) {
                if (element.attr("name") == "aday" || element.attr("name") == "amonth" || element.attr("name") == "ayear")
                    error.insertAfter("#aday");
                else
                    error.insertAfter(element);
            },
            success: function(label) {
                // set   as text for IE
                label.html(" ").addClass("checked");
            }
        });
    });
</script>
<%
        int messageNum = 0;
        ContainerContent containercontent = null;
        String contContId;
        String containerContentsID;
        String parentID;
        String parentName;
        String parentRow;
        String parentColumn;
        String contaminated;
        String containerID;
        String row;
        String column;
        String sampleID;
        String volume;
        String concentration;
        String dilution;
        String comments;
        String materialTypeID;
        Date amplificationDate;
        String ayear;
        String amonth;
        String aday;
        if ((tmpAction = request.getParameter("addContainerContent")) != null) {
            // get container if cc display is only of contents of a container
            viewContId = request.getParameter("viewContId");
            viewList = request.getParameter("viewList");

            contContId = request.getParameter("contContId");
            parentID = request.getParameter("parentID");
            parentName = request.getParameter("parentName");
            parentRow = request.getParameter("parentRow");
            parentColumn = request.getParameter("parentColumn");
            contaminated = request.getParameter("contaminated");
            containerID = request.getParameter("containerID");

            materialTypeID = request.getParameter("materialTypeID");

            ayear = request.getParameter("ayear");
            amonth = request.getParameter("amonth");
            aday = request.getParameter("aday");
            amplificationDate = tmpHttpSessObj.getDate(ayear, amonth, aday,
                    "ayear", "amonth", "aday");
            // if WGA, need amplification date
            if (amplificationDate == null && materialTypeID.equals("2")) {
                messageNum = 5;
            }

            row = request.getParameter("row");
            column = request.getParameter("column");
            sampleID = request.getParameter("sampleID");
            volume = request.getParameter("volume");
            concentration = request.getParameter("concentration");
            dilution = request.getParameter("dilution");
            if (dilution != null && dilution.equals("")) {
                dilution = null;
            }
            comments = request.getParameter("comments");
            if (comments != null && (comments.equals("") || comments.equals("null"))) {
                comments = null;
            }

            if (row != null && row.equals("")) {
                row = null;
            } else {
                try {
                    Integer.parseInt(row);
                } catch (NumberFormatException e) {
                    messageNum = 16;
                }
            }
            if (column != null && column.equals("")) {
                column = null;
            } else {
                try {
                    Integer.parseInt(column);
                } catch (NumberFormatException e) {
                    messageNum = 16;
                }
            }
            if (volume != null && volume.equals("")) {
                volume = null;
            } else {
                try {
                    Double.parseDouble(volume);
                } catch (NumberFormatException e) {
                    messageNum = 16;
                }
            }
            if (concentration != null && concentration.equals("")) {
                concentration = null;
            } else {
                try {
                    Double.parseDouble(concentration);
                } catch (NumberFormatException e) {
                    messageNum = 16;
                }
            }
            if (concentration == null){
                concentration = "-1";
                }
            if (parentRow != null && parentRow.equals("")) {
                parentRow = null;
            } else {
                try {
                    Integer.parseInt(parentRow);
                } catch (NumberFormatException e) {
                    messageNum = 16;
                }
            }
            if (parentColumn != null && parentColumn.equals("")) {
                parentColumn = null;
            } else {
                try {
                    Integer.parseInt(parentColumn);
                } catch (NumberFormatException e) {
                    messageNum = 16;
                }
            }
            if (row == null || row.equals("") ||
                    column == null || column.equals("") ||
                    volume == null || volume.equals("") ||
                    containerID == null || containerID.equals("") ||
                    sampleID == null || sampleID.equals("") ) {
                messageNum = 9;
            }

            if (parentID != null && parentID.equals("")) {
                parentID = null;
            }
            if (parentName != null && parentName.equals("")) {
                parentName = null;
            }

            if (messageNum == 0) {
                Container container = (Container) tmpHttpSessObj.getObjectById(Container.class, containerID);
                HashMap keys = new HashMap();
                keys.put("container.containerName", "'" + parentName + "'");
                keys.put("row", parentRow);
                keys.put("column", parentColumn);
                System.out.println("keys"+keys.toString());
                ContainerContent parent = (parentID != null) ? ((ContainerContent) tmpHttpSessObj.getObjectById(ContainerContent.class, parentID)) : ((ContainerContent) tmpHttpSessObj.getObjectByUniqueKey(ContainerContent.class, keys));
                System.out.println("parent"+parent);
                Sample sample = (Sample) tmpHttpSessObj.getObjectById(Sample.class, sampleID);
                MaterialType materialType = (MaterialType) tmpHttpSessObj.getObjectById(MaterialType.class, materialTypeID);

                if ((parentName != null || parentRow != null || parentColumn != null) && parent == null) {
                    messageNum = 17;
                }
                if (messageNum == 0) {
                    messageNum = tmpHttpSessObj.addContainerContent(contContId, parent, contaminated, container, row, column, sample, volume, concentration, dilution, comments,amplificationDate,materialType);
                }

            }
            if (messageNum == 0) {
                // viewList = "true" when blank value put in input tag
                if (viewList!=null && !viewList.equals("") && viewList.indexOf("null")<0){
                    response.sendRedirect(("./ViewContainerContentsList.jsp"));
                    return;
                }
                if (viewContId!=null && !viewContId.equals("") && viewContId.indexOf("null")<0){
                     pageContext.forward(response.encodeURL("./ViewContainerContents.jsp") + "?add=1&contId="+viewContId);
                    return;
                }
                pageContext.forward(response.encodeURL("./ViewContainerContents.jsp") + "?add=1");
                return;
            } else if (messageNum == 1) {
                pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                return;
            }
%>
<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()" id="addContainerContentForm"
      action="<%=response.encodeURL("./AddContainerContent.jsp")%>">
    <a class="largest">Add Sample</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <%@include file="AddContainerContentE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addContainerContent" value="  Add  "/>
                    <%} else {%>
                    <%=tmpHttpSessObj.getMyMessage(6)%>      <%}%>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10"class="cancel"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else if ((tmpAction = request.getParameter("updateContainerContent")) != null) {

    // get container if cc display is only of contents of a container
    viewContId = request.getParameter("viewContId");
    viewList = request.getParameter("viewList");
    
    containercontent = tmpHttpSessObj.getCurrentContainerContent();
    selfId = request.getParameter("selfId");
    contContId = request.getParameter("contContId");
    parentID = request.getParameter("parentID");
    parentName = request.getParameter("parentName");
    parentRow = request.getParameter("parentRow");
    parentColumn = request.getParameter("parentColumn");
    contaminated = request.getParameter("contaminated");
    containerID = request.getParameter("containerID");
    row = request.getParameter("row");
    column = request.getParameter("column");
    sampleID = request.getParameter("sampleID");
    volume = request.getParameter("volume");
    concentration = request.getParameter("concentration");
    materialTypeID = request.getParameter("materialTypeID");

    ayear = request.getParameter("ayear");
    amonth = request.getParameter("amonth");
    aday = request.getParameter("aday");
    amplificationDate = tmpHttpSessObj.getDate(ayear, amonth, aday,
            "ayear", "amonth", "aday");
    // if WGA, need amplification date
    if (amplificationDate == null && (materialTypeID==null || materialTypeID.equals("2"))) {
        messageNum = 5;
    }

    dilution = request.getParameter("dilution");
    if (dilution != null && (dilution.equals("") || dilution.equals("null") )) {
        dilution = "-1";
    }
    comments = request.getParameter("comments");
    if (comments != null && (comments.equals("") || comments.equals("null"))) {
        comments = null;
    }
    if (row == null || row.equals("")) {
        row = null;
    } else {
        try {
            Integer.parseInt(row);
        } catch (NumberFormatException e) {
            messageNum = 16;
        }
    }
    if (column == null || column.equals("")) {
        column = null;
    } else {
        try {
            Integer.parseInt(column);
        } catch (NumberFormatException e) {
            messageNum = 16;
        }
    }
    if (volume == null || volume.equals("")) {
        volume = null;
    } else {
        try {
            Double.parseDouble(volume);
        } catch (NumberFormatException e) {
            messageNum = 16;
        }
    }
    if (concentration == null || concentration.equals("")|| concentration.equals("null")) {
        concentration = "-1";
    } else {
        try {
            Double.parseDouble(concentration);
        } catch (NumberFormatException e) {
            messageNum = 16;
        }
    }
    if (parentRow == null || parentRow.equals("")) {
        parentRow = null;
    } else {
        try {
            Integer.parseInt(parentRow);
        } catch (NumberFormatException e) {
            messageNum = 16;
        }
    }
    if (parentColumn == null || parentColumn.equals("")) {
        parentColumn = null;
    } else {
        try {
            Integer.parseInt(parentColumn);
        } catch (NumberFormatException e) {
            messageNum = 16;
        }
    }

    if (row == null || row.equals("") ||
            column == null || column.equals("") ||
            volume == null || volume.equals("")) {
        messageNum = 9;
    }

    if (parentID != null && parentID.equals("")) {
        parentID = null;
    }
    if (parentName != null && parentName.equals("")) {
        parentName = null;
    }

    if (messageNum == 0) {
        Container container = (Container) tmpHttpSessObj.getObjectById(Container.class, containerID);
        Sample sample = (Sample) tmpHttpSessObj.getObjectById(Sample.class, sampleID);
        MaterialType materialType = (MaterialType) tmpHttpSessObj.getObjectById(MaterialType.class, materialTypeID);
        HashMap keys = new HashMap();
        keys.put("container.containerName", "'" + parentName + "'");
        keys.put("row", parentRow);
        keys.put("column", parentColumn);
        ContainerContent parent = (parentID != null) ? ((ContainerContent) tmpHttpSessObj.getObjectById(ContainerContent.class, parentID)) : ((ContainerContent) tmpHttpSessObj.getObjectByUniqueKey(ContainerContent.class, keys));

        if (parentColumn != null && parent == null) {
            messageNum = 17;
        }
        if (messageNum == 0) {

            messageNum = tmpHttpSessObj.updateContainerContent(contContId, parent, contaminated, container, row, column, sample, volume, concentration, dilution, comments,amplificationDate,materialType);
        }

    }
    if (messageNum == 0) {
        // viewList = "true" when blank value put in input tag
        if (viewList!=null && !viewList.equals("") && viewList.indexOf("null")<0){
            System.out.println("sending redirect, viewList='"+viewList+"'");
                    response.sendRedirect("./ViewContainerContentsList.jsp");
                    return;
                }
        if (viewContId!=null && !viewContId.equals("") && viewContId.indexOf("null")<0){
                System.out.println("sending redirect, viewContId='"+viewContId+"'");
               pageContext.forward(response.encodeURL("./ViewContainerContents.jsp") + "?add=1&contId="+viewContId);
                    return;
                }
         
        pageContext.forward(response.encodeURL("./ViewContainerContents.jsp") + "?add=1");
        return;
    }
    if (messageNum == 1) {
        pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
        return;
    }

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()" id="addContainerContentForm"
      action="<%=response.encodeURL("./AddContainerContent.jsp")%>">
    <a class="largest">Edit Sample</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <%contContId = request.getParameter("contContId");%>
    <input type="hidden" name="contContId" value="<%=contContId%>"/>
    <%viewList = request.getParameter("viewList");%>
    <input type="hidden" name="viewList" value="<%=viewList%>"/>
    <%viewContId = request.getParameter("viewContId");%>
    <input type="hidden" name="viewContId" value="<%=viewContId%>"/>
    <%@include file="AddContainerContentE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateContainerContent" value="Update"/>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10" class="cancel"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else //from View page
if ((tmpAction = request.getParameter("contContId")) != null) {
    if (tmpAction.equals("-1")) {//Add containercontent
        //?????????????????????
        contContId = null;
        parentID = null;
        parentName = null;
        parentRow = null;
        parentColumn = null;
        contaminated = null;
        containerID = null;
        row = null;
        column = null;
        sampleID = null;
        volume = null;
        concentration = null;
        dilution = null;
        comments = null;
        materialTypeID = null;
        amplificationDate = null;
        Date myDate = new Date();
        ayear = Util.getYear(myDate);
        amonth = Util.getMonth(myDate);
        aday = Util.getDay(myDate);

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()" id="addContainerContentForm"
      action="<%=response.encodeURL("./AddContainerContent.jsp")%>">

    <%viewList = request.getParameter("viewList");%>
    <input type="hidden" name="viewList" value="<%=viewList%>"/>
    <%viewContId = request.getParameter("viewContId");%>
    <input type="hidden" name="viewContId" value="<%=viewContId%>"/>

    <a class="largest">Add Sample</a>
    <br>
    <a class="error">&nbsp;</a>
    <%@include file="AddContainerContentE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addContainerContent" value="  Add  "/>
                    <%} else {%>
                    <%=tmpHttpSessObj.getMyMessage(6)%>      <%}%>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10" class="cancel"/>
                </td>
            </tr>
        </thead>
    </table>
</form><%
    } else {//edit ContainerContent
        tmpHttpSessObj.setCurrentContainerContent(tmpAction);
        containercontent = tmpHttpSessObj.getCurrentContainerContent();
        if (containercontent != null) {
            selfId = tmpAction;
            contContId = containercontent.getVisibleName();
            contContId = request.getParameter(" contContId");
            contContId = containercontent.getContainerContentsID();
            try {
                parentID = ((ContainerContent) containercontent.getParent()).getContainerContentsID();
            } catch (NullPointerException npe) {
                parentID = null;
            }
            try {
                parentName = ((ContainerContent) containercontent.getParent()).getContainer().getcontainerName();
            } catch (NullPointerException npe) {
                parentName = null;
            }
            try {
                parentRow = ((ContainerContent) containercontent.getParent()).getRow();
            } catch (NullPointerException npe) {
                parentRow = null;
            }
            try {
                parentColumn = ((ContainerContent) containercontent.getParent()).getColumn();
            } catch (NullPointerException npe) {
                parentColumn = null;
            }
            contaminated = containercontent.getContaminated();
            containerID = ((Container) containercontent.getContainer()).getContainerID();
            row = containercontent.getRow();
            column = containercontent.getColumn();
            sampleID = ((Sample) containercontent.getSample()).getSampleID();
            volume = containercontent.getVolume();
            concentration = containercontent.getConcentration();
            dilution = containercontent.getDilution();
            comments = containercontent.getComments();
            materialTypeID = containercontent.getMaterialType().getMaterialTypeID();
            amplificationDate = containercontent.getAmplificationDate();
            Date myDate = (containercontent.getAmplificationDate() != null) ? containercontent.getAmplificationDate() : new Date();
            ayear = Util.getYear(myDate);
            amonth = Util.getMonth(myDate);
            aday = Util.getDay(myDate);
            
%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()" id="addContainerContentForm"
      action="<%=response.encodeURL("./AddContainerContent.jsp")%>">
    <a class="largest">Edit Sample</a>
    <br>
    <a class="error">&nbsp;</a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <input type="hidden" name="contContId" value="<%=contContId%>"/>
    <%viewList = request.getParameter("viewList");%>
    <input type="hidden" name="viewList" value="<%=viewList%>"/>
    <%viewContId = request.getParameter("viewContId");%>
    <input type="hidden" name="viewContId" value="<%=viewContId%>"/>
    <%@include file="AddContainerContentE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateContainerContent" value="Update"/>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10" class="cancel"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%}
    }
}%>
<%@include file="Footer.jsp"%>

