<%String lapasId = "ViewContainers";%>
<%@include file="Header.jsp"%>
<%
            response.setHeader("Cache-Control", "no-store");
            if (tmpHttpSessObj.getCurrentUser() == null) {//test for new session
                pageContext.forward(response.encodeURL("./LogIn.jsp"));
                return;
            }
            ViewContainerManager vfm = tmpHttpSessObj.getViewContainerManager();
            String tmpString = request.getParameter("sortCol");
            String shipId = request.getParameter("shipId");
            if (tmpString != null) {
                tmpHttpSessObj.setContainerSortCol(tmpString);
                vfm.setContainerFirst();
            } else if ((tmpString = request.getParameter("add")) != null) {
                vfm.changeContainerTotal(1);
            } else if ((tmpString = request.getParameter("del")) != null) {
                vfm.changeContainerTotal(-1);
            } else if ((tmpString = request.getParameter("previous")) != null) {
                vfm.setContainerPrevious();
            } else if ((tmpString = request.getParameter("next")) != null) {
                vfm.setContainerNext();
            } else if ((tmpString = request.getParameter("first")) != null) {
                vfm.setContainerFirst();
            } else if ((tmpString = request.getParameter("last")) != null) {
                vfm.setContainerLast();
            } else if ((tmpString = request.getParameter("cancelFilter")) != null) {
                tmpHttpSessObj.cancelFilter();
                tmpHttpSessObj.cancelSearch();
            }
            int step = vfm.getContainerStep();
            int first = vfm.getContainerCurrent();
            int total;
            List containerList;
            //int total = vfm.getContainerTotal();
            if (shipId != null && (!shipId.equals("")) && shipId.indexOf("null") < 0 && shipId.length() > 0) {
                if (!tmpHttpSessObj.isFilter()) {
                    containerList = tmpHttpSessObj.getAllContainers(first, shipId);
                    total = tmpHttpSessObj.getAllContainersCount(shipId);
                } else {
                    total = tmpHttpSessObj.getAllContainersCount();
                    containerList = tmpHttpSessObj.getAllContainers(first);
                }
            } else {
                total = tmpHttpSessObj.getAllContainersCount();
                containerList = tmpHttpSessObj.getAllContainers(first);
            }
            vfm.setContainerCount(total);

            if (request.getParameter("updateList") != null) {
                int success = 0;
                // list name entered by
                String newListName = request.getParameter("listName");

                // if list is a new list and name was entered, then start by making the new list
                if (newListName != null && !newListName.equals("")) {
                    success = tmpHttpSessObj.addShoppingList(newListName, tmpHttpSessObj.getCurrentUser());
                    if (success != 0) {%>
<br>
<a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(success)%></a>
    <%                  }
                        // load new list as current
                        if (success == 0) {
                            tmpHttpSessObj.setCurrentShoppingCartList(new ShoppingCartList(tmpHttpSessObj, newListName));
                        }
                    }

                    if (success == 0 && tmpHttpSessObj.getCurrentShoppingCartList() == null
                            && newListName != null && !newListName.equals("")) {
                        success = tmpHttpSessObj.addShoppingList(newListName, tmpHttpSessObj.getCurrentUser());
                        // load new list as current
                        tmpHttpSessObj.setCurrentShoppingCartList(new ShoppingCartList(tmpHttpSessObj, newListName));
                    }

                    if (success == 0 && tmpHttpSessObj.getCurrentShoppingCartList() != null) {
                        // update current list
                        tmpHttpSessObj.updateList(request);
                    }
                }

                // decides whether to show the DB ID
                int viewStart = 1;
                if (tmpHttpSessObj.isTechUser()) {
                    viewStart = 0;
                }


                // see if current list is in use and if list tools should be blocked
                String listUserName = null;
                if (tmpHttpSessObj.getCurrentShoppingCartList() != null
                        && tmpHttpSessObj.getCurrentShoppingCartList().currentListInUseBy() != null) {
                    listUserName = tmpHttpSessObj.getCurrentShoppingCartList().currentListInUseBy().getVisibleName();
                }
                MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
    %>
