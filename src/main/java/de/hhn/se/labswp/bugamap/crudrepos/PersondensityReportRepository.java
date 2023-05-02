package de.hhn.se.labswp.bugamap.crudrepos;

import de.hhn.se.labswp.bugamap.jpa.Persondensityreport;
import org.springframework.data.repository.CrudRepository;


/**
 * Repository interface for persondensityReport table.
 */
public interface PersondensityReportRepository
    extends CrudRepository<Persondensityreport, Integer> {

}
