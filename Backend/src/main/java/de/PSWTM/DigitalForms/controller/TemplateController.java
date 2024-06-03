package de.PSWTM.DigitalForms.controller;

import de.PSWTM.DigitalForms.api.TemplatesApiDelegate;
import de.PSWTM.DigitalForms.model.Form;
import de.PSWTM.DigitalForms.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.List;

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

        if (res == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(res);    }
}
