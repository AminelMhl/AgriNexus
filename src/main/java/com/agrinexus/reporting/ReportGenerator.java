package com.agrinexus.reporting;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ReportGenerator {
 
    public static ChartPanel generateChart(String chartTitle, List<String> categories, List<Number> values) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < categories.size(); i++) {
            dataset.addValue(values.get(i), "Value", categories.get(i));
        }

        JFreeChart chart = ChartFactory.createBarChart(
                chartTitle,         
                "Category",           
                "Value",              
                dataset               
        );

        chart.getTitle().setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18)); // Change title font
        chart.getTitle().setPaint(java.awt.Color.BLUE); // Change title color
        chart.getCategoryPlot().getDomainAxis().setLabelFont(new java.awt.Font("Verdana", java.awt.Font.PLAIN, 14)); // X-axis label font
        chart.getCategoryPlot().getRangeAxis().setLabelFont(new java.awt.Font("Verdana", java.awt.Font.PLAIN, 14)); // Y-axis label font
        
        chart.getCategoryPlot().getRenderer().setSeriesPaint(0, Color.decode("#0066CC"));

        BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
        renderer.setBarPainter(new StandardBarPainter()); // Use a standard painter (no gradient)
        renderer.setShadowVisible(false);

        renderer.setMaximumBarWidth(0.07);
        chart.getCategoryPlot().getDomainAxis().setCategoryMargin(0.05);


        try {
            File outputFile = new File("Charts/"+ chartTitle+"chart output.jpg");
            ChartUtils.saveChartAsJPEG(outputFile, chart, 800, 600);
            System.out.println("Chart saved as: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error saving chart as JPEG: " + e.getMessage());
        }

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        return chartPanel;
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public static void exportPDF(String reportTitle, List<String> reportContent, List<String> categories, List<Number> values) {
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
}

