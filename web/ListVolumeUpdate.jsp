<%-- 
    Document   : ListVolumeUpdate
    Created on : Oct 30, 2009, 3:28:26 PM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">


<%String lapasId = "ListVolumeUpdate";%>
<%@include file="Header.jsp"%>

<%//test new session
        response.setHeader("Cache-Control", "no-store");
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }

// check permissions
if (!tmpHttpSessObj.isAdvancedUserWetUp()) {%>
<script  type="text/javascript" language="javascript">
    window.onload = disableAllButBack;
</script>
<br><a class="largest">Sorry, you do not have permission to use this page. </a><input type='button' onclick='history.go(-1);' name="badPermGoBack" value="Back" /><br><br>
Your privilege level is: <%=tmpHttpSessObj.getCurrentUser().getRightsReadable()%>.
To see the privileges associated with each level <input type="button" class="button" name="getPermissionDoc" onclick="openPermissionsDoc()" value="Click here"/> <br><br>
<br><br>
<% }else{%>

<%

        // lock shopping list
        tmpHttpSessObj.writeLockCurrentShoppingList();

        if (tmpHttpSessObj.getCurrentUser() == null) {//test for new session
            System.out.println("Current user is null.");
            // release write lock on list
            tmpHttpSessObj.releaseLockCurrentShoppingList();
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }

        if (tmpHttpSessObj.getCurrentShoppingCartList() == null) {
            // release write lock on list
            tmpHttpSessObj.releaseLockCurrentShoppingList();
            pageContext.forward(response.encodeURL("./ViewLists.jsp"));
            return;
        }

        String tmpString = null;

        if ((tmpString = request.getParameter("cancel")) != null) {
            //pageContext.forward(request.getHeader("referer"));
            // release write lock on list
            tmpHttpSessObj.releaseLockCurrentShoppingList();
            pageContext.forward(response.encodeURL("./ViewContainerContentsList.jsp"));
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
    function isNumeric(strString,add)
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
    function validate(){
        // error if not enough volume for all samples
        var lowest = document.getElementById('lowestVol').value.valueOf();
        var change = document.getElementById('volChange').value.valueOf();
        var action = document.getElementById('volAction').value.valueOf();
        if(change == null || change == ""){
            alert ("Error: No value entered.");
            return false;
        }
         if(!isNumeric(change,".")){
            alert ("Error: Value entered must be numeric.");
            return false;
        }
        //if all unknown volumes
        if(lowest == null || lowest == "unknown"){
            return confirm("Warning: None of the samples in this list have known volumes. "
                    +"Unknown volumes will only be affetced by 'set to' changes. Would you like to continue?");
        }
        if(action =="subtract" && change*1>lowest*1){
            alert("Error: Not all sample volumes are sufficient to support this decrease."
                        +" The maximum amount you can decrease by is "+lowest+" ul.");
            return false;
        }
        // warn if there are unknown volumes
        var unknown = document.getElementById('unknownVolumes').value.valueOf();
        if(unknown == "true"){
            return confirm("Warning: not all samples in this list have known volumes."
            +" Unknown volumes will only be affetced by 'set to' changes. Would you like to continue?");
        }
        return true;
    }
</script>
<%
    System.out.println("in editBC.jsp 1.2");
        
        // if form has been submitted, perform actions
        if ((tmpString = request.getParameter("editVolumes")) != null) {
            
        // get values from form
        String volAction = request.getParameter("volAction");
        String volChange = request.getParameter("volChange");
        String commentChange = request.getParameter("commentChange");
        int messageNum = 0;
        String listID =tmpHttpSessObj.getCurrentShoppingList().getListID().toString();

System.out.println("in editBC.jsp 1.3");

            if (volChange != null && volChange.equals("")) { volChange = null; }
            else if (volChange != null){  try {
System.out.println("in editBC.jsp 2.2a, volume="+volChange);
               Double.parseDouble(volChange);}
                    catch (NumberFormatException e) { messageNum = 16;}}
            
            if (messageNum == 0) {
                messageNum = tmpHttpSessObj.editBulkListContainerContents(volChange, volAction,commentChange, listID);

            }
            if (messageNum == 0) {
                tmpHttpSessObj.releaseLockCurrentShoppingList();
                pageContext.forward(response.encodeURL("ViewContainerContentsList.jsp"));
                return;
            } else if (messageNum == 1) {
                pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                return;
            }

System.out.println("in editBC.jsp 2.3, messagenum = "+messageNum);
} else { // from view list page
    System.out.println("in editBC.jsp 1.5");

        String listID =tmpHttpSessObj.getCurrentShoppingList().getListID().toString();
        String listName =tmpHttpSessObj.getCurrentShoppingList().getListName();
        int numContents = tmpHttpSessObj.getAllContentsListCount();

        // get the most that can safely be subtracted from ALL contents in list
        // aka get the lowest volume in list of contents
        Double lowestVol = tmpHttpSessObj.getListLowestVolume(listID);
        String lowestVolStr = (lowestVol == null)? "unknown":lowestVol.toString();
        boolean unknownVolumes = tmpHttpSessObj.getListUnknownVolume(listID);

System.out.println("in editBC.jsp 1.6");
%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./ListVolumeUpdate.jsp")%>">
    <a class="largest">Update the Volumes of All Samples of List: <em><%=listName%></em></a>
    <br>

    <fieldset><br>
<table frame="box" border="0">
<tr>
    <td align="left" valign="middle">
    Update the volumes of <%=numContents%> samples: &nbsp;
        <select name="volAction" id="volAction">
            <option value="subtract" selected>Decrease by</option>
            <option value="set" >Set to</option>
            <option value="add">Increase by</option>
        </select>
        &nbsp;
        <input type="text" size="5" name="volChange" id="volChange"/>
        uL<br><br>
    </td>
</tr>
<tr><td colspan="4">The lowest volume in your list of samples is: <%=lowestVolStr%> ul.</td></tr>
<%if(unknownVolumes){%>
<tr><td style="font-weight:bold" colspan="4">Warning: your list contains samples with unknown volumes.</td></tr>
<%}%>
<tr>
    <td class="dialh" align="left" colspan="4"><%--volume:--%>
    Update the comments of <%=numContents%> samples:
     (text will be <em>added</em> to the current comments) &nbsp;
        <input type="text" size="20" name="commentChange" id="commentChange"/>
        <br><br>
    </td>
</tr>
</table>
</fieldset>

    <input type="hidden" name="lowestVol" id="lowestVol" value="<%=lowestVolStr%>"/>
    <input type="hidden" name="unknownVolumes" id="unknownVolumes" value="<%=unknownVolumes%>"/>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="editVolumes" value="  OK  " onclick="return validate();"/>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%
    }}
%>
<%@include file="Footer.jsp"%>



