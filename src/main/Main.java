package main;

import main.java.com.maisa.currencyconverter.service.CurrencyConverterService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            CurrencyConverterService service = new CurrencyConverterService();
            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("**************************");
                System.out.println("\uD83E\uDE99 CURRENCY CONVERTER \uD83E\uDE99");
                System.out.println("**************************");
                System.out.println("1. Convert currency");
                System.out.println("2. Exit");
                System.out.print("Please, choose an option: \n");
                System.out.println("**************************");

                int option = scanner.nextInt();

                switch (option) {
                    case 1:
                        System.out.print("Enter the source currency code (e.g., USD): ");
                        String fromCurrency = scanner.next().toUpperCase();
                        System.out.print("Enter the target currency code (e.g., EUR): ");
                        String toCurrency = scanner.next().toUpperCase();
                        System.out.print("Enter the amount to convert: ");
                        double amount = scanner.nextDouble();
                        try {
                            double result = service.convert(fromCurrency, toCurrency, amount);
                            System.out.printf("%.2f %s = %.2f %s\n", amount, fromCurrency, result, toCurrency);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case 2:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }

            scanner.close();
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}

