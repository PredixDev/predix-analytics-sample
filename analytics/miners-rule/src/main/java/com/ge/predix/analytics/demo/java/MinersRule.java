/*
 * Copyright (c) 2015 - 2016 General Electric Company. All rights reserved.
 *
 * The copyright to the computer software herein is the property of
 * General Electric Company. The software may be used and/or copied only
 * with the written permission of General Electric Company or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */

package com.ge.predix.analytics.demo.java;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ge.predix.analytics.customdto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MinersRule {

    public static final String STRESS_LIMITS_MODEL_NAME = "Stress Limits";
    Logger logger = LoggerFactory.getLogger(MinersRule.class);
    ObjectMapper mapper = new ObjectMapper();

    public String computeCDM(String jsonStr, Map<String, byte[]> inputModels) throws Exception {

        Input input = null;
        try {
            input = mapper.readValue(jsonStr, Input.class);
        } catch (Exception e) {
            throw new Exception("Trying to read the json input:"+jsonStr, e);
        }


        CDM_TimeSeries currentCDM = input.getCurrent_cdm();
        StressesTimeSeries recentStresses = input.getRecentStresses();

        if (inputModels == null || !inputModels.containsKey(STRESS_LIMITS_MODEL_NAME)) {
            throw new Exception(STRESS_LIMITS_MODEL_NAME+" model not found in the input model map");
        }
        byte[] stressLimitsModel = inputModels.get(STRESS_LIMITS_MODEL_NAME);
        if (stressLimitsModel == null) {
            throw new Exception("Model is null.");
        }
        List<Double> lifeLimits = convertToDoubleList(stressLimitsModel);

        verifyInput(recentStresses, lifeLimits);

        computeMinersCDM(currentCDM, recentStresses, lifeLimits);

        Response output = new Response();
        CDM_TimeSeries cdm_timeSeries = new CDM_TimeSeries();
        cdm_timeSeries.setTime_stamp(currentCDM.getTime_stamp());
        cdm_timeSeries.setCdm_values(currentCDM.getCdm_values());
        output.setUpdatedCDM(cdm_timeSeries);
        return mapper.writeValueAsString(output);


    }

    private void verifyInput(StressesTimeSeries recentStresses, List<Double> lifeLimits) throws Exception {
        if (recentStresses.getStresses().size() == 0) {
            throw new Exception("No stress input was recieved.  Fix the input");
        }

        if (lifeLimits.size() == 0) {
            throw new Exception("No life limits were recieved.  Fix the model");
        }

        if (recentStresses.getStresses().size() != lifeLimits.size()) {
            throw new Exception("Number of stresses (" + recentStresses.getStresses().size() +
                    ") did not match the number of life limits in the model (" +
                    lifeLimits.size() + "). Update the model or the inputs.");
        }
    }

    private List<Double> convertToDoubleList(byte[] stressLimitsModel) throws Exception {
        String asString = new String(stressLimitsModel);
        String[] stressLimitsArray = asString.split(",");

        List<Double> stressLimits = new ArrayList<Double>();
        for (String stressLimitAsString : stressLimitsArray) {
            try {
                Double value = Double.parseDouble(stressLimitAsString);
                stressLimits.add(value);
            } catch (NumberFormatException e) {
                throw new Exception("Couldn't parse " + stressLimitAsString + " to a stress limit.  The model contents are corrupted.", e);
            }
        }

        return stressLimits;
    }

    private void computeMinersCDM(CDM_TimeSeries currentCDM, StressesTimeSeries recentStresses, List<Double> stressLifeLimits) {
        List<Long> modelTimes = currentCDM.getTime_stamp();
        Long modelTime = modelTimes.get(modelTimes.size() - 1);

        int newCDMIndex = currentCDM.getCdm_values().size();
        Double currentCDMValue = currentCDM.getCdm_values().get(newCDMIndex - 1);
        Double cdmIncrement = 0.0;

        for (int j = 0; j < recentStresses.getStresses().size(); j++) {
            Double stressCyclesSum = 0.0;
            for (int i = recentStresses.getTime_stamp().size() - 1; i > 0 && i > modelTime; i--) {
                stressCyclesSum += recentStresses.getStresses().get(j).get(i);
            }
            Double stressContribution = stressCyclesSum / stressLifeLimits.get(j);
            cdmIncrement += stressContribution.floatValue();
            currentCDMValue += cdmIncrement;
        }

        List<Long> times = recentStresses.getTime_stamp();
        Long lastTime = times.get(times.size() - 1);

        currentCDM.getCdm_values().add(round2DecimalPlaces(currentCDMValue));
        currentCDM.getTime_stamp().add(lastTime);
    }


    private Double round2DecimalPlaces(Double currentCDMValue) {
        Long t = Math.round(currentCDMValue * 100l);
        Double roundedValue = t / 100.0;
        return roundedValue;
    }

}