package gr.zisis.interestapi.service;

import java.util.Collection;

import gr.zisis.interestapi.controller.response.entity.*;
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
public class MetricsServiceBean implements MetricsService {

	@Autowired
	private MetricsRepository metricsRepository;

	@Override
	public Collection<CumulativeInterest> findCumulativeInterestPerCommit(String url) {
		String owner = getRepositoryOwner(url);
		String repoName = getRepositoryName(url);
		return metricsRepository.findCumulativeInterestPerCommit(owner, repoName);
	}

	@Override
	public Collection<CumulativeInterest> findCumulativeInterestByCommit(String url, String sha) {
		String owner = getRepositoryOwner(url);
		String repoName = getRepositoryName(url);
		return metricsRepository.findCumulativeInterestByCommit(owner, repoName, sha);
	}

	@Override
	public Collection<InterestPerCommitFile> findInterestByCommitFile(String url, String sha, String filePath) {
		String owner = getRepositoryOwner(url);
		String repoName = getRepositoryName(url);
		return metricsRepository.findInterestPerCommitFile(owner, repoName, sha, filePath);
	}

	@Override
	public Collection<InterestChange> findLastCommitInterestChange(String url, String sha) {
		String owner = getRepositoryOwner(url);
		String repoName = getRepositoryName(url);
		return metricsRepository.findInterestChangeByCommit(owner, repoName, sha);
	}

	@Override
	public Collection<NormalizedInterest> findNormalizedInterest(String url) {
		String owner = getRepositoryOwner(url);
		String repoName = getRepositoryName(url);
		return metricsRepository.findNormalizedInterest(owner, repoName);
	}

	@Override
	public Collection<NormalizedInterest> findNormalizedInterestByCommit(String url, String sha) {
		String owner = getRepositoryOwner(url);
		String repoName = getRepositoryName(url);
		return metricsRepository.findNormalizedInterestByCommit(owner, repoName, sha);
	}

	@Override
	public Slice<HighInterestFile> findHighInterestFiles(Pageable pageable, String url, String sha) {
		String owner = getRepositoryOwner(url);
		String repoName = getRepositoryName(url);
		return metricsRepository.findHighInterestFiles(pageable, owner, repoName, sha);
	}

	@Override
	public Slice<ReusabilityMetrics> findReusabilityMetrics(Pageable pageable, String url) {
		String owner = getRepositoryOwner(url);
		String repoName = getRepositoryName(url);
		return metricsRepository.findReusabilityMetrics(pageable, owner, repoName);
	}

	@Override
	public Slice<ReusabilityMetrics> findReusabilityMetrics(Pageable pageable, String url, String sha) {
		String owner = getRepositoryOwner(url);
		String repoName = getRepositoryName(url);
		return metricsRepository.findReusabilityMetrics(pageable, owner, repoName, sha);
	}

	@Override
	public Slice<ReusabilityMetrics> findReusabilityMetrics(Pageable pageable, String url, String sha, String filePath) {
		String owner = getRepositoryOwner(url);
		String repoName = getRepositoryName(url);
		return metricsRepository.findReusabilityMetrics(pageable, owner, repoName, sha, filePath);
	}

	@Override
	public Slice<AnalyzedCommit> findAnalyzedCommits(Pageable pageable, String url) {
		String owner = getRepositoryOwner(url);
		String repoName = getRepositoryName(url);
		return metricsRepository.findAnalyzedCommits(pageable, owner, repoName);
	}

	private String getRepositoryOwner(String url) {
		String newURL = preprocessURL(url);
		String[] urlSplit = newURL.split("/");
		return urlSplit[urlSplit.length - 2].replaceAll(".*@.*:", "");
	}

	private String getRepositoryName(String url) {
		String newURL = preprocessURL(url);
		String[] urlSplit = newURL.split("/");
		return urlSplit[urlSplit.length - 1];
	}

	private String preprocessURL(String url) {
		String newURL = url;
		if (newURL.endsWith(".git/"))
			newURL = newURL.replace(".git/", "");
		if (newURL.endsWith(".git"))
			newURL = newURL.replace(".git", "");
		if (newURL.endsWith("/"))
			newURL = newURL.substring(0, newURL.length() - 1);
		return newURL;
	}

}
