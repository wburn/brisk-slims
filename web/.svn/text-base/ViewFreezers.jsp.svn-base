<%String lapasId = "ViewFreezers";%>
<%@include file="Header.jsp"%>
<%
  if ( tmpHttpSessObj.getCurrentUser() == null){//test for new session
    pageContext.forward(response.encodeURL("./LogIn.jsp"));
    return;
  }
  ViewFreezerManager vfm = tmpHttpSessObj.getViewFreezerManager();
  String tmpString = request.getParameter("sortCol");
  if (tmpString != null){
    tmpHttpSessObj.setFreezerSortCol(tmpString);
    vfm.setFreezerFirst();
  }
  else
  if ((tmpString = request.getParameter("add")) != null) {
    vfm.changeFreezerTotal( 1);
  }
  else
  if ((tmpString = request.getParameter("del")) != null) {
    vfm.changeFreezerTotal( -1);
  }
  else
  if ((tmpString = request.getParameter("previous")) != null) {
    vfm.setFreezerPrevious();
  }
  else
  if ((tmpString = request.getParameter("next")) != null) {
    vfm.setFreezerNext();
  }
  else
  if ((tmpString = request.getParameter("first")) != null) {
    vfm.setFreezerFirst();
  }
  else
  if ((tmpString = request.getParameter("last")) != null) {
    vfm.setFreezerLast();
  }
  else
  if ((tmpString = request.getParameter("cancelFilter")) != null) {
    tmpHttpSessObj.cancelFilter();
  }
  int step = vfm.getFreezerStep();
  int first = vfm.getFreezerCurrent();
  int total = tmpHttpSessObj.getAllFreezersCount();
  //int total = vfm.getFreezerTotal();
  List freezerList = tmpHttpSessObj.getAllFreezers( first);
  vfm.setFreezerCount( freezerList.size());
  MetadataManager mdm = tmpHttpSessObj.getMetadataManager();

  // decides whether to show the DB ID
  int viewStart=1;
  if(tmpHttpSessObj.isTechUser()){
      viewStart=0;
  }
%>
<script  type="text/javascript" language="javascript">
 function setFocus(){
   var x = document.getElementById("current")
   if (x) scrollTo(0,x.offsetTop);

   initSlidedHeader();
 }
function gotoEdit( freezId){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./AddFreezer.jsp")%>"+"?freezId="+freezId);
}

function gotoNext(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewFreezers.jsp")%>"+"?next=0");
}
function gotoPrevious(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewFreezers.jsp")%>"+"?previous=0");
}
function gotoFirst(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewFreezers.jsp")%>"+"?first=0");
}
function gotoLast(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewFreezers.jsp")%>"+"?last=0");
}
function gotoSorted( key){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewFreezers.jsp")%>"+"?sortCol="+key);
}
function eIn(image){
  image.src="./images/edit_b.gif";
}
function eOut(image){
  image.src="./images/edit_a.gif";
}

</script>


<form name="navFormB">
<%@include file="AdminMenu.jsp"%>
</form>

<!--galvas tabula -->
<table class="view_table">
  <thead>
    <tr>
      <td align="left">
        <table class="sample" frame="box">
          <tr>
            <td class="left1" align="right" ><a class="largest">Freezers --</a></td>
<td align="left" class="right1" borderspacing="0" ><a class="larger"><%=vfm.getFreezerControlString()%></td>
          </tr>
          <tr>
          <td colspan=2 >
              <form method="GET" name="reportForm" target="_blank"
              action="<%=response.encodeURL("./ViewReport.jsp")%>">
                Export to Report:
                    <select name="reportFormat" style="font-size:10px">
                        <option value="pdf" selected>PDF</option>
                        <option value="html">HTML</option>
                        <option value="xls">XLS</option>
                        <option value="csv">CSV</option>
                    </select>
            &nbsp;
                    <input type="hidden" name="reportName" value="freezers">
                    <input type="submit" value="Go" style="font-size:10px">
        </form>
                 </td></tr>
