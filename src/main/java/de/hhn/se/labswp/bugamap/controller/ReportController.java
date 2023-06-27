package de.hhn.se.labswp.bugamap.controller;

import de.hhn.se.labswp.bugamap.controller.managementauthority.AdminBugapointController;
import de.hhn.se.labswp.bugamap.crudrepos.AdminRepository;
import de.hhn.se.labswp.bugamap.crudrepos.BugapointRepository;
import de.hhn.se.labswp.bugamap.crudrepos.ReportRepository;
import de.hhn.se.labswp.bugamap.jpa.Admin;
import de.hhn.se.labswp.bugamap.jpa.Bugapoint;
import de.hhn.se.labswp.bugamap.jpa.Report;
import de.hhn.se.labswp.bugamap.requests.BugapointRequest;
import de.hhn.se.labswp.bugamap.responses.DatabaseSaveResponse;
import java.util.Optional;
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
    private static final Logger logger = LogManager.getLogger(ReportController.class);


    private final ReportRepository reportRepository;

    private final AdminRepository adminRepository;

    private final BugapointRepository bugapointRepository;

    public ReportController(ReportRepository reportRepository, AdminRepository adminRepository,
        BugapointRepository bugapointRepository) {
        this.reportRepository = reportRepository;
        this.adminRepository = adminRepository;
        this.bugapointRepository = bugapointRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<DatabaseSaveResponse> save(@RequestBody Report reportRequest) {
        try {
            Optional<Bugapoint> optionalBugapoint = bugapointRepository.findById(reportRequest.getBugaPointID());

            if (optionalBugapoint.isPresent()) {
                Optional<Admin> optionalAdmin = adminRepository.findById(optionalBugapoint.get().getAdminID());

                optionalAdmin.ifPresent(admin -> reportRequest.setAdminEmail(admin.getEmailadress()));
            }

            Report saved = reportRepository.save(reportRequest);
            logger.info("Saved Report (id = " + saved.getId() + ")");
            return ResponseEntity.ok(
                new DatabaseSaveResponse(true, "Report saved. IST DAS ÜBERHAUPT HIER"));
        } catch (Exception e) {
            logger.info(e.getMessage());
            logger.info("Failed to save Report.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new DatabaseSaveResponse(false, "Report not saved."));
        }
    }

}
