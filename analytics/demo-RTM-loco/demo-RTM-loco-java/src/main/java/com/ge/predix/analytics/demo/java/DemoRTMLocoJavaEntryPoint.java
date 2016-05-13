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

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ge.predix.analytics.customdto.LocoRegressionRequest;
import com.ge.predix.analytics.customdto.LocoRegressionResponse;

public class DemoRTMLocoJavaEntryPoint {

	Logger logger = LoggerFactory.getLogger(DemoRTMLocoJavaEntryPoint.class);
	ObjectMapper mapper = new ObjectMapper();

	public String RTM(String jsonStr) {

		try {

			/* extract data */
			LocoRegressionRequest request = mapper.readValue(jsonStr, LocoRegressionRequest.class);

			double[][] train_feats = new double[3][];
			double[][] test_feats = new double[3][];

			double[] train_labels = request.getTrain().getRTM();
			double[] test_labels_true = request.getTest().getRTM();

			train_feats[0] = request.getTrain().getLoco_speed();
			test_feats[0] = request.getTest().getLoco_speed();

			train_feats[1] = request.getTrain().getWind_speed();
			test_feats[1] = request.getTest().getWind_speed();

			/* add non linear feature */
			train_feats[2] = train_feats[0].clone();
			test_feats[2] = test_feats[0].clone();

			/* square train_feats[2] and test_feats[2] */
			for (int i = 0; i < train_feats[2].length; i++) {
				train_feats[2][i] *= train_feats[2][i];
			}
			for (int i = 0; i < test_feats[2].length; i++) {
				test_feats[2][i] *= test_feats[2][i];
			}

			/* transpose the 2D train_feats array */
			double[][] X = new double[train_labels.length][3];
			for (int i = 0; i < train_labels.length; i++) {
				X[i][0] = train_feats[0][i];
				X[i][1] = train_feats[1][i];
				X[i][2] = train_feats[2][i];
			}

			/*  train Ordinary Least Squared regression model */
			OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
			regression.setNoIntercept(true);
			regression.newSampleData(train_labels, X);

			/* get R-Squared statistic for the model generated; add other metrics as needed */
			double coeff_of_det = regression.calculateRSquared();
			logger.info("R^2: " + coeff_of_det);

			/*  get regression model parameters */
			double[] beta = regression.estimateRegressionParameters();

			/* transpose the 2D test_feats array */
			double[][] X1 = new double[test_labels_true.length][3];
			for (int i = 0; i < test_labels_true.length; i++) {
				X1[i][0] = test_feats[0][i];
				X1[i][1] = test_feats[1][i];
				X1[i][2] = test_feats[2][i];
			}

			/* make prediction for test data */
			Array2DRowRealMatrix testMatrix = new Array2DRowRealMatrix(X1);
			double[] test_labels = testMatrix.operate(beta);
			logger.info("Predictions: " + test_labels);

			/* prepare JSON output */
			LocoRegressionResponse output = null;
			output = new LocoRegressionResponse();
			output.setR2(new double[] { coeff_of_det });
			output.setPrediction(test_labels);
			return mapper.writeValueAsString(output);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

}
