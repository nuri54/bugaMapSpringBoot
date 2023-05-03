package de.hhn.se.labswp.bugamap.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A class representing the request to create or update a Bugapoint.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BugapointRequest {

  /**
   * The ID of the park in which the Bugapoint is located.
   */
  private int parkID;

  /**
   * The ID of the admin who created the Bugapoint.
   */
  private int adminID;

  /**
   * The title of the Bugapoint.
   */
  private String title;

  /**
   * The longitude coordinate of the Bugapoint.
   */
  private Double longitude;

  /**
   * The latitude coordinate of the Bugapoint.
   */
  private Double latitude;

  /**
   * The discriminator of the Bugapoint.
   */
  private String discriminator;

  /**
   * The description of the Bugapoint.
   */
  private String description;

}