package de.PSWTM.DigitalForms.controller;

import de.PSWTM.DigitalForms.model.Form;
import de.PSWTM.DigitalForms.configuration.DatabaseLoader;
import de.PSWTM.DigitalForms.repository.FormRepository;
import de.PSWTM.DigitalForms.repository.TemplateGroupRepository;
import de.PSWTM.DigitalForms.repository.TemplatePDFRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DebugController {
    @Autowired
    private FormRepository repository;

    @Autowired
    private TemplatePDFRepository repositoryPdf;

    @Autowired
    private TemplateGroupRepository repositoryGroup;

    //Used to allow for a regenerate Forms
    @GetMapping("/debug/reset")
    public String debugClear(){
        repository.deleteAll();

        DatabaseLoader.initFormRepository(repository,repositoryPdf,repositoryGroup);
        return "Reset Done";
    }

}
