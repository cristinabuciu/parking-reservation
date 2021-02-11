package com.acs.parking;

import com.acs.parking.model.RegisteredCarsEntity;
import com.acs.parking.model.UserEntity;
import com.acs.parking.model.UserRole;
import com.acs.parking.repository.RegisteredCarsRepository;
import com.acs.parking.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class InsertDataMain {

    private static final Logger log = LoggerFactory.getLogger(InsertDataMain.class);

    public static void main(String[] args) {
        SpringApplication.run(InsertDataMain.class);
    }

    @Bean
    public CommandLineRunner demo(UserRepository repository, RegisteredCarsRepository carsRepository,
                                  BCryptPasswordEncoder bCryptPasswordEncoder) {
        return (args) -> {
            // save a few customers
            Long adminUserId = repository.save(new UserEntity("Jack", bCryptPasswordEncoder.encode("Bauer"), UserRole.ADMIN, "name", "email1@domain.com", true)).getId();
            carsRepository.save(new RegisteredCarsEntity(adminUserId, "CT 13 SXI"));
            repository.save(new UserEntity("Chloe", bCryptPasswordEncoder.encode("O'Brian"), UserRole.USER, "name", "email2@domain.com", true));
            repository.save(new UserEntity("Kim", bCryptPasswordEncoder.encode("Bauer"), UserRole.USER, "name", "email3@domain.com", true));
            repository.save(new UserEntity("David", bCryptPasswordEncoder.encode("Palmer"), UserRole.USER, "name", "email4@domain.com", true));
            repository.save(new UserEntity("Michelle", bCryptPasswordEncoder.encode("Dessler"), UserRole.USER, "name", "email5@domain.com", true));

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (UserEntity user : repository.findAll()) {
                log.info(user.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            UserEntity user = repository.findById(1L).get();
            log.info("UserEntity found with findById(1L):");
            log.info("--------------------------------");
            log.info(user.getUsername());
            log.info("");

            // fetch customers by last name
            log.info("UserEntity found with findByUsername('Bauer'):");
            log.info("--------------------------------------------");
            log.info(repository.findByUsername("Jack").toString());
            // for (Customer bauer : repository.findByLastName("Bauer")) {
            //  log.info(bauer.toString());
            // }
            log.info("");
        };
    }

}
