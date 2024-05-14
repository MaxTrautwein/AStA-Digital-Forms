package de.PSWTM.DigitalForms.Factory;

import de.PSWTM.DigitalForms.model.Attachment;
import de.PSWTM.DigitalForms.model.Form;
import de.PSWTM.DigitalForms.model.FormSection;

import java.util.ArrayList;
import java.util.List;

public class FormFactory {
    public static Form createForm(String titel, boolean template, String description, Form.CategoryEnum category, List<FormSection> sections, List<Attachment> attachments){
        Form newForm = new Form();
        newForm.setTitel(titel);
        newForm.template(template);
        newForm.setDescription(description);
        newForm.setCategory(category);
        newForm.setForm(sections);
        newForm.attachments(attachments);
        return newForm;
    }
    public static Form createForm(String titel, boolean template, String description, Form.CategoryEnum category){
        Form newForm = new Form();
        newForm.setTitel(titel);
        newForm.template(template);
        newForm.setDescription(description);
        newForm.setCategory(category);
        newForm.setForm(new ArrayList<>());
        newForm.attachments(new ArrayList<>());
        return newForm;
    }
}
