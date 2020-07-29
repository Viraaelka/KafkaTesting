package steps.rest.headers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapAdapter extends XmlAdapter<MapAdapter.AdaptedMap, Map<String, String>> {

    private DocumentBuilder documentBuilder;

    public MapAdapter() throws Exception {
        documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    public static class AdaptedMap {
        @XmlAnyElement
        public List<Element> elements = new ArrayList<Element>();
    }

    @Override
    public AdaptedMap marshal(Map<String, String> map) throws Exception {
        Document document = documentBuilder.newDocument();
        AdaptedMap adaptedMap = new AdaptedMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            Element element = document.createElement("header");
            element.setAttribute("name", entry.getKey());
            element.setAttribute("value", entry.getValue());
            adaptedMap.elements.add(element);
        }
        return adaptedMap;
    }

    @Override
    public Map<String, String> unmarshal(AdaptedMap adaptedMap) throws Exception {
        HashMap<String, String> map = new HashMap<String, String>();
        for (Element element : adaptedMap.elements) {
            map.put(element.getAttribute("name"), element.getAttribute("value"));
        }
        return map;
    }
}
