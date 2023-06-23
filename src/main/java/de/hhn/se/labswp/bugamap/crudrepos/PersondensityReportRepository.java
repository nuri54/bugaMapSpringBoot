package de.hhn.se.labswp.bugamap.crudrepos;

import de.hhn.se.labswp.bugamap.jpa.Persondensityreport;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Repository interface for persondensityReport table.
 */
public interface PersondensityReportRepository
    extends CrudRepository<Persondensityreport, Integer> {

  @Transactional
  @Modifying
  @Query("delete from Persondensityreport p where p.bugaPointID = ?1")
  void deleteAllWithBugapointID(int bugaPointID);

  List<Persondensityreport> findByValidtillGreaterThan(Instant validtill);

  @Override
  void deleteById(Integer integer);
}
