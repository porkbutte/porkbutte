package com.techelevator;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Purchase {
    private int balance;
    private boolean isPurchaseComplete =  false;
    public int getBalance(){
        return balance;
    }
    public String getBalanceInDollars(){
        return "$" + ((double)balance / 100);
    }
    public void setBalance(int balance){
        this.balance = balance;
    }
    public void purchaseComplete(){
        isPurchaseComplete = true;
    }
    public boolean getPurchaseStatus(){
        return isPurchaseComplete;
    }

    public void printReceipt(String itemName, String itemSlot, int itemPrice) {
        File log = new File("log.txt");
        if (!log.exists()) {
            try {
                log.createNewFile();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            try (PrintWriter printWriter = new PrintWriter(new FileWriter(log, true))) {
                printWriter.println("DateTime                     | Product | Slot | Amount Accepted | Balance");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(log, true))) {
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-uuuu hh:mm a");
            printWriter.print(formatter.format(dateTime) + " | ");
            printWriter.print(itemName + " | ");
            printWriter.print(itemSlot + " | ");
            printWriter.print(getBalanceInDollars() + " | ");
            printWriter.println(getBalanceInDollars());
        } catch (Exception e) {
            System.out.println("There was a problem writing to the log file");
            System.out.println(e.getMessage());
        }
    }

    public String insertMoney(int amount) {
        File log = new File("log.txt");
        String newBalance = ("$" + (getBalance() + amount));
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(log, true))) {
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-uuuu hh:mm a");
            printWriter.print(formatter.format(dateTime) + " | ");
            printWriter.println("Fed Money | n/a | $" + amount + " | " + newBalance);
        } catch (Exception e) {
            System.out.println("There was a problem writing to the log file");
            System.out.println(e.getMessage());
        }
        if (amount == 1 || amount == 2 || amount == 5 || amount == 10) {
            balance += amount * 100;
            return amount * 100 + " dollar(s) accepted.";
        } else {
            return "Amount not accepted. Please only feed whole dollar amounts into the vending machine.";
        }
    }

    public String getChange(){
        int nickels = 0;
        int dimes = 0;
        int quarters = 0;
        File log = new File("log.txt");
        try(PrintWriter printWriter = new PrintWriter(new FileWriter(log, true))){
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-uuuu hh:mm a");
            printWriter.print(formatter.format(dateTime) + " | ");
            printWriter.println("Dispensed Change | n/a | " + getBalanceInDollars() + " | 0.00");
        } catch(Exception e){
            System.out.println("There was a problem writing to the log file");
        } while (balance >= 25){
            quarters++;
            balance -= 25;
        } while (balance >= 10){
            dimes ++;
            balance -= 10;
        } while (balance >= 5){
            nickels++;
            balance -= 5;
        }
        return quarters + " quarter(s), " + dimes + " dime(s) and " + nickels + " nickel(s)";
    }
}