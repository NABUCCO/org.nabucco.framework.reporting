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
package org.nabucco.framework.reporting.facade.datatype;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.Flag;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.code.CodePath;
import org.nabucco.framework.base.facade.datatype.net.Url;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.reporting.ReportFormat;

/**
 * ReportConfiguration<p/>Configuration information for a report creation<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-09-27
 */
public class ReportConfiguration extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;", "l0,255;u0,n;m1,1;",
            "l0,255;u0,n;m1,1;", "l0,255;u0,n;m1,1;", "l0,255;u0,n;m1,1;", "l0,255;u0,n;m1,1;", "l0,255;u0,n;m1,1;",
            "l0,255;u0,n;m1,1;", "m1,1;", "l0,n;u0,n;m1,1;", "m1,1;" };

    public static final String NAME = "name";

    public static final String DESCRIPTION = "description";

    public static final String REPORTFILENAME = "reportFileName";

    public static final String SCRIPTID = "scriptId";

    public static final String TEMPLATEID = "templateId";

    public static final String TEMPLATEURL = "templateUrl";

    public static final String LOCALOUTPUTBASEURL = "localOutputBaseUrl";

    public static final String REMOTEOUTPUTBASEURL = "remoteOutputBaseUrl";

    public static final String REPORTTYPE = "reportType";

    public static final String SYNCHRONOUS = "synchronous";

    public static final String REPORTFORMAT = "reportFormat";

    /** name of this ReportConfiguration */
    private Name name;

    /** description of this ReportConfiguration */
    private Description description;

    /** name of the report file */
    private Name reportFileName;

    /** script identifier */
    private Name scriptId;

    /** template identifier */
    private Name templateId;

    /** the url where the template is placed */
    private Url templateUrl;

    /** the url where the report is stored for local access */
    private Url localOutputBaseUrl;

    /** the url where the report is stored for remote access */
    private Url remoteOutputBaseUrl;

    /** the identifier of the report */
    private Code reportType;

    private Long reportTypeRefId;

    protected static final String REPORTTYPE_CODEPATH = "nabucco.testautomation.report";

    /** should the report be executed synchronous or asynchronous */
    private Flag synchronous;

    /** the putput format of the report */
    private ReportFormat reportFormat;

    /** Constructs a new ReportConfiguration instance. */
    public ReportConfiguration() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ReportConfiguration.
     */
    protected void cloneObject(ReportConfiguration clone) {
        super.cloneObject(clone);
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.getReportFileName() != null)) {
            clone.setReportFileName(this.getReportFileName().cloneObject());
        }
        if ((this.getScriptId() != null)) {
            clone.setScriptId(this.getScriptId().cloneObject());
        }
        if ((this.getTemplateId() != null)) {
            clone.setTemplateId(this.getTemplateId().cloneObject());
        }
        if ((this.getTemplateUrl() != null)) {
            clone.setTemplateUrl(this.getTemplateUrl().cloneObject());
        }
        if ((this.getLocalOutputBaseUrl() != null)) {
            clone.setLocalOutputBaseUrl(this.getLocalOutputBaseUrl().cloneObject());
        }
        if ((this.getRemoteOutputBaseUrl() != null)) {
            clone.setRemoteOutputBaseUrl(this.getRemoteOutputBaseUrl().cloneObject());
        }
        if ((this.getReportType() != null)) {
            clone.setReportType(this.getReportType().cloneObject());
        }
        if ((this.getReportTypeRefId() != null)) {
            clone.setReportTypeRefId(this.getReportTypeRefId());
        }
        if ((this.getSynchronous() != null)) {
            clone.setSynchronous(this.getSynchronous().cloneObject());
        }
        clone.setReportFormat(this.getReportFormat());
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoDatatype.class).getPropertyMap());
        propertyMap.put(NAME,
                PropertyDescriptorSupport.createBasetype(NAME, Name.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createBasetype(DESCRIPTION, Description.class, 4,
                PROPERTY_CONSTRAINTS[1], false));
        propertyMap
                .put(REPORTFILENAME, PropertyDescriptorSupport.createBasetype(REPORTFILENAME, Name.class, 5,
                        PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(SCRIPTID,
                PropertyDescriptorSupport.createBasetype(SCRIPTID, Name.class, 6, PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(TEMPLATEID,
                PropertyDescriptorSupport.createBasetype(TEMPLATEID, Name.class, 7, PROPERTY_CONSTRAINTS[4], false));
        propertyMap.put(TEMPLATEURL,
                PropertyDescriptorSupport.createBasetype(TEMPLATEURL, Url.class, 8, PROPERTY_CONSTRAINTS[5], false));
        propertyMap.put(LOCALOUTPUTBASEURL, PropertyDescriptorSupport.createBasetype(LOCALOUTPUTBASEURL, Url.class, 9,
                PROPERTY_CONSTRAINTS[6], false));
        propertyMap.put(REMOTEOUTPUTBASEURL, PropertyDescriptorSupport.createBasetype(REMOTEOUTPUTBASEURL, Url.class,
                10, PROPERTY_CONSTRAINTS[7], false));
        propertyMap.put(REPORTTYPE, PropertyDescriptorSupport.createDatatype(REPORTTYPE, Code.class, 11,
                PROPERTY_CONSTRAINTS[8], false, PropertyAssociationType.COMPONENT, REPORTTYPE_CODEPATH));
        propertyMap.put(SYNCHRONOUS,
                PropertyDescriptorSupport.createBasetype(SYNCHRONOUS, Flag.class, 12, PROPERTY_CONSTRAINTS[9], false));
        propertyMap.put(REPORTFORMAT, PropertyDescriptorSupport.createEnumeration(REPORTFORMAT, ReportFormat.class, 13,
                PROPERTY_CONSTRAINTS[10], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ReportConfiguration.getPropertyDescriptor(NAME), this.name, null));
        properties.add(super.createProperty(ReportConfiguration.getPropertyDescriptor(DESCRIPTION), this.description,
                null));
        properties.add(super.createProperty(ReportConfiguration.getPropertyDescriptor(REPORTFILENAME),
                this.reportFileName, null));
        properties.add(super.createProperty(ReportConfiguration.getPropertyDescriptor(SCRIPTID), this.scriptId, null));
        properties.add(super.createProperty(ReportConfiguration.getPropertyDescriptor(TEMPLATEID), this.templateId,
                null));
        properties.add(super.createProperty(ReportConfiguration.getPropertyDescriptor(TEMPLATEURL), this.templateUrl,
                null));
        properties.add(super.createProperty(ReportConfiguration.getPropertyDescriptor(LOCALOUTPUTBASEURL),
                this.localOutputBaseUrl, null));
        properties.add(super.createProperty(ReportConfiguration.getPropertyDescriptor(REMOTEOUTPUTBASEURL),
                this.remoteOutputBaseUrl, null));
        properties.add(super.createProperty(ReportConfiguration.getPropertyDescriptor(REPORTTYPE),
                this.getReportType(), this.reportTypeRefId));
        properties.add(super.createProperty(ReportConfiguration.getPropertyDescriptor(SYNCHRONOUS), this.synchronous,
                null));
        properties.add(super.createProperty(ReportConfiguration.getPropertyDescriptor(REPORTFORMAT),
                this.getReportFormat(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(NAME) && (property.getType() == Name.class))) {
            this.setName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == Description.class))) {
            this.setDescription(((Description) property.getInstance()));
            return true;
        } else if ((property.getName().equals(REPORTFILENAME) && (property.getType() == Name.class))) {
            this.setReportFileName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SCRIPTID) && (property.getType() == Name.class))) {
            this.setScriptId(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TEMPLATEID) && (property.getType() == Name.class))) {
            this.setTemplateId(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TEMPLATEURL) && (property.getType() == Url.class))) {
            this.setTemplateUrl(((Url) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LOCALOUTPUTBASEURL) && (property.getType() == Url.class))) {
            this.setLocalOutputBaseUrl(((Url) property.getInstance()));
            return true;
        } else if ((property.getName().equals(REMOTEOUTPUTBASEURL) && (property.getType() == Url.class))) {
            this.setRemoteOutputBaseUrl(((Url) property.getInstance()));
            return true;
        } else if ((property.getName().equals(REPORTTYPE) && (property.getType() == Code.class))) {
            this.setReportType(((Code) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SYNCHRONOUS) && (property.getType() == Flag.class))) {
            this.setSynchronous(((Flag) property.getInstance()));
            return true;
        } else if ((property.getName().equals(REPORTFORMAT) && (property.getType() == ReportFormat.class))) {
            this.setReportFormat(((ReportFormat) property.getInstance()));
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if ((this == obj)) {
            return true;
        }
        if ((obj == null)) {
            return false;
        }
        if ((this.getClass() != obj.getClass())) {
            return false;
        }
        if ((!super.equals(obj))) {
            return false;
        }
        final ReportConfiguration other = ((ReportConfiguration) obj);
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.description == null)) {
            if ((other.description != null))
                return false;
        } else if ((!this.description.equals(other.description)))
            return false;
        if ((this.reportFileName == null)) {
            if ((other.reportFileName != null))
                return false;
        } else if ((!this.reportFileName.equals(other.reportFileName)))
            return false;
        if ((this.scriptId == null)) {
            if ((other.scriptId != null))
                return false;
        } else if ((!this.scriptId.equals(other.scriptId)))
            return false;
        if ((this.templateId == null)) {
            if ((other.templateId != null))
                return false;
        } else if ((!this.templateId.equals(other.templateId)))
            return false;
        if ((this.templateUrl == null)) {
            if ((other.templateUrl != null))
                return false;
        } else if ((!this.templateUrl.equals(other.templateUrl)))
            return false;
        if ((this.localOutputBaseUrl == null)) {
            if ((other.localOutputBaseUrl != null))
                return false;
        } else if ((!this.localOutputBaseUrl.equals(other.localOutputBaseUrl)))
            return false;
        if ((this.remoteOutputBaseUrl == null)) {
            if ((other.remoteOutputBaseUrl != null))
                return false;
        } else if ((!this.remoteOutputBaseUrl.equals(other.remoteOutputBaseUrl)))
            return false;
        if ((this.reportType == null)) {
            if ((other.reportType != null))
                return false;
        } else if ((!this.reportType.equals(other.reportType)))
            return false;
        if ((this.reportTypeRefId == null)) {
            if ((other.reportTypeRefId != null))
                return false;
        } else if ((!this.reportTypeRefId.equals(other.reportTypeRefId)))
            return false;
        if ((this.synchronous == null)) {
            if ((other.synchronous != null))
                return false;
        } else if ((!this.synchronous.equals(other.synchronous)))
            return false;
        if ((this.reportFormat == null)) {
            if ((other.reportFormat != null))
                return false;
        } else if ((!this.reportFormat.equals(other.reportFormat)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.reportFileName == null) ? 0 : this.reportFileName.hashCode()));
        result = ((PRIME * result) + ((this.scriptId == null) ? 0 : this.scriptId.hashCode()));
        result = ((PRIME * result) + ((this.templateId == null) ? 0 : this.templateId.hashCode()));
        result = ((PRIME * result) + ((this.templateUrl == null) ? 0 : this.templateUrl.hashCode()));
        result = ((PRIME * result) + ((this.localOutputBaseUrl == null) ? 0 : this.localOutputBaseUrl.hashCode()));
        result = ((PRIME * result) + ((this.remoteOutputBaseUrl == null) ? 0 : this.remoteOutputBaseUrl.hashCode()));
        result = ((PRIME * result) + ((this.reportType == null) ? 0 : this.reportType.hashCode()));
        result = ((PRIME * result) + ((this.reportTypeRefId == null) ? 0 : this.reportTypeRefId.hashCode()));
        result = ((PRIME * result) + ((this.synchronous == null) ? 0 : this.synchronous.hashCode()));
        result = ((PRIME * result) + ((this.reportFormat == null) ? 0 : this.reportFormat.hashCode()));
        return result;
    }

    @Override
    public ReportConfiguration cloneObject() {
        ReportConfiguration clone = new ReportConfiguration();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * name of this ReportConfiguration
     *
     * @return the Name.
     */
    public Name getName() {
        return this.name;
    }

    /**
     * name of this ReportConfiguration
     *
     * @param name the Name.
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * name of this ReportConfiguration
     *
     * @param name the String.
     */
    public void setName(String name) {
        if ((this.name == null)) {
            if ((name == null)) {
                return;
            }
            this.name = new Name();
        }
        this.name.setValue(name);
    }

    /**
     * description of this ReportConfiguration
     *
     * @return the Description.
     */
    public Description getDescription() {
        return this.description;
    }

    /**
     * description of this ReportConfiguration
     *
     * @param description the Description.
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * description of this ReportConfiguration
     *
     * @param description the String.
     */
    public void setDescription(String description) {
        if ((this.description == null)) {
            if ((description == null)) {
                return;
            }
            this.description = new Description();
        }
        this.description.setValue(description);
    }

    /**
     * name of the report file
     *
     * @return the Name.
     */
    public Name getReportFileName() {
        return this.reportFileName;
    }

    /**
     * name of the report file
     *
     * @param reportFileName the Name.
     */
    public void setReportFileName(Name reportFileName) {
        this.reportFileName = reportFileName;
    }

    /**
     * name of the report file
     *
     * @param reportFileName the String.
     */
    public void setReportFileName(String reportFileName) {
        if ((this.reportFileName == null)) {
            if ((reportFileName == null)) {
                return;
            }
            this.reportFileName = new Name();
        }
        this.reportFileName.setValue(reportFileName);
    }

    /**
     * script identifier
     *
     * @return the Name.
     */
    public Name getScriptId() {
        return this.scriptId;
    }

    /**
     * script identifier
     *
     * @param scriptId the Name.
     */
    public void setScriptId(Name scriptId) {
        this.scriptId = scriptId;
    }

    /**
     * script identifier
     *
     * @param scriptId the String.
     */
    public void setScriptId(String scriptId) {
        if ((this.scriptId == null)) {
            if ((scriptId == null)) {
                return;
            }
            this.scriptId = new Name();
        }
        this.scriptId.setValue(scriptId);
    }

    /**
     * template identifier
     *
     * @return the Name.
     */
    public Name getTemplateId() {
        return this.templateId;
    }

    /**
     * template identifier
     *
     * @param templateId the Name.
     */
    public void setTemplateId(Name templateId) {
        this.templateId = templateId;
    }

    /**
     * template identifier
     *
     * @param templateId the String.
     */
    public void setTemplateId(String templateId) {
        if ((this.templateId == null)) {
            if ((templateId == null)) {
                return;
            }
            this.templateId = new Name();
        }
        this.templateId.setValue(templateId);
    }

    /**
     * the url where the template is placed
     *
     * @return the Url.
     */
    public Url getTemplateUrl() {
        return this.templateUrl;
    }

    /**
     * the url where the template is placed
     *
     * @param templateUrl the Url.
     */
    public void setTemplateUrl(Url templateUrl) {
        this.templateUrl = templateUrl;
    }

    /**
     * the url where the template is placed
     *
     * @param templateUrl the String.
     */
    public void setTemplateUrl(String templateUrl) {
        if ((this.templateUrl == null)) {
            if ((templateUrl == null)) {
                return;
            }
            this.templateUrl = new Url();
        }
        this.templateUrl.setValue(templateUrl);
    }

    /**
     * the url where the report is stored for local access
     *
     * @return the Url.
     */
    public Url getLocalOutputBaseUrl() {
        return this.localOutputBaseUrl;
    }

    /**
     * the url where the report is stored for local access
     *
     * @param localOutputBaseUrl the Url.
     */
    public void setLocalOutputBaseUrl(Url localOutputBaseUrl) {
        this.localOutputBaseUrl = localOutputBaseUrl;
    }

    /**
     * the url where the report is stored for local access
     *
     * @param localOutputBaseUrl the String.
     */
    public void setLocalOutputBaseUrl(String localOutputBaseUrl) {
        if ((this.localOutputBaseUrl == null)) {
            if ((localOutputBaseUrl == null)) {
                return;
            }
            this.localOutputBaseUrl = new Url();
        }
        this.localOutputBaseUrl.setValue(localOutputBaseUrl);
    }

    /**
     * the url where the report is stored for remote access
     *
     * @return the Url.
     */
    public Url getRemoteOutputBaseUrl() {
        return this.remoteOutputBaseUrl;
    }

    /**
     * the url where the report is stored for remote access
     *
     * @param remoteOutputBaseUrl the Url.
     */
    public void setRemoteOutputBaseUrl(Url remoteOutputBaseUrl) {
        this.remoteOutputBaseUrl = remoteOutputBaseUrl;
    }

    /**
     * the url where the report is stored for remote access
     *
     * @param remoteOutputBaseUrl the String.
     */
    public void setRemoteOutputBaseUrl(String remoteOutputBaseUrl) {
        if ((this.remoteOutputBaseUrl == null)) {
            if ((remoteOutputBaseUrl == null)) {
                return;
            }
            this.remoteOutputBaseUrl = new Url();
        }
        this.remoteOutputBaseUrl.setValue(remoteOutputBaseUrl);
    }

    /**
     * the identifier of the report
     *
     * @param reportType the Code.
     */
    public void setReportType(Code reportType) {
        this.reportType = reportType;
        if ((reportType != null)) {
            this.setReportTypeRefId(reportType.getId());
        } else {
            this.setReportTypeRefId(null);
        }
    }

    /**
     * the identifier of the report
     *
     * @return the Code.
     */
    public Code getReportType() {
        return this.reportType;
    }

    /**
     * Getter for the ReportTypeRefId.
     *
     * @return the Long.
     */
    public Long getReportTypeRefId() {
        return this.reportTypeRefId;
    }

    /**
     * Setter for the ReportTypeRefId.
     *
     * @param reportTypeRefId the Long.
     */
    public void setReportTypeRefId(Long reportTypeRefId) {
        this.reportTypeRefId = reportTypeRefId;
    }

    /**
     * should the report be executed synchronous or asynchronous
     *
     * @return the Flag.
     */
    public Flag getSynchronous() {
        return this.synchronous;
    }

    /**
     * should the report be executed synchronous or asynchronous
     *
     * @param synchronous the Flag.
     */
    public void setSynchronous(Flag synchronous) {
        this.synchronous = synchronous;
    }

    /**
     * should the report be executed synchronous or asynchronous
     *
     * @param synchronous the Boolean.
     */
    public void setSynchronous(Boolean synchronous) {
        if ((this.synchronous == null)) {
            if ((synchronous == null)) {
                return;
            }
            this.synchronous = new Flag();
        }
        this.synchronous.setValue(synchronous);
    }

    /**
     * the putput format of the report
     *
     * @return the ReportFormat.
     */
    public ReportFormat getReportFormat() {
        return this.reportFormat;
    }

    /**
     * the putput format of the report
     *
     * @param reportFormat the ReportFormat.
     */
    public void setReportFormat(ReportFormat reportFormat) {
        this.reportFormat = reportFormat;
    }

    /**
     * the putput format of the report
     *
     * @param reportFormat the String.
     */
    public void setReportFormat(String reportFormat) {
        if ((reportFormat == null)) {
            this.reportFormat = null;
        } else {
            this.reportFormat = ReportFormat.valueOf(reportFormat);
        }
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ReportConfiguration.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ReportConfiguration.class).getAllProperties();
    }

    /**
     * Getter for the ReportTypeCodePath.
     *
     * @return the CodePath.
     */
    public static CodePath getReportTypeCodePath() {
        return new CodePath(REPORTTYPE_CODEPATH);
    }
}
