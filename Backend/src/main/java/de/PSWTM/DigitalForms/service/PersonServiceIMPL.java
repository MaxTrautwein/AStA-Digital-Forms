package de.PSWTM.DigitalForms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.PSWTM.DigitalForms.collection.Person;
import de.PSWTM.DigitalForms.repository.PersonRepository;

@Service
public class PersonServiceIMPL implements PersonService{

    @Autowired
    private PersonRepository personRepository;

    @Override
    public String save(Person person) {
        return personRepository.save(person).getPersonId();
    }
}
