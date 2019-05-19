<%--
    Document   : Add96WellPlatesE
    Created on : Nov 17, 2009, 10:40:20 AM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">


<script  type="text/javascript" language="javascript">
    function setFocus(){
        field = document.getElementById("kluda");
        if (field) field.focus();
        else
            document.fForm.genId.focus();
    }
    var submitFlag = 0;
    function checkSubmitFlag(){
        alert("in check");
        if (submitFlag++) {
            alert("in check 1");
            return false;}
        else {
            alert("in check 2");
            //document.getElementById('cancel').disabled=true
            //location.replace("ViewContainersList.jsp");
            return true;
        }
    }


    function selectContainer(id, minVol){

        // make sure there's enough volume for all the plates being made
        var numToMake = document.getElementsByName("deckLocations").length;
        if((minVol*numToMake)>=(5*numToMake)){
            toggleSelect(id);
        }
        else{
            alert("Error: This container does not have enough "
                +"volume to perform this operation. At least 5ul is required in every well for every plate being made.");
            document.getElementById('doPlate'+id).checked=false;
        }
    }
    function toggleSelect(id){
        var check = document.getElementById('doPlate'+id);
        if(check.checked){document.getElementById('orderNewPlate'+id).disabled=false}
        else{document.getElementById('orderNewPlate'+id).disabled=true}
    }
    function toggleDoWith(){
        if(document.getElementById('doStore').checked){
            document.getElementById('freezer').disabled=false;
            document.getElementById('shelf').disabled=false;
            document.getElementById('location').disabled=false;
        }else{
            document.getElementById('freezer').disabled=true;
            document.getElementById('shelf').disabled=true;
            document.getElementById('location').disabled=true;
        }
    }
    function disable(id){
        var button = document.getElementById(id);
        button.disabled=true;
    }

    function validate(){
        // check that every box is filled in
        //var deck = document.getElementsByName("deckLocation");
        var root = document.getElementById("plateNameRoot");
        var bar = document.getElementsByName("barcode");
        var init = document.getElementsByName("initials");

        if(root.value == null || root.value.valueOf() == "" ){
            alert("Error: Root plate name required."+
                    " See red bordered box.");
                root.style.border='2px solid red';
                return false;
        }else{
                root.style.border='';
            }

        for(var i = 0; i<bar.length;i++){
            if(bar[i].value == null || bar[i].value.valueOf() == "" ){
                alert("Error: All barcodes must be entered."+
                    " See red bordered box.");
                bar[i].style.border='2px solid red';
                return false;
            }
            else{
            // all barcodes must be unique
                for(var j = 0; j<bar.length;j++){
                    if(j!=i){
                        if(bar[i].value.valueOf() == bar[j].value.valueOf()){
                            alert("Error: All barcodes must be unique."+
                                " See red bordered boxes.");
                            bar[i].style.border='2px solid red';
                            bar[j].style.border='2px solid red';
                            return false;
                        }else{
                            bar[i].style.border='';
                            bar[j].style.border='';
                        }
                    }
                }
                bar[i].style.border='';
            }
        }
        for(var i = 0; i<init.length;i++){
            if(init[i].value == null || init[i].value.valueOf() == "" ){
                alert("Error: All new plates must have their plate maker's initials specified."+
                    " See red bordered box.");
                init[i].style.border='2px solid red';
                return false;
            } else{
                init[i].style.border='';
            }
        }

        return true;
    }

</script>


   <script type="text/javascript">
    var submitFlag = 0;
    function addLine(){

        // get deck location name dropdown
        var sel = document.getElementById("deckLocationsBase");
        var newSel = document.createElement("select");
        newSel.name="deckLocations";
        var option;
        //go through options of orig sel
        for(var i =1; i<=sel.length;i++){
            //clone option of orig sel
            var option = document.getElementById("P"+i);
            //add clone to new sel
            
            // IE7 doesn't support textContext
            var text = null;
            if(option.innerText){
                text= option.innerText.valueOf();
            }
            if(option.textContent){
                text = option.textContent.valueOf() 
            }
            newSel.options[newSel.length] = new Option(text,option.value.valueOf());
        }


        // get/make table info to make new row
        //var table = document.getElementById("inputTable");
        var table = document.getElementById("inputTable").getElementsByTagName("tbody")[0];
        var index = document.getElementById("rowIndex");
        var indexVal = (index.value.valueOf()*1)+1;
        index.value= indexVal;

        // make table info to make new row
        var newRow = document.createElement("tr");
        newRow.id="tableRow"+indexVal;
        var newCol1 = document.createElement("td");
        var newCol2 = document.createElement("td");
        var newCol3 = document.createElement("td");
        var newCol4 = document.createElement("td");

       // create barcode box
        var newBarcodeIn = document.createElement("input");
        newBarcodeIn.type ="text";
        newBarcodeIn.size ="10";
        newBarcodeIn.name ="barcode";
        newBarcodeIn.id ="barcode"+indexVal;

       // create initials box
        var newInitialsIn = document.createElement("input");
        newInitialsIn .type ="text";
        newInitialsIn .size ="10";
        newInitialsIn .name ="initials";
        newInitialsIn .id ="initials"+indexVal;

        // create remove button
        var remove = document.createElement("input");
        remove.type ="button";
        remove.name ="remove";
        remove.id ="remove"+indexVal;
        remove.value ="remove";
        remove.className = "button";
        var onC='removeRow('+indexVal+')';
        remove.onclick = new Function(onC);

        newCol1.appendChild(newBarcodeIn);
        newCol2.appendChild(newSel);
        newCol3.appendChild(newInitialsIn );
        newCol4.appendChild(remove);
        newRow.appendChild(newCol1);
        newRow.appendChild(newCol2);
        newRow.appendChild(newCol3);
        newRow.appendChild(newCol4);
        table.appendChild(newRow);

    }

    function removeRow(rowNum){
        // get/make table info to make new row
        var table = document.getElementById("inputTable");
        var rowCount = table.rows.length;
        for(var i =0;i<rowCount;i++){
            if(table.rows[i].id.valueOf()=="tableRow"+rowNum){
                table.deleteRow(i);
            }
        }
    }

