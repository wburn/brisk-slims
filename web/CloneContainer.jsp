<%-- 
    Document   : CloneContainer
    Created on : Aug 14, 2009, 2:45:54 PM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%String lapasId = "CloneContainer";%>
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

        String tmpAction;
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

      var submitFlag = 0;
  function checkSubmitFlagAndVolume(){
    if (submitFlag++) return false; else return true;
  }

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

        function changeCheckSingle(elem2, checkId){

        var c=document.getElementById(checkId);
        var x=document.getElementsByName(elem2);

        x[0].disabled = !c.checked;
        if (!c.checked) x[0].style.backgroundColor="#E5EAF0";
        else x[0].style.backgroundColor = "#FFFFFF";

        check = document.fForm.check; mycheck = true
        if (check) for (i=0; i<check.length;i++)
            if (check[i].checked){ mycheck = false; break;}
        document.fForm.updateAliquot.disabled = mycheck;
    }

    function checkVolR( min){
        var action= document.getElementById("volumeActionOrig").value;
        var reduction = document.getElementById("volumeOrig").value;
/*        var matType= document.getElementById("materialTypeID").value;

        // IF MAKING WGA
        // insufficient volume
        if(matType == "2" && action == "subtract" && ((min*1 - reduction*1) < 0)){
            return confirm("Warning: one or more samples in the original plate have insufficient volume "+
                     "to complete this action. If performed as is, these samples "+
                     "will have their volumes set to 0 ul. Would you like to continue?");
        }
        // sufficient volume
        else if(matType == "2"){
            return true;
        }
        //IF MAKING GENOMIC
        else if(matType == "1" && action == "subtract" && ((min*1 - reduction*1) < 0)){
        */
       if(action == "subtract" && ((min*1 - reduction*1) < 0)){
            alert("Error: one or more samples in the original plate have insufficient volume "+
                     "to complete this action. This action cannot be performed.");
            return false;
        }
        else{
            return true;
        }
        
    }

    function activate(which){
        if(which=='concen'){
            document.getElementById('concen').disabled=false;
            document.getElementById('dilu').disabled=true;
            document.getElementById('dilu').value='';
        }
        if(which=='dilu'){
            document.getElementById('concen').disabled=true;
            document.getElementById('concen').value='';
            document.getElementById('dilu').disabled=false;
        }
        if(which=='matSel'){
            document.getElementById('materialTypeID').disabled = false;
        }
        if(which=='notMatSel'){
            document.getElementById('materialTypeID').disabled = true;
        }
    }
