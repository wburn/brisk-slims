<%String lapasId = "ViewSnp";%>
<%@include file="Header.jsp"%>



<%//test new session
        response.setHeader("Cache-Control", "no-store");
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }

// check permissions
if (!tmpHttpSessObj.isAdminUp()) {%>
<script  type="text/javascript" language="javascript">
    window.onload = disableAllButBack;
</script>
<br><a class="largest">Sorry, you do not have permission to use this page. </a><input type='button' onclick='history.go(-1);' name="badPermGoBack" value="Back" /><br><br>
Your privilege level is: <%=tmpHttpSessObj.getCurrentUser().getRightsReadable()%>.
To see the privileges associated with each level <input type="button" class="button" name="getPermissionDoc" onclick="openPermissionsDoc()" value="Click here"/> <br><br>
<br><br>
<% }%>


<%
        String tmpAction;
        String selfId;
        tmpHttpSessObj.clearInvalidField();
//test new session
        response.setHeader("Cache-Control", "no-store");
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }
        if ((tmpAction = request.getParameter("cancel")) != null) {
            pageContext.forward(response.encodeURL("./ViewSnps.jsp"));
            return;
        }
        if ((tmpAction = request.getParameter("deleteSnp")) != null) {
            selfId = request.getParameter("selfId");
            tmpHttpSessObj.deleteSnp(selfId);
            pageContext.forward(response.encodeURL("./ViewSnps.jsp") + "?del=1");
            return;
        }
%>
<script  type="text/javascript" language="javascript">
    function setFocus(){
        field = document.getElementById("kluda");
        if (field) field.focus();
        else
            document.fForm.genId.focus();
    }
    function confirmDelete(){
        return (confirm("Are You sure?"));
    }

</script>
<%
        int messageNum = 0;
        Snp snp = null;
        String snId;
String snpID;
String Chromosome;
String pos;
String geneID;
String fxnClass;
String rsNumber;

        if ((tmpAction = request.getParameter("addSnp")) != null) {
            snId = request.getParameter("snId");
             Chromosome= request.getParameter("Chromosome");
             pos= request.getParameter("pos");
             geneID= request.getParameter("geneID");
             fxnClass= request.getParameter("fxnClass");
             rsNumber= request.getParameter("rsNumber");

            if (messageNum == 0) {
                messageNum = tmpHttpSessObj.addSnp(snId, Chromosome, pos, geneID, fxnClass, rsNumber);

            }
            if (messageNum == 0) {
                pageContext.forward(response.encodeURL("./ViewSnps.jsp") + "?add=1");
                return;
            } else if (messageNum == 1) {
                pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                return;
            }
%>
<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddSnp.jsp")%>">
    <a class="largest">Add Snp</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <%@include file="AddSnpE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addSnp" value="  Add  "/>
                    <%} else {%>
                    <%=tmpHttpSessObj.getMyMessage(6)%>      <%}%>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else if ((tmpAction = request.getParameter("updateSnp")) != null) {
    snp = tmpHttpSessObj.getCurrentSnp();
    selfId = request.getParameter("selfId");
    snId = request.getParameter("snId");
    Chromosome = request.getParameter("Chromosome");
    pos = request.getParameter("pos");
    geneID = request.getParameter("geneID");
    fxnClass = request.getParameter("fxnClass");
    rsNumber = request.getParameter("rsNumber");

    if (messageNum == 0) {
                messageNum = tmpHttpSessObj.updateSnp(snId, Chromosome, pos, geneID, fxnClass, rsNumber);

    }
    if (messageNum == 0) {
        pageContext.forward(response.encodeURL("./ViewSnps.jsp"));
        return;
    }
    if (messageNum == 1) {
        pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
        return;
    }

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddSnp.jsp")%>">
    <a class="largest">Edit Snp</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <%snId = request.getParameter("snId");%>
    <input type="hidden" name="snId" value="<%=snId%>"/>
    <%@include file="AddSnpE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateSnp" value="Update"/>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else //from View page
if ((tmpAction = request.getParameter("snId")) != null) {
    if (tmpAction.equals("-1")) {//Add snp
        //?????????????????????
        snId = null;
    Chromosome = null;
    pos = null;
    geneID = null;
    fxnClass = null;
    rsNumber = null;

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddSnp.jsp")%>">
    <a class="largest">Add Snp</a>
    <br>
    <a class="error">&nbsp;</a>
    <%@include file="AddSnpE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addSnp" value="  Add  "/>
                    <%} else {%>
                    <%=tmpHttpSessObj.getMyMessage(6)%>      <%}%>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form><%
    } else {//edit Snp
        tmpHttpSessObj.setCurrentSnp(tmpAction);
        snp = tmpHttpSessObj.getCurrentSnp();
        if (snp != null) {
            selfId = tmpAction;
            snId = snp.getVisibleName();
            snId = request.getParameter(" snId");
            snId = snp.getSnpID();
            Chromosome = snp.getChromosome();
            pos = snp.getPos();
            geneID = snp.getGeneID();
            fxnClass = snp.getFxnClass();
            rsNumber = snp.getRsNumber();

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddSnp.jsp")%>">
    <a class="largest">Edit Snp</a>
    <br>
    <a class="error">&nbsp;</a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <input type="hidden" name="snId" value="<%=snId%>"/>
    <%@include file="AddSnpE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateSnp" value="Update"/>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%}
    }
}%>
<%@include file="Footer.jsp"%>

