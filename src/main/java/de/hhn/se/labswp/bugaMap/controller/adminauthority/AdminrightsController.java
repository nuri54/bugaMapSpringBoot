package de.hhn.se.labswp.bugaMap.controller.adminauthority;

import de.hhn.se.labswp.bugaMap.crudRepos.AdminRepository;
import de.hhn.se.labswp.bugaMap.jpa.Admin;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/api/v1/admin")
@RequiredArgsConstructor
public class AdminrightsController {

  private final AdminRepository adminRepository;
  @GetMapping("/users")
  public List<Admin> getUsers() {
    return (List<Admin>) adminRepository.findAll();
  }


}
