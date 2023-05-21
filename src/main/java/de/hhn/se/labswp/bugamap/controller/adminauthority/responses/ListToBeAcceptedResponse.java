package de.hhn.se.labswp.bugamap.controller.adminauthority.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListToBeAcceptedResponse {

    String email;
    String firstname;
    String surname;
    String role;
}
