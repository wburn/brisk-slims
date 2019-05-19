<%-- 
    Document   : AddControlWellsToContainerManual
    Created on : Sep 22, 2009, 3:19:35 PM
    Author     : tvanrossum
--%>

<%String lapasId = "AddControlWells";%>
<%@include file="Header.jsp"%>
<%
        String tmpAction;
        String selfId;
        tmpHttpSessObj.clearInvalidField();
//test new session
        response.setHeader("Cache-Control", "no-store");
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }

        if (tmpHttpSessObj.getCurrentShoppingCartList() == null) {
            pageContext.forward(response.encodeURL("./ViewLists.jsp"));
            return;
        }
        Plater plater = tmpHttpSessObj.getCurrentPlater();
        if ((tmpAction = request.getParameter("cancel")) != null) {
            pageContext.forward(response.encodeURL("./ViewContainerContentsList.jsp"));
            return;
        }

        String tmpString = null;
        int messageNum = 0;
%>
<script  type="text/javascript" language="javascript">
    function setFocus(){
        field = document.getElementById("kluda");
        if (field) field.focus();
        else
            document.fForm.genId.focus();
    }
    function confirmDelete(){
        return (confirm("Are you sure?"));
    }
    var submitFlag = 0;

    function validate(){
        if(!checkDups()){
            return false;
        }
        var name = document.getElementById('layoutName');
        if(name.value==null){
            alert("Error: layout name required.");
            return false;
        }
        if(name.value.valueOf()==null || name.value.valueOf()==""){
            alert("Error: layout name required.");
            return false;
        }
        if(!checkUniquenessLayoutName(name)){
            alert("Error: layout name already used, please try another.");
            return false;
        }
        return true;
    }

    function checkDups(){
        // check every row against every other row
        var table = document.getElementById("inputTable");
        var rowCount = table.rows.length;
        for(var i =0;i<rowCount;i++){
            var row = document.getElementById("row"+i);
            var col = document.getElementById("col"+i);
            if(row != null && col!=null){
                for(var j =0;j<rowCount;j++){
                    if(i!=j){
                    var row1 = document.getElementById("row"+j);
                    var col1 = document.getElementById("col"+j);
                    if(row1 != null && col1!=null
                        && row.value.valueOf()==row1.value.valueOf()
                        && col.value.valueOf()==col1.value.valueOf()){
                        alert("Error: Well "+row.value.valueOf()+col.value.valueOf()+" assigned more than once.");
                        return false;
                    }
                }
                }
            }
        }
        return true;
    }
    
</script>

