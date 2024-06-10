package de.PSWTM.DigitalForms.Attchments;

import de.PSWTM.DigitalForms.Factory.AttachmentFactory;
import de.PSWTM.DigitalForms.Factory.FormElementFactory;
import de.PSWTM.DigitalForms.Factory.FormFactory;
import de.PSWTM.DigitalForms.Factory.FormSectionFactory;
import de.PSWTM.DigitalForms.controller.FormsController;
import de.PSWTM.DigitalForms.model.Attachment;
import de.PSWTM.DigitalForms.model.Form;
import de.PSWTM.DigitalForms.model.FormElement;
import de.PSWTM.DigitalForms.model.FormSection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

class AttachmentUtilTest {

    AttachmentUtil attachmentUtil_ExistingRefValue;
    AttachmentUtil attachmentUtil_ExistingRefNoValue;
    AttachmentUtil attachmentUtil_NoRef;

    private Logger logger = LoggerFactory.getLogger(FormsController.class);


    @BeforeEach
    void setUp() {
        // Create a Form with Attachments to Test against
        Form form = FormFactory.createForm("test",false,"test - d", Form.CategoryEnum.ABRECHNUNG);
        List<Attachment> attachments = new ArrayList<>();

        // 2 Required Attachments
        attachments.add(AttachmentFactory.createRequierdAttachment("R1","req",null));
        attachments.add(AttachmentFactory.createRequierdAttachment("R2","req",null));
        // 2 User Attachments
        attachments.add(AttachmentFactory.createUserAttachment("U1","user",null));
        attachments.add(AttachmentFactory.createUserAttachment("U2","user",null));

        // Conditional Attachments "="
        attachments.add(AttachmentFactory.createAttachment("C1","cond-pass",null
                , Attachment.RequiredEnum.CONDITIONAL,"100","=100" ));
        attachments.add(AttachmentFactory.createAttachment("C2","cond-fail",null
                , Attachment.RequiredEnum.CONDITIONAL,"100","=101" ));
        attachments.add(AttachmentFactory.createAttachment("C3","cond-fail",null
                , Attachment.RequiredEnum.CONDITIONAL,"100","=99" ));

        // Conditional Attachments "!"
        attachments.add(AttachmentFactory.createAttachment("C4","cond-fail",null
                , Attachment.RequiredEnum.CONDITIONAL,"100","!100" ));
        attachments.add(AttachmentFactory.createAttachment("C5","cond-pass",null
                , Attachment.RequiredEnum.CONDITIONAL,"100","!101" ));
        attachments.add(AttachmentFactory.createAttachment("C6","cond-pass",null
                , Attachment.RequiredEnum.CONDITIONAL,"100","!99" ));

        // Conditional Attachments ">"
        attachments.add(AttachmentFactory.createAttachment("C7","cond-pass",null
                , Attachment.RequiredEnum.CONDITIONAL,"100",">99" ));
        attachments.add(AttachmentFactory.createAttachment("C8","cond-fail",null
                , Attachment.RequiredEnum.CONDITIONAL,"100",">101" ));
        attachments.add(AttachmentFactory.createAttachment("C9","cond-fail",null
                , Attachment.RequiredEnum.CONDITIONAL,"100",">100" ));

        // Conditional Attachments "<"
        attachments.add(AttachmentFactory.createAttachment("C7","cond-fail",null
                , Attachment.RequiredEnum.CONDITIONAL,"100","<99" ));
        attachments.add(AttachmentFactory.createAttachment("C8","cond-pass",null
                , Attachment.RequiredEnum.CONDITIONAL,"100","<101" ));
        attachments.add(AttachmentFactory.createAttachment("C9","cond-fail",null
                , Attachment.RequiredEnum.CONDITIONAL,"100","<100" ));

        form.setAttachments(attachments);

        this.attachmentUtil_NoRef = new AttachmentUtil(form);

        // add the Reference Req
        List<FormSection> formSections = new ArrayList<>();
        formSections.add(FormSectionFactory.createFormSection(1,"test"));
        FormSection formSection = formSections.get(0);
        formSection.addItemsItem(FormElementFactory.createFormElement("100", FormElement.TypeEnum.MONEY,"test",null));
        form.setForm(formSections);

        this.attachmentUtil_ExistingRefNoValue = new AttachmentUtil(form);
        formSection.getItems().get(0).setValue("100");
        form.setForm(formSections);

        this.attachmentUtil_ExistingRefValue = new AttachmentUtil(form);
    }

    @Test
    void getAttachmentsReq_NoRef() {
        List<Attachment> attachments = attachmentUtil_NoRef.getAttachmentsReq();

        // The reference is Missing we should only get the Required ones
        for (Attachment attachment : attachments) {
            Assertions.assertEquals(attachment.getRequired(), Attachment.RequiredEnum.ALWAYS);
        }
        // We have 2 Required Attachments
        Assertions.assertEquals(attachments.size(), 2);
    }
    @Test
    void getAttachmentsReq_ExistingRefNoValue() {
        List<Attachment> attachments = attachmentUtil_ExistingRefNoValue.getAttachmentsReq();

        // The reference is Set but without a Value; we should only get the Required ones
        for (Attachment attachment : attachments) {
            Assertions.assertEquals(attachment.getRequired(), Attachment.RequiredEnum.ALWAYS);
        }
        // We have 2 Required Attachments
        Assertions.assertEquals(attachments.size(), 2);
    }

    @Test
    void getAttachmentsReq_ExistingRefValue() {
        List<Attachment> attachments = attachmentUtil_ExistingRefValue.getAttachmentsReq();

        // The reference is Set with a Value
        for (Attachment attachment : attachments) {
            if (attachment.getRequired() == Attachment.RequiredEnum.CONDITIONAL) {
                // Check the Conditional Ones:
                Assertions.assertEquals(attachment.getDescription(),"cond-pass");
            }
            // Can Only be ALWAYS or CONDITIONAL
            Assertions.assertTrue(attachment.getRequired() == Attachment.RequiredEnum.ALWAYS
                    || attachment.getRequired() == Attachment.RequiredEnum.CONDITIONAL);
        }
        // We have 2 Required Attachments
        Assertions.assertEquals(attachments.stream()
                .filter( attachment -> attachment.getRequired() ==
                        Attachment.RequiredEnum.ALWAYS ).toList().size(), 2);
        // We have 5 Passing Conditionals
        Assertions.assertEquals(attachments.stream()
                .filter( attachment -> attachment.getRequired() ==
                        Attachment.RequiredEnum.CONDITIONAL ).toList().size(), 5);

    }


    // For The User Attachments there should be no difference between the options
    void SameAssertUser(AttachmentUtil util){
        List<Attachment> attachments = util.getAttachmentsUser();
        Assertions.assertEquals(attachments.size(), 2);

        for (Attachment attachment : attachments) {
            Assertions.assertEquals(attachment.getRequired(), Attachment.RequiredEnum.USER);
        }
    }


    @Test
    void getAttachmentsUser_NoRef() {
        SameAssertUser(attachmentUtil_NoRef);
    }

    @Test
    void getAttachmentsUser_ExistingRefNoValue() {
        SameAssertUser(attachmentUtil_ExistingRefNoValue);
    }

    @Test
    void getAttachmentsUser_ExistingRefValue() {
        SameAssertUser(attachmentUtil_ExistingRefValue);
    }
}