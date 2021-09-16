package gr.zisis.interestapi.persistence;

import gr.zisis.interestapi.controller.response.entity.Project;
import gr.zisis.interestapi.domain.Projects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Dimitrios Zisis <zisisndimitris@gmail.com>
 */
@DataJpaTest
class ProjectsRepositoryTest {

    @Autowired
    private ProjectsRepository underTest;

    @Test
    @DirtiesContext
    void shouldFindProject() {
        Projects project = new Projects(1L, "testOwner", "testRepo", "testURL");
        underTest.save(project);
        Project expectedProject = new Project(1L, project.getUrl(), project.getOwner(), project.getRepo());
        assertThat(expectedProject).isEqualToComparingOnlyGivenFields(underTest.findProject(project.getOwner(), project.getRepo()), "owner", "repo", "url");
    }
}