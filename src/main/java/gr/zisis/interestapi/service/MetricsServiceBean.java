package gr.zisis.interestapi.service;

import java.util.Collection;

import gr.zisis.interestapi.controller.response.entity.InterestPerCommitFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gr.zisis.interestapi.controller.response.entity.CumulativeInterest;
import gr.zisis.interestapi.controller.response.entity.CumulativeInterestPerCommit;
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
	public Collection<CumulativeInterest> findCumulativeInterest(String url) {
		return metricsRepository.findCumulativeInterest(url);
	}

	@Override
	public Collection<CumulativeInterestPerCommit> findCumulativeInterestPerCommit(String url, String sha) {
		return metricsRepository.findCumulativeInterestPerCommit(url, sha);
	}

	@Override
	public Collection<InterestPerCommitFile> findInterestPerCommitFile(String url, String filePath, String sha) {
		return metricsRepository.findInterestPerCommitFile(url, filePath, sha);
	}

}
