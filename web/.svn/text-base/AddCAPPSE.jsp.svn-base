<%-- 
    Document   : AddCAPPSE
    Created on : May 18, 2010, 9:16:49 AM
    Author     : ATan1
--%>
<!--Start of common part of update Sample-->
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
            document.getElementById("ayear").disabled = true;
            document.getElementById("amonth").disabled = true;
            document.getElementById("aday").disabled = true;
        }
        else{
            document.getElementById("shippedToID").disabled = false;
            document.getElementById("ayear").disabled = false;
            document.getElementById("amonth").disabled = false;
            document.getElementById("aday").disabled = false;
        }
    }
    function barVal(){
        var rBar = document.getElementById("barcodeRed");
        var pBar = document.getElementById("barcodePurple");

        if (rBar.value == null || rBar.value.valueOf() == ""){
            rBar.style.border='2px solid red';
            document.getElementById("redError").innerHTML = "Please enter a barcode";
            red = false;
        }
        else{
            rBar.style.border='1px solid #7F9DB9';
            document.getElementById("redError").innerHTML = "";
            red = true;
        }
        if (pBar.value == null || pBar.value.valueOf() == ""){
            pBar.style.border='2px solid red';
            document.getElementById("purpError").innerHTML = "Please enter a barcode";
            purple = false;
        }
        else{
            pBar.style.border='1px solid #7F9DB9';
            document.getElementById("purpError").innerHTML = "";
            purple = true;
        }
        if (rBar.value.valueOf()==pBar.value.valueOf()){
            rBar.style.border='2px solid red';
            pBar.style.border='2px solid red';
            document.getElementById("errorLogs").innerHTML = "Each barcode has to be unique";
            conclusion = false;
        }
        else {
            if (red){
                rBar.style.border='1px solid #7F9DB9';
            }
            if (purple){
                pBar.style.border='1px solid #7F9DB9';
            }
            document.getElementById("errorLogs").innerHTML = "";
            conclusion = true;
        }

        return (volVal() && conclusion && purple && red);
    }
    function volVal() {
        var vVal = document.getElementById("volume");
        if (vVal.value == null || vVal.value.valueOf() == ""){
            vVal.style.border='2px solid red';
            document.getElementById("volError").innerHTML = "Please enter a concentration";
            return false;
        }
        else {
            vVal.style.border='1px solid #7F9DB9';
            document.getElementById("volError").innerHTML = "";
            return true;
        }
    }
</script>
<fieldset>
    <table>
        <thead>

        <input type="hidden" name="sampId"
               value="<%=(sampId != null) ? sampId : "-1"%>"/>
        <%sampId = request.getParameter("sampId");%>
        <input type="hidden" name="subjId"
               value="<%=(subjId != null) ? subjId : "-1"%>"/>
        <%subjId = request.getParameter("subjId");%>
        <input type="hidden" name="contId"
               value="<%=(contId != null) ? contId : "-1"%>"/>
        <%contId = request.getParameter("contId");%>
        <%contContId = request.getParameter("contContId");%>
        <input type="hidden" name="contContId"
               value="<%=(contContId != null) ? contContId : "-1"%>"/>
        <tr>
            <td>
                <p><a class="larger"><%=subjectName%> - <%=parentFunc%></a></p>
            </td>
            <td style="color: red" id="errorLogs"></td>
        </tr>
        <tr>
            <td class="dialh"><%--purple barcode:--%>
                Purple <%=mdm.getSubjectLongName(Fieldname.BARCODE)%></td>
            <td>
                <input type="text" name="barcodePurple" id="barcodePurple" style="" onchange=""/>
            </td>
            <td style="color: red" id="purpError"></td>
        </tr>
        <tr>
            <td class="dialh"><%--red barcode:--%>
                Red <%=mdm.getSubjectLongName(Fieldname.BARCODE)%></td>
            <td>
                <input type="text" name="barcodeRed" id="barcodeRed" style="" onchange=""/>
            </td>
            <td style="color: red" id="redError"></td>
        </tr>

        <tr>
            <td class="dialh"><%--volume:--%>
                <%=mdm.getContainerContentLongName(Fieldname.VOLUME)%></td>
            <td>
                <input type="text" size="5" name="volume" id="volume"
                       value="<%=(volume != null && !volume.equals("-1.0")) ? volume : ""%>"/>
                uL
            </td>
            <td style="color: red" id="volError"></td>
        </tr>


        </thead>
    </table>
</fieldset>



<input type="hidden" size="25" name="sampleNamePurple"
       value="TestBar<%=(subjectName != null) ? subjectName : ""%>Purple"/>

<input type="hidden" size="25" name="sampleNameRed"
       value="TestBar<%=(subjectName != null) ? subjectName : ""%>Red"/>

<input name="valid" type="hidden" value="1"/>

<input name="parentID" type="hidden" value=""/>

<input type="hidden" value="0" name="sampleTypeID"/>