<form name="fForm" method="POST"  onsubmit="return checkSubmitFlag()"
  action="<%= response.encodeURL("./ViewFreezers.jsp") %>"/>
            <%
            if (tmpHttpSessObj.isFilter()){
          %><td>
              <input type="submit" name="cancelFilter" value="Cancel Filter"/>
            </td><%
            }
          %>

            </td></tr>
        </table>
      </td>
      <td align="right">
        <table class="sample"><tr>
          <td align="right" valign="middle">
              <button onclick="gotoEdit('-1')" title="New freezer" style="width:150px" type="button">New Freezer</button>

</td></tr></table>
      </td><%

  %></tr>
  </thead>
</table>

<!--satura tabula -->
<table class="view_table" style="border-width: 0px; border-spacing: 1px; border-color: #ffffff;" >
  <thead>
    <tr bgcolor="#b3cfff">
      <td width="1%"><a href='javascript:jumpFocus("bottomLine")'><img border="0" src="./images/down_d_10_b.gif" title="bottom of page" alt=""></a></td><%

      String[] keyArray = mdm.getFreezerKeyArray();
      int colCount = keyArray.length;
      String cellClassName;

      for (int i = viewStart; i < colCount; i++)
      {
    %><td class="headerEven"><%
        if (mdm.getFreezerSortable(keyArray[i])) {
      %><a class="<%=tmpHttpSessObj.getFreezerSortId(keyArray[i])%>"
            href='javascript:gotoSorted("<%=keyArray[i]%>")'>
            <%=mdm.getFreezerLongName(keyArray[i])%></a><%
        }
        else
        {
      %><b><%=mdm.getFreezerLongName(keyArray[i])%></b><%
        }
    %></td><%
      }
    %>
         <td style="width:30px" >
              <b>Edit</b>
      </td>
    </tr>
  </thead>
  <tbody><%
    // actual data rows
    if (freezerList != null)
    {
      byte[] valueIndexArray = mdm.getFreezerIndexArray();
      Iterator iter = freezerList.iterator();
      String currStripe;
      Long currId = tmpHttpSessObj.getCurrentFreezerId();
      boolean stripOn = true;
      int lineNr = 0;

      while (iter.hasNext())
      {
        Freezer currFreezer = (Freezer) iter.next();
        String[] var = currFreezer.getValueArrayReadable(valueIndexArray, colCount);

        if (currFreezer.getId().equals(currId))
        {
          currStripe = (stripOn) ? "curron" : "curroff";
          %><tr id="current" class="<%=currStripe%>"><%
        }
        else
        {
          currStripe = (stripOn) ? "son" : "soff";
          %><tr class="<%=currStripe%>"><%
        }
        stripOn = !stripOn;
        lineNr++;

      %><td id ="<%=currFreezer.getId()%>" width="1%">
          <a href="javascript:showSlidedHeader('<%=currFreezer.getId()%>')">
             <img id ="headerImg_<%=currFreezer.getId()%>" border='0' src='./images/up_10_b.gif' title="show header before line <%=lineNr%>" alt="">
          </a>
        </td><%

        for (int i = viewStart; i < colCount; i++)
        {
    %><td><%=var[i]%></td><%
        }
          %><td><a onclick="gotoEdit('<%=currFreezer.getId()%>')">
                <img border="0" src="./images/edit_a.gif" onmouseover="eIn(this)"
                     onmouseout="eOut(this)" title="edit" alt="">
              </a></td>
    </tr><%
         }
       }
  %><tr id='slidedHeader' bgcolor="#b3cfff" class="slidedRow">
      <td width="1%">
        <a href='javascript:jumpFocus("bottomLine")'><img border="0" src="./images/down_d_10_b.gif" title="bottom of page" alt=""></a>
      </td><%

      for (int i = viewStart; i < colCount; i++)
      {
    %><td class="headerEven"><%
        if (mdm.getFreezerSortable(keyArray[i]))
        {
      %><a class="<%=tmpHttpSessObj.getFreezerSortId(keyArray[i])%>"
               href='javascript:gotoSorted("<%=keyArray[i]%>")'>
          <%--HREF="<%=response.encodeURL("./ViewFreezers.jsp")+"?sortCol="+keyArray[i]+"&perstotal="+total%>"
          --%><%=mdm.getFreezerLongName(keyArray[i])%></a><%
        }
        else
        {
      %><b><%=mdm.getFreezerLongName(keyArray[i])%></b><%
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
<%=vfm.getFreezerControlString()%>
<%} %>
</form>
<%@include file="Footer.jsp"%>



