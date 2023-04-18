package de.hhn.se.labswp.bugaMap.controller;

import de.hhn.se.labswp.bugaMap.crudRepos.AdminRepository;
import de.hhn.se.labswp.bugaMap.jpa.Admin;
import de.hhn.se.labswp.bugaMap.responses.StandardResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

  public AdminController(AdminRepository adminRepository) {
    this.adminRepository = adminRepository;
  }

  private final AdminRepository adminRepository;

  @GetMapping("/list")
  public List<Admin> getAdmins() {
    List<Admin> admins = (List<Admin>) adminRepository.findAll();

    for (Admin admin :
        admins) {
      admin.setPassword("[HIDDEN]");
    }

    return admins;
  }


  /**
   * POST Request to save one admin.
   *
   * @param admin Admin which will be put into the database.
   */
  @PostMapping("/add")
  public ResponseEntity<StandardResponse> addAdmin(@RequestBody Admin admin) {
    try {
      adminRepository.save(admin);
      return ResponseEntity.ok(new StandardResponse(true, "Admin saved"));
    } catch (Exception e) {
      return ResponseEntity.status(new StandardResponse(false, "Request failed"));
    }
  }



}
