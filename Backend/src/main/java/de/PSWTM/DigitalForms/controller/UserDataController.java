package de.PSWTM.DigitalForms.controller;

import de.PSWTM.DigitalForms.api.UserDataApiDelegate;
import de.PSWTM.DigitalForms.model.UserData;
import de.PSWTM.DigitalForms.repository.UserDataRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserDataController implements UserDataApiDelegate {

    @Autowired
    private UserDataRepository repository;

    private String getUserID(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


    @Override
    public ResponseEntity<UserData> userDataGet() {
        UserData ud = repository.findOwnUserData(getUserID());
        return ResponseEntity.ok(ud);
    }

    @Override
    public ResponseEntity<UserData> userDataPost(UserData userData) {
        if(!repository.existsById(getUserID())) {
            userData.setUserId(getUserID());
            repository.save(userData);
            return ResponseEntity.status(HttpStatus.CREATED).body(userData);
        } else {
            repository.deleteById(getUserID());
            repository.save(userData);
            return ResponseEntity.status(HttpStatus.CREATED).body(userData);
        }
    }
}
