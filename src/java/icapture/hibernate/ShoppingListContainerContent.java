
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package icapture.hibernate;

//~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

/**
 *
 * @author btripp
 */
public class ShoppingListContainerContent implements Serializable {
    private Integer containerContentsID;
    private Long    listID;

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
     * @return the containerContentsID
     */
    public Integer getContainerContentsID() {
        return containerContentsID;
    }

    /**
     * @param containerContentsID the containerContentsID to set
     */
    public void setContainerContentsID(Integer containerContentsID) {
        this.containerContentsID = containerContentsID;
    }
}



