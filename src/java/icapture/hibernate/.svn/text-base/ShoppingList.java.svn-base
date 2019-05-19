
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package icapture.hibernate;

/**
 *
 * @author btripp
 */
public final class ShoppingList extends Persistent {
    private User   inUseBy;
    private Long   listID;
    private String listName;

    /**
     * @return the listID
     */
    public Long getListID() {
        return listID;
    }

    /**
     * @param listID the listID to set
     */
    public void setListID(Long listID) {
        this.listID = listID;
    }

    /**
     * @return the listName
     */
    public String getListName() {
        return listName;
    }

    /**
     * @param listName the listName to set
     */
    public void setListName(String listName) {
        this.listName = listName;
    }

    /**
     * @return the user writing to the list
     */
    public User getInUseBy() {
        return inUseBy;
    }

    /**
     * @param the user writing to the list
     */
    public void setInUseBy(User input) {
        inUseBy = input;
    }

    @Override
    public String getVisibleName() {
        return listName;
    }

    @Override
    public Long getId() {
        return listID;
    }
}



