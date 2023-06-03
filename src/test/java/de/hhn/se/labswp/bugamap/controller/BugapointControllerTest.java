package de.hhn.se.labswp.bugamap.controller;

import de.hhn.se.labswp.bugamap.controller.config.BaseTest;
import de.hhn.se.labswp.bugamap.crudrepos.BugapointRepository;
import de.hhn.se.labswp.bugamap.crudrepos.ParkRepository;
import de.hhn.se.labswp.bugamap.jpa.Admin;
import de.hhn.se.labswp.bugamap.jpa.Bugapoint;
import de.hhn.se.labswp.bugamap.jpa.Park;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;


public class BugapointControllerTest extends BaseTest {

  @Autowired
  private BugapointRepository bugapointRepository;

  @Autowired
  private ParkRepository parkRepository;

  @Test
  void saveTest() {

    Bugapoint bugapoint = new Bugapoint();

  }

  @Test
  void parkTest() {
    Park testLuisen = parkRepository.findById(1).orElse(null);

    assertThat(testLuisen.getTitle()).isEqualTo("Luisenpark");
  }


}
