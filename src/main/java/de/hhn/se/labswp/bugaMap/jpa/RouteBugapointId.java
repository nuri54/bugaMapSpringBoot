package de.hhn.se.labswp.bugaMap.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import org.hibernate.Hibernate;

@Embeddable
public class RouteBugapointId implements Serializable {

  private static final long serialVersionUID = -5905579136684010108L;
  @Column(name = "RouteID", nullable = false)
  private Integer routeID;

  @Column(name = "BugaPointID", nullable = false)
  private Integer bugaPointID;

  public Integer getRouteID() {
    return routeID;
  }

  public void setRouteID(Integer routeID) {
    this.routeID = routeID;
  }

  public Integer getBugaPointID() {
    return bugaPointID;
  }

  public void setBugaPointID(Integer bugaPointID) {
    this.bugaPointID = bugaPointID;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    RouteBugapointId entity = (RouteBugapointId) o;
    return Objects.equals(this.bugaPointID, entity.bugaPointID) &&
        Objects.equals(this.routeID, entity.routeID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bugaPointID, routeID);
  }

}