<!--Start of common part of update MaterialType-->
<%
        MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
%>

<fieldset>
<table>
<thead>


<%matTypId = request.getParameter("matTypId");%>
<input type="hidden" name="matTypId" 
       value="<%=(matTypId != null) ? matTypId : ""%>" />

<tr>
    <td class="dialh"><%--description:--%>
    <%=mdm.getMaterialTypeLongName(Fieldname.DESCRIPTION)%></td>
    <td>
        <input type="text" size="30" name="description"
                  value="<%=(description != null) ? description : ""%>"/>
    </td>
</tr>


</thead>
</table>
</fieldset>


