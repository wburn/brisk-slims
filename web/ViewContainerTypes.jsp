<%String lapasId = "ViewContainerTypes";%>
<%@include file="Header.jsp"%>
<%
  if ( tmpHttpSessObj.getCurrentUser() == null){//test for new session
    pageContext.forward(response.encodeURL("./LogIn.jsp"));
    return;
  }
  ViewContainerTypeManager vContainerTypeM = tmpHttpSessObj.getViewContainerTypeManager();
  String tmpString = request.getParameter("sortCol");
  System.out.println("contTyp tmpString is: "+tmpString);
    System.out.println("contTyp http sess id is: "+tmpHttpSessObj.getContainerTypeSortId("sortOrder"));
      System.out.println("contTyp view cont type manager is: "+vContainerTypeM.getContainerTypeControlString());

    if (tmpString != null){
    tmpHttpSessObj.setContainerTypeSortCol(tmpString);
    vContainerTypeM.setContainerTypeFirst();
  }
  else
  if ((tmpString = request.getParameter("add")) != null) {
    vContainerTypeM.changeContainerTypeTotal( 1);
  }
  else
  if ((tmpString = request.getParameter("del")) != null) {
    vContainerTypeM.changeContainerTypeTotal( -1);
  }
  else
  if ((tmpString = request.getParameter("previous")) != null) {
    vContainerTypeM.setContainerTypePrevious();
  }
  else
  if ((tmpString = request.getParameter("next")) != null) {
    vContainerTypeM.setContainerTypeNext();
  }
  else
  if ((tmpString = request.getParameter("first")) != null) {
    vContainerTypeM.setContainerTypeFirst();
  }
  else
  if ((tmpString = request.getParameter("last")) != null) {
    vContainerTypeM.setContainerTypeLast();
  }
  else
  if ((tmpString = request.getParameter("cancelFilter")) != null) {
    tmpHttpSessObj.cancelFilter();
  }
    System.out.println("contTyp 2");
        System.out.println("contTyp 2, string: "+vContainerTypeM.getContainerTypeControlString());
  int step = vContainerTypeM.getContainerTypeStep();
      System.out.println("contTyp 3");
  int first = vContainerTypeM.getContainerTypeCurrent();
      System.out.println("contTyp 4");
  int total = tmpHttpSessObj.getAllContainerTypesCount();
    System.out.println("contTyp 5, total: "+total);
  //int total = vContainerTypeM.getContainerTypeTotal();
  List containertypeList = tmpHttpSessObj.getAllContainerTypes( first);
  vContainerTypeM.setContainerTypeCount( containertypeList.size());
  MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
      System.out.println("mdm: "+mdm.getClass().toString());
      System.out.println("is mdm null? "+mdm.equals(null));


  // decides whether to show the DB ID
  int viewStart=1;
  if(tmpHttpSessObj.isTechUser()){
      viewStart=0;
  }
   // System.out.println("contTyp 6, mdm: "+mdm.getContainerTypeLongName("1"));

%>
<script  type="text/javascript" language="javascript">
 function setFocus(){
   var x = document.getElementById("current")
   if (x) scrollTo(0,x.offsetTop);

   initSlidedHeader();
 }
function gotoEdit( contTypeId){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./AddContainerType.jsp")%>"+"?contTypeId="+contTypeId);
}

function gotoNext(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewContainerTypes.jsp")%>"+"?next=0");
}
function gotoPrevious(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewContainerTypes.jsp")%>"+"?previous=0");
}
function gotoFirst(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewContainerTypes.jsp")%>"+"?first=0");
}
function gotoLast(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewContainerTypes.jsp")%>"+"?last=0");
}
function gotoSorted( key){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewContainerTypes.jsp")%>"+"?sortCol="+key);
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

<%System.out.println("got to form fFrom");%>
<form name="fForm" method="POST"  onsubmit="return checkSubmitFlag()"
  action="<%= response.encodeURL("./ViewContainerTypes.jsp") %>"/>
