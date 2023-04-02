package de.hhn.se.labswp.bugaMap.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "admin")
public class Admin {

  @Id
  @Column(name = "ID", nullable = false)
  private Integer id;

  @Column(name = "Firstname")
  private String firstname;

  @Column(name = "Lastname")
  private String lastname;

  @Column(name = "Birthday", nullable = false)
  private LocalDate birthday;

  @Column(name = "Emailadress")
  private String emailadress;

  @Column(name = "Password")
  private String password;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  public void setBirthday(LocalDate birthday) {
    this.birthday = birthday;
  }

  public String getEmailadress() {
    return emailadress;
  }

  public void setEmailadress(String emailadress) {
    this.emailadress = emailadress;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}