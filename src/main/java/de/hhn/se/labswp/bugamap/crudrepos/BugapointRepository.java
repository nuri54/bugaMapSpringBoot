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

  @Transactional
  @Modifying
  @Query("update Bugapoint b set b.parkID = ?1 where b.id = ?2")
  int updateParkIDById(int parkID, Integer id);

  @Transactional
  @Modifying
  @Query("update Bugapoint b set b.adminID = ?1 where b.id = ?2")
  int updateAdminIDById(int adminID, Integer id);

  @Transactional
  @Modifying
  @Query("update Bugapoint b set b.latitude = ?1 where b.id = ?2")
  int updateLatitudeById(Double latitude, Integer id);

  @Transactional
  @Modifying
  @Query("update Bugapoint b set b.longitude = ?1 where b.id = ?2")
  int updateLongitudeById(Double longitude, Integer id);

  @Transactional
  @Modifying
  @Query("update Bugapoint b set b.discriminator = ?1 where b.id = ?2")
  int updateDiscriminatorById(String discriminator, Integer id);

  @Transactional
  @Modifying
  @Query("update Bugapoint b set b.description = ?1 where b.id = ?2")
  int updateDescriptionById(String description, Integer id);


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
