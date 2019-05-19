<%-- 
    Document   : SampleSelectorViewResults
    Created on : Nov 6, 2009, 6:01:11 PM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%String lapasId = "SampleSelectorViewResults";%>
<%@include file="Header.jsp"%>
<%
        if (tmpHttpSessObj.getCurrentUser() == null) {//test for new session
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }
        // reload if cancel search
        if (request.getParameter("cancelSelection") != null) {
            tmpHttpSessObj.releaseLockCurrentShoppingList();
            tmpHttpSessObj.setCurrentSampleSelector(null);
            //response.sendRedirect("./Index.jsp");
            pageContext.forward(response.encodeURL("./Index.jsp"));
            return;
        }

        ViewContainerContentManager vfm = tmpHttpSessObj.getViewContainerContentManager();
        String tmpString = request.getParameter("sortCol");
        if (tmpString != null) {
            /*
            // store accept and reject statuses to use when reloading sorted page
            SampleSelector selector = tmpHttpSessObj.getCurrentSampleSelector();
            List containercontentList = selector.getDisplayList();
            Iterator ccIter = containercontentList.iterator();
            String ccID = null;
            while(ccIter.hasNext()){
                ccID = ((ContainerContent)ccIter.next()).getContainerContentsID();
                String tmpString = request.getParameter("Ldo"+ccID);
                if(tmpString.equals("accept")){
                    selector.addToTmpAccept(ccID);
                }else if(tmpString.equals("reject")){
                    selector.addToTmpReject(ccID);
                }
            }
            */
            // sort list
            tmpHttpSessObj.setContainerContentSortCol(tmpString);
            tmpHttpSessObj.sortContentsList();
            vfm.setContainerContentFirst();
        } 
        
        // decides whether to show the DB ID
        int viewStart = 1;
        if (tmpHttpSessObj.isTechUser()) {
            viewStart = 0;
        }
        // submit accepts and re-fetch rejects
        if (request.getParameter("submitSelection") != null) {
            //store accepted contents and update working subject list
            SampleSelector selector = tmpHttpSessObj.getCurrentSampleSelector();
            List containercontentList = selector.getDisplayList();
            Iterator ccIter = containercontentList.iterator();
            ContainerContent cc = null;
            while(ccIter.hasNext()){
                cc = (ContainerContent)ccIter.next();
                tmpString = request.getParameter("Ldo"+cc.getContainerContentsID());
                if(tmpString.equals("accept")){
                    selector.acceptContent(cc);
                }
                if(tmpString.equals("reject")){
                    selector.rejectContent(cc);
                }
            }
            // if accepted all remaining samples, go to summary page
            if(selector.finishedWorkingSubjectList()){
                pageContext.forward(response.encodeURL("./SampleSelectorSummary.jsp"));
                //response.sendRedirect("./SampleSelectorSummary.jsp");
                return;
            }
            selector.fetchSamples();
        }

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
        "<%=response.encodeURL("./SampleSelectorViewResults.jsp")%>"+"?sortCol="+key);
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
</script>
<form name="fForm" method="POST"  onsubmit="return checkSubmitFlag()"
      action="<%= response.encodeURL("./SampleSelectorViewResults.jsp")%>"/>
<!--galvas tabula -->
<%
        SampleSelector selector = tmpHttpSessObj.getCurrentSampleSelector();
        //  if selection process hasn't begun, make a new selector object
        if(selector == null){
            tmpHttpSessObj.setCurrentSampleSelector(null);
            response.sendRedirect("./ViewSubjectsList.jsp");
            return;

        }
        List containercontentList = selector.getDisplayList();
        // if no more sample options, go to summary page
        if(containercontentList.isEmpty()){
                response.sendRedirect("./SampleSelectorSummary.jsp");
                return;
        }

        ArrayList noContents = selector.getNoContentsSubjNames();
        List noMoreMatches = selector.getNoMoreMatchesSubjNames();

        //String listName = tmpHttpSessObj.getCurrentShoppingList().getListName();
        vfm.setContainerContentCount(containercontentList.size());

        %>

<table class="view_table">
    <thead>
        <tr>
            <td align="left">
                <table class="sample">
                    <tr>
                        <td class="left" align="left" ><a class="largest">
                            Sample Selector Results: <%=containercontentList.size()%> Candidate Samples For Review</a>
                    </tr>
                </table>
            </td>
            <td align="right" valign="middle">
        <select id="gotoNav" >
            <option value="view">Customize sample data view</option>
        </select>
        <button type="button" class="buttonSmall" onclick="goNav()">Go</button>
            </td></tr>
    </thead>
</table>

<table style="padding-left:20pt" border="0" frame="box">
   <% Iterator bads = null;
   if(noContents != null && !noContents.isEmpty()){%>
    <tr><td class="subHeader" align="left" colspan="3">Subjects Without Samples</td></tr>
    <tr><td colspan="4">The following subjects do not have any samples in the database.
    </td></tr>
    <tr>
    <td align="left" style="padding-left:2em"><table style="background-color:#FBFBFB">
    <% bads = noContents.iterator();
        while(bads.hasNext()){%>
        <tr><td><%=bads.next()%></td></tr>
      <%}%></table></td></tr>
    <%}%>
   <%if(noMoreMatches != null && !noMoreMatches.isEmpty()){%>
    <tr><td class="subHeader" align="left" colspan="3">Subjects Without Qualifying Samples</td></tr>
    <tr><td colspan="4">The following subjects do not have any samples that match any of your selection profiles.
    </td></tr>
    <tr>
    <td align="left" style="padding-left:2em"><table style="background-color:#FBFBFB">
    <%  bads = noMoreMatches.iterator();
        while(bads.hasNext()){%>
        <tr><td><%=bads.next()%></td></tr>
      <%}%></table></td></tr>
   <%}%>
