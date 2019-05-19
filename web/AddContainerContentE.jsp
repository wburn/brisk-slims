<!--Start of common part of update ContainerContent-->
<%
        MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
%>

<script  type="text/javascript" language="javascript">
function toggleAmpDate(){
        // if WGA, enable amp date
        if(document.getElementById('materialTypeID').value == "2"){
            document.getElementById('ayear').disabled = false;
            document.getElementById('amonth').disabled = false;
            document.getElementById('aday').disabled = false;
        }// else disable it
        else{
            document.getElementById("ayear").disabled = true;
            document.getElementById("ayear").className = null;
            document.getElementById("amonth").disabled = true;
            document.getElementById("amonth").className = null;
            document.getElementById("aday").disabled = true;
            document.getElementById("aday").className = null;
        }
    }

</script>

<fieldset>
    <table>
        <thead>

            <%contContId = request.getParameter("contContId");%>
            <input type="hidden" name="contContId"
                   value="<%=(contContId != null) ? contContId : "-1"%>"/>
<tr>
                <td class="dialh"><%--parentID:--%>
                Parent Well </td>

                <td><table  frame="lhs" style="border-left: thin solid grey">
                        <tr>
                            <td>Container Name </td>
                            <td><select name="parentName" id="parentName">
                                    <%=tmpHttpSessObj.getObjectPrompterField(ContainerContent.class, parentName, "container.containerName", true)%>
                                </select>
                                
                        </td></tr>
                        <tr><td>Row </td>
                        <td>
                                                <select name="parentRow" id="parentRow">
                        <option value="" <%=(parentRow != null && parentRow.equals("0")) ? "selected": ""%>>Please choose a row</option>
                        <option value="1" <%=(parentRow != null && parentRow.equals("1")) ? "selected": ""%>>A</option>
                        <option value="2" <%=(parentRow != null && parentRow.equals("2")) ? "selected": ""%>>B</option>
                        <option value="3" <%=(parentRow != null && parentRow.equals("3")) ? "selected": ""%>>C</option>
                        <option value="4" <%=(parentRow != null && parentRow.equals("4")) ? "selected": ""%>>D</option>
                        <option value="5" <%=(parentRow != null && parentRow.equals("5")) ? "selected": ""%>>E</option>
                        <option value="6" <%=(parentRow != null && parentRow.equals("6")) ? "selected": ""%>>F</option>
                        <option value="7" <%=(parentRow != null && parentRow.equals("7")) ? "selected": ""%>>G</option>
                        <option value="8" <%=(parentRow != null && parentRow.equals("8")) ? "selected": ""%>>H</option>
                        <option value="9" <%=(parentRow != null && parentRow.equals("9")) ? "selected": ""%>>I</option>
                        <option value="10" <%=(parentRow != null && parentRow.equals("10")) ? "selected": ""%>>J</option>
                        <option value="11" <%=(parentRow != null && parentRow.equals("11")) ? "selected": ""%>>K</option>
                        <option value="12" <%=(parentRow != null && parentRow.equals("12")) ? "selected": ""%>>L</option>
                        <option value="13" <%=(parentRow != null && parentRow.equals("13")) ? "selected": ""%>>M</option>
                        <option value="14" <%=(parentRow != null && parentRow.equals("14")) ? "selected": ""%>>N</option>
                        <option value="15" <%=(parentRow != null && parentRow.equals("15")) ? "selected": ""%>>O</option>
                        <option value="16" <%=(parentRow != null && parentRow.equals("16")) ? "selected": ""%>>P</option>
                        <option value="17" <%=(parentRow != null && parentRow.equals("17")) ? "selected": ""%>>Q</option>
                        <option value="18" <%=(parentRow != null && parentRow.equals("18")) ? "selected": ""%>>R</option>
                        <option value="19" <%=(parentRow != null && parentRow.equals("19")) ? "selected": ""%>>S</option>
                        <option value="20" <%=(parentRow != null && parentRow.equals("20")) ? "selected": ""%>>T</option>
                        <option value="21" <%=(parentRow != null && parentRow.equals("21")) ? "selected": ""%>>U</option>
                        <option value="22" <%=(parentRow != null && parentRow.equals("22")) ? "selected": ""%>>V</option>
                        <option value="23" <%=(parentRow != null && parentRow.equals("23")) ? "selected": ""%>>W</option>
                        <option value="24" <%=(parentRow != null && parentRow.equals("24")) ? "selected": ""%>>X</option>
                        <option value="25" <%=(parentRow != null && parentRow.equals("25")) ? "selected": ""%>>Y</option>
                        <option value="26" <%=(parentRow != null && parentRow.equals("26")) ? "selected": ""%>>Z</option>
                    </select>
                            </td></tr>
                        <tr><td>Column </td>
                        <td>
                        <input type="text" size="3" name="parentColumn" id="parentColumn" value="<%=(parentColumn != null) ? parentColumn : ""%>" class="digits">
                        </td></tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td class="dialh"><%--contaminated:--%>
                <%=mdm.getContainerContentLongName(Fieldname.CONTAMINATED)%></td>
                <td>
                    <select name="contaminated" id="contaminated">
                        <option value="1" <%=(contaminated == null || contaminated.equals("1")) ? "selected" : ""%>>Yes</option>
                        <option value="0" <%=(contaminated != null && contaminated.equals("0")) ? "selected" : ""%>>No</option>
                    </select>
                </td>
            </tr>

            <tr>
                <td class="dialh"><%--containerID:--%>
                <%=mdm.getContainerContentLongName(Fieldname.CONTAINER)%></td>
                <td>
                    <select name="containerID" id="containerID">
                        <%=tmpHttpSessObj.getObjectPrompter(Container.class, containerID)%>
                    </select>
                </td>
            </tr>
  
            <tr>
                <td class="dialh"><%--row:--%>
                <%=mdm.getContainerContentLongName(Fieldname.ROW)%>*</td>
                <td>
                    <select name="row" id="row" class="required">
                        <option value="" <%=(row != null && row.equals("")) ? "selected": ""%>>Please choose a row</option>
                        <option value="1" <%=(row != null && row.equals("1")) ? "selected": ""%>>A</option>
                        <option value="2" <%=(row != null && row.equals("2")) ? "selected": ""%>>B</option>
                        <option value="3" <%=(row != null && row.equals("3")) ? "selected": ""%>>C</option>
                        <option value="4" <%=(row != null && row.equals("4")) ? "selected": ""%>>D</option>
                        <option value="5" <%=(row != null && row.equals("5")) ? "selected": ""%>>E</option>
                        <option value="6" <%=(row != null && row.equals("6")) ? "selected": ""%>>F</option>
                        <option value="7" <%=(row != null && row.equals("7")) ? "selected": ""%>>G</option>
                        <option value="8" <%=(row != null && row.equals("8")) ? "selected": ""%>>H</option>
                        <option value="9" <%=(row != null && row.equals("9")) ? "selected": ""%>>I</option>
                        <option value="10" <%=(row != null && row.equals("10")) ? "selected": ""%>>J</option>
                        <option value="11" <%=(row != null && row.equals("11")) ? "selected": ""%>>K</option>
                        <option value="12" <%=(row != null && row.equals("12")) ? "selected": ""%>>L</option>
                        <option value="13" <%=(row != null && row.equals("13")) ? "selected": ""%>>M</option>
                        <option value="14" <%=(row != null && row.equals("14")) ? "selected": ""%>>N</option>
                        <option value="15" <%=(row != null && row.equals("15")) ? "selected": ""%>>O</option>
                        <option value="16" <%=(row != null && row.equals("16")) ? "selected": ""%>>P</option>
                        <option value="17" <%=(row != null && row.equals("17")) ? "selected": ""%>>Q</option>
                        <option value="18" <%=(row != null && row.equals("18")) ? "selected": ""%>>R</option>
                        <option value="19" <%=(row != null && row.equals("19")) ? "selected": ""%>>S</option>
                        <option value="20" <%=(row != null && row.equals("20")) ? "selected": ""%>>T</option>
                        <option value="21" <%=(row != null && row.equals("21")) ? "selected": ""%>>U</option>
                        <option value="22" <%=(row != null && row.equals("22")) ? "selected": ""%>>V</option>
                        <option value="23" <%=(row != null && row.equals("23")) ? "selected": ""%>>W</option>
                        <option value="24" <%=(row != null && row.equals("24")) ? "selected": ""%>>X</option>
                        <option value="25" <%=(row != null && row.equals("25")) ? "selected": ""%>>Y</option>
                        <option value="26" <%=(row != null && row.equals("26")) ? "selected": ""%>>Z</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="dialh"><%--column:--%>
                <%=mdm.getContainerContentLongName(Fieldname.COLUMN)%>*</td>
                <td>
                    <input type="text" size="3" name="column" id="column" class="required digits"
                           value="<%=(column != null) ? column : ""%>"/>
                </td>
            </tr>
            <tr>
                <td class="dialh"><%--sampleID:--%>
                <%=mdm.getContainerContentLongName(Fieldname.SAMPLE)%></td>
                <td colspan="3">
                    <select name="sampleID" id="sampleID">
                        <%=tmpHttpSessObj.getObjectPrompterUniqueField(Sample.class, sampleID, "sampleName", false)%>
                    </select>
                <input type="button" class="button" id="editSample" value="Edit this Sample" title="Edit the selected sample's ID,sample type, subject this sample belongs to." onclick="gotoEditSample(<%=contContId%>)">
                <input type="button" class="button" id="addSample" value="Add new Sample ID" title="Add a new sample ID and define its sample type and the subject it belongs to." onclick="gotoAddSample(<%=contContId%>)">
            </tr>
