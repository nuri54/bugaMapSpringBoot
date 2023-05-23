package de.hhn.se.labswp.bugamap.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.Arrays;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a Bugapoint entity in the Bugamap application. A Bugapoint is a location
 * within a park that has been reported by an admin. It contains information such as its title,
 * coordinates, and description.
 */
@Builder
@Getter
@Setter
@Entity
@Table(name = "bugapoint")
public class Bugapoint {

  /**
   * The unique identifier of this Bugapoint.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", nullable = false)
  private Integer id;

  /**
   * The identifier of the Park this Bugapoint belongs to.
   */

  @JoinColumn(name = "ParkID", nullable = false)
  private int parkID;

  /**
   * The identifier of the admin who reported this Bugapoint.
   */
  @JoinColumn(name = "AdminID", nullable = false)
  private int adminID;

  /**
   * The title of this Bugapoint.
   */
  @Column(name = "Title", nullable = false)
  private String title;

  /**
   * The longitude coordinate of this Bugapoint.
   */
  @Column(name = "Longitude", nullable = false)
  private Double longitude;

  /**
   * The latitude coordinate of this Bugapoint.
   */
  @Column(name = "Latitude", nullable = false)
  private Double latitude;

  /**
   * The type of this Bugapoint. It can be a WC, a food stand, etc.
   */
  @Column(name = "Discriminator", nullable = false)
  private String discriminator;

  /**
   * The description of this Bugapoint.
   */
  @Column(name = "Description", length = 4095)
  private String description;

  @Column(name = "Iconname")
  private String iconname;

  /**
   * Creates a new instance of the `Bugapoint` class with the given parameters.
   *
   * @param id            the ID of the Bugapoint
   * @param parkID        the ID of the park where the Bugapoint is located
   * @param adminID       the ID of the admin who created the Bugapoint
   * @param title         the title of the Bugapoint
   * @param longitude     the longitude of the Bugapoint's location
   * @param latitude      the latitude of the Bugapoint's location
   * @param discriminator the discriminator of the Bugapoint
   * @param description   the description of the Bugapoint
   * @param iconname      the icon name of the bugapoint
   */
  public Bugapoint(Integer id, int parkID, int adminID, String title, Double longitude,
      Double latitude, String discriminator, String description, String iconname) {
    this.id = id;
    this.parkID = parkID;
    this.adminID = adminID;
    this.title = title;
    this.longitude = longitude;
    this.latitude = latitude;
    this.discriminator = discriminator;
    this.description = description;
    this.iconname = iconname;
  }

  /**
   * Creates a new instance of the `Bugapoint` class with default values for all fields.
   */
  public Bugapoint() {

  }

  public static List<String> getColumns() {
    return Arrays.asList("id", "parkId", "adminId", "title", "longitude", "latitude",
        "discriminator", "description");
  }
}