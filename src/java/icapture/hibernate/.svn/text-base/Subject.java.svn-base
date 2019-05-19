package icapture.hibernate;

//~--- non-JDK imports --------------------------------------------------------

import icapture.com.UserHttpSess;
import icapture.com.Util;

import icapture.hibernate.Cohort;

//~--- JDK imports ------------------------------------------------------------

import java.util.Date;

public final class Subject extends Persistent {
    private Cohort    cohort;
    private String    comments;
    private Ethnicity ethnicity;
    private String    familyID;
    private String    fatherName;
    private String    gender;
    private String    hasConsent;
    private String    motherName;
    private String    subjectID;
    private String    subjectName;

    public String getSubjectID() {
        return subjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public Cohort getCohort() {
        return cohort;
    }

    public String getGender() {
        return gender;
    }

    public String getHasConsent() {
        return hasConsent;
    }

    public String getFamilyID() {
        return familyID;
    }

    public Ethnicity getEthnicity() {
        return ethnicity;
    }

    public String getComments() {
        return comments;
    }

    /*
     *  visible name overides allow for different fields to be used as visible name
     * alternate way to assign visibleName (in *.hbm.xml) won't allow for a mapping
     * of the visible name column to its own getter/setter methods
     */
    public String getVisibleName() {
        return subjectName;
    }

    public String getPropertyValue(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return subjectName;
        } else {
            return super.getPropertyValue(propertyName);
        }
    }

    public Object getPropertyObject(String propertyName) {
        if (propertyName.equals("visibleName")) {
            return subjectName;
        } else {
            return super.getPropertyObject(propertyName);
        }
    }

    public String[] getValueArray(byte[] indexArray, int viewSize) {
        return getValueArrayReadable(indexArray, viewSize);
    }

    public String[] getValueArrayReadable(byte[] indexArray, int viewSize) {
        String genderString = gender;

        if (gender != null) {
            genderString = gender.equals("0")
                           ? "unknown"
                           : gender.equals("1")
                             ? "male"
                             : gender.equals("2")
                               ? "female"
                               : gender;
        }

        if (hasConsent != null) {
            hasConsent = hasConsent.equals("0")
                         ? "unknown"
                         : hasConsent.equals("1")
                           ? "yes"
                           : hasConsent.equals("2")
                             ? "no"
                             : hasConsent;
        }

        String[] valueArray = super.getValueArray(indexArray, viewSize);

        if (indexArray[6] > 0) {
            valueArray[indexArray[6] - 1] = subjectID;
        }

        if (indexArray[7] > 0) {
            valueArray[indexArray[7] - 1] = subjectName;
        }

        if (indexArray[8] > 0) {
            valueArray[indexArray[8] - 1] = ((fatherName == null) || fatherName.equals("null")
                                             || fatherName.equals("0"))
                                            ? ""
                                            : fatherName;
        }

        if (indexArray[9] > 0) {
            valueArray[indexArray[9] - 1] = ((motherName == null) || motherName.equals("null")
                                             || motherName.equals("0"))
                                            ? ""
                                            : motherName;
        }

        if (indexArray[10] > 0) {
            valueArray[indexArray[10] - 1] = (cohort == null)
                                             ? ""
                                             : ((cohort.getDescription() == null)
                                             || cohort.getDescription().equals("null"))
                                               ? ""
                                               : cohort.getDescription();
        }

        if (indexArray[11] > 0) {
            valueArray[indexArray[11] - 1] = genderString;
        }

        if (indexArray[12] > 0) {
            valueArray[indexArray[12] - 1] = ((hasConsent == null) || hasConsent.equals("0"))
                                             ? ""
                                             : hasConsent;
        }

        if (indexArray[13] > 0) {
            valueArray[indexArray[13] - 1] = ((familyID == null) || familyID.equals("0"))
                                             ? ""
                                             : familyID;
        }

        if (indexArray[14] > 0) {
            valueArray[indexArray[14] - 1] = (ethnicity == null)
                                             ? ""
                                             : ethnicity.getEthnicity();
        }

        if (indexArray[15] > 0) {
            valueArray[indexArray[15] - 1] = ((comments == null) || comments.equals("null"))
                                             ? ""
                                             : comments;
        }

        return valueArray;
    }

    public void setSubjectID(String input) {
        subjectID = input;
    }

    public void setSubjectName(String input) {
        subjectName = input;
    }

    public void setFatherName(String input) {
        fatherName = input;
    }

    public void setMotherName(String input) {
        motherName = input;
    }

    public void setCohort(Cohort input) {
        cohort = input;
    }

    public void setGender(String input) {
        gender = input;
    }

    public void setHasConsent(String input) {
        hasConsent = input;
    }

    public void setFamilyID(String input) {
        familyID = input;
    }

    public void setEthnicity(Ethnicity input) {
        ethnicity = input;
    }

    public void setComments(String input) {
        comments = input;
    }
}



