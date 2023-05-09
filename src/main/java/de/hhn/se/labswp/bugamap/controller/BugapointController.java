package de.hhn.se.labswp.bugamap.controller;

import de.hhn.se.labswp.bugamap.crudrepos.BugapointRepository;
import de.hhn.se.labswp.bugamap.jpa.Bugapoint;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
   * Sortable by: parkId, title, longitude, latitude.
   * Also query by: discriminator
   *
   * @return bugapoints all bugapoints
   */
  @GetMapping("/list")
  public List<Bugapoint> getBugapoints(@RequestParam(required = false) Map<String, String> query) {
    List<Bugapoint> output = (List<Bugapoint>) bugapointRepository.findAll();
    StringBuilder loggingMessage = new StringBuilder("Sent bugapoints. ");

    //Sorting
    if (query.containsKey("sortBy")) {
      switch (query.get("sortBy")) {
        case "parkId":
          output.sort(Comparator.comparingInt(Bugapoint::getParkID));
        case "title":
          output.sort(Comparator.comparing(Bugapoint::getTitle));
        case "longitude":
          output.sort(Comparator.comparingDouble(Bugapoint::getLongitude));
        case "latitude":
          output.sort(Comparator.comparingDouble(Bugapoint::getLatitude));
      }
      loggingMessage.append("(Sorted by ").append(query.get("sortBy")).append(") ");
    }

    //Where
    if (query.containsKey("whereDiscriminator")) {
      output.removeIf(bugapoint ->
          (!bugapoint.getDiscriminator().trim().equals(query.get("whereDiscriminator").trim())));
      loggingMessage.append("(where discriminator = ")
          .append(query.get("whereDiscriminator")).append(") ");
    }
    if (query.containsKey("whereTitle")) {
      output.removeIf(bugapoint -> (!bugapoint.getTitle().trim()
          .equals(query.get("whereTitle").trim())));
      loggingMessage.append("(where title = ")
          .append(query.get("whereTitle")).append(") ");
    }
    if (query.containsKey("whereParkId")) {
      try {
        output.removeIf(bugapoint ->
            ((bugapoint.getParkID() != Integer.parseInt(query.get("whereParkId")))));
      } catch (Exception ignore) {      }
      loggingMessage.append("(where parkId = ")
          .append(query.get("whereParkId")).append(") ");
    }


    logger.info(loggingMessage.toString().trim());
    return output;
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

