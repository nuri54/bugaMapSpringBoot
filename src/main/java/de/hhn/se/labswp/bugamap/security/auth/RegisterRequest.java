package de.hhn.se.labswp.bugamap.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO class representing the registration request.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  /**
   * The first name of the user.
   */
  private String firstname;

  /**
   * The last name of the user.
   */
  private String lastname;

  /**
   * The email address of the user.
   */
  private String email;

  /**
   * The password of the user.
   */
  private String password;


}
