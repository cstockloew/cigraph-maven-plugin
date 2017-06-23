package org.universAAL.support.cigraph.parser;

import java.io.File;
import java.util.List;

import org.universAAL.support.cigraph.mojos.CiGraph;
import org.w3c.dom.Document;

public class CPDParser extends XMLParser {

	public CPDParser() {
		fXmlFile = new File("target", "cpd.xml");
	}

	@Override
	protected void parse(Document doc) {
		values = new int[1];

		List<String> lst = getAllAttributes(doc, "duplication", "lines");
		for (String lines : lst) {
			values[0] += Integer.parseInt(lines);
		}

		CiGraph.log(" -- results CPDParser: " + values[0]);
	}
}
