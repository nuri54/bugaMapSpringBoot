package de.hhn.se.labswp.bugaMap.jpa;

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

  @Column(name = "AdminID", nullable = false)
  private Integer adminID;

  @Column(name = "ReportText", length = 1000)
  private String reportText;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "AdminID2", nullable = false)
  private Admin adminID2;

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

  public Integer getAdminID() {
    return adminID;
  }

  public void setAdminID(Integer adminID) {
    this.adminID = adminID;
  }

  public String getReportText() {
    return reportText;
  }

  public void setReportText(String reportText) {
    this.reportText = reportText;
  }

  public Admin getAdminID2() {
    return adminID2;
  }

  public void setAdminID2(Admin adminID2) {
    this.adminID2 = adminID2;
  }

}