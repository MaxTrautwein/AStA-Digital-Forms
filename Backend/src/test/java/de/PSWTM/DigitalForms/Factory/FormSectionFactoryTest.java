package de.PSWTM.DigitalForms.Factory;

import de.PSWTM.DigitalForms.model.FormSection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FormSectionFactoryTest {

    @Test
    void createFormSection() {
        FormSection formSection = FormSectionFactory.createFormSection(1,"Section 1");
        Assertions.assertNotNull(formSection);
        Assertions.assertEquals(1, formSection.getOrder());
        Assertions.assertEquals("Section 1", formSection.getSection());
        Assertions.assertNotNull(formSection.getItems());
        Assertions.assertEquals(0, formSection.getItems().size());
    }

    @Test
    void testCreateFormSection() {
        FormSection formSection = FormSectionFactory.createFormSection(1,"Section 1",null);
        Assertions.assertNotNull(formSection);
        Assertions.assertEquals(1, formSection.getOrder());
        Assertions.assertEquals("Section 1", formSection.getSection());
        Assertions.assertNull(formSection.getItems());
        formSection = null;
        Assertions.assertNull(formSection);

        formSection = FormSectionFactory.createFormSection(33,"Section 2",new ArrayList<>());
        Assertions.assertNotNull(formSection);
        Assertions.assertEquals(33, formSection.getOrder());
        Assertions.assertEquals("Section 2", formSection.getSection());
        Assertions.assertNotNull(formSection.getItems());
        Assertions.assertEquals(0, formSection.getItems().size());

    }
}