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

    // TODO: Check for Attachments
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

    // TODO: Check for Attachments & Rechnungs Liste
    private static Form gen_Erstattung_von_Auslagen_und_Rechnungen(){
        Form form = new Form();
        form.setTemplate(true);
        form.setTitel("Ausgaben und Anschaffungen");
        form.setDescription("Erstattung von Auslagen und Rechnungen");
        form.setCategory(Form.CategoryEnum.ABRECHNUNG);

        List<FormSection> sections = new ArrayList<>();

        AddGeneralContactSection(sections,0);

        sections.add(createFormSection(1,"Rechnungs Liste"));
        // TODO: Add List of Wo, Was & Betrag

        gen_Erstattung_bereits_getaetigter_Ausgaben__Erstattung_von_Auslagen_und_Rechnungen(sections,2);

        form.setForm(sections);

        return form;
    }
    // TODO: Check for Attachments & Table Details
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
    // TODO: Check for Attachments
    private static Form gen_Genehmigung_von_Reisen(){
        Form form = new Form();
        form.setTemplate(true);
        form.setTitel("Reise");
        form.setDescription("Antrag auf Genehmigung einer Reise");
        form.setCategory(Form.CategoryEnum.ANTRAG);

        List<FormSection> sections = new ArrayList<>();

        AddGeneralContactSection(sections,0);

        sections.add(createFormSection(1,"Beschreibung"));
        addFormElement(sections,FormElement.TypeEnum.TEXTMULTILINE,"Beschreibung der geplanten Reise"
                ,"(Zweck der Reise + Begründung Unterkunft / geplantes Transportmittel)","reason");
        addFormElement(sections,FormElement.TypeEnum.DATE,"Termin"
                ,null,"date");
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Mitfahrer*innen"
                ,null,"persons");
        addFormElement(sections,FormElement.TypeEnum.MONEY,"Geschätzte Höhe der Ausgaben:"
                ,null,"cost");

        sections.add(createFormSection(2,"Details"));
        addFormElement(sections,FormElement.TypeEnum.ADDRESS,"Genaue Anschrift Reisebeginn"
                ,"(PLZ, Ort, Straße, Hausnummer)","start");
        addFormElement(sections,FormElement.TypeEnum.ADDRESS,"Genaue Anschrift Reiseziel"
                ,"(PLZ, Ort, Straße, Hausnummer)","end");

        sections.add(createFormSection(3,"Wird ein Vorschuss benötigt? "));
        FormSection section = sections.get(sections.size() - 1);

        addPaymentSum(section);
        addPaymentVorschussDates(section);
        addGeneralPaymentInfoElements(section);

        form.setForm(sections);

        return form;
    }
    // TODO: Check for Attachments & Table with Teilnehmer*innenliste
    private static Form gen_Genehmigung_von_Reisen_mit_Fahrgemeinschaften(){
        Form form = new Form();
        form.setTemplate(true);
        form.setTitel("Reisen mit Fahrgemeinschaften");
        form.setDescription("Antrag auf Genehmigung von Reisen mit Fahrgemeinschaften");
        form.setCategory(Form.CategoryEnum.ANTRAG);

        List<FormSection> sections = new ArrayList<>();

        sections.add(createFormSection(0,"Generell"));
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Antragsteller*in"
                ,"(Name, Vorname)","user");
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Fachschaft/Referat/Arbeitskreis"
                ,null,"fs_ref_ar");
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Kontaktdaten"
                ,"(Handy-Nr., E-mail)","contact");

        sections.add(createFormSection(1,"Details"));
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Reise-Grund"
                ,null,"reason");
        addFormElement(sections,FormElement.TypeEnum.ADDRESS,"Reise-Ziel"
                ,null,"target");

        addFormElement(sections,FormElement.TypeEnum.DATE,"Reise-Beginn"
                ,"(Dat./Uhrzeit)","start_time");
        addFormElement(sections,FormElement.TypeEnum.ADDRESS,"Ort"
                ,null,"start_loc");
        addFormElement(sections,FormElement.TypeEnum.DATE,"Reise-Ende"
                ,"(Dat./Uhrzeit)","end_time");
        addFormElement(sections,FormElement.TypeEnum.ADDRESS,"Ort"
                ,null,"end_loc");

        addFormElement(sections,FormElement.TypeEnum.TEXT,"Mitreisende"
                ,null,"persons");
        addFormElement(sections,FormElement.TypeEnum.ADDRESS,"Zustieg"
                ,null,"zustieg");

        sections.add(createFormSection(2,"Reason"));
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Beförderungsmittel"
                ,null,"vehicle");
        addFormElement(sections,FormElement.TypeEnum.MONEY,"Voraussichtlich anfallende Kosten"
                ,null,"cost");
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Begründung"
                ,null,"explReason");

        sections.add(createFormSection(2,"Teilnehmer*innenliste der Reise"));
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Veranstaltung"
                ,null,"A_event");
        addFormElement(sections,FormElement.TypeEnum.DATE,"Termin"
                ,null,"A_date");
        addFormElement(sections,FormElement.TypeEnum.ADDRESS,"Ort"
                ,null,"A_loc");

        // TODO: Add Table with Teilnehmer*innenliste

        form.setForm(sections);

        return form;
    }
    // TODO: Check for Attachments
    private static Form gen_Genehmigung_von_wirtschaftlichen_Veranstaltungen(){
        Form form = new Form();
        form.setTemplate(true);
        form.setTitel("wirtschaftlichen Veranstaltung");
        form.setDescription("Veranstaltungen mit geplanten Einnahmen");
        form.setCategory(Form.CategoryEnum.ANTRAG);

        List<FormSection> sections = new ArrayList<>();

        sections.add(createFormSection(0,"Generell"));
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Name der Veranstaltung"
                ,null,"bez");
        // Note: in the Formular there are 2 fields for this very same info
        addFormElement(sections,FormElement.TypeEnum.DATE,"Datum der Veranstaltung"
                ,null,"date");
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Fachschaft/Referat/Arbeitskreis"
                ,null,"fs_ref_ar");
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Name Verantwortliche*r"
                ,null,"responsible");
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Kontaktdaten"
                ,"(Handy-Nr., E-mail)","contact");


        sections.add(createFormSection(1,"Details"));
        addFormElement(sections,FormElement.TypeEnum.TEXTMULTILINE,"Verwendungszweck"
                ,"(Bitte ausführlich beschreiben! Evtl. Beiblatt nutzen. -  Was? Warum? Welcher Preis?)","reason");
        addFormElement(sections,FormElement.TypeEnum.BOOL,"Unterschriebenes Protokoll der FS-Sitzung mit genau erläutertem Beschluss angehängt? "
                ,null,"protocoll");
        addFormElement(sections,FormElement.TypeEnum.BOOL,"Veranstaltungsplanung (Finanzplanung, Einnahmen/Ausgaben) incl. Preislisten angehängt?"
                ,null,"planning");
        addFormElement(sections,FormElement.TypeEnum.BOOL,"Veranstaltung bei Facilitymanagement / Ordnungsamt / Gema angemeldet? Kopie angehängt?"
                ,null,"fm_gema");
        addFormElement(sections,FormElement.TypeEnum.BOOL,"Zutatenliste erstellt und Aushänge für den Stand vorbereitet? (bei Verkauf von Speisen)"
                ,null,"ingredients");

        sections.add(createFormSection(2,"Date/Cost"));
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Anzahl der Gäste"
                ,"(geplant)","guests");
        addFormElement(sections,FormElement.TypeEnum.MONEY,"Höhe der Ausgaben(ca.)"
                ,null,"cost");

        addFormElement(sections,FormElement.TypeEnum.BOOL,"Vorschuss notwendig?"
                ,null,"req_vorschuss");
        // Note: there is a Vorschuss Field, but we don't need dups in the online tool
        addFormElement(sections,FormElement.TypeEnum.BOOL,"Wechselgeld notwendig?"
                ,null,"req_wechsel");

        sections.add(createFormSection(3,"Vorschuss"));
        FormSection section = sections.get(sections.size() - 1);

        addPaymentSum(section);
        addPaymentVorschussDates(section);
        addGeneralPaymentInfoElements(section);

        form.setForm(sections);

        return form;
    }

    private static Form gen_Abrechnung_eines_Vorschusses(){
        Form form = new Form();
        form.setTemplate(true);
        form.setTitel("Abrechnung erhaltener Vorschüsse");
        form.setDescription("Abrechnung erhaltener Vorschüsse");
        form.setCategory(Form.CategoryEnum.ABRECHNUNG);
        List<FormSection> sections = new ArrayList<>();

        sections.add(createFormSection(0,"Generell"));
        addFormElement(sections,FormElement.TypeEnum.DATE,"Datum der Veranstaltung"
                ,null,"date");
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Name der Veranstaltung"
                ,null,"bez");




        form.setForm(sections);
        return form;
    }

    private static Form gen_Erstattung_von_Reisekosten(){
        Form form = new Form();
        form.setTemplate(true);
        form.setTitel("Erstattung von Reisekosten");
        form.setDescription("Antrag auf Erstattung von Reisekosten");
        form.setCategory(Form.CategoryEnum.ABRECHNUNG);
        List<FormSection> sections = new ArrayList<>();

        gen_ErstattungReisekosten_Erstattung_von_Reisekosten(sections,1);

        form.setForm(sections);
        return form;
    }

    private static Form gen_Genehmigung_von_Fachschafts_wochenenden(){
        Form form = new Form();
        form.setTemplate(true);
        form.setTitel("Fachschafts-Wochenenden");
        form.setDescription("Wir wollen mit der FS auf ein Wochenende");
        form.setCategory(Form.CategoryEnum.ANTRAG);
        List<FormSection> sections = new ArrayList<>();


        form.setForm(sections);
        return form;
    }

    private static Form gen_Genehmigung_von_kulturellen_Veranstaltungen(){
        Form form = new Form();
        form.setTemplate(true);
        form.setTitel("kulturellen Veranstaltungen");
        form.setDescription("Teambuilding, Ersti-Tage, Absolventenverabschiedung, Mitgliederwerbung, Vorträge, ... (ohne geplante Einnahmen)");
        form.setCategory(Form.CategoryEnum.ANTRAG);
        List<FormSection> sections = new ArrayList<>();


        form.setForm(sections);
        return form;
    }

    public static void initFormRepository(FormRepository repository, TemplatePDFRepository pdfRepository){
        Form newForm = repository.save(gen_Genehmigung_von_Ausgaben_und_Anschaffungen());
        pdfRepository.save(new TemplatePDF(newForm.getId(), "AntragAufGenehmigenTemplate"));
        // TODO: Sollte eigentlich Wahl zwischen gen_Abrechnung_eines_Vorschusses & gen_Erstattung_von_Auslagen_und_Rechnungen
        newForm = repository.save(gen_Erstattung_von_Auslagen_und_Rechnungen());
        pdfRepository.save(new TemplatePDF(newForm.getId(), "ErstattungvonAuslagenundRechnungen"));


        newForm = repository.save(gen_Genehmigung_von_Fachschafts_wochenenden());
        pdfRepository.save(new TemplatePDF(newForm.getId(), "GenehmigungvonFachschaftswochenenden"));

        newForm = repository.save(gen_Abrechnung_von_Fachschafts_wochenenden());
        pdfRepository.save(new TemplatePDF(newForm.getId(), "AbrechnungvonFachschaftswochenenden"));


        newForm = repository.save(gen_Genehmigung_von_kulturellen_Veranstaltungen());
        pdfRepository.save(new TemplatePDF(newForm.getId(), "GenehmigungvonkulturellenVeranstaltungen"));
        // TODO: Sollte eigentlich Wahl zwischen gen_Abrechnung_eines_Vorschusses & gen_Erstattung_von_Auslagen_und_Rechnungen
        newForm = repository.save(gen_Abrechnung_eines_Vorschusses());
        pdfRepository.save(new TemplatePDF(newForm.getId(), "AbrechnungeinesVorschusses"));


        newForm = repository.save(gen_Genehmigung_von_Reisen());
        pdfRepository.save(new TemplatePDF(newForm.getId(), "GenehmigungvonReisen"));

        newForm = repository.save(gen_Erstattung_von_Reisekosten());
        pdfRepository.save(new TemplatePDF(newForm.getId(), "ErstattungvonReisekosten"));


        newForm = repository.save(gen_Genehmigung_von_Reisen_mit_Fahrgemeinschaften());
        pdfRepository.save(new TemplatePDF(newForm.getId(), "GenehmigungvonReisenmitFahrgemeinschaften"));
        // TODO: Das gen_Erstattung_von_Reisekosten ist ein Dupe -> Muss später besser gelöst werden
        newForm = repository.save(gen_Erstattung_von_Reisekosten());
        pdfRepository.save(new TemplatePDF(newForm.getId(), "ErstattungvonReisekosten"));

        newForm = repository.save(gen_Genehmigung_von_wirtschaftlichen_Veranstaltungen());
        pdfRepository.save(new TemplatePDF(newForm.getId(), "GenehmigungvonwirtschaftlichenVeranstaltungen"));
        // TODO: Sollte eigentlich Wahl zwischen gen_Abrechnung_eines_Vorschusses & gen_Erstattung_von_Auslagen_und_Rechnungen
        // TODO: Das gen_Erstattung_von_Auslagen_und_Rechnungen ist ein Dupe -> Muss später besser gelöst werden
        newForm = repository.save(gen_Erstattung_von_Auslagen_und_Rechnungen());
        pdfRepository.save(new TemplatePDF(newForm.getId(), "ErstattungvonAuslagenundRechnungen"));

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
