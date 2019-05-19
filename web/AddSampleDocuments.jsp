<%-- 
    Document   : AddSampleDocuments.jsp
    Created on : May 27, 2010, 10:22:33 AM
    Author     : ATan1
--%>
<%String lapasId = "ViewSample";%>
<%@include file="Header.jsp"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletRequestContext"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>

<%//test new session
            response.setHeader("Cache-Control", "no-store");
            if (tmpHttpSessObj.getCurrentUser() == null) {
                pageContext.forward(response.encodeURL("./LogIn.jsp"));
                return;
            }

// check permissions
            if (!tmpHttpSessObj.isPowerUserUp()) {%>
<script  type="text/javascript" language="javascript">
    window.onload = disableAllButBack;
</script>
<br><a class="largest">Sorry, you do not have permission to use this page. </a><input type='button' onclick='history.go(-1);' name="badPermGoBack" value="Back" /><br><br>
Your privilege level is: <%=tmpHttpSessObj.getCurrentUser().getRightsReadable()%>.
To see the privileges associated with each level <input type="button" class="button" name="getPermissionDoc" onclick="openPermissionsDoc()" value="Click here"/> <br><br>
<br><br>
<% }%>


<%
            String tmpAction;
            String selfId = null;
            tmpHttpSessObj.clearInvalidField();
//test new session
            response.setHeader("Cache-Control", "no-store");
            if (tmpHttpSessObj.getCurrentUser() == null) {
                pageContext.forward(response.encodeURL("./LogIn.jsp"));
                return;
            }

// where it was directed from
            String sampId = request.getParameter("sampId");
            if ((tmpAction = request.getParameter("cancelSam")) != null) {
                pageContext.forward(response.encodeURL("./ViewSampleDocumentss.jsp?sampId=" + sampId));
                return;
            }
%>
                        <script type="text/javascript">
            $(window).load(function(){
    $("#sampleDocForm").validate({
        rules: {
            filename:{
                required: true,
                remote: {
                    url: "FilenameValidate",
                    data: ({
                        sampId : function() {
                            var value = $('#sampId').val();
                            if (value=="") value="-1";
                            return value
                        },
                        filename : function() {
                            return $('#filename').val();
                        },
                        sampDocId : function() {
                            return $('#sampDocId').val();
                        }
                    })
                }
            },
            fileToLoad:{
                required: function(){
                    return ($("#sampDocId").val()=="-1");
                }
            }
        },
        messages: {
            filename: {
                required: "Please specify the filename",
                remote: "Filename already in use"
            },
            fileToLoad:{
                required: "Please locate the file you want to upload"
            }
        },
        success: function(label) {
            // set   as text for IE
            label.html(" ").addClass("checked");
        }
    });
    $("#sampId").change(function(){
        var value = $("#filename").val();
        $("#filename").val(value+" ");
        $("#sampleDocForm").validate().element("#filename");
        $("#filename").val(value);
        $("#sampleDocForm").validate().element("#filename");
    });
  });
  function addSampleGetAround(){
      $("#addsample").attr("name", "addSample");
  }
  function updateSampleGetAround(){
      $("#updatesample").attr("name", "updateSample");
  }
  </script>
<script  type="text/javascript" language="javascript">
    function setFocus(){
        field = document.getElementById("kluda");
        if (field) field.focus();
        else
            document.fForm.genId.focus();
    }
    function confirmDelete(){
        return (confirm("Are You sure?"));
    }
    function getFilename(){
        filepath = document.getElementById("fileToLoad").value;
        lastIndex = filepath.lastIndexOf("\\");
        file = filepath.substring(lastIndex+1,filepath.length);
        document.getElementById("filename").value = file;
    }
