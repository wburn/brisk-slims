<%String lapasId = "ViewSampleTypes";%>
<%@include file="Header.jsp"%>
<%
  if ( tmpHttpSessObj.getCurrentUser() == null){//test for new session
    pageContext.forward(response.encodeURL("./LogIn.jsp"));
    return;
  }
  ViewSampleTypeManager vfm = tmpHttpSessObj.getViewSampleTypeManager();
  String tmpString = request.getParameter("sortCol");
  if (tmpString != null){
    tmpHttpSessObj.setSampleTypeSortCol(tmpString);
    vfm.setSampleTypeFirst();
  }
  else
  if ((tmpString = request.getParameter("add")) != null) {
    vfm.changeSampleTypeTotal( 1);
  }
  else
  if ((tmpString = request.getParameter("del")) != null) {
    vfm.changeSampleTypeTotal( -1);
  }
  else
  if ((tmpString = request.getParameter("previous")) != null) {
    vfm.setSampleTypePrevious();
  }
  else
  if ((tmpString = request.getParameter("next")) != null) {
    vfm.setSampleTypeNext();
  }
  else
  if ((tmpString = request.getParameter("first")) != null) {
    vfm.setSampleTypeFirst();
  }
  else
  if ((tmpString = request.getParameter("last")) != null) {
    vfm.setSampleTypeLast();
  }
  else
  if ((tmpString = request.getParameter("cancelFilter")) != null) {
    tmpHttpSessObj.cancelFilter();
  }
  int step = vfm.getSampleTypeStep();
  int first = vfm.getSampleTypeCurrent();
  int total = tmpHttpSessObj.getAllSampleTypesCount();
  //int total = vfm.getSampleTypeTotal();
  List sampletypeList = tmpHttpSessObj.getAllSampleTypes( first);
  vfm.setSampleTypeCount( sampletypeList.size());
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
function gotoEdit( sampTypId){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./AddSampleType.jsp")%>"+"?sampTypId="+sampTypId);
}

function gotoNext(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewSampleTypes.jsp")%>"+"?next=0");
}
function gotoPrevious(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewSampleTypes.jsp")%>"+"?previous=0");
}
function gotoFirst(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewSampleTypes.jsp")%>"+"?first=0");
}
function gotoLast(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewSampleTypes.jsp")%>"+"?last=0");
}
function gotoSorted( key){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewSampleTypes.jsp")%>"+"?sortCol="+key);
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
  action="<%= response.encodeURL("./ViewSampleTypes.jsp") %>"/>
<!--galvas tabula -->
<table class="view_table">
  <thead>
    <tr>
      <td align="left">
        <table class="sample">
          <tr>
            <td class="left" align="left" ><a class="largest">Sample Types</a></td><%
            if (tmpHttpSessObj.isFilter()){
          %><td>
              <input type="submit" name="cancelFilter" value="Cancel Filter"/>
            </td><%}
          %></tr>
  	  <tr>
            <td class="left" align="center" ><%=vfm.getSampleTypeControlString()%></td>
          </tr>
        </table>
      </td>
      <td align="right">
        <table class="sample"><tr>
          <td align="right" valign="middle">
        <button onclick="gotoEdit(-1)" title="New sampletype" style="width:150px" type="button">New Sample Type</button>
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

      String[] keyArray = mdm.getSampleTypeKeyArray();
      int colCount = keyArray.length;
      String cellClassName;

      for (int i = viewStart; i < colCount; i++)
      {
    %><td class="headerEven"><%
        if (mdm.getSampleTypeSortable(keyArray[i])) {
      %><a class="<%=tmpHttpSessObj.getSampleTypeSortId(keyArray[i])%>"
            href='javascript:gotoSorted("<%=keyArray[i]%>")'>
            <%=mdm.getSampleTypeLongName(keyArray[i])%></a><%
        }
        else
        {
      %><b><%=mdm.getSampleTypeLongName(keyArray[i])%></b><%
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
    if (sampletypeList != null)
    {
      byte[] valueIndexArray = mdm.getSampleTypeIndexArray();
      Iterator iter = sampletypeList.iterator();
      String currStripe;
      Long currId = tmpHttpSessObj.getCurrentSampleTypeId();
      boolean stripOn = true;
      int lineNr = 0;

      while (iter.hasNext())
      {
        SampleType currSampleType = (SampleType) iter.next();
        String[] var = currSampleType.getValueArrayReadable(valueIndexArray, colCount);

        if (currSampleType.getId().equals(currId))
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

      %><td id ="<%=currSampleType.getId()%>" width="1%">
          <a href="javascript:showSlidedHeader('<%=currSampleType.getId()%>')">
             <img id ="headerImg_<%=currSampleType.getId()%>" border='0' src='./images/up_10_b.gif' title="show header before line <%=lineNr%>" alt="">
          </a>
        </td><%

        for (int i = viewStart; i < colCount; i++)
        {
    %><td><%=var[i]%></td><%
        }
          %><td><a onclick="gotoEdit('<%=currSampleType.getId()%>')">
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
        if (mdm.getSampleTypeSortable(keyArray[i]))
        {
      %><a class="<%=tmpHttpSessObj.getSampleTypeSortId(keyArray[i])%>"
               href='javascript:gotoSorted("<%=keyArray[i]%>")'>
          <%--HREF="<%=response.encodeURL("./ViewSampleTypes.jsp")+"?sortCol="+keyArray[i]+"&perstotal="+total%>"
          --%><%=mdm.getSampleTypeLongName(keyArray[i])%></a><%
        }
        else
        {
      %><b><%=mdm.getSampleTypeLongName(keyArray[i])%></b><%
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
<%=vfm.getSampleTypeControlString()%>
<%} %>
</form>
<%@include file="Footer.jsp"%>



