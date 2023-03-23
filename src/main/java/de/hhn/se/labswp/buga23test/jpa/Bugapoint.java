package de.hhn.se.labswp.buga23test.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bugapoint")
public class Bugapoint {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", nullable = false)
  private Integer id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "AdminID", nullable = false)
  private User adminID;

  @Column(name = "Title", nullable = false)
  private String title;

  @Column(name = "Longitude", nullable = false)
  private Double longitude;

  @Column(name = "Latitude", nullable = false)
  private Double latitude;

  @Column(name = "Discriminator", nullable = false)
  private String discriminator;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public User getAdminID() {
    return adminID;
  }

  public void setAdminID(User adminID) {
    this.adminID = adminID;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public String getDiscriminator() {
    return discriminator;
  }

  public void setDiscriminator(String discriminator) {
    this.discriminator = discriminator;
  }

}