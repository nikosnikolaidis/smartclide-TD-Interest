package gr.zisis.interestapi.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gr.zisis.interestapi.domain.Projects;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
@Repository
public interface ProjectsRepository extends JpaRepository<Projects, Integer> {

}
