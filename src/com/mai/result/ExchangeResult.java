package com.mai.result;


public abstract class ExchangeResult {
    public static ExchangeResult createValid() {
        return new ValidResult();
    }

    public static ExchangeResult getInvalid() {
        return InvalidResult.invalidResult;
    }


    abstract public boolean isValid();

    abstract public void add(int cost, int count);

}
