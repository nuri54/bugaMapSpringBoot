package de.hhn.se.labswp.bugaMap.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BugapointRequest {

  private int parkID;

  private int adminID;

  private String title;

  private Double longitude;

  private Double latitude;

  private String discriminator;

  private String description;

}