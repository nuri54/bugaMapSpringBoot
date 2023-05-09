package de.hhn.se.labswp.bugamap.controller.adminauthority;

import de.hhn.se.labswp.bugamap.crudrepos.AdminRepository;
import de.hhn.se.labswp.bugamap.crudrepos.BugapointRepository;
import de.hhn.se.labswp.bugamap.jpa.Admin;
import de.hhn.se.labswp.bugamap.jpa.Bugapoint;
import de.hhn.se.labswp.bugamap.requests.BugapointRequest;
import de.hhn.se.labswp.bugamap.responses.DatabaseSaveResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Mappings with Admin access for Admin table.
 */
@RestController()
@RequestMapping("/api/v1/admin/bugapoint")
public class AdminBugapointController {

  private static final Logger logger = LogManager.getLogger(AdminBugapointController.class);

  private final BugapointRepository bugapointRepository;

  private final AdminRepository adminRepository;

  private final JdbcTemplate jdbcTemplate;

  public AdminBugapointController(BugapointRepository bugapointRepository,
      AdminRepository adminRepository, JdbcTemplate jdbcTemplate) {
    this.bugapointRepository = bugapointRepository;
    this.adminRepository = adminRepository;
    this.jdbcTemplate = jdbcTemplate;
  }


  /**
   * Simple request to get all points in the database.
   *
   * @return bugapoints all bugapoints
   */
  @GetMapping("/list")
  public List<Bugapoint> getBugapoints() {
    return (List<Bugapoint>) bugapointRepository.findAll();
  }

  /**
   * Returns all bugapoints with the matching discriminators.
   *
   * @param discriminators discriminators
   * @return filtered bugapoints
   */
  @GetMapping("/list/filter")
  public List<Bugapoint> getBugapoints(
      @RequestParam(value = "discriminators", required = false) List<String> discriminators) {

    if (discriminators == null || discriminators.isEmpty()) {
      return new ArrayList<>();
    }

    String sql = "SELECT * FROM bugapoint WHERE Discriminator IN ("
        + String.join(",", Collections.nCopies(discriminators.size(), "?")) + ")";
    return jdbcTemplate.query(sql, discriminators.toArray(),
        new BeanPropertyRowMapper<>(Bugapoint.class));
  }

  /**
   * Adds the given bugapoint to the database.
   */
  @PostMapping("/save")
  public ResponseEntity<DatabaseSaveResponse> save(@RequestBody BugapointRequest request) {

    Bugapoint bugapoint = Bugapoint.builder()
        .parkID(request.getParkID())
        .adminID(request.getAdminID())
        .title(request.getTitle())
        .latitude(request.getLatitude())
        .longitude(request.getLongitude())
        .description(request.getDescription())
        .discriminator(request.getDiscriminator())
        .build();

    try {
      bugapointRepository.save(bugapoint);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
          new DatabaseSaveResponse(false, e.getCause().getCause().getMessage()));
    }

    logger.info("Bugapoint saved to the database: " + bugapoint);

    return ResponseEntity.ok(new DatabaseSaveResponse(true, "Bugapoint saved"));
  }


  /**
   * Adds a bugapoint to the database.
   *
   * @param parkId identifier of the park
   * @param adminId identifier of the admin
   * @param title title
   * @param latitude latitude
   * @param longitude longitude
   * @param discriminator discriminator
   * @param description description
   *
   * @return Response if the Request was successful or not.
   */
  @PostMapping("/add")
  public ResponseEntity<DatabaseSaveResponse> add(@RequestParam(value = "parkId") int parkId,
      @RequestParam(value = "adminId") int adminId, @RequestParam(value = "title") String title,
      @RequestParam(value = "latitude") double latitude,
      @RequestParam(value = "longitude") double longitude,
      @RequestParam(value = "discriminator") String discriminator,
      @RequestParam(value = "description") String description) {

    try {
      Bugapoint bugapoint = Bugapoint.builder()
          .parkID(parkId)
          .adminID(adminId)
          .title(title)
          .latitude(latitude)
          .longitude(longitude)
          .discriminator(discriminator)
          .description(description)
          .build();

      bugapointRepository.save(bugapoint);

      logger.info("Bugapoint saved to the database: " + bugapoint);
      return ResponseEntity.ok(new DatabaseSaveResponse(true, "saved"));
    } catch (Exception e) {
      logger.info("Bugapoint failed to saved to the database");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new DatabaseSaveResponse(false, e.getCause().getMessage()));
    }

  }


  /**
   * Update Bugapoint method.
   *
   * @param bugaPointId    id of the bugapoint which gets updated
   * @param newLat         new latitude
   * @param newLong        new longitude
   * @param newDescription new description
   * @param newAdminEmailaddress     new admin id
   * @return Response
   */
  @PutMapping("/update")
  public ResponseEntity<DatabaseSaveResponse> updateBugapoint(
      @RequestParam(value = "bugaPointId") int bugaPointId,
      @RequestParam(value = "newLat") double newLat,
      @RequestParam(value = "newLong") double newLong,
      @RequestParam(value = "newDescription") String newDescription,
      @RequestParam(value = "newAdminEmailaddress") String newAdminEmailaddress) {

    //AdminEmail in id
    Optional<Admin> newAdmin = adminRepository.findByEmailadress(newAdminEmailaddress);

    if (newAdmin.isEmpty()) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new DatabaseSaveResponse(false, "Admin not found"));
    }

    if (bugapointRepository.updateLatitudeAndLongitudeAndDescriptionAndAdminIDByIdIn(newLat,
        newLong,
        newDescription.trim(), newAdmin.get().getId(), bugaPointId) == 1) {
      logger.info("Updated bugapoint with id = " + bugaPointId);
      return ResponseEntity.ok(new DatabaseSaveResponse(true, "Updated"));
    }
    logger.info("Failed to updated bugapoint with id = " + bugaPointId);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new DatabaseSaveResponse(false, "Failed"));
  }

  /**
   * Delete bugapoint with the given id.
   *
   * @param id identifier
   */
  @DeleteMapping("/delete")
  public ResponseEntity<DatabaseSaveResponse> deleteById(@RequestParam(value = "id") int id) {
    try {
      bugapointRepository.deleteById(id);
      logger.info("Deleted bugapoint with id = " + id);
      return ResponseEntity.ok(new DatabaseSaveResponse(true, "Bugapoint deleted"));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new DatabaseSaveResponse(false, "Failed"));
    }
  }
}
