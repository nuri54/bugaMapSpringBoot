package de.hhn.se.labswp.bugaMap.controller.adminauthority;

import de.hhn.se.labswp.bugaMap.crudRepos.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/api/v1/admin")
@RequiredArgsConstructor
public class AdminAdminController {

  private final AdminRepository adminRepository;







}
