package de.hhn.se.labswp.buga23test.jpa;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "route_bugapoint")
public class RouteBugapoint {

  @EmbeddedId
  private RouteBugapointId id;

  @MapsId("routeID")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "RouteID", nullable = false)
  private Route routeID;

  @MapsId("bugaPointID")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "BugaPointID", nullable = false)
  private Bugapoint bugaPointID;

  public RouteBugapointId getId() {
    return id;
  }

  public void setId(RouteBugapointId id) {
    this.id = id;
  }

  public Route getRouteID() {
    return routeID;
  }

  public void setRouteID(Route routeID) {
    this.routeID = routeID;
  }

  public Bugapoint getBugaPointID() {
    return bugaPointID;
  }

  public void setBugaPointID(Bugapoint bugaPointID) {
    this.bugaPointID = bugaPointID;
  }

}