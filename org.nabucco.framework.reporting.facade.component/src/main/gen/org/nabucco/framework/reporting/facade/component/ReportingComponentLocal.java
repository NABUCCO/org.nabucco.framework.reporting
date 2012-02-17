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
package org.nabucco.framework.reporting.facade.component;

import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.service.componentrelation.ComponentRelationService;
import org.nabucco.framework.base.facade.service.queryfilter.QueryFilterService;
import org.nabucco.framework.reporting.facade.service.maintain.MaintainReporting;
import org.nabucco.framework.reporting.facade.service.reporting.GenerateReport;
import org.nabucco.framework.reporting.facade.service.resolve.ResolveReporting;
import org.nabucco.framework.reporting.facade.service.search.SearchReporting;

/**
 * ReportingComponentLocal<p/>Reporting component<p/>
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-09
 */
public interface ReportingComponentLocal extends ReportingComponent {

    /**
     * Getter for the ComponentRelationServiceLocal.
     *
     * @return the ComponentRelationService.
     * @throws ServiceException
     */
    ComponentRelationService getComponentRelationServiceLocal() throws ServiceException;

    /**
     * Getter for the QueryFilterServiceLocal.
     *
     * @return the QueryFilterService.
     * @throws ServiceException
     */
    QueryFilterService getQueryFilterServiceLocal() throws ServiceException;

    /**
     * Getter for the MaintainReportingLocal.
     *
     * @return the MaintainReporting.
     * @throws ServiceException
     */
    MaintainReporting getMaintainReportingLocal() throws ServiceException;

    /**
     * Getter for the ResolveReportingLocal.
     *
     * @return the ResolveReporting.
     * @throws ServiceException
     */
    ResolveReporting getResolveReportingLocal() throws ServiceException;

    /**
     * Getter for the SearchReportingLocal.
     *
     * @return the SearchReporting.
     * @throws ServiceException
     */
    SearchReporting getSearchReportingLocal() throws ServiceException;

    /**
     * Getter for the GenerateReportLocal.
     *
     * @return the GenerateReport.
     * @throws ServiceException
     */
    GenerateReport getGenerateReportLocal() throws ServiceException;
}
