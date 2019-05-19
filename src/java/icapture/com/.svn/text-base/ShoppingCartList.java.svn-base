/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icapture.com;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import icapture.hibernate.ShoppingList;
import icapture.hibernate.ShoppingListContainerContent;
import icapture.hibernate.ShoppingListContainer;
import icapture.hibernate.ShoppingListSample;
import icapture.hibernate.ShoppingListSubject;
import icapture.hibernate.Persistent;
import icapture.hibernate.Container;
import icapture.hibernate.ContainerContent;
import icapture.hibernate.Sample;
import icapture.hibernate.Subject;
import icapture.hibernate.User;


import java.io.InputStream;
import java.io.*;
import java.util.*;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.*;
import org.apache.commons.io.*;

/**
 *
 * @author btripp
 */
public class ShoppingCartList {

    private ShoppingList shoppingList;
    //private boolean isPublic;
    private ArrayList<String> containerContentsList;
    private ArrayList<String> containerList;
    private ArrayList<String> sampleList;
    private ArrayList<String> subjectList;
    private UserHttpSess userHttpSess;

    public ShoppingCartList(UserHttpSess userHttpSess, Long listID) throws Exception {
        load(userHttpSess, listID);
    }

    public ShoppingCartList(UserHttpSess userHttpSess, String listName) throws Exception {
        load(userHttpSess, listName);
    }

    /**
     * check if the current list in in use (and so no edits should be allowed)
     * @return the user object using the current list or null if current list is
     *          null or user of current list is null
     */
//    public User currentListInUseBy() {
//        if (userHttpSess.getCurrentShoppingList() == null ||
//                userHttpSess.getCurrentShoppingList().getInUseBy() == null) {
//            return null;
//        }
//        return userHttpSess.getCurrentShoppingList().getInUseBy();
//    }
    public User currentListInUseBy() {
        userHttpSess.openHibSession();
        if (userHttpSess.getCurrentShoppingList() != null) {
            org.hibernate.Query q = userHttpSess.hibSess.createQuery("from ShoppingList shopList where shopList.listID=:listid");
            q.setLong("listid", userHttpSess.getCurrentShoppingList().getListID());
            ArrayList results = (ArrayList) q.list();
            Iterator iter = results.iterator();
            User user = null;
            ShoppingList sList = null;
            while (iter.hasNext()) {
                sList = (ShoppingList) iter.next();
                user = sList.getInUseBy();
            }
            return user;
        }
        return null;
    }

    public void addContainerContents(String containerContentsID) throws Exception {

        // lock shopping list
        userHttpSess.writeLockCurrentShoppingList();

        if (!containerContentsList.contains(containerContentsID)) {
            containerContentsList.add(containerContentsID);
            // add others

            ShoppingListContainerContent slcc = new ShoppingListContainerContent();
            slcc.setListID(shoppingList.getListID());
            slcc.setContainerContentsID(new Integer(containerContentsID));

            userHttpSess.addShoppingListContainerContent(slcc);
        }

        // unlock shopping list
        userHttpSess.releaseLockCurrentShoppingList();
    }

    public void removeContainerContents(String containerContentsID) throws Exception {
        // lock shopping list
        userHttpSess.writeLockCurrentShoppingList();

        containerContentsList.remove(containerContentsID);

        ShoppingListContainerContent slcc = new ShoppingListContainerContent();
        slcc.setListID(shoppingList.getListID());
        slcc.setContainerContentsID(new Integer(containerContentsID));

        userHttpSess.deleteShoppingListContainerContent(slcc);

        // unlock shopping list
        userHttpSess.releaseLockCurrentShoppingList();
    }

    public void clearContainerContents() throws Exception {
        // lock shopping list
        userHttpSess.writeLockCurrentShoppingList();

        Iterator iter = containerContentsList.iterator();
        while (iter.hasNext()) {
            String containerContentsID = (String) iter.next();

            ShoppingListContainerContent slcc = new ShoppingListContainerContent();
            slcc.setListID(shoppingList.getListID());
            slcc.setContainerContentsID(new Integer(containerContentsID));

            userHttpSess.deleteShoppingListContainerContent(slcc);
        }

        containerContentsList.clear();

        // unlock shopping list
        userHttpSess.releaseLockCurrentShoppingList();
    }

    public Iterator<String> containerContentsIterator() {
        return containerContentsList.iterator();
    }

    public Boolean containerContentsListContains(String containerContentsID) {
        return containerContentsList.contains(containerContentsID);
    }

