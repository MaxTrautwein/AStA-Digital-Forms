package de.PSWTM.DigitalForms.configuration;

import de.PSWTM.DigitalForms.collection.ECategory;
import de.PSWTM.DigitalForms.collection.FormSection;
import de.PSWTM.DigitalForms.repository.FormRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import de.PSWTM.DigitalForms.collection.Form;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DatabaseLoader {
    @Bean
    CommandLineRunner initDatabase(FormRepository userRepository) {
        return args -> {
            if(true) {

                Form f1 = new Form(true,
                        "Genehmigung von Ausgaben und Anschaffungen",
                        "bla Genehmigung von zeugs");
                f1.setCategory(ECategory.Antrag);

                List<FormSection> sections = new ArrayList<>();

                FormSection s1 = new FormSection();
                s1.setOrder(0);
                s1.setSection("Generell");

                FormSection s2 = new FormSection();
                s2.setOrder(1);
                s2.setSection("begründung");

                sections.add(s1);
                sections.add(s2);

                f1.setForm(sections);


                userRepository.save(f1);


            }
        };
    }
}
