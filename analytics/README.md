# Sample Analytics

These are sample analytics written for use with the [Predix Analytics](https://www.predix.io/docs/#EG3xVdLg) platform:

- **[demo-adder](demo-adder):** A simple analytic that takes 2 numbers as input and returns their sum. It has been implemented in Java, Matlab (r2011b), and Python.
- **[demo-timeseries-adder-java](demo-timeseries-adder-java):** Takes 2 arrays of timeseries data and returns a timeseries array that contains the sums at each timestamp. Currently available in Java.
- **[demo-timeseries-adder-with-model](demo-timeseries-adder-with-model):** Takes 2 arrays of timeseries data and returns a timeseries array that contains the sums at each timestamp, adjusted by a threshold value provided in a trained model. Currently available in Java and Python.
- **[demo-RTM-loco](demo-RTM-loco):** A reference analytic that is used to calculate locomotive efficiency using a linear regression model. It has been implemented in Java, Matlab (r2011b), and Python.

For more information on developing analytics for use with Predix Analytics, see [Analytic Development](https://www.predix.io/docs/#Qd2kPYb7) on Predix IO. 
