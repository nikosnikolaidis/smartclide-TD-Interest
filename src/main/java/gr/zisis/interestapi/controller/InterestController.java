package gr.zisis.interestapi.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	Collection<CumulativeInterestPerCommit> getCumulativeInterestPerCommits(@RequestParam(required = true) String url, @RequestParam(required = true) String sha) {
		return metricsService.findCumulativeInterestPerCommit(url, sha);
	}

}
