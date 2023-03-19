package de.hhn.se.labswp.buga23test.crudRepos;

import de.hhn.se.labswp.buga23test.jpa.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Integer> {

}
