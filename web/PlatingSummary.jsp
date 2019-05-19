<%-- 
    Document   : PlatingSummary
    Created on : Nov 17, 2009, 12:25:59 PM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%String lapasId = "PlatingSummary";%>
<%@include file="Header.jsp"%>
<%
        System.out.println("in plating summary");

        
//test new session
        if (tmpHttpSessObj.getCurrentUser() == null) {
            System.out.println("tmpHttpSessObj.getCurrentUser() == null");
                // release write lock on list
                tmpHttpSessObj.releaseLockCurrentShoppingList();
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }

       Plater plater =  tmpHttpSessObj.getCurrentPlater();

        if(plater==null){
            System.out.println("plater==null");
            // release write lock on list
            tmpHttpSessObj.releaseLockCurrentShoppingList();
            pageContext.forward(response.encodeURL("./Index.jsp"));
            return;
        }
 

String tmpAction;


        if ((tmpAction = request.getParameter("doneSummary")) != null) {
            plater.plated = 0;
            plater.numberOfPlatesMade = 0;
            plater.controlsJustAdded = 0;
            tmpHttpSessObj.setCurrentPlater(null);
            // release write lock on list
            tmpHttpSessObj.releaseLockCurrentShoppingList();
            // close current list
            tmpHttpSessObj.setCurrentShoppingCartList(null);
            tmpHttpSessObj.setCurrentShoppingList(null);
            response.sendRedirect("./Index.jsp");
            //pageContext.forward(response.encodeURL("ViewContentsLists.jsp"));
            return;
        }

        
        ArrayList<Container> platesMade = new ArrayList<Container>(plater.platesMade);
/*        //test start
        ArrayList<Container> platesMade = new ArrayList<Container>();
        platesMade.add((Container)tmpHttpSessObj.getObjectById(Container.class, "1859"));
        platesMade.add((Container)tmpHttpSessObj.getObjectById(Container.class, "1860"));
        platesMade.add((Container)tmpHttpSessObj.getObjectById(Container.class, "1861"));
        Plater plater = new Plater(tmpHttpSessObj);
        // test end
*/
        int numPlatesMade = platesMade.size();

        %>
<script language="JavaScript">
function gotoPlatingReport( contId, reportFormat){
     window.open('ViewReport.jsp?customPlatingSheet='+contId+'&reportFormat='+reportFormat,'',
        'height=600,width=800,scrollbars=yes,resizable=yes,menubar=no,toolbar=no,location=no,status=no');
}
function gotoLocationReport( contId, reportFormat){
     window.open('ViewReport.jsp?sampleLocationSheet='+contId+'&reportFormat='+reportFormat,'',
        'height=600,width=800,scrollbars=yes,resizable=yes,menubar=no,toolbar=no,location=no,status=no');
}
function goHome(){
      if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./Index.jsp")%>");

}
</script>
<html>
<form method="POST" name="fForm" action="<%=response.encodeURL("./PlatingSummary.jsp")%>">
        <table class="navigate">
            <tr>
                <td class="left" align="left" colspan="2">
                    <a class="largest">Plating Summary:</a></td>
                </tr>
            <tr>
                <td><br>
                    You have made <%=numPlatesMade%> plates.
                    See below to view their layouts and download makesheets.
                    Go to File->Print to print these layouts.
                </td></tr>
            <tr><td>
                If you are done, click here to close your current list and go back to SLIMS home:
                <input type="submit" class="button" value="Finish" name="doneSummary" onclick="goHome()">
                </td></tr>
        </table>
</form>
                <tr><td>&nbsp</td></tr>
            <%Iterator iter = platesMade.iterator();
                Container cont = null;
            while(iter.hasNext()){
                cont = (Container) iter.next();
                %>
            <tr><td > <a style="font-weight:bold"><%=cont.getcontainerName()%></a>&nbsp;&nbsp;
            Sample Location Sheet: 
                <input type="submit" class="button" value="PDF" onclick="gotoLocationReport('<%=cont.getId()%>','pdf')">
                <input type="submit" class="button" value="CSV" onclick="gotoLocationReport('<%=cont.getId()%>','csv')">
                    
                 &nbsp;&nbsp;
            Custom Plating Sheet:
                 <input type="submit" class="button" value="PDF" onclick="gotoPlatingReport('<%=cont.getId()%>','pdf')">
                 <input type="submit" class="button" value="CSV" onclick="gotoPlatingReport('<%=cont.getId()%>','csv')">
                   </td></tr>
                <tr><td><table class="plate" style=""><%=plater.getPlateHtml(cont)%></table></td></tr>
            <tr><td>&nbsp</td></tr>
                <%
            }
            %>

</html>


<%@include file="Footer.jsp"%>