<tr>
        <td class="dialh"><%--materialType--%>
        <%=mdm.getContainerContentLongName(Fieldname.MATERIALTYPE)%></td>
        <td>
          <select name="materialTypeID" id="materialTypeID" onchange="toggleAmpDate(); $('#addContainerContentForm').valid();">
              <%=tmpHttpSessObj.getObjectPrompterUniqueField(MaterialType.class, materialTypeID, "description", false)%>
          </select>
        </td>
      </tr>
       <tr>
        <td class="dialh"><%--shipped date(yyyy/mm/dd):--%>
        <%=mdm.getContainerContentLongName(Fieldname.AMPLIFICATIONDATE)%>*</td>
        <td>
          <input type="text" name="ayear" id="ayear" maxlength="4" size="4"
                 <%=tmpHttpSessObj.getFieldId("ayear")%>
                 value="<%=(ayear!=null)?ayear:""%>"
                 <%=(materialTypeID == null || !materialTypeID.equals("2")) ? "disabled" : ""%>/><b>-</b>
          <input type="text" name="amonth" id="amonth" maxlength="2" size="2"
                 <%=tmpHttpSessObj.getFieldId("amonth")%>
                 value="<%=(amonth!=null)?amonth:""%>"
                 <%=(materialTypeID == null || !materialTypeID.equals("2")) ? "disabled" : ""%>/><b>-</b>
          <input type="text" name="aday" id="aday" maxlength="2" size="2"
                 <%=tmpHttpSessObj.getFieldId("aday")%>
                 value="<%=(aday!=null)?aday:""%>"
                 <%=(materialTypeID == null || !materialTypeID.equals("2")) ? "disabled" : ""%>/> (YYYY MM DD)
        </td>
      </tr>
            <tr>
                <td class="dialh"><%--volume:--%>
                <%=mdm.getContainerContentLongName(Fieldname.VOLUME)%>*</td>
                <td>
                    <input type="text" size="5" name="volume" id="volume" class="number required"
                           value="<%=(volume != null && !volume.equals("-1.0")) ? volume : ""%>"/>
                    uL
                </td>
            </tr>
            <tr>
                <td class="dialh"><%--concentration:--%>
                <%=mdm.getContainerContentLongName(Fieldname.CONCENTRATION)%></td>
                <td>
                    <input type="text" size="5" name="concentration" id="concentration" class="number"
                           value="<%=(concentration != null && !concentration.equals("-1.0")) ? concentration : ""%>"/>
                    ng/uL
                </td>
            </tr>
                        <tr>
                <td class="dialh"><%--dilution:--%>
                <%=mdm.getContainerContentLongName(Fieldname.DILUTION)%></td>
                <td>
                    <input type="text" size="5" name="dilution" id="dilution"
                           value="<%=(dilution != null  && !dilution.equals("-1")) ? dilution : ""%>"/>
                </td>
            </tr>

                        <tr>
                <td class="dialh"><%--comments:--%>
                <%=mdm.getContainerContentLongName(Fieldname.COMMENTS)%></td>
                <td>
                    <input type="text" size="40" name="comments" id="comments"
                    value="<%=(comments != null) ? comments : ""%>" />
                </td>
            </tr>

        </thead>
    </table>
</fieldset>


