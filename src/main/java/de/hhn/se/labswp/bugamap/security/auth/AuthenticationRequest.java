package de.hhn.se.labswp.bugamap.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents an authentication request object. It contains the user's email and
 * password.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

  /**
   * The email of the user making the request.
   */
  private String email;

  /**
   * The password of the user making the request.
   */
  private String password;

}
