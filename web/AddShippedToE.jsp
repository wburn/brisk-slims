<!--Start of common part of update ShippedTo-->
<%
        MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
%>

<fieldset>
    <table>
        <thead>
            
                    <%shipId = request.getParameter("shipId");%>
                    <input type="hidden" name="shipId" 
                           value="<%=(shipId != null) ? shipId : "-1"%>" size="4"/>

            <tr>
                <td class="dialh"><%--Description:--%>
                <%=mdm.getShippedToLongName(Fieldname.DESCRIPTION)%></td>
                <td>
                    <input type="text" size="25" name="description"
                              value="<%=(description != null) ? description : ""%>"/>
                </td>
            </tr>
            <tr>
<!--tr>
    <td class="dialh"><%--sortOrder:--%>
    <%=mdm.getShippedToLongName(Fieldname.SORTORDER)%></td>
    <td>
        <input type="text" size="5"  name="sortOrder"
                  value="<%=(sortOrder != null) ? sortOrder : ""%>"/>
    </td>
</tr-->

        </thead>
    </table>
</fieldset>
<!--End of common part of update persons-->
