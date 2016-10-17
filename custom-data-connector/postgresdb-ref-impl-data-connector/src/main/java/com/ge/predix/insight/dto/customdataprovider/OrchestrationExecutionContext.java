/*
 * Copyright (c) 2015 - 2016 General Electric Company. All rights reserved.
 *
 * The copyright to the computer software herein is the property of
 * General Electric Company. The software may be used and/or copied only
 * with the written permission of General Electric Company or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */

package com.ge.predix.insight.dto.customdataprovider;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="assetId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="orchestrationConfigurationID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="orchestrationExecutionRequestID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="analyticId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="analyticName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="analyticVersion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="analyticExecutionRequestID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "assetId",
    "orchestrationConfigurationID",
    "orchestrationExecutionRequestID",
    "analyticId",
    "analyticName",
    "analyticVersion",
    "analyticExecutionRequestID"
})
@XmlRootElement(name = "orchestrationExecutionContext")
public class OrchestrationExecutionContext {

    @XmlElement(required = true)
    protected String assetId;
    @XmlElement(required = true)
    protected String orchestrationConfigurationID;
    @XmlElement(required = true)
    protected String orchestrationExecutionRequestID;
    @XmlElement(required = true)
    protected String analyticId;
    @XmlElement(required = true)
    protected String analyticName;
    @XmlElement(required = true)
    protected String analyticVersion;
    @XmlElement(required = true)
    protected String analyticExecutionRequestID;

    /**
     * Gets the value of the assetId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssetId() {
        return assetId;
    }

    /**
     * Sets the value of the assetId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssetId(String value) {
        this.assetId = value;
    }

    /**
     * Gets the value of the orchestrationConfigurationID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrchestrationConfigurationID() {
        return orchestrationConfigurationID;
    }

    /**
     * Sets the value of the orchestrationConfigurationID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrchestrationConfigurationID(String value) {
        this.orchestrationConfigurationID = value;
    }

    /**
     * Gets the value of the orchestrationExecutionRequestID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrchestrationExecutionRequestID() {
        return orchestrationExecutionRequestID;
    }

    /**
     * Sets the value of the orchestrationExecutionRequestID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrchestrationExecutionRequestID(String value) {
        this.orchestrationExecutionRequestID = value;
    }

    /**
     * Gets the value of the analyticId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnalyticId() {
        return analyticId;
    }

    /**
     * Sets the value of the analyticId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnalyticId(String value) {
        this.analyticId = value;
    }

    /**
     * Gets the value of the analyticName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnalyticName() {
        return analyticName;
    }

    /**
     * Sets the value of the analyticName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnalyticName(String value) {
        this.analyticName = value;
    }

    /**
     * Gets the value of the analyticVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnalyticVersion() {
        return analyticVersion;
    }

    /**
     * Sets the value of the analyticVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnalyticVersion(String value) {
        this.analyticVersion = value;
    }

    /**
     * Gets the value of the analyticExecutionRequestID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnalyticExecutionRequestID() {
        return analyticExecutionRequestID;
    }

    /**
     * Sets the value of the analyticExecutionRequestID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnalyticExecutionRequestID(String value) {
        this.analyticExecutionRequestID = value;
    }

}
