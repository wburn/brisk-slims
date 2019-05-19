<%String lapasId = "ViewSample";%>
<%@include file="Header.jsp"%>

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
            String selfId;
            tmpHttpSessObj.clearInvalidField();
//test new session
            response.setHeader("Cache-Control", "no-store");
            if (tmpHttpSessObj.getCurrentUser() == null) {
                pageContext.forward(response.encodeURL("./LogIn.jsp"));
                return;
            }

// where it was directed from
            String ccID = request.getParameter("ccID");
            if ((tmpAction = request.getParameter("cancelSam")) != null) {
                if (ccID != null) {
                    pageContext.forward(response.encodeURL("./AddContainerContent.jsp") + "?contContId=" + ccID);
                    return;
                }
                pageContext.forward(response.encodeURL("./CAPPS_Results.jsp"));
                return;
            }

//CONTAINER
            String list = request.getParameter("list");
            if ((tmpAction = request.getParameter("cancel")) != null) {
                if (list == null || list.equals("") || list.equalsIgnoreCase("null")) {
                    pageContext.forward(response.encodeURL("./ViewContainers.jsp"));
                    return;
                } // direct to contents creation if making container from list
                else {
                    pageContext.forward(response.encodeURL("./ViewContainersList.jsp"));
                    return;
                }
            }
            if ((tmpAction = request.getParameter("deleteContainer")) != null) {
                selfId = request.getParameter("selfId");
                tmpHttpSessObj.deleteContainer(selfId);
                pageContext.forward(response.encodeURL("./ViewContainers.jsp") + "?del=1");
                return;
            }
//CONTAINERCONTENT
            String viewList;
            String viewContId;
            if ((tmpAction = request.getParameter("cancel")) != null) {
                // get container if cc display is only of contents of a container
                viewContId = request.getParameter("viewContId");
                viewList = request.getParameter("viewList");
                // viewList = "true" when blank value put in input tag
                if (viewList != null && !viewList.equals("") && viewList.indexOf("null") < 0) {
                    response.sendRedirect(("./ViewContainerContentsList.jsp"));
                    return;
                }
                if (viewContId != null && !viewContId.equals("") && viewContId.indexOf("null") < 0) {
                    pageContext.forward(response.encodeURL("./ViewContainerContents.jsp") + "?contId=" + viewContId);
                    return;
                }
                pageContext.forward(response.encodeURL("./ViewContainerContents.jsp"));
                return;
            }
            if ((tmpAction = request.getParameter("deleteContainerContent")) != null) {
                selfId = request.getParameter("selfId");
                tmpHttpSessObj.deleteContainerContent(selfId);
                pageContext.forward(response.encodeURL("./ViewContainerContents.jsp") + "?del=1");
                return;
            }
%>
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

</script>
<%


            int messageNum = 0;
            Sample sample = null;
            String sampId;
            String sampleID;
            String sampleNameRed;
            String sampleNamePurple;
            String valid;
            String parentID;
            String sampleTypeID;
            String sampleProcessID;
            String subjId;
            String subjectID;
            String subjectName;
            String parentFunc;
            Date collectionDate = null;
            Date extractionDate = null;
            String commentsPurple = null;
            String commentsRed = null;

            Sample parent = null;
            Subject subject = null;
            SampleType sampleType = null;
            SampleProcess sampleProcess = null;
