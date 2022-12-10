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

import org.eclipse.opensmartclide.tdreusabilityapi.controller.response.entity.Project;

import java.io.IOException;
public interface ProjectsService {
    Project save(String url, String VCSAccessToken) throws IOException, InterruptedException;
}
