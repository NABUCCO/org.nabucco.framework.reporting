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
package org.nabucco.framework.reporting.ui.web.communication;

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateFactorySupport;
import org.nabucco.framework.reporting.facade.component.ReportingComponent;
import org.nabucco.framework.reporting.facade.component.ReportingComponentLocator;
import org.nabucco.framework.reporting.ui.web.communication.maintain.MaintainReportingDelegate;
import org.nabucco.framework.reporting.ui.web.communication.reporting.GenerateReportDelegate;
import org.nabucco.framework.reporting.ui.web.communication.resolve.ResolveReportingDelegate;
import org.nabucco.framework.reporting.ui.web.communication.search.SearchReportingDelegate;

/**
 * ServiceDelegateFactoryTemplate<p/>Reporting component<p/>
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-09
 */
public class ReportingComponentServiceDelegateFactory extends ServiceDelegateFactorySupport<ReportingComponent> {

    private static ReportingComponentServiceDelegateFactory instance = new ReportingComponentServiceDelegateFactory();

    private MaintainReportingDelegate maintainReportingDelegate;

    private ResolveReportingDelegate resolveReportingDelegate;

    private SearchReportingDelegate searchReportingDelegate;

    private GenerateReportDelegate generateReportDelegate;

    /** Constructs a new ReportingComponentServiceDelegateFactory instance. */
    private ReportingComponentServiceDelegateFactory() {
        super(ReportingComponentLocator.getInstance());
    }

    /**
     * Getter for the MaintainReporting.
     *
     * @return the MaintainReportingDelegate.
     * @throws ClientException
     */
    public MaintainReportingDelegate getMaintainReporting() throws ClientException {
        try {
            if ((this.maintainReportingDelegate == null)) {
                this.maintainReportingDelegate = new MaintainReportingDelegate(this.getComponent()
                        .getMaintainReporting());
            }
            return this.maintainReportingDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: MaintainReporting", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the ResolveReporting.
     *
     * @return the ResolveReportingDelegate.
     * @throws ClientException
     */
    public ResolveReportingDelegate getResolveReporting() throws ClientException {
        try {
            if ((this.resolveReportingDelegate == null)) {
                this.resolveReportingDelegate = new ResolveReportingDelegate(this.getComponent().getResolveReporting());
            }
            return this.resolveReportingDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: ResolveReporting", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the SearchReporting.
     *
     * @return the SearchReportingDelegate.
     * @throws ClientException
     */
    public SearchReportingDelegate getSearchReporting() throws ClientException {
        try {
            if ((this.searchReportingDelegate == null)) {
                this.searchReportingDelegate = new SearchReportingDelegate(this.getComponent().getSearchReporting());
            }
            return this.searchReportingDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: SearchReporting", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the GenerateReport.
     *
     * @return the GenerateReportDelegate.
     * @throws ClientException
     */
    public GenerateReportDelegate getGenerateReport() throws ClientException {
        try {
            if ((this.generateReportDelegate == null)) {
                this.generateReportDelegate = new GenerateReportDelegate(this.getComponent().getGenerateReport());
            }
            return this.generateReportDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: GenerateReport", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the Instance.
     *
     * @return the ReportingComponentServiceDelegateFactory.
     */
    public static ReportingComponentServiceDelegateFactory getInstance() {
        return instance;
    }
}
