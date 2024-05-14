package de.PSWTM.DigitalForms.controller;


import de.PSWTM.DigitalForms.api.TemplatesApiDelegate;
import de.PSWTM.DigitalForms.model.Form;
import de.PSWTM.DigitalForms.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;

@Component
public class TemplateController implements TemplatesApiDelegate {
    @Autowired
    private FormRepository repository;

    @Override
    public ResponseEntity<List<Form>> templatesGet() {
        return ResponseEntity.ok(repository.findAllTemplates_IdNameDescription());
    }

    @Override
    public ResponseEntity<Form> templatesTemplateIdGet(String templateId) {
        Form res = repository.findById(templateId).orElse(null);

        if (res == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(res);    }
}
