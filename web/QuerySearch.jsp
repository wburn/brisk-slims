<%-- 
    Document   : QuerySearch
    Created on : Jun 9, 2009, 10:41:27 AM
    Author     : tvanrossum
--%>
<%String lapasId = "QuerySearch";%>
<%@include file="Header.jsp"%>
<%
        tmpHttpSessObj.clearInvalidField();
//test new session
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }
        String tmpAction;
// set up filter and go to page requested
        if ((tmpAction = request.getParameter("viewResults")) != null) {
            tmpHttpSessObj.setQuerySearch(request);
            pageContext.forward(response.encodeURL("./ViewQueryResults.jsp"));
            return;
        }
        if ((tmpAction = request.getParameter("exportResults")) != null) {
            tmpHttpSessObj.setQuerySearch(request);
            pageContext.forward(response.encodeURL("./QueryExport.jsp"));
            return;
        }
        MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
%>
<script  type="text/javascript" language="javascript">
    function setFocus(){}
    function clearFilter(){
        document.fForm.queryField.value="";
        return false;

    }
</script>
<form method="POST" name="fForm" action="<%=response.encodeURL("./QuerySearch.jsp")%>"/>
    <table>
        <tr>
            <td class="largest" align="left" COLSPAN="2">SQL Query Search <br> &nbsp;</td>
        </tr>
        <tr>
            <td >Enter SQL statement to query the database:</td>
            </tr>
        <tr>
            <td >
                <textarea rows="5" cols="50" name="query"></textarea>
            </td></tr>
            <tr>
            <td valign="bottom" align="right" colspan="2">
            <!--input type="submit" name="viewResults" value="View Results" disabled/-->
            <input type="submit" name="exportResults" value="Export Results to Excel"/>
            <br>&nbsp;
            </td>

        </tr>
    </table>
</form><%@include file="Footer.jsp"%>
