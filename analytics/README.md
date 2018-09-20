# Sample Analytics

These are sample analytics written for use with [Predix Analytics](https://docs.predix.io/en-US/content/service/analytics_services/analytics_framework/):

- **[demo-adder](demo-adder):** A simple analytic that takes 2 numbers as input and returns their sum. It has been implemented in Java, Matlab (r2011b), and Python.
- **[demo-timeseries-adder-java](demo-timeseries-adder-java):** Takes 2 arrays of timeseries data and returns a timeseries array that contains the sums at each timestamp. Currently available in Java.
- **[demo-timeseries-adder-with-model](demo-timeseries-adder-with-model):** Takes 2 arrays of timeseries data and returns a timeseries array that contains the sums at each timestamp, adjusted by a threshold value provided in a trained model. Currently available in Java and Python.
- **[demo-RTM-loco](demo-RTM-loco):** A reference analytic that is used to calculate locomotive efficiency using a linear regression model. It has been implemented in Java, Matlab (r2011b), and Python.
- **[miners-rule](miners-rule):** A sample analytic that performs a Miner's Rule operation on 2 timeseries arrays and returns a timeseries array. Currently only available in Java.
- **[sample-linear-regression](sample-linear-regression):**:new: A sample analytic that performs a simple linear regression with two arrays as input and returns the p-value, r-value, slope, intercept and standard error. Currently only available in Python 3.

For more information on developing analytics for use with Predix Analytics, see [Analytic Development](https://docs.predix.io/en-US/content/service/analytics_services/analytics_framework/analytic-development) on Predix IO. 
