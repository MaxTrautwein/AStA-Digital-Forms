package de.PSWTM.DigitalForms;

import org.springframework.data.annotation.Id;

public class Person {

    @Id
    public String id;

    public String name;

    public Person(String name) {
        this.name = name;
    }
}
