package de.PSWTM.DigitalForms.Model;

public class TemplatePDF {

    private String formId;

    private String templatePdf;

    public TemplatePDF(String formId, String templatePdf) {
        this.formId = formId;
        this.templatePdf = templatePdf;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getTemplatePdf() {
        return templatePdf;
    }

    public void setTemplatePdf(String templatePdf) {
        this.templatePdf = templatePdf;
    }
}
