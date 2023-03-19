package de.hhn.se.labswp.buga23test;

import de.hhn.se.labswp.buga23test.crudRepos.UserRepository;
import de.hhn.se.labswp.buga23test.jpa.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WsBuga23TestApplication {

  public static void main(String[] args) {
    SpringApplication.run(WsBuga23TestApplication.class, args);
  }

}
