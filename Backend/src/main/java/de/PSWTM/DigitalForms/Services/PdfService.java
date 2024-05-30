package de.PSWTM.DigitalForms.Services;

import de.PSWTM.DigitalForms.controller.FormsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class PdfService {

    private final TemplateEngine templateEngine;

    Logger logger = LoggerFactory.getLogger(FormsController.class);

    public PdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] generatePdf(String title, String content) throws IOException {
        Context context = new Context();
        context.setVariable("title", title);
        context.setVariable("content", content);

        String htmlContent = templateEngine.process("AntragAufGenemigenTemplate", context);

        try {
            // Paths to resources and output files
            String htmlFilePath = "./test.html";
            String imageFilePath = "./AstaRgb.png";
            String pdfFilePath = "./out.pdf";


            // Save HTML content to file
            saveHtmlFile(htmlContent, htmlFilePath);

            // Copy the AStA Logo from resources to the destination folder
            try{
                copyImageFile("/templates/AstaRgb.png", imageFilePath);
            }catch (FileAlreadyExistsException e){
                logger.error("File already exists");
            }


            // Generate PDF from HTML using Chromium
            generatePdfFromHtml(htmlFilePath, pdfFilePath);

            // Retrieve PDF as byte stream
            return readPdfFile(pdfFilePath);

        } catch (IOException | InterruptedException e) {
            logger.error("Error while generating PDF", e);
            return null;
        }
    }

    private static void saveHtmlFile(String content, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }

    private static void copyImageFile(String resourcePath, String destinationPath) throws IOException {
        try (InputStream is = PdfService.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new FileNotFoundException("Resource not found: " + resourcePath);
            }
            Files.copy(is, Paths.get(destinationPath));
        }
    }

    private static void generatePdfFromHtml(String htmlFilePath, String pdfFilePath) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(
                "chromium",
                "--headless",
                "--disable-gpu",
                "--no-margins",
                "--no-sandbox",
                "--no-pdf-header-footer",
                "--print-to-pdf=" + pdfFilePath,
                htmlFilePath
        );
        Process process = pb.start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Chromium command failed with exit code: " + exitCode);
        }
    }

    private static byte[] readPdfFile(String filePath) throws IOException {
        return Files.readAllBytes(Paths.get(filePath));
    }


}
