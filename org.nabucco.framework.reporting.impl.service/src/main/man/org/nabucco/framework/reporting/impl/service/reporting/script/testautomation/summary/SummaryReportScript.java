/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.reporting.impl.service.reporting.script.testautomation.summary;

import java.math.BigInteger;

import org.nabucco.adapter.jasper.facade.datatype.reportdata.ReportDataList;
import org.nabucco.adapter.jasper.facade.datatype.reportdata.ReportEntryInteger;
import org.nabucco.adapter.jasper.facade.datatype.reportdata.ReportEntryString;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.impl.service.maintain.NabuccoQuery;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.reporting.impl.service.reporting.ReportingScript;

/**
 * SummaryReportScript
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class SummaryReportScript implements ReportingScript {

    private final String QUERY_LEVEL_COUNT = "SELECT COUNT(t.id) FROM conf_test_config_element as t, scma_schema_element as s "
            + "WHERE t.schema_element_ref_id = s.id AND s.name = ?";

    private final String QUERY_TEST_SCRIPT_COUNT = "SELECT COUNT(*) FROM scpt_test_script";

    private final String QUERY_TEST_CONFIG_COUNT = "SELECT COUNT(*) FROM conf_test_configuration";

    private final String LEVEL_NAME_TEST_SHEET = "Test Sheet";

    private final String LEVEL_NAME_TEST_CASE = "Test Case";

    private final String LEVEL_NAME_TEST_STEP = "Test Step";

    private String userName;

    public SummaryReportScript(String userName) {
        this.userName = userName;
    }

    @Override
    public ReportDataList createReportData(PersistenceManager persistenceManager) {

        try {
            return this.loadRoot(persistenceManager);
        } catch (PersistenceException pe) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Load the root report data.
     * 
     * @param persistenceManager
     *            the persistence manager
     * 
     * @return the root report data
     * 
     * @throws PersistenceException
     */
    private ReportDataList loadRoot(PersistenceManager persistenceManager) throws PersistenceException {
        NabuccoQuery<?> levelCount = persistenceManager.createNativeQuery(QUERY_LEVEL_COUNT);

        // test sheet
        levelCount.setParameter(1, LEVEL_NAME_TEST_SHEET);
        ReportEntryInteger testsheetCount = new ReportEntryInteger();
        testsheetCount.setKey("levelSum");
        testsheetCount.setValue(((BigInteger) levelCount.getSingleResult()).intValue());
        ReportEntryString testsheetName = new ReportEntryString();
        testsheetName.setKey("levelName");
        testsheetName.setValue("Test Sheets");
        ReportDataList testsheet = new ReportDataList();
        testsheet.setKey("result");
        testsheet.getReportDataList().add(testsheetCount);
        testsheet.getReportDataList().add(testsheetName);

        // test case
        levelCount.setParameter(1, LEVEL_NAME_TEST_CASE);
        ReportEntryInteger testcaseCount = new ReportEntryInteger();
        testcaseCount.setKey("levelSum");
        testcaseCount.setValue(((BigInteger) levelCount.getSingleResult()).intValue());
        ReportEntryString testcaseName = new ReportEntryString();
        testcaseName.setKey("levelName");
        testcaseName.setValue("Test Cases");
        ReportDataList testcase = new ReportDataList();
        testcase.setKey("result");
        testcase.getReportDataList().add(testcaseName);
        testcase.getReportDataList().add(testcaseCount);

        // test step
        levelCount.setParameter(1, LEVEL_NAME_TEST_STEP);
        ReportEntryInteger testsetpCount = new ReportEntryInteger();
        testsetpCount.setKey("levelSum");
        testsetpCount.setValue(((BigInteger) levelCount.getSingleResult()).intValue());
        ReportEntryString teststepName = new ReportEntryString();
        teststepName.setKey("levelName");
        teststepName.setValue("Test Steps");
        ReportDataList teststep = new ReportDataList();
        teststep.setKey("result");
        teststep.getReportDataList().add(testsetpCount);
        teststep.getReportDataList().add(teststepName);

        // sub data
        ReportDataList subReportData = new ReportDataList();
        subReportData.setKey("results");
        subReportData.getReportDataList().add(testsheet);
        subReportData.getReportDataList().add(testcase);
        subReportData.getReportDataList().add(teststep);

        // test script sum
        NabuccoQuery<?> testScriptCount = persistenceManager.createNativeQuery(QUERY_TEST_SCRIPT_COUNT);
        ReportEntryInteger testScriptSum = new ReportEntryInteger();
        testScriptSum.setKey("testScriptSum");
        testScriptSum.setValue(((BigInteger) testScriptCount.getSingleResult()).intValue());

        // test config sum
        NabuccoQuery<?> testConfigCount = persistenceManager.createNativeQuery(QUERY_TEST_CONFIG_COUNT);
        ReportEntryInteger testConfigSum = new ReportEntryInteger();
        testConfigSum.setKey("testConfigSum");
        testConfigSum.setValue(((BigInteger) testConfigCount.getSingleResult()).intValue());

        // user name
        ReportEntryString userName = new ReportEntryString();
        userName.setKey("userName");
        userName.setValue(this.userName);

        // main data
        ReportDataList reportData = new ReportDataList();
        reportData.setKey("mainData");
        reportData.getReportDataList().add(testScriptSum);
        reportData.getReportDataList().add(testConfigSum);
        reportData.getReportDataList().add(userName);
        reportData.getReportDataList().add(subReportData);

        // root
        ReportDataList root = new ReportDataList();
        root.setKey("root");
        root.getReportDataList().add(reportData);

        return root;
    }
}
