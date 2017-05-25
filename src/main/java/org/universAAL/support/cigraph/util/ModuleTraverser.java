package org.universAAL.support.cigraph.util;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ModuleTraverser {
	
	private List<File> paths = null;

	public List<Model> findAllModules(MavenProject baseProject) {
		paths = new ArrayList<File>();
		List<Model> modelList = new ArrayList<Model>();
		recursiveFindModules(baseProject.getBasedir(), modelList);
		return modelList;
	}
	
	public List<File> getPaths() {
		return paths;
	}

	public static Model readPOMFile(File baseDir) throws FileNotFoundException, IOException, XmlPullParserException {
		MavenXpp3Reader reader = new MavenXpp3Reader();
		FileInputStream fis = new FileInputStream(new File(baseDir.getAbsolutePath(), "pom.xml"));
		Model model = reader.read(fis);
		fis.close();
		return model;
	}

	private void recursiveFindModules(File baseDir, List<Model> modelList) {
		Model model = null;
		try {
			model = readPOMFile(baseDir);
		} catch (IOException | XmlPullParserException e) {
			e.printStackTrace();
		}
		if (model == null)
			return;
		
		paths.add(baseDir);

		modelList.add(model);
		//System.out.println(model.getArtifactId());

		Set<String> modulePaths = new LinkedHashSet<>();
		modulePaths.addAll(model.getModules());

		for (String modulePath : modulePaths) {
			File moduleDir = new File(baseDir, modulePath);
			recursiveFindModules(moduleDir, modelList);
		}
	}
}
