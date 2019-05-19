<%-- 
    Document   : SampleSelectorSummary
    Created on : Nov 9, 2009, 5:41:03 PM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%String lapasId = "SampleSelectorSummary";%>
<%@include file="Header.jsp"%>


<%
        if (tmpHttpSessObj.getCurrentUser() == null) {//test for new session
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }

        if (request.getParameter("cancel") != null) {
            tmpHttpSessObj.setCurrentSampleSelector(null);
            tmpHttpSessObj.setCurrentShoppingCartList(null);
            tmpHttpSessObj.setCurrentShoppingList(null);
            response.sendRedirect("./Index.jsp");
            return;
        }

        ViewContainerContentManager vfm = tmpHttpSessObj.getViewContainerContentManager();
        SampleSelector selector = tmpHttpSessObj.getCurrentSampleSelector();

        // decides whether to show the DB ID
        int viewStart = 1;
        if (tmpHttpSessObj.isTechUser()) {
            viewStart = 0;
        }
        // create list, add contents to list, direct to view of list
        if (request.getParameter("finish") != null) {

            // get list name
            String newListName = request.getParameter("listName");

            // try to make shopping list
            int messageNum = tmpHttpSessObj.addShoppingList(newListName,tmpHttpSessObj.getCurrentUser());

            // load new list as current
            if (messageNum == 0) {
                  System.out.println("newListName="+newListName+messageNum);
                  tmpHttpSessObj.setCurrentShoppingCartList(new ShoppingCartList(tmpHttpSessObj, newListName));
            }
            if(messageNum==0){
                selector.fillList();
                //reset selector
                tmpHttpSessObj.setCurrentPlater(null);
                // release write lock on shopping list
                tmpHttpSessObj.releaseLockCurrentShoppingList();
                // direct to viewing list
                response.sendRedirect("./ViewContainerContentsList.jsp");
                return;    
            }
            // if error in list creation (non unique name, likely)
            else{
                System.out.println("Error in list creation.");

%>
<script  type="text/javascript" language="javascript">
    function checkListName(){
        var name = document.getElementById("listName").value;
        if (name != null && name!=""){
            return true;
        }
        else if (name == null || name == "")
        {alert("Descriptive name required for list creation.\nList not created. ");
            return false;}
    }

</script>
<form method="POST" name="searchForm"
          action="<%=response.encodeURL("./SampleSelectorSummary.jsp")%>"
          onsubmit="return checkListName();">

    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>

    <table class="navigate">
        <tr>
            <td class="left" align="left" colspan="2"><a class="largest">Create List For Sample Selection Results</a></td>
        </tr>
    </table>
    <table>
        <tr><td>Your list name ("<%=newListName%>") is already taken, please try again.</td></tr>
        <tr><td>What would you like to name your new list?&nbsp;&nbsp;
        <input type="text" size="30" id="listName" name="listName"/></td>
        <td>
        <input type="submit" name="finish" value="Finalize" title="Accept this selection and create your list." onclick="return checkListName();">
            (This may take a few minutes.)</td></tr><tr><td>
                    If you are not satisfied and would like to start over:
                <input type="submit" name="cancel" value="Cancel Selection">
    </table>
    <br><br>
</form>
<%
            }
}else{
            

        MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
%>
<script  type="text/javascript" language="javascript">
    function setFocus(){
        var x = document.getElementById("current")
        if (x) scrollTo(0,x.offsetTop);

        initSlidedHeader();
    }

 function goNav(){
    var x = document.getElementById("gotoNav").value;
    if(x == "view"){
        gotoCustomize();
    }
}
function gotoCustomize( ){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./EditColumns.jsp")%>"+"?source=list");
}
    function gotoSorted( key){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./ViewContainerContentsList.jsp")%>"+"?sortCol="+key);
    }
        function checkAll(field)
    {
        for (i = 0; i < field.length; i++)
            field[i].checked = true ;
    }
    function uncheckAll(field)
    {
        for (i = 0; i < field.length; i++)
            field[i].checked = false ;
    }


function copyToOtherside(partialID){
    document.getElementById('L'+partialID).checked=true;
    document.getElementById('R'+partialID).checked=true;

}
    function checkListName(){
        var name = document.getElementById("listName").value;
        if (name != null && name!=""){
            return true;
        }
        else if (name == null || name == "")
        {alert("Descriptive name required for list creation.\nList not created.");
            return false;}
    }

