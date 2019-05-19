package icapture.com.xmlWriter;

//~--- JDK imports ------------------------------------------------------------

/**
 * @author Peteris
 */
import java.util.List;

public class XMLSetting {
    private String  name;
    private List    objects;
    private boolean show;
    private String  showName;
    private boolean sort;
    private String  type;

    public XMLSetting() {}

    public XMLSetting(String name, String shortName, String type, boolean show, boolean sort, List filters) {
        XMLUtil.vassert((type != null) &&!type.equals(XMLUtil.OBJECT_UNDEF));
        this.name     = name;
        this.showName = shortName;
        this.type     = type;
        this.objects  = filters;
        this.show     = show;
        this.sort     = sort;
    }

    public boolean isFiltred() {
        return this.objects != null;
    }

    public String getName() {
        return this.name;
    }

    public String getShowName() {
        return this.showName;
    }

    public String getType() {
        return this.type;
    }

    public boolean needShow() {
        return this.show;
    }

    public boolean needSort() {
        return this.sort;
    }

    public List getFilters() {
        return this.objects;
    }

    public void addTittle(XMLWriter writer, String title) {
        writer.startElement("td");
        writer.addAtribute("class", "normal");
        writer.addString(title, false);
        writer.finishStart(false);
        writer.endElement(false);
    }

    public void addCell(XMLWriter writer) {
        writer.startElement("td");
        writer.addAtribute("class", this.getType());
        writer.finishStart(false);
        writer.startElement("input");
        writer.addAtribute("type", "checkbox");

        if (this.needShow()) {
            writer.addAtribute("checked");
        }

        writer.finishStart(false);
        writer.addString(this.getShowName(), false);
        writer.endElement();
    }
}



