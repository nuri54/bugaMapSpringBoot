package de.hhn.se.labswp.bugamap.controller.managementauthority;

import de.hhn.se.labswp.bugamap.controller.managementauthority.responses.FirstnameResponse;
import de.hhn.se.labswp.bugamap.crudrepos.AdminRepository;
import de.hhn.se.labswp.bugamap.jpa.Admin;
import de.hhn.se.labswp.bugamap.security.JwtService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller class for handling admin authority API endpoints.
 */
@RestController
@RequestMapping(path = "/api/v1/management/admin")
@RequiredArgsConstructor
public class AdminAdminController {

  private final AdminRepository adminRepository;
  private final JwtService jwtService;

  /**
   * Returns the first name of the admin associated with the provided JWT token, if the admin
   * exists.
   *
   * @param authorizationHeader the Authorization header value containing the JWT token
   * @return admin's first name if the admin exists, or a 404 response if the admin is not found
   */
  @GetMapping("/firstname")
  public ResponseEntity<FirstnameResponse> getFirstname(
      @RequestHeader("Authorization") String authorizationHeader) {
    String email = jwtService.extractUsername(authorizationHeader);
    Optional<Admin> adminFound = adminRepository.findByEmailadress(email);
    if (adminFound.isPresent()) {
      Admin admin = adminFound.get();

      return ResponseEntity.ok(new FirstnameResponse(admin.getFirstname()));
    }
    return ResponseEntity.notFound().build();
  }
}
