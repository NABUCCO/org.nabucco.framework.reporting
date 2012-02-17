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
package org.nabucco.framework.reporting.impl.service.search;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.reporting.facade.message.ReportConfigurationListMsg;
import org.nabucco.framework.reporting.facade.message.search.ReportConfigurationSearchRq;
import org.nabucco.framework.reporting.facade.service.search.SearchReporting;

/**
 * SearchReportingImpl<p/>Reporting Search Service<p/>
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-09
 */
public class SearchReportingImpl extends ServiceSupport implements SearchReporting {

    private static final long serialVersionUID = 1L;

    private static final String ID = "SearchReporting";

    private static Map<String, String[]> ASPECTS;

    private SearchReportConfigurationServiceHandler searchReportConfigurationServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new SearchReportingImpl instance. */
    public SearchReportingImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.searchReportConfigurationServiceHandler = injector.inject(SearchReportConfigurationServiceHandler.getId());
        if ((this.searchReportConfigurationServiceHandler != null)) {
            this.searchReportConfigurationServiceHandler.setPersistenceManager(persistenceManager);
            this.searchReportConfigurationServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("searchReportConfiguration", NO_ASPECTS);
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<ReportConfigurationListMsg> searchReportConfiguration(
            ServiceRequest<ReportConfigurationSearchRq> rq) throws SearchException {
        if ((this.searchReportConfigurationServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for searchReportConfiguration().");
            throw new InjectionException("No service implementation configured for searchReportConfiguration().");
        }
        ServiceResponse<ReportConfigurationListMsg> rs;
        this.searchReportConfigurationServiceHandler.init();
        rs = this.searchReportConfigurationServiceHandler.invoke(rq);
        this.searchReportConfigurationServiceHandler.finish();
        return rs;
    }
}
