/*******************************************************************************
 * Copyright (C) 2021-2022 University of Macedonia
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.opensmartclide.tdreusabilityapi.service;

import org.eclipse.opensmartclide.tdreusabilityapi.controller.response.entity.Project;
import org.eclipse.opensmartclide.tdreusabilityapi.persistence.ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
public class ProjectsServiceBean implements ProjectsService {

    @Autowired
    private ProjectsRepository projectsRepository;

    @Value("${spring.datasource.driver-class-name}")
    private String databaseDriver;

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @Value("${spring.datasource.username}")
    private String databaseUser;

    @Value("${spring.datasource.password}")
    private String databasePass;

    @Override
    public Project save(String url, String VCSAccessToken) throws IOException, InterruptedException {
        ProcessBuilder pb;
        String owner = getRepositoryOwner(url);
        String repoName = getRepositoryName(url);
        String clonePath = "/tmp/"+ owner + "_" + repoName + "_" + System.currentTimeMillis();
        if (Objects.isNull(VCSAccessToken))
            pb = new ProcessBuilder("java", "-jar", "interest.jar", url, clonePath, databaseDriver, databaseUrl, databaseUser, databasePass);
        else
            pb = new ProcessBuilder("java", "-jar", "interest.jar", url, clonePath, databaseDriver, databaseUrl, databaseUser, databasePass, VCSAccessToken);
        Process process = pb.start();
        process.waitFor();
        FileSystemUtils.deleteRecursively(new File(clonePath));
        if (process.exitValue() == 0) {
            return projectsRepository.findProject(owner, repoName);
        } else if (process.exitValue() == -1) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Incorrect URL");
        } else if (process.exitValue() == -2) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Mandatory arguments not provided");
        } else {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Server error");
        }
    }

    private String getRepositoryOwner(String url) {
        String newURL = preprocessURL(url);
        String[] urlSplit = newURL.split("/");
        return urlSplit[urlSplit.length - 2].replaceAll(".*@.*:", "");
    }

    private String getRepositoryName(String url) {
        String newURL = preprocessURL(url);
        String[] urlSplit = newURL.split("/");
        return urlSplit[urlSplit.length - 1];
    }

    private String preprocessURL(String url) {
        String newURL = url;
        if (newURL.endsWith(".git/"))
            newURL = newURL.replace(".git/", "");
        if (newURL.endsWith(".git"))
            newURL = newURL.replace(".git", "");
        if (newURL.endsWith("/"))
            newURL = newURL.substring(0, newURL.length() - 1);
        return newURL;
    }

}
