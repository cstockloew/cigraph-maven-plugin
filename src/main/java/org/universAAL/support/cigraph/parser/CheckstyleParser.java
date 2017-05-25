package org.universAAL.support.cigraph.parser;

import java.io.File;
import java.util.List;

import org.universAAL.support.cigraph.mojos.CiGraph;
import org.w3c.dom.Document;

public class CheckstyleParser extends XMLParser {

	public CheckstyleParser() {
		fXmlFile = new File("target", "checkstyle-result.xml");
	}

	protected void parse(Document doc) {
		values = new int[3];

		List<String> lst = getAllAttributes(doc, "error", "severity");
		for (String severity : lst) {
			if ("error".equals(severity))
				values[0]++;
			else if ("warning".equals(severity))
				values[1]++;
			else if ("info".equals(severity))
				values[2]++;
			else
				CiGraph.log("unknown severity: " + severity);
		}

		CiGraph.log(" -- results CheckstyleParser: " + values[0] + "  " + values[1] + "  " + values[2]);
	}
}
