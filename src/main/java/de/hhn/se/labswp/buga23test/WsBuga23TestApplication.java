package de.hhn.se.labswp.buga23test;

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
    user.setDiscriminator("test");
    user.setFirstname("Nuri");
    user.setLastname("hi");
    user.setLongitude(5.42);
    user.setLatitude(2.31);
    user.setEMailAdress("test");

    reportRepository.save(user);
  }

}
