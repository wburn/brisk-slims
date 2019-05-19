<!--Start of common part of update ContainerContent-->
<%
        MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
%>

<fieldset>
<table>
<thead>

<tr>
    <td><input type="checkbox" value="true" id="id1" name="doVolume"
    onclick='changeCheck("volumeAction","volume","id1");'>
        </td>
    <td ><%--volume:--%>
    Adjust volumes of samples: </td>
    <td>
        <select name="volumeAction" disabled>
            <option value="set" selected>Set to</option>
            <option value="add">Increase by</option>
            <option value="subtract">Decrease by</option>
        </select>
        </td>
    <td>
        <input type="text" size="5" name="volume"  value="" disabled/> uL
    </td>
</tr>
<tr>
    <td><input type="checkbox" value="true" id="id3" name="doConcen"
    onclick='changeCheck("concentrationAction","concentration","id3");'>
        </td>
    <td class="dialh"><%--concentration:--%>
    Adjust concentrations of samples: </td>
    <td>
        <select name="concentrationAction" disabled>
            <option value="set" selected>Set to</option>
            <option value="add">Increase by</option>
            <option value="subtract">Decrease by</option>
        </select>
        </td>
    <td>
        <input type="text" size="5" name="concentration" disabled
                  value="<%=(concentration != null) ? concentration : ""%>"/>
        ng/uL
    </td>
</tr>
<tr>
    <td><input type="checkbox" value="true" id="id2" name="doComments"
    onclick='changeCheck("comments","comments","id2");'>
        </td>
        <td class="dialh" align="left" colspan="5">
    Update comments of samples:
     (text will be <em>added</em> to the current comments)
    </td>
    <td align="left">
        <input type="text" size="20" name="comments" id="comments" disabled/>
        <br><br>
    </td>
</tr>



</thead>
</table>
</fieldset>


