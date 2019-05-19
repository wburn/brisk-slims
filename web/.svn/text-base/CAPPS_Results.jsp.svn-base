<%String lapasId = "CAPPS_Results";%>
<%@include file="Header.jsp"%>
<%
  if ( tmpHttpSessObj.getCurrentUser() == null){//test for new session
    pageContext.forward(response.encodeURL("./LogIn.jsp"));
    return;
  }
  ViewSubjectManager vfm = tmpHttpSessObj.getViewSubjectManager();
  String tmpString = request.getParameter("sortCol");
  if (tmpString != null){
    tmpHttpSessObj.setSubjectSortCol(tmpString);
    vfm.setSubjectFirst();
  }
  else
  if ((tmpString = request.getParameter("add")) != null) {
    vfm.changeSubjectTotal( 1);
  }
  else
  if ((tmpString = request.getParameter("del")) != null) {
    vfm.changeSubjectTotal( -1);
  }
  else
  if ((tmpString = request.getParameter("previous")) != null) {
    vfm.setSubjectPrevious();
  }
  else
  if ((tmpString = request.getParameter("next")) != null) {
    vfm.setSubjectNext();
  }
  else
  if ((tmpString = request.getParameter("first")) != null) {
    vfm.setSubjectFirst();
  }
  else
  if ((tmpString = request.getParameter("last")) != null) {
    vfm.setSubjectLast();
  }
  else
  if ((tmpString = request.getParameter("cancelFilter")) != null) {
    tmpHttpSessObj.cancelFilter();
    tmpHttpSessObj.cancelSearch();
  }
   // for next/prev page of data navigation
  int step = vfm.getSubjectStep();
  int first = vfm.getSubjectCurrent();
  int total = tmpHttpSessObj.getAllSubjectsCount();
  List subjectList = tmpHttpSessObj.getAllSubjects( first);
  vfm.setSubjectCount( total);

        if (request.getParameter("updateList") != null) {
            int success = 0;
            // list name entered by
            String newListName = request.getParameter("listName");
            // if list is a new list and name was entered, then start by making the new list

                // if list is a new list and name was entered, then start by making the new list
                if (newListName != null && !newListName.equals("")) {
                    success = tmpHttpSessObj.addShoppingList(newListName,tmpHttpSessObj.getCurrentUser());
                    if(success != 0){%>
                          <br>
                        <a class="error">  Error:
                        <%=tmpHttpSessObj.getMyMessage(success)%></a>
<%                  }
                    // load new list as current
                    if (success == 0) {
                        System.out.println("newListName="+newListName+success);
                        tmpHttpSessObj.setCurrentShoppingCartList(new ShoppingCartList(tmpHttpSessObj, newListName));
                    }
                }

            if(success == 0 && tmpHttpSessObj.getCurrentShoppingCartList() == null
                    && newListName!= null && !newListName.equals("")){
                    success = tmpHttpSessObj.addShoppingList(newListName,tmpHttpSessObj.getCurrentUser());
                    // load new list as current
                    tmpHttpSessObj.setCurrentShoppingCartList(new ShoppingCartList(tmpHttpSessObj, newListName));
             }
            if(success == 0 && tmpHttpSessObj.getCurrentShoppingCartList() != null){
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
    if(x == "new"){
        gotoEdit( -1);
    } else if(x == "txt"){
        window.location.assign(
            "<%=response.encodeURL("./Export.jsp?tableToExport=subject")%>");
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
function gotoEdit( subjId){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./AddCAPPS.jsp")%>"+"?subjId="+subjId+"&sampId=-1&contId=-1&contContId=-1");
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
         "<%=response.encodeURL("./ViewSubjects.jsp")%>"+"?next=0");
}
function gotoPrevious(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewSubjects.jsp")%>"+"?previous=0");
}
function gotoFirst(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewSubjects.jsp")%>"+"?first=0");
}
function gotoLast(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewSubjects.jsp")%>"+"?last=0");
}
function gotoSorted( key){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewSubjects.jsp")%>"+"?sortCol="+key);
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
  action="<%= response.encodeURL("./CAPPS_Search.jsp") %>"/>
  <input type="hidden" name="listUserName" id="listUserName" value="<%=listUserName%>">
