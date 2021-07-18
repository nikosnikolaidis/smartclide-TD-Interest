package gr.zisis.interestapi.controller;

import java.util.Collection;

import gr.zisis.interestapi.controller.response.entity.InterestPerCommitFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import gr.zisis.interestapi.controller.response.entity.CumulativeInterest;
import gr.zisis.interestapi.controller.response.entity.CumulativeInterestPerCommit;
import gr.zisis.interestapi.service.MetricsService;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
@RestController
@RequestMapping(value = "/api")
public class InterestController {
	
	@Autowired
	private MetricsService metricsService;
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/cumulativeInterest")
	Collection<CumulativeInterest> getCumulativeInterest(@RequestParam(required = true) String url) {
		return metricsService.findCumulativeInterest(url);
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/cumulativeInterestPerCommit")
	Collection<CumulativeInterestPerCommit> getCumulativeInterestPerCommit(@RequestParam(required = true) String url, @RequestParam(required = true) String sha) {
		return metricsService.findCumulativeInterestPerCommit(url, sha);
	}

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/interestPerCommitFile")
	Collection<InterestPerCommitFile> getInterestPerCommitFile(@RequestParam(required = true) String url, @RequestParam(required = true) String filePath, @RequestParam(required = true) String sha) {
		return metricsService.findInterestPerCommitFile(url, filePath, sha);
	}

//	@CrossOrigin(origins = "*")
//	@PutMapping(value = "/startInterestAnalysis")
//	Void startInterestAnalysis(@RequestBody(required = true) Projects newProject) {
//
//		return null;
//	}

}
