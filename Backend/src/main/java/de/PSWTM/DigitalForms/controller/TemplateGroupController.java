package de.PSWTM.DigitalForms.controller;

import de.PSWTM.DigitalForms.api.GroupsApiDelegate;
import de.PSWTM.DigitalForms.model.TemplateGroup;
import de.PSWTM.DigitalForms.repository.TemplateGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.List;


@Component
public class TemplateGroupController implements GroupsApiDelegate {

    @Autowired
    private TemplateGroupRepository repository;

    @Override
    public ResponseEntity<List<TemplateGroup>> groupsGet() {
        return ResponseEntity.ok(repository.findAll());
    }
}
