package de.hhn.se.labswp.buga23test.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import org.hibernate.Hibernate;

@Embeddable
public class TicketRouteId implements Serializable {

  private static final long serialVersionUID = -2579757955815761479L;
  @Column(name = "TicketID", nullable = false)
  private Integer ticketID;

  @Column(name = "RouteID", nullable = false)
  private Integer routeID;

  public Integer getTicketID() {
    return ticketID;
  }

  public void setTicketID(Integer ticketID) {
    this.ticketID = ticketID;
  }

  public Integer getRouteID() {
    return routeID;
  }

  public void setRouteID(Integer routeID) {
    this.routeID = routeID;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    TicketRouteId entity = (TicketRouteId) o;
    return Objects.equals(this.routeID, entity.routeID) &&
        Objects.equals(this.ticketID, entity.ticketID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(routeID, ticketID);
  }

}