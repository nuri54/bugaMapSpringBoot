package de.hhn.se.labswp.bugaMap.crudRepos;

import de.hhn.se.labswp.bugaMap.jpa.Park;
import org.springframework.data.repository.CrudRepository;


/**
 * Repository interface for park table.
 */
public interface ParkRepository extends CrudRepository<Park, Integer> {

}
