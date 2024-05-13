package de.PSWTM.DigitalForms.Factory;

import de.PSWTM.DigitalForms.model.FormElement;
import de.PSWTM.DigitalForms.model.FormSection;

import java.util.ArrayList;

public class FormSectionFactory {

    public static FormSection createFormSection(Integer order, String section){
        return createFormSection(order, section, new ArrayList<>());
    }
    public static FormSection createFormSection(Integer order, String section, ArrayList<FormElement> items){
        FormSection formSection = new FormSection();
        formSection.setOrder(order);
        formSection.setSection(section);
        formSection.setItems(items);

        return formSection;
    }

}