</script>

<!--elements kept for copying to make new rows-->

<select id="deckLocationsBase" style="visibility:hidden">
    <%for(int j =1; j<=19;j++){%>
        <option value="P<%=j%>" id="P<%=j%>" >P<%=j%></option>
<%}%></select>

<select id="rowBase" style="visibility:hidden">
<option id="rowOp1" value="A">A</option>
<option id="rowOp2" value="B">B</option>
<option id="rowOp3" value="C">C</option>
<option id="rowOp4" value="D">D</option>
<option id="rowOp5" value="E">E</option>
<option id="rowOp6" value="F">F</option>
<option id="rowOp7" value="G">G</option>
<option id="rowOp8" value="H">H</option>
</select>

<select id="colBase" style="visibility:hidden">
<option id="colOp1" value="1">1</option>
<option id="colOp2" value="2">2</option>
<option id="colOp3" value="3">3</option>
<option id="colOp4" value="4">4</option>
<option id="colOp5" value="5">5</option>
<option id="colOp6" value="6">6</option>
<option id="colOp7" value="7">7</option>
<option id="colOp8" value="8">8</option>
<option id="colOp9" value="9">9</option>
<option id="colOp10" value="10">10</option>
<option id="colOp11" value="11">11</option>
<option id="colOp12" value="12">12</option>
</select>
<input type="hidden" value="0" id="rowIndex"/>
<!--end of elements kept for copying to make new rows-->




