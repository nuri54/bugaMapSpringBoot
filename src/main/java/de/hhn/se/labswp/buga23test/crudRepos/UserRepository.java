package de.hhn.se.labswp.buga23test.crudRepos;

import de.hhn.se.labswp.buga23test.jpa.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

}
