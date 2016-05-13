#Locomotive Efficiency Analytic in Python

In this problem, the goal is to model the instantaneous operational efficiency of a diesel locomotive based on some controllable and non-controllable factors. The total amount of useful work performed by a locomotive is typically measured in Revenue Ton Miles (RTM) which is calculated by multiplying the weight in tons of freight by the number of miles transported. Therefore, we will express operational efficiency of a diesel locomotive in Revenue Ton Miles (RTM) per gallon of fuel. There are several factors that contribute to the operational efficiency of a locomotive, such as, wind speed, locomotive speed, grade (slope) of track, locomotive design etc. However, to keep things simple, we will assume that under certain conditions, the instantaneous values of operational efficiency for a single locomotive become a function of locomotive speed and wind speed only.  This is known as a regression problem. Owing to its simplicity, we will use Ordinary Least Squares Linear Regression (LR).

The dataset that we have at our disposal to model the problem consists of 1200 data points and each data point consists of three measurements -- Locomotive Speed, Wind Speed and RTM per gallon. For the current problem, we have a strong reason to believe that the operational efficiency (measured by RTM/gal) is also strongly dependent on the square of locomotive speed. Hence, our analytic will add the Locomotive Speed Squared as an additional feature for regression.

## Compiled binaries
Refer to the [Releases](https://github.com/PredixDev/predix-analytics-sample/releases) page for compiled binaries you can upload directly to Predix Analytics.

## Pre-requisites
To build and run this analytic, you will need to have the following:

- Python 2.7+
- Maven 3+

## Building, deploying and running the analytic
1. Zip the contents of the demo-RTM-loco-py directory, or just get the latest binary from the [Releases](https://github.com/PredixDev/predix-analytics-sample/releases) page.
2. Create an analytic in Analytics Catalog with the name "Demo RTM python" and the version "v1".
3. Upload the zip file and attach it to the created analytic entry.
4. Deploy and test the analytic on Predix Analytics platform.

## Analytic template
This analytic takes in test and training data sets. The analytic returns the predicted RTM values for the test dataset and the R-squared measure the of the linear regression model. This structure is outlined in this [analytic template](../demo-RTM-loco-template.json).

## Input format

The datasets must include the instantaneous values of the locomotive speed, wind speed, and the efficiency metric of the diesel locomotive, namely Revenue Ton Miles (RTM) per gallon.  The expected JSON input data format, which includes training and test datasets, is as follows:  

```
{
  "train": {
    "loco_speed": <list of values of length N1>,
    "wind_speed": <list of values of length N1>,
    "RTM": <list of values of length N1>
  },
  "test": {
    "loco_speed": <list of values of length N2>,
    "wind_speed": <list of values of length N2>,
    "RTM": <list of values of length N2>
  }
}
```

See **[sampleInput.json](../sampleInput.json)** for an example input file.

## Output format

Output includes the predicted values of RTM for the test dataset, and the R-squared measure of the linear regression model used to make the predictions:

```
{
    "Prediction": [
        370.0659,
        366.4111,
        ...
        366.0072,
        361.3121
    ],
    "R2": [
        0.97671
    ]
}
```

See **[sampleOutput.json](../sampleOutput.json)** for an example output file.

## Deploying the analytic to the Predix Cloud
When you upload the py file as an 'Executable' artifact the platform wraps the executable as a web service exposing the analytic via a URI derived from the analytic name.
Requests made to this generated URI will be passed to the entry point method.

### Additional Information
We are oversimplifying the problem of creating analytics that model instantaneous operational efficiency of a diesel locomotive. The purpose of this sample analytic is to get you started on building and deploying your analytic in Predix Analytics Services.  

For more information on developing analytics for use with the Predix Analytics platform, please visit the **[Analytic Development](https://www.predix.io/docs#Qd2kPYb7)** section of the Predix Analytics Services documentation on predix.io. 


