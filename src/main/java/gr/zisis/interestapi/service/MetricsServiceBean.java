package gr.zisis.interestapi.service;

import java.util.Collection;

import gr.zisis.interestapi.controller.response.entity.*;
import gr.zisis.interestapi.domain.ProjectDomain;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import gr.zisis.interestapi.persistence.MetricsRepository;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
@Service
@AllArgsConstructor
public class MetricsServiceBean implements MetricsService {

	@Autowired
	private MetricsRepository metricsRepository;

	@Override
	public Collection<CumulativeInterest> findCumulativeInterestPerCommit(String url) {
		return metricsRepository.findCumulativeInterestPerCommit(new ProjectDomain(url));
	}

	@Override
	public Collection<CumulativeInterest> findCumulativeInterestByCommit(String url, String sha) {
		return metricsRepository.findCumulativeInterestByCommit(new ProjectDomain(url), sha);
	}

	@Override
	public Collection<InterestPerCommitFile> findInterestByCommitFile(String url, String sha, String filePath) {
		return metricsRepository.findInterestPerCommitFile(new ProjectDomain(url), sha, filePath);
	}

	@Override
	public Collection<InterestChange> findInterestChangeByCommit(String url, String sha) {
		return metricsRepository.findInterestChangeByCommit(new ProjectDomain(url), sha);
	}

	@Override
	public Collection<NormalizedInterest> findNormalizedInterest(String url) {
		return metricsRepository.findNormalizedInterest(new ProjectDomain(url));
	}

	@Override
	public Collection<NormalizedInterest> findNormalizedInterestByCommit(String url, String sha) {
		return metricsRepository.findNormalizedInterestByCommit(new ProjectDomain(url), sha);
	}

	@Override
	public Slice<HighInterestFile> findHighInterestFiles(Pageable pageable, String url, String sha) {
		return metricsRepository.findHighInterestFiles(pageable, new ProjectDomain(url), sha);
	}

	@Override
	public Slice<ProjectReusabilityMetrics> findReusabilityMetrics(Pageable pageable, String url) {
		return metricsRepository.findReusabilityMetrics(pageable, new ProjectDomain(url));
	}

	@Override
	public Slice<FileReusabilityMetrics> findReusabilityMetrics(Pageable pageable, String url, String sha) {
		return metricsRepository.findReusabilityMetrics(pageable, new ProjectDomain(url), sha);
	}

	@Override
	public Slice<FileReusabilityMetrics> findReusabilityMetrics(Pageable pageable, String url, String sha, String filePath) {
		return metricsRepository.findReusabilityMetrics(pageable, new ProjectDomain(url), sha, filePath);
	}

	@Override
	public Slice<AnalyzedCommit> findAnalyzedCommits(Pageable pageable, String url) {
		return metricsRepository.findAnalyzedCommits(pageable, new ProjectDomain(url));
	}

}
