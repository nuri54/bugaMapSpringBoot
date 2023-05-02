package de.hhn.se.labswp.bugamap.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "report")
public class Report {

  @Id
  @Column(name = "ID", nullable = false)
  private Integer id;

  @JoinColumn(name = "BugaPointID", nullable = false)
  private int bugaPointID;

  @JoinColumn(name = "AdminID", nullable = false)
  private int adminID;

  @Column(name = "ReportText", length = 4095)
  private String reportText;

}