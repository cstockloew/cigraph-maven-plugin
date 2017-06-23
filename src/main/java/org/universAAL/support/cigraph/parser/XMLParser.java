package org.universAAL.support.cigraph.parser;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public abstract class XMLParser extends Parser {
	protected File fXmlFile;

	public boolean parse() {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setValidating(false);
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			// setting the EntityResolver to avoid having dtd's downloaded (as
			// is the case for coverage.xml)
			dBuilder.setEntityResolver(new EntityResolver() {
				@Override
				public InputSource resolveEntity(String arg0, String arg1) throws SAXException, IOException {
					return new InputSource(new StringReader(""));
				}
			});
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			parse(doc);
			return true;
		} catch (java.io.FileNotFoundException e) {
			// ignore, this happens for FindBugsParser for parent poms which
			// don't have a findbugs report
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	protected abstract void parse(Document doc);

	protected List<String> getAllAttributes(Document doc, String element, String attribute) {
		List<String> lst = new LinkedList<String>();

		NodeList nList = doc.getElementsByTagName(element);
		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String attr = eElement.getAttribute(attribute);
				if (!"".equals(attr))
					lst.add(attr);
			}
		}

		return lst;
	}
}
