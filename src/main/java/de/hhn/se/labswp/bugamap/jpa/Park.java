package de.hhn.se.labswp.bugamap.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * A class representing a park in the BugaMap application.
 */
@Getter
@Setter
@Entity
@Table(name = "park")
public class Park {

  /**
   * The id of the park.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", nullable = false)
  private Integer id;

  /**
   * The title of the park.
   */
  @Column(name = "Title")
  private String title;

  /**
   * The latitude of the park.
   */
  @Column(name = "Latitude", nullable = false)
  private Double latitude;

  /**
   * The longitude of the park.
   */
  @Column(name = "Longitude", nullable = false)
  private Double longitude;

}