package gr.zisis.interestapi.service;

import gr.zisis.interestapi.controller.response.entity.Project;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

public interface ProjectsService {
    Project save(String url) throws IOException, InterruptedException;
}
