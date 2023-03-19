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

  @Bean
  public CommandLineRunner run(UserRepository reportRepository){
    return (args -> {
      insert(reportRepository);
      System.out.println(reportRepository.findAll());
    });
  }


  private void insert(UserRepository reportRepository){
    User user = new User();
    user.setAge(14);
    user.setDiscriminator("wuff");
    user.setFirstname("miau");
    user.setLastname("wau");
    user.setLongitude(4.20);
    user.setLatitude(6.9);
    user.setEMailAdress("tesssstt");

    reportRepository.save(user);
  }

}
