/*******************************************************************************
 * Copyright (C) 2021-2022 UoM - University of Macedonia
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.opensmartclide.tdreusabilityapi.service.reusability;

import org.eclipse.opensmartclide.tdreusabilityapi.controller.reusability.entity.FileReusabilityIndex;
import org.eclipse.opensmartclide.tdreusabilityapi.controller.reusability.entity.ProjectReusabilityIndex;

import java.util.Collection;

/**
 * @author Dimitrios Zisis <zisisndimitris@gmail.com>
 *
 */
public interface ReusabilityService {

    Collection<FileReusabilityIndex> findReusabilityIndexByCommit(String url, String sha, Integer limit);

    Collection<FileReusabilityIndex> findReusabilityIndexByCommitAndFile(String url, String sha, String filePath);

    Collection<ProjectReusabilityIndex> findProjectReusabilityIndexByCommit(String url, String sha);

    Collection<ProjectReusabilityIndex> findProjectReusabilityIndexPerCommit(String url, Integer limit);

}
