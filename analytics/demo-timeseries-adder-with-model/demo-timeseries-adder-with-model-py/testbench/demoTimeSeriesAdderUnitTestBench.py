import sys
import os
import unittest
import json

sys.path.append(os.path.dirname(os.path.abspath(__file__)) + "/..")
from analytics import demoTimeSeriesAdder


class demoTimeSeriesAdderUnitTestBench(unittest.TestCase):
    def test_givenTimeSeriesAnalytic_whenAnalyticIsInvokedWithoutModel_thenAnalyticOutputIsAsExpected(self):
        analytic = demoTimeSeriesAdder()
        with open('./data/inputData/analyticInput.json') as json_input:
            actual_output_data = analytic.add2NumberArrays(json_input.read())
        with open('./data/outputData/expectedAnalyticOutput.json') as json_output:
            expected_output_data = json.loads(json_output.read())

        self.assertEqual(expected_output_data, json.loads(actual_output_data))


    def test_givenTimeSeriesAnalytic_whenAnalyticIsInvokedWithModel_thenAnalyticOutputIsAsExpected(self):
        analytic = demoTimeSeriesAdder()
        modelmap = { "threshold" : "200" }

        with open('./data/inputData/analyticInput.json') as json_input:
            actual_output_data = analytic.add2NumberArrays(json_input.read(), modelmap)
        with open('./data/outputData/expectedAnalyticOutputForThresholdOf200.json') as json_output:
            expected_output_data = json.loads(json_output.read())

        self.assertEqual(expected_output_data, json.loads(actual_output_data))


if __name__ == "__main__":
    unittest.main()
