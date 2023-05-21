package de.hhn.se.labswp.bugamap.controller.adminauthority.requestbodies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApproveRequest {
    private String email;
}
