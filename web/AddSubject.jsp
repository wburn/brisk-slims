<%String lapasId = "ViewSubject";%>
<%@include file="Header.jsp"%>


<%//test new session
        response.setHeader("Cache-Control", "no-store");
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }
String tmpAction;

// check permissions
// if adding, need to be power user dry lab or above
if ((tmpAction = request.getParameter("subjId")) != null) {
    if (tmpAction.equals("-1")) {
        if (!tmpHttpSessObj.isPowerUserDryUp()) {%>
<script  type="text/javascript" language="javascript">
    window.onload = disableAllButBack();
</script>
<br><a class="largest">Sorry, you do not have permission to use this page. </a><input type='button' onclick='history.go(-1);' name="badPermGoBack" value="Back" /><br><br>
Your privilege level is: <%=tmpHttpSessObj.getCurrentUser().getRightsReadable()%>.
To see the privileges associated with each level <input type="button" class="button" name="getPermissionDoc" onclick="openPermissionsDoc()" value="Click here"/> <br><br>
<br><br>
<%      }
    }
// if updating, need to be admin or above
    else{
if (!tmpHttpSessObj.isAdminUp()) {%>
<script  type="text/javascript" language="javascript">
    window.onload = disableAllButBack;
</script>
<br><a class="largest">Sorry, you do not have permission to use this page. </a><input type='button' onclick='history.go(-1);' name="badPermGoBack" value="Back" /><br><br>
Your privilege level is: <%=tmpHttpSessObj.getCurrentUser().getRightsReadable()%>.
To see the privileges associated with each level <input type="button" class="button" name="getPermissionDoc" onclick="openPermissionsDoc()" value="Click here"/> <br><br>
<br><br>
<%      }
    }
}else if (!tmpHttpSessObj.isAdminUp()) {%>
<script  type="text/javascript" language="javascript">
    window.onload = disableAllButBack;
</script>
<br><a class="largest">Sorry, you do not have permission to use this page. </a><input type='button' onclick='history.go(-1);' name="badPermGoBack" value="Back" /><br><br>
Your privilege level is: <%=tmpHttpSessObj.getCurrentUser().getRightsReadable()%>.
To see the privileges associated with each level <input type="button" class="button" name="getPermissionDoc" onclick="openPermissionsDoc()" value="Click here"/> <br><br>
<br><br>
<%}%>

<%
        
        String selfId;
        tmpHttpSessObj.clearInvalidField();

        if ((tmpAction = request.getParameter("cancel")) != null) {
            pageContext.forward(response.encodeURL("./ViewSubjects.jsp"));
            return;
        }
        if ((tmpAction = request.getParameter("deleteSubject")) != null) {
            selfId = request.getParameter("selfId");
            tmpHttpSessObj.deleteSubject(selfId);
            pageContext.forward(response.encodeURL("./ViewSubjects.jsp") + "?del=1");
            return;
        }
%>
<script type="text/javascript">
    function setFocus(){
        field = document.getElementById("kluda");
        if (field) field.focus();
        else
            document.fForm.genId.focus();
    }
    function confirmDelete(){
        return (confirm("Are You sure?"));
    }
    $(window).load(function(){
        $("#addSubjectForm").validate({
            success: function(label) {
                // set   as text for IE
                label.html(" ").addClass("checked");
            }
        });
    })
