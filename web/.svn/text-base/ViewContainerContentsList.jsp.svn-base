<%-- 
    Document   : ViewContainerContentsList
    Created on : Jul 22, 2009, 1:23:47 PM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%String lapasId = "ViewContainerContentsList";%>
<%@include file="Header.jsp"%>
<%
        if (tmpHttpSessObj.getCurrentUser() == null) {//test for new session
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }

        if(tmpHttpSessObj.getCurrentShoppingCartList()==null){
            pageContext.forward(response.encodeURL("./ViewLists.jsp"));
            return;
        }

        ViewContainerContentManager vfm = tmpHttpSessObj.getViewContainerContentManager();
        String tmpString = request.getParameter("sortCol");
        if (tmpString != null) {
            tmpHttpSessObj.setContainerContentSortCol(tmpString);
            tmpHttpSessObj.sortContentsList();
            vfm.setContainerContentFirst();
        } else if ((tmpString = request.getParameter("add")) != null) {
            vfm.changeContainerContentTotal(1);
        } else if ((tmpString = request.getParameter("del")) != null) {
            vfm.changeContainerContentTotal(-1);
        } else if ((tmpString = request.getParameter("previous")) != null) {
            vfm.setContainerContentPrevious();
        } else if ((tmpString = request.getParameter("next")) != null) {
            vfm.setContainerContentNext();
        } else if ((tmpString = request.getParameter("first")) != null) {
            vfm.setContainerContentFirst();
        } else if ((tmpString = request.getParameter("last")) != null) {
            vfm.setContainerContentLast();
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
        
        // relod if cancel search
        if (request.getParameter("cancelFilter") != null) {
            tmpHttpSessObj.cancelFilterList();
            tmpHttpSessObj.cancelSearchList();
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
    if(x == "saveAs"){
    } else if(x == "view"){
        gotoCustomize();
    } else if(x == "txt"){
        window.location.assign(
            "<%=response.encodeURL("./ExportList.jsp?tableToExport=containerContent")%>");
    } else if(x == "createContainer" && listOkToUse()){
        gotoCreateContainer();
    } else if(x == "editVols" && listOkToUse()){
        window.location.assign(
            "<%=response.encodeURL("./ListVolumeUpdate.jsp")%>");
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
    function gotoEdit( contContId){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./AddContainerContent.jsp")%>"+"?contContId="+contContId+"&viewList=true");
    }
function gotoSampleDocuments( sampId){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewSampleDocumentss.jsp")%>"+"?sampId="+sampId);
}
function gotoSampleHistory( contContId){
    if (checkSubmitFlag()) window.location.assign(
    "<%=response.encodeURL("./SampleHistory.jsp")%>"+"?ccId="+contContId);
}
function gotoGenotype( contContId){
    if (checkSubmitFlag()) windows.location.assign(
    "<%=response.encodeURL("./ViewGenotypes.jsp")%>" + "?ccId=" + contContId);
}
function gotoContainerContentHistory( contContId ){
    if (checkSubmitFlag()) window.location.assign(
    "<%=response.encodeURL("./ContainerContentHistory.jsp")%>"+"?ccId="+contContId);
}
function gotoCustomize( ){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./EditColumns.jsp")%>"+"?source=list");
}
function gotoCreateContainer( ){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./HowManyPlates.jsp")%>");
}

    function gotoNext(){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./ViewContainerContentsList.jsp")%>"+"?next=0");
    }
    function gotoPrevious(){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./ViewContainerContentsList.jsp")%>"+"?previous=0");
    }
    function gotoFirst(){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./ViewContainerContentsList.jsp")%>"+"?first=0");
    }
    function gotoLast(){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./ViewContainerContentsList.jsp")%>"+"?last=0");
    }
    function gotoSorted( key){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./ViewContainerContentsList.jsp")%>"+"?sortCol="+key);
    }
    function eIn(image){
        image.src="./images/edit_b.gif";
    }
    function eOut(image){
        image.src="./images/edit_a.gif";
    }
    function sampIn(image){
  image.src="./images/sampleHistory_b.png";
}
function sampOut(image){
  image.src="./images/sampleHistory_a.png";
}
function genotypeIn(image){
    image.src="./image/genotype_b.png";
}
function genotypeOut(image){
    image.src="./image/genotype_a.png";
}
function containerContentIn(image){
    image.src="./images/containerCont_b.png";
}
function containerContentOut(image){
    image.src="./images/containerCont_a.png";
}
function dlIn(image){
  image.src="./images/sampDocs_b.gif";
}
function dlOut(image){
  image.src="./images/sampDocs_a.gif";
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
      action="<%= response.encodeURL("./ViewContainerContentsList.jsp")%>">
      
<input type="hidden" name="listUserName" id="listUserName" value="<%=listUserName%>">

<!--galvas tabula -->
<%

    if (request.getParameter("updateList") != null) {
            tmpHttpSessObj.updateList(request);
    }
    List containercontentList = null;
        if(tmpHttpSessObj.isFilterList() || tmpHttpSessObj.isSearchList()){
        containercontentList = tmpHttpSessObj.getContentsListSearched();
        }
        else{
        containercontentList = tmpHttpSessObj.getAllContentObjectsInList();
        }

        
        int total = tmpHttpSessObj.getAllContentsListCount();
        int step = vfm.getContainerContentStep();
        int first = vfm.getContainerContentCurrent();

        String listName = tmpHttpSessObj.getCurrentShoppingList().getListName();
        vfm.setContainerContentCount(containercontentList.size());

        %>

<table class="view_table">
    <thead>
        <tr>
            <td align="left">
                <table class="sample">
                    <tr>
                        <td class="left" align="left" ><a class="largest"><%=listName%>: Samples--</a>

                        <%int limit = tmpHttpSessObj.getViewLimit();
                              int full = containercontentList.size();
                              String showing = (full>limit)? String.valueOf(limit)+" of "+String.valueOf(full):String.valueOf(full);
                            %>
                            <a class="larger"><%=full%> items</a>
                            <%if (tmpHttpSessObj.isFilterList() || tmpHttpSessObj.isSearchList()) {%>
                            <td><input type="submit" name="cancelFilter" value="Cancel Search"/></td>
                            <%}%>
                    </tr>
                    <!--tr><td style="font-size:10px">
                                Displaying at most <%=limit%> items. <br>For complete listing,
                                <a href="./ExportList.jsp?tableToExport=containerContent" target="_blank" style="font-weight:bold">export to txt</a>
                            
                            </td>
                        </tr-->
                </table>
            </td>
            <td align="right" valign="middle">
        <select id="gotoNav" >
            <option selected value="blank">What would you like to do?</option>
            <option value="view">Customize sample data view</option>
            <option value="txt">Export all items to tab-delimited txt file</option>
            <option >-- List Tools --</option>
            <option value="trim">Trim List</option>
            <option value="createContainer">Create and Populate 96 Well Plates</option>
            <option value="editVols">Update all volumes in list</option>
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
                    <%if(tmpHttpSessObj.getCurrentShoppingCartList() == null){
                        // if user hasn't made their shopping list yet, make them name it
                    %>
                    <input type="hidden" name="listName" id="listName">
                    <input type="submit" onclick="promtForListName()" name="updateList" value="Create List" style="font-size:8pt; background-color:LightBlue"/>
                <%

                    }else{%>
                <input type="submit" name="updateList" value="Update List" style="font-size:8pt; background-color:LightBlue"/>
                <%}%>
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
                </td></tr></table> </td>
        <%

        String[] keyArray = mdm.getContainerContentKeyArray();
        int colCount = keyArray.length;
        String cellClassName;

        for (int i = viewStart; i < colCount; i++) {
        %><td class="headerEven"><%
                  if (mdm.getContainerContentSortable(keyArray[i])) {
            if(tmpHttpSessObj.getContainerContentSortId(keyArray[i]).equals("normal")){%>
            <a class="<%=tmpHttpSessObj.getContainerContentSortId(keyArray[i])%>"
            href='javascript:gotoSorted("<%=keyArray[i]%>")'>
            <%=mdm.getContainerContentLongName(keyArray[i])%>
            <img border="0" src="./images/sort_sm_none.gif" title="Sort" alt=""></a>
                <%}
            if(tmpHttpSessObj.getContainerContentSortId(keyArray[i]).equals("normalo")){%>
            <a class="<%=tmpHttpSessObj.getContainerContentSortId(keyArray[i])%>"
            href='javascript:gotoSorted("<%=keyArray[i]%>")'>
            <%=mdm.getContainerContentLongName(keyArray[i])%>
            <img border="0" src="./images/sort_sm_down.gif" title="Sort" alt=""></a>
            <%}
            if(tmpHttpSessObj.getContainerContentSortId(keyArray[i]).equals("normalu")){%>
            <a class="<%=tmpHttpSessObj.getContainerContentSortId(keyArray[i])%>"
            href='javascript:gotoSorted("<%=keyArray[i]%>")'>
            <%=mdm.getContainerContentLongName(keyArray[i])%>
            <img border="0" src="./images/sort_sm_up.gif" title="Sort" alt=""></a>
                <%}%>

            <%
        } else {
            %><b><%=mdm.getContainerContentLongName(keyArray[i])%></b><%
                  }
        %></td><%
        }
        %>
        <td>
        </td>
        <td>&nbsp;
      </td>
      <td>
      </td>
      <td></td><!--spacers for icons-->
      <td></td>
    </tr>
</thead>
<tbody><%
// actual data rows
        if (containercontentList != null) {
            byte[] valueIndexArray = mdm.getContainerContentIndexArray();
            Iterator iter = containercontentList.iterator();
            String currStripe;
            Long currId = tmpHttpSessObj.getCurrentContainerContentId();
            boolean stripOn = true;
            String[] var;
            int lineNr = 0;
            String inList = "";
            String currSubject = "";
            String prevSubject = "";
            ContainerContent currContainerContent;
            while (iter.hasNext()) {
                currContainerContent = (ContainerContent) iter.next();
                var = currContainerContent.getValueArrayReadable(valueIndexArray, colCount);

                currSubject = currContainerContent.getSample().getSubject().getSubjectName();
                if(!currSubject.equals(prevSubject)){
                stripOn = !stripOn;
                }
                prevSubject = currSubject;
        currStripe = (stripOn) ? "listStripeOn" : "listStripeOff";
    %><tr class="<%=currStripe%>" ><%
      
      lineNr++;

    %><td id ="<%=currContainerContent.getId()%>" width="1%">
        <a href="javascript:showSlidedHeader('<%=currContainerContent.getId()%>')">
            <img id ="headerImg_<%=currContainerContent.getId()%>" border='0' src='./images/up_10_b.gif' title="show header before this line" alt="">
        </a>
    </td>
    <%if (tmpHttpSessObj.inContainerContentsList(currContainerContent.getId().toString())) {
          inList = "checked";
      } else {
          inList = "";
      }%>

    <td  align="center">
    <input type="checkbox" <%=inList%> name="contentListChecked" value="<%=currContainerContent.getId()%>"/>
                             <%if(inList.equals("checked")){
                                 // value so we can know if container was UNticked and therefor should be removed from list
                                 %>
                             <input type="hidden" name="contentListWereChecked" value="<%=currContainerContent.getId()%>">

                            <%}%>
    </td>
    <%

      for (int i = viewStart; i < colCount; i++) {
          // actual field values written here:
          // want null values to appear blank
%><td><%=(var[i] != null) ? var[i] : ""%></td><%
      }
    %><td><a onclick="gotoEdit('<%=currContainerContent.getId()%>')">
            <img border="0" src="./images/edit_a.gif" onmouseover="eIn(this)"
                 onmouseout="eOut(this)" title="Edit" alt="">
    </a></td>
              
              <td>
                <a onclick="gotoSampleDocuments('<%=currContainerContent.getSample().getSampleID()%>')">
                <img border="0" src="./images/sampDocs_a.gif" onmouseover="dlIn(this)"
                     onmouseout="dlOut(this)" title="List All Sample Documents" alt=""/>
              </a></td>
              <td><a onclick="gotoContainerContentHistory('<%=currContainerContent.getId()%>')">
                <img border="0" src="./images/containerCont_a.png" onmouseover="containerContentIn(this)"
                     onmouseout="containerContentOut(this)" title="Get Container Content History" alt=""/>
              </a></td>
              <td><a onclick="gotoSampleHistory('<%=currContainerContent.getId()%>')">
                <img border="0" src="./images/sampleHistory_a.png" onmouseover="sampIn(this)"
                     onmouseout="sampOut(this)" title="Get Sample History" alt=""/>
              </a></td>
              <td><a onclick="gotoGenotype('<%=currContainerContent.getId()%>')">
                <img border="0" src="./images/genotype_a.png" onmouseover="sampIn(this)"
                     onmouseout="sampOut(this)" title="Get Genotyping Results" alt=""/>
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
              if (mdm.getContainerContentSortable(keyArray[i])) {
        %><a class="<%=tmpHttpSessObj.getContainerContentSortId(keyArray[i])%>"
             href='javascript:gotoSorted("<%=keyArray[i]%>")'>
            <%--HREF="<%=response.encodeURL("./ViewContainerContentsList.jsp")+"?sortCol="+keyArray[i]+"&perstotal="+total%>"
        --%><%=mdm.getContainerContentLongName(keyArray[i])%></a><%
        } else {
        %><b><%=mdm.getContainerContentLongName(keyArray[i])%></b><%
              }
    %></td><%
        }
    %><td>&nbsp;</td>
        <td>&nbsp;</td>
    <td></td>
    <td></td>
    <td></td><!--spacers for icons-->
    <td>
        <a href="javascript:scrollTo(0,0)"><img border="0" src="./images/up_d_10_b.gif" title="top of page" alt=""></a>
    </td>
</tr>
</tbody>
</table>
<%if (false){//(total > 0) {%>
&nbsp;
<a href="javascript:scrollTo(0,0)"><img border="0" src="./images/up_d_10_b.gif" title="top of page" alt=""></a>
&nbsp;&nbsp;
<%=vfm.getContainerContentControlString()%>
<%}%>
</form>
<%@include file="Footer.jsp"%>




