<!--Start of common part of update GenotypingRunSampleStatus-->
<%
        MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
%>

<fieldset>
<table>
<thead>


<%genotypingRunID = request.getParameter("genotypingRunID");%>
<input type="hidden" name="genotypingRunID"
       value="<%=(genotypingRunID != null) ? genotypingRunID : ""%>" />

<tr>
    <td class="dialh"><%--containerContentsID:--%>
    <%=mdm.getGenotypingRunSampleStatusLongName(Fieldname.CONTAINERCONTENTSID)%></td>
    <td>
        <input type="text" size="5" name="containerContentsID"
                  value="<%=(containerContentsID != null) ? containerContentsID : ""%>"/>
    </td>
</tr>
<tr>
    <td class="dialh"><%--valid:--%>
    <%=mdm.getGenotypingRunSampleStatusLongName(Fieldname.VALID)%></td>
    <td>
        <input type="text" size="5" name="valid"
                  value="<%=(valid != null) ? valid : ""%>"/>
    </td>
</tr>


</thead>
</table>
</fieldset>


