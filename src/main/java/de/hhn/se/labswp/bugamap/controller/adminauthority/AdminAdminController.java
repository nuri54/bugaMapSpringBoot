package de.hhn.se.labswp.bugamap.controller.adminauthority;

import de.hhn.se.labswp.bugamap.crudrepos.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/api/v1/admin")
@RequiredArgsConstructor
public class AdminAdminController {

  private final AdminRepository adminRepository;

}
