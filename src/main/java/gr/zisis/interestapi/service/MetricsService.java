package gr.zisis.interestapi.service;

import java.util.Collection;

import gr.zisis.interestapi.controller.response.entity.CumulativeInterest;
import gr.zisis.interestapi.controller.response.entity.CumulativeInterestPerCommit;
import gr.zisis.interestapi.controller.response.entity.InterestPerCommitFile;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
public interface MetricsService {

	Collection<CumulativeInterest> findCumulativeInterest(String url);

	Collection<CumulativeInterestPerCommit> findCumulativeInterestPerCommit(String url, String sha);

	Collection<InterestPerCommitFile> findInterestPerCommitFile(String url, String filePath, String sha);

}
