<%String lapasId = "ViewCohorts";%>
<%@include file="Header.jsp"%>
<%
  if ( tmpHttpSessObj.getCurrentUser() == null){//test for new session
    pageContext.forward(response.encodeURL("./LogIn.jsp"));
    return;
  }
  ViewCohortManager vfm = tmpHttpSessObj.getViewCohortManager();
  String tmpString = request.getParameter("sortCol");
  if (tmpString != null){
    tmpHttpSessObj.setCohortSortCol(tmpString);
    vfm.setCohortFirst();
  }
  else
  if ((tmpString = request.getParameter("add")) != null) {
    vfm.changeCohortTotal( 1);
  }
  else
  if ((tmpString = request.getParameter("del")) != null) {
    vfm.changeCohortTotal( -1);
  }
  else
  if ((tmpString = request.getParameter("previous")) != null) {
    vfm.setCohortPrevious();
  }
  else
  if ((tmpString = request.getParameter("next")) != null) {
    vfm.setCohortNext();
  }
  else
  if ((tmpString = request.getParameter("first")) != null) {
    vfm.setCohortFirst();
  }
  else
  if ((tmpString = request.getParameter("last")) != null) {
    vfm.setCohortLast();
  }
  else
  if ((tmpString = request.getParameter("cancelFilter")) != null) {
    tmpHttpSessObj.cancelFilter();
  }
  int step = vfm.getCohortStep();
  int first = vfm.getCohortCurrent();
  int total = tmpHttpSessObj.getAllCohortsCount();
  //int total = vfm.getCohortTotal();
  List cohortList = tmpHttpSessObj.getAllCohorts( first);
  vfm.setCohortCount( cohortList.size());
  MetadataManager mdm = tmpHttpSessObj.getMetadataManager();

  // decides whether to show the DB ID
  int viewStart=1;
  if(tmpHttpSessObj.isTechUser()){
      viewStart=0;
  }

%>
<script  type="text/javascript" language="javascript">
 function setFocus(){
   var x = document.getElementById("current");
   if (x) scrollTo(0,x.offsetTop);

   initSlidedHeader();
 }
function gotoEdit( cohoId){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./AddCohort.jsp")%>"+"?cohoId="+cohoId);
}

function gotoNext(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewCohorts.jsp")%>"+"?next=0");
}
function gotoPrevious(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewCohorts.jsp")%>"+"?previous=0");
}
function gotoFirst(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewCohorts.jsp")%>"+"?first=0");
}
function gotoLast(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewCohorts.jsp")%>"+"?last=0");
}
function gotoSorted( key){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewCohorts.jsp")%>"+"?sortCol="+key);
}
function eIn(image){
  image.src="./images/edit_b.gif";
}
function eOut(image){
  image.src="./images/edit_a.gif";
}

</script>

<!--galvas tabula -->
<form name="navFormB">
<%@include file="AdminMenu.jsp"%>
</form>
<table class="view_table">
  <thead>
    <tr>
      <td align="left">
        <table class="sample" frame="box">
          <tr>
            <td class="left1" align="right" ><a class="largest">Cohorts --</a></td>
<td align="left" class="right1" borderspacing="0" ><a class="larger"><%=vfm.getCohortControlString()%></td>
          </tr>
          <tr>
          <td colspan=2 >
                           </td></tr>
<form name="fForm" method="POST"  onsubmit="return checkSubmitFlag()"
  action="<%= response.encodeURL("./ViewCohorts.jsp") %>"/>
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
        <button onclick="gotoEdit(-1)" title="New cohort" style="width:150px" type="button">New Cohort</button>
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

      String[] keyArray = mdm.getCohortKeyArray();
      int colCount = keyArray.length;
      String cellClassName;

      for (int i = viewStart; i < colCount; i++)
      {
    %><td class="headerEven"><%
        if (mdm.getCohortSortable(keyArray[i])) {
      %><a class="<%=tmpHttpSessObj.getCohortSortId(keyArray[i])%>"
            href='javascript:gotoSorted("<%=keyArray[i]%>")'>
            <%=mdm.getCohortLongName(keyArray[i])%></a><%
        }
        else
        {
      %><b><%=mdm.getCohortLongName(keyArray[i])%></b><%
        }
    %></td><%
      }%>
      <td style="width:30px" >
              <b>Edit</b>
      </td>
    </tr>
  </thead>
  <tbody><%
    // actual data rows
    if (cohortList != null)
    {
      byte[] valueIndexArray = mdm.getCohortIndexArray();
      Iterator iter = cohortList.iterator();
      String currStripe;
      Long currId = tmpHttpSessObj.getCurrentCohortId();
      boolean stripOn = true;
      int lineNr = 0;

      while (iter.hasNext())
      {
        Cohort currCohort = (Cohort) iter.next();
        String[] var = currCohort.getValueArrayReadable(valueIndexArray, colCount);

        if (currCohort.getId().equals(currId))
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

      %><td id ="<%=currCohort.getId()%>" width="1%">
          <a href="javascript:showSlidedHeader('<%=currCohort.getId()%>')">
             <img id ="headerImg_<%=currCohort.getId()%>" border='0' src='./images/up_10_b.gif' title="show header before line <%=lineNr%>" alt="">
          </a>
        </td><%

        for (int i = viewStart; i < colCount; i++)
        {
    %><td><%=var[i]%></td><%
        }
          %><td><a onclick="gotoEdit('<%=currCohort.getId()%>')">
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
        if (mdm.getCohortSortable(keyArray[i]))
        {
      %><a class="<%=tmpHttpSessObj.getCohortSortId(keyArray[i])%>"
               href='javascript:gotoSorted("<%=keyArray[i]%>")'>
          <%--HREF="<%=response.encodeURL("./ViewCohorts.jsp")+"?sortCol="+keyArray[i]+"&perstotal="+total%>"
          --%><%=mdm.getCohortLongName(keyArray[i])%></a><%
        }
        else
        {
      %><b><%=mdm.getCohortLongName(keyArray[i])%></b><%
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
<%=vfm.getCohortControlString()%>
<%} %>
</form>
<%@include file="Footer.jsp"%>



