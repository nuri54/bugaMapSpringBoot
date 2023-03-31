package de.hhn.se.labswp.bugaMap.crudRepos;

import de.hhn.se.labswp.bugaMap.jpa.TicketRoute;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRouteRepository extends CrudRepository<TicketRoute,Integer> {

}
