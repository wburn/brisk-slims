<%-- 
    Document   : Make384From96
    Created on : Aug 31, 2009, 12:39:53 PM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%String lapasId = "Make384From96";%>
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
        String tmpAction = null;

        // lock shopping list
        if ((tmpAction = request.getParameter("cancel")) == null) {
            tmpHttpSessObj.writeLockCurrentShoppingList();
        }

        String newContainerID = null;
        
//test new session
        if (tmpHttpSessObj.getCurrentUser() == null) {
            // unlock shopping list
            tmpHttpSessObj.releaseLockCurrentShoppingList();
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }
        if ((tmpAction = request.getParameter("cancel")) != null) {
            // unlock shopping list
            tmpHttpSessObj.releaseLockCurrentShoppingList();
            response.sendRedirect("./ViewContainersList.jsp");
            //response.encodeRedirectURL("./.jsp");
            //pageContext.forward(response.encodeURL("./ViewContainersList.jsp"));
            return;
        }
        if (tmpHttpSessObj.getCurrentShoppingCartList() == null) {
            // unlock shopping list
            tmpHttpSessObj.releaseLockCurrentShoppingList();
            pageContext.forward(response.encodeURL("./ViewLists.jsp"));
            return;
        }
/* ---------------- TEST VERSION START -------------------
        if ((tmpAction = request.getParameter("finish")) != null) {
            String[] srcPlateIDs = request.getParameterValues("doPlate");
            String[] srcPlateOrder = request.getParameterValues("orderNewPlate");
             String newPlateNameRoot = request.getParameter("plateNameRoot")+request.getParameter("batchName");

            int contID = 1673;
            newContainerID = Integer.toString(contID);
            System.out.println("1 tmpHttpSessObj.getCurrentContainer(): " + tmpHttpSessObj.getCurrentContainer());
            if (contID < 0) {
                System.out.println("Error in making 384. Err message is: " + contID);
                //pageContext.forward(response.encodeURL("./Make384From96.jsp"));
                return;
            }
            if ((tmpAction = request.getParameter("createSDS")) != null) {
                //UserHttpSess tmpHttpSessObj = SessionListener.getUserHttpSessObject(session);

                // get an array of strings,
                //one for each data line of the resulting file
                String[] lines = tmpHttpSessObj.getSDSImportData(String.valueOf(contID));

                // create file
                tmpHttpSessObj.openHibSession();
                response.setContentType("application/plain");
//response.setHeader("Head", "aaaa");
//response.addHeader("Content-Disposition", "attachment; filename=" + table + ".txt");
                response.addHeader("Content-Disposition", "inline; filename=" +
                        newPlateNameRoot.replace(' ', '-') + "-SDSimportFile" + Util.getCurrentDateTime() + ".txt");

                // write the default header business to the file
                String header = "*** SDS Setup File Version	3\t\t\t\n" +
                        "*** Output Plate Size	384\t\t\t\n" +
                        "*** Output Plate ID	Untitled 10\t\t\t\n" +
                        "*** Number of Detectors	2\t\t\t\n" +
                        "Detector	Reporter	Quencher	Description	Comments\n" +
                        "ADRB1_126A	VIC		Mutant\t\n" +
                        "ADRB1_126A	FAM		Wild Type\t\n" +
                        "*** Number of Markers	1\t\t\t\n" +
                        "Marker	AlleleX	AlleleY	Description	Comments\n" +
                        "ADRB1_126 	ADRB1_126A	ADRB1_126A\t\t\t\n" +
                        "Well	Sample Name	Detector	Task	Quantity";
                out.clearBuffer();
                out.println(header);

                // go through array and write each one to the file
                for (int i = 0; i < lines.length; i++) {
                    out.println((i + 1) + "\t" + ((lines[i] == null) ? "" : lines[i]));
                }
                out.flush();
                out.close();
                tmpHttpSessObj.closeHibSession();
            }
            if (contID > 0) {
                System.out.println("2 JUST BEFORE TRICKY REDIRECT");

                //RequestDispatcher rd = request.getRequestDispatcher("./ViewContainerContents.jsp?contId=1673");
                //rd.forward(request, response);

                //response.setHeader("Refresh","1;http://localhost:8080/SampleTracking/ViewContainerContents.jsp?contId=1673");

                //response.sendRedirect("./ViewContainerContents.jsp?contId=1673");
                //pageContext.forward(response.encodeURL("./ViewContainerContents.jsp?contId=1673"));
                //pageContext.forward("./ViewContainerContents.jsp?contId=1673");
                return;
            }
        }  ---------------- TEST VERSION END -------------------*/
        /*---------------- WORKING VERSION START ---------------------*/
          if ((tmpAction = request.getParameter("finish")) != null) {
        System.out.println("About to make 384");
        String[] srcPlateIDs = request.getParameterValues("doPlate");
        String[] srcPlateOrder = request.getParameterValues("orderNewPlate");
        String newPlateNameRoot = request.getParameter("plateNameRoot")+request.getParameter("batchName");

        int[] contIDs = tmpHttpSessObj.four96to384(request);
        //int[] contIDs = {1874,1875,1876,1877};
            System.out.println("post four96to384, contID is: " + contIDs);
            
        // if error running tmpHttpSessObj.four96to384(request)
        if (contIDs[0] < 0) {
            int messageNum = (contIDs[0]==-20)?3:contIDs[0];
            System.out.println("Error in making 384. Err message is: " + contIDs[0]);

            // get list of plates 96 well plates from container list
            List containersList = tmpHttpSessObj.getAllContainerObjectsInList();
            System.out.println("containersList.size" + containersList.size());
            //String listName = tmpHttpSessObj.getCurrentShoppingList().getListName();
            List<Container> sourceContainers = new ArrayList();


            // trim list down to only 96-well plates
            Iterator iter = containersList.listIterator();
            Container cont = null;
            while (iter.hasNext()) {
                cont = (Container) iter.next();
                if (cont.getContainerType().getContainerTypeID().equals("0")) {
                    sourceContainers.add(cont);
                }
            }

            // get the number of source plates
            int numSrcPlates = sourceContainers.size();
            int i = 0;

        %>

<form method="POST" name="fForm" onsubmit=""
      action="<%=response.encodeURL("./Make384From96.jsp")%>">
                  <br>
                      <%if(messageNum==3){%>
    <a class="error">  Error: Name of container already exists in database. Please try again.</a>
                      <%}else if(messageNum==-2){%>
    <a class="error">  Error: Container creation failed.</a>
     <%}else if(messageNum==-3){%>
    <a class="error">  Error: Sample creation in new plate failed.</a>
                      <%}else if(messageNum==-4){%>
    <a class="error">  Error: Failed to reduce volumes of source plates.</a>
                      <%}else{%>
    <a class="error">  Error.</a>
                      <%}%>
          <%@include file="Make384From96E.jsp"%>
</form>

        <%
        //pageContext.forward(response.encodeURL("./Make384From96.jsp"));
        return;
        }  
        else{// if no errors in running tmpHttpSessObj.four96to384(request)
         
        // store newly made plates in plater for summary page
        Plater plater = new Plater(tmpHttpSessObj);
        Container cont = null;
        for(int i = 0; i<contIDs.length;i++){
            cont = (Container) tmpHttpSessObj.getObjectById(Container.class, String.valueOf(contIDs[i]));
            plater.platesMade.add(cont);
        }
        tmpHttpSessObj.setCurrentPlater(plater);

            pageContext.forward("./PlatingSummary.jsp");
            return;
        }
          }
         
         /*---------------- WORKING VERSION END ---------------------*/

        // viewing page the first time
        else {
// get list of plates 96 well plates from container list
            List containersList = tmpHttpSessObj.getAllContainerObjectsInList();
            System.out.println("containersList.size" + containersList.size());
//String listName = tmpHttpSessObj.getCurrentShoppingList().getListName();
            List<Container> sourceContainers = new ArrayList();


// trim list down to only 96-well plates
            Iterator iter = containersList.listIterator();
            Container cont = null;
            while (iter.hasNext()) {
                cont = (Container) iter.next();
                if (cont.getContainerType().getContainerTypeID().equals("0")) {
                    sourceContainers.add(cont);
                }
            }

// get the number of source plates
            int numSrcPlates = sourceContainers.size();
// reject request if no 96-well containers in list
            if (numSrcPlates == 0) {
%><script type="text/javascript">alert("Error: no 96 well plates selected.")</script><%//pageContext.forward(response.encodeURL("./ViewContainersList.jsp"));
//return;
        }

        int i = 0;
%>

<form method="POST" name="fForm" onsubmit=""
      action="<%=response.encodeURL("./Make384From96.jsp")%>">
          
          <%@include file="Make384From96E.jsp"%>
</form>
<%}}%>
<%@include file="Footer.jsp"%>