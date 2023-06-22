package de.hhn.se.labswp.bugamap.controller.managementauthority;


import de.hhn.se.labswp.bugamap.controller.adminauthority.requestbodies.PersonDensityReportRequest;
import de.hhn.se.labswp.bugamap.crudrepos.PersondensityReportRepository;
import de.hhn.se.labswp.bugamap.jpa.Persondensityreport;
import de.hhn.se.labswp.bugamap.responses.DatabaseSaveResponse;
import java.time.Instant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Mappings for the person density reports.
 */
@RestController()
@RequestMapping("/api/v1/management/pdr")
public class AdminPersondensityReportController {

  private static final Logger logger = LogManager.getLogger(AdminPersondensityReportController.class);

  private final PersondensityReportRepository persondensityReportRepository;


  public AdminPersondensityReportController(
      PersondensityReportRepository persondensityReportRepository) {
    this.persondensityReportRepository = persondensityReportRepository;
  }

  /**
   * Save mapping for a new report.
   *
   * @param personDensityReportRequest report
   * @return response
   */
  @PostMapping("/save")
  public ResponseEntity<DatabaseSaveResponse> save(@RequestBody PersonDensityReportRequest
      personDensityReportRequest) {

    try {

      Persondensityreport persondensityreport = new Persondensityreport();

      persondensityreport.setLatitude(personDensityReportRequest.getLatitude());
      persondensityreport.setLongitude(personDensityReportRequest.getLongitude());
      persondensityreport.setDensity(personDensityReportRequest.getDensity());
      persondensityreport.setValidtill(Instant.now().plusSeconds(
          personDensityReportRequest.getDuration()));

      logger.info(Instant.now());

      Persondensityreport saved = persondensityReportRepository.save(persondensityreport);
      logger.info("New persondensityreport saved (id = " + saved.getId() + ")");

      return ResponseEntity.ok(
          new DatabaseSaveResponse(true, "Persondensityreport saved."));
    } catch (Exception e) {
      logger.info(e.getMessage());
      logger.info("Failed to save new persondensityreport.");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
          new DatabaseSaveResponse(false, "Persondensityreport not saved."));
    }
  }

  /**
   * Delete mapping.
   *
   * @param id identifier
   * @return response
   */
  @DeleteMapping("/delete")
  public ResponseEntity<DatabaseSaveResponse> delete(@RequestParam(value = "id") Integer id) {
    try {
      persondensityReportRepository.deleteById(id);
      logger.info("Deleted persondensityreport. (id = " + id + ")");
      return ResponseEntity.ok(
          new DatabaseSaveResponse(true, "Report with the id \""
              + id  + "\" was deleted."));
    } catch (Exception e) {
      logger.info("Failed to delete persondensityreport. (id = " + id + ")");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
          new DatabaseSaveResponse(false, "Unable to delete report with the id \""
              + id + "\""));
    }
  }


}
