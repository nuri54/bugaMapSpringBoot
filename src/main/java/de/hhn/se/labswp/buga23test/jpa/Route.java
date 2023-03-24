package de.hhn.se.labswp.buga23test.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "route")
public class Route {

  @Id
  @Column(name = "ID", nullable = false)
  private Integer id;

  @Column(name = "Departure")
  private Instant departure;

  @Column(name = "Arrival")
  private Instant arrival;

  @Column(name = "TransportationType")
  private Integer transportationType;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Instant getDeparture() {
    return departure;
  }

  public void setDeparture(Instant departure) {
    this.departure = departure;
  }

  public Instant getArrival() {
    return arrival;
  }

  public void setArrival(Instant arrival) {
    this.arrival = arrival;
  }

  public Integer getTransportationType() {
    return transportationType;
  }

  public void setTransportationType(Integer transportationType) {
    this.transportationType = transportationType;
  }

}