<tr><td>&nbsp;</td></tr>
<tr><td class="subHeader" align="left" colspan="3">Best-Match Sample Candidates</td></tr>
<tr><td colspan="4">The following samples are the best matches found according to your profiles and
any rejections you might have made. Subjects for whom you have accepted a sample will not have any samples below.<br>
<br>Review all the samples below and decide if you would like to keep or reject each one.
To make this process easier, try sorting by comments, container names etc. Use the radio buttons
on the left or right-most columns to indicate if you want to accept or reject a sample.
    </td></tr>

<tr><td><input type="submit" name="submitSelection" value="Submit" title="Submit your accepted samples and re-fetch your rejections">(This may take some time)</td></tr>
<tr><td><input type="submit" name="cancelSelection" value="Cancel Selection" title="Abandon selection process."></td></tr>

</table>

<!--satura tabula -->
<table class="view_table" style="border-width: 0px; border-spacing: 1px; border-color: #ffffff;" >
<thead>
    <tr bgcolor="#b3cfff">
        <td width="1%"><a href='javascript:jumpFocus("bottomLine")'><img border="0" src="./images/down_d_10_b.gif" title="bottom of page" alt=""></a></td>
        <td align="center" style="width:100px;font-weight:bold">Accept&nbsp;<nobr><img style="vertical-align:middle" src='./images/check.gif' title="Accept a sample"/>
            <br>Reject&nbsp;<img style="vertical-align:middle" src='./images/reject.gif' title="Reject a sample"></td>
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
                <td align="center" style="width:100px;font-weight:bold">Accept&nbsp;<nobr><img style="vertical-align:middle" src='./images/check.gif' title="Accept a sample"/>
            <br>Reject&nbsp;<img style="vertical-align:middle" src='./images/reject.gif' title="Reject a sample"></td>
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
                currContainerContent = (ContainerContent) iter.next();
                var = currContainerContent.getValueArrayReadable(valueIndexArray, colCount);

                currSubject = currContainerContent.getSample().getSubject().getSubjectName();
                if(!currSubject.equals(prevSubject)){
                stripOn = !stripOn;
                }
                prevSubject = currSubject;
        currStripe = (stripOn) ? "son" : "soff";
    %><tr class="<%=currStripe%>" ><%

      lineNr++;

      
      boolean accepted = true;
      if(request.getParameter("Rdo"+currContainerContent.getId()) != null
              && request.getParameter("Rdo"+currContainerContent.getId()).equals("reject")){
                  accepted = false;
        }

    %><td id ="<%=currContainerContent.getId()%>" width="1%">
        <a href="javascript:showSlidedHeader('<%=currContainerContent.getId()%>')">
            <img id ="headerImg_<%=currContainerContent.getId()%>" border='0' src='./images/up_10_b.gif' title="show header before this line" alt="">
        </a>
    </td>
    <td  align="center"  style="width:50px">
<nobr><input type="radio" name="Ldo<%=currContainerContent.getId()%>" id="Ldo<%=currContainerContent.getId()%>Accept" value="accept" title="Accept this sample"
    <%=(accepted)?"checked":""%> onclick="copyToOtherside('do<%=currContainerContent.getId()%>Accept')"><img src='./images/check.gif' title="Accept this sample">

<input type="radio" name="Ldo<%=currContainerContent.getId()%>" id="Ldo<%=currContainerContent.getId()%>Reject" value="reject" title="Reject this sample"
    <%=(accepted)?"":"checked"%> onclick="copyToOtherside('do<%=currContainerContent.getId()%>Reject')" ><img src='./images/reject.gif' title="Reject this sample">
    </nobr></td>
    <%

      for (int i = viewStart; i < colCount; i++) {
          // actual field values written here:
          // want null values to appear blank
%><td><%=(var[i] != null) ? var[i] : ""%></td><%
      }
    %>

    <td  align="center"style="width:50px">
<nobr><input type="radio" name="Rdo<%=currContainerContent.getId()%>" id="Rdo<%=currContainerContent.getId()%>Accept" value="accept" title="Accept this sample"
    <%=(accepted)?"checked":""%> onclick="copyToOtherside('do<%=currContainerContent.getId()%>Accept')"><img src='./images/check.gif' title="Accept this sample">

<input type="radio" name="Rdo<%=currContainerContent.getId()%>" id="Rdo<%=currContainerContent.getId()%>Reject" value="reject" title="Reject this sample"
    <%=(accepted)?"":"checked"%> onclick="copyToOtherside('do<%=currContainerContent.getId()%>Reject')" ><img src='./images/reject.gif' title="Reject this sample">
 </nobr>   </td>

</tr><%
            }
        }
%>


</tbody>
</table>

<table style="padding-left:20pt;padding-right:20pt" width="100%">
    <tr><td><input type="submit" name="submitSelection" value="Submit" title="Submit your accepted samples and re-fetch your rejections">
(This may take some time)
</td><td align="right"><input type="submit" name="submitSelection" value="Submit" title="Submit your accepted samples and re-fetch your rejections">
(This may take some time)
</td>
</tr>
<tr><td><input type="submit" name="cancelSelection" value="Cancel Selection" title="Abandon selection process."></td>
<td align="right"><input type="submit" name="cancelSelection" value="Cancel Selection" title="Abandon selection process."></td>
</tr>
</table>
</form>
<%@include file="Footer.jsp"%>




