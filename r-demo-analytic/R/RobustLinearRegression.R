library(MASS)

#' Fit a linear model by robust regression using an M estimator.
#'
#' @param x a matrix or data frame containing the explanatory variables.
#' @param y the response: a vector of length the number of rows of x.
#' @param ... arguments passed on to MASS::rlm and underlying functions
#'
#' @return linear regression model
#' @export
#'
#' @examples library(MASS);RobustLinearRegression(cars$speed, cars$dist)
RobustLinearRegression <- function(x, y, ...) {
    rlm(x,y, ...)
}
