package gr.zisis.interestapi.service;

import java.util.Collection;

import gr.zisis.interestapi.controller.response.entity.*;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
public interface MetricsService {

	Collection<CumulativeInterest> findCumulativeInterestPerCommit(String url);

	Collection<CumulativeInterest> findCumulativeInterest(String url, String sha);

	Collection<InterestPerCommitFile> findInterestPerCommitFile(String url, String filePath, String sha);

	Collection<InterestChangePerCommit> findLastCommitInterestChange(String url, String sha);

	Collection<NormalizedInterest> findNormalizedInterest(String url);

	Collection<NormalizedInterest> findNormalizedInterestPerCommit(String url, String sha);

	Collection<HighInterestFile> findHighInterestFiles(String url);

	Collection<HighInterestFile> findHighInterestFiles(String url, String sha);

	Collection<HighInterestFile> findHighInterestFiles(String url, Integer limit);

	Collection<HighInterestFile> findHighInterestFiles(String url, String sha, Integer limit);
}
