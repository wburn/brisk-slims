<%String lapasId = "ViewContainer";%>
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
            if ((tmpAction = request.getParameter("deleteContainer")) != null) {
                selfId = request.getParameter("selfId");
                tmpHttpSessObj.deleteContainer(selfId);
                pageContext.forward(response.encodeURL("./ViewContainers.jsp") + "?del=1");
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
    function reDirect(){
    <%  String list = request.getParameter("list");
                Boolean toList = false;
                if (list == null || list.equals("") || list.equalsIgnoreCase("null")) {
                    toList = false;
                } // direct to contents creation if making container from list
                else {
                    toList = true;
                }
    %>
            if ("<%=toList%>"=="false"){
                window.location.assign('<%=response.encodeURL("./ViewContainers.jsp")%>');
            }
            else {
                window.location.assign('<%=response.encodeURL("./ViewContainersList.jsp")%>');
            }
        }
</script>
<script type="text/javascript">
    $(window).load(function(){
        $("#addContainerForm").validate({
            rules: {
                cday:{
                    required: true,
                    range: [1,31]
                },
                cmonth:{
                    required: true,
                    range: [1,12]
                },
                cyear:{
                    required: true,
                    number: true
                },
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
                containerDate: "cday cmonth cyear",
                shippedDate: "aday amonth ayear"
            },
            errorPlacement: function(error, element) {
                if (element.attr("name") == "cday" || element.attr("name") == "cmonth" || element.attr("name") == "cyear")
                    error.insertAfter("#cday");
                else if (element.attr("name") == "aday" || element.attr("name") == "amonth" || element.attr("name") == "ayear")
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
            Boolean applyAll;


            ShippedTo shippedTo = null;
            ContainerType containerType = null;
            Freezer freezer = null;

            if ((tmpAction = request.getParameter("addContainer")) != null) {
                contId = request.getParameter("contId");
                containerName = request.getParameter("containerName");
                containerAlias = request.getParameter("containerAlias");
                location = request.getParameter("location");
                barcode = request.getParameter("barcode");
                lot = request.getParameter("lot");
                comment = request.getParameter("comment");
                shipmentName = request.getParameter("shipmentName");
                applyAll = Boolean.valueOf(request.getParameter("applyAll"));
                if (lot == null) {
                    lot = "1";
                }
                valid = request.getParameter("valid");
                containerTypeID = request.getParameter("containerTypeID");
                if (containerTypeID != null && containerTypeID.equals("")) {
                    containerTypeID = null;
                }
                freezerID = request.getParameter("freezerID");
                if (freezerID != null && freezerID.equals("")) {
                    freezerID = null;
                }
                initials = request.getParameter("initials");
                if (initials != null && initials.equals("")) {
                    initials = null;
                }
                shelf = request.getParameter("shelf");
                if (shelf != null && (shelf.equals("") || shelf.equals("null"))) {
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



                cyear = request.getParameter("cyear");
                cmonth = request.getParameter("cmonth");
                cday = request.getParameter("cday");
                dateOnContainer = tmpHttpSessObj.getDate(cyear, cmonth, cday,
                        "cyear", "cmonth", "cday");


                comments = request.getParameter("comments");
                if (comments != null && comments.equals("")) {
                    comments = null;
                }
                if (!shippedOut.equals("0")) {
                    if (shipmentName == null || shipmentName.equals("")) {
                        messageNum = 24;
                    }
                    if (comment != null && comment.equals("")) {
                        comment = null;
                    }
                }

                isStock = request.getParameter("isStock");

                if (contId == null || containerName == null || containerTypeID == null
                        || shippedOut == null || discarded == null || shelf == null) {
                    messageNum = 9;
                }

                if (messageNum == 0) {
                    shippedTo = (ShippedTo) tmpHttpSessObj.getObjectById(ShippedTo.class, shippedToID);
                    containerType = (ContainerType) tmpHttpSessObj.getObjectById(ContainerType.class, containerTypeID);
                    freezer = (freezerID != null) ? (Freezer) tmpHttpSessObj.getObjectById(Freezer.class, freezerID) : null;
                }

                if (messageNum == 0) {
                    messageNum = tmpHttpSessObj.addContainer(contId, containerName,
                            containerType, freezer, shelf, discarded, comments, isStock, valid, containerAlias, lot, initials, dateOnContainer, location, barcode);
                }

                if (messageNum == 0) {
                    if (!shippedOut.equals("0")) {
                        messageNum = tmpHttpSessObj.addShipment("-1", shipmentName, shippedDate, shippedTo, shippedOut, comment);
                    }
                }

                if (messageNum == 0) {
                    if (!shippedOut.equals("0")) {
                        messageNum = tmpHttpSessObj.addShipCont("-1", tmpHttpSessObj.getCurrentShipment(), tmpHttpSessObj.getCurrentContainer());
                    }
                }

                if (messageNum == 0) {
                    if (list == null || list.equals("") || list.equalsIgnoreCase("null")) {
                        pageContext.forward(response.encodeURL("./ViewContainers.jsp") + "?add=1");
                        return;
                    } else {
                        pageContext.forward(response.encodeURL("./ViewContainersList.jsp"));
                        return;
                    }
                } else if (messageNum == 1) {
                    pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                    return;
                }

%>
<form method="POST" name="fForm" id="addContainerForm"
      action="<%=response.encodeURL("./AddContainer.jsp") + "?list=" + list%>">
    <input type="hidden" name="list" value="<%=list%>">
    <a class="largest">Add Container</a>
    <br>
    <a class="error">  Error:
        <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
        <%@include file="AddContainerE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addContainer" value="  Add  "/>
                    <%} else {%>
                    <%=tmpHttpSessObj.getMyMessage(6)%>      <%}%>
                </td>
                <td>
                    <input type="button" name="cancel" value="Cancel" size="10" onclick="window.location.assign('<%=response.encodeURL("./ViewContainers.jsp")%>')"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else if ((tmpAction = request.getParameter("updateContainer")) != null) {
            container = tmpHttpSessObj.getCurrentContainer();
            selfId = request.getParameter("selfId");
            contId = request.getParameter("contId");
            containerName = request.getParameter("containerName");
            containerAlias = request.getParameter("containerAlias");
            location = request.getParameter("location");
            barcode = request.getParameter("barcode");
            lot = request.getParameter("lot");
            valid = request.getParameter("valid");
            containerTypeID = request.getParameter("containerTypeID");
            shipmentName = request.getParameter("shipmentName");
            comment = request.getParameter("comment");
            applyAll = Boolean.valueOf(request.getParameter("applyAll"));

            if (containerTypeID != null && containerTypeID.equals("")) {
                containerTypeID = null;
            }
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
            if (shippedDate == null && (shippedOut.equals("1") || shippedOut.equals("2"))) {
                messageNum = 5;
            }

            if (shippedOut == null || shippedOut.equals("0")) {
                shippedDate = null;
                shippedToID = null;
            }
            if (comment != null && comment.equals("")) {
                comment = null;
            }


            cyear = request.getParameter("cyear");
            cmonth = request.getParameter("cmonth");
            cday = request.getParameter("cday");
            dateOnContainer = tmpHttpSessObj.getDate(cyear, cmonth, cday,
                    "cyear", "cmonth", "cday");

            comments = request.getParameter("comments");
            if (comments != null && comments.equals("")) {
                comments = null;
            }
            if (!shippedOut.equals("0")) {
                if (shipmentName == null || shipmentName.equals("")) {
                    messageNum = 24;
                }
                if (comment != null && comment.equals("")) {
                    comment = null;
                }
            }

            if (lot == null) {
                lot = "1";
            }
            isStock = request.getParameter("isStock");

            if (contId == null || containerName == null || containerTypeID == null
                    || shippedOut == null || discarded == null) {
                messageNum = 9;
            }

            if (messageNum == 0) {
                shippedTo = (ShippedTo) tmpHttpSessObj.getObjectById(ShippedTo.class, shippedToID);
                containerType = (ContainerType) tmpHttpSessObj.getObjectById(ContainerType.class, containerTypeID);
                freezer = (freezerID != null) ? (Freezer) tmpHttpSessObj.getObjectById(Freezer.class, freezerID) : null;
            }

            if (messageNum == 0) {
                messageNum = tmpHttpSessObj.updateContainer(contId, containerName,
                        containerType, freezer, shelf, discarded, comments, isStock, valid, containerAlias, lot, initials, dateOnContainer, location, barcode);
            }
            String tmpShipmentID = "-1";

            if (messageNum == 0 && !shippedOut.equals("0")) {
                Container tmpContainer = tmpHttpSessObj.getCurrentContainer();
                List<ShipCont> tmpShipList = (tmpContainer != null) ? tmpContainer.getShipCont():null;
                ShipCont tmpShipCont = (tmpShipList != null && tmpShipList.size()>0) ? tmpShipList.get(tmpShipList.size()-1):null;
                Shipment tmpShipment = (tmpShipCont != null) ? tmpShipCont.getShipment():null;
                tmpShipmentID = (tmpShipment != null) ? tmpShipment.getShipmentID():"-1";
                HashMap contsInShipment = new HashMap();
                contsInShipment.put("shipmentID",tmpShipmentID);
                //There was a shipment before
                if (!tmpShipmentID.equals("-1")) {
                    //If we're editing all shipments || if we're only editing this one but there is only one container in the shipment
                    if ((applyAll) || (!applyAll && tmpHttpSessObj.countKey(ShipCont.class, contsInShipment) <= 1)) {
                        messageNum = tmpHttpSessObj.updateShipment(tmpShipmentID, shipmentName, shippedDate, shippedTo, shippedOut, comment);
                    // else if we're only editing one (and there are more than one containers in the shipment)
                    } else if (!applyAll) {
                        //make a new shipment
                        messageNum = tmpHttpSessObj.addShipment("-1", shipmentName, shippedDate, shippedTo, shippedOut, comment);
                        //edit the table to point to new shipment (remove from previous group shipment)
                        messageNum = tmpHttpSessObj.updateShipCont(tmpShipCont.getShipContID(), tmpHttpSessObj.getCurrentShipment(), tmpHttpSessObj.getCurrentContainer());
                    }
                }
                //if there wasn't a shipment before
                else {
                    messageNum = tmpHttpSessObj.addShipment("-1", shipmentName, shippedDate, shippedTo, shippedOut, comment);
                }

                if (messageNum == 0 && tmpShipmentID.equals("-1")) {
                    messageNum = tmpHttpSessObj.addShipCont("-1", tmpHttpSessObj.getCurrentShipment(), tmpHttpSessObj.getCurrentContainer());
                }
            }
            if (messageNum == 0) {

                if (list == null || list.equals("") || list.equalsIgnoreCase("null")) {
                    pageContext.forward(response.encodeURL("./ViewContainers.jsp") + "?add=1");
                    return;
                } else {
                    pageContext.forward(response.encodeURL("./ViewContainersList.jsp"));
                    return;
                }
            }

            if (messageNum == 1) {
                pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                return;
            }

%>

<form method="POST" name="fForm" id="addContainerForm"
      action="<%=response.encodeURL("./AddContainer.jsp")%>">
    <input type="hidden" name="list" value="<%=list%>">
    <a class="largest">Edit Container</a>
    <br>
    <a class="error">  Error:
        <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <%contId = request.getParameter("contId");%>
    <input type="hidden" name="contId" value="<%=contId%>"/>
    <%@include file="AddContainerE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateContainer" value="Update"/>
                </td>
                <td>
                    <input type='button' onclick="window.location.assign('<%=response.encodeURL("./ViewContainers.jsp")%>')" name="back" value="Cancel" />
                    <!--input type="submit" name="cancel" value="Cancel" size="10"/-->
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else //from View page
        if ((tmpAction = request.getParameter("contId")) != null) {
            System.out.println("2 in AddContainer.jsp");
            if (tmpAction.equals("-1")) {//Add container
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
                lot = null;
                valid = null;
                initials = null;
                dateOnContainer = null;
                location = null;
                barcode = null;
                comment = null;
                shipmentName = null;
                applyAll = false;

                Date myDate = new Date();
                ayear = Util.getYear(myDate);
                amonth = Util.getMonth(myDate);
                aday = Util.getDay(myDate);
                cyear = Util.getYear(myDate);
                cmonth = Util.getMonth(myDate);
                cday = Util.getDay(myDate);

                list = (request.getParameter("list") != null) ? request.getParameter("list") : null;

                System.out.println("end of code block in AddContainer.jsp");

                System.out.println("contId: " + contId);
%>

<form method="POST" name="fForm" id="addContainerForm"
      action="<%=response.encodeURL("./AddContainer.jsp")%>">
    <input type="hidden" name="list" value="<%=list%>">

    <a class="largest">Add Container</a>
    <br>
    <a class="error">&nbsp;</a>
    <%System.out.println("Got to just before AddContainerE.jsp");%>
    <%@include file="AddContainerE.jsp"%>

    <%System.out.println("Got to just after AddContainerE.jsp");
            System.out.println("contId: " + contId);%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="addContainer" value="  Add  "/>
                </td>
                <td>
                    <input type='button' onclick="window.location.assign('<%=response.encodeURL("./ViewContainers.jsp")%>')" name="back" value="Cancel" />
                    <!--input type="submit" name="cancel" value="Cancel" size="10"/-->
                </td>
            </tr>
        </thead>
    </table>
</form><%
    } else {//edit Container
        tmpHttpSessObj.setCurrentContainer(tmpAction);
        container = tmpHttpSessObj.getCurrentContainer();
        if (container != null) {
            selfId = tmpAction;
            //contId = container.getVisibleName();
            //contId = request.getParameter("contId");

            contId = container.getContainerID();
            containerName = container.getcontainerName();
            containerTypeID = container.getContainerType().getContainerTypeID().toString();
            shelf = container.getShelf();
            discarded = container.getDiscarded();
            shippedOut = "0";
            shippedDate = new Date();
            comments = container.getComments();
            isStock = container.getIsStock();
            containerAlias = container.getContainerAlias();
            lot = container.getLot();
            valid = container.getValid();
            initials = container.getInitials();
            dateOnContainer = container.getDateOnContainer();
            location = container.getLocation();
            barcode = container.getBarcode();
            comment = null;
            shipmentName = null;
            shippedToID = null;
            applyAll = false;

            freezerID = (container.getFreezer() != null) ? container.getFreezer().getFreezerID().toString() : null;
            List<ShipCont> shipCont = container.getShipCont();
            Shipment shipment = null;
            if (shipCont != null && shipCont.size()>0) {
                ShipCont lastItem = shipCont.get(shipCont.size()-1);
                if (lastItem.getShipment() != null) {
                    shipment = lastItem.getShipment();
                }
            }
            Date myDate = new Date();
            if (shipment != null) {
                shipmentName = shipment.getShipmentName();
                comment = shipment.getComments();
                shippedToID = (shipment.getShippedTo() != null) ? shipment.getShippedTo().getShippedToID().toString() : null;
                myDate = (shipment.getShipDate() != null) ? shipment.getShipDate() : new Date();
                shippedOut = shipment.getShipAction();
                shippedDate = shipment.getShipDate();
            }
            ayear = Util.getYear(myDate);
            amonth = Util.getMonth(myDate);
            aday = Util.getDay(myDate);
            myDate = (container.getDateOnContainer() != null) ? container.getDateOnContainer() : new Date();
            cyear = Util.getYear(myDate);
            cmonth = Util.getMonth(myDate);
            cday = Util.getDay(myDate);
%>

<form method="POST" name="fForm" id="addContainerForm"
      action="<%=response.encodeURL("./AddContainer.jsp")%>">
    <a class="largest">Edit Container</a>
    <br>
    <a class="error">&nbsp;</a>
    <input type="hidden" name="list" value="<%=list%>">
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <input type="hidden" name="contId" value="<%=contId%>"/>
    <%@include file="AddContainerE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateContainer" value="Update"/>
                </td>
                <td>
                    <input type='button' onclick="window.location.assign('<%=response.encodeURL("./ViewContainers.jsp")%>')" name="back" value="Cancel" />
                    <!--input type="submit" name="cancel" value="Cancel" size="10"/-->
                </td>
            </tr>
        </thead>
    </table>
</form>
<%}
    }
} else {//clone container
    if ((tmpAction = request.getParameter("cloneId")) != null) {
        tmpHttpSessObj.setCurrentContainer(tmpAction);
        container = tmpHttpSessObj.getCurrentContainer();
        if (container != null) {
            selfId = tmpAction;
            //contId = container.getContainerID();
            //containerName = container.getcontainerName();
            contId = null;
            containerName = null;
            containerTypeID = container.getContainerType().getContainerTypeID().toString();
            shelf = container.getShelf();
            discarded = container.getDiscarded();
            comments = container.getComments();
            isStock = container.getIsStock();
            containerAlias = container.getContainerAlias();
            lot = container.getLot();
            valid = container.getValid();
            initials = container.getInitials();
            dateOnContainer = container.getDateOnContainer();
            location = container.getLocation();
            barcode = container.getBarcode();
            comment = null;
            shipmentName = null;
            shippedToID = null;
            shippedOut = "0";
            applyAll = false;

            freezerID = (container.getFreezer() != null) ? container.getFreezer().getFreezerID().toString() : null;
            List<ShipCont> shipCont = container.getShipCont();
            Shipment shipment = null;
            if (shipCont != null) {
                Iterator iter = shipCont.iterator();
                ShipCont lastItem = null;
                while (iter.hasNext()) {
                    lastItem = (ShipCont) iter.next();
                }
                if (lastItem.getShipment() != null) {
                    shipment = lastItem.getShipment();
                }
            }
            Date myDate = new Date();
            if (shipment != null) {
                shippedToID = (shipment.getShippedTo() != null) ? shipment.getShippedTo().getShippedToID().toString() : null;
                myDate = (shipment.getShipDate() != null) ? shipment.getShipDate() : new Date();
                shippedOut = shipment.getShipAction();
                shippedDate = shipment.getShipDate();
            }
            ayear = Util.getYear(myDate);
            amonth = Util.getMonth(myDate);
            aday = Util.getDay(myDate);
            myDate = (container.getDateOnContainer() != null) ? container.getDateOnContainer() : new Date();
            cyear = Util.getYear(myDate);
            cmonth = Util.getMonth(myDate);
            cday = Util.getDay(myDate);
%>

<form method="POST" name="fForm" id="addContainerForm"
      action="<%=response.encodeURL("./AddContainer.jsp")%>">
    <input type="hidden" name="list" value="<%=list%>">
    <a class="largest">Clone Container</a>
    <br><br>
    <a >This tool will create a new container with all the
        same contents (wells/tubes) as the original.<br>
        Enter the details of the new container below.<br></a>
    <a class="error">&nbsp;</a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <%@include file="AddContainerE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="cloneContainer" value="Clone"/>
                </td>
                <td>
                    <input type='button' onclick="window.location.assign('<%=response.encodeURL("./ViewContainers.jsp")%>')" name="back" value="Cancel" />
                    <!--input type="submit" name="cancel" value="Cancel" size="10"/-->
                </td>
            </tr>
        </thead>
    </table>
</form>
<%}
            }
        }
%>
<%@include file="Footer.jsp"%>

