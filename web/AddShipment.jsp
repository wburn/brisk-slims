<%String lapasId = "ViewShipment";%>
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
                pageContext.forward(response.encodeURL("./ViewShipments.jsp"));
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
<script type="text/javascript">
    $(window).load(function(){
        $("#addShipmentForm").validate({
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
                shippedDate: "aday amonth ayear"
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
            Shipment shipment = null;
            String shipId;
            String contId;
            String shippedOut;
            Date shippedDate;
            String shipmentID;
            String ayear;
            String amonth;
            String aday;
            String shippedToID;
            String shipmentName;
            String comment;
            ShippedTo shippedTo = null;

            if ((tmpAction = request.getParameter("updateShipment")) != null) {
                shipId = request.getParameter("shipId");
                contId = request.getParameter("contId");
                comment = request.getParameter("comment");
                shipmentName = request.getParameter("shipmentName");
                shippedOut = request.getParameter("shippedOut");
                if (shippedOut != null && !(shippedOut.equals("1") || shippedOut.equals("0") || shippedOut.equals("2"))) {
                    shippedOut = "0";
                }
                shippedToID = request.getParameter("shippedToID");
                if (shippedToID != null && shippedToID.equals("")) {
                    shippedToID = null;
                }
                ayear = request.getParameter("ayear");
                amonth = request.getParameter("amonth");
                aday = request.getParameter("aday");
                shippedDate = tmpHttpSessObj.getDate(ayear, amonth, aday,
                        "ayear", "amonth", "aday");
                if (shippedDate == null && shippedOut.equals("1")) {
                    messageNum = 5;
                }

                if (shippedOut == null || shippedOut.equals("0")) {
                    shippedDate = null;
                    shippedToID = null;
                }
                if (!shippedOut.equals("0")) {
                    if (shipmentName == null || shipmentName.equals("")) {
                        messageNum = 24;
                    }
                    if (comment != null && comment.equals("")) {
                        comment = null;
                    }
                }
                if (shippedOut == null) {
                    messageNum = 9;
                }
                if (messageNum == 0) {
                    shippedTo = (ShippedTo) tmpHttpSessObj.getObjectById(ShippedTo.class, shippedToID);
                }
                if (messageNum == 0) {
                    if (!shippedOut.equals("0")) {
                        messageNum = tmpHttpSessObj.updateShipment(shipId, shipmentName, shippedDate, shippedTo, shippedOut, comment);
                    }
                }
                if (messageNum == 0) {
                    pageContext.forward(response.encodeURL("./ViewShipments.jsp")+"?contId"+contId);
                    return;
                } else if (messageNum == 1) {
                    pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                    return;
                }
%>
<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()" id="addShipmentForm"
      action="<%=response.encodeURL("./AddShipment.jsp")%>">
    <a class="largest">Update Shipment</a>
    <br>
    <a class="error">  Error:
        <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
        <%@include file="AddShipmentE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="updateShipment" value="  Update  " />
                    <%} else {%>
                    <%=tmpHttpSessObj.getMyMessage(6)%>      <%}%>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10" class="cancel"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else {//edit Shipment
                if ((tmpAction = request.getParameter("shipId")) != null) {
                    tmpHttpSessObj.setCurrentShipment(tmpAction);
                    shipment = tmpHttpSessObj.getCurrentShipment();
                    if (shipment != null) {
                        comment = null;
                        shipmentName = null;
                        shippedToID = null;
                        shippedOut = "0";
                        shippedDate = new Date();
                        Date myDate = new Date();
                        selfId = tmpAction;
                        contId = request.getParameter("contId");
                        shipId = shipment.getVisibleName();
                        shipId = request.getParameter(" shipId");
                        shipId = shipment.getShipmentID();
                        shipmentName = shipment.getShipmentName();
                        comment = shipment.getComments();
                        shippedToID = (shipment.getShippedTo() != null) ? shipment.getShippedTo().getShippedToID().toString() : null;
                        myDate = (shipment.getShipDate() != null) ? shipment.getShipDate() : new Date();
                        shippedOut = shipment.getShipAction();
                        shippedDate = shipment.getShipDate();
                        ayear = Util.getYear(myDate);
                        amonth = Util.getMonth(myDate);
                        aday = Util.getDay(myDate);
%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()" id="addShipmentForm"
      action="<%=response.encodeURL("./AddShipment.jsp")%>">
    <a class="largest">Update Shipment</a>
    <br>
    <a class="error">&nbsp;</a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <input type="hidden" name="shipId" value="<%=shipId%>"/>
    <input type="hidden" name="contId" value="<%=contId%>"/>
    <%@include file="AddShipmentE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateShipment" value="Update"/>
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

