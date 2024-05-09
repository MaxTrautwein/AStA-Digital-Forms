package de.PSWTM.DigitalForms.configuration;

import de.PSWTM.DigitalForms.collection.*;
import de.PSWTM.DigitalForms.repository.FormRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DatabaseLoader {
    @Bean
    CommandLineRunner initDatabase(FormRepository userRepository) {
        return args -> {
            if(true) {
                List<FormSection> sections = new ArrayList<>();

                sections.add(FormSection.builder()
                        .order(0)
                        .section("")
                        .items(new ArrayList<>())
                        .build());
                sections.get(sections.size() - 1).items.add(FormElement.builder()
                        .type(EFormElement.date)
                        .Description("asas")
                        .help("")
                        .id("ID")
                        .build());
                sections.get(sections.size() - 1).items.add(FormElement.builder()
                        .type(EFormElement.date)
                        .build());

                Form f1 = Form.builder().Template(true)
                        .Titel("Genehmigung von Ausgaben und Anschaffungen")
                        .Description("bla Genehmigung von zeugs")
                        .Category(ECategory.Antrag)
                        .form(sections)
                        .build();

                

                userRepository.save(f1);


            }
        };
    }
}
