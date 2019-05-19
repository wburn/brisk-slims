<!--Start of common part of update Snp-->
<%
        MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
%>

<fieldset>
<table>
<thead>


<%snId = request.getParameter("snId");%>
<input type="hidden" name="snId" 
       value="<%=(snId != null) ? snId : ""%>" />

<tr>
    <td class="dialh"><%--Chromosome:--%>
    <%=mdm.getSnpLongName(Fieldname.CHROMOSOME)%></td>
    <td>
        <input type="text" size="5" name="Chromosome"
                  value="<%=(Chromosome != null) ? Chromosome : ""%>"/>
    </td>
</tr>
<tr>
    <td class="dialh"><%--pos:--%>
    <%=mdm.getSnpLongName(Fieldname.POS)%></td>
    <td>
        <input type="text" size="5" name="pos"
                  value="<%=(pos != null) ? pos : ""%>"/>
    </td>
</tr>
<tr>
    <td class="dialh"><%--geneID:--%>
    <%=mdm.getSnpLongName(Fieldname.GENEID)%></td>
    <td>
        <input type="text" size="5" name="geneID"
                  value="<%=(geneID != null) ? geneID : ""%>"/>
    </td>
</tr>
<tr>
    <td class="dialh"><%--fxnClass:--%>
    <%=mdm.getSnpLongName(Fieldname.FXN_CLASS)%></td>
    <td>
        <input type="text" size="5" name="fxnClass"
                  value="<%=(fxnClass != null) ? fxnClass : ""%>"/>
    </td>
</tr>
<tr>
    <td class="dialh"><%--rsNumber:--%>
    <%=mdm.getSnpLongName(Fieldname.RSNUMBER)%></td>
    <td>
        <input type="text" size="5" name="rsNumber"
                  value="<%=(rsNumber != null) ? rsNumber : ""%>"/>
    </td>
</tr>


</thead>
</table>
</fieldset>


