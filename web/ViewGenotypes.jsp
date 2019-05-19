<%String lapasId = "ViewGenotypes";%>
<%@include file="Header.jsp"%>
<%
  if ( tmpHttpSessObj.getCurrentUser() == null){//test for new session
    pageContext.forward(response.encodeURL("./LogIn.jsp"));
    return;
  }
  ViewGenotypeManager vfm = tmpHttpSessObj.getViewGenotypeManager();
  String tmpString = request.getParameter("sortCol");
  String ccId = request.getParameter("ccId");
  if (tmpString != null){
    tmpHttpSessObj.setGenotypeSortCol(tmpString);
    vfm.setGenotypeFirst();
  }
  else
  if ((tmpString = request.getParameter("add")) != null) {
    vfm.changeGenotypeTotal( 1);
  }
  else
  if ((tmpString = request.getParameter("del")) != null) {
    vfm.changeGenotypeTotal( -1);
  }
  else
  if ((tmpString = request.getParameter("previous")) != null) {
    vfm.setGenotypePrevious();
  }
  else
  if ((tmpString = request.getParameter("next")) != null) {
    vfm.setGenotypeNext();
  }
  else
  if ((tmpString = request.getParameter("first")) != null) {
    vfm.setGenotypeFirst();
  }
  else
  if ((tmpString = request.getParameter("last")) != null) {
    vfm.setGenotypeLast();
  }
  else
  if ((tmpString = request.getParameter("cancelFilter")) != null) {
    tmpHttpSessObj.cancelFilter();
  }
  int step = vfm.getGenotypeStep();
  int first = vfm.getGenotypeCurrent();
  int total;
  //int total = vfm.getGenotypeTotal();
    List genotypeList;
              if (ccId != null && (!ccId.equals("")) && ccId.indexOf("null") < 0 && ccId.length() > 0) {
                if (!tmpHttpSessObj.isFilter()){
                    genotypeList = tmpHttpSessObj.getAllGenotypes(first, ccId);
                    total = tmpHttpSessObj.getAllGenotypesCount(ccId);
                }
                else {
                    genotypeList = tmpHttpSessObj.getAllGenotypes(first);
                    total = tmpHttpSessObj.getAllGenotypesCount();
                }
            } else {
                genotypeList = tmpHttpSessObj.getAllGenotypes(first);
                total = tmpHttpSessObj.getAllGenotypesCount();

            }

  vfm.setGenotypeCount( total );
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
 //Feature not fully working, need to be worked on. May not even be nescessary
//function gotoEdit( genotypId){
//  if (checkSubmitFlag()) window.location.assign(
//         "<%--=response.encodeURL("./AddGenotype.jsp")--%>"+"?genotypId="+genotypId);
//}

function gotoNext(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewGenotypes.jsp")%>"+"?next=0"+"&ccId="+<%=ccId%>);
}
function gotoPrevious(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewGenotypes.jsp")%>"+"?previous=0"+"&ccId="+<%=ccId%>);
}
function gotoFirst(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewGenotypes.jsp")%>"+"?first=0"+"&ccId="+<%=ccId%>);
}
function gotoLast(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewGenotypes.jsp")%>"+"?last=0"+"&ccId="+<%=ccId%>);
}
function gotoSorted( key){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewGenotypes.jsp")%>"+"?sortCol="+key+"&ccId="+<%=ccId%>);
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
  action="<%= response.encodeURL("./ViewGenotypes.jsp") %>"/>
<!--galvas tabula -->
<table class="view_table">
  <thead>
    <tr>
      <td align="left">
        <table class="sample">
          <tr>
            <td class="left" align="left" ><a class="largest">Genotypes</a></td><%
            if (tmpHttpSessObj.isFilter()){
          %><td>
              <input type="submit" name="cancelFilter" value="Cancel Filter"/>
            </td><%}
          %></tr>
  	  <tr>
            <td class="left" align="center" ><%=vfm.getGenotypeControlString()%></td>
          </tr>
        </table>
      </td>
      <td align="right">
