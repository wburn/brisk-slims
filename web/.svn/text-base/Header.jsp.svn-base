<%@page   import="java.util.*"
          import="icapture.com.*"
          import="icapture.hibernate.*"
          errorPage="Test_JSP_error.jsp"
          autoFlush="true" buffer="1094kb"%>
<%
  UserHttpSess tmpHttpSessObj = SessionListener.getUserHttpSessObject(session);
  if (tmpHttpSessObj == null) {
    pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
    return;
  }
  String helpString =
      lapasId.equals("LogIn") ? "login" :
      lapasId.equals("ListTrimTool") ? "listTool" :
      lapasId.equals("ViewShippedTo") ? "shippedTo" :
      lapasId.equals("ViewShippedTos") ? "shippedTo" :
      lapasId.equals("ViewFreezer") ? "freezer" :
      lapasId.equals("ViewFreezers") ? "freezer" :
      lapasId.equals("SampleProcess") ? "sampleProcess" :
      lapasId.equals("SampleProcesss") ? "sampleProcess" :
      lapasId.equals("SampleType") ? "sampleType" :
      lapasId.equals("SampleTypes") ? "sampleType" :
      lapasId.equals("MaterialTypes") ? "materialType" :
      lapasId.equals("ViewCohort") ? "cohort" :
      lapasId.equals("ViewCohorts") ? "cohort" :
      lapasId.equals("ViewContainerType") ? "containerType" :
      lapasId.equals("ViewContainerTypes") ? "containerType" :
      lapasId.equals("ViewContainerContent") ? "containerContent" :
      lapasId.equals("ViewContainerContents") ? "containerContent" :
      lapasId.equals("ViewContainerContentsList") ? "containerContent" :
      lapasId.equals("ViewContainer") ? "container" :
      lapasId.equals("ViewContainers") ? "container" :
      lapasId.equals("ViewContainersList") ? "container" :
      lapasId.equals("ViewSubject") ? "subject" :
      lapasId.equals("ViewSubjects") ? "subject" :
      lapasId.equals("ViewSubjectsList") ? "subject" :
      lapasId.equals("ViewSample") ? "sample" :
      lapasId.equals("ViewSampleDocuments") ? "sampleDocuments" :
      lapasId.equals("ViewSamplesList") ? "sample" :
      lapasId.equals("ViewSnp") ? "snp" :
      lapasId.equals("ViewSubjects") ? "subject" :
      lapasId.equals("SelectReport") ? "selectReport" :
      lapasId.equals("DefineFilter") ? "search" :
      lapasId.equals("Search") ? "search" :
      lapasId.equals("Index") ? "index" :
      lapasId.equals("QuerySearch") ? "search" :
      lapasId.equals("DefineSettings") ? "settings" :
      lapasId.equals("AdminPageE") ? "administrative" :
      lapasId.equals("AdminPage") ? "administrative" :
      lapasId.equals("Browse") ? "browse" :  "help";
  tmpHttpSessObj.openHibSession();
%> <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

    <!--Code for roll-over menu-->


<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
    <head>
<link rel="stylesheet" type="text/css" href="ddsmoothmenu.css" />
<link rel="stylesheet" href="dtree.css" type="text/css" />
<script type="text/javascript" src="jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="jquery.validate.min.js"></script>
<script type="text/javascript" src="jquery.simpletip-1.3.1.min.js"></script>
<script type="text/javascript" src="ddsmoothmenu.js">

/***********************************************
* Smooth Navigational Menu- (c) Dynamic Drive DHTML code library (www.dynamicdrive.com)
* This notice MUST stay intact for legal use
* Visit Dynamic Drive at http://www.dynamicdrive.com/ for full source code
***********************************************/

</script>

<script type="text/javascript">
    ddsmoothmenu.init({
        mainmenuid: "smoothmenu1", //menu DIV id
        orientation: 'h', //Horizontal or vertical menu: Set to "h" or "v"
        classname: 'ddsmoothmenu', //class added to menu's outer DIV
        //customtheme: ["#1c5a80", "#18374a"],
        contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
    })


    // Script courtesy of http://www.web-source.net
    // stop the enter key from submitting
    function stopRKey(evt) {
        var evt = (evt) ? evt : ((event) ? event : null);
        var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
        if ((evt.keyCode == 13) && (node.type=="text")) {return false;}
    }
    document.onkeypress = stopRKey;

