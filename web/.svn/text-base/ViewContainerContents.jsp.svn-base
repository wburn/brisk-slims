<%String lapasId = "ViewContainerContents";%>
<%@include file="Header.jsp"%>

<%
  if ( tmpHttpSessObj.getCurrentUser() == null){//test for new session
    pageContext.forward(response.encodeURL("./LogIn.jsp"));
    return;
  }
  ViewContainerContentManager vfm = tmpHttpSessObj.getViewContainerContentManager();
  String tmpString = request.getParameter("sortCol");
  if (tmpString != null){
    tmpHttpSessObj.setContainerContentSortCol(tmpString);
    vfm.setContainerContentFirst();
  }
  else
  if ((tmpString = request.getParameter("add")) != null) {
    vfm.changeContainerContentTotal( 1);
  }
  else
  if ((tmpString = request.getParameter("del")) != null) {
    vfm.changeContainerContentTotal( -1);
  }
  else
  if ((tmpString = request.getParameter("previous")) != null) {
    vfm.setContainerContentPrevious();
  }
  else
  if ((tmpString = request.getParameter("next")) != null) {
    vfm.setContainerContentNext();
  }
  else
  if ((tmpString = request.getParameter("first")) != null) {
    vfm.setContainerContentFirst();
  }
  else
  if ((tmpString = request.getParameter("last")) != null) {
    vfm.setContainerContentLast();
  }
  else
  if ((tmpString = request.getParameter("cancelFilter")) != null) {
    tmpHttpSessObj.cancelFilter();
    tmpHttpSessObj.cancelSearch();
  }
  /*else if ((tmpString = request.getParameter("contId")) != null) {
            tmpHttpSessObj.cancelFilter();
            tmpHttpSessObj.cancelSearch();
            tmpHttpSessObj.setCurrentContainer(tmpString);
            //vfm.setSubjectIdDown(tmpHttpSessObj.getCurrentSubjectId());
        }*/

  // see if current list is in use and if list tools should be blocked
  String listUserName = null;
  if( tmpHttpSessObj.getCurrentShoppingCartList() != null &&
          tmpHttpSessObj.getCurrentShoppingCartList().currentListInUseBy()!=null){
      listUserName = tmpHttpSessObj.getCurrentShoppingCartList().currentListInUseBy().getVisibleName();
    }
  
  List containercontentList = null;
  String contId =request.getParameter("contId");
  String subjId =request.getParameter("subjId");
  String ccId =request.getParameter("ccId");
  System.out.println("contId="+contId+" subjId="+subjId+" ccId="+ccId);

  // for next/prev page of data navigation
  int step = vfm.getContainerContentStep();
  int first = vfm.getContainerContentCurrent();
  int total = 0;

  // get container contents to display, consider limitations through subjects, containers or specific contetns
  if (contId != null && contId.length()>0 && contId.indexOf("null")<0) {
      tmpHttpSessObj.cancelFilter();
      tmpHttpSessObj.cancelSearch();
   containercontentList = tmpHttpSessObj.getAllContainerContents( first, contId, null);
   total = tmpHttpSessObj.getAllContainerContentsCount(contId, null);
 }else if (subjId != null && subjId.length()>0   && subjId.indexOf("null")<0) {
      tmpHttpSessObj.cancelFilter();
      tmpHttpSessObj.cancelSearch();
   containercontentList = tmpHttpSessObj.getAllContainerContents( first, null, subjId);
   total = tmpHttpSessObj.getAllContainerContentsCount(null, subjId);
  }else if (ccId != null  && ccId.length()>0 && ccId.indexOf("null")<0) {
      tmpHttpSessObj.cancelFilter();
      tmpHttpSessObj.cancelSearch();
   containercontentList = new ArrayList();
   containercontentList.add(tmpHttpSessObj.getObjectById(ContainerContent.class, ccId));
   total = 1;
  } else{
   containercontentList = tmpHttpSessObj.getAllContainerContents( first,null,null);
   total = tmpHttpSessObj.getAllContainerContentsCount(null,null);
 }
  // for next/prev page of data navigation
  vfm.setContainerContentCount( total);


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
        if (request.getParameter("addAllResults") != null) {
            int success = 0;
            // list name entered by user if they created one
            String newListName = request.getParameter("listName");
            // if list is a new list and name was entered, then start by making the new list
            if(tmpHttpSessObj.getCurrentShoppingCartList() == null
                    && newListName!= null && !newListName.equals("")){
                    success = tmpHttpSessObj.addShoppingList(newListName,tmpHttpSessObj.getCurrentUser());
                    // load new list as current
                    tmpHttpSessObj.setCurrentShoppingCartList(new ShoppingCartList(tmpHttpSessObj, newListName));
             }
            if(success == 0 && tmpHttpSessObj.getCurrentShoppingCartList() != null){
             // update current list
                List idsToAdd = tmpHttpSessObj.getAllContainerContentIDsUnlimited(contId, subjId);
                Iterator iter = idsToAdd.iterator();
                // ITERATE THROUGH RESULTS FROM SEARCH AND ADD THEM TO THE LIST
                while(iter.hasNext()){
                    tmpHttpSessObj.getCurrentShoppingCartList().addObjectsByContent(iter.next().toString());
                }
            }
            success = tmpHttpSessObj.releaseLockCurrentShoppingList();
            response.sendRedirect("./ViewContainerContentsList.jsp");
            return;
        }

    // decides whether to show the DB ID
  int viewStart=1;
  if(tmpHttpSessObj.isTechUser()){
  viewStart=0;
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
    } else if(x == "view"){
        gotoCustomize();
    } else if(x == "txt"){
        window.location.assign(
            "<%=response.encodeURL("./Export.jsp?tableToExport=containerContent")%>");
    } else if(x == "createContainer" && listOkToUse()){
        gotoCreateContainerFromList();
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
         "<%=response.encodeURL("./AddContainerContent.jsp")%>"+"?contContId="+contContId);
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
    if (checkSubmitFlag()) window.location.assign(
    "<%=response.encodeURL("./ViewGenotypes.jsp")%>"+"?ccId="+contContId);
}
function gotoContainerContentHistory( contContId ){
    if (checkSubmitFlag()) window.location.assign(
    "<%=response.encodeURL("./ContainerContentHistory.jsp")%>"+"?ccId="+contContId);
}
function gotoCustomize( contId,subjId){
        if (checkSubmitFlag()) window.location.assign(
            "<%= response.encodeURL("./EditColumns.jsp?" +
                 ((contId==null)?"":"contId="+contId) +
                 ((subjId==null)?"":"subjId="+subjId) )%>");

}
function gotoCreateContainerFromList( ){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./HowManyPlates.jsp")%>");
}
function gotoNext(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewContainerContents.jsp")%>"+"?next=0");
}
function gotoPrevious(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewContainerContents.jsp")%>"+"?previous=0");
}
function gotoFirst(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewContainerContents.jsp")%>"+"?first=0");
}
function gotoLast(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewContainerContents.jsp")%>"+"?last=0");
}
function gotoSorted( key){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewContainerContents.jsp")%>"+"?sortCol="+key);
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
  image.src="./images/genotype_b.png";
}
function genotypeOut(image){
  image.src="./images/genotype_a.png";
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
  action="<%= response.encodeURL("./ViewContainerContents.jsp"+
                ((contId==null || contId.equals("null"))?"":"?contId="+contId) +
                 ((subjId==null || subjId.equals("null"))?"":"?subjId="+subjId) )%>">
                 
                 <input type="hidden" name="contId" value="<%=contId%>">
                 <input type="hidden" name="subjId" value="<%=subjId%>">
                 <input type="hidden" name="listUserName" id="listUserName" value="<%=listUserName%>">
