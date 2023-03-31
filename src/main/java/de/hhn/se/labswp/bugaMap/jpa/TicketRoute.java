package de.hhn.se.labswp.bugaMap.jpa;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "ticket_route")
public class TicketRoute {

  @EmbeddedId
  private TicketRouteId id;

  @MapsId("ticketID")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "TicketID", nullable = false)
  private Ticket ticketID;

  @MapsId("routeID")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "RouteID", nullable = false)
  private Route routeID;

  public TicketRouteId getId() {
    return id;
  }

  public void setId(TicketRouteId id) {
    this.id = id;
  }

  public Ticket getTicketID() {
    return ticketID;
  }

  public void setTicketID(Ticket ticketID) {
    this.ticketID = ticketID;
  }

  public Route getRouteID() {
    return routeID;
  }

  public void setRouteID(Route routeID) {
    this.routeID = routeID;
  }

}