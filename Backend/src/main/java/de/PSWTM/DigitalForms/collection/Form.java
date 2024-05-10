package de.PSWTM.DigitalForms.collection;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "form")
public class Form {
    @Id
    public String id;

    public boolean template;

    public String titel;

    public String description;

    public ECategory category;

    public List<FormSection> form;

    public List<Attachment> attachments;


}
