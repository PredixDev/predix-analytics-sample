import json

class demoTimeSeriesAdder:
    def __init__(self):
        print "Create python time series demo adder"

    def add2NumberArrays(self, data, modelmap = {}):
        threshold = float(modelmap['threshold']) if len(modelmap) > 0 else None
        data_json = json.loads(data)
        numberArray1 = data_json['data']['time_series']['numberArray1']
        numberArray2 = data_json['data']['time_series']['numberArray2']
        sum = []
        for i in range(len(numberArray1)):
            result = numberArray1[i] + numberArray2[i]
            if (threshold is not None and result > threshold):
                sum.append(-1)
            else:
                sum.append(numberArray1[i] + numberArray2[i])

        timestamps = data_json['data']['time_series']['time_stamp']

        return json.dumps( \
            {
                "data":
                    {
                        "time_series":
                            {
                                "time_stamp": timestamps,
                                "sum": sum
                            }
                    }
            })


