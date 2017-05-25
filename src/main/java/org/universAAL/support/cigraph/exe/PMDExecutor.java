package org.universAAL.support.cigraph.exe;

import org.universAAL.support.cigraph.parser.PMDParser;

public class PMDExecutor extends Executor {

	public PMDExecutor() {
		super(new String[] { "High", "Medium", "Low" });
		parser = new PMDParser();
		title = "PMD";
		name = "pmd";
	}
}