<script type='text/javascript' src='jquery.simplemodal-1.3.5.min.js'></script>
<script  type="text/javascript" language="javascript">
    function setFocus(){
        var x = document.getElementById("current")
        if (x) scrollTo(0,x.offsetTop);

        initSlidedHeader();
    }
    function goNav(){
        var x = document.getElementById("gotoNav").value;
        if(x == "new"){
            gotoEdit( -1);
        }if(x == "96to384" && listOkToUse()){
            window.location.assign(
            "<%=response.encodeURL("./Make384From96.jsp")%>");
        } else if(x == "txt"){
            window.location.assign(
            "<%=response.encodeURL("./Export.jsp?tableToExport=container")%>");
        }else if(x == "trim" && listOkToUse()){
            window.location.assign(
            "<%=response.encodeURL("./ListTrimTool.jsp")%>");
        }else if(x == "checkOut" && listOkToUse()){
            $("#checkOutComment").modal();
        }else if(x == "checkIn" && listOkToUse()){
            $("#checkInConfirm").modal();
        }else if(x == "shipOpt" && listOkToUse()){
            $("#shipOptDialog").modal();
        }

    }

    function gotoShipments(contId){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./ViewShipments.jsp")%>"+"?contId="+contId);
    }

    function listOkToUse(){
        var listUserName =document.getElementById('listUserName').value.valueOf()
        if(listUserName == null || listUserName == "" || listUserName == "null"){
            return true;
        }
        alert("Error: This list is currently being used by "+listUserName
            +". No operations except searching and viewing can be performed until this user is done. (If this user is you, try closing and reopening the list with the List Manager.) ");
        return false;
    }
    function gotoEdit( contId){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./AddContainer.jsp")%>"+"?contId="+contId);
    }
    function gotoLayout(contId){
        window.open('ViewLayout.jsp?containerID='+contId,'',
        'height=400,width=1050,scrollbars=yes,resizable=yes,menubar=no,toolbar=no,location=no,status=no');
    }
    function gotoClone( cloneId){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./CloneContainer.jsp")%>"+"?contId="+cloneId);
    }
    function gotoSDS( contId){
        window.open('MakeSDS.jsp?containerID='+contId,'',
        'height=300,width=400,scrollbars=yes,resizable=yes,menubar=no,toolbar=no,location=no,status=no');
    }
    function gotoBulkEdit( contId){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./EditBulkContents.jsp")%>"+"?contId="+contId);
    }
    function gotoContents( contId){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./ViewContainerContents.jsp")%>"+"?contId="+contId);
    }
    function gotoNext(){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./ViewContainers.jsp")%>"+"?next=0");
    }
    function gotoPrevious(){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./ViewContainers.jsp")%>"+"?previous=0");
    }
    function gotoFirst(){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./ViewContainers.jsp")%>"+"?first=0");
    }
    function gotoLast(){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./ViewContainers.jsp")%>"+"?last=0");
    }
    function gotoSorted( key){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./ViewContainers.jsp")%>"+"?sortCol="+key);
    }
    function eIn(image){
        image.src="./images/edit_b.gif";
    }
    function eOut(image){
        image.src="./images/edit_a.gif";
    }
    function vIn(image){
        image.src="./images/viewLayout_b.gif";
    }
    function vOut(image){
        image.src="./images/viewLayout_a.gif";
    }
    function cIn(image){
        image.src="./images/sheep_b.gif";
    }
    function cOut(image){
        image.src="./images/sheep_a.gif";
    }
    function ebIn(image){
        image.src="./images/editSamples_b.gif";
    }
    function ebOut(image){
        image.src="./images/editSamples_a.gif";
    }
    function ccIn(image){
        image.src="./images/samples_b.gif";
    }
    function ccOut(image){
        image.src="./images/samples_a.gif";
    }
    function sdsIn(image){
        image.src="./images/sds_b.gif";
    }
    function sdsOut(image){
        image.src="./images/sds_a.gif";
    }
    function shipIn(image){
        image.src="./images/shipment_b.png";
    }
    function shipOut(image){
        image.src="./images/shipment_a.png";
    }

    function saIn(image){
        image.src="./images/selectAll_b.png";
    }
    function saOut(image){
        image.src="./images/selectAll_a.png";
    }

    function daIn(image){
        image.src="./images/deselectAll_b.png";
    }
    function daOut(image){
        image.src="./images/deselectAll_a.png";
    }

    function checkAll(field)
    {
        for (i = 0; i < field.length; i++)
            field[i].checked = true ;
    }
    function uncheckAll(field)
    {
        for (i = 0; i < field.length; i++)
            field[i].checked = false ;
    }

    function promptForListName()
    {
        var listName = document.getElementById("listName")
        var name = prompt
        ("Please create a list by entering a descriptive name. \n\n\
NOTE: Only samples checked will be added to your list.");
        if (name != null && name!="") listName.value = name
        else if (name == null) alert("List not created.")
        else if (name == "") alert("Descriptive name required for list creation.\nList not created. ")

    }
