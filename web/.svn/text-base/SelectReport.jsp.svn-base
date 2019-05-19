<%-- 
    Document   : selectReport
    Created on : May 28, 2009, 10:30:23 AM
    Author     : tvanrossum
--%>
<%String lapasId = "SelectReport";%>
<%@include file="Header.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%
            if (tmpHttpSessObj.getCurrentUser() == null) {//test for new session
                System.out.println("current user is null");
                pageContext.forward(response.encodeURL("./LogIn.jsp"));
                return;
            }

%>

<script  type="text/javascript" language="javascript">
    function setFocus(){
        field = document.getElementById("kluda");
        if (field) field.focus();
        else
            document.fForm.genId.focus();
    }
    function displayOptions(sel){
        var f = sel.form;
        var opt = sel.options[sel.selectedIndex].value;
        if(opt=="ShippingRecord"){
            document.getElementById('ShippingRecordOptions').style.visibility='visible';
            f.elements["startYear"].style.display = "inline";
            f.elements["startMonth"].style.display = "inline";
            f.elements["startDay"].style.display = "inline";
            f.elements["endYear"].style.display = "inline";
            f.elements["endMonth"].style.display = "inline";
            f.elements["endDay"].style.display = "inline";
            
        }
        else
        {
            document.getElementById('ShippingRecordOptions').style.visibility='hidden';
            f.elements["startYear"].style.display = "none";
            f.elements["startMonth"].style.display = "none";
            f.elements["startDay"].style.display = "none";
            f.elements["endYear"].style.display = "none";
            f.elements["endMonth"].style.display = "none";
            f.elements["endDay"].style.display = "none";
        }
        if(opt=="recipeName" || opt=="sampleLocationSheetName"){
            document.getElementById('sheetOptions').style.visibility='visible';
            f.elements["containerName"].style.display = "inline";
        }
        else{
            document.getElementById('sheetOptions').style.visibility='hidden';
            f.elements["containerName"].style.display = "none";
        }
        
        return true;

    }

    function validate(){
        // if container sheet, need container name
        var sel = document.getElementById('reportName');
        var opt = sel.options[sel.selectedIndex].value;

        if(opt=="makesheet" || opt=="sampleLocation"){
            var name = document.getElementById('containerName').value;
            if(name == null){
                alert("Error: Container name required");
                return false;
            }
            if(name.length<=0 || name == ""){
                alert("Error: Container name required");
                return false;
            }
        }
        return true;
    }

    function toggleShippingRecordDates(elem, bool){
        var f = elem.form;
        f.elements["startYear"].disabled = bool;
        f.elements["startMonth"].disabled = bool;
        f.elements["startDay"].disabled = bool;
        f.elements["endYear"].disabled = bool;
        f.elements["endMonth"].disabled = bool;
        f.elements["endDay"].disabled = bool;
        return true;
    }
</script>

<html>
    <form method="POST" name="reportForm" target="_blank"
          action="<%=response.encodeURL("./ViewReport.jsp")%>">
        <table class="navigate">

            <tr>
                <td class="left" align="left" colspan="2"><a class="largest">View Pre-Designed Reports</a></td>
            </tr>
            <tr>
                <td valign="middle">

                    Select a Report to View:</td><td valign="middle">
                    <select name="reportName" id="reportName" onchange="displayOptions(this);">
                        <option value="recipeName" selected>Container: Custom plating Sheet</option>
                        <option value="sampleLocationSheetName">Container: Sample Location Sheet</option>
                        <!--option value="ContainerInventory" >Container Inventory</option>
                        <option value="FreezerInventory" >Freezer Inventory</option-->
                        <option value="freezers">Freezers</option>
                        <!--option value="SamplesBySubject">Sample Inventory</option>
                        <option value="ShippingRecord">Shipping Record</option-->
                    </select>
                </td>
                <td valign="middle">
                    <INPUT type="submit" id="getReport" value="Get Report" onclick="return validate();">
                </td>
            </tr>
            <tr>
                <td>Select Report Format:</td>
                <td>
                    <INPUT type="radio" name="reportFormat" value="pdf" CHECKED> PDF (recommended)<BR>
                    <INPUT type="radio" name="reportFormat" value="csv"> CSV<BR>
                    <INPUT type="radio" name="reportFormat" value="html"> HTML<BR>
                    <INPUT type="radio" name="reportFormat" value="xls"> Excel<BR>
                </td></tr>
        </table>

        <div id="sheetOptions" style="visibility:visible;">
            <table><tr><td>
                        Enter the container name <em>exactly</em>:
                        <input type="text" size="40" name="containerName" id ="containerName" onchange="activateGetReport(this)">
                    </td></tr></table>
        </div>

        <div id="ShippingRecordOptions" style="visibility:hidden;">
            <table>
                <tr><td><input type="radio" name="ShippingRecordLimit"
                               value="all" checked onclick="toggleShippingRecordDates(this, true)"> All Records</td></tr>
                <tr><td><input type="radio" name="ShippingRecordLimit"
                               value="date" onclick="toggleShippingRecordDates(this, false)"> Limit Records by Date: </td>
                    <td><%--shipped date(yyyy/mm/dd):--%>
                        From
                        <input type="text" name="startYear" maxlength="4" size="4"
                               value="" disabled/><b>-</b>
                        <input type="text" name="startMonth" maxlength="2" size="2"
                               value="" disabled/><b>-</b>
                        <input type="text" name="startDay" maxlength="2" size="2"
                               value="" disabled/>
                        (yyyy-mm-dd)
                    </td>
                </tr>
                <tr> <td colspan="1">&nbsp;</td>
                    <td>
                        To &nbsp;&nbsp;&nbsp;
                        <input type="text" name="endYear" maxlength="4" size="4"
                               value="" disabled/><b>-</b>
                        <input type="text" name="endMonth" maxlength="2" size="2"
                               value="" disabled/><b>-</b>
                        <input type="text" name="endDay" maxlength="2" size="2"
                               value="" disabled/>
                        (inclusive)
                    </td>
                </tr>
            </table>
        </div>


    </form>

    <%@include file="Footer.jsp"%>
