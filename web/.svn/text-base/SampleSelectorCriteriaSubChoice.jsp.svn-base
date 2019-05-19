<%-- 
    Document   : SampleSelectorCriteriaSubChoice
    Created on : Oct 20, 2009, 2:28:09 PM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%String lapasId = "SampleSelectorCriteriaSubChoice";%>

<%
// for the fiels that don't need input, just update the crtieria
// and send back a blank for the specifications
// can't have header hre because want to use <body>
String criteriaKey = null;
if ((criteriaKey = request.getParameter("criteria")) != null) {
    if (criteriaKey.equals("concentrationMaximize")
            || criteriaKey.equals("volumeMaximize")
            || criteriaKey.equals("amtDNAMaximize")
            || criteriaKey.equals("neverGenotyped")
            || criteriaKey.equals("prevGenotyped")
            || criteriaKey.equals("collectionDateMax")
            || criteriaKey.equals("extractionDateMax")
            || criteriaKey.equals("amplificationDateMax")
            || criteriaKey.equals("stockOnly")) {%>
        <%@page   import="java.util.LinkedHashMap"
          import="icapture.com.*"
          import="icapture.hibernate.*"%>
<%

        UserHttpSess tmpHttpSessObj = SessionListener.getUserHttpSessObject(session);
        if (tmpHttpSessObj == null) {
            pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
            return;
        }
        SampleSelector selector = tmpHttpSessObj.getCurrentSampleSelector();
        int level = selector.getCurrentLevel();
        System.out.println("SubChoice 1 level="+level);

            // get criteria list that this one was chosen from
        LinkedHashMap prevCriteria = selector.getAvailCriteriaForLevel(level);

        System.out.println("SubChoice 3 prevCriteria.size()="+prevCriteria.size());

            // prepare next level's list by removing the selected one
            LinkedHashMap availCriteria = new LinkedHashMap(prevCriteria);
            availCriteria.remove(criteriaKey);

        System.out.println("SubChoice 3 availCriteria.size()="+availCriteria.size());

            // store next level's list
            selector.setAvailCriteriaForLevel((level + 1), availCriteria);
            
        System.out.println("SubChoice 4 ");

            // store selected criteria in criteria profile
            selector.addToCriteriaProfile(level, criteriaKey);
            
        System.out.println("SubChoice 5 ");

        %>

<body onload="document.maximizeForm.submit()">
<html>
<form method="POST" name="maximizeForm" id="maximizeForm"
      action="<%=response.encodeRedirectURL("./SampleSelectorCriteriaChoice.jsp")%>">
<input type="hidden" name="noInput" value="true">
<input type="hidden" name="next" value="true">
<input type="hidden" name="criteriaKey" value="<%=criteriaKey%>">
</form>
</html>
</body>
<%
    return;
    }
}
%>
<%@include file="Header.jsp"%>

<%
        tmpHttpSessObj.clearInvalidField();

//test new session
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }

%>
<script language="JavaScript" type="text/javascript">
    function updateSpecifics(){
        var sel = document.getElementById("ordered");
        var newSel = document.getElementById("specifics");

        // remove all options from select
         while (newSel.firstChild) {
         newSel.removeChild(newSel.firstChild);
         }

        //go through options of orig sel
        for(var i =0; i<sel.length;i++){
            //clone option of orig sel
            var option = sel.options[i];
            //add clone to new sel
            //
            // IE7 doesn't support textContext, uses innerText instead
            var text = null;
            if(option.innerText){ //is IE
                text= option.innerText.valueOf();
            }
            if(option.textContent){
                text = option.textContent.valueOf()
            }
            var cloneOption = new Option(text,option.value.valueOf());
            // make sure they're all selected so I can get them later
   
        cloneOption.selected = true;
            newSel.options[newSel.length] = cloneOption;
        }
    }
    function addOption(theSel, theText, theValue)
    {
        var newOpt = new Option(theText, theValue);
        var selLength = theSel.length;
        theSel.options[selLength] = newOpt;
    }

    function deleteOption(theSel, theIndex)
    {
        var selLength = theSel.length;
        if(selLength>0)
        {
            theSel.options[theIndex] = null;
        }
    }

    function moveUp(theSel){
        var selLength = theSel.length;
        var selCount = 0;
        for (i = 1; i <= selLength-1; i++){
            if (theSel.options[i].selected){
                if (i-1 <= selCount && theSel.options[i-1].selected){
                    selCount++;
                    continue;
                }
                var current = theSel.options[i];
                var oneUp = theSel.options[i-1];
                theSel.insertBefore(current,oneUp);
            }
        }
        updateSpecifics();
    }
    function moveDown(theSel){
        var selLength = theSel.length;
        var selCount = selLength-1;
        for (i=selLength-2; i>=0; i--){
            if (theSel.options[i].selected){
                if (i+1 >= selCount && theSel.options[i+1].selected){
                    selCount--;
                    continue;
                }
                var current = theSel.options[i];
                var next = theSel.options[i+1];
                theSel.insertBefore(next,current);
            }
        }
        updateSpecifics();
    }

    function moveOptions(theSelFrom, theSelTo)
    {

        var selLength = theSelFrom.length;
        var selectedText = new Array();
        var selectedValues = new Array();
        var selectedCount = 0;

        var i;

        // Find the selected Options in reverse order
        // and delete them from the 'from' Select.
        for(i=selLength-1; i>=0; i--)
        {
            if(theSelFrom.options[i].selected)
            {
                selectedText[selectedCount] = theSelFrom.options[i].text;
                selectedValues[selectedCount] = theSelFrom.options[i].value;
                deleteOption(theSelFrom, i);
                selectedCount++;
            }
        }

        // Add the selected text/values in reverse order.
        // This will add the Options to the 'to' Select
        // in the same order as they were in the 'from' Select.
        for(i=selectedCount-1; i>=0; i--)
        {
            addOption(theSelTo, selectedText[i], selectedValues[i]);
        }

        // update the fields eventually grabbed
        updateSpecifics();

    }
</script>

<%
        tmpHttpSessObj.clearInvalidField();