<!--Feature temporarily commented out, may not be necessary
    <table class="sample"><tr>
          <td align="right" valign="middle">
              <button onclick="gotoEdit(-1)" title="New genotype" style="width:150px" type="button">New Genotype</button>
</td></tr></table>-->
      </td><%

  %></tr>
  </thead>
</table>

<!--satura tabula -->
<table class="view_table" style="border-width: 0px; border-spacing: 1px; border-color: #ffffff;" >
  <thead>
    <tr bgcolor="#b3cfff">
      <td width="1%"><a href='javascript:jumpFocus("bottomLine")'><img border="0" src="./images/down_d_10_b.gif" title="bottom of page" alt=""></a></td><%

      String[] keyArray = mdm.getGenotypeKeyArray();
      int colCount = keyArray.length;
      String cellClassName;

      for (int i = viewStart; i < colCount; i++)
      {
    %><td class="headerEven"><%
        if (mdm.getGenotypeSortable(keyArray[i])) {
      %><a class="<%=tmpHttpSessObj.getGenotypeSortId(keyArray[i])%>"
            href='javascript:gotoSorted("<%=keyArray[i]%>")'>
            <%=mdm.getGenotypeLongName(keyArray[i])%></a><%
        }
        else
        {
      %><b><%=mdm.getGenotypeLongName(keyArray[i])%></b><%
        }
    %></td><%
      }
    %>
<!--      <td style="width:30px" >
              <b>Edit</b>
      </td>-->
    </tr>
  </thead>
  <tbody><%
    // actual data rows
    if (genotypeList != null)
    {
      byte[] valueIndexArray = mdm.getGenotypeIndexArray();
      Iterator iter = genotypeList.iterator();
      String currStripe;
      Long currId = tmpHttpSessObj.getCurrentGenotypeId();
      boolean stripOn = true;
      int lineNr = 0;

      while (iter.hasNext())
      {
        Genotype currGenotype = (Genotype) iter.next();
        String[] var = currGenotype.getValueArray(valueIndexArray, colCount);

        if (currGenotype.getId().equals(currId))
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

      %><td id ="<%=currGenotype.getId()%>" width="1%">
          <a href="javascript:showSlidedHeader('<%=currGenotype.getId()%>')">
             <img id ="headerImg_<%=currGenotype.getId()%>" border='0' src='./images/up_10_b.gif' title="show header before line <%=lineNr%>" alt="">
          </a>
        </td><%

        for (int i = viewStart; i < colCount; i++)
        {
            // actual field values written here:
            // want null values to appear blank
    %><td><%=(var[i]!=null)?var[i]:""%></td><%
        }
          %>

<!--Feature not fully working, need to be worked on          
        <td><a onclick="gotoEdit('<%--=currGenotype.getId()--%>')">
                <img border="0" src="./images/edit_a.gif" onmouseover="eIn(this)"
                     onmouseout="eOut(this)" title="edit" alt="">
              </a></td>-->
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
        if (mdm.getGenotypeSortable(keyArray[i]))
        {
      %><a class="<%=tmpHttpSessObj.getGenotypeSortId(keyArray[i])%>"
               href='javascript:gotoSorted("<%=keyArray[i]%>")'>
          <%--HREF="<%=response.encodeURL("./ViewGenotypes.jsp")+"?sortCol="+keyArray[i]+"&perstotal="+total%>"
          --%><%=mdm.getGenotypeLongName(keyArray[i])%></a><%
        }
        else
        {
      %><b><%=mdm.getGenotypeLongName(keyArray[i])%></b><%
        }
    %></td><%
      }
    %>
<!--    <td>&nbsp;</td>-->
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
<%=vfm.getGenotypeControlString()%>
<%} %>
</form>
<%@include file="Footer.jsp"%>



