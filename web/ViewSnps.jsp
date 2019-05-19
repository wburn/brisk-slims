<%String lapasId = "ViewSnps";%>
<%@include file="Header.jsp"%>
<%
  if ( tmpHttpSessObj.getCurrentUser() == null){//test for new session
    pageContext.forward(response.encodeURL("./LogIn.jsp"));
    return;
  }
  ViewSnpManager vfm = tmpHttpSessObj.getViewSnpManager();
  String tmpString = request.getParameter("sortCol");
  if (tmpString != null){
    tmpHttpSessObj.setSnpSortCol(tmpString);
    vfm.setSnpFirst();
  }
  else
  if ((tmpString = request.getParameter("add")) != null) {
    vfm.changeSnpTotal( 1);
  }
  else
  if ((tmpString = request.getParameter("del")) != null) {
    vfm.changeSnpTotal( -1);
  }
  else
  if ((tmpString = request.getParameter("previous")) != null) {
    vfm.setSnpPrevious();
  }
  else
  if ((tmpString = request.getParameter("next")) != null) {
    vfm.setSnpNext();
  }
  else
  if ((tmpString = request.getParameter("first")) != null) {
    vfm.setSnpFirst();
  }
  else
  if ((tmpString = request.getParameter("last")) != null) {
    vfm.setSnpLast();
  }
  else
  if ((tmpString = request.getParameter("cancelFilter")) != null) {
    tmpHttpSessObj.cancelFilter();
  }
  
  int step = vfm.getSnpStep();
  int first = vfm.getSnpCurrent();
  int total = tmpHttpSessObj.getAllSnpsCount();
  //int total = vfm.getSnpTotal();
  List snpList = tmpHttpSessObj.getAllSnps( first);
  vfm.setSnpCount( total);
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
function gotoEdit( snId){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./AddSnp.jsp")%>"+"?snId="+snId);
}

function gotoNext(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewSnps.jsp")%>"+"?next=0");
}
function gotoPrevious(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewSnps.jsp")%>"+"?previous=0");
}
function gotoFirst(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewSnps.jsp")%>"+"?first=0");
}
function gotoLast(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewSnps.jsp")%>"+"?last=0");
}
function gotoSorted( key){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewSnps.jsp")%>"+"?sortCol="+key);
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
  action="<%= response.encodeURL("./ViewSnps.jsp") %>"/>
<!--galvas tabula -->
<table class="view_table">
  <thead>
    <tr>
      <td align="left">
        <table class="sample">
          <tr>
            <td class="left" align="left" ><a class="largest">Snps</a></td><%
            if (tmpHttpSessObj.isFilter()){
          %><td>
              <input type="submit" name="cancelFilter" value="Cancel Filter"/>
            </td><%}
          %></tr>
  	  <tr>
            <td class="left" align="center" ><%=vfm.getSnpControlString()%></td>
          </tr>
        </table>
      </td>
      <td align="right">
        <table class="sample"><tr>
          <td align="right" valign="middle">
        <button onclick="gotoEdit(-1)" title="New snp" style="width:150px" type="button">New Snp</button>
</td></tr></table>
</td></tr>
  </thead>
</table>

<!--satura tabula -->
<table class="view_table" style="border-width: 0px; border-spacing: 1px; border-color: #ffffff;" >
  <thead>
    <tr bgcolor="#b3cfff">
      <td width="1%"><a href='javascript:jumpFocus("bottomLine")'><img border="0" src="./images/down_d_10_b.gif" title="bottom of page" alt=""></a></td><%

      String[] keyArray = mdm.getSnpKeyArray();
      int colCount = keyArray.length;
      String cellClassName;

      for (int i = viewStart; i < colCount; i++)
      {
    %><td class="headerEven"><%
        if (mdm.getSnpSortable(keyArray[i])) {
      %><a class="<%=tmpHttpSessObj.getSnpSortId(keyArray[i])%>"
            href='javascript:gotoSorted("<%=keyArray[i]%>")'>
            <%=mdm.getSnpLongName(keyArray[i])%></a><%
        }
        else
        {
      %><b><%=mdm.getSnpLongName(keyArray[i])%></b><%
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
    if (snpList != null)
    {
      byte[] valueIndexArray = mdm.getSnpIndexArray();
      Iterator iter = snpList.iterator();
      String currStripe;
      Long currId = tmpHttpSessObj.getCurrentSnpId();
      boolean stripOn = true;
      int lineNr = 0;

      while (iter.hasNext())
      {
        Snp currSnp = (Snp) iter.next();
        String[] var = currSnp.getValueArray(valueIndexArray, colCount);

        if (currSnp.getId().equals(currId))
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

      %><td id ="<%=currSnp.getId()%>" width="1%">
          <a href="javascript:showSlidedHeader('<%=currSnp.getId()%>')">
             <img id ="headerImg_<%=currSnp.getId()%>" border='0' src='./images/up_10_b.gif' title="show header before line <%=lineNr%>" alt="">
          </a>
        </td><%

        for (int i = viewStart; i < colCount; i++)
        {
            // actual field values written here:
            // want null values to appear blank
    %><td><%=(var[i]!=null)?var[i]:""%></td><%
        }
          %><td><a onclick="gotoEdit('<%=currSnp.getId()%>')">
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
        if (mdm.getSnpSortable(keyArray[i]))
        {
      %><a class="<%=tmpHttpSessObj.getSnpSortId(keyArray[i])%>"
               href='javascript:gotoSorted("<%=keyArray[i]%>")'>
          <%--HREF="<%=response.encodeURL("./ViewSnps.jsp")+"?sortCol="+keyArray[i]+"&perstotal="+total%>"
          --%><%=mdm.getSnpLongName(keyArray[i])%></a><%
        }
        else
        {
      %><b><%=mdm.getSnpLongName(keyArray[i])%></b><%
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
<%=vfm.getSnpControlString()%>
<%} %>
</form>
<%@include file="Footer.jsp"%>



