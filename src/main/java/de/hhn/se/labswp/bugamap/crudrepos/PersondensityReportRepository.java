package de.hhn.se.labswp.bugamap.crudrepos;

import de.hhn.se.labswp.bugamap.jpa.Persondensityreport;
import java.time.Instant;
import java.util.List;
import org.springframework.data.repository.CrudRepository;


/**
 * Repository interface for persondensityReport table.
 */
public interface PersondensityReportRepository
    extends CrudRepository<Persondensityreport, Integer> {
  List<Persondensityreport> findByValidtillGreaterThan(Instant validtill);

  @Override
  void deleteById(Integer integer);
}
