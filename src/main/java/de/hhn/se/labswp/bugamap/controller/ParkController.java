package de.hhn.se.labswp.bugamap.controller;


import de.hhn.se.labswp.bugamap.crudrepos.ParkRepository;
import de.hhn.se.labswp.bugamap.jpa.Park;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Mappings for Bugapoint table.
 */
@RestController
@RequestMapping("/api/v1/open/park")
@CrossOrigin(origins = "http://localhost:4200")
public class ParkController {

  private final ParkRepository parkRepository;

  private final JdbcTemplate jdbcTemplate;


  public ParkController(ParkRepository parkRepository, JdbcTemplate jdbcTemplate) {
    this.parkRepository = parkRepository;
    this.jdbcTemplate = jdbcTemplate;
  }

  /**
   * Lists all parks.
   *
   * @return parks
   */
  @GetMapping("/list")
  public List<Park> getParks() {
    return (List<Park>) parkRepository.findAll();
  }

  /**
   * Gets park with given id.
   *
   * @param id ID
   * @return park
   */
  @GetMapping("/id/{id}")
  public Park getParkById(@PathVariable int id) {
    return parkRepository.findById(id).get();
  }
}
