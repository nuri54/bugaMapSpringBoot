package de.hhn.se.labswp.bugaMap.crudRepos;

import de.hhn.se.labswp.bugaMap.jpa.Bugapoint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugapointRepository extends CrudRepository<Bugapoint,Integer> {

}
