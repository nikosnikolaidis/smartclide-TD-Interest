package gr.zisis.interestapi.persistence;

import gr.zisis.interestapi.controller.response.entity.*;
import gr.zisis.interestapi.domain.Files;
import gr.zisis.interestapi.domain.Metrics;
import gr.zisis.interestapi.domain.ProjectDomain;
import gr.zisis.interestapi.domain.Projects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Dimitrios Zisis <zisisndimitris@gmail.com>
 */
@DataJpaTest
class MetricsRepositoryTest {

    @Autowired
    private MetricsRepository metricsRepositoryUnderTest;
    
    @Autowired
    private ProjectsRepository projectsRepositoryUnderTest;

    @Autowired
    private FilesRepository filesRepositoryUnderTest;

    @Test
    @DirtiesContext
    void itShouldFindCumulativeInterestPerCommit() {

        Projects projects = new Projects(1L, "testOwner", "testRepo", "testURL");
        projectsRepositoryUnderTest.save(projects);

        Files f1 = new Files(1L, "testPath", "", projects, "testSha");
        Files f2 = new Files(2L, "testPath2", "", projects, "testSha");
        Files f3 = new Files(3L, "testPath3", "", projects, "testSha");
        Files f4 = new Files(4L, "testPath", "", projects, "testSha2");
        Files f5 = new Files(5L, "testPath2", "", projects, "testSha2");
        Files f6 = new Files(6L, "testPath3", "", projects, "testSha2");

        filesRepositoryUnderTest.save(f1);
        filesRepositoryUnderTest.save(f2);
        filesRepositoryUnderTest.save(f3);
        filesRepositoryUnderTest.save(f4);
        filesRepositoryUnderTest.save(f5);
        filesRepositoryUnderTest.save(f6);

        Metrics metricsTest = new Metrics(1L, projects, f1, new BigDecimal("5.0"), "testSha", 1L);
        metricsTest.setInterestHours(new BigDecimal("0.0"));
        Metrics metricsTest2 = new Metrics(2L, projects, f2, new BigDecimal("2.0"), "testSha", 1L);
        metricsTest2.setInterestHours(new BigDecimal("0.0"));
        Metrics metricsTest3 = new Metrics(3L, projects, f3, new BigDecimal("510.0"), "testSha", 1L);
        metricsTest3.setInterestHours(new BigDecimal("0.0"));

        Metrics metricsTest4 = new Metrics(4L, projects, f4, new BigDecimal("2.0"), "testSha2", 2L);
        metricsTest4.setInterestHours(new BigDecimal("0.0"));
        Metrics metricsTest5 = new Metrics(5L, projects, f5, new BigDecimal("3.0"), "testSha2", 2L);
        metricsTest5.setInterestHours(new BigDecimal("0.0"));
        Metrics metricsTest6 = new Metrics(6L, projects, f6, new BigDecimal("5.0"), "testSha2", 2L);
        metricsTest6.setInterestHours(new BigDecimal("0.0"));

        metricsRepositoryUnderTest.save(metricsTest);
        metricsRepositoryUnderTest.save(metricsTest2);
        metricsRepositoryUnderTest.save(metricsTest3);

        metricsRepositoryUnderTest.save(metricsTest4);
        metricsRepositoryUnderTest.save(metricsTest5);
        metricsRepositoryUnderTest.save(metricsTest6);

        Collection<CumulativeInterest> expectedCollection
                = new ArrayList<>(Arrays.asList(new CumulativeInterest("testSha", 1L, new BigDecimal("517.0"),
                new BigDecimal("0.0")),new CumulativeInterest("testSha2", 2L, new BigDecimal("10.0"), new BigDecimal("0.0"))));

        Collection<CumulativeInterest> retrievedCollection = metricsRepositoryUnderTest.findCumulativeInterestPerCommit(new ProjectDomain("https://github.com/testOwner/testRepo"));

        assertThat(retrievedCollection).usingElementComparatorOnFields("sha", "interestEu").hasSameElementsAs(expectedCollection);

    }

