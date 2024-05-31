package de.PSWTM.DigitalForms.controller;

import de.PSWTM.DigitalForms.Model.TemplatePDF;
import de.PSWTM.DigitalForms.Services.PdfService;
import de.PSWTM.DigitalForms.api.FormsApiDelegate;
import de.PSWTM.DigitalForms.model.Form;
import de.PSWTM.DigitalForms.repository.FormRepository;
import de.PSWTM.DigitalForms.repository.TemplatePDFRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Objects;

@Component
public class FormsController implements FormsApiDelegate {
    private static final Logger log = LoggerFactory.getLogger(FormsController.class);

    private final PdfService pdfService;

    @Autowired
    private FormRepository repository;

    @Autowired
    private TemplatePDFRepository pdfRepository;

    Logger logger = LoggerFactory.getLogger(FormsController.class);

    public FormsController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    // Gets the KeyCloak UserID from the Token
    private String getUserID(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public ResponseEntity<Void> formsFormIDDelete(String formID) {
        Form form = repository.findOwnedFormById(getUserID(), formID);
        if (form == null) {
            return ResponseEntity.notFound().build();
        }else {
            repository.delete(form);
            return ResponseEntity.ok().build();
        }
    }

    @Override
    public ResponseEntity<Resource> formsFormIDDownloadGet(String formID) {
        Form form = repository.findOwnedFormById(getUserID(), formID);
        if (form == null) {
            return ResponseEntity.badRequest().build();
        }
        TemplatePDF templatePDF = pdfRepository.findByFormId(form.getParent());
        if (templatePDF == null){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        }

        try {
            byte[] pdfBytes = pdfService.generatePdf(form,templatePDF.getTemplatePdf());
            ByteArrayResource resource = new ByteArrayResource(pdfBytes);

            return ResponseEntity
                    .ok()
                    .contentLength(pdfBytes.length)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body(null);
        }

    }

    @Override
    public ResponseEntity<Form> formsFormIDGet(String formID) {
        Form form = repository.findOwnedFormById(getUserID(), formID);
        if (form == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(form);
    }

    @Override
    public ResponseEntity<Form> formsFormIDPut(String formID, Form form) {
        if (form == null) {
            return ResponseEntity.badRequest().build();
        }
        if(repository.existsById(form.getId())){
            // ID exists Already Check if we can Update
            if(!Objects.equals(repository.findById(form.getId()).get().getOwner(), getUserID())){
                return ResponseEntity.badRequest().build(); // Not ours
            }
        }else {
            // Can't Create new
            return ResponseEntity.badRequest().build();
        }

        form.setTemplate(false); // Can't create Template
        Form created = repository.save(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Override
    public ResponseEntity<List<Form>> formsGet() {
        List<Form> forms = repository.findAllOwnedForm(getUserID());
        if (forms == null || forms.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(forms);
    }

    @Override
    public ResponseEntity<Form> formsPost(Form form) {
        if (form == null) {
            return ResponseEntity.badRequest().build();
        }
        if(repository.existsById(form.getId()) && !form.getTemplate()){
            // ID exists Already Check if we can Update
            if(!Objects.equals(repository.findById(form.getId()).get().getOwner(), getUserID())){
                return ResponseEntity.badRequest().build(); // Not ours
            }
        }else {
            // New Form
            form.parent(form.getId());
            form.setId(null); // ID is created by the DB
            form.setOwner(getUserID());
        }

        form.setTemplate(false); // Can't create Template
        Form created = repository.save(form);
        return ResponseEntity.ok(created);
    }


}
