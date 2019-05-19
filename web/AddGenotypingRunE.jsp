<!--Start of common part of update GenotypingRun-->
<%
        MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
%>

<fieldset>
<table>
<thead>

<%genoRunId = request.getParameter("genoRunId");%>
<input type="hidden" name="genoRunId" 
       value="<%=(genoRunId != null) ? genoRunId : ""%>" size="4"/>

<tr>
    <td class="dialh"><%--description:--%>
    <%=mdm.getGenotypingRunLongName(Fieldname.DESCRIPTION)%></td>
    <td>
        <input type="text" size="10" name="description"
                  value="<%=(description != null) ? description : ""%>"/>
    </td>
</tr>
      <tr>
        <td class="dialh"><%--date(yyyy/mm/dd):--%>
        <%=mdm.getGenotypingRunLongName(Fieldname.DATE)%></td>
        <td>
          <input type="text" name="ayear" maxlength="4" size="4"
                 <%=tmpHttpSessObj.getFieldId("ayear")%>
                 value="<%=(ayear!=null)?ayear:""%>"/><b>-</b>
          <input type="text" name="amonth" maxlength="2" size="2"
                 <%=tmpHttpSessObj.getFieldId("amonth")%>
                 value="<%=(amonth!=null)?amonth:""%>"/><b>-</b>
          <input type="text" name="aday" maxlength="2" size="2"
                 <%=tmpHttpSessObj.getFieldId("aday")%>
                 value="<%=(aday!=null)?aday:""%>"/>
        </td>
      </tr>
    <tr>


</thead>
</table>
</fieldset>


