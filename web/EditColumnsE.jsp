<%-- 
    Document   : EditColumnsE
    Created on : Aug 10, 2009, 3:56:29 PM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<script  type="text/javascript" language="javascript">
/*
   function toggleSelect(el){
       var end = (el.id.length)-5
    var prefix=el.id.substring(0,end)
    alert(prefix+'Num')
    document.getElementByID(prefix+'Num').disabled=el.checked?false:true
    //if(document.all){document.all[prefix+'Num'].disabled=el.checked?false:true}
    //else{document.getElementByID(prefix+'Num').disabled=el.checked?false:true}
   }
*/
</script>

<%
        MetadataManager mdm = tmpHttpSessObj.getMetadataManager();
%>

<fieldset>
<table>
    <tr><p>Select the information you would like to view for each sample
    and the relative order of each column in your view scheme. Multiple fields
    with the same selected order number will appear in alphabetical order.
    Viewable information can also be reset to a default based on your role in
    the lab using the 'Reset to Default' button below.
    </p></tr>

</table>
</fieldset>

<fieldset>
<table>
<thead>
    <tr><th>Show/Hide</th><th>Column</th><th>Order</th></tr>
    <%=tmpHttpSessObj.getPrompterForCCMetaDataOrder("amplificationDate","amplificationDate", "Amplification Date")%>

    <%=tmpHttpSessObj.getPrompterForCCMetaDataOrder("barcode","barcode", "Barcode")%>

    <%=tmpHttpSessObj.getPrompterForCCMetaDataOrder("sample.dateCollected","dateCollected", "Collection Date")%>

    <%=tmpHttpSessObj.getPrompterForCCMetaDataOrder("comments","comments", "Comments")%>

    <%=tmpHttpSessObj.getPrompterForCCMetaDataOrder("concentration","concentration", "Concentration (ng/ul)")%>

    <%=tmpHttpSessObj.getPrompterForCCMetaDataOrder("container", "container", "Container Name")%>

    <%=tmpHttpSessObj.getPrompterForCCMetaDataOrder("container.containerType","containerType","Container Type")%>

    <%=tmpHttpSessObj.getPrompterForCCMetaDataOrder("row","row","Container Row")%>

    <%=tmpHttpSessObj.getPrompterForCCMetaDataOrder("column","column","Container Column")%>

    <%=tmpHttpSessObj.getPrompterForCCMetaDataOrder("dilution","dilution", "Dilution")%>


    <%=tmpHttpSessObj.getPrompterForCCMetaDataOrder("sample.extractionDate","extractionDate","Extraction Date")%>

    <%=tmpHttpSessObj.getPrompterForCCMetaDataOrder("container.freezer","freezer","Freezer Name")%>

    <%=tmpHttpSessObj.getPrompterForCCMetaDataOrder("container.shelf","shelf","Freezer Shelf")%>

    <%=tmpHttpSessObj.getPrompterForCCMetaDataOrder("container.location","location","Freezer Location")%>

    <%=tmpHttpSessObj.getPrompterForCCMetaDataOrder("modifDate","modifDate","Last Modification Date")%>

    <%=tmpHttpSessObj.getPrompterForCCMetaDataOrder("materialType","materialType","Material Type (Genomic or WGA)")%>
    
    <%=tmpHttpSessObj.getPrompterForCCMetaDataOrder("parent","parent","Parent Sample")%>

    <%=tmpHttpSessObj.getPrompterForCCMetaDataOrder("sample","sample","Sample ID")%>

    <%=tmpHttpSessObj.getPrompterForCCMetaDataOrder("sample.sampleType","sampleType","Sample Type (Blood, Buccal etc.)")%>

    <%=tmpHttpSessObj.getPrompterForCCMetaDataOrder("container.isStock","isStock","Stock Material?")%>

    <%=tmpHttpSessObj.getPrompterForCCMetaDataOrder("contaminated","contaminated", "Contaminated?")%>

    <%=tmpHttpSessObj.getPrompterForCCMetaDataOrder("volume","volume","Volume (ul)")%>



</thead>
</table>
</fieldset>

