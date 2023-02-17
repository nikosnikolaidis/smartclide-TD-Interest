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

import org.eclipse.opensmartclide.tdreusabilityapi.domain.principal.Issue;
import org.eclipse.opensmartclide.tdreusabilityapi.domain.principal.Report;
import org.eclipse.opensmartclide.tdreusabilityapi.service.principal.analysis.LocalAnalysis;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocalAnalysisTest {

    static String absolutePath;

    @BeforeAll
    static void setup(){
        String path = "src/test/resources/testProject";
        File file = new File(path);
        absolutePath = file.getAbsolutePath();
    }

    @Test
    void testPath(){
        String widPath = "src\\test\\resources\\testProject";
        String linPath = "src/test/resources/testProject";
        assertTrue(absolutePath.endsWith(widPath) || absolutePath.endsWith(linPath));
    }

    @Test
    void testLocalAnalysisGetMappings(){
        LocalAnalysis localAnalysis = new LocalAnalysis();
        localAnalysis.getAllFiles(new File(absolutePath));
        System.out.println("Get all endpoints");
        try {
            localAnalysis.getMappingsFromAllFiles(new File(absolutePath));
            assert true;
        } catch (IOException e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    void testLocalAnalysisCallTree(){
        LocalAnalysis localAnalysis = new LocalAnalysis();
        localAnalysis.getAllFiles(new File(absolutePath));
        System.out.println("Get all endpoints");
        try {
            localAnalysis.getMappingsFromAllFiles(new File(absolutePath));
            List<Issue> allIssues = new ArrayList<>();
            List<Report> reportList= localAnalysis.getAllReportForAllEndpoints(absolutePath, "", allIssues);
            System.out.println(reportList);
            assert true;
        } catch (IOException e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    void testLocalAnalysisGetCallTreeSize(){
        LocalAnalysis localAnalysis = new LocalAnalysis();
        localAnalysis.getAllFiles(new File(absolutePath));
        System.out.println("Get all endpoints");
        try {
            localAnalysis.getMappingsFromAllFiles(new File(absolutePath));
            List<Issue> allIssues = new ArrayList<>();
            List<Report> reportList= localAnalysis.getAllReportForAllEndpoints(absolutePath, "", allIssues);
            assertEquals(3, reportList.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testLocalAnalysisGetCallTreeMethods(){
        LocalAnalysis localAnalysis = new LocalAnalysis();
        localAnalysis.getAllFiles(new File(absolutePath));
        System.out.println("Get all endpoints");
        try {
            localAnalysis.getMappingsFromAllFiles(new File(absolutePath));
            List<Issue> allIssues = new ArrayList<>();
            List<Report> reportList= localAnalysis.getAllReportForAllEndpoints(absolutePath, "", allIssues);
            List<String> methods= Arrays.asList(reportList.get(0).getMethod(),
                                                reportList.get(1).getMethod(),
                                                reportList.get(2).getMethod());
            assertTrue(methods.contains("createStructure") &&
                    methods.contains("createStuctureJenkins") &&
                    methods.contains("getRepoURL"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testLocalAnalysisGetCallTreeMetricsName(){
        LocalAnalysis localAnalysis = new LocalAnalysis();
        localAnalysis.getAllFiles(new File(absolutePath));
        System.out.println("Get all endpoints");
        try {
            localAnalysis.getMappingsFromAllFiles(new File(absolutePath));
            List<Issue> allIssues = new ArrayList<>();
            List<Report> reportList= localAnalysis.getAllReportForAllEndpoints(absolutePath, "", allIssues);
            assertEquals("TD", reportList.get(0).getMetrics().getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testLocalAnalysisGetCallTreeMetricsValue(){
        LocalAnalysis localAnalysis = new LocalAnalysis();
        localAnalysis.getAllFiles(new File(absolutePath));
        System.out.println("Get all endpoints");
        try {
            localAnalysis.getMappingsFromAllFiles(new File(absolutePath));
            List<Issue> allIssues = new ArrayList<>();
            List<Report> reportList= localAnalysis.getAllReportForAllEndpoints(absolutePath, "", allIssues);
            assertEquals(0, reportList.get(0).getMetrics().getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
