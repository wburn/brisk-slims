<%-- 
    Document   : CheckFile
    Created on : Oct 2, 2009, 1:27:21 PM
    Author     : tvanrossum
--%>

<%String lapasId = "CheckFile";%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@include file="Header.jsp"%>
<%@page import="icapture.com.FileUploading"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.FileItemIterator"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="org.apache.commons.fileio.*"%>


<script language="JavaScript">
    function goBack(){
        alert("in go back");
        pageContext.forward(response.encodeURL("./ChooseFileToLoad.jsp"));
        return;
    }
    function checkListName(){

        var name = document.getElementById("newListName").value;
        if (name != null && name!=""){ return true;}
        else if (name == null || name == "")
        {alert("Descriptive name required for list creation.\nList not created. ");
            return false;}

    }
</script>
<%

        tmpHttpSessObj.clearInvalidField();
//test new session
        if (tmpHttpSessObj.getCurrentUser() == null) {

                // release write lock on list
                tmpHttpSessObj.releaseLockCurrentShoppingList();
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }
        String tmpString = null;
        int messageNum = 0;

        if((tmpString = request.getParameter("create"))!=null){

            String newListName = request.getParameter("newListName");
            String inputType = request.getParameter("inputType");

        // try to make shopping list
        messageNum = tmpHttpSessObj.addShoppingList(newListName,tmpHttpSessObj.getCurrentUser());
        
        // load new list as current
        if (messageNum == 0) {
              System.out.println("newListName="+newListName+messageNum);
              tmpHttpSessObj.setCurrentShoppingCartList(new ShoppingCartList(tmpHttpSessObj, newListName));
              messageNum = tmpHttpSessObj.loadIDsToCurrentList(tmpHttpSessObj.getIdsToLoad(), inputType);
        }
        if(messageNum==0){
            // release write lock on shopping list
            tmpHttpSessObj.releaseLockCurrentShoppingList();
            
            pageContext.forward(response.encodeURL("./ViewLists.jsp"));
            return;
        }
        else{
            System.out.println("Error in list creation.");
    
%>
<form method="POST" name="searchForm"
          action="<%=response.encodeURL("./CheckFile.jsp")%>"
          onsubmit="return checkListName();">

          <input type="hidden" name="inputType" value="<%=inputType%>">
          <input type="hidden" name="create" value="retry">
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>

    <table class="navigate">
        <tr>
            <td class="left" align="left" colspan="2"><a class="largest">Create List From File</a></td>
        </tr>
    </table>
    <table>
        <tr><td>Your list name ("<%=newListName%>") already exists, please try again.</td></tr>
        <tr><td>What would you like to name your new list?&nbsp;&nbsp;
        <input type="text" size="30" id="newListName" name="newListName"/></td>
        <td><input type="submit" name="create" value="Create and Fill"/></td></tr>
    </table>
    <br><br>
</form>
<%}} else  {

        // Check that we have a file upload request
        boolean isMultipart = FileUpload.isMultipartContent(request);

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload();

        ArrayList<String> badFormat = null;
        ArrayList<String> notInDB = null;
        ArrayList<String> moreThanOneMatch = null;
        ArrayList<Long> addTolist = null;
        String type;

        FileItemIterator iter = upload.getItemIterator(request);
        try {
            FileUploading up = new FileUploading();
            Object[] arr = up.checkUploadListFile(iter);
            badFormat = (ArrayList) arr[0];
            notInDB = (ArrayList) arr[1];
            moreThanOneMatch = (ArrayList) arr[2];
            addTolist = (ArrayList) arr[3];
            type = (String) arr[4];
            tmpHttpSessObj.setIdsToLoad(addTolist);
        } catch (Exception e) {
            System.out.println("Error in file checking: " + e.getMessage());
            e.printStackTrace();
            pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
            return;
        }

        Iterator bads = null;
%>

<html>
<form method="POST" name="searchForm"
          action="<%=response.encodeURL("./CheckFile.jsp")%>"
          onsubmit="return checkListName();">
          <input type="hidden" name="inputType" value="<%=type%>">
    <table class="navigate">
        <tr>
            <td class="left" align="left" colspan="2"><a class="largest">Create List From File</a></td>
        </tr>
    </table>
    <table style="padding-left:20pt"><tr><td>
        <%if (moreThanOneMatch == null || notInDB == null || badFormat == null || addTolist == null) {%>
        <table style="background-color:#FBFBFB">
            <tr><td>An error occured while parsing your file, please contact a systems administrator.</td></tr>
        </table>
        <%} else {
    if (badFormat.size() > 0) {%>
        <tr><td class="subHeader" align="left" colspan="3">Format Errors</a></th>
        </tr><tr><td colspan="2">The following lines from your file contained an unrecognised format,
        if you proceed they will not be added to your list:</td></tr>
        <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
                <% bads = badFormat.iterator();
            while (bads.hasNext()) {%><tr><td><%=bads.next()%></td></tr>
    <%}%></td></tr></table><%}
    if (notInDB.size() > 0) {%>
    <tr><td class="subHeader" align="left" colspan="3">Items Not Found</a></th>
    </tr><tr><td colspan="2">The following lines from your file describe items that are not in the database,
    if you proceed they will not be added to your list:</td></tr>
    <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
        <% bads = notInDB.iterator();
        while (bads.hasNext()) {
        %><tr><td><%=bads.next()%></td></tr>
    <%}%></table></td></tr><%
    }
    if (moreThanOneMatch.size() > 0) {%>
    <tr><td class="subHeader" align="left" colspan="3">Multiple Items Found</a></th>
    </tr><tr><td colspan="2">The following lines from your file matched more than one item in
    the database, if you proceed all matches will be added to your list:</td></tr>
    <tr><td width="20">&nbsp;</td><td><table style="background-color:#FBFBFB">
        <% bads = moreThanOneMatch.iterator();
    while (bads.hasNext()) {%>
        <tr><td><%=bads.next()%></td></tr>
    <%}%></table></td></tr><%}%>
    </td></tr>
    <%if (moreThanOneMatch.size() == 0 && notInDB.size() == 0 && badFormat.size() == 0) {%>
    <tr><td>No errors were found in your file.</td></tr>
    <%} else {%>
    <tr><td colspan="2"style="font-weight:bold"><br>Would you like to try again?&nbsp;&nbsp;
    <a href="./ChooseFileToLoad.jsp">Go Back</a></td></tr>
    <tr><td colspan="2">If not, continue below.</td></tr>
    <%}%>
    </table><br><br>
    <table>
        <tr><td><%=addTolist.size()%> items will be added to your list.
        <tr><td>What would you like to name your new list?&nbsp;&nbsp;
        <input type="text" size="30" id="newListName" name="newListName"/></td>
        <td><input type="submit" name="create" value="Create and Fill"/> (This may take some time.)</td></tr>
    </table>
    <%}%>
</form>
<br/>
<br/>
<br/>
<br/>
</html>
<%}%>
<%@include file="Footer.jsp"%>