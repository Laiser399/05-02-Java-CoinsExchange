package com.mai;

import com.mai.result.ExchangeResult;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class MoneyChanger {

    public void start() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            int exchangeCost = fetchExchangeCost(reader);
            List<Integer> coinsCost = fetchCoinsCost(reader);

            ExchangeResult result = exchange(exchangeCost, coinsCost);
            if (result.isValid()) {
                System.out.println("Exchange result: " + result);
            }
            else {
                System.out.println("Exchange impossible.");
            }
        }
    }

    private int fetchExchangeCost(BufferedReader reader) throws IOException {
        Integer exchangeCost = null;
        do {
            System.out.print("Enter exchange cost: ");

            String exchangeCostString = Main.simplifyString(reader.readLine());

            try {
                exchangeCost = parseExchangeCost(exchangeCostString);
            }
            catch (ValueException e) {
                System.out.println("Error: " + e.getMessage());
            }
            catch (NumberFormatException e) {
                System.out.println("Error: Wrong number format.");
            }
        } while (exchangeCost == null);
        return exchangeCost;
    }

    private List<Integer> fetchCoinsCost(BufferedReader reader) throws IOException {
        List<Integer> coinsCost = null;
        do {
            System.out.print("Enter coins cost (through spaces): ");
            String coinsCostString = reader.readLine();
            try {
                coinsCost = parseCoinsCost(coinsCostString);
            }
            catch (ValueException e) {
                System.out.println("Error: " + e.getMessage());
            }
            catch (NumberFormatException e) {
                System.out.println("Error: Wrong number format.");
            }
        } while (coinsCost == null);
        return coinsCost;
    }

    private Integer parseExchangeCost(String exchangeCostString) throws ValueException, NumberFormatException{
        int exchangeCost = Integer.parseInt(exchangeCostString);
        if (exchangeCost <= 0)
            throw new ValueException("Exchange cost <= 0: \"" + exchangeCostString + "\".");
        else
            return exchangeCost;
    }

    private List<Integer> parseCoinsCost(String coinsCostString) throws ValueException, NumberFormatException {
        coinsCostString = Main.simplifyString(coinsCostString);

        List<Integer> result = new ArrayList<>();
        for (String coinStr : coinsCostString.split("\\s")) {
            int coin = Integer.parseInt(coinStr);
            if (coin <= 0)
                throw new ValueException("Wrong coin: \"" + coinStr + "\".");
            else if (!result.contains(coin))
                result.add(coin);
        }

        result.sort(Comparator.reverseOrder());
        return result;
    }

    private ExchangeResult exchange(int exchangeCost, List<Integer> sortedCoins) {
        if (exchangeCost <= 0)
            return ExchangeResult.getInvalid();
        if (sortedCoins.isEmpty())
            return ExchangeResult.getInvalid();

        int currentCoin = sortedCoins.get(0);
        if (exchangeCost % currentCoin == 0) {
            ExchangeResult result = ExchangeResult.createValid();
            result.add(currentCoin, exchangeCost / currentCoin);
            return result;
        }
        else {
            List<Integer> nextCoins = sortedCoins.subList(1, sortedCoins.size());
            for (int i = exchangeCost / currentCoin; i >= 0; --i) {
                ExchangeResult result = exchange(exchangeCost - currentCoin * i, nextCoins);
                if (result.isValid()) {
                    result.add(currentCoin, i);
                    return result;
                }
            }
            return ExchangeResult.getInvalid();
        }
    }

}