    public void addSubject(String subjectID) throws Exception {
        // lock shopping list
        userHttpSess.writeLockCurrentShoppingList();

        if (!subjectList.contains(subjectID)) {
            subjectList.add(subjectID);

            ShoppingListSubject slcc = new ShoppingListSubject();
            slcc.setListID(shoppingList.getListID());
            slcc.setSubjectID(new Integer(subjectID));

            userHttpSess.addShoppingListSubject(slcc);
        }

        // unlock shopping list
        userHttpSess.releaseLockCurrentShoppingList();
    }

    public void removeSubject(String subjectID) throws Exception {
        // lock shopping list
        userHttpSess.writeLockCurrentShoppingList();

        subjectList.remove(subjectID);

        ShoppingListSubject slcc = new ShoppingListSubject();
        slcc.setListID(shoppingList.getListID());
        slcc.setSubjectID(new Integer(subjectID));

        userHttpSess.deleteShoppingListSubject(slcc);

        // unlock shopping list
        userHttpSess.releaseLockCurrentShoppingList();
    }

    public void clearSubject() throws Exception {
        // lock shopping list
        userHttpSess.writeLockCurrentShoppingList();

        Iterator iter = subjectList.iterator();
        while (iter.hasNext()) {
            String subjectID = (String) iter.next();

            ShoppingListSubject slcc = new ShoppingListSubject();
            slcc.setListID(shoppingList.getListID());
            slcc.setSubjectID(new Integer(subjectID));

            userHttpSess.deleteShoppingListSubject(slcc);
        }

        subjectList.clear();

        // unlock shopping list
        userHttpSess.releaseLockCurrentShoppingList();
    }

    public Iterator<String> subjectIterator() {
        return subjectList.iterator();
    }

    public Boolean subjectListContains(String subjectID) {
        return subjectList.contains(subjectID);
    }

    public void addSample(String sampleID) throws Exception {
        // lock shopping list
        userHttpSess.writeLockCurrentShoppingList();

        if (!sampleList.contains(sampleID)) {
            sampleList.add(sampleID);

            ShoppingListSample sls = new ShoppingListSample();
            sls.setListID(shoppingList.getListID());
            sls.setSampleID(new Long(sampleID));

            userHttpSess.addShoppingListSample(sls);
        }

        // unlock shopping list
        userHttpSess.releaseLockCurrentShoppingList();
    }

    public void removeSample(String sampleID) throws Exception {
        // lock shopping list
        userHttpSess.writeLockCurrentShoppingList();

        sampleList.remove(sampleID);

        //todo delete row from database
        ShoppingListSample sls = new ShoppingListSample();
        sls.setListID(shoppingList.getListID());
        sls.setSampleID(new Long(sampleID));

        userHttpSess.deleteShoppingListSample(sls);

        // unlock shopping list
        userHttpSess.releaseLockCurrentShoppingList();
    }

    public void clearSamples() throws Exception {
        // lock shopping list
        userHttpSess.writeLockCurrentShoppingList();

        Iterator iter = sampleList.iterator();
        while (iter.hasNext()) {
            String sampleID = (String) iter.next();

            ShoppingListSample sls = new ShoppingListSample();
            sls.setListID(shoppingList.getListID());
            sls.setSampleID(new Long(sampleID));

            userHttpSess.deleteShoppingListSample(sls);

        }

        sampleList.clear();

        // unlock shopping list
        userHttpSess.releaseLockCurrentShoppingList();
    }

    public Iterator<String> SampleIterator() {
        return sampleList.iterator();
    }

    public Boolean sampleListContains(String sampleID) {
        return sampleList.contains(sampleID);
    }

    public void addContainer(String containerID) throws Exception {
        // lock shopping list
        userHttpSess.writeLockCurrentShoppingList();

        if (!containerList.contains(containerID)) {
            containerList.add(containerID);

            ShoppingListContainer slc = new ShoppingListContainer();
            slc.setListID(shoppingList.getListID());
            slc.setContainerID(new Integer(containerID));

            userHttpSess.addShoppingListContainer(slc);
        }

        // unlock shopping list
        userHttpSess.releaseLockCurrentShoppingList();
    }

    public void removeContainer(String containerID) throws Exception {
        // lock shopping list
        userHttpSess.writeLockCurrentShoppingList();

        containerList.remove(containerID);

        ShoppingListContainer slc = new ShoppingListContainer();
        slc.setListID(shoppingList.getListID());
        slc.setContainerID(new Integer(containerID));

        userHttpSess.deleteShoppingListContainer(slc);

        // unlock shopping list
        userHttpSess.releaseLockCurrentShoppingList();
    }

