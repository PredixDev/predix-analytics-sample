import json


class demoAdder:
    def __init__(self):
        print("Create pyadder")

    def add(self, data):
        data_json = json.loads(data)
        number1 = data_json.get("number1")
        number2 = data_json.get("number2")
        sum = number1 + number2
        return {"result": sum}
