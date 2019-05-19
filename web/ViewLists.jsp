<%-- 
    Document   : ViewLists
    Created on : Jul 22, 2009, 10:35:46 AM
    Author     : tvanrossum
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%String lapasId = "ViewLists";%>
<%@include file="Header.jsp"%>

<script type='text/javascript' src='jquery.simplemodal-1.3.5.min.js'></script>
<script  type="text/javascript" language="javascript">
    function gotoList(view){
        if (checkSubmitFlag()) {
            if(view == "contents"){
            window.location.assign(
            "<%=response.encodeURL("./ViewContainerContentsList.jsp")%>");
            } else if(view == "samples"){
            window.location.assign(
            "<%=response.encodeURL("./ViewSamplesList.jsp")%>");
            } else if(view == "subjects"){
            window.location.assign(
            "<%=response.encodeURL("./ViewSubjectsList.jsp")%>");
            } else if(view == "containers"){
            window.location.assign(
            "<%=response.encodeURL("./ViewContainersList.jsp")%>");
            }
        }
    }
    function gotoCreateListFromFile(){
        if (checkSubmitFlag()) {
            window.location.assign(
            "<%=response.encodeURL("./ChooseFileToLoad.jsp")%>");
        }
    }
    function promptForListName(){
    
        var listName = document.getElementById("listName")
        var name = prompt("Please create a list by entering a descriptive name.");
        if (name != null && name!=""){ listName.value = name; return true;}
        else if (name == null) {alert("List not created."); return false;}
        else if (name == "") {alert("Descriptive name required for list creation.\nList not created. ");return false;}

    }
    function newList(){
        return promptForListName();
    }
    function setToLoad(listID){
        document.getElementById('loadPickedResultID').value = listID;
    }
</script>

<%
if (tmpHttpSessObj.getCurrentUser() == null) {//test for new session
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }
// tmpHttpSessObj.populateFakeSamplesList();
        String tmpString = null;
        String listID = request.getParameter("listID");
        String newListName = request.getParameter("listName");
        int messageNum = 0;
        System.out.println("listID="+listID+" listName="+newListName+" messageNum="+messageNum);

  String listName = (tmpHttpSessObj.getCurrentShoppingList()!=null)?
      tmpHttpSessObj.getCurrentShoppingList().getListName(): "--none--";
                if(response.isCommitted())
                    System.out.println("Line 71");

        // if selected a list from admin-only buttons
        if ((tmpString = request.getParameter("viewContentList")) != null ||
                (tmpString = request.getParameter("viewSampleList")) != null||
                (tmpString = request.getParameter("viewContainerList")) != null){
            
                // load selected list by setting current to it
                tmpHttpSessObj.setCurrentShoppingCartList(new ShoppingCartList(tmpHttpSessObj, (new Long(listID))));

                if ((tmpString = request.getParameter("viewContentList")) != null) {
                    // fwd to content list
                    pageContext.forward(response.encodeURL("./ViewContainerContentsList.jsp"));
                    return;
                }
                if ((tmpString = request.getParameter("viewContainerList")) != null) {
                    // fwd to content list
                    pageContext.forward(response.encodeURL("./ViewContainersList.jsp"));
                    return;
                }
                if ((tmpString = request.getParameter("viewSampleList")) != null) {
                    // fwd to content list
                    pageContext.forward(response.encodeURL("./ViewSamplesList.jsp"));
                    return;
                }
            }

