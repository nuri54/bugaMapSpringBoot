package de.hhn.se.labswp.buga23test.controller;

import de.hhn.se.labswp.buga23test.crudRepos.UserRepository;
import de.hhn.se.labswp.buga23test.jpa.User;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  private final UserRepository userRepository;

  @GetMapping("/users")
  public List<User> getUsers() {
    return (List<User>) userRepository.findAll();
  }

  @GetMapping("/admins")
  public List<User> getAdmins() {
    return userRepository.findByDiscriminatorLikeIgnoreCase("Admin");
  }

  @GetMapping("/usersByType")
  public List<User> getUsersByType(@RequestParam String type) {
    return userRepository.findByDiscriminatorLikeIgnoreCase(type);
  }

  @PostMapping("/users")
  public void addUser(@RequestBody User user) {
    userRepository.save(user);
  }



}
