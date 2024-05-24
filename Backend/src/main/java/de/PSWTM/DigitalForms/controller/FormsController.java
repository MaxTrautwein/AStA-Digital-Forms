package de.PSWTM.DigitalForms.controller;

import de.PSWTM.DigitalForms.api.FormsApiDelegate;
import de.PSWTM.DigitalForms.model.Form;
import de.PSWTM.DigitalForms.repository.FormRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class FormsController implements FormsApiDelegate {
    private static final Logger log = LoggerFactory.getLogger(FormsController.class);

    @Autowired
    private FormRepository repository;

    Logger logger = LoggerFactory.getLogger(FormsController.class);

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
        if(repository.existsById(form.getId())){
            // ID exists Already Check if we can Update
            if(!Objects.equals(repository.findById(form.getId()).get().getOwner(), getUserID())){
                return ResponseEntity.badRequest().build(); // Not ours
            }
        }else {
            // New Form
            form.setId(null); // ID is created by the DB
            form.setOwner(getUserID());
        }

        form.setTemplate(false); // Can't create Template
        Form created = repository.save(form);
        return ResponseEntity.ok(created);
    }


}
