<%String lapasId = "DefineFilter";%>
<%@include file="Header.jsp"%>
<%
  tmpHttpSessObj.clearInvalidField();
  //test new session
  if (tmpHttpSessObj.getCurrentUser() == null) {
    pageContext.forward(response.encodeURL("./LogIn.jsp"));
    return;
  }
  String tmpAction;
  // set up filter and go to page requested
  if ((tmpAction = request.getParameter("viewResults")) != null) {
      tmpAction = request.getParameter("toView");
      String toSearch = request.getParameter("toSearch");
      if(toSearch.equals("database")){
      tmpHttpSessObj.cancelSearch();
      tmpHttpSessObj.setFilter(request);
      if(tmpAction.equals("subjects")){
           tmpHttpSessObj.getViewSubjectManager().clearValues();
    pageContext.forward(response.encodeURL("./ViewSubjects.jsp"));}
      if(tmpAction.equals("samples")){
           tmpHttpSessObj.getViewSampleManager().clearValues();
    pageContext.forward(response.encodeURL("./ViewSamples.jsp"));}
      if(tmpAction.equals("contents")){
           tmpHttpSessObj.getViewContainerContentManager().clearValues();
    pageContext.forward(response.encodeURL("./ViewContainerContents.jsp"));}
      if(tmpAction.equals("containers")){
           tmpHttpSessObj.getViewContainerManager().clearValues();
    pageContext.forward(response.encodeURL("./ViewContainers.jsp"));}
      if(tmpAction.equals("sampDocs")){
          tmpHttpSessObj.getViewContainerManager().clearValues();
      pageContext.forward(response.encodeURL("./ViewSampleDocumentss.jsp"));}
      } else if (toSearch.equals("list")){
      tmpHttpSessObj.cancelSearchList();
      tmpHttpSessObj.setFilterList(request);
      if(tmpAction.equals("subjects")){
    pageContext.forward(response.encodeURL("./ViewSubjectsList.jsp"));}
      if(tmpAction.equals("contents")){
    pageContext.forward(response.encodeURL("./ViewContainerContentsList.jsp"));}
      if(tmpAction.equals("samples")){
    pageContext.forward(response.encodeURL("./ViewSamplesList.jsp"));}
      if(tmpAction.equals("containers")){
    pageContext.forward(response.encodeURL("./ViewContainersList.jsp"));}
      }
      return;
      }
  
  // resetting filter
  if (request.getParameter("cancelFilter") != null) {
    tmpHttpSessObj.cancelSearch();
    tmpHttpSessObj.cancelFilter();
  }
  MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
%>
<script  type="text/javascript" language="javascript">
  function setFocus(){}
  function clearFilter(){
    document.fForm.contShipDateayear.value="";
    document.fForm.contShipDateamonth.value="";
    document.fForm.contShipDateaday.value="";
    document.fForm.contShipDatebyear.value="";
    document.fForm.contShipDatebmonth.value="";
    document.fForm.contShipDatebday.value="";
    document.fForm.subjName.value="";
    document.fForm.subjMother.value="";
    document.fForm.subjFather.value="";
    document.fForm.subjGender.selectedIndex=-1;
    document.fForm.subjCohort.selectedIndex=-1;
    document.fForm.subjFamilyID.selectedIndex=-1;
    document.fForm.subjHasConsent.selectedIndex=-1;
    document.fForm.subjEthnicity.selectedIndex=-1;
    document.fForm.samName.value="";
    document.fForm.samTypeDesc.selectedIndex=-1;
    document.fForm.samProcDesc.selectedIndex=-1;
    document.fForm.samValid.selectedIndex=-1;
    document.fForm.contName.value="";
    document.fForm.contType.selectedIndex=-1;
    document.fForm.contFreez.selectedIndex=-1;
    document.fForm.contDisc.selectedIndex=-1;
    document.fForm.contShipTo.selectedIndex=-1;
    document.fForm.contShipDate.selectedIndex=-1;
    document.fForm.contShipOut.selectedIndex=-1;
    document.fForm.docFilename.selectedIndex=-1;
    document.fForm.docComments.selectedIndex=-1;
    document.fFrom.contBarcode.selectedIndex=-1;
    return false;

  }
