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
import java.sql.ResultSet;
import java.util.List;

import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.impl.service.maintain.NabuccoQuery;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;

/**
 * SimpleTestResultReportDatabaseAccessor
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class SimpleTestResultReportDatabaseAccessor {

    private static final String QUERY_LATEST_TEST_CONFIG_RESULTS = "SELECT id, name, test_configuration_name, start_time, end_time, status "
            + "FROM nabucco.rslt_test_configuration_result GROUP BY(test_configuration_id) HAVING MAX(end_time)";

    private static final String QUERY_COUNT_FIRST_LEVEL_ELEMENTS = "SELECT COUNT(*) FROM ((nabucco.rslt_test_result test_result "
            + "INNER JOIN nabucco.scma_schema_element schema_element ON (test_result.schema_element_id = schema_element.id)) "
            + "INNER JOIN nabucco.rslt_test_result_container test_result_container ON (test_result_container.result_id = test_result.id)) "
            + "WHERE (test_result_container.test_configuration_result_test_result_list_id = ?) "
            + "AND (schema_element.name = ?)";

    private static final String QUERY_COUNT_FIRST_LEVEL_ELEMENTS_BY_STATUS = QUERY_COUNT_FIRST_LEVEL_ELEMENTS
            + " AND (test_result.status = ?)";

    private static final String QUERY_COUNT_SECOND_LEVEL_ELEMENTS = "SELECT COUNT(*) FROM nabucco.rslt_test_result test_result "
            + "INNER JOIN nabucco.rslt_test_result_container test_result_container ON (test_result_container.result_id = test_result.id) "
            + "INNER JOIN nabucco.scma_schema_element schema_element ON(schema_element.id = test_result.schema_element_id) "
            + "WHERE test_result_container.test_result_test_result_list_id IN (SELECT test_result.id FROM ((nabucco.rslt_test_result test_result "
            + "INNER JOIN nabucco.scma_schema_element schema_element ON (test_result.schema_element_id = schema_element.id)) "
            + "INNER JOIN nabucco.rslt_test_result_container test_result_container ON (test_result_container.result_id = test_result.id)) "
            + "WHERE (test_result_container.test_configuration_result_test_result_list_id = ?) "
            + "AND (schema_element.name = ?)) " + "AND (schema_element.name = ?)";

    private static final String QUERY_COUNT_SECOND_LEVEL_ELEMENTS_BY_STATUS = QUERY_COUNT_SECOND_LEVEL_ELEMENTS
            + " AND (test_result.status = ?)";

    private static final String QUERY_COUNT_THIRD_LEVEL_ELEMENTS = "SELECT COUNT(*) FROM nabucco.rslt_test_result test_result "
            + "INNER JOIN nabucco.rslt_test_result_container test_result_container ON (test_result_container.result_id = test_result.id) "
            + "INNER JOIN nabucco.scma_schema_element schema_element ON(schema_element.id = test_result.schema_element_id) "
            + "WHERE test_result_container.test_result_test_result_list_id IN (SELECT test_result.id FROM nabucco.rslt_test_result test_result "
            + "INNER JOIN nabucco.rslt_test_result_container test_result_container ON (test_result_container.result_id = test_result.id) "
            + "INNER JOIN nabucco.scma_schema_element schema_element ON(schema_element.id = test_result.schema_element_id) "
            + "WHERE test_result_container.test_result_test_result_list_id IN (SELECT test_result.id FROM ((nabucco.rslt_test_result test_result "
            + "INNER JOIN nabucco.scma_schema_element schema_element ON (test_result.schema_element_id = schema_element.id)) "
            + "INNER JOIN nabucco.rslt_test_result_container test_result_container ON (test_result_container.result_id = test_result.id)) "
            + "WHERE (test_result_container.test_configuration_result_test_result_list_id = ?) "
            + "AND (schema_element.name = ?)) " + "AND (schema_element.name = ?)) " + "AND (schema_element.name = ?)";

    private static final String QUERY_COUNT_THIRD_LEVEL_ELEMENTS_BY_STATUS = QUERY_COUNT_THIRD_LEVEL_ELEMENTS
            + " AND (test_result.status = ?)";

    private static final String QUERY_SELECT_FIRST_LEVEL_ELEMENTS = "SELECT test_result.id, test_result.name, test_result.status "
            + "FROM ((nabucco.rslt_test_result test_result INNER JOIN nabucco.scma_schema_element schema_element ON (test_result.schema_element_id = schema_element.id)) "
            + "INNER JOIN nabucco.rslt_test_result_container test_result_container ON (test_result_container.result_id = test_result.id)) "
            + "WHERE (test_result_container.test_configuration_result_test_result_list_id = ?)"
            + " AND (schema_element.name = ?)";

    private static final String QUERY_SELECT_ANY_LEVEL_ELEMENTS = "SELECT test_result.id, test_result.name, test_result.status "
            + "FROM nabucco.rslt_test_result test_result INNER JOIN nabucco.rslt_test_result_container test_result_container ON (test_result_container.result_id = test_result.id) "
            + "INNER JOIN nabucco.scma_schema_element schema_element ON(schema_element.id = test_result.schema_element_id) "
            + "WHERE (schema_element.name = ?) " + "AND (test_result_container.test_result_test_result_list_id = ?)";

    private PersistenceManager persistenceManager;

    /**
     * Creates a new {@link SimpleTestResultReportDatabaseAccessor} instance.
     * 
     * @param persistenceManager
     *            the persistence manager
     */
    public SimpleTestResultReportDatabaseAccessor(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }

    /**
     * List<Object[]> = [name, configurationName, startTime, Endtime, Status]
     * 
     * @return all {@link TestConfigurationResult} that should be displayed in the report.
     */
    public List<Object[]> getTestConfigurationResults() {
        try {
            NabuccoQuery<Object[]> query = this.persistenceManager.createNativeQuery(QUERY_LATEST_TEST_CONFIG_RESULTS);
            return query.getResultList();
        } catch (PersistenceException pe) {
            // TODO: Exception!
            return null;
        }
    }

    /**
     * Returns the number of TestSheets that were executed in the {@link TestConfigurationResult}
     * with the given id.
     * 
     * @param testConfigurationResultId
     *            the corresponding {@link TestConfigurationResult} id.
     * @return the number of TestSheets that were executed in the {@link TestConfigurationResult}
     *         with the given id.
     */
    public int getNumberOfTestSheetsExecuted(BigInteger testConfigurationResultId) {
        try {
            NabuccoQuery<?> query = this.persistenceManager.createNativeQuery(QUERY_COUNT_FIRST_LEVEL_ELEMENTS);
            query.setParameter(1, testConfigurationResultId);
            query.setParameter(2, "Test Sheet");
            return ((BigInteger) query.getSingleResult()).intValue();
        } catch (PersistenceException pe) {
            // TODO: Exception!
            return -1;
        }
    }

    /**
     * Returns the number of TestSheets that were executed successfully in the
     * {@link TestConfigurationResult} with the given id.
     * 
     * @param testConfigurationResultId
     *            the corresponding {@link TestConfigurationResult} id.
     * @return the number of TestSheets that were executed successfully in the
     *         {@link TestConfigurationResult} with the given id.
     */
    public int getNumberOfTestSheetsExecutedSuccessfully(BigInteger testConfigurationResultId) {
        try {
            NabuccoQuery<?> query = this.persistenceManager
                    .createNativeQuery(QUERY_COUNT_FIRST_LEVEL_ELEMENTS_BY_STATUS);
            query.setParameter(1, testConfigurationResultId);
            query.setParameter(2, "Test Sheet");
            query.setParameter(3, "PASSED");
            return ((BigInteger) query.getSingleResult()).intValue();
        } catch (PersistenceException pe) {
            // TODO: Exception!
            return -1;
        }
    }

    /**
     * Returns the number of TestSheets that failed in the {@link TestConfigurationResult} with the
     * given id.
     * 
     * @param testConfigurationResultId
     *            the corresponding {@link TestConfigurationResult} id.
     * @return the number of TestSheets that failed in the {@link TestConfigurationResult} with the
     *         given id.
     */
    public int getNumberOfFailedTestSheets(BigInteger testConfigurationResultId) {
        try {
            NabuccoQuery<?> query = this.persistenceManager
                    .createNativeQuery(QUERY_COUNT_FIRST_LEVEL_ELEMENTS_BY_STATUS);
            query.setParameter(1, testConfigurationResultId);
            query.setParameter(2, "Test Sheet");
            query.setParameter(3, "FAILED");
            return ((BigInteger) query.getSingleResult()).intValue();
        } catch (PersistenceException pe) {
            // TODO: Exception!
            return -1;
        }
    }

    /**
     * Returns the number of TestSheets that were skipped in the {@link TestConfigurationResult}
     * with the given id.
     * 
     * @param testConfigurationResultId
     *            the corresponding {@link TestConfigurationResult} id.
     * @return the number of TestSheets that were skipped in the {@link TestConfigurationResult}
     *         with the given id.
     */
    public int getNumberOfSkippedTestSheets(BigInteger testConfigurationResultId) {
        try {
            NabuccoQuery<?> query = this.persistenceManager
                    .createNativeQuery(QUERY_COUNT_FIRST_LEVEL_ELEMENTS_BY_STATUS);
            query.setParameter(1, testConfigurationResultId);
            query.setParameter(2, "Test Sheet");
            query.setParameter(3, "SKIPPED");
            return ((BigInteger) query.getSingleResult()).intValue();
        } catch (PersistenceException pe) {
            // TODO: Exception!
            return -1;
        }
    }

    /**
     * Returns the number of TestCases that were executed in the {@link TestConfigurationResult}
     * with the given id.
     * 
     * @param testConfigurationResultId
     *            the corresponding {@link TestConfigurationResult} id.
     * @return the number of TestCases that were executed in the {@link TestConfigurationResult}
     *         with the given id.
     */
    public int getNumberOfTestCasesExecuted(BigInteger testConfigurationResultId) {
        try {
            NabuccoQuery<?> query = this.persistenceManager.createNativeQuery(QUERY_COUNT_SECOND_LEVEL_ELEMENTS);
            query.setParameter(1, testConfigurationResultId);
            query.setParameter(2, "Test Sheet");
            query.setParameter(3, "Test Case");
            return ((BigInteger) query.getSingleResult()).intValue();
        } catch (PersistenceException pe) {
            // TODO: Exception!
            return -1;
        }
    }

    /**
     * Returns the number of TestCases that were executed successfully in the
     * {@link TestConfigurationResult} with the given id.
     * 
     * @param testConfigurationResultId
     *            the corresponding {@link TestConfigurationResult} id.
     * @return the number of TestCases that were executed successfully in the
     *         {@link TestConfigurationResult} with the given id.
     */
    public int getNumberOfTestCasesExecutedSuccessfully(BigInteger testConfigurationResultId) {
        try {
            NabuccoQuery<?> query = this.persistenceManager
                    .createNativeQuery(QUERY_COUNT_SECOND_LEVEL_ELEMENTS_BY_STATUS);
            query.setParameter(1, testConfigurationResultId);
            query.setParameter(2, "Test Sheet");
            query.setParameter(3, "Test Case");
            query.setParameter(4, "PASSED");
            return ((BigInteger) query.getSingleResult()).intValue();
        } catch (PersistenceException pe) {
            // TODO: Exception!
            return -1;
        }
    }

    /**
     * Returns the number of TestCases that failed in the {@link TestConfigurationResult} with the
     * given id.
     * 
     * @param testConfigurationResultId
     *            the corresponding {@link TestConfigurationResult} id.
     * @return the number of TestCases that failed in the {@link TestConfigurationResult} with the
     *         given id.
     */
    public int getNumberOfFailedTestCases(BigInteger testConfigurationResultId) {
        try {
            NabuccoQuery<?> query = this.persistenceManager
                    .createNativeQuery(QUERY_COUNT_SECOND_LEVEL_ELEMENTS_BY_STATUS);
            query.setParameter(1, testConfigurationResultId);
            query.setParameter(2, "Test Sheet");
            query.setParameter(3, "Test Case");
            query.setParameter(4, "FAILED");
            return ((BigInteger) query.getSingleResult()).intValue();
        } catch (PersistenceException pe) {
            // TODO: Exception!
            return -1;
        }
    }

    /**
     * Returns the number of TestCases that were skipped in the {@link TestConfigurationResult} with
     * the given id.
     * 
     * @param testConfigurationResultId
     *            the corresponding {@link TestConfigurationResult} id.
     * @return the number of TestCases that were skipped in the {@link TestConfigurationResult} with
     *         the given id.
     */
    public int getNumberOfSkippedTestCases(BigInteger testConfigurationResultId) {
        try {
            NabuccoQuery<?> query = this.persistenceManager
                    .createNativeQuery(QUERY_COUNT_SECOND_LEVEL_ELEMENTS_BY_STATUS);
            query.setParameter(1, testConfigurationResultId);
            query.setParameter(2, "Test Sheet");
            query.setParameter(3, "Test Case");
            query.setParameter(4, "SKIPPED");
            return ((BigInteger) query.getSingleResult()).intValue();
        } catch (PersistenceException pe) {
            // TODO: Exception!
            return -1;
        }
    }

    /**
     * Returns the number of TestSteps that were executed in the {@link TestConfigurationResult}
     * with the given id.
     * 
     * @param testConfigurationResultId
     *            the corresponding {@link TestConfigurationResult} id.
     * @return the number of TestSteps that were executed in the {@link TestConfigurationResult}
     *         with the given id.
     */
    public int getNumberOfTestStepsExecuted(BigInteger testConfigurationResultId) {
        try {
            NabuccoQuery<?> query = this.persistenceManager.createNativeQuery(QUERY_COUNT_THIRD_LEVEL_ELEMENTS);
            query.setParameter(1, testConfigurationResultId);
            query.setParameter(2, "Test Sheet");
            query.setParameter(3, "Test Case");
            query.setParameter(4, "TestStep");
            return ((BigInteger) query.getSingleResult()).intValue();
        } catch (PersistenceException pe) {
            // TODO: Exception!
            return -1;
        }
    }

    /**
     * Returns the number of TestSteps that were executed successfully in the
     * {@link TestConfigurationResult} with the given id.
     * 
     * @param testConfigurationResultId
     *            the corresponding {@link TestConfigurationResult} id.
     * @return the number of TestSteps that were executed successfully in the
     *         {@link TestConfigurationResult} with the given id.
     */
    public int getNumberOfTestStepsExecutedSuccessfully(BigInteger testConfigurationResultId) {
        try {
            NabuccoQuery<?> query = this.persistenceManager
                    .createNativeQuery(QUERY_COUNT_THIRD_LEVEL_ELEMENTS_BY_STATUS);
            query.setParameter(1, testConfigurationResultId);
            query.setParameter(2, "Test Sheet");
            query.setParameter(3, "Test Case");
            query.setParameter(4, "Test Step");
            query.setParameter(5, "PASSED");
            return ((BigInteger) query.getSingleResult()).intValue();
        } catch (PersistenceException pe) {
            // TODO: Exception!
            return -1;
        }
    }

    /**
     * Returns the number of TestSteps that failed in the {@link TestConfigurationResult} with the
     * given id.
     * 
     * @param testConfigurationResultId
     *            the corresponding {@link TestConfigurationResult} id.
     * @return the number of TestSteps that failed in the {@link TestConfigurationResult} with the
     *         given id.
     */
    public int getNumberOfFailedTestSteps(BigInteger testConfigurationResultId) {
        try {
            NabuccoQuery<?> query = this.persistenceManager
                    .createNativeQuery(QUERY_COUNT_THIRD_LEVEL_ELEMENTS_BY_STATUS);
            query.setParameter(1, testConfigurationResultId);
            query.setParameter(2, "Test Sheet");
            query.setParameter(3, "Test Case");
            query.setParameter(4, "Test Step");
            query.setParameter(5, "FAILED");
            return ((BigInteger) query.getSingleResult()).intValue();
        } catch (PersistenceException pe) {
            // TODO: Exception!
            return -1;
        }
    }

    /**
     * Returns the number of TestSteps that were skipped in the {@link TestConfigurationResult} with
     * the given id.
     * 
     * @param testConfigurationResultId
     *            the corresponding {@link TestConfigurationResult} id.
     * @return the number of TestSteps that were skipped in the {@link TestConfigurationResult} with
     *         the given id.
     */
    public int getNumberOfSkippedTestSteps(BigInteger testConfigurationResultId) {
        try {
            NabuccoQuery<?> query = this.persistenceManager
                    .createNativeQuery(QUERY_COUNT_THIRD_LEVEL_ELEMENTS_BY_STATUS);
            query.setParameter(1, testConfigurationResultId);
            query.setParameter(2, "Test Sheet");
            query.setParameter(3, "Test Case");
            query.setParameter(4, "Test Step");
            query.setParameter(5, "SKIPPED");
            return ((BigInteger) query.getSingleResult()).intValue();
        } catch (PersistenceException pe) {
            // TODO: Exception!
            return -1;
        }
    }

    /**
     * Return a list of TestResults {@link ResultSet} that correspond to TestSheets and whose parent
     * is the {@link TestConfigurationResult} with the given id.
     * 
     * Object[] = [id, name, status]
     * 
     * @param testConfigurationResultId
     *            the parent {@link TestConfigurationResult} id.
     * @return the list of TestResults {@link ResultSet} that correspond to TestSheets and whose
     *         parent is the {@link TestConfigurationResult} with the given id.
     */

    public List<Object[]> getTestSheetResultList(BigInteger testConfigurationResultId) {
        try {
            NabuccoQuery<Object[]> query = this.persistenceManager.createNativeQuery(QUERY_SELECT_FIRST_LEVEL_ELEMENTS);
            query.setParameter(1, testConfigurationResultId);
            query.setParameter(2, "Test Sheet");
            return query.getResultList();
        } catch (PersistenceException pe) {
            // TODO: Exception!
            return null;
        }
    }

    /**
     * Return a list of TestResults {@link ResultSet} that correspond to TestCases and whose parent
     * is the {@link TestConfigurationResult} with the given id.
     * 
     * Object[] = [id, name, status]
     * 
     * @param testSheetResultId
     *            the parent {@link TestConfigurationResult} id.
     * @return the list of TestResults {@link ResultSet} that correspond to TestCases and whose
     *         parent is the {@link TestResult} with the given id.
     */
    public List<Object[]> getTestCaseResultList(BigInteger testSheetResultId) {
        try {
            NabuccoQuery<Object[]> query = this.persistenceManager.createNativeQuery(QUERY_SELECT_ANY_LEVEL_ELEMENTS);
            query.setParameter(1, "Test Case");
            query.setParameter(2, testSheetResultId);
            return query.getResultList();
        } catch (PersistenceException pe) {
            // TODO: Exception!
            return null;
        }
    }

    /**
     * Return a list of TestResults {@link ResultSet} that correspond to TestSteps and whose parent
     * is the {@link TestConfigurationResult} with the given id.
     * 
     * Object[] = [id, name, status]
     * 
     * @param testCaseResultId
     *            the parent {@link TestConfigurationResult} id.
     * @return the list of TestResults {@link ResultSet} that correspond to TestSteps and whose
     *         parent is the {@link TestResult} with the given id.
     */
    public List<Object[]> getTestStepResultList(BigInteger testCaseResultId) {
        try {
            NabuccoQuery<Object[]> query = this.persistenceManager.createNativeQuery(QUERY_SELECT_ANY_LEVEL_ELEMENTS);
            query.setParameter(1, "Test Step");
            query.setParameter(2, testCaseResultId);
            return query.getResultList();
        } catch (PersistenceException pe) {
            // TODO: Exception!
            return null;
        }
    }

}