//--------------------------------CONTAINER--------------------------
            Container container = null;
            String contId;
            String newPurpSampID = "-1";
            String newRedSampID = "-1";
            String newRedContID = "-1";
            String newPurpContID = "-1";
            String containerNameRed;
            String containerNamePurple;
            String containerTypeID;
            String freezerID;
            String shelf;
            String discarded;
            String shippedOut;
            Date shippedDate;
            String ayear;
            String amonth;
            String aday;
            String shippedToID;
            String isStock;
            String containerAlias;
            String lot;
            String initials;
            Date dateOnContainer;
            String cyear;
            String cmonth;
            String cday;
            String location;
            String barcodeRed;
            String barcodePurple;
            ShippedTo shippedTo = null;
            ContainerType containerType = null;
            Freezer freezer = null;
            //---------------------------------CONTAINER CONTENT--------------------------------------

            ContainerContent containercontent = null;
            String contContId;
            String containerContentsID;
            String parentName;
            String parentRow;
            String parentColumn;
            String contaminated;
            String containerID;
            String row;
            String column;
            String volume;
            String concentration;
            String dilution;
            String materialTypeID;
            Date amplificationDate;
            String comments;

            if ((tmpAction = request.getParameter("addSample")) != null) {
 
                //------------------------------------CONTAINER--------------------------------

                contId = request.getParameter("contId");
//            containerName = request.getParameter("containerName");
                containerAlias = request.getParameter("containerAlias");
                location = request.getParameter("location");
                barcodePurple = request.getParameter("barcodePurple");
                barcodeRed = request.getParameter("barcodeRed");
                containerNameRed = "TestBar" + barcodeRed;
                containerNamePurple = "TestBar" + barcodePurple;
                lot = request.getParameter("lot");
                if (lot == null) {
                    lot = "1";
                }
                valid = request.getParameter("valid");
                containerTypeID = request.getParameter("containerTypeID");
                if (containerTypeID != null && containerTypeID.equals("")) {
                    containerTypeID = null;
                }
                freezerID = request.getParameter("freezerID");
                if (freezerID != null && freezerID.equals("")) {
                    freezerID = null;
                }
                initials = request.getParameter("initials");
                if (initials != null && initials.equals("")) {
                    initials = null;
                }
                shelf = request.getParameter("shelf");
                if (shelf != null && (shelf.equals("") || shelf.equals("null"))) {
                    shelf = null;
                } else {
                    try {
                        Integer.parseInt(shelf);
                    } catch (NumberFormatException e) {
                        messageNum = 10;
                    }
                }
                discarded = request.getParameter("discarded");
                shippedOut = request.getParameter("shippedOut");
                if (shippedOut != null && !(shippedOut.equals("1") || shippedOut.equals("0"))) {
                    shippedOut = "0";
                }
                shippedToID = request.getParameter("shippedToID");
                if (shippedToID != null && shippedToID.equals("")) {
                    shippedToID = null;
                }
                ayear = request.getParameter("ayear");
                amonth = request.getParameter("amonth");
                aday = request.getParameter("aday");
                shippedDate = tmpHttpSessObj.getDate(ayear, amonth, aday,
                        "ayear", "amonth", "aday");
                if (shippedDate == null && shippedOut.equals("1")) {
                    messageNum = 5;
                }

                if (shippedOut == null || shippedOut.equals("0")) {
                    shippedDate = null;
                    shippedToID = null;
                }



                cyear = request.getParameter("cyear");
                cmonth = request.getParameter("cmonth");
                cday = request.getParameter("cday");
                dateOnContainer = tmpHttpSessObj.getDate(cyear, cmonth, cday,
                        "cyear", "cmonth", "cday");


                commentsRed = request.getParameter("commentsRed");
                if (commentsRed != null && commentsRed.equals("")) {
                    commentsRed = null;
                }

                commentsPurple = request.getParameter("commentsPurple");
                if (commentsPurple != null && commentsPurple.equals("")) {
                    commentsPurple = null;
                }

                isStock = request.getParameter("isStock");

                            if (contId == null || containerNameRed == null || containerNamePurple == null || containerTypeID == null ||
                shippedOut == null || discarded == null) {
                messageNum = 9;
                }

                if (messageNum == 0) {
                    shippedTo = (ShippedTo) tmpHttpSessObj.getObjectById(ShippedTo.class, shippedToID);
                    containerType = (ContainerType) tmpHttpSessObj.getObjectById(ContainerType.class, containerTypeID);
                    freezer = (freezerID != null) ? (Freezer) tmpHttpSessObj.getObjectById(Freezer.class, freezerID) : null;
                }

                if (messageNum == 0) {
                    messageNum = tmpHttpSessObj.addContainer(contId, containerNamePurple,
                            containerType, freezer, shelf, discarded, commentsPurple, isStock, valid, containerAlias, lot, initials, dateOnContainer, location, barcodePurple);
                    if (messageNum == 0) {
                        newPurpContID = tmpHttpSessObj.getCurrentContainerId().toString();
                    }
                    messageNum = tmpHttpSessObj.addContainer(contId, containerNameRed,
                            containerType, freezer, shelf, discarded, commentsRed, isStock, valid, containerAlias, lot, initials, dateOnContainer, location, barcodeRed);
                    if (messageNum == 0) {
                        newRedContID = tmpHttpSessObj.getCurrentContainerId().toString();
                    }
                }

                if (messageNum == 1) {
                    pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                    return;
                }
//--------------------------------------------SAMPLES----------------------------------------------------------------
                               sampId = request.getParameter("sampId");
                sampleNamePurple = request.getParameter("sampleNamePurple");
                sampleNameRed = request.getParameter("sampleNameRed");
                valid = request.getParameter("valid");
                parentID = request.getParameter("parentID");
                sampleTypeID = request.getParameter("sampleTypeID");
                sampleProcessID = request.getParameter("sampleProcessID");
                subjectID = request.getParameter("subjectID");
                subjectName = request.getParameter("subjectName");
                subjId = request.getParameter("subjId");

                if (parentID == null || parentID.equals("")) {
                    parentID = null;
                }
                if (sampleTypeID == null || sampleTypeID.equals("")) {
                    sampleTypeID = null;
                }
                if (sampleProcessID == null || sampleProcessID.equals("")) {
                    sampleProcessID = null;
                }

                if (sampId == null || sampId.equals("")
                        || sampleNameRed == null || sampleNameRed.equals("")
                        || sampleNamePurple == null || sampleNamePurple.equals("")
                        || valid == null || valid.equals("")
                        || subjectID == null || subjectID.equals("")
                        || subjId == null || subjId.equals("")) {
                    messageNum = 9;
                }
                if (parentID != null) {
                    try {
                        Integer.parseInt(parentID);
                    } catch (NumberFormatException e) {
                        messageNum = 15;
                    }
                }
                if (messageNum == 0) {
                    if (parentID != null && parentID.length() > 0 && parentID.indexOf("null") < 0) {
                        parent = (Sample) tmpHttpSessObj.getObjectById(Sample.class, parentID);
                    }
                    subject = (Subject) tmpHttpSessObj.getObjectById(Subject.class, subjectID);
                    if (sampleTypeID != null && sampleTypeID.length() > 0 && sampleTypeID.indexOf("null") < 0) {
                        sampleType = (SampleType) tmpHttpSessObj.getObjectById(SampleType.class, sampleTypeID);
                    }
                    if (sampleProcessID != null && sampleProcessID.length() > 0 && sampleProcessID.indexOf("null") < 0) {
                        sampleProcess = (SampleProcess) tmpHttpSessObj.getObjectById(SampleProcess.class, sampleProcessID);
                    }

                }
                if (messageNum == 0) {
                    messageNum = tmpHttpSessObj.addSample(sampId, sampleNamePurple, valid, parent, sampleType, subject, collectionDate, extractionDate, commentsPurple);
                    if (messageNum == 0) {
                        newRedSampID = tmpHttpSessObj.getCurrentSampleId().toString();
                    }
                    messageNum = tmpHttpSessObj.addSample(sampId, sampleNameRed, valid, parent, sampleType, subject, collectionDate, extractionDate, commentsRed);
                    if (messageNum == 0) {
                        newPurpSampID = tmpHttpSessObj.getCurrentSampleId().toString();
                    }
                }
                if (messageNum == 0) {
                    if (ccID != null) {
                        pageContext.forward(response.encodeURL("./AddContainerContent.jsp") + "?contContId=" + ccID);
                        return;
                    }
                } else if (messageNum == 1) {
                    pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                    return;
                }
//--------------------------------------------CONTAINER CONTENTS------------------------------------------------------

                // get container if cc display is only of contents of a container
                viewContId = request.getParameter("viewContId");
                viewList = request.getParameter("viewList");

                contContId = request.getParameter("contContId");
                parentName = request.getParameter("parentName");
                parentFunc = request.getParameter("parentFunc");
                parentRow = request.getParameter("parentRow");
                parentColumn = request.getParameter("parentColumn");
                contaminated = request.getParameter("contaminated");

                materialTypeID = request.getParameter("materialTypeID");

                ayear = request.getParameter("ayear");
                amonth = request.getParameter("amonth");
                aday = request.getParameter("aday");
                amplificationDate = tmpHttpSessObj.getDate(ayear, amonth, aday,
                        "ayear", "amonth", "aday");
                // if WGA, need amplification date
                if (amplificationDate == null && materialTypeID.equals("2")) {
                    messageNum = 5;
                }

                row = request.getParameter("row");
                column = request.getParameter("column");
                volume = request.getParameter("volume");
                concentration = request.getParameter("concentration");
                if (concentration != null && concentration.equals("")) {
                    concentration = "-1";
                }
                dilution = request.getParameter("dilution");
                if (dilution != null && dilution.equals("")) {
                    dilution = null;
                }
                comments = request.getParameter("comments");
                if (comments != null && (comments.equals("") || comments.equals("null"))) {
                    comments = null;
                }

                if (row != null && row.equals("")) {
                    row = null;
                } else {
                    try {
                        Integer.parseInt(row);
                    } catch (NumberFormatException e) {
                        messageNum = 16;
                    }
                }
                if (column != null && column.equals("")) {
                    column = null;
                } else {
                    try {
                        Integer.parseInt(column);
                    } catch (NumberFormatException e) {
                        messageNum = 16;
                    }
                }
                if (volume != null && volume.equals("")) {
                    volume = null;
                } else {
                    try {
                        Double.parseDouble(volume);
                    } catch (NumberFormatException e) {
                        messageNum = 16;
                    }
                }
                if (concentration != null && concentration.equals("")) {
                    concentration = "-1";
                } else {
                    try {
                        Double.parseDouble(concentration);
                    } catch (NumberFormatException e) {
                        messageNum = 16;
                    }
                }
                if (parentRow != null && parentRow.equals("")) {
                    parentRow = null;
                } else {
                    try {
                        Integer.parseInt(parentRow);
                    } catch (NumberFormatException e) {
                        messageNum = 16;
                    }
                }
                if (parentColumn != null && parentColumn.equals("")) {
                    parentColumn = null;
                } else {
                    try {
                        Integer.parseInt(parentColumn);
                    } catch (NumberFormatException e) {
                        messageNum = 16;
                    }
                }
                if (row == null || row.equals("")
                        || column == null || column.equals("")
                        || volume == null || volume.equals("")) {
                    messageNum = 9;
                }

                if (parentID != null && parentID.equals("")) {
                    parentID = null;
                }
                if (parentName != null && parentName.equals("")) {
                    parentName = null;
                }
                if (messageNum == 0) {
                    Container containerRed = (Container) tmpHttpSessObj.getObjectById(Container.class, newRedContID);
                    Container containerPurp = (Container) tmpHttpSessObj.getObjectById(Container.class, newPurpContID);
                    HashMap keys = new HashMap();
                    keys.put("container.containerName", "'" + parentName + "'");
                    keys.put("row", parentRow);
                    keys.put("column", parentColumn);
                    System.out.println("keys" + keys.toString());
                    ContainerContent contParent = (parentID != null) ? ((ContainerContent) tmpHttpSessObj.getObjectById(ContainerContent.class, parentID)) : ((ContainerContent) tmpHttpSessObj.getObjectByUniqueKey(ContainerContent.class, keys));
                    System.out.println("contParent" + contParent);
                    Sample sampleRed = (Sample) tmpHttpSessObj.getObjectById(Sample.class, newRedSampID);
                    Sample samplePurp = (Sample) tmpHttpSessObj.getObjectById(Sample.class, newPurpSampID);
                    MaterialType materialType = (MaterialType) tmpHttpSessObj.getObjectById(MaterialType.class, materialTypeID);

                    if ((parentName != null || parentRow != null || parentColumn != null) && contParent == null) {
                        messageNum = 17;
                    }
                    if (messageNum == 0) {
                        messageNum = tmpHttpSessObj.addContainerContent(contContId, contParent, contaminated, containerRed, row, column, sampleRed, volume, concentration, dilution, comments, amplificationDate, materialType);
                        messageNum = tmpHttpSessObj.addContainerContent(contContId, contParent, contaminated, containerPurp, row, column, samplePurp, volume, concentration, dilution, comments, amplificationDate, materialType);
                    }

                }
                if (messageNum == 0) {
                    pageContext.forward(response.encodeURL("./CAPPS_Results.jsp"));
                    return;
                } else if (messageNum == 1) {
                    pageContext.forward(response.encodeURL("./Test_JSP_error.jsp"));
                    return;
                }
%>
<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddCAPPS.jsp")%>">
    <%--    <input type="hidden" name="ccID" value="<%=ccID%>"/>--%>
    <a class="largest">Sample Creation</a>
    <br>
    <a class="error">  Error:
        <%=tmpHttpSessObj.getMyMessage(messageNum)%></a>
        <%@include file="AddCAPPSE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addSample" value="  Add  " onclick="return validate();"/>
                    <%} else {%>
                    <%=tmpHttpSessObj.getMyMessage(6)%>      <%}%>
                </td>
                <td>
                    <input type="submit" name="cancelSam" value="Cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form>
