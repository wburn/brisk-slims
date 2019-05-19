package icapture.hibernate;

public final class TempMetaData {
    private byte    actualColumnNumber;
    private boolean canShowInReport;
    private String  longName;
    private String  shortName;
    private boolean sortable;
    private int     userID;
    private int     valueRowNumber;
    private int     viewColumnNumber;
    private String  visibleName;

    public TempMetaData(ContainerContentMetaData metadata) {
        visibleName        = metadata.getVisibleName();
        shortName          = metadata.getShortName();
        longName           = metadata.getLongName();
        viewColumnNumber   = metadata.getViewColumnNumber().intValue();
        sortable           = metadata.isSortable();
        actualColumnNumber = 0;
        canShowInReport    = metadata.isShowInReports();
        canShowInReport    = metadata.isShowInReports();
        userID             = metadata.getUserID();
    }

    public TempMetaData(MetaData metadata) {
        visibleName        = metadata.getVisibleName();
        shortName          = metadata.getShortName();
        longName           = metadata.getLongName();
        viewColumnNumber   = metadata.getViewColumnNumber().intValue();
        sortable           = metadata.isSortable();
        actualColumnNumber = 0;
        canShowInReport    = metadata.isShowInReports();
        canShowInReport    = metadata.isShowInReports();
    }

    public String getVisibleName() {
        return visibleName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getLongName() {
        return longName;
    }

    public int getViewColumnNumber() {
        return viewColumnNumber;
    }

    public byte getActualColumnNumber() {
        return actualColumnNumber;
    }

    public int getValueRowNumber() {
        return valueRowNumber;
    }

    public boolean getSortable() {
        return sortable;
    }

    public int getUserID() {
        return userID;
    }

    public boolean canShowInReport() {
        return canShowInReport;
    }

    public boolean isSortable() {
        return sortable;
    }

    public void setShortName(String sName) {
        shortName = sName;
    }

    public void setLongName(String lName) {
        longName = lName;
    }

    public void setViewColumnNumber(int val) {
        viewColumnNumber = val;
    }

    public void setActualColumnNumber(byte val) {
        actualColumnNumber = val;
    }

    public void setValueRowNumber(int cNumber) {
        valueRowNumber = cNumber;
    }

    public void setSortable(boolean val) {
        sortable = val;
    }

    public void setUserID(int id) {
        userID = id;
    }
}