</script>
<form method="POST" name="fForm" action="<%=response.encodeURL("./DefineFilter.jsp")%>">
  <table class="view_table">
      <tr>
        <td class="largest" align="left">Define Power Search Filter </td>
        <td align="right">
          <input type="submit" name="cancelFilter" value="Clear Search"/>
        </td>
      </tr>
      </table>
      <table width="100%">  <tr>
            <td class="instructions">
                Limit the visibility of all data for subjects, samples,
                containers and their contents (wells/tubes) using the fields below. <br>
                To deselect rows or select multiple values use &lt;Ctrl&gt; + mouse click.<br>
                If no values for a field are selected, the viewable data will not be
                resticted by that field.

                </td>

            </tr> 
  </table>
          
        
  <table width="100%">
      <tr>
        <th class="subHeader" colspan="6">Container Fields</th>
      </tr>
      <tr>
          <td><table style="padding-left:2em;padding-right:2em"><tr>
          <th>Container Name</th></tr><tr><td><%=tmpHttpSessObj.getSearchInputForField(Container.class,"contName")%>
          <%=tmpHttpSessObj.getSearchInputForFieldExactBox(Container.class,"contName")%> &nbsp;&nbsp;
          <%=tmpHttpSessObj.getSearchInputForFieldNotBox(Container.class,"contName")%></td>
          </tr>
          <tr>
          <tr>
          <th>Container Alias</th></tr><tr><td><%=tmpHttpSessObj.getSearchInputForField(Container.class,"contAlias")%>
          <%=tmpHttpSessObj.getSearchInputForFieldExactBox(Container.class,"contAlias")%> &nbsp;&nbsp;
          <%=tmpHttpSessObj.getSearchInputForFieldNotBox(Container.class,"contAlias")%></td>
          </tr>
          <tr>
          <tr>
          <th>Plate Maker's Initials</th></tr><tr><td><%=tmpHttpSessObj.getSearchInputForField(Container.class,"contMaker")%>
          <%=tmpHttpSessObj.getSearchInputForFieldExactBox(Container.class,"contMaker")%> &nbsp;&nbsp;
          <%=tmpHttpSessObj.getSearchInputForFieldNotBox(Container.class,"contMaker")%></td>
          </tr>
          
        </table></td><td>
        <table style="padding-right:2em"><tr>
          <th>Container Type</th></tr><tr>
        <%=tmpHttpSessObj.getSearchPrompterForField(ContainerType.class,"description","contType")%>
          </tr>
          <tr>
        <th>Freezer</th></tr><tr><%=tmpHttpSessObj.getSearchPrompterForField(Freezer.class,"description","contFreez")%>
          </tr>
        </table></td><td>
        <table style="padding-right:2em">
        <tr>
        <th><nobr>Shipping Status</nobr></th></tr><tr><%=tmpHttpSessObj.getSearchPrompterForField(Shipment.class,"shipAction","contShipOut")%>
        </tr>
        <tr>
        <th>Discarded?</th>
        </tr><tr>
          <%=tmpHttpSessObj.getSearchPrompterForField(Container.class,"discarded","contDisc")%>
          </tr>
          </table></td><td>
        <table style="padding-right:2em;width:220px">
            <tr>
        <th colspan="2">Shipped To</th></tr><tr><%=tmpHttpSessObj.getSearchPrompterForField(ShippedTo.class,"description","contShipTo")%>
          </tr><tr>
        <th>Date Shipped</th></tr><tr><%=tmpHttpSessObj.getSearchDatePrompter("contShipDate")%>
        </tr>
        </table></td><td>
        <table><tr>
        <th>Comments</th></tr><tr>
          <td><%=tmpHttpSessObj.getSearchInputForField(Container.class,"contComments")%><br>
              <%=tmpHttpSessObj.getSearchInputForFieldExactBox(Container.class,"contComments")%>&nbsp;&nbsp;
                  <%=tmpHttpSessObj.getSearchInputForFieldNotBox(Container.class,"contComments")%></td>
          </tr>
          <tr>
        <th>Barcode</th></tr><tr>
          <td><%=tmpHttpSessObj.getSearchInputForField(Container.class,"contBarcode")%><br>
              <%=tmpHttpSessObj.getSearchInputForFieldExactBox(Container.class,"contBarcode")%>&nbsp;&nbsp;
                  <%=tmpHttpSessObj.getSearchInputForFieldNotBox(Container.class,"contBarcode")%></td>
          </tr>
          </table></td>
          <td width="100%">&nbsp;</td>
          </tr>
  </table>
  <br><br>
  <table border="0" frame="box" width="100%">
      <tr >
        <th class="subHeader" colspan="6">Sample Fields</th>
      </tr></table>
  <table border="0" frame="box" >
      <tr valign="top"><td>
          <table border="0" frame="box" style="padding-left:2em">
              <tr><th valign="top">Sample ID</th>
              <td >
                  <%=tmpHttpSessObj.getSearchInputForField(Sample.class,"samName")%>
                    <br>(CAPPS yr 1 samples<br>have \"_yr1\" suffix)<br><br></td>
             <td>
                  <%=tmpHttpSessObj.getSearchInputForFieldExactBox(Sample.class,"samName")%><br>
                      <%=tmpHttpSessObj.getSearchInputForFieldNotBox(Sample.class,"samName")%>
              
                  <br></td></tr>
                  <tr><th valign="top">Barcode</th>
              <td >
                  <%=tmpHttpSessObj.getSearchInputForField(ContainerContent.class,"ccBarcode")%></td>
             <td>
                  <%=tmpHttpSessObj.getSearchInputForFieldExactBox(ContainerContent.class,"ccBarcode")%><br>
                      <%=tmpHttpSessObj.getSearchInputForFieldNotBox(ContainerContent.class,"ccBarcode")%>

                  <br></td></tr>
              </table><table style="padding-left:2em">
              <tr><th valign="middle">Volume</th>
              <td  colspan="2"><select name="volRel">
                            <option value=">=" <%=(tmpHttpSessObj.inFilter("volRel", ">="))?"selected":""%>>at least</option>
                            <option value="<=" <%=(tmpHttpSessObj.inFilter("volRel", "<="))?"selected":""%>>at most</option>
                            <option value="="<%=(tmpHttpSessObj.inFilter("volRel", "="))?"selected":""%> >exactly</option>
                         </select>
                    <%=tmpHttpSessObj.getSearchInputForFieldNum(ContainerContent.class,"vol")%> ul</td></tr>
           <tr><th valign="middle">Concentration</th>
                    <td colspan="2"><select name="concenRel">
            <option value=">=" <%=(tmpHttpSessObj.inFilter("concenRel", ">="))?"selected":""%>>at least</option>
            <option value="<=" <%=(tmpHttpSessObj.inFilter("concenRel", "<="))?"selected":""%>>at most</option>
            <option value="=" <%=(tmpHttpSessObj.inFilter("concenRel", "="))?"selected":""%>>exactly</option>
        </select>
                    <%=tmpHttpSessObj.getSearchInputForFieldNum(ContainerContent.class,"concen")%> ng/ul</td></tr>
          <tr><th valign="middle">Amount of DNA</th>
                    <td colspan="2"><select name="dnaRel">
            <option value=">=" <%=(tmpHttpSessObj.inFilter("dnaRel", ">="))?"selected":""%>>at least</option>
            <option value="<=" <%=(tmpHttpSessObj.inFilter("dnaRel", "<="))?"selected":""%>>at most</option>
            <option value="=" <%=(tmpHttpSessObj.inFilter("dnaRel", "="))?"selected":""%>>exactly</option>
        </select>
                    <%=tmpHttpSessObj.getSearchInputForFieldNum(ContainerContent.class,"dna")%> ng</td></tr>
          <tr><td colspan="4">
              <table><tr><td>
              <table >
          <tr><th>Material Type</th></tr><tr>
          <%=tmpHttpSessObj.getSearchPrompterForField(MaterialType.class,"description","ccMaterialType")%>
          </tr> </table></td><td><table><tr>
        <th>Is Stock?</th></tr><tr>
          <%=tmpHttpSessObj.getSearchPrompterForField(Container.class,"isStock","contIsStock")%>
          </tr> </table></td><td><table><tr>
          <th>Contanimated</th></tr><tr>
          <%=tmpHttpSessObj.getSearchPrompterForField(ContainerContent.class,"contaminated","ccContaminated")%>
            </tr>
          </table>
              </td></tr></table>
          </td>
          </table>
       </td><td>
       <table border="0" frame="box" >
           <tr valign="top"><th valign="top">Sample Type</th></tr><tr>
               <%=tmpHttpSessObj.getSearchPrompterForField(SampleType.class,"description","samTypeDesc")%>
           </tr>
       </table>
       </td><td>
       <table style="width:220px">
           <tr><th>Collection Date</th></tr><tr><%=tmpHttpSessObj.getSearchDatePrompter("sampleCollDate")%></tr>
        <tr><th>Extraction Date</th></tr><tr><%=tmpHttpSessObj.getSearchDatePrompter("sampleExtracDate")%></tr>
       </table>
      </td>
      
        <td>
        <table><tr>
        <th>Comments</th></tr><tr>
          <td><%=tmpHttpSessObj.getSearchInputForField(ContainerContent.class,"contentComments")%><br>
              <%=tmpHttpSessObj.getSearchInputForFieldExactBox(ContainerContent.class,"contentComments")%>&nbsp;&nbsp;
                  <%=tmpHttpSessObj.getSearchInputForFieldNotBox(ContainerContent.class,"contentComments")%></td>
          </tr>
          <tr>
          <th>Sample Documents Filename</th></tr><tr>
          <td><%=tmpHttpSessObj.getSearchInputForField(SampleDocuments.class,"docFilename")%><br>
              <%=tmpHttpSessObj.getSearchInputForFieldExactBox(SampleDocuments.class,"docFilename")%>&nbsp;&nbsp;
                  <%=tmpHttpSessObj.getSearchInputForFieldNotBox(SampleDocuments.class,"docFilename")%></td>
          </tr>
                    <tr>
          <th>Sample Documents Comments</th></tr><tr>
          <td><%=tmpHttpSessObj.getSearchInputForField(SampleDocuments.class,"docComments")%><br>
              <%=tmpHttpSessObj.getSearchInputForFieldExactBox(SampleDocuments.class,"docComments")%>&nbsp;&nbsp;
                  <%=tmpHttpSessObj.getSearchInputForFieldNotBox(SampleDocuments.class,"docComments")%></td>
          </tr>
          </table></td>
          </tr>

  </table>
  <br><br>
  <table width="100%">
      <tr>
        <th class="subHeader" colspan="6">Subject Fields</th>
      </tr>
      </table>
  <table>
      <tr><td>
          <table style="padding-left:2em" border="0" frame="box">
      <tr><th>Cohort</th></tr><tr>
        <%=tmpHttpSessObj.getSearchPrompterForField(Cohort.class,"description","subjCohort")%>
     </tr>
</table></td><td>
        <table style="padding-left:2em" >
            <tr><th>Subject ID</th><td valign="middle"><%=tmpHttpSessObj.getSearchInputForField(Subject.class,"subjName")%>
        <br><%=tmpHttpSessObj.getSearchInputForFieldExactBox(Subject.class,"subjName")%> &nbsp;&nbsp;
        <%=tmpHttpSessObj.getSearchInputForFieldNotBox(Subject.class,"subjName")%></td>
        </tr>
        
        <tr><th >Mother ID</th><td valign="middle"><%=tmpHttpSessObj.getSearchInputForField(Subject.class,"subjMother")%>
        <br><%=tmpHttpSessObj.getSearchInputForFieldExactBox(Subject.class,"subjMother")%>&nbsp;&nbsp;
        <%=tmpHttpSessObj.getSearchInputForFieldNotBox(Subject.class,"subjMother")%></td>
        </tr>
        
        <tr><th  >Father ID</th><td valign="middle"><%=tmpHttpSessObj.getSearchInputForField(Subject.class,"subjFather")%>
        <br><%=tmpHttpSessObj.getSearchInputForFieldExactBox(Subject.class,"subjFather")%>&nbsp;&nbsp;
        <%=tmpHttpSessObj.getSearchInputForFieldNotBox(Subject.class,"subjFather")%></td>
        </tr>
        
        <tr><th >Family ID</th><td valign="middle"><%=tmpHttpSessObj.getSearchInputForField(Subject.class,"subjFamily")%>
        <br><%=tmpHttpSessObj.getSearchInputForFieldExactBox(Subject.class,"subjFamily")%>&nbsp;&nbsp;
        <%=tmpHttpSessObj.getSearchInputForFieldNotBox(Subject.class,"subjFamily")%></td>
        </tr>

        </table>
        </td>
        <td>
            <table><tr>
            <th>Ethnicity</th></tr><tr><%=tmpHttpSessObj.getSearchPrompterForField(Ethnicity.class,"ethnicity","subjEthnicity")%>
          
            </tr>
            </table>
        </td>
        <td>
            <table><tr>
                <th>Gender</th></tr><tr><%=tmpHttpSessObj.getSearchPrompterForField(Subject.class,"gender","subjGender")%>
            </tr><tr><th>Has Consent</th></tr><tr><%=tmpHttpSessObj.getSearchPrompterForField(Subject.class,"hasConsent","subjHasConsent")%>
            </tr>
            </table>
        </td>

          <td>
        <table><tr>
        <th>Comments</th></tr><tr>
          <td><%=tmpHttpSessObj.getSearchInputForField(Subject.class,"subjComments")%><br>
              <%=tmpHttpSessObj.getSearchInputForFieldExactBox(Subject.class,"subjComments")%>&nbsp;&nbsp;
                  <%=tmpHttpSessObj.getSearchInputForFieldNotBox(Subject.class,"subjComments")%></td>
          </tr>
          </table></td>
          <td >&nbsp;</td>
          </tr>
  </table>

  <table class="actionBlue" frame="box" border="0" style="border:0px;width:auto;padding:3px;font-size:14px">
      <tr>
        <td align="left" valign="middle">
            Apply search to: </td><td>
            <input type="radio" name="toSearch" value="database" checked>Database
         <input type="radio" name="toSearch" value="list"
                <%=(tmpHttpSessObj.getCurrentShoppingList()==null)?"disabled":""%> >
                    <a  <%=(tmpHttpSessObj.getCurrentShoppingList()==null)?"style=\"color:grey\"":""%>>Current List</a>
      </td></tr><tr><td align="right">
            View:</td><td>&nbsp;
            <select id="toView" name="toView">
                <%if(tmpHttpSessObj.isTechUser()){%>
                <option value="contents">Contents</option>
                <option value="samples">DBSamples</option>
                <option value="contents">Contents</option>
            <%} else{%>
                <option value="contents">Samples</option>
                <%}%>
                <option value="containers">Containers</option>
                <option value="subjects">Subjects</option>
                <option value="sampDocs">Files</option>
        </select>
            <input type="submit" name="viewResults" value=" Go ">
        </td>
      </tr>
  </table>
<table width="100%"><tr>
        <td align="right">
  <input type="submit" name="cancelFilter" value="Clear Search"/>
        </td>
</tr></table>
</form>
</html><%@include file="Footer.jsp"%>
