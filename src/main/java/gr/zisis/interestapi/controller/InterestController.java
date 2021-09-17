package gr.zisis.interestapi.controller;

import java.io.IOException;
import java.rmi.ServerException;
import java.util.Collection;
import java.util.Objects;

import gr.zisis.interestapi.controller.response.entity.*;
import gr.zisis.interestapi.domain.ProjectDetails;
import gr.zisis.interestapi.service.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private ProjectsService projectsService;

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/cumulativeInterest")
    Collection<CumulativeInterest> getCumulativeInterestPerCommit(@RequestParam(required = true) String url, @RequestParam(required = false) String sha) {
        if (Objects.isNull(sha))
            return metricsService.findCumulativeInterestPerCommit(url);
        return metricsService.findCumulativeInterestByCommit(url, sha);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/interestPerCommitFile")
    Collection<InterestPerCommitFile> getInterestPerCommitFile(@RequestParam(required = true) String url, @RequestParam(required = true) String sha, @RequestParam(required = true) String filePath) {
        return metricsService.findInterestByCommitFile(url, sha, filePath);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/interestChange")
    Collection<InterestChange> getLastCommitInterestChange(@RequestParam(required = true) String url, @RequestParam(required = true) String sha) {
        return metricsService.findLastCommitInterestChange(url, sha);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/normalizedInterest")
    Collection<NormalizedInterest> getNormalizedInterest(@RequestParam(required = true) String url, @RequestParam(required = false) String sha) {
        return (Objects.isNull(sha)) ? metricsService.findNormalizedInterest(url) : metricsService.findNormalizedInterestByCommit(url, sha);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/highInterestFiles")
    Collection<HighInterestFile> getHighInterestFiles(@RequestParam(required = true) String url, @RequestParam(required = true) String sha, @RequestParam(required = false) Integer limit) {
        return Objects.isNull(limit) ? metricsService.findHighInterestFiles(null, url, sha).getContent() : metricsService.findHighInterestFiles(PageRequest.of(0, limit), url, sha).getContent();
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/reusabilityMetrics")
    Collection<ProjectReusabilityMetrics> getReusabilityMetrics(@RequestParam(required = true) String url, @RequestParam(required = false) Integer limit) {
        return Objects.isNull(limit) ? metricsService.findReusabilityMetrics(null, url).getContent() : metricsService.findReusabilityMetrics(PageRequest.of(0, limit), url).getContent();
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/reusabilityMetricsByCommit")
    Collection<FileReusabilityMetrics> getReusabilityMetricsByCommit(@RequestParam(required = true) String url, @RequestParam(required = true) String sha, @RequestParam(required = false) Integer limit) {
        return Objects.isNull(limit) ? metricsService.findReusabilityMetrics(null, url, sha).getContent() : metricsService.findReusabilityMetrics(PageRequest.of(0, limit), url, sha).getContent();
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/reusabilityMetricsByCommitAndFile")
    Collection<FileReusabilityMetrics> getReusabilityMetricsByCommitAndFile(@RequestParam(required = true) String url, @RequestParam(required = true) String sha, @RequestParam(required = true) String filePath, @RequestParam(required = false) Integer limit) {
        return Objects.isNull(limit) ? metricsService.findReusabilityMetrics(null, url, sha, filePath).getContent() : metricsService.findReusabilityMetrics(PageRequest.of(0, limit), url, sha, filePath).getContent();
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/analyzedCommits")
    Collection<AnalyzedCommit> getAnalyzedCommitIds(@RequestParam(required = true) String url, @RequestParam(required = false) Integer limit) {
        return Objects.isNull(limit) ? metricsService.findAnalyzedCommits(null, url).getContent() : metricsService.findAnalyzedCommits(PageRequest.of(0, limit), url).getContent();
    }

    @CrossOrigin(origins = "*")
	@PostMapping(path = "/startInterestAnalysis", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Project> startInterestAnalysis(@RequestBody(required = true) ProjectDetails projectDetails) throws IOException {
        try {
            return new ResponseEntity<>(projectsService.save(projectDetails.getUrl()), HttpStatus.CREATED);
        } catch (IOException | InterruptedException e) {
            throw new ServerException("IOException");
        }
	}

}