</script>


<!--end of code for roll-over menu-->

<title>Sample-based Laboratory Information Management System</title>
<link rel="shortcut icon" href="./images/favicon.ico"/>
    <link type="text/css" rel="stylesheet" href="formstyle.css"/>
        <script  src="./utils.js" type="text/javascript"></script>
        <script  type="text/javascript">
            var submitFlag = 0;
            warnId = 0;
            function checkSubmitFlag(){
                if (submitFlag++) return false; else return true;
            }
            function openNewWindow(URLtoOpen, windowName, windowFeatures){
                newWindow=window.open(URLtoOpen, windowName, windowFeatures);
                newWindow.focus();
            }
            function confirmLogout(){
                return (confirm("Are you sure?"));
            }
            function openHelpWindow(){
                openNewWindow("./help_"+"<%=helpString%>"+".html",
                "popup","left=10,top=10,dependent=yes,height=500,width=500,toolbar=no,scrollbars=yes,menubar=no,status=no,resizable=yes");
            }
            function show_Alert()
            {
                var link = $("#logoutLink");
                var locLeft;
                var locTop;
                var loc = link.offset();
                if (loc!=null || loc!=undefined){
                    locLeft = loc.left;
                    locTop = loc.top + 29;
                }
                else {
                    locLeft = "40%";
                    locTop = "8%";
                }
                var r = $("#logoutWarning");
                r.css({left:locLeft, top:locTop});
                r.slideDown();
            }
            function timeOutOK(){
                var r = $("#logoutWarning");
                r.slideUp();
                clearInterval(warnId);
                timeOutWarn();
                keepActive('refreshSheep');
            }
            function timeOutDeny(){
                var r = $("#logoutWarning");
                r.slideUp();
                clearInterval(warnId);
            }
            function timeOutWarn(){
                warnId = setInterval ("show_Alert()", 1500000);
                }
            function keepActive(imgName) {
                myImg = document.getElementById(imgName);
                if (myImg)
                    myImg.src = myImg.src.replace(/\?.*$/, '?' + Math.random());
            }
        </script>
        </head>
        <body onload="timeOutWarn(); setFocus();" >
<table width="100%" class="toptable">
    <tr><td>

<table class="toptable" width="100%">
  <tr>
    <td>
            <div id="header" style="background: url(./images/basicBRISKextend.png) repeat-x right transparent;
    height: 120px;">
            <div id="header2" style="background: url(./images/basicBRISKSLIMSbanner.png) no-repeat left transparent;
    height: 120px;"></div>
            </div>
<!--        <img class="slimsBanner" src="./images/basicBRISKbar.png"  alt="SLIMS" style="float:left"/>-->
            <img id="refreshSheep" width="0" height="0" src="./images/toplefttall.gif?" alt="invisible"/>
<!--            <img class="slimsBanner" src="./images/basicBRISKSLIMSbanner.png"  alt="SLIMS" style="float:right"/>-->
    </td>
  </tr>
</table>

