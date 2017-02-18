library(MASS)
library(testthat)
library(jsonlite)

context("Testing Robust Linear Regression Core")

test_that("RobustLinearRegression with car data and M-method", {
    res = RobustLinearRegression(cars$speed, cars$dist, method="M")
    expect_that(res, is_a("rlm"))
    expect_that(res, is_a("lm"))
    expect_equivalent(round(res$coefficients, 3), 2.782)
})

test_that("RobustLinearRegression with car data and MM-method", {
    res = RobustLinearRegression(cars$speed, cars$dist, method="MM")
    expect_equivalent(round(res$coefficients, 3), 2.727)
})

test_that("RobustLinearRegression with short vectors", {
    x = c(2, 10,  4, 22, 16, 10, 18, 26, 34, 17)
    y = c(4,  4,  7,  7,  8,  9, 10, 10, 10, 11 )
    res = RobustLinearRegression(x, y, method="MM")
    expect_equivalent(round(res$coefficients, 3), 0.415)
    # of converged resi
    expect_equivalent(length(res$conv), 4)
})



