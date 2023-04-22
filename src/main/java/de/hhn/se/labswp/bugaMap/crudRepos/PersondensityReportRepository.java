package de.hhn.se.labswp.bugaMap.crudRepos;

import de.hhn.se.labswp.bugaMap.jpa.Persondensityreport;
import org.springframework.data.repository.CrudRepository;


/**
 * Repository interface for persondensityReport table.
 */
public interface PersondensityReportRepository
    extends CrudRepository<Persondensityreport, Integer> {

}
