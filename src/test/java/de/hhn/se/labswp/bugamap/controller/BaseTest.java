package de.hhn.se.labswp.bugamap.controller;

import de.hhn.se.labswp.bugamap.crudrepos.AdminRepository;
import de.hhn.se.labswp.bugamap.crudrepos.BugapointRepository;
import de.hhn.se.labswp.bugamap.crudrepos.ParkRepository;
import de.hhn.se.labswp.bugamap.jpa.Admin;
import de.hhn.se.labswp.bugamap.jpa.Bugapoint;
import de.hhn.se.labswp.bugamap.jpa.Park;
import de.hhn.se.labswp.bugamap.security.auth.SecurityTestConfig;
import de.hhn.se.labswp.bugamap.security.auth.roles.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import static org.assertj.core.api.Assertions.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) //DB neu vor jedem Test
@DataJpaTest
@Import(SecurityTestConfig.class)
public class BaseTest {


  @Autowired
  private ParkRepository parkRepository;

  @Autowired
  private AdminRepository adminRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private BugapointRepository bugapointRepository;

  /**
   * Loads in dummy data.
   */
  @BeforeEach
  public void setup() {

    Park luisenpark = new Park();
    luisenpark.setId(1);
    luisenpark.setLatitude(49.48370207506015);
    luisenpark.setLongitude(8.495712125745413);
    luisenpark.setTitle("Luisenpark");

    Park spinellipark = new Park();
    spinellipark.setId(2);
    spinellipark.setLatitude(49.500406144069565);
    spinellipark.setLongitude(8.52182741864783);
    spinellipark.setTitle("Spinelli");

    parkRepository.save(luisenpark);
    parkRepository.save(spinellipark);


    Admin superAdmin = new Admin();
    superAdmin.setId(1);
    superAdmin.setFirstname("Max");
    superAdmin.setLastname("Mustermann");
    superAdmin.setEmailadress("maxmustermann@buga23.com");
    superAdmin.setPassword(passwordEncoder.encode("supersecure"));
    superAdmin.setRole(Role.ADMIN);

    adminRepository.save(superAdmin);

    Bugapoint haupteingangLuisenpark = Bugapoint.builder()
        .id(1)
        .title("Haupteingang Luisenpark")
        .adminID(1)
        .latitude(49.47935742)
        .longitude(8.49593039)
        .discriminator("Eingang & Ausgang")
        .parkID(1)
        .build();

    bugapointRepository.save(haupteingangLuisenpark);

    Bugapoint swrStudio = Bugapoint.builder()
        .id(2)
        .adminID(1)
        .title("SWR Studio")
        .latitude(49.49613577872157)
        .longitude(8.516308888163922)
        .discriminator("Klimapark")
        .parkID(2)
        .build();

    bugapointRepository.save(swrStudio);
  }


  /**
   * Tests all the insertions from setup()
   */
  @Test
  void testInsertions() {
    Park luisenparkExpected = parkRepository.findById(1).get();
    assertThat(luisenparkExpected.getTitle()).isEqualTo("Luisenpark");

    Park expectSpinelli = parkRepository.findById(2).get();
    assertThat(expectSpinelli.getTitle()).isEqualTo("Spinelli");

    Admin expectSuperAdmin = adminRepository.findById(1).get();
    assertThat(expectSuperAdmin.getEmailadress()).isEqualTo("maxmustermann@buga23.com");

    Bugapoint expectHaupteingangLuisenpark = bugapointRepository.findById(1).get();
    assertThat(expectHaupteingangLuisenpark.getTitle()).isEqualTo("Haupteingang Luisenpark");

    Bugapoint expectSwrStudio = bugapointRepository.findById(2).get();
    assertThat(expectSwrStudio.getTitle()).isEqualTo("SWR Studio");
  }


}
