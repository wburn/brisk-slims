<!--Start of common part of update Freezer-->
<%
        MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
%>

<fieldset>
<table>
<thead>

<%freezId = request.getParameter("freezId");%>
<input type="hidden" name="freezId" 
       value="<%=(freezId != null) ? freezId : "-1"%>" size="4"/>
       
<tr>
    <td class="dialh"><%--description:--%>
    <%=mdm.getFreezerLongName(Fieldname.DESCRIPTION)%></td>
    <td>
        <input type="text" size="20" name="description"
                 value="<%=(description != null) ? description : ""%>"/>
    </td>
</tr>
<tr>
    <td class="dialh"><%--location:--%>
    <%=mdm.getFreezerLongName(Fieldname.LOCATION)%></td>
    <td>
        <input type="text" size="20"  name="location"
                  value="<%=(location != null) ? location : ""%>"/>
    </td>
</tr>
<tr>
    <td class="dialh"><%--temperature:--%>
    <%=mdm.getFreezerLongName(Fieldname.TEMPERATURE)%></td>
    <td>
        <input type="text" size="5" name="temperature"
                  value="<%=(temperature != null) ? temperature : ""%>"/>
    </td>
</tr>



</thead>
</table>
</fieldset>


