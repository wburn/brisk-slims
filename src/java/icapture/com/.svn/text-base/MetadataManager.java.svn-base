/**
 * This file builds the profiles describing what data a user will view for each
 * object (which is based on the Meta_Data Tables). It gets which fields need to
 * be viewed in what order from the MetaData tables and the actually values for
 * these fields from the Object.java’s getValueArrayReadable().
 * It then consolidates these two information arrays to build an array that will
 * be shown to the user.
 * @author tvanrossum
 */
package icapture.com;

import java.util.*;
import icapture.hibernate.MetaData;
import java.lang.reflect.Field;
import icapture.hibernate.TempMetaData;
import icapture.hibernate.Fieldname;

public final class MetadataManager {

    private HashMap freezerData = null;
    private HashMap shippedToData = null;
    private int freezerViewSize = 0;
    private int shippedToViewSize = 0;
    public int ccMetaDataAllColCount = 100;

    public void buildShippedToData(List shippedToMD) {
        int viewSize = 0;
        shippedToData = new HashMap(shippedToMD.size());
        Iterator iter = shippedToMD.iterator();
        while (iter.hasNext()) {
            MetaData metadata = (MetaData) iter.next();
            String key = metadata.getVisibleName();
            TempMetaData meta = new TempMetaData(metadata);
            shippedToData.put(key, meta);
            int m = (key.equals(Fieldname.VISIBLENAME)) ? 0
                    : (key.equals(Fieldname.COMMENT)) ? 1
                    : (key.equals(Fieldname.CREATOR)) ? 2
                    : (key.equals(Fieldname.CREATEDATE)) ? 3
                    : (key.equals(Fieldname.MODIFIER)) ? 4
                    : (key.equals(Fieldname.MODIFDATE)) ? 5
                    : (key.equals(Fieldname.SHIPPEDTOID)) ? 6
                    : (key.equals(Fieldname.DESCRIPTION)) ? 7
                    : (key.equals(Fieldname.SORTORDER)) ? 8 : 0;
            meta.setValueRowNumber(m);
            int n = metadata.getViewColumnNumber().intValue();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        // normalization of column numbers
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = shippedToData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData meta = (TempMetaData) miter.next();
                if (meta.getViewColumnNumber() == destin) {
                    meta.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        shippedToViewSize = target - 1;
    }

    public void buildFreezerData(List freezerMD) {
        //Println used to be here
        int viewSize = 0;
        freezerData = new HashMap(freezerMD.size());
        Iterator iter = freezerMD.iterator();
        while (iter.hasNext()) {
            MetaData metadata = (MetaData) iter.next();
            //String key = metadata.getVisibleName();
            String key = metadata.getVisibleName();
            TempMetaData meta = new TempMetaData(metadata);
            freezerData.put(key, meta);
            int m = (key.equals(Fieldname.VISIBLENAME)) ? 0
                    : (key.equals(Fieldname.COMMENT)) ? 1
                    : (key.equals(Fieldname.CREATOR)) ? 2
                    : (key.equals(Fieldname.CREATEDATE)) ? 3
                    : (key.equals(Fieldname.MODIFIER)) ? 4
                    : (key.equals(Fieldname.MODIFDATE)) ? 5
                    : (key.equals(Fieldname.FREEZERID)) ? 6
                    : (key.equals(Fieldname.DESCRIPTION)) ? 7
                    : (key.equals(Fieldname.LOCATION)) ? 8
                    : (key.equals(Fieldname.TEMPERATURE)) ? 9
                    : (key.equals(Fieldname.SORTORDER)) ? 10 : 0;
            meta.setValueRowNumber(m);
            int n = metadata.getViewColumnNumber().intValue();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        // normalization of column numbers
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = freezerData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData meta = (TempMetaData) miter.next();
                if (meta.getViewColumnNumber() == destin) {
                    meta.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        freezerViewSize = target - 1;
    }

    public String getShippedToLongName(String key) {
        TempMetaData metadata = (TempMetaData) shippedToData.get(key);
        return (metadata != null) ? metadata.getLongName() : key;
    }

    public String getFreezerLongName(String key) {
        TempMetaData metadata = (TempMetaData) freezerData.get(key);
        return (metadata != null) ? metadata.getLongName() : key;
    }
//for Peter

    public String getPropertyLongName(String sort, String key) {
        return (sort.equals(Fieldname.SHIPPEDTO)) ? getShippedToLongName(key)
                : (sort.equals(Fieldname.FREEZER)) ? getFreezerLongName(key)
                : "";
    }

    public String getShippedToShortName(String key) {
        TempMetaData metadata = (TempMetaData) shippedToData.get(key);
        return (metadata != null) ? metadata.getShortName() : key;
    }

    public String getFreezerShortName(String key) {
        TempMetaData metadata = (TempMetaData) freezerData.get(key);
        return (metadata != null) ? metadata.getShortName() : key;
    }
//for Peter

    public String getPropertyShortName(String sort, String key) {
        return (sort.equals(Fieldname.SHIPPEDTO)) ? getShippedToShortName(key)
                : (sort.equals(Fieldname.FREEZER)) ? getFreezerShortName(key)
                : "";
    }

    public boolean getShippedToCanShowInReport(String key) {
        TempMetaData metadata = (TempMetaData) shippedToData.get(key);
        return (metadata != null) ? metadata.canShowInReport() : false;
    }

    public boolean getFreezerCanShowInReport(String key) {
        TempMetaData metadata = (TempMetaData) freezerData.get(key);
        return (metadata != null) ? metadata.canShowInReport() : false;
    }

    public boolean getShippedToSortable(String key) {
        TempMetaData metadata = (TempMetaData) shippedToData.get(key);
        return (metadata != null) ? metadata.isSortable() : false;
    }

    public boolean getFreezerSortable(String key) {
        TempMetaData metadata = (TempMetaData) freezerData.get(key);
        return (metadata != null) ? metadata.isSortable() : false;
    }

//for Peter
    public boolean getPropertySortable(String sort, String key) {
        return (sort.equals(Fieldname.SHIPPEDTO)) ? getShippedToSortable(key)
                : (sort.equals(Fieldname.FREEZER)) ? getFreezerSortable(key)
                : false;
    }

    public int getShippedToViewColumnNumber(String key) {
        TempMetaData metadata = (TempMetaData) shippedToData.get(key);
        return (metadata != null) ? metadata.getActualColumnNumber() : 0;
    }

    public int getFreezerViewColumnNumber(String key) {
        TempMetaData metadata = (TempMetaData) freezerData.get(key);
        return (metadata != null) ? metadata.getActualColumnNumber() : 0;
    }

//for Peter
    public int getPropertyViewColumnNumber(String sort, String key) {
        return (sort.equals(Fieldname.SHIPPEDTO)) ? getShippedToViewColumnNumber(key)
                : (sort.equals(Fieldname.FREEZER)) ? getFreezerViewColumnNumber(key)
                : 0;
    }

    public String[] getShippedToKeyArray() {
        String[] keyArray = new String[shippedToViewSize];
        Iterator iter = shippedToData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                keyArray[metadata.getActualColumnNumber() - 1] =
                        metadata.getVisibleName();
            }
        }
        return keyArray;
    }

    public String[] getFreezerKeyArray() {
        //Println used to be here
        //Println used to be here
        String[] keyArray = new String[freezerViewSize];
        //Println used to be here
        Iterator iter = freezerData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                keyArray[metadata.getActualColumnNumber() - 1] =
                        metadata.getVisibleName();
            }
        }
        //Println used to be here
        return keyArray;
    }

    public byte[] getShippedToIndexArray() {
        byte[] indexArray = new byte[100];
        Iterator iter = shippedToData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                indexArray[metadata.getValueRowNumber()] =
                        metadata.getActualColumnNumber();
            }
        }
        return indexArray;
    }

    public byte[] getFreezerIndexArray() {
        byte[] indexArray = new byte[100];
        Iterator iter = freezerData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                indexArray[metadata.getValueRowNumber()] =
                        metadata.getActualColumnNumber();
            }
        }
        return indexArray;
    }

