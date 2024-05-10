package de.PSWTM.DigitalForms.configuration;

import de.PSWTM.DigitalForms.collection.*;
import de.PSWTM.DigitalForms.repository.FormRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DatabaseLoader {

    private void addFormElement(List<FormSection> sections, EFormElement type, String dec, String help, String id){
        sections.get(sections.size() - 1).items.add(FormElement.builder()
                .type(type)
                .Description(dec)
                .help(help)
                .id(id)
                .build());
    }

    private Form gen_Genehmigung_von_Ausgaben_und_Anschaffungen(){
        List<FormSection> sections = new ArrayList<>();

        sections.add(FormSection.builder()
                .order(0)
                .section("Generell")
                .items(new ArrayList<>())
                .build());
        addFormElement(sections,EFormElement.text,"Kurzbezeichnung des Antrages"
                ,null,"bez");
        addFormElement(sections,EFormElement.text,"Fachschaft/Referat/Arbeitskrei"
                ,null,"fs_ref_ar");
        addFormElement(sections,EFormElement.text,"Kontaktdaten"
                ,"(Handy-Nr., E-mail)","contact");
        addFormElement(sections,EFormElement.text,"Antragsteller*in"
                ,"(Name, Vorname)","user");
        sections.add(FormSection.builder()
                .order(1)
                .section("Hauptteil")
                .items(new ArrayList<>())
                .build());
        addFormElement(sections,EFormElement.TextMultiLine,"Verwendungszweck: (Bitte ausführlich beschreiben! Evtl. Beiblatt nutzen)"
                ,"Was? Warum? Welcher Preis?","reason");
        addFormElement(sections,EFormElement.bool,"Unterschriebenes Protokoll der FS-Sitzung mit genau erläutertem Beschluss im Anhang?"
                ,null,"Attachments-Protokoll");
        addFormElement(sections,EFormElement.date,"Termin (geplant):"
                ,null,"date");
        addFormElement(sections,EFormElement.money,"Höhe der Ausgaben (ca.):"
                ,"(voraussichtliche Höhe angeben, falls nicht genau bekannt)","money");
        sections.add(FormSection.builder()
                .order(2)
                .section("Zahlung")
                .items(new ArrayList<>())
                .build());
        addFormElement(sections,EFormElement.text,"Empfänger*in"
                ,null,"prepay_empf");
        addFormElement(sections,EFormElement.address,"Anschrift"
                ,null,"prepay_adress");
        addFormElement(sections,EFormElement.iban,"IBAN"
                ,null,"prepay_iban");
        addFormElement(sections,EFormElement.text,"Kreditinstitut"
                ,null,"prepay_bank");


        Form f1 = Form.builder().template(true)
                .titel("Genehmigung von Ausgaben und Anschaffungen")
                .description("bla Genehmigung von zeugs")
                .category(ECategory.Antrag)
                .form(sections)
                .build();



        return f1;
    }


    @Bean
    CommandLineRunner initDatabase(FormRepository userRepository) {
        return args -> {
            if(true) {

                userRepository.save(gen_Genehmigung_von_Ausgaben_und_Anschaffungen());
                
            }
        };
    }
}
