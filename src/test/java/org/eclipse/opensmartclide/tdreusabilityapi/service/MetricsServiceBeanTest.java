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

import org.eclipse.opensmartclide.tdreusabilityapi.AbstractBaseTest;
import org.eclipse.opensmartclide.tdreusabilityapi.domain.ProjectDomain;
import org.eclipse.opensmartclide.tdreusabilityapi.persistence.MetricsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

/**
 * @author Dimitrios Zisis <zisisndimitris@gmail.com>
 */
@ExtendWith(MockitoExtension.class)
class MetricsServiceBeanTest extends AbstractBaseTest {

    @Mock
    private MetricsRepository metricsRepository;
    private MetricsService underTest;

    @BeforeEach
    void setUp() {
        underTest = new MetricsServiceBean(metricsRepository);
    }

    @Test
    void findCumulativeInterestPerCommit() {
        String url = "https://github.com/apache/commons-io";
        ProjectDomain project = new ProjectDomain(url);

        underTest.findCumulativeInterestPerCommit(url);

        ArgumentCaptor<ProjectDomain> projectArgumentCaptor = ArgumentCaptor.forClass(ProjectDomain.class);
        verify(metricsRepository).findCumulativeInterestPerCommit(projectArgumentCaptor.capture());

        assertThat(projectArgumentCaptor.getValue()).isEqualTo(project);
    }

    @Test
    void findCumulativeInterestByCommit() {
        String url = "https://github.com/apache/commons-io";
        String sha = "672dc80a1ce1bb0cc07c667304c737483ad13889";
        ProjectDomain project = new ProjectDomain(url);

        underTest.findCumulativeInterestByCommit(url, sha);

        ArgumentCaptor<ProjectDomain> projectArgumentCaptor = ArgumentCaptor.forClass(ProjectDomain.class);
        ArgumentCaptor<String> shaArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(metricsRepository).findCumulativeInterestByCommit(projectArgumentCaptor.capture(), shaArgumentCaptor.capture());

        assertThat(projectArgumentCaptor.getValue()).isEqualTo(project);
        assertThat(shaArgumentCaptor.getValue()).isEqualTo(sha);
    }

    @Test
    void findInterestByCommitFile() {
        String url = "https://github.com/apache/commons-io";
        String sha = "49953ef7e671441aefc1890d743df3e1ba71e713";
        String filePath = "src/main/java/org/apache/commons/io/file/Counters.java";
        ProjectDomain project = new ProjectDomain(url);

        underTest.findInterestByCommitFile(url, sha, filePath);

        ArgumentCaptor<ProjectDomain> projectArgumentCaptor = ArgumentCaptor.forClass(ProjectDomain.class);
        ArgumentCaptor<String> shaArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> filePathArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(metricsRepository).findInterestPerCommitFile(projectArgumentCaptor.capture(), shaArgumentCaptor.capture(), filePathArgumentCaptor.capture());

        assertThat(projectArgumentCaptor.getValue()).isEqualTo(project);
        assertThat(shaArgumentCaptor.getValue()).isEqualTo(sha);
        assertThat(filePathArgumentCaptor.getValue()).isEqualTo(filePath);
    }

    @Test
    void findLastCommitInterestChange() {
        String url = "https://github.com/apache/commons-io";
        String sha = "49953ef7e671441aefc1890d743df3e1ba71e713";
        ProjectDomain project = new ProjectDomain(url);

        underTest.findInterestChangeByCommit(url, sha);

        ArgumentCaptor<ProjectDomain> projectArgumentCaptor = ArgumentCaptor.forClass(ProjectDomain.class);
        ArgumentCaptor<String> shaArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(metricsRepository).findInterestChangeByCommit(projectArgumentCaptor.capture(), shaArgumentCaptor.capture());

        assertThat(projectArgumentCaptor.getValue()).isEqualTo(project);
        assertThat(shaArgumentCaptor.getValue()).isEqualTo(sha);
    }

    @Test
    void findNormalizedInterest() {
        String url = "https://github.com/apache/commons-io";
        ProjectDomain project = new ProjectDomain(url);

        underTest.findNormalizedInterest(url);

        ArgumentCaptor<ProjectDomain> projectArgumentCaptor = ArgumentCaptor.forClass(ProjectDomain.class);
        verify(metricsRepository).findNormalizedInterest(projectArgumentCaptor.capture());

        assertThat(projectArgumentCaptor.getValue()).isEqualTo(project);
    }

    @Test
    void findNormalizedInterestByCommit() {
        String url = "https://github.com/apache/commons-io";
        String sha = "49953ef7e671441aefc1890d743df3e1ba71e713";
        ProjectDomain project = new ProjectDomain(url);

        underTest.findNormalizedInterestByCommit(url, sha);

        ArgumentCaptor<ProjectDomain> projectArgumentCaptor = ArgumentCaptor.forClass(ProjectDomain.class);
        ArgumentCaptor<String> shaArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(metricsRepository).findNormalizedInterestByCommit(projectArgumentCaptor.capture(), shaArgumentCaptor.capture());

        assertThat(projectArgumentCaptor.getValue()).isEqualTo(project);
        assertThat(shaArgumentCaptor.getValue()).isEqualTo(sha);
    }
}
