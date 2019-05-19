<%String lapasId = "ViewUser";%>
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
                pageContext.forward(response.encodeURL("./ViewUsers.jsp"));
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
    $(window).load(function(){
        $("#addUserForm").validate({
            success: function(label) {
                // set   as text for IE
                label.html(" ").addClass("checked");
            }
        });
    })
</script>
<%
            int messageNum = 0;
            User user = null;
            String userID;
            String visibleName;
            String lastName;
            String email;
            String login;
            String passw;
            String rightsStr;
            int rights = -1;
            String userTypeIDStr;
            int userTypeID = -1;
            String initials;
            String comments;

            if ((tmpAction = request.getParameter("addUser")) != null) {
                userID = request.getParameter("userID");
                visibleName = request.getParameter("visibleName");
                lastName = request.getParameter("lastName:");
                email = request.getParameter("email");
                login = request.getParameter("login");
                passw = request.getParameter("passw");
                rightsStr = request.getParameter("rights");
                if (rightsStr != null) {
                    try {
                        rights = Integer.parseInt(rightsStr);
                    } catch (NumberFormatException e) {
                        messageNum = 15;
                    }
                }
                if (userID == null || userID.trim().equals("")
                        || visibleName == null || visibleName.trim().equals("")
                        || login == null || login.trim().equals("")
                        || passw == null || passw.equals("")) {
                    messageNum = 2;
                }
                if (email == null || email.trim().equals("")) {
                    messageNum = 25;
                } else if (!email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")) {
                    messageNum = 25;
                }
                userTypeIDStr = request.getParameter("userTypeID");
                if (userTypeIDStr != null) {
                    try {
                        userTypeID = Integer.parseInt(userTypeIDStr);
                    } catch (NumberFormatException e) {
                        messageNum = 15;
                    }
                }
                initials = request.getParameter("initials");
                comments = request.getParameter("comments");

                if (messageNum == 0) {
                    messageNum = tmpHttpSessObj.addUser(userID, visibleName, login, passw, rights, userTypeID, initials, comments, lastName, email);

                }
                if (messageNum == 0) {
                    pageContext.forward(response.encodeURL("./ViewUsers.jsp") + "?add=1");
                    return;
                } else if (messageNum == 1) {
                    pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                    return;
                }
%>
<form method="POST" name="fForm" id="addUserForm"
      action="<%=response.encodeURL("./AddUser.jsp")%>">
    <a class="largest">Add User</a>
    <br>
    <a class="error">  Error:
        <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
        <%@include file="AddUserE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="addUser" value="  Add  "/>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" class="cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else if ((tmpAction = request.getParameter("updateUser")) != null) {
            user = tmpHttpSessObj.getCurrentUser();
            selfId = request.getParameter("selfId");
            userID = request.getParameter("userID");
            visibleName = request.getParameter("visibleName");
            lastName = request.getParameter("lastName:");
            email = request.getParameter("email");
            login = request.getParameter("login");
            passw = request.getParameter("passw");
            rightsStr = request.getParameter("rights");
            if (rightsStr != null) {
                try {
                    rights = Integer.parseInt(rightsStr);
                } catch (NumberFormatException e) {
                    messageNum = 15;
                }
            }
                if (userID == null || userID.trim().equals("")
                        || visibleName == null || visibleName.trim().equals("")
                        || login == null || login.trim().equals("")
                        || passw == null || passw.equals("")) {
                    messageNum = 2;
                }
                if (email == null || email.trim().equals("")) {
                    messageNum = 25;
                } else if (!email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")) {
                    messageNum = 25;
                }
            userTypeIDStr = request.getParameter("userTypeID");
            if (userTypeIDStr != null) {
                try {
                    userTypeID = Integer.parseInt(userTypeIDStr);
                } catch (NumberFormatException e) {
                    messageNum = 15;
                }
            }
            initials = request.getParameter("initials");
            comments = request.getParameter("comments");

            if (messageNum == 0) {
                messageNum = tmpHttpSessObj.updateUser(userID, visibleName, login, passw, rights, userTypeID, initials, comments, lastName, email);

            }
            if (messageNum == 0) {
                pageContext.forward(response.encodeURL("./ViewUsers.jsp"));
                return;
            }
            if (messageNum == 1) {
                pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                return;
            }

%>

<form method="POST" name="fForm" id="addUserForm"
      action="<%=response.encodeURL("./AddUser.jsp")%>">
    <a class="largest">Edit User</a>
    <br>
    <a class="error">  Error:
        <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <%userID = request.getParameter("userID");%>
    <input type="hidden" name="userID" value="<%=userID%>"/>
    <%@include file="AddUserE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateUser" value="Update"/>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" class="cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else //from View page
        if ((tmpAction = request.getParameter("userID")) != null) {
            if (tmpAction.equals("-1")) {//Add user
                //?????????????????????
                userID = null;
                visibleName = null;
                login = null;
                passw = null;
                lastName = null;
                email = null;
                rightsStr = null;
                rights = -1;
                userTypeID = -1;
                userTypeIDStr = null;
                initials = null;

%>

<form method="POST" name="fForm" id="addUserForm"
      action="<%=response.encodeURL("./AddUser.jsp")%>">
    <a class="largest">Add User</a>
    <br>
    <a class="error">&nbsp;</a>
    <%@include file="AddUserE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="addUser" value="  Add  "/>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" class="cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form><%
    } else {//edit User
        user = (User) tmpHttpSessObj.getObjectById(User.class, tmpAction);
        if (user != null) {
            selfId = tmpAction;
            userID = user.getId().toString();
            visibleName = user.getVisibleName();
            login = user.getLoginName();
            passw = user.getPassword();
            rights = user.getRights();
            userTypeID = user.getUserTypeID();
            initials = user.getInitials();
            lastName = user.getLastName();
            email = user.getEmail();

%>

<form method="POST" name="fForm" id="addUserForm"
      action="<%=response.encodeURL("./AddUser.jsp")%>">
    <a class="largest">Edit User</a>
    <br>
    <a class="error">&nbsp;</a>
    <input type="hidden" name="selfId" value="<%=selfId%>"/>
    <input type="hidden" name="userID" value="<%=userID%>"/>
    <%@include file="AddUserE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateUser" value="Update"/>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" class="cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%}
            }
        }%>
<%@include file="Footer.jsp"%>

