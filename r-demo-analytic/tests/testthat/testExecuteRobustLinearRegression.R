library(MASS)
library(testthat)
library(jsonlite)

context("Testing Robust Linear Regression via JSON (REST call)")

test_that("ExecuteRobustLinearRegression passing JSON in request and response stream", {
    requestJSON = paste0("{\"x\":[4,4,7,7,8,9,10,10,10,11,11,12,12,12,12,13,13,13,13,14,14,14,14,15,15,15,16,16,17,17,17,18,18,18",
                        ",18,19,19,19,20,20,20,20,20,22,23,24,24,24,24,25],\"y\":[2,10,4,22,16,10,18,26,34,17,28,14,20,24,28,26",
                        ",34,34,46,26,36,60,80,20,26,54,32,40,32,40,50,42,56,76,84,36,46,68,32,48,52,56,64,66,54,70,92,93,120,85]}")

    responseJSON = ExecuteRobustLinearRegression(requestJSON)
    res = fromJSON(responseJSON)
    expect_equivalent(round(res$coefficients, 3), 2.727)
    expect_equivalent(res$fitted.values[1:5], c(10.9079, 10.9079, 19.0888, 19.0888, 21.8157))
})

#responseJSON = ExecuteRobustLinearRegression("{ \"runUnitTest\":1}")


