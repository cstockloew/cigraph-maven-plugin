package org.universAAL.support.cigraph.mojos;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.universAAL.support.cigraph.exe.CPDExecutor;
import org.universAAL.support.cigraph.exe.CheckstyleExecutor;
import org.universAAL.support.cigraph.exe.CoverageExecutor;
import org.universAAL.support.cigraph.exe.Executor;
import org.universAAL.support.cigraph.exe.FindBugsExecutor;
import org.universAAL.support.cigraph.exe.PMDExecutor;
import org.universAAL.support.cigraph.exe.SurefireExecutor;
import org.universAAL.support.cigraph.exe.TaglistExecutor;
import org.universAAL.support.cigraph.util.Data;

/**
 * 
 * @author Carsten Stockloew
 * 
 * @goal cigraph
 *
 */
public class CiGraph extends AbstractMojo {

	private static Log log;

	/**
	 * The maven project.
	 * 
	 * @parameter default-value="${project}"
	 * @required
	 * @readonly
	 */
	private MavenProject mavenProject;

	/**
	 * Security token.
	 * 
	 * @parameter property=token
	 * @required
	 * @readonly
	 */
	private String token;

	/** {@inheritDoc} */
	public void execute() throws MojoExecutionException, MojoFailureException {
		Data.project = mavenProject;
		Data.token = token;
		log = getLog();

		List<Executor> lst = new ArrayList<Executor>();
		lst.add(new CheckstyleExecutor());
		lst.add(new CoverageExecutor());
		lst.add(new CPDExecutor());
		lst.add(new FindBugsExecutor());
		lst.add(new PMDExecutor());
		lst.add(new SurefireExecutor());
		lst.add(new TaglistExecutor());
		for (Executor exe : lst) {
			try {
				exe.execute();
			} catch (Exception e) {
				e.printStackTrace();
				log("Exception for " + exe.getClass().getName() + ": ");
			}
		}
	}

	public static void log(String content) {
		if (log != null)
			log.info(content);
		else
			System.out.println(content);
	}
}
