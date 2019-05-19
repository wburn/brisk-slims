<%-- 
    Document   : AddSampleDocumentsE
    Created on : May 27, 2010, 10:22:59 AM
    Author     : ATan1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<!--Start of common part of update Sample-->
<%
            MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
%>
<fieldset>
    <p>Note: No two files of a sample can have the same filename.</p>
    <table>
        <thead>
        <tr>
            <td class="dialh"><%--sampleselect:--%>
                             <input type="hidden" id="addsample" name="blah2"/>
        <input type="hidden" id="updatesample" name="blaH"/>
        <input type="hidden" name="sampId"
               value="<%=(sampId != null) ? sampId : "-1"%>"/>
        <input type="hidden" name="sampDocId" id="sampDocId"
               value="<%=(sampDocId != null) ? sampDocId : "-1"%>"/>
                <%=mdm.getContainerContentLongName(Fieldname.SAMPLE)%>*</td>
            <td>
                <select name="sampId" id="sampId" class="required">
                        <%=tmpHttpSessObj.getObjectPrompterUniqueField(Sample.class, sampId, "sampleName", true)%>
                    </select>
            </td>
        </tr>
        <tr>
            <td class="dialh"><%--fileuploader:--%>
                Select a file:</td>
            <td>
                <input size="40" style="font-size: 10pt" class="button" type="file" name="fileToLoad" id="fileToLoad" onchange="getFilename()"/>
            </td>
        </tr>

        <tr>
            <td class="dialh"><%--filename:--%>
                Filename*:</td>
            <td>
                <input type="text" name="filename" id="filename" value="<%=(filename != null) ? filename : ""%>"/>
            </td>
        </tr>

        <tr>
            <td class="dialh"><%--comments:--%>
                Comments:</td>
            <td>
                <textarea cols="40" rows="3" name="comments" id="comments" onkeyup="autoResize()"><%=(comments != null) ? comments : ""%></textarea>
            </td>
        </tr>


        </thead>
    </table>
</fieldset>