<!--table for rounded edges, top left-->
<table class="roundtable" width="100%" cellpadding="0" cellspacing="0" border="0px" frame="box"
       style="border-spacing:0px;padding:0px;">
  <!--table  class="separate" cellPadding="4" cellSpacing="0" width="100%"-->
    <!--tr class="nav"-->
    <tr class="roundtable" >
      <% if (lapasId.equals("AdminPage") ||
             lapasId.equals("ListTrimTool") ||
             lapasId.equals("LoadNewSubjectsChooseFile") ||
             lapasId.equals("LoadNewSamplesChooseFile") ||
             lapasId.equals("LoadNewContentsChooseFile") ||
             lapasId.equals("LoadNewContainersChooseFile") ||
             lapasId.equals("ViewShippedTos") ||
             lapasId.equals("ViewFreezers") ||
             lapasId.equals("ViewCohorts") ||
             lapasId.equals("ViewControls") ||
             lapasId.equals("ViewSampleProcesss") ||
             lapasId.equals("ViewContainerTypes") ||
             lapasId.equals("ViewContainers") ||
             lapasId.equals("ViewContainersList") ||
             lapasId.equals("ViewContainerContents") ||
             lapasId.equals("ViewContainerContentsList") ||
             lapasId.equals("ViewLists") ||
             lapasId.equals("ViewMaterialTypes") ||
             lapasId.equals("ViewSampleTypes") ||
             lapasId.equals("ViewSubjects") ||
             lapasId.equals("ViewSubjectsList") ||
             lapasId.equals("ViewSamples") ||
             lapasId.equals("ViewSampleDocuments") ||
             lapasId.equals("ViewSamplesList") ||
             lapasId.equals("ViewSnps") ||
             lapasId.equals("ViewUsers") ||
             lapasId.equals("ViewGenotypingRuns") ||
             lapasId.equals("ViewGenotypingRunSampleStatuss") ||
             lapasId.equals("ViewGenotypes") ||
             lapasId.equals("ViewEthnicities") ||
             lapasId.equals("SelectReport") ||
             lapasId.equals("Search") ||
             lapasId.equals("Browse") ||
             lapasId.equals("Index") ||
             lapasId.equals("DefineFilter")||
             lapasId.equals("SimpleSearch") ||
             lapasId.equals("QuerySearch")||
             lapasId.equals("DefineSettings")) { %>


<!--table for rounded edges, top left-->
<td width="10"  class="roundtable" ><img src="./images/toplefttall.gif" width="10" height="29"
                            border="0" alt="..."/></td>
<td bgcolor="#6d97c1"  class="roundtable" >
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
  <li><a href="<%=response.encodeURL("./ViewContainerContentsList.jsp")%>">View List: Contents</a></li>
  <li><a href="<%=response.encodeURL("./ViewSamplesList.jsp")%>">View List: DBSamples</a></li>
  <%}else{%>
  <li><a href="<%=response.encodeURL("./ViewContainerContentsList.jsp")%>">View List: Samples</a></li>
  <%}%>
  <li><a href="<%=response.encodeURL("./ViewContainersList.jsp")%>">View List: Containers</a></li>
  </ul>
</li>

<li><a href="<%= response.encodeURL("./SelectReport.jsp")%>">Reports</a></li>


<li><a href="<%=response.encodeURL("./AdminPage.jsp") %>">Supporting Data</a></li>
<li><a href="<%=response.encodeURL("./Upload.jsp") %>">Upload</a></li>

            <!--li><a href="javascript:openHelpWindow()">Help</a></li-->

</ul>
<ul>
    <li><a id="logoutLink" onclick="return confirmLogout()" href="<%= response.encodeURL("./LogIn.jsp") + "?logOut=yes"%>">Logout</a></li>
</ul>
<ul class="text">
<%if(tmpHttpSessObj.getCurrentShoppingList()!=null){%>
<li><a class="text" >
    Active list: <%=tmpHttpSessObj.getCurrentShoppingList().getListName()%></a></li>
<%}else{%>
<li><a class="text" >
    No active list</a></li>
    <%}%>
</ul>

<br style="clear: left" />
</div>
</td>

<!--table for rounded edges, top right-->
<td width="10px" class="roundtable" >
    <img src="./images/toprighttall.gif" width="10px" height="29px" border="0"  alt="..."/>
</td>

</tr>

    <%}else{%>
<!--table for rounded edges, top left-->
<td width="10px" height="10px" class="roundtable" >
    <img src="./images/topleft10.gif" width="10px" height="10px"
                            border="0px" alt="..."></td>
<!-- top bar-->
        <td style="background-color:#6d97c1" height="10px" class="roundtable" >&nbsp;</td>
<!--table for rounded edges, top right-->
<td width="10px" height="10px" class="roundtable" >
    <img src="./images/topright10.gif" width="10px" height="10px" border="0" alt="...">
</td>

</tr>

    <%}%>

<!--table for rounded edges, left side-->
   <tr class="roundtable" >
        <td style="background-image: url('./images/sideThin.gif');" width="10px" class="roundtable" >
            <img src="./images/blank.gif" width="10px" height="1" border="0" alt="..." />
        </td>
        <td style="padding-left: 0px;">
            
  <!--/table-->
  <table width="100%"><td>
  <table width="100%">
    <td>
            <div id="logoutWarning" style="display:none">
        You will be logged out within 5 minutes, click OK to extend your time<br>
            <a onclick="timeOutOK()"><img border="0" src="images/ok.png" alt="ok"/>OK</a> <a onclick="timeOutDeny()"><img border="0" src="images/ignore.png" alt="Iqnore"/>Iqnore</a>
    </div>