<!--galvas tabula -->
    <table class="view_table" >
        <thead>
            <tr>
                <td align="left">
                    <table class="sample">
                        <tr>
                            <td><a class="largest">Subjects --</a>
                            <a class="larger"><%=vfm.getSubjectControlString()%></a></td>
                            <%if (tmpHttpSessObj.isFilter() || tmpHttpSessObj.isSearch()) {
                            %><td align="left">
                                <input type="submit" name="cancelFilter" value="Cancel Search"/>
                            </td><%}%>
                        </tr>
                    </table>
                </td>
                </tr>
  </thead>
</table>

<!--satura tabula -->
<table class="view_table" style="border-width: 0px; border-spacing: 1px; border-color: #ffffff;" >
  <thead>
    <tr bgcolor="#b3cfff">
      <td width="1%"><a href='javascript:jumpFocus("bottomLine")'><img border="0" src="./images/down_d_10_b.gif" title="bottom of page" alt=""></a></td>

      <%

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
      String inList="";
      String currName="";
      String lastName="";
      int lineNr = 0;

      while (iter.hasNext())
      {
        Subject currSubject = (Subject) iter.next();
        String[] var = currSubject.getValueArrayReadable(valueIndexArray, colCount);
        currName = currSubject.getSubjectName();

        if(!currName.equals(lastName)){
        stripOn = !stripOn;
        }
        lastName= currName;
            if(tmpHttpSessObj.inSubjectsList(currSubject.getId().toString())){
                        currStripe = (stripOn) ? "listStripeOn" : "listStripeOff";
                    }
        else{
          currStripe = (stripOn) ? "son" : "soff";
          }
        lineNr++;
          %><tr class="<%=currStripe%>"><%

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
                <%if(inList.equals("checked")){
                 // value so we can know if container was UNticked and therefor should be removed from list
                 %>
                 <input type="hidden" name="subjectListWereChecked" value="<%=currSubject.getId()%>">
                 <%}%>

<%
String subjectIDmatch = mdm.getSubjectLongName(keyArray[2]);
        for (int i = viewStart; i < colCount; i++)
        {
%><td><% if (mdm.getSubjectLongName(keyArray[i])==subjectIDmatch){%><a href="javascript:gotoEdit('<%=currSubject.getId()%>')"><%=var[i]%></a></td><%}else{%><%=var[i]%></td><%}%><%
        }
          %>
    </tr><%
         }
       }
  %><tr id='slidedHeader' bgcolor="#b3cfff" class="slidedRow">
      <td width="1%">
        <a href='javascript:jumpFocus("bottomLine")'><img border="0" src="./images/down_d_10_b.gif" title="bottom of page" alt=""></a>
      </td><td>&nbsp;</td><%
      for (int i = viewStart; i < colCount; i++) {%>
      <td class="headerEven">
      <%if (mdm.getSubjectSortable(keyArray[i])){%>
      <a class="<%=tmpHttpSessObj.getSubjectSortId(keyArray[i])%>"
               href='javascript:gotoSorted("<%=keyArray[i]%>")'>
          <%--HREF="<%=response.encodeURL("./ViewSubjects.jsp")+"?sortCol="+keyArray[i]+"&perstotal="+total%>"
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
<%if (total>0){ %>
&nbsp;
<a href="javascript:scrollTo(0,0)"><img border="0" src="./images/up_d_10_b.gif" title="top of page" alt=""></a>
&nbsp;&nbsp;
<%=vfm.getSubjectControlString()%>
<%} %>
</form>
<%@include file="Footer.jsp"%>



