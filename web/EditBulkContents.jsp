<%String lapasId = "EditBulkContents";%>
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
<% }%>


<%
System.out.println("in editBC.jsp 1.1");
        String tmpAction;
        String selfId;
        tmpHttpSessObj.clearInvalidField();
//test new session
        response.setHeader("Cache-Control", "no-store");
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }
        if ((tmpAction = request.getParameter("cancel")) != null) {
            pageContext.forward(response.encodeURL("./ViewContainers.jsp"));
            return;
        }
        System.out.println("in editBC.jsp 1.2");
%>
<script  type="text/javascript" language="javascript">
    function setFocus(){
        field = document.getElementById("kluda");
        if (field) field.focus();
        else
            document.fForm.genId.focus();
    }

function changeCheck( elem1, elem2, checkId){

       var c=document.getElementById(checkId);
       var z=document.getElementsByName(elem1);
       var x=document.getElementsByName(elem2);

   x[0].disabled = !c.checked;
   if (!c.checked) x[0].style.backgroundColor="#E5EAF0";
   else x[0].style.backgroundColor = "#FFFFFF";

   z[0].disabled = !c.checked;
   if (!c.checked) z[0].style.backgroundColor="#E5EAF0";
   else z[0].style.backgroundColor = "#FFFFFF";
   
   check = document.fForm.check; mycheck = true
   if (check) for (i=0; i<check.length;i++)
                if (check[i].checked){ mycheck = false; break;}
   document.fForm.updateAliquot.disabled = mycheck;
 }
</script>
<%
System.out.println("in editBC.jsp 1.3");
        int messageNum = 0;
        String containerID;
        Container currCont = tmpHttpSessObj.getCurrentContainer();
        String doVolume;
        String volume;
        String volumeAction;
        String doConcen;
        String concentration;
        String concentrationAction;
        String doComments;
        String comments;

        if ((tmpAction = request.getParameter("editBulkContainerContents")) != null) {
System.out.println("in editBC.jsp 2.1");
            containerID = request.getParameter("contId");
            doVolume = request.getParameter("doVolume");
            volume = request.getParameter("volume");
            volumeAction = request.getParameter("volumeAction");
            doConcen = request.getParameter("doConcen");
            concentration = request.getParameter("concentration");
            concentrationAction = request.getParameter("concentrationAction");
            doComments = request.getParameter("doComments");
            comments = request.getParameter("comments");


            if (volume != null && volume.equals("")) { volume = null; }
            else if (volume != null){  try {
System.out.println("in editBC.jsp 2.2a, volume="+volume);
               Double.parseDouble(volume);}
                    catch (NumberFormatException e) { messageNum = 16;}}
            if (concentration != null && concentration.equals("")) { concentration = null; }
            else if (concentration != null) {  try {
System.out.println("in editBC.jsp 2.2b, concen="+concentration);
Double.parseDouble(concentration);}
                    catch (NumberFormatException e) { messageNum = 16;}}
System.out.println("in editBC.jsp 2.2c, messagenum = "+messageNum);
            if (messageNum == 0) {
                messageNum = tmpHttpSessObj.editBulkContainerContents(containerID, doVolume, volume, volumeAction, doConcen, concentration, concentrationAction,doComments,comments);

            }
            if (messageNum == 0) {
                pageContext.forward(response.encodeURL("./ViewContainerContents.jsp?contId=")+containerID);
                return;
            } else if (messageNum == 1) {
                pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                return;
            }

System.out.println("in editBC.jsp 2.3, messagenum = "+messageNum);
%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./EditBulkContents.jsp")%>">
    <a class="largest">Bulk Edit Container Contents</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <%containerID = request.getParameter("contId");

System.out.println("in editBC.jsp 2.4, messagenum = "+messageNum);%>
    <input type="hidden" name="contId" value="<%=containerID%>"/>
    <%@include file="EditBulkContentsE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                <input type="submit" name="editBulkContainerContents" value="Update"/>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else if ((tmpAction = request.getParameter("contId")) != null) { // from view page
    System.out.println("in editBC.jsp 1.5");
    tmpHttpSessObj.setCurrentContainer(tmpAction);
        currCont = tmpHttpSessObj.getCurrentContainer();
        containerID = tmpAction;
        String contName = currCont.getVisibleName();
        volume = null;
        concentration = null;
System.out.println("in editBC.jsp 1.6");
%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./EditBulkContents.jsp")%>">
    <a class="largest">Edit All Samples of Container <em><%=contName%></em></a>
    <br>
    <a class="error">&nbsp;</a>
    <%@include file="EditBulkContentsE.jsp"%>
    <input type="hidden" name="contId" value="<%=containerID%>"/>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="editBulkContainerContents" value="  Adjust  "/>
                </td>
                <td>
                    <input type='button' onclick='history.go(-1);' name="back" value="Cancel" />
                    <!--input type="submit" name="cancel" value="Cancel" size="10"/-->
                </td>
            </tr>
        </thead>
    </table>
</form>
<%
    }
%>
<%@include file="Footer.jsp"%>

