<!--Start of common part of update Cohort-->
<%
        MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
%>

<fieldset>
<table>
<thead>

<%cohoId = request.getParameter("cohoId");%>
<input type="hidden" name="cohoId" 
    value="<%=(cohoId != null) ? cohoId : "-1"%>"/>
    
<tr>
    <td class="dialh"><%--description:--%>
    <%=mdm.getCohortLongName(Fieldname.DESCRIPTION)%></td>
    <td>
        <input type="text" size="30" name="description"
                  value="<%=(description != null) ? description : ""%>"/>
    </td>
</tr>

</thead>
</table>
</fieldset>


