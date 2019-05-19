<%-- 
    Document   : ListTrimTool
    Created on : Nov 18, 2009, 12:57:45 PM
    Author     : tvanrossum
    lets user either keep or discard entries that fit defined criteria
    either replaces existing list or 'saves list as'
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%String lapasId = "ListTrimTool";%>
<%@include file="Header.jsp"%>
<%
  tmpHttpSessObj.clearInvalidField();
  String tmpAction;
  String newListName = "";
  int result = 0;

  // don't want to see fields filled with active search terms
 if ((tmpAction = request.getParameter("applyTrim")) == null
          && (tmpAction = request.getParameter("postErrorSubmit")) == null) {
      tmpHttpSessObj.cancelFilter();
      tmpHttpSessObj.cancelFilterList();
      tmpHttpSessObj.cancelSearch();
      tmpHttpSessObj.cancelSearchList();
  }

  //test new session
  if (tmpHttpSessObj.getCurrentUser() == null) {
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
  else {System.out.println("locking");tmpHttpSessObj.writeLockCurrentShoppingList();}

      // what does the user want SLIMS to do
    String toView = request.getParameter("toView");
    String keepOrRemove = request.getParameter("keepOrRemove");
    String editOrSaveAs = request.getParameter("editOrSaveAs");
    newListName= request.getParameter("newListName");
  // trim list and view it
  if ((tmpAction = request.getParameter("applyTrim")) != null) {

      // set crtieria
      tmpHttpSessObj.cancelSearchList();
      tmpHttpSessObj.setFilterList(request);
      // know whether to keep or remove elemtents matching the criteria
      if(keepOrRemove.equals("keep") || keepOrRemove.equals("remove")){
          ShoppingCartList cart = tmpHttpSessObj.getCurrentShoppingCartList();
          if(editOrSaveAs.equals("edit")){
            cart.trimListEdit(keepOrRemove);
          }else if(editOrSaveAs.equals("saveAs")){
            result = cart.trimListSaveAs(keepOrRemove, newListName);   // load selected list by setting current to it
            if(result == 0){
                tmpHttpSessObj.setCurrentShoppingCartList(new ShoppingCartList(tmpHttpSessObj, newListName));
            }
        }
      }
      // if list creation didn't fail
      if(result == 0){
          // clear criteria
          tmpHttpSessObj.cancelFilterList();

          // view list
          if(toView.equals("subjects")){
        pageContext.forward(response.encodeURL("./ViewSubjectsList.jsp"));}
          if(toView.equals("contents")){
        pageContext.forward(response.encodeURL("./ViewContainerContentsList.jsp"));}
          if(toView.equals("samples")){
        pageContext.forward(response.encodeURL("./ViewSamplesList.jsp"));}
          if(toView.equals("containers")){
        pageContext.forward(response.encodeURL("./ViewContainersList.jsp"));}

          return;
      }
  }

  // submit after list name error
  
 if ((tmpAction = request.getParameter("postErrorSubmit")) != null) {

      // know whether to keep or remove elemtents matching the criteria
      if(keepOrRemove.equals("keep") || keepOrRemove.equals("remove")){
          ShoppingCartList cart = tmpHttpSessObj.getCurrentShoppingCartList();
          if(editOrSaveAs.equals("edit")){
            cart.trimListEdit(keepOrRemove);
          }else if(editOrSaveAs.equals("saveAs")){
            result = cart.trimListSaveAs(keepOrRemove, newListName);   // load selected list by setting current to it
            if(result == 0){
                tmpHttpSessObj.setCurrentShoppingCartList(new ShoppingCartList(tmpHttpSessObj, newListName));
            }
        }
      }
      // if list creation didn't fail
      if(result == 0){
          // clear criteria
          tmpHttpSessObj.cancelFilterList();

          // view list
          if(toView.equals("subjects")){
        pageContext.forward(response.encodeURL("./ViewSubjectsList.jsp"));}
          if(toView.equals("contents")){
        pageContext.forward(response.encodeURL("./ViewContainerContentsList.jsp"));}
          if(toView.equals("samples")){
        pageContext.forward(response.encodeURL("./ViewSamplesList.jsp"));}
          if(toView.equals("containers")){
        pageContext.forward(response.encodeURL("./ViewContainersList.jsp"));}

          return;
      }
  }
 
  // resetting filter
  if (request.getParameter("cancelFilter") != null) {
    tmpHttpSessObj.cancelSearch();
    tmpHttpSessObj.cancelFilter();
  }
  MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
%>
<script  type="text/javascript" language="javascript">
  window.onload=disable;

  function disable(){
      disableElem(document.getElementById("searchFields"));
  }

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
    return false;

  }
  function activate(){
      // if both required options have been specified (edit vs save as and keep vs remove )
      // them allow user to start setting up and running their search
      var sel1 = document.getElementById('edit').checked.valueOf();
      var sel2 = document.getElementById('saveAs').checked.valueOf();
      var sel3 = document.getElementById('keep').checked.valueOf();
      var sel4 = document.getElementById('remove').checked.valueOf();
      var name = document.getElementById('newListName').value;
      if((sel1 || sel2) && (sel3 || sel4)){
          if(sel2 && (name == null || name == "")){
              alert("Error: new list name required.");
              document.getElementById('newListName').style.border='2px solid red';
          }
          else{
              document.getElementById('newListName').style.border='';
          document.getElementById('first').style.background = "";
         enableElem(document.getElementById("searchFields"));
          }
      }

 }

 function disableElem(el) {
                try {
                    el.disabled =  true;
                    el.style.color = "grey";
                }
                catch(E){}

                if (el.childNodes && el.childNodes.length > 0) {
                    for (var x = 0; x < el.childNodes.length; x++) {
                        disableElem(el.childNodes[x]);
                    }
                }
 }

 function enableElem(el) {
                try {
                    el.disabled = false ;
                    el.style.color = "";
                }
                catch(E){}

                if (el.childNodes && el.childNodes.length > 0) {
                    for (var x = 0; x < el.childNodes.length; x++) {
                        enableElem(el.childNodes[x]);
                    }
                }
 }
