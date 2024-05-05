package de.PSWTM.DigitalForms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

import de.PSWTM.DigitalForms.collection.Person;
import de.PSWTM.DigitalForms.repository.PersonRepository;


@RestController
public class PersonController {

    @Autowired
    private PersonRepository repository;
    
    @GetMapping("/write")
    public String write()  {
        Person p1 = new Person("Alex");
        Person p2 = new Person("Brighitte");
        repository.save(p1);
        repository.save(p2);
        return "Die beiden Personen " + p1.name + " und " + p2.name + " wurden erfolgreich gespeichert";
    }

    @GetMapping("/get")
    public String getDb()  {
        return  repository.findAll().toString();
    }

    @GetMapping("/delete")
    public String del() {
        repository.deleteAll();
        return "All Person-Data has been deleted sucessfully";
    }
}