</script>
<%
        System.out.println("in editBC.jsp 1.3");
        int messageNum = 0;
        String newContID = null;
        String whichVal = null;
        double lowestVol = -1;
        // container fields
        String contId;
        String containerID;
        String containerTypeID;
        String freezerID;
        String shelf;
        String comments;
        String isStock;
        String containerAlias;
        String lot;
        String valid;
        String initials;
        Date dateOnContainer;
        String cyear;
        String cmonth;
        String cday;
        String location;
        String barcode;

        ContainerType containerType = null;
        Freezer freezer = null;

        // container contents fields
        Container currCont;
        String containerName;
        String volumeOrig;
        String volumeActionOrig;
        String volumeNew;
        double volOrig;
        double volNew;
        String volumeActionNew;
        String dilution;
        String concentration;
        double concen;
        String materialTypeID = null;
        MaterialType materialType = null;

        if ((tmpAction = request.getParameter("cloneContainer")) != null) {

            // container
            currCont = tmpHttpSessObj.getCurrentContainer();
            containerName = currCont.getcontainerName();
            contId = request.getParameter("contId");
            System.out.println("In CloneContainer, cloning. contId=" + contId);
            containerName = request.getParameter("containerName");
            containerAlias = request.getParameter("containerAlias");
            location = request.getParameter("location");
            lot = request.getParameter("lot");
            valid = request.getParameter("valid");
            containerTypeID = request.getParameter("containerTypeID");
            barcode = request.getParameter("barcode");
            if (containerTypeID != null && containerTypeID.equals("")) {
                containerTypeID = null;
            }
            freezerID = request.getParameter("freezerID");
            if (freezerID != null && freezerID.equals("")) {
                freezerID = null;
            }
            initials = request.getParameter("initials");
            if (initials != null && initials.equals("")) {
                initials = null;
            }
            shelf = request.getParameter("shelf");
            if (shelf != null && shelf.equals("")) {
                shelf = null;
            } else {
                try {
                    Integer.parseInt(shelf);
                } catch (NumberFormatException e) {
                    messageNum = 10;
                }
            }

            cyear = request.getParameter("cyear");
            cmonth = request.getParameter("cmonth");
            cday = request.getParameter("cday");
            dateOnContainer = tmpHttpSessObj.getDate(cyear, cmonth, cday,
                    "cyear", "cmonth", "cday");



            comments = request.getParameter("comments");
            if (comments != null && comments.equals("")) {
                comments = null;
            }
            if (contId == null || containerName == null || containerTypeID == null || freezerID == null ||
                    shelf == null) {
                messageNum = 9;
            }

            if(lot== null){ lot="DEFAULT";}

            if(request.getParameter("materialTypeChange").equals("no")){
                materialType = null;
            }
            else if(request.getParameter("materialTypeChange").equals("yes")){
                materialTypeID = request.getParameter("materialTypeID");
                materialType = (MaterialType) tmpHttpSessObj.getObjectById(MaterialType.class, materialTypeID);
                
            }
            // the fact that container is being cloned from another means it cannot be stock
            isStock = "0";

        System.out.println("in editBC.jsp 1.4");
            if (messageNum == 0) {
                containerType = (ContainerType) tmpHttpSessObj.getObjectById(ContainerType.class, containerTypeID);
                freezer = (Freezer) tmpHttpSessObj.getObjectById(Freezer.class, freezerID);
            }


            // container contents
            containerID = request.getParameter("contId");

            volumeOrig = request.getParameter("volumeOrig");
            volumeActionOrig = request.getParameter("volumeActionOrig");

            volumeNew = request.getParameter("volumeNew");
            volumeActionNew = request.getParameter("volumeActionNew");

            dilution = request.getParameter("dilution");
            concentration = request.getParameter("concentration");

            if (volumeOrig==null || volumeOrig.equals("") ||
                    volumeNew == null || volumeNew.equals("") ||
                    ((dilution == null || dilution.equals("")) &&
                    (concentration == null || concentration.equals("")))) {
                messageNum = 9;
            }
            volOrig = -1;
            volNew = -1;
            concen = -1;
            if(messageNum == 0){
            try {
                    volOrig = Double.parseDouble(volumeOrig);
                    volNew = Double.parseDouble(volumeNew);
                    if (dilution == null || dilution.equals(""))
                            {concen = Double.parseDouble(concentration);}
                } catch (NumberFormatException e) {
                    messageNum = 16;
                }
            }
            if(messageNum == 0){
            if(volOrig<0 || volNew<0){
                messageNum = 16;
            }
            }
            if(messageNum == 0){
            if(((dilution == null || dilution.equals("")) && concen <0)){
                messageNum = 20;
            }
            }
/*
            if(messageNum == 0){
            System.out.println("BINGO!");
                pageContext.forward(response.encodeURL("./ViewContainers.jsp"));
                return;
            }
*/

            // if genomic and reduction factor is 0 abort and show error
            if(messageNum == 0){
                if ((materialType== null || materialType.getDescription().equalsIgnoreCase("genomic")) &&
                        volumeActionOrig.equals("subtract") && volOrig == 0){
                        messageNum=18;
                    
                }              
            }
            
      
            if (messageNum == 0) {
                // make new container
                String discarded = "0";
                messageNum = tmpHttpSessObj.addContainer(contId, containerName,
                        containerType, freezer, shelf, discarded, comments,
                        isStock, valid,
                        containerAlias,lot,initials,dateOnContainer,location,barcode);

            }
            if (messageNum == 0) {
                // copy contents from old container to new
                messageNum = tmpHttpSessObj.copyContents(contId, containerName,materialType);
                // copy controls from old container to new
                messageNum = tmpHttpSessObj.copyControls(contId, containerName);
                
                //returns the new container ID if successful, -1 otherwise
                newContID = (messageNum == -1)? null: Integer.toString(messageNum);
                messageNum = (messageNum == -1)? -1: 0;
            }


            Boolean setToZero = new Boolean(request.getParameter("setToZero"));
            System.out.println("setToZero = "+setToZero);
            //System.out.println("if(setToZero) = "+(setToZero)?"true":"false");
            
            if (messageNum == 0) {
                // update volumes and dilutions of contents
                // of original (volume only)
                if(setToZero) messageNum = tmpHttpSessObj.editBulkContainerContents(contId, volumeOrig, volumeActionOrig, null, null, true);
                else messageNum = tmpHttpSessObj.editBulkContainerContents(contId, volumeOrig, volumeActionOrig, null, null, false);

                // of new plate
                messageNum = tmpHttpSessObj.editBulkContainerContents(newContID, volumeNew, volumeActionNew, dilution, concentration, false);

            }

            if (messageNum == 0) {
                if(newContID!=null && !newContID.equals("-1")){
                pageContext.forward(response.encodeURL("./ViewContainerContents.jsp?contId="+newContID));
                }else{
                pageContext.forward(response.encodeURL("./ViewContainers.jsp"));
                }
                return;
            } else if (messageNum == 1) {
                pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                return;
            }


            whichVal = request.getParameter("whichVal");

            // for sufficient-volume check
            lowestVol = tmpHttpSessObj.getMinContentVolume(containerID);
            
            System.out.println("in editBC.jsp 2.3, messagenum = " + messageNum);
%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./CloneContainer.jsp")%>">
    <a class="largest">Set New Container Properties for Clone of <em><%=containerName%></em></a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <%containerID = request.getParameter("contId");

            System.out.println("in editBC.jsp 2.4, messagenum = " + messageNum);%>
    <input type="hidden" name="contId" value="<%=containerID%>"/>
    <%@include file="CloneContainerE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td><input type="submit" name="cloneContainer" value="Clone"
                    onclick="return checkVolR(<%=lowestVol%>)"/>
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
            containerID = currCont.getContainerID();
            contId = currCont.getContainerID();
            containerName = currCont.getcontainerName();
            containerTypeID = currCont.getContainerType().getContainerTypeID().toString();
            materialTypeID = null;
            shelf = currCont.getShelf();
            comments = currCont.getComments();
            isStock = currCont.getIsStock();
            containerAlias = currCont.getContainerAlias();
            lot = currCont.getLot();
            valid = currCont.getValid();
            initials = currCont.getInitials();
            dateOnContainer = new Date();
            cyear = Util.getYear(dateOnContainer);
            cmonth = Util.getMonth(dateOnContainer);
            cday = Util.getDay(dateOnContainer);
            location = currCont.getLocation();
            barcode = currCont.getBarcode();



            freezerID = (currCont.getFreezer() != null) ? currCont.getFreezer().getFreezerID().toString() : null;

            containerID = tmpAction;
            containerName = currCont.getVisibleName();
            if(tmpHttpSessObj.containsContaminated(containerID)){
                %>
                    <script  type="text/javascript" language="javascript">
                    alert("Error. This plate cannot be "+
                            "cloned because it contains contaminated samples");
                    window.location.assign( "<%=response.encodeURL("./ViewContainers.jsp")%>");
                     </script>
                <%
            }
            volumeOrig = null;
            volumeNew = null;
            dilution = null;
            concentration = null;
            
            // for sufficient-volume check
            lowestVol = tmpHttpSessObj.getMinContentVolume(containerID);
            System.out.println("lowestVol="+lowestVol);
            
            System.out.println("in editBC.jsp 1.6");
%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./CloneContainer.jsp")%>">
          <input type="hidden" name="setToZero" value="true">
    <a class="largest">Set New Container Properties for Clone of <em><%=containerName%></em></a>
    <br>
    <a class="error">&nbsp;</a>
    <%@include file="CloneContainerE.jsp"%>
    <input type="hidden" name="contId" value="<%=containerID%>"/>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="cloneContainer" value="Clone"
                    onclick="return checkVolR(<%=lowestVol%>)"/>
                    <%} else {%>
                    <%=tmpHttpSessObj.getMyMessage(6)%>      <%}%>
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

