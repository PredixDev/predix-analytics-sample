import json

from scipy import stats


class SimpleLinearRegression:

    def compute(self, data):
        data_json = json.loads(data)
        x = data_json.get("x")
        y = data_json.get("y")
        slope, intercept, r_value, p_value, std_err = stats.linregress(x, y)

        output = {
            "slope": slope,
            "intercept": intercept,
            "r_value": r_value,
            "p_value": p_value,
            "std_err": std_err}

        return json.dumps(output)
