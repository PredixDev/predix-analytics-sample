#demo-adder

3 implementations of a sample analytic for the Predix Analytics platform.
Specific details on how to package these analytics can be found within the implementation folders:

- **[demo-adder-java](demo-adder-java):** A Java implementation of the demo-adder
- **[demo-adder-matlab-2011b](demo-adder-matlab-r2011b-impl):** A Matlab (r2011b) implementation of the demo-adder
- **[demo-adder-py](demo-adder-py):** A Python implementation of the demo-adder

## Analytic template
This analytic takes in 2 numbers and returns their sum. This structure is outlined in this [analytic template](demo-adder-template.json).

## Input format
The expected JSON input data format is as follows:
`{"number1": 123, "number2": 456}`

## Output format
The JSON output format from the analytic is as follows:
`{"result":579}`

For more information on developing analytics for the Predix Analytics platform, see [Analytic Development](https://www.predix.io/docs#Qd2kPYb7) in the Predix Analytics Services documentation on Predix IO.