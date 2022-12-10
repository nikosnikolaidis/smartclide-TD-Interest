package org.eclipse.opensmartclide.tdreusabilityapi.controller;

import org.eclipse.opensmartclide.tdreusabilityapi.controller.principal.entity.RequestBodyAnalysis;
import org.eclipse.opensmartclide.tdreusabilityapi.controller.principal.entity.RequestBodyEndpoints;
import org.eclipse.opensmartclide.tdreusabilityapi.controller.principal.entity.RequestBodyEndpointsManual;
import org.eclipse.opensmartclide.tdreusabilityapi.domain.principal.Issue;
import org.eclipse.opensmartclide.tdreusabilityapi.domain.principal.Metric;
import org.eclipse.opensmartclide.tdreusabilityapi.domain.principal.Report;
import org.eclipse.opensmartclide.tdreusabilityapi.service.principal.AnalysisService;
import org.eclipse.opensmartclide.tdreusabilityapi.service.principal.EndpointAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path= "api/principal")
public class PrincipalController {

    @Autowired
    private AnalysisService analysisService;

    @Autowired
    private EndpointAnalysisService endpointAnalysisService;

    @GetMapping(path="{projectKey}/measures")
    public Metric[] getMeasures(@PathVariable(value = "projectKey") String projectKey) {
        return analysisService.getMeasures(projectKey);
    }

    @GetMapping(path="{projectKey}/issues")
    public List<Issue> getIssues(@PathVariable(value = "projectKey") String projectKey) {
        return analysisService.getIssues(projectKey);
    }

    @PostMapping(path="endpoints")
    public List<Report> getEndpointMetricsPrivateManual(@RequestBody RequestBodyEndpointsManual requestBodyEndpointsManual) {
        return endpointAnalysisService.getEndpointMetricsPrivateManual(requestBodyEndpointsManual);
    }

    @PostMapping(path="endpoints/auto")
    public List<Report> getEndpointMetricsPrivateAuto(@RequestBody RequestBodyEndpoints requestBodyEndpoints) {
        return endpointAnalysisService.getEndpointMetricsPrivateAuto(requestBodyEndpoints);
    }

//	@PostMapping(path="endpoints")
//	public HashMap<String,Report> getEndpointMetricsLocalGitlabSonar(@RequestBody RequestBodyEndpoints requestBodyEndpoints) {
//		return endpointAnalysisService.getEnpointMetricsLocal(requestBodyEndpoints);
//	}

    @PostMapping
    public String makeNewAnalysis(@RequestBody RequestBodyAnalysis requestBodyAnalysis) {
        String report = analysisService.startNewAnalysis(requestBodyAnalysis);
        return report;
    }

}