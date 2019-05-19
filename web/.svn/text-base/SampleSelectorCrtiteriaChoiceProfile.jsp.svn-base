<%--
    Document   : selectorCriteriaChoice
    Created on : Oct 20, 2009, 1:17:15 PM
    Author     : tvanrossum
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%String lapasId = "SampleSelectorCriteriaChoiceProfile";%>

<%@include file="Header.jsp"%>

<%
        tmpHttpSessObj.clearInvalidField();

//test new session
        if (tmpHttpSessObj.getCurrentUser() == null) {
            pageContext.forward(response.encodeURL("./LogIn.jsp"));
            return;
        }


        if(tmpHttpSessObj.getCurrentShoppingCartList()==null){
            pageContext.forward(response.encodeURL("./ViewLists.jsp"));
            return;
        }

        tmpHttpSessObj.clearInvalidField();
        String tmpAction = null;

        int level = -1;

        // if pressed cancel on this page, reset everything and go back to list
        if((tmpAction=request.getParameter("cancel"))!=null){
            tmpHttpSessObj.setCurrentSampleSelector(null);
            response.sendRedirect("./ViewSubjectsList.jsp");
            return;
        }
        // from very first selection
        if((tmpAction=request.getParameter("criteria1"))!=null
                && tmpAction.indexOf("header")<0){
            String criteriaKey = request.getParameter("criteria1");
            response.sendRedirect("./SampleSelectorCriteriaSubChoice.jsp"+"?criteria="+criteriaKey);
            return;
        }
        // all selections after first
        if((tmpAction=request.getParameter("criteria2"))!=null
                && tmpAction.indexOf("header")<0){
            String criteriaKey = request.getParameter("criteria2");
            response.sendRedirect("./SampleSelectorCriteriaSubChoice.jsp"+"?criteria="+criteriaKey);
            return;
        }

        SampleSelector selector = null;
        //  if selection process hasn't begun, make a new selector object
        if(tmpHttpSessObj.getCurrentSampleSelector()==null){
            System.out.println("initializing selector");
            selector = new SampleSelector(tmpHttpSessObj);
            tmpHttpSessObj.setCurrentSampleSelector(selector);//sets current level = 0

        } else{
            selector = tmpHttpSessObj.getCurrentSampleSelector();
            //if returned from subchoice via 'next'
            if((tmpAction=request.getParameter("next"))!=null){
            System.out.println("returned from subchoice via 'next'");

                // save whatever the user put for their criteria specification
                level = selector.getCurrentLevel();
                System.out.println("selector.getCurrentLevel()="+level);
                String criteria = request.getParameter("criteriaKey");

                ArrayList options = null;
                if((tmpAction = request.getParameter("noInput"))!=null){
                    System.out.println("in noInput");
                    options = new ArrayList();
                    options.add("noInput");
                    //selector.addToSpecificationProfile(level, noInput);
                }
                // save after & before dates (all date fields)
                else if((tmpAction = request.getParameter("dates"))!=null){
                    System.out.println("in dates");
                    String ayear = request.getParameter("dateayear");
                    String amonth = request.getParameter("dateamonth");
                    String aday = request.getParameter("dateaday");
                    String byear = request.getParameter("datebyear");
                    String bmonth = request.getParameter("datebmonth");
                    String bday = request.getParameter("datebday");

                    Date dateA = Util.getLowDate(ayear, amonth, aday);
                    Date dateB = Util.getHighDate(byear, bmonth, bday);

                    String strict = request.getParameter("dateStrictCheck");
                    // if only considering samples withing this span
                    if(strict!=null && strict.equals("true")){
                        System.out.println("in dates if");
                        ArrayList in = new ArrayList(3);
                        in.add(Util.truncateDateSQLFormat(dateA));
                        in.add(Util.truncateDateSQLFormat(dateB));
                        in.add("in");

                        options = new ArrayList();
                        options.add(in);
                    }
                    // if considering samples outside of this span as well (with lower preference)
                    else{
                        System.out.println("in dates else");
                        ArrayList in = new ArrayList(3);
                        ArrayList outside = new ArrayList(3);
                        in.add(Util.truncateDate(dateA));
                        in.add(Util.truncateDate(dateB));
                        in.add("in");
                        outside.add(Util.truncateDateSQLFormat(dateA));
                        outside.add(Util.truncateDateSQLFormat(dateB));
                        outside.add("out");

                        options = new ArrayList();
                        options.add(in);
                        options.add(outside);
                    }

                }
                // save selected order or values of options
                // (sampleType & materialType)
                else if((tmpAction = request.getParameter("discreet"))!=null){
                    String[] specs = request.getParameterValues("specifics");
                    options = new ArrayList();
                    for(int i=0;i<specs.length;i++){
                        options.add(specs[i]);
                    }
                }
                // ("set minimum" physical property fields)
               else if((tmpAction = request.getParameter("minimum"))!=null){
                    String minVal = request.getParameter("minVal");
                    String notConsiderFailed = request.getParameter("minimumCheck");
                    if(notConsiderFailed != null && notConsiderFailed.equals("true")){
                        options = new ArrayList();
                        options.add(">="+minVal);
                    }else{
                        options = new ArrayList();
                        options.add(">="+minVal);
                        options.add("<"+minVal);
                    }
                } // set call rate details
               else if((tmpAction = request.getParameter("callRate"))!=null){

                    // callRateWGACheck was removed, considerWGA will always be true
                    String considerWGA = request.getParameter("callRateWGACheck");

                    String dontConsiderLess = request.getParameter("callRateStrictCheck");
                    if(dontConsiderLess != null && dontConsiderLess.equals("false")){
                        options = new ArrayList();
                        ArrayList arr1 = new ArrayList(2);
                        ArrayList arr2 = new ArrayList(2);
                        arr1.add(">="+tmpAction);
                        arr1.add(considerWGA);
                        arr2.add("<"+tmpAction);
                        arr2.add(considerWGA);

                        options.add(arr1);
                        options.add(arr2);
                    }else{
                        options = new ArrayList();
                        ArrayList arr1 = new ArrayList(2);
                        arr1.add(">="+tmpAction);
                        arr1.add(considerWGA);
                        options.add(arr1);
                    }
                } // set dilution details
               else if((tmpAction = request.getParameter("dilution"))!=null){
                   String[] dilutions = request.getParameterValues("dilutionValues");
                    options = new ArrayList();
                    ArrayList arr1 = new ArrayList(dilutions.length);
                    for(int i=0;i<dilutions.length;i++){
                        arr1.add(dilutions[i]);
                    }
                    options.add(arr1);
                }

                System.out.println("POSITIONTRACKER before store:");
                for(int i = 0; i<selector.positionTracker.size();i++){
                    System.out.println(i+": "+((Integer[]) selector.positionTracker.get(i))[0]
                        +" of "+((Integer[]) selector.positionTracker.get(i))[1]);
                }
                System.out.println("positionTracker.size():"+selector.positionTracker.size());
                System.out.println("level:"+level);
                int leafLevel = level;
                System.out.println("leafLevel:"+leafLevel);

                // enter option info

                //if(selector.positionTracker.size()<=leafLevel+1){
                // if adding first
                if(selector.positionTracker.size() == 0){
                // store options
                selector.storeOptions(options, criteria, selector.getPositionTrackerStateArrayList());
                // update position tracker
                System.out.println("adding tracker1, level: "+leafLevel);
                        Integer[] arr = {0,options.size()};
                        selector.positionTracker.add(leafLevel, arr);
                }
                else{
                    selector.storeOptions(options, criteria, selector.getPositionTrackerStateArrayList());
                    // if new level, add
                    if(selector.positionTracker.size() <= leafLevel ||
                            selector.positionTracker.get(leafLevel) == null){
                        System.out.println("adding tracker2, level: "+leafLevel);
                        Integer[] arr = {0,options.size()};
                        selector.positionTracker.add(leafLevel, arr);
                    }
                }
                System.out.println("POSITIONTRACKER before printing:");
                for(int i = 0; i<selector.positionTracker.size();i++){
                    System.out.println(i+": "+((Integer[]) selector.positionTracker.get(i))[0]
                        +" of "+((Integer[]) selector.positionTracker.get(i))[1]);
                }
                    // --------- printing -------------
                    System.out.println("printing options sets");
                    //selector.printOptionsStore();
                    ArrayList optionSet = new ArrayList();
                    selector.printTracker.clear();
                    for(int i = 0; i<=level;i++){
                        selector.printTracker.add(0);
                    }
                    int j = 0;
                    while(optionSet !=null && j<5){
                        optionSet = selector.getNextOptionSet();
                       System.out.println(optionSet);
                       j++;
                    }
                    selector.noMoreOptionSets=false;
                    // -------------------------------


                // update current level
                selector.setCurrentLevel(selector.getCurrentLevel()+1);

                //System.out.println("level"+level);
                //System.out.print(selector.recursiveWrite(0));
            }
            //if returned from subchoice via 'next'
            else if((tmpAction=request.getParameter("back"))!=null){
                // remove just-stored selected criteria in criteria profile
                level = selector.getCurrentLevel();
                selector.removeCriteriaProfile(level);
            }
            //if returned from this page choice via 'done branch'
            // if branch is finished (no more criteria) or 'finish branch' has been pressed
            else if((tmpAction=request.getParameter("doneBranch"))!=null){
                System.out.println("done branch");
                // current level should be set to the most recent with >1 option

                int leafLevel = selector.getCurrentLevel() - 1;

                System.out.println("level: "+(level));
                System.out.println("level-1: "+(leafLevel));
                System.out.println("POSITIONTRACKER BEFORE:");
                for(int i = 0; i<selector.positionTracker.size();i++){
                    System.out.println(i+": "+((Integer[]) selector.positionTracker.get(i))[0]
                        +" of "+((Integer[]) selector.positionTracker.get(i))[1]);
                }

                // get index of current option for parent level
                int optionIndex = ((Integer[]) selector.positionTracker.get(leafLevel))[0];
                // get total number of options for parent level
                int optionTotal = ((Integer[]) selector.positionTracker.get(leafLevel))[1];

                System.out.println("done branch 1");
                System.out.println("optionIndex"+optionIndex);
                System.out.println("optionTotal"+optionTotal);

                // if out of options for this level
                if(optionIndex == optionTotal-1){
                // move up levels until you find one with options left to define
                while(leafLevel>=0 && optionIndex == optionTotal - 1){
                    // if you're already at level 0 and you've finished all your options, you're done!
                    if(leafLevel == 0){
                        System.out.println("-----FINISHED-----");
                        //  finished defining selection
                        response.sendRedirect("./SampleSelectorFetch.jsp");
                        return;
                    }
                    else{
                            System.out.println("POSITIONTRACKER DURING");
                for(int i = 0; i<selector.positionTracker.size();i++){
                    System.out.println(i+": "+((Integer[]) selector.positionTracker.get(i))[0]
                        +" of "+((Integer[]) selector.positionTracker.get(i))[1]);
                }
                            // every time you move up a level, have to reset the
                            // option tracking for the levels below
                            selector.positionTracker.remove(leafLevel);

                System.out.println("done branch 2, leafLevel:"+leafLevel);
                            // move up a level
                            leafLevel--;
                            // get index of current option for this level
                            optionIndex = ((Integer[]) selector.positionTracker.get(leafLevel))[0];
                            // get total number of options for this level
                            optionTotal = ((Integer[]) selector.positionTracker.get(leafLevel))[1];

                        }

                System.out.println("done branch 3");
                        // update current level
                        selector.setCurrentLevel(leafLevel+1);
                        // update option index
                        Integer[] arr = (Integer[])selector.positionTracker.get(leafLevel);
                        arr[0]++;
                        selector.positionTracker.set(leafLevel, arr);
                    }
                }else{
                    // if still have options at this level, go to the next one
                    Integer[] arr = (Integer[])selector.positionTracker.get(leafLevel);
                    arr[0]++;
                    selector.positionTracker.set(leafLevel, arr);
                }
                System.out.println("POSITIONTRACKER AFTER");
                for(int i = 0; i<selector.positionTracker.size();i++){
                    System.out.println(i+": "+((Integer[]) selector.positionTracker.get(i))[0]
                        +" of "+((Integer[]) selector.positionTracker.get(i))[1]);
                }
                selector.increaseProfileNumber(1);
            }
        }
        selector.printSpecificationProfile("crit choice begin");
        level = selector.getCurrentLevel();
        System.out.println("level: "+level);
        LinkedHashMap criteria = (LinkedHashMap) selector.getAvailCriteriaForLevel(level);
        int numCriteria = criteria.size();
        System.out.println("level: "+level);
        selector.updateHighestNumLevels(level);
