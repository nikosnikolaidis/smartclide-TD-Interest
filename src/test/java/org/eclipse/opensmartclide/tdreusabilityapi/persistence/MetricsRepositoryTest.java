/*******************************************************************************
 * Copyright (C) 2021-2022 University of Macedonia
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.opensmartclide.tdreusabilityapi.persistence;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.eclipse.opensmartclide.tdreusabilityapi.AbstractBaseTest;
import org.eclipse.opensmartclide.tdreusabilityapi.controller.response.entity.AnalyzedCommit;
import org.eclipse.opensmartclide.tdreusabilityapi.controller.response.entity.CumulativeInterest;
import org.eclipse.opensmartclide.tdreusabilityapi.controller.response.entity.HighInterestFile;
import org.eclipse.opensmartclide.tdreusabilityapi.controller.response.entity.InterestChange;
import org.eclipse.opensmartclide.tdreusabilityapi.controller.response.entity.InterestPerCommitFile;
import org.eclipse.opensmartclide.tdreusabilityapi.controller.response.entity.NormalizedInterest;
import org.eclipse.opensmartclide.tdreusabilityapi.domain.Files;
import org.eclipse.opensmartclide.tdreusabilityapi.domain.Metrics;
import org.eclipse.opensmartclide.tdreusabilityapi.domain.ProjectDomain;
import org.eclipse.opensmartclide.tdreusabilityapi.domain.Projects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Dimitrios Zisis <zisisndimitris@gmail.com>
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MetricsRepositoryTest extends AbstractBaseTest {

    @Autowired
    private MetricsRepository metricsRepositoryUnderTest;

    @Autowired
    private ProjectsRepository projectsRepositoryUnderTest;

    @Autowired
    private FilesRepository filesRepositoryUnderTest;

    @Test
//    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    void itShouldFindCumulativeInterestPerCommit() {

        Projects projects = new Projects(PID.incrementAndGet(), "testOwner", "testRepo", "testURL");
        projectsRepositoryUnderTest.save(projects);

        Files f1 = new Files(FID.incrementAndGet(), "testPath", new String[]{""}, projects, "testSha");
        Files f2 = new Files(FID.incrementAndGet(), "testPath2", new String[]{""}, projects, "testSha");
        Files f3 = new Files(FID.incrementAndGet(), "testPath3", new String[]{""}, projects, "testSha");
        Files f4 = new Files(FID.incrementAndGet(), "testPath", new String[]{""}, projects, "testSha2");
        Files f5 = new Files(FID.incrementAndGet(), "testPath2", new String[]{""}, projects, "testSha2");
        Files f6 = new Files(FID.incrementAndGet(), "testPath3", new String[]{""}, projects, "testSha2");

        filesRepositoryUnderTest.save(f1);
        filesRepositoryUnderTest.save(f2);
        filesRepositoryUnderTest.save(f3);
        filesRepositoryUnderTest.save(f4);
        filesRepositoryUnderTest.save(f5);
        filesRepositoryUnderTest.save(f6);

        Metrics metricsTest = new Metrics(MID.incrementAndGet(), projects, f1, new BigDecimal("5.0"), "testSha", 1L);
        metricsTest.setInterestHours(new BigDecimal("0.0"));
        Metrics metricsTest2 = new Metrics(MID.incrementAndGet(), projects, f2, new BigDecimal("2.0"), "testSha", 1L);
        metricsTest2.setInterestHours(new BigDecimal("0.0"));
        Metrics metricsTest3 = new Metrics(MID.incrementAndGet(), projects, f3, new BigDecimal("510.0"), "testSha", 1L);
        metricsTest3.setInterestHours(new BigDecimal("0.0"));

        Metrics metricsTest4 = new Metrics(MID.incrementAndGet(), projects, f4, new BigDecimal("2.0"), "testSha2", 2L);
        metricsTest4.setInterestHours(new BigDecimal("0.0"));
        Metrics metricsTest5 = new Metrics(MID.incrementAndGet(), projects, f5, new BigDecimal("3.0"), "testSha2", 2L);
        metricsTest5.setInterestHours(new BigDecimal("0.0"));
        Metrics metricsTest6 = new Metrics(MID.incrementAndGet(), projects, f6, new BigDecimal("5.0"), "testSha2", 2L);
        metricsTest6.setInterestHours(new BigDecimal("0.0"));

        metricsRepositoryUnderTest.save(metricsTest);
        metricsRepositoryUnderTest.save(metricsTest2);
        metricsRepositoryUnderTest.save(metricsTest3);

        metricsRepositoryUnderTest.save(metricsTest4);
        metricsRepositoryUnderTest.save(metricsTest5);
        metricsRepositoryUnderTest.save(metricsTest6);

        Collection<CumulativeInterest> expectedCollection
                = List.of(new CumulativeInterest("testSha", 1L, new BigDecimal("517.0"), new BigDecimal("0.0")),
                          new CumulativeInterest("testSha2", 2L, new BigDecimal("10.0"), new BigDecimal("0.0")));

        Collection<CumulativeInterest> retrievedCollection = metricsRepositoryUnderTest.findCumulativeInterestPerCommit(new ProjectDomain("https://github.com/testOwner/testRepo"));

        assertThat(retrievedCollection)
                .usingComparatorForElementFieldsWithType(Comparator.naturalOrder(), BigDecimal.class)
                .usingElementComparatorOnFields("sha", "interestEu")
                .hasSameElementsAs(expectedCollection);

    }

    @Test
//    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    void itShouldFindCumulativeInterestByCommit() {
        Projects projects = new Projects(PID.incrementAndGet(), "testOwner", "testRepo", "testURL");
        projectsRepositoryUnderTest.save(projects);

        Files f1 = new Files(FID.incrementAndGet(), "testPath", new String[]{""}, projects, "testSha");
        Files f2 = new Files(FID.incrementAndGet(), "testPath2", new String[]{""}, projects, "testSha");
        Files f3 = new Files(FID.incrementAndGet(), "testPath3", new String[]{""}, projects, "testSha");

        filesRepositoryUnderTest.save(f1);
        filesRepositoryUnderTest.save(f2);
        filesRepositoryUnderTest.save(f3);

        Metrics metricsTest = new Metrics(MID.incrementAndGet(), projects, f1, new BigDecimal("5.0"), "testSha", 1L);
        metricsTest.setInterestHours(new BigDecimal("0.0"));
        Metrics metricsTest2 = new Metrics(MID.incrementAndGet(), projects, f2, new BigDecimal("2.0"), "testSha", 1L);
        metricsTest2.setInterestHours(new BigDecimal("0.0"));
        Metrics metricsTest3 = new Metrics(MID.incrementAndGet(), projects, f3, new BigDecimal("510.0"), "testSha", 1L);
        metricsTest3.setInterestHours(new BigDecimal("0.0"));

        metricsRepositoryUnderTest.save(metricsTest);
        metricsRepositoryUnderTest.save(metricsTest2);
        metricsRepositoryUnderTest.save(metricsTest3);

        Collection<CumulativeInterest> expectedCollection
                = List.of(new CumulativeInterest("testSha", 1L, new BigDecimal("517.0"), new BigDecimal("0.0")));

        Collection<CumulativeInterest> retrievedCollection = metricsRepositoryUnderTest.findCumulativeInterestByCommit(new ProjectDomain("https://github.com/testOwner/testRepo"), "testSha");

        assertThat(expectedCollection)
                .usingComparatorForElementFieldsWithType(Comparator.naturalOrder(), BigDecimal.class)
                .usingElementComparatorOnFields("sha", "interestEu")
                .hasSameElementsAs(retrievedCollection);
    }

    @Test
//    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    void itShouldFindInterestPerCommitFile() {
        Projects projects = new Projects(PID.incrementAndGet(), "testOwner", "testRepo", "testURL");
        projectsRepositoryUnderTest.save(projects);

        Files f1 = new Files(FID.incrementAndGet(), "testPath", new String[]{""}, projects, "testSha");

        filesRepositoryUnderTest.save(f1);

        Metrics metricsTest = new Metrics(MID.incrementAndGet(), projects, f1, new BigDecimal("5.00"), "testSha", 1L);
        metricsTest.setInterestHours(new BigDecimal("0.0"));

        metricsRepositoryUnderTest.save(metricsTest);

        Collection<InterestPerCommitFile> expectedCollection
                = List.of(new InterestPerCommitFile("testSha", "testPath", 1L, new BigDecimal("5.00"), new BigDecimal("0.0")));

        Collection<InterestPerCommitFile> retrievedCollection = metricsRepositoryUnderTest.findInterestPerCommitFile(new ProjectDomain("https://github.com/testOwner/testRepo"), "testSha", "testPath");

        assertThat(expectedCollection)
                .usingComparatorForElementFieldsWithType(Comparator.naturalOrder(), BigDecimal.class)
                .usingElementComparatorOnFields("sha", "filePath", "interestEu")
                .hasSameElementsAs(retrievedCollection);
    }

    @Test
//    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    void itShouldFindInterestChangePerCommit() {
        Projects projects = new Projects(PID.incrementAndGet(), "testOwner", "testRepo", "testURL");
        projectsRepositoryUnderTest.save(projects);

        Files f1 = new Files(FID.incrementAndGet(), "testPath", new String[]{""}, projects, "testSha");
        Files f2 = new Files(FID.incrementAndGet(), "testPath2", new String[]{""}, projects, "testSha");

        filesRepositoryUnderTest.save(f1);
        filesRepositoryUnderTest.save(f2);

        Metrics metricsTest = new Metrics(MID.incrementAndGet(), projects, f1, new BigDecimal("5.0"), "testSha", 1L);
        Metrics metricsTest2 = new Metrics(MID.incrementAndGet(), projects, f2, new BigDecimal("2.0"), "testSha2", 2L);

        metricsRepositoryUnderTest.save(metricsTest);
        metricsRepositoryUnderTest.save(metricsTest2);

        Collection<InterestChange> expectedCollection
                = List.of(new InterestChange("testSha2", 2L, new BigDecimal("-3.0"), new BigDecimal("0.0"), new BigDecimal("-0.6")));

        Collection<InterestChange> retrievedCollection = metricsRepositoryUnderTest.findInterestChangeByCommit(new ProjectDomain("https://github.com/testOwner/testRepo"), "testSha2");

        assertThat(expectedCollection)
                .usingComparatorForElementFieldsWithType(Comparator.naturalOrder(), BigDecimal.class)
                .usingElementComparatorOnFields("sha", "changeEu", "changePercentage")
                .hasSameElementsAs(retrievedCollection);

    }

    @Test
//    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    void itShouldFindNormalizedInterestByCommit() {
        Projects projects = new Projects(PID.incrementAndGet(), "testOwner", "testRepo", "testURL");
        projectsRepositoryUnderTest.save(projects);

        Files f1 = new Files(FID.incrementAndGet(), "testPath", new String[]{""}, projects, "testSha");
        Files f2 = new Files(FID.incrementAndGet(), "testPath2", new String[]{""}, projects, "testSha");
        Files f3 = new Files(FID.incrementAndGet(), "testPath3", new String[]{""}, projects, "testSha");

        filesRepositoryUnderTest.save(f1);
        filesRepositoryUnderTest.save(f2);
        filesRepositoryUnderTest.save(f3);

        Metrics metricsTest = new Metrics(MID.incrementAndGet(), projects, f1, new BigDecimal("5.0"), "testSha", 1L);
        Metrics metricsTest2 = new Metrics(MID.incrementAndGet(), projects, f2, new BigDecimal("2.0"), "testSha", 1L);
        Metrics metricsTest3 = new Metrics(MID.incrementAndGet(), projects, f3, new BigDecimal("510.0"), "testSha", 1L);
        metricsTest.setSize1(10);
        metricsTest2.setSize1(20);
        metricsTest3.setSize1(10);

        metricsRepositoryUnderTest.save(metricsTest);
        metricsRepositoryUnderTest.save(metricsTest2);
        metricsRepositoryUnderTest.save(metricsTest3);

        Collection<NormalizedInterest> expectedCollection
                = List.of(new NormalizedInterest("testSha", 1L, new BigDecimal("12.925"), new BigDecimal("0.0")));

        Collection<NormalizedInterest> retrievedCollection = metricsRepositoryUnderTest.findNormalizedInterestByCommit(new ProjectDomain("https://github.com/testOwner/testRepo"), "testSha");

        assertThat(expectedCollection)
                .usingComparatorForElementFieldsWithType(Comparator.naturalOrder(), BigDecimal.class)
                .usingElementComparatorOnFields("sha", "normalizedInterestEu")
                .hasSameElementsAs(retrievedCollection);
    }

    @Test
//    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    void itShouldFindHighInterestFiles() {
        Projects projects = new Projects(PID.incrementAndGet(), "testOwner", "testRepo", "testURL");
        projectsRepositoryUnderTest.save(projects);

        Files f1 = new Files(FID.incrementAndGet(), "testPath", new String[]{""}, projects, "testSha");
        Files f2 = new Files(FID.incrementAndGet(), "testPath2", new String[]{""}, projects, "testSha");
        Files f3 = new Files(FID.incrementAndGet(), "testPath3", new String[]{""}, projects, "testSha");

        filesRepositoryUnderTest.save(f1);
        filesRepositoryUnderTest.save(f2);
        filesRepositoryUnderTest.save(f3);

        Metrics metricsTest = new Metrics(MID.incrementAndGet(), projects, f1, new BigDecimal("100.0"), "testSha", 1L);
        metricsTest.setInterestHours(new BigDecimal("0.0"));
        Metrics metricsTest2 = new Metrics(MID.incrementAndGet(), projects, f2, new BigDecimal("500.0"), "testSha", 1L);
        metricsTest2.setInterestHours(new BigDecimal("0.0"));
        Metrics metricsTest3 = new Metrics(MID.incrementAndGet(), projects, f3, new BigDecimal("400.0"), "testSha", 1L);
        metricsTest3.setInterestHours(new BigDecimal("0.0"));

        metricsRepositoryUnderTest.save(metricsTest);
        metricsRepositoryUnderTest.save(metricsTest2);
        metricsRepositoryUnderTest.save(metricsTest3);

        Collection<HighInterestFile> expectedCollection
                = List.of(new HighInterestFile("testSha", 1L, "testPath2", new BigDecimal("500.0"), new BigDecimal("0.0"), new BigDecimal("0.5")));

        Collection<HighInterestFile> retrievedCollection = metricsRepositoryUnderTest.findHighInterestFiles(PageRequest.of(0, 1), new ProjectDomain("https://github.com/testOwner/testRepo"), "testSha").getContent();

        assertThat(expectedCollection)
                .usingComparatorForElementFieldsWithType(Comparator.naturalOrder(), BigDecimal.class)
                .usingElementComparatorOnFields("sha", "interestEu", "interestPercentageOfProject")
                .hasSameElementsAs(retrievedCollection);
    }

    @Test
//    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    void itShouldFindAnalyzedCommits() {

        Projects projects = new Projects(PID.incrementAndGet(), "testOwner", "testRepo", "testURL");
        projectsRepositoryUnderTest.save(projects);

        Files f1 = new Files(FID.incrementAndGet(), "testPath", new String[]{""}, projects, "testSha");
        Files f2 = new Files(FID.incrementAndGet(), "testPath2", new String[]{""}, projects, "testSha2");

        filesRepositoryUnderTest.save(f1);
        filesRepositoryUnderTest.save(f2);

        Metrics metricsTest = new Metrics(MID.incrementAndGet(), projects, f1, new BigDecimal("5.0"), "testSha", 1L);
        Metrics metricsTest2 = new Metrics(MID.incrementAndGet(), projects, f2, new BigDecimal("2.0"), "testSha2", 2L);

        metricsRepositoryUnderTest.save(metricsTest);
        metricsRepositoryUnderTest.save(metricsTest2);

        Collection<AnalyzedCommit> expectedCollection
                = List.of(new AnalyzedCommit("testSha", 1L), new AnalyzedCommit("testSha2", 2L));

        Collection<AnalyzedCommit> retrievedCollection = metricsRepositoryUnderTest.findAnalyzedCommits(null, new ProjectDomain("https://github.com/testOwner/testRepo")).getContent();

        assertThat(expectedCollection)
                .usingComparatorForElementFieldsWithType(Comparator.naturalOrder(), BigDecimal.class)
                .usingElementComparatorOnFields("sha", "revisionCount")
                .hasSameElementsAs(retrievedCollection);
    }
}
