package de.PSWTM.DigitalForms.configuration;

import de.PSWTM.DigitalForms.Factory.FormElementFactory;
import de.PSWTM.DigitalForms.Model.TemplatePDF;
import de.PSWTM.DigitalForms.model.*;
import de.PSWTM.DigitalForms.repository.FormRepository;
import de.PSWTM.DigitalForms.repository.TemplatePDFRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

import static de.PSWTM.DigitalForms.Factory.AttachmentFactory.createAttachment;
import static de.PSWTM.DigitalForms.Factory.FormElementFactory.createFormElement;
import static de.PSWTM.DigitalForms.Factory.FormFactory.createForm;
import static de.PSWTM.DigitalForms.Factory.FormSectionFactory.createFormSection;

@Configuration
public class DatabaseLoader {

    private static void addFormElement(List<FormSection> sections, FormElement.TypeEnum type, String dec, String help, String id){
        addFormElement(sections,type,dec,help,id,null);
    }

    private static void addFormElement(List<FormSection> sections, FormElement.TypeEnum type, String dec, String help, String id, String ref){
        sections.get(sections.size() - 1).addItemsItem(createFormElement(id,type,dec,help));
    }

    private static void addPaymentSection(List<FormSection> sections, int orderIndex){
        sections.add(createFormSection(orderIndex,"Zahlung"));
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Empfänger*in"
                ,null,"prepay_empf");
        addFormElement(sections,FormElement.TypeEnum.ADDRESS,"Anschrift"
                ,null,"prepay_adress");
        addFormElement(sections,FormElement.TypeEnum.IBAN,"IBAN"
                ,null,"prepay_iban");
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Kreditinstitut"
                ,null,"prepay_bank");
    }

    private static void AddGeneralContactSection(List<FormSection> sections, int orderIndex){
        sections.add(createFormSection(orderIndex,"Generell"));
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Kurzbezeichnung des Antrages"
                ,null,"bez");
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Fachschaft/Referat/Arbeitskreis"
                ,null,"fs_ref_ar");
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Kontaktdaten"
                ,"(Handy-Nr., E-mail)","contact");
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Antragsteller*in"
                ,"(Name, Vorname)","user");
    }


    private static Form gen_Genehmigung_von_Ausgaben_und_Anschaffungen(){
        List<FormSection> sections = new ArrayList<>();

        AddGeneralContactSection(sections,0);
        sections.add(createFormSection(1,"Hauptteil"));
        addFormElement(sections,FormElement.TypeEnum.TEXTMULTILINE,"Verwendungszweck: (Bitte ausführlich beschreiben! Evtl. Beiblatt nutzen)"
                ,"Was? Warum? Welcher Preis?","reason");
        addFormElement(sections,FormElement.TypeEnum.BOOL,"Unterschriebenes Protokoll der FS-Sitzung mit genau erläutertem Beschluss im Anhang?"
                ,null,"prtocoll",
                "Attachments-Protokoll");
        addFormElement(sections,FormElement.TypeEnum.DATE,"Termin (geplant):"
                ,null,"date");
        addFormElement(sections,FormElement.TypeEnum.MONEY,"Höhe der Ausgaben (ca.):"
                ,"(voraussichtliche Höhe angeben, falls nicht genau bekannt)","money");

        addPaymentSection(sections,2);

        List<Attachment> attachments = new ArrayList<>();

        attachments.add(createAttachment("Protokoll","Unterschriebenes Protokoll der FS-Sitzung\nEnthält detailarten beschluss,..."));

        Form f1 = new Form();
        f1.setTitel("Ausgaben und Anschaffungen");
        f1.template(true);
        f1.setDescription("Genehmigung von Ausgaben und Anschaffungen");
        f1.setCategory(Form.CategoryEnum.ANTRAG);
        f1.setForm(sections);
        f1.attachments(attachments);

        return f1;
    }

    private static Form gen_Erstattung_von_Auslagen_und_Rechnungen(){
        Form form = new Form();
        form.setTemplate(true);
        form.setTitel("Ausgaben und Anschaffungen");
        form.setDescription("Erstattung von Auslagen und Rechnungen");
        form.setCategory(Form.CategoryEnum.ABRECHNUNG);

        List<FormSection> sections = new ArrayList<>();

        AddGeneralContactSection(sections,0);

        addPaymentSection(sections,1);

        sections.getLast().getItems().addFirst(
                FormElementFactory.createFormElement("sum",
                        FormElement.TypeEnum.MONEY,"Gesamtbetrag in Euro:",null));

        form.setForm(sections);

        return form;
    }

    public static void initFormRepository(FormRepository repository, TemplatePDFRepository pdfRepository){
        Form newForm = repository.save(gen_Genehmigung_von_Ausgaben_und_Anschaffungen());
        pdfRepository.save(new TemplatePDF(newForm.getId(), "AntragAufGenehmigenTemplate"));

        newForm = repository.save(gen_Erstattung_von_Auslagen_und_Rechnungen());
        pdfRepository.save(new TemplatePDF(newForm.getId(), "ErstattungvonAuslagenundRechnungen"));

        repository.save(createForm("FS-Wochenende",true,"Ein wochenende für die FS", Form.CategoryEnum.ANTRAG));

        repository.save(createForm("Kulturelle Veranstaltung",true,"Eine Kulturelle Veranstaltung. Keine gewinnabsichten",Form.CategoryEnum.ANTRAG));
        repository.save(createForm("Reisekosten", true,"Ich / Wir wollen wo hin.",Form.CategoryEnum.ANTRAG));
        repository.save(createForm("Wirtschaftliche Veranstaltung",true,"Ne Veranstaltung wo geld eingenommen werden soll", Form.CategoryEnum.ANTRAG));
    }


    @Bean
    CommandLineRunner initDatabase(FormRepository formRepository,TemplatePDFRepository pdfRepository) {
        return args -> {
            if(formRepository.findAll().isEmpty()) {
                initFormRepository(formRepository, pdfRepository);

            }
        };
    }
}
