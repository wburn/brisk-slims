<%-- 
    Document   : Add96WellPlates
    Created on : Oct 7, 2009, 2:36:20 PM
    Author     : tvanrossum
    customized 'add container' form for 96 well plates. Used for plating tool.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%String lapasId = "Add96WellPlates";%>
<%@include file="Header.jsp"%>
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
%>
<script  type="text/javascript" language="javascript">
    function setFocus(){
        field = document.getElementById("kluda");
        if (field) field.focus();
        else
            document.fForm.genId.focus();
    }
     function activate(which){
        if(which=='matSel'){
            document.getElementById('materialTypeID').disabled = false;
        }
        if(which=='notMatSel'){
            document.getElementById('materialTypeID').disabled = true;
        }
    }
</script>
<%
        int messageNum = 0;
        Container container = null;
        String contId;
        String newContainerID = "-1";
        String containerName;
        String containerTypeID;
        String freezerID;
        String shelf;
        String discarded;
        String shippedOut;
        Date shippedDate;
        String ayear;
        String amonth;
        String aday;
        String shippedToID;
        String comments;
        String isStock;
        String valid;
        String containerAlias;
        String lot;
        String initials;
        Date dateOnContainer;
        String cyear;
        String cmonth;
        String cday;
        String location;
        String barcode;
        String shipmentName;
        String comment;

        ShippedTo shippedTo = null;
        ContainerType containerType = null;
        Freezer freezer = null;

        Plater plater = tmpHttpSessObj.getCurrentPlater();

        // if pressed next and there are more containers to add
        if ((tmpAction = request.getParameter("addContainer")) != null) {
            contId = request.getParameter("contId");
            containerName = request.getParameter("containerName");
            containerAlias = request.getParameter("containerAlias");
            location = request.getParameter("location");
            barcode = request.getParameter("barcode");
            lot = request.getParameter("lot");
            shipmentName = request.getParameter("shipmentName");
            comment = request.getParameter("comment");

            if(lot== null){ lot="1";}
            valid = request.getParameter("valid");
            containerTypeID = request.getParameter("containerTypeID");
            freezerID = request.getParameter("freezerID");
            if (freezerID != null && freezerID.equals("")) {
                freezerID = null;
            }
            initials = request.getParameter("initials");
            if (initials != null && initials.equals("")) {
                initials = null;
            }
            shelf = request.getParameter("shelf");
            if (shelf != null && shelf.equals("")) {
                shelf = null;
            } else {
                try {
                    Integer.parseInt(shelf);
                } catch (NumberFormatException e) {
                    messageNum = 10;
                }
            }
            discarded = request.getParameter("discarded");
            shippedOut = request.getParameter("shippedOut");
            if (shippedOut != null && !(shippedOut.equals("1") || shippedOut.equals("0"))) {
                shippedOut = "0";
            }
                            if (shippedOut.equals("1") || shippedOut.equals("2")) {
                                if (shipmentName.equals("") || shipmentName == null) {
                                    messageNum = 24;
                                }
                                if (comment != null && comment.equals("")) {
                                    comment = null;
                                }
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

            comments = request.getParameter("comments");
            if (comments != null && comments.equals("")) {
                comments = null;
            }


            cyear = request.getParameter("cyear");
            cmonth = request.getParameter("cmonth");
            cday = request.getParameter("cday");
            dateOnContainer = tmpHttpSessObj.getDate(cyear, cmonth, cday,
                    "cyear", "cmonth", "cday");


            isStock = request.getParameter("isStock");

            if (contId == null || containerName == null || containerTypeID == null ||
                    shippedOut == null || discarded == null) {
                messageNum = 9;
            }

            if (messageNum == 0) {
                shippedTo = (ShippedTo) tmpHttpSessObj.getObjectById(ShippedTo.class, shippedToID);
                containerType = (ContainerType) tmpHttpSessObj.getObjectById(ContainerType.class, containerTypeID);
                freezer = (freezerID!=null)?(Freezer) tmpHttpSessObj.getObjectById(Freezer.class, freezerID):null;
            }
           if (messageNum == 0) {
                messageNum = tmpHttpSessObj.addContainer(contId, containerName,
                        containerType, freezer, shelf, discarded, comments,isStock,valid,containerAlias,lot,initials,dateOnContainer,location,barcode);
                }
            if (messageNum == 0) {
                if (shippedOut.equals("1")){
                    messageNum = tmpHttpSessObj.addShipment("-1", shipmentName, shippedDate, shippedTo, shippedOut, comment);
                }
                if (shippedOut.equals("2")){
                    messageNum = tmpHttpSessObj.addShipment("-1", "(Dummy Shipment) "+ shipmentName, shippedDate, shippedTo, "1", "Dummy shipment for returning new containers");
                    messageNum = tmpHttpSessObj.addShipCont("-1", tmpHttpSessObj.getCurrentShipment(), tmpHttpSessObj.getCurrentContainer());
                    messageNum = tmpHttpSessObj.addShipment("-1", shipmentName, shippedDate, shippedTo, shippedOut, comment);
                }
            }
                if (messageNum == 0) {
                newContainerID = tmpHttpSessObj.getCurrentContainerId().toString();
                messageNum = tmpHttpSessObj.addShipCont("-1", tmpHttpSessObj.getCurrentShipment(), tmpHttpSessObj.getCurrentContainer());
            }

            //set material type for new contents
            if(request.getParameter("materialTypeChange").equals("no")){
                plater.setMaterialType(null);
            }
            else if(request.getParameter("materialTypeChange").equals("yes")){
                String materialTypeID = request.getParameter("materialTypeID");
                plater.setMaterialType((MaterialType) tmpHttpSessObj.getObjectById(MaterialType.class, materialTypeID));
            }

            if (messageNum == 0) {
                // direct to contents creation if making container from list
                    pageContext.forward(response.encodeURL("./AddControlWellsToContainer.jsp") + "?listContainerID="+newContainerID);
                    return;
            } else if (messageNum == 1) {
                pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                return;
            }
int platesDone = plater.numberOfPlatesMade;

%>
<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./Add96WellPlates.jsp")%>">
    <a class="largest">Add 96 Well Plate #<%=platesDone+1%></a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <%@include file="Add96WellPlatesE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addContainer" value="  Add  "/>
                    <%} else {%>
                    <%=tmpHttpSessObj.getMyMessage(6)%>      <%}%>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else{ //from View page
        contId = null;
        containerName = null;
        containerTypeID = null;
        freezerID = null;
        shelf = null;
        discarded = null;
        shippedOut = null;
        shippedDate = null;
        shippedToID = null;
        comments = null;
        isStock = null;
        containerAlias = null;
        lot=null;
        valid=null;
        initials=null;
        dateOnContainer = null;
        location = null;
        barcode = null;

        Date myDate = new Date();
        ayear = Util.getYear(myDate);
        amonth = Util.getMonth(myDate);
        aday = Util.getDay(myDate);
        cyear = Util.getYear(myDate);
        cmonth = Util.getMonth(myDate);
        cday = Util.getDay(myDate);

        int platesDone = plater.numberOfPlatesMade;

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./Add96WellPlates.jsp")%>">

    <a class="largest">Add 96 Well Plate #<%=platesDone+1%></a>
    <br>
    <a class="error">&nbsp;</a>
    <%@include file="Add96WellPlatesE.jsp"%>

    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addContainer" value=" Next "/>
                    <%} else {%>
                    <%=tmpHttpSessObj.getMyMessage(6)%>      <%}%>
                </td>
            </tr>
        </thead>
    </table>
</form><%
    } 
%>
<%@include file="Footer.jsp"%>

