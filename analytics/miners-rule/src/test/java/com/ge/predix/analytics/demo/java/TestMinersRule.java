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

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class TestMinersRule {


    @Test
    public void TestInvalidInputToMinersRule() {
        MinersRule minersRule = new MinersRule();

        // null input
        Assert.assertTrue(validateErrorFound(minersRule, "Trying to read the json input:null", null, null));


        // null model
        String inputStr = null;
        try {
            inputStr = getFileAsString("src/test/resources/minersRuleIn.json");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
        Assert.assertTrue(validateErrorFound(minersRule, MinersRule.STRESS_LIMITS_MODEL_NAME+" model not found in the input model map", inputStr, null));

        Assert.assertTrue(validateErrorFound(minersRule, "Trying to read the json input:Invalid json input structure", "Invalid json input structure", null));

        Map<String, byte[]> modelMap = new HashMap<>();
        Assert.assertTrue(validateErrorFound(minersRule, "Stress Limits model not found in the input model map", inputStr, modelMap));

        byte[] model = "XXXX".getBytes();
        modelMap.put(MinersRule.STRESS_LIMITS_MODEL_NAME, model);
        Assert.assertTrue(validateErrorFound(minersRule, "Couldn't parse XXXX to a stress limit.  The model contents are corrupted.", inputStr, modelMap));

    }

    private boolean validateErrorFound(MinersRule minersRule, String expected, String jsonStr, Map<String, byte[]> inputModels) {
        try {
            minersRule.computeCDM(jsonStr, inputModels);
        } catch (Exception e) {
            String cause = e.getMessage();
            Assert.assertEquals(expected,cause);
            return true;
        }

        return false;
    }


    @Test
    public void TestMinersRule() {
        String inputStr = null;
        try {
            inputStr = getFileAsString("src/test/resources/minersRuleIn.json");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }

        byte[] model = null;
        try {
            model = getFileAsByteArray("src/test/resources/minersRuleModel.txt");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
        Map<String, byte[]> models = new HashMap<String, byte[]>();
        models.put(MinersRule.STRESS_LIMITS_MODEL_NAME, model);

        MinersRule minersRule = new MinersRule();

        String jsonOut = null;
        try {
            jsonOut = minersRule.computeCDM(inputStr, models);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }

        String expectedOut = null;
        try {
            expectedOut = getFileAsString("src/test/resources/minersRuleOut.json");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }


        Assert.assertEquals(expectedOut, jsonOut);

    }

    private byte[] getFileAsByteArray(String path) throws IOException {
        File file = new File(path);
        byte[] contents = Files.readAllBytes(file.toPath());
        return contents;
    }

    private String getFileAsString(String path) throws java.io.IOException {
        File file = new File(path);
        byte[] contents = Files.readAllBytes(file.toPath());
        return new String(contents);
    }

}
