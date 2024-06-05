package de.PSWTM.DigitalForms.repository;

import de.PSWTM.DigitalForms.model.Favourite;
import de.PSWTM.DigitalForms.model.Form;
import de.PSWTM.DigitalForms.model.UserData;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends MongoRepository<UserData ,String> {

        @Query(value = "{'owner':  ?0}")
        UserData findOwnUserData(String user);

        @Query(value = "{'owner':  ?0}")
        UserData findDuplicates(String user);
    
}
