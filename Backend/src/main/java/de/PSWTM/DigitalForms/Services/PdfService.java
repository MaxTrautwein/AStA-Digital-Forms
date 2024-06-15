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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class PdfService {

    private final TemplateEngine templateEngine;

    Logger logger = LoggerFactory.getLogger(FormsController.class);

    public PdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    private Boolean isTrue(String string){
        return string.equalsIgnoreCase("true");
    }

    // Match up Form Values with Template Placeholders
    private Context LoadContextVariables(Form form) {
        Context context = new Context();
        for (FormSection fs : form.getForm()){
            for (FormElement fe: fs.getItems()){
                switch (fe.getType()){

                    case BOOL -> {
                        context.setVariable(fe.getId() + "_YES",isTrue(fe.getValue()));
                        context.setVariable(fe.getId() + "_NO",!isTrue(fe.getValue()));
                    }
                    case DATE -> {
                        LocalDate date = LocalDate.parse(fe.getValue(),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                        context.setVariable(fe.getId(),date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                    }
                    case IBAN -> {
                        // 22 Chars
                        StringBuilder iban = new StringBuilder(fe.getValue().replaceAll(" ",""));
                        // Padd if needed
                        iban.append(" ".repeat(Math.max(0, 22 - iban.length())));


                        context.setVariable(fe.getId() + "_1",iban.substring(0, 4));
                        context.setVariable(fe.getId() + "_2",iban.substring(4, 8));
                        context.setVariable(fe.getId() + "_3",iban.substring(8, 12));
                        context.setVariable(fe.getId() + "_4",iban.substring(12, 16));
                        context.setVariable(fe.getId() + "_5",iban.substring(16, 20));
                        context.setVariable(fe.getId() + "_6",iban.substring(20, 22));

                    }

                    default -> context.setVariable(fe.getId(),fe.getValue());
                }
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
