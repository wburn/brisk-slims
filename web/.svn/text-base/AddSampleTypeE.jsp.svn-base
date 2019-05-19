<!--Start of common part of update SampleType-->
<%
        MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
%>

<fieldset>
<table>
<thead>

<%sampTypId = request.getParameter("sampTypId");%>
<input type="hidden" name="sampTypId" 
       value="<%=(sampTypId != null) ? sampTypId : "-1"%>"/>

<tr>
    <td class="dialh"><%--description:--%>
    <%=mdm.getSampleTypeLongName(Fieldname.DESCRIPTION)%></td>
    <td>
        <input type="text" size="25" name="description"
                  value="<%=(description != null) ? description : ""%>"/>
    </td>
</tr>
<!--tr>
    <td class="dialh"><%--sortOrder:--%>
    <%=mdm.getSampleTypeLongName(Fieldname.SORTORDER)%></td>
    <td>
        <input type="text" size="5" name="sortOrder"
                  value="<%=(sortOrder != null) ? sortOrder : ""%>"/>
    </td>
</tr-->


</thead>
</table>
</fieldset>


