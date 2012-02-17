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
import org.nabucco.framework.reporting.facade.component.ReportingComponent;
import org.nabucco.framework.reporting.facade.service.maintain.MaintainReporting;
import org.nabucco.framework.reporting.facade.service.reporting.GenerateReport;
import org.nabucco.framework.reporting.facade.service.resolve.ResolveReporting;
import org.nabucco.framework.reporting.facade.service.search.SearchReporting;

/**
 * ReportingComponentLocalProxy<p/>Reporting component<p/>
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-09
 */
public class ReportingComponentLocalProxy implements ReportingComponent {

    private static final long serialVersionUID = 1L;

    private final ReportingComponentLocal delegate;

    /**
     * Constructs a new ReportingComponentLocalProxy instance.
     *
     * @param delegate the ReportingComponentLocal.
     */
    public ReportingComponentLocalProxy(ReportingComponentLocal delegate) {
        super();
        if ((delegate == null)) {
            throw new IllegalArgumentException("Cannot create local proxy for component [null].");
        }
        this.delegate = delegate;
    }

    @Override
    public String getId() {
        return this.delegate.getId();
    }

    @Override
    public String getName() {
        return this.delegate.getName();
    }

    @Override
    public String getJndiName() {
        return this.delegate.getJndiName();
    }

    @Override
    public ComponentRelationService getComponentRelationService() throws ServiceException {
        return this.delegate.getComponentRelationServiceLocal();
    }

    @Override
    public QueryFilterService getQueryFilterService() throws ServiceException {
        return this.delegate.getQueryFilterServiceLocal();
    }

    @Override
    public String toString() {
        return this.delegate.toString();
    }

    @Override
    public MaintainReporting getMaintainReporting() throws ServiceException {
        return this.delegate.getMaintainReportingLocal();
    }

    @Override
    public ResolveReporting getResolveReporting() throws ServiceException {
        return this.delegate.getResolveReportingLocal();
    }

    @Override
    public SearchReporting getSearchReporting() throws ServiceException {
        return this.delegate.getSearchReportingLocal();
    }

    @Override
    public GenerateReport getGenerateReport() throws ServiceException {
        return this.delegate.getGenerateReportLocal();
    }
}
