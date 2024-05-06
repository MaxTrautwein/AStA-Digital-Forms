package de.PSWTM.DigitalForms;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.PSWTM.DigitalForms.collection.Person;
import de.PSWTM.DigitalForms.repository.PersonRepository;



@RestController
public class HelloWorldController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();



    @Autowired
    private PersonRepository repository;

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
/* 
    @GetMapping("/test")
    public String test()  {
        repository.save(new Person("My"));
        repository.save(new Person("Your"));
        return "test";
    }

    @GetMapping("/get")
    public String getDb()  {
        return  repository.findAll().toString();
    }
    */
}