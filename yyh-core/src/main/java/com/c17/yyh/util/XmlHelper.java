package com.c17.yyh.util;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlHelper {
	 public static String getTagValue(String sTag, Element eElement) {
		 NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		 Node nValue = (Node) nlList.item(0);
		 return nValue.getNodeValue();
		 }
}
