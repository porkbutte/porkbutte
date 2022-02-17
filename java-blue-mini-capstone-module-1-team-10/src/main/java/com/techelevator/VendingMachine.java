package com.techelevator;

import java.io.*;
import java.util.*;
public class VendingMachine {
    private Map<String, Item> listOfItem = new HashMap<>();

    public VendingMachine(File inputFile) {
        try (FileReader fileReader = new FileReader(inputFile);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] itemProperties;
                itemProperties = line.split("\\|");
                int price = (int) (Double.parseDouble(itemProperties[2]) * 100);
                listOfItem.put(itemProperties[0], new Item(itemProperties[0].toUpperCase(), itemProperties[1], price));
            }
        } catch (Exception e) {
            System.out.println("There is a problem with the file.");
            System.out.println(e.getMessage());
        }
    }

    public List<String> itemsForSale() {
        List<String> itemsToAdd = new ArrayList<>();
        Set<String> keys = listOfItem.keySet();
        for (String key : keys) {
            Item item = listOfItem.get(key);
            StringBuffer itemsToAppend = new StringBuffer();
            itemsToAppend.append(item.getName() + " | ");
            itemsToAppend.append(item.getSlot() + " | ");
            itemsToAppend.append("$" + ((double) item.getPrice() / 100) + " | ");
            if (item.getStock() == 0) {
                itemsToAppend.append("SOLD OUT");
            } else {
                itemsToAppend.append(item.getStock());
                itemsToAdd.add(itemsToAppend.toString());
            }
        }
        return itemsToAdd;
    }

    public String purchase(Purchase newPurchase, String slot) {
        System.out.println(slot);
        Item itemPurchased = listOfItem.get(slot.toLowerCase());
        if (itemPurchased.getStock() == 0) {
            return "Sorry, we're sold out of this item! :(";
        }
        if (itemPurchased.getPrice() <= newPurchase.getBalance()) {
            newPurchase.printReceipt(itemPurchased.getName(), itemPurchased.getSlot(), itemPurchased.getPrice());
            itemPurchased.buy();
            newPurchase.setBalance(newPurchase.getBalance() - itemPurchased.getPrice());
            if (itemPurchased.getSlot().startsWith("A")) {
                itemPurchased = new Chip(slot, itemPurchased.getName(), itemPurchased.getPrice());
            }
            if (itemPurchased.getSlot().startsWith("B")) {
                itemPurchased = new Candy(slot, itemPurchased.getName(), itemPurchased.getPrice());
            }
            if (itemPurchased.getSlot().startsWith("C")) {
                itemPurchased = new Drink(slot, itemPurchased.getName(), itemPurchased.getPrice());
            }
            if (itemPurchased.getSlot().startsWith("D")) {
                itemPurchased = new Gum(slot, itemPurchased.getName(), itemPurchased.getPrice());
            }
            return "You have purchased one " + itemPurchased.getName() + "." + itemPurchased.getSound();
        } else {
            return "Sorry! Your current balance is not enough to purchase this item. Please insert more money. :)";
        }
    }

    public void run() {
        File input = new File("vendingmachine.csv");
        VendingMachine vendingMachine = new VendingMachine(input);
        Scanner scanner = new Scanner(System.in);
        while (true) {

            System.out.println("Thanks for using the Vendo-Matic 800! Please select an option: \n(1) Display Vending Machine Items. \n(2) Purchase Item. \n(3) End Transaction. \n>>>");
            String userInput = scanner.next();
            if ((!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3"))) {
                System.out.println("Please enter (1) to display items, (2) to select an item, or (3) to end the transaction.\n>>>");
            }
            if (userInput.equals("1")) {
                for (String stock : vendingMachine.itemsForSale()) {
                    System.out.println(stock);
                }
            }
            if (userInput.equals("2")) {
                Purchase newPurchase = new Purchase();
                while (!newPurchase.getPurchaseStatus()) {
                    System.out.println("(1)Feed Money \n(2)Select Product \n(3)Finish Transaction");
                    System.out.println("Current Money Provided: \n>>> " + newPurchase.getBalanceInDollars());
                    String inputNumber = scanner.next();
                    if (!inputNumber.equals("1") && !inputNumber.equals("2") && !inputNumber.equals("3")) {
                        System.out.println("Please enter (1) to enter money, (2) to select an item, or (3) to end your transaction. \n>>>");
                    }
                    if (inputNumber.equals("1")) {
                        System.out.println("Machine accepts dollar amounts of 1, 2, 5, and 10. Please enter money: \n>>>");

                        try{
                        String amount = scanner.next();
                        newPurchase.insertMoney(Integer.parseInt(amount));
                        }catch (Exception e){
                            System.out.println("Please only enter dollar amounts into the machine. Thank you!");
                        }
                        System.out.println("Thanks for inserting money!");
                    }
                    if (inputNumber.equals("2")) {
                        for (String stock : vendingMachine.itemsForSale()) {
                            System.out.println(stock);
                        }
                        System.out.println("What would you like today? Please enter selection in slot format (ex. A1, B3, etc.): \n>>>");
                        try {
                            scanner.reset();
                            String itemChoice = scanner.next();
                            System.out.println(vendingMachine.purchase(newPurchase, itemChoice));
                        } catch (Exception e) {
                            System.out.println("Slot invalid. Returning to selection screen. Please enter (1) to enter money, (2) to select an item, or (3) to end your transaction. \n>>> ");
                        }
                    } if (inputNumber.equals("3")) {
                            System.out.println("Your change is: " + newPurchase.getChange());
                            newPurchase.purchaseComplete();
                        }
                    }
                }
                if (userInput.equals("3")) {
                    System.out.println("Thanks for using the Vendo-Matic 800!");
                }
            }
        }


    }




    /* we tried to add a feature where (1) instead of printing the available stock, prints a list of item descriptions, but we never finished it
    public String itemDescriptions(File inputFile)  {
        try(FileReader fileReader = new FileReader(inputFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] itemProperties;
                itemProperties = line.split("\\|");
                listOfItemDescriptions.put(itemProperties[0], itemProperties[4]);
            }
        } catch (Exception e){
            System.out.println("There is a problem with the file.");
            System.out.println(e.getMessage());
        }
        String key = listOfItemDescriptions.get()
        }

    } */
// private Map<String, String> listOfItemDescriptions =  new HashMap<>();