<%--
    Document   : ViewSubjectList
    Created on : Aug 31, 2009, 12:22:12 PM
    Author     : tvanrossum
--%>

<%String lapasId = "ViewSubjectsList";%>
<%@include file="Header.jsp"%>
<%
  if ( tmpHttpSessObj.getCurrentUser() == null){//test for new session
    pageContext.forward(response.encodeURL("./LogIn.jsp"));
    return;
  }


        if(tmpHttpSessObj.getCurrentShoppingCartList()==null){
            pageContext.forward(response.encodeURL("./ViewLists.jsp"));
            return;
        }

  ViewSubjectManager vfm = tmpHttpSessObj.getViewSubjectManager();
  String tmpString = request.getParameter("sortCol");
  if (tmpString != null){
    tmpHttpSessObj.setSubjectSortCol(tmpString);
    tmpHttpSessObj.sortSubjectsList();
    vfm.setSubjectFirst();
  }
  else if ((tmpString = request.getParameter("add")) != null) {
      vfm.changeSubjectTotal(1);
  } else if ((tmpString = request.getParameter("del")) != null) {
      vfm.changeSubjectTotal(-1);
  } else if ((tmpString = request.getParameter("previous")) != null) {
      vfm.setSubjectPrevious();
  } else if ((tmpString = request.getParameter("next")) != null) {
      vfm.setSubjectNext();
  } else if ((tmpString = request.getParameter("first")) != null) {
      vfm.setSubjectFirst();
  } else if ((tmpString = request.getParameter("last")) != null) {
      vfm.setSubjectLast();
  }

  int step = vfm.getSubjectStep();
  int first = vfm.getSubjectCurrent();

        // relod if cancel search
        if (request.getParameter("cancelFilter") != null) {
            tmpHttpSessObj.cancelFilterList();
            tmpHttpSessObj.cancelSearchList();
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
function goNav(){
    var x = document.getElementById("gotoNav").value;
    if(x == "txt"){
        window.location.assign(
            "<%=response.encodeURL("./ExportList.jsp?tableToExport=subject")%>");
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
function gotoEdit( contId){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./AddSubject.jsp")%>"+"?subjId="+contId);
}
function gotoContents( subjId){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewContainerContents.jsp")%>"+"?subjId="+subjId);
}
function gotoNext(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewSubjectsList.jsp")%>"+"?next=0");
}
function gotoPrevious(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewSubjectsList.jsp")%>"+"?previous=0");
}
function gotoFirst(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewSubjectsList.jsp")%>"+"?first=0");
}
function gotoLast(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewSubjectsList.jsp")%>"+"?last=0");
}
function gotoSorted( key){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewSubjectsList.jsp")%>"+"?sortCol="+key);
}
function eIn(image){
  image.src="./images/edit_b.gif";
}
function eOut(image){
  image.src="./images/edit_a.gif";
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

</script>
<form name="fForm" method="POST"  onsubmit="return checkSubmitFlag()"
  action="<%= response.encodeURL("./ViewSubjectsList.jsp") %>"/>
  <input type="hidden" name="listUserName" id="listUserName" value="<%=listUserName%>">
<!--galvas tabula -->
<%

        if (request.getParameter("updateList") != null) {
            tmpHttpSessObj.updateList(request);
        }
    List subjectList = null;
    int total = -1;
        if(tmpHttpSessObj.isFilterList() || tmpHttpSessObj.isSearchList()){
        subjectList = tmpHttpSessObj.getSubjectsListSearched();
        total = subjectList.size();
        }
        else{
            subjectList = tmpHttpSessObj.getAllSubjectObjectsInList();
            total = tmpHttpSessObj.getAllSubjectsListCount();
        }
        
        String listName = tmpHttpSessObj.getCurrentShoppingList().getListName();
        vfm.setSubjectCount(subjectList.size());

  %>
    <table class="view_table" >
        <thead>
            <tr>
                <td align="left">
                    <table class="sample">
                        <tr>
                            <td><a class="largest"><%=listName%>: Subjects--</a>
                            <%int limit = tmpHttpSessObj.getViewLimit();
                              int full = subjectList.size();
                              String showing = (full>limit)? String.valueOf(limit)+" of "+String.valueOf(full):String.valueOf(full);
                            %>
                            <a class="larger"><%=full%> items</a>
                            <%if (tmpHttpSessObj.isFilterList() || tmpHttpSessObj.isSearchList()) {%>
                            <td><input type="submit" name="cancelFilter" value="Cancel Search"/></td>
                            <%}%>
                        </tr>
                        <!--tr><td style="font-size:10px">
                                Displaying at most <%=limit%> items. <br>For complete listing,
                                <a href="./Export.jsp?tableToExport=subject" target="_blank">export to Excel</a>
                            </td-->
                        </tr>
                    </table>
                </td>
      <td align="right" valign="middle">
         <select id="gotoNav" >
            <option selected value="blank">What would you like to do?</option>
            <option value="txt">Export all items to tab-delimited txt file</option>
            <option>--List Tools--</option>
            <option value="trim">Trim List</option>
            <option value="sampleSelector">Select samples for subjects in list</option>
        </select>
        <button type="button" class="buttonSmall" onclick="goNav()"/>Go</button> 

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
                    <input type="submit" onclick="promptForListName()" name="updateList" value="Create List" style="font-size:8pt; background-color:LightBlue"/>
                <%

                    }else{%>
                <input type="submit" name="updateList" value="Update List" style="font-size:8pt; background-color:LightBlue"/>
                <%}%>
                </td></tr>
                <tr><td align="right">
                <a onclick="checkAll(document.fForm.subjectListChecked)">
                    <img border="0" src="./images/selectAll_a.png" onmouseover="saIn(this)"
                         onmouseout="saOut(this)" title="Select All" name="updateList" alt="">
                </a></td><td align="left">
                <a onclick="uncheckAll(document.fForm.subjectListChecked)">
                    <img border="0" src="./images/deselectAll_a.png" onmouseover="daIn(this)"
                         onmouseout="daOut(this)" title="Deselect All" name="updateList" alt="">
                </a>
                </td></tr></table>
                </td>

      <%
      // getSubjectTypeKeyArray() returns column header names
      String[] keyArray = mdm.getSubjectKeyArray();
      int colCount = keyArray.length;
      String cellClassName;

      for (int i = viewStart; i < colCount; i++)
      {
    %><td class="headerEven"><%
        if (mdm.getSubjectSortable(keyArray[i])) {
            if(tmpHttpSessObj.getSubjectSortId(keyArray[i]).equals("normal")){%>
            <a class="<%=tmpHttpSessObj.getSubjectSortId(keyArray[i])%>"
            href='javascript:gotoSorted("<%=keyArray[i]%>")'>
            <%=mdm.getSubjectLongName(keyArray[i])%>
            <img border="0" src="./images/sort_sm_none.gif" title="Sort" alt=""></a>
                <%}
            if(tmpHttpSessObj.getSubjectSortId(keyArray[i]).equals("normalo")){%>
            <a class="<%=tmpHttpSessObj.getSubjectSortId(keyArray[i])%>"
            href='javascript:gotoSorted("<%=keyArray[i]%>")'>
            <%=mdm.getSubjectLongName(keyArray[i])%>
            <img border="0" src="./images/sort_sm_down.gif" title="Sort" alt=""></a>
            <%}
            if(tmpHttpSessObj.getSubjectSortId(keyArray[i]).equals("normalu")){%>
            <a class="<%=tmpHttpSessObj.getSubjectSortId(keyArray[i])%>"
            href='javascript:gotoSorted("<%=keyArray[i]%>")'>
            <%=mdm.getSubjectLongName(keyArray[i])%>
            <img border="0" src="./images/sort_sm_up.gif" title="Sort" alt=""></a>
                <%}%>

            <%
        }
        else
        {
      %><b><%=mdm.getSubjectLongName(keyArray[i])%></b><%
        }
    %></td><%
      }
    %>
     <td>
      </td>
      <td>
      </td>
    </tr>
  </thead>
  <tbody><%
    // actual data rows
    if (subjectList != null)
    {
      byte[] valueIndexArray = mdm.getSubjectIndexArray();
      Iterator iter = subjectList.iterator();
      String currStripe;
      String currId = tmpHttpSessObj.getCurrentSubjectId();
      boolean stripOn = true;
      int lineNr = 0;
            String inList="";
            String currSubjectName="";
            String prevSubjectName="";

      while (iter.hasNext())
      {
        Subject currSubject = (Subject) iter.next();
        String[] var = currSubject.getValueArrayReadable(valueIndexArray, colCount);

        currSubjectName = currSubject.getSubjectName();
                if(!currSubjectName.equals(prevSubjectName)){
                stripOn = !stripOn;
                }
                prevSubjectName = currSubjectName;
        currStripe = (stripOn) ? "listStripeOn" : "listStripeOff";

        %><tr class="<%=currStripe%>"><%
        lineNr++;

      %><td id ="<%=currSubject.getId()%>" width="1%">
          <a href="javascript:showSlidedHeader('<%=currSubject.getId()%>')">
             <img id ="headerImg_<%=currSubject.getId()%>" border='0' src='./images/up_10_b.gif' title="show header before line <%=lineNr%>" alt="">
          </a>
        </td>
        <%if(tmpHttpSessObj.inSubjectsList(currSubject.getId().toString())){
                       inList = "checked";
                    }else{
                      inList = "";
                      }                 %>
                     <td align="center">
                         <input type="checkbox" <%=inList%> name="subjectListChecked" value="<%=currSubject.getId()%>">
                    </td>

                <%if(inList.equals("checked")){
                 // value so we can know if container was UNticked and therefor should be removed from list
                 %>
                 <input type="hidden" name="subjectListWereChecked" value="<%=currSubject.getId()%>">
                 <%}%>
        <%

        for (int i = viewStart; i < colCount; i++)
        {
            // actual field values written here:
            // want null values to appear blank
    %><td><%=(var[i]!=null)?var[i]:""%></td><%
        }
          %><td><a onclick="gotoEdit('<%=currSubject.getId()%>')">
                <img border="0" src="./images/edit_a.gif" onmouseover="eIn(this)"
                     onmouseout="eOut(this)" title="Edit" alt="">
              </a></td>
          <td>
            <a onclick="gotoContents('<%=currSubject.getId()%>')">
            <img border="0" src="./images/samples_a.gif" onmouseover="sIn(this)"
                     onmouseout="sOut(this)" title="Samples" alt="">
              </a></td>
    </tr><%
         }
       }
  %><tr id='slidedHeader' bgcolor="#b3cfff" class="slidedRow">
      <td width="1%">
        <a href='javascript:jumpFocus("bottomLine")'><img border="0" src="./images/down_d_10_b.gif" title="bottom of page" alt=""></a>
      </td><td>&nbsp;</td><%

      for (int i = viewStart; i < colCount; i++)
      {
    %><td class="headerEven"><%
        if (mdm.getSubjectSortable(keyArray[i]))
        {
      %><a class="<%=tmpHttpSessObj.getSubjectSortId(keyArray[i])%>"
               href='javascript:gotoSorted("<%=keyArray[i]%>")'>
          <%--HREF="<%=response.encodeURL("./ViewSubjectsList.jsp")+"?sortCol="+keyArray[i]+"&perstotal="+total%>"
          --%><%=mdm.getSubjectLongName(keyArray[i])%></a><%
        }
        else
        {
      %><b><%=mdm.getSubjectLongName(keyArray[i])%></b><%
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
<%if (false){//(total > 0) {%>
&nbsp;
<a href="javascript:scrollTo(0,0)"><img border="0" src="./images/up_d_10_b.gif" title="top of page" alt=""></a>
&nbsp;&nbsp;
<%=vfm.getSubjectControlString()%>
<%} %>
</form>
<%@include file="Footer.jsp"%>



