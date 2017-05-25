package org.universAAL.support.cigraph.exe;

import org.universAAL.support.cigraph.parser.SurefireParser;

public class SurefireExecutor extends Executor {

	public SurefireExecutor() {
		super(new String[] { "Failed", "Skipped", "Passed" });
		parser = new SurefireParser();
		title = "Surefire";
		name = "surefire";
	}
}
