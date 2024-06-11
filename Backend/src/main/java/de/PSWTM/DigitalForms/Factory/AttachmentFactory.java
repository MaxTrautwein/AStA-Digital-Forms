package de.PSWTM.DigitalForms.Factory;

import de.PSWTM.DigitalForms.model.Attachment;

public class AttachmentFactory {

    public static Attachment createAttachment(String id, String description, String help,
                                              Attachment.RequiredEnum req, String reqRef,
                                              String reqRefVal){
        Attachment attachment = new Attachment();
        attachment.setId(id);
        attachment.setDescription(description);
        attachment.setHelp(help);
        attachment.setRequired(req);
        attachment.setConditionRef(reqRef);
        attachment.setConditionRefVal(reqRefVal);

        return attachment;
    }
    public static Attachment createRequierdAttachment(String id, String description, String help){
        return createAttachment(id,description,help,Attachment.RequiredEnum.ALWAYS,null,
                null);
    }
    public static Attachment createUserAttachment(String id, String description, String help){
        return createAttachment(id,description,help,Attachment.RequiredEnum.USER,null,
                null);
    }

}
