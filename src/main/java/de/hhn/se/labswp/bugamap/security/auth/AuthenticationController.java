package de.hhn.se.labswp.bugamap.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller class that handles authentication requests.
 */
@RestController
@RequestMapping("/api/v1/open/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  /**
   * Registers a new user account.
   *
   * @param request The register request containing the user's email and password.
   * @return ResponseEntity containing an AuthenticationResponse if the registration is successful.
   */
  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.register(request));
  }

  /**
   * Authenticates a user and returns a JWT token.
   *
   * @param request The authentication request containing the user's email and password.
   * @return ResponseEntity containing AuthenticationResponse with JWT if authentication successful.
   */
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  /**
   * Checks if a JWT token is valid.
   *
   * @param request The CheckTokenRequest containing the JWT token to be checked.
   * @return True if the token is valid, false otherwise.
   */
  @PostMapping("/checkToken")
  public boolean checkToken(
      @RequestBody CheckTokenRequest request
  ) {
    if (request.getToken().isEmpty()) {
      return false;
    } else {
      return service.checkToken(request);
    }
  }
}
