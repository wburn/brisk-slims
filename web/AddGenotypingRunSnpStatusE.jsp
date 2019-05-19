<!--Start of common part of update GenotypingRunSnpStatus-->
<%
        MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
%>

<fieldset>
<table>
<thead>


<%genoRunSnpStatId = request.getParameter("genoRunSnpStatId");%>
<input type="hidden" name="genoRunSnpStatId" 
       value="<%=(genoRunSnpStatId != null) ? genoRunSnpStatId : ""%>" />

<tr>
    <td class="dialh"><%--snpID:--%>
    <%=mdm.getGenotypingRunSnpStatusLongName(Fieldname.SNPID)%></td>
    <td>
        <input type="text" size="5" name="snpID"
                  value="<%=(snpID != null) ? snpID : ""%>"/>
    </td>
</tr>
<tr>
    <td class="dialh"><%--valid:--%>
    <%=mdm.getGenotypingRunSnpStatusLongName(Fieldname.VALID)%></td>
    <td>
        <input type="text" size="5" name="valid"
                  value="<%=(valid != null) ? valid : ""%>"/>
    </td>
</tr>
<tr>
    <td class="dialh"><%--strand:--%>
    <%=mdm.getGenotypingRunSnpStatusLongName(Fieldname.STRAND)%></td>
    <td>
        <input type="text" size="5" name="strand"
                  value="<%=(strand != null) ? strand : ""%>"/>
    </td>
</tr>


</thead>
</table>
</fieldset>


