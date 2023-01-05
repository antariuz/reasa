package avada.spacelab.reasa.repo;

import avada.spacelab.reasa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    boolean existsUserByEmail(String email);

    Optional<UserDetails> findByEmail(String email);
}
