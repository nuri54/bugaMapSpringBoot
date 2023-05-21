package de.hhn.se.labswp.bugamap.controller.adminauthority;

import de.hhn.se.labswp.bugamap.controller.adminauthority.requestbodies.ApproveRequest;
import de.hhn.se.labswp.bugamap.controller.adminauthority.responses.ListToBeAcceptedResponse;
import de.hhn.se.labswp.bugamap.security.auth.Roles.Role;
import de.hhn.se.labswp.bugamap.crudrepos.AdminRepository;
import de.hhn.se.labswp.bugamap.jpa.Admin;
import de.hhn.se.labswp.bugamap.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/api/v1/admin")
@RequiredArgsConstructor
public class AdminApproveController {
    private final static Logger logger = LogManager.getLogger(JwtService.class);
    private final AdminRepository adminRepository;

    @PutMapping("/approve")
    public void approveUserWithEmail(
            @RequestBody ApproveRequest userEmail) {

        Optional<Admin> adminFound = adminRepository.findByEmailadress(userEmail.getEmail());
        if (adminFound.isPresent()) {
            Admin admin = adminFound.get();
            admin.setRole(Role.MANAGER);
            adminRepository.save(admin);
            logger.info("Role for " + userEmail.getEmail() + " updated to Manager");
        } else
            logger.info("Update Role for User failed because User not found: " + userEmail.getEmail() + " set role to Manager");

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

