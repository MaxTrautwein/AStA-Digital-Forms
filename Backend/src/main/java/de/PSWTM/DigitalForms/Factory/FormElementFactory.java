package de.PSWTM.DigitalForms.Factory;

import de.PSWTM.DigitalForms.model.FormElement;

public class FormElementFactory {

    public static FormElement createFormElement(String id, FormElement.TypeEnum type, String description, String help){
        FormElement ret = new FormElement();
        ret.setType(type);
        ret.setDescription(description);
        ret.setId(id);
        ret.setHelp(help);

        return ret;
    }
}
