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
package org.nabucco.framework.reporting.facade.service.reporting;

import org.nabucco.framework.base.facade.exception.service.ReportingException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.framework.reporting.facade.message.GenerateReportRq;
import org.nabucco.framework.reporting.facade.message.GenerateReportRs;

/**
 * GenerateReport<p/>Report generation service<p/>
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-09
 */
public interface GenerateReport extends Service {

    /**
     * Missing description at method generateReport.
     *
     * @param rq the ServiceRequest<GenerateReportRq>.
     * @return the ServiceResponse<GenerateReportRs>.
     * @throws ReportingException
     */
    ServiceResponse<GenerateReportRs> generateReport(ServiceRequest<GenerateReportRq> rq) throws ReportingException;
}
