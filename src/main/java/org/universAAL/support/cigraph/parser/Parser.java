package org.universAAL.support.cigraph.parser;

public abstract class Parser {
	
	protected int[] values = null;

	public int[] getValues() {
		return values;
	}
	
	public abstract boolean parse();
}
