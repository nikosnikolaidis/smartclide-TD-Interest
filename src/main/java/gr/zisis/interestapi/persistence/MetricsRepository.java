package gr.zisis.interestapi.persistence;

import java.util.Collection;

import gr.zisis.interestapi.controller.response.entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gr.zisis.interestapi.domain.Metrics;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
@Repository
public interface MetricsRepository extends JpaRepository<Metrics, Integer> {

	@Query(value = "SELECT new gr.zisis.interestapi.controller.response.entity.CumulativeInterest(m.sha, m.revisionCount, SUM(m.interestEu)) "
			+ "FROM Metrics m "
			+ "WHERE m.pid = (SELECT p.pid FROM Projects p WHERE p.url LIKE ?1) GROUP BY m.sha, m.revisionCount ORDER BY m.revisionCount")
	Collection<CumulativeInterest> findCumulativeInterestPerCommit(String url);

	@Query(value = "SELECT new gr.zisis.interestapi.controller.response.entity.CumulativeInterest(m.sha, m.revisionCount, SUM(m.interestEu)) "
			+ "FROM Metrics m "
			+ "WHERE m.pid = (SELECT p.pid FROM Projects p WHERE p.url LIKE ?1) AND m.sha = ?2 GROUP BY m.sha, m.revisionCount")
	Collection<CumulativeInterest> findCumulativeInterest(String url, String sha);

	@Query(value = "SELECT new gr.zisis.interestapi.controller.response.entity.InterestPerCommitFile(m.sha, m.filePath, m.revisionCount, m.interestEu) "
			+ "FROM Metrics m "
			+ "WHERE m.pid = (SELECT p.pid FROM Projects p WHERE p.url LIKE ?1) AND m.sha = ?3 AND m.filePath = ?2 ORDER BY m.filePath")
	Collection<InterestPerCommitFile> findInterestPerCommitFile(String url, String filePath, String sha);

	@Query(value = "SELECT new gr.zisis.interestapi.controller.response.entity.InterestChangePerCommit(m.sha, m.revisionCount, SUM(m.interestEu) - (SELECT SUM(m2.interestEu) FROM Metrics m2 WHERE m2.pid = (SELECT p.pid FROM Projects p WHERE p.url LIKE ?1) AND m2.revisionCount = m.revisionCount-1), (SUM(m.interestEu) - (SELECT SUM(m2.interestEu) FROM Metrics m2 WHERE m2.pid = (SELECT p.pid FROM Projects p WHERE p.url LIKE ?1) AND m2.revisionCount = m.revisionCount-1))/SUM(m.interestEu))"
			+ "FROM Metrics m "
			+ "WHERE m.pid = (SELECT p.pid FROM Projects p WHERE p.url LIKE ?1) AND m.sha = ?2 GROUP BY m.sha, m.revisionCount")
	Collection<InterestChangePerCommit> findInterestChangePerCommit(String url, String sha);

	@Query(value = "SELECT new gr.zisis.interestapi.controller.response.entity.NormalizedInterest(m.sha, m.revisionCount, SUM(m.interestEu)/SUM(m.size1)) "
			+ "FROM Metrics m "
			+ "WHERE m.pid = (SELECT p.pid FROM Projects p WHERE p.url LIKE ?1) GROUP BY m.sha, m.revisionCount")
	Collection<NormalizedInterest> findNormalizedInterest(String url);

	@Query(value = "SELECT new gr.zisis.interestapi.controller.response.entity.NormalizedInterest(m.sha, m.revisionCount, SUM(m.interestEu)/SUM(m.size1)) "
			+ "FROM Metrics m "
			+ "WHERE m.pid = (SELECT p.pid FROM Projects p WHERE p.url LIKE ?1) AND sha = ?2 GROUP BY m.sha, m.revisionCount")
	Collection<NormalizedInterest> findNormalizedInterestPerCommit(String url, String sha);

	@Query(value = "SELECT new gr.zisis.interestapi.controller.response.entity.HighInterestFile(m.sha, m.revisionCount, m.filePath, m.interestEu, m.interestEu/(SELECT SUM(m2.interestEu) FROM Metrics m2 WHERE m2.pid = (SELECT p.pid FROM Projects p WHERE p.url LIKE ?1) AND m2.sha = ?2)) "
			+ "FROM Metrics m "
			+ "WHERE m.pid = (SELECT p.pid FROM Projects p WHERE p.url LIKE ?1) AND sha = ?2 GROUP BY m.sha, m.revisionCount, m.filePath, m.interestEu ORDER BY m.interestEu DESC")
	Slice<HighInterestFile> findHighInterestFiles(Pageable pageable, String url, String sha);

}
