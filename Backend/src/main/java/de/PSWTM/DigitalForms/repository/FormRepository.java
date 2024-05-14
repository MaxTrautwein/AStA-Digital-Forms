package de.PSWTM.DigitalForms.repository;

import de.PSWTM.DigitalForms.model.Form;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface FormRepository extends MongoRepository<Form ,String> {

    @Query(value = "{ 'template' : true }", fields = "{ '_id' : 1, 'titel' : 1 , 'description':  1, 'template':  1}")
    ArrayList<Form> findAllTemplates_IdNameDescription();
    

}
