package de.hhn.se.labswp.bugaMap.crudRepos;

import de.hhn.se.labswp.bugaMap.jpa.User;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

  List<User> findByDiscriminatorLikeIgnoreCase(String discriminator);

}
