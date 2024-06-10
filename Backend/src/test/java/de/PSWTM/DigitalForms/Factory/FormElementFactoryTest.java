package de.PSWTM.DigitalForms.Factory;

import de.PSWTM.DigitalForms.model.FormElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FormElementFactoryTest {

    @Test
    void createFormElement() {
        FormElement formElement = FormElementFactory
                .createFormElement("testID", FormElement.TypeEnum.DATE
                        ,"Test Description yay!","help me");
        Assertions.assertNotNull(formElement);
        Assertions.assertEquals("testID", formElement.getId());
        Assertions.assertEquals("Test Description yay!", formElement.getDescription());
        Assertions.assertEquals("help me", formElement.getHelp());
        Assertions.assertEquals(FormElement.TypeEnum.DATE, formElement.getType());
    }
}