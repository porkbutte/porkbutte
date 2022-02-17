package com.techelevator;

import java.io.File;


public class Main {

	public static void main(String[] args) {

		File inputFile = new File("vendingmachine.csv");
		VendingMachine vendingMachine = new VendingMachine(inputFile);
		vendingMachine.run();
	}
	}

