package org.universAAL.support.cigraph.parser;

import java.io.File;

import org.universAAL.support.cigraph.mojos.CiGraph;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CoverageParser extends XMLParser {

	public CoverageParser() {
		fXmlFile = new File(new File(new File("target", "site"), "cobertura"), "coverage.xml");
	}

	@Override
	protected void parse(Document doc) {
		values = new int[1];

		int linesCovered = -1;
		int linesValid = -1;

		NodeList nList = doc.getElementsByTagName("coverage");
		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String attr = eElement.getAttribute("lines-covered");
				if (!"".equals(attr))
					linesCovered = Integer.parseInt(attr);
				attr = eElement.getAttribute("lines-valid");
				if (!"".equals(attr))
					linesValid = Integer.parseInt(attr);
				break;
			}
		}

		if (linesCovered == -1 || linesValid == -1) {
			CiGraph.log(" -- CoverageParser: could not find values");
			return;
		}

		if (linesValid == 0)
			values[0] = 0;
		else
			values[0] = 100 * linesCovered / linesValid;

		CiGraph.log(" -- results CoverageParser: " + values[0] + " linesCovered: " + linesCovered
				+ " linesValid: " + linesValid);
	}
}
