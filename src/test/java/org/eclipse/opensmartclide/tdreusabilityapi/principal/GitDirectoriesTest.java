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
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GitDirectoriesTest {

    @Test
    void testGitParentDirectories() throws InterruptedException {
        Git git = new Git("https://github.com/apache/commons-io","");
        TimeUnit.MILLISECONDS.sleep(10);
        Git git1 = new Git("https://github.com/apache/commons-io","");

        assertNotEquals(git.getLocalParentPath(), git1.getLocalParentPath());
    }

    @Test
    void testGitDirectoriesSameProject() throws InterruptedException {
        Git git = new Git("https://github.com/apache/commons-io","");
        TimeUnit.MILLISECONDS.sleep(10);
        Git git1 = new Git("https://github.com/apache/commons-io","");

        assertNotEquals(git.getLocalPath(), git1.getLocalPath());
    }

    @Test
    void testGitSameProjectFolder() throws InterruptedException {
        Git git = new Git("https://github.com/apache/commons-io","");
        TimeUnit.MILLISECONDS.sleep(10);
        Git git1 = new Git("https://github.com/apache/commons-io","");

        String path= git.getLocalPath().split("/")[git.getLocalPath().split("/").length-1];
        String path1= git1.getLocalPath().split("/")[git1.getLocalPath().split("/").length-1];
        assertEquals(path, path1);
    }
}
