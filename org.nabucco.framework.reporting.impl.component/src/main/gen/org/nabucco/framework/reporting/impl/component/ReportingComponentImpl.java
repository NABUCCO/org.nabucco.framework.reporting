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
package org.nabucco.framework.reporting.impl.component;

import org.nabucco.framework.base.facade.component.handler.PostConstructHandler;
import org.nabucco.framework.base.facade.component.handler.PreDestroyHandler;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.service.componentrelation.ComponentRelationService;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.facade.service.queryfilter.QueryFilterService;
import org.nabucco.framework.base.impl.component.ComponentSupport;
import org.nabucco.framework.reporting.facade.component.ReportingComponentLocal;
import org.nabucco.framework.reporting.facade.component.ReportingComponentRemote;
import org.nabucco.framework.reporting.facade.service.maintain.MaintainReporting;
import org.nabucco.framework.reporting.facade.service.reporting.GenerateReport;
import org.nabucco.framework.reporting.facade.service.resolve.ResolveReporting;
import org.nabucco.framework.reporting.facade.service.search.SearchReporting;

/**
 * ReportingComponentImpl<p/>Reporting component<p/>
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-09
 */
public class ReportingComponentImpl extends ComponentSupport implements ReportingComponentLocal,
        ReportingComponentRemote {

    private static final long serialVersionUID = 1L;

    private static final String ID = "ReportingComponent";

    /** Constructs a new ReportingComponentImpl instance. */
    public ReportingComponentImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PostConstructHandler handler = injector.inject(PostConstructHandler.getId());
        if ((handler == null)) {
            if (super.getLogger().isDebugEnabled()) {
                super.getLogger().debug("No post construct handler configured for \'", ID, "\'.");
            }
            return;
        }
        handler.setLocatable(this);
        handler.setLogger(super.getLogger());
        handler.invoke();
    }

    @Override
    public void preDestroy() {
        super.preDestroy();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PreDestroyHandler handler = injector.inject(PreDestroyHandler.getId());
        if ((handler == null)) {
            if (super.getLogger().isDebugEnabled()) {
                super.getLogger().debug("No pre destroy handler configured for \'", ID, "\'.");
            }
            return;
        }
        handler.setLocatable(this);
        handler.setLogger(super.getLogger());
        handler.invoke();
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getName() {
        return COMPONENT_NAME;
    }

    @Override
    public String getJndiName() {
        return JNDI_NAME;
    }

    @Override
    public ComponentRelationService getComponentRelationService() throws ServiceException {
        return super.lookup(ReportingComponentJndiNames.COMPONENT_RELATION_SERVICE_REMOTE,
                ComponentRelationService.class);
    }

    @Override
    public ComponentRelationService getComponentRelationServiceLocal() throws ServiceException {
        return super.lookup(ReportingComponentJndiNames.COMPONENT_RELATION_SERVICE_LOCAL,
                ComponentRelationService.class);
    }

    @Override
    public QueryFilterService getQueryFilterService() throws ServiceException {
        return super.lookup(ReportingComponentJndiNames.QUERY_FILTER_SERVICE_REMOTE, QueryFilterService.class);
    }

    @Override
    public QueryFilterService getQueryFilterServiceLocal() throws ServiceException {
        return super.lookup(ReportingComponentJndiNames.QUERY_FILTER_SERVICE_LOCAL, QueryFilterService.class);
    }

    @Override
    public MaintainReporting getMaintainReportingLocal() throws ServiceException {
        return super.lookup(ReportingComponentJndiNames.MAINTAIN_REPORTING_LOCAL, MaintainReporting.class);
    }

    @Override
    public MaintainReporting getMaintainReporting() throws ServiceException {
        return super.lookup(ReportingComponentJndiNames.MAINTAIN_REPORTING_REMOTE, MaintainReporting.class);
    }

    @Override
    public ResolveReporting getResolveReportingLocal() throws ServiceException {
        return super.lookup(ReportingComponentJndiNames.RESOLVE_REPORTING_LOCAL, ResolveReporting.class);
    }

    @Override
    public ResolveReporting getResolveReporting() throws ServiceException {
        return super.lookup(ReportingComponentJndiNames.RESOLVE_REPORTING_REMOTE, ResolveReporting.class);
    }

    @Override
    public SearchReporting getSearchReportingLocal() throws ServiceException {
        return super.lookup(ReportingComponentJndiNames.SEARCH_REPORTING_LOCAL, SearchReporting.class);
    }

    @Override
    public SearchReporting getSearchReporting() throws ServiceException {
        return super.lookup(ReportingComponentJndiNames.SEARCH_REPORTING_REMOTE, SearchReporting.class);
    }

    @Override
    public GenerateReport getGenerateReportLocal() throws ServiceException {
        return super.lookup(ReportingComponentJndiNames.GENERATE_REPORT_LOCAL, GenerateReport.class);
    }

    @Override
    public GenerateReport getGenerateReport() throws ServiceException {
        return super.lookup(ReportingComponentJndiNames.GENERATE_REPORT_REMOTE, GenerateReport.class);
    }
}
