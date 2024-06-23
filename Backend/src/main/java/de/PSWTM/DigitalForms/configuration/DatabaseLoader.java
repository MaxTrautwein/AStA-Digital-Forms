package de.PSWTM.DigitalForms.configuration;

import de.PSWTM.DigitalForms.Factory.FormElementFactory;
import de.PSWTM.DigitalForms.Model.TemplatePDF;
import de.PSWTM.DigitalForms.model.*;
import de.PSWTM.DigitalForms.repository.FormRepository;
import de.PSWTM.DigitalForms.repository.TemplateGroupRepository;
import de.PSWTM.DigitalForms.repository.TemplatePDFRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;

import static de.PSWTM.DigitalForms.Factory.AttachmentFactory.*;
import static de.PSWTM.DigitalForms.Factory.FormElementFactory.createFormElement;
import static de.PSWTM.DigitalForms.Factory.FormFactory.createForm;
import static de.PSWTM.DigitalForms.Factory.FormSectionFactory.createFormSection;
import static de.PSWTM.DigitalForms.Factory.TemplateGroupFactory.createTemplateGroup;

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

        attachments.add(createRequierdAttachment("Protokoll",
                "Unterschriebenes Protokoll der FS-Sitzung",
                "Enthält detailarten beschluss,..."));
        attachments.add(createAttachment("vergleichsAnge","3 Vergleichsangebote",
                "Begründung, welches Angebot ausgewählt wurde und warum, bitte erläutern! Muss auch ins FS-Protokoll!",
                Attachment.RequiredEnum.CONDITIONAL,"money",">150"));

        Form f1 = new Form();
        f1.setTitel("Ausgaben und Anschaffungen");
        f1.template(true);
        f1.setDescription("Genehmigung von Ausgaben und Anschaffungen");
        f1.setCategory(Form.CategoryEnum.ANTRAG);
        f1.setForm(sections);
        f1.attachments(attachments);

        return f1;
    }


    // TODO: Rechnungs Liste
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
        List<Attachment> attachments = new ArrayList<>();
        attachments.add(createRequierdAttachment("Rechnungen",
                "Rechnungen/Quittungen ",
                "im Original beilegen.\nBitte beachten, dass kein Pfandgeld erstattet werden kann!"));
        form.setAttachments(attachments);

        return form;
    }
    // TODO: Table Details
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
                ,null,"dur_from");
        addFormElement(sections,FormElement.TypeEnum.DATE,"Veranstaltungsdauer"
                ,null,"dur_till");

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

        List<Attachment> attachments = new ArrayList<>();
        attachments.add(createRequierdAttachment("Rechnungen",
                "Rechnungen/Quittungen ",
                "im Original beilegen.\nBitte beachten, dass kein Pfandgeld erstattet werden kann!"));
        attachments.add(createRequierdAttachment("Teilnehmer",
                "Teilnehmer*innenliste",
                "Mit Unterschrift"));
        attachments.add(createUserAttachment("Fahrgemeinschaften",
                "Fahrgemeinschaften",
                null));

        form.setAttachments(attachments);

        return form;
    }

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

        List<Attachment> attachments = new ArrayList<>();
        attachments.add(createUserAttachment("invite",
                "Einladung/Tagesordnung",
                "Kopie Anhängen"));

        form.setAttachments(attachments);

        return form;
    }
    // TODO: Table with Teilnehmer*innenliste
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

        List<Attachment> attachments = new ArrayList<>();
        // TODO: Check if that should be an Attachment or an Form Element
        attachments.add(createRequierdAttachment("Teilnehmer",
                "Teilnehmer*innenliste",
                "Mit Unterschrift"));

        form.setAttachments(attachments);

        return form;
    }

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

        List<Attachment> attachments = new ArrayList<>();
        attachments.add(createRequierdAttachment("Protokoll",
                "Unterschriebenes Protokoll der FS-Sitzung",
                "genau erläutertem Beschluss"));
        attachments.add(createRequierdAttachment("moneyPlan",
                "Veranstaltungsplanung",
                "Finanzplanung, Einnahmen/Ausgaben, incl. Preislisten"));
        attachments.add(createRequierdAttachment("angemeldet",
                "Facilitymanagement / Ordnungsamt / Gema",
                "Kopie anhängen"));
        attachments.add(createUserAttachment("ingredients",
                "Zutatenliste & Aushänge",
                "bei Verkauf von Speisen"));
        attachments.add(createUserAttachment("angebote",
                "Vergleichsangebote mit Begründung",
                "bei großen Summen"));

        form.setAttachments(attachments);

        return form;
    }
    // TODO: Check Rechnung's Tabelle vs Attachment & Calc Element
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
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Fachschaft/Referat/Arbeitskreis"
                ,null,"fs_ref_ar");
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Verantwortliche*r"
                ,null,"user");
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Kontaktdaten"
                ,"(Handy-Nr., E-mail)","contact");

        sections.add(createFormSection(1,"Info - Vorschuss"));
        addFormElement(sections,FormElement.TypeEnum.DATE,"Datum der Vorschusszahlung"
                ,null,"dateVorschussPayment");
        addFormElement(sections,FormElement.TypeEnum.MONEY,"Höhe des Vorschusses"
                ,null,"VorschussMoney");
        addFormElement(sections,FormElement.TypeEnum.MONEY,"Gesamtsumme der Ausgaben:"
                ,null,"MoneySpent");
        // We should be able to calculate the Difference, no need for another field
        addFormElement(sections,FormElement.TypeEnum.BOOL,"Differenzbetrag ist vom AStA zu erstatten"
                ,"wenn nicht gesetzt zahlen wir zurück","AstaPayBack");
        addFormElement(sections,FormElement.TypeEnum.DATE,"Datum der Rückzahlung"
                ,null,"datePayBack");

        // TODO or not? Rechnung's Tabelle

        sections.add(createFormSection(2,"Vorschuss"));
        FormSection section = sections.get(sections.size() - 1);

        addPaymentDiff(section);
        addGeneralPaymentInfoElements(section);

        List<Attachment> attachments = new ArrayList<>();
        attachments.add(createRequierdAttachment("Rechnungen",
                "Rechnungen/Quittungen ",
                "im Original beilegen.\nBitte beachten, dass kein Pfandgeld erstattet werden kann!"));

        form.setAttachments(attachments);

        form.setForm(sections);
        return form;
    }

    // TODO: Conditional Sections & Might want some Grouping Option
    private static Form gen_Erstattung_von_Reisekosten(){
        Form form = new Form();
        form.setTemplate(true);
        form.setTitel("Erstattung von Reisekosten");
        form.setDescription("Antrag auf Erstattung von Reisekosten");
        form.setCategory(Form.CategoryEnum.ABRECHNUNG);
        List<FormSection> sections = new ArrayList<>();

        sections.add(createFormSection(0,"Generell"));
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Antragsteller*in"
                ,null,"user");
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Fachschaft/Referat/Arbeitskreis"
                ,null,"fs_ref_ar");
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Kontaktdaten"
                ,"(Handy-Nr., E-mail)","contact");


        addFormElement(sections,FormElement.TypeEnum.TEXT,"Zweck der Reise"
                ,null,"reason");
        addFormElement(sections,FormElement.TypeEnum.ADDRESS,"Ziel-Anschrift"
                ,null,"target");

        sections.add(createFormSection(1,"Reise"));
        addFormElement(sections,FormElement.TypeEnum.DATE,"Reisebeginn"
                ,"(Dat./Uhrzeit)","startDate");
        addFormElement(sections,FormElement.TypeEnum.ADDRESS,"Ort"
                ,null,"startLoc");

        addFormElement(sections,FormElement.TypeEnum.DATE,"Reisebeginn"
                ,"(Dat./Uhrzeit)","endDate");
        addFormElement(sections,FormElement.TypeEnum.ADDRESS,"Ort"
                ,null,"endLoc");

        addFormElement(sections,FormElement.TypeEnum.TEXT,"Mitreisende"
                ,"(Anz./Name)","persons");
        addFormElement(sections,FormElement.TypeEnum.ADDRESS,"Zustieg"
                ,null,"zustieg");

        addFormElement(sections,FormElement.TypeEnum.TEXT,"Beförderungsmittel"
                ,"(Kfz/Bahn etc.)","vehicle");
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Beförderungsmittel"
                ,null,"distance");

        sections.add(createFormSection(2,"Summ-up"));
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Entfernung (Hin+Rückfahrt)"
                ,null,"distanceTotal");
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Anzahl Km mit Mitreisenden"
                ,null,"distanceMultiPerson");

        addFormElement(sections,FormElement.TypeEnum.MONEY,"Nebenkosten"
                ,"(z.B. Parkgebühren, Seminargebühren, -unterlagen, etc.)","sideMoney");
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Begründung für zusätzliche Kosten"
                ,"(z.B. Taxi)","sideMoneyReason");
        addFormElement(sections,FormElement.TypeEnum.MONEY,"Übernachtungskosten"
                ,"(inkl. Frühstück)","sleepMoney");

        sections.add(createFormSection(3,"unentgeltliche Verpflegung - (wenn von Tagungsfirma bereitgestellt)"));

        addFormElement(sections,FormElement.TypeEnum.BOOL,"Frühstück"
                ,"Kürzung des Tagesgeldes um 20 %","unentgeltBreak");
        addFormElement(sections,FormElement.TypeEnum.BOOL,"Mittagessen"
                ,"Kürzung des Tagesgeldes um 50 %","unentgeltLunch");
        addFormElement(sections,FormElement.TypeEnum.BOOL,"Abendessen"
                ,"Kürzung des Tagesgeldes um 30 %","unentgeltDinner");

        sections.add(createFormSection(4,"Anlagen"));

        addFormElement(sections,FormElement.TypeEnum.BOOL,"Anlage zur Reisekostenabrechnung mit Begründung"
                ,"Hotel- und/oder Taxikosten","anlageReiseMoney");
        addFormElement(sections,FormElement.TypeEnum.BOOL,"Aufstellung zur Sammelreisekostenabrechnung"
                ,null,"anlageSammelMoney");
        addFormElement(sections,FormElement.TypeEnum.BOOL,"Teilnehmer*innenliste"
                ,"bei Gruppenreisen","anlagePersons");


        gen_ErstattungReisekosten_Erstattung_von_Reisekosten(sections,5);

        sections.add(createFormSection(6,"Anlage zur Reisekostenabrechnung "));
        // First 4 Fields are Dupes that we don't need again


        addFormElement(sections,FormElement.TypeEnum.BOOL,"Begründung Hotelkosten:"
                ,"Anreise mit öffentlichen Verkehrsmitteln, dadurch geringere Flexibilität, aber dadurch Einsparung von zusätzlichen Fahrtkosten"
                , "hotel1");
        addFormElement(sections,FormElement.TypeEnum.BOOL,"Begründung Hotelkosten:"
                ,"Tagungshotel, gemeinsame Abendveranstaltung, keine zusätzlichen Fahrtkosten"
                , "hotel2");
        addFormElement(sections,FormElement.TypeEnum.BOOL,"Begründung Hotelkosten:"
                ,"keine günstigere Unterkunft verfügbar (Ausdruck von HRS, hotel.de o.ä. liegt bei)"
                , "hotel3");
        addFormElement(sections,FormElement.TypeEnum.BOOL,"Begründung Hotelkosten:"
                ,"Messezeit", "hotel4");

        addFormElement(sections,FormElement.TypeEnum.BOOL,"Begründung Taxibenutzung/Sonstige:"
                ,"Keine öffentlichen Verkehrsmittel vorhanden (frühe/späte Abfahrt/Ankunft, Sonn-/Feiertag)"
                ,"taxi1");
        addFormElement(sections,FormElement.TypeEnum.BOOL,"Begründung Taxibenutzung/Sonstige:"
                ,"Unhandliches und schweres Gepäck/Unterlagen"
                , "taxi2");
        addFormElement(sections,FormElement.TypeEnum.BOOL,"Begründung Taxibenutzung/Sonstige:"
                ,"Taxi mit anderen Mitreisenden geteilt"
                , "taxi3");
        addFormElement(sections,FormElement.TypeEnum.BOOL,"Begründung Taxibenutzung/Sonstige:"
                ,"Im Ausland: sichere und relativ günstige Alternative zu öffentlichen Verkehrsmitteln"
                , "taxi4");


        addFormElement(sections,FormElement.TypeEnum.TEXTMULTILINE,"Sonstige Bemerkungen:"
                ,null, "AdditionalReason");

        form.setForm(sections);

        List<Attachment> attachments = new ArrayList<>();
        attachments.add(createRequierdAttachment("Rechnungen",
                "Rechnungen/Quittungen",
                "im Original beilegen.\nBitte beachten, dass kein Pfandgeld erstattet werden kann!"));
        attachments.add(createRequierdAttachment("MoneyCalc",
                "Aufstellung zur Sammelreisekostenabrechnung ",
                null));

        attachments.add(createUserAttachment("Teilnehmer",
                "Teilnehmer*innenliste",
                "bei Gruppenreisen"));


        form.setAttachments(attachments);

        return form;
    }

    // TODO Check TeilnehmerList
    private static Form gen_Genehmigung_von_Fachschafts_wochenenden(){
        Form form = new Form();
        form.setTemplate(true);
        form.setTitel("Fachschafts-Wochenenden");
        form.setDescription("Wir wollen mit der FS auf ein Wochenende");
        form.setCategory(Form.CategoryEnum.ANTRAG);
        List<FormSection> sections = new ArrayList<>();

        AddGeneralContactSection(sections,0);

        sections.add(createFormSection(1,"Reason"));
        addFormElement(sections,FormElement.TypeEnum.TEXTMULTILINE,"Beschreibung des geplanten Fachschafts-Wochenendes"
                ,"Thema, Zweck, Ablaufplan, Dozent*innen, etc"
                ,"reason");
        addFormElement(sections,FormElement.TypeEnum.BOOL,"Teilnehmer*innenliste"
                ,"vorläufig", "personList");
        addFormElement(sections,FormElement.TypeEnum.BOOL,"Unterschriebenes Protokoll der FS-Sitzung"
                ,"mit genau erläutertem Beschluss", "protocol");
        addFormElement(sections,FormElement.TypeEnum.BOOL,"Ablaufplan, Angebot Unterkunft und genaue Kostenaufstellung"
                ,"Für die Unterkünfte müssen 3 Vergleichsangebote eingeholt werden", "detailPlanningDocs");

        sections.add(createFormSection(2,"Details"));
        addFormElement(sections,FormElement.TypeEnum.DATE,"Termin"
                ,null, "date");
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Teilnehmer*innenzahl"
                ,"Angabe ist wichtig für Versicherung!", "personCnt");
        addFormElement(sections,FormElement.TypeEnum.MONEY,"Geschätzte Höhe der Ausgaben"
                ,null, "cost");
        addFormElement(sections,FormElement.TypeEnum.ADDRESS,"Genaue Anschrift der Unterkunft"
                ,"Adresse, Ort", "loc");

        sections.add(createFormSection(3,"Vorschuss"));
        FormSection section = sections.get(sections.size() - 1);

        addPaymentSum(section);
        addPaymentVorschussDates(section);
        addGeneralPaymentInfoElements(section);

        // Check if TeilnehmerList should be an Element

        form.setForm(sections);

        List<Attachment> attachments = new ArrayList<>();
        // Nicht im Formular, Aber definitiv Gefordert
        attachments.add(createRequierdAttachment("Protokoll",
                "Unterschriebenes Protokoll der FS-Sitzung",
                "Enthält detailarten beschluss,..."));
        attachments.add(createRequierdAttachment("Teilnehmer",
                "Teilnehmer*innenliste",
                "vorläufig"));
        attachments.add(createRequierdAttachment("Ablaufplan",
                "Ablaufplan",
                null));
        attachments.add(createUserAttachment("Vergleichsangebote",
                "Vergleichsangebote",
                "Wenn in Unterkunft"));
        attachments.add(createRequierdAttachment("Kostenaufstellung",
                "genaue Kostenaufstellung",
                null));

        form.setAttachments(attachments);


        return form;
    }


    private static Form gen_Genehmigung_von_kulturellen_Veranstaltungen(){
        Form form = new Form();
        form.setTemplate(true);
        form.setTitel("kulturellen Veranstaltungen");
        form.setDescription("Teambuilding, Ersti-Tage, Absolventenverabschiedung, Mitgliederwerbung, Vorträge, ... (ohne geplante Einnahmen)");
        form.setCategory(Form.CategoryEnum.ANTRAG);
        List<FormSection> sections = new ArrayList<>();

        AddGeneralContactSection(sections,0);

        sections.add(createFormSection(1,"Details"));
        addFormElement(sections,FormElement.TypeEnum.TEXTMULTILINE,"Verwendungszweck"
                ,"(Bitte ausführlich beschreiben! Evtl. Beiblatt nutzen. -  Was? Warum? Welcher Preis?)"
                ,"reason");
        addFormElement(sections,FormElement.TypeEnum.BOOL,"Unterschriebenes Protokoll der FS-Sitzung"
                ,"mit genau erläutertem Beschluss", "protocol");
        addFormElement(sections,FormElement.TypeEnum.DATE,"Termin"
                ,"geplant", "date");
        addFormElement(sections,FormElement.TypeEnum.TEXT,"Teilnehmer*innenzahl"
                ,"geplant", "persons");
        addFormElement(sections,FormElement.TypeEnum.MONEY,"Höhe der Ausgaben"
                ,"voraussichtliche Höhe angeben, falls nicht genau bekannt", "cost");

        // there are some checkboxes for Vorschuss, we should be able to infer that from the following page


        sections.add(createFormSection(3,"Vorschuss"));
        FormSection section = sections.get(sections.size() - 1);

        addPaymentSum(section);
        addPaymentVorschussDates(section);
        addGeneralPaymentInfoElements(section);

        form.setForm(sections);

        List<Attachment> attachments = new ArrayList<>();
        attachments.add(createRequierdAttachment("Protokoll",
                "Unterschriebenes Protokoll der FS-Sitzung",
                "Enthält detailarten beschluss,..."));
        attachments.add(createUserAttachment("Teilnehmer",
                "Teilnehmer*innenliste",
                "bei z.b.: Teambuilding"));

        form.setAttachments(attachments);

        return form;
    }

    public static void initFormRepository(FormRepository repository, TemplatePDFRepository pdfRepository,
                                          TemplateGroupRepository tgRepository){
        String Id_Erstattung_von_Auslagen_und_Rechnungen = "";
        String Id_Abrechnung_eines_Vorschusses = "";
        String Id_Erstattung_von_Reisekosten = "";

        Form newForm = repository.save(gen_Genehmigung_von_Ausgaben_und_Anschaffungen());
        pdfRepository.save(new TemplatePDF(newForm.getId(), "AntragAufGenehmigenTemplate"));

        Id_Erstattung_von_Auslagen_und_Rechnungen = repository.save(gen_Erstattung_von_Auslagen_und_Rechnungen()).getId();
        pdfRepository.save(new TemplatePDF(Id_Erstattung_von_Auslagen_und_Rechnungen, "ErstattungvonAuslagenundRechnungen"));

        Id_Abrechnung_eines_Vorschusses = repository.save(gen_Abrechnung_eines_Vorschusses()).getId();
        pdfRepository.save(new TemplatePDF(Id_Abrechnung_eines_Vorschusses, "AbrechnungeinesVorschusses"));

        TemplateGroup tg = createTemplateGroup("Ausgaben und Anschaffungen", "Wir wollen uns was Anschaffen", newForm.getId());
        tg.getRechnungen().add(Id_Erstattung_von_Auslagen_und_Rechnungen);
        tg.getReasons().add("Gab keinen Vorschuss");
        tg.getRechnungen().add(Id_Abrechnung_eines_Vorschusses);
        tg.getReasons().add("hatten einen Vorschuss");
        tgRepository.save(tg);

        newForm = repository.save(gen_Genehmigung_von_Fachschafts_wochenenden());
        pdfRepository.save(new TemplatePDF(newForm.getId(), "GenehmigungvonFachschaftswochenenden"));

        tg = createTemplateGroup("FS - WE", "Wir gehen mit der FS wo hin. Besprechen und Planen", newForm.getId());

        newForm = repository.save(gen_Abrechnung_von_Fachschafts_wochenenden());
        pdfRepository.save(new TemplatePDF(newForm.getId(), "AbrechnungvonFachschaftswochenenden"));
        tg.getRechnungen().add(newForm.getId());
        tgRepository.save(tg);


        newForm = repository.save(gen_Genehmigung_von_kulturellen_Veranstaltungen());
        pdfRepository.save(new TemplatePDF(newForm.getId(), "GenehmigungvonkulturellenVeranstaltungen"));

        tg = createTemplateGroup("Kulturelle Veranstaltung", "Eine Veranstaltung ohne Gewinnabsicht", newForm.getId());
        tg.getRechnungen().add(Id_Erstattung_von_Auslagen_und_Rechnungen);
        tg.getReasons().add("Gab keinen Vorschuss");
        tg.getRechnungen().add(Id_Abrechnung_eines_Vorschusses);
        tg.getReasons().add("hatten einen Vorschuss");
        tgRepository.save(tg);



        newForm = repository.save(gen_Genehmigung_von_Reisen());
        pdfRepository.save(new TemplatePDF(newForm.getId(), "GenehmigungvonReisen"));

        tg = createTemplateGroup("Reise", "Ich plane eine Reise alleine", newForm.getId());


        Id_Erstattung_von_Reisekosten = repository.save(gen_Erstattung_von_Reisekosten()).getId();
        pdfRepository.save(new TemplatePDF(Id_Erstattung_von_Reisekosten, "ErstattungvonReisekosten"));
        tg.getRechnungen().add(Id_Erstattung_von_Reisekosten);
        tgRepository.save(tg);


        newForm = repository.save(gen_Genehmigung_von_Reisen_mit_Fahrgemeinschaften());
        pdfRepository.save(new TemplatePDF(newForm.getId(), "GenehmigungvonReisenmitFahrgemeinschaften"));

        tg = createTemplateGroup("Reise mit Fahrgemeinschaft", "Wir wollen in einer Gruppe Reisen", newForm.getId());
        tg.getRechnungen().add(Id_Erstattung_von_Reisekosten);
        tgRepository.save(tg);


        newForm = repository.save(gen_Genehmigung_von_wirtschaftlichen_Veranstaltungen());
        pdfRepository.save(new TemplatePDF(newForm.getId(), "GenehmigungvonwirtschaftlichenVeranstaltungen"));

        tg = createTemplateGroup("Wirtschaftliche Veranstaltung", "Eine Veranstaltung mit Gewinnabsicht", newForm.getId());
        tg.getRechnungen().add(Id_Erstattung_von_Auslagen_und_Rechnungen);
        tg.getReasons().add("Gab keinen Vorschuss");
        tg.getRechnungen().add(Id_Abrechnung_eines_Vorschusses);
        tg.getReasons().add("hatten einen Vorschuss");
        tgRepository.save(tg);

    }


    @Bean
    CommandLineRunner initDatabase(FormRepository formRepository, TemplatePDFRepository pdfRepository,
                                   TemplateGroupRepository tgRepository) {
        return args -> {
            if(formRepository.findAll().isEmpty()) {
                initFormRepository(formRepository, pdfRepository, tgRepository);

            }
        };
    }
}
