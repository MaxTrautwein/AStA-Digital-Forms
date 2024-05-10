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
        addFormElement(sections,type,dec,help,id,null);
    }

    private void addFormElement(List<FormSection> sections, EFormElement type, String dec, String help, String id, String ref){
        sections.get(sections.size() - 1).items.add(FormElement.builder()
                .type(type)
                .Description(dec)
                .help(help)
                .id(id)
                //TODO Add ref later
                .build());
    }

    private void addPaymentSection(List<FormSection> sections, int orderIndex){
        sections.add(FormSection.builder()
                .order(orderIndex)
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
        addFormElement(sections,EFormElement.text,"Fachschaft/Referat/Arbeitskreis"
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
                ,null,"prtocoll",
                "Attachments-Protokoll");
        addFormElement(sections,EFormElement.date,"Termin (geplant):"
                ,null,"date");
        addFormElement(sections,EFormElement.money,"Höhe der Ausgaben (ca.):"
                ,"(voraussichtliche Höhe angeben, falls nicht genau bekannt)","money");

        addPaymentSection(sections,2);

        List<Attachment> attachments = new ArrayList<>();

        attachments.add(Attachment.builder().id("Protokoll").Description("Unterschriebenes Protokoll der FS-Sitzung\nEnthält detailarten beschluss,...").build());

        Form f1 = Form.builder().template(true)
                .titel("Genehmigung von Ausgaben und Anschaffungen")
                .description("bla Genehmigung von zeugs")
                .category(ECategory.Antrag)
                .form(sections)
                .build();



        return f1;
    }


    @Bean
    CommandLineRunner initDatabase(FormRepository formRepository) {
        return args -> {
            if(formRepository.count() == 0) {

                formRepository.save(gen_Genehmigung_von_Ausgaben_und_Anschaffungen());

                formRepository.save(Form.builder().titel("FS-Wochenende").description("Ein wochenende für die FS").category(ECategory.Antrag).build());
                formRepository.save(Form.builder().titel("Wirtschaftliche Veranstaltung").description("Ne Veranstaltung wo geld eingenommen werden soll").category(ECategory.Antrag).build());

                formRepository.save(Form.builder().titel("Reisekosten").description("Ich / Wir wollen wo hin.").category(ECategory.Antrag).build());
                formRepository.save(Form.builder().titel("Kulturelle Veranstaltung").description("Eine Kulturelle Veranstaltung. Keine gewinnabsichten").category(ECategory.Antrag).build());
                formRepository.save(Form.builder().titel("Erstattung Reisekosten").description("Wir sind nach Antrag mit Genehmigung wo hin. Gib geld").category(ECategory.Abrechnung).build());

            }
        };
    }
}
