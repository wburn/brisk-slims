<%String lapasId = "ViewSamples";%>
<%@include file="Header.jsp"%>

<%
        if (tmpHttpSessObj.getCurrentUser() == null) {//test for new session
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }

        ViewSampleManager vfm = tmpHttpSessObj.getViewSampleManager();
        String tmpString = request.getParameter("sortCol");
        if (tmpString != null) {
            tmpHttpSessObj.setSampleSortCol(tmpString);
            vfm.setSampleFirst();
        } else if ((tmpString = request.getParameter("add")) != null) {
            vfm.changeSampleTotal(1);
        } else if ((tmpString = request.getParameter("del")) != null) {
            vfm.changeSampleTotal(-1);
        } else if ((tmpString = request.getParameter("previous")) != null) {
            vfm.setSamplePrevious();
        } else if ((tmpString = request.getParameter("next")) != null) {
            vfm.setSampleNext();
        } else if ((tmpString = request.getParameter("first")) != null) {
            vfm.setSampleFirst();
        } else if ((tmpString = request.getParameter("last")) != null) {
            vfm.setSampleLast();
        } else if ((tmpString = request.getParameter("cancelFilter")) != null) {
            tmpHttpSessObj.cancelFilter();
            tmpHttpSessObj.cancelSearch();
        } else if ((tmpString = request.getParameter("subjId")) != null) {
            tmpHttpSessObj.cancelFilter();
            tmpHttpSessObj.cancelSearch();
            tmpHttpSessObj.setCurrentSubject(tmpString);
            vfm.setSubjectIdDown(tmpHttpSessObj.getCurrentSubjectId());
        }
        String subjectId = vfm.getSubjectId();
        int step = vfm.getSampleStep();
        int first = vfm.getSampleCurrent();

        int total = tmpHttpSessObj.getAllSamplesCount();