    public void clearContainers() throws Exception {
        // lock shopping list
        userHttpSess.writeLockCurrentShoppingList();

        Iterator iter = containerList.iterator();
        while (iter.hasNext()) {
            String containerID = (String) iter.next();

            ShoppingListContainer slc = new ShoppingListContainer();
            slc.setListID(shoppingList.getListID());
            slc.setContainerID(new Integer(containerID));

            userHttpSess.deleteShoppingListContainer(slc);
        }

        containerList.clear();

        // unlock shopping list
        userHttpSess.releaseLockCurrentShoppingList();
    }

    public Iterator<String> containerIterator() {
        return containerList.iterator();
    }

    public Boolean containerListContains(String containerID) {
        return containerList.contains(containerID);
    }

    private void load(UserHttpSess userHttpSess, Long listID) throws Exception {
        // lock shopping list
        userHttpSess.writeLockShoppingList(this);

        this.userHttpSess = userHttpSess;
        shoppingList = (ShoppingList) userHttpSess.getObjectById(ShoppingList.class, listID.toString());

        sampleList = new ArrayList<String>();
        Iterator iter = userHttpSess.getShoppingListSamples(shoppingList.getListID()).iterator();
        while (iter.hasNext()) {
            ShoppingListSample sls = (ShoppingListSample) iter.next();
            sampleList.add(sls.getSampleID().toString());
        }

        subjectList = new ArrayList<String>();
        iter = userHttpSess.getShoppingListSubjects(shoppingList.getListID()).iterator();
        while (iter.hasNext()) {
            ShoppingListSubject slsu = (ShoppingListSubject) iter.next();
            subjectList.add(slsu.getSubjectID().toString());
        }

        containerContentsList = new ArrayList();
        iter = userHttpSess.getShoppingListContainerContents(shoppingList.getListID()).iterator();
        while (iter.hasNext()) {
            ShoppingListContainerContent slcc = (ShoppingListContainerContent) iter.next();
            containerContentsList.add(slcc.getContainerContentsID().toString());
        }

        containerList = new ArrayList();
        iter = userHttpSess.getShoppingListContainers(shoppingList.getListID()).iterator();
        while (iter.hasNext()) {
            ShoppingListContainer slc = (ShoppingListContainer) iter.next();
            containerList.add(slc.getContainerID().toString());
        }

        // unlock shopping list
        userHttpSess.releaseLockShoppingList(this);
    }

    private void load(UserHttpSess userHttpSess, String listName) throws Exception {
        // lock shopping list
        userHttpSess.writeLockShoppingList(this);

        this.userHttpSess = userHttpSess;

        HashMap listNameMap = new HashMap(1);
        listNameMap.put("listName", "'" + listName + "'");
        shoppingList = (ShoppingList) userHttpSess.getObjectByUniqueKey(ShoppingList.class, listNameMap);
        if (shoppingList != null) {

            sampleList = new ArrayList<String>();
            Iterator iter = userHttpSess.getShoppingListSamples(shoppingList.getListID()).iterator();
            while (iter.hasNext()) {
                ShoppingListSample sls = (ShoppingListSample) iter.next();
                sampleList.add(sls.getSampleID().toString());
            }

            subjectList = new ArrayList<String>();
            iter = userHttpSess.getShoppingListSubjects(shoppingList.getListID()).iterator();
            while (iter.hasNext()) {
                ShoppingListSubject slsu = (ShoppingListSubject) iter.next();
                subjectList.add(slsu.getSubjectID().toString());
            }

            containerContentsList = new ArrayList();
            iter = userHttpSess.getShoppingListContainerContents(shoppingList.getListID()).iterator();
            while (iter.hasNext()) {
                ShoppingListContainerContent slcc = (ShoppingListContainerContent) iter.next();
                containerContentsList.add(slcc.getContainerContentsID().toString());
            }

            containerList = new ArrayList();
            iter = userHttpSess.getShoppingListContainers(shoppingList.getListID()).iterator();
            while (iter.hasNext()) {
                ShoppingListContainer slc = (ShoppingListContainer) iter.next();
                containerList.add(slc.getContainerID().toString());
            }
        }

        // unlock shopping list
        userHttpSess.releaseLockShoppingList(this);
    }

    /**
     * @return the listName
     */
    public String getListName() {
        return this.shoppingList.getListName();
    }

    /**
     * @return the containerContentsList
     */
    public ArrayList<String> getContainerContentsList() {
        return containerContentsList;
    }

    /**
     * @param containerContentsList the containerContentsList to set
     */
    public void setContainerContentsList(ArrayList<String> containerContentsList) {
        this.containerContentsList = containerContentsList;
    }

