<!--Start of common part of update ContainerType-->
<%
        MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
%>

<fieldset>
<table>
<thead>
<%contTypeId = request.getParameter("contTypeId");%>
<input type="hidden" name="contTypeId" 
       value="<%=(contTypeId != null) ? contTypeId : "-1"%>"/>

<tr>
    <td class="dialh"><%--description:--%>
    <%=mdm.getContainerTypeLongName(Fieldname.DESCRIPTION)%></td>
    <td>
        <input type="text" size="30" name="description" id="name"
                  value="<%=(description != null) ? description : ""%>"/>
    </td>
</tr>
<tr>
    <td class="dialh"><%--rows:--%>
    <%=mdm.getContainerTypeLongName(Fieldname.ROWS)%></td>
    <td>
        <input type="text" size="5"  name="rows" id="row"
                  value="<%=(rows != null) ? rows : ""%>"/>
    </td>
</tr>
<tr>
    <td class="dialh"><%--columns:--%>
    <%=mdm.getContainerTypeLongName(Fieldname.COLUMNS)%></td>
    <td>
        <input type="text" size="5"  name="columns" id="col"
                  value="<%=(columns != null) ? columns : ""%>"/>
    </td>
</tr>

</thead>
</table>
</fieldset>


