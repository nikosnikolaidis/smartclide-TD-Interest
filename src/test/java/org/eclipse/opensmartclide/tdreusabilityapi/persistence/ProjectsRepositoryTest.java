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

import org.eclipse.opensmartclide.tdreusabilityapi.AbstractBaseTest;
import org.eclipse.opensmartclide.tdreusabilityapi.controller.response.entity.Project;
import org.eclipse.opensmartclide.tdreusabilityapi.domain.Projects;
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