    /**
     * @return the sampleList
     */
    public ArrayList<String> getSampleList() {
        return sampleList;
    }

    /**
     * @param sampleList the sampleList to set
     */
    public void setSampleList(ArrayList<String> sampleList) {
        this.sampleList = sampleList;
    }

    /**
     * @return the subjectList
     */
    public ArrayList<String> getSubjectList() {
        return subjectList;
    }

    /**
     * @param subjectList the subjectList to set
     */
    public void setSubjectList(ArrayList<String> subjectList) {
        this.subjectList = subjectList;
    }

    /**
     * @return the containerContentsList
     */
    public ArrayList<String> getContainerList() {
        return containerList;
    }

    /**
     * @param containerContentsList the containerContentsList to set
     */
    public void setContainerList(ArrayList<String> containerList) {
        this.containerList = containerList;
    }

    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    /**
     * add a sample and its containers, subjects and contents to the current shoppinglist
     * @param sampleID
     * @return 0 if successful
     */
    public int addObjectsBySample(String sampleID) throws Exception {
        // lock shopping list
        userHttpSess.writeLockCurrentShoppingList();

        ArrayList ccIDs = null;
        ArrayList contIDs = null;
        ArrayList subjIDs = null;
        shoppingList = userHttpSess.getCurrentShoppingList();

        ccIDs = (ArrayList) userHttpSess.getFieldFromContainerContent("containerContentsID", "sample.sampleID=" + sampleID);
        contIDs = (ArrayList) userHttpSess.getFieldFromContainerContent("container.containerID", "sample.sampleID=" + sampleID);
        subjIDs = (ArrayList) userHttpSess.getFieldFromContainerContent("sample.subject.subjectID", "sample.sampleID=" + sampleID);
        addSample(sampleID);
        Iterator samI = ccIDs.iterator();
        while (samI.hasNext()) {
            addContainerContents((String) samI.next());
        }
        samI = contIDs.iterator();
        while (samI.hasNext()) {
            addContainer((String) samI.next());
        }
        samI = subjIDs.iterator();
        while (samI.hasNext()) {
            addSubject((String) samI.next());
        }

        // unlock shopping list
        userHttpSess.releaseLockCurrentShoppingList();

        return 0;
    }

    /**
     * add a sample and its containers, subjects and contents to the current shoppinglist
     * @param sampleID
     * @return 0 if successful
     */
    public int addObjectsBySubject(String subjectID) throws Exception {
        // lock shopping list
        userHttpSess.writeLockCurrentShoppingList();

        ArrayList ccIDs = null;
        ArrayList contIDs = null;
        ArrayList sampIDs = null;
        shoppingList = userHttpSess.getCurrentShoppingList();
        ccIDs = (ArrayList) userHttpSess.getFieldFromContainerContent("containerContentsID", "sample.subject.subjectID=" + subjectID);
        contIDs = (ArrayList) userHttpSess.getFieldFromContainerContent("container.containerID", "sample.subject.subjectID=" + subjectID);
        sampIDs = (ArrayList) userHttpSess.getFieldFromContainerContent("sample.sampleID", "sample.subject.subjectID=" + subjectID);

        addSubject(subjectID);
        Iterator samI = ccIDs.iterator();
        while (samI.hasNext()) {
            addContainerContents((String) samI.next());
        }
        samI = contIDs.iterator();
        while (samI.hasNext()) {
            addContainer((String) samI.next());
        }
        samI = sampIDs.iterator();
        while (samI.hasNext()) {
            addSample((String) samI.next());
        }

        // unlock shopping list
        userHttpSess.releaseLockCurrentShoppingList();

        return 0;
    }

    /**
     * add a sample and its containers, subjects and contents to the current shoppinglist
     * @param sampleID
     * @return 0 if successful
     */
    public int addObjectsByContent(String contentID) throws Exception {
        // lock shopping list
        userHttpSess.writeLockCurrentShoppingList();

        ContainerContent cc = null;
        String contID = null;
        String sampID = null;
        String subjID = null;
        cc = (ContainerContent) userHttpSess.getObjectById(ContainerContent.class, contentID);
        contID = cc.getContainer().getContainerID();
        sampID = cc.getSample().getSampleID();
        subjID = cc.getSample().getSubject().getSubjectID();
        addContainerContents(contentID);
        // keep other lists up to date
        // this may cause lots of duplicates in other lists but need dups for removing contents
        addContainer(contID);
        addSample(sampID);
        addSubject(subjID);

        // unlock shopping list
        userHttpSess.releaseLockCurrentShoppingList();
        return 0;
    }

