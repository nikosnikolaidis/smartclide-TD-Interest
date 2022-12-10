package org.eclipse.opensmartclide.tdreusabilityapi.controller;

import org.eclipse.opensmartclide.tdreusabilityapi.controller.reusability.entity.FileReusabilityIndex;
import org.eclipse.opensmartclide.tdreusabilityapi.controller.reusability.entity.ProjectReusabilityIndex;
import org.eclipse.opensmartclide.tdreusabilityapi.service.reusability.ReusabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/reusability")
public class ReusabilityController {
    @Autowired
    private ReusabilityService reusabilityService;

    @GetMapping(value = "/reusabilityIndexByCommit")
    Collection<FileReusabilityIndex> getReusabilityIndexByCommit(@RequestParam(required = true) String url, @RequestParam(required = true) String sha, @RequestParam(required = false) Integer limit) {
        return Objects.nonNull(limit) ? reusabilityService.findReusabilityIndexByCommit(url, sha, limit) : reusabilityService.findReusabilityIndexByCommit(url, sha, null);
    }

    @GetMapping(value = "/reusabilityIndexByCommitAndFile")
    Collection<FileReusabilityIndex> getReusabilityIndexByCommitAndFile(@RequestParam(required = true) String url, @RequestParam(required = true) String sha, @RequestParam(required = true) String filePath) {
        return reusabilityService.findReusabilityIndexByCommitAndFile(url, sha, filePath);
    }

    @GetMapping(value = "/projectReusabilityIndexByCommit")
    Collection<ProjectReusabilityIndex> getProjectReusabilityIndexByCommit(@RequestParam(required = true) String url, @RequestParam(required = true) String sha) {
        return reusabilityService.findProjectReusabilityIndexByCommit(url, sha);
    }

    @GetMapping(value = "/projectReusabilityIndexPerCommit")
    Collection<ProjectReusabilityIndex> getProjectReusabilityIndexPerCommit(@RequestParam(required = true) String url, @RequestParam(required = false) Integer limit) {
        return reusabilityService.findProjectReusabilityIndexPerCommit(url, limit);
    }
}
