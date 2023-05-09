package de.hhn.se.labswp.bugamap.crudrepos;

import de.hhn.se.labswp.bugamap.jpa.Bugapoint;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Repository interface for bugapoint table.
 */
@Repository
public interface BugapointRepository extends CrudRepository<Bugapoint, Integer> {

  List<Bugapoint> findByParkID(int parkID);



  @Transactional
  @Modifying
  @Query("""
      update Bugapoint b set b.latitude = ?1, b.longitude = ?2, b.description = ?3, b.adminID = ?4
      where b.id in ?5""")
  int updateLatitudeAndLongitudeAndDescriptionAndAdminIDByIdIn(Double latitude, Double longitude,
      String description, int adminID, int id);

  @Override
  void deleteById(Integer integer);
}
