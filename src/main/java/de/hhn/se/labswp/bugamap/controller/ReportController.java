package de.hhn.se.labswp.bugamap.controller;

import de.hhn.se.labswp.bugamap.controller.managementauthority.AdminBugapointController;
import de.hhn.se.labswp.bugamap.crudrepos.AdminRepository;
import de.hhn.se.labswp.bugamap.crudrepos.ReportRepository;
import de.hhn.se.labswp.bugamap.jpa.Bugapoint;
import de.hhn.se.labswp.bugamap.jpa.Report;
import de.hhn.se.labswp.bugamap.requests.BugapointRequest;
import de.hhn.se.labswp.bugamap.responses.DatabaseSaveResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/open/report")
@CrossOrigin(origins = "http://localhost:4200")
public class ReportController {
    private final ReportRepository reportRepository;
    private static final Logger logger = LogManager.getLogger(ReportController.class);


    public ReportController(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
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

}
