package de.hhn.se.labswp.bugaMap.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "park")
public class Park {

  @Id
  @Column(name = "ID", nullable = false)
  private Integer id;

  @Column(name = "Title")
  private String title;

  @Column(name = "Latitude", nullable = false)
  private Double latitude;

  @Column(name = "Longitude", nullable = false)
  private Double longitude;

}