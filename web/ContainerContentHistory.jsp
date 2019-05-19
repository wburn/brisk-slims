<%String lapasId = "Search";%>
<%@include file="Header.jsp"%>
<%-----------------------------HEADER.JSP-----------------%>
<%--
    Document   : SampleHistory
    Created on : Jun 4, 2010, 9:20:09 AM
    Author     : ATan1
--%>
<%
  if ( tmpHttpSessObj.getCurrentUser() == null){//test for new session
    pageContext.forward(response.encodeURL("./LogIn.jsp"));
    return;
  }
%>
<script type="text/javascript" src="dtree.js"></script>
<%
            boolean openFlag = true;
            String ccId = request.getParameter("ccId");
            String sampId = request.getParameter("sampId");
            ArrayList<String> vec = new ArrayList();
            if (ccId != null || sampId != null) {
%>
<div class="legend">
    <div class="showlegend">
        <a class="showHide" href="#">Hide Iconic Guide</a>
    </div>
    <div class="treelegend">
        <fieldset>
            <img alt="Container with sample" src="img/flask.png"/>Container with sample<br />
            <img alt="Part of the sample was redistributed" src="img/flaskopen.png"/>Part of the sample was redistributed<br />
            <img alt="Container has no sample" src="img/flaskNoVolume.png"/>Container has no sample<br />
            <img alt="All of the sample was redistributed" src="img/flaskNoVolumeOpen.png"/>All of the sample was redistributed<br />
            <img style="padding: 3px 3px 2px 2px" alt="Plus" src="img/nolines_plus.gif"/>Indicates that the part/all of the sample was relocated to another container; click to show containers<br />
            <img style="padding: 3px 3px 2px 2px" alt="Minus" src="img/nolines_minus.gif"/>Indicates that the part/all of the sample was relocated to another container; click to hide containers<br />
        </fieldset>
    </div>
</div>
<div class="dtree">
    <a href ="javascript: history.go(-1);">back</a><br>
    <a href="javascript: d.openAll();">open all</a> | <a href="javascript: d.closeAll();">close all</a>

    <script type="text/javascript">
        <!--

        d = new dTree('d');

        <%
                        int offset = 1;
                        ContainerContent tempConCont;
                        //if ccId not given in URL then get it from the sampId
                        if (ccId == null || ccId.equals("")) {
                            openFlag = false;
                            tempConCont = tmpHttpSessObj.getContainerContentsFromSampId(sampId);
                            if (tempConCont == null){
                                          %>
    </script>
    <a class="error" style="color:#FF0000"><br>  Error:
        <%=tmpHttpSessObj.getMyMessage(23)%></a>

            <%return;
                            }
                            else
                                ccId = tempConCont.getId().toString();
                        } else {
                            tempConCont = (ContainerContent) tmpHttpSessObj.getObjectById(ContainerContent.class, ccId);
                        }

        %>
            d.add(0,-1,'Container Content History of Sample <%=tempConCont.getSample().getSampleName()%>');
        <%
                        tmpHttpSessObj.resetSampleHistoryValues();
                        String parentString = tmpHttpSessObj.getContainerContParent(ccId);
                        ArrayList<String> tree = null;
                        ArrayList<String> parent = null;
                        if (parentString.equals("-1")) {
                            //FILL HERE
        %>
    </script>
    <a class="error">  Error:
        <%=tmpHttpSessObj.getMyMessage(9)%></a>
        <%
                                } else {
                                    if (parentString == null) {
                                        parentString = ccId;
                                    }
                                    tmpHttpSessObj.getContainerContChildren(parentString);
                                    tree = tmpHttpSessObj.getHistTree();
                                    if (tree.isEmpty()) {
                                        //FILL HERE
        %>
</script>
<a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(21)%></a>
    <%
                                    pageContext.forward(response.encodeURL("./ContainerContentHistory.jsp"));
                                }
                                parent = tmpHttpSessObj.getHistParents();
                                if (parent.isEmpty()) {
                                    //FILL HERE
    %>
</script>
<a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(21)%></a>
    <%
                                    pageContext.forward(response.encodeURL("./ContainerContentHistory.jsp"));
                                }
                                for (int i = 0; i < tree.size(); ++i) {
                                    tempConCont = (ContainerContent) tmpHttpSessObj.getObjectById(ContainerContent.class, parent.get(i));
                                    Double tempVol = Double.parseDouble(tempConCont.getVolume());
                                    Double tempCon = Double.parseDouble(tempConCont.getConcentration());
                                    tempCon *= 100.0;
                                    tempCon = Math.floor(tempCon + 0.5);
                                    tempCon /= 100.0;
                                    if (tempVol == 0) {
    %>
d.add(<%=i + offset%>,<%=Integer.parseInt(tree.get(i)) + offset%>,'<%=tempConCont.getSample().getSampleName()%> in Well <%=tmpHttpSessObj.getASCIIrow(tempConCont.getRow())%><%=tempConCont.getColumn()%> of Container <%=tempConCont.getContainer().getContainerID()%>','ViewContainerContents.jsp?ccId=<%=parent.get(i)%>','','','img/flaskNoVolume.png','img/flaskNoVolumeOpen.png');
<%
                                        } else {
                                            tempVol *= 100.0;
                                            tempVol = Math.floor(tempVol + 0.5);
                                            tempVol /= 100.0;
%>
d.add(<%=i + offset%>,<%=Integer.parseInt(tree.get(i)) + offset%>,'<%=tempConCont.getSample().getSampleName()%> in Well <%=tmpHttpSessObj.getASCIIrow(tempConCont.getRow())%><%=tempConCont.getColumn()%> of Container <%=tempConCont.getContainer().getcontainerName()%>','ViewContainerContents.jsp?ccId=<%=parent.get(i)%>');
<%
                        }
                        vec.add("'<b>Sample Information:</b><br>"
                                + "<small><i>Values rounded to nearest hundredth</i></small><br>"
                                + "Concentration (ng/ul): " + tempCon + "<br>"
                                + "Volume (ul): " + tempVol + "'");
                    }
                }
%>

document.write(d);
d.closeAll();
<%
                if (openFlag) {
%>
d.openTo(<%=parent.indexOf(ccId) + offset%>, true);
<%
    } else {
%>
$("div.dtree").find(".nodeSel").attr("class","clip");
d.config.useSelection = false;
<%                    }%>
//-->
</script>
</div>
<%} else {
%>
<a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(9)%></a><br>
<input type='button' onclick='history.go(-1);' name="badPermGoBack" value="Back" />
<%
            }
%>



<script type="text/javascript">
    $(document).ready(function() {
        var infoArray = <%=vec.toString()%>;
        $("div.dtree").find("a[id*=sd]").each(function(i) {
            $(this).simpletip({
                content: infoArray[i],
                position: [550,139]
            });
        });
        $("div.showlegend").mousedown(function() {
            if ($("div.treelegend").is(":hidden")){
                $("div.treelegend").slideDown();
                $("div.showlegend a.showHide").replaceWith('<a class="showHide" href="#">Hide Iconic Guide</a>');
            }
            else {
                $("div.treelegend").slideUp();
                $("div.showlegend a.showHide").replaceWith('<a class="showHide" href="#">Show Iconic Guide</a>');
            }
        });
    });
</script>
<%@include file="Footer.jsp"%>