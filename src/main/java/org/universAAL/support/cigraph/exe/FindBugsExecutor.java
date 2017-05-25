package org.universAAL.support.cigraph.exe;

import org.universAAL.support.cigraph.parser.FindBugsParser;

public class FindBugsExecutor extends Executor {

	public FindBugsExecutor() {
		super(new String[] { "Scariest & Scary", "Troubling", "Of concern" });
		parser = new FindBugsParser();
		title = "FindBugs";
		name = "findbugs";
	}
}
