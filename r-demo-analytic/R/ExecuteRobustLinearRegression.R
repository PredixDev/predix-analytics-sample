library(MASS)
library(jsonlite)

#' Execute RobustLinearRegression called with JSON from Cloudfoundry
#'
#' @param requestJSON the JSON stream passed on via http body when called with http POST
#'
#' @return responseJSON return JSON stream of http POST call
#' @export
#'
#' @examples library(MASS);library(jsonlite);ExecuteRobustLinearRegression('{"x":[4, 4, 7, 7, 8, 9,10,10,10],"y":[2,10, 4,22,16,10,18,26,34]}')
ExecuteRobustLinearRegression <- function(requestJSON) {

    #convert request JSON stream to list
    requestList = fromJSON(requestJSON)
    argNames = names(requestList)

    if ("runUnitTest" %in% argNames) {
        #test_file("./tests/testthat/testRobustLinearRegression.R")
        # run all the tests in test/testthat directory
        testResult = test_dir("./tests/testthat/")
        return(testResult)
    }
    # check arguments - need x and y vector
    if (! "x" %in% argNames) stop("X vector missing")
    if (! "y" %in% argNames) stop("Y vector missing")

    # extract vector x and y
    xVec = requestList[["x"]]
    yVec = requestList[["y"]]

    # execute core logic
    result = RobustLinearRegression(xVec, yVec, method="MM")

    # convert result response to
    resultList = list(coefficients = result$coefficients, fitted.values = result$fitted.values)
    responseJSON = toJSON(resultList)

    return(responseJSON)
}

