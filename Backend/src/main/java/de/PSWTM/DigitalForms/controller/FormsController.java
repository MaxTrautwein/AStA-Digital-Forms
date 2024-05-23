package de.PSWTM.DigitalForms.controller;

import de.PSWTM.DigitalForms.api.FormsApiDelegate;
import de.PSWTM.DigitalForms.model.Form;
import de.PSWTM.DigitalForms.repository.FormRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;

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

        return FormsApiDelegate.super.formsFormIDDelete(formID);
    }



    @Override
    public ResponseEntity<Form> formsFormIDGet(String formID) {
        logger.info(getUserID());


        return FormsApiDelegate.super.formsFormIDGet(formID);
    }

    @Override
    public ResponseEntity<Form> formsFormIDPut(String formID) {
        return FormsApiDelegate.super.formsFormIDPut(formID);
    }


    @Override
    public ResponseEntity<List<Form>> formsGet() {
        logger.info(getUserID());
        return FormsApiDelegate.super.formsGet();
    }

    @Override
    public ResponseEntity<Form> formsPost() {
        return FormsApiDelegate.super.formsPost();
    }
}
