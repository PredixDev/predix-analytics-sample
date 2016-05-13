from analytic import LocomotiveRegression
import pandas as pd


def mapper(*args,**kwargs):
    """Driver for Locomotive Regression analytic"""

    loco_reg = LocomotiveRegression()

    # extract training and test data
    training_df = pd.DataFrame(kwargs["train"])
    testing_df = pd.DataFrame(kwargs["test"])

    # get the predictions from the analytic
    predictions, score = loco_reg.train_and_predict(training_df, testing_df)

    # package results into a dictionary to pass them on to
    # analytic framework
    result = {"Prediction": predictions, "R2": score}

    return result


def results(args):
    return args

