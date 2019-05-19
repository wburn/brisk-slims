package icapture.hibernate;

public abstract class MetaData extends Persistent {
    private byte    actualColumnNumber;
    private Boolean editable;
    private String  longName;
    private String  shortName;
    private Boolean showInReports;
    private Boolean sortable;
    private int     valueRowNumber;
    private Integer viewColumnNumber;

    public String getShortName() {
        return shortName;
    }

    public String getLongName() {
        return longName;
    }

    public Integer getViewColumnNumber() {
        return viewColumnNumber;
    }

    public byte getActualColumnNumber() {
        return actualColumnNumber;
    }

    public int getValueRowNumber() {
        return valueRowNumber;
    }

    public Boolean getSortable() {
        return sortable;
    }

    public Boolean getEditable() {
        return editable;
    }

    public boolean isSortable() {
        return sortable.booleanValue();
    }

    public boolean isEditable() {
        return editable.booleanValue();
    }

    public Boolean getShowInReports() {
        return showInReports;
    }

    public boolean isShowInReports() {
        return showInReports.booleanValue();
    }

    public void setShortName(String sName) {
        shortName = sName;
    }

    public void setLongName(String lName) {
        longName = lName;
    }

    public void setViewColumnNumber(Integer cNumber) {
        viewColumnNumber = cNumber;
    }

    public void setActualColumnNumber(byte val) {
        actualColumnNumber = val;
    }

    public void setValueRowNumber(int cNumber) {
        valueRowNumber = cNumber;
    }

    public void setSortable(Boolean sortab) {
        sortable = sortab;
    }

    public void setEditable(Boolean editab) {
        editable = editab;
    }

    public void setShowInReports(Boolean show) {
        showInReports = show;
    }
}



