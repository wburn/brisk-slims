<!--Start of common part of update Sample-->
<%
        MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
%>

<fieldset>
<table>
<thead>

<%sampId = request.getParameter("sampId");%>
<input type="hidden" name="sampId" 
       value="<%=(sampId != null) ? sampId : "-1"%>"/>
<tr>
    <td class="dialh"><%--sampleName:--%>
    <%=mdm.getSampleLongName(Fieldname.SAMPLENAME)%>*</td>
    <td>
        <input type="text" size="25" name="sampleName" class="required"
                  value="<%=(sampleName != null) ? sampleName : ""%>"/>
    </td>
</tr>
<tr>
    <td class="dialh"><%--valid:--%>
    <%=mdm.getSampleLongName(Fieldname.VALID)%></td>
    <td>
      <select name="valid">
        <option value="1" <%=(valid != null && valid.equals("1")) ? "selected" : ""%>>Yes</option>
        <option value="0" <%=(valid == null || valid.equals("0")) ? "selected" : ""%>>No</option>
      </select>
    </td>
</tr>


<tr>
    <td class="dialh"><%--parentID:--%>
    <%=mdm.getSampleLongName(Fieldname.PARENT)%></td>
    <td>
          <select name="parentID" class="digits">
              <%=tmpHttpSessObj.getObjectPrompterUniqueField(Sample.class, parentID, "sampleName", true)%>
          </select>
    </td>
</tr>
<tr>
    <td class="dialh"><%--sampleTypeID:--%>
    <%=mdm.getSampleLongName(Fieldname.SAMPLETYPE)%></td>
    <td>
          <select name="sampleTypeID">
              <%=tmpHttpSessObj.getObjectPrompterUniqueField(SampleType.class, sampleTypeID, "description", true)%>
          </select>
    </td>
</tr>
<tr>
    <td class="dialh"><%--sampleProcessID:--%>
    <%=mdm.getSampleLongName(Fieldname.SAMPLEPROCESS)%></td>
    <td>
          <select name="sampleProcessID">
              <%=tmpHttpSessObj.getObjectPrompterUniqueField(SampleProcess.class, sampleProcessID, "description", true)%>
          </select>
    </td>
</tr>
<tr>
    <td class="dialh"><%--subjectID:--%>
    <%=mdm.getSampleLongName(Fieldname.SUBJECT)%></td>
    <td>
          <select name="subjectID">
              <%=tmpHttpSessObj.getObjectPrompterSubject(subjectID)%>
          </select>
    </td>
</tr>


</thead>
</table>
</fieldset>


