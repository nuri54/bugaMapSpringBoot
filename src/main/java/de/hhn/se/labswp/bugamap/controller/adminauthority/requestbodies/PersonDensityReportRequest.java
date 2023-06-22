package de.hhn.se.labswp.bugamap.controller.adminauthority.requestbodies;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDensityReportRequest {

  private Double latitude;

  private Double longitude;

  private Integer density;

  private Integer duration; // seconds

}
