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

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.exception.service.ReportingException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.ServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandlerSupport;
import org.nabucco.framework.reporting.facade.message.GenerateReportRq;
import org.nabucco.framework.reporting.facade.message.GenerateReportRs;

/**
 * GenerateReportServiceHandler<p/>Report generation service<p/>
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-09
 */
public abstract class GenerateReportServiceHandler extends PersistenceServiceHandlerSupport implements ServiceHandler,
        PersistenceServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.framework.reporting.impl.service.reporting.GenerateReportServiceHandler";

    /** Constructs a new GenerateReportServiceHandler instance. */
    public GenerateReportServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<GenerateReportRq>.
     * @return the ServiceResponse<GenerateReportRs>.
     * @throws ReportingException
     */
    protected ServiceResponse<GenerateReportRs> invoke(ServiceRequest<GenerateReportRq> rq) throws ReportingException {
        ServiceResponse<GenerateReportRs> rs;
        GenerateReportRs msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.generateReport(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<GenerateReportRs>(rq.getContext());
            rs.setResponseMessage(msg);
            return rs;
        } catch (ReportingException e) {
            super.getLogger().error(e);
            throw e;
        } catch (NabuccoException e) {
            super.getLogger().error(e);
            ReportingException wrappedException = new ReportingException(e);
            throw wrappedException;
        } catch (Exception e) {
            super.getLogger().error(e);
            throw new ReportingException("Error during service invocation.", e);
        }
    }

    /**
     * Missing description at method generateReport.
     *
     * @param msg the GenerateReportRq.
     * @return the GenerateReportRs.
     * @throws ReportingException
     */
    protected abstract GenerateReportRs generateReport(GenerateReportRq msg) throws ReportingException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
