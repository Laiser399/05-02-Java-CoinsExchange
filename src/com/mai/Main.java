package com.mai;


public class Main {
    public static String simplifyString(String string) {
        return string.trim().replaceAll("\\s+", " ");
    }

    public static void main(String[] args) {
        MoneyChanger changer = new MoneyChanger();
        changer.start();
    }
}
