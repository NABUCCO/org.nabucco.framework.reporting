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
package org.nabucco.framework.reporting.impl.component;

/**
 * ReportingComponentJndiNames<p/>Reporting component<p/>
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-09
 */
public interface ReportingComponentJndiNames {

    final String COMPONENT_RELATION_SERVICE_LOCAL = "nabucco/org.nabucco.framework.reporting/org.nabucco.framework.reporting.facade.component.ComponentRelationService/local";

    final String COMPONENT_RELATION_SERVICE_REMOTE = "nabucco/org.nabucco.framework.reporting/org.nabucco.framework.reporting.facade.component.ComponentRelationService/remote";

    final String QUERY_FILTER_SERVICE_LOCAL = "nabucco/org.nabucco.framework.reporting/org.nabucco.framework.reporting.facade.component.QueryFilterService/local";

    final String QUERY_FILTER_SERVICE_REMOTE = "nabucco/org.nabucco.framework.reporting/org.nabucco.framework.reporting.facade.component.QueryFilterService/remote";

    final String MAINTAIN_REPORTING_LOCAL = "nabucco/org.nabucco.framework.reporting/org.nabucco.framework.reporting.facade.service.maintain.MaintainReporting/local";

    final String MAINTAIN_REPORTING_REMOTE = "nabucco/org.nabucco.framework.reporting/org.nabucco.framework.reporting.facade.service.maintain.MaintainReporting/remote";

    final String RESOLVE_REPORTING_LOCAL = "nabucco/org.nabucco.framework.reporting/org.nabucco.framework.reporting.facade.service.resolve.ResolveReporting/local";

    final String RESOLVE_REPORTING_REMOTE = "nabucco/org.nabucco.framework.reporting/org.nabucco.framework.reporting.facade.service.resolve.ResolveReporting/remote";

    final String SEARCH_REPORTING_LOCAL = "nabucco/org.nabucco.framework.reporting/org.nabucco.framework.reporting.facade.service.search.SearchReporting/local";

    final String SEARCH_REPORTING_REMOTE = "nabucco/org.nabucco.framework.reporting/org.nabucco.framework.reporting.facade.service.search.SearchReporting/remote";

    final String GENERATE_REPORT_LOCAL = "nabucco/org.nabucco.framework.reporting/org.nabucco.framework.reporting.facade.service.reporting.GenerateReport/local";

    final String GENERATE_REPORT_REMOTE = "nabucco/org.nabucco.framework.reporting/org.nabucco.framework.reporting.facade.service.reporting.GenerateReport/remote";
}
