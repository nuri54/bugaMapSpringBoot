package de.hhn.se.labswp.bugamap.crudrepos;

import de.hhn.se.labswp.bugamap.jpa.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Repository interface for report table.
 */
@Repository
public interface ReportRepository extends CrudRepository<Report, Integer> {

  List<Report> findByAdminEmail(String adminEmail);

  List<Report> findAll();

  List<Report> findAllByAdminEmailNull();


  @Override
  Optional<Report> findById(Integer integer);
}
