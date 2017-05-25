package org.universAAL.support.unit.tests;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.universAAL.support.cigraph.exe.CPDExecutor;
import org.universAAL.support.cigraph.exe.CheckstyleExecutor;
import org.universAAL.support.cigraph.exe.CoverageExecutor;
import org.universAAL.support.cigraph.exe.Executor;
import org.universAAL.support.cigraph.exe.FindBugsExecutor;
import org.universAAL.support.cigraph.exe.PMDExecutor;
import org.universAAL.support.cigraph.exe.SurefireExecutor;
import org.universAAL.support.cigraph.exe.TaglistExecutor;
import org.universAAL.support.cigraph.parser.CPDParser;
import org.universAAL.support.cigraph.parser.CheckstyleParser;
import org.universAAL.support.cigraph.parser.CoverageParser;
import org.universAAL.support.cigraph.parser.FindBugsParser;
import org.universAAL.support.cigraph.parser.PMDParser;
import org.universAAL.support.cigraph.parser.SurefireParser;
import org.universAAL.support.cigraph.parser.TaglistParser;
import org.universAAL.support.cigraph.util.CategoryGroup;
import org.universAAL.support.cigraph.util.Data;
import org.universAAL.support.cigraph.util.ModuleTraverser;

import junit.framework.TestCase;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

/**
 *
 */
public class MainCheckTest extends TestCase {

	public void testCommentedMain() throws FileNotFoundException, IOException, XmlPullParserException {
/*
		if (true)
			return;

		System.setProperty("user.dir", "D:\\dev\\uaal\\GIT\\platform\\context\\ctxt.pom");

		MavenProject p = new MavenProject();
		// File f = new
		// File("D:\\dev\\uaal\\GIT\\platform\\middleware\\pom\\pom.xml");
		File f = new File("D:\\dev\\uaal\\GIT\\platform\\context\\ctxt.pom\\pom.xml");
		p.setFile(f);
		p.setArtifactId("ctxt.pom");
		// new ModuleTraverser().findAllModules(p);
		// p = new MavenProject(ModuleTraverser.readPOMFile(new
		// File("D:\\dev\\uaal\\GIT\\platform\\context\\ctxt.pom")));
		Data.project = p;
		Data.token = "kfghly";
		// System.out.println(p.getArtifactId());

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
				System.out.println("Exception: ");
				e.printStackTrace();
			}
		}

		if (true)
			return;

		new FindBugsParser().parse();
		new CPDParser().parse();
		new PMDParser().parse();
		new CoverageParser().parse();
		new SurefireParser().parse();
		new CheckstyleParser().parse();
		new TaglistParser().parse();

		if (true)
			return;

		System.setProperty("java.awt.headless", "true");
		
		CategoryGroup g = new CategoryGroup(new String[] { "Errors", "Warnings", "Info" });

		String fc = "3\t4\t2\n" + "2\t6\t2\n";
		BufferedReader bufReader = new BufferedReader(new StringReader(fc));

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		String line = null;
		int linenum = 1;
		try {
			while ((line = bufReader.readLine()) != null) {
				String[] values = line.split("\t");
				for (int i = 0; i < values.length; i++) {
					Integer val = Integer.parseInt(values[i]);
					dataset.addValue(val, g.getCategory(i), "#" + linenum);
					System.out.println("add: " + val);
				}
				linenum++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// result.addValue(2, rowKey, columnKey);

		JFreeChart chart = createChart("Checkstyle", dataset);

		try {
			ChartUtilities.saveChartAsPNG(new File("output.png"), chart, 400, 300);
			// writeChartAsPNG
		} catch (IOException e) {
			e.printStackTrace();
		}
*/	}

	public JFreeChart createChart(String title, CategoryDataset dataset) {

		JFreeChart chart = ChartFactory.createStackedAreaChart(title, // title
				"Category", // domain axis label
				"Value", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				false, false);

		chart.setBackgroundPaint(Color.white);

		final CategoryPlot plot = (CategoryPlot) chart.getPlot();
		// plot.setForegroundAlpha(0.5f);
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);

		final CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setLowerMargin(0.0);
		domainAxis.setUpperMargin(0.0);

		// change the auto tick unit selection to integer units only...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		final CategoryItemRenderer renderer = plot.getRenderer();
		renderer.setBaseItemLabelsVisible(true);
		renderer.setSeriesPaint(0, new Color(0xFF, 0x60, 0x60));
		renderer.setSeriesPaint(1, ChartColor.VERY_LIGHT_YELLOW);
		renderer.setSeriesPaint(2, ChartColor.VERY_LIGHT_BLUE);
		renderer.setSeriesPaint(3, new Color(0xA0, 0xA0, 0xFF));

		return chart;
	}
}
