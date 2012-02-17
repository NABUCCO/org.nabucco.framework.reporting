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
package org.nabucco.framework.reporting.ui.rcp.communication.reporting;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.framework.reporting.facade.message.GenerateReportRq;
import org.nabucco.framework.reporting.facade.message.GenerateReportRs;
import org.nabucco.framework.reporting.facade.service.reporting.GenerateReport;

/**
 * GenerateReportDelegate<p/>Report generation service<p/>
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-09
 */
public class GenerateReportDelegate extends ServiceDelegateSupport {

    private GenerateReport service;

    /**
     * Constructs a new GenerateReportDelegate instance.
     *
     * @param service the GenerateReport.
     */
    public GenerateReportDelegate(GenerateReport service) {
        super();
        this.service = service;
    }

    /**
     * GenerateReport.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the GenerateReportRq.
     * @return the GenerateReportRs.
     * @throws ClientException
     */
    public GenerateReportRs generateReport(GenerateReportRq message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<GenerateReportRq> request = new ServiceRequest<GenerateReportRq>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<GenerateReportRs> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.generateReport(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(GenerateReport.class, "generateReport", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: GenerateReport.generateReport");
    }
}
