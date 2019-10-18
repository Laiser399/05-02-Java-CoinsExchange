package com.mai;

import java.io.IOException;

public class Main {
    public static String simplifyString(String string) {
        return string.trim().replaceAll("\\s+", " ");
    }

    public static void main(String[] args) {
        MoneyChanger changer = new MoneyChanger();
        try {
            changer.start();
        }
        catch (IOException e) {
            System.out.println("IO error: " + e.getMessage());
        }

    }
}
