<%-- 
    Document   : AddControlWellsToContainerE
    Created on : Oct 14, 2009, 10:31:10 AM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">

</script>

<%

// if they have specified a control layout to load, get it
String layoutToLoad = null;
if((tmpString = request.getParameter("loadLayout"))!=null){
    layoutToLoad = request.getParameter("layoutToLoad");
}
//otherwise get default control layout for plate
else{
    layoutToLoad = "In-House Plates Oct2009";
}
// get control locations
plater.setControlLayoutJustUsed(layoutToLoad);
HashMap wellHash = plater.getControlWellHash(layoutToLoad);

%>

<fieldset>
    <table id="inputTable" cellpadding="4">
        <!--tr><td>If you have no controls wells to add,
        <input type="submit" class="button" name="skip" value="Skip Controls"></td></tr-->
        <tr><td>If you would like to use an existing control layout, select it
        <select name="layoutToLoad">
            <%=tmpHttpSessObj.getObjectPrompterField(ControlLayoutWell.class, layoutToLoad, "layoutName", false)%>
        </select><input type="submit" name="loadLayout" value="Load" class="button">
        </td></tr>
        <tr><td>If you would like to define a new control layout, go to &nbsp;
        <input type="submit"  class="button" name="custom" value="Custom Controls"></td></tr>
        <tr><td>
            </fieldset>
            <fieldset>
                <a class="larger">Layout: <%=layoutToLoad%></a><br><br>
        <table class="plate"  style="width:900px">
                    <%for (int j = 1; j <= 8; j++) {%>
                    <tr style="height:40px;"><%for (int k = 1; k <= 12; k++) {
                    // if well is control, put in dropdown
                    if (wellHash.containsKey("row" + j + "col" + k)) {
                        // if well has set value
                        if (!(wellHash.get("row" + j + "col" + k)).toString().equals("none")) {%>
                            <td><select name="controlID">
                                <%=tmpHttpSessObj.getObjectPrompterUniqueField(Control.class, ((Control) wellHash.get("row" + j + "col" + k)).getId().toString(), "description", false)%>
                            </select>
                        <input type="hidden" name="row" value="<%=j%>">
                        <input type="hidden" name="column" value="<%=k%>"></td>
                        <%} //if no set value
                            else {%>
                        <td>
                            <select name="controlID">
                                <%=tmpHttpSessObj.getObjectPrompterUniqueField(Control.class, null, "description", false)%>
                            </select>
                        <input type="hidden" name="row" value="<%=j%>">
                        <input type="hidden" name="column" value="<%=k%>">
                        </td>
                        <%}%>
                        <%} // if not a control well leave blank
                        else {%>
                        <td><a class="smaller"><%=Util.numToLetter(String.valueOf(j))+(k)%></a></td>
                        <%}}%>
                    </tr>
                    <%}%>
                </table>
                <table width="100%">
                <tr><td align="right" >
<input type="submit" name="next" value=" Next "/></td></tr></table>
</fieldset>
</td></tr></table>


