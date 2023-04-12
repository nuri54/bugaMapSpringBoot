package de.hhn.se.labswp.bugaMap.controller;

import de.hhn.se.labswp.bugaMap.crudRepos.AdminRepository;
import de.hhn.se.labswp.bugaMap.crudRepos.BugapointRepository;
import de.hhn.se.labswp.bugaMap.jpa.Admin;
import de.hhn.se.labswp.bugaMap.jpa.Bugapoint;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    Iterable<Admin> admins = adminRepository.findAll();
    bugapoint.setAdminID(admins.iterator().next().getId());
    
    bugapointRepository.save(bugapoint);
  }
  @GetMapping("/getDiscriminators")
  public List<String> getDiscriminators() {
    Set discriminators = new HashSet();
    for (Bugapoint bugapoint: bugapointRepository.findAll()) {
      discriminators.add(bugapoint.getDiscriminator());
    }
    return discriminators.stream().toList();
  }

  @GetMapping("/filterBugapoints")
  public List<Bugapoint> filterBugapoints(@RequestParam("discriminators") Set<String> selectedDiscriminators) {
    List<Bugapoint> filteredBugapoints;
    if (selectedDiscriminators.isEmpty()) {
      filteredBugapoints = StreamSupport.stream(bugapointRepository.findAll().spliterator(), false)
              .collect(Collectors.toList());
    } else {
      filteredBugapoints = StreamSupport.stream(bugapointRepository.findAll().spliterator(), false)
              .filter(bugapoint -> selectedDiscriminators.contains(bugapoint.getDiscriminator()))
              .collect(Collectors.toList());
    }
    return filteredBugapoints;
  }
}
