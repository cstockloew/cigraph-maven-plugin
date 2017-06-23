package org.universAAL.support.cigraph.parser;

import java.io.File;
import java.util.List;

import org.universAAL.support.cigraph.mojos.CiGraph;
import org.universAAL.support.cigraph.util.Data;
import org.universAAL.support.cigraph.util.ModuleTraverser;
import org.w3c.dom.Document;

public class FindBugsParser extends XMLParser {

	@Override
	public boolean parse() {
		values = new int[3];
		ModuleTraverser mt = new ModuleTraverser();
		mt.findAllModules(Data.project);
		List<File> paths = mt.getPaths();
		boolean parsed = false;
		for (File f : paths) {
			fXmlFile = new File(new File(f, "target"), "findbugsXml.xml");
			if (super.parse())
				parsed = true;
		}
		CiGraph.log(" -- results FindBugsParser: " + values[0] + "  " + values[1] + "  " + values[2]);
		return parsed;
	}

	@Override
	protected void parse(Document doc) {
		List<String> lst = getAllAttributes(doc, "BugInstance", "rank");
		for (String rank : lst) {
			int i = Integer.parseInt(rank);
			if (i < 1) {
				CiGraph.log(" -- FindBugsParser: unknown rank: " + i + " in file " + fXmlFile);
			} else if (i < 10) {
				// scariest + scary
				values[0]++;
			} else if (i < 15) {
				// troubling
				values[1]++;
			} else if (i < 21) {
				// of concern
				values[2]++;
			} else {
				CiGraph.log(" -- FindBugsParser: unknown rank: " + i + " in file " + fXmlFile);
			}
		}
	}
}
