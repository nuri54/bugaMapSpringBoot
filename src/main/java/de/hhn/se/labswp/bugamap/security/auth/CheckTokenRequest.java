package de.hhn.se.labswp.bugamap.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the request to check the validity of a token in the AuthenticationService.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckTokenRequest {

  /**
   * The token to be checked for validity.
   */
  private String token;
}
