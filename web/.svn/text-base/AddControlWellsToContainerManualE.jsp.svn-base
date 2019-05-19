<%-- 
    Document   : AddControlWellsToContainerManualE
    Created on : Sep 22, 2009, 3:20:34 PM
    Author     : tvanrossum
--%>


<script type="text/javascript">
    var submitFlag = 0;
    function addLine(){
        // get control name dropdown
        var sel = document.getElementById("controlID");
        var newSel = document.createElement("select");
        newSel.name="controlID";
        var option;
        //go through options of orig sel
        for(var i =0; i<sel.length;i++){
            //clone option of orig sel
            var option = document.getElementById("option"+i);
            //add clone to new sel
            // IE7 doesn't support textContext, uses innerText instead
            var text = null;
            if(option.innerText){ //is IE
                text= option.innerText.valueOf();
            }
            if(option.textContent){
                text = option.textContent.valueOf()
            }
            newSel.options[newSel.length] = new Option(text,option.value.valueOf());
        }
        // get/make table info to make new row
        // IE7 needs to get tbody
            var table = null;
            if(option.innerText){
                 table = document.getElementById("inputTable").getElementsByTagName("tbody")[0];
            }
            if(option.textContent){
                 table = document.getElementById("inputTable");
            }
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
        var newCol5 = document.createElement("td");

        // get row dropdown
        var selRow = document.getElementById("rowBase");
        var newSelRow = document.createElement("select");
        newSelRow.name="row";
        newSelRow.id="row"+indexVal;
        //var rOnCh='checkDups(\''+indexVal+'\')';
        //newSelRow.onchange = new Function(rOnCh);
        //go through options of orig sel
        for(var i =1; i<=selRow.length;i++){
            //clone option of orig sel
            var option = document.getElementById("rowOp"+i);
            //add clone to new sel

            // IE7 doesn't support textContext
            var text = null;
            if(option.innerText){
                text= option.innerText.valueOf();
            }
            if(option.textContent){
                text = option.textContent.valueOf()
            }
            newSelRow.options[newSelRow.length] = new Option(text,option.value.valueOf());
        }


        // get col dropdown
        var selCol = document.getElementById("colBase");
        var newSelCol = document.createElement("select");
        newSelCol.name="column";
        newSelCol.id="col"+indexVal;
        //newSelCol.onchange = new Function(rOnCh);
        //go through options of orig sel
        for(var i =1; i<=selCol.length;i++){
            //clone option of orig sel
            var option = document.getElementById("colOp"+i);
            //add clone to new sel
                        
            // IE7 doesn't support textContext
            var text = null;
            if(option.innerText){
                text= option.innerText.valueOf();
            }
            if(option.textContent){
                text = option.textContent.valueOf() 
            }
            newSelCol.options[newSelCol.length] = new Option(text,option.value.valueOf());
        }

       // create volume box
        var newVolIn = document.createElement("input");
        newVolIn.type ="text";
        newVolIn.size ="2";
        newVolIn.name ="volume";
        newVolIn.id ="volume"+indexVal;
        var vOnCh='checkNum(this,\'vol'+indexVal+'\',\'.\')';
        newVolIn.onchange = new Function(vOnCh);

        // create remove button
        var remove = document.createElement("input");
        remove.type ="button";
        remove.name ="remove";
        remove.id ="remove"+indexVal;
        remove.value ="remove";
        remove.className = "button";
        var onC='removeRow('+indexVal+')';
        remove.onclick = new Function(onC);

            
            // IE7 doesn't support textContext
            var text = null;
            if(option.innerText){
                newCol1.innerText = "Control: ";
                newCol2.innerText = "Row: ";
                newCol3.innerText = "Column: ";
                newCol4.innerText = "Volume (ul): ";
            }
            if(option.textContent){
                newCol1.textContent = "Control: ";
                newCol2.textContent = "Row: ";
                newCol3.textContent = "Column: ";
                newCol4.textContent = "Volume (ul): ";
            }

        newCol1.appendChild(newSel);
        newCol2.appendChild(newSelRow);
        newCol3.appendChild(newSelCol);
        newCol4.appendChild(newVolIn);
        newCol5.appendChild(remove);
        newRow.appendChild(newCol1);
        newRow.appendChild(newCol2);
        newRow.appendChild(newCol3);
        newRow.appendChild(newCol4);
        newRow.appendChild(newCol5);
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
    function checkUniquenessLayoutName(elem){
        if(elem.value != null && elem.value.valueOf() != null){
            var nameVal = elem.value.valueOf();
            var layoutNames = document.getElementsByName('existingLayoutNames');
            for(var i = 0; layoutNames.length;i++){
                if(layoutNames[i].value!= null &&
                        layoutNames[i].value.valueOf()==nameVal){
                      return false;
                }
            }
            return true;
        }
        return false;
    }


</script>

<!--elements kept for copying to make new rows-->
<select id="controlID" style="visibility:hidden">
    <%=tmpHttpSessObj.getObjectPrompterUniqueField(Control.class, null, "description", false)%>
</select>
<select id="rowBase" style="visibility:hidden">
<option id="rowOp1" value="A">A</option>
<option id="rowOp2" value="B">B</option>
<option id="rowOp3" value="C">C</option>
<option id="rowOp4" value="D">D</option>
<option id="rowOp5" value="E">E</option>
<option id="rowOp6" value="F">F</option>
<option id="rowOp7" value="G">G</option>
<option id="rowOp8" value="H">H</option>
<!--option id="rowOp9" value="I">I</option>
<option id="rowOp10" value="J">J</option>
<option id="rowOp11" value="K">K</option>
<option id="rowOp12" value="L">L</option>
<option id="rowOp13" value="M">M</option>
<option id="rowOp14" value="N">N</option>
<option id="rowOp15" value="O">O</option>
<option id="rowOp16" value="P">P</option-->
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
<!--option id="colOp13" value="13">13</option>
<option id="colOp14" value="14">14</option>
<option id="colOp15" value="15">15</option>
<option id="colOp16" value="16">16</option>
<option id="colOp17" value="17">17</option>
<option id="colOp18" value="18">18</option>
<option id="colOp19" value="19">19</option>
<option id="colOp20" value="20">20</option>
<option id="colOp21" value="21">21</option>
<option id="colOp22" value="22">22</option>
<option id="colOp23" value="23">23</option>
<option id="colOp24" value="24">24</option-->
</select>
<input type="hidden" value="0" id="rowIndex"/>
<!--end of elements kept for copying to make new rows-->
<table><tr><td>New Layout Name:
<input type="text" name="layoutName" id="layoutName" size="30"></td></tr></table>
<fieldset>
    <TABLE><TR><TD>
    <table id="inputTable" cellpadding="4">
        <thead>

            <tr id="tableRow0">
                <td class="dialh">
                    Control:
                    <select name="controlID">
                        <%=tmpHttpSessObj.getObjectPrompterUniqueField(Control.class, null, "description", false)%>
                    </select>
                </td>
                <td class="dialh">
                    Row:
                    <!--input type="text" name="row" size="1" id="row0" onchange="checkAlpha(this,'row0')"/-->
                <select name="row"  id="row0" >
<option id="rowOp1" value="A">A</option>
<option id="rowOp2" value="B">B</option>
<option id="rowOp3" value="C">C</option>
<option id="rowOp4" value="D">D</option>
<option id="rowOp5" value="E">E</option>
<option id="rowOp6" value="F">F</option>
<option id="rowOp7" value="G">G</option>
<option id="rowOp8" value="H">H</option>
<!--option id="rowOp9" value="I">I</option>
<option id="rowOp10" value="J">J</option>
<option id="rowOp11" value="K">K</option>
<option id="rowOp12" value="L">L</option>
<option id="rowOp13" value="M">M</option>
<option id="rowOp14" value="N">N</option>
<option id="rowOp15" value="O">O</option>
<option id="rowOp16" value="P">P</option-->
</select>

                </td>
                <td class="dialh">
                    Column: <!--input type="text" name="column" size="2" id="col0" onchange="checkNum(this,'col0','')"/-->
                <select name="column" id="col0">
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
<!--option id="colOp13" value="13">13</option>
<option id="colOp14" value="14">14</option>
<option id="colOp15" value="15">15</option>
<option id="colOp16" value="16">16</option>
<option id="colOp17" value="17">17</option>
<option id="colOp18" value="18">18</option>
<option id="colOp19" value="19">19</option>
<option id="colOp20" value="20">20</option>
<option id="colOp21" value="21">21</option>
<option id="colOp22" value="22">22</option>
<option id="colOp23" value="23">23</option>
<option id="colOp24" value="24">24</option-->
</select>

                </td>
                <td class="dialh">
                    Volume (ul): <input type="text" name="volume" size="2" id="vol0" onchange="checkNum(this,'vol0','.')"/>
                </td>
                <td><input type="button" class="button"
                           value="remove" id="remove0" onclick="removeRow('0');"></td>
            </tr>

        </thead>
    </table>
    <input type="button" class="button" onclick="addLine()" value="Add another" />
    <div id="inputs"></div>
    </TD><TD>

    <table class="plate"  style="width:450px">
                    <%for (int j = 1; j <= 8; j++) {%>
                    <tr style="height:10px;">
                    <%for (int k = 1; k <= 12; k++) {%>
                        <td><a class="smaller"><%=Util.numToLetter(String.valueOf(j))+(k)%></a></td>
                   <%}%>
                    </tr>
                    <%}%>
                </table>


    </TD></TR>
    </TABLE>
</fieldset>


