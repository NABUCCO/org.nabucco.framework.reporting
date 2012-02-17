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
package org.nabucco.framework.reporting.impl.service.resolve;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.reporting.facade.message.ReportConfigurationListMsg;
import org.nabucco.framework.reporting.facade.message.ReportConfigurationMsg;
import org.nabucco.framework.reporting.facade.service.resolve.ResolveReporting;

/**
 * ResolveReportingImpl<p/>Reporting resolution service<p/>
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-09
 */
public class ResolveReportingImpl extends ServiceSupport implements ResolveReporting {

    private static final long serialVersionUID = 1L;

    private static final String ID = "ResolveReporting";

    private static Map<String, String[]> ASPECTS;

    private ResolveReportConfigurationServiceHandler resolveReportConfigurationServiceHandler;

    private ResolveReportConfigurationListServiceHandler resolveReportConfigurationListServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new ResolveReportingImpl instance. */
    public ResolveReportingImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.resolveReportConfigurationServiceHandler = injector.inject(ResolveReportConfigurationServiceHandler
                .getId());
        if ((this.resolveReportConfigurationServiceHandler != null)) {
            this.resolveReportConfigurationServiceHandler.setPersistenceManager(persistenceManager);
            this.resolveReportConfigurationServiceHandler.setLogger(super.getLogger());
        }
        this.resolveReportConfigurationListServiceHandler = injector
                .inject(ResolveReportConfigurationListServiceHandler.getId());
        if ((this.resolveReportConfigurationListServiceHandler != null)) {
            this.resolveReportConfigurationListServiceHandler.setPersistenceManager(persistenceManager);
            this.resolveReportConfigurationListServiceHandler.setLogger(super.getLogger());
        }
    }

    @Override
    public void preDestroy() {
        super.preDestroy();
    }

    @Override
    public String[] getAspects(String operationName) {
        if ((ASPECTS == null)) {
            ASPECTS = new HashMap<String, String[]>();
            ASPECTS.put("resolveReportConfiguration", NO_ASPECTS);
            ASPECTS.put("resolveReportConfigurationList", NO_ASPECTS);
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<ReportConfigurationMsg> resolveReportConfiguration(ServiceRequest<ReportConfigurationMsg> rq)
            throws ResolveException {
        if ((this.resolveReportConfigurationServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for resolveReportConfiguration().");
            throw new InjectionException("No service implementation configured for resolveReportConfiguration().");
        }
        ServiceResponse<ReportConfigurationMsg> rs;
        this.resolveReportConfigurationServiceHandler.init();
        rs = this.resolveReportConfigurationServiceHandler.invoke(rq);
        this.resolveReportConfigurationServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<ReportConfigurationListMsg> resolveReportConfigurationList(
            ServiceRequest<ReportConfigurationListMsg> rq) throws ResolveException {
        if ((this.resolveReportConfigurationListServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for resolveReportConfigurationList().");
            throw new InjectionException("No service implementation configured for resolveReportConfigurationList().");
        }
        ServiceResponse<ReportConfigurationListMsg> rs;
        this.resolveReportConfigurationListServiceHandler.init();
        rs = this.resolveReportConfigurationListServiceHandler.invoke(rq);
        this.resolveReportConfigurationListServiceHandler.finish();
        return rs;
    }
}
