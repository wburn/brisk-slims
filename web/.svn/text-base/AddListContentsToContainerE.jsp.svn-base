<%-- 
    Document   : AddListContentsToContainerDNAE
    Created on : Oct 12, 2009, 1:12:32 PM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<table class="view_table" >
            <tr>
                <td align="left" colspan="2"><a class="largest">Specify Sample Properties</a></td>
                </tr>
            <tr><td><br></td></tr>
                <tr><td class="instructions">For each sample, specify (a) how much DNA (green column 1) or volume (green column 2) of
                an original sample is to be used in the creation of it’s corresponding new
                sample, (b) what the volume of the new sample in the new container will be (green column 3),
                (c) the new samples' concentrations or dilutions (these values can be copied
                from the original samples using the 'originals' button) (green column 4) and (d) any comments
                that should be stored with the new samples (green column 5). If rows are too wide, use the
                "Customize sample data view" option of the drop down box to the right to hide
                some columns. When you are finished, click 'Validate'.
                    </td>
                    <td align="right" valign="middle" width="250"><%
            if (tmpHttpSessObj.isAdvancedUserUp()) {%>
        <select id="gotoNav" >
            <option selected value="blank">What would you like to do?</option>
            <option value="view">Customize sample data view</option>
        </select><button type="button" class="buttonSmall" onclick="goNav(<%=containerID%>)">Go</button>

        <%}%><br><br>
  </td></tr>
</table>


    <%

        MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
        //int total = tmpHttpSessObj.getAllContentsListCount();

        // figure out how many contents to put in this plate
        int contentsDone = plater.plated;
        int controlsHere = plater.controlsJustAdded;
        int total = tmpHttpSessObj.getCurrentShoppingCartList().getContainerContentsList().size();
        int contentsToDo = total - contentsDone;
        int maxContentsHere = ((96 - controlsHere)>contentsToDo)?contentsToDo:(96 - controlsHere);

        List containercontentList = plater.getPlatingList(maxContentsHere);
        
        

        String listName = tmpHttpSessObj.getCurrentShoppingList().getListName();
        vfm.setContainerContentCount(containercontentList.size());

        String[] keyArray = mdm.getContainerContentKeyArray();
        int colCount = keyArray.length;
        String cellClassName;%>

<% // if plate isn't full, ask how the user wants samples laid out
if(maxContentsHere+controlsHere<96){
    //get control well info
    HashMap wellHash = plater.getControlWellHash(plater.getControlLayoutJustUsed());
    // get sample layout options
    int samplesLeft = maxContentsHere;

    // for colouring samples in 'by column' plate diagram
    ArrayList sampByCol = plater.getPlateFillByColumn(plater.getControlLayoutJustUsed(),samplesLeft);

    %>
<fieldset>
<table class="instructions" >
    <tr><td>
Note: This plate will have less than 96 wells full, which sample layout scheme would you prefer? Select one below.
<br> <a style="background-color:#B9E0BA">Green</a> are +/- controls, <a style="background-color:#B4CFEA">blue</a>
are samples and <a style="background-color:white">white</a> are empty wells.
</td></tr>
</table>
<table>
<tr><td><input type="radio" name="sampleLayoutScheme" id="schemeRow" value="row" checked/> By row</td>
<td><input type="radio" name="sampleLayoutScheme" id="schemeCol" value="column"/> By column</td></tr>
<tr><td>
<table class="plate"  style="width:450px">
                    <%for (int j = 1; j <= 8; j++) {%>
                    <tr style="height:25px;"><%
                    // if well is control, mark as such
                        for (int k = 1; k <= 12; k++) {
                            if (wellHash.containsKey("row" + j + "col" + k)) {%>
                            <td style="background-color:#B9E0BA"><a class="smaller"><%=Util.numToLetter(String.valueOf(j))+(k)%></a></td>
                            <%} // if not a control well, put a sample
                            else if(samplesLeft>0){%>
                            <td style="background-color:#B4CFEA"><a class="smaller"><%=Util.numToLetter(String.valueOf(j))+(k)%></a></td>
                            <% samplesLeft--;
                            }// empty well
                            else{%>
                            <td style="background-color:white"><a class="smaller"><%=Util.numToLetter(String.valueOf(j))+(k)%></a></td>
                            <%}
                        }%>
                    </tr>
                    <%}%>
      </table>
</td><td>
<table class="plate"  style="width:450px">
                    <%for (int j = 1; j <= 8; j++) {%>
                    <tr style="height:25px;"><%
                    // if well is control, mark as such
                        for (int k = 1; k <= 12; k++) {
                            //control
                            if (wellHash.containsKey("row" + j + "col" + k)) {%>
                            <td style="background-color:#B9E0BA"><a class="smaller"><%=Util.numToLetter(String.valueOf(j))+(k)%></a></td>
                            <%} // if not a control well, put a sample
                            else if(sampByCol.contains("row" + j + "col" + k)) {%>
                            <td style="background-color:#B4CFEA"><a class="smaller"><%=Util.numToLetter(String.valueOf(j))+(k)%></a></td>
                            <%}// empty well
                            else{%>
                            <td style="background-color:white"><a class="smaller"><%=Util.numToLetter(String.valueOf(j))+(k)%></a></td>
                            <%}
                        }%>
                    </tr>
                    <%}%>
                </table>
</td></tr>
</table>
</fieldset>
<%}%>

