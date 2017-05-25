package org.universAAL.support.cigraph.exe;

import org.universAAL.support.cigraph.parser.CPDParser;

public class CPDExecutor extends Executor {

	public CPDExecutor() {
		super(new String[] { "Lines" });
		parser = new CPDParser();
		title = "CPD";
		name = "cpd";
		lblRange = "Lines";
		legend = true;
		colMod = 1;
	}
}
