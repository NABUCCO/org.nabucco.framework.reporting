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
package org.nabucco.framework.reporting.facade.component;

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.component.locator.ComponentLocator;
import org.nabucco.framework.base.facade.component.locator.ComponentLocatorSupport;

/**
 * Locator for ReportingComponent.
 *
 * @author NABUCCO Generator, PRODYNA AG
 */
public class ReportingComponentLocator extends ComponentLocatorSupport<ReportingComponent> implements
        ComponentLocator<ReportingComponent> {

    private static ReportingComponentLocator instance;

    /**
     * Constructs a new ReportingComponentLocator instance.
     *
     * @param component the Class<ReportingComponent>.
     * @param jndiName the String.
     */
    private ReportingComponentLocator(String jndiName, Class<ReportingComponent> component) {
        super(jndiName, component);
    }

    @Override
    public ReportingComponent getComponent() throws ConnectionException {
        ReportingComponent component = super.getComponent();
        if ((component instanceof ReportingComponentLocal)) {
            return new ReportingComponentLocalProxy(((ReportingComponentLocal) component));
        }
        return component;
    }

    /**
     * Getter for the Instance.
     *
     * @return the ReportingComponentLocator.
     */
    public static ReportingComponentLocator getInstance() {
        if ((instance == null)) {
            instance = new ReportingComponentLocator(ReportingComponent.JNDI_NAME, ReportingComponent.class);
        }
        return instance;
    }
}
