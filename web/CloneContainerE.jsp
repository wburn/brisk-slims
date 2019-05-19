<%-- 
    Document   : AddContainerCloneE
    Created on : Aug 14, 2009, 2:34:54 PM
    Author     : tvanrossum
--%>

<%
        MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
%>

<fieldset>
<table cellspacing="10">

    <thead>
        <tr><td style="font-weight:bold" colspan="2">
                New Plate's Properties
        </td></tr>
    </thead>
    <%contId = request.getParameter("contId");%>
    <input type="hidden" name="contId"
           value="<%=(contId != null) ? contId : "-1"%>"/>

    <tr>
        <td class="dialh"><%--containerName:--%>
        Plate Name</td>
        <td>
            <input type="text" size="50" name="containerName"
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
        <option value="1" <%=(valid != null && valid.equals("1")) ? "selected" : ""%>>Yes</option>
        <option value="0" <%=(valid == null || valid.equals("0")) ? "selected" : ""%>>No</option>
      </select>
    </td>
</tr>
        <!-- shouldn't be able to change the type of container when cloning
only clonable containers are 96 and 384 well plates
    <tr>
        <td class="dialh">
        <%=mdm.getContainerLongName(Fieldname.CONTAINERTYPE)%></td>
        <td>
            <select name="containerTypeID">
                <option value="0" <%if (containerTypeID.equals("0")) {%>selected<%}%>> 96 Well Plate</option>
                <option value="1" <%if (containerTypeID.equals("1")) {%>selected<%}%>>384 Well Plate</option>
            </select>
        </td>
      </tr>
    <tr>
        -->

        <INPUT TYPE="HIDDEN" NAME="containerTypeID" value="<%=containerTypeID%>" >

        <td class="dialh"><%--FreezerID:--%>
        <%=mdm.getContainerLongName(Fieldname.FREEZER)%></td>
        <td>
            <select name="freezerID">
                <%=tmpHttpSessObj.getObjectPrompterUniqueField(Freezer.class, freezerID, "description", false)%>
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
    <tr>
        <td class="dialh"><%--comments:--%>
        <%=mdm.getContainerContentLongName(Fieldname.COMMENTS)%></td>
        <td>
            <textarea cols="40" rows="3" name="comments"><%=(comments != null) ? comments : ""%></textarea>
        </td>
    </tr>
    <tr>
    <td class="dialh"><%--barcode--%>
    <%=mdm.getContainerLongName(Fieldname.BARCODE)%></td>
    <td>
        <input type="text" size="15"  name="barcode"
             value="<%=(barcode != null) ? barcode: ""%>"/>
    </td>
    </tr>
    <tr><td><br><td><tr>
</table>

<table cellspacing="10">

    <tr>
        <thead><td style="font-weight:bold" colspan="2">
    Sample Properties</td> </thead></tr>

    <tr>

    <tr>
        <td>
            <%--volume:--%>
            Update volumes of original (source) plate's samples:
        </td><td>
            <select name="volumeActionOrig" id="volumeActionOrig">
                <option value="subtract" selected>Decrease by</option>
                <option value="set" >Set to</option>
            </select>
            <input type="text" size="6" name="volumeOrig" id="volumeOrig"
                   value="<%=(volumeOrig != null) ? volumeOrig : ""%>"/>
            uL
            <br><br>
        </td>
    </tr>

    <tr>
        <td>Set volumes of new cloned plate's samples:</td>
        <td>
            <!--select name="volumeActionNew" >
            <option value="set" selected>Set to</option>
        </select-->
            <input type="hidden" value="set" name="volumeActionNew">
            <input type="text" size="6" name="volumeNew"
                   value="<%=(volumeNew != null) ? volumeNew : ""%>"/>
            uL
        </td>
    </tr>

    <tr><td>
        <input type="radio" name="whichVal" value="valConcen" onclick="activate('concen')"
            <%=(whichVal==null||whichVal.equals("valConcen")?"checked":"")%>>
        Set concentrations of new cloned plate's samples:</td>
        <td><input type="text" size="6" name="concentration" id="concen"
                       <%=(whichVal==null||whichVal.equals("valConcen")?"":"disabled")%>
                       value="<%=(concentration != null) ? concentration : ""%>"/>
            ng/ul
        </td>
    </tr>

    <tr><td><input type="radio" name="whichVal" value="valDilu" onclick="activate('dilu')"
                <%=(whichVal!=null&&whichVal.equals("valDilu")?"checked":"")%>>
        Set dilutions of new cloned plate's samples:</td>
        <td><input type="text" size="6" name="dilution"  id="dilu" 
        <%=(whichVal!=null&&whichVal.equals("valDilu")?"":"disabled")%>
        value="<%=(dilution != null) ? dilution : ""%>"/>
        (ex: 1:100)
        </td>
    </tr>
<tr><td>&nbsp;</td></tr>
<tr><td colspan="2"><table><tr>
        <td class="dialh">Update material types?</td>
        <td>
            <input type="radio" name="materialTypeChange" id="materialTypeChangeNo" value="no" onclick="activate('notMatSel')" checked>No change from source samples
</td></tr><tr><td>&nbsp;</td><td>
            <input type="radio" name="materialTypeChange" id="materialTypeChangeYes" value="yes" onclick="activate('matSel')"> New material type:
            <select name="materialTypeID" id="materialTypeID" disabled>
                <%=tmpHttpSessObj.getObjectPrompterUniqueField(MaterialType.class, materialTypeID, "description", false)%>
            </select>
        </td>
    </tr>
</table></td></tr>
    <tr><td colspan="2"><br><td><tr>

</table>
</fieldset>



