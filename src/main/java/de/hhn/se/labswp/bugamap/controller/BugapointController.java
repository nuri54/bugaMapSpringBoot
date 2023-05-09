package de.hhn.se.labswp.bugamap.controller;

import de.hhn.se.labswp.bugamap.crudrepos.BugapointRepository;
import de.hhn.se.labswp.bugamap.jpa.Bugapoint;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
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

  private static final Logger logger = LogManager.getLogger(BugapointController.class);
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
    StringBuilder sqlQuery = new StringBuilder("SELECT * FROM bugapoint ");
    StringBuilder notification = new StringBuilder(" ");

    //Where
    StringBuilder whereConditions = new StringBuilder("WHERE (");

    if (query.containsKey("whereId")) {
      whereConditions.append("id = '").append(query.get("whereId")).append("' AND ");
    }
    if (query.containsKey("whereParkId")) {
      whereConditions.append("parkId = '").append(query.get("whereParkId")).append("' AND ");
    }
    if (query.containsKey("whereDiscriminator")) {
      whereConditions.append("discriminator = '").append(query.get("whereDiscriminator")).append("' AND ");
    }
    if (query.containsKey("whereTitle")) {
      whereConditions.append("title = '").append(query.get("whereTitle")).append("' AND ");
    }

    if (!whereConditions.toString().equals("WHERE (")) {
      // Einbauen
      whereConditions.delete(whereConditions.length() - 5,whereConditions.length());
      whereConditions.append(")");
      sqlQuery.append(whereConditions).append(" ");
    }

    //Sorting
    if (query.containsKey("orderBy") && Bugapoint.getColumns().contains(query.get("orderBy"))) {
      sqlQuery.append("ORDER BY ").append(query.get("orderBy")).append(" ");
      if (query.containsKey("order") && (query.get("order").equals("desc") ||
          query.get("order").equals("asc"))) {
        sqlQuery.append(query.get("order"));
      } else {
        notification.append("Order must be \"asc\" or \"desc\"");
      }
    } else {
      notification.append("Can not order by ").append(query.get("orderBy"));
    }




    logger.info("Sent bugapoints with query: " + sqlQuery.toString().trim()
        + notification.toString().trim());
    return jdbcTemplate.query(sqlQuery.toString().trim(),
        (PreparedStatementSetter) null, new BeanPropertyRowMapper<>(Bugapoint.class));
  }

  /** DEPRECATED
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

  /** DEPRECATED
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

