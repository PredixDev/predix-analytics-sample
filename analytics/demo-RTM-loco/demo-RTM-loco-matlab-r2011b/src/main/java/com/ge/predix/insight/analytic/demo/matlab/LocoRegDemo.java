/*
 * Copyright (c) 2015 - 2016 General Electric Company. All rights reserved.
 *
 * The copyright to the computer software herein is the property of
 * General Electric Company. The software may be used and/or copied only
 * with the written permission of General Electric Company or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */

package com.ge.predix.insight.analytic.demo.matlab;

import com.mathworks.toolbox.javabuilder.MWArray;
import LocoRegression.*;

public class LocoRegDemo {
	public String locoRegression(String jsonStr) {
		Object[] result = null;
		Class1 LocoReg = null;
		String resultStr = "error";

		try {
			LocoReg = new Class1();
			result = LocoReg.LocomotiveRegression(1, jsonStr);
			//System.out.println(result[0]);
		} catch (Exception e) {
			System.out.println("Exception: " + e.toString());
		} finally {
			if (result != null) {
				if (result.length > 0) {
					resultStr = result[0].toString();
				}
			}

			MWArray.disposeArray(result);
			LocoReg.dispose();
		}
		return resultStr;
	}
}

