
from analytic import demoAdder

if __name__ == "__main__":
    dm = demoAdder()
    assert dm.add(34,45) == 79
