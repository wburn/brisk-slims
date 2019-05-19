<%-- 
    Document   : AddListContentsToContainerDNA
    Created on : Oct 12, 2009, 1:12:14 PM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">


<%String lapasId = "AddListContentsToContainer";%>
<%@include file="Header.jsp"%>

<%

        if (tmpHttpSessObj.getCurrentUser() == null) {//test for new session
            System.out.println("Current user is null.");
                // release write lock on list
                tmpHttpSessObj.releaseLockCurrentShoppingList();
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }

        if (tmpHttpSessObj.getCurrentShoppingCartList() == null) {
                // release write lock on list
                tmpHttpSessObj.releaseLockCurrentShoppingList();
            pageContext.forward(response.encodeURL("./ViewLists.jsp"));
            return;
        }

        String tmpString = null;
        int messageNum = 0;

        Plater plater = tmpHttpSessObj.getCurrentPlater();

        ViewContainerContentManager vfm = tmpHttpSessObj.getViewContainerContentManager();

        String containerID = tmpHttpSessObj.getCurrentContainer().getId().toString();
        //String containerID = "1719";
        //String containerID = null;


        if ((tmpString = request.getParameter("listContainerID")) != null) {
            containerID = tmpString;
        }

        // if form has been submitted, perform actions
        if ((tmpString = request.getParameter("createContents")) != null) {

            // get values from form
            String[] volReducs = request.getParameterValues("volBoxRmv");
            double volume = 0;
            double volRed = 0;
            String[] volNewCCs = request.getParameterValues("volBoxNew");
            String[] concenDelNewCCs = request.getParameterValues("concenDilBoxNew");
            String dilution = "";
            String concentration = "-1";
            String[] commentsNewCCs = request.getParameterValues("commentsBoxNew");

            // get their parent's containerContentsIDs
            String[] ccIds = request.getParameterValues("ccIds");
            System.out.println("ccIds length = " + ccIds.length);

            Container container = (Container) tmpHttpSessObj.getObjectById(Container.class, containerID);

            String sampleLayoutScheme = request.getParameter("sampleLayoutScheme");
            if(sampleLayoutScheme == null){
                sampleLayoutScheme = "";
            }
            plater.setOrderToPlate(sampleLayoutScheme);
            int[][] coor = tmpHttpSessObj.getAvailableWells(container,sampleLayoutScheme);
            System.out.println("coor:"+coor);
            System.out.println("coor length:"+coor.length);

            for (int i = 0; i < ccIds.length && messageNum == 0; i++) {
                ContainerContent content = (ContainerContent) tmpHttpSessObj.getObjectById(ContainerContent.class, ccIds[i]);
                if (concenDelNewCCs[i].indexOf(":") >= 0) {
                    dilution = concenDelNewCCs[i];
                } else {
                    dilution = "";
                    try {
                        Double.parseDouble(concenDelNewCCs[i]);
                        concentration = concenDelNewCCs[i];
                    } catch (NumberFormatException nfe) {
                        concentration = "-1";
                    }
                }
                // make containerContents for new container
                MaterialType materialType = (plater.getMaterialType() == null)?content.getMaterialType():plater.getMaterialType();
                messageNum = tmpHttpSessObj.addContainerContent("-1", content, "0", container, String.valueOf(coor[i][0]), String.valueOf(coor[i][1]), content.getSample(), volNewCCs[i], concentration, dilution, commentsNewCCs[i],content.getAmplificationDate(),materialType);
                System.out.println("messageNum="+messageNum);
                if (messageNum == 0) {
                    volume = new Double(content.getVolume());
                    volRed = new Double(volReducs[i]);
                    String finalVol = String.valueOf((volume - volRed));
                    // reduce volumes of original samples
                    System.out.println("messageNum = tmpHttpSessObj.updateContainerContentVolume("+ccIds[i]+", "+finalVol+");");
                    messageNum = tmpHttpSessObj.updateContainerContentVolume(ccIds[i], finalVol);
                    System.out.println("messageNum="+messageNum);
                   }
            }

            System.out.println("messageNum" + messageNum);

            if (messageNum == 0) {
                // update values tracking progress
                System.out.println("1 plated:"+plater.plated+" made"+plater.numberOfPlatesMade);
                plater.plated += ccIds.length;
                plater.numberOfPlatesMade++;
                plater.platesMade.add(container);
                System.out.println("2 plated:"+plater.plated+" made"+plater.numberOfPlatesMade);

                // go back to start if there are more plates to make
                int total = tmpHttpSessObj.getCurrentShoppingCartList().getContainerContentsList().size();
                int contentsDone = plater.plated;
                System.out.println("total"+total+" contentsdone"+contentsDone);

                if(total>contentsDone){
                    pageContext.forward("./HowManyPlates.jsp");
                    return;
                }
                // if all contents are plated, see the plate summary
                pageContext.forward("./PlatingSummary.jsp");
                return;
            } else if (messageNum == 1){
                pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                return;
            }
%>
<form name="fForm" method="POST"  onsubmit="return validateEntries()"
      action="<%= response.encodeURL("./AddListContentsToContainer.jsp?listContainerID=" + containerID)%>"/>

                <br>
    <a class="error">  Error:
    <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
    <%@include file="AddListContentsToContainerE.jsp"%>

</form>

<%} else {
            %>
    <script  type="text/javascript" language="javascript">


    function setFocus(){
        var x = document.getElementById("current")
        if (x) scrollTo(0,x.offsetTop);

        initSlidedHeader();
    }
 function goNav(contId){
    var x = document.getElementById("gotoNav").value;
    if(x == "saveAs"){
    } else if(x == "view"){
        gotoCustomize(contId);
    }
}
    function gotoCustomize(contId ){
        if (checkSubmitFlag()) window.location.assign(
        "<%=response.encodeURL("./EditColumns.jsp")%>"+"?source=volumeEdit&listContainerID="+contId);
    }
    function aIn(image){
        image.src="./images/arrow_b.gif";
    }
    function aOut(image){
        image.src="./images/arrow_a.gif";
    }


    function applyToAll(boxNames){
        var value = document.getElementById(boxNames+"All").value;
        var boxes = document.getElementsByName(boxNames);
        for(var i = 0; i<boxes.length;i++){
            if(boxes[i].disabled==false){
                var ccid = boxes[i].id.substring(9, boxes[i].id.length);
                boxes[i].value = value;
               /* if(boxNames=="volBoxNew" || boxNames=="concenDilBoxNew"){
                calcSources(ccid);
                }
                if(boxNames!="commentsBoxNew" && boxNames!="concenDilBoxNew"){
                calcFinalConcen(ccid);
                }*/
            }
        }
    }
    function applyToAllMax(boxNames,base){
        var value = document.getElementById(boxNames+"All").value;
        var boxes = document.getElementsByName(boxNames);
        for(var i = 0; i<boxes.length;i++){
            if(boxes[i].disabled==false){
                var ccid = boxes[i].id.substring(9, boxes[i].id.length);
                boxes[i].value = value;
                //fillOtherSource(ccid,base);
                //calcFinalConcen(ccid);
            }
        }
    }

    function copyOverValues(){
        var volBoxesRmv = document.getElementsByName("volBoxRmv");
        var volBoxesNew = document.getElementsByName("volBoxNew");
        for(var i = 0; i<volBoxesRmv.length;i++){
            var val = volBoxesRmv[i].value;
            volBoxesNew[i].value = val;
        }
    }

    function checkSufficientVolumes(){
        // ---------- for new volumes -------------------------
        var volBoxesNew = document.getElementsByName("volBoxNew");
        var dnaBoxesNew = document.getElementsByName("dnaBoxesNew");
        // check that they're numeric && > 0
        for(var i = 0; i<volBoxesNew.length;i++){
            if(IsNumeric(volBoxesNew[i].value) == false){
                alert("Error: All volumes must be numeric."+
                    " See red bordered box.");
                volBoxesNew[i].style.border='2px solid red';
                return false;
            } else{
                volBoxesNew[i].style.border='';
            }
        }
        // check that they're numeric && > 0
        for(var i = 0; i<dnaBoxesNew.length;i++){
            if(dnaBoxesNew[i].value!=""
            && (IsNumeric(dnaBoxesNew[i].value) == false)){
                alert("Error: All DNA amounts must be numeric."+
                    " See red bordered box.");
                dnaBoxesNew[i].style.border='2px solid red';
                return false;
            } else{
                dnaBoxesNew[i].style.border='';
            }
        }
        // ------- for 'to be used' volumes ------------
        var volBoxesRmv = document.getElementsByName("volBoxRmv");
        var maxVal = 0;
        var insuffVolume = 0;

        for(var i = 0; i<volBoxesRmv.length;i++){
            // check that they're numeric && > 0
            if(IsNumeric(volBoxesRmv[i].value) == false){
                alert("Error: All volumes must be numeric."+
                    " See red bordered box.");
                volBoxesRmv[i].style.border='2px solid red';
                return false;
            } else{
                volBoxesRmv[i].style.border='';
            }
            // check that each is < = available volume
            maxVal = document.getElementById(volBoxesRmv[i].id+"Max").value;
            var x = volBoxesRmv[i].value
            if( (volBoxesRmv[i].value)*1 > maxVal*1){
                insuffVolume++;
                volBoxesRmv[i].style.border='2px solid red';
                volBoxesRmv[i].value = maxVal;
            } else{
                volBoxesRmv[i].style.border='';
            }
        }

        if(insuffVolume>0){
            alert("Error: Insufficient volume available for "+insuffVolume+" sample(s)."
                + " Volume to be withdrawn must"+
                " be equal to or less then volume of original sample."
                + " Volume(s) to be used have been set to max available. Please see red boxes.");
            return false;
        }
        return true;
    }

    function IsNumeric(strString)
    //  check for valid numeric strings
    {
        var strValidChars = "0123456789.:";
        var strChar;
        var blnResult = true;

        if (strString.length == 0) return false;

        //  test strString consists of valid characters listed above
        for (i = 0; i < strString.length && blnResult == true; i++)
        {
            strChar = strString.charAt(i);
            if (strValidChars.indexOf(strChar) == -1)
            {
                blnResult = false;
            }
        }
        return blnResult;
    }


    function applyOrigToAll(){
        var originals = document.getElementsByName("concenDilOrig");
        var concenDilBoxesNew = document.getElementsByName("concenDilBoxNew");
        for(var i = 0; i<concenDilBoxesNew.length;i++){
            if(originals[i].value == -1.0){
                concenDilBoxesNew[i].value = "";
            }
            else{
                concenDilBoxesNew[i].value = originals[i].value;
            }
        }
    }
    function checkConcenDilutions(){
        var concenDilBoxesNew = document.getElementsByName("concenDilBoxNew");
        var val = 0;
        var bad = 0;
        for(var i = 0; i<concenDilBoxesNew.length;i++){
            val = concenDilBoxesNew[i].value;
            if( val != null && val != "" && IsNumeric(val) == false && val.indexOf(':')<0){
                bad++;
                concenDilBoxesNew[i].style.border='2px solid red';
            }
            else{
                concenDilBoxesNew[i].style.border='';
            }

        }
        if(bad>0){
            alert("Error: Invalid concentration(s) or dilution(s) for "+bad+" sample(s)."
                +" Concentrations must be numeric and dilutons must be writen in the form \"x:xxx\""
                +" (ex: \"1:100\")."
                +" See red box(es).");
            return false;
        }
        else{
            return true;
        }
        return true;
    }

    function validateEntries(){
        var ok = true;
        ok = checkSufficientVolumes();
        if(ok){ok = checkConcenDilutions();}
        if(!ok) return false;
        return true;
    }
    function checkAll(field)
    {
        for (i = 0; i < field.length; i++)
            field[i].checked = true ;
    }
    function uncheckAll(field)
    {
        for (i = 0; i < field.length; i++)
            field[i].checked = false ;
    }
/**
    function toggleSource(ccID){
        var dna = document.getElementById("dnaBoxRmv"+ccID);
        var vol = document.getElementById("volBoxRmv"+ccID);
        if(dna.value.valueOf()!=""){

            calcVol(ccID);
            vol.disabled=true;
            dna.disabled=false;
        }else if(vol.value.valueOf()!=""){
            calcVol(ccID);
            vol.disabled=false;
            dna.disabled=true;
        }
        if(dna.value.valueOf()==""){
            vol.disabled=false;
        } if(vol.value.valueOf()==""){
            dna.disabled=false;
        }

    }
    }*/
                        
    function calc(tocalc){
        // call this for each row
        var boxes = document.getElementsByName(tocalc);
        for(var i = 0; i<boxes.length;i++){
        //for(var i = 0; i<3;i++){
            var boxID = boxes[i].id;
            if(tocalc == "dnaBoxRmv"){
                var ccid = boxID.valueOf().substring(9);
                calcSources(ccid);
                calcFromSources(ccid,'vol');
            }
            if(tocalc == "volBoxRmv"){
                var ccid = boxID.valueOf().substring(9);
                calcSources(ccid);
                calcFromSources(ccid,'dna');
            }
            if(tocalc == "volBoxNew"){
                var ccid = boxID.valueOf().substring(9);
                calcFinalVolume(ccid);
            }
            if(tocalc == "concenDilBoxNew"){
                var ccid = boxID.valueOf().substring(15);
                calcFinalConcen(ccid);
            }
        }
    }                    
    function calcSources(ccid){
        var concen = false;
        var dilu = false;
        // if new vol is blank
        if(document.getElementById("volBoxNew"+ccid) == null){
            return;
        }
        
        if(document.getElementById("volBoxNew"+ccid) != null &&
                document.getElementById("concenDilBoxNew"+ccid) == null){
            var concenDiluNew = "";
            var finalVol = document.getElementById("volBoxNew"+ccid).value;
        }
        else{
            var finalVol = document.getElementById("volBoxNew"+ccid).value;
            var concenDiluNew = document.getElementById("concenDilBoxNew"+ccid).value;
        }
        // if new vol and new concen/dilution are both filled out
        if(finalVol != null && finalVol != "" && finalVol != "0"
            && concenDiluNew != null && concenDiluNew != "" && concenDiluNew*1 != "0"){
            // get original concen/dilution
            var concenWarnString = "<a href=\"#\" class=\"concenWarnTooltip\"><img class=\"concenWarnTooltip\" src=\"img/warning.gif\" alt=\"warning\"><span>Warning: <br>Your final concentration is higher than initial</span></a>";
            var originalString = '<input type="text" size="5" name="concenDilBoxNew" id="concenDilBoxNew'+ccid+'" />';
            var origConcen = document.getElementById("concenOrig"+ccid).value;
            var origDilu = document.getElementById("diluOrig"+ccid).value;
            var diffFactor;
            //if don't know originals, can't calc anything
            if((origDilu == null || origDilu == "" || origDilu.indexOf(":") <=0) &&
                (origConcen == null || origConcen == "" || origConcen*1 == "-1.0")){
                return;
            }
            // get difference factor in concen/dilutions (orig & new)
            // if concentration
            if(origConcen != null && origConcen != "" && origConcen != "-1.0"
                        && concenDiluNew != "" && concenDiluNew != "-1.0"){
                concen = true;
                // don't auto-generate for increases in concentration
                // this is because increases in concentration are rarely done
                // and generates negative volumes which is confusing to
                // scientists.
                if( (origConcen*1)<(concenDiluNew*1) ){
                    var concenWarn = document.getElementById("concenTD"+ccid).innerHTML;
                    alert(concenWarn);
                    if (concenWarn.indexOf("href=")==-1){
                        concenWarn = concenWarn + concenWarnString;
                        document.getElementById("concenTD"+ccid).innerHTML = concenWarn;
                    }
                    return;
                }
                else {
                     document.getElementById("concenTD"+ccid).innerHTML = originalString;
                }
                diffFactor = (concenDiluNew*1)/(origConcen*1) ;
            }
            // if dilution
            else if(origDilu != null && origDilu != "" && origDilu.indexOf(":") >0
                && concenDiluNew.indexOf(":") >0){
                dilu = true;
                //get the x and y separate of x:y
                var vals = origDilu.split(":");
                var lhsO = vals[0]*1;
                var rhsO = vals[1]*1;
                var vals = concenDiluNew.split(":");
                var lhsN = vals[0]*1;
                var rhsN = vals[1]*1;

                // don't auto-generate for increases in concentration
                if( (lhsO*1)<=(lhsN*1) && (rhsO*1)<(rhsN*1) ){
                    var concenWarn = document.getElementById("concenTD"+ccid).innerHTML;
                    if (concenWarn.indexOf(concenWarnString)==-1){
                        concenWarn = concenWarn + concenWarnString;
                        document.getElementById("concenTD"+ccid).innerHTML = concenWarn;
                    }
                    return;
                }
                else {
                     concenWarn = document.getElementById("concenTD"+ccid).innerHTML;
                     concenWarn = concenWarn.replace(concenWarnString,"");
                     document.getElementById("concenTD"+ccid).innerHTML = concenWarn;
                }
                diffFactor = (lhsN/lhsO)/(rhsN/rhsO);
            }
            if(concen||dilu){
                // use it to figure out the needed vol of source
                var volNeeded = (finalVol*1)*(diffFactor*1);
                // populate source fields
                document.getElementById("volBoxRmv"+ccid).value = volNeeded;

                // if concentration, figure out ng of DNA being removed
                if(concen){
                    var dnaNeeded = origConcen * volNeeded;
                    document.getElementById("dnaBoxRmv"+ccid).value = dnaNeeded;
                }
            }
        }
        // if new concen isn't filled in but new vol is
        else if(finalVol != null && finalVol != "" && finalVol*1 != "0"
            && (concenDiluNew == null || concenDiluNew == "" || concenDiluNew != "0")){
            calcFinalConcen(ccid);
        }
    }

    function calcFromSources(ccID,base){
        var dna = document.getElementById("dnaBoxRmv"+ccID);
        var vol = document.getElementById("volBoxRmv"+ccID);
        if(base=="dna"){
            fillOtherSource(ccID,"dna");
            calcFinalConcen(ccID);
        }else if(base=="vol"){
            fillOtherSource(ccID,"vol");
            calcFinalConcen(ccID);
        }
    }
    function fillOtherSource(ccid,base){

        var origConcen = document.getElementById("concenOrig"+ccid);
        var dnaToUse = document.getElementById("dnaBoxRmv"+ccid);
        var volToUse = document.getElementById("volBoxRmv"+ccid);
        if((origConcen.value=="" || origConcen.value=="null"
                    || origConcen.value=="-1.0")){
                    dnaToUse.value="";
                    }
        if((dnaToUse.value!="" || volToUse.value!="")
                && (origConcen.value!="" && origConcen.value!="null"
                    && origConcen.value!="-1.0")){
            if(base=="dna"){
                volToUse.value= (dnaToUse.value.valueOf()*1)/(origConcen.value.valueOf()*1);
            }else if(base=="vol"){
                dnaToUse.value= (volToUse.value.valueOf()*1)*(origConcen.value.valueOf()*1);
            }
        }
    }

    function calcFinalConcen(ccid){
        var origConcen = document.getElementById("concenOrig"+ccid);
        var dnaToUse = document.getElementById("dnaBoxRmv"+ccid);
        var volToUse = document.getElementById("volBoxRmv"+ccid);
        var finalVol = document.getElementById("volBoxNew"+ccid);
        var concenNew = document.getElementById("concenDilBoxNew"+ccid);
        if((dnaToUse.value!="" || volToUse.value!="")
                && (finalVol.value!="" && finalVol.value!="null"
                    && finalVol.value!="-1.0")
                && (origConcen.value!="" && origConcen.value!="null"
                    && origConcen.value!="-1.0")){
            if(dnaToUse.value!=""){
                concenNew.value= (dnaToUse.value.valueOf()*1)/(finalVol.value.valueOf()*1);
            }else if(volToUse.value!=""){
                concenNew.value= (volToUse.value.valueOf()*1)*(origConcen.value.valueOf()*1)/(finalVol.value.valueOf()*1);
            }
        }
    }

    function calcFinalVolume(ccid){
        var origConcen = document.getElementById("concenOrig"+ccid);
        var dnaToUse = document.getElementById("dnaBoxRmv"+ccid);
        var volToUse = document.getElementById("volBoxRmv"+ccid);
        var finalVol = document.getElementById("volBoxNew"+ccid);
        var concenNew = document.getElementById("concenDilBoxNew"+ccid);
        if((dnaToUse.value!="" || volToUse.value!="")
                && (concenNew.value!="" && concenNew.value!="null"
                    && concenNew.value!="-1.0")
                && (origConcen.value!="" && origConcen.value!="null"
                    && origConcen.value!="-1.0")){
            if(dnaToUse.value!=""){
                finalVol.value= (dnaToUse.value.valueOf()*1)/(concenNew.value.valueOf()*1);
            }else if(volToUse.value!=""){
                finalVol.value= (volToUse.value.valueOf()*1)*(origConcen.value.valueOf()*1)/(concenNew.value.valueOf()*1);
            }
        }
    }


/*
    function fillIn(ccid){
        //var origConcenDil = document.getElementById("concenDilOrig"+ccid);
        //var origVol = document.getElementById("volBoxRmv"+ccid+"Max");
        var dnaToUse = document.getElementById("dnaBoxRmv"+ccid);
        var volToUse = document.getElementById("volBoxRmv"+ccid);
        var volFinal = document.getElementById("volBoxNew"+ccid);
        var condilFinal = document.getElementById("concenDilBoxNew"+ccid);
        if((dnaToUse.value!="" || volToUse.value!="")
                && (volFinal.value!=""||condilFinal.value!="")){
            if(dnaToUse.value!=="" && volFinal.value!=""){
                condilFinal.disabled=true;
                condilFinal.value= (dnaToUse.value.valueOf()*1)/(volFinal.value.valueOf()*1);
            }else if(dnaToUse.value!=="" && condilFinal.value!=""){
                volFinal.disabled=true;
                if(condilFinal.value.toString().indexOf(":")>-1){
                    volFinal.value= (dnaToUse.value.valueOf()*1)/(volFinal.value.valueOf()*1);
                }
            }

        }
    }
    */
   function takeMaxDNA(){
       var dnaBoxRmv = document.getElementsByName("dnaBoxRmv");
        var origVol = 0;
        var origConcen = 0;
        for(var i = 0; i<dnaBoxRmv.length;i++){
            // check that each is < = available volume
            //get id
            var ccid = dnaBoxRmv[i].id.substring(9, dnaBoxRmv[i].id.length);
            origVol = document.getElementById("volBoxRmv"+ccid+"Max").value.valueOf();
            origConcen = document.getElementById("concenOrig"+ccid).value.valueOf();
            if(origVol!=null && origVol!=""&& origVol!="-1.0"
                && origConcen!=null && origConcen!=""&& origConcen!="-1.0"){
                dnaBoxRmv[i].value=(origVol*1)*(origConcen*1);
                fillOtherSource(ccid,'dna');
                calcFinalConcen(ccid);
            }
        }
   }
</script>
            <%

            tmpString = null;
            containerID = null;


            if ((tmpString = request.getParameter("listContainerID")) != null) {
                containerID = tmpString;
            }
%>
<form name="fForm" method="POST"  onsubmit="return validateEntries()"
      action="<%= response.encodeURL("./AddListContentsToContainer.jsp?listContainerID=" + containerID)%>"/>

    <%@include file="AddListContentsToContainerE.jsp"%>

</form>
<%}%>
<%@include file="Footer.jsp"%>




