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
package org.nabucco.framework.reporting.impl.service.reporting.script.testautomation.testresult.simple;

import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.nabucco.adapter.jasper.facade.datatype.reportdata.JasperReportData;
import org.nabucco.adapter.jasper.facade.datatype.reportdata.ReportDataList;
import org.nabucco.adapter.jasper.facade.datatype.reportdata.ReportEntryInteger;
import org.nabucco.adapter.jasper.facade.datatype.reportdata.ReportEntryString;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.reporting.impl.service.reporting.ReportingScript;

/**
 * SimpleTestResultReportScript
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class SimpleTestResultReportScript implements ReportingScript {

    SimpleTestResultReportDatabaseAccessor databaseAccessor;

    @Override
    public ReportDataList createReportData(PersistenceManager persistenceManager) {
        this.databaseAccessor = new SimpleTestResultReportDatabaseAccessor(persistenceManager);
        List<Object[]> testConfigurationResults = databaseAccessor.getTestConfigurationResults();

        ReportDataList testResults = new ReportDataList();
        testResults.setKey("testResults");

        for (Object[] testConfigurationResult : testConfigurationResults) {
            // For each TestResult (one site in the PDF)
            try {
                testResults.getReportDataList().add(createTestConfigurationResultReportData(testConfigurationResult));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        ReportDataList subRoot = new ReportDataList();
        subRoot.getReportDataList().add(testResults);

        ReportDataList root = new ReportDataList();
        root.getReportDataList().add(subRoot);
        return root;
    }

    private JasperReportData createTestConfigurationResultReportData(Object[] testConfigurationResult)
            throws SQLException {

        ReportDataList testConfigurationResultReportData = new ReportDataList();
        testConfigurationResultReportData.setKey("testConfigurationResultReportData");

        /*
         * Header
         */
        ReportEntryString testResultName = new ReportEntryString();
        testResultName.setKey("testResultName");
        testResultName.setValue((String) testConfigurationResult[1]);
        testConfigurationResultReportData.getReportDataList().add(testResultName);

        ReportEntryString testConfigurationName = new ReportEntryString();
        testConfigurationName.setKey("testConfigurationName");
        testConfigurationName.setValue((String) testConfigurationResult[2]);
        testConfigurationResultReportData.getReportDataList().add(testConfigurationName);

        ReportEntryString testResultStartTime = new ReportEntryString();
        testResultStartTime.setKey("testResultStartTime");
        testResultStartTime.setValue(((Timestamp) testConfigurationResult[3]).toString());
        testConfigurationResultReportData.getReportDataList().add(testResultStartTime);

        ReportEntryString testResultEndTime = new ReportEntryString();
        testResultEndTime.setKey("testResultEndTime");
        testResultEndTime.setValue(((Timestamp) testConfigurationResult[4]).toString());
        testConfigurationResultReportData.getReportDataList().add(testResultEndTime);

        ReportEntryString testResultResult = new ReportEntryString();
        testResultResult.setKey("testResultResult");
        testResultResult.setValue("Successful");
        testConfigurationResultReportData.getReportDataList().add(testResultResult);

        List<Object[]> testSheetResultList = databaseAccessor
                .getTestSheetResultList((BigInteger) testConfigurationResult[0]);
        for (Object[] testResult : testSheetResultList) {
            if (testResult[2].equals("FAILED")) {
                testResultResult.setValue("Run with errors");
                break;
            }
        }

        // The two Tables
        testConfigurationResultReportData.getReportDataList().add(createSummaryTable(testConfigurationResult));
        testConfigurationResultReportData.getReportDataList().add(createDetailTable(testConfigurationResult));

        return testConfigurationResultReportData;
    }

    private JasperReportData createSummaryTable(Object[] testConfigurationResult) throws SQLException {

        ReportDataList summaryTable = new ReportDataList();
        summaryTable.setKey("summaryTable");

        /*
         * Row TestSheet
         */
        ReportDataList testSheetRow = new ReportDataList();
        testSheetRow.setKey("testSheetRow");
        summaryTable.getReportDataList().add(testSheetRow);

        int testSheetsExecuted = databaseAccessor
                .getNumberOfTestSheetsExecuted((BigInteger) testConfigurationResult[0]);
        int testSheetsSuccessful = databaseAccessor
                .getNumberOfTestSheetsExecutedSuccessfully((BigInteger) testConfigurationResult[0]);
        int testSheetsFailed = databaseAccessor.getNumberOfFailedTestSheets((BigInteger) testConfigurationResult[0]);
        int testSheetsSkipped = databaseAccessor.getNumberOfSkippedTestSheets((BigInteger) testConfigurationResult[0]);

        ReportEntryString testSheetNameCell = new ReportEntryString();
        testSheetNameCell.setKey("Cell1");
        testSheetNameCell.setValue("Test Sheets");
        testSheetRow.getReportDataList().add(testSheetNameCell);

        ReportEntryInteger testSheetsExecutedCell = new ReportEntryInteger();
        testSheetsExecutedCell.setKey("Cell2");
        testSheetsExecutedCell.setValue(testSheetsExecuted);
        testSheetRow.getReportDataList().add(testSheetsExecutedCell);

        ReportEntryInteger testSheetsSuccessfulCell = new ReportEntryInteger();
        testSheetsSuccessfulCell.setKey("Cell3");
        testSheetsSuccessfulCell.setValue(testSheetsSuccessful);
        testSheetRow.getReportDataList().add(testSheetsSuccessfulCell);

        ReportEntryInteger testSheetsFailedCell = new ReportEntryInteger();
        testSheetsFailedCell.setKey("Cell4");
        testSheetsFailedCell.setValue(testSheetsFailed);
        testSheetRow.getReportDataList().add(testSheetsFailedCell);

        ReportEntryInteger testSheetsSkippedCell = new ReportEntryInteger();
        testSheetsSkippedCell.setKey("Cell5");
        testSheetsSkippedCell.setValue(testSheetsSkipped);
        testSheetRow.getReportDataList().add(testSheetsSkippedCell);

        /*
         * Row TestCase
         */
        ReportDataList testCaseRow = new ReportDataList();
        testCaseRow.setKey("testCaseRow");
        summaryTable.getReportDataList().add(testCaseRow);

        int testCasesExecuted = databaseAccessor.getNumberOfTestCasesExecuted((BigInteger) testConfigurationResult[0]);
        int testCasesSuccessful = databaseAccessor
                .getNumberOfTestCasesExecutedSuccessfully((BigInteger) testConfigurationResult[0]);
        int testCasesFailed = databaseAccessor.getNumberOfFailedTestCases((BigInteger) testConfigurationResult[0]);
        int testCasesSkipped = databaseAccessor.getNumberOfSkippedTestCases((BigInteger) testConfigurationResult[0]);

        ReportEntryString testCaseNameCell = new ReportEntryString();
        testCaseNameCell.setKey("Cell1");
        testCaseNameCell.setValue("Test Cases");
        testCaseRow.getReportDataList().add(testCaseNameCell);

        ReportEntryInteger testCasesExecutedCell = new ReportEntryInteger();
        testCasesExecutedCell.setKey("Cell2");
        testCasesExecutedCell.setValue(testCasesExecuted);
        testCaseRow.getReportDataList().add(testCasesExecutedCell);

        ReportEntryInteger testCasesSuccessfulCell = new ReportEntryInteger();
        testCasesSuccessfulCell.setKey("Cell3");
        testCasesSuccessfulCell.setValue(testCasesSuccessful);
        testCaseRow.getReportDataList().add(testCasesSuccessfulCell);

        ReportEntryInteger testCasesFailedCell = new ReportEntryInteger();
        testCasesFailedCell.setKey("Cell4");
        testCasesFailedCell.setValue(testCasesFailed);
        testCaseRow.getReportDataList().add(testCasesFailedCell);

        ReportEntryInteger testCasesSkippedCell = new ReportEntryInteger();
        testCasesSkippedCell.setKey("Cell5");
        testCasesSkippedCell.setValue(testCasesSkipped);
        testCaseRow.getReportDataList().add(testCasesSkippedCell);

        /*
         * Row TestStep
         */
        ReportDataList testStepRow = new ReportDataList();
        testStepRow.setKey("testStepRow");
        summaryTable.getReportDataList().add(testStepRow);

        int testStepsExecuted = databaseAccessor.getNumberOfTestStepsExecuted((BigInteger) testConfigurationResult[0]);
        int testStepsSuccessful = databaseAccessor
                .getNumberOfTestStepsExecutedSuccessfully((BigInteger) testConfigurationResult[0]);
        int testStepsFailed = databaseAccessor.getNumberOfFailedTestSteps((BigInteger) testConfigurationResult[0]);
        int testStepsSkipped = databaseAccessor.getNumberOfSkippedTestSteps((BigInteger) testConfigurationResult[0]);

        ReportEntryString testStepNameCell = new ReportEntryString();
        testStepNameCell.setKey("Cell1");
        testStepNameCell.setValue("Test Steps");
        testStepRow.getReportDataList().add(testStepNameCell);

        ReportEntryInteger testStepsExecutedCell = new ReportEntryInteger();
        testStepsExecutedCell.setKey("Cell2");
        testStepsExecutedCell.setValue(testStepsExecuted);
        testStepRow.getReportDataList().add(testStepsExecutedCell);

        ReportEntryInteger testStepsSuccessfulCell = new ReportEntryInteger();
        testStepsSuccessfulCell.setKey("Cell3");
        testStepsSuccessfulCell.setValue(testStepsSuccessful);
        testStepRow.getReportDataList().add(testStepsSuccessfulCell);

        ReportEntryInteger testStepsFailedCell = new ReportEntryInteger();
        testStepsFailedCell.setKey("Cell4");
        testStepsFailedCell.setValue(testStepsFailed);
        testStepRow.getReportDataList().add(testStepsFailedCell);

        ReportEntryInteger testStepsSkippedCell = new ReportEntryInteger();
        testStepsSkippedCell.setKey("Cell5");
        testStepsSkippedCell.setValue(testStepsSkipped);
        testStepRow.getReportDataList().add(testStepsSkippedCell);

        return summaryTable;
    }

    private JasperReportData createDetailTable(Object[] testConfigurationResult) throws SQLException {

        ReportDataList detailTable = new ReportDataList();
        detailTable.setKey("detailTable");

        List<Object[]> testSheetResultList = databaseAccessor
                .getTestSheetResultList((BigInteger) testConfigurationResult[0]);
        for (Object[] testResult : testSheetResultList) {
            ReportDataList testSheetWrapper = new ReportDataList();
            testSheetWrapper.setKey("testSheetWrapper");
            detailTable.getReportDataList().add(testSheetWrapper);
            testSheetWrapper.getReportDataList().add(createDetailTableTestSheetTableRows(testResult));
        }
        return detailTable;
    }

    private JasperReportData createDetailTableTestSheetTableRows(Object[] testSheetResult) {
        ReportDataList testSheet = new ReportDataList();
        testSheet.setKey("testSheet");

        // Create Sheet Row
        ReportDataList testSheetRow = new ReportDataList();
        testSheetRow.setKey("testSheetRow");
        testSheet.getReportDataList().add(testSheetRow);

        ReportEntryString testSheetColumn = new ReportEntryString();
        testSheetColumn.setKey("testSheetColumn");
        testSheetColumn.setValue((String) testSheetResult[1]);
        testSheetRow.getReportDataList().add(testSheetColumn);

        ReportEntryString testCaseColumn = new ReportEntryString();
        testCaseColumn.setKey("testCaseColumn");
        testCaseColumn.setValue("");
        testSheetRow.getReportDataList().add(testCaseColumn);

        ReportEntryString testStepColumn = new ReportEntryString();
        testStepColumn.setKey("testStepColumn");
        testStepColumn.setValue("");
        testSheetRow.getReportDataList().add(testStepColumn);

        ReportEntryString resultColumn = new ReportEntryString();
        resultColumn.setKey("resultColumn");
        resultColumn.setValue((String) testSheetResult[2]);
        testSheetRow.getReportDataList().add(resultColumn);

        // Create TestCaseRows
        List<Object[]> testCaseResultList = databaseAccessor.getTestCaseResultList((BigInteger) testSheetResult[0]);
        for (Object[] testCaseResult : testCaseResultList) {
            createDetailTableTestCaseAndStepRows(testCaseResult, testSheet);
        }
        return testSheet;
    }

    private void createDetailTableTestCaseAndStepRows(Object[] testCaseResult, ReportDataList testSheet) {

        // Create Case Row
        ReportDataList testCaseRow = new ReportDataList();
        testCaseRow.setKey("testCaseRow");
        testSheet.getReportDataList().add(testCaseRow);

        ReportEntryString testSheetColumn = new ReportEntryString();
        testSheetColumn.setKey("testSheetColumn");
        testSheetColumn.setValue("");
        testCaseRow.getReportDataList().add(testSheetColumn);

        ReportEntryString testCaseColumn = new ReportEntryString();
        testCaseColumn.setKey("testCaseColumn");
        testCaseColumn.setValue((String) testCaseResult[1]);
        testCaseRow.getReportDataList().add(testCaseColumn);

        ReportEntryString testStepColumn = new ReportEntryString();
        testStepColumn.setKey("testStepColumn");
        testStepColumn.setValue("");
        testCaseRow.getReportDataList().add(testStepColumn);

        ReportEntryString resultColumn = new ReportEntryString();
        resultColumn.setKey("resultColumn");
        resultColumn.setValue((String) testCaseResult[2]);
        testCaseRow.getReportDataList().add(resultColumn);

        // Create TestStepRows

        List<Object[]> testStepResultList = databaseAccessor.getTestStepResultList((BigInteger) testCaseResult[0]);
        for (Object[] testStepResult : testStepResultList) {
            createDetailTableTesStepRows(testStepResult, testSheet);
        }
    }

    private void createDetailTableTesStepRows(Object[] testStepResult, ReportDataList rowsOfOneTestSheet) {

        // Create Step Row
        ReportDataList testStepRow = new ReportDataList();
        testStepRow.setKey("testStepRow");
        rowsOfOneTestSheet.getReportDataList().add(testStepRow);

        ReportEntryString testSheetColumn = new ReportEntryString();
        testSheetColumn.setKey("testSheetColumn");
        testSheetColumn.setValue("");
        testStepRow.getReportDataList().add(testSheetColumn);

        ReportEntryString testCaseColumn = new ReportEntryString();
        testCaseColumn.setKey("testCaseColumn");
        testCaseColumn.setValue("");
        testStepRow.getReportDataList().add(testCaseColumn);

        ReportEntryString testStepColumn = new ReportEntryString();
        testStepColumn.setKey("testStepColumn");
        testStepColumn.setValue((String) testStepResult[1]);
        testStepRow.getReportDataList().add(testStepColumn);

        ReportEntryString resultColumn = new ReportEntryString();
        resultColumn.setKey("resultColumn");
        resultColumn.setValue((String) testStepResult[2]);
        testStepRow.getReportDataList().add(resultColumn);

    }

}
