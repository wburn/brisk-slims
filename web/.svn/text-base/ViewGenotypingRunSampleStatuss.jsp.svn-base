<%String lapasId = "ViewGenotypingRunSampleStatuss";%>
<%@include file="Header.jsp"%>
<%
  if ( tmpHttpSessObj.getCurrentUser() == null){//test for new session
    pageContext.forward(response.encodeURL("./LogIn.jsp"));
    return;
  }
  ViewGenotypingRunSampleStatusManager vfm = tmpHttpSessObj.getViewGenotypingRunSampleStatusManager();
  String tmpString = request.getParameter("sortCol");
  if (tmpString != null){
    tmpHttpSessObj.setGenotypingRunSampleStatusSortCol(tmpString);
    vfm.setGenotypingRunSampleStatusFirst();
  }
  else
  if ((tmpString = request.getParameter("add")) != null) {
    vfm.changeGenotypingRunSampleStatusTotal( 1);
  }
  else
  if ((tmpString = request.getParameter("del")) != null) {
    vfm.changeGenotypingRunSampleStatusTotal( -1);
  }
  else
  if ((tmpString = request.getParameter("previous")) != null) {
    vfm.setGenotypingRunSampleStatusPrevious();
  }
  else
  if ((tmpString = request.getParameter("next")) != null) {
    vfm.setGenotypingRunSampleStatusNext();
  }
  else
  if ((tmpString = request.getParameter("first")) != null) {
    vfm.setGenotypingRunSampleStatusFirst();
  }
  else
  if ((tmpString = request.getParameter("last")) != null) {
    vfm.setGenotypingRunSampleStatusLast();
  }
  else
  if ((tmpString = request.getParameter("cancelFilter")) != null) {
    tmpHttpSessObj.cancelFilter();
  }
  int step = vfm.getGenotypingRunSampleStatusStep();
  int first = vfm.getGenotypingRunSampleStatusCurrent();
  int total = tmpHttpSessObj.getAllGenotypingRunSampleStatussCount();
  //int total = vfm.getGenotypingRunSampleStatusTotal();
  List genotypingrunsamplestatusList = tmpHttpSessObj.getAllGenotypingRunSampleStatuss( first);
  vfm.setGenotypingRunSampleStatusCount( genotypingrunsamplestatusList.size());
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
function gotoEdit( containerContentsID){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./AddGenotypingRunSampleStatus.jsp")%>"+"?containerContentsID="+containerContentsID);
}

function gotoNext(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewGenotypingRunSampleStatuss.jsp")%>"+"?next=0");
}
function gotoPrevious(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewGenotypingRunSampleStatuss.jsp")%>"+"?previous=0");
}
function gotoFirst(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewGenotypingRunSampleStatuss.jsp")%>"+"?first=0");
}
function gotoLast(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewGenotypingRunSampleStatuss.jsp")%>"+"?last=0");
}
function gotoSorted( key){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewGenotypingRunSampleStatuss.jsp")%>"+"?sortCol="+key);
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
  action="<%= response.encodeURL("./ViewGenotypingRunSampleStatuss.jsp") %>"/>
<!--galvas tabula -->
<table class="view_table">
  <thead>
    <tr>
      <td align="left">
        <table class="sample">
          <tr>
            <td class="left" align="left" ><a class="largest">GenotypingRunSampleStatuss</a></td><%
            if (tmpHttpSessObj.isFilter()){
          %><td>
              <input type="submit" name="cancelFilter" value="Cancel Filter"/>
            </td><%}
          %></tr>
  	  <tr>
            <td class="left" align="center" ><%=vfm.getGenotypingRunSampleStatusControlString()%></td>
          </tr>
        </table>
      </td>
      <td align="right">
        <table class="sample"><tr>
          <td align="right" valign="middle">
              <button onclick="gotoEdit(-1)" title="New genotypingrunsamplestatus" style="width:150px" type="button">New GenotypingRunSampleStatus</button>

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

      String[] keyArray = mdm.getGenotypingRunSampleStatusKeyArray();
      int colCount = keyArray.length;
      String cellClassName;

      for (int i = viewStart; i < colCount; i++)
      {
    %><td class="headerEven"><%
        if (mdm.getGenotypingRunSampleStatusSortable(keyArray[i])) {
      %><a class="<%=tmpHttpSessObj.getGenotypingRunSampleStatusSortId(keyArray[i])%>"
            href='javascript:gotoSorted("<%=keyArray[i]%>")'>
            <%=mdm.getGenotypingRunSampleStatusLongName(keyArray[i])%></a><%
        }
        else
        {
      %><b><%=mdm.getGenotypingRunSampleStatusLongName(keyArray[i])%></b><%
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
    if (genotypingrunsamplestatusList != null)
    {
      byte[] valueIndexArray = mdm.getGenotypingRunSampleStatusIndexArray();
      Iterator iter = genotypingrunsamplestatusList.iterator();
      String currStripe;
      Long currId = tmpHttpSessObj.getCurrentGenotypingRunSampleStatusId();
      boolean stripOn = true;
      int lineNr = 0;

      while (iter.hasNext())
      {
        GenotypingRunSampleStatus currGenotypingRunSampleStatus = (GenotypingRunSampleStatus) iter.next();
        String[] var = currGenotypingRunSampleStatus.getValueArray(valueIndexArray, colCount);

        if (currGenotypingRunSampleStatus.getId().equals(currId))
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

      %><td id ="<%=currGenotypingRunSampleStatus.getId()%>" width="1%">
          <a href="javascript:showSlidedHeader('<%=currGenotypingRunSampleStatus.getId()%>')">
             <img id ="headerImg_<%=currGenotypingRunSampleStatus.getId()%>" border='0' src='./images/up_10_b.gif' title="show header before line <%=lineNr%>" alt="">
          </a>
        </td><%

        for (int i = viewStart; i < colCount; i++)
        {
            // actual field values written here:
            // want null values to appear blank
    %><td><%=(var[i]!=null)?var[i]:""%></td><%
        }
          %><td><a onclick="gotoEdit('<%=currGenotypingRunSampleStatus.getId()%>')">
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
        if (mdm.getGenotypingRunSampleStatusSortable(keyArray[i]))
        {
      %><a class="<%=tmpHttpSessObj.getGenotypingRunSampleStatusSortId(keyArray[i])%>"
               href='javascript:gotoSorted("<%=keyArray[i]%>")'>
          <%--HREF="<%=response.encodeURL("./ViewGenotypingRunSampleStatuss.jsp")+"?sortCol="+keyArray[i]+"&perstotal="+total%>"
          --%><%=mdm.getGenotypingRunSampleStatusLongName(keyArray[i])%></a><%
        }
        else
        {
      %><b><%=mdm.getGenotypingRunSampleStatusLongName(keyArray[i])%></b><%
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
<%=vfm.getGenotypingRunSampleStatusControlString()%>
<%} %>
</form>
<%@include file="Footer.jsp"%>



