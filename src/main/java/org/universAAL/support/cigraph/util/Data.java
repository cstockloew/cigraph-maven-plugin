package org.universAAL.support.cigraph.util;

import org.apache.maven.project.MavenProject;

public class Data {

	public static String token;
	
	public static MavenProject project;
	
	public static String getArtifactID() {
		return project.getArtifactId();
	}
}
