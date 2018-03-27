package com.utils;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class XmlUtils {

    public static Map<String, String> xmltoMap(String xml) {
        try {
            Map map = new HashMap();
            Document document = DocumentHelper.parseText(xml);
            Element nodeElement = document.getRootElement();
            List node = nodeElement.elements();
            for (Iterator it = node.iterator(); it.hasNext(); ) {
                Element elm = (Element) it.next();
                map.put(elm.getName(), elm.getText());
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String mapToXml(Map<String, String> map) {
        String xml = "<xml>";
        for (Map.Entry m : map.entrySet()) {
            if (m.getValue() != null && org.apache.commons.lang3.math.NumberUtils.isCreatable(m.getValue().toString())) {
                xml += "<" + m.getKey() + ">" + m.getValue() + "</" + m.getKey() + ">";

            } else
                xml += "<" + m.getKey() + "><![CDATA[" + m.getValue() + "]]></" + m.getKey() + ">";
        }
        xml += "</xml>";
        return xml;
    }

}
