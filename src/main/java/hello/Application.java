package hello;

import hello.backend.ContactPerson;
import hello.backend.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner loadData(CustomerRepository repository) {
        return (args) -> {
            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (ContactPerson contactPerson : repository.findAll()) {
                log.info(contactPerson.toString());
            }
            log.info("");

            // fetch an individual contactPerson by ID
            ContactPerson contactPerson = repository.findById(1L).get();
            log.info("ContactPerson found with findOne(1L):");
            log.info("--------------------------------");
            log.info(contactPerson.toString());
            log.info("");

            // fetch customers by last name
            log.info("ContactPerson found with findByLastNameStartsWithIgnoreCase('Bauer'):");
            log.info("--------------------------------------------");
            for (ContactPerson bauer : repository
                    .findByLastNameStartsWithIgnoreCase("Bauer")) {
                log.info(bauer.toString());
            }
            log.info("");
        };
    }

}
