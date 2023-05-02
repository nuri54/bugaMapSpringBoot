package de.hhn.se.labswp.bugamap.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents the entity of a report in the database. A report belongs to a specific
 * BugaPoint and is created by an Admin user.
 */
@Getter
@Setter
@Entity
@Table(name = "report")
public class Report {

  /**
   * The ID of the report.
   */
  @Id
  @Column(name = "ID", nullable = false)
  private Integer id;

  /**
   * The ID of the BugaPoint the report belongs to.
   */
  @JoinColumn(name = "BugaPointID", nullable = false)
  private int bugaPointID;

  /**
   * The ID of the Admin user who created the report.
   */
  @JoinColumn(name = "AdminID", nullable = false)
  private int adminID;

  /**
   * The text of the report, up to 4095 characters long.
   */
  @Column(name = "ReportText", length = 4095)
  private String reportText;

}