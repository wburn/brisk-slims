package icapture.hibernate;

//~--- non-JDK imports --------------------------------------------------------

import icapture.com.Util;

//~--- JDK imports ------------------------------------------------------------

import java.text.SimpleDateFormat;

import java.util.Date;

public abstract class Persistent {
    private String comment;
    private Date   createDate;
    private User   creator;
    private Long   id;
    private Date   modifDate;
    private User   modifier;
    private String visibleName;

    public Long getId() {
        return id;
    }

    public String getVisibleName() {
        return visibleName;
    }

    public User getCreator() {
        return creator;
    }

    public User getModifier() {
        return modifier;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getModifDate() {
        return modifDate;
    }

    public String getComment() {
        return comment;
    }

    public String getVisualComment() {
        return (comment == null)
               ? ""
               : comment;
    }

    public String[] getValueArray(byte[] indexArray, int viewSize) {
        String[] valueArray = new String[viewSize];

        if (indexArray[0] > 0) {
            valueArray[indexArray[0] - 1] = visibleName;
        }

        if (indexArray[1] > 0) {
            valueArray[indexArray[1] - 1] = Util.truncateString(comment);
        }

        if (indexArray[2] > 0) {
            valueArray[indexArray[2] - 1] = creator.getLoginName();
        }

        if (indexArray[3] > 0) {
            valueArray[indexArray[3] - 1] = Util.truncateDate(createDate);
        }

        if (indexArray[4] > 0) {
            valueArray[indexArray[4] - 1] = (modifier != null)
                                            ? modifier.getLoginName()
                                            : "";
        }

        if (indexArray[5] > 0) {
            valueArray[indexArray[5] - 1] = Util.truncateDate(modifDate);
        }

        return valueArray;
    }

    /**
     * Determines the way data is displayed in the table. In another words, fills up the table with data
     * @param indexArray
     * @param viewSize The number of columns in the table
     * @return A string array with the data for the table
     */
    public String[] getValueArrayReadable(byte[] indexArray, int viewSize) {
        String[] valueArray = new String[viewSize];

        if (indexArray[0] > 0) {
            valueArray[indexArray[0] - 1] = visibleName;
        }

        if (indexArray[1] > 0) {
            valueArray[indexArray[1] - 1] = Util.truncateString(comment);
        }

        if (indexArray[2] > 0) {
            valueArray[indexArray[2] - 1] = creator.getLoginName();
        }

        if (indexArray[3] > 0) {
            valueArray[indexArray[3] - 1] = Util.truncateDate(createDate);
        }

        if (indexArray[4] > 0) {
            valueArray[indexArray[4] - 1] = (modifier != null)
                                            ? modifier.getLoginName()
                                            : "";
        }

        if (indexArray[5] > 0) {
            valueArray[indexArray[5] - 1] = Util.truncateDate(modifDate);
        }

        return valueArray;
    }

    public String getPropertyValue(String propertyName) {
        return (propertyName.equals("visibleName"))
               ? visibleName
               : (propertyName.equals("creator"))
                 ? visibleName
                 : (propertyName.equals("createDate"))
                   ? Util.truncateDate(createDate)
                   : (propertyName.equals("modifier"))
                     ? (modifier != null)
                       ? modifier.getLoginName()
                       : ""
                     : (propertyName.equals("modifDate"))
                       ? Util.truncateDate(modifDate)
                       : (propertyName.equals("comment"))
                         ? getVisualComment()
                         : "";
    }

    public Object getPropertyObject(String propertyName) {
        return (propertyName.equals("visibleName"))
               ? (Object) visibleName
               : (propertyName.equals("creator"))
                 ? (Object) creator
                 : (propertyName.equals("createDate"))
                   ? (Object) createDate
                   : (propertyName.equals("modifier"))
                     ? (Object) modifier
                     : (propertyName.equals("modifDate"))
                       ? (Object) modifDate
                       : (propertyName.equals("comment"))
                         ? (Object) comment
                         : null;
    }

//  -------------------------------------------
//  -------------------------------------------
//  -------------------------------------------
    private void setId(Long long1) {
        id = long1;
    }

    public void setVisibleName(String string) {
        visibleName = string;
    }

    public void setCreator(User user) {
        creator = user;
    }

    public void setModifier(User user) {
        modifier = user;
    }

    public void setCreateDate(Date date) {
        createDate = date;
    }

    public void setModifDate(Date date) {
        modifDate = date;
    }

    public void setComment(String string) {
        comment = string;
    }

    public void setCreateInfo(User user) {
        createDate = new Date();
        modifDate  = new Date();
        creator    = user;
        modifier   = user;
    }

    public void setModifInfo(User user) {
        modifDate = new Date();
        modifier  = user;
    }

    public String getCreateAndModifyString() {
        StringBuffer strBuf = new StringBuffer("<tr><td>&nbsp;</td><td>Created by &nbsp;");

        strBuf.append(creator.getLoginName()).append("&nbsp; Create data &nbsp;").append(
            Util.truncateTime(createDate)).append("</td></tr>");

        if (modifier != null) {
            strBuf.append("<tr><td>&nbsp;</td><td>Modified by &nbsp;").append(modifier.getLoginName()).append(
                "&nbsp; Modify data &nbsp;").append(Util.truncateTime(modifDate)).append("</td></tr>");
        }

        return strBuf.toString();
    }

    public String toString() {
        return this.getId() + "  " + this.getVisibleName();
    }
}



