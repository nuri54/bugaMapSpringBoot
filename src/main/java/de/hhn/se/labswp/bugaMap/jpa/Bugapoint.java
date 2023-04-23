package de.hhn.se.labswp.bugaMap.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Entity
@Table(name = "bugapoint")
public class Bugapoint {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

  public Bugapoint(int parkID, int adminID, String title, Double longitude, Double latitude,
      String discriminator, String description) {
    this.parkID = parkID;
    this.adminID = adminID;
    this.title = title;
    this.longitude = longitude;
    this.latitude = latitude;
    this.discriminator = discriminator;
    this.description = description;
  }

  public Bugapoint(Integer id, int parkID, int adminID, String title, Double longitude,
      Double latitude, String discriminator, String description) {
    this.id = id;
    this.parkID = parkID;
    this.adminID = adminID;
    this.title = title;
    this.longitude = longitude;
    this.latitude = latitude;
    this.discriminator = discriminator;
    this.description = description;
  }

  public Bugapoint() {

  }
}