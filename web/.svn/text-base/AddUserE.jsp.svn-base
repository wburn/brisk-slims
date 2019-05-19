<!--Start of common part of update User-->
<%
            MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
%>

<fieldset>
    <table>
        <thead>


            <%userID = request.getParameter("userID");%>
        <input type="hidden" name="userID"
               value="<%=(userID != null) ? userID : ""%>" />

        <tr>
            <td class="dialh"><%--visibleName:--%>
                First Name*</td>
            <td>
                <input type="text" size="30" name="visibleName" class="required"
                       value="<%=(visibleName != null) ? visibleName : ""%>"/>
            </td>
        </tr>
        <tr>
            <td class="dialh"><%--visibleName:--%>
                Last Name</td>
            <td>
                <input type="text" size="30" name="lastName"
                       value="<%=(lastName != null) ? lastName : ""%>"/>
            </td>
        </tr>
        <tr>
            <td class="dialh"><%--visibleName:--%>
                Email*</td>
            <td>
                <input type="text" size="30" name="email" class="required email"
                       value="<%=(email != null) ? email : ""%>"/>
            </td>
        </tr>
        <tr>
            <td class="dialh"><%--login:--%>
                <%=mdm.getUserLongName(Fieldname.LOGIN)%>*</td>
            <td>
                <input type="text" size="15" name="login" class="required"
                       value="<%=(login != null) ? login : ""%>"/>
            </td>
        </tr>
        <tr>
            <td class="dialh"><%--passw:--%>
                Password*</td>
            <td>
                <input type="password" size="15" name="passw" class="required"/>
            </td>
        </tr>
        <tr>
            <td class="dialh"><%--rights:--%>
                <%=mdm.getUserLongName(Fieldname.RIGHTS)%></td>
            <td>
                <select name="rights">
                    <option value="10" <%=(rights == 10) ? "selected" : ""%>>Admin User</option>
                    <option value="20" <%=(rights == 20) ? "selected" : ""%>>Power User: Wet Lab</option>
                    <option value="30" <%=(rights == 30) ? "selected" : ""%>>Power User: Dry Lab</option>
                    <option value="40" <%=(rights == 40) ? "selected" : ""%>>Advanced User: Wet Lab</option>
                    <option value="50" <%=(rights == 50) ? "selected" : ""%>>Advanced User: Dry Lab</option>
                    <option value="60" <%=(rights == 60) ? "selected" : ""%>>Basic User</option>
                    <option value="100" <%=(rights == 100) ? "selected" : ""%>>No Access</option>
                </select>
                <input type="button" class="button" name="getPermissionDoc" onclick="openPermissionsDoc()" value="View Permissions"/>
            </td>
        </tr>
        <tr>
            <td class="dialh"><%--userTypeID:--%>
                <%=mdm.getUserLongName(Fieldname.USERTYPEID)%></td>
            <td>
                <select name="userTypeID">
                    <%=tmpHttpSessObj.getObjectPrompterUniqueField(UserType.class, String.valueOf(userTypeID), "description", false)%>
                </select>
            </td>
        </tr>
        <tr>
            <td class="dialh"><%--initials:--%>
                <%=mdm.getUserLongName(Fieldname.INITIALS)%></td>
            <td>
                <input type="text" size="5" name="initials"
                       value="<%=(initials != null) ? initials : ""%>"/>
            </td>
        </tr>


        </thead>
    </table>
</fieldset>


