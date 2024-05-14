package de.PSWTM.DigitalForms.Factory;

import de.PSWTM.DigitalForms.model.Attachment;

public class AttachmentFactory {

    public static Attachment createAttachment(String id, String description){
        Attachment attachment = new Attachment();
        attachment.setId(id);
        attachment.setDescription(description);

        return attachment;
    }
}
