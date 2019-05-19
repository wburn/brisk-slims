<%-- 
    Document   : HowManyPlates
    Created on : Oct 7, 2009, 3:22:25 PM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%String lapasId = "HowManyPlates";%>
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
        tmpHttpSessObj.clearInvalidField();

        // lock shopping list
        int lockRes = tmpHttpSessObj.writeLockCurrentShoppingList();
        System.out.println("lockRes: "+ lockRes);

//test new session
        if (tmpHttpSessObj.getCurrentUser() == null) {

                // release write lock on list
                tmpHttpSessObj.releaseLockCurrentShoppingList();
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }

        if(tmpHttpSessObj.getCurrentShoppingCartList()==null ||
                tmpHttpSessObj.getCurrentShoppingCartList().getContainerContentsList()==null){
            
                // release write lock on list
                tmpHttpSessObj.releaseLockCurrentShoppingList();
            pageContext.forward(response.encodeURL("./ViewLists.jsp"));
            return;
        }
                Plater plater = null;
        // if first time entering plating process, initialize plater
        if(tmpHttpSessObj.getCurrentPlater()==null){
            plater = new Plater(tmpHttpSessObj);
            tmpHttpSessObj.setCurrentPlater(plater);
        }else{
            plater = tmpHttpSessObj.getCurrentPlater();
        }
        
        String tmpAction;
        if ((tmpAction = request.getParameter("cancel")) != null) {
            System.out.println("IN CANCEL");
            plater.plated = 0;
            plater.numberOfPlatesMade = 0;
            plater.controlsJustAdded = 0;
            tmpHttpSessObj.setCurrentPlater(null);
            // release write lock on list
            tmpHttpSessObj.releaseLockCurrentShoppingList();
            response.sendRedirect("./ViewContainerContentsList.jsp");
            //pageContext.forward(response.encodeURL("./ViewContentsLists.jsp"));
            return;
          }
        if ((tmpAction = request.getParameter("finish")) != null) {
            plater.plated = 0;
            plater.numberOfPlatesMade = 0;
            plater.controlsJustAdded = 0;
            tmpHttpSessObj.setCurrentPlater(null);
            // release write lock on list
            tmpHttpSessObj.releaseLockCurrentShoppingList();
            response.sendRedirect("./PlatingSummary.jsp");
            //pageContext.forward(response.encodeURL("ViewContentsLists.jsp"));
            return;
          }
          if ((tmpAction = request.getParameter("next")) != null) {
              String[] phenotypeIDs = request.getParameterValues("phenotype");
              // if considering case/control status
              if(phenotypeIDs != null && phenotypeIDs.length>0){
                plater.prepareCasesAndControls(phenotypeIDs);
                plater.consideringCaseControl = true;
              }else{
                plater.consideringCaseControl = false;
              }
            response.sendRedirect("./Add96WellPlates.jsp");
            return;
          }
        int total = tmpHttpSessObj.getCurrentShoppingCartList().getContainerContentsList().size();
        int contentsDone = plater.plated;
        int platesDone = plater.numberOfPlatesMade;
        int platesStillNeeded = ((total-contentsDone)%96>0)? ((total-contentsDone)/96)+1:((total-contentsDone)/96);
        System.out.println("total"+total+" contentsdone"+contentsDone+" platesDone"+platesDone+" platesstillneeded"+platesStillNeeded);

//does system have phenotype data?
boolean hasPhenotypeData = plater.hasPhenotypeData();

                                    %>
<script language="JavaScript">
function validate(){
    // if considering case/control, at least one phenotype must be selected
    var cccT = document.getElementById('cccT');
    if(cccT.checked
        && document.platingForm.phenotype.value.valueOf() == ''){
        alert("Error: Input missing. If you have selected to consider cases and controls, you must select at least one phenotype.");
        return false;
    }
}
function controlPhenoOptions(){
        var cccT = document.getElementById('cccT');
    if(cccT.checked){
        document.getElementById("phenotypeIn").disabled = false;
    }else{
        document.getElementById("phenotypeIn").disabled = true;
    }

}
</script>

<html>
    <form method="POST" name="platingForm"
          action="<%=response.encodeURL("./HowManyPlates.jsp")%>">
        <table class="navigate">
            <tr>
                <td class="left" align="left" colspan="2">
                    <a class="largest">96 Well Plate Creation from Samples List</a></td>
                </tr>
            <tr>
                <td><br>
                    You have <%=total%> samples in your list 
                    and <%=(total-contentsDone)%> left to plate.
                    You will need at least <%=platesStillNeeded%> more plate(s).
                </td></tr>
                <tr><td>
            <%if(hasPhenotypeData==true && plater.consideringCaseControl==null){%>
                    <table><tr><td colspan="2">
                    Would you like to consider phenotyped-based case/control status in plating?
                    </td></tr><tr><td colspan="2">
                    <input type="radio" name="considerCaseControl" id="cccF" value="false" onclick="controlPhenoOptions()" checked> No</td></tr>
                    <tr><td colspan="2"><input type="radio" name="considerCaseControl" id="cccT" value="true" onclick="controlPhenoOptions()"> Yes (cases and controls will be distributed evenly across plates)</td></tr>
                    <tr><td width="20px">&nbsp;</td><td>
                        Select one or more phenotype to base case/control distinction on.<br>
                        Hold down 'Ctrl' to select or deselect multiple samples.
                        <br><%=plater.getSelectorPrompterForPhenotype()%>
                     </table>
                    <%}else if (plater.consideringCaseControl == true){%>
                    You are considering case/control status in plate layout.
                   <% }else if(hasPhenotypeData==true) {%>
                    You are not considering case/control status in plate layout.
                   <% }%>
                </td></tr>
            <tr><td>
                <%for(int i = 0;i<(platesDone+platesStillNeeded);i++){%>
                <input type="submit" class="button" name="next" value="Create Plate <%=(i+1)%>"
                <%=(platesDone!=i)?"disabled":""%> onclick="return validate()">
                <%}%>
                <%if(total==contentsDone){%>
                <input type="submit" class="button" value="Finish" name="finish">
                <%}%>
            </tr>
                <%if(platesDone==0){%>
            <tr><td>
                <input type="submit" class="button" value="Cancel" name="cancel"></td>
            </tr><%}%>
        </table>
    </form>
    <br/>
    <br/>
    <br/>
    <br/>
</html>

<%}%>
<%@include file="Footer.jsp"%>
