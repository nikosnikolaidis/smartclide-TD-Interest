package gr.zisis.interestapi.controller;

import java.util.Collection;
import java.util.Objects;

import gr.zisis.interestapi.controller.response.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import gr.zisis.interestapi.service.MetricsService;

/**
 * @author George Digkas <digasgeo@gmail.com>
 */
@RestController
@RequestMapping(value = "/api")
public class InterestController {

    @Autowired
    private MetricsService metricsService;

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/cumulativeInterest")
    Collection<CumulativeInterest> getCumulativeInterestPerCommit(@RequestParam(required = true) String url, @RequestParam(required = false) String sha) {
        if (Objects.isNull(sha))
            return metricsService.findCumulativeInterestPerCommit(url);
        return metricsService.findCumulativeInterest(url, sha);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/interestPerCommitFile")
    Collection<InterestPerCommitFile> getInterestPerCommitFile(@RequestParam(required = true) String url, @RequestParam(required = true) String filePath, @RequestParam(required = true) String sha) {
        return metricsService.findInterestPerCommitFile(url, filePath, sha);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/interestChange")
    Collection<InterestChangePerCommit> getLastCommitInterestChange(@RequestParam(required = true) String url, @RequestParam(required = true) String sha) {
        return metricsService.findLastCommitInterestChange(url, sha);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/normalizedInterest")
    Collection<NormalizedInterest> getNormalizedInterest(@RequestParam(required = true) String url, @RequestParam(required = false) String sha) {
        return (Objects.isNull(sha)) ? metricsService.findNormalizedInterest(url) : metricsService.findNormalizedInterestPerCommit(url, sha);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/highInterestFiles")
    Collection<HighInterestFile> getHighInterestFiles(@RequestParam(required = true) String url, @RequestParam(required = true) String sha, @RequestParam(required = false) Integer limit) {
        return Objects.isNull(limit) ? metricsService.findHighInterestFiles(null, url, sha).getContent() : metricsService.findHighInterestFiles(PageRequest.of(0, limit), url, sha).getContent();
    }

//	@CrossOrigin(origins = "*")
//	@PutMapping(value = "/startInterestAnalysis")
//	Void startInterestAnalysis(@RequestBody(required = true) Projects newProject) {
//
//		return null;
//	}

}