%>

<html>

<form method="POST" name="criterionSelectForm"
          action="<%=response.encodeURL("./SampleSelectorCriteriaChoiceProfile.jsp")%>">

        <table class="navigate">
            <tr>
                <td class="left" align="left" colspan="2"><a class="largest">
                    Prepare Selection Criteria</a></td>
                </tr>

            <tr><td colspan="5"><table class="instructions"><tr><td>

                This tool uses the active list of subjects to retrieve the
                best sample for each subject based on
                a hierarchy of selection profiles that you can define below. <br><br>

                <a style="font-weight:bold">Instructions:</a><br>
                Your profiles are displayed below and updated as you define them.
                To get started, select a criterion from the drop-down box for the first level of your first profile.
                (Scroll down to see descriptions of each option.) If further specifications for your choice
                are required, you will be brought to a sub-criterion definition screen. Continue selecting criteria for your first
                profile until you are satisfied with it. Once finished, click 'Finish Profile'
                button to finish your current profile and move on to the next. Repeat the same steps as with your first profile.
                When you have finished all your profiles, you will have completed the definition stage of sample selection.
 </td></tr></table></td></tr>

            <tr>
                <td>

</td>
<td>

              <script language="JavaScript" type="text/javascript">
function goBack(){
  if (checkSubmitFlag()) window.location.assign(
         "<%=response.encodeURL("./ViewSubjectsList.jsp")%>");
}
function checkSelect(elem){
        document.criterionSelectForm.submit();
    // if option selected is a header option, do nothing when 'next' is pressed
    if(elem.value.valueOf().indexOf("header")==0){
        alert("Error: Header selected.");
    }
    // if nothing selected
    else if(elem.value.valueOf()==""){
        alert("Error: you must select a criteria to continue.");
    }
    else{
        document.criterionSelectForm.submit();
    }
}