<table align="right">
    <tr><td valign="bottom" style="border: medium solid MediumAquaMarine">
    <input type="submit" value="Validate" name="createContents"
           onclick="return validateEntries()"></td></tr>
</table>

<!--satura tabula -->
<table class="view_table" style="border-width: 0px; border-spacing: 1px; border-color: #ffffff;" >
<thead>
<tr bgcolor="#b3cfff">
    <td width="1%"><a href='javascript:jumpFocus("bottomLine")'><img border="0" src="./images/down_d_10_b.gif" title="bottom of page" alt=""></a></td>
    <td>
        <table style="background-color:#b3cfff; border-width: 0px; border-spacing: 0px; border-color: #ffffff;">
                <tr><td colspan="2" >
                <input type="submit" name="updateList" value="Update List" style="font-size:8pt; background-color:LightBlue"/>
                </td></tr>
                <tr><td align="right">
                <a onclick="checkAll(document.fForm.contentListChecked)">
                    <img border="0" src="./images/selectAll_a.png" onmouseover="saIn(this)"
                         onmouseout="saOut(this)" title="Select All" name="updateList" alt="">
                </a></td><td align="left">
                <a onclick="uncheckAll(document.fForm.contentListChecked)">
                    <img border="0" src="./images/deselectAll_a.png" onmouseover="daIn(this)"
                         onmouseout="daOut(this)" title="Deselect All" name="updateList" alt="">
                </a>
                </td></tr></table>
    </td>

<%
        for (int j = 1; j < colCount; j++) {
    %><td class="headerEven"><%
                if (mdm.getContainerContentSortable(keyArray[j])) {
        %><a class="<%=tmpHttpSessObj.getContainerContentSortId(keyArray[j])%>"
             href='javascript:gotoSorted("<%=keyArray[j]%>")'>
        <%=mdm.getContainerContentLongName(keyArray[j])%></a><%
        } else {
        %><b><%=mdm.getContainerContentLongName(keyArray[j])%></b><%
                }
    %></td><%
        }
    %>
    <td class="action" style="font-weight:bold">DNA from Source to be Used (ng)
    </td>
    <td class="action" style="font-weight:bold">Volume of Original to be Used (ul)
    </td>
    <td style="background-color:#b3cfff">&nbsp;</td>
    <td class="action" style="font-weight:bold">Volume of New Sample (ul)
    </td>
        <td class="action" style="font-weight:bold">
            New Concen (x ng/ul) or Dilution (x:xxx)
            <!--input type="radio" value="concen" name="concenDilChoice" id="concenChoice" checked>New Concen (ng/ul)
            <br><input type="radio" value="concen" name="concenDilChoice" id="dilChoice">New Dilution -->
    </td>
    <td class="action" style="font-weight:bold">
            New Comments
            </td>
