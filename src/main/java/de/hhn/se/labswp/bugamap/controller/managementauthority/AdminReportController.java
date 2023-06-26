package de.hhn.se.labswp.bugamap.controller.managementauthority;

import de.hhn.se.labswp.bugamap.crudrepos.AdminRepository;
import de.hhn.se.labswp.bugamap.crudrepos.ReportRepository;
import de.hhn.se.labswp.bugamap.jpa.Report;
import de.hhn.se.labswp.bugamap.responses.DatabaseSaveResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/v1/management/report")
public class AdminReportController {

  private static final Logger logger = LogManager.getLogger(
      AdminPersondensityReportController.class);

  private final ReportRepository reportRepository;
  private final AdminRepository adminRepository;

  public AdminReportController(
      ReportRepository reportRepository, AdminRepository adminRepository) {
    this.reportRepository = reportRepository;
    this.adminRepository = adminRepository;
  }

  /**
   * @return all reports
   */
  @GetMapping("/list")
  public List<Report> findAll() {
    return reportRepository.findAll();
  }

  /**
   * Get report by id
   *
   * @param id of the report
   * @return report if exist
   */
  @GetMapping("/id")
  public Report findById(@RequestParam(name = "id") Integer id) {
    Report r =  reportRepository.findById(id).get();

    return r;
  }

  /**
   *
   *
   * @return all reports without assigned admin
   */
  @GetMapping("/orphanList")
  public List<Report> findAllByAdminNull() {
    return reportRepository.findAllByAdminEmailNull();
  }

  /**
   * DELETE? -MAX
   *
   * @param report
   * @return
   */
  @PostMapping("/save")
  public ResponseEntity<DatabaseSaveResponse> save(@RequestBody Report report) {
    try {
      Report saved = reportRepository.save(report);
      logger.info("Saved Report (id = " + saved.getId() + ")");
      return ResponseEntity.ok(
          new DatabaseSaveResponse(true, "Report saved."));
    } catch (Exception e) {
      logger.info(e.getMessage());
      logger.info("Failed to save Report.");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
          new DatabaseSaveResponse(false, "Report not saved."));
    }
  }

  /**
   * Post mapping to close reports.
   *
   * @param ID id of the report
   * @return response
   */
  @PostMapping("/closeReport")
  public ResponseEntity<DatabaseSaveResponse> closeReport(@RequestParam(value = "id") Integer ID) {

    try {
      Optional<Report> optionalReport = reportRepository.findById(ID);
      if (optionalReport.isPresent()) {
        Report reportClosed = optionalReport.get();
        reportClosed.setIsClosed(true);
        Report closed = reportRepository.save(reportClosed);
        logger.info("Closed Report (id = " + closed.getId() + ")");
        return ResponseEntity.ok(
            new DatabaseSaveResponse(true, "Report closed."));
      } else {
        logger.info("Report not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            new DatabaseSaveResponse(false, "Report not found."));
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
      logger.info("Failed to close Report.");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
          new DatabaseSaveResponse(false, "Report not closed."));
    }
  }

  /**
   * Opens an already close report.
   *
   * @param ID id of the report
   * @return response entity
   */
  @PostMapping("/openReport")
  public ResponseEntity<DatabaseSaveResponse> openReport(@RequestParam Integer ID) {
    try {
      Optional<Report> optionalReport = reportRepository.findById(ID);
      if (optionalReport.isPresent()) {
        Report reportNotClosed = optionalReport.get();
        reportNotClosed.setIsClosed(false);
        Report opened = reportRepository.save(reportNotClosed);
        logger.info("Opened Report (id = " + opened.getId() + ")");
        return ResponseEntity.ok(
            new DatabaseSaveResponse(true, "Report opened."));
      } else {
        logger.info("Report not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            new DatabaseSaveResponse(false, "Report not found."));
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
      logger.info("Failed to open Report.");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
          new DatabaseSaveResponse(false, "Report not opened."));
    }
  }

  /**
   * Admin can claim a report with this post mapping.
   *
   * @param ID id of the report
   * @param email email of the admin
   * @return response
   */
  @PostMapping("/claim")
  public ResponseEntity<DatabaseSaveResponse> claim(@RequestParam Integer ID,
      @RequestParam String email) {
    try {
      Optional<Report> optionalReport = reportRepository.findById(ID);
      if (optionalReport.isPresent()) {
        Report report = optionalReport.get();
        report.setAdminEmail(email);
        Report opened = reportRepository.save(report);
        logger.info("Opened Report (id = " + opened.getId() + ")");
        return ResponseEntity.ok(
            new DatabaseSaveResponse(true, "Report opened."));
      } else {
        logger.info("Report not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            new DatabaseSaveResponse(false, "Report not found."));
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
      logger.info("Failed to open Report.");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
          new DatabaseSaveResponse(false, "Report not opened."));
    }
  }

  /**
   * Removes the admin key for a report.
   *
   * @param ID id of the report
   * @return response entity
   */
  @PostMapping("/unclaim")
  public ResponseEntity<DatabaseSaveResponse> unclaim(@RequestParam Integer ID) {
    try {
      Optional<Report> optionalReport = reportRepository.findById(ID);
      if (optionalReport.isPresent()) {
        Report report = optionalReport.get();
        report.setAdminEmail(null);
        Report opened = reportRepository.save(report);
        logger.info("Opened Report (id = " + opened.getId() + ")");
        return ResponseEntity.ok(
            new DatabaseSaveResponse(true, "Report opened."));
      } else {
        logger.info("Report not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            new DatabaseSaveResponse(false, "Report not found."));
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
      logger.info("Failed to open Report.");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
          new DatabaseSaveResponse(false, "Report not opened."));
    }
  }

}
