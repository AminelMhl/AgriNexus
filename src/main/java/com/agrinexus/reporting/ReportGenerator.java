package com.agrinexus.reporting;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

import com.agrinexus.ml.ML_Model;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ReportGenerator {
    private String arrayToString(double[] array) {
        StringBuilder sb = new StringBuilder();
        for (double value : array) {
            sb.append(value).append(" ");
        }
        return sb.toString().trim();
    }

    public static ChartPanel generateChart(String chartTitle, List<String> categories, List<double[]> values) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Populate dataset with values
        for (int seriesIndex = 0; seriesIndex < values.size(); seriesIndex++) {
            double[] seriesValues = values.get(seriesIndex);
            for (int i = 0; i < seriesValues.length; i++) {
                dataset.addValue(seriesValues[i], "Series " + (seriesIndex + 1), categories.get(i));
            }
        }

        // Create the chart
        JFreeChart chart = ChartFactory.createBarChart(
                chartTitle, // Chart title
                "Category", // Domain axis label
                "Value", // Range axis label
                dataset // Data
        );

        // Customize chart title
        chart.getTitle().setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18)); // Change title font
        chart.getTitle().setPaint(java.awt.Color.BLUE); // Change title color

        // Customize axis labels
        chart.getCategoryPlot().getDomainAxis().setLabelFont(new java.awt.Font("Verdana", java.awt.Font.PLAIN, 14));
        chart.getCategoryPlot().getRangeAxis().setLabelFont(new java.awt.Font("Verdana", java.awt.Font.PLAIN, 14));

        // Customize bar color and renderer
        BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
        renderer.setSeriesPaint(0, Color.decode("#0066CC"));
        renderer.setBarPainter(new StandardBarPainter()); // Use a standard painter (no gradient)
        renderer.setShadowVisible(false);
        renderer.setMaximumBarWidth(0.07);

        chart.getCategoryPlot().getDomainAxis().setCategoryMargin(0.05);

        // Save chart as JPEG
        try {
            File outputFile = new File("Charts/" + chartTitle + " chart output.jpg");
            ChartUtils.saveChartAsJPEG(outputFile, chart, 800, 600);
            System.out.println("Chart saved as: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error saving chart as JPEG: " + e.getMessage());
        }

        // Create ChartPanel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        return chartPanel;
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public static String exportPDF(String reportTitle, List<String> reportContent, List<String> categories,
            List<double[]> values) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Reports/" + reportTitle + ".pdf"));
            document.open();

            document.add(new Paragraph("Report: " + reportTitle));

            for (String content : reportContent) {
                document.add(new Paragraph(content));
            }

            ChartPanel chartPanel = generateChart(reportTitle, categories, values);
            JFreeChart chart = chartPanel.getChart();

            // Convert the chart to a BufferedImage
            BufferedImage bufferedImage = chart.createBufferedImage(800, 600);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            javax.imageio.ImageIO.write(bufferedImage, "png", baos);
            baos.flush();
            byte[] chartBytes = baos.toByteArray();
            baos.close();

            Image chartImageToAdd = Image.getInstance(chartBytes);
            chartImageToAdd.scaleToFit(500, 400);
            document.add(chartImageToAdd);

            System.out.println("PDF Report generated successfully!");
            return "Report generated successfully! \nPDF saved at Reports folder";
        } catch (DocumentException | IOException e) {
            System.err.println("Error generating PDF: " + e.getMessage());
            return "Error generating PDF: " + e.getMessage();
        } finally {
            document.close();
        }
    }

    public String generateReport(String reportTitle, double[][] trainingData, double[] regressionTargets,
            ML_Model model) {
        double[] predictions = new double[trainingData.length];

        // Make predictions for each training data point
        for (int i = 0; i < trainingData.length; i++) {
            predictions[i] = model.predict(trainingData[i]);
            System.out.println("Predicted value for input " + arrayToString(trainingData[i]) + ": " + predictions[i]);
        }

        // Prepare report content
        List<String> reportContent = new ArrayList<>();
        reportContent.add("Model Performance Report");
        reportContent.add("=========================");
        reportContent.add("Model: " + model.getClass().getSimpleName());
        reportContent.add(
                "The provided data from the farmer can be analyzed using a linear regression model. In this case, the model can be used to interpret how the yield varies over the years, allowing us to identify trends and predict future yields based on historical data. This relationship highlights the influence of time (year) on agricultural productivity (yield). \n \nThe given chart explains the results \n");

        // Prepare categories and values for the chart
        List<String> categories = new ArrayList<>();
        List<double[]> values = new ArrayList<>();

        for (int i = 0; i < predictions.length; i++) {
            categories.add(arrayToString(trainingData[i]));
            values.add(new double[] { predictions[i] }); // Wrap the prediction in an array
        }

        // Export the report to PDF
        String message = exportPDF(reportTitle, reportContent, categories, values);
        return message;    
    }

}
