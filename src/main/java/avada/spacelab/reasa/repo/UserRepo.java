package avada.spacelab.reasa.repo;

import avada.spacelab.reasa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    boolean existsUserByEmail(String email);

}
