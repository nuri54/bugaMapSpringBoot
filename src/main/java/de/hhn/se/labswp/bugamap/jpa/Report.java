package de.hhn.se.labswp.bugamap.jpa;

import jakarta.persistence.*;
import lombok.*;

/**
 * This class represents the entity of a report in the database. A report belongs to a specific
 * BugaPoint and is created by an Admin user.
 */
@Data
@RequiredArgsConstructor
@Entity
@Table(name = "report")
public class Report {

  /**
   * The ID of the report.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
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
  @JoinColumn(name = "Adminemail")
  private String adminEmail;

  /**
   * The title of the report, up to 4095 characters long.
   */
  @Column(name = "Title", length = 4095, nullable = false)
  private String title;

  /**
   * The message of the report, up to 4095 characters long.
   */
  @Column(name = "Message", length = 4095, nullable = false)
  private String message;

  /**
   * The isClosed Boolean of the report, which indicates if a report is closed.
   */
  @Column(name = "isClosed", nullable = false)
  private Boolean isClosed;
}