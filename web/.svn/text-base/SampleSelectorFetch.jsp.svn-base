<%-- 
    Document   : SampleSelectorFetch
    Created on : Nov 6, 2009, 2:55:54 PM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%String lapasId = "SampleSelectorFetch";%>

<%@include file="Header.jsp"%>

<%
        tmpHttpSessObj.clearInvalidField();

//test new session
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }

        %>
<script language="JavaScript" type="text/javascript">
function goBack(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewSubjectsList.jsp")%>");
}

</script>

<%
        tmpHttpSessObj.clearInvalidField();
        String tmpAction = null;


        int level = -1;
//test new session
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }

        // if pressed cancel on this page, reset everything and go back to list
        if((tmpAction=request.getParameter("cancel"))!=null){
            tmpHttpSessObj.releaseLockCurrentShoppingList();
            tmpHttpSessObj.setCurrentSampleSelector(null);
            response.sendRedirect("./Index.jsp");
            return;
        }

        SampleSelector selector = tmpHttpSessObj.getCurrentSampleSelector();
        //  if selection process hasn't begun, make a new selector object
        if(selector == null){
            tmpHttpSessObj.setCurrentSampleSelector(null);
            response.sendRedirect("./ViewSubjectsList.jsp");
            return;

        } 
         // if pressed done move on to fetching samples
        if((tmpAction=request.getParameter("done"))!=null){

            // use selector to make list of samples
            selector.initialiseFetcher("notUsed");
            selector.fetchSamples();

            // show them
            response.sendRedirect("./SampleSelectorViewResults.jsp");
            return;
        }
        else{
        
        
        selector.printSpecificationProfile("crit choice begin");
        level = selector.getCurrentLevel();
        System.out.println("level: "+level);
%>

<html>
<form method="POST" name="searchForm"
          action="<%=response.encodeURL("./SampleSelectorFetch.jsp")%>">
        <table class="navigate">
            <tr>
                <td class="left" align="left" colspan="2"><a class="largest">Prepare Selection Criteria</a></td>
                </tr>
            <tr>
                <td>
<fieldset>
  <table border="0" width="100%">
      <tr><td class="larger" colspan="2">Verify Your Selection Profiles:</td></tr>

        <tr><td>&nbsp;</td></tr>
        <tr><td>
        <% // draw diagram of selections so far
           // --------- printing -------------
            System.out.println("printing options sets page");
            //selector.printOptionsStore();
            ArrayList optionSet = new ArrayList();
            selector.printTracker.clear();
            if(level>0){

            %><table style="border:thick double gray;border-spacing:0pt;background-color:#f0f8ff;" cellspacing="0"><%

            %><tr><td style="padding-top:1em;">Most Prefered</td><%
            int max = selector.getHighestNumLevels();
            for(int i = 0; i< max; i++){
            %><td style="border-right:gray thin dotted;border-left:gray thin dotted; padding-left:1em;padding-top:1em;border-bottom:black thin solid;">
                Level <%=i+1%></td><%
            }

            int row=0;
            // 'max-level' number of zeros
            for(int i = 0; i<=max;i++){
                selector.printTracker.add(0);
            }

            optionSet = selector.getNextOptionSet();
            while(optionSet !=null){
            row++;

            // highlight current row that user is defining
            int pos = 0;
            System.out.print("positionTracker: (");
            int j = 0;
            for(j = 0; j<selector.positionTracker.size();j++){
                pos = pos +((selector.positionTracker.get(j))[0]) + 1;
                System.out.print(((Integer[]) selector.positionTracker.get(j))[0]+", ");
            }
            System.out.print(")\n");
            System.out.println("pos="+pos);

            System.out.print("printTracker: (");
            for(int i = 0; i<selector.printTracker.size();i++){
                System.out.print(((Integer) selector.printTracker.get(i))+", ");
            }
            System.out.print(")\n");


            // print out option set in a row
                %><tr>
                    <td style="font-weight:bold;padding-left:1em;padding-right:1em;padding-top:1em;border-bottom:thin black solid">
                    Sample Profile <%=row%>:</td><%
               Iterator iter = optionSet.iterator();
               int col = 0;
               HashMap realNames = selector.getAllCriteria();
               while(iter.hasNext()){
                   Object[] option = (Object[])iter.next();
                   System.out.println("printing option:"+option[0]+", "+option[1]);
                   String opStr = selector.getReadableOption(option[0].toString(), option[1]);
                   String name = (String) realNames.get(option[0]);
               %><td  style="border-right:gray thin dotted;border-left:gray thin dotted; padding-left:1em;padding-right:1em;padding-top:1em;border-bottom:black thin solid;">
                   <%=name%><%=opStr%>
               </td><%
               col++;
               }
               
               %></tr><%
                // get next option set
               optionSet = selector.getNextOptionSet();
            }
            %><tr><td style="padding-top:1em;padding-bottom:1em;">Least Prefered</td></tr>

            </table><%
            selector.noMoreOptionSets=false;
            // -------------------------------
        }else{
        %>
<table style="border:thin solid gray;" cellspacing="0">
    <tr><td style="padding-top:1em;">Most Prefered</td>
        <td>Level 1</td>
    </tr>
        <tr>
       <td style="border-bottom:black thin solid;font-weight:bold">
        Sample Profile 1:</td>
        </tr
        <tr><td style="padding-top:1em;padding-bottom:1em;">Least Prefered</td></tr>

</table>
        <%}%>
</fieldset>
       </td></tr>
 <tr><td>&nbsp;</td></tr>
            <tr>
                <td align="left">
                    SLIMS can now fetch 'best-match' samples for your review:
                    <input type="submit" name="done" value="Fetch Samples" title="Find best-match samples according to the profiles you've defined.">
            (This may take a few minutes.)</td></tr><tr><td>
                    If you are not satisfied and would like to start over: 
                <input type="submit" name="cancel" value="Cancel Selection">
                
                </td></tr>
        </table>
    </form>
</html>
 <%}%>
 </td></tr>
        </table>

<%@include file="Footer.jsp"%>
