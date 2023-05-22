package de.hhn.se.labswp.bugamap.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO class that represents the response returned by the AuthenticationController after
 * successful authentication. Contains the JWT token generated for the user.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

  /**
   * The JWT token generated for the user upon successful authentication.
   */
  private String token;

  private String role;

}
