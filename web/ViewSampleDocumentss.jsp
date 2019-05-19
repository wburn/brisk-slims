<%String lapasId = "ViewSampleDocuments";%>
<%@include file="Header.jsp"%>
<%
            if (tmpHttpSessObj.getCurrentUser() == null) {//test for new session
                pageContext.forward(response.encodeURL("./LogIn.jsp"));
                return;
            }
            ViewSampleDocumentsManager vfm = tmpHttpSessObj.getViewSampleDocumentsManager();
            String tmpString = request.getParameter("sortCol");
            String sampId = request.getParameter("sampId");
            if (tmpString != null) {
                tmpHttpSessObj.setSampleDocumentsSortCol(tmpString);
                vfm.setSampleDocumentsFirst();
            } else if ((tmpString = request.getParameter("add")) != null) {
                vfm.changeSampleDocumentsTotal(1);
            } else if ((tmpString = request.getParameter("del")) != null) {
                vfm.changeSampleDocumentsTotal(-1);
            } else if ((tmpString = request.getParameter("previous")) != null) {
                vfm.setSampleDocumentsPrevious();
            } else if ((tmpString = request.getParameter("next")) != null) {
                vfm.setSampleDocumentsNext();
            } else if ((tmpString = request.getParameter("first")) != null) {
                vfm.setSampleDocumentsFirst();
            } else if ((tmpString = request.getParameter("last")) != null) {
                vfm.setSampleDocumentsLast();
            } else if ((tmpString = request.getParameter("cancelFilter")) != null) {
                tmpHttpSessObj.cancelFilter();
                tmpHttpSessObj.cancelSearch();
            }
            // for next/prev page of data navigation
            int step = vfm.getSampleDocumentsStep();
            int first = vfm.getSampleDocumentsCurrent();
            List sampleDocumentsList;
            int total;
            if (sampId != null && (!sampId.equals("")) && sampId.indexOf("null") < 0 && sampId.length() > 0) {
                if (!tmpHttpSessObj.isFilter()){
                    sampleDocumentsList = tmpHttpSessObj.getAllSampleDocuments(first, sampId);
                    total = tmpHttpSessObj.getAllSampleDocumentsCount(sampId);
                }
                else {
                    total = tmpHttpSessObj.getAllSampleDocumentsCount();
                    sampleDocumentsList = tmpHttpSessObj.getAllSampleDocuments(first);
                }
            } else {
                total = tmpHttpSessObj.getAllSampleDocumentsCount();
                sampleDocumentsList = tmpHttpSessObj.getAllSampleDocuments(first);
            }
            vfm.setSampleDocumentsCount(total);






            if (request.getParameter("updateList") != null) {
                int success = 0;
                // list name entered by
                String newListName = request.getParameter("listName");
                // if list is a new list and name was entered, then start by making the new list

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
                            System.out.println("newListName=" + newListName + success);
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
<script  type="text/javascript" language="javascript">
    function setFocus(){
        var x = document.getElementById("current")
        if (x) scrollTo(0,x.offsetTop);

        initSlidedHeader();
    }
    function goNav(){
        var x = document.getElementById("gotoNav").value;
        if(x == "new"){
            gotoNew(<%=sampId%>);
        } else if(x == "txt"){
            window.location.assign(
            "<%=response.encodeURL("./Export.jsp?tableToExport=sampleDocuments&sampId="+sampId)%>");
        }else if(x == "sampleSelector" && listOkToUse()){
            window.location.assign(
            "<%=response.encodeURL("./SampleSelectorCriteriaChoice.jsp")%>");
        }else if(x == "trim" && listOkToUse()){
            window.location.assign(
            "<%=response.encodeURL("./ListTrimTool.jsp")%>");
        }
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
    function gotoDownload( sampDocId){
        if (checkSubmitFlag())
        {
            submitFlag--;
            window.location.assign("<%=response.encodeURL("./dlSampDoc")%>"+"?sampDocId="+sampDocId);
        }
    }

function gotoNew( sampId){
if (sampId == null){
    sampId = -1;
}
if (checkSubmitFlag()) window.location.assign(
"<%=response.encodeURL("./AddSampleDocuments.jsp")%>"+"?sampId="+sampId+"&sampDocId=-1");
}

    function gotoEdit( sampDocId){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./AddSampleDocuments.jsp")%>"+"?sampDocId="+sampDocId);
    }

    function gotoDBSamples( subjId){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./ViewSamples.jsp")%>"+"?subjId="+subjId);
    }
    function gotoContents( subjId){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./ViewContainerContents.jsp")%>"+"?subjId="+subjId);
    }

    function gotoNext(){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./ViewSampleDocumentss.jsp")%>"+"?next=0");
    }
    function gotoPrevious(){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./ViewSampleDocumentss.jsp")%>"+"?previous=0");
    }
    function gotoFirst(){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./ViewSampleDocumentss.jsp")%>"+"?first=0");
    }
    function gotoLast(){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./ViewSampleDocumentss.jsp")%>"+"?last=0");
    }
    function gotoSorted( key){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./ViewSampleDocumentss.jsp")%>"+"?sortCol="+key);
    }
    function eIn(image){
        image.src="./images/edit_b.gif";
    }
    function eOut(image){
        image.src="./images/edit_a.gif";
    }
    function dlIn(image){
        image.src="./images/dl_b.gif";
    }
    function dlOut(image){
        image.src="./images/dl_a.gif";
    }
    function sIn(image){
        image.src="./images/samples_b.gif";
    }
    function sOut(image){
        image.src="./images/samples_a.gif";
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
      action="<%= response.encodeURL("./ViewSampleDocumentss.jsp")%>"/>
<input type="hidden" name="listUserName" id="listUserName" value="<%=listUserName%>"/>
<!--galvas tabula -->
<table class="view_table" >
    <thead>
        <tr>
            <td align="left">
                <table class="sample">
                    <tr>
                        <td><a class="largest">SampleDocuments --</a>
                            <a class="larger"><%=vfm.getSampleDocumentsControlString()%></a></td>
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
                    <option value="new">Attach a new file</option>
                    <option value="txt">Export all items to tab-delimited txt file</option>
                    <option>--List Tools--</option>
                    <option value="trim">Trim List</option>
                    <option value="sampleSelector">Select samples for sampleDocumentss in list</option>
                </select>
                <button type="button" class="buttonSmall" onclick="goNav('<%=request.getParameter("subjId")%>')">Go</button>

            </td></tr>
    </thead>
</table>

<!--satura tabula -->
<table class="view_table" style="border-width: 0px; border-spacing: 1px; border-color: #ffffff;" >
    <thead>
        <tr bgcolor="#b3cfff">
            <td width="1%"><a href='javascript:jumpFocus("bottomLine")'><img border="0" src="./images/down_d_10_b.gif" title="bottom of page" alt=""/></a></td>


            <%

                        String[] keyArray = mdm.getSampleDocumentsKeyArray();
                        int colCount = keyArray.length;
                        String cellClassName;

                        for (int i = viewStart; i < colCount; i++) {
            %><td class="headerEven"><%
                                        if (mdm.getSampleDocumentsSortable(keyArray[i])) {
                                            if (tmpHttpSessObj.getSampleDocumentsSortId(keyArray[i]).equals("normal")) {%>
                <a class="<%=tmpHttpSessObj.getSampleDocumentsSortId(keyArray[i])%>"
                   href='javascript:gotoSorted("<%=keyArray[i]%>")'>
                    <%=mdm.getSampleDocumentsLongName(keyArray[i])%>
                    <img border="0" src="./images/sort_sm_none.gif" title="Sort" alt=""/></a>
                    <%}
                                                                if (tmpHttpSessObj.getSampleDocumentsSortId(keyArray[i]).equals("normalo")) {%>
                <a class="<%=tmpHttpSessObj.getSampleDocumentsSortId(keyArray[i])%>"
                   href='javascript:gotoSorted("<%=keyArray[i]%>")'>
                    <%=mdm.getSampleDocumentsLongName(keyArray[i])%>
                    <img border="0" src="./images/sort_sm_down.gif" title="Sort" alt=""/></a>
                    <%}
                                                                if (tmpHttpSessObj.getSampleDocumentsSortId(keyArray[i]).equals("normalu")) {%>
                <a class="<%=tmpHttpSessObj.getSampleDocumentsSortId(keyArray[i])%>"
                   href='javascript:gotoSorted("<%=keyArray[i]%>")'>
                    <%=mdm.getSampleDocumentsLongName(keyArray[i])%>
                    <img border="0" src="./images/sort_sm_up.gif" title="Sort" alt=""/></a>
                    <%}%>

                <%
                                                        } else {
                %><b><%=mdm.getSampleDocumentsLongName(keyArray[i])%></b><%
                                            }
                %></td><%
                            }
                %>

            <td style="width:10em; font-size: 9pt; text-align:center">
                <b>Download</b>
            </td>
            <td>&nbsp;</td>
        </tr>
    </thead>
    <tbody><%
                // actual data rows
                if (sampleDocumentsList != null) {
                    byte[] valueIndexArray = mdm.getSampleDocumentsIndexArray();
                    Iterator iter = sampleDocumentsList.iterator();
                    String currStripe;
                    String currId = tmpHttpSessObj.getCurrentSampleDocumentsId();
                    boolean stripOn = true;
                    String inList = "";
                    String currName = "";
                    String lastName = "";
                    int lineNr = 0;

                    while (iter.hasNext()) {
                        SampleDocuments currSampleDocuments = (SampleDocuments) iter.next();
                        String[] var = currSampleDocuments.getValueArrayReadable(valueIndexArray, colCount);
                        currName = currSampleDocuments.getSampleDocumentID();

                        if (!currName.equals(lastName)) {
                            stripOn = !stripOn;
                        }
                        lastName = currName;
                        if (tmpHttpSessObj.inSampleDocumentsList(currSampleDocuments.getId().toString())) {
                            currStripe = (stripOn) ? "listStripeOn" : "listStripeOff";
                        } else {
                            currStripe = (stripOn) ? "son" : "soff";
                        }
                        lineNr++;
        %><tr class="<%=currStripe%>"><%

            %><td id ="<%=currSampleDocuments.getId()%>" width="1%">
                <a href="javascript:showSlidedHeader('<%=currSampleDocuments.getId()%>')">
                    <img id ="headerImg_<%=currSampleDocuments.getId()%>" border='0' src='./images/up_10_b.gif' title="show header before line <%=lineNr%>" alt=""/>
                </a>
            </td>
            <%if (tmpHttpSessObj.inSampleDocumentsList(currSampleDocuments.getId().toString())) {
                                        inList = "checked";
                                    } else {
                                        inList = "";
                                    }%>

            <%if (inList.equals("checked")) {
                                        // value so we can know if container was UNticked and therefor should be removed from list
            %>
    <input type="hidden" name="sampleDocumentsListWereChecked" value="<%=currSampleDocuments.getId()%>"/>
    <%}%>

    <%

                            for (int i = viewStart; i < colCount; i++) {
    %><td><%=var[i]%></td><%
                            }
    %>
    <td><a onclick="gotoDownload('<%=currSampleDocuments.getId()%>')">
            <img style="display: block; margin-left: auto; margin-right: auto" border="0" src="./images/dl_a.gif" onmouseover="dlIn(this)"
                 onmouseout="dlOut(this)" title="Download Document" alt=""/>
        </a></td>
        <td><a onclick="gotoEdit('<%=currSampleDocuments.getId()%>')">
                        <img border="0" src="./images/edit_a.gif" onmouseover="eIn(this)"
                             onmouseout="eOut(this)" title="Edit" alt=""/>
                </a></td>
</tr><%
                }
            }
%><tr id='slidedHeader' bgcolor="#b3cfff" class="slidedRow">
    <td width="1%">
        <a href='javascript:jumpFocus("bottomLine")'><img border="0" src="./images/down_d_10_b.gif" title="bottom of page" alt=""/></a>
    </td><%
                for (int i = viewStart; i < colCount; i++) {%>
    <td class="headerEven">
        <%if (mdm.getSampleDocumentsSortable(keyArray[i])) {%>
        <a class="<%=tmpHttpSessObj.getSampleDocumentsSortId(keyArray[i])%>"
           href='javascript:gotoSorted("<%=keyArray[i]%>")'>
            <%--HREF="<%=response.encodeURL("./ViewSampleDocuments.jsp")+"?sortCol="+keyArray[i]+"&perstotal="+total%>"
            --%><%=mdm.getSampleDocumentsLongName(keyArray[i])%></a><%
            } else {
            %><b><%=mdm.getSampleDocumentsLongName(keyArray[i])%></b><%
                                }
        %></td><%
                    }
        %>
    <td style="width:10em; font-size: 9pt; text-align:center">
        <b>Download</b>
    </td>
    <td>&nbsp;</td>
    <td>
        <a href="javascript:scrollTo(0,0)"><img border="0" src="./images/up_d_10_b.gif" title="top of page" alt=""/></a>
    </td>
</tr>
</tbody>
</table>
<%if (total > 0) {%>
&nbsp;
<a href="javascript:scrollTo(0,0)"><img border="0" src="./images/up_d_10_b.gif" title="top of page" alt=""/></a>
&nbsp;&nbsp;
<%=vfm.getSampleDocumentsControlString()%>
<%}%>
</form>
<%@include file="Footer.jsp"%>



