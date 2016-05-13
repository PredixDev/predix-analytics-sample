from sklearn.linear_model import LinearRegression
import logging

class LocomotiveRegression:

    def __init__(self):
        print "Locomotive Linear Regression"

    def train_and_predict(self, training_df, testing_df):
        """
            Train a linear regression model for Locomotive dataset and
            perform predictions on the test dataset.

            Args:
                training_df: pandas.DataFrame with columns
                            ['loco_speed', 'wind_speed', 'RTM']
                testing_df: pandas.DataFrame with columns
                            ['loco_speed', 'wind_speed']

            Returns:
        """

        # do feature transformation and
        # add locomotive speed squared as a non linear feature
        training_df["loco_speed_sqr"] = training_df["loco_speed"]**2
        testing_df["loco_speed_sqr"] = testing_df["loco_speed"]**2

        # dependent variables
        feature_columns = ['loco_speed','wind_speed', "loco_speed_sqr"]
        # target and prediction variable
        label_column = 'RTM'

        # transform data into numpy arrays to use with
        # scikit regression algorithm

        # training data
        train_feats = training_df[feature_columns].values
        train_targets = training_df[label_column].values

        # test data
        test_feats = testing_df[feature_columns].values
        test_targets = testing_df[label_column].values

        # train regression model and perform predictions
        lr = LinearRegression()
        model = lr.fit(train_feats, train_targets)

        # preform predictions for the test set using the fitted model
        # import pdb; pdb.set_trace()
        test_pred = model.predict(test_feats).tolist()
        logging.info("R^2: " + str(test_pred))
        
        # prediction scores in terms of R^2 (unused)
        score = [model.score(test_feats, test_targets.T)]
        logging.info("Predictions: " + str(score))
        
        # return predicted labels as a list
        return (test_pred, score)
