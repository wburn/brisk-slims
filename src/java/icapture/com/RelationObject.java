package icapture.com;

public final class RelationObject {
    private String oldRelId  = null;
    private String personId  = null;
    private String relTypeId = null;

    public RelationObject() {
        relTypeId = null;
        oldRelId  = "0";
        personId  = null;
    }

    public RelationObject(String aRelTypeId, String aOldRelId, String aPersonId) {
        relTypeId = aRelTypeId;
        oldRelId  = aOldRelId;
        personId  = aPersonId;
    }

    public String getOldRelId() {
        return (oldRelId != null)
               ? oldRelId
               : "0";
    }

    public String getPersonId() {
        return (personId != null)
               ? personId
               : "0";
    }

    public Long getPersonIdLong() {
        return (personId != null)
               ? new Long(personId)
               : null;
    }

    public String getRelTypeId() {
        return (relTypeId != null)
               ? relTypeId
               : "0";
    }
}