</script>
<form method="POST" name="fForm" action="<%=response.encodeURL("./ListTrimTool.jsp")%>"/>
    <%// if list creation failed
      if(result == 3){
          %>

  <a class="error">Error: Shopping List name ("<%=newListName%>") already exists, please try another name:</a>
          <input type="text" id="newListName" name="newListName" size="40" />
          <input type="hidden" name="keepOrRemove" value="<%=keepOrRemove%>">
          <input type="hidden" name="editOrSaveAs" value="<%=editOrSaveAs%>">
          <input type="hidden" name="toView" value="<%=toView%>">
          <input type="submit" name="postErrorSubmit" value="Trim"/>

          <%
      }
      else if(result != 0){
          %>
          <a class="error">Error: Shopping List creation failed.</a>
          <%
      }else{%>
  <table class="view_table">
      <tr>
        <td class="largest" align="left">Trim List Tool </td>
        <td align="right">
          <input type="submit" name="cancelFilter" value="Clear Search"/>
        </td>
      </tr>
      </table>
      <table width="100%">  <tr>
            <td class="instructions">
                Trim your active list by choosing to keep or remove all the 
                entries that satisfy the criteria you define below. 
                <br>
                To deselect rows or select multiple values use &lt;Ctrl&gt; + mouse click.<br>
                If no values for a field are selected, the viewable data will not be
                resticted by that field.

                </td>
            </tr>

  </table>

<fieldset>
  <table id="first" width="100%" style="background:#B9E0BA">
      <tr><th colspan="4">To start, select how this trim will affect your list:</th></tr>
      <tr><td>1)</td><td><input type="radio" id="edit" name="editOrSaveAs" value="edit" onclick="activate()">
      Make changes to the active list.
      <br>
          <input type="radio" id="saveAs" name="editOrSaveAs" value="saveAs" onclick="activate()">
      Make a new list from the results of this trim. (Your original active list will be unchanged and your active list will be switched to the new list.)
      <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Enter a name for your new list: 
        <input type="text" id="newListName" name="newListName" size="40" onchange="activate()"/>
       </td></tr>
      <tr>
      <tr><td>2)</td><td><input type="radio" id="keep" name="keepOrRemove" value="keep" onclick="activate()">
      Keep <em>only</em> those elements in the list that satisfy the criteria below. (ex; if select Material Type: WGA below, then your list will have only WGA samples)
      <br>
          <input type="radio" id="remove" name="keepOrRemove" value="remove" onclick="activate()">
      Remove <em>all</em> elements from the list that satisfy the criteria below. (ex; if select Material Type: WGA below, then your list will not have any WGA samples
          </td></tr>
</table>
</fieldset>

<div id="searchFields">
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
        <table style="padding-right:2em"><tr>
        <th><nobr>(To Be) Shipped Out?</nobr></th></tr><tr><%=tmpHttpSessObj.getSearchPrompterForField(Container.class,"shippedOut","contShipOut")%>
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
            Apply trim and view: </td>
            <td>&nbsp;
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
        </select>
            <input type="submit" name="applyTrim" value=" Trim ">
        </td>
      </tr>
  </table>
</div>
<table width="100%"><tr>
        <td align="right">
  <input type="submit" name="cancelFilter" value="Clear Search"/>
        </td>
</tr></table>
<%}%>
</form><%@include file="Footer.jsp"%>
