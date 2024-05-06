package de.PSWTM.DigitalForms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import de.PSWTM.DigitalForms.collection.Person;

@Repository
public interface PersonRepository extends MongoRepository<Person,String> {                  //Repository Stores Persons and the Primary Key is of Type String

    
}
