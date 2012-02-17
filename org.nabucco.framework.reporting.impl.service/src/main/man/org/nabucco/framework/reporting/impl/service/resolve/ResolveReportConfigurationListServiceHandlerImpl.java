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

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.reporting.facade.component.ReportingComponent;
import org.nabucco.framework.reporting.facade.component.ReportingComponentLocator;
import org.nabucco.framework.reporting.facade.datatype.ReportConfiguration;
import org.nabucco.framework.reporting.facade.message.ReportConfigurationListMsg;
import org.nabucco.framework.reporting.facade.message.ReportConfigurationMsg;
import org.nabucco.framework.reporting.facade.service.resolve.ResolveReporting;

/**
 * ResolveReportConfigurationListServiceHandlerImpl
 * 
 * @author Dominic Trumpfheller, PRODYNA AG
 */
public class ResolveReportConfigurationListServiceHandlerImpl extends ResolveReportConfigurationListServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected ReportConfigurationListMsg resolveReportConfigurationList(ReportConfigurationListMsg msg)
            throws ResolveException {

        ResolveReporting resolveService = this.locateResolveService();

        ReportConfigurationListMsg result = new ReportConfigurationListMsg();

        for (ReportConfiguration reportConfiguration : msg.getReportConfigurationList()) {
            ReportConfigurationMsg reportConfigurationMsg = new ReportConfigurationMsg();
            reportConfigurationMsg.setReportConfiguration(reportConfiguration);

            ServiceRequest<ReportConfigurationMsg> rq = new ServiceRequest<ReportConfigurationMsg>(super.getContext());
            rq.setRequestMessage(reportConfigurationMsg);

            ServiceResponse<ReportConfigurationMsg> rs = resolveService.resolveReportConfiguration(rq);

            result.getReportConfigurationList().add(rs.getResponseMessage().getReportConfiguration());
        }

        return result;
    }

    /**
     * Locate the resolve service from JNDI.
     * 
     * @return the resolve service
     * 
     * @throws ResolveException
     *             when the service cannot be located
     */
    private ResolveReporting locateResolveService() throws ResolveException {
        try {

            ReportingComponent reportingComponent = ReportingComponentLocator.getInstance().getComponent();
            ResolveReporting resolveService = reportingComponent.getResolveReporting();
            return resolveService;

        } catch (ConnectionException ce) {
            throw new ResolveException("Error locating resolve reporting service.", ce);
        } catch (ServiceException se) {
            throw new ResolveException("Error locating resolve reporting service.", se);
        } catch (Exception e) {
            throw new ResolveException("Error locating resolve reporting service.", e);
        }
    }
}
