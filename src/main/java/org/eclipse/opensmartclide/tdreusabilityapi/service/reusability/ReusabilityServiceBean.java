/*******************************************************************************
 * Copyright (C) 2021-2022 UoM - University of Macedonia
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.opensmartclide.tdreusabilityapi.service.reusability;

import org.eclipse.opensmartclide.tdreusabilityapi.controller.response.entity.FileReusabilityMetrics;
import org.eclipse.opensmartclide.tdreusabilityapi.controller.response.entity.ProjectReusabilityMetrics;
import org.eclipse.opensmartclide.tdreusabilityapi.controller.reusability.entity.FileReusabilityIndex;
import org.eclipse.opensmartclide.tdreusabilityapi.controller.reusability.entity.ProjectReusabilityIndex;
import org.eclipse.opensmartclide.tdreusabilityapi.service.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.lang.Math.log10;

@Service
public class ReusabilityServiceBean implements ReusabilityService {

    @Autowired
    private MetricsService metricsService;

    @Override
    public Collection<FileReusabilityIndex> findReusabilityIndexByCommit(String url, String sha, Integer limit) {
        try {
            Collection<FileReusabilityMetrics> fileReusabilityMetrics =
                    Objects.isNull(limit) ? metricsService.findReusabilityMetrics(null, url, sha).getContent() : metricsService.findReusabilityMetrics(PageRequest.of(0, limit), url, sha).getContent();
            if (fileReusabilityMetrics.isEmpty())
                return new ArrayList<>();

            List<FileReusabilityIndex> fileReusabilityIndexList = new ArrayList<>();
            for (FileReusabilityMetrics m : fileReusabilityMetrics) {
                double index = -1 * (8.753 * log10(m.getCbo().doubleValue() + 1) + 2.505 * log10(m.getDit() + 1) - 1.922 * log10(m.getWmc().doubleValue() + 1) + 0.892 * log10(m.getRfc().doubleValue() + 1) - 0.399 * log10(m.getLcom().doubleValue() + 1) - 1.080 * log10(m.getNocc() + 1));
                fileReusabilityIndexList.add(new FileReusabilityIndex(m.getSha(), m.getRevisionCount(), m.getFilePath(), index));
            }
            Collections.sort(fileReusabilityIndexList);
            return fileReusabilityIndexList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public Collection<FileReusabilityIndex> findReusabilityIndexByCommitAndFile(String url, String sha, String filePath) {
        try {
            Collection<FileReusabilityMetrics> fileReusabilityMetrics =  metricsService.findReusabilityMetrics(null, url, sha, filePath).getContent();
            if (fileReusabilityMetrics.isEmpty())
                return new ArrayList<>();

            List<FileReusabilityIndex> fileReusabilityIndexList = new ArrayList<>();
            for (FileReusabilityMetrics m : fileReusabilityMetrics) {
                double index = -1 * (8.753 * log10(m.getCbo().doubleValue() + 1) + 2.505 * log10(m.getDit() + 1) - 1.922 * log10(m.getWmc().doubleValue() + 1) + 0.892 * log10(m.getRfc().doubleValue() + 1) - 0.399 * log10(m.getLcom().doubleValue() + 1) - 1.080 * log10(m.getNocc() + 1));
                fileReusabilityIndexList.add(new FileReusabilityIndex(m.getSha(), m.getRevisionCount(), filePath, index));
            }
            return fileReusabilityIndexList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public Collection<ProjectReusabilityIndex> findProjectReusabilityIndexByCommit(String url, String sha) {
        try {
            List<FileReusabilityMetrics> fileReusabilityMetrics = metricsService.findReusabilityMetrics(null, url, sha).getContent();
            if (fileReusabilityMetrics.isEmpty())
                return new ArrayList<>();
            double avgIndex = 0.0;
            for (FileReusabilityMetrics m : fileReusabilityMetrics) {
                double index = -1 * (8.753 * log10(m.getCbo().doubleValue() + 1) + 2.505 * log10(m.getDit() + 1) - 1.922 * log10(m.getWmc().doubleValue() + 1) + 0.892 * log10(m.getRfc().doubleValue() + 1) - 0.399 * log10(m.getLcom().doubleValue() + 1) - 1.080 * log10(m.getNocc() + 1));
                avgIndex += index;
            }
            avgIndex /= fileReusabilityMetrics.size();
            return Collections.singletonList(new ProjectReusabilityIndex(sha, fileReusabilityMetrics.get(0).getRevisionCount(), avgIndex));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public Collection<ProjectReusabilityIndex> findProjectReusabilityIndexPerCommit(String url, Integer limit) {
        List<ProjectReusabilityIndex> projectReusabilityIndexList = new ArrayList<>();
        try {
            Collection<ProjectReusabilityMetrics>  projectReusabilityMetrics =
                    Objects.isNull(limit) ? metricsService.findReusabilityMetrics(null, url).getContent() : metricsService.findReusabilityMetrics(PageRequest.of(0, limit), url).getContent();
            if (projectReusabilityMetrics.isEmpty())
                return new ArrayList<>();
            for (ProjectReusabilityMetrics m : projectReusabilityMetrics) {
                double avgIndex = -1 * (8.753 * log10(m.getCbo().doubleValue() + 1) + 2.505 * log10(m.getDit() + 1) - 1.922 * log10(m.getWmc().doubleValue() + 1) + 0.892 * log10(m.getRfc().doubleValue() + 1) - 0.399 * log10(m.getLcom().doubleValue() + 1) - 1.080 * log10(m.getNocc() + 1));
                projectReusabilityIndexList.add(new ProjectReusabilityIndex(m.getSha(), m.getRevisionCount(), avgIndex));
            }
            Collections.sort(projectReusabilityIndexList);
            return projectReusabilityIndexList;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
