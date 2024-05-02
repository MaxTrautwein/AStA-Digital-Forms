package de.PSWTM.DigitalForms.collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "person")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person {
    @Id
    private String PersonId;
    private String FirstName;
    private String SecondName;
}
