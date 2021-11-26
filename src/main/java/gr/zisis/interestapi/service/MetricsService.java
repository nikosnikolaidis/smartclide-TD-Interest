package gr.zisis.interestapi.service;

import java.util.Collection;

import gr.zisis.interestapi.controller.response.entity.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
public interface MetricsService {

	Collection<CumulativeInterest> findCumulativeInterestPerCommit(String url);

	Collection<CumulativeInterest> findCumulativeInterestByCommit(String url, String sha);

	Collection<InterestPerCommitFile> findInterestByCommitFile(String url, String sha, String filePath);

	Collection<InterestChange> findInterestChangeByCommit(String url, String sha);

	FileInterestChange findInterestChangeByCommitAndFile(String url, String sha, String filePath);

	Collection<NormalizedInterest> findNormalizedInterest(String url);

	Collection<NormalizedInterest> findNormalizedInterestByCommit(String url, String sha);

	Slice<HighInterestFile> findHighInterestFiles(Pageable pageable, String url, String sha);

	Slice<ProjectReusabilityMetrics> findReusabilityMetrics(Pageable pageable, String url);

	Slice<FileReusabilityMetrics> findReusabilityMetrics(Pageable pageable, String url, String sha);

	Slice<FileReusabilityMetrics> findReusabilityMetrics(Pageable pageable, String url, String sha, String filePath);

	Slice<AnalyzedCommit> findAnalyzedCommits(Pageable pageable, String url);

}
