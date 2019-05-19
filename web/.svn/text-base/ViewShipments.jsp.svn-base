<%String lapasId = "ViewShippedTos";%>
<%@include file="Header.jsp"%>
<%
  if ( tmpHttpSessObj.getCurrentUser() == null){//test for new session
    pageContext.forward(response.encodeURL("./LogIn.jsp"));
    return;
  }
  ViewShipmentManager vhm = tmpHttpSessObj.getViewShipmentManager();
  String tmpString = request.getParameter("sortCol");
  String contId = request.getParameter("contId");
  if (tmpString != null){
    tmpHttpSessObj.setShipmentSortCol(tmpString);
    vhm.setShipmentFirst();
  }
  else
  if ((tmpString = request.getParameter("add")) != null) {
    vhm.changeShipmentTotal( 1);
  }
  else
  if ((tmpString = request.getParameter("del")) != null) {
    vhm.changeShipmentTotal( -1);
  }
  else
  if ((tmpString = request.getParameter("previous")) != null) {
    vhm.setShipmentPrevious();
  }
  else
  if ((tmpString = request.getParameter("next")) != null) {
    vhm.setShipmentNext();
  }
  else
  if ((tmpString = request.getParameter("first")) != null) {
    vhm.setShipmentFirst();
  }
  else
  if ((tmpString = request.getParameter("last")) != null) {
    vhm.setShipmentLast();
  }
  else
  if ((tmpString = request.getParameter("cancelFilter")) != null) {
    tmpHttpSessObj.cancelFilter();
  }
  int step = vhm.getShipmentStep();
  int first = vhm.getShipmentCurrent();
  int total;
  List shipmentList;
              if (contId != null && (!contId.equals("")) && contId.indexOf("null") < 0 && contId.length() > 0) {
                if (!tmpHttpSessObj.isFilter()){
                    shipmentList = tmpHttpSessObj.getAllShipment(first, contId);
                    total = tmpHttpSessObj.getAllShipmentCount(contId);
                }
                else {
                    total = tmpHttpSessObj.getAllShipmentCount();
                    shipmentList = tmpHttpSessObj.getAllShipment(first);
                }
            } else {
                total = tmpHttpSessObj.getAllShipmentCount();
                shipmentList = tmpHttpSessObj.getAllShipment(first);
            }
  vhm.setShipmentCount( shipmentList.size());
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
     var contId = <%=contId%>;
     if (contId != null || contId != ""){
         if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./AddShipment.jsp")%>"+"?shipId="+shipId+"&contId="+contId);
     }
     else {
         if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./AddShipment.jsp")%>"+"?shipId="+shipId);
     }
 }

function gotoView( shipId){
    if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./ViewContainers.jsp")%>"+"?shipId="+shipId);
}

function gotoNext(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewShipments.jsp")%>"+"?next=0");
}
function gotoPrevious(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewShipments.jsp")%>"+"?previous=0");
}
function gotoFirst(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewShipments.jsp")%>"+"?first=0");
}
function gotoLast(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewShipments.jsp")%>"+"?last=0");
}
function gotoSorted( key){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewShipments.jsp")%>"+"?sortCol="+key);
}
function eIn(image){
  image.src="./images/edit_b.gif";
}
function eOut(image){
  image.src="./images/edit_a.gif";
}
function vIn(image){
  image.src="./images/viewLayout_b.gif";
}
function vOut(image){
  image.src="./images/viewLayout_a.gif";
}
</script>

<form name="navFormB">
<%@include file="AdminMenu.jsp"%>
</form>

<form name="fForm" method="POST"  onsubmit="return checkSubmitFlag()"
  action="<%= response.encodeURL("./ViewShipments.jsp") %>"/>
<!--galvas tabula -->
<table class="view_table">
  <thead>
    <tr>
      <td align="left">
        <table class="sample">
          <tr>
            <td class="left" align="left" ><a class="largest">Shipments
                <%
                if (contId!=null && !contId.equals("")){
    Container tmpCont = (Container)tmpHttpSessObj.getObjectById(Container.class, contId);%>
                    for Container <%=tmpCont.getVisibleName()%><%}%></a></td><%
            if (tmpHttpSessObj.isFilter()){
          %><td>
              <input type="submit" name="cancelFilter" value="Cancel Filter"/>
            </td><%}
          %></tr>
  	  <tr>
            <td class="left" align="center" ><%=vhm.getShipmentControlString()%></td>
          </tr>
        </table>
      </td>
    </tr>
  </thead>
</table>

<!--satura tabula -->
<table class="view_table" style="border-width: 0px; border-spacing: 1px; border-color: #ffffff;" >
  <thead>
    <tr bgcolor="#b3cfff">
      <td width="1%"><a href='javascript:jumpFocus("bottomLine")'><img border="0" src="./images/down_d_10_b.gif" title="bottom of page" alt=""></a></td><%

      String[] keyArray = mdm.getShipmentKeyArray();
      int colCount = keyArray.length;
      String cellClassName;

      for (int i = viewStart; i < colCount; i++)
      {
    %><td class="headerEven"><%
        if (mdm.getShipmentSortable(keyArray[i])) {
      %><a class="<%=tmpHttpSessObj.getShipmentSortId(keyArray[i])%>"
            href='javascript:gotoSorted("<%=keyArray[i]%>")'>
            <%=mdm.getShipmentLongName(keyArray[i])%></a><%
        }
        else
        {
      %><b><%=mdm.getShipmentLongName(keyArray[i])%></b><%
        }
    %></td><%
      }
    %>
         <td style="width:30px" >
              <b>Edit</b>
      </td>
      <td style="width:30px" >
          <b>View</b>
      </td>
    </tr>
  </thead>
  <tbody><%
    // actual data rows
    if (shipmentList != null)
    {
      byte[] valueIndexArray = mdm.getShipmentIndexArray();
      Iterator iter = shipmentList.iterator();
      String currStripe;
      Long currId = tmpHttpSessObj.getCurrentShipmentId();
      boolean stripOn = true;
      int lineNr = 0;

      while (iter.hasNext())
      {
        Shipment ship = (Shipment) iter.next();
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
          %><td><a onclick="gotoEdit('<%=ship.getId()%>')"/>
                <img border="0" src="./images/edit_a.gif" onmouseover="eIn(this)"
                     onmouseout="eOut(this)" title="edit" alt=""></td>
            <td><a onclick="gotoView('<%=ship.getId()%>')"/>
                <img border="0" src="./images/viewLayout_a.gif" onmouseover="vIn(this)"
                     onmouseout="vOut(this)" title="view" alt=""></td>
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
        if (mdm.getShipmentSortable(keyArray[i]))
        {
      %><a class="<%=tmpHttpSessObj.getShipmentSortId(keyArray[i])%>"
               href='javascript:gotoSorted("<%=keyArray[i]%>")'>
          <%--HREF="<%=response.encodeURL("./ViewShipments.jsp")+"?sortCol="+keyArray[i]+"&perstotal="+total%>"
          --%><%=mdm.getShipmentLongName(keyArray[i])%></a><%
        }
        else
        {
      %><b><%=mdm.getShipmentLongName(keyArray[i])%></b><%
        }
    %></td><%
      }
    %><td>&nbsp;</td>
    <td>&nbsp;</td>
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
<%=vhm.getShipmentControlString()%>
<%} %>
</form>
<%@include file="Footer.jsp"%>


