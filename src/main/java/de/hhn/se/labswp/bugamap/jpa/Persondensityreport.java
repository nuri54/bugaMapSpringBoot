package de.hhn.se.labswp.bugamap.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "persondensityreport")
public class Persondensityreport {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", nullable = false)
  private Integer id;

  @Column(name = "Validtill", nullable = false)
  private Instant validtill;

  @Column(name = "Density", nullable = false)
  private Integer density;

  /**
   * The ID of the BugaPoint the report belongs to.
   */
  @Column(name = "Bugapointid")
  private int bugaPointID;

}