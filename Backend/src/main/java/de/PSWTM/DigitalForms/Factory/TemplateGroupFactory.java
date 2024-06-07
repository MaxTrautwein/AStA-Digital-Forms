package de.PSWTM.DigitalForms.Factory;

import de.PSWTM.DigitalForms.model.TemplateGroup;

import java.util.ArrayList;
import java.util.List;

public class TemplateGroupFactory {

    public static TemplateGroup createTemplateGroup(String titel, String desc, String AntragsID){
        TemplateGroup tg = new TemplateGroup();
        tg.setTitel(titel);
        tg.setDescription(desc);
        tg.setAntragId(AntragsID);
        tg.setReasons(new ArrayList<>());
        tg.setRechnungen(new ArrayList<>());

        return tg;
    }

}
