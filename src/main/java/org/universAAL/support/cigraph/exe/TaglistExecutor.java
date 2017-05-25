package org.universAAL.support.cigraph.exe;

import org.universAAL.support.cigraph.parser.TaglistParser;

public class TaglistExecutor extends Executor {

	public TaglistExecutor() {
		super(new String[] { "Tags" });
		parser = new TaglistParser();
		title = "Tag list";
		name = "taglist";
		lblRange = "Tags";
		legend = false;
		colMod = 1;
	}
}
