<%-- 
    Document   : EditColumns
    Created on : Aug 10, 2009, 3:24:03 PM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%String lapasId = "EditColumns";%>
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
            String source = request.getParameter("source");
            String subjId = request.getParameter("subjId");
            String contId= request.getParameter("contId");
            String listContainerID = request.getParameter("listContainerID");
               System.out.println("cont:"+contId+" subj:"+subjId+" source:"+source+" listContainerID"+listContainerID);
        
        if ((tmpAction = request.getParameter("cancel")) != null) {

               System.out.println("editcolumns cancel");
                if(source!=null && source.equals("list")){
                    pageContext.forward(response.encodeURL("./ViewContainerContentsList.jsp"));
                    return;
                }
                if(source!=null && source.equals("volumeEdit")){
                    response.sendRedirect("./AddListContentsToContainer.jsp" + "?listContainerID="+listContainerID);
                    //pageContext.forward(response.encodeURL("./AddListContentsToContainer.jsp") + "?listContainerID="+listContainerID);
                    return;
                }
               System.out.println(" cancel cont:"+contId+" subj"+subjId);
               System.out.println(((contId==null || contId.equals("") || contId.equals("null"))?"":"?contId="+contId) +
                 ((subjId==null || subjId.equals("") || subjId.equals("null"))?"":"?subjId="+subjId) );
 /*               pageContext.forward(response.encodeURL("./ViewContainerContents.jsp")+
                 ((contId==null || contId.equals("") || contId.equals("null"))?"":"?contId="+contId) +
                 ((subjId==null || subjId.equals("") || subjId.equals("null"))?"":"?subjId="+subjId) );
   */
                //pageContext.forward(response.encodeURL("./ViewContainerContents.jsp")+"?subjId="+subjId);
                pageContext.forward(response.encodeURL("./ViewContainerContents.jsp"));
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

       function toggleSelect(el){
       var end = (el.id.length)-5
    var prefix=el.id.substring(0,end)
    if(document.all){document.all[prefix+'Num'].disabled=el.checked?false:true}
    else{document.getElementById(prefix+'Num').disabled=el.checked?false:true}
   }

</script>
<%
        int messageNum = 0;

        ViewContainerContentManager vccm = new ViewContainerContentManager(tmpHttpSessObj);

        String ccmd_barcodeCheck;
        String ccmd_barcodeNum;
        String ccmd_amplificationDateCheck;
        String ccmd_amplificationDateNum;
        String ccmd_dateCollectedCheck;
        String ccmd_dateCollectedNum;
        String ccmd_columnCheck;
        String ccmd_columnNum;
        String ccmd_commentsCheck;
        String ccmd_commentsNum;
        String ccmd_concentrationCheck;
        String ccmd_concentrationNum;
        String ccmd_containerCheck;
        String ccmd_containerNum;
        String ccmd_containerTypeCheck;
        String ccmd_containerTypeNum;
        String ccmd_dilutionCheck;
        String ccmd_dilutionNum;
        String ccmd_extractionDateCheck;
        String ccmd_extractionDateNum;
        String ccmd_freezerCheck;
        String ccmd_freezerNum;
        String ccmd_isStockCheck;
        String ccmd_isStockNum;
        String ccmd_locationCheck;
        String ccmd_locationNum;
        String ccmd_materialTypeCheck;
        String ccmd_materialTypeNum;
        String ccmd_modifDateCheck;
        String ccmd_modifDateNum;
        String ccmd_parentCheck;
        String ccmd_parentNum;
        String ccmd_rowCheck;
        String ccmd_rowNum;
        String ccmd_sampleCheck;
        String ccmd_sampleNum;
        String ccmd_sampleTypeCheck;
        String ccmd_sampleTypeNum;
        String ccmd_shelfCheck;
        String ccmd_shelfNum;
        String ccmd_contaminatedCheck;
        String ccmd_contaminatedNum;
        String ccmd_volumeCheck;
        String ccmd_volumeNum;


        if ((tmpAction = request.getParameter("updateColumns")) != null) {

            ccmd_amplificationDateCheck = request.getParameter("ccmd_amplificationDateCheck");
            ccmd_amplificationDateNum = request.getParameter("ccmd_amplificationDateNum");
            ccmd_barcodeCheck = request.getParameter("ccmd_barcodeCheck");
            ccmd_barcodeNum = request.getParameter("ccmd_barcodeNum");
            ccmd_dateCollectedCheck = request.getParameter("ccmd_dateCollectedCheck");
            ccmd_dateCollectedNum = request.getParameter("ccmd_dateCollectedNum");
            ccmd_columnCheck = request.getParameter("ccmd_columnCheck");
            ccmd_columnNum = request.getParameter("ccmd_columnNum");
            ccmd_commentsCheck = request.getParameter("ccmd_commentsCheck");
            ccmd_commentsNum = request.getParameter("ccmd_commentsNum");
            ccmd_concentrationCheck = request.getParameter("ccmd_concentrationCheck");
            ccmd_concentrationNum = request.getParameter("ccmd_concentrationNum");
            ccmd_containerCheck = request.getParameter("ccmd_containerCheck");
            ccmd_containerNum = request.getParameter("ccmd_containerNum");
            ccmd_containerTypeCheck = request.getParameter("ccmd_containerTypeCheck");
            ccmd_containerTypeNum = request.getParameter("ccmd_containerTypeNum");
            ccmd_dilutionCheck = request.getParameter("ccmd_dilutionCheck");
            ccmd_dilutionNum = request.getParameter("ccmd_dilutionNum");
            ccmd_extractionDateCheck = request.getParameter("ccmd_extractionDateCheck");
            ccmd_extractionDateNum = request.getParameter("ccmd_extractionDateNum");
            ccmd_freezerCheck = request.getParameter("ccmd_freezerCheck");
            ccmd_freezerNum = request.getParameter("ccmd_freezerNum");
            ccmd_isStockCheck = request.getParameter("ccmd_isStockCheck");
            ccmd_isStockNum = request.getParameter("ccmd_isStockNum");
            ccmd_materialTypeCheck = request.getParameter("ccmd_materialTypeCheck");
            ccmd_materialTypeNum = request.getParameter("ccmd_materialTypeNum");
            ccmd_locationCheck = request.getParameter("ccmd_locationCheck");
            ccmd_locationNum = request.getParameter("ccmd_locationNum");
            ccmd_modifDateCheck = request.getParameter("ccmd_modifDateCheck");
            ccmd_modifDateNum = request.getParameter("ccmd_modifDateNum");
            ccmd_parentCheck = request.getParameter("ccmd_parentCheck");
            ccmd_parentNum = request.getParameter("ccmd_parentNum");
            ccmd_rowCheck = request.getParameter("ccmd_rowCheck");
            ccmd_rowNum = request.getParameter("ccmd_rowNum");
            ccmd_sampleCheck = request.getParameter("ccmd_sampleCheck");
            ccmd_sampleNum = request.getParameter("ccmd_sampleNum");
            ccmd_sampleTypeCheck = request.getParameter("ccmd_sampleTypeCheck");
            ccmd_sampleTypeNum = request.getParameter("ccmd_sampleTypeNum");
            ccmd_shelfCheck = request.getParameter("ccmd_shelfCheck");
            ccmd_shelfNum = request.getParameter("ccmd_shelfNum");
            ccmd_contaminatedCheck = request.getParameter("ccmd_contaminatedCheck");
            ccmd_contaminatedNum = request.getParameter("ccmd_contaminatedNum");
            ccmd_volumeCheck = request.getParameter("ccmd_volumeCheck");
            ccmd_volumeNum = request.getParameter("ccmd_volumeNum");

            messageNum = vccm.processMetaDataRow("barcode", ccmd_barcodeNum, ccmd_barcodeCheck);
            messageNum = vccm.processMetaDataRow("amplificationDate", ccmd_amplificationDateNum, ccmd_amplificationDateCheck);
            messageNum = vccm.processMetaDataRow("sample.dateCollected", ccmd_dateCollectedNum, ccmd_dateCollectedCheck);
            messageNum = vccm.processMetaDataRow("column", ccmd_columnNum, ccmd_columnCheck);
            messageNum = vccm.processMetaDataRow("comments", ccmd_commentsNum, ccmd_commentsCheck);
            messageNum = vccm.processMetaDataRow("concentration", ccmd_concentrationNum, ccmd_concentrationCheck);
            messageNum = vccm.processMetaDataRow("container", ccmd_containerNum, ccmd_containerCheck);
            messageNum = vccm.processMetaDataRow("container.containerType", ccmd_containerTypeNum, ccmd_containerTypeCheck);
            messageNum = vccm.processMetaDataRow("dilution", ccmd_dilutionNum, ccmd_dilutionCheck);
            messageNum = vccm.processMetaDataRow("sample.extractionDate", ccmd_extractionDateNum, ccmd_extractionDateCheck);
            messageNum = vccm.processMetaDataRow("container.freezer", ccmd_freezerNum, ccmd_freezerCheck);
            messageNum = vccm.processMetaDataRow("container.isStock", ccmd_isStockNum, ccmd_isStockCheck);
            messageNum = vccm.processMetaDataRow("container.location", ccmd_locationNum, ccmd_locationCheck);
            messageNum = vccm.processMetaDataRow("materialType", ccmd_materialTypeNum, ccmd_materialTypeCheck);
            messageNum = vccm.processMetaDataRow("modifDate", ccmd_modifDateNum, ccmd_modifDateCheck);
            messageNum = vccm.processMetaDataRow("parent", ccmd_parentNum, ccmd_parentCheck);
            messageNum = vccm.processMetaDataRow("row", ccmd_rowNum, ccmd_rowCheck);
            messageNum = vccm.processMetaDataRow("sample", ccmd_sampleNum, ccmd_sampleCheck);
            messageNum = vccm.processMetaDataRow("sample.sampleType", ccmd_sampleTypeNum, ccmd_sampleTypeCheck);
            messageNum = vccm.processMetaDataRow("container.shelf", ccmd_shelfNum, ccmd_shelfCheck);
            messageNum = vccm.processMetaDataRow("contaminated", ccmd_contaminatedNum, ccmd_contaminatedCheck);
            messageNum = vccm.processMetaDataRow("volume", ccmd_volumeNum, ccmd_volumeCheck);

            messageNum = vccm.refreshContainerContentMetaData();

            if (messageNum == 0) {
                if(source!=null && source.equals("list")){
                    pageContext.forward(response.encodeURL("./ViewContainerContentsList.jsp"));
                    return;
                }
                if(source!=null && source.equals("volumeEdit")){
                    listContainerID = request.getParameter("listContainerID");
                    pageContext.forward(response.encodeURL("./AddListContentsToContainer.jsp") + "?listContainerID="+listContainerID);
                    return;
                }
                               System.out.println(((contId==null || contId.equals("") || contId.equals("null"))?"":"?contId="+contId) +
                 ((subjId==null || subjId.equals("") || subjId.equals("null"))?"":"?subjId="+subjId) );
 /*               pageContext.forward(response.encodeURL("./ViewContainerContents.jsp")+
                 ((contId==null || contId.equals("") || contId.equals("null"))?"":"?contId="+contId) +
                 ((subjId==null || subjId.equals("") || subjId.equals("null"))?"":"?subjId="+subjId) );
   */
                //pageContext.forward(response.encodeURL("./ViewContainerContents.jsp")+"?subjId="+subjId);
                pageContext.forward(response.encodeURL("./ViewContainerContents.jsp"));
                return;
            } else if (messageNum == -1) {
                pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                return;
            }

%>
<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./EditColumns.jsp")%>">
          <input type="hidden" name="subjId" value="<%=subjId%>">
          <input type="hidden" name="contId" value="<%=contId%>">
          <input type="hidden" name="listContainerID" value="<%=listContainerID%>">
          <input type="hidden" name="source" value="<%=source%>">
    <a class="largest">Customize Your View</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <%@include file="EditColumnsE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateColumns" value="Update"/>
                </td>
                <td>
                    <input type="submit" name="resetView" value="Set to Default"/>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else if ((tmpAction = request.getParameter("resetView")) != null) {

    // reset metadata to defaults for user's group
    messageNum = vccm.resetMetaDataRows();
    
    messageNum = vccm.refreshContainerContentMetaData();

    if (messageNum == 0) {

                tmpAction = request.getParameter("source");
                if (tmpAction != null && tmpAction.equals("list")) {
                    pageContext.forward(response.encodeURL("./ViewContainerContentsList.jsp"));
                    return;
                }
                  System.out.println(((contId==null || contId.equals("") || contId.equals("null"))?"":"?contId="+contId) +
                 ((subjId==null || subjId.equals("") || subjId.equals("null"))?"":"?subjId="+subjId) );
 /*               pageContext.forward(response.encodeURL("./ViewContainerContents.jsp")+
                 ((contId==null || contId.equals("") || contId.equals("null"))?"":"?contId="+contId) +
                 ((subjId==null || subjId.equals("") || subjId.equals("null"))?"":"?subjId="+subjId) );
   */
                //pageContext.forward(response.encodeURL("./ViewContainerContents.jsp")+"?subjId="+subjId);

                  if(source!=null && source.equals("list")){
                    pageContext.forward(response.encodeURL("./ViewContainerContentsList.jsp"));
                    return;
                }
                if(source!=null && source.equals("volumeEdit")){
                    listContainerID = request.getParameter("listContainerID");
                    pageContext.forward(response.encodeURL("./AddListContentsToContainer.jsp") + "?listContainerID="+listContainerID);
                    return;
                }
                pageContext.forward(response.encodeURL("./ViewContainerContents.jsp"));
                return;
    }
    if (messageNum == -1) {
        pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
        return;
    }

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./EditColumns.jsp")%>">
          <input type="hidden" name="subjId" value="<%=subjId%>">
          <input type="hidden" name="contId" value="<%=contId%>">
          <input type="hidden" name="listContainerID" value="<%=listContainerID%>">
          <input type="hidden" name="source" value="<%=source%>">
    <a class="largest">Customize Your View</a>
    <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <%@include file="EditColumnsE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateColumns" value="Update"/>
                </td>
                <td>
                    <input type="submit" name="resetView" value="Reset to Default"/>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%}  else //from View page
{
%>
<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./EditColumns.jsp")%>">
          <input type="hidden" name="subjId" value="<%=subjId%>">
          <input type="hidden" name="contId" value="<%=contId%>">
          <input type="hidden" name="listContainerID" value="<%=listContainerID%>">
          <input type="hidden" name="source" value="<%=source%>">
    <a class="largest">Customise Your View</a>
    <br>
    <a class="error">&nbsp;</a>
    <%@include file="EditColumnsE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" name="updateColumns" value="Update"/>
                </td>
                <td>
                    <input type="submit" name="resetView" value="Reset to Default"/>
                </td>
                <td>
                    <input type="submit" name="cancel" value="Cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%}%>
<%@include file="Footer.jsp"%>

