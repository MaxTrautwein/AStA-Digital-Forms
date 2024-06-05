package de.PSWTM.DigitalForms.repository;


import de.PSWTM.DigitalForms.model.UserData;



import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends MongoRepository<UserData ,String> {

        @Query(value = "{'userId':  ?0}")
        UserData findOwnUserData(String user);

        @Query(value = "{'userId':  ?0}")
        UserData findDuplicates(String user);
    
}
