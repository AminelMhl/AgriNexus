package com.agrinexus.reporting;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ReportGenerator {

    // Method to export a report to a PDF file
    public void exportPDF(String reportTitle, List<String> reportContent) {
        Document document = new Document();
        try {
            // Create a new PDF file
            PdfWriter.getInstance(document, new FileOutputStream("report.pdf"));
            document.open();

            // Add report title
            document.add(new Paragraph("Report: " + reportTitle));

            // Add report content
            for (String content : reportContent) {
                document.add(new Paragraph(content));
            }

            System.out.println("PDF Report generated successfully!");
        } catch (DocumentException | IOException e) {
            System.err.println("Error generating PDF: " + e.getMessage());
        } finally {
            document.close();
        }
    }

    // Method to generate a simple chart for the report
    public ChartPanel generateChart(String chartTitle, List<String> categories, List<Integer> values) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Populate dataset
        for (int i = 0; i < categories.size(); i++) {
            dataset.addValue(values.get(i), "Value", categories.get(i));
        }

        // Create a chart
        JFreeChart chart = ChartFactory.createBarChart(
                chartTitle,           // chart title
                "Category",           // domain axis label
                "Value",              // range axis label
                dataset               // dataset
        );

        // Display the chart in a ChartPanel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        return chartPanel;
    }
}

