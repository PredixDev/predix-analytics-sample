
def driver(*args,**kwargs):
    # decode args and kwargs
    from analytic import demoAdder
    da = demoAdder()
    return da.add(kwargs['number1'],kwargs['number2'])

def sum(sum):
    return sum
