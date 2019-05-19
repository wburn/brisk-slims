<!--Start of common part of update Subject-->
<%
        MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
%>

<fieldset>
<table>
<thead>

<%subjId = request.getParameter("subjId");%>
<input type="hidden" name="subjId" 
       value="<%=(subjId != null) ? subjId : "-1"%>" size="4"/>

<tr>
    <td class="dialh"><%--subjectName:--%>
    <%=mdm.getSubjectLongName(Fieldname.SUBJECTNAME)%></td>
    <td>
        <input type="text" size="15" name="subjectName" class="required"
                  value="<%=(subjectName != null) ? subjectName : ""%>"/>
    </td>
</tr>

<tr>
    <td class="dialh"><%--ethnicityID:--%>
    <%=mdm.getSubjectLongName(Fieldname.ETHNICITY)%></td>
    <td>
          <select name="ethnicityID">
             <%=tmpHttpSessObj.getObjectPrompterUniqueField(Ethnicity.class, ethnicityID, "ethnicity", false)%>
        </select>
    </td>
</tr>

<tr>
    <td class="dialh"><%--gender:--%>
    <%=mdm.getSubjectLongName(Fieldname.GENDER)%></td>
    <td>
<select name="gender">
        <option value="0" <%=(gender == null || gender.equalsIgnoreCase("0")) ? "selected" : ""%>>Unknown</option>
        <option value="1" <%=(gender != null && gender.equalsIgnoreCase("1")) ? "selected" : ""%>>Male</option>
        <option value="2" <%=(gender != null && gender.equalsIgnoreCase("2")) ? "selected" : ""%>>Female</option>
      </select>
    </td>

</tr>

<tr>
    <td class="dialh"><%--fatherName:--%>
    <%=mdm.getSubjectLongName(Fieldname.FATHERID)%></td>
    <td>
        <select name="fatherName">
             <%=tmpHttpSessObj.getObjectPrompterField(Subject.class, fatherName, "subjectName", true)%>
        </select>
    </td>
</tr>
<tr>
    <td class="dialh"><%--motherName:--%>
    <%=mdm.getSubjectLongName(Fieldname.MOTHERID)%></td>
    <td>
        <select name="motherName">
             <%=tmpHttpSessObj.getObjectPrompterField(Subject.class, motherName, "subjectName", true)%>
        </select>
    </td>
</tr>

<tr>
    <td class="dialh"><%--familyID:--%>
    <%=mdm.getSubjectLongName(Fieldname.FAMILYID)%></td>
    <td>
        <input type="text" size="15" name="familyID"
                  value="<%=(familyID != null) ? familyID : ""%>"/>
    </td>
</tr>

<tr>
    <td class="dialh"><%--cohortID:--%>
    <%=mdm.getSubjectLongName(Fieldname.COHORTID)%></td>
    <td>
          <select name="cohortID">
             <%=tmpHttpSessObj.getObjectPrompterUniqueField(Cohort.class, cohortID, "description", false)%>
        </select>
    </td>
</tr>

<tr>
    <td class="dialh"><%--hasConsent:--%>
    <%=mdm.getSubjectLongName(Fieldname.HASCONSENT)%></td>
    <td>
<select name="hasConsent">
        <option value="0" <%=(hasConsent == null || hasConsent.equalsIgnoreCase("0")) ? "selected" : ""%>>Unknown</option>
        <option value="1" <%=(hasConsent!= null && hasConsent.equalsIgnoreCase("1")) ? "selected" : ""%>>Yes</option>
        <option value="2" <%=(hasConsent!= null && hasConsent.equalsIgnoreCase("2")) ? "selected" : ""%>>No</option>
      </select>
    </td>

</tr>
      <tr>
                <td class="dialh"><%--comments:--%>
                <%=mdm.getSubjectLongName(Fieldname.COMMENTS)%></td>
                <td>
                    <input type="text" size="40" name="comments" value="<%=(comments != null) ? comments : ""%>"/>
                </td>
            </tr>



</thead>
</table>
</fieldset>


