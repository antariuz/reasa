package avada.spacelab.reasa.repo;

import avada.spacelab.reasa.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepo extends JpaRepository<Language, Long> {
}
