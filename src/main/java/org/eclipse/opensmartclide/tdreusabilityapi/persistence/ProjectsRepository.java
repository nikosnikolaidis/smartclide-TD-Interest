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

import org.eclipse.opensmartclide.tdreusabilityapi.controller.response.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.eclipse.opensmartclide.tdreusabilityapi.domain.Projects;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
@Repository
public interface ProjectsRepository extends JpaRepository<Projects, Long> {

    @Query(value = "SELECT new org.eclipse.opensmartclide.tdreusabilityapi.controller.response.entity.Project(p.pid, p.url, p.owner, p.repo) "
            + "FROM Projects p "
            + "WHERE p.owner = ?1 AND p.repo = ?2")
    Project findProject(String owner, String repo);

}
