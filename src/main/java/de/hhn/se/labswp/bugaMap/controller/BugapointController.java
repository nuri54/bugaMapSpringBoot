package de.hhn.se.labswp.bugaMap.controller;

import de.hhn.se.labswp.bugaMap.crudRepos.BugapointRepository;
import de.hhn.se.labswp.bugaMap.crudRepos.AdminRepository;
import de.hhn.se.labswp.bugaMap.jpa.Bugapoint;
import de.hhn.se.labswp.bugaMap.jpa.Admin;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BugapointController {

  private final BugapointRepository bugapointRepository;

  private final AdminRepository adminRepository;

  public BugapointController(BugapointRepository bugapointRepository,
      AdminRepository adminRepository) {
    this.bugapointRepository = bugapointRepository;
    this.adminRepository = adminRepository;
  }

  @GetMapping("/bugapoints")
  public List<Bugapoint> getBugapoints() {
    return (List<Bugapoint>) bugapointRepository.findAll();
  }

  @PostMapping("/addBugapoint")
  public void addBugapoint(@RequestParam String title, @RequestParam double latitude,
      @RequestParam double longitude, @RequestParam String type) {

    Bugapoint bugapoint = new Bugapoint();

    bugapoint.setTitle(title);
    bugapoint.setLongitude(longitude);
    bugapoint.setLatitude(latitude);
    bugapoint.setDiscriminator(type);

    //Demo
    Iterable<Admin> users = adminRepository.findAll();
    bugapoint.setAdminID(users.iterator().next());
    
    bugapointRepository.save(bugapoint);
  }
}
