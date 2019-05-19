<%-- 
    Document   : AddControlWellsToContainer
    Created on : Oct 14, 2009, 10:31:04 AM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

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
        
        String tmpString = null;
        Plater plater = tmpHttpSessObj.getCurrentPlater();
        String listContainerID = request.getParameter("listContainerID");

        if (tmpHttpSessObj.getCurrentShoppingCartList() == null) {
            pageContext.forward(response.encodeURL("./ViewLists.jsp"));
            return;
        }
        if ((tmpAction = request.getParameter("cancel")) != null) {
            pageContext.forward(response.encodeURL("./ViewContainerContentsList.jsp"));
            
        }
        if((tmpString = request.getParameter("custom"))!=null){
            pageContext.forward(response.encodeURL("./AddControlWellsToContainerManual.jsp") + "?listContainerID="+listContainerID);
            return;
        }
        if((tmpString = request.getParameter("skip"))!=null){
            plater.controlsJustAdded = 0;
            pageContext.forward(response.encodeURL("./AddListContentsToContainer.jsp") + "?listContainerID="+listContainerID);
            return;
        }

        int messageNum = 0;
%>
<script  type="text/javascript" language="javascript">
    function setFocus(){
        field = document.getElementById("kluda");
        if (field) field.focus();
        else
            document.fForm.genId.focus();
    }

</script>

<%
        messageNum = 0;
        String[] controlID;
        String[] row;
        String[] column;
        String[] volume;
        // if finished choosing controls
        if ((tmpAction = request.getParameter("next")) != null) {
            System.out.println("in next1");
            controlID = request.getParameterValues("controlID");
            System.out.println("in next1.2");
            row = request.getParameterValues("row");
            System.out.println("in next1.3");
            column = request.getParameterValues("column");
            System.out.println("in next1.4");
            volume = request.getParameterValues("volume");
            System.out.println("in next1.5");

            System.out.println("in next2");
            // add the controls to the container
            if (messageNum == 0) {
                messageNum = tmpHttpSessObj.addControls(listContainerID,controlID, row, column,volume);
                System.out.println("in next3");
            }
            // store the number of controls used and fwd to next page
            if (messageNum == 0) {
                System.out.println("in next4");
                plater.controlsJustAdded =(controlID==null)?0: controlID.length;
                pageContext.forward(response.encodeURL("./AddListContentsToContainer.jsp") + "?listContainerID="+listContainerID);
                return;
            } else if (messageNum == 1) {
                pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                return;
            }
%>
<form method="POST" name="fForm" onsubmit="return checkDups()"
      action="<%=response.encodeURL("./AddControlWellsToContainer.jsp?listContainerID="+listContainerID)%>">
    <a class="largest">Add Controls</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <%@include file="AddControlWellsToContainerE.jsp"%>

</form>


<%} else{  //from View page
            controlID = null;
            row = null;
            column = null;
            volume = null;

%>

<form method="POST" name="fForm" onsubmit="return checkDups();"
      action="<%=response.encodeURL("./AddControlWellsToContainer.jsp?listContainerID="+listContainerID)%>">
    <a class="largest">Add Controls</a>
    <br>
    <a class="error">&nbsp;</a>
    <%@include file="AddControlWellsToContainerE.jsp"%>
  
</form>
<%}%>
<%@include file="Footer.jsp"%>

