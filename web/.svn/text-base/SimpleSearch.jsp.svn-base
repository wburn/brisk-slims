<%-- 
    Document   : Search
    Created on : Jun 3, 2009, 5:42:17 PM
    Author     : tvanrossum
--%>
<%String lapasId = "SimpleSearch";%>
<%@include file="Header.jsp"%>
<%
        tmpHttpSessObj.clearInvalidField();
//test new session
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }
        if (request.getParameter("searchFor") != null) {
                tmpHttpSessObj.cancelFilter();
                tmpHttpSessObj.cancelSearch();
            
            if ((request.getParameter("searchFor")).equalsIgnoreCase("sample")) {
                tmpHttpSessObj.setSearch(request);
                pageContext.forward(response.encodeURL("./ViewContainerContents.jsp"));
%>
<input type="hidden" name="searchFor" value=""/>
<%
        return;
    }
    if ((request.getParameter("searchFor")).equalsIgnoreCase("subject")) {
        tmpHttpSessObj.setSearch(request);
        pageContext.forward(response.encodeURL("./ViewSubjects.jsp"));
%>
<input type="hidden" name="searchFor" value=""/>
<%
        return;
    }
    if ((request.getParameter("searchFor")).equalsIgnoreCase("container")) {
        tmpHttpSessObj.setSearch(request);
        pageContext.forward(response.encodeURL("./ViewContainers.jsp"));
%>
<input type="hidden" name="searchFor" value=""/>
<%
                return;
            }
 if ((request.getParameter("searchFor")).equalsIgnoreCase("shipment")) {
        tmpHttpSessObj.setSearch(request);
        pageContext.forward(response.encodeURL("./ViewContainers.jsp"));
%>
<input type="hidden" name="searchFor" value=""/>
<%
                return;
            }
        }
%>
<script type="text/javascript"  language="JavaScript">
    function getOptions(searchSubject) {
        var list;
        var listValue;
        if (searchSubject == "sample") {
            //list = new Array("Name","Type");
            list = new Array("Name");
            //listValue = new Array("sampleName","sampleType","sampleValidity");
            listValue = new Array("sampleName");
            $(".shipmentHide").css("display","inline");
            $(".shipmentHide").attr("disabled", "");
            $(".shipmentDisable").attr("disabled", "");
            $(".shipmentShow").css("display","none");
            $(".shipmentShow").css("disabled","disabled");
        }
        if (searchSubject == "container") {
            list = new Array("Name","Alias","Name & Alias");
            listValue = new Array("containerName","containerAlias","containerNameAndAlias");
            $(".shipmentHide").css("display","inline");
            $(".shipmentHide").attr("disabled", "");
            $(".shipmentDisable").attr("disabled", "");
            $(".shipmentShow").css("display","none");
            $(".shipmentShow").css("disabled","disabled");
        }
        if (searchSubject == "subject") {
            list = new Array("ID");
            listValue = new Array("subjectName");
            $(".shipmentHide").css("display","inline");
            $(".shipmentHide").attr("disabled", "");
            $(".shipmentDisable").attr("disabled", "");
            $(".shipmentShow").css("display","none");
            $(".shipmentShow").css("disabled","disabled");
        }
        if (searchSubject == "shipment") {
            list = new Array("Shipment Name");
            listValue = new Array("shipmentName");
            $(".shipmentHide").css("display","none");
            $(".shipmentHide").css("disabled","disabled");
            $(".shipmentDisable").attr("disabled", "disabled");
            $(".shipmentShow").css("display","inline");
            $(".shipmentShow").attr("disabled", "");
        }
        for (var i = 0; i < document.searchForm.searchFor.length; i++) { //Clear the 2nd menu
            document.searchForm.searchBy.options[i] = null;
        }
        for (var i = 0; i < list.length; i++) { //Repopulate 2nd menu
            document.searchForm.searchBy.options[i] = new Option(list[i],listValue[i],0,0);
        }
    }
</script>

<%
        tmpHttpSessObj.clearInvalidField();
//test new session
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }
%>

<html>
    <form method="POST" name="searchForm"
          action="<%=response.encodeURL("./SimpleSearch.jsp")%>">
        <table class="navigate">
            <tr>
                <td class="left" align="left" colspan="2"><a class="largest">Simple Search of the Database</a></td>
                </tr>
            <tr>
                <td><br>
                    Search for &nbsp;
                    <select name="searchFor" onchange="getOptions(this.value)">
                        <option value="container">Containers</option>
                        <option value="sample" selected>Samples</option>
                        <option value="subject">Subjects</option>
                        <option value="shipment">Shipment</option>
                    </select>&nbsp;&nbsp; by &nbsp;&nbsp;
                    <select name="searchBy" style="width:120px">
                        <option value="sampleName">Name</option>
                    </select>&nbsp;&nbsp;
                    <input type="text" name="searchTerm" class="shipmentHide">
                    <select multiple="multiple" name="shipmentTerm" class="shipmentShow" style="display:none">
                        <%=tmpHttpSessObj.getShipmentListPrompter(null)%>
                    </select>
                    &nbsp;&nbsp;
                    <input type="checkbox" name="exact" class="shipmentDisable" value="true"/>Exact
                    &nbsp;&nbsp;
                    <input type="submit" value="Go"/>
                </td>
            </tr>
        </table>
    </form>
    <br/>
    <br/>
    <br/>
    <br/>
</html>


<%@include file="Footer.jsp"%>