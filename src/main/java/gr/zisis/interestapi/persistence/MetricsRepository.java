package gr.zisis.interestapi.persistence;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gr.zisis.interestapi.controller.response.entity.CumulativeInterest;
import gr.zisis.interestapi.controller.response.entity.CumulativeInterestPerCommit;
import gr.zisis.interestapi.domain.Metrics;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
@Repository
public interface MetricsRepository extends JpaRepository<Metrics, Integer> {

	//	@Query(value = "SELECT new gr.zisis.interestapi.controller.response.entity.CumulativeInterest(sha, revision_count, SUM(interest_eu)) "
	//			+ "FROM metrics "
	//			+ "WHERE pid = (SELECT pid FROM projects WHERE url LIKE ?1) GROUP BY sha, revision_count", nativeQuery = true)
	@Query(value = "SELECT new gr.zisis.interestapi.controller.response.entity.CumulativeInterest(m.sha, m.revisionCount, SUM(m.interestEu)) "
			+ "FROM Metrics m "
			+ "WHERE m.pid = (SELECT p.pid FROM Projects p WHERE p.url LIKE ?1) GROUP BY m.sha, m.revisionCount")
	Collection<CumulativeInterest> findCumulativeInterest(String url);

	@Query(value = "SELECT new gr.zisis.interestapi.controller.response.entity.CumulativeInterestPerCommit(m.sha, m.revisionCount, SUM(m.interestEu)) "
			+ "FROM Metrics m "
			+ "WHERE m.pid = (SELECT p.pid FROM Projects p WHERE p.url LIKE ?1) AND m.sha = ?2 GROUP BY m.sha, m.revisionCount")
	Collection<CumulativeInterestPerCommit> findCumulativeInterestPerCommit(String url, String sha);

}
