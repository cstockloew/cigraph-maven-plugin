package org.universAAL.support.cigraph.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.universAAL.support.cigraph.mojos.CiGraph;
import org.universAAL.support.cigraph.util.Data;
import org.universAAL.support.cigraph.util.ModuleTraverser;

public class SurefireParser extends Parser {

	@Override
	public void parse() {
		values = new int[3];
		ModuleTraverser mt = new ModuleTraverser();
		mt.findAllModules(Data.project);
		List<File> paths = mt.getPaths();
		for (File f : paths) {
			f = new File(new File(f, "target"), "surefire-reports");
			parseModule(f);
		}

		// values now contains:
		// 0: Tests run
		// 1: Failures + Errors
		// 2: Skipped
		// calculate the real values
		int tmp[] = values;
		values = new int[3];
		values[0] = tmp[1]; // Failures + Errors
		values[1] = tmp[2]; // Skipped
		values[2] = tmp[0] - tmp[1]; // success

		CiGraph.log(" -- results SurefireParser: " + values[0] + "  " + values[1] + "  " + values[2]);
	}

	private void parseModule(File path) {
		File files[] = path.listFiles();
		if (files == null)
			return;
		for (File f : files) {
			if (f.getName().startsWith("TEST-"))
				continue;
			parseFile(f);
		}
	}

	private void parseFile(File f) {
		final int[] idx = { 0, 1, 1, 2 };
		// System.out.println(f.getName());
		try {
			try (BufferedReader br = new BufferedReader(new FileReader(f))) {
				br.readLine();
				br.readLine();
				br.readLine();
				String line = br.readLine();
				String[] sa = line.split(":");
				for (int i = 1; i < 5; i++) {
					String[] sa2 = sa[i].split(",");
					String s = sa2[0].trim();
					// System.out.println(s);
					values[idx[i - 1]] += Integer.parseInt(s);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
