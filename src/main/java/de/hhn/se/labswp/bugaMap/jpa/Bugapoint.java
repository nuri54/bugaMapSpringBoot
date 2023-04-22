package de.hhn.se.labswp.bugaMap.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bugapoint")
public class Bugapoint {

  @Id
  @Column(name = "ID", nullable = false)
  private Integer id;

  @JoinColumn(name = "ParkID", nullable = false)
  private int parkID;

  @JoinColumn(name = "AdminID", nullable = false)
  private int adminID;

  @Column(name = "Title", nullable = false)
  private String title;

  @Column(name = "Longitude", nullable = false)
  private Double longitude;

  @Column(name = "Latitude", nullable = false)
  private Double latitude;

  @Column(name = "Discriminator", nullable = false)
  private String discriminator;

  @Column(name = "Description", length = 4095)
  private String description;

}