package org.universAAL.support.cigraph.util;

import java.awt.Color;
import java.util.ArrayList;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;

public class Chart {

	public static JFreeChart createChart(String title, CategoryDataset dataset, String lblDomain, String lblRange,
			boolean legend, int colMod) {
		JFreeChart chart = ChartFactory.createStackedAreaChart(title, // title
				lblDomain, // domain axis label
				lblRange, // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				legend, // include legend
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
		ArrayList<Color> lst = new ArrayList<Color>();
		lst.add(new Color(0xFF, 0x60, 0x60));
		lst.add(ChartColor.VERY_LIGHT_YELLOW);
		lst.add(ChartColor.VERY_LIGHT_BLUE);
		lst.add(new Color(0xA0, 0xA0, 0xFF));
		for (int i = 0; i < lst.size()-colMod; i++)
			renderer.setSeriesPaint(i, lst.get(i + colMod));

		return chart;
	}
}
