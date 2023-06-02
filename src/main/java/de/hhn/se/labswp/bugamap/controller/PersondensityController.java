package de.hhn.se.labswp.bugamap.controller;


import de.hhn.se.labswp.bugamap.crudrepos.PersondensityReportRepository;
import de.hhn.se.labswp.bugamap.jpa.Persondensityreport;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1/open/pdr")
@CrossOrigin(origins = "http://localhost:4200")
public class PersondensityController {

  private final PersondensityReportRepository persondensityReportRepository;

  public PersondensityController(PersondensityReportRepository persondensityReportRepository) {
    this.persondensityReportRepository = persondensityReportRepository;
  }


  @GetMapping("/list")
  public List<Persondensityreport> getPersondensityreports(@RequestParam(required = false) Map<String, String> query) {

    return (List<Persondensityreport>) persondensityReportRepository.findAll();
  }
}
