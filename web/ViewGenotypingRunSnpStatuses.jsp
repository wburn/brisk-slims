<%String lapasId = "ViewGenotypingRunSnpStatuss";%>
<%@include file="Header.jsp"%>
<%
  if ( tmpHttpSessObj.getCurrentUser() == null){//test for new session
    pageContext.forward(response.encodeURL("./LogIn.jsp"));
    return;
  }
  ViewGenotypingRunSnpStatusManager vfm = tmpHttpSessObj.getViewGenotypingRunSnpStatusManager();
  String tmpString = request.getParameter("sortCol");
  if (tmpString != null){
    tmpHttpSessObj.setGenotypingRunSnpStatusSortCol(tmpString);
    vfm.setGenotypingRunSnpStatusFirst();
  }
  else
  if ((tmpString = request.getParameter("add")) != null) {
    vfm.changeGenotypingRunSnpStatusTotal( 1);
  }
  else
  if ((tmpString = request.getParameter("del")) != null) {
    vfm.changeGenotypingRunSnpStatusTotal( -1);
  }
  else
  if ((tmpString = request.getParameter("previous")) != null) {
    vfm.setGenotypingRunSnpStatusPrevious();
  }
  else
  if ((tmpString = request.getParameter("next")) != null) {
    vfm.setGenotypingRunSnpStatusNext();
  }
  else
  if ((tmpString = request.getParameter("first")) != null) {
    vfm.setGenotypingRunSnpStatusFirst();
  }
  else
  if ((tmpString = request.getParameter("last")) != null) {
    vfm.setGenotypingRunSnpStatusLast();
  }
  else
  if ((tmpString = request.getParameter("cancelFilter")) != null) {
    tmpHttpSessObj.cancelFilter();
  }
  int step = vfm.getGenotypingRunSnpStatusStep();
  int first = vfm.getGenotypingRunSnpStatusCurrent();
  int total = tmpHttpSessObj.getAllGenotypingRunSnpStatussCount();
  //int total = vfm.getGenotypingRunSnpStatusTotal();
  List genotypingrunsnpstatusList = tmpHttpSessObj.getAllGenotypingRunSnpStatuss( first);
  vfm.setGenotypingRunSnpStatusCount( genotypingrunsnpstatusList.size());
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
function gotoEdit( genoRunSnpStatId){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./AddGenotypingRunSnpStatus.jsp")%>"+"?genoRunSnpStatId="+genoRunSnpStatId);
}

function gotoNext(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewGenotypingRunSnpStatuss.jsp")%>"+"?next=0");
}
function gotoPrevious(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewGenotypingRunSnpStatuss.jsp")%>"+"?previous=0");
}
function gotoFirst(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewGenotypingRunSnpStatuss.jsp")%>"+"?first=0");
}
function gotoLast(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewGenotypingRunSnpStatuss.jsp")%>"+"?last=0");
}
function gotoSorted( key){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewGenotypingRunSnpStatuss.jsp")%>"+"?sortCol="+key);
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
  action="<%= response.encodeURL("./ViewGenotypingRunSnpStatuss.jsp") %>"/>
<!--galvas tabula -->
<table class="view_table">
  <thead>
    <tr>
      <td align="left">
        <table class="sample">
          <tr>
            <td class="left" align="left" ><a class="largest">GenotypingRunSnpStatuss</a></td><%
            if (tmpHttpSessObj.isFilter()){
          %><td>
              <input type="submit" name="cancelFilter" value="Cancel Filter"/>
            </td><%}
          %></tr>
  	  <tr>
            <td class="left" align="center" ><%=vfm.getGenotypingRunSnpStatusControlString()%></td>
          </tr>
        </table>
      </td>
      <td align="right">
        <table class="sample"><tr>
          <td align="right" valign="middle">
              <button onclick="gotoEdit(-1)" title="New genotypingrunsnpstatus" style="width:150px" type="button">New GenotypingRunSnpStatus</button>
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

      String[] keyArray = mdm.getGenotypingRunSnpStatusKeyArray();
      int colCount = keyArray.length;
      String cellClassName;

      for (int i = viewStart; i < colCount; i++)
      {
    %><td class="headerEven"><%
        if (mdm.getGenotypingRunSnpStatusSortable(keyArray[i])) {
      %><a class="<%=tmpHttpSessObj.getGenotypingRunSnpStatusSortId(keyArray[i])%>"
            href='javascript:gotoSorted("<%=keyArray[i]%>")'>
            <%=mdm.getGenotypingRunSnpStatusLongName(keyArray[i])%></a><%
        }
        else
        {
      %><b><%=mdm.getGenotypingRunSnpStatusLongName(keyArray[i])%></b><%
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
    if (genotypingrunsnpstatusList != null)
    {
      byte[] valueIndexArray = mdm.getGenotypingRunSnpStatusIndexArray();
      Iterator iter = genotypingrunsnpstatusList.iterator();
      String currStripe;
      Long currId = tmpHttpSessObj.getCurrentGenotypingRunSnpStatusId();
      boolean stripOn = true;
      int lineNr = 0;

      while (iter.hasNext())
      {
        GenotypingRunSnpStatus currGenotypingRunSnpStatus = (GenotypingRunSnpStatus) iter.next();
        String[] var = currGenotypingRunSnpStatus.getValueArray(valueIndexArray, colCount);

        if (currGenotypingRunSnpStatus.getId().equals(currId))
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

      %><td id ="<%=currGenotypingRunSnpStatus.getId()%>" width="1%">
          <a href="javascript:showSlidedHeader('<%=currGenotypingRunSnpStatus.getId()%>')">
             <img id ="headerImg_<%=currGenotypingRunSnpStatus.getId()%>" border='0' src='./images/up_10_b.gif' title="show header before line <%=lineNr%>" alt="">
          </a>
        </td><%

        for (int i = viewStart; i < colCount; i++)
        {
            // actual field values written here:
            // want null values to appear blank
    %><td><%=(var[i]!=null)?var[i]:""%></td><%
        }
          %><td><a onclick="gotoEdit('<%=currGenotypingRunSnpStatus.getId()%>')">
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
        if (mdm.getGenotypingRunSnpStatusSortable(keyArray[i]))
        {
      %><a class="<%=tmpHttpSessObj.getGenotypingRunSnpStatusSortId(keyArray[i])%>"
               href='javascript:gotoSorted("<%=keyArray[i]%>")'>
          <%--HREF="<%=response.encodeURL("./ViewGenotypingRunSnpStatuss.jsp")+"?sortCol="+keyArray[i]+"&perstotal="+total%>"
          --%><%=mdm.getGenotypingRunSnpStatusLongName(keyArray[i])%></a><%
        }
        else
        {
      %><b><%=mdm.getGenotypingRunSnpStatusLongName(keyArray[i])%></b><%
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
<%=vfm.getGenotypingRunSnpStatusControlString()%>
<%} %>
</form>
<%@include file="Footer.jsp"%>



