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

import org.nabucco.adapter.jasper.facade.adapter.JasperAdapter;
import org.nabucco.adapter.jasper.facade.adapter.JasperAdapterLocator;
import org.nabucco.adapter.jasper.facade.datatype.JasperReport;
import org.nabucco.adapter.jasper.facade.datatype.JasperReportFormat;
import org.nabucco.adapter.jasper.facade.datatype.JasperReportResult;
import org.nabucco.adapter.jasper.facade.datatype.reportdata.ReportDataList;
import org.nabucco.adapter.jasper.facade.exception.JasperException;
import org.nabucco.adapter.jasper.facade.message.JasperReportRq;
import org.nabucco.adapter.jasper.facade.message.JasperReportRs;
import org.nabucco.framework.base.facade.component.connection.Connection;
import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.component.connection.ConnectionFactory;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.net.Url;
import org.nabucco.framework.base.facade.exception.service.ReportingException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.reporting.facade.component.ReportingComponent;
import org.nabucco.framework.reporting.facade.component.ReportingComponentLocator;
import org.nabucco.framework.reporting.facade.datatype.ReportConfiguration;
import org.nabucco.framework.reporting.facade.message.GenerateReportRq;
import org.nabucco.framework.reporting.facade.message.GenerateReportRs;
import org.nabucco.framework.reporting.facade.message.ReportConfigurationListMsg;
import org.nabucco.framework.reporting.facade.message.search.ReportConfigurationSearchRq;
import org.nabucco.framework.reporting.facade.service.search.SearchReporting;

/**
 * GenerateReportServiceHandlerImpl
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class GenerateReportServiceHandlerImpl extends GenerateReportServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected GenerateReportRs generateReport(GenerateReportRq msg) throws ReportingException {

        try {
            // load report configuration
            ReportConfiguration config = loadReportConfiguration(msg.getReportType());

            // get data for report
            ReportDataList reportDataList = executeScript(config.getScriptId());

            // request reporting server
            JasperReport report = new JasperReport();
            report.setOutputBaseUrl(config.getLocalOutputBaseUrl());
            report.setReportFileName(config.getReportFileName());
            report.setReportDataList(reportDataList);
            report.setTemplateId(config.getTemplateId());
            report.setTemplateUrl(config.getTemplateUrl());

            if (config.getReportFormat() != null) {
                report.setReportFormat(JasperReportFormat.valueOf(config.getReportFormat().name()));
            }

            JasperReportResult result = this.createJasperReport(report);

            GenerateReportRs response = new GenerateReportRs();
            Url localUrl = result.getOutputUrl();

            StringBuilder remoteUrl = new StringBuilder();
            remoteUrl.append(config.getRemoteOutputBaseUrl());
            remoteUrl.append(localUrl.getValue().substring(config.getLocalOutputBaseUrl().getValue().length()));

            response.setUrl(new Url(remoteUrl.toString()));

            return response;

        } catch (Exception e) {
            throw new ReportingException(e);
        }
    }

    /**
     * Load the Report Configuration.
     * 
     * @param reportType
     *            the report type
     * 
     * @return the report configuration
     * 
     * @throws ReportingException
     *             when the configuration cannot be loaded
     */
    private ReportConfiguration loadReportConfiguration(Code reportType) throws ReportingException {

        try {
            ReportConfigurationSearchRq message = new ReportConfigurationSearchRq();
            message.setReportType(reportType);

            ServiceRequest<ReportConfigurationSearchRq> rq = new ServiceRequest<ReportConfigurationSearchRq>(
                    super.getContext());
            rq.setRequestMessage(message);

            ReportingComponent reportingComponent = ReportingComponentLocator.getInstance().getComponent();
            SearchReporting searchService = reportingComponent.getSearchReporting();

            ServiceResponse<ReportConfigurationListMsg> rs = searchService.searchReportConfiguration(rq);
            NabuccoList<ReportConfiguration> configurationList = rs.getResponseMessage().getReportConfigurationList();

            if (configurationList.isEmpty()) {
                throw new ReportingException("No Report Configuration found!");
            }

            if (configurationList.size() > 1) {
                throw new ReportingException("More than 1 Report Configurations found!");
            }

            return configurationList.first();

        } catch (SearchException e) {
            throw new ReportingException(e);
        } catch (ServiceException e) {
            throw new ReportingException(e);
        } catch (ConnectionException e) {
            throw new ReportingException(e);
        }
    }

    /**
     * Create the Jasper Report in the Jasper Server.
     * 
     * @param report
     *            the report to create
     * 
     * @return the created report
     * 
     * @throws ReportingException
     *             when the report cannot be created
     */
    private JasperReportResult createJasperReport(JasperReport report) throws ReportingException {

        try {
            ServiceRequest<JasperReportRq> rq = new ServiceRequest<JasperReportRq>(super.getContext());
            JasperReportRq message = new JasperReportRq();
            message.setReport(report);
            rq.setRequestMessage(message);

            Connection connection = ConnectionFactory.getInstance().createLocalConnection();
            JasperAdapter adapter = JasperAdapterLocator.getInstance().getAdapter(connection);

            ServiceResponse<JasperReportRs> rs = adapter.getJasperReportService().generateReport(rq);

            if (rs == null || rs.getResponseMessage() == null) {
                throw new ReportingException("No valid response message returned from Jasper Adapter.");
            }

            return rs.getResponseMessage().getResult();

        } catch (ConnectionException e) {
            throw new ReportingException("Error connecting to Jasper Adapter.");
        } catch (JasperException je) {
            throw new ReportingException("Error creating Jasper Report");
        } catch (Exception se) {
            throw new ReportingException("Unexpected error creating Jasper Report");
        }
    }

    private ReportDataList executeScript(Name scriptId) throws ReportingException {

        // as long as scripting is not available
        // a dummy implementation is used

        ScriptDelegate scripting = new ScriptDelegate();
        ReportDataList reportDataList = scripting.executeScript(scriptId, super.getPersistenceManager());

        return reportDataList;
    }

}
