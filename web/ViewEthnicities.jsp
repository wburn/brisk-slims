<%String lapasId = "ViewEthnicities";%>
<%@include file="Header.jsp"%>
<%
  if ( tmpHttpSessObj.getCurrentUser() == null){//test for new session
    pageContext.forward(response.encodeURL("./LogIn.jsp"));
    return;
  }
  ViewEthnicityManager vfm = tmpHttpSessObj.getViewEthnicityManager();
  String tmpString = request.getParameter("sortCol");
  if (tmpString != null){
    tmpHttpSessObj.setEthnicitySortCol(tmpString);
    vfm.setEthnicityFirst();
  }
  else
  if ((tmpString = request.getParameter("add")) != null) {
    vfm.changeEthnicityTotal( 1);
  }
  else
  if ((tmpString = request.getParameter("del")) != null) {
    vfm.changeEthnicityTotal( -1);
  }
  else
  if ((tmpString = request.getParameter("previous")) != null) {
    vfm.setEthnicityPrevious();
  }
  else
  if ((tmpString = request.getParameter("next")) != null) {
    vfm.setEthnicityNext();
  }
  else
  if ((tmpString = request.getParameter("first")) != null) {
    vfm.setEthnicityFirst();
  }
  else
  if ((tmpString = request.getParameter("last")) != null) {
    vfm.setEthnicityLast();
  }
  else
  if ((tmpString = request.getParameter("cancelFilter")) != null) {
    tmpHttpSessObj.cancelFilter();
  }
  int step = vfm.getEthnicityStep();
  int first = vfm.getEthnicityCurrent();
  int total = tmpHttpSessObj.getAllEthnicitiesCount();
  //int total = vfm.getEthnicityTotal();
  List ethnicityList = tmpHttpSessObj.getAllEthnicities( first);
  vfm.setEthnicityCount( ethnicityList.size());
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
function gotoEdit( ethnId){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./AddEthnicity.jsp")%>"+"?ethnId="+ethnId);
}

function gotoNext(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewEthnicities.jsp")%>"+"?next=0");
}
function gotoPrevious(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewEthnicities.jsp")%>"+"?previous=0");
}
function gotoFirst(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewEthnicities.jsp")%>"+"?first=0");
}
function gotoLast(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewEthnicities.jsp")%>"+"?last=0");
}
function gotoSorted( key){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewEthnicities.jsp")%>"+"?sortCol="+key);
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
  action="<%= response.encodeURL("./ViewEthnicities.jsp") %>"/>
<!--galvas tabula -->

<table class="view_table">
  <thead>
    <tr>
      <td align="left">
        <table class="sample">
          <tr>
            <td class="left" align="left" ><a class="largest">Ethnicities</a></td><%
            if (tmpHttpSessObj.isFilter()){
          %><td>
              <input type="submit" name="cancelFilter" value="Cancel Filter"/>
            </td><%}
          %></tr>
  	  <tr>
            <td class="left" align="center" ><%=vfm.getEthnicityControlString()%></td>
          </tr>
        </table>
      </td>
      <td align="right">
        <table class="sample"><tr>
          <td align="right" valign="middle">
        <button onclick="gotoEdit(-1)" title="New ethnicity" style="width:150px" type="button">New Ethnicity</button>
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

      String[] keyArray = mdm.getEthnicityKeyArray();
      int colCount = keyArray.length;
      String cellClassName;

      for (int i = viewStart; i < colCount; i++)
      {
    %><td class="headerEven"><%
        if (mdm.getEthnicitySortable(keyArray[i])) {
      %><a class="<%=tmpHttpSessObj.getEthnicitySortId(keyArray[i])%>"
            href='javascript:gotoSorted("<%=keyArray[i]%>")'>
            <%=mdm.getEthnicityShortName(keyArray[i])%></a><%
        }
        else
        {
      %><b><%=mdm.getEthnicityShortName(keyArray[i])%></b><%
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
    if (ethnicityList != null)
    {
      byte[] valueIndexArray = mdm.getEthnicityIndexArray();
      Iterator iter = ethnicityList.iterator();
      String currStripe;
      Long currId = tmpHttpSessObj.getCurrentEthnicityId();
      boolean stripOn = true;
      int lineNr = 0;

      while (iter.hasNext())
      {
        Ethnicity currEthnicity = (Ethnicity) iter.next();
        String[] var = currEthnicity.getValueArray(valueIndexArray, colCount);

        if (currEthnicity.getId().equals(currId))
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

      %><td id ="<%=currEthnicity.getId()%>" width="1%">
          <a href="javascript:showSlidedHeader('<%=currEthnicity.getId()%>')">
             <img id ="headerImg_<%=currEthnicity.getId()%>" border='0' src='./images/up_10_b.gif' title="show header before line <%=lineNr%>" alt="">
          </a>
        </td><%

        for (int i = viewStart; i < colCount; i++)
        {
            // actual field values written here:
            // want null values to appear blank
    %><td><%=(var[i]!=null)?var[i]:""%></td><%
        }
          %><td><a onclick="gotoEdit('<%=currEthnicity.getId()%>')">
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
        if (mdm.getEthnicitySortable(keyArray[i]))
        {
      %><a class="<%=tmpHttpSessObj.getEthnicitySortId(keyArray[i])%>"
               href='javascript:gotoSorted("<%=keyArray[i]%>")'>
          <%--HREF="<%=response.encodeURL("./ViewEthnicities.jsp")+"?sortCol="+keyArray[i]+"&perstotal="+total%>"
          --%><%=mdm.getEthnicityShortName(keyArray[i])%></a><%
        }
        else
        {
      %><b><%=mdm.getEthnicityShortName(keyArray[i])%></b><%
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
<%=vfm.getEthnicityControlString()%>
<%} %>
</form>
<%@include file="Footer.jsp"%>



