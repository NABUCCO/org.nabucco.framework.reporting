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

import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.reporting.facade.datatype.ReportConfiguration;
import org.nabucco.framework.reporting.facade.message.ReportConfigurationMsg;

/**
 * ResolveReportConfigurationServiceHandlerImpl
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class ResolveReportConfigurationServiceHandlerImpl extends ResolveReportConfigurationServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected ReportConfigurationMsg resolveReportConfiguration(ReportConfigurationMsg msg) throws ResolveException {

        ReportConfiguration reportConfiguration = msg.getReportConfiguration();
        ReportConfigurationMsg response = new ReportConfigurationMsg();

        try {
            reportConfiguration = super.getPersistenceManager().find(reportConfiguration);
            super.getPersistenceManager().clear();
        } catch (PersistenceException e) {
            throw new ResolveException("Cannot resolve ReportConfiguration with id " + reportConfiguration.getId(), e);
        } catch (Exception e) {
            throw new ResolveException("Cannot resolve ReportConfiguration with id " + reportConfiguration.getId(), e);
        }

        response.setReportConfiguration(reportConfiguration);
        return response;
    }

}