<!--galvas tabula -->
<table class="view_table">
  <thead>
    <tr>
      <td align="left">
        <table class="sample">
          <tr>
            <td class="left" align="left" ><a class="largest">Samples --</a>
            <a class="larger"><%=vfm.getContainerContentControlString()%></a></td>

          <%
            if (tmpHttpSessObj.isFilter()  || tmpHttpSessObj.isSearch()){
          %><td>
              <input type="submit" name="cancelFilter" value="Cancel Search"/>
              <%if(total>tmpHttpSessObj.getViewLimit()){%>
              
              <%if(tmpHttpSessObj.getCurrentShoppingCartList() == null){
                        // if user hasn't made their shopping list yet, make them name it
                    %>
                    <input type="hidden" name="listName" id="listName">
                    <input type="submit" name="addAllResults" value="Add All Results to List" onclick="promptForListName()" />
                    <%
                    
                    }else{%>
                <input type="submit" name="addAllResults" value="Add All Results to List" />
                    <%}%>
              
                <%}%>
            </td><%}
          %></tr>
                       
        </table>
      </td>
      <td align="right" valign="middle">
        <select id="gotoNav" >
            <option selected value="blank">What would you like to do?</option>
            <option value="new">Create a new sample</option>
            <option value="view">Customize sample data view</option>
            <option value="txt">Export all items to tab-delimited txt file</option>
            <option >-- List Tools --</option>
            <option value="trim">Trim List</option>
            <option value="createContainer">Create and populate 96 well plates using list</option>
            <option value="editVols">Update all volumes of samples in list</option>
        </select>
        <button type="button" class="buttonSmall" onclick="goNav(<%=contId%>,<%=subjId%>)">Go</button>

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
                <a onclick="checkAll(document.fForm.contentListChecked)">
                    <img border="0" src="./images/selectAll_a.png" onmouseover="saIn(this)"
                         onmouseout="saOut(this)" title="Select All" name="updateList" alt="">
                </a></td><td align="left">
                <a onclick="uncheckAll(document.fForm.contentListChecked)">
                    <img border="0" src="./images/deselectAll_a.png" onmouseover="daIn(this)"
                         onmouseout="daOut(this)" title="Deselect All" name="updateList" alt="">
                </a>
                </td></tr></table>
                </td>
      <%

      String[] keyArray = mdm.getContainerContentKeyArray();
      int colCount = keyArray.length;
      String cellClassName;

      for (int i = viewStart; i < colCount; i++)
      {
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
        }
        else
        {
      %><b><%=mdm.getContainerContentLongName(keyArray[i])%></b><%
        }
    %></td><%
      }
    %>
      <td>&nbsp;</td>
      <td style="width: 3em"></td>
      <td></td>
      <td></td>
      <td></td> <!--spacers for icons-->
    </tr>
  </thead>
  <tbody><%
    // actual data rows
    if (containercontentList != null)
    {
      byte[] valueIndexArray = mdm.getContainerContentIndexArray();
      Iterator iter = containercontentList.iterator();
      String currStripe;
      Long currId = tmpHttpSessObj.getCurrentContainerContentId();
      String lastName = null;
      String currName = null;
      boolean stripOn = true;
      int lineNr = 0;
            String inList="";

      while (iter.hasNext())
      {
        ContainerContent currContainerContent = (ContainerContent) iter.next();
        currName = currContainerContent.getSample().getSubject().getSubjectName();
        if(!currName.equals(lastName)){
        stripOn = !stripOn;
        }
        lastName= currName;
        String[] var = currContainerContent.getValueArrayReadable(valueIndexArray, colCount);

            if(tmpHttpSessObj.inContainerContentsList(currContainerContent.getId().toString())){
                        currStripe = (stripOn) ? "listStripeOn" : "listStripeOff";
                    }
        else{
          currStripe = (stripOn) ? "son" : "soff";
          }
          %><tr class="<%=currStripe%>"><%
        
        lineNr++;

      %><td id ="<%=currContainerContent.getId()%>" width="1%">
          <a href="javascript:showSlidedHeader('<%=currContainerContent.getId()%>')">
             <img id ="headerImg_<%=currContainerContent.getId()%>" border='0' src='./images/up_10_b.gif' title="show header before line <%=lineNr%>" alt="">
          </a>
        </td>
                    <%if(tmpHttpSessObj.inContainerContentsList(currContainerContent.getId().toString())){
                       inList = "checked";
                    }else{
                      inList = "";
                      }                 %>
                    <!--input type="checkbox" <%=inList%> name="sample<%=currContainerContent.getId()%>" id="sample<%=currContainerContent.getId()%>" onclick="determineValue(sample<%=currContainerContent.getId()%>)"
                     -->
                     <td align="center">
                         <input type="checkbox" <%=inList%> name="contentListChecked" value="<%=currContainerContent.getId()%>"
                     >
                             <%if(inList.equals("checked")){
                                 // value so we can know if container was UNticked and therefor should be removed from list
                                 %>
                             <input type="hidden" name="contentListWereChecked" value="<%=currContainerContent.getId()%>">

                            <%}%>
                    </td>
<%

        for (int i = viewStart; i < colCount; i++)
        {
            // actual field values written here:
            // want null values to appear blank
    %><td><%=(var[i]!=null)?var[i]:""%></td><%
        }
          %><td><a onclick="gotoEdit('<%=currContainerContent.getId()%>')">
                <img border="0" src="./images/edit_a.gif" onmouseover="eIn(this)"
                     onmouseout="eOut(this)" title="Edit" alt=""/>
              </a></td>
              <td>
                  <a onclick="gotoSampleDocuments('<%=currContainerContent.getSample().getSampleID()%>')">
                <img border="0" src="./images/sampDocs_a.gif" onmouseover="dlIn(this)"
                     onmouseout="dlOut(this)" title="List All Sample Documents" alt=""/>
              </a></td>
              <td>
                  <a onclick="gotoContainerContentHistory('<%=currContainerContent.getId()%>')">
                <img border="0" src="./images/containerCont_a.png" onmouseover="containerContentIn(this)"
                     onmouseout="containerContentOut(this)" title="Get Container Content History" alt=""/>
              </a></td>
              <td><a onclick="gotoSampleHistory('<%=currContainerContent.getId()%>')">
                <img border="0" src="./images/sampleHistory_a.png" onmouseover="sampIn(this)"
                     onmouseout="sampOut(this)" title="Get Sample History" alt=""/>
              </a></td>
              <td><a onclick="gotoGenotype('<%=currContainerContent.getId()%>')">
                <img border="0" src="./images/genotype_a.png" onmouseover="genotypeIn(this)"
                     onmouseout="genotypeOut(this)" title="Get Genotype Results" alt=""/>
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
        if (mdm.getContainerContentSortable(keyArray[i]))
        {
      %><a class="<%=tmpHttpSessObj.getContainerContentSortId(keyArray[i])%>"
               href='javascript:gotoSorted("<%=keyArray[i]%>")'>
          <%--HREF="<%=response.encodeURL("./ViewContainerContents.jsp")+"?sortCol="+keyArray[i]+"&perstotal="+total%>"
          --%><%=mdm.getContainerContentLongName(keyArray[i])%></a><%
        }
        else
        {
      %><b><%=mdm.getContainerContentLongName(keyArray[i])%></b><%
        }
    %></td><%
      }
    %><td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td><!--spacers for icons-->
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
<%=vfm.getContainerContentControlString()%>
<%} %>
</form>
<%@include file="Footer.jsp"%>



