package com.mai.result;

import java.util.*;

class ValidResult extends ExchangeResult {
    private Map<Integer, Integer> costToCount = new HashMap<>();

    ValidResult() {}

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void add(int cost, int count) {
        if (cost <= 0 || count <= 0)
            return;

        int prevCount = 0;
        if (costToCount.containsKey(cost))
            prevCount = costToCount.remove(cost);
        costToCount.put(cost, prevCount + count);
    }

    @Override
    public String toString() {
        List<Integer> costs = new ArrayList<>(costToCount.keySet());
        costs.sort(Comparator.reverseOrder());
        StringBuilder result = new StringBuilder();
        for (Integer cost : costs) {
            if (result.length() != 0)
                result.append(" ");
            result.append(cost);
            result.append('[');
            result.append(costToCount.get(cost));
            result.append(']');
        }
        return result.toString();
    }
}
