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

    @GetMapping("/list")
    public List<Report> findAll() {
        return reportRepository.findAll();
    }

  @GetMapping("/adminList")
  public List<Report> findAllByAdmin(@RequestParam(name = "email") String email) {
    return reportRepository.findByAdminEmail(email);
  }

    @GetMapping("/orphanList")
    public List<Report> findAllByAdminNull() {
        return reportRepository.findAllByAdminEmailNull();
    }


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

    @PostMapping("/closeReport")
    public ResponseEntity<DatabaseSaveResponse> check(@RequestParam Integer ID) {

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

    @PostMapping("/openReport")
    public ResponseEntity<DatabaseSaveResponse> uncheck(@RequestParam Integer ID) {
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

    @PostMapping("/claim")
    public ResponseEntity<DatabaseSaveResponse> claim(@RequestParam Integer ID, @RequestParam String email) {
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

    @PostMapping("/unclaim")
    public ResponseEntity<DatabaseSaveResponse> claim(@RequestParam Integer ID) {
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
