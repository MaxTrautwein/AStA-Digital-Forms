package de.PSWTM.DigitalForms.controller;

import de.PSWTM.DigitalForms.collection.Form;
import de.PSWTM.DigitalForms.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FormController {
    @Autowired
    private FormRepository repository;

    @GetMapping("/form/{id}")
    public Form getFormByID(@PathVariable String id){
        return repository.findById(id).orElse(null);
    }

    @GetMapping("/getTemplates")
    public List<Form> getTemplates(){
        return repository.findAllTemplates_IdNameDescription();
    }

    //Used to allow for a regenerate on next Start
    @GetMapping("/debug/clear")
    public String debugClear(){
        repository.deleteAll();
        return "deleteAll";
    }

}
