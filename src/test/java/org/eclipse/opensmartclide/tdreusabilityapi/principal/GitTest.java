/*******************************************************************************
 * Copyright (C) 2021-2022 University of Macedonia
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.opensmartclide.tdreusabilityapi.principal;

import org.eclipse.opensmartclide.tdreusabilityapi.service.principal.analysis.Git;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GitTest {

    static Git git;

    @BeforeAll
    static void setup() {
        git = new Git("https://github.com/apache/commons-io","");
    }

    @Test
    void testGitOwnerName() {
        assertEquals("apache",git.getOwnerName());
    }

    @Test
    void testGitProjectName() {
        assertEquals("commons-io",git.getProjectName());
    }

    @Test
    void testGitToken() {
        assertEquals("",git.getToken());
    }
}
