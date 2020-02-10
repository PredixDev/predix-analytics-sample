import json
from unittest import TestCase

from analytics import SimpleLinearRegression


class SimpleLinearRegressionTest(TestCase):

    def setUp(self):
        pass

    def test_compute(self):
        input_data_dict = {"y": [19, 20, 20.5, 21.5, 22, 23, 23, 25.5, 24],
                           "x": [1, 2, 3, 4, 5, 6, 7, 8, 9]}

        simple_linear_regression = SimpleLinearRegression()
        output_data = simple_linear_regression.compute(json.dumps(input_data_dict))

        analytic_output_dict = json.loads(output_data)

        print(analytic_output_dict)

        self.assertEqual(0.7166666666666667, analytic_output_dict["slope"])
        self.assertEqual(0.9559490311973318, analytic_output_dict["r_value"])
        self.assertEqual(5.6576124923241295e-05, analytic_output_dict["p_value"])
        self.assertEqual(18.472222222222225, analytic_output_dict["intercept"])
        self.assertEqual(0.08317445171439007, analytic_output_dict["std_err"])