//test new session
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }
        SampleSelector selector = tmpHttpSessObj.getCurrentSampleSelector();
        int level = selector.getCurrentLevel();
        criteriaKey = null;
        if ((criteriaKey = request.getParameter("criteria")) != null) {


             System.out.println("SubChoice 1 level="+level);

            // get criteria list that this one was chosen from
        LinkedHashMap prevCriteria = selector.getAvailCriteriaForLevel(level);

        System.out.println("SubChoice 3 prevCriteria.size()="+prevCriteria.size());

            // prepare next level's list by removing the selected one
            LinkedHashMap availCriteria = new LinkedHashMap(prevCriteria);
            availCriteria.remove(criteriaKey);

        System.out.println("SubChoice 3 availCriteria.size()="+availCriteria.size());

            // store next level's list
            selector.setAvailCriteriaForLevel((level + 1), availCriteria);

        System.out.println("SubChoice 4 ");

            // store selected criteria in criteria profile
            selector.addToCriteriaProfile(level, criteriaKey);

        System.out.println("SubChoice 5 ");

            // get display name of property
            String criteriaValue = (String) prevCriteria.get(criteriaKey);

            // if material type or sample type (criteria with distinct options)
            // use this interface
            if (criteriaKey.equals("materialType") || criteriaKey.equals("sampleType")) {

                // get options for box sizes
                ArrayList options = null;
                if (criteriaKey.equals("materialType")) {
                    options = (ArrayList) tmpHttpSessObj.getAllField(MaterialType.class, "description");
                }
                if (criteriaKey.equals("sampleType")) {
                    options = (ArrayList) tmpHttpSessObj.getAllField(SampleType.class, "description");
                }
%>

<html>
            <script language="JavaScript" type="text/javascript">
            var removAble = 0;
    function checkSelect(){
        if(
        document.optionsForm.specifics.value == null ||
        document.optionsForm.specifics.value == "" ||
        document.optionsForm.specifics.value.valueOf() == ''){
        $("#emptyError").html("Error: Input missing. <br>Please move at least one entry <br>to the right box or click 'back'.");
        $("#emptyError").css("display","inline");
        removAble = 1;
            return false;
        }
        else return true;
    }
    function removeError(){
        if (removAble==1)
        if(!(document.optionsForm.specifics.value == null ||
            document.optionsForm.specifics.value == "" ||
            document.optionsForm.specifics.value.valueOf() == '')){
                $("#emptyError").css("display","none");
                removAble = 0;
        }
    }
</script>
<form method="POST" name="optionsForm"
      action="<%=response.encodeRedirectURL("./SampleSelectorCriteriaChoice.jsp")%>" onmousemove="removeError()" onmouseup="removeError()">
        <input type="hidden" name="discreet" value="true"/>
        <input type="hidden" name="criteriaKey" value="<%=criteriaKey%>">
    <table class="navigate">
        <tr>
            <td class="left" align="left" colspan="2"><a class="largest">Prepare Selection Criteria</a></td>
        </tr>
        <tr>
            <td>
            <fieldset>
            <table border="0" frame="box">
            <tr><td class="larger" colspan="4">Define the preference hierarchy of <%=criteriaValue%>'s options:</td></tr>
            <tr><td colspan="4">Select a row and use the arrows to move it<br><br></td></tr>
            <tr><td>These are your available options for this field.<br>
                    If you leave any options in this box, samples <br>
                    with these values will NOT be considered.</td>
            <td>&nbsp;</td>
            <td>Samples will be selected
                <br>according to the order below.
                    <div>
                        <label class="error" generated="true" for="orderedOptions" id="emptyError" style="display:none"></label>
                    </div></td>
            </tr>
            <tr>
                <td valign="middle" align="center">
                    <select name="available" id="available" size="<%=options.size()%>" multiple="multiple">
               <%if (criteriaKey.equals("materialType")) {%>
                    <%=tmpHttpSessObj.getObjectPrompter(MaterialType.class, "")%>
               <%} else if (criteriaKey.equals("sampleType")) { %>
                    <%=tmpHttpSessObj.getObjectPrompter(SampleType.class, "")%>
               <%}%>
                    </select>
                </td>
                <td align="left" valign="middle"><input type="button" value="&#8594;"
                           onclick="moveOptions(this.form.available, this.form.orderedOptions);" />Add<br />
                    <input type="button" value="&#8592;"
                           onclick="moveOptions(this.form.orderedOptions, this.form.available);" />Remove
                </td>
                <td>
                    <select name="orderedOptions" id="ordered" size="<%=options.size()%>" multiple="multiple">
                    </select>
                </td>
                                                                    <td align="left" valign="middle"><input type="button" value="&#8593;"
                           onclick="moveUp(this.form.orderedOptions);" />Move Up<br />
                    <input type="button" value="&#8595;"
                           onclick="moveDown(this.form.orderedOptions);" />Move Down
                </td>
        <tr>
                <td colspan="4">
                    <input type="submit" name="back" value="Back"/>
                    <input type="submit" name="next" value="Next" onclick="return checkSelect();"/>
                </td>
            </tr>
    </table>
    </fieldset>

    <select name="specifics" id="specifics" style="visibility:hidden" size="1" multiple="multiple"/>
      </td></tr></table>
</form>
</html>
<%} else if(criteriaKey.equals("collectionDate")
                    || criteriaKey.equals("extractionDate")
                    || criteriaKey.equals("amplificationDate") ){
    %>
   <html>
<form method="POST" name="dateForm"
      action="<%=response.encodeRedirectURL("./SampleSelectorCriteriaChoice.jsp")%>">
          <script language="JavaScript" type="text/javascript">
    function checkSelect(){
        if(document.dateForm.dateayear.value.valueOf() == ''
            || document.dateForm.datebyear.value.valueOf() == ''){
        alert("Error: Input missing. Please fill in a numeric value for "
                +"(at least) each year or click 'back'.");
            return false;
        }
    }</script>
    <table class="navigate">
        <input type="hidden" name="dates" value="true"/>
        <input type="hidden" name="criteriaKey" value="<%=criteriaKey%>">
        <tr>
            <td class="left" align="left" colspan="2">
                <a class="largest">Prepare Selection Criteria</a></td>
        </tr>
        <tr>
            <td>
            <fieldset>
            <table border="0" frame="box">
            <tr><td class="larger" colspan="4">Define the dates limiting <%=criteriaValue%>'s preferred span:</td></tr>
            <tr><td colspan="4">Dates are inclusive. 
            Blanks will be interpreted as the lowest calendar value for the "after" date and
            the highest calendar value for the "before" date. 
            (Ex: 2000 as "after" would be 2000-01-01, 
            2000 as "before" would be 2000-12-31)
            Use the checkbox to control whether samples with dates outside of
            the span entered and samples with missing dates will be considered
            in selection. </td></tr>
            <tr><td>&nbsp;</td></tr>
            <tr>
                    <%=selector.getSelectorDatePrompter("date")%>
            </tr><tr>
                <td valign="middle" align="left">
                    <input type="checkbox" name="dateStrictCheck" value="true"/>
                    Do not consdier any samples with missing dates or dates outside of this span
                    <input type="hidden" name="dateStrictCheck" value="false"/>
                    </td>
            </tr>
            <tr><td>&nbsp;</td></tr>
            <tr>
                <td colspan="4">
                    <input type="submit" name="back" value="Back"/>
                    <input type="submit" name="next" value="Next" onclick="return checkSelect();"/>
                </td>
            </tr>

            
    </table>
    </fieldset>
          </td></tr></table>
</form>
</html>

<%  }else if(criteriaKey.equals("concentrationMinimum")
                    || criteriaKey.equals("volumeMinimum")
                    || criteriaKey.equals("amtDNAMinimum") ){
    String field = (criteriaKey.equals("concentrationMinimum"))? "Concentration":
                    (criteriaKey.equals("volumeMinimum"))? "Volume":
                        "Amount of DNA";
    String aan = (criteriaKey.equals("concentrationMinimum"))? "a":
                    (criteriaKey.equals("volumeMinimum"))? "a":
                        "an";
    String unit = (criteriaKey.equals("concentrationMinimum"))? "ng/ul":
                    (criteriaKey.equals("volumeMinimum"))? "ul":
                        "ng";

%>
   <html>
<form method="POST" name="minForm"
      action="<%=response.encodeRedirectURL("./SampleSelectorCriteriaChoice.jsp")%>">
          <script language="JavaScript" type="text/javascript">
    function checkSelect(){
        if(document.minForm.minVal.value.valueOf() == ''){
        alert("Error: Input missing. Please fill in a numeric value or click 'back'.");
            return false;
        }
    }</script>

    <table class="navigate">
<input type="hidden" name="criteriaKey" value="<%=criteriaKey%>">
        <tr>
            <td class="left" align="left" colspan="2">
                <a class="largest">Prepare Selection Criteria</a></td>
        </tr>
        <tr>
            <td>
            <fieldset>
            <table border="0" frame="box">
            <tr><td class="larger" colspan="4">Define the Minimum <%=field%> Allowable:</td></tr>
            <tr><td colspan="4">All samples with <%=aan%> <%=field.toLowerCase()%> greater than
            or equal to the value entered below will be considered preferentially
            in sample selection. Use the checkbox to control whether samples with
            <%=aan%> <%=field.toLowerCase()%> less than the
             value entered <em>or with missing</em> <%=aan%> <%=field.toLowerCase()%> will be considered in selection at all.</td></tr>
            <tr><td>&nbsp;</td></tr>
            <tr>
                <td valign="middle" align="left">
                    <input type="hidden" name="minimum" value="true"/>
                    <input type="text" size="8" name="minVal"/> <%=unit%>
                    </td>
            </tr>
            <tr>
                <td valign="middle" align="left">
                    <input type="checkbox" name="minimumCheck" value="true"/>
                    Do not consdier any samples with missing <%=field.toLowerCase()%> or with <%=aan%> <%=field.toLowerCase()%> less than this
                    </td>
            </tr><tr>
                <td colspan="4">
                    <input type="submit" name="back" value="Back">
                    <input type="submit" name="next" value="Next" onclick="return checkSelect();"/>
                </td>
            </tr>
    </table>
    </fieldset>
          </td></tr></table>
</form>
</html>


<%
}else if(criteriaKey.equals("callRate") ){
%>
   <html>
<form method="POST" name="callRateForm"
      action="<%=response.encodeRedirectURL("./SampleSelectorCriteriaChoice.jsp")%>">
<script language="JavaScript" type="text/javascript">
    function checkSelect(){
        if(document.callRateForm.callRate.value.valueOf() == ''){
        alert("Error: Input missing. Please fill in a numeric value or click 'back'.");
            return false;
        }
    }</script>

    <table class="navigate">

<input type="hidden" name="criteriaKey" value="<%=criteriaKey%>">
        <tr>
            <td class="left" align="left" colspan="2">
                <a class="largest">Prepare Selection Criteria</a></td>
        </tr>
        <tr>
            <td>
            <fieldset>
            <table border="0" frame="box">
            <tr><td class="larger" colspan="4">Define Pass/Fail Genotyping by Call Rate:</td></tr>
            <tr><td colspan="4">
            Samples with all their call rates greater than
            or equal to the value entered below will be considered preferentially
            in sample selection. Use the  
            checkbox to control whether any samples with a call rate less than the
             value entered from <em>any</em> genotyping run will be considered in selection.
             <br><br>
            (Ex: If 95% is entered and the checkbox <em>is not</em> ticked,
             samples that have always obtained a
             call rate of 95% or higher will be considered preferentially 
             for selection over samples that have any call rate less than 95%.<br>
             If the checkbox <em>is</em> ticked and 95% is entered,
            only samples that have always obtained a
             call rate of 95% or higher will be considered for selection.
             Any sample that has a 94.9% call rate from any genotyping run
             will never be considered.)</td></tr>
             <tr>
            <tr><td>&nbsp;</td></tr>
            <tr>
                <td valign="middle" align="left">
                    <input type="text" size="8" name="callRate"/> %
                    </td>
            </tr>
            <input type="hidden" name="callRateWGACheck" value="true"/>
            <tr>
                <td valign="middle" align="left">
                    <input type="checkbox" name="callRateStrictCheck" value="true"/>
                    Do not consdier any samples with a call rate less than this
                    <input type="hidden" name="callRateStrictCheck" value="false"/>
                    </td>
            </tr>
            <tr><td>&nbsp;</td></tr>
            <tr>
                <td colspan="4">
                    <input type="submit" name="back" value="Back">
                    <input type="submit" name="next" value="Next" onclick="return checkSelect();"/>
                </td>
            </tr>
    </table>
    </fieldset>
          </td></tr></table>
</form>
</html>


<%}else if(criteriaKey.equals("dilution") ){
%>
   <html>
<form method="POST" name="dilutionForm"
      action="<%=response.encodeRedirectURL("./SampleSelectorCriteriaChoice.jsp")%>">
<script language="JavaScript" type="text/javascript">
    function checkSelect(){
        var d1 = document.getElementById('d1').checked;
        var d2 = document.getElementById('d2').checked;
        var d3 = document.getElementById('d3').checked;
        if( d1 == false && d2 == false && d3 == false){
        alert("Error: Input missing. Please check one or more boxes or click 'back'.");
            return false;
        }
    }
</script>

    <table class="navigate">

<input type="hidden" name="criteriaKey" value="<%=criteriaKey%>">
<input type="hidden" name="dilution" value="<%=criteriaKey%>">
        <tr>
            <td class="left" align="left" colspan="2">
                <a class="largest">Prepare Selection Criteria</a></td>
        </tr>
        <tr>
            <td>
            <fieldset>
            <table border="0" frame="box">
            <tr><td class="larger" colspan="4">Define Dilutions Allowable:</td></tr>
            <tr><td colspan="4">
            Select which dilution values are acceptable for samples
            (typically, only WGA samples have dilution values). Any sample that does not have
             one of the
            dilution values checked below will <em>not</em> be considered in selection.
            This includes samples where the dilution value is missing or undefined.
             <tr>
            <tr><td>&nbsp;</td></tr>
            <tr>
                <td valign="middle" align="left">
                    <input type="checkbox" name="dilutionValues" id="d1" value="1:1"/>1:1 (Stock)<br>
                    <input type="checkbox" name="dilutionValues" id="d2" value="1:10"/>1:10 <br>
                    <input type="checkbox" name="dilutionValues" id="d3" value="1:100"/>1:100 <br>
                    </td>
            </tr>
            <tr><td>&nbsp;</td></tr>
            <tr>
                <td colspan="4">
                    <input type="submit" name="back" value="Back">
                    <input type="submit" name="next" value="Next" onclick="return checkSelect();"/>
                </td>
            </tr>
    </table>
    </fieldset>
          </td></tr></table>  
</form>
</html>


<%}
}%>

<%@include file="Footer.jsp"%>