</tr>
</thead>
<tr>
    <%for (int j = 0; j < 2; j++) {%>
    <td bgcolor="#b3cfff">&nbsp;</td>
    <%}%>
    <%for (int j = 1; j < colCount; j++) {%>
    <td class="headerEven" >&nbsp;</td>
    <%}%>
    <td class="action">
        <input type="text" size="5" name="dnaBoxRmvAll" id="dnaBoxRmvAll"/> 
    <input type="button" onclick="applyToAllMax('dnaBoxRmv','dna')" value="All" class="buttonSmall" title="Apply this value to all samples">
    <input type="button" onclick="takeMaxDNA()" value="Maximum" class="buttonSmall"  title="Use the maximum amount of DNA available from the source sample">
    <input type="button" onclick="calc('dnaBoxRmv')" class="buttonSmall" value="Calculate" title="Try to calculate this value based others filled in">
    </td>
    <td class="action">
        <input type="text" size="5" name="volBoxRmvAll" id="volBoxRmvAll"/> 
    <input type="button" onclick="applyToAllMax('volBoxRmv','vol')" value="All" class="buttonSmall" title="Apply this value to all samples">
    <input type="button" onclick="calc('volBoxRmv')" class="buttonSmall" value="Calculate" title="Try to calculate this value based others filled in">
    </td>
    <td style="background-color:#b3cfff">&nbsp;</td>
    <td class="action">
        <input type="text" size="5" name="volBoxNewAll" id="volBoxNewAll"/> 
    <input type="button" onclick="applyToAll('volBoxNew')" value="All" class="buttonSmall" title="Apply this value to all samples">
    <input type="button" onclick="calc('volBoxNew')" class="buttonSmall" value="Calculate" title="Try to calculate this value based others filled in">
    </td>
    <td class="action">
    <input type="text" size="5" name="concenDilBoxNewAll" id="concenDilBoxNewAll"/>
    <input type="button" onclick="applyToAll('concenDilBoxNew')" value="All" class="buttonSmall" title="Apply this value to all samples">
    <input type="button" onclick="applyOrigToAll()" value="Originals" class="buttonSmall" title="Use original concentrations or dilutions of source samples">
    <input type="button" onclick="calc('concenDilBoxNew')" class="buttonSmall" value="Calculate" title="Try to calculate this value based others filled in">
    </td>
    <td class="action">
        <input type="text" size="5" name="commentsNewAll" id="commentsBoxNewAll"/>
    <input type="button" onclick="applyToAll('commentsBoxNew')" value="All" class="buttonSmall"><br>
    </td>
