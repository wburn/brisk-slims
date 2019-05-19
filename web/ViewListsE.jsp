<%-- 
    Document   : ViewListsE.jsp
    Created on : Sep 30, 2009, 9:53:42 AM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<table class="navigate" >
    <tr>
        <td class="left" align="left" colspan="6">
            <a class="largest">List Manager</a></td>
    </tr>
</table><table>
    <tr>
        <td colspan="6" class="instructions" style="width:auto">
            Here you may create a new list or load one that was previously
            made either by you or someone else.
            To load, either select a list from the drop down menu
            (sorted by creation date)
            or search for a list by it's name,
            it's creator's name or the name of user who last modified it. Once a list is
            loaded you can view its contents using the "View List: Subjects", "View List: Samples" or
            "View List: Containers" options from the "Lists" top menu option.
        </td>
    </tr>
</table>
<table cellpadding="4px" style="padding-left:20px;">
    <tr><td>
            <table cellpadding="4px" width="100%"
                   style="background-color:#B4CFEA;"><tr>
                    <td class="actionBlue" style="font-size:14px;" >
                        Active List: <u><%=listName%></u>
                        <% if (tmpHttpSessObj.getCurrentShoppingCartList() != null) {%>
                        <input type="submit" class="button" name="closeList" value="Close">
                        <% }%>
                    </td>
                    <td >&nbsp;</td>
                    <td align="right">
                        <input type="button" class="button" style="visibility:hidden"> &nbsp;

                        <%if (tmpHttpSessObj.getCurrentShoppingList() != null) {%>
                        <input type="button" class="button" value="View Subjects" onclick="gotoList('subjects')"> &nbsp;
                        <input type="button" class="button" value="View Samples"  onclick="gotoList('contents')">&nbsp;
                        <input type="button" class="button" value="View Containers" onclick="gotoList('containers')">&nbsp;
                        <%}%></td></tr>
            </table>
        </td></tr><tr><td>
            <table cellpadding="4px" style="padding-left:20px" >
                <tr>
                    <td valign="middle" ><a style="font-weight:bold">Create:</a></td>
                    <td >
                        <input type="hidden" name="listName" id="listName">
                        <input type="submit" class="button" onclick="return newList()" name="createList" value="Create Empty List"/>
                        <input type="submit" class="button" onclick="return gotoCreateListFromFile()" name="createList" value="Create List From File"/>
                    </td>
                </tr>
                <tr>
                    <td valign="middle"  style="font-weight:bold;">
                        Browse:</td><td valign="middle" colspan="3">
                        <select name="listID" id="listID">
                            <%=tmpHttpSessObj.getShoppingListPrompter((tmpHttpSessObj.getCurrentShoppingList() == null) ? null : tmpHttpSessObj.getCurrentShoppingList().getListID().toString())%>
                        </select>
                        <!--button onclick="gotoList()" title="Load List" type="button">Go</button-->
                        <input type="submit" class="button" value="Load" name="loadList"></td>
                        <%if (tmpHttpSessObj.isTechUser()) {%>
                    <td><input type="submit" class="button" value="Load List: Contents" name="viewContentList">
                        <input type="submit" class="button" value="Load List: Containers" name="viewContainerList">
                        <input type="submit" class="button" value="Load List: Samples" name="viewSampleList"></td>
                        <%}%>
                </tr>
                <tr>
                    <td valign="middle"  style="font-weight:bold">Search:</td>
                    <td> by
                        <select name="field">
                            <option value="listName">List Name</option>
                            <option value="creator.visibleName">Created By</option>
                            <option value="modifier.visibleName">Last Modified By</option>
                        </select>
                        &nbsp; for <input type="text" style="width:150px" name="term"></td>
                    <td><input type="checkbox" name="exact" value="true">Exact? &nbsp;

                    <input class="button" type="submit" value="View Results" name="searchLists"><td>
                </tr>

                <%if (tmpHttpSessObj.getCurrentShoppingList() != null) {%>
                <tr>
                    <td valign="middle" ><a style="font-weight:bold">Modify:</a></td>
                    <td >
                        <input type="button" class="button" value="Rename List" onclick="$('#renameList').modal()">&nbsp;
                        <input type="button" class="button" value="Delete List" onclick="$('#deleteList').modal()">&nbsp;
                    </td>
                </tr>
                <%}%>
            </table>
        </td></tr></table>

<!--            Modal display box for RENAME function-->
<div id="renameList" style="display:none">
    <div style="width: 100%; text-align:right">
        <a href='#' title='Close' class='simplemodal-close' style='font-size:23px;'><b>x</b></a>
    </div>
    <h1 style=" margin-top: 0; margin-bottom: 0"><img height="42px" width="42px" alt="Rename Indicator" src="./img/pencil.png" style="vertical-align:middle;"/>Rename</h1>
    <div id="renameListOptions">
        Rename to:<input type="text" name="renameTo"/><br>
        <input type="submit" name="renameList" value="Ok" />
        <input type="button" class="simplemodal-close" value="Cancel"/>
    </div>
</div>

<!--            Modal display box for DELETE function-->
<div id="deleteList" style="display:none">
    <div style="width: 100%; text-align:right">
        <a href='#' title='Close' class='simplemodal-close' style='font-size:23px;'><b>x</b></a>
    </div>
    <h1 style=" margin-top: 0; margin-bottom: 0"><img height="45px" width="42px" alt="Delete Warning" src="./img/recycle.png" style="vertical-align:middle;"/>Delete</h1>
    <div id="deleteListOptions">
        Are you sure you want to delete this list? This operation is IRREVERSIBLE. <i>Be patient, this process can take a few seconds.</i><br>
        <input type="submit" name="deleteList" value="Submit" />
        <input type="button" class="simplemodal-close" value="Cancel"/>
    </div>
</div>
