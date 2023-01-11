package avada.spacelab.reasa.repo;

import avada.spacelab.reasa.model.HelpCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelpCenterRepo extends JpaRepository<HelpCenter, Long> {
}