</script>
<%


            int messageNum = 0;
            String sampDocId = null;;
            String filePath = null;
            String filename = null;
            byte[] document;
            String comments = null;
            SampleDocuments sampDocs;
            Sample sample = null;
            byte[] fileBytes = null;
            boolean addSample = false;
            boolean updateSample = false;

            ServletFileUpload multiStream = new ServletFileUpload();
            DiskFileItemFactory itemFac = new DiskFileItemFactory();
            ServletRequestContext rCxt = new ServletRequestContext(request);
            multiStream.setFileItemFactory(itemFac);

            if (multiStream.isMultipartContent(rCxt)) {
                List list = multiStream.parseRequest(rCxt);
                Iterator<FileItem> iter = list.iterator();
                while (iter.hasNext()) {
                    FileItem item = iter.next();
                    if (item.getFieldName().equals("sampDocId")) {
                        sampDocId = item.getString();
                    } else if (item.getFieldName().equals("sampId")) {
                        sampId = item.getString();
                    } else if (item.getFieldName().equals("fileToLoad")) {
                        filePath = item.getName();
                        fileBytes = item.get();
                        } else if (item.getFieldName().equals("filename")) {
                            filename = item.getString();
                    } else if (item.getFieldName().equals("comments")) {
                        comments = item.getString();
                    } else if (item.getFieldName().equals("cancelSam")) {
                        pageContext.forward(response.encodeURL("./ViewSampleDocumentss.jsp?sampId=" + sampId));
                    } else if (item.getFieldName().equals("addSample")) {
                        addSample = true;
                    } else if (item.getFieldName().equals("updateSample")) {
                        updateSample = true;
                    }
                }
            }
            if (addSample) {

                if ((comments.equals("") || comments.equals("null")) || comments == null) {
                    comments = null;
                }

                if (sampId == null || sampId.equals("")) {
                    messageNum = 9;
                }

                if (filePath.equals("") || filePath == null) {
                    messageNum = 9;
                }

                if (fileBytes.equals("") || fileBytes == null) {
                    messageNum = 9;
                }

                if (messageNum == 0) {
                    sample = (Sample) tmpHttpSessObj.getObjectById(Sample.class, sampId);
                }
                if (messageNum == 0) {
                    messageNum = tmpHttpSessObj.addSampleDocuments(sampDocId, filename, sample, fileBytes, comments);
                    System.out.println("test");
                    if (messageNum == 1) {
                        messageNum = 21;
                    }
                }
                if (messageNum == 0) {
                    pageContext.forward(response.encodeRedirectURL("./ViewSampleDocumentss.jsp?sampId=" + sampId));
                    return;
                } else if (messageNum == 1) {
                    pageContext.forward(response.encodeRedirectURL("./Test_JSP_error.jsp"));
                    return;
                }
%>
<form method="POST" name="fForm" id="sampleDocForm"
      enctype="multipart/form-data" action="<%=response.encodeURL("./AddSampleDocuments.jsp?sampId=" + sampId + "&sampDocId=" + sampDocId)%>">
        <%if (sampId == null || sampId == "-1") sampId = request.getParameter("sampId");%>
    <%if (sampDocId == null || sampDocId == "-1")sampDocId = request.getParameter("sampDocId");%>
    <a class="largest">Add Sample Documents</a>
    <br>
    <a class="error">  Error:
        <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
        <%@include file="AddSampleDocumentsE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" class="submitter" name="addSample" value="  Add  " onclick="addSampleGetAround();"/>
                    <%} else {%>
                    <%=tmpHttpSessObj.getMyMessage(6)%>      <%}%>
                </td>
                <td>
                    <input type="button" name="cancelSam" value="Cancel" size="10" onclick="window.location.assign('<%=response.encodeURL("./ViewSampleDocumentss.jsp?sampId=" + sampId)%>')"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else if (updateSample) {
                sampDocId = tmpHttpSessObj.getCurrentSampleDocumentsId();
                sampId = tmpHttpSessObj.getCurrentSampleDocuments().getSample().getSampleID();

                if (sampId == null || sampId.equals("") || sampId.equals("-1")) {
                    messageNum = 9;
                }
                if (messageNum == 0) {
                    sample = (Sample) tmpHttpSessObj.getObjectById(Sample.class, sampId);
                }
                if (messageNum == 0) {
                    messageNum = tmpHttpSessObj.updateSampleDocuments(sampDocId, filename, sample, fileBytes, comments);
                }
                if (messageNum == 0) {
                    out.println("hi");
                    System.out.println("hi");
                    pageContext.forward(response.encodeURL("./ViewSampleDocumentss.jsp?sampId=" + sampId));
                    return;
                }
                if (messageNum == 1) {
                    pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                    return;
                }

%>

<form method="POST" name="fForm" id="sampleDocForm"
      enctype="multipart/form-data" action="<%=response.encodeURL("./AddSampleDocuments.jsp?sampDocId=" + sampDocId)%>">
    <%if (sampId == null || sampId == "-1") sampId = request.getParameter("sampId");%>
    <%if (sampDocId == null || sampDocId == "-1")sampDocId = request.getParameter("sampDocId");%>
    <a class="largest">Edit Sample Documents</a>
    <br>
    <a class="error">  Error:
        <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
        <%@include file="AddSampleDocumentsE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" class="submitter" name="updateSample" value="Update" onclick="updateSampleGetAround();"/>
                </td>
                <td>
                    <input type="button" name="cancelSam" value="Cancel" size="10" onclick="window.location.assign('<%=response.encodeURL("./ViewSampleDocumentss.jsp?sampId=" + sampId)%>')"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else //from View page
            if ((tmpAction = request.getParameter("sampDocId")) != null) {
                if (tmpAction.equals("-1") || (tmpAction.equals("") && tmpAction == null)) {//Add sample
                    //?????????????????????
                    filename = null;
                    filePath = null;
                    comments = null;

%>

<form method="POST" name="fForm"  id="sampleDocForm"
      enctype="multipart/form-data" action="<%=response.encodeURL("./AddSampleDocuments.jsp?sampId=" + request.getParameter("sampId") + "&sampDocId=" + request.getParameter("sampDocId"))%>">
    <a class="largest">Add Sample Documents</a>
    <br>
    <a class="error">&nbsp;</a>
    <%@include file="AddSampleDocumentsE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" class="submitter" name="addSample" value="  Add  " onclick="addSampleGetAround()"/>
                    <%} else {%>
                    <%=tmpHttpSessObj.getMyMessage(6)%>      <%}%>
                </td>
                <td>
                    <input type="button" name="cancelSam" value="Cancel" size="10" onclick="window.location.assign('<%=response.encodeURL("./ViewSampleDocumentss.jsp?sampId=" + sampId)%>')"/>
                </td>
            </tr>
        </thead>
    </table>
</form><%
                } else {//edit Sample
                    tmpHttpSessObj.setCurrentSampleDocuments(tmpAction);
                    sampDocs = tmpHttpSessObj.getCurrentSampleDocuments();
                    if (sampDocs != null) {
                        selfId = tmpAction;
                        sampDocId = tmpAction;
                        sampId = sampDocs.getSample().getSampleID();
                        comments = sampDocs.getComments();
                        filename = sampDocs.getFilename();
%>

<form method="POST" name="fForm" id="sampleDocForm"
      enctype="multipart/form-data" action="<%=response.encodeURL("./AddSampleDocuments.jsp?sampDocId=" + sampDocId)%>">
    <a class="largest">Edit Sample Documents</a>
    <br>
    <a class="error">&nbsp;</a>
    <%@include file="AddSampleDocumentsE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <input type="submit" class="submitter" name="updateSample" value="Update" onclick="updateSampleGetAround();"/>
                </td>
                <td>
                    <input type="button" name="cancelSam" value="Cancel" size="10" onclick="window.location.assign('<%=response.encodeURL("./ViewSampleDocumentss.jsp?sampId=" + sampId)%>')"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%}
                }
            }%>
<%@include file="Footer.jsp"%>

