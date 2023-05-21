package de.hhn.se.labswp.bugamap.controller.adminauthority;

import de.hhn.se.labswp.bugamap.crudrepos.AdminRepository;
import de.hhn.se.labswp.bugamap.crudrepos.BugapointRepository;
import de.hhn.se.labswp.bugamap.jpa.Admin;
import de.hhn.se.labswp.bugamap.jpa.Bugapoint;
import de.hhn.se.labswp.bugamap.requests.BugapointRequest;
import de.hhn.se.labswp.bugamap.responses.DatabaseSaveResponse;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.buf.UEncoder;
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
        .adminID(request.getAdminID())
        .title(request.getTitle())
        .latitude(request.getLatitude())
        .longitude(request.getLongitude())
        .description(request.getDescription())
        .discriminator(request.getDiscriminator())
        .build();

    //Calculate Park ID: Over or under Longitude
    if (bugapoint.getLongitude() > 8.505825382916116) {
      bugapoint.setParkID(2);
    } else {
      bugapoint.setParkID(1);
    }

    try {
      bugapointRepository.save(bugapoint);
      logger.info("New bugapoint saved: " + bugapoint);
    } catch (Exception e) {
      logger.info("Something failed to save a bugapoint.");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
          new DatabaseSaveResponse(false, e.getCause().getMessage()));
    }

    logger.info("Bugapoint saved to the database: " + bugapoint);

    return ResponseEntity.ok(new DatabaseSaveResponse(true, "Bugapoint saved"));
  }


  /**
   * Adds a bugapoint to the database.
   *
   * @param parkId        identifier of the park
   * @param adminId       identifier of the admin
   * @param title         title
   * @param latitude      latitude
   * @param longitude     longitude
   * @param discriminator discriminator
   * @param description   description
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
   * @param bugaPointId id of the bugapoint which gets update
   * @param request values of the new bugapoint
   * @return Response
   *
   */
  @PutMapping("/update")
  public ResponseEntity<DatabaseSaveResponse> updateBugapoint(
      @RequestParam(value = "bugaPointId") int bugaPointId,
      @RequestBody BugapointRequest request) {

    ArrayList<String> succeeded = new ArrayList<>();
    ArrayList<String> failed = new ArrayList<>();

    Optional<Bugapoint> bugapoint = bugapointRepository.findById(bugaPointId);
    if (bugapoint.isEmpty()) {
      logger.info("Tried to update a bugapoint with the id " + bugaPointId
          + ". Error: No bugapoint found.");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DatabaseSaveResponse(
          false, "Bugapoint (id = " + bugaPointId + ")not found"));
    }

    StringBuilder responseText = new StringBuilder("Updated bugapoint (id = " + bugaPointId + "): ");


    //Admin
    if (request.getAdminID() != null) {
      try {
        bugapointRepository.updateAdminIDById(request.getAdminID(), bugaPointId);
        responseText.append("AdminID changed to: ")
            .append(request.getAdminID()).append(", ");
        succeeded.add("admin");
      } catch (Exception e) {
        responseText.append("AdminID unable to changed to: ")
            .append(request.getAdminID()).append(", ");
        failed.add("admin");
      }
    }

    //Description
    if (request.getDescription() != null) {
      try {
        bugapointRepository.updateDescriptionById(request.getDescription(), bugaPointId);
        responseText.append("description changed to: \"").append(request.getDescription())
            .append("\", ");
        succeeded.add("description");
      } catch (Exception e) {
        responseText.append("description unable to changed to: \"").append(request.getDescription())
            .append("\", ");
        failed.add("description");
      }
    }

    //Latitude
    if (request.getLatitude() != null) {

      try {
        bugapointRepository.updateLatitudeById(request.getLatitude(), bugaPointId);
        responseText.append("latitude changed to: ").append(request.getLatitude())
            .append(", ");
        succeeded.add("latitude");
      } catch (Exception e) {
        responseText.append("latitude unable to changed to: ").append(request.getLatitude())
            .append(", ");
        failed.add("latitude");
      }
    }

    //Longitude
    if (request.getLongitude() != null) {
      try {
        bugapointRepository.updateLongitudeById(request.getLongitude(), bugaPointId);
        responseText.append("longitude changed to: ").append(request.getLongitude())
            .append(", ");
        succeeded.add("longitude");
      } catch (Exception e ) {
        responseText.append("longitude unable to changed to: ").append(request.getLongitude())
            .append(", ");
        failed.add("longitude");
      }
    }

    //Discriminator
    if (request.getDiscriminator() != null) {
      try {
        bugapointRepository.updateDiscriminatorById(request.getDiscriminator(), bugaPointId);
        responseText.append("discriminator changed to: \"").append(request.getDiscriminator())
            .append("\", ");
        succeeded.add("discriminator");
      } catch (Exception e) {
        responseText.append("discriminator unable to changed to: \"").append(request.getDiscriminator())
            .append("\", ");
        failed.add("discriminator");
      }

    }

    responseText.delete(responseText.length() - 2,responseText.length());
    logger.info(responseText);

    DatabaseSaveResponse successResponse = new DatabaseSaveResponse(true, responseText.toString());
    successResponse.setSucceeded(succeeded);
    successResponse.setFailed(failed);

    return ResponseEntity.ok(successResponse);
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
