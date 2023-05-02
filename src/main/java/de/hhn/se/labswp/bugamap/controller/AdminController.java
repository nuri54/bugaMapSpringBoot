package de.hhn.se.labswp.bugamap.controller;

import de.hhn.se.labswp.bugamap.crudrepos.AdminRepository;
import de.hhn.se.labswp.bugamap.jpa.Admin;
import de.hhn.se.labswp.bugamap.responses.DatabaseSaveResponse;
import java.util.List;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Mappings for Admin table.
 */
@RestController()
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

  private final AdminRepository adminRepository;

  private final JdbcTemplate jdbcTemplate;

  public AdminController(AdminRepository adminRepository, JdbcTemplate jdbcTemplate) {
    this.adminRepository = adminRepository;
    this.jdbcTemplate = jdbcTemplate;
  }


  /**
   * Standard request to get all admins. Notice: Passwords get blacken.
   *
   * @return All admins.
   */
  @GetMapping("/list")
  public List<Admin> getAdmins() {
    List<Admin> admins = (List<Admin>) adminRepository.findAll();

    // Hide passwords
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
   * @return response if saving to the database was successful or not.
   */
  @PostMapping(value = "/save")
  public ResponseEntity<DatabaseSaveResponse> add(@RequestBody Admin admin) {
    try {
      adminRepository.save(admin);
      return ResponseEntity.ok(new DatabaseSaveResponse(true, "Admin saved"));
    } catch (Exception e) {

      // Return special message if mail address is already in use
      if (e.getCause() instanceof ConstraintViolationException) {
        if (e.getCause().getCause().getMessage().contains("Duplicate")
            && e.getCause().getCause().getMessage().contains("Emailadress")) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new DatabaseSaveResponse(false, "Emailadress duplicated"));
        }
      }

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new DatabaseSaveResponse(false, e.getMessage()));
    }
  }


  /**
   * Returns the admin with given id.
   *
   * @param id ID of the admin
   * @return admin
   */
  @GetMapping(value = "id/{id}")
  public Admin getById(@PathVariable int id) {
    return jdbcTemplate.queryForObject("SELECT * FROM admin WHERE id = ?",
        new BeanPropertyRowMapper<>(Admin.class), id);
  }

  /**
   * Returns an admin over the given email address.
   *
   * @return admin
   */
  @GetMapping(value = "emailaddress/{emailaddress}")
  public Admin getByEmailAdress(@PathVariable String emailaddress) {
    return jdbcTemplate.queryForObject("SELECT * FROM admin WHERE emailadress = ?",
        new BeanPropertyRowMapper<>(Admin.class), emailaddress);
  }


}
