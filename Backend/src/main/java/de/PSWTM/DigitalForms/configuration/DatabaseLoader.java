package de.PSWTM.DigitalForms.configuration;

import de.PSWTM.DigitalForms.Factory.FormElementFactory;
import de.PSWTM.DigitalForms.Model.TemplatePDF;
import de.PSWTM.DigitalForms.model.*;
import de.PSWTM.DigitalForms.repository.FormRepository;
import de.PSWTM.DigitalForms.repository.TemplatePDFRepository;
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
        addFormElement(sections.get(sections.size() - 1),type,dec,help,id,ref);
    }

    private static void addFormElement(FormSection section,FormElement.TypeEnum type, String dec, String help, String id){
        addFormElement(section,type,dec,help,id,null);
    }

    private static void addFormElement(FormSection section,FormElement.TypeEnum type, String dec, String help, String id, String ref){
        section.addItemsItem(createFormElement(id,type,dec,help));
    }


    private static void addGeneralPaymentInfoElements(FormSection section){
        addFormElement(section,FormElement.TypeEnum.TEXT,"Empfänger*in"
                ,null,"prepay_empf");
        addFormElement(section,FormElement.TypeEnum.ADDRESS,"Anschrift"
                ,null,"prepay_adress");
        addFormElement(section,FormElement.TypeEnum.IBAN,"IBAN"
                ,null,"prepay_iban");
        addFormElement(section,FormElement.TypeEnum.TEXT,"Kreditinstitut"
                ,null,"prepay_bank");
    }
    private static void addPaymentSum(FormSection section){
        addPaymentTotal(section,"Gesamtbetrag in Euro",null);
    }
    private static void addPaymentDiff(FormSection section){
        addPaymentTotal(section,"Differenzbetrag in Euro",null);
    }
    private static void addPaymentTotal(FormSection section, String desc, String help){
        addFormElement(section,FormElement.TypeEnum.MONEY,desc
                ,help,"prepay_sum");
    }

    private static void addPaymentVorschussDates(FormSection section){
        addFormElement(section,FormElement.TypeEnum.DATE,"Der Vorschuss wird bis zum folgenden Datum benötigt"
                ,null,"prepay_req_till");
        addFormElement(section,FormElement.TypeEnum.DATE,"Spätester Termin für die Rückzahlung"
                ,"(Muss unmittelbar nach dem Kauf erfolgen!)","prepay_payback_till");
    }


    private static void gen_Vorschuss_Genehmigung_von_Ausgaben_und_Anschaffungen(List<FormSection> sections, int orderIndex ){
        sections.add(createFormSection(orderIndex,"Vorschuss"));
        FormSection section = sections.get(sections.size() - 1);

        addPaymentSum(section);
        addPaymentVorschussDates(section);
        addGeneralPaymentInfoElements(section);
    }
    private static void gen_Erstattung_bereits_getaetigter_Ausgaben__Erstattung_von_Auslagen_und_Rechnungen(List<FormSection> sections, int orderIndex ){
        sections.add(createFormSection(orderIndex,"Erstattung bereits getätigter Ausgaben"));
        FormSection section = sections.get(sections.size() - 1);

        addPaymentSum(section);
        addGeneralPaymentInfoElements(section);
    }
    private static void gen_VorschussAbrechFSWE_Abrechnung_von_Fachschafts_wochenenden(List<FormSection> sections, int orderIndex ){
        sections.add(createFormSection(orderIndex,"Abrechnung Vorschuss für Fachschaftswochenenden"));
        FormSection section = sections.get(sections.size() - 1);

        addFormElement(section,FormElement.TypeEnum.MONEY,"Vorschuss erhalten?"
                ,"(Betrag in Euro)","vorschuss_sum");
        addFormElement(section,FormElement.TypeEnum.MONEY,"Vorschuss zurückgezahlt?"
                ,"(Betrag in Euro)","vorschuss_payback_sum");
        addFormElement(section,FormElement.TypeEnum.DATE,"Datum"
                ,null,"vorschuss_payback_date");

        addFormElement(section,FormElement.TypeEnum.BOOL,"Rückerstattung Differenzbetrag notwendig?"
                ,null,"erstatt_req");
        addFormElement(section,FormElement.TypeEnum.MONEY,"Betrag"
                ,"(Betrag in Euro)","erstatt_sum");

        addGeneralPaymentInfoElements(section);
    }
    private static void gen_ErstattungReisekosten_Erstattung_von_Reisekosten(List<FormSection> sections, int orderIndex){
        sections.add(createFormSection(orderIndex,"Erstattung von Reisekosten"));
        FormSection section = sections.get(sections.size() - 1);

        addPaymentSum(section);
        addGeneralPaymentInfoElements(section);
        addFormElement(section,FormElement.TypeEnum.BOOL,"Vorschuss gezahlt?"
                ,null,"vorschuss_exists");
        addFormElement(section,FormElement.TypeEnum.MONEY,"Höhe"
                ,"(Betrag in Euro)","vorschuss_sum");
        addFormElement(section,FormElement.TypeEnum.MONEY,"Restbetrag"
                ,"(Betrag in Euro)","vorschuss_sum_diff");
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

        addFormElement(sections,FormElement.TypeEnum.BOOL,"3 Vergleichsangebote vorhanden?:"
                ,"(Ab einem Bestellwert von 150 € sind drei Angebote beizufügen!)\n(Begründung, welches Angebot ausgewählt wurde und warum, bitte erläutern! Muss auch ins FS-Protokoll!) "
                ,"vergelichAnge");

        addFormElement(sections,FormElement.TypeEnum.BOOL,"Vorschuss nötig?"
                ,null
                ,"req_Vorschuss");

        // Note: Auf dem Export wird auch auf seite 1 Nach der Höhe gefragt.
        // Kann von Seite 2 Übernommen werden

        gen_Vorschuss_Genehmigung_von_Ausgaben_und_Anschaffungen(sections,2);

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

        gen_Erstattung_bereits_getaetigter_Ausgaben__Erstattung_von_Auslagen_und_Rechnungen(sections,1);

        sections.getLast().getItems().addFirst(
                FormElementFactory.createFormElement("sum",
                        FormElement.TypeEnum.MONEY,"Gesamtbetrag in Euro:",null));

        form.setForm(sections);

        return form;
    }

    private static Form gen_Abrechnung_von_Fachschafts_wochenenden(){
        Form form = new Form();
        form.setTemplate(true);
        form.setTitel("Fachschafts-Wochenende");
        form.setDescription("Abrechnung eines Fachschafts-Wochenendes");
        form.setCategory(Form.CategoryEnum.ABRECHNUNG);

        List<FormSection> sections = new ArrayList<>();

        sections.add(createFormSection(0,"Generell"));
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Bezeichnung der Veranstaltung"
                ,null,"bez");
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Fachschaft/Referat/Arbeitskreis"
                ,null,"fs_ref_ar");
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Kontaktdaten"
                ,"(Handy-Nr., E-mail)","contact");
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Antragsteller*in"
                ,"(Name, Vorname)","user");
        addFormElement(sections,FormElement.TypeEnum.ADDRESS,"Veranstaltungsort"
                ,null,"loc");

        addFormElement(sections,FormElement.TypeEnum.DATE,"Veranstaltungsdauer"
                ,null,"dur-from");
        addFormElement(sections,FormElement.TypeEnum.DATE,"Veranstaltungsdauer"
                ,null,"dur-till");

        sections.add(createFormSection(1,"Details"));
        // TODO Add Table Details

        gen_VorschussAbrechFSWE_Abrechnung_von_Fachschafts_wochenenden(sections,2);

        sections.add(createFormSection(3,"Tatsächliche TeilnehmerInnenliste"));
        addFormElement(sections,FormElement.TypeEnum.DATE,"Termin"
                ,null,"date_actual");
        addFormElement(sections,FormElement.TypeEnum.ADDRESS,"Anschrift"
                ,null,"loc_actual");

        // TODO Add Table

        form.setForm(sections);

        return form;
    }

    private static Form gen_Genehmigung_von_Reisen(){
        Form form = new Form();
        form.setTemplate(true);
        form.setTitel("Reise");
        form.setDescription("Antrag auf Genehmigung einer Reise");
        form.setCategory(Form.CategoryEnum.ANTRAG);

        List<FormSection> sections = new ArrayList<>();




        form.setForm(sections);

        return form;
    }

    private static Form gen_Genehmigung_von_Reisen_mit_Fahrgemeinschaften(){
        Form form = new Form();
        form.setTemplate(true);
        form.setTitel("Reisen mit Fahrgemeinschaften");
        form.setDescription("Antrag auf Genehmigung von Reisen mit Fahrgemeinschaften");
        form.setCategory(Form.CategoryEnum.ANTRAG);

        List<FormSection> sections = new ArrayList<>();




        form.setForm(sections);

        return form;
    }

    private static Form gen_Genehmigung_von_wirtschaftlichen_Veranstaltungen(){
        Form form = new Form();
        form.setTemplate(true);
        form.setTitel("wirtschaftlichen Veranstaltung");
        form.setDescription("Veranstaltungen mit geplanten Einnahmen");
        form.setCategory(Form.CategoryEnum.ANTRAG);

        List<FormSection> sections = new ArrayList<>();




        form.setForm(sections);

        return form;
    }

    public static void initFormRepository(FormRepository repository, TemplatePDFRepository pdfRepository){
        Form newForm = repository.save(gen_Genehmigung_von_Ausgaben_und_Anschaffungen());
        pdfRepository.save(new TemplatePDF(newForm.getId(), "AntragAufGenehmigenTemplate"));

        newForm = repository.save(gen_Erstattung_von_Auslagen_und_Rechnungen());
        pdfRepository.save(new TemplatePDF(newForm.getId(), "ErstattungvonAuslagenundRechnungen"));

        newForm = repository.save(gen_Abrechnung_von_Fachschafts_wochenenden());
        pdfRepository.save(new TemplatePDF(newForm.getId(), "AbrechnungvonFachschaftswochenenden"));

        newForm = repository.save(gen_Genehmigung_von_Reisen());
        pdfRepository.save(new TemplatePDF(newForm.getId(), "GenehmigungvonReisen"));

        newForm = repository.save(gen_Genehmigung_von_Reisen_mit_Fahrgemeinschaften());
        pdfRepository.save(new TemplatePDF(newForm.getId(), "GenehmigungvonReisenmitFahrgemeinschaften"));

        newForm = repository.save(gen_Genehmigung_von_wirtschaftlichen_Veranstaltungen());
        pdfRepository.save(new TemplatePDF(newForm.getId(), "GenehmigungvonwirtschaftlichenVeranstaltungen"));

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
