package de.hhn.se.labswp.bugamap.controller;

import de.hhn.se.labswp.bugamap.crudrepos.BugapointRepository;
import de.hhn.se.labswp.bugamap.jpa.Bugapoint;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Mappings for Bugapoint table.
 */
@RestController
@RequestMapping("/bugapoint")
public class BugapointController {

  private static Logger logger = LogManager.getLogger(BugapointController.class);
  private final BugapointRepository bugapointRepository;

  private final JdbcTemplate jdbcTemplate;

  public BugapointController(BugapointRepository bugapointRepository, JdbcTemplate jdbcTemplate) {
    this.bugapointRepository = bugapointRepository;
    this.jdbcTemplate = jdbcTemplate;
  }


  /**
   * Simple request to get all points in the database.
   *
   * @return bugapoints all bugapoints
   */
  @GetMapping("/list")
  public List<Bugapoint> getBugapoints() {
    return (List<Bugapoint>) bugapointRepository.findAll();
  }

  /**
   * Returns all bugapoints with the matching discriminators.
   *
   * @param discriminators discriminators
   * @return filtered bugapoints
   */
  @GetMapping("/list/filter")
  public List<Bugapoint> getBugapoints(@RequestParam(value = "discriminators", required = false)
    List<String> discriminators) {

    if (discriminators == null || discriminators.isEmpty()) {
      return new ArrayList<>();
    }

    String sql = "SELECT * FROM bugapoint WHERE Discriminator IN (" +
        String.join(",", Collections.nCopies(discriminators.size(), "?")) + ")";
    return jdbcTemplate.query(sql, discriminators.toArray(),
        new BeanPropertyRowMapper<>(Bugapoint.class));
  }

  /**
   * Returns all bugapoints with the parkId.
   *
   * @param parkid ParkID
   * @return bugapoints
   */
  @GetMapping("/list/park")
  public List<Bugapoint> getBugapoints(@RequestParam(value = "parkid", required = false) String parkid) {
    if (parkid == null) {
      return (List<Bugapoint>) bugapointRepository.findAll();
    }
    return bugapointRepository.findByParkID(Integer.parseInt(parkid));
  }

  /**
   * Simple request to get all the different types of the buga points.
   *
   * @return distinct discriminators
   */
  @GetMapping("/discriminators")
  public List<String> getDiscriminators() {
    return jdbcTemplate.queryForList(
        "SELECT DISTINCT discriminator FROM bugapoint", String.class);
  }
}

