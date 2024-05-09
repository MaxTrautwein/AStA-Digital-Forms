package de.PSWTM.DigitalForms.repository;

import de.PSWTM.DigitalForms.collection.Form;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FormRepository extends MongoRepository<Form ,String> {

}
