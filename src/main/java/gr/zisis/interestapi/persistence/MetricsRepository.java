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

	@Query(value = "SELECT new gr.zisis.interestapi.controller.response.entity.CumulativeInterest(m.sha, m.revisionCount, ROUND(SUM(m.interestEu), 2), ROUND(SUM(m.interestHours), 1)) "
			+ "FROM Metrics m "
			+ "WHERE m.pid = (SELECT pid FROM Projects WHERE owner = ?1 AND repo = ?2) GROUP BY m.sha, m.revisionCount ORDER BY m.revisionCount")
	Collection<CumulativeInterest> findCumulativeInterestPerCommit(String owner, String repo);

	@Query(value = "SELECT new gr.zisis.interestapi.controller.response.entity.CumulativeInterest(m.sha, m.revisionCount, SUM(m.interestEu), SUM(m.interestHours)) "
			+ "FROM Metrics m "
			+ "WHERE m.pid = (SELECT pid FROM Projects WHERE owner = ?1 AND repo = ?2) AND m.sha = ?3 GROUP BY m.sha, m.revisionCount")
	Collection<CumulativeInterest> findCumulativeInterest(String owner, String repo, String sha);

	@Query(value = "SELECT new gr.zisis.interestapi.controller.response.entity.InterestPerCommitFile(m.sha, f.filePath, m.revisionCount, m.interestEu, m.interestHours) "
			+ "FROM Metrics m JOIN Files f ON m.fid = f.fid AND m.sha = f.sha "
			+ "WHERE m.pid = (SELECT pid FROM Projects WHERE owner = ?1 AND repo = ?2) AND m.sha = ?3 AND f.filePath = ?4 ORDER BY f.filePath")
	Collection<InterestPerCommitFile> findInterestPerCommitFile(String owner, String repo, String sha, String filePath);

	@Query(value = "SELECT new gr.zisis.interestapi.controller.response.entity.InterestChangePerCommit(m.sha, m.revisionCount, SUM(m.interestEu) - (SELECT SUM(m2.interestEu) FROM Metrics m2 WHERE m2.pid = (SELECT pid FROM Projects WHERE owner = ?1 AND repo = ?2) AND m2.revisionCount = m.revisionCount-1), SUM(m.interestHours) - (SELECT SUM(m2.interestHours) FROM Metrics m2 WHERE m2.pid = (SELECT pid FROM Projects WHERE owner = ?1 AND repo = ?2) AND m2.revisionCount = m.revisionCount-1), (SUM(m.interestEu) - (SELECT SUM(m2.interestEu) FROM Metrics m2 WHERE m2.pid = (SELECT pid FROM Projects WHERE owner = ?1 AND repo = ?2) AND m2.revisionCount = m.revisionCount-1))/SUM(m.interestEu))"
			+ "FROM Metrics m "
			+ "WHERE m.pid = (SELECT pid FROM Projects WHERE owner = ?1 AND repo = ?2) AND m.sha = ?3 GROUP BY m.sha, m.revisionCount")
	Collection<InterestChangePerCommit> findInterestChangePerCommit(String owner, String repo, String sha);

	@Query(value = "SELECT new gr.zisis.interestapi.controller.response.entity.NormalizedInterest(m.sha, m.revisionCount, SUM(m.interestEu)/SUM(m.size1), SUM(m.interestHours)/SUM(m.size1)) "
			+ "FROM Metrics m "
			+ "WHERE m.pid = (SELECT pid FROM Projects WHERE owner = ?1 AND repo = ?2) GROUP BY m.sha, m.revisionCount HAVING SUM(m.size1) <> 0 ORDER BY m.revisionCount")
	Collection<NormalizedInterest> findNormalizedInterest(String owner, String repo);

	@Query(value = "SELECT new gr.zisis.interestapi.controller.response.entity.NormalizedInterest(m.sha, m.revisionCount, SUM(m.interestEu)/SUM(m.size1), SUM(m.interestHours)/SUM(m.size1)) "
			+ "FROM Metrics m "
			+ "WHERE m.pid = (SELECT pid FROM Projects WHERE owner = ?1 AND repo = ?2) AND sha = ?3 GROUP BY m.sha, m.revisionCount HAVING SUM(m.size1) <> 0")
	Collection<NormalizedInterest> findNormalizedInterestPerCommit(String owner, String repo, String sha);

	@Query(value = "SELECT new gr.zisis.interestapi.controller.response.entity.HighInterestFile(m.sha, m.revisionCount, f.filePath, m.interestEu, m.interestHours, m.interestEu/(SELECT SUM(m2.interestEu) FROM Metrics m2 WHERE m2.pid = (SELECT pid FROM Projects WHERE owner = ?1 AND repo = ?2) AND m2.sha = ?3)) "
			+ "FROM Metrics m JOIN Files f ON m.pid = f.pid AND m.fid = f.fid AND m.sha = f.sha "
			+ "WHERE m.pid = (SELECT pid FROM Projects WHERE owner = ?1 AND repo = ?2) AND m.sha = ?3 GROUP BY m.sha, m.revisionCount, f.filePath, m.interestEu, m.interestHours ORDER BY m.interestEu DESC")
	Slice<HighInterestFile> findHighInterestFiles(Pageable pageable, String owner, String repo, String sha);

	@Query(value = "SELECT new gr.zisis.interestapi.controller.response.entity.ReusabilityMetrics(m.sha, m.revisionCount, f.filePath, m.cbo, m.dit, m.wmc, m.rfc, m.lcom, m.nocc) "
			+ "FROM Metrics m JOIN Files f ON m.pid = f.pid AND m.fid = f.fid AND m.sha = f.sha "
			+ "WHERE m.pid = (SELECT pid FROM Projects WHERE owner = ?1 AND repo = ?2) ORDER BY f.filePath")
	Slice<ReusabilityMetrics> findReusabilityMetrics(Pageable pageable, String owner, String repo);

	@Query(value = "SELECT new gr.zisis.interestapi.controller.response.entity.ReusabilityMetrics(m.sha, m.revisionCount, f.filePath, m.cbo, m.dit, m.wmc, m.rfc, m.lcom, m.nocc) "
			+ "FROM Metrics m JOIN Files f ON m.pid = f.pid AND m.fid = f.fid AND m.sha = f.sha "
			+ "WHERE m.pid = (SELECT pid FROM Projects WHERE owner = ?1 AND repo = ?2) AND m.sha = ?3 ORDER BY f.filePath")
	Slice<ReusabilityMetrics> findReusabilityMetrics(Pageable pageable, String owner, String repo, String sha);

	@Query(value = "SELECT new gr.zisis.interestapi.controller.response.entity.ReusabilityMetrics(m.sha, m.revisionCount, f.filePath, m.cbo, m.dit, m.wmc, m.rfc, m.lcom, m.nocc) "
			+ "FROM Metrics m JOIN Files f ON m.pid = f.pid AND m.fid = f.fid AND m.sha = f.sha "
			+ "WHERE m.pid = (SELECT pid FROM Projects WHERE owner = ?1 AND repo = ?2) AND m.sha = ?3 AND f.filePath = ?4 ORDER BY f.filePath")
	Slice<ReusabilityMetrics> findReusabilityMetrics(Pageable pageable, String owner, String repo, String sha, String filePath);

	@Query(value = "SELECT DISTINCT new gr.zisis.interestapi.controller.response.entity.AnalyzedCommits(m.sha, m.revisionCount) "
			+ "FROM Metrics m "
			+ "WHERE m.pid = (SELECT pid FROM Projects WHERE owner = ?1 AND repo = ?2) ORDER BY m.revisionCount DESC")
	Slice<AnalyzedCommits> findAnalyzedCommits(Pageable pageable, String owner, String repo);

}
