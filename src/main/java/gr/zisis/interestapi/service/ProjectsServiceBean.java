package gr.zisis.interestapi.service;

import gr.zisis.interestapi.controller.response.entity.Project;
import gr.zisis.interestapi.persistence.ProjectsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

/*
 *
 *  * Copyright (C) 2021 UoM - University of Macedonia
 *  *
 *  * This program and the accompanying materials are made available under the
 *  * terms of the Eclipse Public License 2.0 which is available at
 *  * https://www.eclipse.org/legal/epl-2.0/
 *  *
 *  * SPDX-License-Identifier: EPL-2.0
 *
 */

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
    public Project save(String url) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("java", "-jar", "jars/interest.jar", url, databaseDriver, databaseUrl, databaseUser, databasePass);
        Process process = pb.start();
        process.waitFor();
        if (process.exitValue() == 0) {
            String owner = getRepositoryOwner(url);
            String repoName = getRepositoryName(url);
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
