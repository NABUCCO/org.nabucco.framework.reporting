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
package org.nabucco.framework.reporting.impl.service.maintain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.reporting.facade.message.ReportConfigurationListMsg;
import org.nabucco.framework.reporting.facade.message.ReportConfigurationMsg;
import org.nabucco.framework.reporting.facade.service.maintain.MaintainReporting;

/**
 * MaintainReportingImpl<p/>Reporting maintenance service<p/>
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-09
 */
public class MaintainReportingImpl extends ServiceSupport implements MaintainReporting {

    private static final long serialVersionUID = 1L;

    private static final String ID = "MaintainReporting";

    private static Map<String, String[]> ASPECTS;

    private MaintainReportConfigurationServiceHandler maintainReportConfigurationServiceHandler;

    private MaintainReportConfigurationListServiceHandler maintainReportConfigurationListServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new MaintainReportingImpl instance. */
    public MaintainReportingImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.maintainReportConfigurationServiceHandler = injector.inject(MaintainReportConfigurationServiceHandler
                .getId());
        if ((this.maintainReportConfigurationServiceHandler != null)) {
            this.maintainReportConfigurationServiceHandler.setPersistenceManager(persistenceManager);
            this.maintainReportConfigurationServiceHandler.setLogger(super.getLogger());
        }
        this.maintainReportConfigurationListServiceHandler = injector
                .inject(MaintainReportConfigurationListServiceHandler.getId());
        if ((this.maintainReportConfigurationListServiceHandler != null)) {
            this.maintainReportConfigurationListServiceHandler.setPersistenceManager(persistenceManager);
            this.maintainReportConfigurationListServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("maintainReportConfiguration", NO_ASPECTS);
            ASPECTS.put("maintainReportConfigurationList", NO_ASPECTS);
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<ReportConfigurationMsg> maintainReportConfiguration(ServiceRequest<ReportConfigurationMsg> rq)
            throws MaintainException {
        if ((this.maintainReportConfigurationServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for maintainReportConfiguration().");
            throw new InjectionException("No service implementation configured for maintainReportConfiguration().");
        }
        ServiceResponse<ReportConfigurationMsg> rs;
        this.maintainReportConfigurationServiceHandler.init();
        rs = this.maintainReportConfigurationServiceHandler.invoke(rq);
        this.maintainReportConfigurationServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<ReportConfigurationListMsg> maintainReportConfigurationList(
            ServiceRequest<ReportConfigurationListMsg> rq) throws MaintainException {
        if ((this.maintainReportConfigurationListServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for maintainReportConfigurationList().");
            throw new InjectionException("No service implementation configured for maintainReportConfigurationList().");
        }
        ServiceResponse<ReportConfigurationListMsg> rs;
        this.maintainReportConfigurationListServiceHandler.init();
        rs = this.maintainReportConfigurationListServiceHandler.invoke(rq);
        this.maintainReportConfigurationListServiceHandler.finish();
        return rs;
    }
}
