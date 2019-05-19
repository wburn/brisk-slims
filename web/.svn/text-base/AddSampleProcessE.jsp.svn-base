<!--Start of common part of update SampleProcess-->
<%
        MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
%>

<fieldset>
<table>
<thead>
<%if (sampProcId != null) {%>
<tr>
    <%--sampleProcessID should not be field if adding new SampleProcess--%>
    <td class="dialh"><%--SampleProcess ID:--%>
    <%=mdm.getSampleProcessLongName(Fieldname.SAMPLEPROCESSID)%></td>
    <td>
        <!--USED TO BE IN INPUT TAG%=tmpHttpSessObj.getFieldId("sampProcId")%-->
        <%sampProcId = request.getParameter("sampProcId");%>
        <input type="hidden" name="sampProcId" value="<%=sampProcId%>"/>
        <input type="text" name="sampProcId" readonly="readonly"
               style="background-color:#E5EAF0"
               value="<%=sampProcId%>" size="4"/>
    </td>
</tr>
<%} else {%> <%--sampleProcessID should not be field if adding new SampleProcess--%>

<%sampProcId = request.getParameter("sampProcId");%>
<input type="hidden" name="sampProcId" value="<%=sampProcId%>"/>
<input type="hidden" name="sampProcId" readonly="readonly"
       style="background-color:#E5EAF0"
       value="<%=(sampProcId != null) ? sampProcId : ""%>" size="4"/>
<%}%>
<tr>
    <td class="dialh"><%--description:--%>
    <%=mdm.getSampleProcessLongName(Fieldname.DESCRIPTION)%></td>
    <td>
        <input type="text" size="5" name="description"
                  value="<%=(description != null) ? description : ""%>"/>
    </td>
</tr>
<tr>
    <td class="dialh"><%--sortOrder:--%>
    <%=mdm.getSampleProcessLongName(Fieldname.SORTORDER)%></td>
    <td>
        <input type="text" size="5" name="sortOrder"
                  value="<%=(sortOrder != null) ? sortOrder : ""%>"/>
    </td>
</tr>


</thead>
</table>
</fieldset>


