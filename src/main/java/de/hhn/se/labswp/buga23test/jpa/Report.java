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
@Table(name = "report")
public class Report {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", nullable = false)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "BugaPointID", nullable = false)
  private Bugapoint bugaPointID;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "AdminID", nullable = false)
  private User adminID;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "UserID", nullable = false)
  private User userID;

  @Column(name = "ReportText", length = 1000)
  private String reportText;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Bugapoint getBugaPointID() {
    return bugaPointID;
  }

  public void setBugaPointID(Bugapoint bugaPointID) {
    this.bugaPointID = bugaPointID;
  }

  public User getAdminID() {
    return adminID;
  }

  public void setAdminID(User adminID) {
    this.adminID = adminID;
  }

  public User getUserID() {
    return userID;
  }

  public void setUserID(User userID) {
    this.userID = userID;
  }

  public String getReportText() {
    return reportText;
  }

  public void setReportText(String reportText) {
    this.reportText = reportText;
  }

}