</script>
<form name="fForm" method="POST"  onsubmit="return checkSubmitFlag()"
      action="<%= response.encodeURL("./SampleSelectorSummary.jsp")%>"/>
<!--galvas tabula -->
<%

        //  if selection process hasn't begun, make a new selector object
        if(selector == null){
            tmpHttpSessObj.setCurrentSampleSelector(null);
            response.sendRedirect("./ViewSubjectsList.jsp");
            return;

        }
        List containercontentList = selector.getAcceptedContents();

        ArrayList noContents = selector.getNoContentsSubjNames();
        List noMoreMatches = selector.getNoMoreMatchesSubjNames();

        String listName = tmpHttpSessObj.getCurrentShoppingList().getListName();
        if(containercontentList != null){
        vfm.setContainerContentCount(containercontentList.size());
        }

        %>

<table class="view_table">
    <thead>
        <tr>
            <td align="left">
                <table class="sample">
                    <tr>
                        <td class="left" align="left" ><a class="largest">
                            Sample Selection Complete: Summary Screen</a>
                    </tr>
                </table>
            </td>
            <td align="right" valign="middle">
        <select id="gotoNav" >
            <option value="view">Customize sample data view</option>
        </select>
        <button type="button" class="buttonSmall" onclick="goNav()"/>Go</button>
            </td></tr>
    </thead>
</table>

