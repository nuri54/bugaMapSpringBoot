package de.hhn.se.labswp.bugamap.crudrepos;

import de.hhn.se.labswp.bugamap.jpa.Report;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for report table.
 */
@Repository
public interface ReportRepository extends CrudRepository<Report, Integer> {

}
