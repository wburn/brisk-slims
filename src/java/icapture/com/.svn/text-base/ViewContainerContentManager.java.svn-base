/**
 * Manages table view screens, particularly multi-page navigation of tables with
 * more entries than the set view limit per page (usually 1000).
 * Manages the processing, adding and removing steps involved when a user
 * customizes or resets their columns for that screen.
 */
package icapture.com;

import icapture.hibernate.ContainerContentMetaData;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import java.util.List;
import java.util.Iterator;

public final class ViewContainerContentManager {

    private UserHttpSess parent;
    private int total;
    private int first;
    private int step;
    private int containercontentCount;

    public ViewContainerContentManager(UserHttpSess tmpHttpSessObj) {
        parent = tmpHttpSessObj;
        step = 0;
        total = -1;
        first = 0;
    }

    public void clearValues() {
        total = -1;
        first = 0;
        step = parent.getViewLimit();
    }

    public void changeContainerContentTotal(int diff) {
        total += diff;
    }

    public void setContainerContentCount(int val) {
        total = val;
        //if (total < containercontentCount) total = val;
    }

    public boolean hasContainerContentPrevious() {
        return (step > 0) && (first > 0);
    }

    public void setContainerContentPrevious() {
        first -= step;
        if (first < 0) {
            first = 0;
        }
    }

    public boolean hasContainerContentNext() {
        return (step > 0) && (first + step < total - 1);
    }

    public void setContainerContentNext() {
        first += step;
        if (first >= total) {
            setContainerContentPrevious();
        }
    }

    public void setContainerContentFirst() {
        first = 0;
    }

    public void setContainerContentLast() {
        first = total - step;
        if (first < 0) {
            first = 0;
        }
    }

    public int getContainerContentStep() {
        return step;
    }

    public int getContainerContentCurrent() {
        return first;
    }

    public String getContainerContentControlString() {
        StringBuffer xyz = new StringBuffer();
        if ((step > 0) && (total > step)) {
            if (first > 0) {
                xyz.append("<button type='button' onclick='gotoFirst()' title='first' class='lines'>").append("<img border='0' src='./images/left_d_10_b.gif' title='first' alt=''>").append("</button>");
                xyz.append("<button type='button' onclick='gotoPrevious()' title='previous' class='lines'>").append("<img border='0' src='./images/left_10_b.gif' title='previous' alt=''>").append("</button>");
            } else {
                xyz.append("<button type='button' disabled class='lines'>").append("<img border='0' src='./images/left_d_10_h.gif' alt=''>").append("</button>");
                xyz.append("<button type='button' disabled class='lines'>").append("<img border='0' src='./images/left_10_h.gif' alt=''>").append("</button>");
            }
            xyz.append(first + 1).append('-').append(first + step).append("&nbsp;of&nbsp;").append(total).append("&nbsp;items&nbsp;");
            if (first + step < total) {
                xyz.append("<button type='button' onclick='gotoNext()' title='next' class='lines'>").append("<img border='0' src='./images/right_10_b.gif' title='next' alt=''>").append("</button>");
                xyz.append("<button type='button' onclick='gotoLast()' title='last' class='lines'>").append("<img border='0' src='./images/right_d_10_b.gif' title='last' alt=''>").append("</button>");
            } else {
                xyz.append("<button type='button' disabled class='lines'>").append("<img border='0' src='./images/right_10_h.gif' alt=''>").append("</button>");
                xyz.append("<button type='button' disabled class='lines'>").append("<img border='0' src='./images/right_d_10_h.gif' alt=''>").append("</button>");
            }
        } else {
            xyz.append(total).append("&nbsp;items&nbsp;");
        }
        return xyz.toString();
    }

    /**
     * either adds, edits or removes a row from a user's container content metadata
     * @param rowName
     * @param colNum
     * @param show
     * @return 0 is sucessful
     */
    public int processMetaDataRow(String rowName, String colNumStr, String show) {

        System.out.println("processMetaDataRow for " + rowName + " colNum: " + colNumStr + " show:" + show);

        parent.openHibSession();
        Session hibSess = parent.hibSess;
        ContainerContentMetaData ccMeta = null;
        int result = 0;

        // see if row already exists
        try {
            Query q = hibSess.createQuery("select tbl from ContainerContentMetaData tbl"
                    + " where visibleName = '" + rowName + "' and userID = " + parent.getCurrentUser().getId());
            ccMeta = (ContainerContentMetaData) q.uniqueResult();

            if (show.equals("on")) {

                int colNum = new Integer(colNumStr);
                if (ccMeta != null) {
//                    ccMeta = (ContainerContentMetaData) q.uniqueResult();
                    result = updateCCMetaDataRow(rowName, colNum, ccMeta);
                } else {
                    result = addRowToMetaData(rowName, colNum);
                }
            } else if (ccMeta != null) {
                result = deleteCCMetaDataRow(rowName, ccMeta);
            }

        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            return -1;
        }

        return result;
    }