//int total = vfm.getSampleTotal();
        List sampleList = tmpHttpSessObj.getAllSamples(subjectId, first);
        vfm.setSubjectIdDown(null);
        vfm.setSampleCount(sampleList.size());

        if (request.getParameter("updateList") != null) {
            tmpHttpSessObj.updateList(request);
        }

        // decides whether to show the DB ID
        int viewStart = 1;
        if (tmpHttpSessObj.isTechUser()) {
            viewStart = 0;
        }


  // see if current list is in use and if list tools should be blocked
  String listUserName = null;
  if( tmpHttpSessObj.getCurrentShoppingCartList() != null &&
          tmpHttpSessObj.getCurrentShoppingCartList().currentListInUseBy()!=null){
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
    function goNav( subjID){
    var x = document.getElementById("gotoNav").value;
    if(x == "new"){
        gotoEdit( -1);
    } else if(x == "view"){
        gotoCustomize();
    } else if(x == "txt"){
        window.location.assign(
            "<%=response.encodeURL("./Export.jsp?tableToExport=sample&subjectID")%>"+subjID);
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
    function gotoEdit( sampId){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./AddSample.jsp")%>"+"?sampId="+sampId);
    }

    function gotoList(){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./ViewSamplesList.jsp")%>"+"?samplesListID=-1");
    }

    function gotoNext(){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./ViewSamples.jsp")%>"+"?next=0");
    }
    function gotoPrevious(){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./ViewSamples.jsp")%>"+"?previous=0");
    }
    function gotoFirst(){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./ViewSamples.jsp")%>"+"?first=0");
    }
    function gotoLast(){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./ViewSamples.jsp")%>"+"?last=0");
    }
    function gotoSorted( key){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./ViewSamples.jsp")%>"+"?sortCol="+key);
    }
    function eIn(image){
        image.src="./images/edit_b.gif";
    }
    function eOut(image){
        image.src="./images/edit_a.gif";
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


</script>
<form name="fForm" method="POST"  onsubmit="return checkSubmitFlag()"
      action="<%= response.encodeURL("./ViewSamples.jsp")%>"/>
      
  <input type="hidden" name="listUserName" id="listUserName" value="<%=listUserName%>">

    <!--galvas tabula -->
    <table class="view_table" >
        <thead>
            <tr>
                <td align="left">
                    <table class="sample">
                        <tr>
                            <td><a class="largest">DBSamples --</a>
                            <%int limit = tmpHttpSessObj.getViewLimit();
                            int full = sampleList.size();
            String showing = (full>limit)? String.valueOf(limit)+" of "+String.valueOf(full):String.valueOf(full);
           %>
            <a class="larger"><%=showing%></a></td>
                            <%if (tmpHttpSessObj.isFilter() || tmpHttpSessObj.isSearch()) {
                            %><td align="left">
                                <input type="submit" name="cancelFilter" value="Cancel Search"/>
                            </td><%}%>
                        </tr>
                        <tr><td style="font-size:10px">
                                Displaying at most <%=limit%> items. <br>For complete listing,

                                <a href="./Export.jsp?tableToExport=sample&subjectID=<%=request.getParameter("subjId")%>" target="_blank">export to Excel</a>
                            </td>
                        </tr>
                    </table>
                </td>

                <td align="right" valign="middle">
                                    <select id="gotoNav" >
            <option selected value="blank">What would you like to do?</option>
            <option value="new">Create a new sample</option>
            <option value="view">Customize sample data view</option>
            <option value="txt">Export all items to tab-delimited txt file</option>
        </select>
        <button type="button" class="buttonSmall" onclick="goNav('<%=request.getParameter("subjId")%>')"/>Go</button>

                </td></tr>
        </thead>
    </table>

    <!--satura tabula -->
    <table class="view_table" style="border-width: 0px; border-spacing: 1px; border-color: #ffffff;" >
        <thead>
            <tr bgcolor="#b3cfff">
                <td width="1%"><a href='javascript:jumpFocus("bottomLine")'><img border="0" src="./images/down_d_10_b.gif" title="bottom of page" alt=""></a></td>
                
                <td >
                    <table style="background-color:#b3cfff; border-width: 0px; border-spacing: 0px; border-color: #ffffff;">
                <tr><td colspan="2" >
                <input type="submit" name="updateList" value="Update List" style="font-size:8pt; background-color:LightBlue"/>
                </td></tr>
                <tr><td align="right">
                <a onclick="checkAll(document.fForm.contentListChecked)">
                    <img border="0" src="./images/selectAll_a.png" onmouseover="saIn(this)"
                         onmouseout="saOut(this)" title="Select All" name="updateList" alt="">
                </a></td><td align="left">
                <a onclick="uncheckAll(document.fForm.contentListChecked)">
                    <img border="0" src="./images/deselectAll_a.png" onmouseover="daIn(this)"
                         onmouseout="daOut(this)" title="Deselect All" name="updateList" alt="">
                </a>
                </td></tr></table></td>
                <%
        String[] keyArray = mdm.getSampleKeyArray();
        int colCount = keyArray.length;
        String cellClassName;
        
        for (int i = viewStart; i < colCount; i++) {
                %><td class="headerEven"><%
            if (mdm.getSampleSortable(keyArray[i])) {
            if(tmpHttpSessObj.getSampleSortId(keyArray[i]).equals("normal")){%>
            <a class="<%=tmpHttpSessObj.getSampleSortId(keyArray[i])%>"
            href='javascript:gotoSorted("<%=keyArray[i]%>")'>
            <%=mdm.getSampleLongName(keyArray[i])%>
            <img border="0" src="./images/sort_sm_none.gif" title="Sort" alt=""></a>
                <%}
            if(tmpHttpSessObj.getSampleSortId(keyArray[i]).equals("normalo")){%>
            <a class="<%=tmpHttpSessObj.getSampleSortId(keyArray[i])%>"
            href='javascript:gotoSorted("<%=keyArray[i]%>")'>
            <%=mdm.getSampleLongName(keyArray[i])%>
            <img border="0" src="./images/sort_sm_down.gif" title="Sort" alt=""></a>
            <%}
            if(tmpHttpSessObj.getSampleSortId(keyArray[i]).equals("normalu")){%>
            <a class="<%=tmpHttpSessObj.getSampleSortId(keyArray[i])%>"
            href='javascript:gotoSorted("<%=keyArray[i]%>")'>
            <%=mdm.getSampleLongName(keyArray[i])%>
            <img border="0" src="./images/sort_sm_up.gif" title="Sort" alt=""></a>
                <%}%>

            <%
        } else {
                    %><b><%=mdm.getSampleLongName(keyArray[i])%></b><%
                            }
                %></td><%
        }
                %>
                <td>
                </td>
            </tr>
        </thead>

        <tbody><%
        // actual data rows
        if (sampleList != null) {
            byte[] valueIndexArray = mdm.getSampleIndexArray();
            Iterator iter = sampleList.iterator();
            String currStripe;
            Long currId = tmpHttpSessObj.getCurrentSampleId();
            boolean stripOn = true;
            int lineNr = 0;
            String overrideColour="";
            String inList="";

            while (iter.hasNext()) {
                Sample currSample = (Sample) iter.next();
                String[] var = currSample.getValueArrayReadable(valueIndexArray, colCount);

                if (currSample.getId().equals(currId)) {
                    currStripe = (stripOn) ? "curron" : "curroff";
                    if(tmpHttpSessObj.inSamplesList(currSample.getId().toString())){
                        overrideColour = "style=\"background-color:LightBlue\"";
                    }
            %><tr id="current" class="<%=currStripe%>" <%=overrideColour%>><%
                    } else {
                    if(tmpHttpSessObj.inSamplesList(currSample.getId().toString())){
                        overrideColour = "style=\"background-color:LightBlue\"";
                    }
                        currStripe = (stripOn) ? "son" : "soff";
            %><tr class="<%=currStripe%>" <%=overrideColour%>><%
                    }
                    overrideColour="";
                    stripOn = !stripOn;
                    lineNr++;

                %><td id ="<%=currSample.getId()%>" width="1%">
                    <a href="javascript:showSlidedHeader('<%=currSample.getId()%>')">
                        <img id ="headerImg_<%=currSample.getId()%>" border='0' src='./images/up_10_b.gif' title="show header before line <%=lineNr%>" alt="">
                    </a>
                </td>
                <td align="center">
                    <%if(tmpHttpSessObj.inSamplesList(currSample.getId().toString())){
                       inList = "checked";
                    }else{
                      inList = "";
                      }                 %>
                    <!--input type="checkbox" <%=inList%> name="sample<%=currSample.getId()%>" id="sample<%=currSample.getId()%>" onclick="determineValue(sample<%=currSample.getId()%>)"
                     -->
                         <input type="checkbox" <%=inList%> name="sampleListChecked" value="<%=currSample.getId()%>"
                     >
                        
                    </td>
                                    <%if(inList.equals("checked")){
                 // value so we can know if container was UNticked and therefor should be removed from list
                 %>
                 <input type="hidden" name="sampleListWereChecked" value="<%=currSample.getId()%>">
                 <%}%>
                <%

                    for (int i = viewStart; i < colCount; i++) {
                        // actual field values written here:
                        // want null values to appear blank
%><td><%=(var[i] != null) ? var[i] : ""%></td><%
                    }
                %><td><a onclick="gotoEdit('<%=currSample.getId()%>')">
                        <img border="0" src="./images/edit_a.gif" onmouseover="eIn(this)"
                             onmouseout="eOut(this)" title="Edit" alt="">
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
                            if (mdm.getSampleSortable(keyArray[i])) {
                    %><a class="<%=tmpHttpSessObj.getSampleSortId(keyArray[i])%>"
                         href='javascript:gotoSorted("<%=keyArray[i]%>")'>
                        <%--HREF="<%=response.encodeURL("./ViewSamples.jsp")+"?sortCol="+keyArray[i]+"&perstotal="+total%>"
                    --%><%=mdm.getSampleLongName(keyArray[i])%></a><%
                    } else {
                    %><b><%=mdm.getSampleLongName(keyArray[i])%></b><%
                            }
                %></td><%
        }
                %><td>&nbsp;</td>
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
    <%=vfm.getSampleControlString()%>
    <%}%>
</form>
<%@include file="Footer.jsp"%>



