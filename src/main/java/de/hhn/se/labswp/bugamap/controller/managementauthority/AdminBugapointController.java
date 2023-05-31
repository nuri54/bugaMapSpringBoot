package de.hhn.se.labswp.bugamap.controller.managementauthority;

import de.hhn.se.labswp.bugamap.crudrepos.BugapointRepository;
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
@RequestMapping("/api/v1/management/bugapoint")
public class AdminBugapointController {

  private static final Logger logger = LogManager.getLogger(AdminBugapointController.class);

  private final BugapointRepository bugapointRepository;

  private final JdbcTemplate jdbcTemplate;

  double[] southWestMannheim = new double[]{49.562104830601314, 8.36095242436513};
  double[] northEastMannheim = new double[]{49.40726086087864, 8.619292747892453};

  public AdminBugapointController(BugapointRepository bugapointRepository,
      JdbcTemplate jdbcTemplate) {
    this.bugapointRepository = bugapointRepository;
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
    logger.info(request);
    Bugapoint bugapoint;
    try {
      bugapoint = Bugapoint.builder()
          .adminID(request.getAdminID())
          .title(request.getTitle())
          .latitude(request.getLatitude())
          .longitude(request.getLongitude())
          .description(request.getDescription())
          .discriminator(request.getDiscriminator())
          .iconname(request.getIconname())
          .build();
    } catch (Exception e) {
      logger.info("Something failed to save a bugapoint. Sent values are faulty.");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
          new DatabaseSaveResponse(false, "Sent values are faulty."));
    }

    if (!checkIfCoordsInMannheim(bugapoint.getLatitude(), bugapoint.getLongitude())) {
      logger.info("New bugapoint was not saved: Not in Mannheim");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
          new DatabaseSaveResponse(false, "Coordinates not in Mannheim."));
    }


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
   * Update Bugapoint method.
   *
   * @param bugaPointId id of the bugapoint which gets update
   * @param request     values of the new bugapoint
   * @return Response
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

    StringBuilder responseText = new StringBuilder(
        "Updated bugapoint (id = " + bugaPointId + "): ");

    //Admin
    if (request.getAdminID() != null && request.getAdminID() != bugapoint.get()
        .getAdminID()) { // Check if exists AND new value
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
    if (request.getDescription() != null &&
        !request.getDescription().equals(bugapoint.get().getDescription())) {
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
    if (request.getLatitude() != null && !request.getLatitude()
        .equals(bugapoint.get().getLatitude())) {

      if (checkIfCoordsInMannheim(request.getLatitude(), southWestMannheim[1])) {
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
      } else {
        responseText.append("latitude unable to changed to: ").append(request.getLatitude())
            .append(", ");
        failed.add("latitude");
      }


    }

    //Longitude
    if (request.getLongitude() != null &&
        !request.getLongitude().equals(bugapoint.get().getLongitude())) {

      if (checkIfCoordsInMannheim(southWestMannheim[0], request.getLongitude())) {
        try {
          bugapointRepository.updateLongitudeById(request.getLongitude(), bugaPointId);
          responseText.append("longitude changed to: ").append(request.getLongitude());

          //ParkID
          if (request.getLongitude() > 8.505825382916116) {
            bugapointRepository.updateParkIDById(2, bugaPointId);
            responseText.append(" (parkId = ").append(2);
          } else {
            bugapointRepository.updateParkIDById(1, bugaPointId);
            responseText.append(" (parkId = ").append(1);
          }

          responseText.append(", ");

          succeeded.add("longitude");
        } catch (Exception e) {
          responseText.append("longitude unable to changed to: ").append(request.getLongitude())
              .append(", ");
          failed.add("longitude");
        }
      } else {
        responseText.append("longitude unable to changed to: ").append(request.getLongitude())
            .append(", ");
        failed.add("longitude");
      }
    }

    //Discriminator
    if (request.getDiscriminator() != null &&
        !request.getDiscriminator().equals(bugapoint.get().getDiscriminator())) {
      try {
        bugapointRepository.updateDiscriminatorById(request.getDiscriminator(), bugaPointId);
        responseText.append("discriminator changed to: \"").append(request.getDiscriminator())
            .append("\", ");
        succeeded.add("discriminator");
      } catch (Exception e) {
        responseText.append("discriminator unable to changed to: \"")
            .append(request.getDiscriminator())
            .append("\", ");
        failed.add("discriminator");
      }

    }

    responseText.delete(responseText.length() - 2, responseText.length());
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


  /**
   * Sums up all different icon names currently in use.
   *
   * @return all icon names
   */
  @GetMapping("/iconnames")
  public List<String> getIconnames() {
    List<String> iconnames = jdbcTemplate.queryForList("SELECT DISTINCT iconname FROM bugapoint",
        String.class);
    iconnames.remove(null); //Remove null object
    return iconnames;
  }


  private boolean checkIfCoordsInMannheim(double lat, double lng) {

    double maxLat = southWestMannheim[0];
    double minLng = southWestMannheim[1];
    double minLat = northEastMannheim[0];
    double maxLng = northEastMannheim[1];

    // Überprüfe, ob die gegebenen Koordinaten innerhalb des begrenzten Bereichs liegen
    return lat >= minLat && lat <= maxLat && lng >= minLng && lng <= maxLng;
  }

}
