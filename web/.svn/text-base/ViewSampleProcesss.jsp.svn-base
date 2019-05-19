<%String lapasId = "ViewSampleProcesss";%>
<%@include file="Header.jsp"%>
<%
  if ( tmpHttpSessObj.getCurrentUser() == null){//test for new session
    pageContext.forward(response.encodeURL("./LogIn.jsp"));
    return;
  }
  ViewSampleProcessManager vfm = tmpHttpSessObj.getViewSampleProcessManager();
  String tmpString = request.getParameter("sortCol");
  if (tmpString != null){
    tmpHttpSessObj.setSampleProcessSortCol(tmpString);
    vfm.setSampleProcessFirst();
  }
  else
  if ((tmpString = request.getParameter("add")) != null) {
    vfm.changeSampleProcessTotal( 1);
  }
  else
  if ((tmpString = request.getParameter("del")) != null) {
    vfm.changeSampleProcessTotal( -1);
  }
  else
  if ((tmpString = request.getParameter("previous")) != null) {
    vfm.setSampleProcessPrevious();
  }
  else
  if ((tmpString = request.getParameter("next")) != null) {
    vfm.setSampleProcessNext();
  }
  else
  if ((tmpString = request.getParameter("first")) != null) {
    vfm.setSampleProcessFirst();
  }
  else
  if ((tmpString = request.getParameter("last")) != null) {
    vfm.setSampleProcessLast();
  }
  else
  if ((tmpString = request.getParameter("cancelFilter")) != null) {
    tmpHttpSessObj.cancelFilter();
  }
  int step = vfm.getSampleProcessStep();
  int first = vfm.getSampleProcessCurrent();
  int total = tmpHttpSessObj.getAllSampleProcesssCount();
  //int total = vfm.getSampleProcessTotal();
  List sampleprocessList = tmpHttpSessObj.getAllSampleProcesss( first);
  vfm.setSampleProcessCount( sampleprocessList.size());
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
function gotoEdit( sampProcId){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./AddSampleProcess.jsp")%>"+"?sampProcId="+sampProcId);
}

function gotoNext(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewSampleProcesss.jsp")%>"+"?next=0");
}
function gotoPrevious(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewSampleProcesss.jsp")%>"+"?previous=0");
}
function gotoFirst(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewSampleProcesss.jsp")%>"+"?first=0");
}
function gotoLast(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewSampleProcesss.jsp")%>"+"?last=0");
}
function gotoSorted( key){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewSampleProcesss.jsp")%>"+"?sortCol="+key);
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
  action="<%= response.encodeURL("./ViewSampleProcesss.jsp") %>"/>
<!--galvas tabula -->
<table class="view_table">
  <thead>
    <tr>
      <td align="left">
        <table class="sample">
          <tr>
            <td class="left" align="left" ><a class="largest">Sample Processs</a></td><%
            if (tmpHttpSessObj.isFilter()){
          %><td>
              <input type="submit" name="cancelFilter" value="Cancel Filter"/>
            </td><%}
          %></tr>
  	  <tr>
            <td class="left" align="center" ><%=vfm.getSampleProcessControlString()%></td>
          </tr>
        </table>
      </td>
      <td align="right">
        <table class="sample"><tr>
          <td align="right" valign="middle">
              <button onclick="gotoEdit(-1)" title="New sampleprocess" style="width:150px" type="button">New Sample Process</button>
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

      String[] keyArray = mdm.getSampleProcessKeyArray();
      int colCount = keyArray.length;
      String cellClassName;

      for (int i = viewStart; i < colCount; i++)
      {
    %><td class="headerEven"><%
        if (mdm.getSampleProcessSortable(keyArray[i])) {
      %><a class="<%=tmpHttpSessObj.getSampleProcessSortId(keyArray[i])%>"
            href='javascript:gotoSorted("<%=keyArray[i]%>")'>
            <%=mdm.getSampleProcessLongName(keyArray[i])%></a><%
        }
        else
        {
      %><b><%=mdm.getSampleProcessLongName(keyArray[i])%></b><%
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
    if (sampleprocessList != null)
    {
      byte[] valueIndexArray = mdm.getSampleProcessIndexArray();
      Iterator iter = sampleprocessList.iterator();
      String currStripe;
      Long currId = tmpHttpSessObj.getCurrentSampleProcessId();
      boolean stripOn = true;
      int lineNr = 0;

      while (iter.hasNext())
      {
        SampleProcess currSampleProcess = (SampleProcess) iter.next();
        String[] var = currSampleProcess.getValueArrayReadable(valueIndexArray, colCount);

        if (currSampleProcess.getId().equals(currId))
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

      %><td id ="<%=currSampleProcess.getId()%>" width="1%">
          <a href="javascript:showSlidedHeader('<%=currSampleProcess.getId()%>')">
             <img id ="headerImg_<%=currSampleProcess.getId()%>" border='0' src='./images/up_10_b.gif' title="show header before line <%=lineNr%>" alt="">
          </a>
        </td><%

        for (int i = viewStart; i < colCount; i++)
        {
    %><td><%=var[i]%></td><%
        }
          %><td><a onclick="gotoEdit('<%=currSampleProcess.getId()%>')">
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
        if (mdm.getSampleProcessSortable(keyArray[i]))
        {
      %><a class="<%=tmpHttpSessObj.getSampleProcessSortId(keyArray[i])%>"
               href='javascript:gotoSorted("<%=keyArray[i]%>")'>
          <%--HREF="<%=response.encodeURL("./ViewSampleProcesss.jsp")+"?sortCol="+keyArray[i]+"&perstotal="+total%>"
          --%><%=mdm.getSampleProcessLongName(keyArray[i])%></a><%
        }
        else
        {
      %><b><%=mdm.getSampleProcessLongName(keyArray[i])%></b><%
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
<%=vfm.getSampleProcessControlString()%>
<%} %>
</form>
<%@include file="Footer.jsp"%>



