#demo-timeseries-adder-with-model

2 implementations of a sample analytic for the Predix Analytics platform that processes timeseries data and uses a trained model.
Specific details on how to package these analytics can be found within the implementation folders:

- **[demo-timeseries-adder-with-model-java](demo-timeseries-adder-with-model-java):** A Java implementation of the demo-timeseries-adder-with-model
- **[demo-timeseries-adder-with-model-py](demo-timeseries-adder-with-model-py):** A Python implementation of the demo-timeseries-adder-with-model

## Analytic template
This analytic takes in 2 timeseries arrays and returns their sum, augmented by a "threshold" model. This structure is outlined in this [analytic template](demo-timeseries-adder-with-model-template.json).

## Input format
The expected JSON input data format is as follows:
```json
{
  "data": {
    "time_series": {
      "numberArray1": [
        1.0,
        2.0,
        3.0
      ],
      "numberArray2": [
        100.0,
        200.0,
        300.0
      ],
      "time_stamp": [
        "1455733669601",
        "1455733669602",
        "1455733669603"
      ]
    }
  }
}
```

## Output format
The JSON output format from the analytic is as follows:
```json
{
  "data": {
    "time_series": {
      "time_stamp": [
        "1455733669601",
        "1455733669602",
        "1455733669603"
      ],
      "sum": [
        101.0,
        202.0,
        303.0
      ]
    }
  }
}
```

## Model
These analytics are written to accept a model with the key "threshold" that contains some numeric value. This is illustrated as the file [sampleModel.txt](sampleModel.txt).

For more information on developing analytics for the Predix Analytics platform, see [Analytic Development](https://docs.predix.io/en-US/content/service/analytics_services/analytics_framework/analytic-development) in the Predix Analytics Services documentation on Predix IO.