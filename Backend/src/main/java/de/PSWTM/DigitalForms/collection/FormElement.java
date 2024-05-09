package de.PSWTM.DigitalForms.collection;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FormElement {

    public String id;

    public String Description;

    public String help;

    // Maybe as a Class
    public EFormElement type;

    //Not Shure how to best handle that refence in java
    //public String ref;

}