    @Test
    @DirtiesContext
    void itShouldFindCumulativeInterestByCommit() {
        Projects projects = new Projects(1L, "testOwner", "testRepo", "testURL");
        projectsRepositoryUnderTest.save(projects);

        Files f1 = new Files(1L, "testPath", "", projects, "testSha");
        Files f2 = new Files(2L, "testPath2", "", projects, "testSha");
        Files f3 = new Files(3L, "testPath3", "", projects, "testSha");

        filesRepositoryUnderTest.save(f1);
        filesRepositoryUnderTest.save(f2);
        filesRepositoryUnderTest.save(f3);

        Metrics metricsTest = new Metrics(1L, projects, f1, new BigDecimal("5.0"), "testSha", 1L);
        metricsTest.setInterestHours(new BigDecimal("0.0"));
        Metrics metricsTest2 = new Metrics(2L, projects, f2, new BigDecimal("2.0"), "testSha", 1L);
        metricsTest2.setInterestHours(new BigDecimal("0.0"));
        Metrics metricsTest3 = new Metrics(3L, projects, f3, new BigDecimal("510.0"), "testSha", 1L);
        metricsTest3.setInterestHours(new BigDecimal("0.0"));

        metricsRepositoryUnderTest.save(metricsTest);
        metricsRepositoryUnderTest.save(metricsTest2);
        metricsRepositoryUnderTest.save(metricsTest3);

        Collection<CumulativeInterest> expectedCollection
                = new ArrayList<>(Arrays.asList(new CumulativeInterest("testSha", 1L, new BigDecimal("517.0"), new BigDecimal("0.0"))));

        Collection<CumulativeInterest> retrievedCollection = metricsRepositoryUnderTest.findCumulativeInterestByCommit(new ProjectDomain("https://github.com/testOwner/testRepo"), "testSha");

        assertThat(expectedCollection).usingElementComparatorOnFields("sha", "interestEu").hasSameElementsAs(retrievedCollection);
    }

    @Test
    @DirtiesContext
    void itShouldFindInterestPerCommitFile() {
        Projects projects = new Projects(1L, "testOwner", "testRepo", "testURL");
        projectsRepositoryUnderTest.save(projects);

        Files f1 = new Files(1L, "testPath", "", projects, "testSha");

        filesRepositoryUnderTest.save(f1);

        Metrics metricsTest = new Metrics(1L, projects, f1, new BigDecimal("5.00"), "testSha", 1L);
        metricsTest.setInterestHours(new BigDecimal("0.0"));

        metricsRepositoryUnderTest.save(metricsTest);

        Collection<InterestPerCommitFile> expectedCollection
                = new ArrayList<>(Arrays.asList(new InterestPerCommitFile("testSha", "testPath", 1L, new BigDecimal("5.00"), new BigDecimal("0.0"))));

        Collection<InterestPerCommitFile> retrievedCollection = metricsRepositoryUnderTest.findInterestPerCommitFile(new ProjectDomain("https://github.com/testOwner/testRepo"), "testSha", "testPath");

        assertThat(expectedCollection).usingElementComparatorOnFields("sha", "filePath", "interestEu").hasSameElementsAs(retrievedCollection);
    }

    @Test
    @DirtiesContext
    void itShouldFindInterestChangePerCommit() {
        Projects projects = new Projects(1L, "testOwner", "testRepo", "testURL");
        projectsRepositoryUnderTest.save(projects);

        Files f1 = new Files(1L, "testPath", "", projects, "testSha");
        Files f2 = new Files(2L, "testPath2", "", projects, "testSha");

        filesRepositoryUnderTest.save(f1);
        filesRepositoryUnderTest.save(f2);

        Metrics metricsTest = new Metrics(1L, projects, f1, new BigDecimal("5.0"), "testSha", 1L);
        Metrics metricsTest2 = new Metrics(2L, projects, f2, new BigDecimal("2.0"), "testSha2", 2L);

        metricsRepositoryUnderTest.save(metricsTest);
        metricsRepositoryUnderTest.save(metricsTest2);

        Collection<InterestChange> expectedCollection
                = new ArrayList<>(Arrays.asList(new InterestChange("testSha2", 2L, new BigDecimal("-3.0"), new BigDecimal("0.0"), new BigDecimal("-0.6"))));

        Collection<InterestChange> retrievedCollection = metricsRepositoryUnderTest.findInterestChangeByCommit(new ProjectDomain("https://github.com/testOwner/testRepo"), "testSha2");

        assertThat(expectedCollection).usingElementComparatorOnFields("sha", "changeEu", "changePercentage").hasSameElementsAs(retrievedCollection);

    }