//for Peter's view/export
    public TempMetaData getShippedToMetaData(String key) {
        return (TempMetaData) shippedToData.get(key);
    }

    public TempMetaData getFreezerMetaData(String key) {
        return (TempMetaData) freezerData.get(key);
    }

    public Collection getShippedToMetaData() {
        return shippedToData.values();
    }

    public Collection getFreezerMetaData() {
        return freezerData.values();
    }

    public static List createOrderMetaData(Collection values) {
        // order settings by column number
        ArrayList dataList = new ArrayList(values);

        orderMetaData(dataList);

        return dataList;
    }

    public static void orderMetaData(List dataList) {
        Collections.sort(dataList, new Comparator() {

            public int compare(Object obj1, Object obj2) {
                TempMetaData data1 = (TempMetaData) obj1;
                TempMetaData data2 = (TempMetaData) obj2;

                if (data1.getViewColumnNumber() != data2.getViewColumnNumber()) {
                    if (Math.max(data1.getViewColumnNumber(), data2.getViewColumnNumber()) > 0) {
                        if (Math.min(data1.getViewColumnNumber(), data2.getViewColumnNumber()) > 0) {
                            return (data1.getViewColumnNumber() < data2.getViewColumnNumber() ? -1 : 1);
                        } else {
                            return (data1.getViewColumnNumber() <= 0 ? 1 : -1);
                        }
                    }
                }

                return data1.getShortName().compareTo(data2.getShortName());
            }
        });
    }

    public void normalizeShippedToData() {
        int viewSize = 0;
        Iterator iter = shippedToData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            metadata.setActualColumnNumber((byte) 0);
            int n = metadata.getViewColumnNumber();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = shippedToData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData metadata = (TempMetaData) miter.next();
                if (metadata.getViewColumnNumber() == destin) {
                    metadata.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        shippedToViewSize = target - 1;
    }

    public void normalizeFreezerData() {
        int viewSize = 0;
        Iterator iter = freezerData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            metadata.setActualColumnNumber((byte) 0);
            int n = metadata.getViewColumnNumber();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = freezerData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData metadata = (TempMetaData) miter.next();
                if (metadata.getViewColumnNumber() == destin) {
                    metadata.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        freezerViewSize = target - 1;
    }

// utility methods
    private static void appendFields(
            StringBuffer toAppend, Field[] fs, String selName) {
        for (int i = 0; i < fs.length; i++) {
            String name = fs[i].getName();
            if (name.equals("id") || name.equals("aliquots")
                    || name.equals("samples") || name.equals("relations2")
                    || name.equals("concentrationUnit") || name.equals("volumeUnit")) {
                continue;
            }
            toAppend.append("<option");
            if (name.equals(selName)) {
                toAppend.append(" selected");
            }
            toAppend.append(" value='").append(name).append("'>").append(name);
        }
    }

    public static String getClassFieldPrompter(Class cl, String selName) {
        StringBuffer xyz = new StringBuffer();
        Field[] fs = cl.getDeclaredFields();
        appendFields(xyz, fs, selName);
        fs = cl.getSuperclass().getDeclaredFields();
        appendFields(xyz, fs, selName);
        return xyz.toString();
    }
    private HashMap containertypeData = null;
    private int containertypeViewSize = 0;

    public void buildContainerTypeData(List containertypeMD) {
        //Println used to be here
        //Println used to be here
        //Println used to be here
        int viewSize = 0;
        containertypeData = new HashMap(containertypeMD.size());
        Iterator iter = containertypeMD.iterator();
        while (iter.hasNext()) {
            MetaData metadata = (MetaData) iter.next();
            String key = metadata.getVisibleName();
            //Println used to be here
            TempMetaData meta = new TempMetaData(metadata);
            containertypeData.put(key, meta);
            int m = (key.equals(Fieldname.VISIBLENAME)) ? 0
                    : (key.equals(Fieldname.COMMENT)) ? 1
                    : (key.equals(Fieldname.CREATOR)) ? 2
                    : (key.equals(Fieldname.CREATEDATE)) ? 3
                    : (key.equals(Fieldname.MODIFIER)) ? 4
                    : (key.equals(Fieldname.MODIFDATE)) ? 5
                    : (key.equals(Fieldname.CONTAINERTYPEID)) ? 6
                    : (key.equals(Fieldname.DESCRIPTION)) ? 7
                    : (key.equals(Fieldname.ROWS)) ? 8
                    : (key.equals(Fieldname.COLUMNS)) ? 9
                    : (key.equals(Fieldname.SORTORDER)) ? 10 : 0;

            meta.setValueRowNumber(m);
            int n = metadata.getViewColumnNumber().intValue();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        // normalization of column numbers
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = containertypeData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData meta = (TempMetaData) miter.next();
                if (meta.getViewColumnNumber() == destin) {
                    meta.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        containertypeViewSize = target - 1;
    }

    public String getContainerTypeLongName(String key) {
        TempMetaData metadata = (TempMetaData) containertypeData.get(key);
        return (metadata != null) ? metadata.getLongName() : key;
    }

    public boolean getContainerTypeCanShowInReport(String key) {
        TempMetaData metadata = (TempMetaData) containertypeData.get(key);
        return (metadata != null) ? metadata.canShowInReport() : false;
    }

    public String getContainerTypeShortName(String key) {
        TempMetaData metadata = (TempMetaData) containertypeData.get(key);
        return (metadata != null) ? metadata.getShortName() : key;
    }

    public boolean getContainerTypeSortable(String key) {
        TempMetaData metadata = (TempMetaData) containertypeData.get(key);
        return (metadata != null) ? metadata.isSortable() : false;
    }

    public int getContainerTypeViewColumnNumber(String key) {
        TempMetaData metadata = (TempMetaData) containertypeData.get(key);
        return (metadata != null) ? metadata.getActualColumnNumber() : 0;
    }

    public String[] getContainerTypeKeyArray() {
        //Println used to be here
        //Println used to be here
        String[] keyArray = new String[containertypeViewSize];
        //Println used to be here
        //Println used to be here
        //Println used to be here
        //Println used to be here
        Iterator iter = containertypeData.values().iterator();
        //Println used to be here
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                keyArray[metadata.getActualColumnNumber() - 1] =
                        metadata.getVisibleName();
            }
        }
        //Println used to be here
        return keyArray;
    }

    public byte[] getContainerTypeIndexArray() {
        byte[] indexArray = new byte[100];
        Iterator iter = containertypeData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                indexArray[metadata.getValueRowNumber()] =
                        metadata.getActualColumnNumber();
            }
        }
        return indexArray;
    }

    public void normalizeContainerTypeData() {
        int viewSize = 0;
        Iterator iter = containertypeData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            metadata.setActualColumnNumber((byte) 0);
            int n = metadata.getViewColumnNumber();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = containertypeData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData metadata = (TempMetaData) miter.next();
                if (metadata.getViewColumnNumber() == destin) {
                    metadata.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        containertypeViewSize = target - 1;
    }

    public Collection getContainerTypeMetaData() {
        return containertypeData.values();
    }

    public TempMetaData getContainerTypeMetaData(String key) {
        return (TempMetaData) containertypeData.get(key);
    }
    private HashMap cohortData = null;
    private int cohortViewSize = 0;

    public void buildCohortData(List cohortMD) {
        int viewSize = 0;
        cohortData = new HashMap(cohortMD.size());
        Iterator iter = cohortMD.iterator();
        while (iter.hasNext()) {
            MetaData metadata = (MetaData) iter.next();
            //String key = metadata.getVisibleName();
            String key = metadata.getVisibleName();
            TempMetaData meta = new TempMetaData(metadata);
            cohortData.put(key, meta);
            int m = (key.equals(Fieldname.VISIBLENAME)) ? 0
                    : (key.equals(Fieldname.COMMENT)) ? 1
                    : (key.equals(Fieldname.CREATOR)) ? 2
                    : (key.equals(Fieldname.CREATEDATE)) ? 3
                    : (key.equals(Fieldname.MODIFIER)) ? 4
                    : (key.equals(Fieldname.MODIFDATE)) ? 5
                    : (key.equals(Fieldname.COHORTID)) ? 6
                    : (key.equals(Fieldname.DESCRIPTION)) ? 7
                    : (key.equals(Fieldname.SORTORDER)) ? 8 : 0;

            meta.setValueRowNumber(m);
            int n = metadata.getViewColumnNumber().intValue();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        // normalization of column numbers
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = cohortData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData meta = (TempMetaData) miter.next();
                if (meta.getViewColumnNumber() == destin) {
                    meta.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        cohortViewSize = target - 1;
    }

    public String getCohortLongName(String key) {
        TempMetaData metadata = (TempMetaData) cohortData.get(key);
        return (metadata != null) ? metadata.getLongName() : key;
    }

    public boolean getCohortCanShowInReport(String key) {
        TempMetaData metadata = (TempMetaData) cohortData.get(key);
        return (metadata != null) ? metadata.canShowInReport() : false;
    }

    public String getCohortShortName(String key) {
        TempMetaData metadata = (TempMetaData) cohortData.get(key);
        return (metadata != null) ? metadata.getShortName() : key;
    }

    public boolean getCohortSortable(String key) {
        TempMetaData metadata = (TempMetaData) cohortData.get(key);
        return (metadata != null) ? metadata.isSortable() : false;
    }

    public int getCohortViewColumnNumber(String key) {
        TempMetaData metadata = (TempMetaData) cohortData.get(key);
        return (metadata != null) ? metadata.getActualColumnNumber() : 0;
    }

    public String[] getCohortKeyArray() {
        String[] keyArray = new String[cohortViewSize];
        Iterator iter = cohortData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                keyArray[metadata.getActualColumnNumber() - 1] =
                        metadata.getVisibleName();
            }
        }
        return keyArray;
    }

    public byte[] getCohortIndexArray() {
        byte[] indexArray = new byte[100];
        Iterator iter = cohortData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                indexArray[metadata.getValueRowNumber()] =
                        metadata.getActualColumnNumber();
            }
        }
        return indexArray;
    }

    public void normalizeCohortData() {
        int viewSize = 0;
        Iterator iter = cohortData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            metadata.setActualColumnNumber((byte) 0);
            int n = metadata.getViewColumnNumber();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = cohortData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData metadata = (TempMetaData) miter.next();
                if (metadata.getViewColumnNumber() == destin) {
                    metadata.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        cohortViewSize = target - 1;
    }

    public Collection getCohortMetaData() {
        return cohortData.values();
    }

    public TempMetaData getCohortMetaData(String key) {
        return (TempMetaData) cohortData.get(key);
    }
    private HashMap containerData = null;
    private int containerViewSize = 0;

    public void buildContainerData(List containerMD) {
        int viewSize = 0;
        containerData = new HashMap(containerMD.size());
        Iterator iter = containerMD.iterator();
        while (iter.hasNext()) {
            MetaData metadata = (MetaData) iter.next();
            //String key = metadata.getVisibleName();
            String key = metadata.getVisibleName();
            TempMetaData meta = new TempMetaData(metadata);
            containerData.put(key, meta);
            int m = (key.equals(Fieldname.VISIBLENAME)) ? 0
                    : (key.equals(Fieldname.COMMENT)) ? 1
                    : (key.equals(Fieldname.CREATOR)) ? 2
                    : (key.equals(Fieldname.CREATEDATE)) ? 3
                    : (key.equals(Fieldname.MODIFIER)) ? 4
                    : (key.equals(Fieldname.MODIFDATE)) ? 5
                    : (key.equals(Fieldname.CONTAINERID)) ? 6
                    : (key.equals(Fieldname.CONTAINERNAME)) ? 7
                    : (key.equals(Fieldname.CONTAINERTYPE)) ? 8
                    : (key.equals(Fieldname.FREEZER)) ? 9
                    : (key.equals(Fieldname.SHELF)) ? 10
                    : (key.equals(Fieldname.DISCARDED)) ? 11
                    : (key.equals(Fieldname.SHIPPEDOUT)) ? 12
                    : (key.equals(Fieldname.SHIPPEDDATE)) ? 13
                    : (key.equals(Fieldname.SHIPPEDTO)) ? 14
                    : (key.equals(Fieldname.COMMENTS)) ? 15
                    : (key.equals(Fieldname.ISSTOCK)) ? 16
                    : (key.equals(Fieldname.VALID)) ? 17
                    : (key.equals(Fieldname.CONTAINERALIAS)) ? 18
                    : (key.equals(Fieldname.LOT)) ? 19
                    : (key.equals(Fieldname.INITIALS)) ? 20
                    : (key.equals(Fieldname.DATEONCONTAINER)) ? 21
                    : (key.equals(Fieldname.LOCATION)) ? 22
                    : (key.equals(Fieldname.BARCODE)) ? 23
                    : (key.equals(Fieldname.CHECKEDOUT)) ? 24 : 0;

            meta.setValueRowNumber(m);
            int n = metadata.getViewColumnNumber().intValue();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        // normalization of column numbers
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = containerData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData meta = (TempMetaData) miter.next();
                if (meta.getViewColumnNumber() == destin) {
                    meta.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        containerViewSize = target - 1;
    }

    public String getContainerLongName(String key) {
        TempMetaData metadata = (TempMetaData) containerData.get(key);
        return (metadata != null) ? metadata.getLongName() : key;
    }

    public boolean getContainerCanShowInReport(String key) {
        TempMetaData metadata = (TempMetaData) containerData.get(key);
        return (metadata != null) ? metadata.canShowInReport() : false;
    }

    public String getContainerShortName(String key) {
        TempMetaData metadata = (TempMetaData) containerData.get(key);
        return (metadata != null) ? metadata.getShortName() : key;
    }

    public boolean getContainerSortable(String key) {
        TempMetaData metadata = (TempMetaData) containerData.get(key);
        return (metadata != null) ? metadata.isSortable() : false;
    }

    public int getContainerViewColumnNumber(String key) {
        TempMetaData metadata = (TempMetaData) containerData.get(key);
        return (metadata != null) ? metadata.getActualColumnNumber() : 0;
    }

    public String[] getContainerKeyArray() {
        String[] keyArray = new String[containerViewSize];
        Iterator iter = containerData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                keyArray[metadata.getActualColumnNumber() - 1] =
                        metadata.getVisibleName();
            }
        }
        return keyArray;
    }

    public byte[] getContainerIndexArray() {
        byte[] indexArray = new byte[100];
        Iterator iter = containerData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                indexArray[metadata.getValueRowNumber()] =
                        metadata.getActualColumnNumber();
            }
        }
        return indexArray;
    }

    public void normalizeContainerData() {
        int viewSize = 0;
        Iterator iter = containerData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            metadata.setActualColumnNumber((byte) 0);
            int n = metadata.getViewColumnNumber();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = containerData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData metadata = (TempMetaData) miter.next();
                if (metadata.getViewColumnNumber() == destin) {
                    metadata.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        containerViewSize = target - 1;
    }

    public Collection getContainerMetaData() {
        return containerData.values();
    }

    public TempMetaData getContainerMetaData(String key) {
        return (TempMetaData) containerData.get(key);
    }
    private HashMap sampleprocessData = null;
    private int sampleprocessViewSize = 0;

    public void buildSampleProcessData(List sampleprocessMD) {
        int viewSize = 0;
        sampleprocessData = new HashMap(sampleprocessMD.size());
        Iterator iter = sampleprocessMD.iterator();
        while (iter.hasNext()) {
            MetaData metadata = (MetaData) iter.next();
            //String key = metadata.getVisibleName();
            String key = metadata.getVisibleName();
            TempMetaData meta = new TempMetaData(metadata);
            sampleprocessData.put(key, meta);
            int m = (key.equals(Fieldname.VISIBLENAME)) ? 0
                    : (key.equals(Fieldname.COMMENT)) ? 1
                    : (key.equals(Fieldname.CREATOR)) ? 2
                    : (key.equals(Fieldname.CREATEDATE)) ? 3
                    : (key.equals(Fieldname.MODIFIER)) ? 4
                    : (key.equals(Fieldname.MODIFDATE)) ? 5
                    : (key.equals(Fieldname.SAMPLEPROCESSID)) ? 6
                    : (key.equals(Fieldname.DESCRIPTION)) ? 7
                    : (key.equals(Fieldname.SORTORDER)) ? 8 : 0;

            meta.setValueRowNumber(m);
            int n = metadata.getViewColumnNumber().intValue();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        // normalization of column numbers
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = sampleprocessData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData meta = (TempMetaData) miter.next();
                if (meta.getViewColumnNumber() == destin) {
                    meta.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        sampleprocessViewSize = target - 1;
    }

    public String getSampleProcessLongName(String key) {
        TempMetaData metadata = (TempMetaData) sampleprocessData.get(key);
        return (metadata != null) ? metadata.getLongName() : key;
    }

    public boolean getSampleProcessCanShowInReport(String key) {
        TempMetaData metadata = (TempMetaData) sampleprocessData.get(key);
        return (metadata != null) ? metadata.canShowInReport() : false;
    }

    public String getSampleProcessShortName(String key) {
        TempMetaData metadata = (TempMetaData) sampleprocessData.get(key);
        return (metadata != null) ? metadata.getShortName() : key;
    }

    public boolean getSampleProcessSortable(String key) {
        TempMetaData metadata = (TempMetaData) sampleprocessData.get(key);
        return (metadata != null) ? metadata.isSortable() : false;
    }

    public int getSampleProcessViewColumnNumber(String key) {
        TempMetaData metadata = (TempMetaData) sampleprocessData.get(key);
        return (metadata != null) ? metadata.getActualColumnNumber() : 0;
    }

    public String[] getSampleProcessKeyArray() {
        String[] keyArray = new String[sampleprocessViewSize];
        Iterator iter = sampleprocessData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                keyArray[metadata.getActualColumnNumber() - 1] =
                        metadata.getVisibleName();
            }
        }
        return keyArray;
    }

    public byte[] getSampleProcessIndexArray() {
        byte[] indexArray = new byte[100];
        Iterator iter = sampleprocessData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                indexArray[metadata.getValueRowNumber()] =
                        metadata.getActualColumnNumber();
            }
        }
        return indexArray;
    }

    public void normalizeSampleProcessData() {
        int viewSize = 0;
        Iterator iter = sampleprocessData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            metadata.setActualColumnNumber((byte) 0);
            int n = metadata.getViewColumnNumber();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = sampleprocessData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData metadata = (TempMetaData) miter.next();
                if (metadata.getViewColumnNumber() == destin) {
                    metadata.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        sampleprocessViewSize = target - 1;
    }

    public Collection getSampleProcessMetaData() {
        return sampleprocessData.values();
    }

    public TempMetaData getSampleProcessMetaData(String key) {
        return (TempMetaData) sampleprocessData.get(key);
    }
    private HashMap sampletypeData = null;
    private int sampletypeViewSize = 0;

    public void buildSampleTypeData(List sampletypeMD) {
        int viewSize = 0;
        sampletypeData = new HashMap(sampletypeMD.size());
        Iterator iter = sampletypeMD.iterator();
        while (iter.hasNext()) {
            MetaData metadata = (MetaData) iter.next();
            //String key = metadata.getVisibleName();
            String key = metadata.getVisibleName();
            TempMetaData meta = new TempMetaData(metadata);
            sampletypeData.put(key, meta);
            int m = (key.equals(Fieldname.VISIBLENAME)) ? 0
                    : (key.equals(Fieldname.COMMENT)) ? 1
                    : (key.equals(Fieldname.CREATOR)) ? 2
                    : (key.equals(Fieldname.CREATEDATE)) ? 3
                    : (key.equals(Fieldname.MODIFIER)) ? 4
                    : (key.equals(Fieldname.MODIFDATE)) ? 5
                    : (key.equals(Fieldname.SAMPLETYPEID)) ? 6
                    : (key.equals(Fieldname.DESCRIPTION)) ? 7
                    : (key.equals(Fieldname.SORTORDER)) ? 8 : 0;

            meta.setValueRowNumber(m);
            int n = metadata.getViewColumnNumber().intValue();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        // normalization of column numbers
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = sampletypeData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData meta = (TempMetaData) miter.next();
                if (meta.getViewColumnNumber() == destin) {
                    meta.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        sampletypeViewSize = target - 1;
    }

    public String getSampleTypeLongName(String key) {
        TempMetaData metadata = (TempMetaData) sampletypeData.get(key);
        return (metadata != null) ? metadata.getLongName() : key;
    }

    public boolean getSampleTypeCanShowInReport(String key) {
        TempMetaData metadata = (TempMetaData) sampletypeData.get(key);
        return (metadata != null) ? metadata.canShowInReport() : false;
    }

    public String getSampleTypeShortName(String key) {
        TempMetaData metadata = (TempMetaData) sampletypeData.get(key);
        return (metadata != null) ? metadata.getShortName() : key;
    }

    public boolean getSampleTypeSortable(String key) {
        TempMetaData metadata = (TempMetaData) sampletypeData.get(key);
        return (metadata != null) ? metadata.isSortable() : false;
    }

    public int getSampleTypeViewColumnNumber(String key) {
        TempMetaData metadata = (TempMetaData) sampletypeData.get(key);
        return (metadata != null) ? metadata.getActualColumnNumber() : 0;
    }

    public String[] getSampleTypeKeyArray() {
        String[] keyArray = new String[sampletypeViewSize];
        Iterator iter = sampletypeData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                keyArray[metadata.getActualColumnNumber() - 1] =
                        metadata.getVisibleName();
            }
        }
        return keyArray;
    }

    public byte[] getSampleTypeIndexArray() {
        byte[] indexArray = new byte[100];
        Iterator iter = sampletypeData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                indexArray[metadata.getValueRowNumber()] =
                        metadata.getActualColumnNumber();
            }
        }
        return indexArray;
    }

    public void normalizeSampleTypeData() {
        int viewSize = 0;
        Iterator iter = sampletypeData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            metadata.setActualColumnNumber((byte) 0);
            int n = metadata.getViewColumnNumber();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = sampletypeData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData metadata = (TempMetaData) miter.next();
                if (metadata.getViewColumnNumber() == destin) {
                    metadata.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        sampletypeViewSize = target - 1;
    }

    public Collection getSampleTypeMetaData() {
        return sampletypeData.values();
    }

    public TempMetaData getSampleTypeMetaData(String key) {
        return (TempMetaData) sampletypeData.get(key);
    }
    private HashMap subjectData = null;
    private int subjectViewSize = 0;

    public void buildSubjectData(List subjectMD) {
        int viewSize = 0;
        subjectData = new HashMap(subjectMD.size());
        Iterator iter = subjectMD.iterator();
        while (iter.hasNext()) {
            MetaData metadata = (MetaData) iter.next();
            //String key = metadata.getVisibleName();
            String key = metadata.getVisibleName();
            TempMetaData meta = new TempMetaData(metadata);
            subjectData.put(key, meta);
            int m = (key.equals(Fieldname.VISIBLENAME)) ? 0
                    : (key.equals(Fieldname.COMMENT)) ? 1
                    : (key.equals(Fieldname.CREATOR)) ? 2
                    : (key.equals(Fieldname.CREATEDATE)) ? 3
                    : (key.equals(Fieldname.MODIFIER)) ? 4
                    : (key.equals(Fieldname.MODIFDATE)) ? 5
                    : (key.equals(Fieldname.SUBJECTID)) ? 6
                    : (key.equals(Fieldname.SUBJECTNAME)) ? 7
                    : (key.equals(Fieldname.FATHERNAME)) ? 8
                    : (key.equals(Fieldname.MOTHERNAME)) ? 9
                    : (key.equals(Fieldname.COHORT)) ? 10
                    : (key.equals(Fieldname.GENDER)) ? 11
                    : (key.equals(Fieldname.HASCONSENT)) ? 12
                    : (key.equals(Fieldname.FAMILYID)) ? 13
                    : (key.equals(Fieldname.ETHNICITY)) ? 14
                    : (key.equals(Fieldname.COMMENTS)) ? 15 : 0;

            meta.setValueRowNumber(m);
            int n = metadata.getViewColumnNumber().intValue();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        // normalization of column numbers
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = subjectData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData meta = (TempMetaData) miter.next();
                if (meta.getViewColumnNumber() == destin) {
                    meta.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        subjectViewSize = target - 1;
    }

    public String getSubjectLongName(String key) {
        TempMetaData metadata = (TempMetaData) subjectData.get(key);
        return (metadata != null) ? metadata.getLongName() : key;
    }

    public boolean getSubjectCanShowInReport(String key) {
        TempMetaData metadata = (TempMetaData) subjectData.get(key);
        return (metadata != null) ? metadata.canShowInReport() : false;
    }

    public String getSubjectShortName(String key) {
        TempMetaData metadata = (TempMetaData) subjectData.get(key);
        return (metadata != null) ? metadata.getShortName() : key;
    }

    public boolean getSubjectSortable(String key) {
        TempMetaData metadata = (TempMetaData) subjectData.get(key);
        return (metadata != null) ? metadata.isSortable() : false;
    }

    public int getSubjectViewColumnNumber(String key) {
        TempMetaData metadata = (TempMetaData) subjectData.get(key);
        return (metadata != null) ? metadata.getActualColumnNumber() : 0;
    }

    public String[] getSubjectKeyArray() {
        String[] keyArray = new String[subjectViewSize];
        Iterator iter = subjectData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                keyArray[metadata.getActualColumnNumber() - 1] =
                        metadata.getVisibleName();
            }
        }
        return keyArray;
    }

    public byte[] getSubjectIndexArray() {
        byte[] indexArray = new byte[100];
        Iterator iter = subjectData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                indexArray[metadata.getValueRowNumber()] =
                        metadata.getActualColumnNumber();
            }
        }
        return indexArray;
    }

    public void normalizeSubjectData() {
        int viewSize = 0;
        Iterator iter = subjectData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            metadata.setActualColumnNumber((byte) 0);
            int n = metadata.getViewColumnNumber();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = subjectData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData metadata = (TempMetaData) miter.next();
                if (metadata.getViewColumnNumber() == destin) {
                    metadata.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        subjectViewSize = target - 1;
    }

    public Collection getSubjectMetaData() {
        return subjectData.values();
    }

    public TempMetaData getSubjectMetaData(String key) {
        return (TempMetaData) subjectData.get(key);
    }
    private HashMap sampleData = null;
    private int sampleViewSize = 0;

    public void buildSampleData(List sampleMD) {
        int viewSize = 0;
        sampleData = new HashMap(sampleMD.size());
        Iterator iter = sampleMD.iterator();
        while (iter.hasNext()) {
            MetaData metadata = (MetaData) iter.next();
            //String key = metadata.getVisibleName();
            String key = metadata.getVisibleName();
            TempMetaData meta = new TempMetaData(metadata);
            sampleData.put(key, meta);
            int m = (key.equals(Fieldname.VISIBLENAME)) ? 0
                    : (key.equals(Fieldname.COMMENT)) ? 1
                    : (key.equals(Fieldname.CREATOR)) ? 2
                    : (key.equals(Fieldname.CREATEDATE)) ? 3
                    : (key.equals(Fieldname.MODIFIER)) ? 4
                    : (key.equals(Fieldname.MODIFDATE)) ? 5
                    : (key.equals(Fieldname.SAMPLEID)) ? 6
                    : (key.equals(Fieldname.SAMPLENAME)) ? 7
                    : (key.equals(Fieldname.VALID)) ? 8
                    : (key.equals(Fieldname.PARENT)) ? 9
                    : (key.equals(Fieldname.SAMPLETYPE)) ? 10
                    : (key.equals(Fieldname.SAMPLEPROCESS)) ? 11
                    : (key.equals(Fieldname.SUBJECT)) ? 12
                    : (key.equals(Fieldname.DATECOLLECTED)) ? 13
                    : (key.equals(Fieldname.DATEEXTRACTED)) ? 14
                    : (key.equals(Fieldname.COMMENTS)) ? 15 : 0;

            meta.setValueRowNumber(m);
            int n = metadata.getViewColumnNumber().intValue();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        // normalization of column numbers
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = sampleData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData meta = (TempMetaData) miter.next();
                if (meta.getViewColumnNumber() == destin) {
                    meta.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        sampleViewSize = target - 1;
    }

    public String getSampleLongName(String key) {
        TempMetaData metadata = (TempMetaData) sampleData.get(key);
        return (metadata != null) ? metadata.getLongName() : key;
    }

    public boolean getSampleCanShowInReport(String key) {
        TempMetaData metadata = (TempMetaData) sampleData.get(key);
        return (metadata != null) ? metadata.canShowInReport() : false;
    }

    public String getSampleShortName(String key) {
        TempMetaData metadata = (TempMetaData) sampleData.get(key);
        return (metadata != null) ? metadata.getShortName() : key;
    }

    public boolean getSampleSortable(String key) {
        TempMetaData metadata = (TempMetaData) sampleData.get(key);
        return (metadata != null) ? metadata.isSortable() : false;
    }

    public int getSampleViewColumnNumber(String key) {
        TempMetaData metadata = (TempMetaData) sampleData.get(key);
        return (metadata != null) ? metadata.getActualColumnNumber() : 0;
    }

    public String[] getSampleKeyArray() {
        String[] keyArray = new String[sampleViewSize];
        Iterator iter = sampleData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                keyArray[metadata.getActualColumnNumber() - 1] =
                        metadata.getVisibleName();
            }
        }
        return keyArray;
    }

    public byte[] getSampleIndexArray() {
        byte[] indexArray = new byte[100];
        Iterator iter = sampleData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                indexArray[metadata.getValueRowNumber()] =
                        metadata.getActualColumnNumber();
            }
        }
        return indexArray;
    }

    public void normalizeSampleData() {
        int viewSize = 0;
        Iterator iter = sampleData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            metadata.setActualColumnNumber((byte) 0);
            int n = metadata.getViewColumnNumber();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = sampleData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData metadata = (TempMetaData) miter.next();
                if (metadata.getViewColumnNumber() == destin) {
                    metadata.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        sampleViewSize = target - 1;
    }

    public Collection getSampleMetaData() {
        return sampleData.values();
    }

    public TempMetaData getSampleMetaData(String key) {
        return (TempMetaData) sampleData.get(key);
    }
    // CONTAINER CONTENTS
    private HashMap containercontentData = null;
    private int containercontentViewSize = 0;

    public void buildContainerContentData(List containercontentMD) {
        // order of containercontentMD determines order of columns
        // if they have the same view column number
        int viewSize = 0;
        containercontentData = new HashMap(containercontentMD.size());
        Iterator iter = containercontentMD.iterator();
        while (iter.hasNext()) {
            icapture.hibernate.ContainerContentMetaData metadata = (icapture.hibernate.ContainerContentMetaData) iter.next();

//            System.out.println("buildContainerContentData " + metadata.getVisibleName()
//                    + " col: " + metadata.getActualColumnNumber()
//                    + " row: " + metadata.getValueRowNumber());
            // visible name = PROPERTY_NAME column of metadata table
            // it should match name in object.java file
            String key = metadata.getVisibleName();
            TempMetaData meta = new TempMetaData(metadata);
            containercontentData.put(key, meta);
            int m = -1;

            m = (key.equals(Fieldname.VISIBLENAME)) ? 0
                    : (key.equals(Fieldname.COMMENT)) ? 1
                    : (key.equals(Fieldname.CREATOR)) ? 2
                    : (key.equals(Fieldname.CREATEDATE)) ? 3
                    : (key.equals(Fieldname.MODIFIER)) ? 4
                    : (key.equals(Fieldname.MODIFDATE)) ? 5
                    : (key.equals(Fieldname.CONTAINERCONTENTSID)) ? 6
                    : (key.equals(Fieldname.PARENT)) ? 7
                    : (key.equals(Fieldname.CONTAMINATED)) ? 8
                    : (key.equals(Fieldname.CONTAINER)) ? 9
                    : (key.equals(Fieldname.ROW)) ? 10
                    : (key.equals(Fieldname.COLUMN)) ? 11
                    : (key.equals(Fieldname.SAMPLE)) ? 12
                    : (key.equals(Fieldname.VOLUME)) ? 13
                    : (key.equals(Fieldname.CONCENTRATION)) ? 14
                    : (key.equals(Fieldname.DILUTION)) ? 15
                    : (key.equals(Fieldname.COMMENTS)) ? 16
                    : (key.equals(Fieldname.SAMPLESAMPLETYPE)) ? 17
                    : (key.equals(Fieldname.SAMPLEDATECOLLECTED)) ? 18
                    : (key.equals(Fieldname.SAMPLEDATEEXTRACTED)) ? 19
                    : (key.equals(Fieldname.MATERIALTYPE)) ? 20
                    : (key.equals(Fieldname.CONTAINERISSTOCK)) ? 21
                    : (key.equals(Fieldname.CONTAINERCONTAINERTYPE)) ? 22
                    : (key.equals(Fieldname.CONTAINERFREEZER)) ? 23
                    : (key.equals(Fieldname.CONTAINERSHELF)) ? 24
                    : (key.equals(Fieldname.MODIFDATE)) ? 25
                    : (key.equals(Fieldname.AMPLIFICATIONDATE)) ? 26
                    : (key.equals(Fieldname.BARCODE)) ? 27
                    : (key.equals(Fieldname.CONTAINERLOCATION)) ? 28 : 0;

            meta.setValueRowNumber(m);
            int n = metadata.getViewColumnNumber().intValue();
            if (n > viewSize) {
                viewSize = n;
            }
            // deals with case if two columns have same view number
            // and both are highest in range
            if (n == viewSize) {
                viewSize = n + 1;
            }
        }

        // keep track of how many ccMetaData columns there are
        ccMetaDataAllColCount = 29;


        // normalization of column numbers
        byte target = 1;
        // for every slot in the view table
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = containercontentData.values().iterator();
            // run through all possible columns
            while (miter.hasNext()) {
                TempMetaData meta = (TempMetaData) miter.next();
                // if the column's view order number matches the index
                // of the slot, set the column's actual col num to: its view col num
                // plus the number of other columns with the same view num
                if (meta.getViewColumnNumber() == destin) {
                    meta.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        containercontentViewSize = target - 1;
    }

    public String getContainerContentLongName(String key) {
        TempMetaData metadata = (TempMetaData) containercontentData.get(key);
        return (metadata != null) ? metadata.getLongName() : key;
    }

    public boolean getContainerContentCanShowInReport(String key) {
        TempMetaData metadata = (TempMetaData) containercontentData.get(key);
        return (metadata != null) ? metadata.canShowInReport() : false;
    }

    public String getContainerContentShortName(String key) {
        TempMetaData metadata = (TempMetaData) containercontentData.get(key);
        return (metadata != null) ? metadata.getShortName() : key;
    }

    public boolean getContainerContentSortable(String key) {
        TempMetaData metadata = (TempMetaData) containercontentData.get(key);
        return (metadata != null) ? metadata.isSortable() : false;
    }

    public int getContainerContentViewColumnNumber(String key) {
        TempMetaData metadata = (TempMetaData) containercontentData.get(key);
        return (metadata != null) ? metadata.getActualColumnNumber() : 0;
    }

    public String[] getContainerContentKeyArray() {
        String[] keyArray = new String[containercontentViewSize];
        Iterator iter = containercontentData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                keyArray[metadata.getActualColumnNumber() - 1] =
                        metadata.getVisibleName();
            }
        }
        return keyArray;
    }

    public byte[] getContainerContentIndexArray() {
        byte[] indexArray = new byte[ccMetaDataAllColCount];
        Iterator iter = containercontentData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
//            System.out.println(metadata.getLongName()
//                    + " col: " + metadata.getActualColumnNumber()
//                    + " row: " + metadata.getValueRowNumber());
            if (metadata.getActualColumnNumber() > 0) {
                indexArray[metadata.getValueRowNumber()] =
                        metadata.getActualColumnNumber();
            }
        }
        return indexArray;
    }

    public void normalizeContainerContentData() {
        int viewSize = 0;
        Iterator iter = containercontentData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            metadata.setActualColumnNumber((byte) 0);
            int n = metadata.getViewColumnNumber();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = containercontentData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData metadata = (TempMetaData) miter.next();
                if (metadata.getViewColumnNumber() == destin) {
                    metadata.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        containercontentViewSize = target - 1;
    }

    public Collection getContainerContentMetaData() {
        return containercontentData.values();
    }

    public TempMetaData getContainerContentMetaData(String key) {
        return (TempMetaData) containercontentData.get(key);
    }
    private HashMap genotypingruncontainerData = null;
    private int genotypingruncontainerViewSize = 0;

    public void buildGenotypingRunContainerData(List genotypingruncontainerMD) {
        int viewSize = 0;
        genotypingruncontainerData = new HashMap(genotypingruncontainerMD.size());
        Iterator iter = genotypingruncontainerMD.iterator();
        while (iter.hasNext()) {
            MetaData metadata = (MetaData) iter.next();
            //String key = metadata.getVisibleName();
            String key = metadata.getVisibleName();
            TempMetaData meta = new TempMetaData(metadata);
            genotypingruncontainerData.put(key, meta);
            int m = (key.equals(Fieldname.VISIBLENAME)) ? 0
                    : (key.equals(Fieldname.COMMENT)) ? 1
                    : (key.equals(Fieldname.CREATOR)) ? 2
                    : (key.equals(Fieldname.CREATEDATE)) ? 3
                    : (key.equals(Fieldname.MODIFIER)) ? 4
                    : (key.equals(Fieldname.MODIFDATE)) ? 5
                    : (key.equals(Fieldname.GENOTYPINGRUNID)) ? 6
                    : (key.equals(Fieldname.CONTAINERID)) ? 7 : 0;

            meta.setValueRowNumber(m);
            int n = metadata.getViewColumnNumber().intValue();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        // normalization of column numbers
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = genotypingruncontainerData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData meta = (TempMetaData) miter.next();
                if (meta.getViewColumnNumber() == destin) {
                    meta.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        genotypingruncontainerViewSize = target - 1;
    }

    public String getGenotypingRunContainerLongName(String key) {
        TempMetaData metadata = (TempMetaData) genotypingruncontainerData.get(key);
        return (metadata != null) ? metadata.getLongName() : key;
    }

    public boolean getGenotypingRunContainerCanShowInReport(String key) {
        TempMetaData metadata = (TempMetaData) genotypingruncontainerData.get(key);
        return (metadata != null) ? metadata.canShowInReport() : false;
    }

    public String getGenotypingRunContainerShortName(String key) {
        TempMetaData metadata = (TempMetaData) genotypingruncontainerData.get(key);
        return (metadata != null) ? metadata.getShortName() : key;
    }

    public boolean getGenotypingRunContainerSortable(String key) {
        TempMetaData metadata = (TempMetaData) genotypingruncontainerData.get(key);
        return (metadata != null) ? metadata.isSortable() : false;
    }

    public int getGenotypingRunContainerViewColumnNumber(String key) {
        TempMetaData metadata = (TempMetaData) genotypingruncontainerData.get(key);
        return (metadata != null) ? metadata.getActualColumnNumber() : 0;
    }

    public String[] getGenotypingRunContainerKeyArray() {
        String[] keyArray = new String[genotypingruncontainerViewSize];
        Iterator iter = genotypingruncontainerData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                keyArray[metadata.getActualColumnNumber() - 1] =
                        metadata.getVisibleName();
            }
        }
        return keyArray;
    }

    public byte[] getGenotypingRunContainerIndexArray() {
        byte[] indexArray = new byte[100];
        Iterator iter = genotypingruncontainerData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                indexArray[metadata.getValueRowNumber()] =
                        metadata.getActualColumnNumber();
            }
        }
        return indexArray;
    }

    public void normalizeGenotypingRunContainerData() {
        int viewSize = 0;
        Iterator iter = genotypingruncontainerData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            metadata.setActualColumnNumber((byte) 0);
            int n = metadata.getViewColumnNumber();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = genotypingruncontainerData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData metadata = (TempMetaData) miter.next();
                if (metadata.getViewColumnNumber() == destin) {
                    metadata.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        genotypingruncontainerViewSize = target - 1;
    }

    public Collection getGenotypingRunContainerMetaData() {
        return genotypingruncontainerData.values();
    }

    public TempMetaData getGenotypingRunContainerMetaData(String key) {
        return (TempMetaData) genotypingruncontainerData.get(key);
    }
    private HashMap genotypingrunData = null;
    private int genotypingrunViewSize = 0;

    public void buildGenotypingRunData(List genotypingrunMD) {
        int viewSize = 0;
        genotypingrunData = new HashMap(genotypingrunMD.size());
        Iterator iter = genotypingrunMD.iterator();
        while (iter.hasNext()) {
            MetaData metadata = (MetaData) iter.next();
            //String key = metadata.getVisibleName();
            String key = metadata.getVisibleName();
            TempMetaData meta = new TempMetaData(metadata);
            genotypingrunData.put(key, meta);
            int m = (key.equals(Fieldname.VISIBLENAME)) ? 0
                    : (key.equals(Fieldname.COMMENT)) ? 1
                    : (key.equals(Fieldname.CREATOR)) ? 2
                    : (key.equals(Fieldname.CREATEDATE)) ? 3
                    : (key.equals(Fieldname.MODIFIER)) ? 4
                    : (key.equals(Fieldname.MODIFDATE)) ? 5
                    : (key.equals(Fieldname.GENOTYPINGRUNID)) ? 6
                    : (key.equals(Fieldname.DESCRIPTION)) ? 7
                    : (key.equals(Fieldname.DATE)) ? 8 : 0;

            meta.setValueRowNumber(m);
            int n = metadata.getViewColumnNumber().intValue();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        // normalization of column numbers
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = genotypingrunData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData meta = (TempMetaData) miter.next();
                if (meta.getViewColumnNumber() == destin) {
                    meta.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        genotypingrunViewSize = target - 1;
    }

    public String getGenotypingRunLongName(String key) {
        TempMetaData metadata = (TempMetaData) genotypingrunData.get(key);
        return (metadata != null) ? metadata.getLongName() : key;
    }

    public boolean getGenotypingRunCanShowInReport(String key) {
        TempMetaData metadata = (TempMetaData) genotypingrunData.get(key);
        return (metadata != null) ? metadata.canShowInReport() : false;
    }

    public String getGenotypingRunShortName(String key) {
        TempMetaData metadata = (TempMetaData) genotypingrunData.get(key);
        return (metadata != null) ? metadata.getShortName() : key;
    }

    public boolean getGenotypingRunSortable(String key) {
        TempMetaData metadata = (TempMetaData) genotypingrunData.get(key);
        return (metadata != null) ? metadata.isSortable() : false;
    }

    public int getGenotypingRunViewColumnNumber(String key) {
        TempMetaData metadata = (TempMetaData) genotypingrunData.get(key);
        return (metadata != null) ? metadata.getActualColumnNumber() : 0;
    }

    public String[] getGenotypingRunKeyArray() {
        String[] keyArray = new String[genotypingrunViewSize];
        Iterator iter = genotypingrunData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                keyArray[metadata.getActualColumnNumber() - 1] =
                        metadata.getVisibleName();
            }
        }
        return keyArray;
    }

    public byte[] getGenotypingRunIndexArray() {
        byte[] indexArray = new byte[100];
        Iterator iter = genotypingrunData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                indexArray[metadata.getValueRowNumber()] =
                        metadata.getActualColumnNumber();
            }
        }
        return indexArray;
    }

    public void normalizeGenotypingRunData() {
        int viewSize = 0;
        Iterator iter = genotypingrunData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            metadata.setActualColumnNumber((byte) 0);
            int n = metadata.getViewColumnNumber();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = genotypingrunData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData metadata = (TempMetaData) miter.next();
                if (metadata.getViewColumnNumber() == destin) {
                    metadata.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        genotypingrunViewSize = target - 1;
    }

    public Collection getGenotypingRunMetaData() {
        return genotypingrunData.values();
    }

    public TempMetaData getGenotypingRunMetaData(String key) {
        return (TempMetaData) genotypingrunData.get(key);
    }
    private HashMap genotypingrunsamplestatusData = null;
    private int genotypingrunsamplestatusViewSize = 0;

    public void buildGenotypingRunSampleStatusData(List genotypingrunsamplestatusMD) {
        int viewSize = 0;
        genotypingrunsamplestatusData = new HashMap(genotypingrunsamplestatusMD.size());
        Iterator iter = genotypingrunsamplestatusMD.iterator();
        while (iter.hasNext()) {
            MetaData metadata = (MetaData) iter.next();
            //String key = metadata.getVisibleName();
            String key = metadata.getVisibleName();
            TempMetaData meta = new TempMetaData(metadata);
            genotypingrunsamplestatusData.put(key, meta);
            int m = (key.equals(Fieldname.VISIBLENAME)) ? 0
                    : (key.equals(Fieldname.COMMENT)) ? 1
                    : (key.equals(Fieldname.CREATOR)) ? 2
                    : (key.equals(Fieldname.CREATEDATE)) ? 3
                    : (key.equals(Fieldname.MODIFIER)) ? 4
                    : (key.equals(Fieldname.MODIFDATE)) ? 5
                    : (key.equals(Fieldname.GENOTYPINGRUNID)) ? 6
                    : (key.equals(Fieldname.CONTAINERCONTENTSID)) ? 7
                    : (key.equals(Fieldname.VALID)) ? 8
                    : (key.equals(Fieldname.CALLRATE)) ? 9 : 0;

            meta.setValueRowNumber(m);
            int n = metadata.getViewColumnNumber().intValue();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        // normalization of column numbers
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = genotypingrunsamplestatusData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData meta = (TempMetaData) miter.next();
                if (meta.getViewColumnNumber() == destin) {
                    meta.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        genotypingrunsamplestatusViewSize = target - 1;
    }

    public String getGenotypingRunSampleStatusLongName(String key) {
        TempMetaData metadata = (TempMetaData) genotypingrunsamplestatusData.get(key);
        return (metadata != null) ? metadata.getLongName() : key;
    }

    public boolean getGenotypingRunSampleStatusCanShowInReport(String key) {
        TempMetaData metadata = (TempMetaData) genotypingrunsamplestatusData.get(key);
        return (metadata != null) ? metadata.canShowInReport() : false;
    }

    public String getGenotypingRunSampleStatusShortName(String key) {
        TempMetaData metadata = (TempMetaData) genotypingrunsamplestatusData.get(key);
        return (metadata != null) ? metadata.getShortName() : key;
    }

    public boolean getGenotypingRunSampleStatusSortable(String key) {
        TempMetaData metadata = (TempMetaData) genotypingrunsamplestatusData.get(key);
        return (metadata != null) ? metadata.isSortable() : false;
    }

    public int getGenotypingRunSampleStatusViewColumnNumber(String key) {
        TempMetaData metadata = (TempMetaData) genotypingrunsamplestatusData.get(key);
        return (metadata != null) ? metadata.getActualColumnNumber() : 0;
    }

    public String[] getGenotypingRunSampleStatusKeyArray() {
        String[] keyArray = new String[genotypingrunsamplestatusViewSize];
        Iterator iter = genotypingrunsamplestatusData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                keyArray[metadata.getActualColumnNumber() - 1] =
                        metadata.getVisibleName();
            }
        }
        return keyArray;
    }

    public byte[] getGenotypingRunSampleStatusIndexArray() {
        byte[] indexArray = new byte[100];
        Iterator iter = genotypingrunsamplestatusData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                indexArray[metadata.getValueRowNumber()] =
                        metadata.getActualColumnNumber();
            }
        }
        return indexArray;
    }

    public void normalizeGenotypingRunSampleStatusData() {
        int viewSize = 0;
        Iterator iter = genotypingrunsamplestatusData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            metadata.setActualColumnNumber((byte) 0);
            int n = metadata.getViewColumnNumber();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = genotypingrunsamplestatusData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData metadata = (TempMetaData) miter.next();
                if (metadata.getViewColumnNumber() == destin) {
                    metadata.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        genotypingrunsamplestatusViewSize = target - 1;
    }

    public Collection getGenotypingRunSampleStatusMetaData() {
        return genotypingrunsamplestatusData.values();
    }

    public TempMetaData getGenotypingRunSampleStatusMetaData(String key) {
        return (TempMetaData) genotypingrunsamplestatusData.get(key);
    }
    private HashMap genotypingrunsnpstatusData = null;
    private int genotypingrunsnpstatusViewSize = 0;

    public void buildGenotypingRunSnpStatusData(List genotypingrunsnpstatusMD) {
        int viewSize = 0;
        genotypingrunsnpstatusData = new HashMap(genotypingrunsnpstatusMD.size());
        Iterator iter = genotypingrunsnpstatusMD.iterator();
        while (iter.hasNext()) {
            MetaData metadata = (MetaData) iter.next();
            //String key = metadata.getVisibleName();
            String key = metadata.getVisibleName();
            TempMetaData meta = new TempMetaData(metadata);
            genotypingrunsnpstatusData.put(key, meta);
            int m = (key.equals(Fieldname.VISIBLENAME)) ? 0
                    : (key.equals(Fieldname.COMMENT)) ? 1
                    : (key.equals(Fieldname.CREATOR)) ? 2
                    : (key.equals(Fieldname.CREATEDATE)) ? 3
                    : (key.equals(Fieldname.MODIFIER)) ? 4
                    : (key.equals(Fieldname.MODIFDATE)) ? 5
                    : (key.equals(Fieldname.GENOTYPINGRUNID)) ? 6
                    : (key.equals(Fieldname.SNPID)) ? 7
                    : (key.equals(Fieldname.VALID)) ? 8
                    : (key.equals(Fieldname.STRAND)) ? 9 : 0;

            meta.setValueRowNumber(m);
            int n = metadata.getViewColumnNumber().intValue();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        // normalization of column numbers
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = genotypingrunsnpstatusData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData meta = (TempMetaData) miter.next();
                if (meta.getViewColumnNumber() == destin) {
                    meta.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        genotypingrunsnpstatusViewSize = target - 1;
    }

    public String getGenotypingRunSnpStatusLongName(String key) {
        TempMetaData metadata = (TempMetaData) genotypingrunsnpstatusData.get(key);
        return (metadata != null) ? metadata.getLongName() : key;
    }

    public boolean getGenotypingRunSnpStatusCanShowInReport(String key) {
        TempMetaData metadata = (TempMetaData) genotypingrunsnpstatusData.get(key);
        return (metadata != null) ? metadata.canShowInReport() : false;
    }

    public String getGenotypingRunSnpStatusShortName(String key) {
        TempMetaData metadata = (TempMetaData) genotypingrunsnpstatusData.get(key);
        return (metadata != null) ? metadata.getShortName() : key;
    }

    public boolean getGenotypingRunSnpStatusSortable(String key) {
        TempMetaData metadata = (TempMetaData) genotypingrunsnpstatusData.get(key);
        return (metadata != null) ? metadata.isSortable() : false;
    }

    public int getGenotypingRunSnpStatusViewColumnNumber(String key) {
        TempMetaData metadata = (TempMetaData) genotypingrunsnpstatusData.get(key);
        return (metadata != null) ? metadata.getActualColumnNumber() : 0;
    }

    public String[] getGenotypingRunSnpStatusKeyArray() {
        String[] keyArray = new String[genotypingrunsnpstatusViewSize];
        Iterator iter = genotypingrunsnpstatusData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                keyArray[metadata.getActualColumnNumber() - 1] =
                        metadata.getVisibleName();
            }
        }
        return keyArray;
    }

    public byte[] getGenotypingRunSnpStatusIndexArray() {
        byte[] indexArray = new byte[100];
        Iterator iter = genotypingrunsnpstatusData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                indexArray[metadata.getValueRowNumber()] =
                        metadata.getActualColumnNumber();
            }
        }
        return indexArray;
    }

    public void normalizeGenotypingRunSnpStatusData() {
        int viewSize = 0;
        Iterator iter = genotypingrunsnpstatusData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            metadata.setActualColumnNumber((byte) 0);
            int n = metadata.getViewColumnNumber();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = genotypingrunsnpstatusData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData metadata = (TempMetaData) miter.next();
                if (metadata.getViewColumnNumber() == destin) {
                    metadata.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        genotypingrunsnpstatusViewSize = target - 1;
    }

    public Collection getGenotypingRunSnpStatusMetaData() {
        return genotypingrunsnpstatusData.values();
    }

    public TempMetaData getGenotypingRunSnpStatusMetaData(String key) {
        return (TempMetaData) genotypingrunsnpstatusData.get(key);
    }
    private HashMap genotypeData = null;
    private int genotypeViewSize = 0;

    public void buildGenotypeData(List genotypeMD) {
        int viewSize = 0;
        genotypeData = new HashMap(genotypeMD.size());
        Iterator iter = genotypeMD.iterator();
        while (iter.hasNext()) {
            MetaData metadata = (MetaData) iter.next();
            //String key = metadata.getVisibleName();
            String key = metadata.getVisibleName();
            TempMetaData meta = new TempMetaData(metadata);
            genotypeData.put(key, meta);
            int m = (key.equals(Fieldname.VISIBLENAME)) ? 0
                    : (key.equals(Fieldname.COMMENT)) ? 1
                    : (key.equals(Fieldname.CREATOR)) ? 2
                    : (key.equals(Fieldname.CREATEDATE)) ? 3
                    : (key.equals(Fieldname.MODIFIER)) ? 4
                    : (key.equals(Fieldname.MODIFDATE)) ? 5
                    : (key.equals(Fieldname.GENOTYPEID)) ? 6
                    : (key.equals(Fieldname.CONTAINERCONTENTSID)) ? 7
                    : (key.equals(Fieldname.GENOTYPINGRUNID)) ? 8
                    : (key.equals(Fieldname.ALLELE1)) ? 9
                    : (key.equals(Fieldname.ALLELE2)) ? 10
                    : (key.equals(Fieldname.SNPID)) ? 11 : 0;

            meta.setValueRowNumber(m);
            int n = metadata.getViewColumnNumber().intValue();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        // normalization of column numbers
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = genotypeData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData meta = (TempMetaData) miter.next();
                if (meta.getViewColumnNumber() == destin) {
                    meta.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        genotypeViewSize = target - 1;
    }

    public String getGenotypeLongName(String key) {
        TempMetaData metadata = (TempMetaData) genotypeData.get(key);
        return (metadata != null) ? metadata.getLongName() : key;
    }

    public boolean getGenotypeCanShowInReport(String key) {
        TempMetaData metadata = (TempMetaData) genotypeData.get(key);
        return (metadata != null) ? metadata.canShowInReport() : false;
    }

    public String getGenotypeShortName(String key) {
        TempMetaData metadata = (TempMetaData) genotypeData.get(key);
        return (metadata != null) ? metadata.getShortName() : key;
    }

    public boolean getGenotypeSortable(String key) {
        TempMetaData metadata = (TempMetaData) genotypeData.get(key);
        return (metadata != null) ? metadata.isSortable() : false;
    }

    public int getGenotypeViewColumnNumber(String key) {
        TempMetaData metadata = (TempMetaData) genotypeData.get(key);
        return (metadata != null) ? metadata.getActualColumnNumber() : 0;
    }

    public String[] getGenotypeKeyArray() {
        String[] keyArray = new String[genotypeViewSize];
        Iterator iter = genotypeData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                keyArray[metadata.getActualColumnNumber() - 1] =
                        metadata.getVisibleName();
            }
        }
        return keyArray;
    }

    public byte[] getGenotypeIndexArray() {
        byte[] indexArray = new byte[100];
        Iterator iter = genotypeData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                indexArray[metadata.getValueRowNumber()] =
                        metadata.getActualColumnNumber();
            }
        }
        return indexArray;
    }

    public void normalizeGenotypeData() {
        int viewSize = 0;
        Iterator iter = genotypeData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            metadata.setActualColumnNumber((byte) 0);
            int n = metadata.getViewColumnNumber();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = genotypeData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData metadata = (TempMetaData) miter.next();
                if (metadata.getViewColumnNumber() == destin) {
                    metadata.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        genotypeViewSize = target - 1;
    }

    public Collection getGenotypeMetaData() {
        return genotypeData.values();
    }

    public TempMetaData getGenotypeMetaData(String key) {
        return (TempMetaData) genotypeData.get(key);
    }
    private HashMap snpData = null;
    private int snpViewSize = 0;

    public void buildSnpData(List snpMD) {
        int viewSize = 0;
        snpData = new HashMap(snpMD.size());
        Iterator iter = snpMD.iterator();
        while (iter.hasNext()) {
            MetaData metadata = (MetaData) iter.next();
            //String key = metadata.getVisibleName();
            String key = metadata.getVisibleName();
            TempMetaData meta = new TempMetaData(metadata);
            snpData.put(key, meta);
            int m = (key.equals(Fieldname.VISIBLENAME)) ? 0
                    : (key.equals(Fieldname.COMMENT)) ? 1
                    : (key.equals(Fieldname.CREATOR)) ? 2
                    : (key.equals(Fieldname.CREATEDATE)) ? 3
                    : (key.equals(Fieldname.MODIFIER)) ? 4
                    : (key.equals(Fieldname.MODIFDATE)) ? 5
                    : (key.equals(Fieldname.SNPID)) ? 6
                    : (key.equals(Fieldname.CHROMOSOME)) ? 7
                    : (key.equals(Fieldname.POS)) ? 8
                    : (key.equals(Fieldname.GENEID)) ? 9
                    : (key.equals(Fieldname.FXN_CLASS)) ? 10
                    : (key.equals(Fieldname.RSNUMBER)) ? 11 : 0;

            meta.setValueRowNumber(m);
            int n = metadata.getViewColumnNumber().intValue();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        // normalization of column numbers
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = snpData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData meta = (TempMetaData) miter.next();
                if (meta.getViewColumnNumber() == destin) {
                    meta.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        snpViewSize = target - 1;
    }

    public String getSnpLongName(String key) {
        TempMetaData metadata = (TempMetaData) snpData.get(key);
        return (metadata != null) ? metadata.getLongName() : key;
    }

    public boolean getSnpCanShowInReport(String key) {
        TempMetaData metadata = (TempMetaData) snpData.get(key);
        return (metadata != null) ? metadata.canShowInReport() : false;
    }

    public String getSnpShortName(String key) {
        TempMetaData metadata = (TempMetaData) snpData.get(key);
        return (metadata != null) ? metadata.getShortName() : key;
    }

    public boolean getSnpSortable(String key) {
        TempMetaData metadata = (TempMetaData) snpData.get(key);
        return (metadata != null) ? metadata.isSortable() : false;
    }

    public int getSnpViewColumnNumber(String key) {
        TempMetaData metadata = (TempMetaData) snpData.get(key);
        return (metadata != null) ? metadata.getActualColumnNumber() : 0;
    }

    public String[] getSnpKeyArray() {
        String[] keyArray = new String[snpViewSize];
        Iterator iter = snpData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                keyArray[metadata.getActualColumnNumber() - 1] =
                        metadata.getVisibleName();
            }
        }
        return keyArray;
    }

    public byte[] getSnpIndexArray() {
        byte[] indexArray = new byte[100];
        Iterator iter = snpData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                indexArray[metadata.getValueRowNumber()] =
                        metadata.getActualColumnNumber();
            }
        }
        return indexArray;
    }

    public void normalizeSnpData() {
        int viewSize = 0;
        Iterator iter = snpData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            metadata.setActualColumnNumber((byte) 0);
            int n = metadata.getViewColumnNumber();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = snpData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData metadata = (TempMetaData) miter.next();
                if (metadata.getViewColumnNumber() == destin) {
                    metadata.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        snpViewSize = target - 1;
    }

    public Collection getSnpMetaData() {
        return snpData.values();
    }

    public TempMetaData getSnpMetaData(String key) {
        return (TempMetaData) snpData.get(key);
    }
    private HashMap ethnicityData = null;
    private int ethnicityViewSize = 0;

    public void buildEthnicityData(List ethnicityMD) {
        int viewSize = 0;
        ethnicityData = new HashMap(ethnicityMD.size());
        Iterator iter = ethnicityMD.iterator();
        while (iter.hasNext()) {
            MetaData metadata = (MetaData) iter.next();
            //String key = metadata.getVisibleName();
            String key = metadata.getVisibleName();
            TempMetaData meta = new TempMetaData(metadata);
            ethnicityData.put(key, meta);
            int m = (key.equals(Fieldname.VISIBLENAME)) ? 0
                    : (key.equals(Fieldname.COMMENT)) ? 1
                    : (key.equals(Fieldname.CREATOR)) ? 2
                    : (key.equals(Fieldname.CREATEDATE)) ? 3
                    : (key.equals(Fieldname.MODIFIER)) ? 4
                    : (key.equals(Fieldname.MODIFDATE)) ? 5
                    : (key.equals(Fieldname.ETHNICITYID)) ? 6
                    : (key.equals(Fieldname.ETHNICITY)) ? 7 : 0;

            meta.setValueRowNumber(m);
            int n = metadata.getViewColumnNumber().intValue();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        // normalization of column numbers
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = ethnicityData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData meta = (TempMetaData) miter.next();
                if (meta.getViewColumnNumber() == destin) {
                    meta.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        ethnicityViewSize = target - 1;
    }

    public String getEthnicityLongName(String key) {
        TempMetaData metadata = (TempMetaData) ethnicityData.get(key);
        return (metadata != null) ? metadata.getLongName() : key;
    }

    public boolean getEthnicityCanShowInReport(String key) {
        TempMetaData metadata = (TempMetaData) ethnicityData.get(key);
        return (metadata != null) ? metadata.canShowInReport() : false;
    }

    public String getEthnicityShortName(String key) {
        TempMetaData metadata = (TempMetaData) ethnicityData.get(key);
        return (metadata != null) ? metadata.getShortName() : key;
    }

    public boolean getEthnicitySortable(String key) {
        TempMetaData metadata = (TempMetaData) ethnicityData.get(key);
        return (metadata != null) ? metadata.isSortable() : false;
    }

    public int getEthnicityViewColumnNumber(String key) {
        TempMetaData metadata = (TempMetaData) ethnicityData.get(key);
        return (metadata != null) ? metadata.getActualColumnNumber() : 0;
    }

    public String[] getEthnicityKeyArray() {
        String[] keyArray = new String[ethnicityViewSize];
        Iterator iter = ethnicityData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                keyArray[metadata.getActualColumnNumber() - 1] =
                        metadata.getVisibleName();
            }
        }
        return keyArray;
    }

    public byte[] getEthnicityIndexArray() {
        byte[] indexArray = new byte[100];
        Iterator iter = ethnicityData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                indexArray[metadata.getValueRowNumber()] =
                        metadata.getActualColumnNumber();
            }
        }
        return indexArray;
    }

    public void normalizeEthnicityData() {
        int viewSize = 0;
        Iterator iter = ethnicityData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            metadata.setActualColumnNumber((byte) 0);
            int n = metadata.getViewColumnNumber();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = ethnicityData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData metadata = (TempMetaData) miter.next();
                if (metadata.getViewColumnNumber() == destin) {
                    metadata.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        ethnicityViewSize = target - 1;
    }

    public Collection getEthnicityMetaData() {
        return ethnicityData.values();
    }

    public TempMetaData getEthnicityMetaData(String key) {
        return (TempMetaData) ethnicityData.get(key);
    }
    private HashMap materialtypeData = null;
    private int materialtypeViewSize = 0;

    public void buildMaterialTypeData(List materialtypeMD) {
        int viewSize = 0;
        materialtypeData = new HashMap(materialtypeMD.size());
        Iterator iter = materialtypeMD.iterator();
        while (iter.hasNext()) {
            MetaData metadata = (MetaData) iter.next();
            //String key = metadata.getVisibleName();
            String key = metadata.getVisibleName();
            TempMetaData meta = new TempMetaData(metadata);
            materialtypeData.put(key, meta);
            int m = (key.equals(Fieldname.VISIBLENAME)) ? 0
                    : (key.equals(Fieldname.COMMENT)) ? 1
                    : (key.equals(Fieldname.CREATOR)) ? 2
                    : (key.equals(Fieldname.CREATEDATE)) ? 3
                    : (key.equals(Fieldname.MODIFIER)) ? 4
                    : (key.equals(Fieldname.MODIFDATE)) ? 5
                    : (key.equals(Fieldname.MATERIALTYPEID)) ? 6
                    : (key.equals(Fieldname.DESCRIPTION)) ? 7
                    : (key.equals(Fieldname.SORTORDER)) ? 8 : 0;

            meta.setValueRowNumber(m);
            int n = metadata.getViewColumnNumber().intValue();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        // normalization of column numbers
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = materialtypeData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData meta = (TempMetaData) miter.next();
                if (meta.getViewColumnNumber() == destin) {
                    meta.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        materialtypeViewSize = target - 1;
    }

    public String getMaterialTypeLongName(String key) {
        TempMetaData metadata = (TempMetaData) materialtypeData.get(key);
        return (metadata != null) ? metadata.getLongName() : key;
    }

    public boolean getMaterialTypeCanShowInReport(String key) {
        TempMetaData metadata = (TempMetaData) materialtypeData.get(key);
        return (metadata != null) ? metadata.canShowInReport() : false;
    }

    public String getMaterialTypeShortName(String key) {
        TempMetaData metadata = (TempMetaData) materialtypeData.get(key);
        return (metadata != null) ? metadata.getShortName() : key;
    }

    public boolean getMaterialTypeSortable(String key) {
        TempMetaData metadata = (TempMetaData) materialtypeData.get(key);
        return (metadata != null) ? metadata.isSortable() : false;
    }

    public int getMaterialTypeViewColumnNumber(String key) {
        TempMetaData metadata = (TempMetaData) materialtypeData.get(key);
        return (metadata != null) ? metadata.getActualColumnNumber() : 0;
    }

    public String[] getMaterialTypeKeyArray() {
        String[] keyArray = new String[materialtypeViewSize];
        Iterator iter = materialtypeData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                keyArray[metadata.getActualColumnNumber() - 1] =
                        metadata.getVisibleName();
            }
        }
        return keyArray;
    }

    public byte[] getMaterialTypeIndexArray() {
        byte[] indexArray = new byte[22];
        Iterator iter = materialtypeData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                indexArray[metadata.getValueRowNumber()] =
                        metadata.getActualColumnNumber();
            }
        }
        return indexArray;
    }

    public void normalizeMaterialTypeData() {
        int viewSize = 0;
        Iterator iter = materialtypeData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            metadata.setActualColumnNumber((byte) 0);
            int n = metadata.getViewColumnNumber();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = materialtypeData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData metadata = (TempMetaData) miter.next();
                if (metadata.getViewColumnNumber() == destin) {
                    metadata.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        materialtypeViewSize = target - 1;
    }

    public Collection getMaterialTypeMetaData() {
        return materialtypeData.values();
    }

    public TempMetaData getMaterialTypeMetaData(String key) {
        return (TempMetaData) materialtypeData.get(key);
    }
    private HashMap controlData = null;
    private int controlViewSize = 0;

    public void buildControlData(List controlMD) {
        int viewSize = 0;
        controlData = new HashMap(controlMD.size());
        Iterator iter = controlMD.iterator();
        while (iter.hasNext()) {
            MetaData metadata = (MetaData) iter.next();
            //String key = metadata.getVisibleName();
            String key = metadata.getVisibleName();
            TempMetaData meta = new TempMetaData(metadata);
            controlData.put(key, meta);
            int m = (key.equals(Fieldname.VISIBLENAME)) ? 0
                    : (key.equals(Fieldname.COMMENT)) ? 1
                    : (key.equals(Fieldname.CREATOR)) ? 2
                    : (key.equals(Fieldname.CREATEDATE)) ? 3
                    : (key.equals(Fieldname.MODIFIER)) ? 4
                    : (key.equals(Fieldname.MODIFDATE)) ? 5
                    : (key.equals(Fieldname.CONTROLID)) ? 6
                    : (key.equals(Fieldname.DESCRIPTION)) ? 7
                    : (key.equals(Fieldname.CONTROLTYPE)) ? 8 : 0;

            meta.setValueRowNumber(m);
            int n = metadata.getViewColumnNumber().intValue();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        // normalization of column numbers
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = controlData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData meta = (TempMetaData) miter.next();
                if (meta.getViewColumnNumber() == destin) {
                    meta.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        controlViewSize = target - 1;
    }

    public String getControlLongName(String key) {
        TempMetaData metadata = (TempMetaData) controlData.get(key);
        return (metadata != null) ? metadata.getLongName() : key;
    }

    public boolean getControlCanShowInReport(String key) {
        TempMetaData metadata = (TempMetaData) controlData.get(key);
        return (metadata != null) ? metadata.canShowInReport() : false;
    }

    public String getControlShortName(String key) {
        TempMetaData metadata = (TempMetaData) controlData.get(key);
        return (metadata != null) ? metadata.getShortName() : key;
    }

    public boolean getControlSortable(String key) {
        TempMetaData metadata = (TempMetaData) controlData.get(key);
        return (metadata != null) ? metadata.isSortable() : false;
    }

    public int getControlViewColumnNumber(String key) {
        TempMetaData metadata = (TempMetaData) controlData.get(key);
        return (metadata != null) ? metadata.getActualColumnNumber() : 0;
    }

    public String[] getControlKeyArray() {
        String[] keyArray = new String[controlViewSize];
        Iterator iter = controlData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                keyArray[metadata.getActualColumnNumber() - 1] =
                        metadata.getVisibleName();
            }
        }
        return keyArray;
    }

    public byte[] getControlIndexArray() {
        byte[] indexArray = new byte[22];
        Iterator iter = controlData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                indexArray[metadata.getValueRowNumber()] =
                        metadata.getActualColumnNumber();
            }
        }
        return indexArray;
    }

    public void normalizeControlData() {
        int viewSize = 0;
        Iterator iter = controlData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            metadata.setActualColumnNumber((byte) 0);
            int n = metadata.getViewColumnNumber();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = controlData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData metadata = (TempMetaData) miter.next();
                if (metadata.getViewColumnNumber() == destin) {
                    metadata.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        controlViewSize = target - 1;
    }

    public Collection getControlMetaData() {
        return controlData.values();
    }

    public TempMetaData getControlMetaData(String key) {
        return (TempMetaData) controlData.get(key);
    }
    private HashMap userData = null;
    private int userViewSize = 0;

    public void buildUserData(List userMD) {
        int viewSize = 0;
        userData = new HashMap(userMD.size());
        Iterator iter = userMD.iterator();
        while (iter.hasNext()) {
            MetaData metadata = (MetaData) iter.next();
            //String key = metadata.getVisibleName();
            String key = metadata.getVisibleName();
            TempMetaData meta = new TempMetaData(metadata);
            userData.put(key, meta);
            int m = (key.equals(Fieldname.VISIBLENAME)) ? 12
                    : (key.equals(Fieldname.COMMENT)) ? 1
                    : (key.equals(Fieldname.CREATOR)) ? 2
                    : (key.equals(Fieldname.CREATEDATE)) ? 3
                    : (key.equals(Fieldname.MODIFIER)) ? 4
                    : (key.equals(Fieldname.MODIFDATE)) ? 5
                    : (key.equals(Fieldname.USERID)) ? 6
                    : (key.equals(Fieldname.LOGIN)) ? 7
                    : (key.equals(Fieldname.PASSW)) ? 8
                    : (key.equals(Fieldname.RIGHTS)) ? 9
                    : (key.equals(Fieldname.USERTYPEID)) ? 10
                    : (key.equals(Fieldname.INITIALS)) ? 11 : 0;

            meta.setValueRowNumber(m);
            int n = metadata.getViewColumnNumber().intValue();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        // normalization of column numbers
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = userData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData meta = (TempMetaData) miter.next();
                if (meta.getViewColumnNumber() == destin) {
                    meta.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        userViewSize = target - 1;
    }

    public String getUserLongName(String key) {
        TempMetaData metadata = (TempMetaData) userData.get(key);
        return (metadata != null) ? metadata.getLongName() : key;
    }

    public boolean getUserCanShowInReport(String key) {
        TempMetaData metadata = (TempMetaData) userData.get(key);
        return (metadata != null) ? metadata.canShowInReport() : false;
    }

    public String getUserShortName(String key) {
        TempMetaData metadata = (TempMetaData) userData.get(key);
        return (metadata != null) ? metadata.getShortName() : key;
    }

    public boolean getUserSortable(String key) {
        TempMetaData metadata = (TempMetaData) userData.get(key);
        return (metadata != null) ? metadata.isSortable() : false;
    }

    public int getUserViewColumnNumber(String key) {
        TempMetaData metadata = (TempMetaData) userData.get(key);
        return (metadata != null) ? metadata.getActualColumnNumber() : 0;
    }

    public String[] getUserKeyArray() {
        String[] keyArray = new String[userViewSize];
        Iterator iter = userData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                keyArray[metadata.getActualColumnNumber() - 1] =
                        metadata.getVisibleName();
            }
        }
        return keyArray;
    }

    public byte[] getUserIndexArray() {
        byte[] indexArray = new byte[22];
        Iterator iter = userData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                indexArray[metadata.getValueRowNumber()] =
                        metadata.getActualColumnNumber();
            }
        }
        return indexArray;
    }

    public void normalizeUserData() {
        int viewSize = 0;
        Iterator iter = userData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            metadata.setActualColumnNumber((byte) 0);
            int n = metadata.getViewColumnNumber();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = userData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData metadata = (TempMetaData) miter.next();
                if (metadata.getViewColumnNumber() == destin) {
                    metadata.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        userViewSize = target - 1;
    }

    public Collection getUserMetaData() {
        return userData.values();
    }

    public TempMetaData getUserMetaData(String key) {
        return (TempMetaData) userData.get(key);
    }
////////////////////////////----------------\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//|||||||||||||||||||||||||||SAMPLE DOCUMENTS||||||||||||||||||||||||||||||
/////////////////////////////----------------//////////////////////////////
    private HashMap sampleDocumentsData = null;
    private int sampleDocumentsViewSize = 0;

    public void buildSampleDocumentsData(List sampleDocumentsMD) {
        int viewSize = 0;
        sampleDocumentsData = new HashMap(sampleDocumentsMD.size());
        Iterator iter = sampleDocumentsMD.iterator();
        while (iter.hasNext()) {
            MetaData metadata = (MetaData) iter.next();
            //String key = metadata.getVisibleName();
            String key = metadata.getVisibleName();
            TempMetaData meta = new TempMetaData(metadata);
            sampleDocumentsData.put(key, meta);
            int m = (key.equals(Fieldname.VISIBLENAME)) ? 0
                    : (key.equals(Fieldname.COMMENT)) ? 1
                    : (key.equals(Fieldname.CREATOR)) ? 2
                    : (key.equals(Fieldname.CREATEDATE)) ? 3
                    : (key.equals(Fieldname.MODIFIER)) ? 4
                    : (key.equals(Fieldname.MODIFDATE)) ? 5
                    : (key.equals(Fieldname.SAMPLEDOCUMENTID)) ? 6
                    : (key.equals(Fieldname.SAMPLEID)) ? 7
                    : (key.equals(Fieldname.FILENAME)) ? 8
                    : (key.equals(Fieldname.COMMENTS)) ? 9 : 0;

            meta.setValueRowNumber(m);
            int n = metadata.getViewColumnNumber().intValue();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        // normalization of column numbers
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = sampleDocumentsData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData meta = (TempMetaData) miter.next();
                if (meta.getViewColumnNumber() == destin) {
                    meta.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        sampleDocumentsViewSize = target - 1;
    }

    public String getSampleDocumentsLongName(String key) {
        TempMetaData metadata = (TempMetaData) sampleDocumentsData.get(key);
        return (metadata != null) ? metadata.getLongName() : key;
    }

    public boolean getSampleDocumentsCanShowInReport(String key) {
        TempMetaData metadata = (TempMetaData) sampleDocumentsData.get(key);
        return (metadata != null) ? metadata.canShowInReport() : false;
    }

    public String getSampleDocumentsShortName(String key) {
        TempMetaData metadata = (TempMetaData) sampleDocumentsData.get(key);
        return (metadata != null) ? metadata.getShortName() : key;
    }

    public boolean getSampleDocumentsSortable(String key) {
        TempMetaData metadata = (TempMetaData) sampleDocumentsData.get(key);
        return (metadata != null) ? metadata.isSortable() : false;
    }

    public int getSampleDocumentsViewColumnNumber(String key) {
        TempMetaData metadata = (TempMetaData) sampleDocumentsData.get(key);
        return (metadata != null) ? metadata.getActualColumnNumber() : 0;
    }

    public String[] getSampleDocumentsKeyArray() {
        String[] keyArray = new String[sampleDocumentsViewSize];
        Iterator iter = sampleDocumentsData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                keyArray[metadata.getActualColumnNumber() - 1] =
                        metadata.getVisibleName();
            }
        }
        return keyArray;
    }

    public byte[] getSampleDocumentsIndexArray() {
        byte[] indexArray = new byte[100];
        Iterator iter = sampleDocumentsData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                indexArray[metadata.getValueRowNumber()] =
                        metadata.getActualColumnNumber();
            }
        }
        return indexArray;
    }

    public void normalizeSampleDocumentsData() {
        int viewSize = 0;
        Iterator iter = sampleDocumentsData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            metadata.setActualColumnNumber((byte) 0);
            int n = metadata.getViewColumnNumber();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = sampleDocumentsData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData metadata = (TempMetaData) miter.next();
                if (metadata.getViewColumnNumber() == destin) {
                    metadata.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        sampleDocumentsViewSize = target - 1;
    }

    public Collection getSampleDocumentsMetaData() {
        return sampleDocumentsData.values();
    }

    public TempMetaData getSampleDocumentsMetaData(String key) {
        return (TempMetaData) sampleDocumentsData.get(key);
    }
//////////////////////////////////////////////////////////SHIPMENT/////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////SHIPCONT/////////////////////////////////////////////////////////
    private HashMap shipmentData = null;
    private int shipmentViewSize = 0;

    public void buildShipmentData(List shipmentMD) {
        int viewSize = 0;
        shipmentData = new HashMap(shipmentMD.size());
        Iterator iter = shipmentMD.iterator();
        while (iter.hasNext()) {
            MetaData metadata = (MetaData) iter.next();
            String key = metadata.getVisibleName();
            TempMetaData meta = new TempMetaData(metadata);
            shipmentData.put(key, meta);
            int m = (key.equals(Fieldname.VISIBLENAME)) ? 0
                    : (key.equals(Fieldname.COMMENT)) ? 1
                    : (key.equals(Fieldname.CREATOR)) ? 2
                    : (key.equals(Fieldname.CREATEDATE)) ? 3
                    : (key.equals(Fieldname.MODIFIER)) ? 4
                    : (key.equals(Fieldname.MODIFDATE)) ? 5
                    : (key.equals(Fieldname.SHIPMENTID)) ? 6
                    : (key.equals(Fieldname.SHIPMENTNAME)) ? 7
                    : (key.equals(Fieldname.SHIPDATE)) ? 8
                    : (key.equals(Fieldname.SHIPPEDTOID)) ? 9
                    : (key.equals(Fieldname.SHIPACTION)) ? 10
                    : (key.equals(Fieldname.COMMENTS)) ? 11 : 0;
            meta.setValueRowNumber(m);
            int n = metadata.getViewColumnNumber().intValue();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        // normalization of column numbers
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = shipmentData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData meta = (TempMetaData) miter.next();
                if (meta.getViewColumnNumber() == destin) {
                    meta.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        shipmentViewSize = target - 1;
    }

    public String getShipmentLongName(String key) {
        TempMetaData metadata = (TempMetaData) shipmentData.get(key);
        return (metadata != null) ? metadata.getLongName() : key;
    }

    public String getShipmentShortName(String key) {
        TempMetaData metadata = (TempMetaData) shipmentData.get(key);
        return (metadata != null) ? metadata.getShortName() : key;
    }

    public boolean getShipmentCanShowInReport(String key) {
        TempMetaData metadata = (TempMetaData) shipmentData.get(key);
        return (metadata != null) ? metadata.canShowInReport() : false;
    }

    public boolean getShipmentSortable(String key) {
        TempMetaData metadata = (TempMetaData) shipmentData.get(key);
        return (metadata != null) ? metadata.isSortable() : false;
    }

    public int getShipmentViewColumnNumber(String key) {
        TempMetaData metadata = (TempMetaData) shipmentData.get(key);
        return (metadata != null) ? metadata.getActualColumnNumber() : 0;
    }

    public String[] getShipmentKeyArray() {
        String[] keyArray = new String[shipmentViewSize];
        Iterator iter = shipmentData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                keyArray[metadata.getActualColumnNumber() - 1] =
                        metadata.getVisibleName();
            }
        }
        return keyArray;
    }

    public byte[] getShipmentIndexArray() {
        byte[] indexArray = new byte[100];
        Iterator iter = shipmentData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            if (metadata.getActualColumnNumber() > 0) {
                indexArray[metadata.getValueRowNumber()] =
                        metadata.getActualColumnNumber();
            }
        }
        return indexArray;
    }

    public TempMetaData getShipmentMetaData(String key) {
        return (TempMetaData) shipmentData.get(key);
    }

    public Collection getShipmentMetaData() {
        return shipmentData.values();
    }

    public void normalizeShipmentData() {
        int viewSize = 0;
        Iterator iter = shipmentData.values().iterator();
        while (iter.hasNext()) {
            TempMetaData metadata = (TempMetaData) iter.next();
            metadata.setActualColumnNumber((byte) 0);
            int n = metadata.getViewColumnNumber();
            if (n > viewSize) {
                viewSize = n;
            }
        }
        byte target = 1;
        for (int destin = 1; destin <= viewSize; destin++) {
            Iterator miter = shipmentData.values().iterator();
            while (miter.hasNext()) {
                TempMetaData metadata = (TempMetaData) miter.next();
                if (metadata.getViewColumnNumber() == destin) {
                    metadata.setActualColumnNumber(target);
                    target++;
                }
            }
        }
        shipmentViewSize = target - 1;
    }
}