</script>

        <table>
        <tr><td>
        <% // draw diagram of selections so far
           // --------- printing -------------
            System.out.println("printing options sets page");
            //selector.printOptionsStore();
            ArrayList optionSet = new ArrayList();
            selector.printTracker.clear();
            if(level>0){

            %><fieldset>
            <table style="border-spacing:0pt" cellspacing="0">
                <tr><td class="larger" colspan="2">Current Selection Profiles:</td></tr>
                <tr><td>&nbsp;</td></tr>
                <%

            %><tr><td style="padding-top:1em;">Most Prefered</td><%
            int max = selector.getHighestNumLevels();
            for(int i = 0; i< max+1; i++){
            %><td style="border-right:gray thin dotted;border-left:gray thin dotted; padding-left:1em;padding-top:1em;border-bottom:black thin solid;">
                Level <%=i+1%></td><%
            }

            int row=0;
            // 'max-level' number of zeros
            for(int i = 0; i<=max;i++){
                selector.printTracker.add(0);
            }

            optionSet = selector.getNextOptionSet();
            while(optionSet !=null){
            row++;

            // highlight current row that user is defining
            int pos = 0;
            System.out.print("positionTracker: (");
            int j = 0;
            for(j = 0; j<selector.positionTracker.size();j++){
                pos = pos +((selector.positionTracker.get(j))[0]) + 1;
                System.out.print(((Integer[]) selector.positionTracker.get(j))[0]+", ");
            }
            System.out.print(")\n");
            System.out.println("pos="+pos);

            System.out.print("printTracker: (");
            for(int i = 0; i<selector.printTracker.size();i++){
                System.out.print(((Integer) selector.printTracker.get(i))+", ");
            }
            System.out.print(")\n");


            // print out option set in a row
                %><tr>
                    <td style="font-weight:bold;padding-left:1em;padding-right:1em;padding-top:1em;border-bottom:thin black solid">
                   <nobr>Selection Profile <%=row%>:</nobr> </td><%
               Iterator iter = optionSet.iterator();
               int col = 0;
               HashMap realNames = selector.getAllCriteria();
               while(iter.hasNext()){
                   Object[] option = (Object[])iter.next();
                   System.out.println("printing option:"+option[0]+", "+option[1]);
                   String opStr = selector.getReadableOption(option[0].toString(), option[1]);
                   String name = (String) realNames.get(option[0]);
               %><td  style="border-right:gray thin dotted;border-left:gray thin dotted; padding-left:1em;padding-right:1em;padding-top:1em;border-bottom:black thin solid;">
                   <%=name%><%=opStr%>
               </td><%
               col++;
               }
               if(row==selector.getProfileNumber()){%>

<td style="background-color:white;border:thin solid black;vertical-align:middle;padding-left:1em;padding-right:1em;padding-top:0.5em;padding-bottom:0.5em;">
     <nobr>       <select name="criteria1" id="criteria1"  onchange="checkSelect(this)">
                <%Iterator it2 = criteria.keySet().iterator();
                while (it2.hasNext()) {
                // Retrieve key
                String key = (String) it2.next();
                String value = (String) criteria.get(key); %>
                <option value="<%=key%>" id="<%=key%>"><%=value%></option>
                <%}%>
			</select> or
            <input type="submit" class="button" name="doneBranch" value="Finish Profile" >
       </nobr>
       </td>
               <%}
               %></tr><%
                // get next option set
               optionSet = selector.getNextOptionSet();
            }
            %><tr><td style="padding-top:1em;padding-bottom:1em;">Least Prefered</td></tr>

            </table></fieldset><%
            selector.noMoreOptionSets=false;
            // -------------------------------
        }else{
        %>
<fieldset>
<fieldset>
<table style="border-spacing:0px;" cellspacing="0">
<tr><td class="larger" colspan="2">Current Selection Profiles:</td></tr>
<tr><td>&nbsp;</td></tr>
    <tr><td style="padding-top:1em;">Most Prefered</td>
        <td>Level 1</td>
    </tr>
        <tr>
       <td style="border-bottom:black thin solid;font-weight:bold;vertical-align:bottom">
        Selection Profile 1:&nbsp;&nbsp;</td>

        <td style="background-color:white;border:thin solid black;vertical-align:middle;padding-left:1em;padding-right:1em;padding-top:0.5em;padding-bottom:0.5em;">
            <nobr>
            <select name="criteria1" id="criteria1"  onchange="checkSelect(this)">
                <%Iterator it2 = criteria.keySet().iterator();
                while (it2.hasNext()) {
                // Retrieve key
                String key = (String) it2.next();
                String value = (String) criteria.get(key); %>
                <option value="<%=key%>" id="<%=key%>"><%=value%></option>
                <%}%>
			</select> or
            <input type="submit" class="button" name="doneBranch" value="Finish Profile" disabled>
        </nobr>
        </td>
        </tr>
        <tr><td style="padding-top:1em;padding-bottom:1em;">Least Prefered</td></tr>

</table>
</fieldset>
</fieldset>
        <%}%>
       </td></tr>

        </table>

    </td></tr>
    <tr><td align="left" colspan="4">
                <input type="submit" name="cancel" value="Cancel Selection">
                </td></tr>