<input type="hidden" value="" name="sampleProcessID"/>

<input type="hidden" name="subjectID" value="<%=subjId%>" />


<%----------------------------------CONTAINER---------------------%>
<%----------------------------------CONTAINER---------------------%>
<%----------------------------------CONTAINER---------------------%>
<%----------------------------------CONTAINER---------------------%>

<input type="hidden" size="40" name="containerName"
       value="ignored"/>

<input type="hidden" size="30" name="initials"
       value="AT"/>

<input type="hidden" name="cyear" id="cyear" maxlength="4" size="4"
       <%=tmpHttpSessObj.getFieldId("cyear")%>
       value="<%=(cyear != null) ? cyear : ""%>"
       />
<input type="hidden" name="cmonth" id="cmonth" maxlength="2" size="2"
       <%=tmpHttpSessObj.getFieldId("cmonth")%>
       value="<%=(cmonth != null) ? cmonth : ""%>"
       />
<input type="hidden" name="cday" id="cday" maxlength="2" size="2"
       <%=tmpHttpSessObj.getFieldId("cday")%>
       value="<%=(cday != null) ? cday : ""%>"
       />

<input type="hidden" value="1" name="isStock"/>

<input type="hidden" value="2" name="containerTypeID"/>

<input type="hidden" value="" name="freezerID"/>

<input type="hidden" size="5"  name="shelf"
       value=""/>

<input type="hidden" size="15"  name="location"
       value=""/>

<input type="hidden" value="0" name="discarded"/>

<input type="hidden" value="0" name="shippedOut" id="shippedOut"/>

<input type="hidden" name="ayear" id="ayear" maxlength="4" size="4"
       <%=tmpHttpSessObj.getFieldId("ayear")%>
       value="<%=(ayear != null) ? ayear : ""%>"
       <%=(shippedOut == null || shippedOut.equals("0")) ? "disabled" : ""%>/>
<input type="hidden" name="amonth" id="amonth" maxlength="2" size="2"
       <%=tmpHttpSessObj.getFieldId("amonth")%>
       value="<%=(amonth != null) ? amonth : ""%>"
       <%=(shippedOut == null || shippedOut.equals("0")) ? "disabled" : ""%>/>
<input type="hidden" name="aday" id="aday" maxlength="2" size="2"
       <%=tmpHttpSessObj.getFieldId("aday")%>
       value="<%=(aday != null) ? aday : ""%>"
       <%=(shippedOut == null || shippedOut.equals("0")) ? "disabled" : ""%>/>

<input type="hidden" value="" name="shippedToID" id="shippedToID"/>

<textarea style="visibility: hidden" cols="40" rows="1" name="commentsPurple">Purple Top Tube</textarea>

<textarea style="visibility: hidden" cols="40" rows="1" name="commentsRed">Red Top Tube</textarea>

<%-----------------------CONTAINER CONTENT ------------------------------%>
<%-----------------------CONTAINER CONTENT ------------------------------%>
<%-----------------------CONTAINER CONTENT ------------------------------%>
<%-----------------------CONTAINER CONTENT ------------------------------%>
<%-----------------------CONTAINER CONTENT ------------------------------%>
<input type="hidden" value="" name="parentName"/>

<input type="hidden" value="" name="parentRow"/>

<input type="hidden" value="" name="parentColumn"/>

<input type="hidden" value="0" name="contaminated"/>

<input type="hidden" value="1" name="row"/>

<input type="hidden" size="3" name="column" id="column"
       value="1"/>

<input type="hidden" value="5" id="materialTypeID" name="materialTypeID"/>

<input type="hidden" name="ayear" id="ayear" maxlength="4" size="4"
       <%=tmpHttpSessObj.getFieldId("ayear")%>
       value="<%=(ayear != null) ? ayear : ""%>"
       <%=(materialTypeID == null || !materialTypeID.equals("2")) ? "disabled" : ""%>/>
<input type="hidden" name="amonth" id="amonth" maxlength="2" size="2"
       <%=tmpHttpSessObj.getFieldId("amonth")%>
       value="<%=(amonth != null) ? amonth : ""%>"
       <%=(materialTypeID == null || !materialTypeID.equals("2")) ? "disabled" : ""%>/>
<input type="hidden" name="aday" id="aday" maxlength="2" size="2"
       <%=tmpHttpSessObj.getFieldId("aday")%>
       value="<%=(aday != null) ? aday : ""%>"
       <%=(materialTypeID == null || !materialTypeID.equals("2")) ? "disabled" : ""%>/>

<input type="hidden" size="5" name="dilution" id="dilution"
       value="<%=(dilution != null && !dilution.equals("-1")) ? dilution : ""%>"/>

<input type="hidden" size="40" name="comments" id="comments"
       value="<%=(comments != null) ? comments : ""%>" />

<input type="hidden" size="5" name="concentration" id="concentration"
       value="-1"/>
