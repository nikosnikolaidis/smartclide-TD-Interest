package gr.zisis.interestapi.service;

import java.util.Collection;

import gr.zisis.interestapi.controller.response.entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
public interface MetricsService {

	Collection<CumulativeInterest> findCumulativeInterestPerCommit(String url);

	Collection<CumulativeInterest> findCumulativeInterest(String url, String sha);

	Collection<InterestPerCommitFile> findInterestPerCommitFile(String url, String sha, String filePath);

	Collection<InterestChangePerCommit> findLastCommitInterestChange(String url, String sha);

	Collection<NormalizedInterest> findNormalizedInterest(String url);

	Collection<NormalizedInterest> findNormalizedInterestPerCommit(String url, String sha);

	Slice<HighInterestFile> findHighInterestFiles(Pageable pageable, String url, String sha);

}
