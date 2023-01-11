package avada.spacelab.reasa.repo;

import avada.spacelab.reasa.model.RealEstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealEstateRepo extends JpaRepository<RealEstate, Long> {
}
