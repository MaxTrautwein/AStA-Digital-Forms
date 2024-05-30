package de.PSWTM.DigitalForms.repository;

import de.PSWTM.DigitalForms.model.Favourite;
import de.PSWTM.DigitalForms.model.Form;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouriteRepository extends MongoRepository<Favourite, String> {

    @Query(value = "{'owner':  ?0}")
    ArrayList<Favourite> findAllOwnedFavouritess(String user);

    @Query(value = "{'owner':  ?0, 'formId': ?1}")
    ArrayList<Favourite> findDuplicates(String user, String FormId);
}
