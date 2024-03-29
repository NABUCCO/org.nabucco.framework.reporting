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
package org.nabucco.framework.reporting.impl.service.reporting;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.ReportingException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.reporting.facade.message.GenerateReportRq;
import org.nabucco.framework.reporting.facade.message.GenerateReportRs;
import org.nabucco.framework.reporting.facade.service.reporting.GenerateReport;

/**
 * GenerateReportImpl<p/>Report generation service<p/>
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-09
 */
public class GenerateReportImpl extends ServiceSupport implements GenerateReport {

    private static final long serialVersionUID = 1L;

    private static final String ID = "GenerateReport";

    private static Map<String, String[]> ASPECTS;

    private GenerateReportServiceHandler generateReportServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new GenerateReportImpl instance. */
    public GenerateReportImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.generateReportServiceHandler = injector.inject(GenerateReportServiceHandler.getId());
        if ((this.generateReportServiceHandler != null)) {
            this.generateReportServiceHandler.setPersistenceManager(persistenceManager);
            this.generateReportServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("generateReport", NO_ASPECTS);
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<GenerateReportRs> generateReport(ServiceRequest<GenerateReportRq> rq)
            throws ReportingException {
        if ((this.generateReportServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for generateReport().");
            throw new InjectionException("No service implementation configured for generateReport().");
        }
        ServiceResponse<GenerateReportRs> rs;
        this.generateReportServiceHandler.init();
        rs = this.generateReportServiceHandler.invoke(rq);
        this.generateReportServiceHandler.finish();
        return rs;
    }
}