</tr>
<tbody><%
        // actual data rows
        if (containercontentList != null) {
            byte[] valueIndexArray = mdm.getContainerContentIndexArray();
            Iterator iter = containercontentList.iterator();
            String currStripe;
            Long currId = tmpHttpSessObj.getCurrentContainerContentId();
            boolean stripOn = true;
            int lineNr = 0;
            String overrideColour = "";
            String inList = "";

            while (iter.hasNext()) {
                ContainerContent currContainerContent = (ContainerContent) tmpHttpSessObj.getObjectById(ContainerContent.class, iter.next().toString());
                String[] var = currContainerContent.getValueArrayReadable(valueIndexArray, colCount);

                if (currContainerContent.getId().equals(currId)) {
                    currStripe = (stripOn) ? "curron" : "curroff";
                    if (tmpHttpSessObj.inContainerContentsList(currContainerContent.getId().toString())) {
                        overrideColour = "style=\"background-color:LightBlue\"";
                    }
    %><tr id="current" class="<%=currStripe%>" <%=overrideColour%>><%
                    } else {
                        if (tmpHttpSessObj.inContainerContentsList(currContainerContent.getId().toString())) {
                            overrideColour = "style=\"background-color:LightBlue\"";
                        }
                        currStripe = (stripOn) ? "son" : "soff";
    %><tr class="<%=currStripe%>" <%=overrideColour%>><%
                    }
                    stripOn = !stripOn;
                    lineNr++;

        %><td id ="<%=currContainerContent.getId()%>" width="1%">
            <a href="javascript:showSlidedHeader('<%=currContainerContent.getId()%>')">
                <img id ="headerImg_<%=currContainerContent.getId()%>" border='0' src='./images/up_10_b.gif' title="show header before line <%=lineNr%>" alt="">
            </a>
        </td>
        <%if (tmpHttpSessObj.inContainerContentsList(currContainerContent.getId().toString())) {
                        inList = "checked";
                    } else {
                        inList = "";
                    }%>

        <td>
            <input type="checkbox" <%=inList%> name="contentListChecked" value="<%=currContainerContent.getId()%>"
                   />

        </td>
        <%

                    for (int k = 1; k < colCount; k++) {
                        // actual field values written here:
                        // want null values to appear blank
%><td><%=(var[k] != null) ? var[k] : ""%></td><%
                    }
        %>

        <input type="hidden" name="ccIds" value="<%=currContainerContent.getId()%>">
        <input type="hidden" id="volBoxRmv<%=currContainerContent.getId()%>Max" value="<%=currContainerContent.getVolume()%>">
        <input type="hidden" name="concenDilOrig" id="concenDilOrig<%=currContainerContent.getId()%>"
               value="<%=(currContainerContent.getConcentration() == null
               || currContainerContent.getConcentration().equals("-1.0")
               || currContainerContent.getConcentration().equals(""))?currContainerContent.getDilution():currContainerContent.getConcentration()%>">
        <input type="hidden" name="concenOrig" id="concenOrig<%=currContainerContent.getId()%>"
               value="<%=currContainerContent.getConcentration()%>">
        <input type="hidden" name="diluOrig" id="diluOrig<%=currContainerContent.getId()%>"
               value="<%=currContainerContent.getDilution()%>">

        <td class="action">
            <input type="text" size="5" name="dnaBoxRmv" id="dnaBoxRmv<%=currContainerContent.getId()%>" />
            </td>
            <td class="action">
            <input type="text" size="5" name="volBoxRmv" id="volBoxRmv<%=currContainerContent.getId()%>" />
            </td>
        <td ></td>
        <td class="action">
            <input type="text" size="5" name="volBoxNew" id="volBoxNew<%=currContainerContent.getId()%>" />
        </td>
        <td class="action" id="concenTD<%=currContainerContent.getId()%>">
            <input type="text" size="5" name="concenDilBoxNew" id="concenDilBoxNew<%=currContainerContent.getId()%>" />
        </td>
        <td class="action">
            <input type="text" size="5" name="commentsBoxNew" id="commentsBoxNew<%=currContainerContent.getId()%>"/>
        </td>
    </tr><%
            }
        }
    %><tr id='slidedHeader' bgcolor="#b3cfff" class="slidedRow">
        <td width="1%">
            <a href='javascript:jumpFocus("bottomLine")'><img border="0" src="./images/down_d_10_b.gif" title="bottom of page" alt=""></a>
        </td><%

        for (int n = 1; n < colCount; n++) {
        %><td class="headerEven"><%
                    if (mdm.getContainerContentSortable(keyArray[n])) {
            %><a class="<%=tmpHttpSessObj.getContainerContentSortId(keyArray[n])%>"
                 href='javascript:gotoSorted("<%=keyArray[n]%>")'>
                <%--HREF="<%=response.encodeURL("./ViewContainerContentsList.jsp")+"?sortCol="+keyArray[n]+"&perstotal="+total%>"
            --%><%=mdm.getContainerContentLongName(keyArray[n])%></a><%
            } else {
            %><b><%=mdm.getContainerContentLongName(keyArray[n])%></b><%
                    }
        %></td><%
        }
        %><td>&nbsp;</td>
        <td>
            <a href="javascript:scrollTo(0,0)"><img border="0" src="./images/up_d_10_b.gif" title="top of page" alt=""></a>
        </td>
    </tr>
</tbody>
</table>

<table align="right">
                <tr><td valign="bottom" style="border: medium solid MediumAquaMarine">
                <input type="submit" value="Validate" name="createContents"
                       onclick="return validateEntries()"></td></tr>
            </table>

<!--%if (total > 0) {%>
&nbsp;
<a href="javascript:scrollTo(0,0)"><img border="0" src="./images/up_d_10_b.gif" title="top of page" alt=""></a>
&nbsp;&nbsp;
<!--%=vfm.getContainerContentControlString()%>
<!--%}%-->