    /**
     * add a sample and its containers, subjects and contents to the current shoppinglist
     * @param sampleID
     * @return 0 if successful
     */
    public int addObjectsByContainer(String containerID) throws Exception {
        // lock shopping list
        userHttpSess.writeLockCurrentShoppingList();

        ArrayList ccIDs = null;
        ArrayList sampIDs = null;
        ArrayList subjIDs = null;
        shoppingList = userHttpSess.getCurrentShoppingList();

        ccIDs = (ArrayList) userHttpSess.getFieldFromContainerContent("containerContentsID", "container.containerID=" + containerID);
        sampIDs = (ArrayList) userHttpSess.getFieldFromContainerContent("sample.sampleID", "container.containerID=" + containerID);
        subjIDs = (ArrayList) userHttpSess.getFieldFromContainerContent("sample.subject.subjectID", "container.containerID=" + containerID);
        addContainer(containerID);
        Iterator samI = ccIDs.iterator();
        while (samI.hasNext()) {
            addContainerContents((String) samI.next());
        }
        samI = sampIDs.iterator();
        while (samI.hasNext()) {
            addSample((String) samI.next());
        }
        samI = subjIDs.iterator();
        while (samI.hasNext()) {
            addSubject((String) samI.next());
        }

        // unlock shopping list
        userHttpSess.releaseLockCurrentShoppingList();
        return 0;
    }

    /**
     * remove a sample and its containers, subjects and contents from the current shoppinglist
     * @param sampleID
     * @return 0 if successful
     */
    public int removeObjectsBySample(String sampleID) throws Exception {
        // lock shopping list
        userHttpSess.writeLockCurrentShoppingList();

        ArrayList ccIDs = null;
        shoppingList = userHttpSess.getCurrentShoppingList();

        ccIDs = (ArrayList) userHttpSess.getFieldFromContainerContent("containerContentsID", "sample.sampleID=" + sampleID);

        removeSample(sampleID);

        // remove contents
        Iterator samI = ccIDs.iterator();
        while (samI.hasNext()) {
            removeContainerContents((String) samI.next());
        }

        // remove a container if all its contents have been removed
        updateContainerList(shoppingList.getListID().toString());
        // remove a subject if all its contents have been removed
        updateSubjectList(shoppingList.getListID().toString());

        // unlock shopping list
        userHttpSess.releaseLockCurrentShoppingList();
        return 0;
    }

    /**
     * remove a sample and its containers, subjects and contents from the current shoppinglist
     * @param sampleID
     * @return 0 if successful
     */
    public int removeObjectsBySubject(String subjectID) throws Exception {
        // lock shopping list
        userHttpSess.writeLockCurrentShoppingList();

        ArrayList ccIDs = null;
        shoppingList = userHttpSess.getCurrentShoppingList();
        ccIDs = (ArrayList) userHttpSess.getFieldFromContainerContent("containerContentsID", "sample.subject.subjectID=" + subjectID);

        removeSubject(subjectID);

        Iterator iter = (new HashSet(ccIDs)).iterator();
        while (iter.hasNext()) {
            removeContainerContents((String) iter.next());
        }

        // remove a container if all its contents have been removed
        updateContainerList(shoppingList.getListID().toString());
        // remove a sample if all its contents have been removed
        updateSampleList(shoppingList.getListID().toString());




        // unlock shopping list
        userHttpSess.releaseLockCurrentShoppingList();
        return 0;
    }

    /**
     * remove a sample and its containers, subjects and contents from the current shoppinglist
     * @param sampleID
     * @return 0 if successful
     */
    public int removeObjectsByContent(String contentID) throws Exception {
        // lock shopping list
        userHttpSess.writeLockCurrentShoppingList();

        removeContainerContents(contentID);

        // keep other lists up to date
        updateContainerList(shoppingList.getListID().toString());
        updateSampleList(shoppingList.getListID().toString());
        updateSubjectList(shoppingList.getListID().toString());

        // unlock shopping list
        userHttpSess.releaseLockCurrentShoppingList();
        return 0;
    }

