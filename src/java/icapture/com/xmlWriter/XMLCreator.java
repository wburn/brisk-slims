package icapture.com.xmlWriter;

import java.util.*;
import icapture.com.UserHttpSess;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;

/**
 * @author Peteris
 */
public class XMLCreator {

    public XMLCreator(UserHttpSess tmpHttpSessObj) {
        this.sess = tmpHttpSessObj;
        this.objectMap = new HashMap();
        this.settingMap = new HashMap();
        this.settingSet = new HashSet();
        HttpSession ses = this.sess.getHttpSess();
        ServletContext sc = ses.getServletContext();
        String path = sc.getRealPath("/temp" + this.sess.getHttpSessId() + ".xxx");
        this.writer = new XMLWriter(path);
    }
    /*
    public void writeSearch()
    {
    if (this.objectMap.size() < 1) return;

    String objName;
    List settings;
    XMLSetting setting;
    boolean hasFilter;

    // add list of the object names
    this.addSection("Search", "disable", true);
    this.addMenu("Search" ,"head");

    for (int i = 0; i < this.menu.length; i++)
    {
    objName = this.menu[i];

    this.addField(null, null, true);
    this.addTable(objName + " search table", null, false);
    settings = (List) this.objectMap.get(objName + "Setting");
    hasFilter = false;

    // add field title
    this.addRow(null, objName + " field title", null, null, false);
    this.addCell(null, "larger", objName + " filter", false);
    this.endCell(false);
    this.endRow(false);

    for (Iterator iter = settings.iterator(); !hasFilter && iter.hasNext();)
    hasFilter = ((XMLSetting) iter.next()).isFiltred();

    if (hasFilter)
    {
    // add titles
    this.addRow(null, objName + " search setting titles", null, null, false);

    for (Iterator iter = settings.iterator(); iter.hasNext();)
    {
    setting = (XMLSetting) iter.next();

    if (setting.isFiltred())
    {
    this.addCell(null, "normal", setting.getShowName(), false);
    this.endCell(false);
    }
    }

    this.endRow(false);

    // add filters
    this.addRow(null, objName + " search row", null, null, false);

    for (Iterator iter = settings.iterator(); iter.hasNext();)
    {
    setting = (XMLSetting) iter.next();

    if (setting.isFiltred())
    {
    // add date filter
    if (this.isRangeFilter(setting)) this.addDateFilter(setting);
    // add select filter
    else this.addSelectFilter(setting);
    }
    }

    this.endRow(false);
    }

    this.endTable(true);
    this.endField(true);
    }

    this.addMenu("Search", "tail");
    this.endSection(true);
    }

    public void writeObjects()
    {
    XMLUtil.vassert(this.menu.length > 0);

    if (this.menu.length < 1) return;

    String objName;
    List settings;
    List objects;
    boolean stripOn;
    String selectObj = this.menu[0];

    // add list of the object name
    this.addSection("Objects", "disable", true);

    for (int i = 0; i < this.menu.length; i++)
    {
    this.writer().startElement("a");
    this.writer().finishStart(false);
    this.writer().addString(this.menu[i], false);
    this.writer().endElement(false);
    }
    this.endSection(true);

    // add values of the settings of the objects
    for (int i = 0; i < this.menu.length; i++)
    {
    objName = this.menu[i];
    settings = (List) this.objectMap.get(objName + "Setting");
    objects = (List) this.objectMap.get(objName + "List");
    stripOn = false;

    XMLUtil.vassert(settings != null && objects != null);

    this.addSection(objName, objName.equals(selectObj) ? null : "disable", true);
    this.addMenu(objName, "head");
    this.addTable(objName + " list table", null, false);
    this.addTittles(settings);

    for (Iterator objIter = objects.iterator(); objIter.hasNext();)
    this.addObject(objName, objIter.next(), (stripOn = !stripOn), settings);

    this.endTable(true);

    this.addMenu(objName, "tail");
    this.endSection(true);
    }
    }

    public void writeObjects(String selectName)
    {
    XMLUtil.vassert(this.menu.length > 0);

    String objName = XMLUtil.PERSON;
    List settings;
    List objects;
    boolean stripOn;
    String selectObj = this.menu[0];

    if (selectName.equals("samples")) objName = XMLUtil.SAMPLE;
    else if (selectName.equals("aliquots")) objName = XMLUtil.ALIQUOT;

    settings = (List)this.objectMap.get(objName + "Setting");
    objects = (List)this.objectMap.get(objName + "List");
    stripOn = false;

    XMLUtil.vassert(settings != null && objects != null);

    this.writeTitles(settings);

    for (Iterator objIter = objects.iterator(); objIter.hasNext(); )
    this.writeObject(objName, objIter.next(), settings);
    }

    public void writeExport(String selectName)
    {
    try
    {
    MetadataManager mdm = this.sess.getMetadataManager();
    String objName = this.menu[0];

    if (selectName.equals("samples")) objName = XMLUtil.SAMPLE;
    else if (selectName.equals("aliquots")) objName = XMLUtil.ALIQUOT;

    List objList = this.getObjectList(objName);
    List metaDatatList =  this.getMetaDataList(objName);
    Object objValue;
    Persistent obj;
    Timestamp time;
    TempMetaData metaData;

    mdm.orderMetaData(metaDatatList);

    // write titles
    String[] keyArray =
    (objName.equals(XMLUtil.PERSON)) ? mdm.getPersonKeyArray() :
    (objName.equals(XMLUtil.SAMPLE)) ? mdm.getSampleKeyArray() :
    (objName.equals(XMLUtil.ALIQUOT)) ? mdm.getAliquotKeyArray() :
    null;
    int colCount = keyArray.length;
    boolean canShowInReport[] = new boolean[keyArray.length];

    for (int i = 0; i < colCount; i++)
    {
    canShowInReport[i] = mdm.getPropertyCanShowInReport(objName, keyArray[i]);
    }

    for (int i = 0; i < colCount; i++)
    {
    if (canShowInReport[i])
    {
    this.writer.addString(mdm.getPersonShortName(keyArray[i]) + "\t", false);
    }
    }

    this.writer.newLine();

    // write objects
    String[] var;
    byte[] valueIndexArray =
    (objName.equals(XMLUtil.PERSON)) ? mdm.getPersonIndexArray() :
    (objName.equals(XMLUtil.SAMPLE)) ? mdm.getSampleIndexArray() :
    (objName.equals(XMLUtil.ALIQUOT)) ? mdm.getAliquotIndexArray() :
    null;

    for (Iterator oIter = objList.iterator(); oIter.hasNext();)
    {
    obj = (Persistent) oIter.next();

    var = obj.getValueArray(valueIndexArray, colCount);

    for (int i = 0; i < colCount; i++)
    {
    if (canShowInReport[i])
    {
    this.writer.addString((var[i].equals("&nbsp;") ? "" : var[i]) + "\t", false);
    }
    }

    this.writer.newLine();
    }
    }
    catch (Exception ex)
    {
    System.out.println("wrong MetadataManager!!!");
    }
    }

    private void writeTitles(List settings)
    {
    XMLSetting setting;
    for (Iterator iter = settings.iterator(); iter.hasNext();)
    {
    setting = (XMLSetting) iter.next();
    this.writer.addString(setting.getName() + "\t", false);
    }

    this.writer.newLine();
    }

    private void writeObject(String objectName, Object obj, List settings)
    {
    Object ownerIndex = this.objectMap.get(XMLCreator.getOwner(obj));
    XMLSetting setting;
    Object objValue;
    Timestamp time;

    XMLUtil.vassert(ownerIndex == null || ownerIndex instanceof Long);

    for (Iterator iter = settings.iterator(); iter.hasNext();)
    {
    setting = (XMLSetting) iter.next();

    XMLUtil.vassert(obj instanceof Persistent);

    objValue = ((Persistent) obj).getPropertyObject(setting.getName());

    if (objValue instanceof Timestamp)
    {
    time = (Timestamp) objValue;
    //objValue = (new SimpleDateFormat("yyyy.MM.dd hh:mm:ss")).format(time);
    objValue = time.toString();
    }
    this.writer.addString(objValue + "\t", false);
    }

    this.writer.newLine();
    }

    private void addObject(String objectName, Object obj, boolean stripOn, List settings)
    {
    Object ownerIndex = this.objectMap.get(XMLCreator.getOwner(obj));
    XMLSetting setting;
    Object objValue;

    XMLUtil.vassert(ownerIndex == null || ownerIndex instanceof Long);

    this.addRowWithId(obj, stripOn ? "stripeon" : "stripeoff", null, ownerIndex != null && ownerIndex instanceof Long ? (Long) ownerIndex : null, false, true);

    for (Iterator iter = settings.iterator(); iter.hasNext();)
    {
    setting = (XMLSetting) iter.next();

    XMLUtil.vassert(obj instanceof Persistent);

    //objValue = ((Persistent) obj).getPropertyValue(setting.getName());
    objValue = ((Persistent) obj).getPropertyObject(setting.getName());

    this.addSettingCell(setting, objValue);
    }

    this.endRow(true);
    }

    private void addSettingCell(XMLSetting setting, Object obj)
    {
    this.addTableObject("td", (obj instanceof Date) ? this.truncateDate((Date) obj) : null, (setting.needShow() ? null : "disable"), null, false);
    //this.addCell(null, (setting.needShow() ? null : "disable"), null, false);

    String value = this.getStringValue(obj);

    if (obj != null && !(value == null || value.equals("")))
    {
    String printValue = (obj instanceof Date) ? Util.truncateDate((Date) obj) : this.getStringPrintValue(value);

    this.writer.addString(printValue, false);
    }

    this.endTableObject(false);
    }

    private void addTittles(List settingsList)
    {
    int i = 0;

    this.addRow(null, null, null, null, false);

    for (Iterator iter = settingsList.iterator(); iter.hasNext(); i++)
    this.addTittleCell((XMLSetting) iter.next(), "sortColumn(" + i + ")");

    this.endRow(false);
    }

    protected void addTittleCell(XMLSetting setting, String onclick)
    {
    this.writer().startElement("td");
    if (setting.getName() != null) this.writer().addAtribute("id", setting.getName());
    this.writer().addAtribute("class", (setting.needShow() ? "normal" : "disable"));
    this.writer().finishStart(false);

    this.writer().startElement("a");
    this.writer().addAtribute("class", "normal");
    if (onclick != null && setting.needSort() == true) this.writer().addAtribute("href", "javascript:" + onclick);
    this.writer().finishStart(false);
    this.writer().addString(setting.getShowName(), false);
    this.writer().endElement(false);

    this.writer().endElement(false);
    }

    private void addMenu(String hideName, String subTitle)
    {
    String objName;

    this.addTable(hideName + " " + subTitle +" header menu", null, false);
    this.addRow(null, null, null, null, false);

    for (int i = 0; i < this.menu.length; i++ ) this.addMenuCell(hideName, this.menu[i]);

    this.addMenuCell(hideName, "Setting");
    this.addMenuCell(hideName, "Search");

    if (hideName == "Search") this.addMenuCell(hideName, "Clear filter");

    this.endRow(false);
    this.endTable(true);
    }

    private void addMenuCell(String hideName, String objectName)
    {
    String onclick =
    "javascript:" +
    (objectName == "Clear filter" ? "clearFilters()" : "showObjectList(\"" + objectName + "\")");

    this.addCell(null, null, null, false);
    this.addMenuButton(
    onclick,
    objectName + (objectName.equals("Search") ? "" : "s"),
    objectName.equals(hideName));
    this.endCell(false);
    }

    private void addMenuButton(String onClick, String value, boolean hide)
    {
    this.writer.startElement("input");
    this.writer.addAtribute("type", "button");
    this.writer.addAtribute("onclick", onClick);
    this.writer.addAtribute("value", value);

    if (hide) this.writer.addAtribute("disabled", true);

    this.writer.endElement(false);
    }

    private void addDateFilter(XMLSetting setting)
    {
    List yearList = (List) setting.getFilters();

    this.addCell(
    "date " + setting.getName(),
    "" + ((Long) this.objectMap.get(setting)).longValue(),
    null,
    false);

    this.addDateSelect(yearList, (String) yearList.get(0), "from ");
    this.addDateSelect(yearList, (String) yearList.get(yearList.size() - 1), " to ");

    this.endCell(false);
    }

    private void addDateSelect(List yearList, String currYear, String text)
    {
    String year;

    this.writer.startElement("a");
    this.writer.finishStart(false);
    this.writer.addString(text, false);
    this.writer.startElement("select");
    this.writer.addAtribute("id", "select" + (this.index - 1));
    this.writer.finishStart(false);

    for (Iterator iter = yearList.iterator(); iter.hasNext();)
    {
    year = (String) iter.next();
    this.writer.startElement("option");
    if (currYear.equals(year)) this.writer.addAtribute("selected", "selected");
    this.writer.finishStart(false);
    this.writer.addString(year, false);
    this.writer.endElement(false);
    }

    this.writer.endElement(false);
    this.writer.endElement(false);
    }

    private void addSelectFilter(XMLSetting setting)
    {
    if (!this.isRangeFilter(setting))
    {
    this.addCell(
    "select " + setting.getName(),
    "" + ((Long) this.objectMap.get(setting)).longValue(),
    null,
    false);

    List filterList = setting.getFilters();
    String option;

    this.writer.startElement("select");
    this.writer.addAtribute("name", setting.getName());
    this.writer.addAtribute("multiple size", filterList.size());

    for (Iterator iter = filterList.iterator(); iter.hasNext();)
    {
    option = (String) iter.next();
    this.writer.startElement("option");
    this.writer.addAtribute("value", this.index++);
    this.writer.finishStart(false);
    this.writer.addString(option, false);
    this.writer.endElement(false);
    }

    this.writer.endElement(false);

    this.endCell(false);
    }
    }

    private boolean isRangeFilter(XMLSetting setting)
    {
    return setting.getType().equals(XMLUtil.DATE_TYPE);
    }


    private List getMetaDataList(String objName)
    {
    try
    {
    MetadataManager mdm = this.sess.getMetadataManager();

    return (objName.equals(XMLUtil.PERSON)) ?
    mdm.createOrderMetaData(mdm.getPersonMetaData()) :
    (objName.equals(XMLUtil.SAMPLE)) ?
    mdm.createOrderMetaData(mdm.getSampleMetaData()) :
    (objName.equals(XMLUtil.ALIQUOT)) ?
    mdm.createOrderMetaData(mdm.getAliquotMetaData()) :
    null;
    }
    catch (Exception ex)
    {
    return null;
    }
    }

    private List getObjectList(String objName)
    {
    try
    {
    //            List dataList = this.sess.getAllPersons( 0);

    return (objName.equals(XMLUtil.PERSON)) ? this.sess.getAllPersons( -1) :
    (objName.equals(XMLUtil.SAMPLE)) ? this.sess.getAllSamples( null, -1) :
    (objName.equals(XMLUtil.ALIQUOT)) ? this.sess.getAllAliquots( null, null, -1) :
    null;
    }
    catch (Exception ex)
    {
    return null;
    }
    }

    //==========================================================================
    // section: table object related
    //==========================================================================

    private void addSection(String idName, String className, boolean newLine)
    {
    this.addTableObject("div", idName, className, null, newLine);
    }

    private void endSection(boolean newLine)
    {
    this.endTableObject(newLine);
    }

    private void addField(String idName, String className, boolean newLine)
    {
    this.addTableObject("fieldset", idName, className, null, newLine);
    }

    private void endField(boolean newLine)
    {
    this.endTableObject(newLine);
    }

    private void addTable(String idName, String className, boolean newLine)
    {
    addTableObject("table", idName, className, null, newLine);
    }

    private void endTable(boolean newLine)
    {
    this.endTableObject(newLine);
    }

    private void addRow(Object obj, String className, String typeName, Long ownerIndex, boolean newLine)
    {
    this.addRowWithId(obj, className, typeName, ownerIndex, newLine, false);
    }

    private void addRowWithId(Object obj, String className, String typeName, Long ownerIndex, boolean newLine, boolean withId)
    {
    this.writer.startElement("tr");

    if (withId && obj != null)
    {
    this.objectMap.put(obj, new Long(this.index));
    this.writer.addAtribute("id", "" + this.index);
    this.index++;
    }

    if (className != null) this.writer.addAtribute("class", className);
    if (ownerIndex != null) this.writer.addAtribute("title", "" + ownerIndex);
    else if (typeName != null) this.writer.addAtribute("title", typeName);

    this.writer.finishStart(newLine);
    }

    private void endRow(boolean newLine)
    {
    this.endTableObject(newLine);
    }

    private void addCell(String idName, String className, String text, boolean newLine)
    {
    this.addTableObject("td", idName, className, null, newLine);
    if (text != null) this.writer.addString(text, false);
    }

    private void endCell(boolean newLine)
    {
    this.endTableObject(newLine);
    }

    private void addTableObject(String tagName, Object idName, String className, String name, boolean newLine)
    {
    this.writer.startElement(tagName);

    if (idName != null)
    if (idName instanceof String)this.writer.addAtribute("id", (String) idName);
    else if (idName instanceof Long)this.writer.addAtribute("id", ((Long) idName).intValue());

    if (className != null) this.writer.addAtribute("class", className);
    if (name != null) this.writer.addAtribute("name", (String) name);

    this.writer.finishStart(newLine);
    }

    private void endTableObject(boolean newLine)
    {
    this.writer.endElement(false);
    if (newLine) this.writer.newLine();
    }

    //==========================================================================
    // section: other staff
    //==========================================================================

    public List getResult()
    {
    this.writer.close();
    return this.writer.getResult();
    }

    private boolean validObject(Object obj)
    {
    return (obj != null && (obj instanceof Aliquot || obj instanceof Sample || obj instanceof Person));
    }

    private boolean validObjectName(String objName)
    {
    return (objName.equals("Aliquot") || objName.equals("Sample") || objName.equals("Person"));
    }

    private String getStringPrintValue(String value)
    {
    if (value == null) return null;
    if (value.length() > 23) return value.substring(0, 20) + "...";

    return value;
    }

    private String getDate(Date date)
    {
    return (this.truncateDate(date));
    }

    static private String getStringValue(Object obj)
    {
    if (obj == null) return null;
    else if (obj instanceof String) return (String) obj;
    else if (obj instanceof Persistent) return ((Persistent) obj).getVisibleName();
    else if (obj instanceof Double) return "" + ((Double) obj).doubleValue();
    else if (obj instanceof Long) return "" + ((Long) obj).longValue();
    else if (obj instanceof Partner) return ((Partner) obj).getVisibleName();
    else if (obj instanceof Date) return ("" + ((Date) obj).getTime());
    return null;
    }

    static private Object getOwner(Object obj)
    {
    if (obj instanceof Sample) return ((Sample) obj).getPerson();
    else if (obj instanceof Aliquot) return ((Aliquot) obj).getSample();
    else return null;
    }

    static private String truncateDate(Date date)
    {
    return (date == null ? null : (new SimpleDateFormat("yyyy-MM-dd")).format(date));
    //return (date == null ? null : (new SimpleDateFormat("yyyy-MMM-dd")).format(date));
    }

    protected XMLWriter writer()
    {
    return this.writer;
    }
     */
    //==========================================================================
    // section: private data
    //==========================================================================
    //private static String menu[] = {XMLUtil.PERSON, XMLUtil.SAMPLE, XMLUtil.ALIQUOT};
    private XMLWriter writer;
    private UserHttpSess sess = null;
    private Map objectMap;
    private Map settingMap;
    private Set settingSet;
    private ArrayList settingList;
    private long index = 0;

    private void jbInit() throws Exception {
    }
}
