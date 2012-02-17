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

import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.reporting.facade.datatype.ReportConfiguration;
import org.nabucco.framework.reporting.facade.message.ReportConfigurationMsg;

/**
 * MaintainReportConfigurationServiceHandlerImpl
 * 
 * @author Dominic Trumpfheller, PRODYNA AG
 */
public class MaintainReportConfigurationServiceHandlerImpl extends MaintainReportConfigurationServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected ReportConfigurationMsg maintainReportConfiguration(ReportConfigurationMsg msg) throws MaintainException {

        ReportConfiguration reportConfiguration = msg.getReportConfiguration();

        try {
            super.getPersistenceManager().persist(reportConfiguration);
        } catch (Exception pe) {
            throw new MaintainException("Error maintaining ReportConfiguration.", pe);
        }

        return msg;
    }

}
