
from analytic import demoAdder

if __name__ == "__main__":
    dm = demoAdder()
    assert dm.add('{"number2": 45, "number1": 34}') == {"result": 79}
