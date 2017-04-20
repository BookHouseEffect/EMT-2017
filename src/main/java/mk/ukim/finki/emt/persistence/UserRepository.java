package mk.ukim.finki.emt.persistence;

import mk.ukim.finki.emt.model.jpa.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author CekovskiMrGjorche
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