</script>
<form name="fForm" method="POST"  onsubmit="return checkSubmitFlag()"
      action="<%= response.encodeURL("./ViewContainers.jsp")%>">
    <input type="hidden" name="listUserName" id="listUserName" value="<%=listUserName%>">

    <%-- Section of code that displays a modal dialog boxes --%>
    <%
                if (request.getParameter("checkOutList") != null) {
                    ArrayList errors = tmpHttpSessObj.checkOutContainersList(request);
                    if (!errors.isEmpty() && errors.size() != 0) {
                        String errorMessage = "<div style='margin-bottom:5px'>The following container(s) were already checked out:</div>";
                        String errorType = "Warning: Already Checked Out";
                        String errorString = Util.checkOutErrorHelper(errorMessage, errorType, errors, "");
    %>
    <script type="text/javascript">
        $(window).load(function(){
            $.modal("<%=errorString%>");
        });
    </script>
    <%
                        } else {
                            response.sendRedirect("./ViewContainers.jsp");
                            return;
                        }
                    } else if (request.getParameter("checkOutChecked") != null) {
                        ArrayList errors = tmpHttpSessObj.checkOutContainersChecked(request);
                        if (!errors.isEmpty() && errors.size() != 0) {
                            String errorMessage = "<div style='margin-bottom:5px'>The following container(s) were already checked out:</div>";
                            String errorType = "Warning: Already Checked Out";
                            String errorString = Util.checkOutErrorHelper(errorType, errorMessage, errors, "");
    %>
    <script type="text/javascript">
        $(window).load(function(){
            $.modal("<%=errorString%>");
        });
    </script>
    <%
                        } else {
                            response.sendRedirect("./ViewContainers.jsp");
                            return;
                        }
                    } else if ((request.getParameter("checkInList")) != null) {
                        ArrayList errors = tmpHttpSessObj.checkInContainersList();
                        if (!errors.isEmpty() && errors.size() != 0) {
                            String errorType = "Error: Insufficient Permissions";
                            String errorMessage = "<div style='margin-bottom:5px'>You do not have enough permissions to check-in the following containers:</div>";
                            String additionalComments = "<br>Please get an Admin or the user that checked it out to check it back in.";
                            String errorString = Util.checkOutErrorHelper(errorType, errorMessage, errors, additionalComments);
    %>
    <script type="text/javascript">
        $(window).load(function(){
            $.modal("<%=errorString%>");
        });
    </script>
    <%
                        } else {
                            response.sendRedirect("./ViewContainers.jsp");
                            return;
                        }
                    } else if (request.getParameter("checkInChecked") != null) {
                        ArrayList errors = tmpHttpSessObj.checkInContainersChecked(request);
                        if (!errors.isEmpty() && errors.size() != 0) {
                            String errorType = "Error: Insufficient Permissions";
                            String errorMessage = "<div style='margin-bottom:5px'>You do not have enough permissions to check-in the following containers:</div>";
                            String additionalComments = "<br>Please get an Admin or the user that checked it out to check it back in.";
                            String errorString = Util.checkOutErrorHelper(errorType, errorMessage, errors, additionalComments);
    %>
    <script type="text/javascript">
        $(window).load(function(){
            $.modal("<%=errorString%>");
        });
    </script>
    <%
                        } else {
                            response.sendRedirect("./ViewContainers.jsp");
                            return;
                        }
                    } else if (request.getParameter("shipChecked") != null) {
                        ArrayList errors = tmpHttpSessObj.addShippingInfoChecked(request);
                        if (!errors.isEmpty() && errors.size() != 0) {
                            String errorType = "Error";
                            String errorMessage = "<div style='margin-bottom:5px'>The following errors occured:</div>";
                            String additionalComments = "<br>Please fix the errors, and try again.";
                            String errorString = Util.checkOutErrorHelper(errorType, errorMessage, errors, additionalComments);
    %>
    <script type="text/javascript">
        $(window).load(function(){
            $.modal("<%=errorString%>");
        });
    </script>
    <%
                        } else {
                            response.sendRedirect("./ViewContainers.jsp");
                            return;
                        }
                    } else if (request.getParameter("shipList") != null) {
                        ArrayList errors = tmpHttpSessObj.addShippingInfoList(request);
                        if (!errors.isEmpty() && errors.size() != 0) {
                            String errorType = "Error";
                            String errorMessage = "<div style='margin-bottom:5px'>The following errors occured:</div>";
                            String additionalComments = "<br>Please fix the errors, and try again.";
                            String errorString = Util.checkOutErrorHelper(errorType, errorMessage, errors, additionalComments);
    %>
    <script type="text/javascript">
        $(window).load(function(){
            $.modal("<%=errorString%>");
        });
    </script>
    <%
                        } else {
                            response.sendRedirect("./ViewContainers.jsp");
                            return;
                        }
                    }
    %>
    <table class="view_table" >
        <thead>
            <tr>
                <td align="left">
                    <table class="sample">
                        <tr>
                            <td><a class="largest">Containers --</a>
                                <a class="larger"><%=vfm.getContainerControlString()%></a></td>
                                <%if (tmpHttpSessObj.isFilter() || tmpHttpSessObj.isSearch()) {
                                %><td align="left">
                                <input type="submit" name="cancelFilter" value="Cancel Search"/>
                            </td><%}%>
                        </tr>
                    </table>
                </td>
                <td align="right" valign="middle">
                    <select id="gotoNav" >
                        <option selected value="blank">What would you like to do?</option>
                        <option value="new">Create a new container</option>
                        <option value="txt">Export all items to tab-delimited txt file</option>
                        <option> -- List Tools --</option>
                        <option value="trim">Trim List</option>
                        <option value="96to384">Create 384 well plates from 96 well plates</option>
                        <option value="checkOut">Check-Out Containers</option>
                        <option value="checkIn">Check-In Containers</option>
                        <option value="shipOpt">Shipping Options</option>
                    </select>
                    <button type="button" class="buttonSmall" onclick="goNav()">Go</button>

                </td></tr>
        </thead>
    </table>

    <!--satura tabula -->
    <table class="view_table" style="border-width: 0px; border-spacing: 1px; border-color: #ffffff;" >
        <thead>
            <tr bgcolor="#b3cfff">
                <td width="1%"><a href='javascript:jumpFocus("bottomLine")'><img border="0" src="./images/down_d_10_b.gif" title="bottom of page" alt=""></a></td>
                <td align="center">
                    <table style="background-color:#b3cfff; border-width: 0px; border-spacing: 0px; border-color: #ffffff;">
                        <tr><td colspan="2" >

                                <%--------------------------------------%>
                                <%--Modal dialog box setup starts here--%>
                                <%--------------------------------------%>
                                <%---------Checkout Modal Box-----------%>
                                <div id="checkOutComment" style="display:none">
                                    <div style="width: 100%; text-align:right">
                                        <a href='#' title='Close' class='simplemodal-close' style='font-size:23px;'><b>x</b></a>
                                    </div>
                                    <h1 style=" margin-top: 0; margin-bottom: 0">Check-Out</h1>
                                    <div id="checkOutOptions">
                                        <i>The following fields are optional.</i>
                                        <br>
                                        Name: <input type="text" name="checkName" style="width:179px">
                                        <br>
                                        Comments:
                                        <br><textarea name="checkComment" cols="25" rows="6" style="margin-bottom:5px"></textarea>
                                    </div>
                                    What items would you like to check-out?
                                    <br>
                                    <%if (tmpHttpSessObj.getCurrentShoppingCartList() != null) {%>
                                    <input type="submit" name="checkOutList" value="List" />
                                    <%} else {%>
                                    <input type="button" value="List" disabled/>
                                    <%}%>
                                    <input type="submit" name="checkOutChecked" value="Checked Items" />
                                    <input type="button" class="simplemodal-close" value="Cancel"/>
                                </div>

                                <%----------Checkin Modal Box-----------%>
                                <div id="checkInConfirm" style="display:none">
                                    <div style="width: 100%; text-align:right">
                                        <a href='#' title='Close' class='simplemodal-close' style='font-size:23px;'><b>x</b></a>
                                    </div>
                                    <h1 style="margin-top: 0; margin-bottom: 0">Check-In</h1>
                                    What items would you like to check-in?
                                    <br>
                                    <%if (tmpHttpSessObj.getCurrentShoppingCartList() != null) {%>
                                    <input type="submit" name="checkInList" value="List"/>
                                    <%} else {%>
                                    <input type="button" value="List" disabled/>
                                    <%}%>
                                    <input type="submit" name="checkInChecked" value="Checked Items"/>
                                    <input type="button" class="simplemodal-close" value="Cancel"/>
                                </div>

                                <%-------------Shipping Modal Box--------------%>
                                <div id="shipOptDialog" style="display:none">
                                    <div style="width: 100%; text-align:right">
                                        <a href='#' title='Close' class='simplemodal-close' style='font-size:23px;'><b>x</b></a>
                                    </div>
                                    <h1 style="margin-top: 0; margin-bottom: 0">Shipping</h1>
                                    <table style="background-color:white">
                                        <tr>
                                            <td><%--shippedOut:--%>
                                                Shipping Status</td>
                                            <td>
                                                <select name="shippedOut" id="shippedOut">
                                                    <option value="1" selected>Shipped</option>
                                                    <option value="2" >Returned</option>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><%--shipmentName:--%>
                                                Shipment Name</td>
                                            <td>
                                                <input type="text" name="shipmentName"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><%--shipped date(yyyy/mm/dd):--%>
                                                <%=mdm.getContainerLongName(Fieldname.SHIPPEDDATE)%></td>
                                            <td>
                                                <input type="text" name="ayear" id="ayear" maxlength="4" size="4"
                                                       <%=tmpHttpSessObj.getFieldId("ayear")%>
                                                       <%Calendar cal = Calendar.getInstance();%>
                                                       value="<%= (cal).get(Calendar.YEAR)%>"/><b>-</b>
                                                <input type="text" name="amonth" id="amonth" maxlength="2" size="2"
                                                       <%=tmpHttpSessObj.getFieldId("amonth")%>
                                                       value="<%=(cal).get(Calendar.MONTH) + 1%>"/><b>-</b>
                                                <input type="text" name="aday" id="aday" maxlength="2" size="2"
                                                       <%=tmpHttpSessObj.getFieldId("aday")%>
                                                       value="<%=(cal).get(Calendar.DAY_OF_MONTH)%>"/> (YYYY MM DD)
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><%--shippedToID:--%>
                                                <%=mdm.getContainerLongName(Fieldname.SHIPPEDTO)%></td>
                                            <td>
                                                <select name="shippedToID" id="shippedToID">
                                                    <%=tmpHttpSessObj.getObjectPrompterUniqueField(ShippedTo.class, null, "description", false)%>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>            Comments:</td>
                                            <td>
                                                <textarea name="checkComment" cols="25" rows="6" style="margin-bottom:5px"></textarea>
                                            </td>
                                        </tr>
                                    </table>
                                    <br>
                                    <%if (tmpHttpSessObj.getCurrentShoppingCartList() != null) {%>
                                    <input type="submit" name="shipList" value="List"/>
                                    <%} else {%>
                                    <input type="button" value="List" disabled/>
                                    <%}%>
                                    <input type="submit" name="shipChecked" value="Checked Items"/>
                                    <input type="button" class="simplemodal-close" value="Cancel"/>
                                </div>
                                <%--------------------------------------%>
                                <%------------End Modal Box Setup-------%>
                                <%--------------------------------------%>

                                <%if (tmpHttpSessObj.getCurrentShoppingCartList() == null) {
                                                // if user hasn't made their shopping list yet, make them name it
%>
                                <input type="hidden" name="listName" id="listName">
                                <input type="submit" onclick="promptForListName()" name="updateList" value="Create List" style="font-size:8pt; background-color:LightBlue"/>
                                <%                } else {%>
                                <input type="submit" name="updateList" value="Update List" style="font-size:8pt; background-color:LightBlue"/>
                                <%}%>
                            </td></tr>
                        <tr><td align="right">
                                <a onclick="checkAll(document.fForm.containerListChecked)">
                                    <img border="0" src="./images/selectAll_a.png" onmouseover="saIn(this)"
                                         onmouseout="saOut(this)" title="Select All" name="updateList" alt="">
                                </a></td><td align="left">
                                <a onclick="uncheckAll(document.fForm.containerListChecked)">
                                    <img border="0" src="./images/deselectAll_a.png" onmouseover="daIn(this)"
                                         onmouseout="daOut(this)" title="Deselect All" name="updateList" alt="">
                                </a>
                            </td></tr></table>
                </td>

                <%
                            // getContainerTypeKeyArray() returns column header names
                            String[] keyArray = mdm.getContainerKeyArray();
                            int colCount = keyArray.length;
                            String cellClassName;

                            for (int i = viewStart; i < colCount; i++) {
                %><td class="headerEven"><%
                                                if (mdm.getContainerSortable(keyArray[i])) {
                                                    if (tmpHttpSessObj.getContainerSortId(keyArray[i]).equals("normal")) {%>
                    <a class="<%=tmpHttpSessObj.getContainerSortId(keyArray[i])%>"
                       href='javascript:gotoSorted("<%=keyArray[i]%>")'>
                        <%=mdm.getContainerLongName(keyArray[i])%>
                        <img border="0" src="./images/sort_sm_none.gif" title="Sort" alt=""></a>
                        <%}
                                                                            if (tmpHttpSessObj.getContainerSortId(keyArray[i]).equals("normalo")) {%>
                    <a class="<%=tmpHttpSessObj.getContainerSortId(keyArray[i])%>"
                       href='javascript:gotoSorted("<%=keyArray[i]%>")'>
                        <%=mdm.getContainerLongName(keyArray[i])%>
                        <img border="0" src="./images/sort_sm_down.gif" title="Sort" alt=""></a>
                        <%}
                                                                            if (tmpHttpSessObj.getContainerSortId(keyArray[i]).equals("normalu")) {%>
                    <a class="<%=tmpHttpSessObj.getContainerSortId(keyArray[i])%>"
                       href='javascript:gotoSorted("<%=keyArray[i]%>")'>
                        <%=mdm.getContainerLongName(keyArray[i])%>
                        <img border="0" src="./images/sort_sm_up.gif" title="Sort" alt=""></a>
                        <%}%>

                    <%
                                                                    } else {
                    %><b><%=mdm.getContainerLongName(keyArray[i])%></b><%
                                                    }
                    %></td><%
                                }
                    %>
                <td>
                </td>
                <td>
                </td>
                <td>
                </td>
                <td>
                </td>
                <td>
                </td>
                <td>
                </td>
                <td>
                </td>
            </tr>
        </thead>
        <tbody><%
                    // actual data rows
                    if (containerList != null) {
                        byte[] valueIndexArray = mdm.getContainerIndexArray();
                        Iterator iter = containerList.iterator();
                        String currStripe;
                        Long currId = tmpHttpSessObj.getCurrentContainerId();
                        boolean stripOn = true;
                        int lineNr = 0;
                        String currName = "";
                        String lastName = "";
                        String inList = "";
                        while (iter.hasNext()) {
                            Container currContainer = (Container) iter.next();
                            String[] var = currContainer.getValueArrayReadable(valueIndexArray, colCount);
                            currName = currContainer.getcontainerName();
                            if (!currName.equals(lastName)) {
                                stripOn = !stripOn;
                            }
                            lastName = currName;
                            if (tmpHttpSessObj.inContainersList(currContainer.getId().toString())) {
                                currStripe = (stripOn) ? "listStripeOn" : "listStripeOff";
                            } else {
                                currStripe = (stripOn) ? "son" : "soff";
                            }
                            lineNr++;
            %><tr class="<%=currStripe%>"><%

                %><td id ="<%=currContainer.getId()%>" width="1%">
                    <a href="javascript:showSlidedHeader('<%=currContainer.getId()%>')">
                        <img id ="headerImg_<%=currContainer.getId()%>" border='0' src='./images/up_10_b.gif' title="show header before line <%=lineNr%>" alt="">
                    </a>
                </td>
                <%if (tmpHttpSessObj.inContainersList(currContainer.getId().toString())) {
                                                inList = "checked";
                                            } else {
                                                inList = "";
                                            }%>
                <td align="center">
                    <input type="checkbox" <%=inList%> name="containerListChecked" value="<%=currContainer.getId()%>">
                    <%if (inList.equals("checked")) {
                                                    // value so we can know if container was UNticked and therefor should be removed from list
%>
                    <input type="hidden" name="containerListWereChecked" value="<%=currContainer.getId()%>">

                    <%}%>
                </td>
                <%

                                            for (int i = viewStart; i < colCount; i++) {
                                                // actual field values written here:
                                                // want null values to appear bl        ank
                %><td><%=(var[i] != null) ? var[i] : ""%></td><%
                            }
                %><td>
                    <a onclick="gotoLayout('<%=currContainer.getId()%>')">
                        <img border="0" src="./images/viewLayout_a.gif" onmouseover="vIn(this)"
                             onmouseout="vOut(this)" title="View Layout" alt="">
                    </a>
                </td>
                <td>
                    <% // only can get SDS for a 384-well plate
                                                if (currContainer.getContainerType().getDescription().equals("384 Well Plate")) {%>
                    <a onclick="gotoSDS('<%=currContainer.getId()%>')">
                        <img border="0" src="./images/sds_a.gif" onmouseover="sdsIn(this)"
                             onmouseout="sdsOut(this)"
                             title="Download SDS import file for this plate."
                             alt="">
                    </a>
                    <%}%>
                </td>
                <td><a onclick="gotoEdit('<%=currContainer.getId()%>')">
                        <img border="0" src="./images/edit_a.gif" onmouseover="eIn(this)"
                             onmouseout="eOut(this)" title="Edit" alt="">
                    </a>
                </td>
                <td>
                    <% // only can clone 96-well and 384-well plates
                                                if (currContainer.getContainerType().getDescription().equals("96 Well Plate")
                                                        || currContainer.getContainerType().getDescription().equals("384 Well Plate")) {%>
                    <a onclick="gotoClone('<%=currContainer.getId()%>')">
                        <img border="0" src="./images/sheep_a.gif" onmouseover="cIn(this)"
                             onmouseout="cOut(this)" title="Clone" alt="">
                    </a>
                    <%}%>
                </td>
                <td><a onclick="gotoContents('<%=currContainer.getId()%>')">
                        <img border="0" src="./images/samples_a.gif" onmouseover="ccIn(this)"
                             onmouseout="ccOut(this)" title="View Samples" alt="">
                    </a></td>
                <td><a onclick="gotoBulkEdit('<%=currContainer.getId()%>')">
                        <img border="0" src="./images/editSamples_a.gif" onmouseover="ebIn(this)"
                             onmouseout="ebOut(this)" title="Edit All Samples" alt="">
                    </a></td>
                <td><a onclick="gotoShipments('<%=currContainer.getId()%>')">
                        <img border="0" src="./images/shipment_a.png" onmouseover="shipIn(this)"
                             onmouseout="shipOut(this)" title="View All Shipments" alt="">
                    </a></td>
            </tr><%
                            }
                        }
            %><tr id='slidedHeader' bgcolor="#b3cfff" class="slidedRow">
                <td width="1%">
                    <a href='javascript:jumpFocus("bottomLine")'><img border="0" src="./images/down_d_10_b.gif" title="bottom of page" alt=""></a>
                </td><td>&nbsp;</td><%

                            for (int i = viewStart; i < colCount; i++) {
                %><td class="headerEven"><%
                                                if (mdm.getContainerSortable(keyArray[i])) {
                    %><a class="<%=tmpHttpSessObj.getContainerSortId(keyArray[i])%>"
                       href='javascript:gotoSorted("<%=keyArray[i]%>")'>
                        <%--HREF="<%=response.encodeURL("./ViewContainers.jsp")+"?sortCol="+keyArray[i]+"&perstotal="+total%>"
                        --%><%=mdm.getContainerLongName(keyArray[i])%></a><%
                                                                        } else {
                        %><b><%=mdm.getContainerLongName(keyArray[i])%></b><%
                                                        }
                    %></td><%
                                }
                    %><td>&nbsp;</td><td>&nbsp;</td>
                <td>
                    <a href="javascript:scrollTo(0,0)"><img border="0" src="./images/up_d_10_b.gif" title="top of page" alt=""></a>
                </td>
            </tr>
        </tbody>
    </table>
    <%if (total > 0) {%>
    &nbsp;
    <a href="javascript:scrollTo(0,0)"><img border="0" src="./images/up_d_10_b.gif" title="top of page" alt=""></a>
    &nbsp;&nbsp;
    <%=vfm.getContainerControlString()%>
    <%}%>
</form>
<%@include file="Footer.jsp"%>



