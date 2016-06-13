#demo-adder-matlab (Matlab r2011b)

A Matlab-based sample analytic for Predix Analytics.

## Pre-requisites
To build and run this analytic, you will need to have the following:

- Matlab 2011b with Java Builder
- JDK 1.7+
- Maven 3+

## Building, deploying and running the analytic
1. Obtain the javabuilder.jar file corresponding to Matlab version r2011b and place it in the libs directory (`src/main/resources/libs`).
2. From the demo-adder-matlab-r2011b directory, run the `mvn clean package` command to build the analytic jar file.
3. Create an analytic in Analytics Catalog with the name "Demo Adder Matlab" and the version "v1".
4. Upload the generated jar file from the demo-adder-matlab-r2011b/target directory and attach it to the created analytic entry.
5. Deploy and test the analytic on the Predix Analytics platform.


## Input format
The expected JSON input data format is as follows:
`{"number1": 123, "number2": 456}`


## Developing a Matlab-based analytic
1. Implement the Matlab analytic, preferably such that it takes in data as a JSON string.
2. Generate the Java JAR for the Matlab analytic using the instructions in the document Matlab Builder for Java found at [http://soliton.ae.gatech.edu/classes/ae6382/documents/matlab/mathworks/javabuilder.pdf](http://soliton.ae.gatech.edu/classes/ae6382/documents/matlab/mathworks/javabuilder.pdf). Note the package, class name, and method name entered.
3. Create a Java module that consumes your Matlab analytic as a library. If you are using Maven, this means including the analytic JAR file as a dependency in the `pom.xml` file. 
4. Obtain the javabuilder.jar file corresponding to the Matlab version in which your analytic was developed and configure the Java module to consume it as a library.
5. Create a Java entry-point method which takes in the input data as a string, calls your Matlab algorithm, and returns the output as a string. If your Matlab method does not accept a JSON string as input, this Java entry point method will need to do the JSON parsing and call your Matlab method with the correctly formatted parameters.
6. Create the JSON configuration file `src/main/resources/config.json` containing the className, MethodName, and matlabVersion definitions that instruct the generated wrapper code to call your designated entry point method with the request payload.
7. Create a JAR package out of the Java module using either Maven (`mvn clean package`) or other tools.

In this example, the POM file consumes the **(3)** Java JAR for the Matlab analytic (matlabadder.jar) and **(4)** the javabuilder.jar for Matlab r2011b (which you will need to provide), which need to be located in the libs directory (`src/main/resources/libs`). The entry-point is **(5)** `add2Numbers` in the [DemoAdderJavaEntryPoint](src/main/java/com/ge/predix/insight/analytic/demo/matlab/DemoMatlabAdderEntryPoint.java) class.
**(6)** [config.json](src/main/resources/config.json) properly maps the entry point to the `add2Numbers` method of the `DemoMatlabAdderEntryPoint` class. 

## Deploying the analytic to the Predix Cloud
When you upload the jar file **(7)** as an 'Executable' artifact the platform wraps the executable as a web service exposing the analytic via a URI derived from the analytic name. 
Requests made to this generated URI will be passed to the entry point method.


For more information, see [Matlab Analytic Development](https://www.predix.io/docs#zwCROx4n) in the Predix Analytics Services documentation on Predix IO.