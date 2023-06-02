package de.hhn.se.labswp.bugamap.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a person density report entity which holds information such as latitude,
 * longitude and validity of the report.
 */
@Getter
@Setter
@Entity
@Table(name = "persondensityreport")
public class Persondensityreport {

  /**
   * The unique identifier of the report.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", nullable = false)
  private Integer id;

  /**
   * The latitude value of the location.
   */
  @Column(name = "Latitude", nullable = false)
  private Double latitude;

  /**
   * The longitude value of the location.
   */
  @Column(name = "Longitude", nullable = false)
  private Double longitude;


  @Column(name = "Validtill")
  private LocalDate validTill;

}