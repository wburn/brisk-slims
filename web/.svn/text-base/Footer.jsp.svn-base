


</td>
</table>



</table>

<!-- bottom of rounded table-->
</td>
<!--right side bar-->
<td style="background-image: url('./images/sideThin.gif');" width="10px" class="roundtable" >
    <img src="./images/blank.gif" width="10px" height="1" border="0" alt="..." />
</td>
</tr>
<!--bottom bar-->
<tr class="roundtable" >
    <td class="roundtable" >
        <img src="./images/bottomleft10.gif" width="10px" height="10px" border="0" alt="...">
    </td>
    <td style="background-image: url('./images/sideThin.gif');" class="roundtable" >
        <img src="./images/blank.gif" width="1px" height="10px" border="0" alt="..." />
    </td>
    <td class="roundtable" >
        <img src="./images/bottomright10.gif" width="10px" height="10px" border="0" alt="...">
    </td>
</tr>
</table>

<!--table  class="separate" cellPadding="4" cellSpacing="0" width="100%">
    <tr class="nav"><td class="separate"></td></tr>
    </table-->

<%if(false){%>
<table  class="separate" cellPadding="4" cellSpacing="0" width="100%">
    <tr class="nav">
        <% if (lapasId.equals("AdminPage") ||
                lapasId.equals("ListTrimTool") ||
                lapasId.equals("ViewShippedTos") ||
                lapasId.equals("ViewFreezers") ||
                lapasId.equals("ViewCohorts") ||
                lapasId.equals("ViewControls") ||
                lapasId.equals("ViewSampleProcesss") ||
                lapasId.equals("ViewContainerTypes") ||
                lapasId.equals("ViewContainers") ||
                lapasId.equals("ViewContainersList") ||
                lapasId.equals("ViewContainerContents") ||
                lapasId.equals("ViewLists") ||
                lapasId.equals("ViewMaterialTypes") ||
                lapasId.equals("ViewSampleTypes") ||
                lapasId.equals("ViewSubjects") ||
                lapasId.equals("ViewSubjectsList") ||
                lapasId.equals("ViewSamples") ||
                lapasId.equals("ViewSamplesList") ||
                lapasId.equals("ViewContainerContentsList") ||
                lapasId.equals("ViewGenotypingRuns") ||
                lapasId.equals("ViewGenotypingRunSampleStatuss") ||
                lapasId.equals("ViewGenotypes") ||
                lapasId.equals("ViewSnps") ||
                lapasId.equals("ViewUsers") ||
                lapasId.equals("ViewEthnicities") ||
                lapasId.equals("SelectReport") ||
                lapasId.equals("Search") ||
                lapasId.equals("Browse") ||
                lapasId.equals("Index") ||
                lapasId.equals("DefineFilter") ||
                lapasId.equals("SimpleSearch") ||
                lapasId.equals("DefineSettings") ||
                lapasId.equals("QuerySearch")) {%>

     <div id="smoothmenu1" class="ddsmoothmenu">
<ul>
<li><a href="<%=response.encodeURL("./Index.jsp")%>">Home</a></li>
<li><a href="<%= response.encodeURL("./Search.jsp")%>">Search</a>
  <ul>
  <li><a href="<%=response.encodeURL("./SimpleSearch.jsp")%>">Simple Search</a></li>
  <li><a href="<%=response.encodeURL("./DefineFilter.jsp")%>">Power Search</a></li>
  <li><a href="<%=response.encodeURL("./QuerySearch.jsp")%>">SQL Query Search</a></li>
  </ul>
</li>
<%    if (tmpHttpSessObj.isFilter() || tmpHttpSessObj.isSearch()){%>
<li><a href="<%= response.encodeURL("./Browse.jsp")%>">Results</a>
<%} else{%>
<li><a href="<%= response.encodeURL("./Browse.jsp")%>">Browse</a>
<%}%>
  <ul>
  <li><a href="<%= response.encodeURL("./ViewSubjects.jsp")%>">Subjects</a></li>
  <%    if (tmpHttpSessObj.isTechUser()){%>
  <li><a href="<%= response.encodeURL("./ViewSamples.jsp")%>">DBSamples</a></li>
  <%}%>
  <%    if (tmpHttpSessObj.isTechUser()){%>
  <li><a href="<%= response.encodeURL("./ViewContainerContents.jsp")%>">Contents</a></li>
  <%} else{%>
  <li><a href="<%= response.encodeURL("./ViewContainerContents.jsp")%>">Samples</a></li>
  <%}%>
  <li><a href="<%= response.encodeURL("./ViewContainers.jsp")%>">Containers</a></li>
  </ul>
</li>
<li><a href="<%=response.encodeURL("./ViewLists.jsp")%>">Lists</a>
  <ul>
  <!--li><a href="<%=response.encodeURL("./ViewContainerContentsList.jsp")%>">Active List</a></li-->
  <li><a href="<%=response.encodeURL("./ViewLists.jsp")%>">List Manager</a></li>
  <li><a href="<%=response.encodeURL("./ViewSubjectsList.jsp")%>">View List: Subjects</a></li>
  <%    if (tmpHttpSessObj.isTechUser()){%>
  <li><a href="<%=response.encodeURL("./ViewContainerContentsList.jsp")%>">Active List: Contents</a></li>
  <li><a href="<%=response.encodeURL("./ViewSamplesList.jsp")%>">View List: DBSamples</a></li>
  <%}else{%>
  <li><a href="<%=response.encodeURL("./ViewContainerContentsList.jsp")%>">View List: Samples</a></li>
  <%}%>
  <li><a href="<%=response.encodeURL("./ViewContainersList.jsp")%>">View List: Containers</a></li>
  </ul>
</li>
<li><a href="<%= response.encodeURL("./SelectReport.jsp")%>">Reports</a></li>


      <%    if (tmpHttpSessObj.isTechUser()){%>
            <li><a href="<%=response.encodeURL("./AdminPage.jsp") %>">Admin Tables</a></li>
      <%    }%>
            <!--li><a href="javascript:openHelpWindow()">Help</a></li-->

</ul>
<ul>
    <li><a onclick="return confirmLogout()" href="<%= response.encodeURL("./LogIn.jsp") + "?logOut=yes"%>">Logout</a></li>
</ul>
<br style="clear: left" />
</div>
</td>
    </tr>
    <%}else{%>


        <td class="separate"></td>

    <%}%>
  </table>
<%}%>


<br>
<center>
    You are logged in as : <%= tmpHttpSessObj.getUserString()%>
    <br>Version 1.31, Last update: July 12, 2010
    <br>WebApps Developed by <a href="http://genapha.icapture.ubc.ca/teamMembers.do">The Daley Lab</a>
    <br><small> Copyright &copy; 2009</small>
</center>
</body>
</html>
<%
        tmpHttpSessObj.closeHibSession();
%>
