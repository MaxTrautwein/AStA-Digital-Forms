package de.PSWTM.DigitalForms.Factory;

import de.PSWTM.DigitalForms.model.Form;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Since The Factory is used in another Test, it must work for, so that said test can work correctly
class FormFactoryTest {

    @Test
    void createFormShort() {
        Form form = FormFactory.createForm("testT",true,"desc"
                , Form.CategoryEnum.ABRECHNUNG);
        Assertions.assertNotNull(form);
        Assertions.assertEquals(Form.CategoryEnum.ABRECHNUNG, form.getCategory());
        Assertions.assertEquals("testT", form.getTitel());
        Assertions.assertEquals("desc", form.getDescription());
        Assertions.assertEquals(true, form.getTemplate());
        Assertions.assertNotNull(form.getForm());
        Assertions.assertNotNull(form.getAttachments());
        assertEquals(0, form.getAttachments().size());
        assertEquals(0, form.getForm().size());

    }

    @Test
    void createFormLongNull() {
        Form form = FormFactory.createForm("testT",true,"desc"
                , Form.CategoryEnum.ABRECHNUNG,null,null);
        Assertions.assertNotNull(form);
        Assertions.assertEquals(Form.CategoryEnum.ABRECHNUNG, form.getCategory());
        Assertions.assertEquals("testT", form.getTitel());
        Assertions.assertEquals("desc", form.getDescription());
        Assertions.assertEquals(true, form.getTemplate());
        Assertions.assertNull(form.getForm());
        Assertions.assertNull(form.getAttachments());
    }

    @Test
    void createFormLong() {
        Form form = FormFactory.createForm("testT",true,"desc"
                , Form.CategoryEnum.ABRECHNUNG,new ArrayList<>(),new ArrayList<>());
        Assertions.assertNotNull(form);
        Assertions.assertEquals(Form.CategoryEnum.ABRECHNUNG, form.getCategory());
        Assertions.assertEquals("testT", form.getTitel());
        Assertions.assertEquals("desc", form.getDescription());
        Assertions.assertEquals(true, form.getTemplate());
        Assertions.assertNotNull(form.getForm());
        Assertions.assertNotNull(form.getAttachments());
        assertEquals(0, form.getAttachments().size());
        assertEquals(0, form.getForm().size());
    }
}