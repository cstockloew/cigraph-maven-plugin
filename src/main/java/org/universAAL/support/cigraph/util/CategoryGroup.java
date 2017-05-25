package org.universAAL.support.cigraph.util;

public class CategoryGroup {
	Category[] cats;

	public CategoryGroup(String[] cats) {
		this.cats = new Category[cats.length];
		for (int i = 0; i < cats.length; i++)
			this.cats[i] = new Category(this, cats[i]);
	}

	public Category getCategory(int idx) {
		return cats[idx];
	}

	private Integer getIndex(Category c) {
		for (int i = 0; i < cats.length; i++) {
			if (cats[i] == c)
				return i;
		}
		throw new IllegalArgumentException();
	}

	public int compare(Category c1, Category c2) {
		return getIndex(c1).compareTo(getIndex(c2));
	}
}