    /**
     * resets a user's container content metadata to default based on their user type
     * @param rowName
     * @param colNum
     * @param show
     * @return 0 is sucessful
     */
    public int resetMetaDataRows() {

        parent.openHibSession();
        int result = 0;

        try {

            // delete all meta data for current user
            result = deleteAllUsersMetaData();
            // reset to default (depends on user's group)
            setMetaDataToDefault();

        } catch (Exception ex) {
            return -1;
        }

        return result;
    }

    /**
     * either adds, edits or removes a row from a user's container content metadata
     * @param rowName
     * @param colNum
     * @param show
     * @return 0 is sucessful
     */
    private void setMetaDataToDefault() throws Exception {
        parent.openHibSession();
        Session hibSess = parent.hibSess;
        ContainerContentMetaData ccMeta = null;

        // get user's group and their defaults
        int userTypeID = parent.getCurrentUser().getUserTypeID();
        String[] rows = icapture.com.ColumnData.getDefault(userTypeID);


        for (int i = 0; i < rows.length; i++) {

            ccMeta = new ContainerContentMetaData();
            ccMeta = icapture.com.ColumnData.setFields(ccMeta, rows[i], i + 1, parent);

            System.out.println("In addViewColumn, just before tx init.");
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(ccMeta);
                tx.commit();

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            }
        }
        parent.closeHibSession(); //needs before forward
    }

    /**
     * adds a specified row to container content's meta data
     * which adds a column to a user's custom container contents view
     *
     * @return 0 if sucessful
     */
    private int addRowToMetaData(String rowName, int colNum) throws Exception {
        parent.openHibSession();
        Session hibSess = parent.hibSess;

        int rezult = 0;

        System.out.println("ADDING COLUMN" + rowName + " pos:" + colNum);
        if (rezult == 0) {
            ContainerContentMetaData ccMeta = new ContainerContentMetaData();
            ccMeta = icapture.com.ColumnData.setFields(ccMeta, rowName, colNum, parent);

            System.out.println("In addViewColumn, just before tx init.");
            Transaction tx = null;
            try {
                tx = hibSess.beginTransaction();
                hibSess.save(ccMeta);
                tx.commit();

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                parent.closeHibSession(); //needs before forward
            }
        }
        return rezult;

    }

    /**
     * adds a specified row to container content's meta data
     * which adds a column to a user's custom container contents view
     *
     * @return 0 if sucessful, -1 if existing row not found
     */
    private int updateCCMetaDataRow(String rowName, int colNum, ContainerContentMetaData ccMetaData) throws Exception {
        parent.openHibSession();
        Session hibSess = parent.hibSess;
        int rezult = 0;

        System.out.println("In addViewColumn, rezult=" + rezult);

        try {
            ccMetaData.setViewColumnNumber(colNum);

        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            return -1;
        }

        System.out.println("In addViewColumn, just before tx init.");
        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.update(ccMetaData);
            tx.commit();

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            parent.closeHibSession(); //needs before forward
        }

        return rezult;

    }

    /**
     * deletes a specified row from a user's container content meta data
     * which removes a column to a user's custom container contents view
     *
     * @return 0 if sucessful, -1 if existing row not found
     */
    private int deleteCCMetaDataRow(String rowName, ContainerContentMetaData ccMeta)
            throws Exception {
        parent.openHibSession();
        Session hibSess = parent.hibSess;
        int rezult = 0;

        Transaction tx = null;
        try {
            tx = hibSess.beginTransaction();
            hibSess.delete(ccMeta);
            tx.commit();

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            parent.closeHibSession(); //needs before forward
        }

        return rezult;

    }

    /**
     * deletes all rows of a user's container content meta data
     * which removes all columns from their custom container contents view
     *
     * @return 0 if sucessful, -1 if existing row not found
     */
    public int deleteAllUsersMetaData() throws Exception {
        parent.openHibSession();
        Session hibSess = parent.hibSess;
        List<ContainerContentMetaData> ccMetaList = null;
        int rezult = 0;

        System.out.println("In addViewColumn, rezult=" + rezult);

        try {
            Query q = hibSess.createQuery("select tbl from ContainerContentMetaData tbl"
                    + " where userID = " + parent.getCurrentUser().getId());
            ccMetaList = q.list();


        } catch (Exception ex) {
            return -1;
        }

        Transaction tx = null;
        try {
            Iterator iter = ccMetaList.listIterator();
            while (iter.hasNext()) {
                tx = hibSess.beginTransaction();
                hibSess.delete(iter.next());
                tx.commit();
            }

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            parent.closeHibSession(); //needs before forward
        }

        return rezult;

    }

    public int refreshContainerContentMetaData() {
        try {
            parent.openHibSession();
            ViewContainerContentManager vccm = parent.getViewContainerContentManager();
            vccm = new ViewContainerContentManager(parent);

            MetadataManager mdm = parent.getMetadataManager();
            List list = parent.getAllMetaDataByUserID(ContainerContentMetaData.class);
            mdm.buildContainerContentData(list);
            //mdm.normalizeContainerContentData();

            parent.closeHibSession();
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            return -1;
        }
        return 0;
    }
}
