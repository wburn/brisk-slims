<!--Start of common part of update Control-->
<%
        MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
%>

<fieldset>
<table>
<thead>


<%cntrlId = request.getParameter("cntrlId");%>
<input type="hidden" name="cntrlId" 
       value="<%=(cntrlId != null) ? cntrlId : ""%>" />

<tr>
    <td class="dialh"><%--description:--%>
    <%=mdm.getControlLongName(Fieldname.DESCRIPTION)%></td>
    <td>
        <input type="text" size="10" name="description"
                  value="<%=(description != null) ? description : ""%>"/>
    </td>
</tr>
<tr>
    <td class="dialh"><%--controlType:--%>
    <%=mdm.getControlLongName(Fieldname.CONTROLTYPE)%></td>
    <td>
        <select name="controlType" value="<%=(controlType != null) ? controlType : ""%>">
            <option value="0">Negative</option>
            <option value="1">Positive</option>
        </select>
    </td>
</tr>


</thead>
</table>
</fieldset>


