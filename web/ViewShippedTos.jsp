<%String lapasId = "ViewShippedTos";%>
<%@include file="Header.jsp"%>
<%
  if ( tmpHttpSessObj.getCurrentUser() == null){//test for new session
    pageContext.forward(response.encodeURL("./LogIn.jsp"));
    return;
  }
  ViewShippedToManager vhm = tmpHttpSessObj.getViewShippedToManager();
  String tmpString = request.getParameter("sortCol");
  if (tmpString != null){
    tmpHttpSessObj.setShippedToSortCol(tmpString);
    vhm.setShippedToFirst();
  }
  else
  if ((tmpString = request.getParameter("add")) != null) {
    vhm.changeShippedToTotal( 1);
  }
  else
  if ((tmpString = request.getParameter("del")) != null) {
    vhm.changeShippedToTotal( -1);
  }
  else
  if ((tmpString = request.getParameter("previous")) != null) {
    vhm.setShippedToPrevious();
  }
  else
  if ((tmpString = request.getParameter("next")) != null) {
    vhm.setShippedToNext();
  }
  else
  if ((tmpString = request.getParameter("first")) != null) {
    vhm.setShippedToFirst();
  }
  else
  if ((tmpString = request.getParameter("last")) != null) {
    vhm.setShippedToLast();
  }
  else
  if ((tmpString = request.getParameter("cancelFilter")) != null) {
    tmpHttpSessObj.cancelFilter();
  }
  int step = vhm.getShippedToStep();
  int first = vhm.getShippedToCurrent();
  int total = tmpHttpSessObj.getAllSubjectsCount();
  List shippedToList = tmpHttpSessObj.getAllShippedTo( first);
  vhm.setShippedToCount( shippedToList.size());
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
function gotoEdit( shipId){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./AddShippedTo.jsp")%>"+"?shipId="+shipId);
}

function gotoNext(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewShippedTos.jsp")%>"+"?next=0");
}
function gotoPrevious(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewShippedTos.jsp")%>"+"?previous=0");
}
function gotoFirst(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewShippedTos.jsp")%>"+"?first=0");
}
function gotoLast(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewShippedTos.jsp")%>"+"?last=0");
}
function gotoSorted( key){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewShippedTos.jsp")%>"+"?sortCol="+key);
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

<form name="fForm" method="POST"  onsubmit="return checkSubmitFlag()"
  action="<%= response.encodeURL("./ViewShippedTos.jsp") %>"/>
<!--galvas tabula -->
<table class="view_table">
  <thead>
    <tr>
      <td align="left">
        <table class="sample">
          <tr>
            <td class="left" align="left" ><a class="largest">Shipping Locations</a></td><%
            if (tmpHttpSessObj.isFilter()){
          %><td>
              <input type="submit" name="cancelFilter" value="Cancel Filter"/>
            </td><%}
          %></tr>
  	  <tr>
            <td class="left" align="center" ><%=vhm.getShippedToControlString()%></td>
          </tr>
        </table>
      </td>
      <td align="right">
        <table class="sample"><tr>
          <td align="right" valign="middle">
        <button onclick="gotoEdit(-1)" title="New shippedTo" style="width:170px" type="button">New Shipping Location</button>
</td></tr></table>
      </td></tr>
  </thead>
</table>

<!--satura tabula -->
<table class="view_table" style="border-width: 0px; border-spacing: 1px; border-color: #ffffff;" >
  <thead>
    <tr bgcolor="#b3cfff">
      <td width="1%"><a href='javascript:jumpFocus("bottomLine")'><img border="0" src="./images/down_d_10_b.gif" title="bottom of page" alt=""></a></td><%

      String[] keyArray = mdm.getShippedToKeyArray();
      int colCount = keyArray.length;
      String cellClassName;

      for (int i = viewStart; i < colCount; i++)
      {
    %><td class="headerEven"><%
        if (mdm.getShippedToSortable(keyArray[i])) {
      %><a class="<%=tmpHttpSessObj.getShippedToSortId(keyArray[i])%>"
            href='javascript:gotoSorted("<%=keyArray[i]%>")'>
            <%=mdm.getShippedToLongName(keyArray[i])%></a><%
        }
        else
        {
      %><b><%=mdm.getShippedToLongName(keyArray[i])%></b><%
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
    if (shippedToList != null)
    {
      byte[] valueIndexArray = mdm.getShippedToIndexArray();
      Iterator iter = shippedToList.iterator();
      String currStripe;
      Long currId = tmpHttpSessObj.getCurrentShippedToId();
      boolean stripOn = true;
      int lineNr = 0;

      while (iter.hasNext())
      {
        ShippedTo ship = (ShippedTo) iter.next();
        String[] var = ship.getValueArrayReadable(valueIndexArray, colCount);

        if (ship.getId().equals(currId))
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

      %><td id ="<%=ship.getId()%>" width="1%">
          <a href="javascript:showSlidedHeader('<%=ship.getId()%>')">
             <img id ="headerImg_<%=ship.getId()%>" border='0' src='./images/up_10_b.gif' title="show header before line <%=lineNr%>" alt="">
          </a>
        </td><%

        for (int i = viewStart; i < colCount; i++)
        {
    %><td><%=var[i]%></td><%
        }
          %><td><a onclick="gotoEdit('<%=ship.getId()%>')">
                <img border="0" src="./images/edit_a.gif" onmouseover="eIn(this)"
                     onmouseout="eOut(this)" title="edit" alt="">
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
        if (mdm.getShippedToSortable(keyArray[i]))
        {
      %><a class="<%=tmpHttpSessObj.getShippedToSortId(keyArray[i])%>"
               href='javascript:gotoSorted("<%=keyArray[i]%>")'>
          <%--HREF="<%=response.encodeURL("./ViewShippedTos.jsp")+"?sortCol="+keyArray[i]+"&perstotal="+total%>"
          --%><%=mdm.getShippedToLongName(keyArray[i])%></a><%
        }
        else
        {
      %><b><%=mdm.getShippedToLongName(keyArray[i])%></b><%
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
<%=vhm.getShippedToControlString()%>
<%} %>
</form>
<%@include file="Footer.jsp"%>


