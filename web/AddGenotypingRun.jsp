<%String lapasId = "ViewGenotypingRun";%>
<%@include file="Header.jsp"%>

<%//test new session
        response.setHeader("Cache-Control", "no-store");
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }

// check permissions
if (!tmpHttpSessObj.isPowerUserUp()) {%>
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
            pageContext.forward(response.encodeURL("./ViewGenotypingRuns.jsp"));
            return;
        }
        if ((tmpAction = request.getParameter("deleteGenotypingRun")) != null) {
            selfId = request.getParameter("selfId");
            tmpHttpSessObj.deleteGenotypingRun(selfId);
            pageContext.forward(response.encodeURL("./ViewGenotypingRuns.jsp") + "?del=1");
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
        GenotypingRun genotypingrun = null;
        String genoRunId;
        String genotypingRunID;
        String description;
        Date date;
        String ayear;
        String amonth;
        String aday;

        if ((tmpAction = request.getParameter("addGenotypingRun")) != null) {
            genoRunId = request.getParameter("genoRunId");
            description = request.getParameter("description");
            ayear = request.getParameter("ayear");
            amonth = request.getParameter("amonth");
            aday = request.getParameter("aday");
            date = tmpHttpSessObj.getDate(ayear, amonth, aday,
                    "ayear", "amonth", "aday");
            if (date == null) {
                messageNum = 5;
            }

            if (genoRunId == null || genoRunId.equals("")) {
                messageNum = 9;
            }

            if (messageNum == 0) {
                messageNum = tmpHttpSessObj.addGenotypingRun(genoRunId, description, date);

            }
            if (messageNum == 0) {
                pageContext.forward(response.encodeURL("./ViewGenotypingRuns.jsp") + "?add=1");
                return;
            } else if (messageNum == 1) {
                pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                return;
            }
%>
<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddGenotypingRun.jsp")%>">
    <a class="largest">Add GenotypingRun</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <%@include file="AddGenotypingRunE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addGenotypingRun" value="  Add  "/>
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
<%} else if ((tmpAction = request.getParameter("updateGenotypingRun")) != null) {
    genotypingrun = tmpHttpSessObj.getCurrentGenotypingRun();
    selfId = request.getParameter("selfId");
    genoRunId = request.getParameter("genoRunId");
    description = request.getParameter("description");
    ayear = request.getParameter("ayear");
    amonth = request.getParameter("amonth");
    aday = request.getParameter("aday");
    date = tmpHttpSessObj.getDate(ayear, amonth, aday,
            "ayear", "amonth", "aday");
    if (date == null) {
        messageNum = 5;
    }

    if (genoRunId == null || genoRunId.equals("")) {
        messageNum = 9;
    }

    if (messageNum == 0) {
        messageNum = tmpHttpSessObj.updateGenotypingRun(genoRunId, description, date);

    }
    if (messageNum == 0) {
        pageContext.forward(response.encodeURL("./ViewGenotypingRuns.jsp"));
        return;
    }
    if (messageNum == 1) {
        pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
        return;
    }

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddGenotypingRun.jsp")%>">
    <a class="largest">Edit GenotypingRun</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <%genoRunId = request.getParameter("genoRunId");%>
    <input type="hidden" name="genoRunId" value="<%=genoRunId%>"/>
    <%@include file="AddGenotypingRunE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateGenotypingRun" value="Update"/>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else //from View page
if ((tmpAction = request.getParameter("genoRunId")) != null) {
    if (tmpAction.equals("-1")) {//Add genotypingrun
        //?????????????????????
        genoRunId = null;
        description = null;
        date = null;
        Date myDate = new Date();
        ayear = Util.getYear(myDate);
        amonth = Util.getMonth(myDate);
        aday = Util.getDay(myDate);

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddGenotypingRun.jsp")%>">
    <a class="largest">Add GenotypingRun</a>
    <br>
    <a class="error">&nbsp;</a>
    <%@include file="AddGenotypingRunE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addGenotypingRun" value="  Add  "/>
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
    } else {//edit GenotypingRun
        tmpHttpSessObj.setCurrentGenotypingRun(tmpAction);
        genotypingrun = tmpHttpSessObj.getCurrentGenotypingRun();
        if (genotypingrun != null) {
            selfId = tmpAction;
            genoRunId = genotypingrun.getVisibleName();
            genoRunId = request.getParameter(" genoRunId");
            genoRunId = genotypingrun.getGenotypingRunID();
            description = genotypingrun.getDescription();
            date = genotypingrun.getDate();

            Date myDate = (genotypingrun.getDate() != null) ?
                genotypingrun.getDate() : new Date();
            ayear = Util.getYear(myDate);
            amonth = Util.getMonth(myDate);
            aday = Util.getDay(myDate);

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddGenotypingRun.jsp")%>">
    <a class="largest">Edit GenotypingRun</a>
    <br>
    <a class="error">&nbsp;</a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <input type="hidden" name="genoRunId" value="<%=genoRunId%>"/>
    <%@include file="AddGenotypingRunE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateGenotypingRun" value="Update"/>
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

