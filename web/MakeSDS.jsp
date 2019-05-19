<%-- 
    Document   : MakeSDS
    Created on : Nov 24, 2009, 10:29:46 AM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@page   import="java.util.*"
          import="icapture.com.*"
          import="icapture.hibernate.*"
          errorPage="Test_JSP_error.jsp"
%>

<%
  UserHttpSess tmpHttpSessObj = SessionListener.getUserHttpSessObject(session);
  if (tmpHttpSessObj == null) {
    pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
    return;
  }

  tmpHttpSessObj.openHibSession();

String contID;
        if((contID = request.getParameter("containerID")) != null) {
        // get container (for plate name)
        Container container = (Container) tmpHttpSessObj.getObjectById(Container.class, contID);
        if(container!=null){
            System.out.println("------- IN IFS -----");
        //UserHttpSess tmpHttpSessObj = SessionListener.getUserHttpSessObject(session);

        // get an array of strings,
        //one for each data line of the resulting file
        String[] lines = tmpHttpSessObj.getSDSImportData(contID);

        String plateName = container.getcontainerName();
                
        // create file
        tmpHttpSessObj.openHibSession();
        response.setContentType("application/txt");
        response.setHeader("Content-Disposition", "inline; filename=" +
            plateName.replace(' ', '-')+"-SDSimportFile"+Util.getCurrentDateTime()+".txt");

        out.clearBuffer();
        // write the default header business to the file
        // "\n" doesn't translate in notepad
        out.println("*** SDS Setup File Version	3\t\t\t");
        out.println("*** Output Plate Size	384\t\t\t");
        out.println("*** Output Plate ID	Untitled 10\t\t\t");
        out.println("*** Number of Detectors	2\t\t\t");
        out.println("Detector	Reporter	Quencher	Description	Comments");
        out.println("ADRB1_126A	VIC		Mutant\t");
        out.println("ADRB1_126A	FAM		Wild Type\t");
        out.println("*** Number of Markers	1\t\t\t");
        out.println("Marker	AlleleX	AlleleY	Description	Comments");
        out.println("ADRB1_126 	ADRB1_126A	ADRB1_126A\t\t\t");
        out.println("Well	Sample Name	Detector	Task	Quantity");


        // go through array and write each one to the file
        for(int i = 0;i<lines.length;i++) {
        out.println((i+1)+"\t"+((lines[i]==null)?"0":lines[i]));
        }
        out.flush();
        out.close();
        tmpHttpSessObj.closeHibSession();
        pageContext.forward("./Index.jsp");
        return;
        }
        }
%>


