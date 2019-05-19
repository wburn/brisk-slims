
<%String lapasId = "AdminPage";%>
<%@include file="Header.jsp"%>
<%String tmpAction = request.getParameter("aname");%>
<script  type="text/javascript" language="javascript">
 function setFocus(){
 <%if (tmpAction!=null){%>
   var x = document.getElementById("<%=tmpAction%>")
   if (x) scrollTo(0,x.offsetTop);
<%}%>
 }
</script>
<br><br>
<a class="larger">Select data to view, create or edit:</a>
<br><br>
<form name="navFormB">
<%@include file="AdminMenu.jsp"%>
</form>
<br><br>
<%@include file="Footer.jsp"%>
