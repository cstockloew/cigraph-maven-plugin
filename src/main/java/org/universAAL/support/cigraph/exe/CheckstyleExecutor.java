package org.universAAL.support.cigraph.exe;

import org.universAAL.support.cigraph.parser.CheckstyleParser;

public class CheckstyleExecutor extends Executor {

	public CheckstyleExecutor() {
		super(new String[] { "Errors", "Warnings", "Info" });
		parser = new CheckstyleParser();
		title = "Checkstyle";
		name = "checkstyle";
	}
}