// first time loading the page, before any actions done
else{
      
      // if closed a list
      if ((tmpString = request.getParameter("closeList")) != null &&
                !(tmpString = request.getParameter("closeList")).equals("")){
            listID=null;
            // release write lock on list
            tmpHttpSessObj.releaseLockCurrentShoppingList();
            tmpHttpSessObj.setCurrentShoppingCartList(null);
        // have to reload page to get header to change
           response.sendRedirect("./ViewLists.jsp");
           return;
      }
        // if selected a list from search results
        if ((tmpString = request.getParameter("loadPickedResult")) != null &&
                !tmpString.equals("")){
            listID=request.getParameter("loadPickedResultID");
            System.out.println("loading picked result: "+listID);
            // load selected list by setting current to it
                tmpHttpSessObj.setCurrentShoppingCartList(new ShoppingCartList(tmpHttpSessObj, (new Long(listID))));
        // have to reload page to get header to change
           response.sendRedirect("./ViewLists.jsp");
           return;
        }

        // if creating a new list
        if ((tmpString = request.getParameter("createList")) != null &&
                !(tmpString = request.getParameter("createList")).equals("")) {
                //create list
                System.out.println("newListName="+newListName);

                // if list is a new list and name was entered, then start by making the new list
                if (newListName != null && !newListName.equals("")) {
                    messageNum = tmpHttpSessObj.addShoppingList(newListName,null);
                    // load new list as current
                    if (messageNum == 0) {
                        System.out.println("newListName="+newListName+messageNum);
                        tmpHttpSessObj.setCurrentShoppingCartList(new ShoppingCartList(tmpHttpSessObj, newListName));
}
                       if(messageNum != 0){%>
      <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>

        <%    }
                    }

            }

        // if loaded a list from drop down
        else if ((tmpString = request.getParameter("loadList")) != null &&
                (tmpString = request.getParameter("loadList")) != ""){
            // load selected list by setting current to it
                tmpHttpSessObj.setCurrentShoppingCartList(new ShoppingCartList(tmpHttpSessObj, (new Long(listID))));
        // have to reload page to get header to change
                if(response.isCommitted())
                    System.out.println("Before Send Redirect");
           response.sendRedirect("./ViewLists.jsp");
           return;
        }


   listName = (tmpHttpSessObj.getCurrentShoppingList()!=null)?
      tmpHttpSessObj.getCurrentShoppingList().getListName(): "--none--";
  
%>

<form name="fForm" method="POST"  onsubmit="return checkSubmitFlag()"
      action="<%= response.encodeURL("./ViewLists.jsp")%>">

<%@include file="ViewListsE.jsp"%>

<%
// if renaming a list
   if ((tmpString = request.getParameter("renameList")) != null &&
           !(tmpString = request.getParameter("renameList")).equals("")){
       ShoppingCartList currShopCart = tmpHttpSessObj.getCurrentShoppingCartList();
       User currUser = tmpHttpSessObj.getCurrentUser();
       ShoppingList currShopList = (currShopCart!=null)? currShopCart.getShoppingList():null;
       User shopListCreator = (currShopList != null)? currShopList.getCreator():null;
       String renameTo = request.getParameter("renameTo");
       if (shopListCreator != null && currUser != null && shopListCreator.getId().equals(currUser.getId())){
            messageNum = tmpHttpSessObj.updateShoppingList(renameTo, currShopCart, currUser);
                                   if(messageNum != 0){%>
      <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>

        <%    }
            if (messageNum == 0){
            response.sendRedirect("./ViewLists.jsp");
            return;
            }
       }
       else{
           String properUser = "Placeholder Name";
           if (shopListCreator!=null){
               properUser = shopListCreator.getVisibleName();
           }
           String errorType = "Error: Insufficient Permissions";
           String errorMessage = "This list was made by " + properUser + ". Please get " + properUser + " to rename the list.";
           String errorString = Util.writeModalErrorMessages(errorType, errorMessage);
           %>
            <script type="text/javascript">
                    $(window).load(function(){
                        $.modal("<%=errorString%>", {overlayClose: true});
                    });
            </script>
       <%
       }
   }
// if deleting a list
   if ((tmpString = request.getParameter("deleteList")) != null &&
           !(tmpString = request.getParameter("deleteList")).equals("")){
       ShoppingCartList currShopCart = tmpHttpSessObj.getCurrentShoppingCartList();
       User currUser = tmpHttpSessObj.getCurrentUser();
       ShoppingList currShopList = (currShopCart!=null)? currShopCart.getShoppingList():null;
       User shopListCreator = (currShopList != null)? currShopList.getCreator():null;
       if (shopListCreator != null && currUser != null && shopListCreator.getId().equals(currUser.getId())){
            currShopCart.clearContainerContents();
            currShopCart.clearContainers();
            currShopCart.clearSamples();
            currShopCart.clearSubject();
            tmpHttpSessObj.deleteShoppingList(currShopList.getId().toString());
            response.sendRedirect("./ViewLists.jsp");
            return;
       }
       else{
           String properUser = "Placeholder Name";
           if (shopListCreator!=null){
               properUser = shopListCreator.getVisibleName();
           }
           String errorType = "Error: Insufficient Permissions";
           String errorMessage = "This list was made by " + properUser + ". Please get " + properUser + " to delete the list.";
           String errorString = Util.writeModalErrorMessages(errorType, errorMessage);
           %>
            <script type="text/javascript">
                    $(window).load(function(){
                        $.modal("<%=errorString%>", {overlayClose: true});
                    });
            </script>
       <%
       }
   }
// if searched for list
   if ((tmpString = request.getParameter("searchLists")) != null  &&
                (tmpString = request.getParameter("searchLists")) != ""){
        System.out.println("search");
            // field searched by
            String field = request.getParameter("field");
            // search term
            String term= request.getParameter("term");
            String exact = request.getParameter("exact");
            if(exact == null) exact = "false";
        System.out.println("field="+field+" term="+term+" exact="+exact.toString());

            ArrayList results = (ArrayList) tmpHttpSessObj.getShoppingListResults(field, term, new Boolean(exact));
           %>

<!--search results-->
<a class="largest" ><br>Search Results for "<%=term%>"</a>
<br><br>
    <input type="hidden" name="loadPickedResultID" id="loadPickedResultID" value=""/>
<table class="results">
        <tr bgcolor="#b3cfff">
            <th>List Name</th><th>Created By</th><th>Date Created</th><th>Modified By</th><th>Date Modified</th><th></th>
        </tr>
        <%if (results != null && !results.isEmpty()){
        Iterator iter = results.listIterator();
        while(iter.hasNext()){
            ShoppingList list = (ShoppingList) iter.next();
            Long rowListID = list.getId();
            String createDate = (new java.sql.Date(list.getCreateDate().getTime())).toString();
            String modifDate = (list.getModifDate()!=null)?(new java.sql.Date(list.getModifDate().getTime())).toString():"";
          %>
         <tr valign="middle">
             <td><%=list.getListName()%></td>
             <td><%=list.getCreator().getVisibleName()%></td>
             <td><%=createDate%></td>
             <td><%=(list.getModifier()!=null)?list.getModifier().getVisibleName():""%></td>
             <td><%=modifDate%></td>
             <td><input type="submit" class="button" onclick="setToLoad(<%=rowListID%>)" name="loadPickedResult" value="Load"/></td>
        </tr>
        <%} }%>
    </table>
        <%}%>
</form>
<% }%>
    <br/>
    <br/>
    <br/>
    <br/>
<%@include file="Footer.jsp"%>