    @Test
    @DirtiesContext
    void itShouldFindNormalizedInterestByCommit() {
        Projects projects = new Projects(1L, "testOwner", "testRepo", "testURL");
        projectsRepositoryUnderTest.save(projects);

        Files f1 = new Files(1L, "testPath", "", projects, "testSha");
        Files f2 = new Files(2L, "testPath2", "", projects, "testSha");
        Files f3 = new Files(3L, "testPath3", "", projects, "testSha");

        filesRepositoryUnderTest.save(f1);
        filesRepositoryUnderTest.save(f2);
        filesRepositoryUnderTest.save(f3);

        Metrics metricsTest = new Metrics(1L, projects, f1, new BigDecimal("5.0"), "testSha", 1L);
        Metrics metricsTest2 = new Metrics(2L, projects, f2, new BigDecimal("2.0"), "testSha", 1L);
        Metrics metricsTest3 = new Metrics(3L, projects, f3, new BigDecimal("510.0"), "testSha", 1L);
        metricsTest.setSize1(10);
        metricsTest2.setSize1(20);
        metricsTest3.setSize1(10);

        metricsRepositoryUnderTest.save(metricsTest);
        metricsRepositoryUnderTest.save(metricsTest2);
        metricsRepositoryUnderTest.save(metricsTest3);

        Collection<NormalizedInterest> expectedCollection
                = new ArrayList<>(Arrays.asList(new NormalizedInterest("testSha", 1L, new BigDecimal("12.925"), new BigDecimal("0.0"))));

        Collection<NormalizedInterest> retrievedCollection = metricsRepositoryUnderTest.findNormalizedInterestByCommit(new ProjectDomain("https://github.com/testOwner/testRepo"), "testSha");

        assertThat(expectedCollection).usingElementComparatorOnFields("sha", "normalizedInterestEu").hasSameElementsAs(retrievedCollection);
    }

    @Test
    @DirtiesContext
    void itShouldFindHighInterestFiles() {
        Projects projects = new Projects(1L, "testOwner", "testRepo", "testURL");
        projectsRepositoryUnderTest.save(projects);

        Files f1 = new Files(1L, "testPath", "", projects, "testSha");
        Files f2 = new Files(2L, "testPath2", "", projects, "testSha");
        Files f3 = new Files(3L, "testPath3", "", projects, "testSha");

        filesRepositoryUnderTest.save(f1);
        filesRepositoryUnderTest.save(f2);
        filesRepositoryUnderTest.save(f3);

        Metrics metricsTest = new Metrics(1L, projects, f1, new BigDecimal("100.0"), "testSha", 1L);
        metricsTest.setInterestHours(new BigDecimal("0.0"));
        Metrics metricsTest2 = new Metrics(2L, projects, f2, new BigDecimal("500.0"), "testSha", 1L);
        metricsTest2.setInterestHours(new BigDecimal("0.0"));
        Metrics metricsTest3 = new Metrics(3L, projects, f3, new BigDecimal("400.0"), "testSha", 1L);
        metricsTest3.setInterestHours(new BigDecimal("0.0"));

        metricsRepositoryUnderTest.save(metricsTest);
        metricsRepositoryUnderTest.save(metricsTest2);
        metricsRepositoryUnderTest.save(metricsTest3);

        Collection<HighInterestFile> expectedCollection
                = new ArrayList<>(Arrays.asList(new HighInterestFile("testSha", 1L, "testPath2", new BigDecimal("500.0"), new BigDecimal("0.0"), new BigDecimal("0.5"))));

        Collection<HighInterestFile> retrievedCollection = metricsRepositoryUnderTest.findHighInterestFiles(PageRequest.of(0, 1),new ProjectDomain("https://github.com/testOwner/testRepo"), "testSha").getContent();

        assertThat(expectedCollection).usingElementComparatorOnFields("sha", "interestEu", "interestPercentageOfProject").hasSameElementsAs(retrievedCollection);
    }

    @Test
    @DirtiesContext
    void itShouldFindAnalyzedCommits() {

        Projects projects = new Projects(1L, "testOwner", "testRepo", "testURL");
        projectsRepositoryUnderTest.save(projects);

        Files f1 = new Files(1L, "testPath", "", projects, "testSha");
        Files f2 = new Files(2L, "testPath2", "", projects, "testSha2");

        filesRepositoryUnderTest.save(f1);
        filesRepositoryUnderTest.save(f2);

        Metrics metricsTest = new Metrics(1L, projects, f1, new BigDecimal("5.0"), "testSha", 1L);
        Metrics metricsTest2 = new Metrics(2L, projects, f2, new BigDecimal("2.0"), "testSha2", 2L);

        metricsRepositoryUnderTest.save(metricsTest);
        metricsRepositoryUnderTest.save(metricsTest2);

        Collection<AnalyzedCommit> expectedCollection
                = new ArrayList<>(Arrays.asList(new AnalyzedCommit("testSha", 1L), new AnalyzedCommit("testSha2", 2L)));

        Collection<AnalyzedCommit> retrievedCollection = metricsRepositoryUnderTest.findAnalyzedCommits(null, new ProjectDomain("https://github.com/testOwner/testRepo")).getContent();

        assertThat(expectedCollection).usingElementComparatorOnFields("sha", "revisionCount").hasSameElementsAs(retrievedCollection);
    }
}