</script>
<%
        int messageNum = 0;
        Subject subject = null;
        String subjId;
        String subjectID;
        String subjectName;
        String fatherName;
        String motherName;
        String cohortID;
        String gender;
        String familyID;
        String hasConsent;
        String ethnicityID;
        String comments;
        Ethnicity ethnicity = null;
        Cohort cohort = null;

        if ((tmpAction = request.getParameter("addSubject")) != null) {
            subjId = request.getParameter("subjId");
            subjectName = request.getParameter("subjectName");
            comments= request.getParameter("comments");
            fatherName = request.getParameter("fatherName");
            if (subjectName == null || subjectName.equals("")){
                subjectName = null;
             }
            if (fatherName == null || fatherName.equals("")) {
                fatherName = "0";
            }
            motherName = request.getParameter("motherName");
            if (motherName == null || motherName.equals("")) {
                motherName = "0";
            }
            cohortID = request.getParameter("cohortID");
            if (cohortID == null || cohortID.equals("")) {
                cohortID = null;
            }
            gender = request.getParameter("gender");
            if (gender == null || gender.equals("")) {
                gender = "0";
            }
            hasConsent = request.getParameter("hasConsent");
            if (hasConsent == null || hasConsent.equals("")) {
                hasConsent = "0";
            }
            familyID = request.getParameter("familyID");
            if (familyID == null || familyID.equals("")) {
                familyID = "0";
            }
            ethnicityID = request.getParameter("ethnicityID");
            if (ethnicityID == null || ethnicityID.equals("")) {
                ethnicityID = "0";
            }
            if (subjId == null || subjectName == null || cohortID == null 
                    || gender == null || motherName == null || fatherName == null
                    || familyID == null || hasConsent == null || ethnicityID == null) {
                messageNum = 9;
            }

            if (messageNum == 0) {
                cohort = (Cohort) tmpHttpSessObj.getObjectById(Cohort.class, cohortID);
                ethnicity = (Ethnicity) tmpHttpSessObj.getObjectById(Ethnicity.class, ethnicityID);
            }

            if (messageNum == 0) {
                messageNum = tmpHttpSessObj.addSubject(subjId, subjectName,
                        fatherName, motherName, cohort, gender, familyID,
                        hasConsent, ethnicity,comments);

            }
            if (messageNum == 0) {
                pageContext.forward(response.encodeURL("./ViewSubjects.jsp") + "?add=1");
                return;
            } else if (messageNum == 1) {
                pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                return;
            }
%>
<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()" id="addSubjectForm"
      action="<%=response.encodeURL("./AddSubject.jsp")%>">
    <a class="largest">Add Subject</a>
    <br>
    <a class="error">  Error:
        <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
        <%@include file="AddSubjectE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addSubject" value="  Add  "/>
                    <%} else {%>
                    <%=tmpHttpSessObj.getMyMessage(6)%>      <%}%>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10" class="cancel"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else if ((tmpAction = request.getParameter("updateSubject")) != null) {
    subject = tmpHttpSessObj.getCurrentSubject();
    selfId = request.getParameter("selfId");
    subjId = request.getParameter("subjId");
            subjectName = request.getParameter("subjectName");
            comments= request.getParameter("comments");
            fatherName = request.getParameter("fatherName");
            if (subjectName == null || subjectName.equals("")){
                subjectName = null;
             }
            if (fatherName == null || fatherName.equals("")) {
                fatherName = "0";
            }
            motherName = request.getParameter("motherName");
            if (motherName == null || motherName.equals("")) {
                motherName = "0";
            }
            cohortID = request.getParameter("cohortID");
            if (cohortID == null || cohortID.equals("")) {
                cohortID = null;
            }
            gender = request.getParameter("gender");
            if (gender == null || gender.equals("")) {
                gender = "0";
            }
            hasConsent = request.getParameter("hasConsent");
            if (hasConsent == null || hasConsent.equals("")) {
                hasConsent = "0";
            }
            familyID = request.getParameter("familyID");
            if (familyID == null || familyID.equals("")) {
                familyID = "0";
            }
            ethnicityID = request.getParameter("ethnicityID");
            if (ethnicityID == null || ethnicityID.equals("")) {
                ethnicityID = null;
            }
            if (subjId == null || subjectName == null || cohortID == null || gender == null || motherName == null || fatherName == null || familyID == null || hasConsent == null || ethnicityID == null) {
                messageNum = 9;
            }
    if (subjId == null || subjectName == null || cohortID == null || gender == null || motherName == null || fatherName == null || familyID == null || hasConsent == null || ethnicityID == null) {
        messageNum = 9;
    }

    if (messageNum == 0) {
        cohort = (Cohort) tmpHttpSessObj.getObjectById(Cohort.class, cohortID);
        ethnicity = (Ethnicity) tmpHttpSessObj.getObjectById(Ethnicity.class, ethnicityID);
    }
    if (messageNum == 0) {
        messageNum = tmpHttpSessObj.updateSubject(subjId, subjectName,
                fatherName, motherName, cohort, gender, familyID,
                hasConsent, ethnicity,comments);

    }
    if (messageNum == 0) {
        pageContext.forward(response.encodeURL("./ViewSubjects.jsp"));
        return;
    }
    if (messageNum == 1) {
        pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
        return;
    }

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()" id="addSubjectForm"
      action="<%=response.encodeURL("./AddSubject.jsp")%>">
    <a class="largest">Edit Subject</a>
    <br>
    <a class="error">  Error:
        <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <%subjId = request.getParameter("subjId");%>
    <input type="hidden" name="subjId" value="<%=subjId%>"/>
    <%@include file="AddSubjectE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateSubject" value="Update"/>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10" class="cancel"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else //from View page
if ((tmpAction = request.getParameter("subjId")) != null) {
    if (tmpAction.equals("-1")) {//Add subject
        //?????????????????????
        subjId = null;
        subjectName = null;
        fatherName = null;
        motherName = null;
        cohortID = null;
        gender = null;
        familyID = null;
        hasConsent = null;
        ethnicityID=null;
        comments = null;

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()" id="addSubjectForm"
      action="<%=response.encodeURL("./AddSubject.jsp")%>">
    <a class="largest">Add Subject</a>
    <br>
    <a class="error">&nbsp;</a>
    <%@include file="AddSubjectE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addSubject" value="  Add  "/>
                    <%} else {%>
                    <%=tmpHttpSessObj.getMyMessage(6)%>      <%}%>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10" class="cancel"/>
                </td>
            </tr>
        </thead>
    </table>
</form><%
    } else {//edit Subject
        tmpHttpSessObj.setCurrentSubject(tmpAction);
        subject = tmpHttpSessObj.getCurrentSubject();
        if (subject != null) {
            selfId = tmpAction;
            subjId = subject.getVisibleName();
            subjId = request.getParameter(" subjId");
            subjId = subject.getSubjectID();
            subjectName = subject.getSubjectName();
            fatherName = subject.getFatherName();
            motherName = subject.getMotherName();
            cohortID = subject.getCohort().getCohortID();
            gender = subject.getGender();
            familyID = subject.getFamilyID();
            hasConsent = subject.getHasConsent();
            ethnicityID =  subject.getEthnicity().getEthnicityID();
            comments = subject.getComments();

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()" id="addSubjectForm"
      action="<%=response.encodeURL("./AddSubject.jsp")%>">
    <a class="largest">Edit Subject</a>
    <br>
    <a class="error">&nbsp;</a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <input type="hidden" name="subjId" value="<%=subjId%>"/>
    <%@include file="AddSubjectE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateSubject" value="Update"/>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10" class="cancel"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%}
    }
}%>
<%@include file="Footer.jsp"%>

