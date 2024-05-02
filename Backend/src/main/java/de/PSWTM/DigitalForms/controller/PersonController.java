package de.PSWTM.DigitalForms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.PSWTM.DigitalForms.collection.Person;
import de.PSWTM.DigitalForms.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    public String save(@RequestBody Person person) {
        return personService.save(person);
    }
}
