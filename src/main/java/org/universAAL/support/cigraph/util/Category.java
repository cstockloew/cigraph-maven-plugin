package org.universAAL.support.cigraph.util;

public class Category implements Comparable<Category> {

	CategoryGroup group;
	String name;

	public Category(CategoryGroup group, String name) {
		this.group = group;
		this.name = name;
	}

	public int compareTo(Category o) {
		return group.compare(this, o);
	}

	@Override
	public String toString() {
		return name;
	}
}
