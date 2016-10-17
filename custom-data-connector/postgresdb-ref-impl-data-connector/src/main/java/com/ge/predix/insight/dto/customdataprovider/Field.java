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
import com.ge.predix.insight.dto.errorresponse.ErrorResponse;


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
 *         &lt;element name="fieldId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fullyQualifiedPortName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dataType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="engUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/&gt;
 *         &lt;element name="queryCriteria" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/&gt;
 *         &lt;element ref="{http://predix.ge.com/insight/dto/errorresponse}errorResponse" minOccurs="0"/&gt;
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
    "fieldId",
    "fullyQualifiedPortName",
    "dataType",
    "engUnit",
    "data",
    "queryCriteria",
    "errorResponse"
})
@XmlRootElement(name = "field")
public class Field {

    @XmlElement(required = true)
    protected String fieldId;
    @XmlElement(required = true)
    protected String fullyQualifiedPortName;
    protected String dataType;
    protected String engUnit;
    protected Object data;
    protected Object queryCriteria;
    @XmlElement(namespace = "http://predix.ge.com/insight/dto/errorresponse")
    protected ErrorResponse errorResponse;

    /**
     * Gets the value of the fieldId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldId() {
        return fieldId;
    }

    /**
     * Sets the value of the fieldId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldId(String value) {
        this.fieldId = value;
    }

    /**
     * Gets the value of the fullyQualifiedPortName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFullyQualifiedPortName() {
        return fullyQualifiedPortName;
    }

    /**
     * Sets the value of the fullyQualifiedPortName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFullyQualifiedPortName(String value) {
        this.fullyQualifiedPortName = value;
    }

    /**
     * Gets the value of the dataType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * Sets the value of the dataType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataType(String value) {
        this.dataType = value;
    }

    /**
     * Gets the value of the engUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEngUnit() {
        return engUnit;
    }

    /**
     * Sets the value of the engUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEngUnit(String value) {
        this.engUnit = value;
    }

    /**
     * Gets the value of the data property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setData(Object value) {
        this.data = value;
    }

    /**
     * Gets the value of the queryCriteria property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getQueryCriteria() {
        return queryCriteria;
    }

    /**
     * Sets the value of the queryCriteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setQueryCriteria(Object value) {
        this.queryCriteria = value;
    }

    /**
     * Gets the value of the errorResponse property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorResponse }
     *     
     */
    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    /**
     * Sets the value of the errorResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorResponse }
     *     
     */
    public void setErrorResponse(ErrorResponse value) {
        this.errorResponse = value;
    }

}
