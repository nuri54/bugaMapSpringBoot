package de.hhn.se.labswp.buga23test.controller;

import de.hhn.se.labswp.buga23test.crudRepos.BugapointRepository;
import de.hhn.se.labswp.buga23test.jpa.Bugapoint;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BugapointController {

  private final BugapointRepository bugapointRepository;

  public BugapointController(BugapointRepository bugapointRepository) {
    this.bugapointRepository = bugapointRepository;
  }

  @GetMapping("/bugapoints")
  public List<Bugapoint> getBugapoints() {
    return (List<Bugapoint>) bugapointRepository.findAll();
  }
}
