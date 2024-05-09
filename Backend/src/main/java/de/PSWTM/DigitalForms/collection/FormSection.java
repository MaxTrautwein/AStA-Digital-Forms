package de.PSWTM.DigitalForms.collection;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class FormSection {

    public int order;

    public String section;

    public List<FormElement> items;
}
