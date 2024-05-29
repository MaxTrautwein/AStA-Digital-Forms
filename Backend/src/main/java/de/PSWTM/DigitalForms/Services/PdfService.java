package de.PSWTM.DigitalForms.Services;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import de.PSWTM.DigitalForms.controller.FormsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
import com.itextpdf.kernel.pdf.PdfOutputIntent;

import java.io.*;

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

        //Generate
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ConverterProperties converterProperties = new ConverterProperties();

            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            PageSize pageSize = PageSize.A4;
            pageSize.applyMargins(0,0,0,0,false);
            pdf.setDefaultPageSize(pageSize);




            HtmlConverter.convertToPdf(htmlContent, pdf , converterProperties);

            return outputStream.toByteArray();
        } catch (Exception e) {
            logger.error("Exception", e);
            return null;
        }
    }
}
