package gr.zisis.interestapi.persistence;

import gr.zisis.interestapi.AbstractBaseTest;
import gr.zisis.interestapi.controller.response.entity.Project;
import gr.zisis.interestapi.domain.Projects;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Dimitrios Zisis <zisisndimitris@gmail.com>
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProjectsRepositoryTest extends AbstractBaseTest {

    @Autowired
    private ProjectsRepository underTest;

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    void shouldFindProject() {
        Projects project = new Projects(PID.incrementAndGet(), "testOwner", "testRepo", "testURL");
        underTest.save(project);
        Project expectedProject = new Project(PID.get(), project.getUrl(), project.getOwner(), project.getRepo());
        assertThat(expectedProject)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(underTest.findProject(project.getOwner(), project.getRepo()));
    }
}
