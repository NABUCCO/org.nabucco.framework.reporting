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

import org.nabucco.framework.base.facade.component.Component;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.reporting.facade.service.maintain.MaintainReporting;
import org.nabucco.framework.reporting.facade.service.reporting.GenerateReport;
import org.nabucco.framework.reporting.facade.service.resolve.ResolveReporting;
import org.nabucco.framework.reporting.facade.service.search.SearchReporting;

/**
 * ReportingComponent<p/>Reporting component<p/>
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-09
 */
public interface ReportingComponent extends Component {

    final String COMPONENT_NAME = "org.nabucco.framework.reporting";

    final String COMPONENT_PREFIX = "repo";

    final String JNDI_NAME = ((((JNDI_PREFIX + "/") + COMPONENT_NAME) + "/") + "org.nabucco.framework.reporting.facade.component.ReportingComponent");

    /**
     * Getter for the MaintainReporting.
     *
     * @return the MaintainReporting.
     * @throws ServiceException
     */
    MaintainReporting getMaintainReporting() throws ServiceException;

    /**
     * Getter for the ResolveReporting.
     *
     * @return the ResolveReporting.
     * @throws ServiceException
     */
    ResolveReporting getResolveReporting() throws ServiceException;

    /**
     * Getter for the SearchReporting.
     *
     * @return the SearchReporting.
     * @throws ServiceException
     */
    SearchReporting getSearchReporting() throws ServiceException;

    /**
     * Getter for the GenerateReport.
     *
     * @return the GenerateReport.
     * @throws ServiceException
     */
    GenerateReport getGenerateReport() throws ServiceException;
}