<%System.out.println("got to galvas tabula");%>
<!--galvas tabula -->
<table class="view_table">
  <thead>
    <tr>
      <td align="left">
        <table class="sample">
          <tr>
            <td class="left" align="left" ><a class="largest">Container Types</a></td><%
            if (tmpHttpSessObj.isFilter()){
          %><td>
              <input type="submit" name="cancelFilter" value="Cancel Filter"/>
            </td><%}
          %></tr>
  	  <tr>
            <td class="left" align="center" ><%=vContainerTypeM.getContainerTypeControlString()%></td>
          </tr>
        </table>
      </td>
      <td align="right">
        <table class="sample"><tr>
          <td align="right" valign="middle"><button onclick="gotoEdit(-1)" title="New containertype" style="width:150px" type="button">New Container Type</button>
</td></tr></table>
      </td><%

  %></tr>
  </thead>
</table>
<%System.out.println("got to satura tabula");%>
<!--satura tabula -->
<table class="view_table" style="border-width: 0px; border-spacing: 1px; border-color: #ffffff;" >
  <thead>
    <tr bgcolor="#b3cfff">
      <td width="1%"><a href='javascript:jumpFocus("bottomLine")'><img border="0" src="./images/down_d_10_b.gif" title="bottom of page" alt=""></a></td><%
      // getContainerTypeKeyArray() returns column header names
      String[] keyArray = mdm.getContainerTypeKeyArray();
      int colCount = keyArray.length;
      String cellClassName;
System.out.println("col count: "+keyArray.length);
System.out.println("key array: "+keyArray[0]);
      for (int i = viewStart; i < colCount; i++)
      {
    %><td class="headerEven"><%
        if (mdm.getContainerTypeSortable(keyArray[i])) {
      %><a class="<%=tmpHttpSessObj.getContainerTypeSortId(keyArray[i])%>"
            href='javascript:gotoSorted("<%=keyArray[i]%>")'>
            <%=mdm.getContainerTypeLongName(keyArray[i])%></a><%
        }
        else
        {
      %><b><%=mdm.getContainerTypeLongName(keyArray[i])%></b><%
        }
    %></td><%
      }
    %>
      <td style="width:30px" >
              <b>Edit</b>
      </td>
    </tr>
  </thead>
  <tbody>
      <%
    // actual data rows
    if (containertypeList != null)
    {
      byte[] valueIndexArray = mdm.getContainerTypeIndexArray();
      System.out.println("index array length: "+valueIndexArray.length);
      System.out.println("index array [0]: "+valueIndexArray[0]);
      Iterator iter = containertypeList.iterator();
      String currStripe;
      Long currId = tmpHttpSessObj.getCurrentContainerTypeId();
       boolean stripOn = true;
      int lineNr = 0;

      while (iter.hasNext())
      {
        ContainerType currContainerType = (ContainerType) iter.next();
        String[] var = currContainerType.getValueArrayReadable(valueIndexArray, colCount);
      System.out.println("value array [0]"+var[0]);
        if (currContainerType.getId().equals(currId))
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

      %><td id ="<%=currContainerType.getId()%>" width="1%">
          <a href="javascript:showSlidedHeader('<%=currContainerType.getId()%>')">
             <img id ="headerImg_<%=currContainerType.getId()%>" border='0' src='./images/up_10_b.gif' title="show header before line <%=lineNr%>" alt="">
          </a>
        </td><%

        for (int i = viewStart; i < colCount; i++)
        { // actual data fields are stored in var[]
    %><td><%=var[i]%></td><%
        }
          %><td><a onclick="gotoEdit('<%=currContainerType.getId()%>')">
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
        if (mdm.getContainerTypeSortable(keyArray[i]))
        {
      %><a class="<%=tmpHttpSessObj.getContainerTypeSortId(keyArray[i])%>"
               href='javascript:gotoSorted("<%=keyArray[i]%>")'>
          <%--HREF="<%=response.encodeURL("./ViewContainerTypes.jsp")+"?sortCol="+keyArray[i]+"&perstotal="+total%>"
          --%><%=mdm.getContainerTypeLongName(keyArray[i])%></a><%
        }
        else
        {
      %><b><%=mdm.getContainerTypeLongName(keyArray[i])%></b><%
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
<%=vContainerTypeM.getContainerTypeControlString()%>
<%} %>
</form>
<%@include file="Footer.jsp"%>



