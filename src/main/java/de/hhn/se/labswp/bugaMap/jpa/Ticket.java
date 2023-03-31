package de.hhn.se.labswp.bugaMap.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ticket")
public class Ticket {

  @Id
  @Column(name = "ID", nullable = false)
  private Integer id;

  @Column(name = "Tarif", nullable = false)
  private String tarif;

  @Column(name = "Price", nullable = false)
  private Double price;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTarif() {
    return tarif;
  }

  public void setTarif(String tarif) {
    this.tarif = tarif;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

}