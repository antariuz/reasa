package avada.spacelab.reasa.repo;

import avada.spacelab.reasa.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByToken(String token);
}