<%
// make batch
String batch = Util.getCurrentBatchID();
%>
<br><a class="largest">Specifiy New Plate Name and Layout</a>
<br>
<a class="error">&nbsp;</a>
<fieldset>
    <table>
        <tr><td>
            <table>
                <tr><th class="larger" colspan="3">New 384-Well Plate Details:</th></tr>
                
                <tr><td  valign="middle">Plate name root: </td><td colspan="3" valign="middle">
                <input type="text" size="30" name="plateNameRoot" id="plateNameRoot"/>
                <br> (ex: SAGE_VANP9/10/11/12GEN)<br>
                </td></tr>
                <tr><td  valign="middle">Batch:</td><td valign="middle" colspan="2"><br>
                <input type="text" value="<%=batch%>" name="batchName" id="batchName" readonly><br><br></td></tr>
                
                <!--tr><td  valign="middle" colspan="3">How many plates in this batch?
                <select name="platesInBatch" id="platesInBatch">
                    <%for(int k =1; k<=15;k++){%>
                        <option value="<%=k%>"><%=k%></option>
                    <%}%>
                </select></td></tr-->
                <tr><th  valign="middle">Individual Plates: </th></tr>
                <tr><td colspan="5">
                    <fieldset>
                <table id="inputTable" cellpadding="4">
                <tr>
                    <td >Barcode</td>
                    <td >Deck Location</td>
                    <td >Plate Maker's Initials</td>
                </tr>
                <tr id="tableRow0">
                    <td ><input type="text" size="10" name="barcode" id="barcode0"/></td>
                    <td ><select name="deckLocations" id="deckLocations0">
                    <%for(int j =1; j<=19;j++){%>
                        <option value="P<%=j%>">P<%=j%></option>
                    <%}%></select></td>
                    <td ><input type="text" size="10" name="initials" id ="initials0"/></td>
                </tr>
                </table>
    <input type="button" class="button" onclick="addLine()" value="Add another" />
                </fieldset>
                <tr><td>&nbsp;</td></tr>
                </td></tr>
                <tr><td  valign="middle" align="left">
                    <input type="radio" name="doWith" id="doStore" value="doStore" onchange="toggleDoWith()" checked>
                To be stored</td>
                <td valign="middle" > Freezer: </td><td>
                        <select name="freezerID" id="freezer">
                            <%=tmpHttpSessObj.getObjectPrompterUniqueField(Freezer.class, null, "description", false)%>
                </select></td><td>&nbsp;</td></tr>
                <tr><td>&nbsp;</td><td valign="middle" >Shelf: </td><td>
                <input type="text" size="5" name="shelf" id="shelf"/></td></tr>
                <tr><td>&nbsp;</td><td valign="middle" >Location: </td><td>
                <input type="text" size="5" name="location" id="location"/></td></tr>
                <tr><td  valign="middle" align="left">
                    <input type="radio" name="doWith" id="doDisard" value="doDisard" onchange="toggleDoWith()">
                    To be discarded </td>
                        </tr>
            </table>
            <br><br>
            <table>
            <tr><th class="larger" colspan="2">New 384-Well Plate Layout:</th></tr>
            <tr><td>
                    <table>
                        <tr><td class="instructions" colspan="5">Select at most 4 96-well plates to create a new 384-well plate
                        and set their order/layout on the new plate using the diagram on the right.<br></td></tr>

                        <tr>
                            <td align="center" width="20">Use<br>Plate?</td>
                            <td align="center" width="40">Order<br>(Q1,Q2,Q3,Q4)</td>
                            <td align="left" >Source Plate</td>
                        </tr><tr>
                            <%
                            for (i = 0; i < numSrcPlates; i++) {
            Container container = sourceContainers.get(i);
            System.out.println("tmpHttpSessObj.getMinContentVolume(container.getId().toString())" + tmpHttpSessObj.getMinContentVolume(container.getId().toString()));
                            %>
                            <td align="center"><input type="checkbox" name="doPlate" value="<%=container.getId()%>" id="doPlate<%=container.getId()%>" onchange="selectContainer(<%=container.getId()%>,<%=tmpHttpSessObj.getMinContentVolume(container.getId().toString())%>)"/></td>
                            <!--td><input type="text" size="1" name="whichNewPlate" id="whichNewPlate<%=container.getId()%>"></td-->
                            <td align="center"><input type="text" size="1" name="orderNewPlate" id="orderNewPlate<%=container.getId()%>" disabled/></td>
                        <td align="left"><%=container.getVisibleName()%></td></tr>
                        <%}%>
                    </table>
                </td>
        </tr>
        <tr><td>
                <table cellpadding="4" >
                    <tr><td colspan="1">For each 384 well plate to be made, 5ul of solution will be withdrawn from every well of the source plates.</td></tr>
        <!--tr><td colspan="2"><input type="checkbox" name="createSDS" checked value="true"> Download SDS import file?</td></tr-->
        <tr><td align="center" colspan="1"><input type="submit" name="finish" value="Create Plate" id="createPlate" onclick="return validate();">
        <a style="font-size:8pt;text-align:left"> &nbsp;&nbsp;
            (This may take some time)
        </a></td>
    </table>
        </td></tr>
    </table>
    </td>
    <td align="center" valign="middle">
    <table>
        <tr><th colspan="4">384 Well Plate Quadrant (Q) Layout:</th></tr>
            
        <tr><td></td><td>
                <table class="clearTop">
                    <tr>
                        <%for (int j = 0; j < 24; j++) {%>
                        <td><%=(j + 1)%></td>
                        <%}%>
                    </tr>
                </table>
        </td></tr>
        <tr><td>
                <!--table border="box" style="text-align:center;background-color:white;
                border-width:medium;border-color:gray;height:200px;table-layout:fixed;width:200px;"-->
                <table class="clearSide" style="width:14px">
                    <tr><td>A</td></tr>
                    <tr><td>B</td></tr>
                    <tr><td>C</td></tr>
                    <tr><td>D</td></tr>
                    <tr><td>E</td></tr>
                    <tr><td>F</td></tr>
                    <tr><td>G</td></tr>
                    <tr><td>H</td></tr>
                    <tr><td>I</td></tr>
                    <tr><td>J</td></tr>
                    <tr><td>K</td></tr>
                    <tr><td>L</td></tr>
                    <tr><td>M</td></tr>
                    <tr><td>N</td></tr>
                    <tr><td>O</td></tr>
                    <tr><td>P</td></tr>
            </table></td><td>
                <table class="plate" style="width:480px;height:320px;">
                    <%for (int k = 0; k < 16; k=k+2) {%>
                    <tr><%for (int j = 0; j < 12; j++) {%><td style="background-color:#D6E8F5">1</td><td style="background-color:#B6D3E7">2</td><%}%></tr>
                    <tr><%for (int j = 0; j < 12; j++) {%><td style="background-color:#99C5E4">3</td><td style="background-color:#B5DAF5">4</td><%}%></tr>
                    <%}%>
              </table>
            </td>
    </tr></table>
    </td></tr></table>

</fieldset>
<fieldset>
    <table cellpadding="4">
        <tr><td><input type="submit" name="cancel" value="Cancel"></td></tr>
    </table>
</fieldset>