<%} else //from View page
            if ((tmpAction = request.getParameter("sampId")) != null) {
                if (tmpAction.equals("-1")) {//Add sample
                    //?????????????????????
                    sampId = null;
                    sampleNameRed = null;
                    sampleNamePurple = null;
                    valid = null;
                    parentID = null;
                    sampleTypeID = null;
                    sampleProcessID = null;
                    subjectID = null;
                    collectionDate = null;
                    extractionDate = null;
                    commentsRed = null;
                    commentsPurple = null;
                    subjectName = null;

                    if ((tmpAction = request.getParameter("contId")) != null) {
                        System.out.println("2 in AddContainer.jsp");
                        if (tmpAction.equals("-1")) {//Add container
                            contId = null;
                            containerNameRed = null;
                            containerNamePurple = null;
                            containerTypeID = null;
                            freezerID = null;
                            shelf = null;
                            discarded = null;
                            shippedOut = null;
                            shippedDate = null;
                            shippedToID = null;
                            isStock = null;
                            containerAlias = null;
                            lot = null;
                            initials = null;
                            dateOnContainer = null;
                            location = null;
                            barcodeRed = null;
                            barcodePurple = null;

                            Date myDate = new Date();
                            ayear = Util.getYear(myDate);
                            amonth = Util.getMonth(myDate);
                            aday = Util.getDay(myDate);
                            cyear = Util.getYear(myDate);
                            cmonth = Util.getMonth(myDate);
                            cday = Util.getDay(myDate);

                            list = (request.getParameter("list") != null) ? request.getParameter("list") : null;

                            System.out.println("end of code block in AddContainer.jsp");

                            System.out.println("contId: " + contId);
                            if ((tmpAction = request.getParameter("contContId")) != null) {
                                if (tmpAction.equals("-1")) {//Add containercontent
                                    //?????????????????????
                                    contContId = null;
                                    parentID = null;
                                    parentName = null;
                                    parentRow = null;
                                    parentColumn = null;
                                    contaminated = null;
                                    parentFunc = null;
                                    row = null;
                                    column = null;
                                    volume = null;
                                    concentration = null;
                                    dilution = null;
                                    comments = null;
                                    materialTypeID = null;
                                    amplificationDate = null;
                                    tmpAction = request.getParameter("subjId");
                                    tmpHttpSessObj.setCurrentSubject(tmpAction);
                                    subject = tmpHttpSessObj.getCurrentSubject();
                                    if (subject != null) {
                                        selfId = tmpAction;
                                        subjId = subject.getVisibleName();
                                        subjId = request.getParameter(" subjId");
                                        subjId = subject.getSubjectID();
                                        subjectName = subject.getSubjectName();
                                        parentFunc = subjectName.substring(subjectName.length() - 1);
                                        if (parentFunc.equals("3") || parentFunc.equals("4")) {
                                            parentFunc = "Mother";
                                        } else if (parentFunc.equals("5")) {
                                            parentFunc = "Father";
                                        } else if (parentFunc.equals("1") || parentFunc.equals("2")) {
                                            parentFunc = "Child";
                                        } else {
                                            parentFunc = "Subject ID does not match naming convention";
                                        }

%>

<form method="POST" name="fForm" onsubmit="return checkSubmitFlag()"
      action="<%=response.encodeURL("./AddCAPPS.jsp")%>">
    <%--    <input type="hidden" name="ccID" value="<%=ccID%>"/> --%>
    <a class="largest">Sample Creation</a>
    <br>
    <a class="error">&nbsp;</a>
    <%@include file="AddCAPPSE.jsp"%>
    <table cellpadding="4">
        <thead>
            <tr>
                <td>
                    <%if (tmpHttpSessObj.isAdvancedUserUp()) {%>
                    <input type="submit" name="addSample" value="  Add  "  onclick="return (barVal());"/>
                    <%} else {%>
                    <%=tmpHttpSessObj.getMyMessage(6)%>      <%}%>
                </td>
                <td>
                    <input type="submit" name="cancelSam" value="Cancel" size="10"/>
                </td>
            </tr>
        </thead>
    </table>
</form><%
                                    }
                                }
                            }
                        }
                    }
                }
            }%>
<%@include file="Footer.jsp"%>

