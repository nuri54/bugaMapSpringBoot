package de.hhn.se.labswp.bugamap.controller.adminauthority;

import de.hhn.se.labswp.bugamap.controller.adminauthority.requestbodies.ApproveRequest;
import de.hhn.se.labswp.bugamap.controller.adminauthority.responses.ListToBeAcceptedResponse;
import de.hhn.se.labswp.bugamap.responses.DatabaseSaveResponse;
import de.hhn.se.labswp.bugamap.security.auth.roles.Role;
import de.hhn.se.labswp.bugamap.crudrepos.AdminRepository;
import de.hhn.se.labswp.bugamap.jpa.Admin;
import de.hhn.se.labswp.bugamap.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/api/v1/admin")
@RequiredArgsConstructor
public class AdminApproveController {

  private final static Logger logger = LogManager.getLogger(JwtService.class);
  private final AdminRepository adminRepository;

  @PutMapping("/approve")
  public ResponseEntity<DatabaseSaveResponse> approveUserWithEmail(
      @RequestBody ApproveRequest userEmail) {

    Optional<Admin> adminFound = adminRepository.findByEmailadress(userEmail.getEmail());
    if (adminFound.isPresent()) {
      Admin admin = adminFound.get();
      admin.setRole(Role.MANAGER);
      adminRepository.save(admin);
      logger.info("Role for " + userEmail.getEmail() + " updated to Manager");
      return ResponseEntity.ok(new DatabaseSaveResponse(true, "Manager wurde approved"));
    } else {
      logger.info("Update Role for user failed because user not found: " + userEmail.getEmail());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DatabaseSaveResponse(false, "Manager wurde nicht approved"));
    }
  }


  @GetMapping("listNotAccepted")
  public List<ListToBeAcceptedResponse> getAllToBeAcceptedUsers() {
    List<Object[]> adminList = adminRepository.findAllToBeAcceptedUsers();
    List<ListToBeAcceptedResponse> responseList = new ArrayList<>();

    for (Object[] admin : adminList) {
      String email = (String) admin[0];
      String firstname = (String) admin[1];
      String surname = (String) admin[2];
      Role role = (Role) admin[3];
      String rolename = role.name();

      ListToBeAcceptedResponse response = ListToBeAcceptedResponse.builder()
          .email(email)
          .firstname(firstname)
          .surname(surname)
          .role(rolename)
          .build();

      responseList.add(response);
    }

    return responseList;

  }
}