    /**
     * remove a sample and its containers, subjects and contents from the current shoppinglist
     * @param sampleID
     * @return 0 if successful
     */
    public int removeObjectsByContainer(String containerID) throws Exception {
        // lock shopping list
        userHttpSess.writeLockCurrentShoppingList();

        ArrayList ccIDs = null;
        shoppingList = userHttpSess.getCurrentShoppingList();

        ccIDs = (ArrayList) userHttpSess.getFieldFromContainerContent("containerContentsID", "container.containerID=" + containerID);

        removeContainer(containerID);

        Iterator samI = ccIDs.iterator();
        while (samI.hasNext()) {
            removeContainerContents((String) samI.next());
        }

        updateSampleList(shoppingList.getListID().toString());
        updateSubjectList(shoppingList.getListID().toString());

        // unlock shopping list
        userHttpSess.releaseLockCurrentShoppingList();
        return 0;
    }

    public int loadIDsToCurrentList(ArrayList<String> ids, String idType) throws Exception {
        // lock shopping list
        userHttpSess.writeLockCurrentShoppingList();

        Iterator iter = ids.iterator();
        // for every id, remove the object itself, and the corresponding other objects
        try {
            if (idType.equals("samples")) {
                while (iter.hasNext()) {
                    removeObjectsBySample((String) iter.next());
                }
            }
            if (idType.equals("subjects")) {
                while (iter.hasNext()) {
                    removeObjectsBySubject((String) iter.next());
                }
            }
            if (idType.equals("containers")) {
                while (iter.hasNext()) {
                    removeObjectsByContainer((String) iter.next());
                }
            }
            if (idType.equals("contents")) {
                while (iter.hasNext()) {
                    removeObjectsByContent((String) iter.next());
                }
            }
        } catch (Exception e) {

            // unlock shopping list
            userHttpSess.releaseLockCurrentShoppingList();
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
            return -1;
        }

        // unlock shopping list
        userHttpSess.releaseLockCurrentShoppingList();
        return 0;
    }

    /**
     * trim the current list according to the list filter (power search)
     * @param keepOrRemove is "keep" if the elements that match the criteria in
     * the search are to be kept; is "remove" if the elements that match the criteria in
     * the search are all to be removed from the list
     * @return if successful, returns 0
     */
    public int trimListEdit(String keepOrRemove) throws Exception {

        Iterator iter = null;

        if (userHttpSess.isFilterListQueryingSubjects()) {
            // get all list's subjects amd subjects of list that meet search criteria
            ArrayList<Subject> matchingSubjects = (ArrayList) userHttpSess.getSubjectsListSearched();
            ArrayList<Subject> allSubjects = (ArrayList) userHttpSess.getAllSubjectObjectsInList();
            Subject subject = null;

            if (keepOrRemove.equals("remove")) {
                if (userHttpSess.isFilterListQueryingSubjects()) {
                    iter = matchingSubjects.iterator();
                    // remove each subject that matches criteria
                    while (iter.hasNext()) {
                        subject = (Subject) iter.next();
                        removeObjectsBySubject(subject.getSubjectID());
                    }
                }
            }// remove any subject (and it's samples + containers) that don't match criteria
            else if (keepOrRemove.equals("keep")) {
                // go through all subjects, if one's not in the matching list, remove it
                iter = allSubjects.iterator();
                while (iter.hasNext()) {
                    subject = (Subject) iter.next();
                    if (!matchingSubjects.contains(subject)) {
                        removeObjectsBySubject(subject.getSubjectID());
                    }
                }
            }
        }

        if (userHttpSess.isFilterListQueryingSamples()) {
            // get all list's samples amd samples of list that meet search criteria
            ArrayList<Sample> matchingSamples = (ArrayList) userHttpSess.getSamplesListSearched();
            ArrayList<Sample> allSamples = (ArrayList) userHttpSess.getAllSampleObjectsInList();
            Sample sample = null;

            if (keepOrRemove.equals("remove")) {
                if (userHttpSess.isFilterListQueryingSamples()) {
                    iter = matchingSamples.iterator();
                    // remove each sample that matches criteria
                    while (iter.hasNext()) {
                        sample = (Sample) iter.next();
                        removeObjectsBySample(sample.getSampleID());
                    }
                }
            }// remove any sample (and it's samples + containers) that don't match criteria
            else if (keepOrRemove.equals("keep")) {
                // go through all samples, if one's not in the matching list, remove it
                iter = allSamples.iterator();
                while (iter.hasNext()) {
                    sample = (Sample) iter.next();
                    if (!matchingSamples.contains(sample)) {
                        removeObjectsBySample(sample.getSampleID());
                    }
                }
            }
        }

        if (userHttpSess.isFilterListQueryingContents()) {
            // get all list's contents amd contents of list that meet search criteria
            ArrayList<ContainerContent> matchingContents = (ArrayList) userHttpSess.getContentsListSearched();
            ArrayList<ContainerContent> allContents = (ArrayList) userHttpSess.getAllContentObjectsInList();
            ContainerContent content = null;

            if (keepOrRemove.equals("remove")) {
                if (userHttpSess.isFilterListQueryingContents()) {
                    iter = matchingContents.iterator();
                    // remove each content that matches criteria
                    while (iter.hasNext()) {
                        content = (ContainerContent) iter.next();
                        removeObjectsByContent(content.getContainerContentsID());
                    }
                }
            }// remove any content (and it's contents + containers) that don't match criteria
            else if (keepOrRemove.equals("keep")) {
                // go through all contents, if one's not in the matching list, remove it
                iter = allContents.iterator();
                while (iter.hasNext()) {
                    content = (ContainerContent) iter.next();
                    if (!matchingContents.contains(content)) {
                        removeObjectsByContent(content.getContainerContentsID());
                    }
                }
            }
        }

        if (userHttpSess.isFilterListQueryingContainers()) {
            // get all list's containers amd containers of list that meet search criteria
            ArrayList<Container> matchingContainers = (ArrayList) userHttpSess.getContainersListSearched();
            ArrayList<Container> allContainers = (ArrayList) userHttpSess.getAllContainerObjectsInList();
            Container container = null;

            if (keepOrRemove.equals("remove")) {
                if (userHttpSess.isFilterListQueryingContainers()) {
                    iter = matchingContainers.iterator();
                    // remove each container that matches criteria
                    while (iter.hasNext()) {
                        container = (Container) iter.next();
                        removeObjectsByContainer(container.getContainerID());
                    }
                }
            }// remove any container (and it's containers + containers) that don't match criteria
            else if (keepOrRemove.equals("keep")) {
                // go through all containers, if one's not in the matching list, remove it
                iter = allContainers.iterator();
                while (iter.hasNext()) {
                    container = (Container) iter.next();
                    if (!matchingContainers.contains(container)) {
                        removeObjectsByContainer(container.getContainerID());
                    }
                }
            }
        }

        return 0;
    }

