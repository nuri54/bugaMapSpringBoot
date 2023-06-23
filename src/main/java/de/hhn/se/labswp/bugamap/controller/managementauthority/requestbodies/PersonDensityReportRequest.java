package de.hhn.se.labswp.bugamap.controller.managementauthority.requestbodies;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDensityReportRequest {

  private Integer bugapointid;

  private Integer density;

  private Integer duration; // seconds

}
