package icapture.hibernate;

public final class Phenotype extends Persistent {
    private String abbreviation;
    private String description;
    private String name;
    private String phenotypeID;
    private String type;

    public String getPhenotypeID() {
        return phenotypeID;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    /*
     *  visible name overides allow for different fields to be used as visible name
     * alternate way to assign visibleName (in *.hbm.xml) won't allow for a mapping
     * of the visible name column to its own getter/setter methods
     */
    public String getVisibleName() {
        return phenotypeID;
    }

    public String getPropertyValue(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return phenotypeID;
        } else {
            return super.getPropertyValue(propertyName);
        }
    }

    public Object getPropertyObject(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return phenotypeID;
        } else {
            return super.getPropertyObject(propertyName);
        }
    }

    public String[] getValueArray(byte[] indexArray, int viewSize) {
        String[] valueArray = super.getValueArray(indexArray, viewSize);

        if (indexArray[6] > 0) {
            valueArray[indexArray[6] - 1] = phenotypeID;
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = name;
        }

        if (indexArray[8] > 0) {
            valueArray[indexArray[8] - 1] = type;
        }

        if (indexArray[9] > 0) {
            valueArray[indexArray[9] - 1] = description;
        }

        if (indexArray[10] > 0) {
            valueArray[indexArray[10] - 1] = abbreviation;
        }

        return valueArray;
    }

    public void setPhenotypeID(String input) {
        phenotypeID = input;
    }

    public void setName(String input) {
        name = input;
    }

    public void setType(String input) {
        type = input;
    }

    public void setDescription(String input) {
        description = input;
    }

    public void setAbbreviation(String input) {
        abbreviation = input;
    }
}



