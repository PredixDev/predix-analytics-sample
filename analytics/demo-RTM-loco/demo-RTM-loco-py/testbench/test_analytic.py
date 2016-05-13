'''Offline testing of the analytic without any predix dependencies
Not part of predix app'''

# export PYTHONPATH=..:$PYTHONPATH
import json
from analytic import LocomotiveRegression
from driver.AnalyticDriver import mapper


def main():
    with open('input.json') as _fp:
        data = json.load(_fp)
    result = mapper(**data)
    print result

if __name__ == "__main__":
    main()