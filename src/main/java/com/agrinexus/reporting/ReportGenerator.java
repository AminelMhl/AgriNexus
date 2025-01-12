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

import com.agrinexus.ui.GUI;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ReportGenerator {
 
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
                chartTitle,         // Chart title
                "Category",         // Domain axis label
                "Value",            // Range axis label
                dataset             // Data
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
    public static void exportPDF(String reportTitle, List<String> reportContent, List<String> categories, List<double[]> values) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Reports/"+reportTitle+".pdf"));
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
        } catch (DocumentException | IOException e) {
            System.err.println("Error generating PDF: " + e.getMessage());
        } finally {
            document.close();
        }
    }

    public static void generateReport(String reportTitle, List<String> categories, List<double[]> values, GUI gui) {
        // Prepare report content
        List<String> reportContent = new ArrayList<>();
        reportContent.add("Model Performance Report");
        reportContent.add("=========================");
        reportContent.add("Model: Linear Regression");
        reportContent.add("Predictions: ");

        for (int i = 0; i < values.size(); i++) {
            reportContent.add("Input: " + categories.get(i) + " => Prediction: " + values.get(i));
        }

        // Prepare chart
        ChartPanel chartPanel = generateChart(reportTitle, categories, values);

        // Display report and chart on GUI
        gui.displayReport(reportContent, chartPanel);
    }


}

