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

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.reporting.facade.component.ReportingComponent;
import org.nabucco.framework.reporting.facade.component.ReportingComponentLocator;
import org.nabucco.framework.reporting.facade.datatype.ReportConfiguration;
import org.nabucco.framework.reporting.facade.message.ReportConfigurationListMsg;
import org.nabucco.framework.reporting.facade.message.ReportConfigurationMsg;
import org.nabucco.framework.reporting.facade.service.maintain.MaintainReporting;

/**
 * MaintainReportConfigurationListServiceHandlerImpl
 * 
 * @author Dominic Trumpfheller, PRODYNA AG
 */
public class MaintainReportConfigurationListServiceHandlerImpl extends MaintainReportConfigurationListServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected ReportConfigurationListMsg maintainReportConfigurationList(ReportConfigurationListMsg msg)
            throws MaintainException {

        MaintainReporting maintainService = this.locateMaintainService();

        ReportConfigurationListMsg result = new ReportConfigurationListMsg();

        for (ReportConfiguration reportConfiguration : msg.getReportConfigurationList()) {
            ReportConfigurationMsg reportConfigurationMsg = new ReportConfigurationMsg();
            reportConfigurationMsg.setReportConfiguration(reportConfiguration);

            ServiceRequest<ReportConfigurationMsg> rq = new ServiceRequest<ReportConfigurationMsg>(super.getContext());
            rq.setRequestMessage(reportConfigurationMsg);

            ServiceResponse<ReportConfigurationMsg> rs = maintainService.maintainReportConfiguration(rq);

            result.getReportConfigurationList().add(rs.getResponseMessage().getReportConfiguration());
        }

        return result;
    }

    /**
     * Locate the maintain service from JNDI.
     * 
     * @return the maintain service
     * 
     * @throws MaintainException
     *             when the service cannot be located
     */
    private MaintainReporting locateMaintainService() throws MaintainException {
        try {

            ReportingComponent reportingComponent = ReportingComponentLocator.getInstance().getComponent();
            MaintainReporting maintainService = reportingComponent.getMaintainReporting();
            return maintainService;

        } catch (ConnectionException ce) {
            throw new MaintainException("Error locating maintain reporting service.", ce);
        } catch (ServiceException se) {
            throw new MaintainException("Error locating maintain reporting service.", se);
        } catch (Exception e) {
            throw new MaintainException("Error locating maintain reporting service.", e);
        }
    }

}
