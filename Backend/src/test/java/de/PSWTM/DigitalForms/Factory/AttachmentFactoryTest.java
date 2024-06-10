package de.PSWTM.DigitalForms.Factory;

import de.PSWTM.DigitalForms.model.Attachment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttachmentFactoryTest {

    private void createAttachmentTestHelper(Attachment attachment,Attachment.RequiredEnum ReqEnum){
        Assertions.assertNotNull(attachment);
        Assertions.assertEquals("test-ID", attachment.getId());
        Assertions.assertEquals("test descript", attachment.getDescription());
        Assertions.assertEquals("help", attachment.getHelp());
        Assertions.assertEquals(ReqEnum, attachment.getRequired());
        Assertions.assertEquals("req-ID", attachment.getConditionRef());
        Assertions.assertEquals("some refval", attachment.getConditionRefVal());
    }

    @Test
    void createAttachment() {
        Attachment attachment = AttachmentFactory.createAttachment("test-ID","test descript"
                ,"help", Attachment.RequiredEnum.CONDITIONAL,"req-ID","some refval");
        createAttachmentTestHelper(attachment,Attachment.RequiredEnum.CONDITIONAL);
        attachment = null;
        Assertions.assertNull(attachment);

        attachment = AttachmentFactory.createAttachment("test-ID","test descript"
                ,"help", Attachment.RequiredEnum.USER,"req-ID","some refval");
        createAttachmentTestHelper(attachment,Attachment.RequiredEnum.USER);
        attachment = null;
        Assertions.assertNull(attachment);

        attachment = AttachmentFactory.createAttachment("test-ID","test descript"
                ,"help", Attachment.RequiredEnum.ALWAYS,"req-ID","some refval");
        createAttachmentTestHelper(attachment,Attachment.RequiredEnum.ALWAYS);

    }

    @Test
    void createRequierdAttachment() {
        Attachment attachment = AttachmentFactory.createRequierdAttachment("idReq","desc","help");
        Assertions.assertNotNull(attachment);
        Assertions.assertEquals("idReq", attachment.getId());
        Assertions.assertEquals("desc", attachment.getDescription());
        Assertions.assertEquals("help", attachment.getHelp());
        Assertions.assertEquals(Attachment.RequiredEnum.ALWAYS, attachment.getRequired());
        Assertions.assertNull(attachment.getConditionRef());
        Assertions.assertNull(attachment.getConditionRefVal());
    }

    @Test
    void createUserAttachment() {
        Attachment attachment = AttachmentFactory.createUserAttachment("iuser","desc","help");
        Assertions.assertNotNull(attachment);
        Assertions.assertEquals("iuser", attachment.getId());
        Assertions.assertEquals("desc", attachment.getDescription());
        Assertions.assertEquals("help", attachment.getHelp());
        Assertions.assertEquals(Attachment.RequiredEnum.USER, attachment.getRequired());
        Assertions.assertNull(attachment.getConditionRef());
        Assertions.assertNull(attachment.getConditionRefVal());
    }
}