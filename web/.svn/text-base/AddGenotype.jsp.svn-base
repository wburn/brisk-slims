<%String lapasId = "ViewGenotype";%>
<%@include file="Header.jsp"%>


<%//test new session
        response.setHeader("Cache-Control", "no-store");
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }

// check permissions
if (!tmpHttpSessObj.isPowerUserDryUp()) {%>
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
            pageContext.forward(response.encodeURL("./ViewGenotypes.jsp"));
            return;
        }
        if ((tmpAction = request.getParameter("deleteGenotype")) != null) {
            selfId = request.getParameter("selfId");
            tmpHttpSessObj.deleteGenotype(selfId);
            pageContext.forward(response.encodeURL("./ViewGenotypes.jsp") + "?del=1");
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
        Genotype genotype = null;
        String genotypId;
String genotypeID;
String allele1;
String allele2;
String containerContentsID;
String genotypingRunID;
String snpID;

        if ((tmpAction = request.getParameter("addGenotype")) != null) {
            genotypId = request.getParameter("genotypId");
             allele1= request.getParameter("allele1");
             allele2= request.getParameter("allele2");
             containerContentsID= request.getParameter("containerContentsID");
             genotypingRunID= request.getParameter("genotypingRunID");
             snpID= request.getParameter("snpID");

            if (messageNum == 0) {
                messageNum = tmpHttpSessObj.addGenotype(allele1, allele2, containerContentsID, genotypingRunID, snpID);

            }
            if (messageNum == 0) {
                pageContext.forward(response.encodeURL("./ViewGenotypes.jsp") + "?add=1");
                return;
            } else if (messageNum == 1) {
                pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                return;
            }
%>
<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddGenotype.jsp")%>">
    <a class="largest">Add Genotype</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <%@include file="AddGenotypeE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addGenotype" value="  Add  "/>
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
<%} else if ((tmpAction = request.getParameter("updateGenotype")) != null) {
    genotype = tmpHttpSessObj.getCurrentGenotype();
    selfId = request.getParameter("selfId");
    genotypId = request.getParameter("genotypId");
    allele1 = request.getParameter("allele1");
    allele2 = request.getParameter("allele2");
    containerContentsID = request.getParameter("containerContentsID");
    genotypingRunID = request.getParameter("genotypingRunID");
    snpID = request.getParameter("snpID");

    if (messageNum == 0) {
                messageNum = tmpHttpSessObj.updateGenotype(genotypId, allele1, allele2, containerContentsID, genotypingRunID, snpID);

    }
    if (messageNum == 0) {
        pageContext.forward(response.encodeURL("./ViewGenotypes.jsp"));
        return;
    }
    if (messageNum == 1) {
        pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
        return;
    }

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddGenotype.jsp")%>">
    <a class="largest">Edit Genotype</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <%genotypId = request.getParameter("genotypId");%>
    <input type="hidden" name="genotypId" value="<%=genotypId%>"/>
    <%@include file="AddGenotypeE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateGenotype" value="Update"/>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else //from View page
if ((tmpAction = request.getParameter("genotypId")) != null) {
    if (tmpAction.equals("-1")) {//Add genotype
        //?????????????????????
        genotypId = null;
    allele1 = null;
    allele2 = null;
    containerContentsID = null;
    genotypingRunID = null;
    snpID = null;

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddGenotype.jsp")%>">
    <a class="largest">Add Genotype</a>
    <br>
    <a class="error">&nbsp;</a>
    <%@include file="AddGenotypeE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addGenotype" value="  Add  "/>
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
    } else {//edit Genotype
        tmpHttpSessObj.setCurrentGenotype(tmpAction);
        genotype = tmpHttpSessObj.getCurrentGenotype();
        if (genotype != null) {
            selfId = tmpAction;
            genotypId = genotype.getVisibleName();
            genotypId = request.getParameter(" genotypId");
            genotypId = genotype.getGenotypeID();
            allele1 = genotype.getAllele1();
            allele2 = genotype.getAllele2();
            containerContentsID = (genotype.getContainerContents()!=null)?genotype.getContainerContents().getContainerContentsID():null;
            genotypingRunID = (genotype.getGenotypingRun()!=null)?genotype.getGenotypingRun().getGenotypingRunID():null;
            snpID = (genotype.getSnp()!=null)?genotype.getSnp().getSnpID():null;

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddGenotype.jsp")%>">
    <a class="largest">Edit Genotype</a>
    <br>
    <a class="error">&nbsp;</a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <input type="hidden" name="genotypId" value="<%=genotypId%>"/>
    <%@include file="AddGenotypeE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateGenotype" value="Update"/>
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

