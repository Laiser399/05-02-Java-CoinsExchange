package com.mai.result;

class InvalidResult extends ExchangeResult {
    final static InvalidResult invalidResult = new InvalidResult();

    private InvalidResult() {}

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public void add(int cost, int count) {}
}
