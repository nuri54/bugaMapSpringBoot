package de.hhn.se.labswp.bugaMap.controller;

import de.hhn.se.labswp.bugaMap.crudRepos.BugapointRepository;
import de.hhn.se.labswp.bugaMap.crudRepos.AdminRepository;
import de.hhn.se.labswp.bugaMap.jpa.Bugapoint;
import de.hhn.se.labswp.bugaMap.jpa.Admin;
import java.util.Collections;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
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
    bugapoint.setAdminID(users.iterator().next().getId());
    
    bugapointRepository.save(bugapoint);
  }

  /**
   * Simple request to get all the different types of the buga points.
   *
   * @return distinct discriminators
   */
  @GetMapping("/getDiscriminators")
  public List<String> getDiscriminators() {
    return jdbcTemplate.queryForList("SELECT DISTINCT discriminator FROM bugapoint", String.class);
  }


  /**
   * Gets all the buga points with the given discriminators.
   *
   * @param discriminators discriminators
   * @return all buga points with the matching discriminator
   */
  @GetMapping("/filterBugapoints")
  public List<Bugapoint> filterBugapoints(@RequestParam(value = "discriminators", required = false) List<String> discriminators) {

    if (discriminators == null || discriminators.isEmpty()) {
      return (List<Bugapoint>) bugapointRepository.findAll();
    }

    String sql = "SELECT * FROM bugapoint WHERE Discriminator IN (" +
        String.join(",", Collections.nCopies(discriminators.size(), "?")) + ")";
    return jdbcTemplate.query(sql, discriminators.toArray(),
        new BeanPropertyRowMapper<>(Bugapoint.class));
  }

}
