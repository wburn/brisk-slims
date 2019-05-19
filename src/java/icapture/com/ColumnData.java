package icapture.com;

//~--- non-JDK imports --------------------------------------------------------

import icapture.com.UserHttpSess;

import icapture.hibernate.*;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;

/*
* This file enables the adding and removing of columns from a user’s view of
* container contents data. It stores the default views for each user type and
* an array for every possible column to be added.
 */

/**
 * Constants for all possible columns to be added to a user's view of
 * container contents. Each hashmap has the same keys but different values.
 * {Property name (VisibleName), LongName, ShortName, Sortable, Editable, ShowInReports}
 * @author tvanrossum
 */
public class ColumnData {

//  default view schemes according to user groups for initial setup and resetting
    private static final String[] adminDefault = {
        "containerContentsID", "sample", "sample.sampleType", "materialType", "container.isStock", "volume",
        "concentration", "sample.dateCollected", "sample.extractionDate", "container", "contaminated", "comments"
    };
    private static final String[] dryLabDefault = {
        "containerContentsID", "sample", "sample.sampleType", "materialType", "container.isStock", "volume",
        "concentration", "sample.dateCollected", "sample.extractionDate", "contaminated", "comments"
    };
    private static HashMap        nameToData     = null;
    private static final Object[] modifDate      = {
        "modifDate", "Last Modified", "Last Modified", true, true, true
    };
    private static final Object[] materialType   = {
        "materialType", "Material Type", "Material Type", true, true, true
    };
    private static final Object[] location       = {
        "container.location", "Location", "Location", true, true, true
    };
    private static final Object[] freezer        = {
        "container.freezer", "Freezer", "Freezer", true, true, true
    };
    private static final Object[] extractionDate = {
        "sample.extractionDate", "Extraction Date", "Extraction Date", true, true, true
    };
    private static final Object[] dilution       = {
        "dilution", "Dilution", "Dilution", true, true, true
    };
    private static final Object[] dateCollected  = {
        "sample.dateCollected", "Collection Date", "Collection Date", true, true, true
    };
    private static final Object[] containerType  = {
        "container.containerType", "Container Type", "Container Type", true, true, true
    };

    /*
     *     0   Property name (VisibleName)
     *     1   LongName
     *     2   ShortName
     *     3   Sortable
     *     4   Editable
     *     5   ShowInReports
     *
     * Property name (VisibleName), LongName, ShortName, Sortable, Editable, ShowInReports;
     */
    private static final Object[] containerContentsID = {
        "containerContentsID", "Container Contents", "Container Contents", true, false, false
    };
    private static final Object[] container           = {
        "container", "Container", "Container", true, true, true
    };
    private static final Object[] concentration       = {
        "concentration", "Concentration (ng/ul)", "Concentration (ng/ul)", true, true, true
    };
    private static final Object[] comments            = {
        "comments", "Comments", "Comments", true, true, true
    };
    private static final Object[] column              = {
        "column", "Column", "Column", true, true, true
    };
    private static final Object[] barcode             = {
        "barcode", "Barcode", "Barcode", true, true, true
    };
    private static final Object[] amplificationDate   = {
        "amplificationDate", "Amplification Date", "Amplification Date", true, true, true
    };
    private static final Object[] parent              = {
        "parent", "Parent", "Parent", true, true, true
    };
    private static final Object[] row                 = {
        "row", "Row", "Row", true, true, true
    };
    private static final Object[] sample              = {
        "sample", "Sample", "Sample", true, true, true
    };
    private static final Object[] sampleType          = {
        "sample.sampleType", "Sample Type", "Sample Type", true, true, true
    };
    private static final Object[] shelf               = {
        "container.shelf", "Shelf", "Shelf", true, true, true
    };
    private static final Object[] isStock             = {
        "container.isStock", "Stock", "Stock", true, true, true
    };
    private static final Object[] contaminated        = {
        "contaminated", "Contaminated", "Contaminated", true, true, true
    };
    private static final String[] techDefault         = {
        "containerContentsID", "sample", "materialType", "volume", "concentration", "dilution", "barcode", "container",
        "row", "column", "parent", "contaminated", "comments"
    };
    private static final Object[] volume        = {
        "volume", "Volume (ul)", "Volume (ul)", true, true, true
    };
    private static final String[] wetLabDefault = {
        "containerContentsID", "sample", "sample.sampleType", "materialType", "container.isStock", "volume",
        "concentration", "barcode", "container.containerType", "container", "row", "column", "contaminated",
        "container.freezer", "container.shelf", "container.location", "comments", "modifDate"
    };

    /**
     * makes hash of row names to constants, if it doesn't already exists
     */
    private static void makeHash() {
        if ((nameToData == null) || nameToData.isEmpty()) {
            nameToData = new HashMap();
            nameToData.put("containerContentsID", containerContentsID);
            nameToData.put("barcode", barcode);
            nameToData.put("amplificationDate", amplificationDate);
            nameToData.put("sample.dateCollected", dateCollected);
            nameToData.put("column", column);
            nameToData.put("comments", comments);
            nameToData.put("concentration", concentration);
            nameToData.put("container", container);
            nameToData.put("container.containerType", containerType);
            nameToData.put("dilution", dilution);
            nameToData.put("sample.extractionDate", extractionDate);
            nameToData.put("container.freezer", freezer);
            nameToData.put("container.location", location);
            nameToData.put("modifDate", modifDate);
            nameToData.put("materialType", materialType);
            nameToData.put("parent", parent);
            nameToData.put("row", row);
            nameToData.put("sample", sample);
            nameToData.put("sample.sampleType", sampleType);
            nameToData.put("container.shelf", shelf);
            nameToData.put("container.isStock", isStock);
            nameToData.put("contaminated", contaminated);
            nameToData.put("volume", volume);
        }
    }

    /**
     * Sets fields of a new metadata object in preparation to add it to the table
     * @param ccMeta - the new metadata object
     * @param rowName - the name of the row to add to the metaData table
     * @param colNum - the place in the view order the new column is to have
     * @return ContainerContentMetaData with fields set
     */
    public static ContainerContentMetaData setFields(ContainerContentMetaData ccMeta, String rowName, int colNum,
            UserHttpSess session) {
        makeHash();

        if (nameToData.containsKey(rowName)) {
            Object[] constants = (Object[]) nameToData.get(rowName);

            ccMeta.setVisibleName((String) constants[0]);
            ccMeta.setLongName((String) constants[1]);
            ccMeta.setShortName((String) constants[2]);
            ccMeta.setViewColumnNumber(colNum);
            ccMeta.setSortable((Boolean) constants[3]);
            ccMeta.setEditable((Boolean) constants[4]);
            ccMeta.setShowInReports((Boolean) constants[5]);
            ccMeta.setCreateInfo(session.getCurrentUser());
            ccMeta.setUserID(session.getCurrentUser().getId().intValue());
            System.out.println("USER ID: " + session.getCurrentUser().getId().intValue());

            // ccMeta.setComment(string);

            return ccMeta;
        }

        return null;
    }

    public static String[] getDefault(int userTypeID) {
        if (userTypeID == 0) {
            return adminDefault;
        }

        if (userTypeID == 1) {
            return dryLabDefault;
        }

        if (userTypeID == 2) {
            return wetLabDefault;
        }

        if (userTypeID == 3) {
            return techDefault;
        }

        if (userTypeID == 4) {
            return techDefault;
        }

        return null;
    }
}



