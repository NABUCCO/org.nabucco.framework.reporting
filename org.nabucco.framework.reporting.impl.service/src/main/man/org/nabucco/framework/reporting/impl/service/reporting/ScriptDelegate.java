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

import java.util.HashMap;
import java.util.Map;

import org.nabucco.adapter.jasper.facade.datatype.reportdata.ReportDataList;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.exception.service.ReportingException;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.reporting.impl.service.reporting.script.testautomation.summary.SummaryReportScript;
import org.nabucco.framework.reporting.impl.service.reporting.script.testautomation.testresult.simple.SimpleTestResultReportScript;

/**
 * ScriptDelegate
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
class ScriptDelegate {

    private static Map<String, Class<? extends ReportingScript>> keyToScriptMap = new HashMap<String, Class<? extends ReportingScript>>();

    static {
        keyToScriptMap.put("SummaryReportScript", SummaryReportScript.class);
        keyToScriptMap.put("SimpleTestResultScript", SimpleTestResultReportScript.class);
    }

    public ReportDataList executeScript(Name scriptId, PersistenceManager persistenceManager) throws ReportingException {

        Class<? extends ReportingScript> scriptClass = keyToScriptMap.get(scriptId.getValue());
        if (scriptClass != null) {
            try {
                return scriptClass.newInstance().createReportData(persistenceManager);
            } catch (Exception e) {
                throw new ReportingException("Could not initialze ReportingScript for id '" + scriptId + "'!");
            }
        }

        throw new ReportingException("No script for id '" + scriptId + "' found!");
    }
}
