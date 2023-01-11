package avada.spacelab.reasa.repo;

import avada.spacelab.reasa.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepo extends JpaRepository<Owner, Long> {

    Optional<Owner> getOwnerByEmail(String email);

}