    /**
     * trim the current list according to the list filter (power search)
     * sets the new list (but not cart) as current
     * @param keepOrRemove is "keep" if the elements that match the criteria in
     * the search are to be kept; is "remove" if the elements that match the criteria in
     * the search are all to be removed from the list
     * @param newListName if this is null, will edit/overwrite current list
     * @return if successful, returns 0
     */
    public int trimListSaveAs(String keepOrRemove, String newListName) throws Exception {

        int result = 0;
        Iterator iter = null;

        ArrayList<Subject> matchingSubjects = null;
        ArrayList<Subject> allSubjects = null;
        Subject subject = null;

        ArrayList<Sample> matchingSamples = null;
        ArrayList<Sample> allSamples = null;
        Sample sample = null;

        ArrayList<Container> matchingContainers = null;
        ArrayList<Container> allContainers = null;
        Container container = null;

        ArrayList<ContainerContent> matchingContainerContents = null;
        ArrayList<ContainerContent> allContainerContents = null;
        ContainerContent containerContent = null;

        // get all list's subjects amd subjects of list that meet search criteria
        matchingSubjects = (ArrayList) userHttpSess.getSubjectsListSearched();
        allSubjects = (ArrayList) userHttpSess.getAllSubjectObjectsInList();

        // get all list's samples amd samples of list that meet search criteria
        matchingSamples = (ArrayList) userHttpSess.getSamplesListSearched();
        allSamples = (ArrayList) userHttpSess.getAllSampleObjectsInList();


        // get all list's containers and containers of list that meet search criteria
        matchingContainers = (ArrayList) userHttpSess.getContainersListSearched();
        allContainers = (ArrayList) userHttpSess.getAllContainerObjectsInList();

        // get all list's containerContents and containerContents of list that meet search criteria
        matchingContainerContents = (ArrayList) userHttpSess.getContentsListSearched();
        allContainerContents = (ArrayList) userHttpSess.getAllContentObjectsInList();

        // we're making a new list so the
        // source list is the current list and the destination list is a new list
        result = userHttpSess.addShoppingList(newListName, userHttpSess.getCurrentUser());
        if (result == 0) {
            // set this cart's shoppingList object to be the newly created one so add
            // methods apply to the new shoppingList object
            // also reset all cart object's ArrayLists
            load(userHttpSess, newListName);


            // add elements to new list according to criteria

            if (keepOrRemove.equals("remove")) {
                // add only subjects that don't match criteria to new list
                iter = allSubjects.iterator();
                while (iter.hasNext()) {
                    subject = (Subject) iter.next();
                    if (!matchingSubjects.contains(subject)) {
                        addSubject(subject.getSubjectID());
                    }
                }
                // add only samples that don't match criteria to new list
                iter = allSamples.iterator();
                while (iter.hasNext()) {
                    sample = (Sample) iter.next();
                    if (!matchingSamples.contains(sample)) {
                        addSample(sample.getSampleID());
                    }
                }

                // add only containerContents that don't match criteria to new list
                iter = allContainerContents.iterator();
                while (iter.hasNext()) {
                    containerContent = (ContainerContent) iter.next();
                    if (!matchingContainerContents.contains(containerContent)) {
                        addContainerContents(containerContent.getContainerContentsID());
                    }
                }
                // add only containers that don't match criteria to new list
                iter = allContainers.iterator();
                while (iter.hasNext()) {
                    container = (Container) iter.next();
                    if (!matchingContainers.contains(container)) {
                        addContainer(container.getContainerID());
                    }
                }
            } else if (keepOrRemove.equals("keep")) {
                // add only subjects that match criteria
                iter = matchingSubjects.iterator();
                while (iter.hasNext()) {
                    subject = (Subject) iter.next();
                    addSubject(subject.getSubjectID());
                }

                // add only samples that match criteria
                iter = matchingSamples.iterator();
                while (iter.hasNext()) {
                    sample = (Sample) iter.next();
                    addSample(sample.getSampleID());
                }

                // add only containers that match criteria
                iter = matchingContainers.iterator();
                while (iter.hasNext()) {
                    container = (Container) iter.next();
                    addContainer(container.getContainerID());
                }

                // add only containerContents that match criteria
                iter = matchingContainerContents.iterator();
                while (iter.hasNext()) {
                    containerContent = (ContainerContent) iter.next();
                    addContainerContents(containerContent.getContainerContentsID());
                }

            }

        }
        return result;
    }

