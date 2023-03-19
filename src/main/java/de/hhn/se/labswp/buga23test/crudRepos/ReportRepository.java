package de.hhn.se.labswp.buga23test.crudRepos;

import de.hhn.se.labswp.buga23test.jpa.Report;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends CrudRepository <Report, Integer> {

}
