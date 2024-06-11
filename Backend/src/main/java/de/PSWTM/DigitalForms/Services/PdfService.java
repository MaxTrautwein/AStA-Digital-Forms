package de.PSWTM.DigitalForms.Services;

import de.PSWTM.DigitalForms.Attchments.AttachmentUtil;
import de.PSWTM.DigitalForms.controller.FormsController;
import de.PSWTM.DigitalForms.model.Form;
import de.PSWTM.DigitalForms.model.FormElement;
import de.PSWTM.DigitalForms.model.FormSection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PdfService {

    private final TemplateEngine templateEngine;

    Logger logger = LoggerFactory.getLogger(FormsController.class);

    public PdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    // Match up Form Values with Template Placeholders
    private Context LoadContextVariables(Form form) {
        Context context = new Context();
        for (FormSection fs : form.getForm()){
            for (FormElement fe: fs.getItems()){
                context.setVariable(fe.getId(),fe.getValue());
            }
        }
        return context;
    }




    synchronized public byte[] generatePdf(Form form, String Template) throws IOException {
        Context context = LoadContextVariables(form);

        AttachmentUtil attachmentUtil = new AttachmentUtil(form);
        if (!attachmentUtil.getAttachmentsReq().isEmpty() || !attachmentUtil.getAttachmentsUser().isEmpty()){
            // We Have at least one Attachment -> Append the Checklist
            Context attachmentContext = new Context();

            attachmentContext.setVariable("ReqAttachment",attachmentUtil.getAttachmentsReq());
            attachmentContext.setVariable("UserAttachment",attachmentUtil.getAttachmentsUser());
            //TODO: Link up attachment Context

            String Checklist = templateEngine.process("AttachmentsChecklist", attachmentContext);

            context.setVariable("Anhang",Checklist);
        }


        String htmlContent = templateEngine.process(Template, context);


        try {
            // Paths to resources and output files
            String htmlFilePath = "./test.html";
            String pdfFilePath = "./out.pdf";


            // Save HTML content to file
            saveHtmlFile(htmlContent, htmlFilePath);

            // Copy the AStA Logo & Styles from resources to the destination folder
            copyFile("/templates/AstaRgb.png", "./AstaRgb.png");
            copyFile("/templates/Styles.css", "./Styles.css");


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

    private static void copyFile(String resourcePath, String destinationPath) throws IOException {
        try (InputStream is = PdfService.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new FileNotFoundException("Resource not found: " + resourcePath);
            }
            final Path path = Paths.get(destinationPath);
            if (!Files.exists(path)){
                Files.copy(is, path);
            }

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
