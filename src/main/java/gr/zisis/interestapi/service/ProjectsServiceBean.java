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
        return projectsRepository.findProject(url);
    }

}
