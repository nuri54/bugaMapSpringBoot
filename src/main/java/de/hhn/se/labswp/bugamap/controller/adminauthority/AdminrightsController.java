package de.hhn.se.labswp.bugamap.controller.adminauthority;

import de.hhn.se.labswp.bugamap.crudrepos.AdminRepository;
import de.hhn.se.labswp.bugamap.jpa.Admin;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class represents the REST controller for accessing and manipulating admin users. It handles
 * requests related to the admin authority.
 */
@RestController
@RequestMapping(path = "/api/v1/admin")
@RequiredArgsConstructor
public class AdminrightsController {

  private final AdminRepository adminRepository;

  /**
   * Returns a list of all admin users stored in the database.
   *
   * @return a list of admin users
   */
  @GetMapping("/users")
  public List<Admin> getUsers() {
    return (List<Admin>) adminRepository.findAll();
  }


}
