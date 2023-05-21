package de.hhn.se.labswp.bugamap.crudrepos;

import de.hhn.se.labswp.bugamap.jpa.Admin;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for admin table.
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

  Optional<Admin> findByEmailadress(String email);

  @Query("SELECT a.emailadress, a.firstname, a.lastname, a.role FROM Admin a WHERE a.role = 'TOBEACCEPTED'")
  List<Object[]> findAllToBeAcceptedUsers();
}