    /**
     * delete any containers in a list that do not have any containerContents in that list
     * @param listID list to update
     * @throws java.lang.Exception
     */
    private void updateContainerList(String listID) throws Exception {
        userHttpSess.openHibSession();
        System.out.println("Before query: " + Util.getCurrentDateTime());
        // get containers that have no contents left
        String query = "select containerID from ShoppingListContainer "
                + " where listID =" + listID + " and containerID not in "
                + "(select container.containerID from ContainerContent "
                + "where containerContentsID in (select containerContentsID from "
                + "ShoppingListContainerContent where listID =" + listID + " ))";

        org.hibernate.Query q = userHttpSess.hibSess.createQuery(query);
        ArrayList results = (ArrayList) q.list();

        System.out.println("After query: " + Util.getCurrentDateTime());

        // delete them from the list
        Iterator iter = results.iterator();
        while (iter.hasNext()) {
            removeContainer(iter.next().toString());
        }

    }

    /**
     * delete any samples in a list that do not have any containerContents in that list
     * @param listID list to update
     * @throws java.lang.Exception
     */
    private void updateSampleList(String listID) throws Exception {
        userHttpSess.openHibSession();
        // get samples that have no contents left
        String query = "select sampleID from ShoppingListSample "
                + " where listID =" + listID + " and sampleID not in "
                + "(select sample.sampleID from ContainerContent "
                + "where containerContentsID in (select containerContentsID from "
                + "ShoppingListContainerContent where listID =" + listID + " ))";

        org.hibernate.Query q = userHttpSess.hibSess.createQuery(query);
        ArrayList results = (ArrayList) q.list();
        // delete them from the list
        Iterator iter = results.iterator();
        while (iter.hasNext()) {
            removeSample(iter.next().toString());
        }

    }

    /**
     * delete any subjects in a list that do not have any DB SAMPLES in that list
     * in order to get rid of all subjects that do not have any contents in a list,
     * updateSampleList(...) must be run first
     * @param listID list to update
     * @throws java.lang.Exception
     */
    private void updateSubjectList(String listID) throws Exception {
        userHttpSess.openHibSession();

        // get subjects that have no contents left
        String query = "select subjectID from ShoppingListSubject "
                + " where listID =" + listID + " and subjectID not in "
                + "(select sample.subject.subjectID from ContainerContent "
                + "where containerContentsID in (select containerContentsID from "
                + "ShoppingListContainerContent where listID =" + listID + " ))";

        org.hibernate.Query q = userHttpSess.hibSess.createQuery(query);
        ArrayList results = (ArrayList) q.list();


        // delete them from the list
        Iterator iter = results.iterator();
        while (iter.hasNext()) {
            removeSubject(iter.next().toString());
        }

    }
}
