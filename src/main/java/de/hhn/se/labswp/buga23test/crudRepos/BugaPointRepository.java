package de.hhn.se.labswp.buga23test.crudRepos;

import de.hhn.se.lapswp.buga23testbycicle.model.BugaPoint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugaPointRepository extends CrudRepository<BugaPoint,Long> {

}