</form>

</table>
<fieldset>
<a class="larger">Criteria Descriptions</a>
<table border="1" frame="box" style="padding:1em;border-spacing: 0px;height:500px">

    <tr><th>Option</th><th>Description
        </th></tr><tr><td>Sample Type</td><td>Use this option to select and order the biological sources of samples (blood, buccal cells, etc).
        </td></tr><tr><td>Material Type</td><td>Use this option to select and order the physical materials of samples (Genomic (DNA), WGA (DNA), Unextracted (original sample), etc).
        </td></tr><tr><td>Stock Samples Only</td><td>Choose this option to only consider stock tubes (genomic) and stock plates (WGA).
        </td></tr><tr><td>Concentration</td><td>Use this option to control preference of samples based on a cut-off concentration.
        </td></tr><tr><td>Volume</td><td>Use this option to control preference of samples based on a cut-off volume.
        </td></tr><tr><td>Amount of DNA</td><td>Use this option to control preference of samples based on a cut-off amount of DNA in the samples.
        </td></tr><tr><td>Dilution</td><td>Use this option to control which dilution values are acceptable.
        </td></tr><tr><td>Maximize Concentration</td><td>Selecting this option will make a higher concentration sample
                                            preferable to a lower concentration one if both meet all other criteria equally.
        </td></tr><tr><td>Maximize Volume</td><td>Selecting this option will make a higher volume sample
                                            preferable to a lower volume one if both meet all other criteria equally.
        </td></tr><tr><td>Maximize Amount of DNA</td><td>Selecting this option will make a sample with more ng of DNA
                                            preferable to one with less if both meet all other criteria equally.
        </td></tr><tr><td>Previously Genotyped</td><td>Choose this option to only consider samples that have been previously genotyped.
        </td></tr><tr><td>Genotyping Call Rate</td><td>Choose this option to set preference for samples that have been previously genotyped with a call rate abouve a cut-off that you will define.
        </td></tr><tr><td>Never Genotyped</td><td>Choose this option to only consider samples that have never been genotyped.
        </td></tr><tr><td>Collection Date Range</td><td>Use this option to define a prefereable range of collection dates.
        </td></tr><tr><td>Extraction Date Range</td><td>Use this option to define a prefereable range of extraction dates.
        </td></tr><tr><td>Amplification Date Range</td><td>Use this option to define a prefereable range of amplification dates.
        </td></tr><tr><td>Most Recent Collection Date</td><td>Selecting this option will make a sample with a more recent collection date
                                            preferable to one less recent if both meet all other criteria equally.
        </td></tr><tr><td>Most Recent Extraction Date</td><td>Selecting this option will make a sample with a more recent extraction date
                                            preferable to one less recent if both meet all other criteria equally.
        </td></tr><tr><td>Most Recent Amplification Date</td><td>Selecting this option will make a sample with a more recent amplification date
                                            preferable to one less recent if both meet all other criteria equally.

    </td></tr>

</table>
</fieldset>
</html>



<%@include file="Footer.jsp"%>
