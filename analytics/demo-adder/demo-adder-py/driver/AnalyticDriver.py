
def driver(*args,**kwargs):
    # decode args and kwargs
    from analytic import demoAdder
    da = demoAdder()
    total =  da.add(kwargs['number1'],kwargs['number2'])
    return { "result" : total }

def sum(sum):
    return sum
