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
package org.nabucco.framework.reporting.ui.rcp.communication.maintain;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.framework.reporting.facade.message.ReportConfigurationListMsg;
import org.nabucco.framework.reporting.facade.message.ReportConfigurationMsg;
import org.nabucco.framework.reporting.facade.service.maintain.MaintainReporting;

/**
 * MaintainReportingDelegate<p/>Reporting maintenance service<p/>
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-09
 */
public class MaintainReportingDelegate extends ServiceDelegateSupport {

    private MaintainReporting service;

    /**
     * Constructs a new MaintainReportingDelegate instance.
     *
     * @param service the MaintainReporting.
     */
    public MaintainReportingDelegate(MaintainReporting service) {
        super();
        this.service = service;
    }

    /**
     * MaintainReportConfiguration.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the ReportConfigurationMsg.
     * @return the ReportConfigurationMsg.
     * @throws ClientException
     */
    public ReportConfigurationMsg maintainReportConfiguration(ReportConfigurationMsg message,
            ServiceSubContext... subContexts) throws ClientException {
        ServiceRequest<ReportConfigurationMsg> request = new ServiceRequest<ReportConfigurationMsg>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<ReportConfigurationMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.maintainReportConfiguration(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(MaintainReporting.class, "maintainReportConfiguration", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: MaintainReporting.maintainReportConfiguration");
    }

    /**
     * MaintainReportConfigurationList.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the ReportConfigurationListMsg.
     * @return the ReportConfigurationListMsg.
     * @throws ClientException
     */
    public ReportConfigurationListMsg maintainReportConfigurationList(ReportConfigurationListMsg message,
            ServiceSubContext... subContexts) throws ClientException {
        ServiceRequest<ReportConfigurationListMsg> request = new ServiceRequest<ReportConfigurationListMsg>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<ReportConfigurationListMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.maintainReportConfigurationList(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(MaintainReporting.class, "maintainReportConfigurationList", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: MaintainReporting.maintainReportConfigurationList");
    }
}
