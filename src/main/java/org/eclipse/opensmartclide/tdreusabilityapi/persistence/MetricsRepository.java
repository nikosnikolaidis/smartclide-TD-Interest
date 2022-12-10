/*******************************************************************************
 * Copyright (C) 2021-2022 University of Macedonia
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.opensmartclide.tdreusabilityapi.persistence;

import java.util.Collection;

import org.eclipse.opensmartclide.tdreusabilityapi.domain.ProjectDomain;
import org.eclipse.opensmartclide.tdreusabilityapi.controller.response.entity.*;
import org.eclipse.opensmartclide.tdreusabilityapi.domain.Metrics;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
@Repository
public interface MetricsRepository extends JpaRepository<Metrics, Long> {

	@Query(value = "SELECT new org.eclipse.opensmartclide.tdreusabilityapi.controller.response.entity.CumulativeInterest(m.sha, m.revisionCount, ROUND(SUM(m.interestEu), 2), ROUND(SUM(m.interestHours), 1)) "
			+ "FROM Metrics m "
			+ "WHERE m.pid = (SELECT pid FROM Projects WHERE owner = :#{#project.owner} AND repo = :#{#project.repo}) GROUP BY m.sha, m.revisionCount ORDER BY m.revisionCount")
	Collection<CumulativeInterest> findCumulativeInterestPerCommit(ProjectDomain project);

	@Query(value = "SELECT new org.eclipse.opensmartclide.tdreusabilityapi.controller.response.entity.CumulativeInterest(m.sha, m.revisionCount, SUM(m.interestEu), SUM(m.interestHours)) "
			+ "FROM Metrics m "
			+ "WHERE m.pid = (SELECT pid FROM Projects WHERE owner = :#{#project.owner} AND repo = :#{#project.repo}) AND m.sha = :sha GROUP BY m.sha, m.revisionCount")
	Collection<CumulativeInterest> findCumulativeInterestByCommit(ProjectDomain project, @Param("sha") String sha);

	@Query(value = "SELECT new org.eclipse.opensmartclide.tdreusabilityapi.controller.response.entity.InterestPerCommitFile(m.sha, f.filePath, m.revisionCount, m.interestEu, m.interestHours) "
			+ "FROM Metrics m JOIN Files f ON m.fid = f.fid AND m.sha = f.sha "
			+ "WHERE m.pid = (SELECT pid FROM Projects WHERE owner = :#{#project.owner} AND repo = :#{#project.repo}) AND m.sha = :sha AND f.filePath = :filePath ORDER BY f.filePath")
	Collection<InterestPerCommitFile> findInterestPerCommitFile(ProjectDomain project, @Param("sha") String sha, @Param("filePath") String filePath);

	@Query(value = "SELECT new org.eclipse.opensmartclide.tdreusabilityapi.controller.response.entity.InterestChange(m.sha, m.revisionCount, SUM(m.interestEu) - (SELECT SUM(m2.interestEu) FROM Metrics m2 WHERE m2.pid = (SELECT pid FROM Projects WHERE owner = :#{#project.owner} AND repo = :#{#project.repo}) AND m2.revisionCount = m.revisionCount-1), SUM(m.interestHours) - (SELECT SUM(m2.interestHours) FROM Metrics m2 WHERE m2.pid = (SELECT pid FROM Projects WHERE owner = :#{#project.owner} AND repo = :#{#project.repo}) AND m2.revisionCount = m.revisionCount-1), (SUM(m.interestEu) - (SELECT SUM(m2.interestEu) FROM Metrics m2 WHERE m2.pid = (SELECT pid FROM Projects WHERE owner = :#{#project.owner} AND repo = :#{#project.repo}) AND m2.revisionCount = m.revisionCount-1))/(SELECT SUM(m3.interestEu) FROM Metrics m3 WHERE m3.pid = (SELECT pid FROM Projects WHERE owner = :#{#project.owner} AND repo = :#{#project.repo}) AND m3.revisionCount = m.revisionCount-1))"
			+ "FROM Metrics m "
			+ "WHERE m.pid = (SELECT pid FROM Projects WHERE owner = :#{#project.owner} AND repo = :#{#project.repo}) AND m.sha = :sha GROUP BY m.sha, m.revisionCount")
	Collection<InterestChange> findInterestChangeByCommit(ProjectDomain project, @Param("sha") String sha);

	@Query(value = "SELECT new org.eclipse.opensmartclide.tdreusabilityapi.controller.response.entity.FileInterestChange(m.sha, m.revisionCount, f.filePath, m.interestEu - (SELECT m2.interestEu FROM Metrics m2 JOIN Files f2 ON m2.fid = f2.fid WHERE m2.pid = (SELECT pid FROM Projects WHERE owner = :#{#project.owner} AND repo = :#{#project.repo}) AND f.filePath = f2.filePath AND m2.revisionCount = m.revisionCount-1), m.interestHours - (SELECT m3.interestHours FROM Metrics m3 JOIN Files f3 ON m3.fid = f3.fid WHERE m3.pid = (SELECT pid FROM Projects WHERE owner = :#{#project.owner} AND f.filePath = f3.filePath AND repo = :#{#project.repo}) AND m3.revisionCount = m.revisionCount-1), (m.interestEu - (SELECT m4.interestEu FROM Metrics m4 JOIN Files f4 ON m4.fid = f4.fid WHERE m4.pid = (SELECT pid FROM Projects WHERE owner = :#{#project.owner} AND repo = :#{#project.repo}) AND f.filePath = f4.filePath AND m4.revisionCount = m.revisionCount-1))/NULLIF((SELECT m5.interestEu FROM Metrics m5 JOIN Files f5 ON m5.fid = f5.fid WHERE m5.pid = (SELECT pid FROM Projects WHERE owner = :#{#project.owner} AND repo = :#{#project.repo}) AND f.filePath = f5.filePath AND m5.revisionCount = m.revisionCount-1),0))"
			+ "FROM Metrics m JOIN Files f ON m.fid = f.fid AND m.sha = f.sha "
			+ "WHERE m.pid = (SELECT pid FROM Projects WHERE owner = :#{#project.owner} AND repo = :#{#project.repo}) AND m.sha = :sha AND f.filePath = :filePath")
    FileInterestChange findInterestChangeByCommitAndFile(ProjectDomain project, @Param("sha") String sha, @Param("filePath") String filePath);

	@Query(value = "SELECT new org.eclipse.opensmartclide.tdreusabilityapi.controller.response.entity.NormalizedInterest(m.sha, m.revisionCount, SUM(m.interestEu)/SUM(m.size1), SUM(m.interestHours)/SUM(m.size1)) "
			+ "FROM Metrics m "
			+ "WHERE m.pid = (SELECT pid FROM Projects WHERE owner = :#{#project.owner} AND repo = :#{#project.repo}) GROUP BY m.sha, m.revisionCount HAVING SUM(m.size1) <> 0 ORDER BY m.revisionCount")
	Collection<NormalizedInterest> findNormalizedInterest(ProjectDomain project);

	@Query(value = "SELECT new org.eclipse.opensmartclide.tdreusabilityapi.controller.response.entity.NormalizedInterest(m.sha, m.revisionCount, SUM(m.interestEu)/SUM(m.size1), SUM(m.interestHours)/SUM(m.size1)) "
			+ "FROM Metrics m "
			+ "WHERE m.pid = (SELECT pid FROM Projects WHERE owner = :#{#project.owner} AND repo = :#{#project.repo}) AND sha = :sha GROUP BY m.sha, m.revisionCount HAVING SUM(m.size1) <> 0")
	Collection<NormalizedInterest> findNormalizedInterestByCommit(ProjectDomain project, @Param("sha") String sha);

	@Query(value = "SELECT new org.eclipse.opensmartclide.tdreusabilityapi.controller.response.entity.HighInterestFile(m.sha, m.revisionCount, f.filePath, m.interestEu, m.interestHours, m.interestEu/(SELECT SUM(m2.interestEu) FROM Metrics m2 WHERE m2.pid = (SELECT pid FROM Projects WHERE owner = :#{#project.owner} AND repo = :#{#project.repo}) AND m2.sha = :sha)) "
			+ "FROM Metrics m JOIN Files f ON m.pid = f.pid AND m.fid = f.fid AND m.sha = f.sha "
			+ "WHERE m.pid = (SELECT pid FROM Projects WHERE owner = :#{#project.owner} AND repo = :#{#project.repo}) AND m.sha = :sha GROUP BY m.sha, m.revisionCount, f.filePath, m.interestEu, m.interestHours ORDER BY m.interestEu DESC")
	Slice<HighInterestFile> findHighInterestFiles(Pageable pageable, ProjectDomain project, @Param("sha") String sha);

	@Query(value = "SELECT new org.eclipse.opensmartclide.tdreusabilityapi.controller.response.entity.ProjectReusabilityMetrics(m.sha, m.revisionCount, AVG(m.cbo), AVG(m.dit), AVG(m.wmc), AVG(m.rfc), AVG(m.lcom), AVG(m.nocc)) "
			+ "FROM Metrics m "
			+ "WHERE m.pid = (SELECT pid FROM Projects WHERE owner = :#{#project.owner} AND repo = :#{#project.repo}) AND m.lcom >= 0 AND m.dit >= 0 GROUP BY m.sha, m.revisionCount ORDER BY m.revisionCount")
	Slice<ProjectReusabilityMetrics> findReusabilityMetrics(Pageable pageable, ProjectDomain project);

	@Query(value = "SELECT new org.eclipse.opensmartclide.tdreusabilityapi.controller.response.entity.FileReusabilityMetrics(m.sha, m.revisionCount, f.filePath, m.cbo, m.dit, m.wmc, m.rfc, m.lcom, m.nocc) "
			+ "FROM Metrics m JOIN Files f ON m.pid = f.pid AND m.fid = f.fid AND m.sha = f.sha "
			+ "WHERE m.pid = (SELECT pid FROM Projects WHERE owner = :#{#project.owner} AND repo = :#{#project.repo}) AND m.sha = :sha AND m.lcom >= 0 AND m.dit >= 0 ORDER BY f.filePath")
	Slice<FileReusabilityMetrics> findReusabilityMetrics(Pageable pageable, ProjectDomain project, @Param("sha") String sha);

	@Query(value = "SELECT new org.eclipse.opensmartclide.tdreusabilityapi.controller.response.entity.FileReusabilityMetrics(m.sha, m.revisionCount, f.filePath, m.cbo, m.dit, m.wmc, m.rfc, m.lcom, m.nocc) "
			+ "FROM Metrics m JOIN Files f ON m.pid = f.pid AND m.fid = f.fid AND m.sha = f.sha "
			+ "WHERE m.pid = (SELECT pid FROM Projects WHERE owner = :#{#project.owner} AND repo = :#{#project.repo}) AND m.sha = :sha AND f.filePath = :filePath AND m.lcom >= 0 AND m.dit >= 0 ORDER BY f.filePath")
	Slice<FileReusabilityMetrics> findReusabilityMetrics(Pageable pageable, ProjectDomain project, @Param("sha") String sha, @Param("filePath") String filePath);

	@Query(value = "SELECT DISTINCT new org.eclipse.opensmartclide.tdreusabilityapi.controller.response.entity.AnalyzedCommit(m.sha, m.revisionCount) "
			+ "FROM Metrics m "
			+ "WHERE m.pid = (SELECT pid FROM Projects WHERE owner = :#{#project.owner} AND repo = :#{#project.repo}) ORDER BY m.revisionCount DESC")
	Slice<AnalyzedCommit> findAnalyzedCommits(Pageable pageable, ProjectDomain project);

}
