package de.hhn.se.labswp.bugamap.controller.managementauthority;

import de.hhn.se.labswp.bugamap.crudrepos.AdminRepository;
import de.hhn.se.labswp.bugamap.crudrepos.ReportRepository;
import de.hhn.se.labswp.bugamap.jpa.Persondensityreport;
import de.hhn.se.labswp.bugamap.jpa.Report;
import de.hhn.se.labswp.bugamap.responses.DatabaseSaveResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/management/report")
public class AdminReportController {

    private static final Logger logger = LogManager.getLogger(AdminPersondensityReportController.class);

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
        return reportRepository.findAllByAdminEmail(adminRepository.findByEmailadress(email)
                .orElseThrow(() -> new IllegalArgumentException("Admin with Email" + email + " not found")).getId());
    }

    @GetMapping("/orphanList")
    public List<Report> findAllByAdminNull() {
        return reportRepository.findAllByAdminEmailNull();
    }


    @PostMapping("/saveReport")
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
    public ResponseEntity<DatabaseSaveResponse> check(@RequestBody Report report) {

        try {
            Report reportClosed = report;
            reportClosed.setIsClosed(true);
            Report closed = reportRepository.save(reportClosed);
            logger.info("Closed Report (id = " + closed.getId() + ")");
            return ResponseEntity.ok(
                    new DatabaseSaveResponse(true, "Report closed."));
        } catch (Exception e) {
            logger.info(e.getMessage());
            logger.info("Failed to close Report.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new DatabaseSaveResponse(false, "Report not closed."));
        }
    }

    @PostMapping("/openReport")
    public ResponseEntity<DatabaseSaveResponse> uncheck(@RequestBody Report report) {

        try {
            Report reportNotClosed = report;
            reportNotClosed.setIsClosed(false);
            Report closed = reportRepository.save(reportNotClosed);
            logger.info("Opened Report (id = " + closed.getId() + ")");
            return ResponseEntity.ok(
                    new DatabaseSaveResponse(true, "Report opened."));
        } catch (Exception e) {
            logger.info(e.getMessage());
            logger.info("Failed to open Report.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new DatabaseSaveResponse(false, "Report not opened."));
        }
    }
}
