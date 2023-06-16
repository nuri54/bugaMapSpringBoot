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
  @JoinColumn(name = "BugaPointID")
  private int bugaPointID;

  /**
   * The ID of the Park the report belongs to.
   */
  @JoinColumn(name = "ParkID")
  private int parkID;

  /**
   * The Email of the Admin user who works on the report.
   */
  @JoinColumn(name = "AdminEmail")
  private String adminEmail;

  /**
   * The title of the report, up to 4095 characters long.
   */
  @Column(name = "title", length = 4095, nullable = false)
  private String title;

  /**
   * The message of the report, up to 4095 characters long.
   */
  @Column(name = "message", length = 4095, nullable = false)
  private String message;

  /**
   * The isClosed Boolean of the report, which indicates if a report is closed.
   */
  @Column(name = "isClosed", nullable = false)
  private Boolean isClosed;
}