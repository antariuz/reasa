package avada.spacelab.reasa.repo;

import avada.spacelab.reasa.model.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GalleryRepo extends JpaRepository<Gallery, Long> {
}
