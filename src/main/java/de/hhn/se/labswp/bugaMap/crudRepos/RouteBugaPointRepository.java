package de.hhn.se.labswp.bugaMap.crudRepos;

import de.hhn.se.labswp.bugaMap.jpa.RouteBugapoint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteBugaPointRepository extends CrudRepository<RouteBugapoint, Integer> {

}
