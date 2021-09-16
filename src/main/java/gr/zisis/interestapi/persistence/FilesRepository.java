package gr.zisis.interestapi.persistence;

import gr.zisis.interestapi.domain.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Dimitrios Zisis <zisisndimitris@gmail.com>
 */
@Repository
public interface FilesRepository extends JpaRepository<Files, Long> {
}
