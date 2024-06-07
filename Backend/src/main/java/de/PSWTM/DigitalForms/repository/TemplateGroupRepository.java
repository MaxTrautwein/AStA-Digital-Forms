package de.PSWTM.DigitalForms.repository;

import de.PSWTM.DigitalForms.model.TemplateGroup;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TemplateGroupRepository  extends MongoRepository<TemplateGroup, String> {

}
