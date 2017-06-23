package org.universAAL.support.cigraph.parser;

import java.io.File;
import java.util.List;

import org.universAAL.support.cigraph.mojos.CiGraph;
import org.w3c.dom.Document;

public class PMDParser extends XMLParser {

	public PMDParser() {
		fXmlFile = new File("target", "pmd.xml");
	}

	@Override
	protected void parse(Document doc) {
		values = new int[3];

		List<String> lst = getAllAttributes(doc, "violation", "priority");
		for (String priority : lst) {
			int i = Integer.parseInt(priority);
			if (i < 1) {
				CiGraph.log(" -- PMDParser: unknown priority: " + i);
			} else if (i < 3) {
				values[0]++;
			} else if (i < 5) {
				values[1]++;
			} else if (i < 6) {
				values[2]++;
			} else {
				CiGraph.log(" -- PMDParser: unknown priority: " + i);
			}
		}

		CiGraph.log(" -- results PMDParser: " + values[0] + "  " + values[1] + "  " + values[2]);
	}
}
