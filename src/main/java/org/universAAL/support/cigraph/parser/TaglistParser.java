package org.universAAL.support.cigraph.parser;

import java.io.File;
import java.util.List;

import org.universAAL.support.cigraph.mojos.CiGraph;
import org.w3c.dom.Document;

public class TaglistParser extends XMLParser {

	public TaglistParser() {
		fXmlFile = new File(new File("target", "taglist"), "taglist.xml");
	}

	@Override
	protected void parse(Document doc) {
		values = new int[1];

		List<String> lst = getAllAttributes(doc, "tag", "count");
		for (String count : lst) {
			values[0] = Integer.parseInt(count);
		}

		CiGraph.log(" -- results TaglistParser: " + values[0]);
	}
}
