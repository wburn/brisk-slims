<!--Start of common part of update Ethnicity-->
<%
        MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
%>

<fieldset>
<table>
<thead>


<%ethnId = request.getParameter("ethnId");%>
<input type="hidden" name="ethnId" 
       value="<%=(ethnId != null) ? ethnId : ""%>" />

<tr>
    <td class="dialh"><%--ethnicity:--%>
    <%=mdm.getEthnicityLongName(Fieldname.ETHNICITY)%></td>
    <td>
        <input type="text" size="15" name="ethnicity"
                  value="<%=(ethnicity != null) ? ethnicity : ""%>"/>
    </td>
</tr>


</thead>
</table>
</fieldset>


