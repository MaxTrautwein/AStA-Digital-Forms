package de.PSWTM.DigitalForms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

import de.PSWTM.DigitalForms.collection.Person;
import de.PSWTM.DigitalForms.service.PersonService;

/* 
@RestController
@RequestMapping(value = "/person", method = RequestMethod.POST)
public class PersonController {

    @Autowired
    private PersonService personService;
    private Person person = Person.builder().build();
    

    @PostMapping
    public String save() {
        person.setFirstName("My");
        person.setSecondName("Self");
        person.setPersonId("42");
        return personService.save(person);
    }
}

*/