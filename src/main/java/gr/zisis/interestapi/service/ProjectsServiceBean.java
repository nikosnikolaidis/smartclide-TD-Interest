package gr.zisis.interestapi.service;

import gr.zisis.interestapi.controller.response.entity.Project;
import gr.zisis.interestapi.persistence.ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class ProjectsServiceBean implements ProjectsService {

    @Autowired
    private ProjectsRepository projectsRepository;

    @Override
    public Project save(String url) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("java", "-jar", "/interest.jar", url);
        Process process = pb.start();
        process.waitFor();
        String owner = getRepositoryOwner(url);
        String repoName = getRepositoryName(url);
        return projectsRepository.findProject(owner, repoName);
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
