package gr.zisis.interestapi.persistence;

import gr.zisis.interestapi.domain.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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

/**
 * @author Dimitrios Zisis <zisisndimitris@gmail.com>
 */
@Repository
public interface FilesRepository extends JpaRepository<Files, Long> {
}
