package de.PSWTM.DigitalForms.repository;

import de.PSWTM.DigitalForms.Model.TemplatePDF;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplatePDFRepository extends MongoRepository<TemplatePDF, String> {

    @Query(value = "{'formId' : ?0}")
    public TemplatePDF findByFormId(String formId);
}
