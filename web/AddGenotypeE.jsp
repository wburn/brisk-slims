<!--Start of common part of update Genotype-->
<%
        MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
%>

<fieldset>
<table>
<thead>


<%genotypId = request.getParameter("genotypId");%>
<input type="hidden" name="genotypId" 
       value="<%=(genotypId != null) ? genotypId : ""%>" />

<tr>
    <td class="dialh"><%--allele1:--%>
    <%=mdm.getGenotypeLongName(Fieldname.ALLELE1)%></td>
    <td>
        <input type="text" size="5" name="allele1"
                  value="<%=(allele1 != null) ? allele1 : ""%>"/>
    </td>
</tr>
<tr>
    <td class="dialh"><%--allele2:--%>
    <%=mdm.getGenotypeLongName(Fieldname.ALLELE2)%></td>
    <td>
        <input type="text" size="5" name="allele2"
                  value="<%=(allele2 != null) ? allele2 : ""%>"/>
    </td>
</tr>
<tr>
    <td class="dialh"><%--containerContentsID:--%>
    <%=mdm.getGenotypeLongName(Fieldname.CONTAINERCONTENTSID)%></td>
    <td>
        <input type="text" size="5" name="containerContentsID"
                  value="<%=(containerContentsID != null) ? containerContentsID : ""%>"/>
    </td>
</tr>
<tr>
    <td class="dialh"><%--genotypingRunID:--%>
    <%=mdm.getGenotypeLongName(Fieldname.GENOTYPINGRUNID)%></td>
    <td>
        <input type="text" size="5" name="genotypingRunID"
                  value="<%=(genotypingRunID != null) ? genotypingRunID : ""%>"/>
    </td>
</tr>
<tr>
    <td class="dialh"><%--snpID:--%>
    <%=mdm.getGenotypeLongName(Fieldname.SNPID)%></td>
    <td>
        <input type="text" size="5" name="snpID"
                  value="<%=(snpID != null) ? snpID : ""%>"/>
    </td>
</tr>


</thead>
</table>
</fieldset>


