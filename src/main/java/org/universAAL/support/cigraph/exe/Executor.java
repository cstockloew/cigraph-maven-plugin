package org.universAAL.support.cigraph.exe;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.universAAL.support.cigraph.mojos.CiGraph;
import org.universAAL.support.cigraph.parser.Parser;
import org.universAAL.support.cigraph.util.CategoryGroup;
import org.universAAL.support.cigraph.util.Chart;
import org.universAAL.support.cigraph.util.Data;

public class Executor {

	protected CategoryGroup g;
	protected Parser parser;
	protected String title;
	protected String name;
	protected DefaultCategoryDataset dataset;
	protected JFreeChart chart;
	protected String lblDomain = null;// "Category";
	protected String lblRange = "Count";// "Value";
	protected boolean legend = true;
	private String baseURL = "http://depot.universaal.org/ci/";
	int colMod = 0;

	protected Executor(String[] cg) {
		g = new CategoryGroup(cg);
	}

	public void execute() throws IOException {
		parser.parse();
		uploadNewValues(parser.getValues());
		String values = downloadValues();
		createDataset(values);
		createChart();
		uploadChart();
	}

	private void uploadChart() throws IOException {
		// Just generate some unique random value.
		String boundary = Long.toHexString(System.currentTimeMillis());
		String CRLF = "\r\n"; // Line separator required by multipart/form-data.
		String charset = "UTF-8";
		// URL url = new URL(baseURL + "upload_chart.php");
		URL url = new URL(
				baseURL + "upload_chart.php?token=" + Data.token + "&pom=" + Data.getArtifactID() + "&name=" + name);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

		OutputStream output = conn.getOutputStream();
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
		// Send binary file.
		writer.append("--" + boundary).append(CRLF);
		writer.append("Content-Disposition: form-data; name=\"binaryFile\"; filename=\"" + Data.getArtifactID() + "_"
				+ name + ".png\"").append(CRLF);
		writer.append("Content-Type: " + URLConnection.guessContentTypeFromName("test.png")).append(CRLF);
		writer.append("Content-Transfer-Encoding: binary").append(CRLF);
		writer.append(CRLF).flush();
		ChartUtilities.writeChartAsPNG(output, chart, 330, 250);
		// Files.copy(binaryFile.toPath(), output);
		output.flush(); // Important before continuing with writer!
		writer.append(CRLF).flush(); // CRLF is important! It indicates end
										// of boundary.

		// End of multipart/form-data.
		writer.append("--" + boundary + "--").append(CRLF).flush();

		// we have to read the input, otherwise the files don't make it to the
		// server
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		// System.out.println(" -- Response from upload_chart:");
		while ((inputLine = in.readLine()) != null) {
			// System.out.println(inputLine);
		}
		in.close();

		output.close();
	}

	private void createDataset(String serialized) {
		// System.out.println(" - createDataset from:\n" + serialized);
		dataset = new DefaultCategoryDataset();
		serialized.replace("\r\n", "\n");
		String[] lines = serialized.split("\n");
		int linenum = 1;
		for (String l : lines) {
			String[] values = l.split("_");
			for (int i = 0; i < values.length; i++) {
				Integer val = Integer.parseInt(values[i]);
				dataset.addValue(val, g.getCategory(i), "#" + linenum);
				// System.out.println("add: " + val);
			}
			linenum++;
		}
		// System.out.println("- found " + (linenum - 1) + " lines");
	}

	private String downloadValues() throws IOException {
		URL url = new URL(baseURL + "data_" + Data.getArtifactID() + "_" + name + ".txt");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
			response.append("\n");
		}
		in.close();
		return response.toString();
	}

	private void uploadNewValues(int[] val) throws IOException {
		if (val == null || val.length == 0)
			throw new RuntimeException("no values available");
		String s = "";
		for (int i = 0; i < val.length; i++) {
			s += val[i];
			if (i < val.length - 1)
				s += "_";
		}
		// System.out.println("." + s + ".");
		URL url = new URL(baseURL + "upload_data.php?token=" + Data.token + "&pom=" + Data.getArtifactID() + "&name="
				+ name + "&data=" + s);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.connect();
		int responseCode = conn.getResponseCode();
		// System.out.println("responseCode: " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		conn.disconnect();
		if (responseCode != 200)
			throw new RuntimeException(
					"Invalid response code while uploading new values: " + responseCode + "\n" + response.toString());
	}

	private void createChart() throws IOException {
		chart = Chart.createChart(title, dataset, lblDomain, lblRange, legend, colMod);
		// CiGraph.log("writing chart as file");
		// ChartUtilities.saveChartAsPNG(new File("output.png"), chart, 400,
		// 300);
	}
}
