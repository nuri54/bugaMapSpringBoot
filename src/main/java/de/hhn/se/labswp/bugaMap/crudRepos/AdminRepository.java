package de.hhn.se.labswp.bugaMap.crudRepos;

import de.hhn.se.labswp.bugaMap.jpa.Admin;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

  Optional<Admin> findByEmailadress(String email);
}
