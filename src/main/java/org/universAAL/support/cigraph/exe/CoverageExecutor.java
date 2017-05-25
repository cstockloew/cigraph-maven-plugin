package org.universAAL.support.cigraph.exe;

import org.universAAL.support.cigraph.parser.CoverageParser;

public class CoverageExecutor extends Executor {

	public CoverageExecutor() {
		super(new String[] { "Percent" });
		parser = new CoverageParser();
		title = "Coverage";
		name = "coverage";
		lblRange = "Percent";
		legend = false;
		colMod = 2;
	}
}
