package de.PSWTM.DigitalForms.collection;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "form")
public class Form {
    @Id
    public String id;

    public boolean Template;

    public String Titel;

    public String Description;

    public Form(boolean template, String titel, String description) {
        Template = template;
        Titel = titel;
        Description = description;
    }

    public ECategory Category;

    public List<FormSection> form;

    public List<Attachment> Attachments;


}
