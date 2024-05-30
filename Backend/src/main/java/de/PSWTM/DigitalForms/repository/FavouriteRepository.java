package de.PSWTM.DigitalForms.repository;

import de.PSWTM.DigitalForms.model.Favourite;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouriteRepository extends MongoRepository<Favourite, String> {
}
