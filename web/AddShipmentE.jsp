<!--Start of common part of update Shipment-->
<%
            MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
%>

<fieldset>
    <table>
        <thead>
            <%shipId = request.getParameter("shipId");%>
        <input type="hidden" name="shipId"
               value="<%=(shipId != null) ? shipId : "-1"%>"/>

        <tr>
            <td class="dialh"><%--shippedOut:--%>
                Shipping Status</td>
            <td>
                <select name="shippedOut" id="shippedOut" onchange="shipFields(); $('#addContainerForm').valid(); resetReturnDate();">
                    <%if (shippedOut == null || (!shippedOut.equals("1") && !shippedOut.equals("2"))) {%>
                    <option value="0" <%=(shippedOut == null || shippedOut.equals("0")) ? "selected" : ""%> >None</option>
                    <%}%>
                    <option value="1" <%=(shippedOut != null && shippedOut.equals("1")) ? "selected" : ""%> >Shipped</option>
                    <option value="2" <%=(shippedOut != null && shippedOut.equals("2")) ? "selected" : ""%> >Returned</option>
                </select>
            </td>
        </tr>
        <tr>
            <td class="dialh"><%--shipmentName:--%>
                Shipment Name</td>
            <td><input type="text" name="shipmentName" value="<%= (shipmentName != null) ? shipmentName : ""%>" <%=(shippedOut == null || shippedOut.equals("0")) ? "disabled" : ""%>></td>
        </tr>
        <tr>
            <td class="dialh"><%--shipped date(yyyy/mm/dd):--%>
                <%=mdm.getContainerLongName(Fieldname.SHIPPEDDATE)%></td>
            <td>
                <input type="text" name="ayear" id="ayear" maxlength="4" size="4"
                       <%=tmpHttpSessObj.getFieldId("ayear")%>
                       value="<%=(ayear != null) ? ayear : ""%>"
                       <%=(shippedOut == null || shippedOut.equals("0")) ? "disabled" : ""%>/><b>-</b>
                <input type="text" name="amonth" id="amonth" maxlength="2" size="2"
                       <%=tmpHttpSessObj.getFieldId("amonth")%>
                       value="<%=(amonth != null) ? amonth : ""%>"
                       <%=(shippedOut == null || shippedOut.equals("0")) ? "disabled" : ""%>/><b>-</b>
                <input type="text" name="aday" id="aday" maxlength="2" size="2"
                       <%=tmpHttpSessObj.getFieldId("aday")%>
                       value="<%=(aday != null) ? aday : ""%>"
                       <%=(shippedOut == null || shippedOut.equals("0")) ? "disabled" : ""%>/> (YYYY MM DD)
            </td>
        </tr>
        <tr>
            <td class="dialh"><%--shippedToID:--%>
                Shipped To</td>
            <td>
                <select name="shippedToID" id="shippedToID"
                        <%=(shippedOut == null || shippedOut.equals("0")) ? "disabled" : ""%>>
                    <%=tmpHttpSessObj.getObjectPrompterUniqueField(ShippedTo.class, shippedToID, "description", false)%>
                </select>
            </td>
        </tr>
        <tr>
            <td class="dialh"><%--Shipping Comments:--%>
                <%=mdm.getContainerContentLongName(Fieldname.COMMENTS)%></td>
            <td>
                <textarea cols="40" rows="1" name="comment" <%=(shippedOut == null || shippedOut.equals("0")) ? "disabled" : ""%>><%=(comment != null) ? comment : ""%></textarea>
            </td>
        </tr>
        </thead>
    </table>
</fieldset>


