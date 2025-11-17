// This file is resposible for preloading some test data into the DB every time the application starts

package com.example.travel_api;


import java.time.LocalDate; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(TravelRepository repository) {

    return args -> {
      log.info("Preloading " + repository.save(
        new Travel("Amsterdam", LocalDate.of(2026,8,22), "Holanda")));
      log.info("Preloading " + repository.save(
        new Travel("Rio de Janeiro", LocalDate.of(2026,2,20), "Brasil")));
    };
  }
}