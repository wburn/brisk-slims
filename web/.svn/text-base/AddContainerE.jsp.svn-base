<!--Start of common part of update Container-->
<%
            MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
%>

<script  type="text/javascript" language="javascript">
    function setFocus(){
        field = document.getElementById("kluda");
        if (field) field.focus();
        else
            document.fForm.genId.focus();
    }
    function shipFields(){
        var out = document.getElementById("shippedOut").value;
        if(out == 0){
            document.getElementById("shippedToID").disabled = true;
            document.getElementById("shippedToID").className = null;
            document.getElementById("shipmentName").disabled = true;
            document.getElementById("shipmentName").className = null;
            document.getElementById("ayear").disabled = true;
            document.getElementById("ayear").className = null;
            document.getElementById("amonth").disabled = true;
            document.getElementById("amonth").className = null;
            document.getElementById("aday").disabled = true;
            document.getElementById("aday").className = null;
            document.getElementById("comment").disabled = true;
            document.getElementById("comment").disabled = null;
        }
        else{
            document.getElementById("shippedToID").disabled = false;
            document.getElementById("shipmentName").disabled = false;
            document.getElementById("ayear").disabled = false;
            document.getElementById("amonth").disabled = false;
            document.getElementById("aday").disabled = false;
            document.getElementById("comment").disabled = false;
        }
    }
    function resetReturnDate(){
        if (<%=shippedOut%>==1)
        var dateObj = new Date();
        $("#ayear").val(dateObj.getFullYear());
        $("#amonth").val(dateObj.getMonth()+1);
        $("#aday").val(dateObj.getDate());
    }
</script>
<fieldset>
    <table cellspacing="10">
        <thead>
            <%contId = request.getParameter("contId");%>
        <input type="hidden" name="contId"  
               value="<%=(contId != null) ? contId : "-1"%>"/>

        <tr>
            <td class="dialh"><%--containerName:--%>
                <%=mdm.getContainerLongName(Fieldname.CONTAINERNAME)%>*</td>
            <td>
                <input type="text" size="40" name="containerName"
                       value="<%=(containerName != null) ? containerName : ""%>" class="required"/>
            </td>
        </tr>
        <tr>
            <td class="dialh"><%--maker's initials--%>
                Plate maker(s)'s initials</td>
            <td>
                <input type="text" size="30" name="initials"
                       value="<%=(initials != null) ? initials : ""%>"/>
            </td>
        </tr>

        <tr>
            <td class="dialh"><%--date on container(yyyy/mm/dd):--%>
                <%=mdm.getContainerLongName(Fieldname.DATEONCONTAINER)%>*</td>
            <td>
                <input type="text" name="cyear" id="cyear" maxlength="4" size="4"
                       <%=tmpHttpSessObj.getFieldId("cyear")%>
                       value="<%=(cyear != null) ? cyear : ""%>"
                       /><b>-</b>
                <input type="text" name="cmonth" id="cmonth" maxlength="2" size="2"
                       <%=tmpHttpSessObj.getFieldId("cmonth")%>
                       value="<%=(cmonth != null) ? cmonth : ""%>"
                       /><b>-</b>
                <input type="text" name="cday" id="cday" maxlength="2" size="2"
                       <%=tmpHttpSessObj.getFieldId("cday")%>
                       value="<%=(cday != null) ? cday : ""%>"
                       /> (YYYY MM DD)
            </td>
        </tr>

        <tr>
            <td class="dialh"><%--valid:--%>
                <%=mdm.getSampleLongName(Fieldname.VALID)%></td>
            <td>
                <select name="valid">
                    <option value="1" <%=(valid == null || valid.equals("1")) ? "selected" : ""%>>Yes</option>
                    <option value="0" <%=(valid != null && valid.equals("0")) ? "selected" : ""%>>No</option>
                </select>
            </td>
        </tr>

        <tr>
            <td class="dialh"><%--is stock--%>
                <%=mdm.getContainerLongName(Fieldname.ISSTOCK)%></td>
            <td>
                <select name="isStock">
                    <option value="1" <%=(isStock != null && isStock.equals("1")) ? "selected" : ""%>>Yes</option>
                    <option value="0" <%=(isStock == null || isStock.equals("0")) ? "selected" : ""%>>No</option>
                </select>
            </td>
        </tr>

        <tr>
            <td class="dialh"><%--containerTypeID--%>
                <%=mdm.getContainerLongName(Fieldname.CONTAINERTYPE)%></td>
            <td>
                <select name="containerTypeID">
                    <%=tmpHttpSessObj.getObjectPrompterUniqueField(ContainerType.class, containerTypeID, "description", false)%>
                </select>
            </td>
        </tr>
        <tr>
            <td class="dialh"><%--FreezerID:--%>
                <%=mdm.getContainerLongName(Fieldname.FREEZER)%></td>
            <td>
                <select name="freezerID">
                    <%=tmpHttpSessObj.getObjectPrompterUniqueField(Freezer.class, freezerID, "description", true)%>
                </select>
            </td>
        </tr>
        <tr>
            <td class="dialh"><%--shelf:--%>
                <%=mdm.getContainerLongName(Fieldname.SHELF)%>*</td>
            <td>
                <input type="text" size="5"  name="shelf"
                       value="<%=(shelf != null) ? shelf : ""%>" class="required digit"/>
            </td>
        </tr>
        <tr>
            <td class="dialh"><%--location--%>
                <%=mdm.getContainerLongName(Fieldname.LOCATION)%></td>
            <td>
                <input type="text" size="15"  name="location"
                       value="<%=(location != null) ? location : ""%>"/>
            </td>
        </tr>
        <tr>
            <td class="dialh"><%--discarded:--%>
                <%=mdm.getContainerLongName(Fieldname.DISCARDED)%>?</td>
            <td>
                <select name="discarded">
                    <option value="0" <%=(discarded == null || discarded.equals("0")) ? "selected" : ""%>>No</option>
                    <option value="1" <%=(discarded != null && discarded.equals("1")) ? "selected" : ""%>>Yes</option>
                </select>
            </td>
        </tr>


        <%System.out.println("4 in AddContainerE.jsp");%>

        <tr>
            <td class="dialh"><%--comments:--%>
                <%=mdm.getContainerContentLongName(Fieldname.COMMENTS)%></td>
            <td>
                <textarea cols="40" rows="1" name="comments"><%=(comments != null) ? comments : ""%></textarea>
            </td>
        </tr>
        <tr>
            <td class="dialh"><%--barcode:--%>
                <%=mdm.getContainerLongName(Fieldname.BARCODE)%></td>
            <td>
                <input type="text" size="40" name="barcode" id="barcode"
                       value="<%=(barcode != null) ? barcode : ""%>" />
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <hr><b style=" font-size: 13px">Shipping Options</b>
                Note: Only use this feature to EDIT. To RETURN/SHIP items, please do so from the Container screen.
            </td>
        </tr>
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
        <tr>
            <td></td>
            <td>
                <input type="checkbox" name="applyAll" value="true" id="applyAll"/><label for="applyAll">Apply options to all containers in shipment?</label>
            </td>
        </tr>
        </thead>
    </table>
</fieldset>