<table style="padding-left:20pt">
   <tr>
    <td class="left" align="left" ><a class="larger">
    These are your final results:</a> </td>
    </tr>
   <% Iterator bads = null;
   if(noContents != null && !noContents.isEmpty()){%>
    <tr><td class="subHeader" align="left" colspan="3">Subjects Without Samples</td></tr>
    <tr><td colspan="3">The following subjects do not have any samples in the database.
    </td></tr>
    <tr><td width="20">&nbsp;</td>
    <td><table style="background-color:#FBFBFB">
    <% bads = noContents.iterator();
        while(bads.hasNext()){%>
        <tr><td><%=bads.next()%></td></tr>
      <%}%></table></td></tr>
    <%}%>
   <%if(noMoreMatches != null && !noMoreMatches.isEmpty()){%>
    <tr><td class="subHeader" align="left" colspan="3">Subjects Without Qualifying Samples</td></tr>
    <tr><td colspan="3">The following subjects either do not have any samples that match your selection profiles
    or do not have any <em>more</em> matching samples (all potential samples have been rejected).
    </td></tr>
    <tr><td width="20">&nbsp;</td>
    <td><table style="background-color:#FBFBFB">
    <%  bads = noMoreMatches.iterator();
        while(bads.hasNext()){%>
        <tr><td><%=bads.next()%></td></tr>
      <%}%></table></td></tr>
   <%}%>


   <%if(containercontentList == null || containercontentList.isEmpty()){%>
<tr><td>&nbsp;</td></tr>
<tr><td class="subHeader" align="left" colspan="3">Best-Match Samples</td></tr>
<tr><td colspan="3">You have no best-match samples.<br>
    </td></tr>
<%}else{%>
<tr><td>&nbsp;</td></tr>
<tr><td class="subHeader" align="left" colspan="3">Best-Match Samples</td></tr>
<tr><td colspan="3">The following <%=containercontentList.size()%> samples are the best matches found according to your profiles and
any rejections you may have made.<br>
    </td></tr>
</table>

<!--satura tabula -->
<table class="view_table" style="border-width: 0px; border-spacing: 1px; border-color: #ffffff;" >
<thead>
    <tr bgcolor="#b3cfff">
        <td width="1%"><a href='javascript:jumpFocus("bottomLine")'><img border="0" src="./images/down_d_10_b.gif" title="bottom of page" alt=""></a></td>
        <td align="center"></td>
        <%
        String[] keyArray = mdm.getContainerContentKeyArray();
        int colCount = keyArray.length;
        String cellClassName;

        for (int i = viewStart; i < colCount; i++) {
        %><td class="headerEven"><%
                  if (mdm.getContainerContentSortable(keyArray[i])) {
            if(tmpHttpSessObj.getContainerContentSortId(keyArray[i]).equals("normal")){%>
            <a class="<%=tmpHttpSessObj.getContainerContentSortId(keyArray[i])%>"
            href='javascript:gotoSorted("<%=keyArray[i]%>")'>
            <%=mdm.getContainerContentLongName(keyArray[i])%>
            <img border="0" src="./images/sort_sm_none.gif" title="Sort" alt=""></a>
                <%}
            if(tmpHttpSessObj.getContainerContentSortId(keyArray[i]).equals("normalo")){%>
            <a class="<%=tmpHttpSessObj.getContainerContentSortId(keyArray[i])%>"
            href='javascript:gotoSorted("<%=keyArray[i]%>")'>
            <%=mdm.getContainerContentLongName(keyArray[i])%>
            <img border="0" src="./images/sort_sm_down.gif" title="Sort" alt=""></a>
            <%}
            if(tmpHttpSessObj.getContainerContentSortId(keyArray[i]).equals("normalu")){%>
            <a class="<%=tmpHttpSessObj.getContainerContentSortId(keyArray[i])%>"
            href='javascript:gotoSorted("<%=keyArray[i]%>")'>
            <%=mdm.getContainerContentLongName(keyArray[i])%>
            <img border="0" src="./images/sort_sm_up.gif" title="Sort" alt=""></a>
                <%}%>

            <%
        } else {
            %><b><%=mdm.getContainerContentLongName(keyArray[i])%></b><%
                  }
        %></td><%
        }
        %>
        <td>
        </td>
    </tr>
</thead>
<tbody><%
// actual data rows
        if (containercontentList != null) {
            byte[] valueIndexArray = mdm.getContainerContentIndexArray();
            Iterator iter = containercontentList.iterator();
            String currStripe;
            Long currId = tmpHttpSessObj.getCurrentContainerContentId();
            boolean stripOn = true;
            String[] var;
            int lineNr = 0;
            String inList = "";
            String currSubject = "";
            String prevSubject = "";
            ContainerContent currContainerContent;
            while (iter.hasNext()) {
                String currContainerContentID = iter.next().toString();
                currContainerContent = (ContainerContent) tmpHttpSessObj.getObjectById(ContainerContent.class, currContainerContentID);
                var = currContainerContent.getValueArrayReadable(valueIndexArray, colCount);

                currSubject = currContainerContent.getSample().getSubject().getSubjectName();
                if(!currSubject.equals(prevSubject)){
                stripOn = !stripOn;
                }
                prevSubject = currSubject;
        currStripe = (stripOn) ? "son" : "soff";
    %><tr class="<%=currStripe%>" ><%

      lineNr++;

    %><td id ="<%=currContainerContentID%>" width="1%">
        <a href="javascript:showSlidedHeader('<%=currContainerContentID%>')">
            <img id ="headerImg_<%=currContainerContentID%>" border='0' src='./images/up_10_b.gif' title="show header before this line" alt="">
        </a>
    </td>

    <%

      for (int i = viewStart; i < colCount; i++) {
          // actual field values written here:
          // want null values to appear blank
%><td><%=(var[i] != null) ? var[i] : ""%></td><%
      }
    %>

</tr><%
            }
        }
        }
%>
</tbody>
</table>

<table>
    <%if(containercontentList != null && !containercontentList.isEmpty()){%>
    <tr>
    <td class="left" align="left" style="font-weight:bold">
    If you are happy with your results, enter a name for your list of samples:
        <input type="text" size="30" name="listName" id="listName"></td>
    <td><input type="submit" name="finish" value="Finalize" title="Accept this selection and create your list." onclick="return checkListName();">
    <!--td><input type="submit" name="finishPlater" value="Finalize and Go to Plate Maker" title="Accept this selection and create your list."></td-->
    </tr>
    <%}%>
    <tr>
    <td class="left" align="left" style="font-weight:bold">
    If you are unsatisfied, cancel this selection: &nbsp;&nbsp;
    <input type="submit" name="cancel" value="Cancel Selection" title="Cancel this selection and start over."></td>
    </tr>
</table>
</form>
<%}%>
<%@include file="Footer.jsp"%>