<%
        messageNum = 0;
        String layoutName;
        String[] controlID;
        String[] row;
        String[] column;
        String[] volume;
        String listContainerID = request.getParameter("listContainerID");

        if ((tmpAction = request.getParameter("next")) != null) {
            System.out.println("in next1");
            layoutName = request.getParameter("layoutName");
            controlID = request.getParameterValues("controlID");
            System.out.println("in next1.2");
            row = request.getParameterValues("row");
            System.out.println("in next1.3");
            column = request.getParameterValues("column");
            System.out.println("in next1.4");
            volume = request.getParameterValues("volume");
            System.out.println("in next1.5");

            System.out.println("in next2");
            if (messageNum == 0) {
                // save layout (layout name might already exist)
                messageNum = tmpHttpSessObj.addControlLayout(layoutName,controlID, row, column);
            }
            if (messageNum == 0) {
                plater.setControlLayoutJustUsed(layoutName);
                // add controls
                messageNum = tmpHttpSessObj.addControls(listContainerID,controlID, row, column,volume);

                System.out.println("in next3");
            }
            if (messageNum == 0) {

            System.out.println("in next4");
                plater.controlsJustAdded =(controlID==null)?0: controlID.length;
                    pageContext.forward(response.encodeURL("./AddListContentsToContainer.jsp") + "?listContainerID="+listContainerID);
                    return;
            } else if (messageNum != 0) {
                pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                return;
            }
%>
<form method="POST" name="fForm" onsubmit="return validate()"
      action="<%=response.encodeURL("./AddControlWellsToContainerManual.jsp?listContainerID="+listContainerID)%>">

    <%
        // prepare list names in session so we can check for uniqueness with javascript
        List names = tmpHttpSessObj.getAllObjects(ControlLayoutWell.class);
        Iterator iter = names.iterator();
        while(iter.hasNext()){
            String name = ((ControlLayoutWell) iter.next()).getLayoutName();
            %>
<input type="hidden" name="existingLayoutNames" value="<%=name%>">
            <%
        }%>


    <a class="largest">Add Controls</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <%@include file="AddControlWellsToContainerManualE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="next" value=" Next " onclick="validateEntries()"/>
  
            </tr>
        </thead>
    </table>
</form>


<%} else{ %>
            <script  type="text/javascript" language="javascript">
    function validateEntries(){
    alert("validating 1");
        // check that all rows are present and letters
        var rowsI = document.getElementsByName("row");

            for(var i = 0; i<rowsI.length;i++){
                if(rowsI[i].value.valueOf().length!=1 || !IsAplpha(rowsI[i].value.valueOf())){
                    alert("Error: All row values must each be a single letter (ex: \"A\").");
                    return false;
                }
            }
        
    alert("validating2");
        // check that all cols are present and numbers
        var cols = document.getElementsByName("column");
            for(var i = 0; i<rows.length;i++){
                if(cols[i].value.valueOf().length!=1 || !IsNumeric(cols[i].value.valueOf(),"")){
                    alert("Error: All columns must be given a numeric value (ex: \"11\").");
                    return false;
                }
            }
        

    alert("validating3");
        // check that all vols are numbers
        var vols = document.getElementsByName("volume");
            for(var i = 0; i<vols.length;i++){
                if(vols[i].value.valueOf().length!=1 || !IsNumeric(vols[i].value.valueOf(),".")){
                    alert("Error: All volumes must be numeric values (ex: \"5.0\").");
                    return false;
                }
            }
            return true;
    }

function IsNumeric(strString,add)
    //  check for valid numeric strings
    {
        var strValidChars = "0123456789"+add;
        var strChar;
        var blnResult = true;

        if (strString.length == 0) return false;

        //  test strString consists of valid characters listed above
        for (i = 0; i < strString.length && blnResult == true; i++)
        {
            strChar = strString.charAt(i);
            if (strValidChars.indexOf(strChar) == -1)
            {
                blnResult = false;
            }
        }
        return blnResult;
    }

function IsAlpha(strString)
    //  check for valid numeric strings
    {
        var strValidChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        var strChar;
        var blnResult = true;

        if (strString.length == 0) return false;

        //  test strString consists of valid characters listed above
        for (i = 0; i < strString.length && blnResult == true; i++)
        {
            strChar = strString.charAt(i);
            if (strValidChars.indexOf(strChar) == -1)
            {
                blnResult = false;
            }
        }
        return blnResult;
    }
        </script>
           <% //from View page
            controlID = null;
            row = null;
            column = null;
            volume = null;

%>

<form method="POST" name="fForm" onsubmit="return validate();"
      action="<%=response.encodeURL("./AddControlWellsToContainerManual.jsp?listContainerID="+listContainerID)%>">

        <%
        // prepare list names in session so we can check for uniqueness with javascript
        List names = tmpHttpSessObj.getAllObjects(ControlLayoutWell.class);
        Iterator iter = names.iterator();
        while(iter.hasNext()){
            String name = ((ControlLayoutWell) iter.next()).getLayoutName();
            %>
<input type="hidden" name="existingLayoutNames" value="<%=name%>">
            <%
        }%>

    <a class="largest">Add Controls</a>
    <br>
    <a class="error">&nbsp;</a>
    <%@include file="AddControlWellsToContainerManualE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="next" value=" Next "/>
                <!--input type="submit" name="next" onclick="validateEntries()" value=" Next "/-->

                </td>
                <td>
                    <!--input type="submit" name="cancel" value="Cancel" size="10"/-->
                </td>
            </tr>
        </thead>
    </table>
</form>
<%}%>
<%@include file="Footer.jsp"%>

