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
            String ccId = request.getParameter("ccId");
            if (ccId != null) {
%>
<div class="legend">
    <div class="showlegend">
        <a class="showHide" href="#">Hide Iconic Guide</a>
    </div>
    <div class="treelegend">
        <fieldset>
            <img alt="Sample" src="img/rna.png"/>Sample<br />
            <img alt="Originating sample" src="img/rnaOpen.png"/>Original parent sample<br />
            <img style="padding: 3px 3px 2px 2px" alt="Plus" src="img/nolines_plus.gif"/>Indicates that the sample has been used to make new samples; click to show new samples<br />
            <img style="padding: 3px 3px 2px 2px" alt="Minus" src="img/nolines_minus.gif"/>Indicates that the sample has been used to make new samples; click to hide new samples<br />
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
            String sampId;
            ContainerContent tempCont = null;
            Sample tempSample = null;

            tempCont = (ContainerContent) tmpHttpSessObj.getObjectById(ContainerContent.class, ccId);
            tempSample = tempCont.getSample();
            sampId = tempSample.getSampleID().toString();

        %>
            d.add(0,-1,'Sample History of Sample <%=tempSample.getSampleName()%>','','','','img/rnaOpenHistory.png','img/rnaOpenHistory.png');
        <%
            tmpHttpSessObj.resetSampleHistoryValues();
            String parentString = tmpHttpSessObj.getSampleParent(sampId);
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
                                tmpHttpSessObj.getSampleChildren(parentString);
                                tree = tmpHttpSessObj.getHistTree();
                                if (tree.isEmpty()) {
                                        //FILL HERE
%>
</script>
<a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(21)%></a>
    <%
                                pageContext.forward(response.encodeURL("./SampleHistory.jsp"));
                            }
                            parent = tmpHttpSessObj.getHistParents();
                            if (parent.isEmpty()) {
                                        //FILL HERE
%>
</script>
<a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(21)%></a>
    <%
                                pageContext.forward(response.encodeURL("./SampleHistory.jsp"));
                            }
                            for (int i = 0; i < tree.size(); ++i) {
                                tempSample = (Sample) tmpHttpSessObj.getObjectById(Sample.class, parent.get(i));
    %>
d.add(<%=i + offset%>,<%=Integer.parseInt(tree.get(i)) + offset%>,'Sample <%=tempSample.getSampleName()%> - <%=tempSample.getSampleType().getDescription()%>','ContainerContentHistory.jsp?sampId=<%=parent.get(i)%>','','','img/rna.png','img/rnaOpen.png');
<%
        }
    }
%>

document.write(d);
d.closeAll();
d.openTo(<%=parent.indexOf(ccId) + offset%>, true);
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
        $("div.dtree").find("#id0").attr("src","img/rnaOpenHistory.png");
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