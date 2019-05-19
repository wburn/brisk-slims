<%-- 
    Document   : Add96WellPlatesE
    Created on : Oct 7, 2009, 2:40:20 PM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
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
    function checknum(){
        var ctrl = document.getElementById("numCtrls").value.getValue();
        var samp = document.getElementById("numSamps").value.getValue();
        if((ctrl*1+samp*1)>96){
            alert("Error: number of controls plus number of samples must sum to 96 or less.");
            document.getElementById("numSamps").value = "0";
        }
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
    <%=mdm.getContainerLongName(Fieldname.CONTAINERNAME)%></td>
    <td>
        <input type="text" size="40" name="containerName"
                  value="<%=(containerName != null) ? containerName : ""%>"/>
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
        <%=mdm.getContainerLongName(Fieldname.DATEONCONTAINER)%></td>
        <td>
          <input type="text" name="cyear" id="cyear" maxlength="4" size="4"
                 <%=tmpHttpSessObj.getFieldId("cyear")%>
                 value="<%=(cyear!=null)?cyear:""%>"
                 /><b>-</b>
          <input type="text" name="cmonth" id="cmonth" maxlength="2" size="2"
                 <%=tmpHttpSessObj.getFieldId("cmonth")%>
                 value="<%=(cmonth!=null)?cmonth:""%>"
                 /><b>-</b>
          <input type="text" name="cday" id="cday" maxlength="2" size="2"
                 <%=tmpHttpSessObj.getFieldId("cday")%>
                 value="<%=(cday!=null)?cday:""%>"
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
<input type="hidden" name="containerTypeID" value="0">
    
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
    <%=mdm.getContainerLongName(Fieldname.SHELF)%></td>
    <td>
        <input type="text" size="5"  name="shelf"
             value="<%=(shelf != null) ? shelf : ""%>"/>
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
<input type="hidden" name="discarded" value="0">

<tr>
    <td class="dialh"><%--shippedOut:--%>
    To be shipped out?</td>
    <td>
      <select name="shippedOut" id="shippedOut" onchange="shipFields()">
        <option value="0" <%=(shippedOut == null || shippedOut.equals("0")) ? "selected" : ""%> >No</option>
        <option value="1" <%=(shippedOut!= null && shippedOut.equals("1")) ? "selected" : ""%> >Yes</option>
      </select>
      </td>
</tr>

      <tr>
        <td class="dialh"><%--shipped date(yyyy/mm/dd):--%>
        <%=mdm.getContainerLongName(Fieldname.SHIPPEDDATE)%></td>
        <td>
          <input type="text" name="ayear" id="ayear" maxlength="4" size="4"
                 <%=tmpHttpSessObj.getFieldId("ayear")%>
                 value="<%=(ayear!=null)?ayear:""%>"
                 <%=(shippedOut == null || shippedOut.equals("0")) ? "disabled" : ""%>/><b>-</b>
          <input type="text" name="amonth" id="amonth" maxlength="2" size="2"
                 <%=tmpHttpSessObj.getFieldId("amonth")%>
                 value="<%=(amonth!=null)?amonth:""%>"
                 <%=(shippedOut == null || shippedOut.equals("0")) ? "disabled" : ""%>/><b>-</b>
          <input type="text" name="aday" id="aday" maxlength="2" size="2"
                 <%=tmpHttpSessObj.getFieldId("aday")%>
                 value="<%=(aday!=null)?aday:""%>"
                 <%=(shippedOut == null || shippedOut.equals("0")) ? "disabled" : ""%>/> (YYYY MM DD)
        </td>
      </tr>
    <tr>
        <td class="dialh"><%--shippedToID:--%>
        <%=mdm.getContainerLongName(Fieldname.SHIPPEDTO)%></td>
        <td>
          <select name="shippedToID" id="shippedToID"
          <%=(shippedOut == null || shippedOut.equals("0")) ? "disabled" : ""%>>
              <%=tmpHttpSessObj.getObjectPrompterUniqueField(ShippedTo.class, shippedToID, "description", false)%>
          </select>
        </td>
      </tr>

                             <tr>
                <td class="dialh"><%--comments:--%>
                <%=mdm.getContainerContentLongName(Fieldname.COMMENTS)%></td>
                <td>
                    <textarea cols="40" rows="1" name="comments"><%=(comments != null) ? comments : ""%></textarea>
                </td>
            </tr>

<tr><td colspan="2"><table><tr>
        <td class="dialh">Material types for this plate's samples:</td>
        <td>
            <input type="radio" name="materialTypeChange" id="materialTypeChangeNo" value="no" onclick="activate('notMatSel')" checked>No change from source samples
</td></tr><tr><td>&nbsp;</td><td>
            <input type="radio" name="materialTypeChange" id="materialTypeChangeYes" value="yes" onclick="activate('matSel')"> New material type:
            <select name="materialTypeID" id="materialTypeID" disabled>
                <%=tmpHttpSessObj.getObjectPrompterUniqueField(MaterialType.class, null, "description", false)%>
            </select>
        </td>
    </tr>
</table></td></tr>
    <tr>
    <td class="dialh"><%--barcode--%>
    <%=mdm.getContainerLongName(Fieldname.BARCODE)%></td>
    <td>
        <input type="text" size="15"  name="barcode"
             value="<%=(barcode != null) ? barcode: ""%>"/>
    </td>
    </tr>
</thead>
</table>
</fieldset>


