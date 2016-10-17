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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.ge.predix.insight.dto.errorresponse.ErrorResponse;


/**
 * <p>Java class for DataResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DataResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://predix.ge.com/insight/dto/customdataprovider}field" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://predix.ge.com/insight/dto/customdataprovider}orchestrationExecutionContext"/&gt;
 *         &lt;element ref="{http://predix.ge.com/insight/dto/errorresponse}errorResponse" minOccurs="0"/&gt;
 *         &lt;element name="dataSourceId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataResponse", propOrder = {
    "field",
    "orchestrationExecutionContext",
    "errorResponse",
    "dataSourceId"
})
@XmlSeeAlso({
    AnalyticWriteDataResponse.class,
    AnalyticReadDataResponse.class
})
public class DataResponse {

    @XmlElement(namespace = "http://predix.ge.com/insight/dto/customdataprovider")
    protected List<Field> field;
    @XmlElement(namespace = "http://predix.ge.com/insight/dto/customdataprovider", required = true)
    protected OrchestrationExecutionContext orchestrationExecutionContext;
    @XmlElement(namespace = "http://predix.ge.com/insight/dto/errorresponse")
    protected ErrorResponse errorResponse;
    protected String dataSourceId;

    /**
     * Gets the value of the field property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the field property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getField().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Field }
     * 
     * 
     */
    public List<Field> getField() {
        if (field == null) {
            field = new ArrayList<Field>();
        }
        return this.field;
    }

    /**
     * Gets the value of the orchestrationExecutionContext property.
     * 
     * @return
     *     possible object is
     *     {@link OrchestrationExecutionContext }
     *     
     */
    public OrchestrationExecutionContext getOrchestrationExecutionContext() {
        return orchestrationExecutionContext;
    }

    /**
     * Sets the value of the orchestrationExecutionContext property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrchestrationExecutionContext }
     *     
     */
    public void setOrchestrationExecutionContext(OrchestrationExecutionContext value) {
        this.orchestrationExecutionContext = value;
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

    /**
     * Gets the value of the dataSourceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataSourceId() {
        return dataSourceId;
    }

    /**
     * Sets the value of the dataSourceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataSourceId(String value) {
        this.dataSourceId = value;
    }

}
