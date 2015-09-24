#demo-adder-py

A python-based sample analytic for Predix Analytics.

## Compiled binaries
Refer to the Releases page for compiled binaries you can upload directly to Predix Analytics.

## Pre-requisites
To run this analytic locally, you will need to have the following:
- Python 2.7+
- Flask 0.10+

## Building, deploying and running the analytic
1. Zip the demo-analytics/demo-adder-py directory, or just get the latest demo-adder-py binary from the Releases page.
2. Create an analytic in Analytics Catalog with the name "demo-adder-py" and the version "v1".
3. Upload the zip file and attach it to the created analytic.
4. Deploy and test the analytic on Predix Analytics platform.

## Input format
The expected JSON input data format is as follows:
`{"number1": 123, "number2": 456}`

## Output format
The JSON output format from the analytic is as follows:
`{"result":579}`

## Testing the analytic locally
1. Download the wrapped analytic from the Analytics Catalog.
2. Unzip the resultant zip file.
3. Run the command `python AnalyticDeployer.py` to start up the analytic. If the analytic runs successfully, the console log displays the port number. The default port number is 5000.
4. Invoke the REST API (POST http://localhost:5000/api/v1/demo-adder-py/execution) with input data as the request payload.

## Developing a Python-based analytic
1. Implement the analytic (and test functions) according to your development guidelines. In this discussion, the top level of the analytic implementation project structure is referred to as analytic directory.
2. Create a driver directory at the top level of your project structure. Place an AnalyticDriver.py file in the driver directory. The AnalyticDriver.py file must define driver and result provider functions.
3. Create a config.json file in the top level of the project directory. Specify the tags, libs, conda-libs, memory, and instance values.
4. Package all the analytic files and the driver directory and the config.json file into a ZIP file.

For more information, see "Analytic Development" in the Predix Analytics Services documentation on Predix IO.