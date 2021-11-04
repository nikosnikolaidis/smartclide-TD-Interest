package gr.zisis.interestapi.service;

import gr.zisis.interestapi.controller.response.entity.Project;
import gr.zisis.interestapi.persistence.ProjectsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;

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
        ProcessBuilder pb = new ProcessBuilder("java", "-jar", "/interest.jar", url, databaseDriver, databaseUrl, databaseUser, databasePass);
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
