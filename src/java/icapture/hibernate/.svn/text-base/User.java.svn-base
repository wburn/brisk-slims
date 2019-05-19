package icapture.hibernate;

//~--- JDK imports ------------------------------------------------------------
import java.util.Date;
import java.util.List;

public final class User extends Persistent {

    private String comment;
    private Date createDate;
    private Long id;
    private String initials;
    private String loginName;
    private Date modifDate;
    private String password;
    private Integer rights;
    private Integer userTypeID;
    private String visibleName;
    private String lastName;
    private String email;

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public String getVisibleName() {
        return visibleName;
    }

    public String getLoginName() {
        return loginName;
    }

    public Integer getRights() {
        return rights;
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

    public String getPassword() {
        return password;
    }

    public Integer getUserTypeID() {
        return userTypeID;
    }

    public String getInitials() {
        return initials;
    }

    public void setId(Long long1) {
        id = long1;
    }

    public String[] getValueArray(byte[] indexArray, int viewSize) {
        String[] valueArray = super.getValueArray(indexArray, viewSize);

        if (indexArray[6] > 0) {
            valueArray[indexArray[6] - 1] = id.toString();
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = loginName;
        }

        if (indexArray[8] > 0) {
            valueArray[indexArray[8] - 1] = password;
        }

        if (indexArray[9] > 0) {
            valueArray[indexArray[9] - 1] = getRightsReadable();
        }

        if (indexArray[10] > 0) {
            valueArray[indexArray[10] - 1] = getUserTypeReadable();
        }

        if (indexArray[11] > 0) {
            valueArray[indexArray[11] - 1] = initials;
        }

        if (indexArray[11] > 0) {
            valueArray[indexArray[12] - 1] = visibleName;
        }

        return valueArray;
    }

    public void setVisibleName(String string) {
        visibleName = string;
    }

    public void setLoginName(String string) {
        loginName = string;
    }

    public void setRights(Integer string) {
        rights = string;
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

    public void setPassword(String string) {
        password = string;
    }

    public void setUserTypeID(Integer input) {
        userTypeID = input;
    }

    public void setInitials(String input) {
        initials = input;
    }

    public String toString() {
        return this.getId() + "  " + visibleName + " : " + loginName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRightsReadable() {
        return (rights == 10)
                ? "Admin User"
                : (rights == 20)
                ? "Power User: Wet Lab"
                : (rights == 30)
                ? "Power User: Dry Lab"
                : (rights == 40)
                ? "Advanced User: Wet Lab"
                : (rights == 50)
                ? "Advanced User: Dry Lab"
                : (rights == 60)
                ? "Basic User"
                : (rights == 100)
                ? "No Access"
                : rights.toString();
    }

    public String getUserTypeReadable() {
        return (userTypeID == 1)
                ? "Dry Lab"
                : (userTypeID == 2)
                ? "Wet Lab"
                : (userTypeID == 0)
                ? "Admin"
                : (userTypeID == 3)
                ? "System Tech"
                : (userTypeID == 4)
                ? "System Admin"
                : userTypeID.toString();
    }
}
