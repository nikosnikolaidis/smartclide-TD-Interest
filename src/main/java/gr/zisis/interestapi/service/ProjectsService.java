package gr.zisis.interestapi.service;

import gr.zisis.interestapi.controller.response.entity.Project;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

/*
 *
 *  * Copyright (C) 2021 UoM - University of Macedonia
 *  *
 *  * This program and the accompanying materials are made available under the
 *  * terms of the Eclipse Public License 2.0 which is available at
 *  * https://www.eclipse.org/legal/epl-2.0/
 *  *
 *  * SPDX-License-Identifier: EPL-2.0
 *
 */

public interface ProjectsService {
    Project save(String url) throws IOException, InterruptedException;
}
