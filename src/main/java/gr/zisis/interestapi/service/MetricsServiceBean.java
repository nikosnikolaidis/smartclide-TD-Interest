package gr.zisis.interestapi.service;

import java.util.Collection;

import gr.zisis.interestapi.controller.response.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
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
		return metricsRepository.findCumulativeInterestPerCommit(url);
	}

	@Override
	public Collection<CumulativeInterest> findCumulativeInterest(String url, String sha) {
		return metricsRepository.findCumulativeInterest(url, sha);
	}

	@Override
	public Collection<InterestPerCommitFile> findInterestPerCommitFile(String url, String filePath, String sha) {
		return metricsRepository.findInterestPerCommitFile(url, filePath, sha);
	}

	@Override
	public Collection<InterestChangePerCommit> findLastCommitInterestChange(String url, String sha) {
		return metricsRepository.findInterestChangePerCommit(url, sha);
	}

	@Override
	public Collection<NormalizedInterest> findNormalizedInterest(String url) {
		return metricsRepository.findNormalizedInterest(url);
	}

	@Override
	public Collection<NormalizedInterest> findNormalizedInterestPerCommit(String url, String sha) {
		return metricsRepository.findNormalizedInterestPerCommit(url, sha);
	}

	@Override
	public Collection<HighInterestFile> findHighInterestFiles(String url) {
		return metricsRepository.findHighInterestFiles(url);
	}

	@Override
	public Collection<HighInterestFile> findHighInterestFiles(String url, String sha) {
		return metricsRepository.findHighInterestFiles(url, sha);
	}

	@Override
	public Collection<HighInterestFile> findHighInterestFiles(String url, Integer limit) {
		return metricsRepository.findHighInterestFiles(url, limit);
	}

	@Override
	public Collection<HighInterestFile> findHighInterestFiles(String url, String sha, Integer limit) {
		return metricsRepository.findHighInterestFiles(url, sha, limit);
	}

}
