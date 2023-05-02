package de.hhn.se.labswp.bugamap.crudrepos;

import de.hhn.se.labswp.bugamap.jpa.Park;
import org.springframework.data.repository.CrudRepository;


/**
 * Repository interface for park table.
 */
public interface ParkRepository extends CrudRepository<Park, Integer> {


}
